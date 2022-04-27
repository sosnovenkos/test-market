--liquibase formatted sql
--changeset ssosnovenko:v_001 dbms:postgresql
--preconditions onFail:HALT onError:HALT

CREATE SCHEMA IF NOT EXISTS test_market;

CREATE TABLE IF NOT EXISTS test_market.action
(
    id           UUID PRIMARY KEY NOT NULL,
    user_id      INT,
    data         VARCHAR (500),
    create_add   DATE
);
