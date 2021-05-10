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
  `nome` varchar(100) NOT NULL DEFAULT '',
  `aliquota` float NOT NULL,
  PRIMARY KEY (`nome`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*!40000 ALTER TABLE `categoria` DISABLE KEYS */;
/*!40000 ALTER TABLE `categoria` ENABLE KEYS */;

DROP TABLE IF EXISTS `coupon`;
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

DROP TABLE IF EXISTS `coupon_applicato`;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*!40000 ALTER TABLE `inventario` DISABLE KEYS */;
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

/*!40000 ALTER TABLE `inventario_prodotto` DISABLE KEYS */;
/*!40000 ALTER TABLE `inventario_prodotto` ENABLE KEYS */;

DROP TABLE IF EXISTS `ordine`;
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

/*!40000 ALTER TABLE `ordine_prodotto` DISABLE KEYS */;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*!40000 ALTER TABLE `prodotto` DISABLE KEYS */;
/*!40000 ALTER TABLE `prodotto` ENABLE KEYS */;

DROP TABLE IF EXISTS `spedizione`;
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
