DROP TABLE IF EXISTS students, groups, teachers, audiences, disciplines, lessons CASCADE;
DROP SEQUENCE IF EXISTS sequence_group_id, sequence_audience_id, sequence_discipline_id, sequence_lesson_id CASCADE;

CREATE SEQUENCE sequence_group_id;

CREATE TABLE groups (
	group_id            BIGSERIAL       PRIMARY KEY ,
	name                VARCHAR(25)     NOT NULL,
	admissionYear       INT             NOT NULL
);

CREATE TABLE students (
	name                    VARCHAR(35)     NOT NULL,
	emailAddress            VARCHAR(30)     NOT NULL,
	studentCardNumber_id    BIGINT          PRIMARY KEY,
	course                  VARCHAR(10)     NOT NULL,
	group_id                BIGINT          REFERENCES groups,
	CONSTRAINT student_pk CHECK (studentCardNumber_id > 0)
);

CREATE TABLE teachers (
	name                VARCHAR(35)     NOT NULL,
	emailAddress        VARCHAR(30)     NOT NULL,
	taxpayerID          BIGINT          PRIMARY KEY,
	qualification       VARCHAR(25)     NOT NULL
);

CREATE SEQUENCE sequence_audience_id;

CREATE TABLE audiences (
	audience_id     BIGSERIAL   PRIMARY KEY,
	number          INT         NOT NULL,
	CONSTRAINT number CHECK (number > 0)
);

CREATE SEQUENCE sequence_discipline_id;

CREATE TABLE disciplines(
	discipline_id   BIGSERIAL       PRIMARY KEY,
	name            VARCHAR(50)     NOT NULL
);

CREATE SEQUENCE sequence_lesson_id;

CREATE TABLE lessons(
	lesson_id       BIGSERIAL       PRIMARY KEY,
	discipline_id   BIGINT          REFERENCES disciplines,
	date_start      TIMESTAMP       NOT NULL,
	date_end        TIMESTAMP       NOT NULL,
	audience_id     BIGINT          REFERENCES audiences,
	teacher_id      BIGINT          REFERENCES teachers(taxpayerID),
	group_id        BIGINT          REFERENCES groups
);

INSERT INTO groups VALUES( nextval('sequence_group_id'), 'ДКИ-ПОМ18', 2018 );
INSERT INTO groups VALUES( nextval('sequence_group_id'), 'ДКИ-ПОТр18', 2018 );
INSERT INTO groups VALUES( nextval('sequence_group_id'), 'ДКИ-ПОПг17', 2017 );
INSERT INTO groups VALUES( nextval('sequence_group_id'), 'ДЕА-ПОСт16', 2016 );

INSERT INTO students VALUES('Николай Харченко', 'nk_harc@gmail.com', 10000411,'first' );
INSERT INTO students VALUES('Олексей Богданов', 'bogdanov@gmail.com', 10000412, 'first');
INSERT INTO students VALUES('Тимофей Васейко', 't_vas@gmail.com', 10000413, 'first',2);
INSERT INTO students VALUES('Оксана Рубаненко', 'oxs_rub@gmail.com', 10000414, 'first');
INSERT INTO students VALUES('Татьяна Немашкало', 'tt_nemashko@gmail.com', 10000415, 'first');
INSERT INTO students VALUES('Михаил Сторожко', 'mick_storoj@gmail.com', 10000416, 'first');
INSERT INTO students VALUES('Артём Яшин', 'artem_yashin@gmail.com', 10000417, 'first');
INSERT INTO students VALUES('Дмитрий Файнер', 'dm_fainer@gmail.com', 10000418, 'first');
INSERT INTO students VALUES('Елена Хаченкова', 'elenhach@gmail.com', 10000419, 'third');
INSERT INTO students VALUES('Николай Лепский', 'nk_leps@gmail.com', 10000420, 'first');
INSERT INTO students VALUES('Евгений Сушко', 'sushka@gmail.com' ,10000421, 'first');
INSERT INTO students VALUES('Михаил Купцов', 'm_kupzov@gmail.com', 10000422, 'second');
INSERT INTO students VALUES('Дмитрий Васеленко', 'dimitrius@gmail.com', 10000423, 'second');
INSERT INTO students VALUES('Катерина Рубан', 'kateruban@gmail.com', 10000424, 'second');
INSERT INTO students VALUES('Алёна Чванина', 'alenacvav@gmail.com', 10000425, 'third');
INSERT INTO students VALUES('Богдан Богомол', 'bog_bog@gmail.com', 10000426, 'first');
INSERT INTO students VALUES('Андрей Яшинко', 'andru@gmail.com', 10000427, 'first');
INSERT INTO students VALUES('Дмитрий Фоменко', 'fomenko@gmail.com', 10000428, 'second');
INSERT INTO students VALUES('Елена Конотоп', 'kohjtjp@gmail.com', 10000429, 'first');
INSERT INTO students VALUES('Семён Коваленко', 'kovalek@gmail.com', 10000430, 'second');
INSERT INTO students VALUES('Николай Тризуб', 'niktrizub@gmail.com', 10000431, 'third');
INSERT INTO students VALUES('Олексей Шаповал', 'shapoval@gmail.com', 10000432, 'second');
INSERT INTO students VALUES('Анатолий Киценко', 'kicko_an@gmail.com', 10000433, 'third');
INSERT INTO students VALUES('Оксана Митина', 'mitin_oxs@gmail.com', 10000434, 'first');
INSERT INTO students VALUES('Екатерина Шарикало', 'sharikalo_ekat@gmail.com', 10000435, 'first');
INSERT INTO students VALUES('Михаил Шевченко', 'mick_sheva@gmail.com', 10000436, 'first');
INSERT INTO students VALUES('Артём Дудка', 'arti_dudka@gmail.com', 10000437, 'third');
INSERT INTO students VALUES('Дмитрий Бондар', 'lm_bondar@gmail.com', 10000438, 'third');
INSERT INTO students VALUES('Елена Божедай', 'bojedai@gmail.com', 10000439, 'third');

INSERT INTO teachers VALUES('Ковалчук Любовь Васильевна', 'kovalchuklv@gmail.com', 39808038040191, 'ст.препод.');
INSERT INTO teachers VALUES('Буданов Павел Феофанович', 'budanov_pf@gmail.com', 72614939464103, 'доцент к.т.н.');
INSERT INTO teachers VALUES('Егорова Ольга Юрьевна', 'egorova_ou@gmail.com', 17205626336334, 'доц.к.н.');
INSERT INTO teachers VALUES('Чернюк Артем Михайлович', 'chernuk_am@gmail.com', 89971548400703, 'проф.д.н.');
INSERT INTO teachers VALUES('Литвин Олег Олегович', 'litvin_oo@gmail.com', 37722487125591, 'ст.препод.');

INSERT INTO audiences VALUES(nextval('sequence_audience_id'), 100);
INSERT INTO audiences VALUES(nextval('sequence_audience_id'), 101);
INSERT INTO audiences VALUES(nextval('sequence_audience_id'), 102);
INSERT INTO audiences VALUES(nextval('sequence_audience_id'), 103);
INSERT INTO audiences VALUES(nextval('sequence_audience_id'), 200);
INSERT INTO audiences VALUES(nextval('sequence_audience_id'), 210);
INSERT INTO audiences VALUES(nextval('sequence_audience_id'), 256);
INSERT INTO audiences VALUES(nextval('sequence_audience_id'), 430);

INSERT INTO disciplines VALUES(nextval('sequence_discipline_id'), 'Программная инженерия');
INSERT INTO disciplines VALUES(nextval('sequence_discipline_id'), 'Основы энерго и ресурсосбережения');
INSERT INTO disciplines VALUES(nextval('sequence_discipline_id'), 'Дидактические основы профессионального образования');
INSERT INTO disciplines VALUES(nextval('sequence_discipline_id'), 'Теоретическая и прикладная механика');
INSERT INTO disciplines VALUES(nextval('sequence_discipline_id'), 'Основы инженерно-педагогического творчества');

INSERT INTO lessons VALUES(nextval('sequence_lesson_id'), 1,'2019-04-04 08:00:00', '2019-04-04 09:20:00', 3, 39808038040191, 3);
INSERT INTO lessons VALUES(nextval('sequence_lesson_id'), 3,'2019-04-04 09:30:00', '2019-04-04 10:50:00', 1, 72614939464103, 1);
INSERT INTO lessons VALUES(nextval('sequence_lesson_id'), 5,'2019-04-04 11:20:00', '2019-04-04 12:50:00', 6, 37722487125591, 1);




