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
		ON CASCADE,
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
	    ON CASCADE
);


INSERT INTO zips VALUES
(2700, 'Brønshøj'),
(2200, 'Nørrebro'),
(9800,'Hjørring'),
(4850,'Stubbekøbing'),
(1416, 'Christianshavn'),
(0167, 'Oslo'),
(3540, 'Lynge'),
(4000, 'Roskilde'),
(4735, 'Mern');

INSERT INTO mh_types VALUES
(1, 'Compact', 200, 1, 2),
(2, 'Medium', 250, 2, 3),
(3, 'Large', 300, 2, 4),
(4, 'Family medium', 269, 3, 3),
(5, 'Family large', 329, 4, 4),
(6, 'Luxury compact', 289, 1, 2),
(7, 'Luxury medium', 319, 2, 3),
(8, 'Luxury Large', 359, 2, 4);

INSERT INTO customers VALUES
(1, 'Christoffer', 'Bjerge', 'Valkyriegade 32', '1829382', 'B','60110716', 'Dansk', 2200),
(2, 'William', 'Omoe', 'Lersoe Park allé 19', '1233427', 'E', '12345678', 'Dansk', 4850),
(3, 'Hening', 'Frederiksen', 'Nørrebrogade 78', '283948','B', '83948192', 'Dansk', 2200),
(4, 'Strom', 'Willan', 'Hegdehaugsveien 20', '28477141', 'E', '24495849', 'Norsk', 0167),
(5, 'Henrik', 'Olesen', 'Stendyssevej 13', '23948182', 'C', '42837491', 'Dansk', 3540),
(6, 'Jørgen', 'MeierMann', 'Vandtårnsvej 2', '28387491', 'B', '2837481', 'Dansk', 4735),
(7, 'Birte', 'Bülow', 'Maglegårdsvej 32', '3087646', 'C', '19062742', 'Dansk', 4000);

INSERT INTO motorhomes VALUES
(1, 'RE32344', 'Volkswagen', '180', 'Grand califonia', 0, 2),
(2, 'EM20384', 'Volkswagen', '180', 'Grand califonia', 0, 2),
(3, 'OP39409', 'Volkswagen', '180', 'Grand califonia', 0, 2),
(4, 'HL92849', 'Volkswagen', '180', 'Grand califonia', 0, 2),
(5, 'DE20392', 'Dethleffs', '200', 'Advantage I 6501', 4000, 3),
(6, 'GO29284', 'Dethleffs', '200', 'Advantage I 6501', 2948, 3),
(7, 'KL02948', 'Dethleffs', '200', 'Advantage I 6501', 238, 3),
(8, 'PX02940', 'McLouis', '175', 'Lagan', 329, 1),
(9, 'GL29490', 'McLouis', '175', 'Lagan', 789, 1),
(10, 'GJ28394', 'McLouis', '175', 'Lagan', 890, 1),
(11, 'BN28390', 'McLouis', '175', 'Lagan', 21, 1),
(12, 'PJ28340', 'McLouis', '175', 'Lagan', 234, 1),
(13, 'IO92800', 'McLouis', '175', 'Lagan', 523, 1),
(14, 'JK92092', 'Niesmann & Bischoff', '180', 'Smove 7.4E', 478, 8),
(15, 'HJ92092', 'Niesmann & Bischoff', '180', 'Smove 7.4E', 498, 8),
(16, 'OP92842', 'Sunlight', '200', 'A 70 2020', 785, 5),
(17, 'JL02940', 'Sunlight', '200', 'A 70 2020', 904, 5),
(18, 'NM02940', 'Sunlight', '200', 'A 70 2020', 289, 5),
(19, 'DF29584', 'Sunlight', '200', 'A 70 2020', 596, 5),
(20, 'GH29845', 'Sunlight', '180', 'T 58 2019', 949, 4),
(21, 'JI29489', 'Sunlight', '180', 'T 58 2019', 900, 4),
(22, 'BN29854', 'Chausson', '175', 'Twist Prestige', 257, 1),
(23, 'IG29058', 'Chausson', '175', 'Twist Prestige', 56, 1),
(24, 'BG28941', 'Chausson', '175', 'Twist Prestige', 862, 1),
(25, 'ET29024', 'Airstream', '185', 'Interstate', 912, 6),
(26, 'FH23452', 'Airstream', '185', 'Interstate', 276, 6),
(27, 'ET29024', 'Airstream', '185', 'Interstate', 1021, 6),
(28, 'DE28478', 'Winnebago', '200', 'View', 1534, 7),
(29, 'UI92842', 'Winnebago', '200', 'View', 1634, 7),
(30, 'KL90289', 'Winnebago', '200', 'View', 1784, 7),
(31, 'DB29482', 'Winnebago', '200', 'View', 1424, 7),
(32, 'VY20485', 'Winnebago', '200', 'View', 1674, 7);


INSERT INTO repairs VALUES
(1, 'Left headlight needs new lightbolp', '2020-02-22', 1),
(2, 'Needs new tires', '2020-04-14', 18),
(3, 'Car battery needs replaments', '2020-02-22', 31);


INSERT INTO contracts VALUES
(1, 890, '2020-01-01', '2020-01-20', 100, true, false, true, false, false, 35, 0, 1, 1),
(2, 990, '2020-02-02', '2020-02-03', 20, true, false, false, false, false, 0, 45, 2, 2),
(3, 799, '2020-05-19', '2020-06-4', 20, true, true, true, true, true, 0, 45, 4, 14),
(4, 925, '2020-07-27', '2020-08-03', 20, false, false, true, true, true, 0, 0, 6, 21);

INSERT INTO invoices VALUES
(1,0,0,500,0,1,0),
(2,67,0,300,16,3,0),
(3,0,78,789,7,4,1);

SELECT * FROM invoices;
-- SELECT inv.invoice_odometer_end - con.contract_odometer_start AS total_driven
-- FROM contracts con JOIN invoices inv ON inv.contract_id = con.contract_id;

-- SELECT invoice_id, invoice_total_price, i.invoice_odometer_end - con.contract_odometer_start AS invoice_distance_driven,i.invoice_odometer_end,i.contract_id
-- FROM invoices i JOIN contracts con ON i.contract_id = con.contract_id


