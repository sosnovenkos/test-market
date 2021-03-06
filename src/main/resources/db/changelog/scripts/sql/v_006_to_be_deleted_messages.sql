--liquibase formatted sql
--changeset ssosnovenko:v_006 dbms:postgresql
--preconditions onFail:HALT onError:HALT

CREATE TABLE IF NOT EXISTS test_market.to_be_deleted_message
(
    id                  BIGINT PRIMARY KEY NOT NULL GENERATED BY DEFAULT AS IDENTITY,
    user_id             BIGINT,
    chat_id             BIGINT,
    message_id          INT,
    text                TEXT
);