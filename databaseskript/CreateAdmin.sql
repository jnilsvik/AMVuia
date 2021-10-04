create database if not exists AMVDatabase;
use AMVDatabase;

create table if not exists Admin
(
    userID integer,
    CONSTRAINT Admin_PK primary key (userID),
    CONSTRAINT Admin_FK foreign key (userID) references User(userID)
);