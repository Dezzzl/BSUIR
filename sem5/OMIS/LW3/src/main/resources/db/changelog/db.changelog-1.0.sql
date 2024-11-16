--liquibase formatted sql

--changeset dezzzl:1
CREATE TABLE Users
(
    id            SERIAL PRIMARY KEY,
    avatar_url    VARCHAR(255),
    date_of_birth DATE,
    name          VARCHAR(255)        NOT NULL,
    email         VARCHAR(255) UNIQUE NOT NULL,
    password      VARCHAR(255)
);

--changeset dezzzl:3
CREATE TABLE Review_Body
(
    id          SERIAL PRIMARY KEY,
    body_type   VARCHAR(255) NOT NULL,
    text        TEXT,
    CONSTRAINT body_type_check CHECK (body_type IN ('TEXT'))
);

--changeset dezzzl:13
CREATE TABLE Text_Review_Body
(
    id          SERIAL PRIMARY KEY,
    text        TEXT
);

--changeset dezzzl:2
CREATE TABLE Review
(
    id        SERIAL PRIMARY KEY,
    title     VARCHAR(255) NOT NULL,
    body_id   INT,
    author_id INT REFERENCES Users (id) ON DELETE SET NULL,
    rating    INT,
    CONSTRAINT fk_body FOREIGN KEY (body_id) REFERENCES Review_Body (id)
);

--changeset dezzzl:4
CREATE TABLE user_roles
(
    user_id INT REFERENCES Users (id) ON DELETE CASCADE,
    role    VARCHAR(50) CHECK (role IN ('USER', 'ADMINISTRATOR', 'MANAGER')),
    PRIMARY KEY (user_id, role)
);

--changeset dezzzl:5
CREATE TABLE Event
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    date_time   TIMESTAMP,
    rating      REAL,
    location_id INT
);
--changeset dezzzl:6
CREATE TABLE event_review_ids
(
    event_id  INT REFERENCES Event (id) ON DELETE CASCADE,
    review_id INT REFERENCES Review (id) ON DELETE CASCADE
);

--changeset dezzzl:7
CREATE TABLE Location
(
    id           SERIAL PRIMARY KEY,
    name         VARCHAR(255) NOT NULL,
    geo_location VARCHAR(255)
);

--changeset dezzzl:8
CREATE TABLE location_photo_urls
(
    location_id INT REFERENCES Location (id) ON DELETE CASCADE,
    photo_url   VARCHAR(255) NOT NULL
);

--changeset dezzzl:9
CREATE TABLE location_administrator_emails
(
    location_id         INT REFERENCES Location (id) ON DELETE CASCADE,
    administrator_email VARCHAR(255) NOT NULL
);

--changeset dezzzl:10
CREATE TABLE location_review_ids
(
    location_id INT REFERENCES Location (id) ON DELETE CASCADE,
    review_id   INT REFERENCES Review (id) ON DELETE CASCADE
);

--changeset dezzzl:11
ALTER TABLE Location
    ADD COLUMN nearest_event_id INT,
    ADD CONSTRAINT fk_location_nearest_event
        FOREIGN KEY (nearest_event_id) REFERENCES Event (id) ON DELETE SET NULL;

--changeset dezzzl:12
ALTER TABLE Event
    ADD CONSTRAINT fk_event_location
        FOREIGN KEY (location_id) REFERENCES Location (id) ON DELETE SET NULL;








