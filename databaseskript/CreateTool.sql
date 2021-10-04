create database if not exists AMVDatabase;
use AMVDatabase;

create table if not exists Tool
(
  toolID integer unique auto_increment,
  toolTypeID integer not null ,
  location varchar(255) not null,
  status varchar(255) not null,
  CONSTRAINT Tool_PK primary key (toolID),
  CONSTRAINT Tool_FK foreign key (toolTypeID) references ToolType(toolTypeID)
);