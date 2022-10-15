DROP DATABASE IF EXISTS JDDistributors ;
CREATE DATABASE IF NOT EXISTS JDDistributors;
SHOW DATABASES ;
USE JDDistributors;
//////////////////////////

DROP TABLE IF EXISTS Administrator;
CREATE TABLE IF NOT EXISTS Administrator(
    AdminName VARCHAR(20),
    AdminDateOfBirth VARCHAR(15),
    AdminNIC VARCHAR(28),
    ContuctNumber int,
    AdminEmail VARCHAR(30),
    AdminUserName VARCHAR(15) NOT NULL,
    Password VARCHAR(15)
    );
SHOW TABLES ;
DESCRIBE Administrator;

/////////////////////////
DROP TABLE IF EXISTS Employee$And$Role;
CREATE TABLE IF NOT EXISTS Employee$And$Role(
    RoleID VARCHAR(10),
    EmployeeName VARCHAR(26),
    EmployeeNIC VARCHAR(28),
    EmployeeAddress VARCHAR(30) NOT NULL DEFAULT 'Unknown',
    RoleDescription VARCHAR(30),
    ContuctNumber int,
    BasicSalary DOUBLE DEFAULT 0.00,
    CONSTRAINT PRIMARY KEY (RoleID)
    );
SHOW TABLES ;
DESCRIBE Employee$And$Role;

///////////////////////////
DROP TABLE IF EXISTS System$User ;
CREATE TABLE IF NOT EXISTS System$User(
    RoleID VARCHAR(10),
    UserName VARCHAR(28) NOT NULL,
    Password VARCHAR(15),
    CONSTRAINT FOREIGN KEY (RoleID) REFERENCES Employee$And$Role(RoleID) ON DELETE CASCADE ON UPDATE CASCADE    );
SHOW TABLES ;
DESCRIBE System$User;

////////////////////////////

DROP TABLE IF EXISTS Salary;
CREATE TABLE IF NOT EXISTS Salary(
    RoleID VARCHAR(10),
    BasicSalary DOUBLE DEFAULT 0.00,
    MonthlyAdvance DOUBLE DEFAULT 0.00,
    SalaryIncrement DOUBLE DEFAULT 0.00,
    SalaryCut DOUBLE DEFAULT 0.00,
    FinalSalary DOUBLE DEFAULT 0.00,
    CONSTRAINT FOREIGN KEY (RoleID) REFERENCES Employee$And$Role(RoleID) ON DELETE CASCADE ON UPDATE CASCADE    );
SHOW TABLES ;
DESCRIBE Salary;

//////////////////////////

DROP TABLE IF EXISTS Supplier;
CREATE TABLE IF NOT EXISTS Supplier(
    SupplierID VARCHAR(10),
    SupplierName VARCHAR(26) ,
    SupplierAddress VARCHAR(30),
    SupplierEmail VARCHAR (20),
    ContuctNumber int,
    CONSTRAINT PRIMARY KEY (SupplierID)
    );
SHOW TABLES ;
DESCRIBE Supplier;

/////////////////////////////////

DROP TABLE IF EXISTS Perfume;
CREATE TABLE IF NOT EXISTS Perfume(
    PerfumeCode VARCHAR(10),
    NameOfPerfume VARCHAR(26),
    Quantity int,
    DistributePrice DOUBLE,
    UnitPrice DOUBLE,
    MRPPrice DOUBLE,
    SupplierID VARCHAR(10),
    CONSTRAINT PRIMARY KEY (PerfumeCode),
    CONSTRAINT FOREIGN KEY (SupplierID) REFERENCES Supplier(SupplierID) ON DELETE CASCADE ON UPDATE CASCADE    );

SHOW TABLES ;
DESCRIBE Perfume;

//////////////////////////////////
DROP TABLE IF EXISTS ReturnPerfume;
CREATE TABLE IF NOT EXISTS Return$Perfume(
    PerfumeCode VARCHAR(10),
    Quantity int,
    ReturnedDate Date,
    Amount DOUBLE,
    CONSTRAINT FOREIGN KEY (PerfumeCode) REFERENCES Perfume(PerfumeCode) ON DELETE CASCADE ON UPDATE CASCADE    );

SHOW TABLES ;
DESCRIBE Return$Perfume;

////////////////////////////////////

DROP TABLE IF EXISTS Customer$Invoice;
CREATE TABLE IF NOT EXISTS Customer$Invoice(
    InvoiceNumber VARCHAR(10),
    CustomerName VARCHAR(26),
    Address VARCHAR(28),
    InvoiceDate DATE,
    CONSTRAINT PRIMARY KEY (InvoiceNumber));

SHOW TABLES ;
DESCRIBE Customer$Invoice;

////////////////////////////////////////

DROP TABLE IF EXISTS Ordered$Perfume;
CREATE TABLE IF NOT EXISTS Ordered$Perfume(
PerfumeCode VARCHAR(10),
QuantityForSale int,
Cost DOUBLE,
InvoiceNumber VARCHAR(10),
CONSTRAINT FOREIGN KEY (PerfumeCode) REFERENCES Perfume(PerfumeCode) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT FOREIGN KEY (InvoiceNumber) REFERENCES Customer$Invoice(InvoiceNumber) ON DELETE CASCADE ON UPDATE CASCADE);

SHOW TABLES ;
DESCRIBE Ordered$Perfume;

///////////////////////////////////////////
DROP TABLE IF EXISTS Payment;
CREATE TABLE IF NOT EXISTS Payment(
    InvoiceNumber VARCHAR(10) ,
    PaymentAmount DOUBLE,
    Cash DOUBLE NOT NULL DEFAULT 000.00,
    ChequeNumber int NULL,
    PaymentCollector VARCHAR(20),
    CONSTRAINT FOREIGN KEY (InvoiceNumber) REFERENCES Customer$Invoice(InvoiceNumber) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT FOREIGN KEY (ChequeNumber) REFERENCES Cheque(ChequeNumber) ON DELETE CASCADE ON UPDATE CASCADE);

SHOW TABLES ;
DESCRIBE Payment;

/////////////////////////////////////////////////
DROP TABLE IF EXISTS Cheque;
CREATE TABLE IF NOT EXISTS Cheque(
    ChequeNumber int,
    NameOfCheque VARCHAR(30),
    CollectionDate DATE,
    ChequeAmount DOUBLE,
    Bank VARCHAR(15),
    BankingDate DATE,
    InvoiceNumber VARCHAR(10),

CONSTRAINT PRIMARY KEY (ChequeNumber),
CONSTRAINT FOREIGN KEY (InvoiceNumber) REFERENCES Customer$Invoice(InvoiceNumber) ON DELETE CASCADE ON UPDATE CASCADE);

SHOW TABLES ;
DESCRIBE Cheque;

///////////////////////////////////////////////

DROP TABLE IF EXISTS Vehicle;
CREATE TABLE IF NOT EXISTS Vehicle(
    VehicleNumber VARCHAR(15),
    VehicleType VARCHAR(15),
    FuelType VARCHAR(15),
    Color VARCHAR (20),
    CONSTRAINT PRIMARY KEY (VehicleNumber));

SHOW TABLES ;
DESCRIBE Vehicle;

//////////////////////////////////////////////

    DROP TABLE IF EXISTS Delivery;
    CREATE TABLE IF NOT EXISTS Delivery(
    InvoiceNumber VARCHAR(10),
    DeliveryStaff VARCHAR(28),
    VehicleNumber VARCHAR(15),
    DeliveryDate DATE,
    BillAmount DOUBLE,
    CONSTRAINT FOREIGN KEY (InvoiceNumber) REFERENCES Customer$Invoice(InvoiceNumber) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT FOREIGN KEY (VehicleNumber) REFERENCES Vehicle(VehicleNumber) ON DELETE CASCADE ON UPDATE CASCADE

);

    SHOW TABLES ;
    DESCRIBE Delivery;

////////////////

DROP TABLE IF EXISTS `Customer Order`;
CREATE TABLE IF NOT EXISTS `Customer Order`(
    OrderDate DATE ,
    InvoiceNumber VARCHAR(10),
    CustomerName VARCHAR (35),
    Cost DOUBLE,

    CONSTRAINT FOREIGN KEY (InvoiceNumber) REFERENCES Customer$Invoice(InvoiceNumber) ON DELETE CASCADE ON UPDATE CASCADE);
DESCRIBE `Customer Order`;
//////////////////////////////////////

DROP TABLE IF EXISTS PendingDelivery;
CREATE TABLE IF NOT EXISTS PendingDelivery(
    OrderDate DATE ,
    InvoiceNumber VARCHAR(10),
    CustomerName VARCHAR (35),
    CustomerAddress VARCHAR (35),
    Cost DOUBLE,


    CONSTRAINT FOREIGN KEY (InvoiceNumber) REFERENCES Customer$Invoice(InvoiceNumber) ON DELETE CASCADE ON UPDATE CASCADE);

DESCRIBE PendingDelivery;
SELECT * FROM PendingDelivery;
/////////////
SHOW DATABASES ;
USE JDDistributors;

SHOW TABLES ;
DESCRIBE Employee$And$Role;
SELECT * FROM Employee$And$Role;

DESCRIBE System$User;
SELECT * FROM System$User;

DESCRIBE Salary;
SELECT * FROM Salary;

DESCRIBE Supplier;
SELECT * FROM Supplier;

DESCRIBE Perfume;
SELECT * FROM Perfume;

DESCRIBE Return$Perfume;
SELECT * FROM Return$Perfume;

DESCRIBE Customer$Invoice;
SELECT * FROM Customer$Invoice;

DESCRIBE Ordered$Perfume;
SELECT * FROM Ordered$Perfume;

DESCRIBE Payment;
SELECT * FROM Payment;

DESCRIBE Cheque;
SELECT * FROM Cheque;

DESCRIBE Vehicle;
SELECT * FROM Vehicle;

DESCRIBE Delivery;
SELECT * FROM Delivery;

DESCRIBE administrator;
SELECT * FROM administrator;

DESCRIBE `Customer Order`;
SELECT * FROM `Customer Order`;


SELECT * FROM `Customer Order`;
SELECT * FROM Ordered$Perfume;
SELECT * FROM Perfume;






