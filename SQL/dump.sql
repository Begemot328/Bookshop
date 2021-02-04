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
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `authors`
--

LOCK TABLES `authors` WRITE;
/*!40000 ALTER TABLE `authors` DISABLE KEYS */;
INSERT INTO `authors` VALUES (1,' ','The Church',NULL,NULL),(2,'George','Martin','https://drive.google.com/uc?export=view&id=10c9CU4e_FWvGbc4RMvwSCH7HMqPPbgTX','George Raymond Richard Martin(born George Raymond Martin; September 20, 1948), also known as GRRM, is an American novelist and short story writer, screenwriter, and television producer. He is the author of the series of epic fantasy novels A Song of Ice and Fire, which was adapted into the Emmy Award-winning HBO series Game of Thrones (2011–2019).\r\n\r\nIn 2005, Lev Grossman of Time called Martin \"the American Tolkien\", and in 2011, he was included on the annual Time 100 list of the most influential people in the world.'),(3,'George','Orwell','https://drive.google.com/uc?export=view&id=1MkbsHovRhQFoYSE_0DLWSkJW-SIYbSSD',NULL),(4,'Roger ','Zelazny','https://drive.google.com/uc?export=view&id=1W7yH9YKjrvxdb5ILvo3u57T2NIBw23_t',NULL),(5,'Лев','Толстой','https://drive.google.com/uc?export=view&id=1j-sx_Uq_Cc1YEUcQGxc_m2ucZ-PsrPxq',NULL),(6,'John','Tolkien','https://drive.google.com/uc?export=view&id=197fOoctZCdA3NhoRpycjoM2tTu4vwyFV',NULL),(7,'Федор','Достоевский','https://drive.google.com/uc?export=view&id=1ffGYUh8B3gFX-0vgrnjg2fyunoZ9cZiM',NULL),(8,'Александр','Пушкин','https://drive.google.com/uc?export=view&id=1b6-V7ZBHNWINqrHKMLGGcetWPVXooEoD',NULL),(9,'Terry','Pratchett','https://drive.google.com/uc?export=view&id=1ZzlzMEfk1r0G1Tm2fgMrCrhqPepD13TK',NULL),(10,'Янка','Купала','https://drive.google.com/uc?export=view&id=1XlhV2kImiFwHPnnxDtduYFaNREuTueIk',NULL),(11,'Сергей','Довлатов','https://drive.google.com/uc?export=view&id=19F_Al4LukFLFjd-rY04_wpW0LnFpQjJl',NULL),(12,'Віктар','Марціновіч','https://drive.google.com/uc?export=view&id=1YvwCqMAAuPt_DW-ZdbE7qljAq4PWjL5y',NULL),(13,'Александр','Солженицын','https://drive.google.com/uc?export=view&id=1wQQSj6brxAB8xjbimKwlUyKf3yPpE6BA',NULL),(14,'Gregory David','Roberts','https://drive.google.com/uc?export=view&id=1FMeaPu8dAwqBBiOhXj5mzm755ykS7-ii',NULL),(15,'fdfsd','sdfsdf',NULL,NULL),(16,'Alexander','Pushkin','http://www.google.com','description'),(17,'Alexander','Pushkin','http://www.google.com','description'),(18,'Alexander','Pushkin','http://www.google.com','description');
/*!40000 ALTER TABLE `authors` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `books`
--

DROP TABLE IF EXISTS `books`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `books` (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(40) NOT NULL,
  `author_id` int NOT NULL,
  `price` float DEFAULT NULL,
  `description` text,
  `photo_link` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_index` (`id`),
  KEY `title_index` (`title`),
  KEY `author_index` (`author_id`),
  KEY `price_index` (`price`),
  CONSTRAINT `books_ibfk_1` FOREIGN KEY (`author_id`) REFERENCES `authors` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `books`
--

LOCK TABLES `books` WRITE;
/*!40000 ALTER TABLE `books` DISABLE KEYS */;
INSERT INTO `books` VALUES (1,'Holy bible',1,26,NULL,NULL),(2,'Game of thrones',2,54,'','https://drive.google.com/uc?export=view&id=1bb51AbdRZNaWYPTMAi8q9g6IOhhH2AjK'),(3,'Clash of kings',2,35,NULL,'https://drive.google.com/uc?export=view&id=10KL--ZudHfEul_uWVBTdm-4GHVsfZzxc'),(4,'1984',3,35,'','https://drive.google.com/uc?export=view&id=17JXG686-AM6MKmQvIv1SeWbsYjz5-FIg'),(5,'Игра Престолов',2,27,'','https://drive.google.com/uc?export=view&id=1J-UMvbXs5VHX9UmjMntq-b4qJeRp8Ufl'),(6,'Shantaram',14,27.5,'','https://drive.google.com/uc?export=view&id=1hA2eua5COf2Ec9EM5iZQjfWz67gnHsrs'),(7,'Animal\'s Farm',3,14,'','https://drive.google.com/uc?export=view&id=1OWLlM_hM9jhWYHObX6YzP8eFfxylhsy6'),(8,'Преступление и наказание',7,9.44,'Одно из \"краеугольных\" произведений русской и мировой литературы, включенный во все школьные и университетские программы, неоднократно экранизированный роман Достоевского \"Преступление и наказание\" ставит перед читателем важнейшие нравственно-мировоззренческие вопросы - о вере, совести, грехе и об искуплении через страдание. Опровержение преступной \"идеи-страсти\", \"безобразной мечты\", завладевшей умом Родиона Раскольникова в самом \"умышленном\" и \"фантастическом\" городе на свете, составляет основное содержание этой сложнейшей, соединившей в себе несколько различных жанров книги. Задуманный как \"психологический отчет одного преступления\", роман Достоевского предстал перед читателем грандиозным художественно-философским исследованием человеческой природы, христианской трагедией о смерти и воскресении души.','https://drive.google.com/uc?export=view&id=151kC5TeqWKVRc0YzUx6VUKm3QiW8Bmvz'),(9,'Идиот',7,9.44,'\"Главная идея... - писал Ф. М. Достоевский о своем романе \"Идиот\", - изобразить положительно-прекрасного человека. Труднее этого нет ничего на свете...\" Не для того ли писатель явил миру \"князя-Христа\", чтобы мы не забывали: \"Страдание есть главнейший и, может быть, единственный закон бытия всего человечества\".','https://drive.google.com/uc?export=view&id=1EWPzKpt_3dZQcruu2Ma6RxOAnOCBxW2F'),(10,'Братья Карамазовы',7,10.52,'Последний, самый объемный и один из наиболее известных романов Ф. М. Достоевского обращает читателя к вневременным нравственно-философским вопросам о грехе, воздаянии, сострадании и милосердии. Книга, которую сам писатель определил как \"роман о богохульстве и опровержении его\", явилась попыткой \"решить вопрос о человеке\", \"разгадать тайну\" человека, что, по Достоевскому, означало \"решить вопрос о Боге\". Сквозь призму истории провинциальной семьи Карамазовых автор повествует об извечной борьбе Божественного и дьявольского в человеческой душе. Один из самых глубоких в мировой литературе опытов отражения христианского сознания, \"Братья Карамазовы\" стали в ХХ веке объектом парадоксальных философских и психоаналитических интерпретаций.','https://drive.google.com/uc?export=view&id=1g8-5l2isSkCt-TjVuveT6PpI9dEmOLBb'),(11,'Бесы',7,10.1,'Уже были написаны \"Записки из Мертвого дома\", \"Записки из подполья\", \"Преступление и наказание\", \"Идиот\" и другие шедевры, а Достоевский все еще испытывал острое чувство неудовлетворенности и, по собственному признанию, только подбирался к главному своему произведению. В 1871-1872 годах выходит его шестой роман с вызывающим и символическим названием \"Бесы\". Увлекательная драма, захватывающий сюжет, бушующие страсти и чрезвычайные, дикие события \"под стеклянным колпаком\" провинциального города, - таково одно из величайших произведений не только русской, но и мировой литературы.','https://drive.google.com/uc?export=view&id=1GA_iXTGZVbcTYlMWQvmiGZB4jAC79ZLR'),(12,'Белые ночи',7,9.2,'Одно из лучших произведений школы \"сентиментального натурализма\", по мнению критика Аполлона Григорьева. Это лирическая исповедь героя-мечтателя, одинокого и робкого человека, в жизни которого на какое-то время появляется девушка, а вместе с ней и надежда на более светлое будущее.','https://drive.google.com/uc?export=view&id=1bpY4bh4SUtEcxBokD2ossIMfaYbZdwiF'),(13,'The Hedge Knight',2,23,'','https://drive.google.com/uc?export=view&id=1xLuOz_mUmaJxZh5mBrWC5vePiPY7bv8j'),(14,'Fire and blood',2,26,'Fire & Blood is a fantasy book by American writer George R. R. Martin. It tells the history of House Targaryen, a family from his series A Song of Ice and Fire. Although originally (in 2013) planned for publication after the completion of the series, Martin has revealed his intent to publish the history in two volumes as the material had grown too large. The first volume was released on November 20, 2018.','https://drive.google.com/uc?export=view&id=14BmyyLD_jjQmhMb44_4T-3xZTHrN3ogk'),(15,'A Feast for Crows',2,35.1,'A Feast for Crows is the fourth of seven planned novels in the epic fantasy series A Song of Ice and Fire by American author George R. R. Martin. The novel was first published on October 17, 2005, in the United Kingdom, with a United States edition following on November 8, 2005.','https://drive.google.com/uc?export=view&id=1QxdHu3yWmQxV7ptyTOZDKRqkYHGx4x79'),(16,'A Dance with Dragons',2,45,'A Dance with Dragons is the fifth novel, of seven planned, in the epic fantasy series A Song of Ice and Fire by American author George R. R. Martin. In some areas, the paperback edition was published in two parts, titled Dreams and Dust and After the Feast. It was the only novel in the series to be published during the eight-season run of the HBO adaptation of the series, Game of Thrones, and runs to 1,040 pages with a word count of almost 415,000.','https://drive.google.com/uc?export=view&id=1zykdaxY-v8AGiPYy9jE6sXHoX-8zqq4t'),(17,'A Clash of Kings',2,54,'A Clash of Kings is the second novel in A Song of Ice and Fire, an epic fantasy series by American author George R. R. Martin expected to consist of seven volumes. It was first published on 16 November 1998 in the United Kingdom, although the first United States edition did not follow until February 2, 1999[2] Like its predecessor, A Game of Thrones, it won the Locus Award (in 1999) for Best Novel and was nominated for the Nebula Award (also in 1999) for best novel. In May 2005 Meisha Merlin released a limited edition of the novel, fully illustrated by John Howe.','https://drive.google.com/uc?export=view&id=1xYcFXHf-GF05FmIYv9sgx6N0YapHYXAf');
/*!40000 ALTER TABLE `books` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Temporary view structure for view `books_genres`
--

DROP TABLE IF EXISTS `books_genres`;
/*!50001 DROP VIEW IF EXISTS `books_genres`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `books_genres` AS SELECT 
 1 AS `id`,
 1 AS `title`,
 1 AS `author_id`,
 1 AS `price`,
 1 AS `description`,
 1 AS `photo_link`,
 1 AS `genre_id`,
 1 AS `name`*/;
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
-- Table structure for table `genre_books`
--

DROP TABLE IF EXISTS `genre_books`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `genre_books` (
  `genre_id` int NOT NULL,
  `book_id` int NOT NULL,
  PRIMARY KEY (`genre_id`,`book_id`),
  KEY `book_id_index` (`book_id`),
  KEY `genre_id_index` (`genre_id`),
  CONSTRAINT `genre_books_ibfk_1` FOREIGN KEY (`book_id`) REFERENCES `books` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `genre_books_ibfk_2` FOREIGN KEY (`genre_id`) REFERENCES `genres` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `genre_books`
--

LOCK TABLES `genre_books` WRITE;
/*!40000 ALTER TABLE `genre_books` DISABLE KEYS */;
INSERT INTO `genre_books` VALUES (1,6),(2,6),(3,6),(5,6),(3,8),(4,8),(2,9),(3,9),(4,9),(3,10),(4,10),(3,11),(4,11),(3,12);
/*!40000 ALTER TABLE `genre_books` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `genres`
--

DROP TABLE IF EXISTS `genres`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `genres` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(40) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_index` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `genres`
--

LOCK TABLES `genres` WRITE;
/*!40000 ALTER TABLE `genres` DISABLE KEYS */;
INSERT INTO `genres` VALUES (1,'detective'),(2,'novel'),(3,'classic'),(4,'russian'),(5,'india');
/*!40000 ALTER TABLE `genres` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `position_actions`
--

LOCK TABLES `position_actions` WRITE;
/*!40000 ALTER TABLE `position_actions` DISABLE KEYS */;
INSERT INTO `position_actions` VALUES (1,NULL,1,0,1,NULL,2,'2020-11-10 09:10:11',1,1,26),(2,NULL,2,0,1,NULL,2,'2020-11-10 09:20:11',1,1,54),(3,NULL,5,0,1,NULL,2,'2020-11-15 09:25:11',1,1,35),(4,NULL,3,0,1,NULL,2,'2020-11-10 09:20:11',3,1,54),(5,NULL,4,0,1,NULL,3,'2020-11-10 09:30:11',2,2,26),(6,1,1,1,3,4,3,'2020-11-10 09:30:11',1,2,26),(7,2,2,1,2,5,3,'2020-11-10 09:30:11',1,1,54),(8,6,6,0,1,5,3,'2020-11-10 09:30:11',2,1,35),(9,6,7,1,2,NULL,2,'2020-12-15 09:25:11',1,1,35),(10,2,8,1,1,NULL,1,'2021-01-13 08:31:21',1,1,54),(11,3,8,1,1,NULL,1,'2021-01-13 08:31:21',3,1,54),(12,5,8,1,1,NULL,1,'2021-01-13 08:31:21',1,1,54),(18,NULL,14,0,1,NULL,1,'2021-01-28 05:52:47',5,3,35.1),(19,NULL,15,0,1,NULL,1,'2021-01-28 05:55:42',6,6,27),(20,15,16,1,3,4,1,'2021-01-28 06:23:47',2,6,27),(21,NULL,17,0,1,NULL,1,'2021-01-29 07:47:16',5,7,10.52),(22,NULL,18,0,1,NULL,1,'2021-01-29 07:47:49',3,8,14),(23,NULL,19,0,1,NULL,1,'2021-01-29 07:48:18',89,3,10.52),(24,NULL,20,0,1,NULL,1,'2021-01-29 07:48:35',9,5,35.1),(25,NULL,21,0,1,NULL,1,'2021-01-29 07:49:30',7,4,27.5),(26,NULL,22,0,1,NULL,1,'2021-01-29 07:50:08',6,7,9.44),(27,NULL,23,0,1,NULL,1,'2021-01-29 07:51:11',8,2,45),(28,NULL,24,0,1,NULL,1,'2021-01-29 07:52:03',9,6,9.2),(29,21,25,1,3,4,1,'2021-01-29 07:52:45',1,4,27.5),(30,21,26,1,2,5,1,'2021-01-29 07:53:19',1,4,27.5),(31,24,27,1,2,4,1,'2021-01-29 07:54:07',1,6,9.2),(32,22,28,1,3,4,1,'2021-01-29 08:10:32',1,7,9.44),(33,24,29,1,3,5,1,'2021-01-29 11:06:16',1,6,9.2),(34,24,30,1,3,5,1,'2021-01-29 11:06:17',1,6,9.2);
/*!40000 ALTER TABLE `position_actions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `position_status`
--

DROP TABLE IF EXISTS `position_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `position_status` (
  `id` tinyint NOT NULL,
  `name` varchar(40) NOT NULL,
  `picture_link` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `position_status`
--

LOCK TABLES `position_status` WRITE;
/*!40000 ALTER TABLE `position_status` DISABLE KEYS */;
INSERT INTO `position_status` VALUES (0,'non-existent',NULL),(1,'ready',NULL),(2,'sold',NULL),(3,'reserved',NULL),(4,'transfer',NULL);
/*!40000 ALTER TABLE `position_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `positions`
--

DROP TABLE IF EXISTS `positions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `positions` (
  `id` int NOT NULL AUTO_INCREMENT,
  `book_id` int DEFAULT NULL,
  `shop_id` int DEFAULT NULL,
  `status` tinyint NOT NULL,
  `note` text,
  `quantity` int NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `status` (`status`),
  KEY `id_index` (`id`),
  KEY `book_index` (`book_id`),
  KEY `shop_index` (`shop_id`),
  CONSTRAINT `positions_ibfk_1` FOREIGN KEY (`status`) REFERENCES `position_status` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `positions_ibfk_2` FOREIGN KEY (`shop_id`) REFERENCES `shops` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `positions_ibfk_3` FOREIGN KEY (`book_id`) REFERENCES `books` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `positions`
--

LOCK TABLES `positions` WRITE;
/*!40000 ALTER TABLE `positions` DISABLE KEYS */;
INSERT INTO `positions` VALUES (1,1,1,1,NULL,1),(2,2,1,0,NULL,1),(3,2,1,0,NULL,3),(4,1,2,1,NULL,2),(5,2,1,0,NULL,1),(6,4,1,1,NULL,1),(7,4,1,2,NULL,1),(8,2,1,1,NULL,5),(14,15,3,1,'На складе справа ',5),(15,5,6,1,'Стопка',4),(16,5,6,3,NULL,2),(17,10,7,1,'',5),(18,7,8,1,'',3),(19,10,3,1,'',89),(20,15,5,1,'',9),(21,6,4,1,'',5),(22,9,7,1,'',5),(23,16,2,1,'',8),(24,12,6,1,'',7),(25,6,4,3,NULL,1),(26,6,4,2,NULL,1),(27,12,6,2,NULL,1),(28,9,7,3,NULL,1),(29,12,6,3,NULL,1),(30,12,6,3,NULL,1);
/*!40000 ALTER TABLE `positions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shops`
--

DROP TABLE IF EXISTS `shops`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `shops` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(40) NOT NULL,
  `address` varchar(100) NOT NULL,
  `photo_link` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_index` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shops`
--

LOCK TABLES `shops` WRITE;
/*!40000 ALTER TABLE `shops` DISABLE KEYS */;
INSERT INTO `shops` VALUES (1,'Na Grushevke','Prospekt Dzerzhinskogo 19, Minsk 220089, Belarus',''),(2,'Na Malinovke','Praspiekt Dziaržynskaha 130, Minsk, Belarus',NULL),(3,'Маяк Минска','НИПТРУП «Институт НИПТИС стройиндустрии», vulica Francyska Skaryny 15, Minsk, Belarus','https://drive.google.com/uc?export=view&id=1wn5ZrOitfxm1EAzFKH7XkdBtrZ5frHCv'),(4,'Масюковщина','vulica Uladzislava Halubka 14, Minsk, Belarus','https://drive.google.com/uc?export=view&id=1yHRz4XiHiwGYdwqbWsk0FKKaq3ANi2eg'),(5,'Сухарево','vulica Prytyckaha 25, Minsk, Belarus','https://drive.google.com/uc?export=view&id=1mZWkbBvRzIpnl0GEleDDnqgCzLLVuDYn'),(6,'Чижовка','vulica Ubareviča 4, Minsk, Belarus','https://drive.google.com/uc?export=view&id=1QYQvrBgIE2LjVuUOloV2mutFjonXhUdj'),(7,'Шабаны','vulica Sialickaha 9, Minsk, Belarus','https://drive.google.com/uc?export=view&id=1Fl8g7zDNSyaKnbBK-irWiiZ30ix178jR'),(8,'Веснянка','Prospekte Pobeditelei 39, Minsk, Belarus','https://drive.google.com/uc?export=view&id=1cpH4w2ahVQPcNgzFREMJWgjRDArh7443');
/*!40000 ALTER TABLE `shops` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Table structure for table `user_actions`
--

DROP TABLE IF EXISTS `user_actions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_actions` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_admin` int NOT NULL,
  `id_user` int NOT NULL,
  `date_time` datetime DEFAULT NULL,
  `initial_status` tinyint NOT NULL,
  `final_status` tinyint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `initial_status` (`initial_status`),
  KEY `final_status` (`final_status`),
  KEY `id_index` (`id`),
  KEY `user_index` (`id_user`),
  KEY `admin_index` (`id_admin`),
  CONSTRAINT `user_actions_ibfk_1` FOREIGN KEY (`id_user`) REFERENCES `users` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `user_actions_ibfk_2` FOREIGN KEY (`id_admin`) REFERENCES `users` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `user_actions_ibfk_3` FOREIGN KEY (`initial_status`) REFERENCES `user_status` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `user_actions_ibfk_4` FOREIGN KEY (`final_status`) REFERENCES `user_status` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_actions`
--

LOCK TABLES `user_actions` WRITE;
/*!40000 ALTER TABLE `user_actions` DISABLE KEYS */;
INSERT INTO `user_actions` VALUES (1,1,2,'2020-10-27 09:20:11',0,3),(2,1,3,'2020-10-27 09:20:11',0,3),(3,1,4,'2020-10-27 09:20:11',0,2),(4,1,5,'2020-10-27 09:20:11',0,2);
/*!40000 ALTER TABLE `user_actions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_status`
--

DROP TABLE IF EXISTS `user_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_status` (
  `id` tinyint NOT NULL,
  `name` varchar(40) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_status`
--

LOCK TABLES `user_status` WRITE;
/*!40000 ALTER TABLE `user_status` DISABLE KEYS */;
INSERT INTO `user_status` VALUES (0,'non-existent'),(1,'disabled'),(2,'buyer'),(3,'seller'),(4,'admin'),(5,'courier');
/*!40000 ALTER TABLE `user_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `first_name` varchar(40) NOT NULL,
  `last_name` varchar(40) NOT NULL,
  `login` varchar(40) NOT NULL,
  `password` varchar(100) NOT NULL,
  `address` varchar(100) NOT NULL,
  `photo_link` varchar(100) DEFAULT NULL,
  `status` tinyint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_index` (`id`),
  KEY `lastname_index` (`last_name`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'Yury','Zmushko','root','ma3CMbBFMx5RSlFrS3aA9YjjgjITq+kBc4vDrWey9vyzxk77k9GAAliNPMwaSe+64c4gy0PfNrOGUfEfp1Z46A==','vulica Hrušaŭskaja 73, Minsk 220089, Belarus',NULL,4),(2,'Ivan','Ivanoy','IIvanoy','DdPlEmQsl8o/dH+aduN0+9pz+SkoI8AxO+nXit183Y9yI1rwxVPdJnl+eOGFTt7grgAviroHSwZt/OGvEU4y+A==','praspiekt Niezaliežnasci 65, Minsk, Belarus',NULL,3),(3,'Petr','Petroy','PPetroy','DdPlEmQsl8o/dH+aduN0+9pz+SkoI8AxO+nXit183Y9yI1rwxVPdJnl+eOGFTt7grgAviroHSwZt/OGvEU4y+A==','Ulitsa Novovilenskaya 11, Minsk, Belarus',NULL,3),(4,'Sidor ','Sidorov','SSidorov','DdPlEmQsl8o/dH+aduN0+9pz+SkoI8AxO+nXit183Y9yI1rwxVPdJnl+eOGFTt7grgAviroHSwZt/OGvEU4y+A==','vulica Janki Lučyny 11, Minsk, Belarus',NULL,2),(5,'Morozov ','Igor','IMorozov','DdPlEmQsl8o/dH+aduN0+9pz+SkoI8AxO+nXit183Y9yI1rwxVPdJnl+eOGFTt7grgAviroHSwZt/OGvEU4y+A==','praspiekt Haziety Praŭda 25, Minsk 220089, Belarus',NULL,2);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Final view structure for view `books_genres`
--

/*!50001 DROP VIEW IF EXISTS `books_genres`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `books_genres` AS select `books`.`id` AS `id`,`books`.`title` AS `title`,`books`.`author_id` AS `author_id`,`books`.`price` AS `price`,`books`.`description` AS `description`,`books`.`photo_link` AS `photo_link`,`g`.`id` AS `genre_id`,`g`.`name` AS `name` from ((`books` join `genre_books` `gb` on((`gb`.`book_id` = `books`.`id`))) join `genres` `g` on((`gb`.`genre_id` = `g`.`id`))) */;
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

-- Dump completed on 2021-02-04 11:02:56
