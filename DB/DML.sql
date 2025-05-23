-- MySQL dump 10.13  Distrib 8.0.42, for Win64 (x86_64)
--
-- Host: localhost    Database: db
-- ------------------------------------------------------
-- Server version	8.0.42

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
-- Dumping data for table `comments`
--

LOCK TABLES `comments` WRITE;
/*!40000 ALTER TABLE `comments` DISABLE KEYS */;
INSERT INTO `comments` VALUES (1,2,1,'有多好','2025-05-14 13:13:13'),(2,2,2,'是喔','2025-05-14 13:13:53'),(3,2,3,'加油','2025-05-14 13:14:04'),(4,3,1,'讚','2025-05-14 13:16:23'),(5,3,5,'網路世界','2025-05-14 13:16:33'),(6,3,4,'我也是','2025-05-14 13:16:42'),(7,1,4,'我是前輩','2025-05-14 13:17:17'),(8,1,6,'什麼?','2025-05-14 13:17:27'),(9,1,7,'阿勒勒~','2025-05-14 13:17:52');
/*!40000 ALTER TABLE `comments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `posts`
--

LOCK TABLES `posts` WRITE;
/*!40000 ALTER TABLE `posts` DISABLE KEYS */;
INSERT INTO `posts` VALUES (1,1,'天氣真好',NULL,'2025-05-14 13:09:49'),(2,1,'今天出門真好玩',NULL,'2025-05-14 13:10:25'),(3,1,'要去考試TT','/uploads/1747228282371_cry.png','2025-05-14 13:11:22'),(4,2,'第一次來!',NULL,'2025-05-14 13:12:51'),(5,2,'這裡是哪',NULL,'2025-05-14 13:13:01'),(6,3,'成功了!',NULL,'2025-05-14 13:15:06'),(7,3,'真相只有一個','/uploads/1747228552492_conan.jpg','2025-05-14 13:15:52');
/*!40000 ALTER TABLE `posts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'王曉明','wang@gmail.com','0911111111','$2a$10$6fpOvttHCPIeog.Z/idAuOHHC.eDjrIdcCTpZ6xGezN7wXrUGpzwS','/uploads/1747228163924_goodMorning.png','我是王曉明','2025-05-14 13:09:24'),(2,'林曉茹','lin@gmail.com','0922222222','$2a$10$neJtFxQqTZRp1hf3FcI68uqkI6iaQd0C68XBZ1HFQtMdg9DI/p/ee','/uploads/1747228347905_goodMorning.png','我是林曉茹','2025-05-14 13:12:28'),(3,'陳小俊','chen@gmail.com','0933333333','$2a$10$9opUg23Z2gx1aSn1neJ2b.s7NJ2NCIkcyOxb5w5cC8y0Sx3wFQLYa','/uploads/1747228479785_goodMorning.png','我是陳小俊','2025-05-14 13:14:40');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-05-14 21:55:36
