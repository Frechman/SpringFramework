DROP TABLE IF EXISTS genre CASCADE;
DROP TABLE IF EXISTS author CASCADE;
DROP TABLE IF EXISTS book;

CREATE TABLE IF NOT EXISTS genre (
  id          INTEGER PRIMARY KEY,
  name        VARCHAR(64),
  description VARCHAR(64)
  );

CREATE TABLE IF NOT EXISTS author (
  id         INTEGER PRIMARY KEY,
  last_name  VARCHAR(64) NOT NULL,
  first_name VARCHAR(64) NOT NULL
  );

CREATE TABLE IF NOT EXISTS book (
  isbn         VARCHAR(32) PRIMARY KEY,
  title        VARCHAR(64) NOT NULL,
  publish_year INTEGER,
  genre_id     INT         NOT NULL,
  author_id    INT         NOT NULL,
  FOREIGN KEY (genre_id) REFERENCES genre (id),
  FOREIGN KEY (author_id) REFERENCES author (id)
  );