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

-- Dump dei dati della tabella ArtForm.badge: ~2 rows (circa)
/*!40000 ALTER TABLE `badge` DISABLE KEYS */;
INSERT INTO `badge` (`ID`, `contenuto`, `utenteID`, `punteggio`) VALUES
	(1, 'Primo contenuto pubblicato', 1, 0),
	(2, 'Veterano', 2, 1000);
/*!40000 ALTER TABLE `badge` ENABLE KEYS */;

-- Dump dei dati della tabella ArtForm.commissione: ~2 rows (circa)
/*!40000 ALTER TABLE `commissione` DISABLE KEYS */;
INSERT INTO `commissione` (`ID`, `titolo`, `prezzo`, `data`, `artistaID`, `clienteID`, `indirizzoConto`) VALUES
	(11, 'richiesta disegno velociraptor 3D', 17, '2021-12-13 15:32:51', 1, 2, 'IT67X'),
	(12, 'richiesta disegno paesaggio', 20, '2021-12-14 15:27:20', 3, 1, 'AA9B7');
/*!40000 ALTER TABLE `commissione` ENABLE KEYS */;

-- Dump dei dati della tabella ArtForm.notifica: ~1 rows (circa)
/*!40000 ALTER TABLE `notifica` DISABLE KEYS */;
INSERT INTO `notifica` (`data`, `categoria`, `descrizione`, `collegamento`, `utenteID`) VALUES
	('2021-12-10 13:23:18', 4, 'Hai ottenuto 210 punti!', NULL, 1);
/*!40000 ALTER TABLE `notifica` ENABLE KEYS */;

-- Dump dei dati della tabella ArtForm.post: ~2 rows (circa)
/*!40000 ALTER TABLE `post` DISABLE KEYS */;
INSERT INTO `post` (`ID`, `titolo`, `tags`, `like`, `tipologia`, `utenteID`, `dataPubblicazione`, `topic`) VALUES
	(1, 'Velociraptor', '#dinosaur #animals #cute #3d', 0, 'true', 1, '2021-12-09 17:25:11', '3D'),
	(2, 'Paesaggio commissione', '#paesaggio #landscape #nature', 0, 'true', 1, '2021-12-14 15:28:09', 'Tradizionale');
/*!40000 ALTER TABLE `post` ENABLE KEYS */;

-- Dump dei dati della tabella ArtForm.postSalvati: ~3 rows (circa)
/*!40000 ALTER TABLE `postSalvati` DISABLE KEYS */;
INSERT INTO `postSalvati` (`utenteID`, `postID`) VALUES
	(2, 1),
	(3, 1),
	(3, 2);
/*!40000 ALTER TABLE `postSalvati` ENABLE KEYS */;

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

-- Dump dei dati della tabella ArtForm.utente: ~3 rows (circa)
/*!40000 ALTER TABLE `utente` DISABLE KEYS */;
INSERT INTO `utente` (`ID`, `nome`, `cognome`, `username`, `email`, `numeroTelefono`, `password`, `punteggio`) VALUES
	(1, 'Manbir', 'Aceveda', 'arianna', 'ift@', '338', 'password', 0),
	(2, 'Alessandro', 'Dituri', 'dv8d', '@#', NULL, 'pass', 0),
	(3, 'Mario', 'Rossi', 'marione', 'm@', NULL, '123456', 0);
/*!40000 ALTER TABLE `utente` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
