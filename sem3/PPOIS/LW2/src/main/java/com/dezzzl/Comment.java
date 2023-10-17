package com.dezzzl;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Comment {
    private int id;
    private final User user;
    private final String text;
    private final Date date;

    /**
     * Конструктор, создающий комментарий по его id, user, text, date
     *
     * @param id   id комментария
     * @param user пользователь, оставивший комментарий
     * @param text текст комментария
     * @param date дата публикации комментария
     */
    public Comment(int id, User user, String text, Date date) {
        this.id = id;
        this.user = user;
        this.text = text;
        this.date = date;
    }

    /**
     * Возвращает текст комментария
     *
     * @return текст комментария
     */
    public String getText() {
        return text;
    }

    /**
     * Возвращает дату публикации комментаиря
     *
     * @return дата публикации комментария
     */
    public Date getDate() {
        return date;
    }

    /**
     * Возвращает комментарий в виде строки
     *
     * @return комментарий в виде строки
     */
    @Override
    public String toString() {
        StringBuilder st = new StringBuilder();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm  dd.MM.yy");
        String formattedDate = dateFormat.format(getDate());
        st.append(user.getUsername() + " отправил в " + formattedDate + "\n");
        st.append(getText() + "\n");
        return st.toString();
    }
}
