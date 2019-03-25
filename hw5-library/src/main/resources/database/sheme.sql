CREATE TABLE IF NOT EXISTS genre (
  id          INTEGER PRIMARY KEY,
  name        VARCHAR(64),
  description VARCHAR(100)
  );

CREATE TABLE IF NOT EXISTS book (
  isbn         VARCHAR(32) PRIMARY KEY,
  title        VARCHAR(64) NOT NULL,
  publish_year INTEGER,
  genre_id     INT         NOT NULL,
  author_id    INT         NOT NULL,
  FOREIGN KEY (genre_id) REFERENCES genre (id)
  );

CREATE TABLE IF NOT EXISTS author (
  id         INTEGER PRIMARY KEY,
  last_name  VARCHAR(64) NOT NULL,
  first_name VARCHAR(64) NOT NULL
  );

CREATE TABLE IF NOT EXISTS book_author (
  id        INTEGER PRIMARY KEY,
  book_isbn INTEGER NOT NULL,
  author_id INTEGER NOT NULL,
  FOREIGN KEY (book_isbn) REFERENCES book_author (book_isbn),
  FOREIGN KEY (author_id) REFERENCES book_author (author_id)
  );
