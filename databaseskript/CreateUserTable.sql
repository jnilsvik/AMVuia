create database if not exists AMVDatabase;
use AMVDatabase;

create table if not exists User
(
    userID  integer unique auto_increment,
    firstname varchar(255) not null,
    lastname varchar(255),
    password varchar(32),
    phoneNumber varchar(16),
    unionMember boolean,
    email varchar(255),
    CONSTRAINT User_PK primary key (userID)
);