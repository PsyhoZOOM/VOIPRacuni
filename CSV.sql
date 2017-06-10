CREATE DATABASE  IF NOT EXISTS `CSV` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `CSV`;
-- MySQL dump 10.13  Distrib 5.7.17, for macos10.12 (x86_64)
--
-- Host: localhost    Database: CSV
-- ------------------------------------------------------
-- Server version	5.6.36

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `brojevi`
--

DROP TABLE IF EXISTS `brojevi`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `brojevi` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userID` int(11) DEFAULT NULL,
  `brTel` text,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `brojevi`
--

LOCK TABLES `brojevi` WRITE;
/*!40000 ALTER TABLE `brojevi` DISABLE KEYS */;
INSERT INTO `brojevi` VALUES (11,1,'131222'),(13,2,'2312'),(14,2,'2312'),(12,1,'131222'),(15,2,'2312'),(16,2,'2312'),(17,1,'22');
/*!40000 ALTER TABLE `brojevi` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `csv`
--

DROP TABLE IF EXISTS `csv`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `csv` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account` text COLLATE utf8_unicode_ci,
  `from` text COLLATE utf8_unicode_ci,
  `to` text COLLATE utf8_unicode_ci,
  `country` text COLLATE utf8_unicode_ci,
  `description` text COLLATE utf8_unicode_ci,
  `connectTime` text COLLATE utf8_unicode_ci,
  `chargedTimeMS` text COLLATE utf8_unicode_ci,
  `chargedTimeS` int(11) DEFAULT NULL,
  `chargedAmountRSD` double DEFAULT NULL,
  `serviceName` text COLLATE utf8_unicode_ci,
  `chargedQuantity` int(11) DEFAULT NULL,
  `serviceUnit` text COLLATE utf8_unicode_ci,
  `customerID` text COLLATE utf8_unicode_ci,
  `fileName` text COLLATE utf8_unicode_ci,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=7346 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `csv`
--

LOCK TABLES `csv` WRITE;
/*!40000 ALTER TABLE `csv` DISABLE KEYS */;
INSERT INTO `csv` VALUES (6295,'172.31.255.50','38112347792','38112430111','SERBIA','Proper','2017-04-03 08:56:28','2:06',126,2.1,'Voice Calls',126,'second','12968','2017-04-30-2017-04-01-customer12968'),(6298,'38112430001','38112430001','38112430111','','Calls between IP phones','2017-04-07 17:46:35','1:00',60,0,'Voice Calls',60,'second','13444','2017-04-30-2017-04-01-customer13444'),(6302,'38112430223','38112430223','38112326971','SERBIA','Srbija Fiksna','2017-04-08 10:18:04','2:00',120,2,'Voice Calls',120,'second','14600','2017-04-30-2017-04-01-customer14600'),(6417,'38112430000','38112430000','381621035100','SERBIA','Srbija Mobilna','2017-04-01 10:22:09','1:00',60,6.66667,'Voice Calls',60,'second','14704','2017-04-30-2017-04-01-customer14704'),(6430,'38112327920','38112327920','38112326604','SERBIA','Srbija Fiksna','2017-04-03 13:41:13','1:00',60,1,'Voice Calls',60,'second','14838','2017-04-30-2017-04-01-customer14838'),(6435,'38112430445','38112430445','38112430111','','Calls between IP phones','2017-04-07 10:04:22','1:00',60,0,'Voice Calls',60,'second','15477','2017-04-30-2017-04-01-customer15477'),(6442,'38112430101','38112430101','38112326876','SERBIA','Srbija Fiksna','2017-04-14 13:15:19','4:00',240,0,'Voice Calls',240,'second','15690','2017-04-30-2017-04-13-customer15690'),(6948,'172.31.255.50','38112326876','38112430101','SERBIA','Proper','2017-05-01 08:31:31','1:27',87,1.45,'Voice Calls',87,'second','12968','2017-05-31-2017-05-01-customer12968'),(6949,'38112430001','','Manual payment','','Balance adjustment - Manual Payment','2017-05-19 10:25:40','0:00',0,-7.67,'Payments',0,'','13444','2017-05-31-2017-05-01-customer13444'),(6957,'38112430222','38112430222','381648413003','SERBIA','Srbija Mobilna','2017-05-01 08:11:56','2:00',120,13.33334,'Voice Calls',120,'second','14600','2017-05-31-2017-05-01-customer14600'),(7138,'38112430111','38112430111','38112327971','SERBIA','Srbija Fiksna','2017-05-02 10:55:37','1:00',60,1,'Voice Calls',60,'second','14704','2017-05-31-2017-05-01-customer14704'),(7148,'38112327920','38112327920','38112326963','SERBIA','Srbija Fiksna','2017-05-03 20:12:42','12:00',720,0,'Voice Calls',720,'second','14838','2017-05-31-2017-05-01-customer14838'),(7160,'38112430444','38112430444','38112327971','SERBIA','Srbija Fiksna','2017-05-12 13:18:11','1:00',60,0,'Voice Calls',60,'second','15477','2017-05-31-2017-05-01-customer15477'),(7180,'38112430101','38112430101','38126316022','SERBIA','Srbija Fiksna','2017-05-03 08:42:10','4:00',240,0,'Voice Calls',240,'second','15690','2017-05-31-2017-05-01-customer15690'),(7199,'38112430588','38112430588','38112331926','SERBIA','Srbija Fiksna','2017-05-01 10:27:51','1:00',60,0,'Voice Calls',60,'second','15781','2017-05-31-2017-05-01-customer15781'),(7234,'38112430429','38112430429','38112430111','','Calls between IP phones','2017-05-02 12:07:19','1:00',60,0,'Voice Calls',60,'second','15892','2017-05-31-2017-05-02-customer15892'),(7274,'38112430365','38112430365','38112430111','','Calls between IP phones','2017-05-09 14:47:20','1:00',60,0,'Voice Calls',60,'second','15968','2017-05-31-2017-05-04-customer15968'),(7290,'38112430113','38112430113','381631862452','SERBIA','Srbija Mobilna','2017-05-17 12:06:16','1:00',60,6.66667,'Voice Calls',60,'second','16024','2017-05-31-2017-05-08-customer16024'),(7345,'38112430333','38112430333','38112430111','','Calls between IP phones','2017-05-11 14:19:49','1:00',60,0,'Voice Calls',60,'second','16128','2017-05-31-2017-05-11-customer16128'),(6459,'38112430588','38112430588','38112430001','','Calls between IP phones','2017-04-25 16:06:54','1:00',60,0,'Voice Calls',60,'second','15781','2017-04-30-2017-04-24-customer15781');
/*!40000 ALTER TABLE `csv` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `korisnici`
--

DROP TABLE IF EXISTS `korisnici`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `korisnici` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `imePrezime` text,
  `adresa` text,
  `mesto` text,
  `postbr` text,
  `brUgovora` text,
  `customerID` text,
  `pozivNaBroj` text,
  `brojTelefona` text,
  `paketID` int(11) DEFAULT NULL,
  `stampa` tinyint(4) DEFAULT NULL,
  `firma` tinyint(4) DEFAULT NULL,
  `mbr` varchar(45) DEFAULT NULL,
  `pib` varchar(45) DEFAULT NULL,
  `nazivFirme` varchar(45) DEFAULT NULL,
  `fullPayment` tinyint(4) NOT NULL DEFAULT '1',
  `datumPrikljucka` varchar(45) DEFAULT '1900-01-01',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `korisnici`
--

LOCK TABLES `korisnici` WRITE;
/*!40000 ALTER TABLE `korisnici` DISABLE KEYS */;
INSERT INTO `korisnici` VALUES (4,'Vladan Jović','Mileta Rakića 7','Petrovac','12300','430-333/2017','00000','430333','38112430333',1,1,0,'','','',1,'1900-01-01'),(5,'Zoran Marinković','Stamnica bb','Stamnica','12300','430-222/2017','00001','430222','38112430222',1,1,NULL,NULL,NULL,NULL,1,'1900-01-01'),(6,'Zoran Marinković','Stamnica bb','Stamnica','12300','430-223/2017','00002','430223','38112430223',1,1,NULL,NULL,NULL,NULL,1,'1900-01-01'),(7,'Zoran Marinković','Stamnica bb','Stamnica','12300','430-224/2017','00003','430224','38112430224',1,1,NULL,NULL,NULL,NULL,1,'1900-01-01'),(8,'Zoran Marinković','Bate Bulića bb','Petrovac','12300','430-000/2017','00004','430000','38112430000',1,1,0,NULL,NULL,NULL,1,'2017-02-02'),(9,'Marko Maljković','Bate Bulića 13/3','Petrovac','12300','430-001/2017','00005','430001','38112430001',1,1,0,NULL,NULL,NULL,1,'2016-12-13'),(10,'Dušan Mihajlović','8. Marta C/23','Petrovac','12300','430-101/2017','00006','430101','38112430101',1,1,0,NULL,NULL,NULL,1,'2017-04-20'),(11,'Zoran Marinković','Bate Bulića bb','Petrovac','12300','430-111/2017','00007','430111','38112430111',1,1,NULL,NULL,NULL,NULL,1,'1900-01-01'),(12,'Siniša Miloradović','Krvije bb','Krvije','12300','430-113/2017','00008','430113','38112430113',1,1,NULL,NULL,NULL,NULL,1,'1900-01-01'),(13,'UR Kuglana Bowling Dobrica Dragić PR ','Drageta Živkovića 1','Petrovac','12300','430-123/2017','00009','430123','38112430123',1,1,1,'64569406','109992835','UR Kuglana Bowling Dobrica Dragić PR 2',1,'1900-01-01'),(14,'Ljubiša Pantelić','Stamnica bb','Stamnica','12300','430-365/2017','00010','430365','38112430365',1,1,NULL,NULL,NULL,NULL,1,'1900-01-01'),(15,'Mira Trilović','Srpskih vladara 390/2','Petrovac','12300','430-429/2017','00011','430429','38112430429',1,1,NULL,NULL,NULL,NULL,1,'1900-01-01'),(16,'BRANIČEVO MEDIJA CENTAR DOO','Bate Bulića bb','Petrovac','12300','430-444/2017','00012','430444','38112430444',1,1,1,'20293446','105022284','BRANIČEVO MEDIJA CENTAR DOO',1,'1900-01-01'),(17,'BRANIČEVO MEDIJA CENTAR DOO','Bate Bulića bb','Petrovac','12300','430-445/2017','00013','430445','38112430445',1,1,1,'20293446','105022284','BRANIČEVO MEDIJA CENTAR DOO',1,'1900-01-01'),(18,'Miletić Mihajlović','8.marta bb','Petrovac','12300','430-588/2017','00014','430588','38112430588',1,1,0,NULL,NULL,NULL,1,'1900-01-01'),(19,'Mira Trailović','Vere Gvozdenović bb','Petrovac','12300','430-846/2017','00015','430846','38112430846',1,1,NULL,NULL,NULL,NULL,1,'1900-01-01'),(20,'Goran Ristić','Slobodana Braunovića 3/1','Petrovac','12300','327-920/2017','00016','327920','38112327920',1,1,NULL,NULL,NULL,NULL,1,'1900-01-01'),(22,'Perica Peric','perica adresa','petrovac','12300','23','23','23','38112430111',1,1,0,'','','',1,'1900-01-01'),(23,'Mika Mikic','R Defroap','Petrovac','12300','321321321','54654654','44566','38112430123',1,1,0,'','','',1,'1900-01-01');
/*!40000 ALTER TABLE `korisnici` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `paketi`
--

DROP TABLE IF EXISTS `paketi`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `paketi` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `naziv` text,
  `pretplata` double DEFAULT NULL,
  `PDV` double DEFAULT NULL,
  `besplatniMinutiFiksna` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=54 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `paketi`
--

LOCK TABLES `paketi` WRITE;
/*!40000 ALTER TABLE `paketi` DISABLE KEYS */;
INSERT INTO `paketi` VALUES (1,'Telefon-Gratis60',222,1,60),(51,'Telefon-Gratis30',200,20,30);
/*!40000 ALTER TABLE `paketi` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `racuniaa`
--

DROP TABLE IF EXISTS `racuniaa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `racuniaa` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `zaMesec` text COLLATE utf8_unicode_ci,
  `userID` int(11) DEFAULT NULL,
  `zaUplatu` double DEFAULT NULL,
  `uplaceno` double DEFAULT NULL,
  `vremeUplate` text COLLATE utf8_unicode_ci,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `racuniaa`
--

LOCK TABLES `racuniaa` WRITE;
/*!40000 ALTER TABLE `racuniaa` DISABLE KEYS */;
/*!40000 ALTER TABLE `racuniaa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `uplate`
--

DROP TABLE IF EXISTS `uplate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `uplate` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ime` text COLLATE utf8_unicode_ci,
  `brojTel` text COLLATE utf8_unicode_ci,
  `zaUplatu` double DEFAULT '0',
  `uplaceno` double DEFAULT '0',
  `zaMesec` text COLLATE utf8_unicode_ci,
  `datumZaduzenja` text COLLATE utf8_unicode_ci,
  `uplatio` text COLLATE utf8_unicode_ci,
  `userID` int(11) DEFAULT NULL,
  `modelPoziv` text COLLATE utf8_unicode_ci,
  `datumUplate` text COLLATE utf8_unicode_ci,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=34 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `uplate`
--

LOCK TABLES `uplate` WRITE;
/*!40000 ALTER TABLE `uplate` DISABLE KEYS */;
INSERT INTO `uplate` VALUES (1,'Dušan Mihajlović','38112430101',200,0,'4',NULL,NULL,10,'430101','2017-05-17'),(2,'UR Kuglana Bowling Dobrica Dragić PR ','38112430123',133,133,'4',NULL,NULL,13,'430123','2017-05-23'),(3,'BRANIČEVO MEDIJA CENTAR DOO','38112430444',418.75,0,'4',NULL,NULL,16,'430444','2017-05-17'),(4,'BRANIČEVO MEDIJA CENTAR DOO','38112430445',400,0,'4',NULL,NULL,17,'430445','2017-05-17'),(5,'Miletić Mihajlović','38112430588',139.25,0,'4',NULL,NULL,18,'430588','2017-05-17'),(6,'Goran Ristić','38112327920',406.25,0,'4',NULL,NULL,20,'327920','2017-05-17'),(7,'Vladan Jović','38112430333',400,0,'3','2017-03-01 00:00:00',NULL,4,'430333',NULL),(8,'Zoran Marinković','38112430222',400,0,'3','2017-03-01 00:00:00',NULL,5,'430222',NULL),(9,'Zoran Marinković','38112430223',400,0,'3','2017-03-01 00:00:00',NULL,6,'430223',NULL),(10,'Zoran Marinković','38112430224',400,0,'3','2017-03-01 00:00:00',NULL,7,'430224',NULL),(11,'Zoran Marinković','38112430000',400,0,'3','2017-03-01 00:00:00',NULL,8,'430000',NULL),(12,'Marko Maljković','38112430001',400,0,'3','2017-03-01 00:00:00',NULL,9,'430001',NULL),(13,'Dušan Mihajlović','38112430101',200,0,'3','2017-03-01 00:00:00',NULL,10,'430101',NULL),(14,'Zoran Marinković','38112430111',400,0,'3','2017-03-01 00:00:00',NULL,11,'430111',NULL),(15,'Siniša Miloradović','38112430113',400,0,'3','2017-03-01 00:00:00',NULL,12,'430113',NULL),(16,'UR Kuglana Bowling Dobrica Dragić PR ','38112430123',133,0,'3','2017-03-01 00:00:00',NULL,13,'430123',NULL),(17,'Ljubiša Pantelić','38112430365',400,0,'3','2017-03-01 00:00:00',NULL,14,'430365',NULL),(18,'Mira Trilović','38112430429',400,0,'3','2017-03-01 00:00:00',NULL,15,'430429',NULL),(19,'BRANIČEVO MEDIJA CENTAR DOO','38112430444',400,0,'3','2017-03-01 00:00:00',NULL,16,'430444',NULL),(20,'BRANIČEVO MEDIJA CENTAR DOO','38112430445',400,0,'3','2017-03-01 00:00:00',NULL,17,'430445',NULL),(21,'Miletić Mihajlović','38112430588',133,0,'3','2017-03-01 00:00:00',NULL,18,'430588',NULL),(22,'Mira Trailović','38112430846',400,0,'3','2017-03-01 00:00:00',NULL,19,'430846',NULL),(23,'Goran Ristić','38112327920',400,0,'3','2017-03-01 00:00:00',NULL,20,'327920',NULL),(33,'Mika Mikic','38112430123',400,0,'4','2017-04-01 00:00:00',NULL,23,'44566',NULL);
/*!40000 ALTER TABLE `uplate` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `zaduzenja`
--

DROP TABLE IF EXISTS `zaduzenja`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `zaduzenja` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `datumZaduzenja` varchar(45) DEFAULT NULL,
  `userID` int(11) DEFAULT NULL,
  `zaMesec` varchar(45) DEFAULT NULL,
  `zaUplatu` double DEFAULT NULL,
  `uplaceno` double DEFAULT '0',
  `komentar` text,
  `destination` varchar(45) DEFAULT NULL,
  `zoneID` int(11) DEFAULT NULL,
  `zoneCeneID` int(11) DEFAULT NULL,
  `minutaZaNaplatu` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=161 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `zaduzenja`
--

LOCK TABLES `zaduzenja` WRITE;
/*!40000 ALTER TABLE `zaduzenja` DISABLE KEYS */;
INSERT INTO `zaduzenja` VALUES (105,'2017-06-09',4,'2017-04',222,0,'Paket',NULL,NULL,NULL,NULL),(106,'2017-06-09',5,'2017-04',222,0,'Paket',NULL,NULL,NULL,NULL),(107,'2017-06-09',6,'2017-04',1.92,0,'Saobracaj','Srbija Fiksna',347,1,2),(108,'2017-06-09',6,'2017-04',222,0,'Paket',NULL,NULL,NULL,NULL),(109,'2017-06-09',7,'2017-04',222,0,'Paket',NULL,NULL,NULL,NULL),(110,'2017-06-09',8,'2017-04',6.25,0,'Saobracaj','Srbija Mobilna',348,2,1),(111,'2017-06-09',8,'2017-04',222,0,'Paket',NULL,NULL,NULL,NULL),(112,'2017-06-09',9,'2017-04',0,0,'Saobracaj','Calls between IP phones',457,12,1),(113,'2017-06-09',9,'2017-04',222,0,'Paket',NULL,NULL,NULL,NULL),(114,'2017-06-09',10,'2017-04',3.84,0,'Saobracaj','Srbija Fiksna',347,1,4),(115,'2017-06-09',10,'2017-04',74,0,'Paket',NULL,NULL,NULL,NULL),(116,'2017-06-09',11,'2017-04',222,0,'Paket',NULL,NULL,NULL,NULL),(117,'2017-06-09',12,'2017-04',222,0,'Paket',NULL,NULL,NULL,NULL),(118,'2017-06-09',13,'2017-04',222,0,'Paket',NULL,NULL,NULL,NULL),(119,'2017-06-09',14,'2017-04',222,0,'Paket',NULL,NULL,NULL,NULL),(120,'2017-06-09',15,'2017-04',222,0,'Paket',NULL,NULL,NULL,NULL),(121,'2017-06-09',16,'2017-04',222,0,'Paket',NULL,NULL,NULL,NULL),(122,'2017-06-09',17,'2017-04',0,0,'Saobracaj','Calls between IP phones',457,12,1),(123,'2017-06-09',17,'2017-04',222,0,'Paket',NULL,NULL,NULL,NULL),(124,'2017-06-09',18,'2017-04',0,0,'Saobracaj','Calls between IP phones',457,12,1),(125,'2017-06-09',18,'2017-04',222,0,'Paket',NULL,NULL,NULL,NULL),(126,'2017-06-09',19,'2017-04',222,0,'Paket',NULL,NULL,NULL,NULL),(127,'2017-06-09',20,'2017-04',0.96,0,'Saobracaj','Srbija Fiksna',347,1,1),(128,'2017-06-09',20,'2017-04',222,0,'Paket',NULL,NULL,NULL,NULL),(129,'2017-06-09',22,'2017-04',222,0,'Paket',NULL,NULL,NULL,NULL),(130,'2017-06-09',23,'2017-04',222,0,'Paket',NULL,NULL,NULL,NULL),(131,'2017-06-09',4,'2017-05',0,0,'Saobracaj','Calls between IP phones',457,12,1),(132,'2017-06-09',4,'2017-05',222,0,'Paket',NULL,NULL,NULL,NULL),(133,'2017-06-09',5,'2017-05',12.5,0,'Saobracaj','Srbija Mobilna',348,2,2),(134,'2017-06-09',5,'2017-05',222,0,'Paket',NULL,NULL,NULL,NULL),(135,'2017-06-09',6,'2017-05',222,0,'Paket',NULL,NULL,NULL,NULL),(136,'2017-06-09',7,'2017-05',222,0,'Paket',NULL,NULL,NULL,NULL),(137,'2017-06-09',8,'2017-05',222,0,'Paket',NULL,NULL,NULL,NULL),(138,'2017-06-09',9,'2017-05',222,0,'Paket',NULL,NULL,NULL,NULL),(139,'2017-06-09',10,'2017-05',3.84,0,'Saobracaj','Srbija Fiksna',347,1,4),(140,'2017-06-09',10,'2017-05',222,0,'Paket',NULL,NULL,NULL,NULL),(141,'2017-06-09',11,'2017-05',0.96,0,'Saobracaj','Srbija Fiksna',347,1,1),(142,'2017-06-09',11,'2017-05',222,0,'Paket',NULL,NULL,NULL,NULL),(143,'2017-06-09',12,'2017-05',6.25,0,'Saobracaj','Srbija Mobilna',348,2,1),(144,'2017-06-09',12,'2017-05',222,0,'Paket',NULL,NULL,NULL,NULL),(145,'2017-06-09',13,'2017-05',222,0,'Paket',NULL,NULL,NULL,NULL),(146,'2017-06-09',14,'2017-05',0,0,'Saobracaj','Calls between IP phones',457,12,1),(147,'2017-06-09',14,'2017-05',222,0,'Paket',NULL,NULL,NULL,NULL),(148,'2017-06-09',15,'2017-05',0,0,'Saobracaj','Calls between IP phones',457,12,1),(149,'2017-06-09',15,'2017-05',222,0,'Paket',NULL,NULL,NULL,NULL),(150,'2017-06-09',16,'2017-05',0.96,0,'Saobracaj','Srbija Fiksna',347,1,1),(151,'2017-06-09',16,'2017-05',222,0,'Paket',NULL,NULL,NULL,NULL),(152,'2017-06-09',17,'2017-05',222,0,'Paket',NULL,NULL,NULL,NULL),(153,'2017-06-09',18,'2017-05',0.96,0,'Saobracaj','Srbija Fiksna',347,1,1),(154,'2017-06-09',18,'2017-05',222,0,'Paket',NULL,NULL,NULL,NULL),(155,'2017-06-09',19,'2017-05',222,0,'Paket',NULL,NULL,NULL,NULL),(156,'2017-06-09',20,'2017-05',11.52,0,'Saobracaj','Srbija Fiksna',347,1,12),(157,'2017-06-09',20,'2017-05',222,0,'Paket',NULL,NULL,NULL,NULL),(158,'2017-06-09',22,'2017-05',0.96,0,'Saobracaj','Srbija Fiksna',347,1,1),(159,'2017-06-09',22,'2017-05',222,0,'Paket',NULL,NULL,NULL,NULL),(160,'2017-06-09',23,'2017-05',222,0,'Paket',NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `zaduzenja` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `zone`
--

DROP TABLE IF EXISTS `zone`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `zone` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `naziv` text,
  `opis` text,
  `zona` text,
  `zonaID` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=458 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `zone`
--

LOCK TABLES `zone` WRITE;
/*!40000 ALTER TABLE `zone` DISABLE KEYS */;
INSERT INTO `zone` VALUES (1,'AFGHANISTAN','Afghanistan','Inostranstvo zona 4',6),(2,'ALBANIA','Albania','Inostranstvo zona 3',5),(3,'ALBANIA','Albania-MobileAMC','Inostranstvo zona 5',7),(4,'ALBANIA','Albania-MobileEagle','Inostranstvo zona 5',7),(5,'ALBANIA','Albania-MobilePlus','Inostranstvo zona 5',7),(6,'ALBANIA','Albania-MobileVodafone','Inostranstvo zona 5',7),(7,'ALGERIA','Algeria','Inostranstvo zona 5',7),(8,'AMERICAN SAMOA','AmericanSamoa','Inostranstvo zona 3',5),(9,'ANDORRA','Andorra','Inostranstvo zona 3',5),(10,'ANGOLA','Angola','Inostranstvo zona 4',6),(11,'ANGUILLA','Anguilla','Inostranstvo zona 4',6),(12,'ANTARCTICA','AustralianExternalTerritories','Inostranstvo zona 7',9),(13,'ANTIGUA AND BARBUDA','AntiguaandBarbuda','Inostranstvo zona 4',6),(14,'ARGENTINA','Argentina','Inostranstvo zona 2',4),(15,'ARMENIA','Armenia','Inostranstvo zona 4',6),(16,'ARUBA','Aruba','Inostranstvo zona 4',6),(17,'AUSTRALIA','Australia','Inostranstvo zona 1',3),(18,'AUSTRALIA','Australia-MobileOthers','Inostranstvo zona 2',4),(19,'AUSTRALIA','Australia-MobileTelstra','Inostranstvo zona 2',4),(20,'AUSTRALIA','Australia-SatelliteOptus','Inostranstvo zona 7',9),(21,'AUSTRALIA','Australia-SatelliteTelstra','Inostranstvo zona 7',9),(22,'AUSTRIA','Austria','Inostranstvo zona 2',4),(23,'AUSTRIA','Austria-MobileHutchinson3G','Inostranstvo zona 5',7),(24,'AUSTRIA','Austria-MobileMobilkomA1','Inostranstvo zona 5',7),(25,'AUSTRIA','Austria-MobileMTEL','Inostranstvo zona 5',7),(26,'AUSTRIA','Austria-MobileOthers','Inostranstvo zona 5',7),(27,'AUSTRIA','Austria-MobilePremium','Inostranstvo zona 5',7),(28,'AUSTRIA','Austria-MobileTMobile','Inostranstvo zona 5',7),(29,'AUSTRIA','Austria-Vienna','Inostranstvo zona 2',4),(30,'AUSTRIA','Mobile','Inostranstvo zona 5',7),(31,'AZERBAIJAN','Azerbaijan','Inostranstvo zona 5',7),(32,'BAHAMAS','Bahamas','Inostranstvo zona 3',5),(33,'BAHRAIN','Bahrain','Inostranstvo zona 3',5),(34,'BANGLADESH','Bangladesh','Inostranstvo zona 2',4),(35,'BARBADOS','Barbados','Inostranstvo zona 5',7),(36,'BELARUS','Belarus','Inostranstvo zona 5',7),(37,'BELGIUM','Belgium','Inostranstvo zona 2',4),(38,'BELGIUM','Belgium-MobileBase','Inostranstvo zona 5',7),(39,'BELGIUM','Belgium-MobileMobistar','Inostranstvo zona 5',7),(40,'BELGIUM','Belgium-MobileOthers','Inostranstvo zona 5',7),(41,'BELGIUM','Belgium-MobileProximus','Inostranstvo zona 5',7),(42,'BELGIUM','Belgium-MobileTelenet','Inostranstvo zona 5',7),(43,'BELIZE','Belize','Inostranstvo zona 4',6),(44,'BENIN','Benin','Inostranstvo zona 5',7),(45,'BERMUDA','Bermuda','Inostranstvo zona 3',5),(46,'BHUTAN','Bhutan','Inostranstvo zona 3',5),(47,'BOLIVIA','Bolivia','Inostranstvo zona 4',6),(48,'BOSNIA AND HERZEGOVINA','Bosnia&Herzegovina-AlternativeNetworks','Inostranstvo zona 3',5),(49,'BOSNIA AND HERZEGOVINA','Bosnia&Herzegovina-BHTelekom','Inostranstvo zona 3',5),(50,'BOSNIA AND HERZEGOVINA','Bosnia&Herzegovina-HTMostar','Inostranstvo zona 3',5),(51,'BOSNIA AND HERZEGOVINA','Bosnia&Herzegovina-MobileBHTelekom','Inostranstvo zona 5',7),(52,'BOSNIA AND HERZEGOVINA','Bosnia&Herzegovina-MobileHTMostar','Inostranstvo zona 5',7),(53,'BOSNIA AND HERZEGOVINA','Bosnia&Herzegovina-MobileMtel','Inostranstvo zona 5',7),(54,'BOSNIA AND HERZEGOVINA','Bosnia&Herzegovina-Mtel','Inostranstvo zona 3',5),(55,'BOTSWANA','Botswana','Inostranstvo zona 4',6),(56,'BRAZIL','Brasil','Inostranstvo zona 3',5),(57,'BRUNEI DARUSSALAM','Brunei','Inostranstvo zona 3',5),(58,'BULGARIA','Bulgaria-Alternative','Inostranstvo zona 2',4),(59,'BULGARIA','Bulgaria','Inostranstvo zona 2',4),(60,'BULGARIA','Bulgaria-MobileGlobul','Inostranstvo zona 5',7),(61,'BULGARIA','Bulgaria-MobileMobiltel','Inostranstvo zona 5',7),(62,'BULGARIA','Bulgaria-MobileVIVACOM','Inostranstvo zona 5',7),(63,'BULGARIA','Bulgaria-Wimax','Inostranstvo zona 2',4),(64,'BURKINA FASO','BurkinaFaso','Inostranstvo zona 5',7),(65,'BURUNDI','Burundi','Inostranstvo zona 6',8),(66,'CAMBODIA','Cambodia','Inostranstvo zona 3',5),(67,'CAMEROON','Cameroon','Inostranstvo zona 5',7),(68,'CANADA','Alberta','Inostranstvo zona 1',3),(69,'CANADA','Canada','Inostranstvo zona 1',3),(457,'U mrezi YuVideo','Calls between IP phones','Saobraćaj u mreži YuVideo',12),(71,'CAPE VERDE','CapeVerde','Inostranstvo zona 5',7),(72,'CAYMAN ISLANDS','CaymanIslands','Inostranstvo zona 4',6),(73,'CENTRAL AFRICAN REPUBLIC','CentralAfricanRepublic','Inostranstvo zona 6',8),(74,'CHAD','Chad','Inostranstvo zona 6',8),(75,'CHILE','Chile','Inostranstvo zona 2',4),(76,'CHINA','China','Inostranstvo zona 1',3),(77,'COLOMBIA','Colombia','Inostranstvo zona 2',4),(78,'COMOROS','Comoros','Inostranstvo zona 7',9),(79,'CONGO, Democratic Republic of (was Zaire)','Congo','Inostranstvo zona 5',7),(80,'CONGO, Republic of','Congo','Inostranstvo zona 6',8),(81,'COOK ISLANDS','CookIslands','Inostranstvo zona 7',9),(82,'COSTA RICA','CostaRica','Inostranstvo zona 3',5),(83,'COTE D\'IVOIRE','IvoryCoast','Inostranstvo zona 6',8),(84,'CROATIA','Croatia','Inostranstvo zona 3',5),(85,'CROATIA','Croatia-MobileTele2','Inostranstvo zona 5',7),(86,'CROATIA','Croatia-MobileT-Mobile','Inostranstvo zona 5',7),(87,'CROATIA','Croatia-MobileVIPnet','Inostranstvo zona 5',7),(88,'CUBA','Cuba','Inostranstvo zona 6',8),(89,'CYPRUS','Cyprus-Alternative','Inostranstvo zona 1',3),(90,'CYPRUS','Cyprus','Inostranstvo zona 1',3),(91,'CYPRUS','Cyprus-MobileCytamobile-Vodafone','Inostranstvo zona 3',5),(92,'CYPRUS','Cyprus-MobileLemontel','Inostranstvo zona 3',5),(93,'CYPRUS','Cyprus-MobileMTN','Inostranstvo zona 3',5),(94,'CYPRUS','Cyprus-MobilePrimetel','Inostranstvo zona 3',5),(95,'CYPRUS','Cyprus-SpecialServices','Inostranstvo zona 3',5),(96,'CYPRUS','Cyprus-VoiceMail','Inostranstvo zona 5',7),(97,'CZECH REPUBLIC','CzechRepublic','Inostranstvo zona 1',3),(98,'CZECH REPUBLIC','CzechRepublic-MobileMobilkom','Inostranstvo zona 2',4),(99,'CZECH REPUBLIC','CzechRepublic-MobileO2','Inostranstvo zona 2',4),(100,'CZECH REPUBLIC','CzechRepublic-MobileT-Mobile','Inostranstvo zona 2',4),(101,'CZECH REPUBLIC','CzechRepublic-MobileVodafone','Inostranstvo zona 2',4),(102,'CZECH REPUBLIC','CzechRepublic-SpecialServices','Inostranstvo zona 1',3),(103,'DENMARK','Denmark','Inostranstvo zona 1',3),(104,'DENMARK','Denmark-MobileBarablu','Inostranstvo zona 2',4),(105,'DENMARK','Denmark-MobileHi3G','Inostranstvo zona 2',4),(106,'DENMARK','Denmark-MobileLycamobile','Inostranstvo zona 2',4),(107,'DENMARK','Denmark-MobileOthers','Inostranstvo zona 2',4),(108,'DENMARK','Denmark-MobileTDC','Inostranstvo zona 2',4),(109,'DENMARK','Denmark-MobileTelenor','Inostranstvo zona 2',4),(110,'DENMARK','Denmark-MobileTelia','Inostranstvo zona 2',4),(111,'DJIBOUTI','Djibouti','Inostranstvo zona 5',7),(112,'DOMINICA','Dominica','Inostranstvo zona 4',6),(113,'DOMINICAN REPUBLIC','DominicanRepublic','Inostranstvo zona 3',5),(114,'ECUADOR','Ecuador','Inostranstvo zona 4',6),(115,'EGYPT','Egypt','Inostranstvo zona 3',5),(116,'EL SALVADOR','ElSalvador','Inostranstvo zona 4',6),(117,'EQUATORIAL GUINEA','EquatorialGuinea','Inostranstvo zona 6',8),(118,'ERITREA','Eritrea','Inostranstvo zona 5',7),(119,'ESTONIA','Estonia','Inostranstvo zona 5',7),(120,'ETHIOPIA','Ethiopia','Inostranstvo zona 4',6),(121,'FALKLAND ISLANDS','FalklandIslands','Inostranstvo zona 7',9),(122,'FAROE ISLANDS','FaroeIslands','Inostranstvo zona 3',5),(123,'FIJI','Fiji','Inostranstvo zona 5',7),(124,'FINLAND','Finland','Inostranstvo zona 2',4),(125,'FINLAND','Finland-Mobile','Inostranstvo zona 2',4),(126,'FINLAND','Finland-NWPCN','Inostranstvo zona 2',4),(127,'FRANCE','France','Inostranstvo zona 1',3),(128,'FRANCE','France-MobileBouygues','Inostranstvo zona 5',7),(129,'FRANCE','France-MobileFREE','Inostranstvo zona 5',7),(130,'FRANCE','France-MobileGlobalstar','Inostranstvo zona 5',7),(131,'FRANCE','France-MobileOrange','Inostranstvo zona 5',7),(132,'FRANCE','France-MobileOthers','Inostranstvo zona 5',7),(133,'FRANCE','France-MobileSFR','Inostranstvo zona 5',7),(134,'FRANCE','France-VAS','Inostranstvo zona 5',7),(135,'FRENCH GUIANA','FrenchGuiana','Inostranstvo zona 2',4),(136,'FRENCH GUIANA','FrenchGuiana-Mobile','Inostranstvo zona 3',5),(137,'FRENCH POLYNESIA','FrenchPolynesia','Inostranstvo zona 5',7),(138,'GABON','Gabon','Inostranstvo zona 5',7),(139,'GAMBIA','Gambia','Inostranstvo zona 6',8),(140,'GEORGIA','Georgia','Inostranstvo zona 4',6),(141,'GERMANY','Germany','Inostranstvo zona 1',3),(142,'GERMANY','Germany-MobileE-Plus','Inostranstvo zona 4',6),(143,'GERMANY','Germany-MobileO2','Inostranstvo zona 4',6),(144,'GERMANY','Germany-MobilePersonal','Inostranstvo zona 4',6),(145,'GERMANY','Germany-MobileT-Mobile','Inostranstvo zona 4',6),(146,'GERMANY','Germany-MobileVodafone','Inostranstvo zona 4',6),(147,'GHANA','Ghana','Inostranstvo zona 5',7),(148,'GIBRALTAR','Gibraltar','Inostranstvo zona 4',6),(149,'GREECE','Greece','Inostranstvo zona 1',3),(150,'GREECE','Greece-MobileCosmote','Inostranstvo zona 2',4),(151,'GREECE','Greece-MobileCYTAMVNO','Inostranstvo zona 2',4),(152,'GREECE','Greece-MobileVodafone','Inostranstvo zona 2',4),(153,'GREECE','Greece-MobileWind','Inostranstvo zona 2',4),(154,'GREENLAND','Greenland','Inostranstvo zona 6',8),(155,'GRENADA','Grenada','Inostranstvo zona 4',6),(156,'GUADELOUPE','Guadeloupe','Inostranstvo zona 3',5),(157,'GUADELOUPE','Guadeloupe-Mobile','Inostranstvo zona 3',5),(158,'GUAM','Guam','Inostranstvo zona 3',5),(159,'GUATEMALA','Guatemala','Inostranstvo zona 3',5),(160,'GUINEA-BISSAU','GuineaBissau','Inostranstvo zona 6',8),(161,'GUINEA','Guinea','Inostranstvo zona 6',8),(162,'GUYANA','Guyana','Inostranstvo zona 4',6),(163,'HAITI','Haiti','Inostranstvo zona 5',7),(164,'HONDURAS','Honduras','Inostranstvo zona 3',5),(165,'HONG KONG','HongKong','Inostranstvo zona 1',3),(166,'HUNGARY','Hungary-Budapest','Inostranstvo zona 2',4),(167,'HUNGARY','Hungary','Inostranstvo zona 1',3),(168,'HUNGARY','Hungary-MobileTelenor','Inostranstvo zona 2',4),(169,'HUNGARY','Hungary-MobileTesco','Inostranstvo zona 2',4),(170,'HUNGARY','Hungary-MobileT-Mobile','Inostranstvo zona 2',4),(171,'HUNGARY','Hungary-MobileVodafone','Inostranstvo zona 2',4),(172,'ICELAND','Iceland','Inostranstvo zona 1',3),(173,'INDIA','India','Inostranstvo zona 1',3),(174,'INDONESIA','Indonesia','Inostranstvo zona 2',4),(175,'IRAN','Iran','Inostranstvo zona 3',5),(176,'IRAQ','Iraq','Inostranstvo zona 4',6),(177,'IRELAND','Ireland','Inostranstvo zona 1',3),(178,'IRELAND','Ireland-Mobile','Inostranstvo zona 2',4),(179,'ISRAEL','Israel','Inostranstvo zona 1',3),(180,'ITALY','Italy','Inostranstvo zona 1',3),(181,'ITALY','Italy-MobileBTItalia','Inostranstvo zona 5',7),(182,'ITALY','Italy-MobileDIGI','Inostranstvo zona 5',7),(183,'ITALY','Italy-MobileElsacom','Inostranstvo zona 5',7),(184,'ITALY','Italy-MobileH3G','Inostranstvo zona 5',7),(185,'ITALY','Italy-MobileIntermatica','Inostranstvo zona 5',7),(186,'ITALY','Italy-MobileLycatel','Inostranstvo zona 5',7),(187,'ITALY','Italy-MobileNoverca','Inostranstvo zona 5',7),(188,'ITALY','Italy-MobilePosteMobile','Inostranstvo zona 5',7),(189,'ITALY','Italy-MobileRFI','Inostranstvo zona 5',7),(190,'ITALY','Italy-MobileTIM','Inostranstvo zona 5',7),(191,'ITALY','Italy-MobileVodafone','Inostranstvo zona 5',7),(192,'ITALY','Italy-MobileWind','Inostranstvo zona 5',7),(193,'JAMAICA','Jamaica','Inostranstvo zona 4',6),(194,'JAPAN','Japan','Inostranstvo zona 2',4),(195,'JORDAN','Jordan','Inostranstvo zona 3',5),(196,'KENYA','Kenya','Inostranstvo zona 5',7),(197,'KIRIBATI','Kiribati','Inostranstvo zona 7',9),(198,'KOREA, NORTH','Korea','Inostranstvo zona 6',8),(199,'KOREA, SOUTH','Korea(South)','Inostranstvo zona 2',4),(200,'KUWAIT','Kuwait','Inostranstvo zona 3',5),(201,'KYRGYZSTAN','Kyrgyzstan','Inostranstvo zona 4',6),(202,'LAOS','Laos','Inostranstvo zona 3',5),(203,'LATVIA','Latvia','Inostranstvo zona 6',8),(204,'LEBANON','Lebanon','Inostranstvo zona 4',6),(205,'LESOTHO','Lesotho','Inostranstvo zona 5',7),(206,'LIBERIA','Liberia','Inostranstvo zona 5',7),(207,'LIBYA','Libya','Inostranstvo zona 5',7),(208,'LIECHTENSTEIN','Liechtenstain','Inostranstvo zona 5',7),(209,'LITHUANIA','Lithuania','Inostranstvo zona 6',8),(210,'LUXEMBOURG','Luxembourg','Inostranstvo zona 1',3),(211,'LUXEMBOURG','Luxembourg-Mobile','Inostranstvo zona 2',4),(212,'MACAU','Macao','Inostranstvo zona 2',4),(213,'MACEDONIA','Macedonia-Alternative','Inostranstvo zona 3',5),(214,'MACEDONIA','Macedonia','Inostranstvo zona 3',5),(215,'MACEDONIA','Macedonia-MobileAlbafone','Inostranstvo zona 5',7),(216,'MACEDONIA','Macedonia-MobileOne','Inostranstvo zona 5',7),(217,'MACEDONIA','Macedonia-MobileT-Mobile','Inostranstvo zona 5',7),(218,'MACEDONIA','Macedonia-MobileVIP','Inostranstvo zona 5',7),(219,'MADAGASCAR','Madagascar','Inostranstvo zona 6',8),(220,'MALAWI','Malawi','Inostranstvo zona 5',7),(221,'MALAYSIA','Malaysia','Inostranstvo zona 2',4),(222,'MALDIVES','Maldives','Inostranstvo zona 6',8),(223,'MALI','Mali','Inostranstvo zona 5',7),(224,'MALTA','Malta','Inostranstvo zona 2',4),(225,'MARSHALL ISLANDS','MarshallIslands','Inostranstvo zona 4',6),(226,'MARTINIQUE','Martinique','Inostranstvo zona 3',5),(227,'MARTINIQUE','Martinique-Mobile','Inostranstvo zona 3',5),(228,'MAURITANIA','Mauritania','Inostranstvo zona 6',8),(229,'MAURITIUS','Mauritius','Inostranstvo zona 3',5),(230,'MEXICO','Mexico','Inostranstvo zona 1',3),(231,'MICRONESIA','Micronesia','Inostranstvo zona 6',8),(232,'MOLDOVA','Moldova','Inostranstvo zona 5',7),(233,'MONACO','Monaco','Inostranstvo zona 2',4),(234,'MONACO','Monaco-MobileKFOR','Inostranstvo zona 5',7),(235,'MONACO','Monaco-MobileOthers','Inostranstvo zona 5',7),(236,'MONGOLIA','Mongolia','Inostranstvo zona 2',4),(237,'MONTENEGRO','Montenegro-FixedMTEL','Inostranstvo zona 3',5),(238,'MONTENEGRO','Montenegro','Inostranstvo zona 3',5),(239,'MONTENEGRO','Montenegro-MobileMTEL','Inostranstvo zona 5',7),(240,'MONTENEGRO','Montenegro-MobileTelenor','Inostranstvo zona 5',7),(241,'MONTENEGRO','Montenegro-MobileT-Mobile','Inostranstvo zona 5',7),(242,'MONTSERRAT','Montserrat','Inostranstvo zona 4',6),(243,'MOROCCO','Morocco','Inostranstvo zona 5',7),(244,'MOZAMBIQUE','Mozambique','Inostranstvo zona 5',7),(245,'MYANMAR','Myanmar','Inostranstvo zona 5',7),(246,'NAMIBIA','Namibia','Inostranstvo zona 3',5),(247,'NAURU','Nauru','Inostranstvo zona 7',9),(248,'NEPAL','Nepal','Inostranstvo zona 3',5),(249,'NETHERLANDS ANTILLES','NetherlandsAntilles','Inostranstvo zona 3',5),(250,'NETHERLANDS','Netherlands','Inostranstvo zona 1',3),(251,'NETHERLANDS','Netherlands-MobileKPN','Inostranstvo zona 2',4),(252,'NETHERLANDS','Netherlands-MobileLycamobile','Inostranstvo zona 2',4),(253,'NETHERLANDS','Netherlands-MobileOthers','Inostranstvo zona 2',4),(254,'NETHERLANDS','Netherlands-MobileTele2','Inostranstvo zona 2',4),(255,'NETHERLANDS','Netherlands-MobileTelfort','Inostranstvo zona 2',4),(256,'NETHERLANDS','Netherlands-MobileT-Mobile','Inostranstvo zona 2',4),(257,'NETHERLANDS','Netherlands-MobileVodafone','Inostranstvo zona 2',4),(258,'NEW CALEDONIA','NewCaledonia','Inostranstvo zona 4',6),(259,'NEW ZEALAND','NewZealand','Inostranstvo zona 1',3),(260,'NICARAGUA','Nicaragua','Inostranstvo zona 4',6),(261,'NIGERIA','Nigeria','Inostranstvo zona 3',5),(262,'NIGER','Niger','Inostranstvo zona 6',8),(263,'NIUE','Niue','Inostranstvo zona 7',9),(264,'NORWAY','Norway','Inostranstvo zona 1',3),(265,'NORWAY','Norway-MobileNetcom','Inostranstvo zona 2',4),(266,'NORWAY','Norway-MobileOthers','Inostranstvo zona 2',4),(267,'NORWAY','Norway-MobileTelenor','Inostranstvo zona 2',4),(268,'NORWAY','Norway-SpecialServices','Inostranstvo zona 1',3),(269,'Not Applicable','Ascension','Inostranstvo zona 7',9),(270,'Not Applicable','Inmarsat-AERO','Inostranstvo zona 7',9),(271,'Not Applicable','Inmarsat-A','Inostranstvo zona 7',9),(272,'Not Applicable','Inmarsat-BGANHSD','Inostranstvo zona 7',9),(273,'Not Applicable','Inmarsat-BGAN','Inostranstvo zona 7',9),(274,'Not Applicable','Inmarsat-BHSD','Inostranstvo zona 7',9),(275,'Not Applicable','Inmarsat-B','Inostranstvo zona 7',9),(276,'Not Applicable','Inmarsat-GFS','Inostranstvo zona 7',9),(277,'Not Applicable','Inmar-SAT','Inostranstvo zona 7',9),(278,'Not Applicable','Inmarsat-MGFS','Inostranstvo zona 7',9),(279,'Not Applicable','Inmarsat-M','Inostranstvo zona 7',9),(280,'Not Applicable','InternationalNetworks-A','Inostranstvo zona 7',9),(281,'Not Applicable','InternationalNetworks-B','Inostranstvo zona 7',9),(282,'Not Applicable','InternationalNetworks-Thuraya','Inostranstvo zona 7',9),(283,'Not Applicable','USA','Inostranstvo zona 1',3),(284,'OMAN','Oman','Inostranstvo zona 4',6),(285,'PAKISTAN','Pakistan','Inostranstvo zona 2',4),(286,'PALAU','Palau','Inostranstvo zona 4',6),(287,'PANAMA','Panama','Inostranstvo zona 3',5),(288,'PAPUA NEW GUINEA','PapuaNewGuinea','Inostranstvo zona 7',9),(289,'PARAGUAY','Paraguay','Inostranstvo zona 2',4),(290,'PERU','Peru','Inostranstvo zona 2',4),(291,'PHILIPPINES','Philippines','Inostranstvo zona 3',5),(292,'POLAND','Poland','Inostranstvo zona 1',3),(293,'POLAND','Poland-MobileAero2','Inostranstvo zona 4',6),(294,'POLAND','Poland-MobileOrange','Inostranstvo zona 4',6),(295,'POLAND','Poland-MobileP4','Inostranstvo zona 4',6),(296,'POLAND','Poland-MobilePlus','Inostranstvo zona 4',6),(297,'POLAND','Poland-MobileT-Mobile','Inostranstvo zona 4',6),(298,'PORTUGAL','Portugal','Inostranstvo zona 1',3),(299,'PORTUGAL','Portugal-Mobile','Inostranstvo zona 5',7),(300,'PUERTO RICO','PuertoRico','Inostranstvo zona 1',3),(301,'QATAR','Qatar','Inostranstvo zona 4',6),(302,'REUNION','Mayotte','Inostranstvo zona 3',5),(303,'REUNION','Mayotte-Mobile','Inostranstvo zona 5',7),(304,'REUNION','Reunion','Inostranstvo zona 3',5),(305,'REUNION','Reunion-Mobile','Inostranstvo zona 3',5),(306,'ROMANIA','Romania-Alternative','Inostranstvo zona 1',3),(307,'ROMANIA','Romania-Bucarest','Inostranstvo zona 1',3),(308,'ROMANIA','Romania-DirectoryAssistance','Inostranstvo zona 1',3),(309,'ROMANIA','Romania','Inostranstvo zona 1',3),(310,'ROMANIA','Romania-MobileCosmote','Inostranstvo zona 2',4),(311,'ROMANIA','Romania-MobileDigiMobile','Inostranstvo zona 2',4),(312,'ROMANIA','Romania-MobileOrange','Inostranstvo zona 2',4),(313,'ROMANIA','Romania-MobileOthers','Inostranstvo zona 2',4),(314,'ROMANIA','Romania-MobileRomtelecom','Inostranstvo zona 2',4),(315,'ROMANIA','Romania-MobileTelemobil','Inostranstvo zona 2',4),(316,'ROMANIA','Romania-MobileVodafone','Inostranstvo zona 2',4),(317,'RUSSIAN FEDERATION','Kazakhstan','Inostranstvo zona 1',3),(318,'RUSSIAN FEDERATION','Kazakhstan-Mobile','Inostranstvo zona 3',5),(319,'RUSSIAN FEDERATION','Russia-Fixed1','Inostranstvo zona 2',4),(320,'RUSSIAN FEDERATION','Russia-Fixed2','Inostranstvo zona 2',4),(321,'RUSSIAN FEDERATION','Russia-Fixed3','Inostranstvo zona 2',4),(322,'RUSSIAN FEDERATION','Russia-Fixed4','Inostranstvo zona 2',4),(323,'RUSSIAN FEDERATION','Russia-Fixed5','Inostranstvo zona 2',4),(324,'RUSSIAN FEDERATION','Russia-Globaltel','Inostranstvo zona 7',9),(325,'RUSSIAN FEDERATION','Russia-Iridium','Inostranstvo zona 7',9),(326,'RUSSIAN FEDERATION','Russia-MobileBeelilne','Inostranstvo zona 3',5),(327,'RUSSIAN FEDERATION','Russia-MobileMegafon','Inostranstvo zona 3',5),(328,'RUSSIAN FEDERATION','Russia-MobileMTS','Inostranstvo zona 3',5),(329,'RUSSIAN FEDERATION','Russia-MobileMVNO','Inostranstvo zona 3',5),(330,'RUSSIAN FEDERATION','Russia-MobileOthers','Inostranstvo zona 3',5),(331,'RUSSIAN FEDERATION','Russia-MobileRostelecom','Inostranstvo zona 3',5),(332,'RUSSIAN FEDERATION','Russia-Moscow','Inostranstvo zona 2',4),(333,'RUSSIAN FEDERATION','Russia-St.Petersburg','Inostranstvo zona 2',4),(334,'RWANDA','Rwanda','Inostranstvo zona 5',7),(335,'SAINT HELENA, ASCENSION AND TRISTAN DA CUNHA','SaintHelena','Inostranstvo zona 7',9),(336,'SAINT KITTS AND NEVIS','SaintKittsandNevis','Inostranstvo zona 4',6),(337,'SAINT LUCIA','SaintLucia','Inostranstvo zona 4',6),(338,'SAINT MARTIN','SintMaarten','Inostranstvo zona 3',5),(339,'SAINT PIERRE AND MIQUELON','SaintPierreandMiquelon','Inostranstvo zona 6',8),(340,'SAINT PIERRE AND MIQUELON','SaintPierreandMiquelon-Mobile','Inostranstvo zona 6',8),(341,'SAINT VINCENT AND THE GRENADINES','SaintVincentandGrenadines','Inostranstvo zona 4',6),(342,'SAMOA','WesternSamoa','Inostranstvo zona 6',8),(343,'SAN MARINO','SanMarino','Inostranstvo zona 4',6),(344,'SAO TOME AND PRINCIPE','SaoTomeandPrincipe','Inostranstvo zona 7',9),(345,'SAUDI ARABIA','SaudiArabia','Inostranstvo zona 3',5),(346,'SENEGAL','Senegal','Inostranstvo zona 5',7),(347,'SERBIA','Srbija Fiksna','Srbija fiksni',1),(348,'SERBIA','Srbija Mobilna','Srbija mobilni',2),(349,'SEYCHELLES','Seychelles','Inostranstvo zona 6',8),(350,'SIERRA LEONE','SierraLeone','Inostranstvo zona 6',8),(351,'SINGAPORE','Singapore','Inostranstvo zona 1',3),(352,'SINGAPORE','Singapore-Mobile','Inostranstvo zona 1',3),(353,'SLOVAKIA','Slovakia','Inostranstvo zona 1',3),(354,'SLOVAKIA','Slovakia-Mobile','Inostranstvo zona 2',4),(355,'SLOVENIA','Slovenia-Alternative','Inostranstvo zona 3',5),(356,'SLOVENIA','Slovenia','Inostranstvo zona 3',5),(357,'SLOVENIA','Slovenia-MobileOthers','Inostranstvo zona 5',7),(358,'SLOVENIA','Slovenia-MobileSimobil','Inostranstvo zona 5',7),(359,'SLOVENIA','Slovenia-MobileT2MOBILE','Inostranstvo zona 5',7),(360,'SLOVENIA','Slovenia-MobileTelekomSlovenije','Inostranstvo zona 5',7),(361,'SLOVENIA','Slovenia-MobileTelemach','Inostranstvo zona 5',7),(362,'SOLOMON ISLANDS','SolomonIslands','Inostranstvo zona 7',9),(363,'SOMALIA','Somalia','Inostranstvo zona 7',9),(364,'SOUTH AFRICA','SouthAfrica','Inostranstvo zona 3',5),(365,'SOUTH SUDAN','SouthSudan','Inostranstvo zona 5',7),(366,'SPAIN','Spain','Inostranstvo zona 1',3),(367,'SPAIN','Spain-IPnumbers','Inostranstvo zona 1',3),(368,'SPAIN','Spain-MobileMovistar','Inostranstvo zona 2',4),(369,'SPAIN','Spain-MobileOrange','Inostranstvo zona 2',4),(370,'SPAIN','Spain-MobileVodafone','Inostranstvo zona 2',4),(371,'SPAIN','Spain-MobileXfera','Inostranstvo zona 2',4),(372,'SPAIN','Spain-MovistarM2MServices','Inostranstvo zona 2',4),(373,'SPAIN','Spain-PNS','Inostranstvo zona 3',5),(374,'SPAIN','Spain-SpecialServices','Inostranstvo zona 4',6),(375,'SRI LANKA','SriLanka','Inostranstvo zona 3',5),(376,'SUDAN','Sudan','Inostranstvo zona 3',5),(377,'SURINAME','Suriname','Inostranstvo zona 5',7),(378,'SWAZILAND','Swaziland','Inostranstvo zona 3',5),(379,'SWEDEN','Sweden','Inostranstvo zona 1',3),(380,'SWEDEN','Sweden-MobileComviq','Inostranstvo zona 2',4),(381,'SWEDEN','Sweden-MobileHi3G','Inostranstvo zona 2',4),(382,'SWEDEN','Sweden-MobileOthers','Inostranstvo zona 2',4),(383,'SWEDEN','Sweden-MobileTelenor','Inostranstvo zona 2',4),(384,'SWEDEN','Sweden-MobileTelia','Inostranstvo zona 2',4),(385,'SWITZERLAND','Switzerland','Inostranstvo zona 1',3),(386,'SWITZERLAND','Switzerland-MobileLycatel','Inostranstvo zona 5',7),(387,'SWITZERLAND','Switzerland-MobileOthers','Inostranstvo zona 5',7),(388,'SWITZERLAND','Switzerland-MobileSalt','Inostranstvo zona 5',7),(389,'SWITZERLAND','Switzerland-MobileSunrise','Inostranstvo zona 5',7),(390,'SWITZERLAND','Switzerland-MobileSwisscom','Inostranstvo zona 5',7),(391,'SYRIA','Syria','Inostranstvo zona 3',5),(392,'TAIWAN','Taiwan','Inostranstvo zona 3',5),(393,'TAJIKISTAN','Tajikistan','Inostranstvo zona 3',5),(394,'TANZANIA','Tanzania','Inostranstvo zona 5',7),(395,'THAILAND','Thailand','Inostranstvo zona 2',4),(396,'TOGO','Togo','Inostranstvo zona 6',8),(397,'TOKELAU','Tokelau','Inostranstvo zona 7',9),(398,'TONGA','Tonga','Inostranstvo zona 6',8),(399,'TRINIDAD AND TOBAGO','TrinidadandTobago','Inostranstvo zona 4',6),(400,'TUNISIA','Tunisia','Inostranstvo zona 6',8),(401,'TURKEY','Turkey-Adana','Inostranstvo zona 1',3),(402,'TURKEY','Turkey-Ankara','Inostranstvo zona 1',3),(403,'TURKEY','Turkey-Antalya','Inostranstvo zona 1',3),(404,'TURKEY','Turkey-Bursa','Inostranstvo zona 1',3),(405,'TURKEY','Turkey-DirectoryAssistance','Inostranstvo zona 4',6),(406,'TURKEY','Turkey','Inostranstvo zona 1',3),(407,'TURKEY','Turkey-Istanbul','Inostranstvo zona 1',3),(408,'TURKEY','Turkey-Izmir','Inostranstvo zona 1',3),(409,'TURKEY','Turkey-MobileAvea','Inostranstvo zona 6',8),(410,'TURKEY','Turkey-MobileGlobalstar','Inostranstvo zona 6',8),(411,'TURKEY','Turkey-MobileTurkcell','Inostranstvo zona 6',8),(412,'TURKEY','Turkey-MobileTurkcellNCyp','Inostranstvo zona 6',8),(413,'TURKEY','Turkey-MobileVodafone','Inostranstvo zona 6',8),(414,'TURKEY','Turkey-MobileVodafoneNCyp','Inostranstvo zona 6',8),(415,'TURKEY','Turkey-NorthCyprus','Inostranstvo zona 1',3),(416,'TURKEY','Turkey-Samsun','Inostranstvo zona 1',3),(417,'TURKMENISTAN','Turkmenistan','Inostranstvo zona 3',5),(418,'TURKS AND CAICOS ISLANDS','TurksandCaicosIslands','Inostranstvo zona 4',6),(419,'TUVALU','Tuvalu','Inostranstvo zona 7',9),(420,'UGANDA','Uganda','Inostranstvo zona 5',7),(421,'UKRAINE','Ukraine','Inostranstvo zona 2',4),(422,'UKRAINE','Ukraine-MobileAstelit','Inostranstvo zona 4',6),(423,'UKRAINE','Ukraine-MobileIntertelecom','Inostranstvo zona 4',6),(424,'UKRAINE','Ukraine-MobileKyivStar','Inostranstvo zona 4',6),(425,'UKRAINE','Ukraine-MobileMTS','Inostranstvo zona 4',6),(426,'UKRAINE','Ukraine-MobileTelesystems','Inostranstvo zona 4',6),(427,'UKRAINE','Ukraine-MobileTriMob','Inostranstvo zona 4',6),(428,'UNITED ARAB EMIRATES','UnitedArabEmirates','Inostranstvo zona 3',5),(429,'UNITED KINGDOM','UnitedKingdom','Inostranstvo zona 1',3),(430,'UNITED KINGDOM','UnitedKingdom-MobileH3G','Inostranstvo zona 4',6),(431,'UNITED KINGDOM','UnitedKingdom-MobileLyca','Inostranstvo zona 4',6),(432,'UNITED KINGDOM','UnitedKingdom-MobileO2','Inostranstvo zona 4',6),(433,'UNITED KINGDOM','UnitedKingdom-MobileOrange','Inostranstvo zona 4',6),(434,'UNITED KINGDOM','UnitedKingdom-MobileOthers','Inostranstvo zona 4',6),(435,'UNITED KINGDOM','UnitedKingdom-MobilePNS','Inostranstvo zona 4',6),(436,'UNITED KINGDOM','UnitedKingdom-MobileTMobile','Inostranstvo zona 4',6),(437,'UNITED KINGDOM','UnitedKingdom-MobileVodafone','Inostranstvo zona 4',6),(438,'UNITED KINGDOM','UnitedKingdom-NFS','Inostranstvo zona 1',3),(439,'UNITED KINGDOM','UnitedKingdom-NTS1','Inostranstvo zona 1',3),(440,'UNITED KINGDOM','UnitedKingdom-NTS2','Inostranstvo zona 1',3),(441,'UNITED KINGDOM','UnitedKingdom-WideCalls','Inostranstvo zona 1',3),(443,'UNITED STATES OF AMERICA','USA-Alaska','Inostranstvo zona 1',3),(444,'UNITED STATES OF AMERICA','USA-Hawaii','Inostranstvo zona 1',3),(445,'UNITED STATES OF AMERICA','USA','Inostranstvo zona 1',3),(446,'URUGUAY','Uruguay','Inostranstvo zona 3',5),(447,'UZBEKISTAN','Uzbekistan','Inostranstvo zona 2',4),(448,'VANUATU','Vanuatu','Inostranstvo zona 6',8),(449,'VENEZUELA','Venezuela','Inostranstvo zona 2',4),(450,'VIETNAM','Vietnam','Inostranstvo zona 3',5),(451,'VIRGIN ISLANDS, BRITISH','BritishVirginIslands','Inostranstvo zona 4',6),(452,'VIRGIN ISLANDS, U.S.','UnitedStatesVirginIslands','Inostranstvo zona 4',6),(453,'WALLIS AND FUTUNA ISLANDS','WallisandFutuna','Inostranstvo zona 7',9),(454,'YEMEN','Yemen','Inostranstvo zona 3',5),(455,'ZAMBIA','Zambia','Inostranstvo zona 6',8),(456,'ZIMBABWE','Zimbabwe','Inostranstvo zona 6',8);
/*!40000 ALTER TABLE `zone` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `zoneCene`
--

DROP TABLE IF EXISTS `zoneCene`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `zoneCene` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `vrstaUsluge` text,
  `providerCena` double DEFAULT NULL,
  `providerPDV` double DEFAULT NULL,
  `cena` double DEFAULT NULL,
  `PDV` double DEFAULT NULL,
  `otherCena` double DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `zoneCene`
--

LOCK TABLES `zoneCene` WRITE;
/*!40000 ALTER TABLE `zoneCene` DISABLE KEYS */;
INSERT INTO `zoneCene` VALUES (1,'Srbija fiksni',0.85,20,0.96,20,1.2),(2,'Srbija mobilni',6,20,6.25,20,8),(3,'Inostranstvo zona 1',4.5,20,10.42,20,16.86),(4,'Inostranstvo zona 2',11.25,20,13.75,20,20.8),(5,'Inostranstvo zona 3',18.75,20,19.58,20,24.51),(6,'Inostranstvo zona 4',26.25,20,33.75,20,44.51),(8,'Inostranstvo zona 5',41.25,20,42.08,20,53.46),(9,'Inostranstvo zona 6',71.25,20,79.17,20,0),(10,'Inostranstvo zona 7',262.5,20,292.08,20,0),(11,'Poziv na kratke brojeve',1.5,20,2.5,20,0),(12,'Saobraćaj u mreži YuVideo',0,20,0,20,0);
/*!40000 ALTER TABLE `zoneCene` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-06-10  9:05:28
