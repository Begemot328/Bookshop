create table users
(
    id         int auto_increment
        primary key,
    first_name varchar(40)  not null,
    last_name  varchar(40)  not null,
    login      varchar(10)  not null,
    password   int          not null,
    address    varchar(100) not null,
    photo_link varchar(100) null,
    status     tinyint      null
);

create index id_index
    on users (id);

create index lastname_index
    on users (last_name);

INSERT INTO bookshop.users (id, first_name, last_name, login, password, address, photo_link, status) VALUES (1, 'Yury', 'Zmushko', 'root', 3506402, 'vulica Hrušaŭskaja 73, Minsk 220089, Belarus', null, 4);
INSERT INTO bookshop.users (id, first_name, last_name, login, password, address, photo_link, status) VALUES (2, 'Ivan', 'Ivanoy', 'IIvanoy', -946852072, 'praspiekt Niezaliežnasci 65, Minsk, Belarus', null, 3);
INSERT INTO bookshop.users (id, first_name, last_name, login, password, address, photo_link, status) VALUES (3, 'Petr', 'Petroy', 'PPetroy', -946852072, 'Ulitsa Novovilenskaya 11, Minsk, Belarus', null, 3);
INSERT INTO bookshop.users (id, first_name, last_name, login, password, address, photo_link, status) VALUES (4, 'Sidor ', 'Sidorov', 'SSidorov', -946852072, 'vulica Janki Lučyny 11, Minsk, Belarus', null, 2);
INSERT INTO bookshop.users (id, first_name, last_name, login, password, address, photo_link, status) VALUES (5, 'Morozov ', 'Igor', 'IMorozov', -946852072, 'praspiekt Haziety Praŭda 25, Minsk 220089, Belarus', null, 2);