create table position_actions
(
    id                  int auto_increment
        primary key,
    initial_position_id int           null,
    final_position_id   int           not null,
    initial_status      tinyint       not null,
    final_status        tinyint       not null,
    buyer_id            int           null,
    seller_id           int           null,
    date_time           datetime      null,
    quantity            int default 1 not null,
    shop_id             int           not null,
    current_price       double        not null,
    constraint position_actions_ibfk_1
        foreign key (initial_status) references position_status (id)
            on update cascade,
    constraint position_actions_ibfk_2
        foreign key (final_status) references position_status (id)
            on update cascade,
    constraint position_actions_ibfk_3
        foreign key (initial_position_id) references positions (id)
            on update cascade,
    constraint position_actions_ibfk_4
        foreign key (final_position_id) references positions (id)
            on update cascade,
    constraint position_actions_ibfk_5
        foreign key (buyer_id) references users (id)
            on update cascade,
    constraint position_actions_ibfk_6
        foreign key (seller_id) references users (id)
            on update cascade
);

create index current_price_index
    on position_actions (current_price);

create index final_position_index
    on position_actions (final_position_id);

create index final_status
    on position_actions (final_status);

create index id_index
    on position_actions (id);

create index initial_position_index
    on position_actions (initial_position_id);

create index initial_status
    on position_actions (initial_status);

create index librarian_index
    on position_actions (seller_id);

create index reader_index
    on position_actions (buyer_id);

INSERT INTO bookshop.position_actions (id, initial_position_id, final_position_id, initial_status, final_status, buyer_id, seller_id, date_time, quantity, shop_id, current_price) VALUES (1, null, 1, 0, 1, null, 2, '2020-11-10 09:10:11', 1, 1, 26);
INSERT INTO bookshop.position_actions (id, initial_position_id, final_position_id, initial_status, final_status, buyer_id, seller_id, date_time, quantity, shop_id, current_price) VALUES (2, null, 2, 0, 1, null, 2, '2020-11-10 09:20:11', 1, 1, 54);
INSERT INTO bookshop.position_actions (id, initial_position_id, final_position_id, initial_status, final_status, buyer_id, seller_id, date_time, quantity, shop_id, current_price) VALUES (3, null, 5, 0, 1, null, 2, '2020-11-15 09:25:11', 1, 1, 35);
INSERT INTO bookshop.position_actions (id, initial_position_id, final_position_id, initial_status, final_status, buyer_id, seller_id, date_time, quantity, shop_id, current_price) VALUES (4, null, 3, 0, 1, null, 2, '2020-11-10 09:20:11', 3, 1, 54);
INSERT INTO bookshop.position_actions (id, initial_position_id, final_position_id, initial_status, final_status, buyer_id, seller_id, date_time, quantity, shop_id, current_price) VALUES (5, null, 4, 0, 1, null, 3, '2020-11-10 09:30:11', 2, 2, 26);
INSERT INTO bookshop.position_actions (id, initial_position_id, final_position_id, initial_status, final_status, buyer_id, seller_id, date_time, quantity, shop_id, current_price) VALUES (6, 1, 1, 1, 3, 4, 3, '2020-11-10 09:30:11', 1, 2, 26);
INSERT INTO bookshop.position_actions (id, initial_position_id, final_position_id, initial_status, final_status, buyer_id, seller_id, date_time, quantity, shop_id, current_price) VALUES (7, 2, 2, 1, 2, 5, 3, '2020-11-10 09:30:11', 1, 1, 54);
INSERT INTO bookshop.position_actions (id, initial_position_id, final_position_id, initial_status, final_status, buyer_id, seller_id, date_time, quantity, shop_id, current_price) VALUES (8, 6, 6, 0, 1, 5, 3, '2020-11-10 09:30:11', 2, 1, 35);
INSERT INTO bookshop.position_actions (id, initial_position_id, final_position_id, initial_status, final_status, buyer_id, seller_id, date_time, quantity, shop_id, current_price) VALUES (9, 6, 7, 1, 2, null, 2, '2020-12-15 09:25:11', 1, 1, 35);
INSERT INTO bookshop.position_actions (id, initial_position_id, final_position_id, initial_status, final_status, buyer_id, seller_id, date_time, quantity, shop_id, current_price) VALUES (10, 2, 8, 1, 1, null, 1, '2021-01-13 08:31:21', 1, 1, 54);
INSERT INTO bookshop.position_actions (id, initial_position_id, final_position_id, initial_status, final_status, buyer_id, seller_id, date_time, quantity, shop_id, current_price) VALUES (11, 3, 8, 1, 1, null, 1, '2021-01-13 08:31:21', 3, 1, 54);
INSERT INTO bookshop.position_actions (id, initial_position_id, final_position_id, initial_status, final_status, buyer_id, seller_id, date_time, quantity, shop_id, current_price) VALUES (12, 5, 8, 1, 1, null, 1, '2021-01-13 08:31:21', 1, 1, 54);