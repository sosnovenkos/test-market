--liquibase formatted sql
--changeset ssosnovenko:v_001 dbms:postgresql
--preconditions onFail:HALT onError:HALT

insert into test_market.items (id, product_code, name, description, price, "count") values
                                                                                    (1,'2548844', '1', '111111111', 112.2, 5),
                                                                                    (2,'145695', '2', '2222222221111', 52.4, 10),
                                                                                    (3,'65855', '3', '333333333333333', 58.36, 2);
