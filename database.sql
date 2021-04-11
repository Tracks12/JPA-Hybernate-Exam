-- --------------------------------------------------------
-- Hôte :                        127.0.0.1
-- Version du serveur:           10.4.12-MariaDB - mariadb.org binary distribution
-- SE du serveur:                Win64
-- HeidiSQL Version:             10.2.0.5599
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Listage de la structure de la base pour evaluation
DROP DATABASE IF EXISTS `evaluation`;
CREATE DATABASE IF NOT EXISTS `evaluation` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `evaluation`;

-- Listage de la structure de la table evaluation. libraries
DROP TABLE IF EXISTS `libraries`;
CREATE TABLE IF NOT EXISTS `libraries` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `type` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- Listage des données de la table evaluation.libraries : ~0 rows (environ)
/*!40000 ALTER TABLE `libraries` DISABLE KEYS */;
INSERT INTO `libraries` (`id`, `name`, `type`) VALUES
	(1, 'Ohio Central Library', 'CENTRAL');
/*!40000 ALTER TABLE `libraries` ENABLE KEYS */;

-- Listage de la structure de la table evaluation. books
DROP TABLE IF EXISTS `books`;
CREATE TABLE IF NOT EXISTS `books` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(50) DEFAULT NULL,
  `isbn_group` int(11) DEFAULT NULL,
  `isbn_registrant` int(11) DEFAULT NULL,
  `isbn_publication` int(11) DEFAULT NULL,
  `isbn_checksum` int(11) DEFAULT NULL,
  `library_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_books_books` (`library_id`),
  CONSTRAINT `FK_books_books` FOREIGN KEY (`library_id`) REFERENCES `libraries` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- Listage des données de la table evaluation.books : ~0 rows (environ)
/*!40000 ALTER TABLE `books` DISABLE KEYS */;
INSERT INTO `books` (`id`, `title`, `isbn_group`, `isbn_registrant`, `isbn_publication`, `isbn_checksum`, `library_id`) VALUES
	(1, 'JPA for dummy', 5, 1542, 1542, 2, 1);
/*!40000 ALTER TABLE `books` ENABLE KEYS */;

-- Listage de la structure de la table evaluation. customers
DROP TABLE IF EXISTS `customers`;
CREATE TABLE IF NOT EXISTS `customers` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `library_id` bigint(20) DEFAULT NULL,
  `lastName` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_customers_libraries` (`library_id`),
  CONSTRAINT `FK_customers_libraries` FOREIGN KEY (`library_id`) REFERENCES `libraries` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- Listage des données de la table evaluation.customers : ~0 rows (environ)
/*!40000 ALTER TABLE `customers` DISABLE KEYS */;
INSERT INTO `customers` (`id`, `library_id`, `lastName`) VALUES
	(1, 1, 'Dupond');
/*!40000 ALTER TABLE `customers` ENABLE KEYS */;

-- Listage de la structure de la table evaluation. customers_firstnames
DROP TABLE IF EXISTS `customers_firstnames`;
CREATE TABLE IF NOT EXISTS `customers_firstnames` (
  `customer_id` bigint(20) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  KEY `FK_customers_firstnames_customers` (`customer_id`),
  CONSTRAINT `FK_customers_firstnames_customers` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Listage des données de la table evaluation.customers_firstnames : ~0 rows (environ)
/*!40000 ALTER TABLE `customers_firstnames` DISABLE KEYS */;
INSERT INTO `customers_firstnames` (`customer_id`, `name`) VALUES
	(1, 'Jean'),
	(1, 'Guillaume');
/*!40000 ALTER TABLE `customers_firstnames` ENABLE KEYS */;

-- Listage de la structure de la table evaluation. borrowings
DROP TABLE IF EXISTS `borrowings`;
CREATE TABLE IF NOT EXISTS `borrowings` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `customer_id` bigint(20) DEFAULT NULL,
  `book_id` bigint(20) DEFAULT NULL,
  `start_date` date DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_borrowings_customers` (`customer_id`),
  KEY `FK_borrowings_books` (`book_id`),
  CONSTRAINT `FK_borrowings_books` FOREIGN KEY (`book_id`) REFERENCES `books` (`id`),
  CONSTRAINT `FK_borrowings_customers` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- Listage des données de la table evaluation.borrowings : ~0 rows (environ)
/*!40000 ALTER TABLE `borrowings` DISABLE KEYS */;
INSERT INTO `borrowings` (`id`, `customer_id`, `book_id`, `start_date`, `end_date`) VALUES
	(1, 1, 1, '2020-03-09', NULL);
/*!40000 ALTER TABLE `borrowings` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
