create table files(
                      id int not null auto_increment unique,
                      name varchar(255),
                      content blob,
                      contentType varchar(255),
                      PRIMARY KEY (id)
);