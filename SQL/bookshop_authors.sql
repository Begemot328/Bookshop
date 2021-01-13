create table authors
(
    id         int auto_increment
        primary key,
    first_name varchar(40)  not null,
    last_name  varchar(40)  not null,
    photo_link varchar(100) null
);

create index id_index
    on authors (id);

INSERT INTO bookshop.authors (id, first_name, last_name, photo_link) VALUES (1, ' ', 'The Church', null);
INSERT INTO bookshop.authors (id, first_name, last_name, photo_link) VALUES (2, 'George', 'Martin', 'https://drive.google.com/uc?export=view&id=10c9CU4e_FWvGbc4RMvwSCH7HMqPPbgTX');
INSERT INTO bookshop.authors (id, first_name, last_name, photo_link) VALUES (3, 'George', 'Orwell', 'https://drive.google.com/uc?export=view&id=1MkbsHovRhQFoYSE_0DLWSkJW-SIYbSSD');
INSERT INTO bookshop.authors (id, first_name, last_name, photo_link) VALUES (4, 'Roger ', 'Zelazny', 'https://drive.google.com/uc?export=view&id=1W7yH9YKjrvxdb5ILvo3u57T2NIBw23_t');
INSERT INTO bookshop.authors (id, first_name, last_name, photo_link) VALUES (5, 'Лев', 'Толстой', 'https://drive.google.com/uc?export=view&id=1j-sx_Uq_Cc1YEUcQGxc_m2ucZ-PsrPxq');
INSERT INTO bookshop.authors (id, first_name, last_name, photo_link) VALUES (6, 'John', 'Tolkien', 'https://drive.google.com/uc?export=view&id=197fOoctZCdA3NhoRpycjoM2tTu4vwyFV');
INSERT INTO bookshop.authors (id, first_name, last_name, photo_link) VALUES (7, 'Федор', 'Достоевский', 'https://drive.google.com/uc?export=view&id=1ffGYUh8B3gFX-0vgrnjg2fyunoZ9cZiM');
INSERT INTO bookshop.authors (id, first_name, last_name, photo_link) VALUES (8, 'Александр', 'Пушкин', 'https://drive.google.com/uc?export=view&id=1b6-V7ZBHNWINqrHKMLGGcetWPVXooEoD');
INSERT INTO bookshop.authors (id, first_name, last_name, photo_link) VALUES (9, 'Terry', 'Pratchett', 'https://drive.google.com/uc?export=view&id=1ZzlzMEfk1r0G1Tm2fgMrCrhqPepD13TK');
INSERT INTO bookshop.authors (id, first_name, last_name, photo_link) VALUES (10, 'Янка', 'Купала', 'https://drive.google.com/uc?export=view&id=1XlhV2kImiFwHPnnxDtduYFaNREuTueIk');
INSERT INTO bookshop.authors (id, first_name, last_name, photo_link) VALUES (11, 'Сергей', 'Довлатов', 'https://drive.google.com/uc?export=view&id=19F_Al4LukFLFjd-rY04_wpW0LnFpQjJl');
INSERT INTO bookshop.authors (id, first_name, last_name, photo_link) VALUES (12, 'Віктар', ' Марціновіч', 'https://drive.google.com/uc?export=view&id=1YvwCqMAAuPt_DW-ZdbE7qljAq4PWjL5y');
INSERT INTO bookshop.authors (id, first_name, last_name, photo_link) VALUES (13, 'Александр', 'Солженицын', 'https://drive.google.com/uc?export=view&id=1wQQSj6brxAB8xjbimKwlUyKf3yPpE6BA');
INSERT INTO bookshop.authors (id, first_name, last_name, photo_link) VALUES (14, 'Gregory David', 'Roberts', 'https://drive.google.com/uc?export=view&id=1FMeaPu8dAwqBBiOhXj5mzm755ykS7-ii');