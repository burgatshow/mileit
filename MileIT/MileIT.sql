CREATE DATABASE IF NOT EXISTS `mileit`;
USE `mileit`;

CREATE TABLE IF NOT EXISTS `cars` (
  `car_id` int(11) NOT NULL AUTO_INCREMENT,
  `manufacturer` varchar(20) COLLATE utf8mb4_hungarian_ci NOT NULL,
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
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_hungarian_ci;
