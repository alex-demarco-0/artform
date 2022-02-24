-- --------------------------------------------------------
-- Host:                         localhost
-- Versione server:              8.0.27 - MySQL Community Server - GPL
-- S.O. server:                  Linux
-- HeidiSQL Versione:            11.3.0.6295
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dump della struttura del database ArtForm
CREATE DATABASE IF NOT EXISTS `ArtForm` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `ArtForm`;

-- Dump della struttura di tabella ArtForm.badge
CREATE TABLE IF NOT EXISTS `badge` (
  `nome` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `descrizione` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `punteggio` int NOT NULL,
  PRIMARY KEY (`nome`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dump dei dati della tabella ArtForm.badge: ~2 rows (circa)
/*!40000 ALTER TABLE `badge` DISABLE KEYS */;
INSERT INTO `badge` (`nome`, `descrizione`, `punteggio`) VALUES
	('Easy', 'gg', 8),
	('Primo contenuto pubblicato', '', 0),
	('Veterano', '', 1000);
/*!40000 ALTER TABLE `badge` ENABLE KEYS */;

-- Dump della struttura di tabella ArtForm.badgeUtente
CREATE TABLE IF NOT EXISTS `badgeUtente` (
  `utenteUsername` varchar(50) NOT NULL,
  `badgeNome` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '',
  PRIMARY KEY (`utenteUsername`,`badgeNome`) USING BTREE,
  KEY `FK_badgeUtente_badge` (`badgeNome`) USING BTREE,
  CONSTRAINT `FK_badgeUtente_badge` FOREIGN KEY (`badgeNome`) REFERENCES `badge` (`nome`),
  CONSTRAINT `FK_badgeUtente_utente` FOREIGN KEY (`utenteUsername`) REFERENCES `utente` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dump dei dati della tabella ArtForm.badgeUtente: ~0 rows (circa)
/*!40000 ALTER TABLE `badgeUtente` DISABLE KEYS */;
INSERT INTO `badgeUtente` (`utenteUsername`, `badgeNome`) VALUES
	('alex', 'Primo contenuto pubblicato'),
	('arianna', 'Veterano');
/*!40000 ALTER TABLE `badgeUtente` ENABLE KEYS */;

-- Dump della struttura di tabella ArtForm.commissione
CREATE TABLE IF NOT EXISTS `commissione` (
  `Id` int NOT NULL AUTO_INCREMENT,
  `titolo` varchar(100) NOT NULL,
  `prezzo` double NOT NULL DEFAULT '0',
  `data` timestamp NOT NULL,
  `artistaUsername` varchar(50) NOT NULL,
  `clienteUsername` varchar(50) NOT NULL,
  `indirizzoConto` varchar(50) NOT NULL DEFAULT '',
  PRIMARY KEY (`Id`),
  KEY `FK_commissione_utente` (`artistaUsername`) USING BTREE,
  KEY `FK_commissione_utente_2` (`clienteUsername`),
  CONSTRAINT `FK_commissione_utente` FOREIGN KEY (`artistaUsername`) REFERENCES `utente` (`username`),
  CONSTRAINT `FK_commissione_utente_2` FOREIGN KEY (`clienteUsername`) REFERENCES `utente` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dump dei dati della tabella ArtForm.commissione: ~2 rows (circa)
/*!40000 ALTER TABLE `commissione` DISABLE KEYS */;
INSERT INTO `commissione` (`Id`, `titolo`, `prezzo`, `data`, `artistaUsername`, `clienteUsername`, `indirizzoConto`) VALUES
	(11, 'richiesta disegno velociraptor 3D', 17, '2021-12-13 15:32:51', 'arianna', 'dv8d', 'IT67X'),
	(12, 'richiesta disegno paesaggio', 20, '2021-12-14 15:27:20', 'dv8d', 'arianna', 'AA9B7'),
	(13, 'test', 200000, '2022-11-14 15:27:20', 'alex', 'arianna', 'AC9B7');
/*!40000 ALTER TABLE `commissione` ENABLE KEYS */;

-- Dump della struttura di tabella ArtForm.notifica
CREATE TABLE IF NOT EXISTS `notifica` (
  `data` timestamp NOT NULL,
  `categoria` tinyint NOT NULL,
  `descrizione` varchar(150) NOT NULL,
  `collegamento` varchar(1500) DEFAULT NULL,
  `utenteUsername` varchar(50) NOT NULL,
  PRIMARY KEY (`data`),
  KEY `FK_notifica_utente` (`utenteUsername`),
  CONSTRAINT `FK_notifica_utente` FOREIGN KEY (`utenteUsername`) REFERENCES `utente` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dump dei dati della tabella ArtForm.notifica: ~2 rows (circa)
/*!40000 ALTER TABLE `notifica` DISABLE KEYS */;
INSERT INTO `notifica` (`data`, `categoria`, `descrizione`, `collegamento`, `utenteUsername`) VALUES
	('2021-11-10 13:23:18', 4, 'Hai ottenuto 210 punti!', 'http://www.cacca.it/', 'arianna'),
	('2021-12-06 16:57:53', 0, 'Prima registrazione', NULL, 'arianna'),
	('2021-12-10 13:23:18', 4, 'Hai ottenuto 210 punti!', NULL, 'arianna'),
	('2022-02-23 09:51:42', 1, 'test', NULL, 'alex');
/*!40000 ALTER TABLE `notifica` ENABLE KEYS */;

-- Dump della struttura di tabella ArtForm.notificheUtente
CREATE TABLE IF NOT EXISTS `notificheUtente` (
  `utenteUsername` varchar(50) NOT NULL,
  `utenteExtUsername` varchar(50) NOT NULL,
  PRIMARY KEY (`utenteUsername`,`utenteExtUsername`),
  KEY `FK_notificheUtente_utente` (`utenteUsername`),
  KEY `FK_notificheUtente_utenteExt` (`utenteExtUsername`),
  CONSTRAINT `FK_notificheUtente_utente` FOREIGN KEY (`utenteUsername`) REFERENCES `utente` (`username`),
  CONSTRAINT `FK_notificheUtente_utenteExt` FOREIGN KEY (`utenteExtUsername`) REFERENCES `utente` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dump dei dati della tabella ArtForm.notificheUtente: ~2 rows (circa)
/*!40000 ALTER TABLE `notificheUtente` DISABLE KEYS */;
INSERT INTO `notificheUtente` (`utenteUsername`, `utenteExtUsername`) VALUES
	('admin', 'arianna'),
	('alex', 'pollastro'),
	('pollastro', 'admin');
/*!40000 ALTER TABLE `notificheUtente` ENABLE KEYS */;

-- Dump della struttura di tabella ArtForm.post
CREATE TABLE IF NOT EXISTS `post` (
  `Id` int NOT NULL AUTO_INCREMENT,
  `utenteUsername` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `titolo` varchar(50) NOT NULL,
  `topic` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `tags` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `dataPubblicazione` timestamp NOT NULL,
  `like` int NOT NULL DEFAULT '0',
  `tipologia` varchar(50) NOT NULL,
  `contenutoSrc` varchar(300) NOT NULL,
  PRIMARY KEY (`Id`),
  KEY `FK_post_utente` (`utenteUsername`),
  KEY `FK_post_topic` (`topic`),
  CONSTRAINT `FK_post_topic` FOREIGN KEY (`topic`) REFERENCES `topic` (`nome`),
  CONSTRAINT `FK_post_utente` FOREIGN KEY (`utenteUsername`) REFERENCES `utente` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dump dei dati della tabella ArtForm.post: ~2 rows (circa)
/*!40000 ALTER TABLE `post` DISABLE KEYS */;
INSERT INTO `post` (`Id`, `utenteUsername`, `titolo`, `topic`, `tags`, `dataPubblicazione`, `like`, `tipologia`, `contenutoSrc`) VALUES
	(1, 'arianna', 'Velociraptor', '3D', '#dinosaur #animals #cute #3d', '2021-12-09 17:25:11', 2, 'true', 'just'),
	(2, 'arianna', 'Paesaggio commissione', 'Tradizionale', '#paesaggio #landscape #nature', '2021-12-14 15:28:09', 0, 'true', ''),
	(4, 'alex', 'Pulce', 'Animali', '#prurito', '2022-02-23 09:45:27', 0, 'true', 'media/imagePosts/4.jpg'),
	(5, 'alex', 'Arch', 'Sci-Fi', '#top', '2022-02-24 14:07:09', 99, 'true', 'media/imagePosts/5.jpg'),
	(6, 'alex', 'Cutie', 'Animali', '#:3', '2022-02-24 14:07:58', 0, 'true', 'media/imagePosts/6.jpg'),
	(7, 'alex', 'Chads', 'Animazione', '#strongmans', '2022-02-24 14:09:00', 0, 'true', 'media/imagePosts/7.jpg');
/*!40000 ALTER TABLE `post` ENABLE KEYS */;

-- Dump della struttura di tabella ArtForm.postSalvati
CREATE TABLE IF NOT EXISTS `postSalvati` (
  `utenteUsername` varchar(50) NOT NULL,
  `postID` int NOT NULL,
  PRIMARY KEY (`utenteUsername`,`postID`),
  KEY `FK_postSalvati_post` (`postID`),
  CONSTRAINT `FK_postSalvati_post` FOREIGN KEY (`postID`) REFERENCES `post` (`Id`),
  CONSTRAINT `FK_postSalvati_utente` FOREIGN KEY (`utenteUsername`) REFERENCES `utente` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dump dei dati della tabella ArtForm.postSalvati: ~3 rows (circa)
/*!40000 ALTER TABLE `postSalvati` DISABLE KEYS */;
INSERT INTO `postSalvati` (`utenteUsername`, `postID`) VALUES
	('dv8d', 1),
	('marione', 1),
	('marione', 2),
	('alex', 4);
/*!40000 ALTER TABLE `postSalvati` ENABLE KEYS */;

-- Dump della struttura di tabella ArtForm.topic
CREATE TABLE IF NOT EXISTS `topic` (
  `nome` varchar(30) NOT NULL,
  PRIMARY KEY (`nome`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dump dei dati della tabella ArtForm.topic: ~17 rows (circa)
/*!40000 ALTER TABLE `topic` DISABLE KEYS */;
INSERT INTO `topic` (`nome`) VALUES
	('3D'),
	('Animali'),
	('Animazione'),
	('Arte Digitale'),
	('Astratto'),
	('Fantasy'),
	('Fotografia'),
	('Fotomontaggio'),
	('Fumetti'),
	('Horror'),
	('Pixel Art'),
	('Sci-Fi'),
	('Speedpaint'),
	('Tradizionale'),
	('Tutorial'),
	('Videogiochi'),
	('Wallpaper');
/*!40000 ALTER TABLE `topic` ENABLE KEYS */;

-- Dump della struttura di tabella ArtForm.topicUtente
CREATE TABLE IF NOT EXISTS `topicUtente` (
  `utenteUsername` varchar(50) NOT NULL,
  `topicNome` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`utenteUsername`,`topicNome`),
  KEY `FK_topicUtente_topic` (`topicNome`),
  CONSTRAINT `FK_topicUtente_topic` FOREIGN KEY (`topicNome`) REFERENCES `topic` (`nome`),
  CONSTRAINT `FK_topicUtente_utente` FOREIGN KEY (`utenteUsername`) REFERENCES `utente` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dump dei dati della tabella ArtForm.topicUtente: ~5 rows (circa)
/*!40000 ALTER TABLE `topicUtente` DISABLE KEYS */;
INSERT INTO `topicUtente` (`utenteUsername`, `topicNome`) VALUES
	('pollastro', '3D'),
	('alex', 'Animali'),
	('arianna', 'Animali'),
	('alex', 'Fumetti'),
	('arianna', 'Horror');
/*!40000 ALTER TABLE `topicUtente` ENABLE KEYS */;

-- Dump della struttura di tabella ArtForm.utente
CREATE TABLE IF NOT EXISTS `utente` (
  `nome` varchar(50) NOT NULL,
  `cognome` varchar(50) NOT NULL,
  `username` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `numeroTelefono` varchar(10) DEFAULT NULL,
  `password` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_cs NOT NULL,
  `bio` varchar(100) DEFAULT NULL,
  `punteggio` int NOT NULL DEFAULT '0',
  `immagineProfiloSrc` varchar(300) NOT NULL,
  PRIMARY KEY (`username`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dump dei dati della tabella ArtForm.utente: ~9 rows (circa)
/*!40000 ALTER TABLE `utente` DISABLE KEYS */;
INSERT INTO `utente` (`nome`, `cognome`, `username`, `email`, `numeroTelefono`, `password`, `bio`, `punteggio`, `immagineProfiloSrc`) VALUES
	('ADMIN', 'ADMIN', 'admin', 'admin@admin.com', '000', 'admin', NULL, 999, ''),
	('Alessandro', 'De Marco', 'alex', 'a.demarco@itsrizzoli.it', '3384148744', 'password', 'alien', 0, 'media/userProfilePics/alex.jpg'),
	('Manbir', 'Aceveda', 'arianna', 'ift@k.it', '338', 'password', 'hey #you', 1, 'media/userProfilePics/arianna.jpg'),
	('Zante', 'Aceveda', 'ariannolo', 'i@k.it', NULL, 'pwd', NULL, 0, ''),
	('Hylo', 'hghgh', 'arinolo', 'aaaa@ggg.it', '', 'hy', NULL, 0, ''),
	('Alessandro', 'Dituri', 'dv8d', '@#', NULL, 'pass', NULL, 101, ''),
	('Gerico', 'Cris', 'Hiloman3', '@@', '441', 'kkkka', NULL, 3, ''),
	('Mario', 'Rossi', 'marione', 'm@', NULL, '123456', NULL, 0, ''),
	('Gabbo', 'Uop', 'pollastro', 'a6@i.it', '0', 'oi', NULL, 0, ''),
	('gesuita', 'manolo', 'sterzata', 'r@i.com', '', 'popo', '', 0, '');
/*!40000 ALTER TABLE `utente` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
