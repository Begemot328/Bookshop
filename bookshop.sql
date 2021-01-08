drop schema if exists bookshop;

create schema if not exists bookshop;

use
bookshop;

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
    photo_link varchar(100)
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

#views
#all books in stock
drop view if exists bookshop.books_in_stock;
create view bookshop.books_in_stock as
select books.title,
       books.price,
       sum(positions.quantity),
       shops.name as shop_name
from bookshop.books
         join bookshop.positions on books.id = positions.book_id
         join bookshop.shops on positions.shop_id = shops.id
where positions.status = 2
group by books.id, shops.id;

drop view if exists total_price;
create view total_price as
select sum(positions.quantity * b.price) as total_sum
from positions
         join books b on positions.book_id = b.id;

drop view if exists bookshop.total_price_by_shop;
create view bookshop.total_price_by_shop as
select shops.name, sum(positions.quantity * b.price) as total_sum
from bookshop.positions
         join bookshop.books b on positions.book_id = b.id
         join bookshop.shops on positions.shop_id = shops.id
group by shops.id;


drop view if exists bookshop.books_by_reader;
create view bookshop.books_by_reader as
select users.first_name,
       users.last_name,
       books.title,
       authors.first_name as authors_first_name,
       authors.last_name  as authors_last_name,
       ps.name
from position_actions
         join positions on position_actions.initial_position_id = positions.id
    or position_actions.final_position_id = positions.id
         join books on positions.book_id = books.id
         join users on position_actions.buyer_id = users.id
         join authors on books.author_id = authors.id
         join position_status ps on position_actions.final_status = ps.id
group by users.id;

drop view if exists bookshop.books_by_author;
create view bookshop.books_by_author as
select bookshop.authors.id,
       bookshop.authors.first_name,
       bookshop.authors.last_name,
       bookshop.books.title,
       bookshop.books.price
from bookshop.authors
         join bookshop.books
              on bookshop.authors.id = bookshop.books.author_id
group by books.title;

select *
from (
         select *
         from bookshop.books_by_author
         where books_by_author.price > 20) as q1
where id = 2;

#root admin, do not change!
insert users(id, first_name, last_name, login, password, address, status)
values (1, 'Yury', 'Zmushko', 'root', 3506402, #root root
        'vulica Hrušaŭskaja 73, Minsk 220089, Belarus', 4);

#populating tables
insert users(id, first_name, last_name, login, password, address, status)
values (2, 'Ivan', 'Ivanoy', 'IIvanoy', -946852072, #change to hash
        'praspiekt Niezaliežnasci 65, Minsk, Belarus', 3);
insert
user_actions(id_admin, id_user, date_time, initial_status, final_status)
VALUES (1, 2, '2020_10_27 09:20:11', 0, 3);

insert
users(id, first_name, last_name, login, password, address, status)
values (3, 'Petr', 'Petroy', 'PPetroy', -946852072, #change to hash
        'Ulitsa Novovilenskaya 11, Minsk, Belarus', 3);
insert
user_actions(id_admin, id_user, date_time, initial_status, final_status)
VALUES (1, 3, '2020_10_27 09:20:11', 0, 3);

insert
users(id, first_name, last_name, login, password, address, status)
values (4, 'Sidor ', 'Sidorov', 'SSidorov', -946852072, #change to hash
        'vulica Janki Lučyny 11, Minsk, Belarus', 2);
insert
user_actions(id_admin, id_user, date_time, initial_status, final_status)
VALUES (1, 4, '2020_10_27 09:20:11', 0, 2);

insert
users(id, first_name, last_name, login, password, address, status)
values (5, 'Morozov ', 'Igor', 'IMorozov', -946852072, #change to hash
        'praspiekt Haziety Praŭda 25, Minsk 220089, Belarus', 2);
insert
user_actions(id_admin, id_user, date_time, initial_status, final_status)
VALUES (1, 5, '2020_10_27 09:20:11', 0, 2);

insert
authors (id, first_name, last_name)
VALUES (2, 'George', 'Martin');
insert
authors (id, first_name, last_name)
VALUES (1, ' ', 'The Church');
insert
authors (id, first_name, last_name)
VALUES (3, 'George', 'Orwell');

insert
shops(id, name, address, photo_link)
values (1, 'Na Grushevke', 'Prospekt Dzerzhinskogo 19, Minsk 220089, Belarus', '');

insert
shops(id, name, address)
values (2, 'Na Malinovke', 'Praspiekt Dziaržynskaha 130, Minsk, Belarus');

insert
books(id, title, author_id, price, description)
VALUES (1, 'Holy bible', 1, 26, null);
insert
positions(id, book_id, shop_id, status, note, quantity)
VALUES (1, 1, 1, 1, null, 1);
insert
position_actions(final_position_id, buyer_id, seller_id, date_time,
                     initial_status, final_status, shop_id, quantity, current_price)
values (1, null, 2, '2020_11_10 09:10:11', 0, 1, 1, 1, 26);

insert
books(id, title, author_id, price, description)
VALUES (2, 'Game of thrones', 2, 54, null);
insert
positions(id, book_id, shop_id, status, note, quantity)
values (2, 2, 1, 1, null, 1);
insert
position_actions(final_position_id, buyer_id, seller_id, date_time,
                     initial_status, final_status, shop_id, quantity, current_price)
values (2, null, 2, '2020_11_10 09:20:11', 0, 1, 1, 1, 54);

insert
books(id, title, author_id, price, description, photo_link)
VALUES (3, 'Clash of kings', 2, 35, null,
        'https://drive.google.com/uc?export=view&id=10KL--ZudHfEul_uWVBTdm-4GHVsfZzxc');
insert
positions(id, book_id, shop_id, status, note, quantity)
values (5, 2, 1, 1, null, 1);
insert
position_actions(initial_position_id, final_position_id, buyer_id, seller_id, date_time,
                     initial_status, final_status, shop_id, quantity, current_price)
values (null, 5, null, 2, '2020_11_15 09:25:11', 0, 1, 1, 1, 35);

insert
positions(id, book_id, shop_id, status, note, quantity)
values (3, 2, 1, 1, null, 3);
insert
position_actions(initial_position_id, final_position_id, buyer_id, seller_id, date_time,
                     initial_status, final_status, shop_id, quantity, current_price)
values (null, 3, null, 2, '2020_11_10 09:20:11', 0, 1, 1, 3, 54);
insert
positions(id, book_id, shop_id, status, note, quantity)
values (4, 1, 2, 1, null, 2);
insert
position_actions(final_position_id, buyer_id, seller_id, date_time,
                     initial_status, final_status, shop_id, quantity, current_price)
values (4, null, 3, '2020_11_10 09:30:11', 0, 1, 2, 2, 26);

insert
position_actions(initial_position_id, final_position_id, buyer_id, seller_id, date_time,
                     initial_status, final_status, shop_id, quantity, current_price)
values (1, 1, 4, 3, '2020_11_10 09:30:11', 1, 3, 2, 1, 26);
insert
position_actions(initial_position_id, final_position_id, buyer_id, seller_id, date_time,
                     initial_status, final_status, shop_id, quantity, current_price)
values (2, 2, 5, 3, '2020_11_10 09:30:11', 1, 2, 1, 1, 54);

insert
books(id, title, author_id, price, description)
VALUES (4, '1984', 3, 35, null);
insert
positions(id, book_id, shop_id, status, note, quantity)
values (6, 4, 1, 1, null, 2);
insert
position_actions(initial_position_id, final_position_id, buyer_id, seller_id, date_time,
                     initial_status, final_status, shop_id, quantity, current_price)
values (6, 6, 5, 3, '2020_11_10 09:30:11', 0, 1, 1, 2, 35);

insert
positions(id, book_id, shop_id, status, note, quantity)
values (7, 4, 1, 2, null, 1);
insert
position_actions(initial_position_id, final_position_id, buyer_id, seller_id, date_time,
                     initial_status, final_status, shop_id, quantity, current_price)
values (6, 7, null, 2, '2020_12_15 09:25:11', 1, 2, 1, 1, 35);
update positions
set quantity = 1
where id = 6;