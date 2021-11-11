CREATE DATABASE IF NOT EXISTS saviyayougurt;
SHOW DATABASES;
USE saviyayougurt;

DROP TABLE IF EXISTS loginForm ;
CREATE TABLE IF NOT EXISTS loginForm(
    role VARCHAR (50) not null,
    userName VARCHAR (40) not null,
    password VARCHAR (20) not null,
    CONSTRAINT PRIMARY KEY(userName)
);

SHOW TABLES;
DESC loginForm;

DROP TABLE IF EXISTS employee;
CREATE TABLE IF NOT EXISTS employee(
    employeeId VARCHAR(20),
    employeeType VARCHAR(20),
    employeeNIC VARCHAR(20),
    employeeName VARCHAR(100) DEFAULT 'Unknown',
    employeeAddress VARCHAR(100),
    employeePhoneNumber INT(12),
    employeeSalary DOUBLE(10,2),
    CONSTRAINT PRIMARY KEY(employeeId)
);
ALTER TABLE employee MODIFY COLUMN employeePhoneNumber VARCHAR(20);

SHOW TABLES;
DESC employee;

DROP TABLE IF EXISTS store;
CREATE TABLE IF NOT EXISTS store(
    itemCode VARCHAR (20),
    description VARCHAR (100),
    unitPrice DECIMAL(10,2),
    qtyOnHand INT(10),
    disconnect DECIMAL(6,2),
    CONSTRAINT PRIMARY KEY (itemCode)
);
SHOW TABLES;
DESC store;

DROP TABLE IF EXISTS driver;
CREATE TABLE IF NOT EXISTS driver(
    driverID VARCHAR (10),
    driverNIC VARCHAR (20),
    driverName VARCHAR (100) NOT NULL DEFAULT 'Unknown',
    driverAddress VARCHAR (100),
    driverPhoneNumber int(10),
    CONSTRAINT PRIMARY KEY (driverID)
);

ALTER TABLE driver MODIFY COLUMN driverPhoneNumber VARCHAR(20);
SHOW TABLES;
DESC driver;

DROP TABLE IF NOT EXISTS vehicle;
CREATE TABLE IF NOT EXISTS vehicle(
    vehicleID VARCHAR (10),
    vehicleNumber VARCHAR (20),
    vehicleType VARCHAR (50),
    vehicleRent VARCHAR (20),
    CONSTRAINT PRIMARY KEY (vehicleID)
);
SHOW TABLES;
DESC vehicle;

DROP TABLE IF NOT EXISTS ref;
CREATE TABLE IF NOT EXISTS ref(
    refID VARCHAR (20),
    refNIC VARCHAR (20),
    refName VARCHAR (100) NOT NULL DEFAULT 'Unknown',
    refAddress VARCHAR (100),
    refPhoneNumber int(10),
    CONSTRAINT PRIMARY KEY(refID)
);
ALTER TABLE ref MODIFY COLUMN refPhoneNumber VARCHAR(20);

SHOW TABLES;
DESC ref;

DROP TABLE IF NOT EXISTS shop;
CREATE TABLE IF NOT EXISTS shop(
    shopID VARCHAR (20),
    shopName VARCHAR (50),
    shopPhoneNumber INT (10),
    shopAddress VARCHAR (100),
    CONSTRAINT PRIMARY KEY (shopID)
);
ALTER TABLE shop MODIFY COLUMN shopPhoneNumber VARCHAR(20);

SHOW TABLES;
DESC shop;

DROP TABLE IF NOT EXISTS orderDetails;
DROP TABLE  orderDetails;
CREATE TABLE IF NOT EXISTS orderDetails(
    orderID VARCHAR(20),
    refID VARCHAR(20),
    shopID VARCHAR(20),
    itemCode VARCHAR (20),
    unitPrice VARCHAR (20),
    qtyOnHand INT,
    qty INT,
    total DOUBLE DEFAULT 0.00,
    CONSTRAINT PRIMARY KEY (orderID,refID),
    CONSTRAINT FOREIGN KEY (refID) REFERENCES ref(refID) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT FOREIGN KEY (shopID) REFERENCES shop(shopID) ON DELETE CASCADE ON UPDATE CASCADE
);

SELECT * FROM orderDetails;
SHOW TABLES;
DESC orderDetails;

INSERT INTO loginForm VALUES ('Manager','upeksha','1234');
DESC salary;
SHOW TABLES;



INSERT INTO loginForm VALUES('StoreKeeper','Sachintha','1234');
CREATE TABLE salary(
    salaryId VARCHAR (20),
    employeeId VARCHAR(20),
    employeeSalary DECIMAL (20),
    incentive DOUBLE DEFAULT 0.00,
    ot DOUBLE DEFAULT 0.00,
    total DOUBLE DEFAULT 0.00,
    CONSTRAINT FOREIGN KEY (employeeId) REFERENCES employee(employeeId) ON DELETE CASCADE ON UPDATE CASCADE
);
SELECT * FROM loginForm;

DESC salary;
SHOW TABLES;
DROP TABLE  salary;


select itemCode,sum(qty) as qty form orderDetails group by itemCode;

