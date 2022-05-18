--liquibase formatted sql
--changeset ssosnovenko:v_001 dbms:postgresql
--preconditions onFail:HALT onError:HALT

CREATE SCHEMA IF NOT EXISTS test_market;

CREATE TABLE IF NOT EXISTS test_market.action
(
    id              BIGINT PRIMARY KEY NOT NULL GENERATED BY DEFAULT AS IDENTITY,
    user_id         BIGINT,
    user_name       VARCHAR(30),
    created_at      TIMESTAMP,
    data            jsonb
);

CREATE TABLE IF NOT EXISTS test_market.users
(
    id              BIGINT PRIMARY KEY NOT NULL,
    user_id         BIGINT UNIQUE,
    user_status     VARCHAR(10),
    name            VARCHAR(20),
    surname         VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS test_market.items
(
    id              BIGINT PRIMARY KEY NOT NULL,
    product_code    VARCHAR(15),
    name            VARCHAR(100),
    description     VARCHAR(500),
    price           DECIMAL,
    count           INTEGER
);

CREATE TABLE IF NOT EXISTS test_market.orders
(
    id              BIGINT PRIMARY KEY NOT NULL GENERATED BY DEFAULT AS IDENTITY,
    user_id         BIGINT,
    items           jsonb,
    status          TEXT,
    created_at      TIMESTAMP
);

CREATE TABLE IF NOT EXISTS test_market.orders_items
(
    id              BIGINT PRIMARY KEY NOT NULL,
    user_id         BIGINT,
    order_id        BIGINT,
    item_id         BIGINT
)
