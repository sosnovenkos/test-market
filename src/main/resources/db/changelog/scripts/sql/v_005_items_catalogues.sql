--liquibase formatted sql
--changeset ssosnovenko:v_005 dbms:postgresql
--preconditions onFail:HALT onError:HALT

delete from test_market.orders;
delete from test_market.items;
ALTER TABLE test_market.items
    ADD COLUMN IF NOT EXISTS parent_id BIGINT;
insert into test_market.items (product_code, name, description, price, "count", parent_id) values
                                                                                    ('1', 'Миндаль', '500 гр.', 500.00, 5, 11),
                                                                                    ('2', 'Грецкий орех', '500 гр.', 400.00, 10, 11),
                                                                                    ('3', 'Кедровый орех', '200 гр.', 500.00, 2, 11),
                                                                                    ('4', 'Бразильский орех', '500 гр.', 800.00, 2, 11),
                                                                                    ('5', 'Изюм белый', '700 гр.', 250.00, 2, 12),
                                                                                    ('6', 'Изюм чёрный', '700 гр.', 250.00, 2, 12),
                                                                                    ('7', 'Изюм корич', '700 гр.', 200.00, 2, 12),
                                                                                    ('8', 'Финики', '500 гр.', 250.00, 2, 12),
                                                                                    ('9', 'Курага', '500 гр.', 300.00, 2, 12),
                                                                                    ('10', 'Чернослив', '500 гр.', 150.00, 2, 12),
                                                                                    ('11', 'ОРЕХИ', null, null, null, null),
                                                                                    ('12', 'СухоФрукты', null, null, null, null),
                                                                                    ('13', 'Цукаты', null, null, null, null),
                                                                                    ('14', '.Назад', null, null, null, 11),
                                                                                    ('15', '.Назад', null, null, null, 12),
                                                                                    ('16', '.Назад', null, null, null, 13)
;
