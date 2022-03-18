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

-- Dump dei dati della tabella ArtForm.badge: ~4 rows (circa)
/*!40000 ALTER TABLE `badge` DISABLE KEYS */;
INSERT INTO `badge` (`nome`, `descrizione`, `punteggio`) VALUES
	('Easy', '???', 8),
	('First content published', 'Congratulations for publishing your first content on ArtForm!', 0),
	('Veteran', '???', 1000),
	('Welcome on ArtForm!', 'This badge represents your committment into spreading art throughout the world!', 0);
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

-- Dump dei dati della tabella ArtForm.badgeUtente: ~7 rows (circa)
/*!40000 ALTER TABLE `badgeUtente` DISABLE KEYS */;
INSERT INTO `badgeUtente` (`utenteUsername`, `badgeNome`) VALUES
	('alex', 'First content published'),
	('arianna', 'First content published'),
	('alex', 'Welcome on ArtForm!'),
	('arianna', 'Welcome on ArtForm!'),
	('rr', 'Welcome on ArtForm!'),
	('tr', 'Welcome on ArtForm!'),
	('trr', 'Welcome on ArtForm!'),
	('ty', 'Welcome on ArtForm!');
/*!40000 ALTER TABLE `badgeUtente` ENABLE KEYS */;

-- Dump della struttura di tabella ArtForm.commissione
CREATE TABLE IF NOT EXISTS `commissione` (
  `Id` int NOT NULL AUTO_INCREMENT,
  `titolo` varchar(100) NOT NULL,
  `prezzo` double NOT NULL DEFAULT '0',
  `descrizione` varchar(200) NOT NULL DEFAULT '',
  `topic` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `data` timestamp NOT NULL,
  `dataTermine` timestamp NOT NULL,
  `artistaUsername` varchar(50) NOT NULL,
  `clienteUsername` varchar(50) NOT NULL,
  `indirizzoConto` varchar(50) NOT NULL DEFAULT '',
  PRIMARY KEY (`Id`),
  KEY `FK_commissione_utente` (`artistaUsername`) USING BTREE,
  KEY `FK_commissione_utente_2` (`clienteUsername`),
  KEY `FK_commissione_artform.topic` (`topic`),
  CONSTRAINT `FK_commissione_artform.topic` FOREIGN KEY (`topic`) REFERENCES `topic` (`nome`),
  CONSTRAINT `FK_commissione_utente` FOREIGN KEY (`artistaUsername`) REFERENCES `utente` (`username`),
  CONSTRAINT `FK_commissione_utente_2` FOREIGN KEY (`clienteUsername`) REFERENCES `utente` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dump dei dati della tabella ArtForm.commissione: ~5 rows (circa)
/*!40000 ALTER TABLE `commissione` DISABLE KEYS */;
INSERT INTO `commissione` (`Id`, `titolo`, `prezzo`, `descrizione`, `topic`, `data`, `dataTermine`, `artistaUsername`, `clienteUsername`, `indirizzoConto`) VALUES
	(11, 'richiesta disegno velociraptor 3D', 17, 'potresti disegnarmi un bell\'esemplare di velociraptor in 3D?', '3D', '2021-12-13 15:32:51', '2022-06-04 00:00:00', 'arianna', 'alex', 'IT67X'),
	(12, 'richiesta disegno paesaggio', 20, 'messaggio', 'Wallpaper', '2021-12-14 15:27:20', '2029-12-12 00:00:00', 'pollastro', 'arianna', 'AA9B7'),
	(13, 'test', 200000, 'testt', 'Sci-Fi', '2022-11-14 15:27:20', '2009-01-10 00:00:00', 'alex', 'arianna', 'AC9B7'),
	(14, 'tttttt', 665.65, 'ryyyreyeryeryey', 'Fotomontaggio', '2022-03-07 11:46:42', '2022-03-17 11:45:53', 'arianna', 'alex', 'alar6ttFn '),
	(17, 'commmmmm', 2000, 'potresti?', 'Astratto', '2022-03-08 14:38:17', '2022-03-31 15:37:49', 'alex', 'admin', 'adal2coAe ');
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

-- Dump dei dati della tabella ArtForm.notifica: ~10 rows (circa)
/*!40000 ALTER TABLE `notifica` DISABLE KEYS */;
INSERT INTO `notifica` (`data`, `categoria`, `descrizione`, `collegamento`, `utenteUsername`) VALUES
	('2022-03-08 14:38:20', 3, 'User admin sent you a commission request', '', 'arianna'),
	('2022-03-08 16:39:31', 1, 'arianna published a new artwork', '24', 'admin'),
	('2022-03-08 16:39:56', 2, 'Someone liked your post!', '24', 'arianna'),
	('2022-03-17 22:23:52', 1, 'alex published a new artwork', '25', 'arianna'),
	('2022-03-17 22:29:59', 1, 'arianna published a new artwork', '26', 'admin'),
	('2022-03-17 22:30:00', 1, 'arianna published a new artwork', '26', 'alex'),
	('2022-03-17 22:30:48', 2, 'Someone liked your post!', '26', 'arianna'),
	('2022-03-18 09:45:30', 5, 'Congrats! You obtained a new Badge: Welcome on ArtForm!', 'badge', 'tr'),
	('2022-03-18 09:48:20', 5, 'Congrats! You obtained a new Badge: Welcome on ArtForm!', 'badge', 'trr'),
	('2022-03-18 09:50:19', 5, 'Congrats! You obtained a new Badge: Welcome on ArtForm!', 'badge', 'arianna'),
	('2022-03-18 11:25:47', 5, 'Congrats! You obtained a new Badge: Welcome on ArtForm!', 'badge', 'ty'),
	('2022-03-18 14:42:04', 3, 'alex accepted your commission request', '11', 'arianna'),
	('2022-03-18 14:43:31', 5, 'Congrats! You obtained a new Badge: First content published', 'badge', 'arianna');
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

-- Dump dei dati della tabella ArtForm.notificheUtente: ~3 rows (circa)
/*!40000 ALTER TABLE `notificheUtente` DISABLE KEYS */;
INSERT INTO `notificheUtente` (`utenteUsername`, `utenteExtUsername`) VALUES
	('admin', 'arianna'),
	('alex', 'admin'),
	('alex', 'arianna');
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
  `tipologia` varchar(3) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`Id`),
  KEY `FK_post_utente` (`utenteUsername`),
  KEY `FK_post_topic` (`topic`),
  CONSTRAINT `FK_post_topic` FOREIGN KEY (`topic`) REFERENCES `topic` (`nome`),
  CONSTRAINT `FK_post_utente` FOREIGN KEY (`utenteUsername`) REFERENCES `utente` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dump dei dati della tabella ArtForm.post: ~18 rows (circa)
/*!40000 ALTER TABLE `post` DISABLE KEYS */;
INSERT INTO `post` (`Id`, `utenteUsername`, `titolo`, `topic`, `tags`, `dataPubblicazione`, `like`, `tipologia`) VALUES
	(1, 'alex', 'Velociraptor', '3D', '#dinosaur #animals #cute #3d', '2021-12-09 17:25:11', 3, 'img'),
	(2, 'alex', 'Paesaggio commissione', 'Tradizionale', '#paesaggio #landscape #nature', '2021-12-14 15:28:09', 0, 'img'),
	(4, 'alex', 'Pulce', 'Animali', '#prurito', '2022-02-23 09:45:27', 7, 'img'),
	(5, 'alex', 'Arch', 'Sci-Fi', '#top', '2022-02-24 14:07:09', 104, 'img'),
	(6, 'alex', 'Cutie', 'Animali', '#:3', '2022-02-24 14:07:58', 5, 'img'),
	(7, 'alex', 'Chads', 'Animazione', '#strongmans', '2022-02-24 14:09:00', 2, 'img'),
	(14, 'alex', 's', 'Animazione', '#', '2022-03-24 14:09:00', 0, 'img'),
	(18, 'alex', 'a', '3D', 'a', '2022-03-07 17:11:55', 15, 'img'),
	(21, 'alex', 'USA', 'Horror', 'corp', '2022-03-08 10:45:01', 6, 'img'),
	(27, 'arianna', 'Pixart cloudy', 'Pixel Art', '#clouds', '2022-03-18 16:27:46', 0, 'img'),
	(28, 'arianna', 'sug4rfairy', 'Fumetti', '#commissione #icon #', '2022-03-18 16:29:04', 0, 'img'),
	(29, 'arianna', 'horse', 'Animali', '#prova', '2022-03-18 16:29:31', 0, 'img'),
	(30, 'arianna', 'disegnino animaletto', 'Speedpaint', '# carino', '2022-03-18 16:30:26', 0, 'img'),
	(31, 'arianna', 'campo fiorito', 'Tradizionale', '#fragranze #colorful', '2022-03-18 16:31:43', 0, 'img'),
	(32, 'arianna', 'magikarp', 'Animazione', '#pkmn #pastel', '2022-03-18 16:32:38', 0, 'img'),
	(33, 'arianna', 'hamsterino', 'Animali', '##', '2022-03-18 16:33:36', 0, 'img'),
	(34, 'arianna', 'opera senza titolo', 'Arte Digitale', '#girl', '2022-03-18 16:34:15', 0, 'img'),
	(35, 'arianna', 'ragazza glitchosa', 'Arte Digitale', '#girl #glitch #glitchy #dark #light', '2022-03-18 16:35:04', 0, 'img'),
	(36, 'arianna', 'ragazza glitchosa 2', 'Arte Digitale', '#girl #glitch #glitchy #dark #light', '2022-03-18 16:35:42', 0, 'img'),
	(37, 'arianna', 'trex', 'Astratto', '#pencil', '2022-03-18 16:36:30', 0, 'img'),
	(38, 'arianna', 'tired gal sketch', 'Videogiochi', '#stanchezza', '2022-03-18 16:41:12', 0, 'img'),
	(39, 'arianna', 'landscape', 'Wallpaper', '#landscape #colorful', '2022-03-18 16:42:34', 0, 'img');
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

-- Dump dei dati della tabella ArtForm.postSalvati: ~0 rows (circa)
/*!40000 ALTER TABLE `postSalvati` DISABLE KEYS */;
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

-- Dump dei dati della tabella ArtForm.topicUtente: ~11 rows (circa)
/*!40000 ALTER TABLE `topicUtente` DISABLE KEYS */;
INSERT INTO `topicUtente` (`utenteUsername`, `topicNome`) VALUES
	('pollastro', '3D'),
	('ty', '3D'),
	('alex', 'Animali'),
	('arianna', 'Animali'),
	('ty', 'Animali'),
	('ty', 'Animazione'),
	('rr', 'Astratto'),
	('trr', 'Astratto'),
	('ty', 'Astratto'),
	('pollastro', 'Fantasy'),
	('ty', 'Fantasy'),
	('ty', 'Fotografia'),
	('rr', 'Fotomontaggio'),
	('trr', 'Fotomontaggio'),
	('ty', 'Fotomontaggio'),
	('alex', 'Fumetti'),
	('ty', 'Fumetti'),
	('arianna', 'Horror'),
	('alex', 'Sci-Fi'),
	('ty', 'Sci-Fi'),
	('ty', 'Tradizionale'),
	('ty', 'Videogiochi'),
	('ty', 'Wallpaper');
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
  PRIMARY KEY (`username`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dump dei dati della tabella ArtForm.utente: ~7 rows (circa)
/*!40000 ALTER TABLE `utente` DISABLE KEYS */;
INSERT INTO `utente` (`nome`, `cognome`, `username`, `email`, `numeroTelefono`, `password`, `bio`, `punteggio`) VALUES
	('ADMIN', 'ADMIN', 'admin', 'admin@admin.com', '000', 'admin', NULL, 999),
	('Alessandro', 'De Marco', 'alex', 'a.demarco@itsrizzoli.it', '3384148744', 'password', 'I\'m an alien', 24),
	('Manbir', 'Aceveda', 'arianna', 'ift@k.it', '338', 'password', '#cuteknife#', 8),
	('Gabbo', 'Uop', 'pollastro', 'a6@i.it', '3', 'oi', 'lll', 0);
/*!40000 ALTER TABLE `utente` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
