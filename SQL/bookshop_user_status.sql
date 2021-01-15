create table user_status
(
    id   tinyint     not null
        primary key,
    name varchar(40) not null
);

INSERT INTO bookshop.user_status (id, name) VALUES (0, 'non-existent');
INSERT INTO bookshop.user_status (id, name) VALUES (1, 'disabled');
INSERT INTO bookshop.user_status (id, name) VALUES (2, 'buyer');
INSERT INTO bookshop.user_status (id, name) VALUES (3, 'seller');
INSERT INTO bookshop.user_status (id, name) VALUES (4, 'admin');
INSERT INTO bookshop.user_status (id, name) VALUES (5, 'courier');