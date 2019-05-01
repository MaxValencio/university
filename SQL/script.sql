DROP TABLE IF EXISTS faculties, students, groups, teachers, audiences, disciplines,
	lessons, teachers_disciplines, faculties_teachers, faculties_groups
	CASCADE;
	
DROP SEQUENCE IF EXISTS 
	sequence_faculties_id, sequence_group_id, sequence_student_id, sequence_teacher_id, 
	sequence_audience_id, sequence_discipline_id, sequence_lesson_id 
	CASCADE;

CREATE SEQUENCE sequence_faculties_id;

CREATE TABLE faculties (
	id          BIGSERIAL		PRIMARY KEY,
	name        VARCHAR(40)		NOT NULL
);

CREATE SEQUENCE sequence_group_id;

CREATE TABLE groups (
    id                  BIGSERIAL       PRIMARY KEY,
    name                VARCHAR(25)     UNIQUE NOT NULL,
    year                INT             NOT NULL,
    faculty_id          BIGINT          REFERENCES faculties(id)
);

CREATE SEQUENCE sequence_student_id;

CREATE TABLE students (
    id                      BIGSERIAL       PRIMARY KEY,
    name                    VARCHAR(35)     NOT NULL,
    emailAddress            VARCHAR(30)     UNIQUE NOT NULL,
    course                  INT             NOT NULL,
    group_id                BIGINT          REFERENCES groups(id),
    CONSTRAINT student_pk CHECK (id > 0),
	CHECK (course between 1 and 7)
);

CREATE SEQUENCE sequence_teacher_id;

CREATE TABLE teachers (
    id                  BIGSERIAL       PRIMARY KEY,
    name                VARCHAR(35)     NOT NULL,
    emailAddress        VARCHAR(30)     UNIQUE NOT NULL,
    qualification       VARCHAR(25)     NOT NULL
);

CREATE SEQUENCE sequence_audience_id;

CREATE TABLE audiences (
    id              BIGSERIAL   PRIMARY KEY,
    number          INT         UNIQUE NOT NULL,
    CONSTRAINT number CHECK (number > 0)
);

CREATE SEQUENCE sequence_discipline_id;

CREATE TABLE disciplines(
    id              BIGSERIAL       PRIMARY KEY,
    name            VARCHAR(50)     UNIQUE NOT NULL
);

CREATE SEQUENCE sequence_lesson_id;

CREATE TABLE lessons(
    lesson_id       BIGSERIAL       PRIMARY KEY,
    discipline_id   BIGINT          REFERENCES disciplines(id)ON DELETE CASCADE,
    date_start      TIMESTAMP       NOT NULL,
    date_end        TIMESTAMP       NOT NULL,
    audience_id     BIGINT          REFERENCES audiences(id) ON DELETE CASCADE,
    teacher_id      BIGINT          REFERENCES teachers(id) ON DELETE CASCADE,
    group_id        BIGINT          REFERENCES groups(id) ON DELETE CASCADE
);

CREATE TABLE teachers_disciplines(
	teacher_id      BIGINT		REFERENCES teachers(id) ON DELETE CASCADE,
	discipline_id   BIGINT		REFERENCES disciplines(id) ON DELETE CASCADE,
	CONSTRAINT teacher_id_discipline_id_pk PRIMARY KEY(teacher_id, discipline_id)
);

CREATE TABLE faculties_teachers(
	faculty_id		BIGINT		REFERENCES faculties(id),
	teacher_id		BIGINT		REFERENCES teachers(id) ON DELETE CASCADE
);

CREATE TABLE faculties_groups(
	faculty_id		BIGINT		REFERENCES faculties(id) ON DELETE CASCADE,
	group_id		BIGINT		REFERENCES groups(id) ON DELETE CASCADE 
);

INSERT INTO faculties(name) VALUES 
    ('Факультет Машиностроения'),
    ('Факультет Транспорта'),
    ('Факультет Информационных технологий');
	

INSERT INTO groups(name, year) VALUES
    ('ДКИ-ПОМ18', 2018),
    ('ДКИ-ПОТр18', 2018),
    ('ДКИ-ПОИт17', 2017),
    ('ДЕА-ПОИт16', 2016);

INSERT INTO students(name, emailAddress, course, group_id) VALUES
	('Николай Харченко', 'nk_harc@gmail.com', 1, 1),
    ('Олексей Богданов', 'bogdanov@gmail.com', 1, 1),
    ('Тимофей Васейко', 't_vas@gmail.com', 1, 1),
    ('Оксана Рубаненко', 'oxs_rub@gmail.com', 1, 1),
    ('Татьяна Немашкало', 'tt_nemashko@gmail.com', 1, 1),
    ('Михаил Сторожко', 'mick_storoj@gmail.com', 1, 1),
    ('Артём Яшин', 'artem_yashin@gmail.com', 1, 1),
    ('Дмитрий Файнер', 'dm_fainer@gmail.com', 1, 1),
    ('Елена Хаченкова', 'elenhach@gmail.com', 3, 3),
    ('Николай Лепский', 'nk_leps@gmail.com', 1, 1),
    ('Евгений Сушко', 'sushka@gmail.com', 1, 1),
    ('Михаил Купцов', 'm_kupzov@gmail.com', 2, 2),
    ('Дмитрий Васеленко', 'dimitrius@gmail.com', 2, 2),
    ('Катерина Рубан', 'kateruban@gmail.com', 2, 2),
    ('Алёна Чванина', 'alenacvav@gmail.com', 3, 3),
    ('Богдан Богомол', 'bog_bog@gmail.com', 1, 1),
    ('Андрей Яшинко', 'andru@gmail.com', 1,1),
    ('Дмитрий Фоменко', 'fomenko@gmail.com', 2, 2),
    ('Елена Конотоп', 'kohjtjp@gmail.com', 1, 1),
    ('Семён Коваленко', 'kovalek@gmail.com', 2, 2),
    ('Николай Тризуб', 'niktrizub@gmail.com', 3, 3),
    ('Олексей Шаповал', 'shapoval@gmail.com', 2, 2),
    ('Анатолий Киценко', 'kicko_an@gmail.com', 3, 3),
    ('Оксана Митина', 'mitin_oxs@gmail.com', 1, 1),
    ('Екатерина Шарикало', 'sharikalo_ekat@gmail.com', 1, 1),
    ('Михаил Шевченко', 'mick_sheva@gmail.com', 1, 1),
    ('Артём Дудка', 'arti_dudka@gmail.com', 3, 3),
    ('Дмитрий Бондар', 'lm_bondar@gmail.com', 3, 3),
    ('Елена Божедай', 'bojedai@gmail.com', 3, 3);

INSERT INTO teachers(name, emailAddress, qualification) VALUES
    ('Ковалчук Любовь Васильевна', 'kovalchuklv@gmail.com', 'ст.препод.' ),
    ('Буданов Павел Феофанович', 'budanov_pf@gmail.com', 'доцент к.т.н.'),
    ('Егорова Ольга Юрьевна', 'egorova_ou@gmail.com', 'доц.к.н.'),
    ('Чернюк Артем Михайлович', 'chernuk_am@gmail.com', 'проф.д.н.'),
    ('Литвин Олег Олегович', 'litvin_oo@gmail.com', 'ст.препод.');

INSERT INTO audiences(number) VALUES(100),
    (101),
    (102),
    (103),
    (200),
    (210),
    (256),
    (430);

INSERT INTO disciplines(name) VALUES
    ('Программная инженерия'),
    ('Основы энерго и ресурсосбережения'),
    ('Дидактические основы профессионального образования'),
    ('Теоретическая и прикладная механика'),
    ('Основы инженерно-педагогического творчества');

INSERT INTO lessons(discipline_id, date_start, date_end, audience_id, teacher_id, group_id) VALUES
    (1,'2019-04-04 08:00:00', '2019-04-04 09:20:00', 3, 3, 3),
    (3,'2019-04-04 09:30:00', '2019-04-04 10:50:00', 1, 1, 1),
    (5,'2019-04-04 11:20:00', '2019-04-04 12:50:00', 6, 2, 1);