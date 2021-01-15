create table authors
(
    id          int auto_increment
        primary key,
    first_name  varchar(40)  not null,
    last_name   varchar(40)  not null,
    photo_link  varchar(100) null,
    description text         null
);

create index id_index
    on authors (id);

INSERT INTO bookshop.authors (id, first_name, last_name, photo_link, description) VALUES (1, ' ', 'The Church', null, null);
INSERT INTO bookshop.authors (id, first_name, last_name, photo_link, description) VALUES (2, 'George', 'Martin', 'https://drive.google.com/uc?export=view&id=10c9CU4e_FWvGbc4RMvwSCH7HMqPPbgTX', 'George Raymond Richard Martin(born George Raymond Martin; September 20, 1948), also known as GRRM, is an American novelist and short story writer, screenwriter, and television producer. He is the author of the series of epic fantasy novels A Song of Ice and Fire, which was adapted into the Emmy Award-winning HBO series Game of Thrones (2011–2019).

In 2005, Lev Grossman of Time called Martin "the American Tolkien", and in 2011, he was included on the annual Time 100 list of the most influential people in the world.');
INSERT INTO bookshop.authors (id, first_name, last_name, photo_link, description) VALUES (3, 'George', 'Orwell', 'https://drive.google.com/uc?export=view&id=1MkbsHovRhQFoYSE_0DLWSkJW-SIYbSSD', null);
INSERT INTO bookshop.authors (id, first_name, last_name, photo_link, description) VALUES (4, 'Roger ', 'Zelazny', 'https://drive.google.com/uc?export=view&id=1W7yH9YKjrvxdb5ILvo3u57T2NIBw23_t', null);
INSERT INTO bookshop.authors (id, first_name, last_name, photo_link, description) VALUES (5, 'Лев', 'Толстой', 'https://drive.google.com/uc?export=view&id=1j-sx_Uq_Cc1YEUcQGxc_m2ucZ-PsrPxq', null);
INSERT INTO bookshop.authors (id, first_name, last_name, photo_link, description) VALUES (6, 'John', 'Tolkien', 'https://drive.google.com/uc?export=view&id=197fOoctZCdA3NhoRpycjoM2tTu4vwyFV', null);
INSERT INTO bookshop.authors (id, first_name, last_name, photo_link, description) VALUES (7, 'Федор', 'Достоевский', 'https://drive.google.com/uc?export=view&id=1ffGYUh8B3gFX-0vgrnjg2fyunoZ9cZiM', null);
INSERT INTO bookshop.authors (id, first_name, last_name, photo_link, description) VALUES (8, 'Александр', 'Пушкин', 'https://drive.google.com/uc?export=view&id=1b6-V7ZBHNWINqrHKMLGGcetWPVXooEoD', null);
INSERT INTO bookshop.authors (id, first_name, last_name, photo_link, description) VALUES (9, 'Terry', 'Pratchett', 'https://drive.google.com/uc?export=view&id=1ZzlzMEfk1r0G1Tm2fgMrCrhqPepD13TK', null);
INSERT INTO bookshop.authors (id, first_name, last_name, photo_link, description) VALUES (10, 'Янка', 'Купала', 'https://drive.google.com/uc?export=view&id=1XlhV2kImiFwHPnnxDtduYFaNREuTueIk', null);
INSERT INTO bookshop.authors (id, first_name, last_name, photo_link, description) VALUES (11, 'Сергей', 'Довлатов', 'https://drive.google.com/uc?export=view&id=19F_Al4LukFLFjd-rY04_wpW0LnFpQjJl', null);
INSERT INTO bookshop.authors (id, first_name, last_name, photo_link, description) VALUES (12, 'Віктар', ' Марціновіч', 'https://drive.google.com/uc?export=view&id=1YvwCqMAAuPt_DW-ZdbE7qljAq4PWjL5y', null);
INSERT INTO bookshop.authors (id, first_name, last_name, photo_link, description) VALUES (13, 'Александр', 'Солженицын', 'https://drive.google.com/uc?export=view&id=1wQQSj6brxAB8xjbimKwlUyKf3yPpE6BA', null);
INSERT INTO bookshop.authors (id, first_name, last_name, photo_link, description) VALUES (14, 'Gregory David', 'Roberts', 'https://drive.google.com/uc?export=view&id=1FMeaPu8dAwqBBiOhXj5mzm755ykS7-ii', null);