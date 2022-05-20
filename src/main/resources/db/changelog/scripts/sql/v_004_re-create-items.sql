--liquibase formatted sql
--changeset ssosnovenko:v_001 dbms:postgresql
--preconditions onFail:HALT onError:HALT

delete from test_market.orders;
delete from test_market.items;
insert into test_market.items (product_code, name, description, price, "count") values
                                                                                    ('1', 'Миндаль', '500 гр.', 500.00, 5),
                                                                                    ('2', 'Грецкий орех', '500 гр.', 400.00, 10),
                                                                                    ('3', 'Кедровый орех', '200 гр.', 500.00, 2),
                                                                                    ('4', 'Бразильский орех', '500 гр.', 800.00, 2),
                                                                                    ('5', 'Изюм белый', '700 гр.', 250.00, 2),
                                                                                    ('6', 'Изюм чёрный', '700 гр.', 250.00, 2),
                                                                                    ('7', 'Изюм корич', '700 гр.', 200.00, 2),
                                                                                    ('8', 'Финики', '500 гр.', 250.00, 2),
                                                                                    ('9', 'Курага', '500 гр.', 300.00, 2),
                                                                                    ('10', 'Чернослив', '500 гр.', 150.00, 2)