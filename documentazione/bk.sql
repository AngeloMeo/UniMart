/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

CREATE DATABASE IF NOT EXISTS `dipartimento` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `dipartimento`;

CREATE TABLE IF NOT EXISTS `contratto` (
  `codice_contratto` int NOT NULL AUTO_INCREMENT,
  `CF` varchar(16) NOT NULL,
  `sub_particella` int NOT NULL,
  `particella` int NOT NULL,
  `codice_catastale` varchar(6) NOT NULL,
  `costo_al_mq` float NOT NULL,
  `data_firma` date NOT NULL,
  `data_scadenza` date NOT NULL,
  PRIMARY KEY (`codice_contratto`,`sub_particella`,`particella`,`codice_catastale`,`CF`),
  KEY `sub_particella` (`sub_particella`,`particella`,`codice_catastale`),
  KEY `CF` (`CF`),
  CONSTRAINT `contratto_ibfk_1` FOREIGN KEY (`sub_particella`, `particella`, `codice_catastale`) REFERENCES `lotto` (`sub_particella`, `particella`, `codice_catastale`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `contratto_ibfk_2` FOREIGN KEY (`CF`) REFERENCES `persona` (`CF`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*!40000 ALTER TABLE `contratto` DISABLE KEYS */;
INSERT INTO `contratto` (`codice_contratto`, `CF`, `sub_particella`, `particella`, `codice_catastale`, `costo_al_mq`, `data_firma`, `data_scadenza`) VALUES
	(1, 'MCZDPB27T27A194J', 2, 3, '1', 12, '2021-02-06', '2021-04-11'),
	(3, 'CRUMSB53B41A526J', 1, 1, '1', 23, '2020-02-02', '2021-03-10'),
	(4, 'FLMVNL29H15H418T', 1, 2, '1', 74, '2021-09-06', '2021-10-05'),
	(5, 'FPZCLC90P08D731V', 2, 3, '4', 23, '2021-03-11', '2021-12-01'),
	(6, 'GWBMWT52H07B236H', 1, 6, '3', 120, '2021-01-08', '2021-02-12'),
	(7, 'HRMLMR27C24E910X', 1, 2, '50', 66, '2021-04-02', '2021-06-01'),
	(8, 'FLMVNL29H15H418T', 3, 1, '1', 63, '2021-01-04', '2021-02-08'),
	(9, 'KSXRLX90B54I367G', 2, 1, '4', 85, '2021-02-01', '2021-03-01'),
	(10, 'MCZDPB27T27A194J', 1, 7, '3', 19, '2021-05-07', '2021-06-09'),
	(11, 'ZZDHRI43M68I936Y', 2, 2, '1', 65, '2021-08-01', '2021-09-05'),
	(12, 'GWBMWT52H07B236H', 2, 4, '1', 23, '2021-05-06', '2021-11-08'),
	(13, 'HRMLMR27C24E910X', 1, 6, '4', 32, '2021-09-09', '2021-10-03'),
	(14, 'ZZDHRI43M68I936Y', 2, 2, '50', 21, '2021-05-03', '2021-08-05'),
	(15, 'CRUMSB53B41A526J', 1, 4, '3', 99, '2021-07-08', '2021-09-11'),
	(16, 'HRMLMR27C24E910X', 1, 3, '1', 63, '2021-03-07', '2021-04-12'),
	(17, 'FLMVNL29H15H418T', 2, 3, '4', 56, '2021-05-01', '2021-09-12'),
	(18, 'HRMLMR27C24E910X', 1, 3, '4', 11, '2021-06-06', '2021-08-01'),
	(19, 'ZZDHRI43M68I936Y', 1, 4, '1', 71, '2021-03-04', '2021-04-09'),
	(20, 'GWBMWT52H07B236H', 3, 3, '1', 73, '2021-02-09', '2021-06-11'),
	(21, 'ZZDHRI43M68I936Y', 2, 1, '2', 96, '2021-03-05', '2021-06-09');
/*!40000 ALTER TABLE `contratto` ENABLE KEYS */;

CREATE TABLE IF NOT EXISTS `eseguito` (
  `sub_particella` int NOT NULL,
  `particella` int NOT NULL,
  `codice_catastale` varchar(6) NOT NULL,
  `intervento` int NOT NULL,
  PRIMARY KEY (`sub_particella`,`particella`,`codice_catastale`,`intervento`),
  KEY `particella` (`particella`,`codice_catastale`),
  KEY `intervento` (`intervento`),
  CONSTRAINT `eseguito_ibfk_1` FOREIGN KEY (`particella`, `codice_catastale`) REFERENCES `terreno` (`particella`, `codice_catastale`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `eseguito_ibfk_2` FOREIGN KEY (`intervento`) REFERENCES `intervento` (`intervento`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*!40000 ALTER TABLE `eseguito` DISABLE KEYS */;
INSERT INTO `eseguito` (`sub_particella`, `particella`, `codice_catastale`, `intervento`) VALUES
	(1, 1, '1', 1),
	(1, 2, '50', 4),
	(6, 1, '1', 6),
	(1, 1, '1', 7),
	(1, 1, '4', 8),
	(2, 1, '1', 9),
	(4, 3, '1', 10),
	(1, 1, '1', 11),
	(4, 2, '50', 12),
	(3, 1, '1', 13),
	(6, 1, '1', 14),
	(1, 3, '1', 15),
	(2, 1, '2', 16),
	(4, 3, '1', 17),
	(3, 1, '1', 18),
	(2, 1, '2', 19),
	(2, 1, '2', 20),
	(2, 1, '4', 21),
	(1, 1, '4', 22),
	(2, 1, '4', 23),
	(2, 2, '1', 24),
	(1, 2, '1', 25),
	(3, 3, '1', 26),
	(1, 2, '1', 27),
	(1, 2, '50', 28),
	(5, 3, '1', 29),
	(2, 1, '1', 30),
	(2, 2, '50', 31),
	(2, 3, '1', 32),
	(5, 3, '1', 33),
	(2, 2, '1', 34),
	(1, 2, '50', 35),
	(2, 2, '50', 36),
	(4, 2, '50', 37),
	(1, 3, '1', 38),
	(2, 3, '1', 39),
	(3, 3, '1', 40);
/*!40000 ALTER TABLE `eseguito` ENABLE KEYS */;

CREATE TABLE IF NOT EXISTS `intervento` (
  `intervento` int NOT NULL AUTO_INCREMENT,
  `data` date NOT NULL,
  `Tipo` enum('Raccolta','Potatura','Concimazione') NOT NULL,
  `alberi_abattuti` int DEFAULT NULL,
  `frutto` varchar(20) DEFAULT NULL,
  `qta_acqua_impiegata` float DEFAULT NULL,
  PRIMARY KEY (`intervento`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*!40000 ALTER TABLE `intervento` DISABLE KEYS */;
INSERT INTO `intervento` (`intervento`, `data`, `Tipo`, `alberi_abattuti`, `frutto`, `qta_acqua_impiegata`) VALUES
	(1, '2021-01-06', 'Potatura', 14, NULL, NULL),
	(2, '2021-01-09', 'Raccolta', NULL, 'nocciole', NULL),
	(3, '2021-01-08', 'Concimazione', NULL, NULL, 10),
	(4, '2020-12-31', 'Raccolta', NULL, 'nocciole', NULL),
	(5, '2021-01-11', 'Potatura', 20, NULL, NULL),
	(6, '2021-01-01', 'Potatura', 5, NULL, NULL),
	(7, '2021-01-15', 'Raccolta', NULL, 'mele', NULL),
	(8, '2021-01-05', 'Raccolta', NULL, 'noci', NULL),
	(9, '2021-01-18', 'Potatura', 22, NULL, NULL),
	(10, '2021-01-05', 'Concimazione', NULL, NULL, 30),
	(11, '2021-01-01', 'Concimazione', NULL, NULL, 20),
	(12, '2021-01-02', 'Raccolta', NULL, 'mele', NULL),
	(13, '2021-01-12', 'Potatura', 10, NULL, NULL),
	(14, '2021-01-03', 'Potatura', 80, NULL, NULL),
	(15, '2021-01-05', 'Raccolta', NULL, 'noci', NULL),
	(16, '2021-01-12', 'Raccolta', NULL, 'noci', NULL),
	(17, '2021-01-11', 'Raccolta', NULL, 'mele', NULL),
	(18, '2021-01-19', 'Concimazione', NULL, NULL, 37),
	(19, '2021-01-10', 'Concimazione', NULL, NULL, 60),
	(20, '2020-12-27', 'Potatura', 63, NULL, NULL),
	(21, '2020-12-25', 'Concimazione', NULL, NULL, 63),
	(22, '2021-02-13', 'Potatura', 50, NULL, NULL),
	(23, '2021-01-18', 'Potatura', 23, NULL, NULL),
	(24, '2021-01-20', 'Concimazione', NULL, NULL, 23),
	(25, '2021-01-16', 'Raccolta', NULL, 'noci', NULL),
	(26, '2021-03-18', 'Raccolta', NULL, 'noci', NULL),
	(27, '2020-12-14', 'Potatura', 15, NULL, NULL),
	(28, '2020-12-19', 'Concimazione', NULL, NULL, 68),
	(29, '2021-01-24', 'Raccolta', NULL, 'noci', NULL),
	(30, '2021-01-15', 'Raccolta', NULL, 'nocciole', NULL),
	(31, '2021-01-27', 'Concimazione', NULL, NULL, 33),
	(32, '2021-01-25', 'Concimazione', NULL, NULL, 44),
	(33, '2021-01-29', 'Concimazione', NULL, NULL, 100),
	(34, '2021-01-17', 'Potatura', 66, NULL, NULL),
	(35, '2021-01-27', 'Potatura', 55, NULL, NULL),
	(36, '2020-12-16', 'Potatura', 61, NULL, NULL),
	(37, '2020-11-28', 'Potatura', 11, NULL, NULL),
	(38, '2021-01-17', 'Potatura', 60, NULL, NULL),
	(39, '2021-01-25', 'Potatura', 34, NULL, NULL),
	(40, '2020-11-29', 'Potatura', 22, NULL, NULL);
/*!40000 ALTER TABLE `intervento` ENABLE KEYS */;

CREATE TABLE IF NOT EXISTS `lotto` (
  `sub_particella` int NOT NULL,
  `particella` int NOT NULL,
  `codice_catastale` varchar(6) NOT NULL,
  `mq` float NOT NULL,
  `latitudine` varchar(15) NOT NULL,
  `longitudine` varchar(15) NOT NULL,
  `codice_atto_proprieta` int NOT NULL,
  `CF` varchar(16) NOT NULL,
  PRIMARY KEY (`sub_particella`,`particella`,`codice_catastale`),
  KEY `CF` (`CF`),
  KEY `lotto_ibfk_1` (`particella`,`codice_catastale`),
  CONSTRAINT `lotto_ibfk_1` FOREIGN KEY (`particella`, `codice_catastale`) REFERENCES `terreno` (`particella`, `codice_catastale`),
  CONSTRAINT `lotto_ibfk_2` FOREIGN KEY (`CF`) REFERENCES `persona` (`CF`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*!40000 ALTER TABLE `lotto` DISABLE KEYS */;
INSERT INTO `lotto` (`sub_particella`, `particella`, `codice_catastale`, `mq`, `latitudine`, `longitudine`, `codice_atto_proprieta`, `CF`) VALUES
	(1, 1, '1', 10, '14', '56', 1, 'QBHSDU47H04Z713F'),
	(1, 1, '4', 82, '26', '29', 13, 'VVRBPF77D63B578C'),
	(1, 2, '1', 41, '36', '65', 12, 'VHVMCT71P27E903F'),
	(1, 2, '50', 64, '46', '11', 11, 'TWVDNM87C22L285A'),
	(1, 3, '1', 96, '74', '66', 10, 'SVADCW70R25F838U'),
	(1, 3, '4', 91, '84', '55', 9, 'PRLHVQ43C07I014G'),
	(1, 4, '1', 52, '94', '52', 8, 'FTXMNX91M18L013K'),
	(1, 4, '3', 32, '44', '35', 7, 'FNPFSH70T66E341Q'),
	(1, 6, '3', 63, '45', '22', 6, 'CDBDJG90S55I404L'),
	(1, 6, '4', 74, '46', '89', 5, 'DCFJPG92A23L322U'),
	(1, 7, '3', 14, '41', '47', 4, 'CDBDJG90S55I404L'),
	(2, 1, '1', 21, '16', '32', 2, 'QBHSDU47H04Z713F'),
	(2, 1, '2', 63, '42', '74', 15, 'XSHZTZ61D48B862T'),
	(2, 1, '4', 22, '43', '71', 17, 'VVRBPF77D63B578C'),
	(2, 2, '1', 14, '12', '98', 18, 'WNRDDD81P52A678A'),
	(2, 2, '50', 52, '17', '14', 19, 'XSHZTZ61D48B862T'),
	(2, 3, '1', 87, '19', '52', 20, 'CDBDJG90S55I404L'),
	(2, 3, '4', 64, '25', '37', 21, 'FNPFSH70T66E341Q'),
	(2, 4, '1', 25, '54', '88', 22, 'FTXMNX91M18L013K'),
	(3, 1, '1', 14, '32', '58', 14, 'WNRDDD81P52A678A'),
	(3, 3, '1', 25, '47', '99', 24, 'SVADCW70R25F838U'),
	(3, 3, '4', 56, '56', '77', 25, 'TWVDNM87C22L285A'),
	(3, 4, '1', 32, '13', '41', 23, 'PRLHVQ43C07I014G'),
	(4, 2, '50', 21, '19', '68', 27, 'ZNVGLV28C18F632N'),
	(4, 3, '1', 15, '97', '96', 26, 'XSHZTZ61D48B862T'),
	(5, 3, '1', 19, '91', '14', 28, 'VVRBPF77D63B578C'),
	(6, 1, '1', 32, '9', '64', 16, 'ZNVGLV28C18F632N');
/*!40000 ALTER TABLE `lotto` ENABLE KEYS */;

CREATE TABLE IF NOT EXISTS `persona` (
  `CF` varchar(16) NOT NULL,
  `nome` varchar(20) NOT NULL,
  `cognome` varchar(20) NOT NULL,
  `via` varchar(20) NOT NULL,
  `comune` varchar(30) NOT NULL,
  `provincia` varchar(2) NOT NULL,
  `civico` int NOT NULL,
  `data_nascita` date NOT NULL,
  `Tipo` enum('Affittuario','Proprietario') NOT NULL,
  `metodo_pagamento` varchar(20) DEFAULT NULL,
  `fascia_di_reddito` int DEFAULT NULL,
  `telefono` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`CF`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*!40000 ALTER TABLE `persona` DISABLE KEYS */;
INSERT INTO `persona` (`CF`, `nome`, `cognome`, `via`, `comune`, `provincia`, `civico`, `data_nascita`, `Tipo`, `metodo_pagamento`, `fascia_di_reddito`, `telefono`) VALUES
	('CDBDJG90S55I404L', 'Anna', 'Proietti', 'visciano', 'Baronissi', 'SA', 76, '1990-01-15', 'Proprietario', NULL, 4, '6325147894'),
	('CRUMSB53B41A526J', 'Martina', 'Testa', 'rossi', 'Poggiomarino', 'SA', 34, '1977-02-15', 'Affittuario', 'bonifico', NULL, '3256147894'),
	('DCFJPG92A23L322U', 'Caterina', 'Napolitano', 'verga', 'Visciano', 'NA', 8, '1994-05-14', 'Proprietario', NULL, 4, '3625987415'),
	('FLMVNL29H15H418T', 'Francesco', 'Ricci', 'generale', 'Tufino', 'NA', 64, '1996-03-25', 'Affittuario', 'assegno', NULL, '9654128745'),
	('FNPFSH70T66E341Q', 'Rosa', 'Verdi', 'adige', 'Avella', 'AV', 8, '1951-07-03', 'Proprietario', NULL, 1, '3578456128'),
	('FPZCLC90P08D731V', 'Luca', 'Ferrari', 'mascagni', 'Nola', 'NA', 8, '1995-03-30', 'Affittuario', 'contanti', NULL, '6587451298'),
	('FTXMNX91M18L013K', 'Luca', 'Ferrari', 'mario de sena', 'Montoro', 'SA', 44, '1988-06-03', 'Proprietario', NULL, 1, '9854715247'),
	('GWBMWT52H07B236H', 'Paolo', 'Barbieri', 'rossini', 'Nola', 'NA', 21, '1995-02-17', 'Affittuario', 'contanti', NULL, '354124789'),
	('HRMLMR27C24E910X', 'Giovanni', 'Pace', 'po', 'Schiava', 'NA', 4, '1995-11-14', 'Affittuario', 'bonifico', NULL, '2148795632'),
	('KSXRLX90B54I367G', 'Marco', 'Gialli', 'puccini', 'Casamarciano', 'NA', 4, '1999-07-30', 'Affittuario', 'conto corrente', NULL, '524178946'),
	('MCZDPB27T27A194J', 'Marta', 'Bianchi', 'tevere', 'Nola', 'NA', 4, '1992-09-08', 'Affittuario', 'paypal', NULL, '324587945'),
	('PRLHVQ43C07I014G', 'Marco', 'Russo', 'puccini', 'Avellino', 'AV', 77, '1979-04-13', 'Proprietario', NULL, 3, '214587459'),
	('QBHSDU47H04Z713F', 'Mario', 'Rossi', 'po', 'Visciano', 'NA', 45, '1986-10-15', 'Proprietario', NULL, 5, '145269876'),
	('SVADCW70R25F838U', 'Giovanna', 'Rossi', 'dante alighieri', 'Napoli', 'NA', 8, '1935-07-23', 'Proprietario', NULL, 1, '259876485'),
	('TWVDNM87C22L285A', 'Mattia', 'Napolitano', 'paisiello', 'Avellino', 'AV', 10, '1989-03-13', 'Proprietario', NULL, 3, '2547896354'),
	('VHVMCT71P27E903F', 'Marta', 'Russo', 'verdi', 'Visciano', 'NA', 47, '1972-06-23', 'Proprietario', NULL, 4, '254789514'),
	('VVRBPF77D63B578C', 'Giovanni', 'Gialli', 'carducci', 'Baiano', 'AV', 29, '1970-07-23', 'Proprietario', NULL, 2, '658749123'),
	('WNRDDD81P52A678A', 'Maria', 'Napolitano', 'anfiteatro', 'Fisciano', 'SA', 12, '1999-07-16', 'Proprietario', NULL, 5, '247851479'),
	('XSHZTZ61D48B862T', 'Matteo', 'Lombardo', 'lancellotti', 'Cimitile', 'NA', 73, '1977-04-20', 'Proprietario', NULL, 1, '1458963258'),
	('ZNVGLV28C18F632N', 'Carmine', 'Lorusso', 'giotto', 'Cicciano', 'NA', 97, '1971-06-30', 'Proprietario', NULL, 3, '149632587'),
	('ZZDHRI43M68I936Y', 'Luigi', 'Napolitano', 'milano', 'Sarno', 'SA', 16, '1994-04-01', 'Affittuario', 'conto corrente', NULL, '654604408');
/*!40000 ALTER TABLE `persona` ENABLE KEYS */;

CREATE TABLE IF NOT EXISTS `prodotto` (
  `nome` varchar(30) NOT NULL,
  `intervento` int NOT NULL,
  PRIMARY KEY (`nome`,`intervento`),
  KEY `intervento` (`intervento`),
  CONSTRAINT `prodotto_ibfk_1` FOREIGN KEY (`intervento`) REFERENCES `intervento` (`intervento`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*!40000 ALTER TABLE `prodotto` DISABLE KEYS */;
INSERT INTO `prodotto` (`nome`, `intervento`) VALUES
	('misto', 3),
	('organico', 3),
	('seccatutto', 3),
	('semplice', 3),
	('organico', 10),
	('misto ', 11),
	('misto', 18),
	('organico', 18),
	('seccatutto ', 18),
	('semplice', 18),
	('seccatutto', 19),
	('semplice ', 19),
	('organico', 21),
	('misto ', 24),
	('organico', 24),
	('seccatutto', 24),
	('semplice ', 24),
	('semplice ', 28),
	('misto ', 31),
	('organico ', 31),
	('seccatutto ', 31),
	('semplice ', 31),
	('misto ', 32),
	('organico ', 32),
	('misto', 33);
/*!40000 ALTER TABLE `prodotto` ENABLE KEYS */;

CREATE TABLE `sommamqperterreno` (
	`sub_particella` INT(10) NOT NULL,
	`particella` INT(10) NOT NULL,
	`codice_catastale` VARCHAR(6) NOT NULL COLLATE 'utf8mb4_0900_ai_ci',
	`SommaMq` DOUBLE NULL
) ENGINE=MyISAM;

CREATE TABLE IF NOT EXISTS `terreno` (
  `particella` int NOT NULL,
  `codice_catastale` varchar(6) NOT NULL,
  PRIMARY KEY (`particella`,`codice_catastale`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*!40000 ALTER TABLE `terreno` DISABLE KEYS */;
INSERT INTO `terreno` (`particella`, `codice_catastale`) VALUES
	(1, '1'),
	(1, '2'),
	(1, '4'),
	(2, '1'),
	(2, '50'),
	(3, '1'),
	(3, '4'),
	(4, '1'),
	(4, '3'),
	(6, '3'),
	(6, '4'),
	(7, '3');
/*!40000 ALTER TABLE `terreno` ENABLE KEYS */;

DROP TABLE IF EXISTS `sommamqperterreno`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `dipartimento`.`sommamqperterreno` AS select `l`.`sub_particella` AS `sub_particella`,`l`.`particella` AS `particella`,`l`.`codice_catastale` AS `codice_catastale`,sum(`l`.`mq`) AS `SommaMq` from `dipartimento`.`lotto` `l` group by `l`.`particella`,`l`.`codice_catastale`;

CREATE DATABASE IF NOT EXISTS `initcategorie` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `initcategorie`;

CREATE TABLE IF NOT EXISTS `categoria` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `descrizione` varchar(300) DEFAULT '0',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*!40000 ALTER TABLE `categoria` DISABLE KEYS */;
INSERT INTO `categoria` (`ID`, `nome`, `descrizione`) VALUES
	(1, 'Vini', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. In facilisis blandit justo sit amet gravida. Donec maximus odio vitae quam scelerisque feugiat. Ut sit amet lobortis urna. Nulla malesuada id nisi nec facilisis. Donec vulputate mollis mi, in sagittis ligula rhoncus in. Sed bibendum ex eu duc.'),
	(2, 'Pasta', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. In facilisis blandit justo sit amet gravida. Donec maximus odio vitae quam scelerisque feugiat. Ut sit amet lobortis urna. Nulla malesuada id nisi nec facilisis. Donec vulputate mollis mi, in sagittis ligula rhoncus in. Sed bibendum ex eu duc.'),
	(3, 'Surgelati', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. In facilisis blandit justo sit amet gravida. Donec maximus odio vitae quam scelerisque feugiat. Ut sit amet lobortis urna. Nulla malesuada id nisi nec facilisis. Donec vulputate mollis mi, in sagittis ligula rhoncus in. Sed bibendum ex eu duc.'),
	(4, 'Carni', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. In facilisis blandit justo sit amet gravida. Donec maximus odio vitae quam scelerisque feugiat. Ut sit amet lobortis urna. Nulla malesuada id nisi nec facilisis. Donec vulputate mollis mi, in sagittis ligula rhoncus in. Sed bibendum ex eu duc.'),
	(5, 'Pane', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. In facilisis blandit justo sit amet gravida. Donec maximus odio vitae quam scelerisque feugiat. Ut sit amet lobortis urna. Nulla malesuada id nisi nec facilisis. Donec vulputate mollis mi, in sagittis ligula rhoncus in. Sed bibendum ex eu duc.');
/*!40000 ALTER TABLE `categoria` ENABLE KEYS */;

CREATE TABLE IF NOT EXISTS `prodotto` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `descrizione` varchar(300) DEFAULT NULL,
  `prezzo` float NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*!40000 ALTER TABLE `prodotto` DISABLE KEYS */;
INSERT INTO `prodotto` (`ID`, `nome`, `descrizione`, `prezzo`) VALUES
	(1, 'Prodotto1', 'Curabitur et nisi semper, gravida massa quis, gravida ante. Curabitur in metus semper, ultrices nulla venenatis, fermentum leo. Nunc et laoreet massa. In sit amet aliquet libero. Nam sagittis, mauris eu hendrerit faucibus, leo enim finibus magna, in rhoncus mauris magna in ipsum. Proin mattis lectus', 15.52),
	(2, 'Prodotto2', 'Curabitur et nisi semper, gravida massa quis, gravida ante. Curabitur in metus semper, ultrices nulla venenatis, fermentum leo. Nunc et laoreet massa. In sit amet aliquet libero. Nam sagittis, mauris eu hendrerit faucibus, leo enim finibus magna, in rhoncus mauris magna in ipsum. Proin mattis lectus', 1),
	(3, 'Prodotto3', 'Curabitur et nisi semper, gravida massa quis, gravida ante. Curabitur in metus semper, ultrices nulla venenatis, fermentum leo. Nunc et laoreet massa. In sit amet aliquet libero. Nam sagittis, mauris eu hendrerit faucibus, leo enim finibus magna, in rhoncus mauris magna in ipsum. Proin mattis lectus', 2),
	(4, 'Prodotto4', 'Curabitur et nisi semper, gravida massa quis, gravida ante. Curabitur in metus semper, ultrices nulla venenatis, fermentum leo. Nunc et laoreet massa. In sit amet aliquet libero. Nam sagittis, mauris eu hendrerit faucibus, leo enim finibus magna, in rhoncus mauris magna in ipsum. Proin mattis lectus', 3),
	(5, 'Prodotto5', 'Curabitur et nisi semper, gravida massa quis, gravida ante. Curabitur in metus semper, ultrices nulla venenatis, fermentum leo. Nunc et laoreet massa. In sit amet aliquet libero. Nam sagittis, mauris eu hendrerit faucibus, leo enim finibus magna, in rhoncus mauris magna in ipsum. Proin mattis lectus', 20),
	(6, 'Prodotto6', 'Curabitur et nisi semper, gravida massa quis, gravida ante. Curabitur in metus semper, ultrices nulla venenatis, fermentum leo. Nunc et laoreet massa. In sit amet aliquet libero. Nam sagittis, mauris eu hendrerit faucibus, leo enim finibus magna, in rhoncus mauris magna in ipsum. Proin mattis lectus', 19),
	(7, 'Prodotto7', 'Curabitur et nisi semper, gravida massa quis, gravida ante. Curabitur in metus semper, ultrices nulla venenatis, fermentum leo. Nunc et laoreet massa. In sit amet aliquet libero. Nam sagittis, mauris eu hendrerit faucibus, leo enim finibus magna, in rhoncus mauris magna in ipsum. Proin mattis lectus', 18),
	(8, 'Prodotto8', 'Curabitur et nisi semper, gravida massa quis, gravida ante. Curabitur in metus semper, ultrices nulla venenatis, fermentum leo. Nunc et laoreet massa. In sit amet aliquet libero. Nam sagittis, mauris eu hendrerit faucibus, leo enim finibus magna, in rhoncus mauris magna in ipsum. Proin mattis lectus', 17),
	(9, 'Prodotto9', 'Curabitur et nisi semper, gravida massa quis, gravida ante. Curabitur in metus semper, ultrices nulla venenatis, fermentum leo. Nunc et laoreet massa. In sit amet aliquet libero. Nam sagittis, mauris eu hendrerit faucibus, leo enim finibus magna, in rhoncus mauris magna in ipsum. Proin mattis lectus', 16),
	(10, 'Prodotto10', 'Curabitur et nisi semper, gravida massa quis, gravida ante. Curabitur in metus semper, ultrices nulla venenatis, fermentum leo. Nunc et laoreet massa. In sit amet aliquet libero. Nam sagittis, mauris eu hendrerit faucibus, leo enim finibus magna, in rhoncus mauris magna in ipsum. Proin mattis lectus', 15),
	(11, 'Prodotto11', 'Curabitur et nisi semper, gravida massa quis, gravida ante. Curabitur in metus semper, ultrices nulla venenatis, fermentum leo. Nunc et laoreet massa. In sit amet aliquet libero. Nam sagittis, mauris eu hendrerit faucibus, leo enim finibus magna, in rhoncus mauris magna in ipsum. Proin mattis lectus', 14),
	(12, 'Prodotto12', 'Curabitur et nisi semper, gravida massa quis, gravida ante. Curabitur in metus semper, ultrices nulla venenatis, fermentum leo. Nunc et laoreet massa. In sit amet aliquet libero. Nam sagittis, mauris eu hendrerit faucibus, leo enim finibus magna, in rhoncus mauris magna in ipsum. Proin mattis lectus', 13),
	(13, 'Prodotto13', 'Curabitur et nisi semper, gravida massa quis, gravida ante. Curabitur in metus semper, ultrices nulla venenatis, fermentum leo. Nunc et laoreet massa. In sit amet aliquet libero. Nam sagittis, mauris eu hendrerit faucibus, leo enim finibus magna, in rhoncus mauris magna in ipsum. Proin mattis lectus', 12),
	(14, 'Prodotto14', 'Curabitur et nisi semper, gravida massa quis, gravida ante. Curabitur in metus semper, ultrices nulla venenatis, fermentum leo. Nunc et laoreet massa. In sit amet aliquet libero. Nam sagittis, mauris eu hendrerit faucibus, leo enim finibus magna, in rhoncus mauris magna in ipsum. Proin mattis lectus', 10),
	(15, 'Prodotto15', 'Curabitur et nisi semper, gravida massa quis, gravida ante. Curabitur in metus semper, ultrices nulla venenatis, fermentum leo. Nunc et laoreet massa. In sit amet aliquet libero. Nam sagittis, mauris eu hendrerit faucibus, leo enim finibus magna, in rhoncus mauris magna in ipsum. Proin mattis lectus', 9),
	(16, 'Prodotto16', 'Curabitur et nisi semper, gravida massa quis, gravida ante. Curabitur in metus semper, ultrices nulla venenatis, fermentum leo. Nunc et laoreet massa. In sit amet aliquet libero. Nam sagittis, mauris eu hendrerit faucibus, leo enim finibus magna, in rhoncus mauris magna in ipsum. Proin mattis lectus', 8),
	(17, 'Prodotto17', 'Curabitur et nisi semper, gravida massa quis, gravida ante. Curabitur in metus semper, ultrices nulla venenatis, fermentum leo. Nunc et laoreet massa. In sit amet aliquet libero. Nam sagittis, mauris eu hendrerit faucibus, leo enim finibus magna, in rhoncus mauris magna in ipsum. Proin mattis lectus', 7),
	(18, 'Prodotto18', 'Curabitur et nisi semper, gravida massa quis, gravida ante. Curabitur in metus semper, ultrices nulla venenatis, fermentum leo. Nunc et laoreet massa. In sit amet aliquet libero. Nam sagittis, mauris eu hendrerit faucibus, leo enim finibus magna, in rhoncus mauris magna in ipsum. Proin mattis lectus', 6),
	(19, 'Prodotto19', 'Curabitur et nisi semper, gravida massa quis, gravida ante. Curabitur in metus semper, ultrices nulla venenatis, fermentum leo. Nunc et laoreet massa. In sit amet aliquet libero. Nam sagittis, mauris eu hendrerit faucibus, leo enim finibus magna, in rhoncus mauris magna in ipsum. Proin mattis lectus', 5),
	(20, 'Prodotto20', 'Curabitur et nisi semper, gravida massa quis, gravida ante. Curabitur in metus semper, ultrices nulla venenatis, fermentum leo. Nunc et laoreet massa. In sit amet aliquet libero. Nam sagittis, mauris eu hendrerit faucibus, leo enim finibus magna, in rhoncus mauris magna in ipsum. Proin mattis lectus', 4);
/*!40000 ALTER TABLE `prodotto` ENABLE KEYS */;

CREATE TABLE IF NOT EXISTS `prodottocategoria` (
  `idProdotto` int NOT NULL,
  `idCategoria` int NOT NULL,
  PRIMARY KEY (`idProdotto`,`idCategoria`),
  KEY `idCategoria_idx` (`idCategoria`),
  CONSTRAINT `idCategoria` FOREIGN KEY (`idCategoria`) REFERENCES `categoria` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `idProdotto` FOREIGN KEY (`idProdotto`) REFERENCES `prodotto` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*!40000 ALTER TABLE `prodottocategoria` DISABLE KEYS */;
INSERT INTO `prodottocategoria` (`idProdotto`, `idCategoria`) VALUES
	(1, 1),
	(2, 1),
	(3, 1),
	(4, 1),
	(5, 2),
	(6, 2),
	(7, 2),
	(8, 2),
	(9, 2),
	(10, 2),
	(11, 3),
	(12, 4),
	(13, 4),
	(14, 4),
	(15, 4),
	(16, 4),
	(17, 4),
	(18, 4),
	(19, 5),
	(20, 5);
/*!40000 ALTER TABLE `prodottocategoria` ENABLE KEYS */;

CREATE TABLE IF NOT EXISTS `utente` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `username` varchar(100) NOT NULL,
  `passwordhash` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `admin` tinyint NOT NULL DEFAULT '0',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `Indice 2` (`email`),
  UNIQUE KEY `Indice 3` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*!40000 ALTER TABLE `utente` DISABLE KEYS */;
INSERT INTO `utente` (`ID`, `username`, `passwordhash`, `email`, `admin`) VALUES
	(1, 'sabato', '956ffe4f98accd91736950254d4272cc1855e2d2', 's@g.c', 0),
	(2, 'root', 'dc76e9f0c0006e8f919e0c515c66dbba3982f785', 'r@r.r', 0),
	(3, 't', '8efd86fb78a56a5145ed7739dcb00c78581c5375', 't@t.t', 0),
	(4, 'q', '22ea1c649c82946aa6e479e1ffd321e4a318b1b0', 'q@q.q', 0),
	(5, 'k', '13fbd79c3d390e5d6585a21e11ff5ec1970cff0c', 'k@k.k', 1),
	(6, 'g', '54fd1711209fb1c0781092374132c66e79e2241b', 'g@g.g', 0);
/*!40000 ALTER TABLE `utente` ENABLE KEYS */;

CREATE DATABASE IF NOT EXISTS `modellomvcdb` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `modellomvcdb`;

CREATE TABLE IF NOT EXISTS `customer` (
  `id` int NOT NULL AUTO_INCREMENT,
  `firstName` varchar(50) DEFAULT NULL,
  `lastName` varchar(50) DEFAULT NULL,
  `balance` double DEFAULT NULL,
  `bookmarked` tinyint DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` (`id`, `firstName`, `lastName`, `balance`, `bookmarked`) VALUES
	(1, 'Harry', 'Hacker', -3456.78, 1),
	(2, 'Codie', 'Coder', 235.56, 0),
	(3, 'Polly', 'Programmer', 987654.32, 1),
	(4, 'Sabato', 'Genovese', 15, 0),
	(5, 'admin', 'admin', -55, 1),
	(6, 'Admin', 'Admin', 0, 1);
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;

DELIMITER //
CREATE PROCEDURE `setAllBookmarked`(
	IN `value` INT
)
BEGIN
	UPDATE customer c SET c.bookmarked = VALUE;
END//
DELIMITER ;

CREATE DATABASE IF NOT EXISTS `test` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `test`;

DELIMITER //
CREATE EVENT `AggiornamentoStato` ON SCHEDULE EVERY 5 MINUTE STARTS '2021-04-22 09:00:00' ENDS '2021-04-22 20:00:00' ON COMPLETION PRESERVE DISABLE DO BEGIN
	UPDATE ordini o SET o.Stato = 'Consegnato' WHERE o.Stato = 'In Consegna';
	UPDATE ordini o SET o.Stato = 'In Consegna' WHERE o.Stato = 'Spedito';
	UPDATE ordini o SET o.Stato = 'Spedito' WHERE o.Stato = 'In Preparazione';
	UPDATE ordini o SET o.Stato = 'In Preparazione' WHERE o.Stato = 'Accettato';
	UPDATE ordini o SET o.Stato = 'Accettato' WHERE o.Stato = 'Inviato';
END//
DELIMITER ;

CREATE TABLE IF NOT EXISTS `ordini` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `Stato` enum('Salvato','Inviato','Accettato','In Preparazione','Spedito','In Consegna','Consegnato') NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*!40000 ALTER TABLE `ordini` DISABLE KEYS */;
INSERT INTO `ordini` (`ID`, `Stato`) VALUES
	(1, 'Salvato'),
	(2, 'Salvato'),
	(3, 'Consegnato'),
	(4, 'Consegnato'),
	(5, 'Consegnato'),
	(6, 'Consegnato'),
	(7, 'Consegnato'),
	(8, 'Consegnato');
/*!40000 ALTER TABLE `ordini` ENABLE KEYS */;

CREATE DATABASE IF NOT EXISTS `unimart` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `unimart`;

DELIMITER //
CREATE EVENT `AggiornamentoOrdini` ON SCHEDULE EVERY 5 MINUTE STARTS '2021-05-04 08:00:00' ENDS '2021-05-04 18:00:00' ON COMPLETION PRESERVE DISABLE DO BEGIN
	UPDATE ordini o SET o.Stato = 'consegnato' WHERE o.Stato = 'in consegna';
	UPDATE ordini o SET o.Stato = 'in consegna' WHERE o.Stato = 'spedito';
	UPDATE ordini o SET o.Stato = 'spedito' WHERE o.Stato = 'preparazione';
	UPDATE ordini o SET o.Stato = 'accettato' WHERE o.Stato = 'preparazione';
END//
DELIMITER ;

CREATE TABLE IF NOT EXISTS `categoria` (
  `nome` varchar(100) NOT NULL DEFAULT '',
  `aliquota` float NOT NULL,
  PRIMARY KEY (`nome`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*!40000 ALTER TABLE `categoria` DISABLE KEYS */;
/*!40000 ALTER TABLE `categoria` ENABLE KEYS */;

CREATE TABLE IF NOT EXISTS `coupon` (
  `numeroCoupon` int NOT NULL AUTO_INCREMENT,
  `stato` enum('Riscattato','Disponibile') NOT NULL,
  `sconto` float NOT NULL,
  `cfCreatore` varchar(16) NOT NULL DEFAULT '',
  PRIMARY KEY (`numeroCoupon`),
  KEY `FK__utente` (`cfCreatore`),
  CONSTRAINT `FK__utente` FOREIGN KEY (`cfCreatore`) REFERENCES `utente` (`CF`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*!40000 ALTER TABLE `coupon` DISABLE KEYS */;
INSERT INTO `coupon` (`numeroCoupon`, `stato`, `sconto`, `cfCreatore`) VALUES
	(2, 'Disponibile', 10, 'ERFDPG92A23L322U');
/*!40000 ALTER TABLE `coupon` ENABLE KEYS */;

CREATE TABLE IF NOT EXISTS `coupon_applicato` (
  `idCoupon` int NOT NULL,
  `idOrdine` int NOT NULL,
  PRIMARY KEY (`idCoupon`),
  KEY `FK_ordine` (`idOrdine`),
  CONSTRAINT `FK_coupon` FOREIGN KEY (`idCoupon`) REFERENCES `coupon` (`numeroCoupon`) ON UPDATE CASCADE,
  CONSTRAINT `FK_ordine` FOREIGN KEY (`idOrdine`) REFERENCES `ordine` (`numeroOrdine`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*!40000 ALTER TABLE `coupon_applicato` DISABLE KEYS */;
/*!40000 ALTER TABLE `coupon_applicato` ENABLE KEYS */;

CREATE TABLE IF NOT EXISTS `inventario` (
  `codiceInventario` int NOT NULL AUTO_INCREMENT,
  `indirizzo` varchar(150) NOT NULL,
  `regione` varchar(50) NOT NULL,
  `nome` varchar(50) NOT NULL,
  `note` text,
  `cfResponsabile` varchar(16) NOT NULL,
  PRIMARY KEY (`codiceInventario`),
  KEY `FK_utente` (`cfResponsabile`),
  CONSTRAINT `FK_utente` FOREIGN KEY (`cfResponsabile`) REFERENCES `utente` (`CF`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*!40000 ALTER TABLE `inventario` DISABLE KEYS */;
/*!40000 ALTER TABLE `inventario` ENABLE KEYS */;

CREATE TABLE IF NOT EXISTS `inventario_prodotto` (
  `idInventario` int NOT NULL,
  `idProdotto` int NOT NULL,
  `giacenza` float NOT NULL,
  PRIMARY KEY (`idProdotto`,`idInventario`),
  KEY `FK__inventario` (`idInventario`),
  CONSTRAINT `FK__inventario` FOREIGN KEY (`idInventario`) REFERENCES `inventario` (`codiceInventario`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK__prodotto` FOREIGN KEY (`idProdotto`) REFERENCES `prodotto` (`codiceIAN`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*!40000 ALTER TABLE `inventario_prodotto` DISABLE KEYS */;
/*!40000 ALTER TABLE `inventario_prodotto` ENABLE KEYS */;

CREATE TABLE IF NOT EXISTS `ordine` (
  `numeroOrdine` int NOT NULL,
  `stato` enum('salvato','accettato','preparazione','spedito','in consegna','consegnato','annullato') NOT NULL,
  `feedback` text,
  `ricevutaPagamento` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `dataAcquisto` date NOT NULL,
  `cfCliente` varchar(16) NOT NULL DEFAULT '',
  `metodoSpedizione` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`numeroOrdine`),
  UNIQUE KEY `Indice 4` (`ricevutaPagamento`),
  KEY `FK_cliente` (`cfCliente`),
  KEY `FK_spedizione` (`metodoSpedizione`),
  CONSTRAINT `FK_cliente` FOREIGN KEY (`cfCliente`) REFERENCES `utente` (`CF`) ON UPDATE CASCADE,
  CONSTRAINT `FK_spedizione` FOREIGN KEY (`metodoSpedizione`) REFERENCES `spedizione` (`ID`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*!40000 ALTER TABLE `ordine` DISABLE KEYS */;
/*!40000 ALTER TABLE `ordine` ENABLE KEYS */;

CREATE TABLE IF NOT EXISTS `ordine_prodotto` (
  `idOrdine` int NOT NULL,
  `idProdotto` int NOT NULL,
  `prezzoAcquisto` float NOT NULL,
  `quantita` float NOT NULL,
  PRIMARY KEY (`idOrdine`,`idProdotto`),
  KEY `FK_ordine_prodotto_prodotto` (`idProdotto`),
  CONSTRAINT `FK_ordine_prodotto_ordine` FOREIGN KEY (`idOrdine`) REFERENCES `ordine` (`numeroOrdine`) ON UPDATE CASCADE,
  CONSTRAINT `FK_ordine_prodotto_prodotto` FOREIGN KEY (`idProdotto`) REFERENCES `prodotto` (`codiceIAN`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*!40000 ALTER TABLE `ordine_prodotto` DISABLE KEYS */;
/*!40000 ALTER TABLE `ordine_prodotto` ENABLE KEYS */;

CREATE TABLE IF NOT EXISTS `prodotto` (
  `codiceIAN` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(150) NOT NULL DEFAULT '',
  `prezzo` float unsigned NOT NULL DEFAULT '0',
  `peso` float unsigned NOT NULL DEFAULT '0',
  `foto` varchar(200) NOT NULL DEFAULT '',
  `volumeOccupato` float unsigned NOT NULL DEFAULT '0',
  `descrizione` text NOT NULL,
  `nomeCategoria` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '',
  PRIMARY KEY (`codiceIAN`),
  KEY `FK_prodotto_categoria` (`nomeCategoria`),
  CONSTRAINT `FK_prodotto_categoria` FOREIGN KEY (`nomeCategoria`) REFERENCES `categoria` (`nome`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*!40000 ALTER TABLE `prodotto` DISABLE KEYS */;
/*!40000 ALTER TABLE `prodotto` ENABLE KEYS */;

CREATE TABLE IF NOT EXISTS `spedizione` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(80) NOT NULL DEFAULT '0',
  `costo` float NOT NULL DEFAULT '0',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*!40000 ALTER TABLE `spedizione` DISABLE KEYS */;
INSERT INTO `spedizione` (`ID`, `nome`, `costo`) VALUES
	(1, 'eco', 5.99),
	(2, 'standard', 9.99),
	(3, 'express', 14.99);
/*!40000 ALTER TABLE `spedizione` ENABLE KEYS */;

CREATE TABLE IF NOT EXISTS `utente` (
  `CF` varchar(16) NOT NULL DEFAULT '',
  `nome` varchar(100) NOT NULL DEFAULT '',
  `cognome` varchar(100) NOT NULL DEFAULT '',
  `viaCivico` varchar(100) NOT NULL DEFAULT '',
  `fotoProfilo` varchar(200) DEFAULT NULL,
  `tipo` enum('Semplice','Amministratore') NOT NULL DEFAULT 'Semplice',
  `citta` varchar(100) NOT NULL DEFAULT '',
  `regione` varchar(100) NOT NULL DEFAULT '',
  `telefono` varchar(10) NOT NULL DEFAULT '',
  `dataDiNascita` date NOT NULL,
  `email` varchar(100) NOT NULL DEFAULT '',
  `token` varchar(100) NOT NULL DEFAULT '',
  `username` varchar(50) NOT NULL DEFAULT '',
  `passwordHash` varchar(100) NOT NULL DEFAULT '',
  PRIMARY KEY (`CF`),
  UNIQUE KEY `Indice 2` (`username`),
  UNIQUE KEY `Indice 3` (`token`),
  UNIQUE KEY `Indice 4` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*!40000 ALTER TABLE `utente` DISABLE KEYS */;
INSERT INTO `utente` (`CF`, `nome`, `cognome`, `viaCivico`, `fotoProfilo`, `tipo`, `citta`, `regione`, `telefono`, `dataDiNascita`, `email`, `token`, `username`, `passwordHash`) VALUES
	('DCFJPG92A23L322U', 'sabato', 'genovese', 'po,2', NULL, 'Semplice', 'visciano', 'campania', '33334888', '2000-05-05', 's@s.com', '795cbb3993ff966ec0444d87de357bb33f302897', 'sabato', '795cbb3993ff966ec0444d87de357bb33f302897'),
	('ERFDPG92A23L322U', 'admin', 'admin', 'adige,9', NULL, 'Amministratore', 'visciano', 'campania', '38974888', '2000-01-05', 'admin@admin.com', 'd033e22ae348aeb5660fc2140aec35850c4da997', 'admin', 'd033e22ae348aeb5660fc2140aec35850c4da997');
/*!40000 ALTER TABLE `utente` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
