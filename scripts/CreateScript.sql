-- Created by Vertabelo (http://vertabelo.com)
-- Last modification date: 2020-12-29 11:53:14.962

-- tables
-- Table: Booking
CREATE TABLE Booking (
    Id int  NOT NULL IDENTITY,
    DoctorToTestId int  NOT NULL,
    PacientId int  NOT NULL,
    StartsAt datetime  NOT NULL,
    Paid bit  NOT NULL,
    IsPositive bit  NOT NULL,
    CONSTRAINT Booking_pk PRIMARY KEY  (Id)
);

-- Table: Doctor
CREATE TABLE Doctor (
    Id int  NOT NULL IDENTITY,
    Name nvarchar(30)  NOT NULL,
    Surname nvarchar(30)  NOT NULL,
    Title nvarchar(3)  NOT NULL,
    LocationId int  NOT NULL,
    CONSTRAINT Doctor_pk PRIMARY KEY  (Id)
);

-- Table: DoctorToTest
CREATE TABLE DoctorToTest (
    Id int  NOT NULL IDENTITY,
    TestId int  NOT NULL,
    DoctorId int  NOT NULL,
    CONSTRAINT DoctorToTest_pk PRIMARY KEY  (Id)
);

-- Table: Location
CREATE TABLE Location (
    Id int  NOT NULL IDENTITY,
    Name nvarchar(30)  NOT NULL,
    City nvarchar(30)  NOT NULL,
    Street nvarchar(max)  NOT NULL,
    OpenedFrom time(0)  NOT NULL,
    OpenedTo time(0)  NOT NULL,
    CONSTRAINT Location_pk PRIMARY KEY  (Id)
);

-- Table: Pacient
CREATE TABLE Pacient (
    Id int  NOT NULL IDENTITY,
    Name nvarchar(30)  NOT NULL,
    Surname nvarchar(30)  NOT NULL,
    PersonalIdentifier nvarchar(11)  NOT NULL,
    CONSTRAINT PersonalIdentifier UNIQUE (PersonalIdentifier),
    CONSTRAINT Pacient_pk PRIMARY KEY  (Id)
);

-- Table: Test
CREATE TABLE Test (
    Id int  NOT NULL IDENTITY,
    Name nvarchar(50)  NOT NULL,
    Description nvarchar(max)  NULL,
    Price money  NOT NULL,
    Length int  NOT NULL,
    CONSTRAINT Test_pk PRIMARY KEY  (Id)
);

-- foreign keys
-- Reference: Booking_DoctorToTest (table: Booking)
ALTER TABLE Booking ADD CONSTRAINT Booking_DoctorToTest
    FOREIGN KEY (DoctorToTestId)
    REFERENCES DoctorToTest (Id)
    ON DELETE  CASCADE;

-- Reference: Booking_Pacient (table: Booking)
ALTER TABLE Booking ADD CONSTRAINT Booking_Pacient
    FOREIGN KEY (PacientId)
    REFERENCES Pacient (Id)
    ON DELETE  CASCADE;

-- Reference: DoctorToTest_Doctor (table: DoctorToTest)
ALTER TABLE DoctorToTest ADD CONSTRAINT DoctorToTest_Doctor
    FOREIGN KEY (DoctorId)
    REFERENCES Doctor (Id)
    ON DELETE  CASCADE;

-- Reference: DoctorToTest_Test (table: DoctorToTest)
ALTER TABLE DoctorToTest ADD CONSTRAINT DoctorToTest_Test
    FOREIGN KEY (TestId)
    REFERENCES Test (Id);

-- Reference: Doctor_Location (table: Doctor)
ALTER TABLE Doctor ADD CONSTRAINT Doctor_Location
    FOREIGN KEY (LocationId)
    REFERENCES Location (Id);

-- End of file.

