-- MySQL dump 10.13  Distrib 8.0.21, for Win64 (x86_64)
--
-- Host: localhost    Database: bookshop
-- ------------------------------------------------------
-- Server version	8.0.21

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Temporary view structure for view `total_price`
--

DROP TABLE IF EXISTS `total_price`;
/*!50001 DROP VIEW IF EXISTS `total_price`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `total_price` AS SELECT 
 1 AS `total_sum`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `books_in_stock`
--

DROP TABLE IF EXISTS `books_in_stock`;
/*!50001 DROP VIEW IF EXISTS `books_in_stock`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `books_in_stock` AS SELECT 
 1 AS `title`,
 1 AS `price`,
 1 AS `sum(positions.quantity)`,
 1 AS `shop_name`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `books_by_reader`
--

DROP TABLE IF EXISTS `books_by_reader`;
/*!50001 DROP VIEW IF EXISTS `books_by_reader`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `books_by_reader` AS SELECT 
 1 AS `first_name`,
 1 AS `last_name`,
 1 AS `title`,
 1 AS `authors_first_name`,
 1 AS `authors_last_name`,
 1 AS `name`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `books_by_author`
--

DROP TABLE IF EXISTS `books_by_author`;
/*!50001 DROP VIEW IF EXISTS `books_by_author`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `books_by_author` AS SELECT 
 1 AS `id`,
 1 AS `first_name`,
 1 AS `last_name`,
 1 AS `title`,
 1 AS `price`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `total_price_by_shop`
--

DROP TABLE IF EXISTS `total_price_by_shop`;
/*!50001 DROP VIEW IF EXISTS `total_price_by_shop`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `total_price_by_shop` AS SELECT 
 1 AS `name`,
 1 AS `total_sum`*/;
SET character_set_client = @saved_cs_client;

--
-- Final view structure for view `total_price`
--

/*!50001 DROP VIEW IF EXISTS `total_price`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `total_price` AS select sum((`positions`.`quantity` * `b`.`price`)) AS `total_sum` from (`positions` join `books` `b` on((`positions`.`book_id` = `b`.`id`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `books_in_stock`
--

/*!50001 DROP VIEW IF EXISTS `books_in_stock`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `books_in_stock` AS select `books`.`title` AS `title`,`books`.`price` AS `price`,sum(`positions`.`quantity`) AS `sum(positions.quantity)`,`shops`.`name` AS `shop_name` from ((`books` join `positions` on((`books`.`id` = `positions`.`book_id`))) join `shops` on((`positions`.`shop_id` = `shops`.`id`))) where (`positions`.`status` = 2) group by `books`.`id`,`shops`.`id` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `books_by_reader`
--

/*!50001 DROP VIEW IF EXISTS `books_by_reader`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `books_by_reader` AS select `users`.`first_name` AS `first_name`,`users`.`last_name` AS `last_name`,`books`.`title` AS `title`,`authors`.`first_name` AS `authors_first_name`,`authors`.`last_name` AS `authors_last_name`,`ps`.`name` AS `name` from (((((`position_actions` join `positions` on(((`position_actions`.`initial_position_id` = `positions`.`id`) or (`position_actions`.`final_position_id` = `positions`.`id`)))) join `books` on((`positions`.`book_id` = `books`.`id`))) join `users` on((`position_actions`.`buyer_id` = `users`.`id`))) join `authors` on((`books`.`author_id` = `authors`.`id`))) join `position_status` `ps` on((`position_actions`.`final_status` = `ps`.`id`))) group by `users`.`id` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `books_by_author`
--

/*!50001 DROP VIEW IF EXISTS `books_by_author`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `books_by_author` AS select `authors`.`id` AS `id`,`authors`.`first_name` AS `first_name`,`authors`.`last_name` AS `last_name`,`books`.`title` AS `title`,`books`.`price` AS `price` from (`authors` join `books` on((`authors`.`id` = `books`.`author_id`))) group by `books`.`title` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `total_price_by_shop`
--

/*!50001 DROP VIEW IF EXISTS `total_price_by_shop`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `total_price_by_shop` AS select `shops`.`name` AS `name`,sum((`positions`.`quantity` * `b`.`price`)) AS `total_sum` from ((`positions` join `books` `b` on((`positions`.`book_id` = `b`.`id`))) join `shops` on((`positions`.`shop_id` = `shops`.`id`))) group by `shops`.`id` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-01-16 15:22:32
