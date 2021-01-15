create definer = root@localhost view books_in_stock as
select `bookshop`.`books`.`title`             AS `title`,
       `bookshop`.`books`.`price`             AS `price`,
       sum(`bookshop`.`positions`.`quantity`) AS `sum(positions.quantity)`,
       `bookshop`.`shops`.`name`              AS `shop_name`
from ((`bookshop`.`books` join `bookshop`.`positions` on ((`bookshop`.`books`.`id` = `bookshop`.`positions`.`book_id`)))
         join `bookshop`.`shops` on ((`bookshop`.`positions`.`shop_id` = `bookshop`.`shops`.`id`)))
where (`bookshop`.`positions`.`status` = 2)
group by `bookshop`.`books`.`id`, `bookshop`.`shops`.`id`;

INSERT INTO bookshop.books_in_stock (title, price, `sum(positions.quantity)`, shop_name) VALUES ('1984', 35, 1, 'Na Grushevke');