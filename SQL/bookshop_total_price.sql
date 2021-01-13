create definer = root@localhost view total_price as
select sum((`bookshop`.`positions`.`quantity` * `b`.`price`)) AS `total_sum`
from (`bookshop`.`positions`
         join `bookshop`.`books` `b` on ((`bookshop`.`positions`.`book_id` = `b`.`id`)));

INSERT INTO bookshop.total_price (total_sum) VALUES (688);