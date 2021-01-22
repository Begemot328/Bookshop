drop schema if exists bookshop;
create schema if not exists bookshop;
use bookshop;

create table position_status
(
    id           tinyint primary key,
    name         varchar(40) not null,
    picture_link varchar(40)
);

insert
    position_status (id, name)
values (0, 'non-existent');
insert
    position_status (id, name)
values (1, 'ready');
insert
    position_status (id, name)
values (2, 'sold');
insert
    position_status (id, name)
values (3, 'reserved');
insert
    position_status (id, name)
values (4, 'transfer');

create table user_status
(
    id   tinyint primary key,
    name varchar(40) not null
);

insert
    user_status (id, name)
values (0, 'non-existent');
insert
    user_status (id, name)
values (1, 'disabled');
insert
    user_status (id, name)
values (2, 'buyer');
insert
    user_status (id, name)
values (3, 'seller');
insert
    user_status (id, name)
values (4, 'admin');
insert
    user_status (id, name)
values (5, 'courier');

create table users
(
    id         int primary key auto_increment,
    first_name varchar(40) not null,
    last_name  varchar(40) not null,
    login      varchar(10) not null,
    password   int(10) not null,
    address    varchar(100) not null,
    photo_link varchar(100),
    status     tinyint
);

drop table if exists genres;

create table genres
(
    id   int primary key auto_increment,
    name varchar(40) not null
);

create index id_index on genres (id);

create
    index id_index on users (id);
create
    index lastname_index on users (last_name);

create table user_actions
(
    id             int primary key auto_increment not null,
    id_admin       int     not null,
    id_user        int     not null,
    date_time      datetime,
    initial_status tinyint not null,
    final_status   tinyint not null,

    foreign key (id_user) references users (id) ON DELETE RESTRICT ON UPDATE CASCADE,
    foreign key (id_admin) references users (id) ON DELETE RESTRICT ON UPDATE CASCADE,
    foreign key (initial_status) references user_status (id) ON DELETE RESTRICT ON UPDATE CASCADE,
    foreign key (final_status) references user_status (id) ON DELETE RESTRICT ON UPDATE CASCADE
);

create
    index id_index on user_actions (id);
create
    index user_index on user_actions (id_user);
create
    index admin_index on user_actions (id_admin);

create table authors
(
    id         int primary key auto_increment,
    first_name varchar(40) not null,
    last_name  varchar(40) not null,
    photo_link varchar(100),
    description varchar(400)
);

create
    index id_index on authors (id);

create table books
(
    id          int primary key auto_increment,
    title       varchar(40) not null,
    author_id   int         not null,
    price       float,
    description text,
    photo_link  varchar(100),
    foreign key (author_id) references authors (id) ON DELETE RESTRICT ON UPDATE CASCADE
);

create
    index id_index on books (id);
create
    index title_index on books (title);
create
    index author_index on books (author_id);
create
    index price_index on books (price);


drop table if exists genre_books;

create table genre_books
(
    genre_id int,
    book_id  int,
    primary key (genre_id, book_id),
    foreign key (book_id) references books (id) ON DELETE RESTRICT ON UPDATE CASCADE,
    foreign key (genre_id) references genres (id) ON DELETE CASCADE ON UPDATE CASCADE
);
create index book_id_index on genre_books (book_id);
create index genre_id_index on genre_books (genre_id);

drop view if exists books_genres;

create view books_genres as
select books.id, books.title, books.author_id, books.price,
       books.description, books.photo_link,
       g.id as genre_id
from books
         join genre_books gb on gb.book_id = books.id
         join genres g on gb.genre_id = g.id;


create table shops
(
    id         int primary key auto_increment not null,
    name       varchar(40)  not null,
    address    varchar(100) not null,
    photo_link varchar(100)
);

create
    index id_index on shops (id);

create table positions
(
    id       int primary key auto_increment,
    book_id  int,
    shop_id  int,
    status   tinyint not null,
    note     text,
    quantity int     not null default 1,
    foreign key (status) references position_status (id) ON DELETE RESTRICT ON UPDATE CASCADE,
    foreign key (shop_id) references shops (id) ON DELETE RESTRICT ON UPDATE CASCADE,
    foreign key (book_id) references books (id) ON DELETE RESTRICT ON UPDATE CASCADE
);

create
    index id_index on positions (id);
create
    index book_index on positions (book_id);
create
    index shop_index on positions (shop_id);

create table position_actions
(
    id                  int primary key auto_increment not null,
    initial_position_id int,
    final_position_id   int     not null,
    initial_status      tinyint not null,
    final_status        tinyint not null,
    buyer_id            int,
    seller_id           int,
    date_time           datetime,
    quantity            int     not null default 1,
    shop_id             int     not null,
    current_price       double  not null,


    foreign key (initial_status) references position_status (id) ON DELETE RESTRICT ON UPDATE CASCADE,
    foreign key (final_status) references position_status (id) ON DELETE RESTRICT ON UPDATE CASCADE,
    foreign key (initial_position_id) references positions (id) ON DELETE RESTRICT ON UPDATE CASCADE,
    foreign key (final_position_id) references positions (id) ON DELETE RESTRICT ON UPDATE CASCADE,
    foreign key (buyer_id) references users (id) ON DELETE RESTRICT ON UPDATE CASCADE,
    foreign key (seller_id) references users (id) ON DELETE RESTRICT ON UPDATE CASCADE
);

create
    index id_index on position_actions (id);
create
    index initial_position_index on position_actions (initial_position_id);
create
    index final_position_index on position_actions (final_position_id);
create
    index librarian_index on position_actions (seller_id);
create
    index reader_index on position_actions (buyer_id);
create
    index current_price_index on position_actions (current_price);