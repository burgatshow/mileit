CREATE DATABASE IF NOT EXISTS `mileit`;
USE `mileit`;

CREATE TABLE IF NOT EXISTS `cars` (
  `car_id` int(11) NOT NULL AUTO_INCREMENT,
  `manufacturer`int(11) NOT NULL,
  `model` varchar(20) COLLATE utf8mb4_hungarian_ci NOT NULL,
  `manufacture_date` datetime DEFAULT NULL,
  `color` varchar(7) COLLATE utf8mb4_hungarian_ci DEFAULT NULL,
  `vin` varchar(30) COLLATE utf8mb4_hungarian_ci NOT NULL,
  `plate_number` varchar(10) COLLATE utf8mb4_hungarian_ci NOT NULL,
  `fuel_capacity` double unsigned DEFAULT NULL,
  `fuel` tinyint(1) unsigned DEFAULT NULL,
  `start_date` datetime DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `description` varchar(255) COLLATE utf8mb4_hungarian_ci DEFAULT NULL,
  `friendly_name` varchar(255) COLLATE utf8mb4_hungarian_ci DEFAULT NULL,
  `user_id` int(11) NOT NULL,
  `active` tinyint(1) NOT NULL DEFAULT 1,
  `archived` int(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`car_id`),
  UNIQUE KEY `vin` (`vin`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_hungarian_ci;

CREATE TABLE IF NOT EXISTS `maintenances` (
  `mntnc_id` int(11) NOT NULL AUTO_INCREMENT,
  `car_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL DEFAULT 0,
  `pm_id` int(11) NOT NULL,
  `odometer` double unsigned DEFAULT NULL,
  `date` datetime DEFAULT current_timestamp(),
  `description` varchar(1000) COLLATE utf8mb4_hungarian_ci DEFAULT NULL,
  `amount` double NOT NULL,
  PRIMARY KEY (`mntnc_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_hungarian_ci;

CREATE TABLE IF NOT EXISTS `payment_method` (
  `pm_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8mb4_hungarian_ci NOT NULL,
  `user_id` int(11) NOT NULL,
  `description` varchar(100) COLLATE utf8mb4_hungarian_ci DEFAULT NULL,
  PRIMARY KEY (`pm_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_hungarian_ci;

CREATE TABLE IF NOT EXISTS `places` (
  `place_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `name` varchar(50) COLLATE utf8mb4_hungarian_ci NOT NULL,
  `address` varchar(50) COLLATE utf8mb4_hungarian_ci DEFAULT NULL,
  `longitude` double DEFAULT NULL,
  `latitude` double DEFAULT NULL,
  PRIMARY KEY (`place_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_hungarian_ci;

CREATE TABLE IF NOT EXISTS `refuels` (
  `refuel_id` int(11) NOT NULL AUTO_INCREMENT,
  `car_id` int(11) NOT NULL,
  `place_id` int(11) DEFAULT NULL,
  `refuel_timestamp` datetime NOT NULL DEFAULT current_timestamp(),
  `odometer` double unsigned DEFAULT NULL,
  `unit_price` double NOT NULL,
  `fuel_amount` double NOT NULL,
  `pm_id` int(11) DEFAULT NULL,
  `amount` double unsigned NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`refuel_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_hungarian_ci;

CREATE TABLE IF NOT EXISTS `users` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) COLLATE utf8mb4_hungarian_ci DEFAULT NULL,
  `currency` varchar(5) COLLATE utf8mb4_hungarian_ci DEFAULT 'Ft',
  `locale` varchar(2) COLLATE utf8mb4_hungarian_ci DEFAULT 'hu',
  `distance` int(1) UNSIGNED NOT NULL DEFAULT '1',
  `rounded` INT(1) UNSIGNED NOT NULL DEFAULT '1' AFTER
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_hungarian_ci;

CREATE TABLE IF NOT EXISTS `sup_car_manufacturers` (
  `manufacturer_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8mb4_hungarian_ci NOT NULL DEFAULT '0',
  `active` int(1) NOT NULL DEFAULT 1,
  PRIMARY KEY (`manufacturer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_hungarian_ci;

INSERT INTO sup_car_manufacturers (name) VALUES
	('FORD'),
	('VOLKSWAGEN'),
	('BMW'),
	('ABARTH'),
	('AC'),
	('CURA'),
	('DLER'),
	('IXAM'),
	('LEKO'),
	('LFA ROMEO'),
	('LPINA'),
	('MC'),
	('RO'),
	('ASIA'),
	('ASTON MARTIN'),
	('AUDI'),
	('AUSTIN MORRIS'),
	('AUTOBIANCHI'),
	('BAJAJ'),
	('BARKAS'),
	('BENTLEY'),
	('BERTONE'),
	('BRILLIANCE (BMW)'),
	('BRILLIANCE (ZHONGHUA)'),
	('BUICK'),
	('CADILLAC'),
	('CHEVROLET'),
	('CHRYSLER'),
	('CITROEN'),
	('DACIA'),
	('DAEWOO'),
	('DAIHATSU'),
	('DAIMLER'),
	('DATSUN'),
	('DKW'),
	('DODGE'),
	('DR'),
	('DS'),
	('FERRARI'),
	('FIAT'),
	('FISKER'),
	('GAZ'),
	('GEO'),
	('GMC'),
	('GOGGOMOBIL'),
	('GREAT WALL'),
	('HONDA'),
	('HUDSON'),
	('HUMBER'),
	('HUMMER'),
	('HYUNDAI'),
	('INFINITI'),
	('INNOCENTI'),
	('ISUZU'),
	('IVECO'),
	('JAGUAR'),
	('JDM'),
	('JEEP'),
	('JENSEN'),
	('KIA'),
	('LADA'),
	('LAMBORGHINI'),
	('LANCIA'),
	('LAND ROVER'),
	('LEXUS'),
	('LIGIER'),
	('LINCOLN'),
	('LONDON'),
	('LUAZ'),
	('MAHINDRA'),
	('MARUTI'),
	('MASERATI'),
	('MATRA'),
	('MAZDA'),
	('MCLAREN'),
	('MERCEDES-AMG'),
	('MERCEDES-BENZ'),
	('MERCEDES-MAYBACH'),
	('MERCURY'),
	('MG'),
	('MINAUTO'),
	('MINI'),
	('MITSUBISHI'),
	('MORRIS'),
	('MOSZKVICS'),
	('NISSAN'),
	('NSU'),
	('NYSA'),
	('OLDSMOBILE'),
	('OPEL'),
	('PACKARD'),
	('PAGANI'),
	('PEUGEOT'),
	('PIAGGIO'),
	('PLYMOUTH'),
	('POLSKI FIAT'),
	('PONTIAC'),
	('PORSCHE'),
	('PROTON'),
	('RELIANT'),
	('RENAULT'),
	('REPLIKA'),
	('ROLLS-ROYCE'),
	('ROVER'),
	('SAAB'),
	('SATURN'),
	('SEAT'),
	('SIMCA'),
	('SKODA'),
	('SMART'),
	('SSANGYONG'),
	('STEYR PUCH'),
	('SUBARU'),
	('SUZUKI'),
	('TALBOT'),
	('TATA'),
	('TATRA'),
	('TAVRIA'),
	('TAZZARI'),
	('TESLA'),
	('TOYOTA'),
	('TRABANT'),
	('TRIUMPH'),
	('UAZ'),
	('VAUXHALL'),
	('VOLGA'),
	('VOLVO'),
	('WANDERER'),
	('WARSZAWA'),
	('WARTBURG'),
	('WOLSELEY'),
	('YUGO'),
	('ZAPOROZSEC'),
	('ZASTAVA');

CREATE TABLE IF NOT EXISTS `sup_tyre_manufacturers` (
  `manufacturer_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8mb4_hungarian_ci NOT NULL DEFAULT '0',
  `active` int(1) NOT NULL DEFAULT 1,
  PRIMARY KEY (`manufacturer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_hungarian_ci;

INSERT INTO mileit.sup_tyre_manufacturers (name) VALUES 
	('ACHILLES'),
	('APOLLO'),
	('AUSTONE'),
	('BARUM'),
	('BFGOODRICH'),
	('BRIDGESTONE'),
	('CHENGSHAN'),
	('CONTINENTAL'),
	('COOPER'),
	('DEBICA'),
	('DOUBLESTAR'),
	('DUNLOP'),
	('ETERNITY'),
	('FALKEN'),
	('FIRESTONE'),
	('FORTUNE'),
	('FULDA'),
	('GOALSTAR'),
	('GOODRIDE'),
	('GOODYEAR'),
	('GRIPMAX'),
	('HANKOOK'),
	('INFINITY'),
	('INTERSTATE'),
	('KLÉBER'),
	('KORMORAN'),
	('KUMHO'),
	('LANDSAIL'),
	('LAUFENN'),
	('MAXXIS'),
	('MICHELIN'),
	('MOMO'),
	('NANKANG'),
	('NEXEN'),
	('PIRELLI'),
	('PRESTIVO'),
	('RAPID'),
	('RIKEN'),
	('ROADSTONE'),
	('ROTALLA'),
	('SAETTA'),
	('SAILUN'),
	('SAVA'),
	('SEMPERIT'),
	('SUMITOMO'),
	('TOYO'),
	('TRIANGLE'),
	('UNIROYAL'),
	('VREDESTEIN'),
	('YOKOHAMA');
