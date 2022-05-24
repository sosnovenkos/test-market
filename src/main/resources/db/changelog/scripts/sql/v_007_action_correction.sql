--liquibase formatted sql
--changeset ssosnovenko:v_006 dbms:postgresql
--preconditions onFail:HALT onError:HALT

ALTER TABLE test_market.action
    ADD COLUMN waiting_for_action BOOLEAN DEFAULT FALSE;
ALTER TABLE test_market.action
    ALTER COLUMN data TYPE TEXT;
ALTER TABLE test_market.orders
    ADD COLUMN address_id BIGINT;
