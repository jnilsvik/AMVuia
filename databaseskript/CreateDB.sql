CREATE TABLE Tools (
                       toolID int NOT NULL auto_increment,
                       toolName VARCHAR(50) NOT NULL,
                       maintenance boolean NOT NULL,
                       priceFirst int NOT NULL,
                       priceAfter int NOT NULL,
                       toolCategory VARCHAR(50) NOT NULL,
                       certificateID int NOT NULL,
                       PRIMARY KEY (toolID),
                       FOREIGN KEY (certificateID) REFERENCES ToolCertificate(certificateID),
                       CHECK(toolCategory in (
                                              'Various small equipment', 'Nailguns', 'Woodcutting equipment', 'Car trailers', 'Larger equipment'
                           ))
);

CREATE TABLE Users (
                       userID INT NOT NULL auto_increment,
                       email VARCHAR(50) NOT NULL,
                       passwordHash VARCHAR(250) NOT NULL,
                       firstName VARCHAR(50),
                       lastName VARCHAR(50) NOT NULL,
                       phoneNumber VARCHAR(16),
                       unionMember boolean NOT NULL,
                       userAdmin boolean NOT NULL,
                       PRIMARY KEY (userID)
);

CREATE TABLE Bookings (
                          orderID int NOT NULL auto_increment,
                          startDate date NOT NULL,
                          endDate date NOT NULL,
                          totalPrice int NOT NULL,
                          userID int NOT NULL,
                          toolID int NOT NULL,
                          PRIMARY KEY (orderID),
                          FOREIGN KEY (userID) REFERENCES Users(userID) ON DELETE SET NULL,
                          FOREIGN KEY (toolID) REFERENCES Tools(toolID) ON DELETE SET NULL
);

CREATE TABLE ToolCertificate (
                                 certificateID int NOT NULL auto_increment,
                                 certificateName VARCHAR(50) NOT NULL,
                                 PRIMARY KEY (certificateID)
);

CREATE TABLE UsersCertificate (
                                  userID int NOT NULL,
                                  certificateID int NOT NULL,
                                  accomplishDate date NOT NULL,
                                  Primary key (userID, certificateID, accomplishDate),
                                  FOREIGN KEY (userID) REFERENCES Users(userID),
                                  FOREIGN KEY (certificateID) REFERENCES ToolCertificate(certificateID) on delete cascade
);
