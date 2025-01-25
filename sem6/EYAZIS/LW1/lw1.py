import json
import os
import re
from collections import Counter
from tkinter import ttk, filedialog, simpledialog, messagebox, Toplevel, Label, Button
from striprtf.striprtf import rtf_to_text
import spacy
import tkinter as tk
import textwrap
from openpyxl import Workbook

class TextAnalyzerApp:
    def __init__(self, root):
        self.root = root
        self.root.title("Анализ текста")
        self.root.geometry("1300x600")

        # Загрузка конфигурации
        self.load_config()

        # Инициализация данных
        self.data_file = self.config["DATA_FILE"]
        self.case_map = self.config["CASE_MAP"]
        self.pos_map = self.config["POS_MAP"]
        self.number_map = self.config["NUMBER_MAP"]
        self.gender_map = self.config["GENDER_MAP"]

        # Настройка интерфейса
        self.setup_ui()

        # Загрузка данных
        self.update_table(self.load_existing_data())

    def load_config(self):
        with open("config.json", "r", encoding="utf-8") as config_file:
            self.config = json.load(config_file)

    def load_existing_data(self):
        if os.path.exists(self.data_file):
            with open(self.data_file, "r", encoding="utf-8") as file:
                return json.load(file)
        return []


    def save_to_file(self, new_data):
        with open(self.data_file, "w", encoding="utf-8") as file:
            json.dump(new_data, file, ensure_ascii=False, indent=4)


    def get_info_from_text(self, text):
        nlp = spacy.load("ru_core_news_sm")
        doc = nlp(text)
        word_info = {}
        counter = Counter(token.text for token in doc if token.is_alpha)

        for token in doc:
            if not token.is_alpha or not self.is_russian(token.text):
                continue
            if token.text in word_info:
                continue

            morph_info = []
            if token.pos_:
                morph_info.append(f"Часть речи: {self.pos_map.get(token.pos_, token.pos_)}")
            if token.morph.get("Gender"):
                genders = [self.gender_map.get(g, g) for g in token.morph.get("Gender")]
                morph_info.append(f"Род: {', '.join(genders)}")
            if token.morph.get("Number"):
                numbers = [self.number_map.get(n, n) for n in token.morph.get("Number")]
                morph_info.append(f"Число: {', '.join(numbers)}")
            if token.morph.get("Case"):
                cases = [self.case_map.get(c, c) for c in token.morph.get("Case")]
                morph_info.append(f"Падеж: {', '.join(cases)}")

            morph_info_str = "\n".join(morph_info) if morph_info else "Нет данных"

            word_info[token.text] = (token.text, token.lemma_, counter[token.text], morph_info_str)

        return list(word_info.values())

    def is_russian(self, word):
        return bool(re.fullmatch(r"[а-яА-ЯёЁ]+", word))


    def read_text_from_file(self, file_path):
        if not os.path.isfile(file_path):
            raise FileNotFoundError(f"Файл не найден: {file_path}")
        _, extension = os.path.splitext(file_path)
        extension = extension.lower()
        if extension not in ['.txt', '.rtf']:
            raise ValueError(f"Неподдерживаемый формат файла: {extension}. Поддерживаются только .txt и .rtf.")
        if extension == '.txt':
            with open(file_path, "r", encoding="utf-8") as file:
                return file.read()
        else:
            with open(file_path, "r", encoding="utf-8") as file:
                return rtf_to_text(file.read())


    def update_table(self, data):
        max_meta_width = 50

        for row in self.tree.get_children():
            self.tree.delete(row)

        sorted_data = sorted(data, key=lambda item: item[0].lower())

        for item in sorted_data:
            wrapped_meta = textwrap.fill(item[3], width=max_meta_width)
            self.tree.insert("", "end", values=(item[0], item[1], item[2], wrapped_meta))


    def on_select_file(self):
        file_path = filedialog.askopenfilename(
            title="Выберите файл",
            filetypes=[("Текстовые файлы", "*.txt *.rtf")]
        )
        if file_path:
            data = self.read_text_from_file(file_path)
            info = self.get_info_from_text(data)
            combined_data = self.load_existing_data()
            combined_dict = {entry[0]: entry for entry in combined_data}
            for word, lemma, count, meta in info:
                if word in combined_dict:
                    combined_dict[word][2] += count
                else:
                    combined_dict[word] = [word, lemma, count, meta]
            self.save_to_file(list(combined_dict.values()))
            self.update_table(list(combined_dict.values()))


    def add_meta_info(self):
        selected_item = self.tree.selection()
        if not selected_item:
            tk.messagebox.showwarning("Ошибка", "Пожалуйста, выберите запись для добавления морфологической информации.")
            return

        word = self.tree.item(selected_item[0])["values"][0]
        current_meta = self.tree.item(selected_item[0])["values"][3]
        new_meta = simpledialog.askstring("Морфологическая информация", f"Введите дополнительную морфологическую для слова '{word}':", initialvalue=current_meta)
        if new_meta is None:
            return

        data = self.load_existing_data()
        for item in data:
            if item[0] == word:
                item[3] = new_meta
                break

        self.save_to_file(data)
        self.update_table(data)


    def search_words(self, event=None):
        """Фильтрует данные в таблице на основе всех критериев."""
        word_query = self.word_entry.get().lower()
        count_query = self.count_entry.get()
        meta_query = self.meta_entry.get().lower()
        data = self.load_existing_data()

        filtered_data = []
        for item in data:
            word, lemma, count, meta = item

            # Фильтрация по слову (начало слова)
            if word_query and not word.lower().startswith(word_query):
                continue

            # Фильтрация по количеству (больше указанного числа)
            if count_query:
                try:
                    if count <= int(count_query):
                        continue
                except ValueError:
                    messagebox.showerror("Ошибка", "Введите число для фильтрации по количеству.")
                    return

            # Фильтрация по морфологической информации (наличие слова в описании)
            if meta_query and meta_query not in meta.lower():
                continue

            # Если запись прошла все фильтры, добавляем её
            filtered_data.append(item)

        self.update_table(filtered_data)

    def save_to_excel(self):
        # Проверяем, есть ли выбранные строки
        selected_items = self.tree.selection()
        data_to_save = []

        if selected_items:
            # Если строки выбраны, собираем их данные
            for item_id in selected_items:
                row = self.tree.item(item_id, "values")
                data_to_save.append(row)
        else:
            # Если строки не выбраны, сохраняем все строки
            for item_id in self.tree.get_children():
                row = self.tree.item(item_id, "values")
                data_to_save.append(row)

        # Открываем диалог для сохранения файла
        file_path = filedialog.asksaveasfilename(defaultextension=".xlsx", filetypes=[("Excel files", "*.xlsx")])
        if file_path:
            # Создаем Excel файл
            workbook = Workbook()
            sheet = workbook.active
            sheet.title = "Данные"

            # Записываем заголовки
            headers = ["Слово", "Лексема", "Количество", "Морфологическая информация"]
            sheet.append(headers)

            # Записываем данные
            for row in data_to_save:
                sheet.append(row)

            # Сохраняем файл
            workbook.save(file_path)
            print("Файл успешно сохранён:", file_path)

    def show_help(self):
        # Создаем новое окно
        help_window = Toplevel(root)
        help_window.title("Помощь")
        help_window.geometry("400x300")
        help_window.resizable(False, False)

        # Текст помощи
        help_text = (
            "Как пользоваться приложением:\n\n"
            "1. Нажмите 'Выбрать файл', чтобы загрузить текстовый файл.\n"
            "2. Нажмите 'Добавить морфологическую информацию', чтобы проанализировать данные.\n"
            "3. В таблице отображаются слова, их лексемы, количество и морфологическая информация.\n"
            "4. Используйте фильтры справа:\n"
            "   - 'Поиск по слову' — для поиска слов по вхождению.\n"
            "   - 'Фильтр по количеству' — чтобы отобразить слова с количеством больше заданного.\n"
            "   - 'Поиск по морфологической информации' — для поиска по части речи.\n"
            "5. Нажмите 'Сохранить в Excel', чтобы экспортировать таблицу.\n"
            "\nЕсли у вас возникли вопросы, обратитесь к разработчику!"
        )

        # Отображаем текст помощи
        help_label = Label(help_window, text=help_text, justify="left", wraplength=380, padx=10, pady=10)
        help_label.pack(expand=True, fill="both")

        # Кнопка для закрытия окна
        close_button = Button(help_window, text="Закрыть", command=help_window.destroy)
        close_button.pack(pady=10)
    def setup_ui(self):
        # Главный контейнер
        main_frame = tk.Frame(root)
        main_frame.pack(fill="both", expand=True)

        # Левая часть (таблица)
        table_frame = tk.Frame(main_frame)
        table_frame.pack(side="left", fill="both", expand=True, padx=10, pady=10)

        # Настройка таблицы
        style = ttk.Style()
        style.configure("Treeview", rowheight=50)

        columns = ("word", "lemma", "count", "meta")
        self.tree = ttk.Treeview(table_frame, columns=columns, show="headings", height=25)
        self.tree.heading("word", text="Слово")
        self.tree.heading("lemma", text="Лексема")
        self.tree.heading("count", text="Количество")
        self.tree.heading("meta", text="Морфологическая информация")
        self.tree.column("word", width=150)
        self.tree.column("lemma", width=150)
        self.tree.column("count", width=100)
        self.tree.column("meta", width=400)

        # Прокрутка
        scrollbar = ttk.Scrollbar(table_frame, orient="vertical", command=self.tree.yview)
        self.tree.configure(yscroll=scrollbar.set)
        scrollbar.pack(side="right", fill="y")
        self.tree.pack(expand=True, fill="both")

        # Правая часть (кнопки и фильтры)
        right_frame = tk.Frame(main_frame)
        right_frame.pack(side="right", fill="y", padx=10, pady=10)

        self.setup_controls(right_frame)

    def setup_controls(self, right_frame):
        # Область с кнопками
        buttons_frame = ttk.LabelFrame(right_frame, text="Управление", padding=10)
        buttons_frame.pack(fill="x", pady=(0, 10))

        # Кнопка "Выбрать файл"
        choose_file_button = ttk.Button(buttons_frame, text="Выбрать файл", command=self.on_select_file)
        choose_file_button.pack(side="top", fill="x", pady=5)

        # Кнопка "Добавить морфологическую информацию"
        add_info_button = ttk.Button(buttons_frame, text="Добавить морфологическую информацию", command=self.add_meta_info)
        add_info_button.pack(side="top", fill="x", pady=5)

        save_excel_button = ttk.Button(buttons_frame, text="Сохранить в Excel", command=self.save_to_excel)
        save_excel_button.pack(side="top", fill="x", pady=5)

        help_button = ttk.Button(buttons_frame, text="Помощь", command=self.show_help)
        help_button.pack(side="top", fill="x", pady=5)

        # Область фильтров
        filter_frame = ttk.LabelFrame(right_frame, text="Фильтр", padding=10)
        filter_frame.pack(fill="x", pady=(10, 0))

        # Поле для поиска по слову
        word_label = tk.Label(filter_frame, text="Поиск по слову:")
        word_label.pack(pady=5, anchor="w")
        self.word_entry = tk.Entry(filter_frame, width=30)
        self.word_entry.pack(pady=5)
        self.word_entry.bind("<KeyRelease>", self.search_words)

        # Поле для поиска по количеству
        count_label = tk.Label(filter_frame, text="Фильтр по количеству (больше числа):")
        count_label.pack(pady=5, anchor="w")
        self.count_entry = tk.Entry(filter_frame, width=30)
        self.count_entry.pack(pady=5)
        self.count_entry.bind("<KeyRelease>", self.search_words)

        # Поле для поиска по морфологической информации
        meta_label = tk.Label(filter_frame, text="Поиск по морфологической информации:")
        meta_label.pack(pady=5, anchor="w")
        self.meta_entry = tk.Entry(filter_frame, width=30)
        self.meta_entry.pack(pady=5)
        self.meta_entry.bind("<KeyRelease>", self.search_words)

if __name__ == '__main__':
    root = tk.Tk()
    app = TextAnalyzerApp(root)
    root.mainloop()