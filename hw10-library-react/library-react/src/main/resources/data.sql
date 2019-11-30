INSERT INTO author (id, last_name, first_name)
VALUES (1, 'Достоевский', 'Федор');

INSERT INTO author (id, last_name, first_name)
VALUES (2, 'Пушкин', 'Александр');

INSERT INTO author (id, last_name, first_name)
VALUES (3, 'Булгаков', 'Михаил');

INSERT INTO genre(id, name, description)
VALUES (1, 'Роман', 'Роман - не человек');

INSERT INTO genre(id, name, description)
VALUES (2, 'Поэзия', 'Поэзия');

INSERT INTO book(id, isbn, title, publish_year, genre_id, author_id)
VALUES (1, '111-111-1', 'Идиот', 1869, 1, 1);

INSERT INTO book(id, isbn, title, publish_year, genre_id, author_id)
VALUES (2, '222-222-2', 'Мастер и Маргарита', 1937, 1, 3);

INSERT INTO book(id, isbn, title, publish_year, genre_id, author_id)
VALUES (3, '333-333-3', 'Евгений Онегин', 1831, 1, 2);

INSERT INTO book(id, isbn, title, publish_year, genre_id, author_id)
VALUES (4, '444-444-4', 'Медный всадник', 1837, 2, 2);

INSERT INTO users(id, first_name, last_name, email, password)
VALUES (1, 'serega', 'dvornik', 'moidvor@gmail.com',
        '$2a$08$I96jWKwM5m4CSfNf5ZZdVe9OCzBw69B52V1O5QO0kuTyzp20XxHYO');

INSERT INTO user_role(user_id, roles)
VALUES (1, 'ROLE_ADMIN');
