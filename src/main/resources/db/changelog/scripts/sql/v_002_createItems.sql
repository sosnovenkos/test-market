--liquibase formatted sql
--changeset ssosnovenko:v_001 dbms:postgresql
--preconditions onFail:HALT onError:HALT

insert into test_market.items (product_code, name, description, price, "count") values
                                                                                    ('1', 'миндаль', '500 гр.', 500.00, 5),
                                                                                    ('1', 'грецкий орех', '500 гр.', 400.00, 10),
                                                                                    ('3', 'кедровый орех', '200 гр.', 500.00, 2),
                                                                                    ('4', 'бра', '500 гр.', 800.00, 2),
                                                                                    ('5', 'изюм белый', '700 гр.', 250.00, 2),
                                                                                    ('6', 'изюм чёрный', '700 гр.', 250.00, 2),
                                                                                    ('7', 'изюм коричневый', '700 гр.', 200.00, 2),
                                                                                    ('8', 'финики', '500 гр.', 150.00, 2),
                                                                                    ('9', 'курага', '500 гр.', 300.00, 2),
                                                                                    ('10', 'чернослив', '500 гр.', 150.00, 2)