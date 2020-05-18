DROP DATABASE IF EXISTS nordicMotorhome;

CREATE DATABASE nordicMotorhome;

USE nordicMotorhome;

DROP TABLE IF EXISTS zips;

CREATE TABLE zips
(
	zip_code INT PRIMARY KEY UNIQUE NOT NULL,
    zip_city VARCHAR(45) NOT NULL 
    
);

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
    customer_nationality VARCHAR(45)  NOT NULL,
    zip_code INT NOT NULL,
    CONSTRAINT customers_fk_zips
		FOREIGN KEY (zip_code) REFERENCES zips (zip_code)
);

DROP TABLE IF EXISTS mh_types;

CREATE TABLE mh_types
(
	type_id INT PRIMARY KEY UNIQUE NOT NULL,
    type_description VARCHAR(45) NOT NULL,
    type_price_per_day INT NOT NULL,
    type_bed_count INT NOT NULL,
    type_seat_count INT NOT NULL
);

DROP TABLE IF EXISTS motorhomes;

CREATE TABLE motorhomes
(
	motorhome_reg_number VARCHAR(45) UNIQUE PRIMARY KEY NOT NULL,
    motorhome_brand VARCHAR(45) NOT NULL,
    motorhome_room_height INT NOT NULL,
    motorhome_model VARCHAR(45) NOT NULL,
    motorhome_odometer INT NOT NULL,
    type_id INT NOT NULL,
    CONSTRAINT motorhomes_fk_mh_types
		FOREIGN KEY (type_id) REFERENCES mh_types (type_id)
);

DROP TABLE IF EXISTS contracts;

CREATE TABLE contracts
(
	contract_id INT PRIMARY KEY UNIQUE NOT NULL AUTO_INCREMENT,
    contract_rent_price DECIMAL NOT NULL,
    contract_start_date DATE NOT NULL,
    contract_end_date DATE NOT NULL,
    contract_odometer_start INT NOT NULL,
    contract_extra_bike_rack TINYINT NOT NULL,
    contract_extra_child_seat TINYINT NOT NULL,
    contract_extra_picnic_table TINYINT NOT NULL,
    contract_extra_chairs TINYINT NOT NULL,
    customer_id INT NOT NULL,
    motorhome_reg_number VARCHAR(45) NOT NULL,
    CONSTRAINT contracts_fk_customers
		FOREIGN KEY (customer_id) REFERENCES customers (customer_id),
	CONSTRAINT contracts_fk_motorhomes
		FOREIGN KEY (motorhome_reg_number) REFERENCES motorhomes (motorhome_reg_number)
);

DROP TABLE IF EXISTS invoices;

CREATE TABLE invoices
(
	invoice_id INT PRIMARY KEY UNIQUE NOT NULL AUTO_INCREMENT,
    invoice_total_price DECIMAL NOT NULL,
    invoice_distance_driven INT NOT NULL,
    contract_id INT NOT NULL,
    CONSTRAINT invoices_fk_contracts
		FOREIGN KEY (contract_id) REFERENCES contracts (contract_id)
);



INSERT INTO mh_types VALUES
(1, 'compact', 1999, 2, 2);
