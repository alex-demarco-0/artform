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
  `contenuto` varchar(50) NOT NULL,
  `utenteID` int NOT NULL DEFAULT '0',
  `punteggio` int NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK__utente` (`utenteID`),
  CONSTRAINT `FK__utente` FOREIGN KEY (`utenteID`) REFERENCES `utente` (`ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dump dei dati della tabella ArtForm.badge: ~0 rows (circa)
/*!40000 ALTER TABLE `badge` DISABLE KEYS */;
INSERT INTO `badge` (`ID`, `contenuto`, `utenteID`, `punteggio`) VALUES
	(1, 'Primo contenuto pubblicato', 1, 0),
	(2, 'Veterano', 2, 1000);
/*!40000 ALTER TABLE `badge` ENABLE KEYS */;

-- Dump della struttura di tabella ArtForm.commissione
CREATE TABLE IF NOT EXISTS `commissione` (
  `ID` int NOT NULL,
  `titolo` varchar(100) NOT NULL,
  `prezzo` double NOT NULL DEFAULT '0',
  `data` timestamp NOT NULL,
  `utenteID` int NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_commissione_utente` (`utenteID`),
  CONSTRAINT `FK_commissione_utente` FOREIGN KEY (`utenteID`) REFERENCES `utente` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dump dei dati della tabella ArtForm.commissione: ~0 rows (circa)
/*!40000 ALTER TABLE `commissione` DISABLE KEYS */;
INSERT INTO `commissione` (`ID`, `titolo`, `prezzo`, `data`, `utenteID`) VALUES
	(45, 'richiesta disegno velociraptor 3D', 17, '2021-12-10 17:23:06', 1);
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

-- Dump dei dati della tabella ArtForm.notifica: ~0 rows (circa)
/*!40000 ALTER TABLE `notifica` DISABLE KEYS */;
INSERT INTO `notifica` (`data`, `categoria`, `descrizione`, `collegamento`, `utenteID`) VALUES
	('2021-12-10 13:23:18', 4, 'Hai ottenuto 210 punti!', NULL, 1);
/*!40000 ALTER TABLE `notifica` ENABLE KEYS */;

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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dump dei dati della tabella ArtForm.post: ~0 rows (circa)
/*!40000 ALTER TABLE `post` DISABLE KEYS */;
INSERT INTO `post` (`ID`, `titolo`, `tags`, `like`, `tipologia`, `utenteID`, `dataPubblicazione`, `topic`) VALUES
	(1, 'Velociraptor', '#dinosaur #animals #cute #3d', 0, 'true', 1, '2021-12-09 17:25:11', '3D');
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

-- Dump dei dati della tabella ArtForm.postSalvati: ~0 rows (circa)
/*!40000 ALTER TABLE `postSalvati` DISABLE KEYS */;
INSERT INTO `postSalvati` (`utenteID`, `postID`) VALUES
	(2, 1);
/*!40000 ALTER TABLE `postSalvati` ENABLE KEYS */;

-- Dump della struttura di tabella ArtForm.topic
CREATE TABLE IF NOT EXISTS `topic` (
  `nome` varchar(30) NOT NULL,
  PRIMARY KEY (`nome`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dump dei dati della tabella ArtForm.topic: ~15 rows (circa)
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
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dump dei dati della tabella ArtForm.utente: ~2 rows (circa)
/*!40000 ALTER TABLE `utente` DISABLE KEYS */;
INSERT INTO `utente` (`ID`, `nome`, `cognome`, `username`, `email`, `numeroTelefono`, `password`) VALUES
	(1, 'Manbir', 'Aceveda', 'arianna', 'ift@', '338', 'password'),
	(2, 'Alessandro', 'Dituri', 'dv8d', '@#', NULL, 'pass');
/*!40000 ALTER TABLE `utente` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
