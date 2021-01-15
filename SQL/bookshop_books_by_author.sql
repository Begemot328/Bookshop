create definer = root@localhost view books_by_author as
select `bookshop`.`authors`.`id`         AS `id`,
       `bookshop`.`authors`.`first_name` AS `first_name`,
       `bookshop`.`authors`.`last_name`  AS `last_name`,
       `bookshop`.`books`.`title`        AS `title`,
       `bookshop`.`books`.`price`        AS `price`
from (`bookshop`.`authors`
         join `bookshop`.`books` on ((`bookshop`.`authors`.`id` = `bookshop`.`books`.`author_id`)))
group by `bookshop`.`books`.`title`;

INSERT INTO bookshop.books_by_author (id, first_name, last_name, title, price) VALUES (1, ' ', 'The Church', 'Holy bible', 26);
INSERT INTO bookshop.books_by_author (id, first_name, last_name, title, price) VALUES (2, 'George', 'Martin', 'Game of thrones', 54);
INSERT INTO bookshop.books_by_author (id, first_name, last_name, title, price) VALUES (2, 'George', 'Martin', 'Clash of kings', 35);
INSERT INTO bookshop.books_by_author (id, first_name, last_name, title, price) VALUES (3, 'George', 'Orwell', '1984', 35);
INSERT INTO bookshop.books_by_author (id, first_name, last_name, title, price) VALUES (2, 'George', 'Martin', 'Игра Престолов', 27);
INSERT INTO bookshop.books_by_author (id, first_name, last_name, title, price) VALUES (14, 'Gregory David', 'Roberts', 'Shantaram', 27.5);
INSERT INTO bookshop.books_by_author (id, first_name, last_name, title, price) VALUES (3, 'George', 'Orwell', 'Animal''s Farm', 14);
INSERT INTO bookshop.books_by_author (id, first_name, last_name, title, price) VALUES (1, ' ', 'The Church', '43', 1);