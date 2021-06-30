/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

DROP DATABASE IF EXISTS `unimart`;
CREATE DATABASE IF NOT EXISTS `unimart` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `unimart`;

DROP EVENT IF EXISTS `AggiornamentoOrdini`;
DELIMITER //
CREATE EVENT `AggiornamentoOrdini` ON SCHEDULE EVERY 5 MINUTE STARTS '2021-05-04 08:00:00' ENDS '2021-05-04 18:00:00' ON COMPLETION PRESERVE DISABLE DO BEGIN
	UPDATE ordini o SET o.Stato = 'consegnato' WHERE o.Stato = 'in consegna';
	UPDATE ordini o SET o.Stato = 'in consegna' WHERE o.Stato = 'spedito';
	UPDATE ordini o SET o.Stato = 'spedito' WHERE o.Stato = 'preparazione';
	UPDATE ordini o SET o.Stato = 'accettato' WHERE o.Stato = 'preparazione';
END//
DELIMITER ;

DROP TABLE IF EXISTS `categoria`;
CREATE TABLE IF NOT EXISTS `categoria` (
  `nome` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `aliquota` float NOT NULL,
  PRIMARY KEY (`nome`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DELETE FROM `categoria`;
/*!40000 ALTER TABLE `categoria` DISABLE KEYS */;
INSERT INTO `categoria` (`nome`, `aliquota`) VALUES
	('alimentari', 20),
	('biscotti', 15),
	('formaggi', 14),
	('frutta', 11),
	('frutta secca', 10),
	('pasta', 3);
/*!40000 ALTER TABLE `categoria` ENABLE KEYS */;

DROP TABLE IF EXISTS `coupon`;
CREATE TABLE IF NOT EXISTS `coupon` (
  `numeroCoupon` int NOT NULL AUTO_INCREMENT,
  `stato` enum('Riscattato','Disponibile') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'Disponibile',
  `sconto` float NOT NULL,
  `cfCreatore` varchar(16) NOT NULL DEFAULT '',
  PRIMARY KEY (`numeroCoupon`),
  KEY `FK__utente` (`cfCreatore`),
  CONSTRAINT `FK__utente` FOREIGN KEY (`cfCreatore`) REFERENCES `utente` (`CF`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DELETE FROM `coupon`;
/*!40000 ALTER TABLE `coupon` DISABLE KEYS */;
INSERT INTO `coupon` (`numeroCoupon`, `stato`, `sconto`, `cfCreatore`) VALUES
	(3, 'Disponibile', 10, 'ERFDPG92A23L322U'),
	(4, 'Disponibile', 100, 'ERFDPG92A23L322U'),
	(5, 'Disponibile', 1, 'ERFDPG92A23L322U'),
	(6, 'Disponibile', 3, 'ERFDPG92A23L322U'),
	(7, 'Disponibile', 2, 'ERFDPG92A23L322U'),
	(8, 'Disponibile', 4, 'ERFDPG92A23L322U'),
	(9, 'Disponibile', 6, 'ERFDPG92A23L322U'),
	(10, 'Disponibile', 7, 'ERFDPG92A23L322U'),
	(11, 'Disponibile', 7, 'ERFDPG92A23L322U'),
	(12, 'Disponibile', 12, 'ERFDPG92A23L322U'),
	(13, 'Disponibile', 50, 'ERFDPG92A23L322U'),
	(14, 'Disponibile', 60, 'ERFDPG92A23L322U'),
	(17, 'Disponibile', 0.5, 'ERFDPG92A23L322U'),
	(18, 'Disponibile', 6, 'ERFDPG92A23L322U'),
	(19, 'Disponibile', 59, 'ERFDPG92A23L322U'),
	(20, 'Disponibile', 2, 'ERFDPG92A23L322U'),
	(39, 'Disponibile', 89, 'test'),
	(42, 'Disponibile', 15, 'test'),
	(44, 'Riscattato', 90, 'test'),
	(47, 'Disponibile', 23, 'test'),
	(48, 'Disponibile', 19, 'test'),
	(49, 'Disponibile', 18, 'test');
/*!40000 ALTER TABLE `coupon` ENABLE KEYS */;

DROP TABLE IF EXISTS `coupon_applicato`;
CREATE TABLE IF NOT EXISTS `coupon_applicato` (
  `idCoupon` int NOT NULL,
  `idOrdine` int NOT NULL,
  PRIMARY KEY (`idCoupon`),
  KEY `FK_ordine` (`idOrdine`),
  CONSTRAINT `FK_coupon` FOREIGN KEY (`idCoupon`) REFERENCES `coupon` (`numeroCoupon`) ON UPDATE CASCADE,
  CONSTRAINT `FK_ordine` FOREIGN KEY (`idOrdine`) REFERENCES `ordine` (`numeroOrdine`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DELETE FROM `coupon_applicato`;
/*!40000 ALTER TABLE `coupon_applicato` DISABLE KEYS */;
INSERT INTO `coupon_applicato` (`idCoupon`, `idOrdine`) VALUES
	(44, 1);
/*!40000 ALTER TABLE `coupon_applicato` ENABLE KEYS */;

DROP TABLE IF EXISTS `inventario`;
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
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DELETE FROM `inventario`;
/*!40000 ALTER TABLE `inventario` DISABLE KEYS */;
INSERT INTO `inventario` (`codiceInventario`, `indirizzo`, `regione`, `nome`, `note`, `cfResponsabile`) VALUES
	(1, 'via po, 2', 'Campania', 'euroCampania', 'Magazzino alimentare', 'test'),
	(2, 'test', 'test', 'test', 'test', 'ERFDPG92A23L322U'),
	(6, 'via rossi,9', 'Lazio', 'LazioMart', 'Contiene anche prodotti tipici della regione.         ', 'test'),
	(7, 'ciao', 'ewfwe', 'ewrwe', '                                    \r\n         werewerherugeirpngvujeuidv uierguiheruighuiehrgiurehiugheriughieurpgrehvuinnfvieurhgirneviugierhuignreiugvrehguierninvuignreiuviojasjiioijfdfhdiufckcidkodsjiofrgroehgioejiorgoegvmijviuiomerijvoejrf9jegao\r\n         \r\n         ', 'test');
/*!40000 ALTER TABLE `inventario` ENABLE KEYS */;

DROP TABLE IF EXISTS `inventario_prodotto`;
CREATE TABLE IF NOT EXISTS `inventario_prodotto` (
  `idInventario` int NOT NULL,
  `idProdotto` int NOT NULL,
  `giacenza` float NOT NULL,
  PRIMARY KEY (`idProdotto`,`idInventario`),
  KEY `FK__inventario` (`idInventario`),
  CONSTRAINT `FK__inventario` FOREIGN KEY (`idInventario`) REFERENCES `inventario` (`codiceInventario`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK__prodotto` FOREIGN KEY (`idProdotto`) REFERENCES `prodotto` (`codiceIAN`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DELETE FROM `inventario_prodotto`;
/*!40000 ALTER TABLE `inventario_prodotto` DISABLE KEYS */;
/*!40000 ALTER TABLE `inventario_prodotto` ENABLE KEYS */;

DROP TABLE IF EXISTS `ordine`;
CREATE TABLE IF NOT EXISTS `ordine` (
  `numeroOrdine` int NOT NULL AUTO_INCREMENT,
  `stato` enum('salvato','accettato','preparazione','spedito','in consegna','consegnato','annullato') NOT NULL,
  `feedback` text,
  `ricevutaPagamento` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `dataAcquisto` timestamp NOT NULL,
  `cfCliente` varchar(16) NOT NULL DEFAULT '',
  `metodoSpedizione` int NOT NULL DEFAULT '0',
  `regione` varchar(100) NOT NULL,
  `citta` varchar(100) NOT NULL,
  `viaCivico` varchar(100) NOT NULL,
  PRIMARY KEY (`numeroOrdine`),
  UNIQUE KEY `Indice 4` (`ricevutaPagamento`),
  KEY `FK_cliente` (`cfCliente`),
  KEY `FK_spedizione` (`metodoSpedizione`),
  CONSTRAINT `FK_cliente` FOREIGN KEY (`cfCliente`) REFERENCES `utente` (`CF`) ON UPDATE CASCADE,
  CONSTRAINT `FK_spedizione` FOREIGN KEY (`metodoSpedizione`) REFERENCES `spedizione` (`ID`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DELETE FROM `ordine`;
/*!40000 ALTER TABLE `ordine` DISABLE KEYS */;
INSERT INTO `ordine` (`numeroOrdine`, `stato`, `feedback`, `ricevutaPagamento`, `dataAcquisto`, `cfCliente`, `metodoSpedizione`, `regione`, `citta`, `viaCivico`) VALUES
	(1, 'salvato', 'ok', '000', '2021-06-12 18:52:11', 'we', 1, 'Campania', 'nola', 'via po,3'),
	(2, 'preparazione', 'bene', '123', '2021-06-12 18:52:11', 'we', 3, 'Campania', 'visciano', 'via po,3'),
	(3, 'spedito', 'bene', '9999', '2021-06-12 18:52:11', 'we', 3, 'Campania', 'visciano', 'via po,3');
/*!40000 ALTER TABLE `ordine` ENABLE KEYS */;

DROP TABLE IF EXISTS `ordine_prodotto`;
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

DELETE FROM `ordine_prodotto`;
/*!40000 ALTER TABLE `ordine_prodotto` DISABLE KEYS */;
INSERT INTO `ordine_prodotto` (`idOrdine`, `idProdotto`, `prezzoAcquisto`, `quantita`) VALUES
	(1, 1, 14, 3),
	(1, 2, 1.8, 4),
	(2, 1, 11, 2),
	(3, 1, 10, 2);
/*!40000 ALTER TABLE `ordine_prodotto` ENABLE KEYS */;

DROP TABLE IF EXISTS `prodotto`;
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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DELETE FROM `prodotto`;
/*!40000 ALTER TABLE `prodotto` DISABLE KEYS */;
INSERT INTO `prodotto` (`codiceIAN`, `nome`, `prezzo`, `peso`, `foto`, `volumeOccupato`, `descrizione`, `nomeCategoria`) VALUES
	(1, 'abbracci', 15.15, 10, '1_inventario.jpg', 2, 'biscotti buoni', 'biscotti'),
	(2, 'Mandorle', 1.5, 1.9, '2_inventario.jpg', 2, 'Buone da mangiare', 'frutta secca'),
	(3, 'fettuccine', 1.5, 6, '2_inventario.jpg', 3, 'Buone da mangiare', 'pasta');
/*!40000 ALTER TABLE `prodotto` ENABLE KEYS */;

DROP VIEW IF EXISTS `prodotto_preferito`;
CREATE TABLE `prodotto_preferito` (
	`idProdotto` INT(10) NULL,
	`Prodotto Acquistato Maggiormente` DOUBLE NULL
) ENGINE=MyISAM;

DROP TABLE IF EXISTS `spedizione`;
CREATE TABLE IF NOT EXISTS `spedizione` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(80) NOT NULL DEFAULT '0',
  `costo` float NOT NULL DEFAULT '0',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DELETE FROM `spedizione`;
/*!40000 ALTER TABLE `spedizione` DISABLE KEYS */;
INSERT INTO `spedizione` (`ID`, `nome`, `costo`) VALUES
	(1, 'eco', 5.99),
	(2, 'standard', 9.99),
	(3, 'express', 14.99);
/*!40000 ALTER TABLE `spedizione` ENABLE KEYS */;

DROP VIEW IF EXISTS `spedizione_preferita`;
CREATE TABLE `spedizione_preferita` (
	`metodoSpedizione` INT(10) NULL,
	`Spedizione Scelta Maggiormente` BIGINT(19) NULL
) ENGINE=MyISAM;

DROP TABLE IF EXISTS `utente`;
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
  `username` varchar(50) NOT NULL DEFAULT '',
  `passwordHash` varchar(100) NOT NULL DEFAULT '',
  PRIMARY KEY (`CF`),
  UNIQUE KEY `Indice 2` (`username`),
  UNIQUE KEY `Indice 4` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DELETE FROM `utente`;
/*!40000 ALTER TABLE `utente` DISABLE KEYS */;
INSERT INTO `utente` (`CF`, `nome`, `cognome`, `viaCivico`, `fotoProfilo`, `tipo`, `citta`, `regione`, `telefono`, `dataDiNascita`, `email`, `username`, `passwordHash`) VALUES
	('cfcf', 'cf', 'cf', 'cf', 'cfcf_20180327_110324.jpg', 'Semplice', 'cf', 'cf', '123', '2020-08-25', 'cf@cf.com', 'cf', 'f78b64c9e0f2ea24fddce2b0d809cb2855fed1a6'),
	('DCFJPG92A23L322U', 'sabato', 'genovese', 'po,2', NULL, 'Semplice', 'visciano', 'campania', '33334888', '2000-05-05', 's@s.com', 'sabato', '795cbb3993ff966ec0444d87de357bb33f302897'),
	('ERFDPG92A23L322U', 'admin', 'admin', 'adige,9', NULL, 'Amministratore', 'visciano', 'campania', '38974888', '2000-01-05', 'admin@admin.com', 'admin', 'd033e22ae348aeb5660fc2140aec35850c4da997'),
	('MSFLTF44P15C400R', 'sabato', 'genovese', 'po,2', 'logo_small_icon_only_inverted.png', 'Semplice', 'nola', 'campania', '1234567890', '2000-07-05', 'sa@sa.com', 'tino', '795cbb3993ff966ec0444d87de357bb33f302897'),
	('sabatoG', 'sabato', 'genovese', 'sg', 'sabatoG_20180327_110324.jpg', 'Semplice', 'ss', 'sss', '123456', '2019-07-26', 'sabato@genovese.com', 'sg', 'ff39796487e85a7066e18d814bcb63856de6cfff'),
	('test', 'test', 'test', 'test', 'test_wallpaper.jpg', 'Amministratore', 'Visciano', 'Campania', '1234', '2022-06-20', 'test@test', 'test', 'a94a8fe5ccb19ba61c4c0873d391e987982fbbd3'),
	('tttttt', 'ttttt', 'tttt', 'tttt', 'tttttt_20180327_110331.jpg', 'Semplice', 'tttt', 'tttt', '123456', '2019-07-25', 'tet@tetw.com', '00', 'b6589fc6ab0dc82cf12099d1c2d40ab994e8410c'),
	('we', 'we', 'we', 'we', 'we_wallpaper.jpg', 'Semplice', 'we', 'campania', '3333333', '2019-06-27', 'we@we.it', 'we', '676e6f35cfc173f73fea9fe27699cf8185397f0c');
/*!40000 ALTER TABLE `utente` ENABLE KEYS */;

DROP TRIGGER IF EXISTS `coupon_applicato_after_insert`;
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `coupon_applicato_after_insert` AFTER INSERT ON `coupon_applicato` FOR EACH ROW BEGIN
	UPDATE coupon c SET c.stato = 'Riscattato' WHERE c.numeroCoupon = NEW.idCoupon; 
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

DROP TRIGGER IF EXISTS `coupon_before_delete`;
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `coupon_before_delete` AFTER DELETE ON `coupon_applicato` FOR EACH ROW BEGIN
	UPDATE coupon c SET c.stato = 'Disponibile' WHERE c.numeroCoupon = OLD.idCoupon; 
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

DROP TRIGGER IF EXISTS `NormalizeCategoriaInsert`;
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `NormalizeCategoriaInsert` BEFORE INSERT ON `categoria` FOR EACH ROW BEGIN
	SET new.nome = LOWER(new.nome);
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

DROP TRIGGER IF EXISTS `NormalizeCategoriaUpdate`;
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `NormalizeCategoriaUpdate` BEFORE UPDATE ON `categoria` FOR EACH ROW BEGIN
SET new.nome = LOWER(new.nome);
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

DROP VIEW IF EXISTS `prodotto_preferito`;
DROP TABLE IF EXISTS `prodotto_preferito`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `prodotto_preferito` AS select `temp`.`idProdotto` AS `idProdotto`,max(`temp`.`QuantitàTotale`) AS `Prodotto Acquistato Maggiormente` from (select `op`.`idProdotto` AS `idProdotto`,sum(`op`.`quantita`) AS `QuantitàTotale` from (`ordine_prodotto` `op` left join `prodotto` `p` on((`p`.`codiceIAN` = `op`.`idProdotto`))) group by `op`.`idProdotto`) `temp`;

DROP VIEW IF EXISTS `spedizione_preferita`;
DROP TABLE IF EXISTS `spedizione_preferita`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `spedizione_preferita` AS select `temp`.`metodoSpedizione` AS `metodoSpedizione`,max(`temp`.`Utilizzi`) AS `Spedizione Scelta Maggiormente` from (select `o`.`metodoSpedizione` AS `metodoSpedizione`,count(0) AS `Utilizzi` from `ordine` `o` group by `o`.`metodoSpedizione`) `temp`;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
