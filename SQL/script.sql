DROP TABLE IF EXISTS students, groups, teachers, audiences, disciplines, lessons CASCADE;
DROP SEQUENCE IF EXISTS sequence_group_id, sequence_audience_id, sequence_discipline_id, sequence_lesson_id CASCADE;

CREATE SEQUENCE sequence_group_id;

CREATE TABLE groups (
    id                  BIGSERIAL       PRIMARY KEY,
    name                VARCHAR(25)     NOT NULL,
    admissionYear       INT             NOT NULL
);

CREATE TABLE students (
    id                      BIGINT          PRIMARY KEY,
    name                    VARCHAR(35)     NOT NULL,
    emailAddress            VARCHAR(30)     NOT NULL,
    course                  VARCHAR(10)     NOT NULL,
    group_id                BIGINT          REFERENCES groups(id),
    CONSTRAINT student_pk CHECK (id > 0)
);

CREATE TABLE teachers (
    id                  BIGINT          PRIMARY KEY,
    name                VARCHAR(35)     NOT NULL,
    emailAddress        VARCHAR(30)     NOT NULL,
    qualification       VARCHAR(25)     NOT NULL
);

CREATE SEQUENCE sequence_audience_id;

CREATE TABLE audiences (
    id              BIGSERIAL   PRIMARY KEY,
    number          INT         NOT NULL,
    CONSTRAINT number CHECK (number > 0)
);

CREATE SEQUENCE sequence_discipline_id;

CREATE TABLE disciplines(
    id              BIGSERIAL       PRIMARY KEY,
    name            VARCHAR(50)     NOT NULL
);

CREATE SEQUENCE sequence_lesson_id;

CREATE TABLE lessons(
    lesson_id       BIGSERIAL       PRIMARY KEY,
    discipline_id   BIGINT          REFERENCES disciplines(id),
    date_start      TIMESTAMP       NOT NULL,
    date_end        TIMESTAMP       NOT NULL,
    audience_id     BIGINT          REFERENCES audiences(id),
    teacher_id      BIGINT          REFERENCES teachers(id),
    group_id        BIGINT          REFERENCES groups(id)
);

INSERT INTO groups VALUES( nextval('sequence_group_id'), 'ДКИ-ПОМ18', 2018 ),
    ( nextval('sequence_group_id'), 'ДКИ-ПОТр18', 2018 ),
    ( nextval('sequence_group_id'), 'ДКИ-ПОПг17', 2017 ),
    ( nextval('sequence_group_id'), 'ДЕА-ПОСт16', 2016 );

INSERT INTO students VALUES( 10000411, 'Николай Харченко', 'nk_harc@gmail.com', 'first' ),
    (10000412, 'Олексей Богданов', 'bogdanov@gmail.com', 'first'),
    (10000413, 'Тимофей Васейко', 't_vas@gmail.com', 'first'),
    (10000414, 'Оксана Рубаненко', 'oxs_rub@gmail.com', 'first'),
    (10000415, 'Татьяна Немашкало', 'tt_nemashko@gmail.com', 'first'),
    (10000416, 'Михаил Сторожко', 'mick_storoj@gmail.com', 'first'),
    (10000417, 'Артём Яшин', 'artem_yashin@gmail.com', 'first'),
    (10000418, 'Дмитрий Файнер', 'dm_fainer@gmail.com', 'first'),
    (10000419, 'Елена Хаченкова', 'elenhach@gmail.com', 'third'),
    (10000420, 'Николай Лепский', 'nk_leps@gmail.com', 'first'),
    (10000421, 'Евгений Сушко', 'sushka@gmail.com' , 'first'),
    (10000422, 'Михаил Купцов', 'm_kupzov@gmail.com', 'second'),
    (10000423, 'Дмитрий Васеленко', 'dimitrius@gmail.com', 'second'),
    (10000424, 'Катерина Рубан', 'kateruban@gmail.com', 'second'),
    (10000425, 'Алёна Чванина', 'alenacvav@gmail.com', 'third'),
    (10000426, 'Богдан Богомол', 'bog_bog@gmail.com', 'first'),
    (10000427, 'Андрей Яшинко', 'andru@gmail.com', 'first'),
    (10000428, 'Дмитрий Фоменко', 'fomenko@gmail.com', 'second'),
    (10000429, 'Елена Конотоп', 'kohjtjp@gmail.com', 'first'),
    (10000430, 'Семён Коваленко', 'kovalek@gmail.com', 'second'),
    (10000431, 'Николай Тризуб', 'niktrizub@gmail.com', 'third'),
    (10000432, 'Олексей Шаповал', 'shapoval@gmail.com', 'second'),
    (10000433, 'Анатолий Киценко', 'kicko_an@gmail.com', 'third'),
    (10000434, 'Оксана Митина', 'mitin_oxs@gmail.com', 'first'),
    (10000435, 'Екатерина Шарикало', 'sharikalo_ekat@gmail.com', 'first'),
    (10000436, 'Михаил Шевченко', 'mick_sheva@gmail.com', 'first'),
    (10000437, 'Артём Дудка', 'arti_dudka@gmail.com', 'third'),
    (10000438, 'Дмитрий Бондар', 'lm_bondar@gmail.com', 'third'),
    (10000439, 'Елена Божедай', 'bojedai@gmail.com', 'third');

INSERT INTO teachers VALUES(39808038040191, 'Ковалчук Любовь Васильевна', 'kovalchuklv@gmail.com', 'ст.препод.'),
    (72614939464103, 'Буданов Павел Феофанович', 'budanov_pf@gmail.com', 'доцент к.т.н.'),
    (17205626336334, 'Егорова Ольга Юрьевна', 'egorova_ou@gmail.com', 'доц.к.н.'),
    (89971548400703, 'Чернюк Артем Михайлович', 'chernuk_am@gmail.com', 'проф.д.н.'),
    (37722487125591, 'Литвин Олег Олегович', 'litvin_oo@gmail.com', 'ст.препод.');

INSERT INTO audiences VALUES(nextval('sequence_audience_id'), 100),
    (nextval('sequence_audience_id'), 101),
    (nextval('sequence_audience_id'), 102),
    (nextval('sequence_audience_id'), 103),
    (nextval('sequence_audience_id'), 200),
    (nextval('sequence_audience_id'), 210),
    (nextval('sequence_audience_id'), 256),
    (nextval('sequence_audience_id'), 430);

INSERT INTO disciplines VALUES(nextval('sequence_discipline_id'), 'Программная инженерия'),
    (nextval('sequence_discipline_id'), 'Основы энерго и ресурсосбережения'),
    (nextval('sequence_discipline_id'), 'Дидактические основы профессионального образования'),
    (nextval('sequence_discipline_id'), 'Теоретическая и прикладная механика'),
    (nextval('sequence_discipline_id'), 'Основы инженерно-педагогического творчества');

INSERT INTO lessons VALUES(nextval('sequence_lesson_id'), 1,'2019-04-04 08:00:00', '2019-04-04 09:20:00', 3, 39808038040191, 3),
    (nextval('sequence_lesson_id'), 3,'2019-04-04 09:30:00', '2019-04-04 10:50:00', 1, 72614939464103, 1),
    (nextval('sequence_lesson_id'), 5,'2019-04-04 11:20:00', '2019-04-04 12:50:00', 6, 37722487125591, 1);