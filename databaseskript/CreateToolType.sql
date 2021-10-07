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
VALUES
       (       'Orbital Sander', 0, 20, 'Various Tools', null
       ),
       (       'Belt Sander', 0, 20, 'Various Tools', null
       ),
       (       'Hand Plane', 0, 20, 'Various Tools', null
       ),
       (       'Miter Saw', 0, 20, 'Various Tools', null
       ),
       (       'Grass Trimmer', 0, 20, 'Various Tools', null
       ),
       (       'Hammer Drill', 0, 20, 'Various Tools', null
       ),
       (       'String Trimmer', 0, 20, 'Various Tools', null
       ),
       (       'Air Compressor', 0, 20, 'Various Tools', null
       ),
       (       'Car Diagnosis ToolUNFINISH', 0, 50, 'Various Tools', null
       ),
       (       'Vibrating PlateUNFINISH', 0, 50, 'Various Tools', null
       ),
       (       'Tile Cutter Ceramic', 0, 20, 'Various Tools', null
       ),
       (       'Automatic Screwer', 0, 20, 'Various Tools', null
       ),
       (       'Motorized Wheelbarrow', 0, 50, 'Various Tools', null
       ),
       (       'Nailgun Pressurized Large', 0, 20, 'Nailguns', null
       ),
       (       'Nailgun Pressurized Small', 0, 20, 'Nailguns', null
       ),
       (       'Nailgun Milwaukee Large', 0, 20, 'Nailguns', null
       ),
       (       'Nailgun Milwaukee Medium', 0, 20, 'Nailguns', null
       ),
       (       'Nailgun Milwaukee Small', 0, 20, 'Nailguns', null
       ),
       (       'Wood Splitter', 0, 50, 'Woodcutting', null
       ),
       (       'Tile Cutter Wood', 0, 50, 'Woodcutting', null
       ),
       (       'Trailer Buggy', 0, 50, 'Car Trailers', null
       ),
       (       'Trailer Small', 0, 50, 'Car Trailers', null
       ),
       (       'Trailer Cargo', 0, 50, 'Car Trailers', null
       ),
       (       'Aerial Work Platform', 100, 100, 'Large Equipment', 1
       ),
       (       'Power Supply', 0, 50, 'Large Equipment', null
       );