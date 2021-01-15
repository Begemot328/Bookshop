create table user_actions
(
    id             int auto_increment
        primary key,
    id_admin       int      not null,
    id_user        int      not null,
    date_time      datetime null,
    initial_status tinyint  not null,
    final_status   tinyint  not null,
    constraint user_actions_ibfk_1
        foreign key (id_user) references users (id)
            on update cascade,
    constraint user_actions_ibfk_2
        foreign key (id_admin) references users (id)
            on update cascade,
    constraint user_actions_ibfk_3
        foreign key (initial_status) references user_status (id)
            on update cascade,
    constraint user_actions_ibfk_4
        foreign key (final_status) references user_status (id)
            on update cascade
);

create index admin_index
    on user_actions (id_admin);

create index final_status
    on user_actions (final_status);

create index id_index
    on user_actions (id);

create index initial_status
    on user_actions (initial_status);

create index user_index
    on user_actions (id_user);

INSERT INTO bookshop.user_actions (id, id_admin, id_user, date_time, initial_status, final_status) VALUES (1, 1, 2, '2020-10-27 09:20:11', 0, 3);
INSERT INTO bookshop.user_actions (id, id_admin, id_user, date_time, initial_status, final_status) VALUES (2, 1, 3, '2020-10-27 09:20:11', 0, 3);
INSERT INTO bookshop.user_actions (id, id_admin, id_user, date_time, initial_status, final_status) VALUES (3, 1, 4, '2020-10-27 09:20:11', 0, 2);
INSERT INTO bookshop.user_actions (id, id_admin, id_user, date_time, initial_status, final_status) VALUES (4, 1, 5, '2020-10-27 09:20:11', 0, 2);