--liquibase formatted sql
--changeset ssosnovenko:v_001 dbms:postgresql
--preconditions onFail:HALT onError:HALT

insert into test_market.items (id, product_code, name, description, price, "count") values
                                                                                    ('55a53324-48ce-457d-8d10-89fe8cccce94','2548844', '1', '111111111', 112.2, 5),
                                                                                    ('33671e8a-4d11-4857-a177-6590a285e72f','145695', '2', '2222222221111', 52.4, 10),
                                                                                    ('a457d2b3-c4c0-4000-bdca-8027def52a8f','65855', '3', '333333333333333', 58.36, 2);

insert into test_market.orders (id) values ('3228f2aa-f2da-48ee-965c-efb1a5972f44');