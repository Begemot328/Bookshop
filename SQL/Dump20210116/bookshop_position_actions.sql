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
-- Table structure for table `position_actions`
--

DROP TABLE IF EXISTS `position_actions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `position_actions` (
  `id` int NOT NULL AUTO_INCREMENT,
  `initial_position_id` int DEFAULT NULL,
  `final_position_id` int NOT NULL,
  `initial_status` tinyint NOT NULL,
  `final_status` tinyint NOT NULL,
  `buyer_id` int DEFAULT NULL,
  `seller_id` int DEFAULT NULL,
  `date_time` datetime DEFAULT NULL,
  `quantity` int NOT NULL DEFAULT '1',
  `shop_id` int NOT NULL,
  `current_price` double NOT NULL,
  PRIMARY KEY (`id`),
  KEY `initial_status` (`initial_status`),
  KEY `final_status` (`final_status`),
  KEY `id_index` (`id`),
  KEY `initial_position_index` (`initial_position_id`),
  KEY `final_position_index` (`final_position_id`),
  KEY `librarian_index` (`seller_id`),
  KEY `reader_index` (`buyer_id`),
  KEY `current_price_index` (`current_price`),
  CONSTRAINT `position_actions_ibfk_1` FOREIGN KEY (`initial_status`) REFERENCES `position_status` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `position_actions_ibfk_2` FOREIGN KEY (`final_status`) REFERENCES `position_status` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `position_actions_ibfk_3` FOREIGN KEY (`initial_position_id`) REFERENCES `positions` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `position_actions_ibfk_4` FOREIGN KEY (`final_position_id`) REFERENCES `positions` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `position_actions_ibfk_5` FOREIGN KEY (`buyer_id`) REFERENCES `users` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `position_actions_ibfk_6` FOREIGN KEY (`seller_id`) REFERENCES `users` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `position_actions`
--

LOCK TABLES `position_actions` WRITE;
/*!40000 ALTER TABLE `position_actions` DISABLE KEYS */;
INSERT INTO `position_actions` VALUES (1,NULL,1,0,1,NULL,2,'2020-11-10 09:10:11',1,1,26),(2,NULL,2,0,1,NULL,2,'2020-11-10 09:20:11',1,1,54),(3,NULL,5,0,1,NULL,2,'2020-11-15 09:25:11',1,1,35),(4,NULL,3,0,1,NULL,2,'2020-11-10 09:20:11',3,1,54),(5,NULL,4,0,1,NULL,3,'2020-11-10 09:30:11',2,2,26),(6,1,1,1,3,4,3,'2020-11-10 09:30:11',1,2,26),(7,2,2,1,2,5,3,'2020-11-10 09:30:11',1,1,54),(8,6,6,0,1,5,3,'2020-11-10 09:30:11',2,1,35),(9,6,7,1,2,NULL,2,'2020-12-15 09:25:11',1,1,35),(10,2,8,1,1,NULL,1,'2021-01-13 08:31:21',1,1,54),(11,3,8,1,1,NULL,1,'2021-01-13 08:31:21',3,1,54),(12,5,8,1,1,NULL,1,'2021-01-13 08:31:21',1,1,54);
/*!40000 ALTER TABLE `position_actions` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-01-16 15:22:32
