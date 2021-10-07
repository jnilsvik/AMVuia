create database if not exists AMVDatabase;
use AMVDatabase;

create table if not exists ToolType
(
    toolTypeID integer unique auto_increment,
    toolTypeName varchar(255) not null,
    priceFirstDay double,
    priceAfterFirstDay double,
    category varchar(255),
    certificateID integer,
    CONSTRAINT ToolType_PK primary key (toolTypeID),
    CONSTRAINT ToolType_FK foreign key (certificateID) references ToolCertificate(certificateID)
);

INSERT INTO ToolType(
        toolTypeName, priceFirstDay, priceAfterFirstDay, category, certificateID
        )
VALUES(
       'Hammer',0.0, 5, 'small tools',null
      );