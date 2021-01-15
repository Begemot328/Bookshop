create table positions
(
    id       int auto_increment
        primary key,
    book_id  int           null,
    shop_id  int           null,
    status   tinyint       not null,
    note     text          null,
    quantity int default 1 not null,
    constraint positions_ibfk_1
        foreign key (status) references position_status (id)
            on update cascade,
    constraint positions_ibfk_2
        foreign key (shop_id) references shops (id)
            on update cascade,
    constraint positions_ibfk_3
        foreign key (book_id) references books (id)
            on update cascade
);

create index book_index
    on positions (book_id);

create index id_index
    on positions (id);

create index shop_index
    on positions (shop_id);

create index status
    on positions (status);

INSERT INTO bookshop.positions (id, book_id, shop_id, status, note, quantity) VALUES (1, 1, 1, 1, null, 1);
INSERT INTO bookshop.positions (id, book_id, shop_id, status, note, quantity) VALUES (2, 2, 1, 0, null, 1);
INSERT INTO bookshop.positions (id, book_id, shop_id, status, note, quantity) VALUES (3, 2, 1, 0, null, 3);
INSERT INTO bookshop.positions (id, book_id, shop_id, status, note, quantity) VALUES (4, 1, 2, 1, null, 2);
INSERT INTO bookshop.positions (id, book_id, shop_id, status, note, quantity) VALUES (5, 2, 1, 0, null, 1);
INSERT INTO bookshop.positions (id, book_id, shop_id, status, note, quantity) VALUES (6, 4, 1, 1, null, 1);
INSERT INTO bookshop.positions (id, book_id, shop_id, status, note, quantity) VALUES (7, 4, 1, 2, null, 1);
INSERT INTO bookshop.positions (id, book_id, shop_id, status, note, quantity) VALUES (8, 2, 1, 1, null, 5);