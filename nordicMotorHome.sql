DROP DATABASE IF EXISTS nordicMotorhome;

CREATE DATABASE nordicMotorhome;

USE nordicMotorhome;

DROP TABLE IF EXISTS customers;

CREATE TABLE customers
(
	customer_id  INT  PRIMARY KEY NOT NULL AUTO_INCREMENT,
    customer_first_name VARCHAR(45)  NOT NULL,
    customer_last_name VARCHAR(45)  NOT NULL,
    customer_address VARCHAR(45)  NOT NULL,
    customer_drivers_license VARCHAR(45)  NOT NULL,
    customer_license_type VARCHAR(45)  NOT NULL,
    customer_phone VARCHAR(45) NOT NULL,
    customer_city VARCHAR(45)  NOT NULL,
    customer_country VARCHAR(45)  NOT NULL,
    customer_zip_code INT  NOT NULL
);


