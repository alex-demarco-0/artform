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
  `ID` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `descrizione` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `punteggio` int NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dump dei dati della tabella ArtForm.badge: ~2 rows (circa)
/*!40000 ALTER TABLE `badge` DISABLE KEYS */;
INSERT INTO `badge` (`ID`, `nome`, `descrizione`, `punteggio`) VALUES
	(1, 'Primo contenuto pubblicato', '', 0),
	(2, 'Veterano', '', 1000);
/*!40000 ALTER TABLE `badge` ENABLE KEYS */;

-- Dump della struttura di tabella ArtForm.badgeUtente
CREATE TABLE IF NOT EXISTS `badgeUtente` (
  `utenteID` int NOT NULL,
  `badgeID` int NOT NULL,
  PRIMARY KEY (`utenteID`,`badgeID`),
  KEY `FK_badgeUtente_badge` (`badgeID`),
  CONSTRAINT `FK_badgeUtente_badge` FOREIGN KEY (`badgeID`) REFERENCES `badge` (`ID`),
  CONSTRAINT `FK_badgeUtente_utente` FOREIGN KEY (`utenteID`) REFERENCES `utente` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dump dei dati della tabella ArtForm.badgeUtente: ~0 rows (circa)
/*!40000 ALTER TABLE `badgeUtente` DISABLE KEYS */;
/*!40000 ALTER TABLE `badgeUtente` ENABLE KEYS */;

-- Dump della struttura di tabella ArtForm.commissione
CREATE TABLE IF NOT EXISTS `commissione` (
  `ID` int NOT NULL,
  `titolo` varchar(100) NOT NULL,
  `prezzo` double NOT NULL DEFAULT '0',
  `data` timestamp NOT NULL,
  `artistaID` int NOT NULL,
  `clienteID` int NOT NULL,
  `indirizzoConto` varchar(50) NOT NULL DEFAULT '',
  PRIMARY KEY (`ID`),
  KEY `FK_commissione_utente` (`artistaID`) USING BTREE,
  KEY `FK_commissione_utente_2` (`clienteID`),
  CONSTRAINT `FK_commissione_utente` FOREIGN KEY (`artistaID`) REFERENCES `utente` (`ID`),
  CONSTRAINT `FK_commissione_utente_2` FOREIGN KEY (`clienteID`) REFERENCES `utente` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dump dei dati della tabella ArtForm.commissione: ~2 rows (circa)
/*!40000 ALTER TABLE `commissione` DISABLE KEYS */;
INSERT INTO `commissione` (`ID`, `titolo`, `prezzo`, `data`, `artistaID`, `clienteID`, `indirizzoConto`) VALUES
	(11, 'richiesta disegno velociraptor 3D', 17, '2021-12-13 15:32:51', 1, 2, 'IT67X'),
	(12, 'richiesta disegno paesaggio', 20, '2021-12-14 15:27:20', 3, 1, 'AA9B7');
/*!40000 ALTER TABLE `commissione` ENABLE KEYS */;

-- Dump della struttura di tabella ArtForm.notifica
CREATE TABLE IF NOT EXISTS `notifica` (
  `data` timestamp NOT NULL,
  `categoria` tinyint NOT NULL,
  `descrizione` varchar(150) NOT NULL,
  `collegamento` varchar(1500) DEFAULT NULL,
  `utenteID` int NOT NULL,
  PRIMARY KEY (`data`),
  KEY `FK_notifica_utente` (`utenteID`),
  CONSTRAINT `FK_notifica_utente` FOREIGN KEY (`utenteID`) REFERENCES `utente` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dump dei dati della tabella ArtForm.notifica: ~2 rows (circa)
/*!40000 ALTER TABLE `notifica` DISABLE KEYS */;
INSERT INTO `notifica` (`data`, `categoria`, `descrizione`, `collegamento`, `utenteID`) VALUES
	('2021-12-06 16:57:53', 0, 'Prima registrazione', NULL, 1),
	('2021-12-10 13:23:18', 4, 'Hai ottenuto 210 punti!', NULL, 1);
/*!40000 ALTER TABLE `notifica` ENABLE KEYS */;

-- Dump della struttura di tabella ArtForm.notificheUtente
CREATE TABLE IF NOT EXISTS `notificheUtente` (
  `utenteID` int NOT NULL,
  `utenteExtID` int NOT NULL,
  PRIMARY KEY (`utenteID`,`utenteExtID`),
  KEY `FK_notificheUtente_utenteExt` (`utenteExtID`),
  CONSTRAINT `FK_notificheUtente_utente` FOREIGN KEY (`utenteID`) REFERENCES `utente` (`ID`),
  CONSTRAINT `FK_notificheUtente_utenteExt` FOREIGN KEY (`utenteExtID`) REFERENCES `utente` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dump dei dati della tabella ArtForm.notificheUtente: ~0 rows (circa)
/*!40000 ALTER TABLE `notificheUtente` DISABLE KEYS */;
/*!40000 ALTER TABLE `notificheUtente` ENABLE KEYS */;

-- Dump della struttura di tabella ArtForm.post
CREATE TABLE IF NOT EXISTS `post` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `titolo` varchar(50) NOT NULL,
  `tags` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `like` int NOT NULL DEFAULT '0',
  `tipologia` varchar(50) NOT NULL,
  `utenteID` int NOT NULL,
  `dataPubblicazione` timestamp NOT NULL,
  `topic` varchar(50) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_post_utente` (`utenteID`),
  KEY `FK_post_topic` (`topic`),
  CONSTRAINT `FK_post_topic` FOREIGN KEY (`topic`) REFERENCES `topic` (`nome`),
  CONSTRAINT `FK_post_utente` FOREIGN KEY (`utenteID`) REFERENCES `utente` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dump dei dati della tabella ArtForm.post: ~2 rows (circa)
/*!40000 ALTER TABLE `post` DISABLE KEYS */;
INSERT INTO `post` (`ID`, `titolo`, `tags`, `like`, `tipologia`, `utenteID`, `dataPubblicazione`, `topic`) VALUES
	(1, 'Velociraptor', '#dinosaur #animals #cute #3d', 2, 'true', 1, '2021-12-09 17:25:11', '3D'),
	(2, 'Paesaggio commissione', '#paesaggio #landscape #nature', 0, 'true', 1, '2021-12-14 15:28:09', 'Tradizionale');
/*!40000 ALTER TABLE `post` ENABLE KEYS */;

-- Dump della struttura di tabella ArtForm.postSalvati
CREATE TABLE IF NOT EXISTS `postSalvati` (
  `utenteID` int NOT NULL,
  `postID` int NOT NULL,
  PRIMARY KEY (`utenteID`,`postID`),
  KEY `FK_postSalvati_post` (`postID`),
  CONSTRAINT `FK_postSalvati_post` FOREIGN KEY (`postID`) REFERENCES `post` (`ID`),
  CONSTRAINT `FK_postSalvati_utente` FOREIGN KEY (`utenteID`) REFERENCES `utente` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dump dei dati della tabella ArtForm.postSalvati: ~3 rows (circa)
/*!40000 ALTER TABLE `postSalvati` DISABLE KEYS */;
INSERT INTO `postSalvati` (`utenteID`, `postID`) VALUES
	(2, 1),
	(3, 1),
	(3, 2);
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

-- Dump della struttura di tabella ArtForm.utente
CREATE TABLE IF NOT EXISTS `utente` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(50) NOT NULL,
  `cognome` varchar(50) NOT NULL,
  `username` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `numeroTelefono` varchar(10) DEFAULT NULL,
  `password` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_cs NOT NULL,
  `punteggio` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dump dei dati della tabella ArtForm.utente: ~3 rows (circa)
/*!40000 ALTER TABLE `utente` DISABLE KEYS */;
INSERT INTO `utente` (`ID`, `nome`, `cognome`, `username`, `email`, `numeroTelefono`, `password`, `punteggio`) VALUES
	(1, 'Manbir', 'Aceveda', 'arianna', 'ift@', '338', 'password', 0),
	(2, 'Alessandro', 'Dituri', 'dv8d', '@#', NULL, 'pass', 101),
	(3, 'Mario', 'Rossi', 'marione', 'm@', NULL, '123456', 0),
	(4, 'Gerico', 'Cris', 'Hiloman3', '@@', '441', 'kkkka', 3);
/*!40000 ALTER TABLE `utente` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
