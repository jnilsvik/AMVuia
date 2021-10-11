create database if not exists AMVDatabase;
use AMVDatabase;

-- by Joachim Nilsvik

create table if not exists Tools
(   -- tool details
	toolID integer
	    unique
	    auto_increment
	    not null
	    primary key,
    toolCategory varchar(255),
    toolName varchar(255),
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
    