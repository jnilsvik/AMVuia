create database if not exists AMVDatabase;
use AMVDatabase;

 create table if not exists ToolCertificate
(
    certificateID integer unique auto_increment,
    certificateName varchar(255) not null,
    CONSTRAINT ToolCertificate_PK primary key (certificateID)
 );