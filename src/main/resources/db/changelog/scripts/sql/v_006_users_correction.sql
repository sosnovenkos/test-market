--liquibase formatted sql
--changeset ssosnovenko:v_006 dbms:postgresql
--preconditions onFail:HALT onError:HALT

CREATE TABLE IF NOT EXISTS test_market.address
(
    id                  BIGINT PRIMARY KEY NOT NULL,
    user_id             BIGINT UNIQUE,
    phone               TEXT,
    description         TEXT
);
