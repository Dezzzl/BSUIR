--liquibase formatted sql

--changeset dezzzl:1
CREATE TABLE habitat_zone
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(32) NOT NULL,
    description TEXT        NOT NULL
);


CREATE TABLE wintering_place
(
    id             SERIAL PRIMARY KEY,
    code           CHAR(3)     NOT NULL,
    country        VARCHAR(32) NOT NULL,
    arrival_date   DATE        NOT NULL,
    departure_date DATE        NOT NULL
);

CREATE TABLE feed_type
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(32) NOT NULL
);

CREATE TABLE feeding_ration
(
    id           SERIAL PRIMARY KEY,
    name         VARCHAR(32) NOT NULL,
    feed_type_id INTEGER     NOT NULL
);

CREATE TABLE employee
(
    id             SERIAL PRIMARY KEY,
    name           VARCHAR(32) NOT NULL,
    birthdate      DATE        NOT NULL,
    phone_number   VARCHAR(20) NOT NULL,
    employee_type  VARCHAR(32) NOT NULL,
    marital_status VARCHAR(2)  NOT NULL
);


CREATE TABLE spouses
(
    first_spouse_id  INTEGER NOT NULL,
    second_spouse_id INTEGER NOT NULL
);

CREATE TABLE pet
(
    id                SERIAL PRIMARY KEY,
    name              VARCHAR(32) NOT NULL,
    birthdate         DATE        NOT NULL,
    sex               VARCHAR(4)  NOT NULL,
    feeding_ration_id INTEGER,
    habitat_zone_id   INTEGER
);

CREATE TABLE bird
(
    id                 INTEGER PRIMARY KEY,
    wintering_place_id INTEGER NOT NULL
);

CREATE TABLE reptile
(
    id                 INTEGER PRIMARY KEY,
    normal_temperature NUMERIC(8, 2) NOT NULL,
    sleep_period       VARCHAR(128)  NOT NULL
);

CREATE TABLE employee_pet
(
    employee_id INTEGER NOT NULL,
    pet_id      INTEGER NOT NULL
);

CREATE INDEX idx_pet_name ON pet (name);

ALTER TABLE spouses
ADD CONSTRAINT unique_spouses_first_spouse_second_spouse UNIQUE (first_spouse_id, second_spouse_id);

ALTER TABLE employee_pet
ADD CONSTRAINT unique_emplpet_employee_pet UNIQUE (employee_id, pet_id);

ALTER TABLE feeding_ration
ADD CONSTRAINT fk_feeding_ration_feed_type FOREIGN KEY (feed_type_id) REFERENCES feed_type (id) ON DELETE CASCADE;

ALTER TABLE spouses
ADD CONSTRAINT fk_spouses_first_spouse FOREIGN KEY (first_spouse_id) REFERENCES employee (id) ON DELETE CASCADE,
ADD CONSTRAINT fk_spouses_second_spouse FOREIGN KEY (second_spouse_id) REFERENCES employee(id) ON DELETE CASCADE;

ALTER TABLE pet
ADD CONSTRAINT fk_pet_feeding_ration FOREIGN KEY (feeding_ration_id) REFERENCES feeding_ration (id) ON DELETE CASCADE,
ADD CONSTRAINT fk_pet_habitat_zone FOREIGN KEY (habitat_zone_id) REFERENCES habitat_zone(id) ON DELETE CASCADE;

ALTER TABLE bird
ADD CONSTRAINT fk_bird_id FOREIGN KEY (id) REFERENCES pet (id) ON DELETE CASCADE,
ADD CONSTRAINT fk_bird_wintering_place FOREIGN KEY (wintering_place_id) REFERENCES wintering_place(id) ON DELETE CASCADE;

ALTER TABLE reptile
ADD CONSTRAINT fk_reptile_id FOREIGN KEY (id) REFERENCES pet (id) ON DELETE CASCADE;

ALTER TABLE employee_pet
ADD CONSTRAINT fk_emppet_employee FOREIGN KEY (employee_id) REFERENCES employee (id) ON DELETE CASCADE,
ADD CONSTRAINT fk_emppet_pet FOREIGN KEY (pet_id) REFERENCES pet(id) ON DELETE CASCADE;



CREATE OR REPLACE FUNCTION are_both_single(first_employee_id INT, second_employee_id INT)
    RETURNS BOOLEAN AS '
DECLARE
    first_marital_status VARCHAR(2);
    second_marital_status VARCHAR(2);
BEGIN
    -- Получаем статус брака для первого работника
SELECT marital_status INTO first_marital_status
FROM employee
WHERE id = first_employee_id;

-- Получаем статус брака для второго работника
SELECT marital_status INTO second_marital_status
FROM employee
WHERE id = second_employee_id;

-- Проверяем, что оба работника имеют статус SINGLE
IF first_marital_status = ''S'' AND second_marital_status = ''S'' THEN
        RETURN true;
ELSE
        RETURN false;
END IF;
END;
' LANGUAGE plpgsql;


CREATE OR REPLACE PROCEDURE add_spouse(first_spouse_id INT, second_spouse_id INT)
LANGUAGE plpgsql AS '
BEGIN
    RAISE NOTICE ''Attempting to add spouse: %, %'', first_spouse_id, second_spouse_id;

    IF are_both_single(first_spouse_id, second_spouse_id) THEN
        RAISE NOTICE ''Both employees are single. Proceeding with the insert.'';
        INSERT INTO spouses (first_spouse_id, second_spouse_id)
        VALUES (first_spouse_id, second_spouse_id);

    ELSE
        RAISE EXCEPTION ''Один или оба работника не имеют статус SINGLE'';
    END IF;
END;
';


CREATE OR REPLACE FUNCTION update_marital_status()
RETURNS TRIGGER AS '
BEGIN
    RAISE NOTICE ''Trigger fired: %'', TG_OP;

    IF (TG_OP = ''INSERT'') THEN
        RAISE NOTICE ''Insert operation detected.'';
        UPDATE employee
        SET marital_status = ''M''
        WHERE id = NEW.first_spouse_id OR id = NEW.second_spouse_id;

    ELSIF (TG_OP = ''DELETE'') THEN
        RAISE NOTICE ''Delete operation detected.'';
        UPDATE employee
        SET marital_status = ''S''
        WHERE id = OLD.first_spouse_id OR id = OLD.second_spouse_id;

    ELSIF (TG_OP = ''UPDATE'') THEN
        RAISE NOTICE ''Update operation detected.'';
        UPDATE employee
        SET marital_status = ''S''
        WHERE id = OLD.first_spouse_id OR id = OLD.second_spouse_id;

        UPDATE employee
        SET marital_status = ''M''
        WHERE id = NEW.first_spouse_id OR id = NEW.second_spouse_id;
    END IF;

    RETURN NULL;
END;
' LANGUAGE plpgsql;


-- Создаем триггер для таблицы spouses
CREATE TRIGGER marital_status_trigger
    AFTER INSERT OR UPDATE OR DELETE ON spouses
    FOR EACH ROW
    EXECUTE FUNCTION update_marital_status();






