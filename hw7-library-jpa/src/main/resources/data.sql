INSERT INTO author (id, last_name, first_name)
VALUES (1, 'Достоевский', 'Федор');

INSERT INTO genre(id, name, description)
VALUES (1, 'Роман', 'Роман - не человек');

INSERT INTO genre(id, name, description)
VALUES (2, 'Поэзия', 'Поэзия');

INSERT INTO book(isbn, title, publish_year, genre_id, author_id)
VALUES ('999-888', 'Идиот', '1869', 1, 1);