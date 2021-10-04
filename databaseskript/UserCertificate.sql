create database if not exists AMVDatabase;
use AMVDatabase;

create table if not exists UserCertificate
(
    userID integer not null,
    certificateID integer not null,
    accomplishDate DATE not null,
    CONSTRAINT UserCertificate_PK primary key (userID, certificateID, accomplishDate),
    CONSTRAINT UserCertificate_FK1 foreign key (userID) references User(userID),
    CONSTRAINT UserCertificate_FK2 foreign key (certificateID) references UserCertificate(certificateID)
)