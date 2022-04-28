--liquibase formatted sql
--changeset ssosnovenko:v_001 dbms:postgresql
--preconditions onFail:HALT onError:HALT

CREATE SCHEMA IF NOT EXISTS test_market;

CREATE TABLE IF NOT EXISTS test_market.action
(
    id              UUID PRIMARY KEY NOT NULL,
    user_id         BIGINT,
    user_name       VARCHAR(30),
    created_at      TIMESTAMP,
    data            jsonb
);

CREATE TABLE IF NOT EXISTS test_market.users
(
    id              UUID PRIMARY KEY NOT NULL,
    user_id         BIGINT UNIQUE,
    user_status     VARCHAR(10),
    name            VARCHAR(20),
    surname         VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS test_market.items
(
    id              UUID PRIMARY KEY NOT NULL,
    product_code    VARCHAR(15),
    name            VARCHAR(100),
    description     VARCHAR(500),
    price           DECIMAL,
    count           INTEGER
);

CREATE TABLE IF NOT EXISTS test_market.orders
(
    id              UUID PRIMARY KEY NOT NULL,
    user_id         BIGINT,
    items           jsonb,
    created_at      TIMESTAMP,
    CONSTRAINT fk_orders_user_id FOREIGN KEY (user_id)
        REFERENCES test_market.users (user_id)
--     CONSTRAINT fk_orders_items FOREIGN KEY (items)
--         REFERENCES test_market.items MATCH FULL (product_code)
);

CREATE TABLE IF NOT EXISTS test_market.orders_items
(
    id              UUID PRIMARY KEY NOT NULL,
    user_id         BIGINT,
    order_id        UUID,
    item_id         UUID,
    CONSTRAINT fk_orders_items_user_id FOREIGN KEY (user_id)
        REFERENCES test_market.users (user_id),
    CONSTRAINT fk_orders_items_order_id FOREIGN KEY (order_id)
        REFERENCES test_market.orders (id),
    CONSTRAINT fk_orders_items_item_id FOREIGN KEY (item_id)
        REFERENCES test_market.items (id)
)
