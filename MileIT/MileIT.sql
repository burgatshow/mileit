CREATE DATABASE IF NOT EXISTS `mileit`;
USE `mileit`;

CREATE TABLE IF NOT EXISTS `cars` (
  `car_id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `manufacturer`INT(11) UNSIGNED NOT NULL,
  `model` VARCHAR(20) COLLATE utf8mb4_hungarian_ci NOT NULL,
  `manufacture_date` DATETIME DEFAULT NULL,
  `color` VARCHAR(7) COLLATE utf8mb4_hungarian_ci DEFAULT NULL,
  `vin` VARCHAR(30) COLLATE utf8mb4_hungarian_ci NOT NULL,
  `plate_number` VARCHAR(10) COLLATE utf8mb4_hungarian_ci NOT NULL,
  `fuel_capacity` DOUBLE UNSIGNED DEFAULT NULL,
  `fuel` TINYINT(1) UNSIGNED DEFAULT NULL,
  `start_date` DATETIME DEFAULT NULL,
  `end_date` DATETIME DEFAULT NULL,
  `description` VARCHAR(255) COLLATE utf8mb4_hungarian_ci DEFAULT NULL,
  `friendly_name` VARCHAR(255) COLLATE utf8mb4_hungarian_ci DEFAULT NULL,
  `user_id` INT(11) UNSIGNED NOT NULL,
  `active` TINYINT(1) UNSIGNED NOT NULL DEFAULT 1,
  `archived` TINYINT(1) UNSIGNED NOT NULL DEFAULT 0,
  PRIMARY KEY (`car_id`),
  UNIQUE KEY `vin` (`vin`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_hungarian_ci;

CREATE TABLE IF NOT EXISTS `maintenances` (
  `mntnc_id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `car_id` INT(11) UNSIGNED NOT NULL,
  `user_id` INT(11) UNSIGNED NOT NULL DEFAULT 0,
  `pm_id` INT(11) UNSIGNED NOT NULL,
  `odometer` DOUBLE UNSIGNED DEFAULT NULL,
  `date` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `description` VARCHAR(1000) COLLATE utf8mb4_hungarian_ci DEFAULT NULL,
  `amount` DOUBLE UNSIGNED NOT NULL,
  PRIMARY KEY (`mntnc_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_hungarian_ci;

CREATE TABLE IF NOT EXISTS `payment_method` (
  `pm_id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) COLLATE utf8mb4_hungarian_ci NOT NULL,
  `user_id` INT(11) UNSIGNED NOT NULL,
  `description` VARCHAR(100) COLLATE utf8mb4_hungarian_ci DEFAULT NULL,
  `archived` TINYINT(1) UNSIGNED NOT NULL DEFAULT 0,
  PRIMARY KEY (`pm_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_hungarian_ci;

CREATE TABLE IF NOT EXISTS `places` (
  `place_id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` INT(11) UNSIGNED NOT NULL,
  `name` VARCHAR(50) COLLATE utf8mb4_hungarian_ci NOT NULL,
  `address` VARCHAR(50) COLLATE utf8mb4_hungarian_ci DEFAULT NULL,
  `longitude` DOUBLE DEFAULT NULL,
  `latitude` DOUBLE DEFAULT NULL,
  `fuel_station` TINYINT(1) UNSIGNED NULL DEFAULT 1,
  `archived` TINYINT(1) UNSIGNED NULL DEFAULT 0,
  PRIMARY KEY (`place_id`))
 ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_hungarian_ci;

CREATE TABLE IF NOT EXISTS `refuels` (
  `refuel_id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `car_id` INT(11) UNSIGNED NOT NULL,
  `place_id` INT(11) UNSIGNED NULL,
  `refuel_timestamp` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `odometer` DOUBLE UNSIGNED DEFAULT NULL,
  `unit_price` DOUBLE UNSIGNED NOT NULL,
  `fuel_amount` DOUBLE UNSIGNED NOT NULL,
  `pm_id` INT(11) UNSIGNED DEFAULT NULL,
  `amount` DOUBLE UNSIGNED NOT NULL,
  `partial_refuel` TINYINT(1) UNSIGNED NOT NULL DEFAULT 0, 
  `user_id` INT(11) UNSIGNED NOT NULL,
  PRIMARY KEY (`refuel_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_hungarian_ci;

CREATE TABLE IF NOT EXISTS `users` (
  `user_id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(20) NOT NULL COLLATE utf8mb4_hungarian_ci,
  `currency` VARCHAR(5) NOT NULL COLLATE utf8mb4_hungarian_ci DEFAULT 'Ft',
  `locale` VARCHAR(2) NOT NULL COLLATE utf8mb4_hungarian_ci DEFAULT 'hu',
  `distance` TINYINT(1) UNSIGNED NOT NULL DEFAULT 1,
  `rounded` TINYINT(1) UNSIGNED NOT NULL DEFAULT 1,
  `archived` TINYINT(1) UNSIGNED NOT NULL DEFAULT 0,
  PRIMARY KEY (`user_id`), UNIQUE KEY `username` (`username`)) 
  ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_hungarian_ci;

CREATE TABLE IF NOT EXISTS `sup_car_manufacturers` (
  `manufacturer_id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) COLLATE utf8mb4_hungarian_ci NOT NULL DEFAULT 0,
  `active` TINYINT(1) UNSIGNED NOT NULL DEFAULT 1,
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

CREATE TABLE IF NOT EXISTS `tyres` (
  `tyre_id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` INT(11) UNSIGNED NOT NULL,
  `type` INT(1) UNSIGNED NOT NULL DEFAULT 1,
  `manufacturer` INT(11) UNSIGNED NOT NULL,
  `model` VARCHAR(50) COLLATE utf8mb4_hungarian_ci NOT NULL,
  `axis` INT(1) UNSIGNED NOT NULL DEFAULT 1,
  `size_r` INT(11) UNSIGNED NOT NULL,
  `size_h` INT(11) UNSIGNED NOT NULL,
  `size_w` INT(11) UNSIGNED NOT NULL,
  `purchase_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `archived` TINYINT(1) UNSIGNED NOT NULL DEFAULT 0,
  PRIMARY KEY (`tyre_id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_hungarian_ci;

CREATE TABLE IF NOT EXISTS `sup_tyre_manufacturers` (
  `manufacturer_id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) COLLATE utf8mb4_hungarian_ci NOT NULL DEFAULT 0,
  `active` TINYINT(1) UNSIGNED NOT NULL DEFAULT 1,
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

CREATE TABLE IF NOT EXISTS `tyres_events` (
	`te_id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
	`user_id` INT(11) UNSIGNED NOT NULL,
	`tyre_id` INT(11) UNSIGNED NOT NULL,
	`car_id` INT(11) UNSIGNED NOT NULL,
	`odometer_start` DOUBLE UNSIGNED NOT NULL,
	`odometer_end` DOUBLE UNSIGNED NULL,
	`change_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY (`te_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_hungarian_ci;

CREATE TABLE IF NOT EXISTS `routes` (
	`route_id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
	`user_id` INT(11) UNSIGNED NOT NULL,
	`car_id` INT(11) UNSIGNED NOT NULL,
	`start_place_id` INT(11) UNSIGNED NOT NULL,
	`end_place_id` INT(11) UNSIGNED NOT NULL,
	`route_type` TINYINT(1) UNSIGNED NOT NULL DEFAULT 0,
	`route_datetime` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`route_distance` DOUBLE UNSIGNED NULL,
	`archived` TINYINT(1) UNSIGNED NOT NULL DEFAULT 0,
	PRIMARY KEY (`route_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_hungarian_ci;

