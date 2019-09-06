INSERT INTO author (id, last_name, first_name)
VALUES (1, 'Достоевский', 'Федор');

INSERT INTO author (id, last_name, first_name)
VALUES (2, 'Пушкин', 'Александр');

INSERT INTO genre(id, name, description)
VALUES (1, 'Роман', 'Роман - не человек');

INSERT INTO genre(id, name, description)
VALUES (2, 'Поэзия', 'Поэзия');

INSERT INTO book(id, isbn, title, publish_year, genre_id, author_id)
VALUES (0, '999-888', 'Идиот', 1869, 1, 1);
INSERT INTO book(id, isbn, title, publish_year, genre_id, author_id)
VALUES (1, '999-8881', 'Идиот', 1869, 1, 1);
INSERT INTO book(id, isbn, title, publish_year, genre_id, author_id)
VALUES (2, '999-8882', 'Идиот', 1869, 1, 1);
INSERT INTO book(id, isbn, title, publish_year, genre_id, author_id)
VALUES (3, '999-8883', 'Идиот', 1869, 1, 1);