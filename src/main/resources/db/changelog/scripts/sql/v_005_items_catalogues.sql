--liquibase formatted sql
--changeset ssosnovenko:v_005 dbms:postgresql
--preconditions onFail:HALT onError:HALT

delete from test_market.orders;
ALTER TABLE test_market.orders
    ADD COLUMN amount BIGINT,
    ADD COLUMN weight BIGINT;
delete from test_market.items;
ALTER TABLE test_market.items
    ADD COLUMN parent_id BIGINT,
    ADD COLUMN weight BIGINT;
insert into test_market.items (product_code, name, description, weight, price, "count", parent_id) values
                                                                                    ('1', 'Миндаль', null, 500, 500.00, 5, 11),
                                                                                    ('2', 'Грецкий орех', null, 500, 400.00, 10, 11),
                                                                                    ('3', 'Кедровый орех', null, 200, 500.00, 2, 11),
                                                                                    ('4', 'Бразильский орех', null, 500, 800.00, 2, 11),
                                                                                    ('5', 'Изюм белый', null, 700, 250.00, 2, 12),
                                                                                    ('6', 'Изюм чёрный', null, 700, 250.00, 2, 12),
                                                                                    ('7', 'Изюм корич', null, 700, 200.00, 2, 12),
                                                                                    ('8', 'Финики', null, 500, 250.00, 2, 12),
                                                                                    ('9', 'Курага', null, 500, 300.00, 2, 12),
                                                                                    ('10', 'Чернослив', null, 500, 150.00, 2, 12),
                                                                                    ('11', 'ОРЕХИ', null, null, null, null, null),
                                                                                    ('12', 'СухоФрукты', null, null, null, null, null),
                                                                                    ('13', 'Цукаты', null, null, null, null, null),
                                                                                    ('14', '.Назад', null, null, null, null, 11),
                                                                                    ('15', '.Назад', null, null, null, null, 12),
                                                                                    ('16', '.Назад', null, null, null, null, 13)
;
