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
CREATE DEFINER=`root`@`%` EVENT `AggiornamentoOrdini` ON SCHEDULE EVERY 5 MINUTE STARTS '2021-05-04 08:00:00' ENDS '2021-05-04 18:00:00' ON COMPLETION PRESERVE DISABLE DO BEGIN
	UPDATE ordini o SET o.Stato = 'consegnato' WHERE o.Stato = 'in consegna';
	UPDATE ordini o SET o.Stato = 'in consegna' WHERE o.Stato = 'spedito';
	UPDATE ordini o SET o.Stato = 'spedito' WHERE o.Stato = 'preparazione';
	UPDATE ordini o SET o.Stato = 'accettato' WHERE o.Stato = 'preparazione';
END//
DELIMITER ;

DROP TABLE IF EXISTS `categoria`;
CREATE TABLE IF NOT EXISTS `categoria` (
  `nome` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `aliquota` float NOT NULL DEFAULT '0',
  PRIMARY KEY (`nome`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*!40000 ALTER TABLE `categoria` DISABLE KEYS */;
INSERT INTO `categoria` (`nome`, `aliquota`) VALUES
	('acqua e analcolici', 5),
	('animali', 2),
	('bucato', 20),
	('carne', 15),
	('casa', 18),
	('cura della persona', 22),
	('dispensa', 15),
	('frutta', 1),
	('gastronomia', 15),
	('gelati e surgelati', 17),
	('infanzia', 10),
	('pane farine e preparati', 25),
	('pesce', 4),
	('prodotti biologici', 5),
	('salumi e formaggi', 10),
	('uova latte e latticini', 5),
	('verdura', 4),
	('vini e birre', 16);
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*!40000 ALTER TABLE `coupon` DISABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*!40000 ALTER TABLE `inventario` DISABLE KEYS */;
INSERT INTO `inventario` (`codiceInventario`, `indirizzo`, `regione`, `nome`, `note`, `cfResponsabile`) VALUES
	(1, 'via po, 3', 'Campania', 'euroCampania', 'Magazzino alimentare', 'LLFJPG92A23L322U'),
	(2, 'test', 'test', 'test', 'test', 'ERFDPG92A23L322U'),
	(6, 'via rossi,9', 'Lazio', 'LazioMart', 'Contiene anche prodotti tipici della regione.Lazio', 'LLFJPG92A23L322U'),
	(7, 'ciao', 'ewfwe', 'ewrwe', 'ehguierninvuignreiuviojasjiioijfdfhdiufckcidkodsjiofrgroehgioejiorgoegvmijviuiomok', 'LLFJPG92A23L322U');
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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*!40000 ALTER TABLE `ordine` DISABLE KEYS */;
/*!40000 ALTER TABLE `ordine` ENABLE KEYS */;

DROP TABLE IF EXISTS `ordine_prodotto`;
CREATE TABLE IF NOT EXISTS `ordine_prodotto` (
  `idOrdine` int NOT NULL,
  `idProdotto` int NOT NULL,
  `prezzoAcquisto` float NOT NULL,
  `quantita` float NOT NULL,
  PRIMARY KEY (`idOrdine`,`idProdotto`) USING BTREE,
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
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*!40000 ALTER TABLE `prodotto` DISABLE KEYS */;
INSERT INTO `prodotto` (`codiceIAN`, `nome`, `prezzo`, `peso`, `foto`, `volumeOccupato`, `descrizione`, `nomeCategoria`) VALUES
	(1, 'zucchero ', 0.9, 1, '1_zucchero.jpg', 2, 'ottimo per dolci/bevande calde', 'gastronomia'),
	(2, 'minestrone vegetale findus', 3.5, 1.5, '2_minestrone.png', 1, 'ricco di vitamine ', 'infanzia'),
	(3, 'pesto tigulio(carciofi e noci)', 2.5, 2, '3_tigullio-star.jpg', 3, 'ideale a chi piace un mix di sapori', 'dispensa'),
	(4, 'tonno', 3.8, 0.7, '4_tonno.jpg', 1, 'in una confezione ideale ovunque', 'dispensa'),
	(5, 'patate surgelate', 2.7, 1.7, '5_patate.png', 4, 'ottime in forno ', 'gelati e surgelati'),
	(6, 'fazzoletti', 1.1, 0.5, '6_tempo.jpg', 2, 'pratico molto utilizzato in viaggio', 'cura della persona'),
	(7, 'sole detersivo liquido', 2.7, 3, '7_sole.png', 2, 'ottimo per i capi bianchi', 'bucato'),
	(8, 'dentifricio', 4.2, 0.7, '8_blanx.jpg', 2, 'ottimo per lo sbiancamento dei denti ', 'cura della persona'),
	(9, 'ace candeggina', 1.7, 1, '9_ace.jpg', 3, 'utilizzare solo su panni bianchi', 'casa'),
	(10, 'crocchette cane', 3.5, 3, '10_crocchette.jpg', 5, 'per cani di grande taglia', 'animali'),
	(11, 'bagnoschiuma', 1.8, 1, '11_dove.png', 2, 'per pelli delicate', 'cura della persona'),
	(12, 'crema corpo', 1.9, 2, '12_crema.jpg', 2, 'per un\'idratazione della pelle ', 'cura della persona'),
	(13, 'merluzzo', 5.5, 1.2, '13_3D-Cap-Filetti-Merluzzo.png', 1, 'ottimo per i bambini', 'gelati e surgelati'),
	(14, 'ace pavimento', 1.9, 1, '14_82518-fancybox_default.jpg', 2, 'UTILIZZARE PER IL PAVIMENTO', 'casa'),
	(16, 'elive shampo', 2.9, 10, '16_dream-long-shampoo-ripara-lunghezze.jpg', 1, 'per capelli lunghi', 'cura della persona'),
	(17, 'patate vivaci', 1.5, 1, '17_l_20651.jpg', 2, 'un mix di sapori', 'dispensa'),
	(18, 'carne di bovino', 6, 1.5, '18_eg-0021614_1_big.jpg', 5, 'buona alla brace', 'carne'),
	(19, 'deodorante dove', 2.8, 0.25, '19_big_167236_066184_02_nkyuy1zjkyc7hltv.jpg', 6, 'per una profumazione migliore', 'cura della persona'),
	(20, 'pasta divella', 1.5, 1, '20_0000079161.jpg', 4, 'ideale per mille idee', 'dispensa'),
	(21, 'sale', 0.85, 1, '21_CG0155-1.jpg', 3, 'utilizzato per tutto', 'dispensa'),
	(22, 'pan carrè', 1.5, 2, '22_PANCARRE24FETTEMULINOB-8076809530842-1.png', 5, 'ideale per mille idee', 'dispensa'),
	(23, 'olio per friggere', 1.95, 1.5, '23_31960_2.jpg', 4, 'utilizzato per tutto', 'dispensa'),
	(24, 'mirtilli', 1.5, 1.6, '24_IMMAGINO_IMMAGINO_EAN8028873001728_1_renditions_600x600.png', 5, 'ideale per dolci', 'frutta'),
	(25, 'banane', 1.78, 1.7, '25_Chiquita_Banana_Class_Extra_Yellow.jpg', 3, 'ricca di vitamine', 'frutta'),
	(26, 'mandorle ', 2.41, 3, '26_0000168662-1024x1024.jpg', 2, 'ricco di vitamine', 'frutta'),
	(27, 'pizza surgelata', 3.14, 2, '27_PIZZAMARGHERITABELLANAPOLI-8000300391961-1.png', 2, 'rapida e veloce', 'gelati e surgelati'),
	(28, 'mais in scatola', 2.3, 2.3, '28_mais-bonduelle-in-scatola-300-g-.jpg', 3, 'per mille ricette', 'gastronomia'),
	(29, 'olive verdi', 1.5, 2, '29_phoca_thumb_l_2biolverd250ec_a.jpg', 4, 'pratiche e veloci', 'verdura'),
	(30, 'farina barilla', 0.9, 2, '30_279FARINA00BARILLA-8076802795019-1.png', 3, 'utilizzata per dolci e pizza', 'casa'),
	(31, 'gelato nuji', 3.8, 3, '31_Nuii_Multipack_x4_SaltedCaramelAustralianMacadamia_IT-980x748.jpg', 2, 'per chi ama il caramello e non puÃ² farne almeno', 'gelati e surgelati'),
	(32, 'petto di pollo', 6.82, 6, '32_PAM_442323_MINIFILETTO-POLLO-PP.jpg', 5, 'per cotolette ', 'gastronomia'),
	(33, 'piselli findus', 5.5, 2, '33_191206_pack piselli novelli 450.png', 2, 'buoni', 'gelati e surgelati'),
	(34, 'fagioli cannellini', 2.3, 1, '34_cannellini-vaso-vetro-new.jpg', 3, 'ottimi per qualsiasi cosa', 'dispensa'),
	(35, 'lenticchie secche', 3.01, 2, '35_lenticchie-verdi.jpg', 3, 'ottime per gli amanti dei legumi', 'gastronomia'),
	(36, 'cotechino', 4.2, 3, '36_G7122 NEGRONI Grancotechino.png', 3, 'un sapore inaspettato', 'carne'),
	(37, 'nascondini mulino bianco', 2.5, 3, '37_0000187449.jpg', 5, 'un mix di biscotti e cioccolato', 'gastronomia'),
	(38, 'caramelle haribo', 0.39, 2, '38_l_60940.jpg', 2, 'mix di gusto', 'gastronomia'),
	(39, 'cioccolata lindt', 3.5, 4, '39_l_21773.jpg', 3, 'un viaggio nel sapore della cioccolata', 'dispensa'),
	(40, 'mix party', 2.62, 3, '40_Snack Friends Bretzel 100 g.png', 4, 'un mix di sa', 'acqua e analcolici'),
	(41, 'mix party', 2.62, 3, '41_Snack Friends Bretzel 100 g.png', 4, 'un mix di salato', 'pane farine e preparati'),
	(42, 'plumcake', 2.85, 0.4, '42_083443-mulino-bianco-plumcake-classico-allo-yogurt-confezione-da-10-plumcake---350-grammi-totali.jpg', 5, 'ottimi per la colazione', 'gastronomia'),
	(43, 'origano', 4.15, 3, '43_Origano-secco-frantumato-per-salumi-salmistrati-Dama.jpg', 3, 'ottimno per pane e fagioli', 'frutta'),
	(44, 'cipolle', 3.5, 0.5, '44_sacchetto-carta-dorata-943x1024.jpg', 6, 'ideale per il brodo', 'prodotti biologici'),
	(45, 'bastoncini findus', 3.6, 6, '45_70600236.jpg', 7, 'merluzzo inpanato', 'pesce'),
	(46, 'crocchÃ¨', 1.9, 0.9, '46_pim-00000008002411402055-main-20201002-140225.jpg', 5, 'pronti in poco tempo', 'gelati e surgelati'),
	(47, 'pane', 0.89, 0.1, '47_baguettes-precotte-dailybread-2-panini-300gr.jpg', 4, 'pratico da portare anche in viaggio', 'pane farine e preparati'),
	(48, 'cereali', 3.9, 1.5, '48_2974-home_default.jpg', 4, 'ideale per la colazione', 'dispensa'),
	(49, 'chilly intimo', 4.1, 2.1, '49_chilly-detergente-intimo-delicato-formula-lenitiva.jpg', 2, 'per intimo ', 'cura della persona'),
	(50, 'sgrassatore ', 3.41, 0.7, '50_51X1F1n5IIL._AC_SY879_.jpg', 5, 'adatto per mille usi', 'casa'),
	(51, 'nelsen piatti', 0.78, 0.7, '51_71f4pa9VJCL._AC_SS450_.jpg', 4, 'ottimo per il lavaggio dei piatti', 'casa'),
	(52, 'spugnette piatti', 0.78, 2, '52_unnamed.jpg', 1, 'utilizzare per i piatti', 'casa'),
	(53, 'riso ', 4.5, 1.7, '53_81vKrhUZuYL._AC_SX679_.jpg', 5, 'ottimo per il risotto', 'dispensa'),
	(54, 'capsule dixan', 3.5, 2, '54_l_62446.jpg', 4, 'pratica e veloce', 'bucato'),
	(55, 'carta igienica', 2.5, 3, '55_01.jpg', 4, 'utilizzata per il bagno', 'casa'),
	(56, 'carta argentata', 1.9, 2, '56_l_3551.jpg', 1, 'ottimo', 'dispensa'),
	(57, 'carta forno', 2, 4, '57_614c8x6j8QL._AC_SS450_.jpg', 3, 'ottimo da utillizare per il forno', 'casa'),
	(58, 'pellicola', 3.4, 2, '58_069767c1-9ecb-44e4-8c18-2f9b90e3d2ad.jpg', 6, 'ideale per il frigo', 'dispensa'),
	(59, 'pan grattato', 3.1, 2, '59_0000000064-1024x1024.jpg', 1, 'ottimo per le cotolette', 'pane farine e preparati'),
	(60, 'thÃ¨ limone', 1.4, 4, '60_l_23393.jpg', 5, 'ottimo per l\'estate', 'gastronomia');
/*!40000 ALTER TABLE `prodotto` ENABLE KEYS */;

DROP TABLE IF EXISTS `prodotto_preferito`;
CREATE TABLE IF NOT EXISTS `prodotto_preferito` (
  `codiceIAN` int DEFAULT NULL,
  `nome` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `prezzo` float unsigned DEFAULT NULL,
  `peso` float unsigned DEFAULT NULL,
  `foto` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `volumeOccupato` float unsigned DEFAULT NULL,
  `descrizione` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci,
  `nomeCategoria` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `Quantità Totale Acquistata` double DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*!40000 ALTER TABLE `prodotto_preferito` DISABLE KEYS */;
/*!40000 ALTER TABLE `prodotto_preferito` ENABLE KEYS */;

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

DROP TABLE IF EXISTS `spedizione_preferita`;
CREATE TABLE IF NOT EXISTS `spedizione_preferita` (
  `ID` int DEFAULT NULL,
  `nome` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `costo` float DEFAULT NULL,
  `Utilizzi` bigint DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*!40000 ALTER TABLE `spedizione_preferita` DISABLE KEYS */;
/*!40000 ALTER TABLE `spedizione_preferita` ENABLE KEYS */;

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

/*!40000 ALTER TABLE `utente` DISABLE KEYS */;
INSERT INTO `utente` (`CF`, `nome`, `cognome`, `viaCivico`, `fotoProfilo`, `tipo`, `citta`, `regione`, `telefono`, `dataDiNascita`, `email`, `username`, `passwordHash`) VALUES
	('cfcf', 'cf', 'cf', 'cf', 'cfcf_20180327_110324.jpg', 'Semplice', 'cf', 'cf', '123', '2020-08-25', 'cf@cf.com', 'cf', 'f78b64c9e0f2ea24fddce2b0d809cb2855fed1a6'),
	('DCFJPG92A23L322U', 'sabato', 'genovese', 'po,2', NULL, 'Semplice', 'visciano', 'campania', '33334888', '2000-05-05', 's@s.com', 'sabato', '795cbb3993ff966ec0444d87de357bb33f302897'),
	('ERFDPG92A23L322U', 'admin', 'admin', 'adige,9', NULL, 'Semplice', 'visciano', 'campania', '38974888', '2000-01-05', 'admin@admin.com', 'admin', 'd033e22ae348aeb5660fc2140aec35850c4da997'),
	('Gehuebevehdb', 'Bho', 'Come ', 'Domenico 5', 'Gehuebevehdb_image.jpg', 'Semplice', 'Napoli ', 'Campania ', '3333557834', '2003-07-11', 'monica@gmail.com', 'Monica ', '4b4446969034c7b0cee153226a398194c133f65b'),
	('LLFJPG92A23L322U', 'test', 'test', 'via test', 'LLFJPG92A23L322U_admin.svg', 'Amministratore', 'Visciano', 'Campania', '4444', '2022-06-20', 'test@test', 'test', 'a94a8fe5ccb19ba61c4c0873d391e987982fbbd3'),
	('MSFLTF44P15C400R', 'sabato', 'genovese', 'po,2', 'logo_small_icon_only_inverted.png', 'Semplice', 'nola', 'campania', '1234567890', '2000-07-05', 'sa@sa.com', 'tino', '795cbb3993ff966ec0444d87de357bb33f302897'),
	('sabatoG', 'sabato', 'genovese', 'sg', 'sabatoG_20180327_110324.jpg', 'Semplice', 'ss', 'sss', '123456', '2019-07-26', 'sabato@genovese.com', 'sg', 'ff39796487e85a7066e18d814bcb63856de6cfff'),
	('tttttt', 'ttttt', 'tttt', 'tttt', 'tttttt_20180327_110331.jpg', 'Semplice', 'tttt', 'tttt', '123456', '2019-07-25', 'tet@tetw.com', '00', 'b6589fc6ab0dc82cf12099d1c2d40ab994e8410c'),
	('we', 'we', 'we', 'we', 'we_wallpaper.jpg', 'Semplice', 'nola', 'campania', '3333333', '2019-06-27', 'we@we.it', 'we', '676e6f35cfc173f73fea9fe27699cf8185397f0c');
/*!40000 ALTER TABLE `utente` ENABLE KEYS */;

DROP TRIGGER IF EXISTS `coupon_applicato_after_insert`;
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE DEFINER=`root`@`%` TRIGGER `coupon_applicato_after_insert` AFTER INSERT ON `coupon_applicato` FOR EACH ROW BEGIN
	UPDATE coupon c SET c.stato = 'Riscattato' WHERE c.numeroCoupon = NEW.idCoupon; 
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

DROP TRIGGER IF EXISTS `coupon_before_delete`;
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE DEFINER=`root`@`%` TRIGGER `coupon_before_delete` AFTER DELETE ON `coupon_applicato` FOR EACH ROW BEGIN
	UPDATE coupon c SET c.stato = 'Disponibile' WHERE c.numeroCoupon = OLD.idCoupon; 
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

DROP TRIGGER IF EXISTS `NormalizeCategoriaInsert`;
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE DEFINER=`root`@`%` TRIGGER `NormalizeCategoriaInsert` BEFORE INSERT ON `categoria` FOR EACH ROW BEGIN
	SET new.nome = LOWER(new.nome);
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

DROP TRIGGER IF EXISTS `NormalizeCategoriaUpdate`;
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE DEFINER=`root`@`%` TRIGGER `NormalizeCategoriaUpdate` BEFORE UPDATE ON `categoria` FOR EACH ROW BEGIN
SET new.nome = LOWER(new.nome);
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
