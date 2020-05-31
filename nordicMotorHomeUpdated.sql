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
	motorhome_id INT UNIQUE PRIMARY KEY NOT NULL,
	motorhome_reg_number VARCHAR(45) NOT NULL,
    motorhome_brand VARCHAR(45) NOT NULL,
    motorhome_room_height INT NOT NULL,
    motorhome_model VARCHAR(45) NOT NULL,
    motorhome_odometer INT NOT NULL,
    type_id INT NOT NULL,
    CONSTRAINT motorhomes_fk_mh_types
		FOREIGN KEY (type_id) REFERENCES mh_types (type_id)
);

DROP TABLE IF EXISTS repairs;

CREATE TABLE repairs
(
repair_id INT UNIQUE PRIMARY KEY NOT NULL AUTO_INCREMENT,
repair_description VARCHAR(45) NOT NULL,
repair_date DATE NOT NULL,
motorhome_id INT NOT NULL,
CONSTRAINT repairs_fk_motorhomes
	FOREIGN KEY (motorhome_id) REFERENCES motorhomes (motorhome_id)
    ON DELETE CASCADE
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
    contract_extra_bed_sheets TINYINT NOT NULL,
    contract_extra_picnic_table TINYINT NOT NULL,
    contract_extra_chairs TINYINT NOT NULL,
    contract_pick_up_distance INT NOT NULL,
    contract_drop_off_distance INT NOT NULL,
    customer_id INT NOT NULL,
    motorhome_id INT NOT NULL,
    CONSTRAINT contracts_fk_customers
		FOREIGN KEY (customer_id) REFERENCES customers (customer_id)
        ON DELETE CASCADE,
	CONSTRAINT contracts_fk_motorhomes
		FOREIGN KEY (motorhome_id) REFERENCES motorhomes (motorhome_id)
);

DROP TABLE IF EXISTS invoices;

CREATE TABLE invoices
(
	invoice_id INT PRIMARY KEY UNIQUE NOT NULL AUTO_INCREMENT,
    invoice_total_price DECIMAL NOT NULL,
    invoice_distance_driven INT NOT NULL,
    invoice_odometer_end INT NOT NULL,
    invoice_rent_days INT NOT NULL,
    contract_id INT NOT NULL,
    invoice_fuel_gage BOOLEAN NOT NULL,
    CONSTRAINT invoices_fk_contracts
		FOREIGN KEY (contract_id) REFERENCES contracts (contract_id)
        ON DELETE CASCADE
);


INSERT INTO zips VALUES
(2700, 'Brønshøj'),
(2200, 'Nørrebro'),
(9800,'Hjørring'),
(4850,'Stubbekøbing'),
(1416, 'Christianshavn');

INSERT INTO mh_types VALUES
(1, 'compact', 1999, 2, 2),
(2, 'Medium', 2499, 3, 3);

INSERT INTO customers VALUES
(1, 'Christoffer', 'Bjerge', 'Valkyriegade', 'abc1234', 'B','60110716', 'Dansk', 2200),
(2, 'William', 'Omoe', 'Lersoe Park allé', '123abc', 'E', '12345678', 'Dansk', 4850);

INSERT INTO motorhomes VALUES
(1, 'Reg12345', 'Volkswagen', '180', 'AMG', 1, 1),
(2, 'AM1235', 'Pegueot', '200', 'Cayenne', 200, 2);

INSERT INTO repairs VALUES
(1, 'shits fucked yo', '2020-02-22', 1);

INSERT INTO contracts VALUES
(1, 9999, '2020-01-01', '2020-01-02', 100, true, false, true, false, false, 35, 0, 1, 1),
(2, 5555, '2020-02-02', '2020-02-03', 20, true, false, false, false, false, 0, 45, 2, 2);

INSERT INTO invoices VALUES
(1,0,0,500,0,1,0);
-- (2,0,0,200,0,2,0);

SELECT inv.invoice_odometer_end - con.contract_odometer_start AS total_driven
FROM contracts con JOIN invoices inv ON inv.contract_id = con.contract_id;

SELECT invoice_id, invoice_total_price, i.invoice_odometer_end - con.contract_odometer_start AS invoice_distance_driven,i.invoice_odometer_end,i.contract_id
FROM invoices i JOIN contracts con ON i.contract_id = con.contract_id


