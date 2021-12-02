DROP TABLE IF EXISTS `Acquisti`;

CREATE TABLE `Acquisti` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cliente_id` int(11) DEFAULT NULL,
  `prodotto_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `clienteFK_idx` (`cliente_id`),
  KEY `prodottoFK_idx` (`prodotto_id`),
  CONSTRAINT `clienteFK` FOREIGN KEY (`cliente_id`) REFERENCES `clienti` (`id`),
  CONSTRAINT `prodottoFK` FOREIGN KEY (`prodotto_id`) REFERENCES `prodotti` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


LOCK TABLES `Acquisti` WRITE;

INSERT INTO `Acquisti` VALUES (1,1,1),(2,1,2),(3,2,3);

UNLOCK TABLES;


DROP TABLE IF EXISTS `Clienti`;

CREATE TABLE `Clienti` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(45) DEFAULT NULL,
  `cognome` varchar(45) DEFAULT NULL,
  `datanascina` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


LOCK TABLES `Clienti` WRITE;

INSERT INTO `Clienti` VALUES (1,'Antonio','Lezzi',NULL),(2,'Mario','Rossi','1970-04-20'),(3,'Giuseppe','Verdi',NULL),(4,'Mosè','Bianchi',NULL);

UNLOCK TABLES;



DROP TABLE IF EXISTS `Prodotti`;

CREATE TABLE `Prodotti` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(45) DEFAULT NULL,
  `prezzo` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



LOCK TABLES `Prodotti` WRITE;

INSERT INTO `Prodotti` VALUES (1,'CocaCola',2),(2,'Patatine',1),(3,'Pasta',1),(4,'Cellulare',200),(5,'Penna',3);

UNLOCK TABLES;
