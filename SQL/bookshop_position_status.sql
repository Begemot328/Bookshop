create table position_status
(
    id           tinyint     not null
        primary key,
    name         varchar(40) not null,
    picture_link varchar(40) null
);

INSERT INTO bookshop.position_status (id, name, picture_link) VALUES (0, 'non-existent', null);
INSERT INTO bookshop.position_status (id, name, picture_link) VALUES (1, 'ready', null);
INSERT INTO bookshop.position_status (id, name, picture_link) VALUES (2, 'sold', null);
INSERT INTO bookshop.position_status (id, name, picture_link) VALUES (3, 'reserved', null);
INSERT INTO bookshop.position_status (id, name, picture_link) VALUES (4, 'transfer', null);