create table shops
(
    id         int auto_increment
        primary key,
    name       varchar(40)  not null,
    address    varchar(100) not null,
    photo_link varchar(100) null
);

create index id_index
    on shops (id);

INSERT INTO bookshop.shops (id, name, address, photo_link) VALUES (1, 'Na Grushevke', 'Prospekt Dzerzhinskogo 19, Minsk 220089, Belarus', '');
INSERT INTO bookshop.shops (id, name, address, photo_link) VALUES (2, 'Na Malinovke', 'Praspiekt Dziaržynskaha 130, Minsk, Belarus', null);