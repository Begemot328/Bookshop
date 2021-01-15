create definer = root@localhost view books_by_reader as
select `bookshop`.`users`.`first_name`   AS `first_name`,
       `bookshop`.`users`.`last_name`    AS `last_name`,
       `bookshop`.`books`.`title`        AS `title`,
       `bookshop`.`authors`.`first_name` AS `authors_first_name`,
       `bookshop`.`authors`.`last_name`  AS `authors_last_name`,
       `ps`.`name`                       AS `name`
from (((((`bookshop`.`position_actions` join `bookshop`.`positions` on ((
        (`bookshop`.`position_actions`.`initial_position_id` = `bookshop`.`positions`.`id`) or
        (`bookshop`.`position_actions`.`final_position_id` =
         `bookshop`.`positions`.`id`)))) join `bookshop`.`books` on ((`bookshop`.`positions`.`book_id` = `bookshop`.`books`.`id`))) join `bookshop`.`users` on ((`bookshop`.`position_actions`.`buyer_id` = `bookshop`.`users`.`id`))) join `bookshop`.`authors` on ((`bookshop`.`books`.`author_id` = `bookshop`.`authors`.`id`)))
         join `bookshop`.`position_status` `ps` on ((`bookshop`.`position_actions`.`final_status` = `ps`.`id`)))
group by `bookshop`.`users`.`id`;

INSERT INTO bookshop.books_by_reader (first_name, last_name, title, authors_first_name, authors_last_name, name) VALUES ('Sidor ', 'Sidorov', 'Holy bible', ' ', 'The Church', 'reserved');
INSERT INTO bookshop.books_by_reader (first_name, last_name, title, authors_first_name, authors_last_name, name) VALUES ('Morozov ', 'Igor', 'Game of thrones', 'George', 'Martin', 'sold');