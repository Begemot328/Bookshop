create definer = root@localhost view total_price_by_shop as
select `bookshop`.`shops`.`name` AS `name`, sum((`bookshop`.`positions`.`quantity` * `b`.`price`)) AS `total_sum`
from ((`bookshop`.`positions` join `bookshop`.`books` `b` on ((`bookshop`.`positions`.`book_id` = `b`.`id`)))
         join `bookshop`.`shops` on ((`bookshop`.`positions`.`shop_id` = `bookshop`.`shops`.`id`)))
group by `bookshop`.`shops`.`id`;

INSERT INTO bookshop.total_price_by_shop (name, total_sum) VALUES ('Na Grushevke', 636);
INSERT INTO bookshop.total_price_by_shop (name, total_sum) VALUES ('Na Malinovke', 52);