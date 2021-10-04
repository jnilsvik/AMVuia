create database if not exists AMVDatabase;
use AMVDatabase;

create table if not exists CreateBooking
(
    bookingID integer unique auto_increment,
    userID integer not null,
    toolID integer not null ,
    startDate Date not null ,
    endDate Date not null,
    totalPrice double,
    CONSTRAINT CreateBooking_PK primary key (bookingID),
    CONSTRAINT CreateBooking_FK1 foreign key (userID) references User(userID),
    CONSTRAINT CreateBooking_FK2 foreign key (toolID) references Tool(toolID)
);