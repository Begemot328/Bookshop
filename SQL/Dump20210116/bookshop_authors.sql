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
-- Table structure for table `authors`
--

DROP TABLE IF EXISTS `authors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `authors` (
  `id` int NOT NULL AUTO_INCREMENT,
  `first_name` varchar(40) NOT NULL,
  `last_name` varchar(40) NOT NULL,
  `photo_link` varchar(100) DEFAULT NULL,
  `description` text,
  PRIMARY KEY (`id`),
  KEY `id_index` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `authors`
--

LOCK TABLES `authors` WRITE;
/*!40000 ALTER TABLE `authors` DISABLE KEYS */;
INSERT INTO `authors` VALUES (1,' ','The Church',NULL,NULL),(2,'George','Martin','https://drive.google.com/uc?export=view&id=10c9CU4e_FWvGbc4RMvwSCH7HMqPPbgTX','George Raymond Richard Martin(born George Raymond Martin; September 20, 1948), also known as GRRM, is an American novelist and short story writer, screenwriter, and television producer. He is the author of the series of epic fantasy novels A Song of Ice and Fire, which was adapted into the Emmy Award-winning HBO series Game of Thrones (2011–2019).\r\n\r\nIn 2005, Lev Grossman of Time called Martin \"the American Tolkien\", and in 2011, he was included on the annual Time 100 list of the most influential people in the world.'),(3,'George','Orwell','https://drive.google.com/uc?export=view&id=1MkbsHovRhQFoYSE_0DLWSkJW-SIYbSSD',NULL),(4,'Roger ','Zelazny','https://drive.google.com/uc?export=view&id=1W7yH9YKjrvxdb5ILvo3u57T2NIBw23_t',NULL),(5,'Лев','Толстой','https://drive.google.com/uc?export=view&id=1j-sx_Uq_Cc1YEUcQGxc_m2ucZ-PsrPxq',NULL),(6,'John','Tolkien','https://drive.google.com/uc?export=view&id=197fOoctZCdA3NhoRpycjoM2tTu4vwyFV',NULL),(7,'Федор','Достоевский','https://drive.google.com/uc?export=view&id=1ffGYUh8B3gFX-0vgrnjg2fyunoZ9cZiM',NULL),(8,'Александр','Пушкин','https://drive.google.com/uc?export=view&id=1b6-V7ZBHNWINqrHKMLGGcetWPVXooEoD',NULL),(9,'Terry','Pratchett','https://drive.google.com/uc?export=view&id=1ZzlzMEfk1r0G1Tm2fgMrCrhqPepD13TK',NULL),(10,'Янка','Купала','https://drive.google.com/uc?export=view&id=1XlhV2kImiFwHPnnxDtduYFaNREuTueIk',NULL),(11,'Сергей','Довлатов','https://drive.google.com/uc?export=view&id=19F_Al4LukFLFjd-rY04_wpW0LnFpQjJl',NULL),(12,'Віктар',' Марціновіч','https://drive.google.com/uc?export=view&id=1YvwCqMAAuPt_DW-ZdbE7qljAq4PWjL5y',NULL),(13,'Александр','Солженицын','https://drive.google.com/uc?export=view&id=1wQQSj6brxAB8xjbimKwlUyKf3yPpE6BA',NULL),(14,'Gregory David','Roberts','https://drive.google.com/uc?export=view&id=1FMeaPu8dAwqBBiOhXj5mzm755ykS7-ii',NULL),(15,'fdfsd','sdfsdf',NULL,NULL);
/*!40000 ALTER TABLE `authors` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-01-16 15:22:30
