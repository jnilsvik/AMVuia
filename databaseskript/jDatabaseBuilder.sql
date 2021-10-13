create database if not exists AMVDatabase;
use AMVDatabase;

-- by Joachim Nilsvik

create or replace table Tools
(   -- tool details
	toolID integer
	    unique
	    auto_increment
	    not null
	    primary key,
    toolCategory varchar(255),
    toolName varchar(255),
    certificateID integer, -- not sure if this should be here
    -- tool pricing
    priceInitial double,
    priceAfter double,
    -- tool status
    underMaintenance bool
        default '0'
);

create table if not exists Users
(   -- login details
	userID integer
	    unique
	    auto_increment
	    not null
	    primary key,
    userPassword varchar(255)
        not null,
    -- contact details
    email varchar(255)
        not null,
    phoneNumber char(10),
    -- personal details
    firstname varchar(255)
        not null,
    lastname varchar(255)
        not null,
	unionMember boolean
	    not null
	    default '0',
	admin boolean
        default '0'
);  

create table if not exists ToolsCertificate
(
	certificateID varchar(255)
	    primary key,
    toolID integer,
	certificateName varchar(255),
	foreign key(toolID) references Tools(toolID) on delete set null
);

create table if not exists UserCertificate
(
	userID integer,
    certificateID varchar(255),
    accomplishedDate date,
    foreign key(userID) references Users(userID) on delete set null
);

create table if not exists BookingSummary
(   -- order details
	orderID integer
	    auto_increment
	    not null
	    primary key,
    startDate date,
    enDate date,
    price double,
    toolID integer,
    foreign key(toolID) references Tools(toolID) on delete set null,
    userID integer,
    foreign key(userID) references Users(userID) on delete set null
);

-- prints all tables
-- Tools
describe Tools;
-- Users
describe Users;
-- t.cert
describe ToolsCertificate;
-- u.cert
describe UserCertificate;
-- booking
describe BookingSummary;



-- now that the database is initialised, this will start loading in the data
-- First up is the tool data. (totally not just a reformatted version of what marius had done)
INSERT INTO Tools(
    toolName, priceInitial, priceAfter, toolCategory, certificateID, underMaintenance
)
VALUES
    (       'Orbital Sander', 0, 20, 'Various Tools', null, 0),
    (       'Belt Sander', 0, 20, 'Various Tools', null, 0),
    (       'Hand Plane', 0, 20, 'Various Tools', null, 0),
    (       'Miter Saw', 0, 20, 'Various Tools', null, 0),
    (       'Grass Trimmer', 0, 20, 'Various Tools', null, 0),
    (       'Hammer Drill', 0, 20, 'Various Tools', null, 0),
    (       'String Trimmer', 0, 20, 'Various Tools', null, 0),
    (       'Air Compressor', 0, 20, 'Various Tools', null, 0),
    (       'Car Diagnosis ToolUNFINISH', 0, 50, 'Various Tools', null, 0),
    (       'Vibrating PlateUNFINISH', 0, 50, 'Various Tools', null, 0),
    (       'Tile Cutter Ceramic', 0, 20, 'Various Tools', null, 0),
    (       'Automatic Screwer', 0, 20, 'Various Tools', null, 0),
    (       'Motorized Wheelbarrow', 0, 50, 'Various Tools', null, 0),
    (       'Nailgun Pressurized Large', 0, 20, 'Nailguns', null, 0),
    (       'Nailgun Pressurized Small', 0, 20, 'Nailguns', null, 0),
    (       'Nailgun Milwaukee Large', 0, 20, 'Nailguns', null, 0),
    (       'Nailgun Milwaukee Medium', 0, 20, 'Nailguns', null, 0),
    (       'Nailgun Milwaukee Small', 0, 20, 'Nailguns', null, 0),
    (       'Wood Splitter', 0, 50, 'Woodcutting', null, 0),
    (       'Tile Cutter Wood', 0, 50, 'Woodcutting', null, 0),
    (       'Trailer Buggy', 0, 50, 'Car Trailers', null, 0),
    (       'Trailer Small', 0, 50, 'Car Trailers', null, 0),
    (       'Trailer Cargo', 0, 50, 'Car Trailers', null, 0),
    (       'Aerial Work Platform', 100, 100, 'Large Equipment', 1, 0),
    (       'Power Supply', 0, 50, 'Large Equipment', null, 0);

-- should probably add something to prevent other things than numbers being inserted for phone nr...
INSERT into Users(userpassword, email, phonenumber, firstname, lastname, unionmember)
-- passwords needs to be hashed or smth, but idk how
values ('Hei', 'joachimn@uia.no', null, 'Joachim', 'Nilsvik','0'),
       ('Hei', 'dilans@uia.no', null, 'Dilan', 'Shwane','0'),
       ('Hei', 'mariusbn@uia.no', null, 'Marius Berg', 'Nordbø', '0'),
       ('hei', 'roelandc@uia.no', null, 'Roeland', 'Camps', '0'),
       ('hei', 'paulfe@uia.no', null, 'Paul', 'Feichtenschlager', '0'),
       ('hei', 'johannao@uia.no', null, 'Johanna', 'Ockenfels', '0'),
       ('hei', 'idkwhatbriansmailsis@uia.no', null, 'Brian "Cheuk Long"', 'Chan','0');