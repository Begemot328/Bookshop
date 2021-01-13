create table books
(
    id          int auto_increment
        primary key,
    title       varchar(40)  not null,
    author_id   int          not null,
    price       float        null,
    description text         null,
    photo_link  varchar(100) null,
    constraint books_ibfk_1
        foreign key (author_id) references authors (id)
            on update cascade
);

create index author_index
    on books (author_id);

create index id_index
    on books (id);

create index price_index
    on books (price);

create index title_index
    on books (title);

INSERT INTO bookshop.books (id, title, author_id, price, description, photo_link) VALUES (1, 'Holy bible', 1, 26, null, null);
INSERT INTO bookshop.books (id, title, author_id, price, description, photo_link) VALUES (2, 'Game of thrones', 2, 54, null, null);
INSERT INTO bookshop.books (id, title, author_id, price, description, photo_link) VALUES (3, 'Clash of kings', 2, 35, null, 'https://drive.google.com/uc?export=view&id=10KL--ZudHfEul_uWVBTdm-4GHVsfZzxc');
INSERT INTO bookshop.books (id, title, author_id, price, description, photo_link) VALUES (4, '1984', 3, 35, '', 'https://drive.google.com/uc?export=view&id=17JXG686-AM6MKmQvIv1SeWbsYjz5-FIg');
INSERT INTO bookshop.books (id, title, author_id, price, description, photo_link) VALUES (5, 'Игра Престолов', 2, 27, '', 'https://drive.google.com/uc?export=view&id=1J-UMvbXs5VHX9UmjMntq-b4qJeRp8Ufl');
INSERT INTO bookshop.books (id, title, author_id, price, description, photo_link) VALUES (6, 'Shantaram', 14, 27.5, 'Shantaram is a 2003 novel by Gregory David Roberts, in which a convicted Australian bank robber and heroin addict escapes from Pentridge Prison and flees to India. The novel is commended by many for its vivid portrayal of tumultuous life in Bombay.  The novel is reportedly influenced by real events in the life of the author, though some claims made by Roberts are contested by others involved in the story.', 'https://drive.google.com/uc?export=view&id=1hA2eua5COf2Ec9EM5iZQjfWz67gnHsrs');
INSERT INTO bookshop.books (id, title, author_id, price, description, photo_link) VALUES (7, 'Animal''s Farm', 3, 14, '', 'https://drive.google.com/uc?export=view&id=1OWLlM_hM9jhWYHObX6YzP8eFfxylhsy6');