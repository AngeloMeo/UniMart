-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Versione server:              8.0.24 - MySQL Community Server - GPL
-- S.O. server:                  Win64
-- HeidiSQL Versione:            11.3.0.6295
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

-- Dump dei dati della tabella unimart.categoria: ~18 rows (circa)
DELETE FROM `categoria`;
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

-- Dump dei dati della tabella unimart.coupon: ~2 rows (circa)
DELETE FROM `coupon`;
/*!40000 ALTER TABLE `coupon` DISABLE KEYS */;
INSERT INTO `coupon` (`numeroCoupon`, `stato`, `sconto`, `cfCreatore`) VALUES
	(1, 'Riscattato', 10, 'LLFJPG92A23L322U'),
	(7, 'Disponibile', 10, 'LLFJPG92A23L322U');
/*!40000 ALTER TABLE `coupon` ENABLE KEYS */;

-- Dump dei dati della tabella unimart.coupon_applicato: ~0 rows (circa)
DELETE FROM `coupon_applicato`;
/*!40000 ALTER TABLE `coupon_applicato` DISABLE KEYS */;
INSERT INTO `coupon_applicato` (`idCoupon`, `idOrdine`) VALUES
	(1, 1);
/*!40000 ALTER TABLE `coupon_applicato` ENABLE KEYS */;

-- Dump dei dati della tabella unimart.inventario: ~4 rows (circa)
DELETE FROM `inventario`;
/*!40000 ALTER TABLE `inventario` DISABLE KEYS */;
INSERT INTO `inventario` (`codiceInventario`, `indirizzo`, `regione`, `nome`, `note`, `cfResponsabile`) VALUES
	(1, 'via po, 3', 'Campania', 'euroCampania', 'Magazzino alimentare', 'LLFJPG92A23L322U'),
	(2, 'test', 'test', 'test', 'test', 'ERFDPG92A23L322U'),
	(6, 'via rossi,9', 'Lazio', 'LazioMart', 'Contiene anche prodotti tipici della regione.Lazio', 'LLFJPG92A23L322U'),
	(7, 'ciao', 'ewfwe', 'ewrwe', 'ehguierninvuignreiuviojasjiioijfdfhdiufckcidkodsjiofrgroehgioejiorgoegvmijviuiomok', 'LLFJPG92A23L322U');
/*!40000 ALTER TABLE `inventario` ENABLE KEYS */;

-- Dump dei dati della tabella unimart.inventario_prodotto: ~33 rows (circa)
DELETE FROM `inventario_prodotto`;
/*!40000 ALTER TABLE `inventario_prodotto` DISABLE KEYS */;
INSERT INTO `inventario_prodotto` (`idInventario`, `idProdotto`, `giacenza`) VALUES
	(1, 1, 5),
	(6, 1, 15),
	(7, 1, 0),
	(1, 2, 1),
	(6, 2, 33),
	(1, 3, 3),
	(6, 3, 11),
	(1, 4, 10),
	(6, 4, 35),
	(6, 5, 55),
	(1, 6, 29),
	(6, 6, 7),
	(7, 6, 13),
	(6, 7, 99),
	(6, 8, 344),
	(6, 9, 7),
	(6, 10, 20),
	(7, 10, 9),
	(7, 11, 39),
	(1, 12, 11),
	(6, 12, 77),
	(1, 13, 10),
	(6, 13, 44),
	(6, 14, 55),
	(6, 16, 12),
	(7, 17, 11),
	(1, 19, 1),
	(6, 21, 3),
	(7, 21, 5),
	(6, 24, 2),
	(7, 25, 78),
	(6, 27, 10),
	(2, 28, 2),
	(7, 29, 1),
	(6, 31, 10),
	(7, 31, 4),
	(6, 35, 54),
	(7, 35, 44),
	(6, 37, 15),
	(6, 42, 14),
	(6, 49, 1),
	(6, 50, 11),
	(6, 57, 33),
	(1, 58, 15),
	(1, 59, 15),
	(1, 60, 16);
/*!40000 ALTER TABLE `inventario_prodotto` ENABLE KEYS */;

-- Dump dei dati della tabella unimart.ordine: ~6 rows (circa)
DELETE FROM `ordine`;
/*!40000 ALTER TABLE `ordine` DISABLE KEYS */;
INSERT INTO `ordine` (`numeroOrdine`, `stato`, `feedback`, `ricevutaPagamento`, `dataAcquisto`, `cfCliente`, `metodoSpedizione`, `regione`, `citta`, `viaCivico`) VALUES
	(1, 'consegnato', 'tutto ok', '1', '2021-07-20 00:00:00', 'we', 3, 'campania', 'nola', 'via po,2'),
	(2, 'consegnato', '', '123', '2021-07-23 00:00:00', 'LLFJPG92A23L322U', 1, 'Campania', 'Visciano', 'via giotto,5'),
	(3, 'annullato', '', 'a7fbbad7be1568484fbbbde283e0e546a33692bd', '2021-07-23 00:00:00', 'LLFJPG92A23L322U', 1, 'Campania', 'Visciano', 'via giotto,5'),
	(4, 'annullato', '', 'c2a417f171748b29d3fd116d511b2631f65db8ad', '2021-07-23 00:00:00', 'LLFJPG92A23L322U', 1, 'Campania', 'Visciano', 'via giotto,5'),
	(5, 'consegnato', '', 'b9ff665fecc53a6fec054fba3733dba4c99a15d5', '2021-07-23 00:00:00', 'LLFJPG92A23L322U', 1, 'Campania', 'Visciano', 'via giotto,5'),
	(6, 'annullato', '', 'ed6b9f1afe31cd50ca9039c093a58dce33e7cf82', '2021-07-23 00:00:00', 'LLFJPG92A23L322U', 1, 'Campania', 'Visciano', 'via giotto,5'),
	(14, 'accettato', '', '4c3cc49df6fc1d94fc286a1be32ca6f2190fba76', '2021-07-25 00:00:00', 'MEOGPP61H27F839B', 3, 'campania', 'san paolo bel sito', 'f. scala 60');
/*!40000 ALTER TABLE `ordine` ENABLE KEYS */;

-- Dump dei dati della tabella unimart.ordine_prodotto: ~15 rows (circa)
DELETE FROM `ordine_prodotto`;
/*!40000 ALTER TABLE `ordine_prodotto` DISABLE KEYS */;
INSERT INTO `ordine_prodotto` (`idOrdine`, `idProdotto`, `prezzoAcquisto`, `quantita`) VALUES
	(1, 14, 10, 1),
	(1, 39, 14, 3),
	(1, 49, 12, 10),
	(2, 10, 3.5, 1),
	(3, 27, 3.14, 1),
	(4, 27, 3.14, 1),
	(5, 10, 3.5, 1),
	(5, 27, 3.14, 1),
	(6, 6, 1.1, 1),
	(6, 8, 4.2, 1),
	(6, 11, 1.8, 1),
	(6, 12, 1.9, 1),
	(6, 16, 2.9, 1),
	(6, 19, 2.8, 1),
	(6, 49, 4.1, 1),
	(14, 20, 1.5, 1),
	(14, 24, 1.5, 1),
	(14, 39, 3.5, 1);
/*!40000 ALTER TABLE `ordine_prodotto` ENABLE KEYS */;

-- Dump dei dati della tabella unimart.prodotto: ~59 rows (circa)
DELETE FROM `prodotto`;
/*!40000 ALTER TABLE `prodotto` DISABLE KEYS */;
INSERT INTO `prodotto` (`codiceIAN`, `nome`, `prezzo`, `peso`, `foto`, `volumeOccupato`, `descrizione`, `nomeCategoria`) VALUES
	(1, 'Zucchero ', 0.9, 1, '1_zucchero.jpg', 2, 'Ottimo per dolci/bevande calde', 'gastronomia'),
	(2, 'Minestrone vegetale Findus', 3.5, 1.5, '2_minestrone.png', 1, 'Ricco di vitamine ', 'infanzia'),
	(3, 'Pesto Tigullio', 2.5, 2, '3_tigullio-star.jpg', 3, 'Pesto con carciofi e noci', 'dispensa'),
	(4, 'Tonno', 3.8, 0.7, '4_tonno.jpg', 1, 'All\'olio', 'dispensa'),
	(5, 'Patate surgelate', 2.7, 1.7, '5_patate.png', 4, 'Ottime in forno ', 'gelati e surgelati'),
	(6, 'Fazzoletti', 1.1, 0.5, '6_tempo.jpg', 2, 'Monovelo', 'cura della persona'),
	(7, 'Sole detersivo liquido', 2.7, 3, '7_sole.png', 2, 'Consigliato per i capi bianchi', 'bucato'),
	(8, 'Dentifricio', 4.2, 0.7, '8_blanx.jpg', 2, 'Consigliato da 1 dentista su 20', 'cura della persona'),
	(9, 'Ace candeggina', 1.7, 1, '9_ace.jpg', 3, 'Solo per capi bianchi', 'casa'),
	(10, 'Crocchette cane', 3.5, 3, '10_crocchette.jpg', 5, 'Cani di grossa taglia', 'animali'),
	(11, 'Bagnoschiuma', 1.8, 1, '11_dove.png', 2, 'Per pelli delicate', 'cura della persona'),
	(12, 'Crema corpo', 1.9, 2, '12_crema.jpg', 2, 'Per pelli secche', 'cura della persona'),
	(13, 'Merluzzo', 5.5, 1.2, '13_3D-Cap-Filetti-Merluzzo.png', 1, 'Surgelato', 'gelati e surgelati'),
	(14, 'Ace pavimento', 1.9, 1, '14_82518-fancybox_default.jpg', 2, 'Piastrelle/Marmo', 'casa'),
	(16, 'Elvive shampo', 2.9, 10, '16_dream-long-shampoo-ripara-lunghezze.jpg', 1, 'Per capelli lunghi', 'cura della persona'),
	(17, 'Patate vivaci', 1.5, 1, '17_l_20651.jpg', 2, 'Sapore vivace', 'dispensa'),
	(18, 'Carne di bovino', 6, 1.5, '18_eg-0021614_1_big.jpg', 5, 'Ottima per il colesterolo', 'carne'),
	(19, 'Deodorante Dove', 2.8, 0.25, '19_big_167236_066184_02_nkyuy1zjkyc7hltv.jpg', 6, 'Evita odori sgradevoli', 'cura della persona'),
	(20, 'Pasta Divella', 1.5, 1, '20_0000079161.jpg', 4, 'Tempo di cottura: 72 ore', 'dispensa'),
	(21, 'Sale', 0.85, 1, '21_CG0155-1.jpg', 3, 'Scende', 'dispensa'),
	(22, 'PanCarrè', 1.5, 2, '22_PANCARRE24FETTEMULINOB-8076809530842-1.png', 5, 'Ideale da tostare', 'dispensa'),
	(23, 'Olio Frittura', 1.95, 1.5, '23_31960_2.jpg', 4, 'Olio Frittura', 'dispensa'),
	(24, 'Mirtilli', 1.5, 1.6, '24_IMMAGINO_IMMAGINO_EAN8028873001728_1_renditions_600x600.png', 5, 'Possiedono proprietà anti-aging', 'frutta'),
	(25, 'Banane', 1.78, 1.7, '25_Chiquita_Banana_Class_Extra_Yellow.jpg', 3, 'Fresche', 'frutta'),
	(26, 'Mandorle ', 2.41, 3, '26_0000168662-1024x1024.jpg', 2, 'Ricco di vitamine', 'frutta'),
	(27, 'Pizza Surgelata', 3.14, 2, '27_PIZZAMARGHERITABELLANAPOLI-8000300391961-1.png', 2, 'Preparazione rapida', 'gelati e surgelati'),
	(28, 'Mais in scatola', 2.3, 2.3, '28_mais-bonduelle-in-scatola-300-g-.jpg', 3, 'Per mille ricette', 'gastronomia'),
	(29, 'Olive verdi', 1.5, 2, '29_phoca_thumb_l_2biolverd250ec_a.jpg', 4, 'Olive', 'verdura'),
	(30, 'Farina Barilla', 0.9, 2, '30_279FARINA00BARILLA-8076802795019-1.png', 3, 'Utilizzata per dolci e pizza ', 'casa'),
	(31, 'Gelato Nuii', 3.8, 3, '31_Nuii_Multipack_x4_SaltedCaramelAustralianMacadamia_IT-980x748.jpg', 2, 'Per chi ama il caramello e non può farne a meno', 'gelati e surgelati'),
	(32, 'Petto di pollo', 6.82, 6, '32_PAM_442323_MINIFILETTO-POLLO-PP.jpg', 5, 'Ottima per la dieta', 'gastronomia'),
	(33, 'Piselli Findus', 5.5, 2, '33_191206_pack piselli novelli 450.png', 2, 'Buoni', 'gelati e surgelati'),
	(34, 'Fagioli Cannellini', 2.3, 1, '34_cannellini-vaso-vetro-new.jpg', 3, 'Ottimi per Insalate', 'dispensa'),
	(35, 'Lenticchie secche', 3.01, 2, '35_lenticchie-verdi.jpg', 3, 'Legumi', 'gastronomia'),
	(36, 'Cotechino', 4.2, 3, '36_G7122 NEGRONI Grancotechino.png', 3, 'Un sapore inaspettato', 'carne'),
	(37, 'Nascondini Mulino Bianco', 2.5, 3, '37_0000187449.jpg', 5, 'Biscotti ripieni di cioccolato', 'gastronomia'),
	(38, 'Caramelle Haribo', 0.39, 2, '38_l_60940.jpg', 2, 'Gusti assortiti', 'gastronomia'),
	(39, 'Cioccolata lindt', 3.5, 4, '39_l_21773.jpg', 3, 'Maître Chocolatier', 'dispensa'),
	(41, 'Mix Party Pretzel', 2.62, 3, '41_Snack Friends Bretzel 100 g.png', 4, 'Mix Salato', 'pane farine e preparati'),
	(42, 'Plumcake Mulino Bianco', 2.85, 0.4, '42_083443-mulino-bianco-plumcake-classico-allo-yogurt-confezione-da-10-plumcake---350-grammi-totali.jpg', 5, 'Ottimi per la colazione', 'gastronomia'),
	(43, 'Origano', 4.15, 3, '43_Origano-secco-frantumato-per-salumi-salmistrati-Dama.jpg', 3, 'Spezie', 'frutta'),
	(44, 'Cipolle', 3.5, 0.5, '44_sacchetto-carta-dorata-943x1024.jpg', 6, 'Ideale per il brodo', 'prodotti biologici'),
	(45, 'Bastoncini Findus', 3.6, 6, '45_70600236.jpg', 7, 'Merluzzo impanato', 'pesce'),
	(46, 'Crocché', 1.9, 0.9, '46_pim-00000008002411402055-main-20201002-140225.jpg', 5, 'Pronti in poco tempo', 'gelati e surgelati'),
	(47, 'Pane', 0.89, 0.1, '47_baguettes-precotte-dailybread-2-panini-300gr.jpg', 4, 'Rosette 300g', 'pane farine e preparati'),
	(48, 'Cereali', 3.9, 1.5, '48_2974-home_default.jpg', 4, 'Ideale per la colazione', 'dispensa'),
	(49, 'Chilly intimo', 4.1, 2.1, '49_chilly-detergente-intimo-delicato-formula-lenitiva.jpg', 2, 'Per intimo ', 'cura della persona'),
	(50, 'Sgrassatore ', 3.41, 0.7, '50_51X1F1n5IIL._AC_SY879_.jpg', 5, 'Adatto per mille usi', 'casa'),
	(51, 'Nelsen piatti', 0.78, 0.7, '51_71f4pa9VJCL._AC_SS450_.jpg', 4, 'Ottimo per il lavaggio dei piatti', 'casa'),
	(52, 'Spugnetta Piatti', 0.78, 2, '52_unnamed.jpg', 1, 'Utilizzare per i piatti', 'casa'),
	(53, 'Riso ', 4.5, 1.7, '53_81vKrhUZuYL._AC_SX679_.jpg', 5, 'Ottimo per il risotto', 'dispensa'),
	(54, 'Capsule dixan', 3.5, 2, '54_l_62446.jpg', 4, 'Pratica e veloce', 'bucato'),
	(55, 'Carta igienica', 2.5, 3, '55_01.jpg', 4, 'Doppio Velo', 'casa'),
	(56, 'Carta argentata', 1.9, 2, '56_l_3551.jpg', 1, 'Conservazione Alimenti', 'dispensa'),
	(57, 'Carta da forno', 2, 4, '57_614c8x6j8QL._AC_SS450_.jpg', 3, 'Carta da usare in forno', 'casa'),
	(58, 'Pellicola', 3.4, 2, '58_069767c1-9ecb-44e4-8c18-2f9b90e3d2ad.jpg', 6, 'Conservazione Alimenti', 'dispensa'),
	(59, 'Pan grattato', 3.1, 2, '59_0000000064-1024x1024.jpg', 1, 'Ottimo per le cotolette', 'pane farine e preparati'),
	(60, 'Thè limone', 1.4, 4, '60_l_23393.jpg', 5, 'Fresca', 'gastronomia');
/*!40000 ALTER TABLE `prodotto` ENABLE KEYS */;

-- Dump dei dati della tabella unimart.prodotto_preferito: 0 rows
DELETE FROM `prodotto_preferito`;
/*!40000 ALTER TABLE `prodotto_preferito` DISABLE KEYS */;
/*!40000 ALTER TABLE `prodotto_preferito` ENABLE KEYS */;

-- Dump dei dati della tabella unimart.spedizione: ~0 rows (circa)
DELETE FROM `spedizione`;
/*!40000 ALTER TABLE `spedizione` DISABLE KEYS */;
INSERT INTO `spedizione` (`ID`, `nome`, `costo`) VALUES
	(1, 'eco', 5.99),
	(2, 'standard', 9.99),
	(3, 'express', 14.99);
/*!40000 ALTER TABLE `spedizione` ENABLE KEYS */;

-- Dump dei dati della tabella unimart.spedizione_preferita: 0 rows
DELETE FROM `spedizione_preferita`;
/*!40000 ALTER TABLE `spedizione_preferita` DISABLE KEYS */;
/*!40000 ALTER TABLE `spedizione_preferita` ENABLE KEYS */;

-- Dump dei dati della tabella unimart.utente: ~0 rows (circa)
DELETE FROM `utente`;
/*!40000 ALTER TABLE `utente` DISABLE KEYS */;
INSERT INTO `utente` (`CF`, `nome`, `cognome`, `viaCivico`, `fotoProfilo`, `tipo`, `citta`, `regione`, `telefono`, `dataDiNascita`, `email`, `username`, `passwordHash`) VALUES
	('cfcf', 'cf', 'cf', 'cf', 'cfcf_20180327_110324.jpg', 'Semplice', 'cf', 'cf', '123', '2020-08-25', 'cf@cf.com', 'cf', 'f78b64c9e0f2ea24fddce2b0d809cb2855fed1a6'),
	('DCFJPG92A23L322U', 'sabato', 'genovese', 'po,2', NULL, 'Semplice', 'visciano', 'campania', '33334888', '2000-05-05', 's@s.com', 'sabato', '795cbb3993ff966ec0444d87de357bb33f302897'),
	('ERFDPG92A23L322U', 'admin', 'admin', 'adige,9', NULL, 'Semplice', 'visciano', 'campania', '38974888', '2000-01-05', 'admin@admin.com', 'admin', 'd033e22ae348aeb5660fc2140aec35850c4da997'),
	('FDMJCB86P16E185O', 'luigi', 'crescenzo', 'via cavour,23', 'FDMJCB86P16E185O_05.jpg', 'Amministratore', 'sarno', 'campania', '0123456789', '2023-05-21', 'luigi@gmail.com', 'crescenzo', '52d92f4db63498b43ed949190afd56a5fa49c463'),
	('FMEJRH67L53D462P', 'uytfuiytkf', 'gvuygfv', 'yutgfyhjyu', 'FMEJRH67L53D462P_05.jpg', 'Semplice', 'gyutg', 'yugtg', '0122456788', '2020-06-22', 'jhyfuyj@gmail.com', 'gyuygl', '459ee0e1bc34dd04312447f89b500b82e3584532'),
	('Gehuebevehdb', 'Bho', 'Come ', 'Domenico 5', 'Gehuebevehdb_image.jpg', 'Semplice', 'Napoli ', 'Campania ', '3333557834', '2003-07-11', 'monica@gmail.com', 'Monica ', '4b4446969034c7b0cee153226a398194c133f65b'),
	('KLPNBA32P28L037Q', 'sabato', 'geno', 'ciao,3', 'KLPNBA32P28L037Q_01 (2).jpg', 'Semplice', 'dsf', 'dsf', '3391904141', '2021-07-07', 'dsf@dsf.dsf', 'lafoca', '1b23aecc420daee59f146461aaad71d54bdfbc09'),
	('LLFJPG92A23L322U', 'test', 'test', 'via test', 'LLFJPG92A23L322U_admin.svg', 'Amministratore', 'Visciano', 'Campania', '4444', '2022-06-20', 'test@test.it', 'testtt', 'ec0695a0fb09cdcd75fdf99ecb9dfc8340272ec8'),
	('MEOGPP61H27F839B', 'giuseppe', 'meo', 'f. scala 60', 'MEOGPP61H27F839B_P_20160318_124634.jpg', 'Amministratore', 'san paolo bel sito', 'campania', '0818235695', '1961-06-27', 'giuseppe-meo@virgilio.it', 'angelo', '72dd5a9fc52d7c3953ef7bbf3d1d4bfb96c4d399'),
	('MSFLTF44P15C400R', 'sabato', 'genovese', 'po,2', 'logo_small_icon_only_inverted.png', 'Semplice', 'nola', 'campania', '1234567890', '2000-07-05', 'sa@sa.com', 'tino', '795cbb3993ff966ec0444d87de357bb33f302897'),
	('sabatoG', 'sabato', 'genovese', 'sg', 'sabatoG_20180327_110324.jpg', 'Semplice', 'ss', 'sss', '123456', '2019-07-26', 'sabato@genovese.com', 'sg', 'ff39796487e85a7066e18d814bcb63856de6cfff'),
	('tttttt', 'ttttt', 'tttt', 'tttt', 'tttttt_20180327_110331.jpg', 'Semplice', 'tttt', 'tttt', '123456', '2019-07-25', 'tet@tetw.com', '00', 'b6589fc6ab0dc82cf12099d1c2d40ab994e8410c'),
	('we', 'we', 'we', 'we', 'we_wallpaper.jpg', 'Semplice', 'nola', 'campania', '3333333', '2019-06-27', 'we@we.it', 'wewewe', 'ec0695a0fb09cdcd75fdf99ecb9dfc8340272ec8'),
	('YGXRLB88C60A485Q', 'uygfyu', 'fgyugvf', 'yutgfvyt', 'YGXRLB88C60A485Q_02.jpg', 'Semplice', 'fgtyu', 'fuytf', '0123456789', '2019-05-21', 'yufu@uygfuy.uyg', 'uyugfuy', '22391f8c001e6d8063d70d9baa4be4a537b21182');
/*!40000 ALTER TABLE `utente` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
