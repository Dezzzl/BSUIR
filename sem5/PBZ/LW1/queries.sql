--Вариант 21
--Самута Д. В.
--Группа 221703

CREATE DATABASE PBZ_LW1;

CREATE TABLE S
(
    id     VARCHAR(8) PRIMARY KEY,
    name   VARCHAR(36),
    status INTEGER,
    city   VARCHAR(36)
);

CREATE TABLE P
(
    id    VARCHAR(8) PRIMARY KEY,
    name  VARCHAR(36),
    color VARCHAR(36),
    size  INTEGER,
    city  VARCHAR(36)
);

CREATE TABLE J
(
    id   VARCHAR(8) PRIMARY KEY,
    name VARCHAR(36),
    city VARCHAR(36)
);

CREATE TABLE SPJ
(
    s_id VARCHAR(36) REFERENCES S (id),
    p_id VARCHAR(36) REFERENCES P (id),
    j_id VARCHAR(36) REFERENCES J (id),
    s    INTEGER,
    PRIMARY KEY (s_id, p_id, j_id)
);

INSERT INTO S(id, name, status, city)
VALUES ('П1', 'Петров', 20, 'Москва'),
       ('П2', 'Синицин', 10, 'Таллинн'),
       ('П3', 'Федоров', 30, 'Таллинн'),
       ('П4', 'Чаянов', 20, 'Минск'),
       ('П5', 'Крюков', 30, 'Киев');


INSERT INTO P(id, name, color, size, city)
VALUES ('Д1', 'Болт', 'Красный', 12, 'Москва'),
       ('Д2', 'Гайка', 'Зеленая', 17, 'Минск'),
       ('Д3', 'Диск', 'Черный', 17, 'Вильнюс'),
       ('Д4', 'Диск', 'Черный', 14, 'Москва'),
       ('Д5', 'Корпус', 'Красный', 12, 'Минск'),
       ('Д6', 'Крышки', 'Красный', 19, 'Москва');

INSERT INTO J(id, name, city)
VALUES ('ПР1', 'ИПР1', 'Минск'),
       ('ПР2', 'ИПР2', 'Таллинн'),
       ('ПР3', 'ИПР3', 'Псков'),
       ('ПР4', 'ИПР4', 'Псков'),
       ('ПР5', 'ИПР5', 'Москва'),
       ('ПР6', 'ИПР6', 'Саратов'),
       ('ПР7', 'ИПР7', 'Москва');


INSERT INTO SPJ(S_ID, P_ID, J_ID, s)
VALUES ('П1', 'Д1', 'ПР1', 200),
       ('П1', 'Д1', 'ПР2', 700),
       ('П2', 'Д3', 'ПР1', 400),
       ('П2', 'Д2', 'ПР2', 200),
       ('П2', 'Д3', 'ПР3', 200),
       ('П2', 'Д3', 'ПР4', 500),
       ('П2', 'Д3', 'ПР5', 600),
       ('П2', 'Д3', 'ПР6', 400),
       ('П2', 'Д3', 'ПР7', 800),
       ('П2', 'Д5', 'ПР2', 100),
       ('П3', 'Д3', 'ПР1', 200),
       ('П3', 'Д4', 'ПР2', 500),
       ('П4', 'Д6', 'ПР3', 300),
       ('П4', 'Д6', 'ПР7', 300),
       ('П5', 'Д2', 'ПР2', 200),
       ('П5', 'Д2', 'ПР4', 100),
       ('П5', 'Д5', 'ПР5', 500),
       ('П5', 'Д5', 'ПР7', 100),
       ('П5', 'Д6', 'ПР2', 200),
       ('П5', 'Д1', 'ПР2', 100),
       ('П5', 'Д3', 'ПР4', 200),
       ('П5', 'Д4', 'ПР4', 800),
       ('П5', 'Д5', 'ПР4', 400),
       ('П5', 'Д6', 'ПР4', 500),
       ('П1', 'Д1', 'ПР3', 200),
       ('П1', 'Д1', 'ПР4', 700),
       ('П1', 'Д1', 'ПР5', 200),
       ('П1', 'Д1', 'ПР6', 700),
       ('П1', 'Д1', 'ПР7', 200);


--12 запрос
-- Получить номера деталей, поставляемых для всех проектов, обеспечиваемых поставщиком
-- из того же города, где размещен проект.
SELECT distinct p_id
FROM SPJ
         JOIN public.j j on SPJ.j_id = j.id
         JOIN public.s s on s.id = SPJ.s_id
WHERE j.city = s.city;

--31 запрос
--Получить номера поставщиков, поставляющих одну и ту же деталь для всех проектов.
SELECT e.s_id
FROM (SELECT spj.s_id, count(spj.j_id) AS c
      FROM spj
      GROUP BY spj.s_id, spj.p_id) AS e
WHERE e.c = (SELECT count(j.id) FROM j);

--3 запрос
-- Получить номера поставщиков, которые обеспечивают проект ПР1.
SELECT post.id
FROM S post
         JOIN SPJ S2 on post.id = S2.s_id
WHERE S2.j_id = 'ПР1';

--15 запрос
-- Получить общее число проектов, обеспечиваемых поставщиком П1.
SELECT count(DISTINCT spj.j_id)
FROM SPJ spj
WHERE spj.s_id = 'П5';

--18 запрос
-- Получить номера деталей, поставляемых для некоторого проекта со средним количеством больше 320.
SELECT av.p_id FROM (SELECT spj.p_id, avg(spj.s) AS ss
             FROM SPJ spj
             GROUP BY spj.p_id) av
WHERE av.ss>320;

--21 запрос
-- Получить номера деталей, поставляемых для какого-либо проекта в
-- Лондоне (сделал для Минска, т.к. с ним данных достаточно).
SELECT distinct spj.p_id FROM SPJ spj
JOIN public.j j2 on j2.id = spj.j_id
WHERE j2.city = 'Минск';

--25 запрос
--Получить номера проектов, город которых стоит первым в алфавитном списке городов.
SELECT j.id FROM J j
WHERE j.city =(
        SELECT j.city FROM J j
        ORDER BY j.city
        LIMIT 1);

--23 запрос
-- Получить номера поставщиков, поставляющих по крайней мере одну деталь, поставляемую по крайней
-- мере одним поставщиком, который поставляет по крайней мере одну красную деталь.
SELECT distinct SPJ.s_id FROM SPJ
WHERE SPJ.p_id in (SELECT distinct SPJ.p_id FROM spj
                   WHERE SPJ.s_id in (SELECT distinct spj.s_id FROM SPJ spj
                                      JOIN public.p p2 on p2.id = spj.p_id
                                      WHERE p2.color = 'Красный'));



--7 запрос
--Получить все такие тройки "номера поставщиков-номера деталей-номера проектов",
-- для которых выводимые поставщик, деталь и проект не размещены в одном городе.
SELECT spj.s_id, spj.p_id, spj.j_id
FROM spj
         JOIN public.p p on spj.p_id = p.id
         JOIN public.j j on spj.j_id = j.id
         JOIN public.s s on s.id = spj.s_id
WHERE p.city != j.city or s.city != j.city;

-- номер 27
-- Получить номера поставщиков, поставляющих деталь Д1 для некоторого проекта в
-- количестве, большем среднего количества деталей Д1 в поставках для этого проекта.
SELECT spj.s_id, spj.j_id, spj.s FROM spj
JOIN (SELECT spj.j_id, AVG(spj.s) AS avg_s
      FROM spj
      WHERE spj.p_id = 'Д1'
      GROUP BY spj.j_id
) AS a ON spj.j_id = a.j_id
WHERE spj.p_id = 'Д1' AND spj.s > a.avg_s;

--S - поставщики
--P - детали
--J - проекты