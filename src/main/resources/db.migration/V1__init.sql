CREATE TABLE users
(
    id            SERIAL PRIMARY KEY,
    name     VARCHAR(128) NOT NULL
);

CREATE TABLE file
(
    id           SERIAL PRIMARY KEY,
    name         VARCHAR(128) NOT NULL,
    file_path         VARCHAR(128) NOT NULL
);

CREATE TABLE event
(
    id          SERIAL PRIMARY KEY,
    user_id   BIGINT REFERENCES users (id),
    file_id   BIGINT REFERENCES file (id)

);

CREATE TABLE users_event
(
    user_id INT REFERENCES users (id),
    event_id INT REFERENCES event (id),
    PRIMARY KEY (user_id, event_id)
);