--liquibase formatted sql

--changeset dezzzl:1
ALTER TABLE spouses
    ADD CONSTRAINT unique_first_spouse UNIQUE (first_spouse_id);
ALTER TABLE spouses
    ADD CONSTRAINT unique_second_spouse UNIQUE (second_spouse_id);







