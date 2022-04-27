CREATE TABLE IF NOT EXISTS `action`
(
    id           UUID PRIMARY KEY AUTO_INCREMENT,
    user_id      INT,
    `data`       VARCHAR (500),
    create_add   DATE
);
