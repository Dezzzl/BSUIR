--liquibase formatted sql

-- --changeset dezzzl:1
-- CREATE TYPE SEX AS ENUM ('M', 'W');
--
-- --changeset dezzzl:2
-- CREATE TYPE MARITAL_STATUS AS ENUM('MARRIED', 'SINGLE');
--
-- --changeset dezzzl:3
-- CREATE TYPE EMPLOYEE_TYPE AS ENUM ('VETERINARIAN', 'ZOOKEEPER');

--changeset dezzzl:4
CREATE TABLE habitat_zone(
     id SERIAL PRIMARY KEY ,
     name VARCHAR(32) NOT NULL ,
     description TEXT NOT NULL
);

--changeset dezzzl:5
CREATE TABLE wintering_place(
    id SERIAL PRIMARY KEY,
    code CHAR(3) NOT NULL ,
    country VARCHAR(32) NOT NULL ,
    arrival_date DATE NOT NULL,
    departure_date DATE NOT NULL
);

--changeset dezzzl:6
CREATE TABLE feed_type(
  id SERIAL PRIMARY KEY,
  name VARCHAR(32) NOT NULL
);

--changeset dezzzl:7
CREATE TABLE feeding_ration(
   id SERIAL PRIMARY KEY ,
   name VARCHAR(32) NOT NULL,
   feed_type_id INTEGER REFERENCES feed_type(id)
);

--changeset dezzzl:8
CREATE TABLE employee(
     id SERIAL PRIMARY KEY ,
     name VARCHAR(32) NOT NULL ,
     birthdate DATE NOT NULL ,
     phone_number VARCHAR(20) NOT NULL ,
     employee_type VARCHAR(32) NOT NULL,
     marital_status VARCHAR(32) NOT NULL
);


--changeset dezzzl:9
CREATE TABLE spouses(
    id SERIAL PRIMARY KEY ,
    first_spouse_id INTEGER  REFERENCES employee(id) ON DELETE CASCADE,
    second_spouse_id INTEGER  REFERENCES employee(id) ON DELETE CASCADE,
    UNIQUE (first_spouse_id, second_spouse_id)
);

--changeset dezzzl:10
CREATE TABLE pet(
    id SERIAL PRIMARY KEY,
    name VARCHAR(32) NOT NULL ,
    birthdate DATE NOT NULL ,
    sex VARCHAR(4) NOT NULL,
    feeding_ration_id INTEGER REFERENCES feeding_ration(id) NOT NULL ,
    habitat_zone_id INTEGER REFERENCES habitat_zone(id) NOT NULL
);

--changeset dezzzl:11
CREATE TABLE bird(
    id INTEGER PRIMARY KEY REFERENCES pet(id),
    wintering_place_id INTEGER REFERENCES wintering_place(id)
);

--changeset dezzzl:12
CREATE TABLE reptile(
    id INTEGER PRIMARY KEY REFERENCES pet(id),
    normal_temperature NUMERIC(8, 2) NOT NULL ,
    sleep_period INTERVAL NOT NULL
);

--changeset dezzzl:14
CREATE TABLE employee_pet(
     id SERIAL PRIMARY KEY ,
     employee_id INTEGER REFERENCES employee(id),
     pet_id INTEGER REFERENCES pet(id),
     UNIQUE (employee_id, pet_id)
);



