--liquibase formatted sql
--changeset ssosnovenko:v_001 dbms:postgresql
--preconditions onFail:HALT onError:HALT

ALTER TABLE test_market.orders ADD COLUMN status CHARACTER(15);