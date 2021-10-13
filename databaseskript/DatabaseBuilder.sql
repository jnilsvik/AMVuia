drop schema AMVDatabase;
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
INSERT INTO ToolCertificate(certificateName)
VALUES ('none'),
       ('Aerial Work Platform');

INSERT INTO Tool(toolName, pricefirst, priceAfter, toolCategory, certificateID, maintenance)
VALUES ('Orbital Sander', 0, 20, 'Various Tools', '1', '0'),
       ('Belt Sander', 0, 20, 'Various Tools', '1', '0'),
       ('Hand Plane', 0, 20, 'Various Tools', '1', '0'),
       ('Miter Saw', 0, 20, 'Various Tools', '1', '0'),
       ('Grass Trimmer', 0, 20, 'Various Tools', '1', '0'),
       ('Hammer Drill', 0, 20, 'Various Tools', '1', '0'),
       ('String Trimmer', 0, 20, 'Various Tools', '1', '0'),
       ('Air Compressor', 0, 20, 'Various Tools', '1', '0'),
       ('Car Diagnosis ToolUNFINISH', 0, 50, 'Various Tools', '1', '0'),
       ('Vibrating PlateUNFINISH', 0, 50, 'Various Tools', '1', '0'),
       ('Tile Cutter Ceramic', 0, 20, 'Various Tools', '1', '0'),
       ('Automatic Screwer', 0, 20, 'Various Tools', '1', '0'),
       ('Motorized Wheelbarrow', 0, 50, 'Various Tools', '1', '0'),
       ('Nailgun Pressurized Large', 0, 20, 'Nailguns', '1', '0'),
       ('Nailgun Pressurized Small', 0, 20, 'Nailguns', '1', '0'),
       ('Nailgun Milwaukee Large', 0, 20, 'Nailguns', '1', '0'),
       ('Nailgun Milwaukee Medium', 0, 20, 'Nailguns', '1', '0'),
       ('Nailgun Milwaukee Small', 0, 20, 'Nailguns', '1', '0'),
       ('Wood Splitter', 0, 50, 'Woodcutting', '1', '0'),
       ('Tile Cutter Wood', 0, 50, 'Woodcutting', '1', '0'),
       ('Trailer Buggy', 0, 50, 'Car Trailers', '1', '0'),
       ('Trailer Small', 0, 50, 'Car Trailers', '1', '0'),
       ('Trailer Cargo', 0, 50, 'Car Trailers', '1', '0'),
       ('Aerial Work Platform', 100, 100, 'Large Equipment', '2', '0'),
       ('Power Supply', 0, 50, 'Large Equipment', '1', '0');

-- should probably add something to prevent other things than numbers being inserted for phone nr...
-- passwords needs to be hashed or smth, but idk how
INSERT INTO AMVUser(passwordHash, email, phonenumber, firstname, lastname, unionmember, userAdmin)
VALUES ('Hei', 'joachimn@uia.no', null, 'Joachim', 'Nilsvik','0', '1'),
       ('Hei', 'dilans@uia.no', null, 'Dilan', 'Shwane','1', '1'),
       ('Hei', 'mariusbn@uia.no', null, 'Marius Berg', 'Nordbø', '0', '1'),
       ('hei', 'roelandc@uia.no', null, 'Roeland', 'Camps', '1', '1'),
       ('hei', 'paulfe@uia.no', null, 'Paul', 'Feichtenschlager', '0', '1'),
       ('hei', 'johannao@uia.no', null, 'Johanna', 'Ockenfels', '1', '1'),
       ('hei', 'idkwhatbriansmailsis@uia.no', null, 'Brian "Cheuk Long"', 'Chan','0', '1');

INSERT INTO UsersCertificate(userID, certificateID, accomplishDate)
VALUES ('1','1','2021-10-10'),
       ('2','2','2021-10-11'),
       ('3','1','2021-10-12'),
       ('4','2','2021-10-13'),
       ('5','1','2021-10-14'),
       ('6','2','2021-10-15'),
       ('7','1','2021-10-16');


INSERT INTO Booking (startDate, endDate, totalPrice, userID, toolID)
VALUES
    ('2021-01-20', '2021-01-24', 80, 1, 2),
    ('2021-01-25', '2021-01-26', 20, 4, 5),
    ('2020-05-10', '2020-05-15', 250, 3, 10),
    ('2021-09-05', '2021-09-07', 40, 6, 4),
    ('2021-08-11', '2021-08-13', 100, 1, 13),
    ('2021-10-09', '2021-10-14', 100, 5, 18),
    ('2020-07-14', '2020-07-18', 200, 7, 20),
    ('2020-12-01', '2020-12-01', 0, 2, 9),
    ('2021-11-22', '2021-11-25', 300, 5, 24),
    ('2021-03-17', '2021-03-21', 80, 3, 6),
    ('2021-10-10', '2021-10-21', 80, 3, 6);

-- List all equipment in the system with their type
-- works
Select toolName, toolCategory
from Tool;

-- List all the available (at the moment – not already borrowed) equipment
-- works
select toolName, b.toolID
from Tool as t, Booking as b
where t.toolID = b.toolID and b.toolID not in
(select t.toolID from Tool t, Booking b
where t.toolID=b.toolID
  and b.startDate<=current_date()
  and b.endDate>=current_date());

-- List the names and number of borrows of the three users with most equipment borrowed, sorted by number of borrows
-- works
select firstName, lastName, count(*) as 'number of borrows'
from AMVUser as u, Booking as b
where u.userID = b.userID
group by b.userID
order by `number of borrows` desc
limit 3;

-- List all the equipment borrowed by the user with the highest number of equipment borrowed, sorted by date/time
-- works
select toolName, startDate
from Tool as t, Booking as b
where t.toolID = b.toolID and b.userID = (select userID from Booking as b group by b.userID order by count(*) desc limit 1)
Order by startDate DESC;

-- List all equipment that is borrowed at the moment
-- works
select t.toolID, t.toolName
from Tool t, Booking b
where t.toolID=b.toolID
  and b.startDate<=current_date()
  and b.endDate>=current_date();


