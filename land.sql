-- MySQL dump 10.13  Distrib 8.0.30, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: land
-- ------------------------------------------------------
-- Server version	8.0.30

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `apply`
--

DROP TABLE IF EXISTS `apply`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `apply` (
  `LNO` int NOT NULL,
  `APPLICANT_UNO` int NOT NULL,
  `STATE` varchar(2) DEFAULT NULL,
  PRIMARY KEY (`LNO`,`APPLICANT_UNO`),
  KEY `UNO_FK1` (`APPLICANT_UNO`),
  CONSTRAINT `LNO_FK` FOREIGN KEY (`LNO`) REFERENCES `land` (`LNO`),
  CONSTRAINT `UNO_FK1` FOREIGN KEY (`APPLICANT_UNO`) REFERENCES `user` (`UNO`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `apply`
--

LOCK TABLES `apply` WRITE;
/*!40000 ALTER TABLE `apply` DISABLE KEYS */;
INSERT INTO `apply` VALUES (2,4,'通过'),(2,23500,'审核'),(3,4,'审核'),(3,23498,'审核'),(6,1,'审核'),(7,1,'审核'),(8,4,'审核'),(8,23498,'审核'),(9,4,'审核'),(9,23500,'审核'),(26,4,'审核');
/*!40000 ALTER TABLE `apply` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `land`
--

DROP TABLE IF EXISTS `land`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `land` (
  `LNO` int NOT NULL AUTO_INCREMENT,
  `UNO` int NOT NULL,
  `LTYPE` varchar(10) DEFAULT NULL,
  `TTYPE` varchar(10) NOT NULL,
  `LOCATION` varchar(10) NOT NULL,
  `PRICE` int NOT NULL,
  PRIMARY KEY (`LNO`),
  KEY `SNO_LAND` (`UNO`),
  CONSTRAINT `SNO_LAND` FOREIGN KEY (`UNO`) REFERENCES `user` (`UNO`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `land`
--

LOCK TABLES `land` WRITE;
/*!40000 ALTER TABLE `land` DISABLE KEYS */;
INSERT INTO `land` VALUES (2,1,'耕地','转让','河北省',100),(3,1,'耕地','转让','河北省',100),(6,1,'耕地','转让','山西省',100),(7,1,'住宅用地','租让','黑龙江省',923759832),(8,1,'耕地','转让','河北省',100),(9,1,'耕地','转让','河北省',100),(10,1,'耕地','转让','河北省',100),(11,1,'耕地','转让','河北省',100),(12,1,'耕地','转让','河北省',100),(13,1,'耕地','转让','河北省',100),(14,1,'耕地','转让','河北省',100),(15,1,'耕地','转让','河北省',100),(16,1,'耕地','转让','河北省',100),(17,1,'耕地','转让','河北省',100),(18,1,'耕地','转让','河北省',100),(19,1,'耕地','转让','河北省',100),(20,1,'耕地','转让','河北省',100),(21,1,'耕地','转让','河北省',100),(22,1,'耕地','转让','河北省',100),(23,1,'耕地','转让','河北省',100),(24,1,'耕地','转让','河北省',100),(25,1,'耕地','转让','河北省',100),(26,1,'耕地','转让','河北省',100),(27,1,'耕地','转让','河北省',100),(28,23496,'住宅用地','租让','黑龙江省',9999),(29,23496,'园地','入股','辽宁省',123),(30,23496,'养殖用地','入股','安徽省',4645);
/*!40000 ALTER TABLE `land` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `UNO` int NOT NULL AUTO_INCREMENT,
  `UNAME` varchar(10) DEFAULT NULL,
  `PASSWORD` int NOT NULL,
  PRIMARY KEY (`UNO`),
  UNIQUE KEY `UNAME` (`UNAME`)
) ENGINE=InnoDB AUTO_INCREMENT=23501 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'root',1234),(2,'TEST1',123),(3,'abcdefg',12345),(4,'test9',1234),(23496,'hhh',123),(23498,'test10',123),(23500,'test11',1234);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-12-24 18:19:41
