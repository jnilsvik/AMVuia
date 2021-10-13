create database if not exists AMVDatabase;
use AMVDatabase;

CREATE OR REPLACE TABLE ToolCertificate(
    certificateID int NOT NULL auto_increment,
    certificateName VARCHAR(50) NOT NULL,
    PRIMARY KEY (certificateID)
);

CREATE OR REPLACE TABLE Tool (
    toolID int NOT NULL auto_increment,
    toolName VARCHAR(50) NOT NULL,
    maintenance boolean NOT NULL,
    priceFirst int NOT NULL,
    priceAfter int NOT NULL,
    toolCategory VARCHAR(50) NOT NULL,
    certificateID int NOT NULL,
    PRIMARY KEY (toolID),
    FOREIGN KEY (certificateID) REFERENCES ToolCertificate(certificateID),
    CHECK(toolCategory in
          ('Various Tools', 'Nailguns', 'Woodcutting', 'Car Trailers', 'Large Equipment'))
);

CREATE OR REPLACE TABLE AMVUser (
    userID INT NOT NULL auto_increment,
    email VARCHAR(50) NOT NULL,
    passwordHash VARCHAR(250) NOT NULL,
    firstName VARCHAR(50),
    lastName VARCHAR(50) NOT NULL,
    phoneNumber VARCHAR(16),
    unionMember boolean NOT NULL,
    userAdmin boolean NOT NULL,
    PRIMARY KEY (userID)
);

CREATE OR REPLACE TABLE Booking (
    orderID int NOT NULL auto_increment,
    startDate date NOT NULL,
    endDate date NOT NULL,
    totalPrice int NOT NULL,
    userID int NOT NULL,
    toolID int NOT NULL,
    PRIMARY KEY (orderID),
    FOREIGN KEY (userID) REFERENCES AMVUser(userID),
    FOREIGN KEY (toolID) REFERENCES Tool(toolID)
);


CREATE OR REPLACE TABLE UsersCertificate (
    userID int NOT NULL,
    certificateID int NOT NULL,
    accomplishDate date NOT NULL,
    Primary key (userID, certificateID, accomplishDate),
    FOREIGN KEY (userID) REFERENCES AMVUser(userID) on delete cascade ,
    FOREIGN KEY (certificateID) REFERENCES ToolCertificate(certificateID) on delete cascade
);
-- now that the database is initialised, this will start loading in the data
-- First up is the tool data. (totally not just a reformatted version of what marius had done)
INSERT INTO Tool( toolName, pricefirst, priceAfter, toolCategory, certificateID, maintenance)

VALUES
    ('Orbital Sander', 0, 20, 'Various Tools', 1, 0),
    ('Belt Sander', 0, 20, 'Various Tools', 1, 0),
    ('Hand Plane', 0, 20, 'Various Tools', 1, 0),
    ('Miter Saw', 0, 20, 'Various Tools', 1, 0),
    ('Grass Trimmer', 0, 20, 'Various Tools', 1, 0),
    ('Hammer Drill', 0, 20, 'Various Tools', 1, 0),
    ('String Trimmer', 0, 20, 'Various Tools', 1, 0),
    ('Air Compressor', 0, 20, 'Various Tools', 1, 0),
    ('Car Diagnosis ToolUNFINISH', 0, 50, 'Various Tools', 1, 0),
    ('Vibrating PlateUNFINISH', 0, 50, 'Various Tools', 1, 0),
    ('Tile Cutter Ceramic', 0, 20, 'Various Tools', 1, 0),
    ('Automatic Screwer', 0, 20, 'Various Tools', 1, 0),
    ('Motorized Wheelbarrow', 0, 50, 'Various Tools', 1, 0),
    ('Nailgun Pressurized Large', 0, 20, 'Nailguns', 1, 0),
    ('Nailgun Pressurized Small', 0, 20, 'Nailguns', 1, 0),
    ('Nailgun Milwaukee Large', 0, 20, 'Nailguns', 1, 0),
    ('Nailgun Milwaukee Medium', 0, 20, 'Nailguns', 1, 0),
    ('Nailgun Milwaukee Small', 0, 20, 'Nailguns', 1, 0),
    ('Wood Splitter', 0, 50, 'Woodcutting', 1, 0),
    ('Tile Cutter Wood', 0, 50, 'Woodcutting', 1, 0),
    ('Trailer Buggy', 0, 50, 'Car Trailers', 1, 0),
    ('Trailer Small', 0, 50, 'Car Trailers', 1, 0),
    ('Trailer Cargo', 0, 50, 'Car Trailers', 1, 0),
    ('Aerial Work Platform', 100, 100, 'Large Equipment', 1, 0),
    ('Power Supply', 0, 50, 'Large Equipment', 1, 0);

-- should probably add something to prevent other things than numbers being inserted for phone nr...
INSERT into AMVUser(passwordHash, email, phonenumber, firstname, lastname, unionmember, userAdmin)
-- passwords needs to be hashed or smth, but idk how
values ('Hei', 'joachimn@uia.no', null, 'Joachim', 'Nilsvik','0', '1'),
       ('Hei', 'dilans@uia.no', null, 'Dilan', 'Shwane','1', '1'),
       ('Hei', 'mariusbn@uia.no', null, 'Marius Berg', 'Nordbø', '0', '1'),
       ('hei', 'roelandc@uia.no', null, 'Roeland', 'Camps', '1', '1'),
       ('hei', 'paulfe@uia.no', null, 'Paul', 'Feichtenschlager', '0', '1'),
       ('hei', 'johannao@uia.no', null, 'Johanna', 'Ockenfels', '1', '1'),
       ('hei', 'idkwhatbriansmailsis@uia.no', null, 'Brian "Cheuk Long"', 'Chan','0', '1');