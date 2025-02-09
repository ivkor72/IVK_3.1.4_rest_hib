CREATE TABLE  IF NOT EXISTS users (
  id int,
  username varchar(15),
  password varchar(100),
  enabled tinyint(1),
  PRIMARY KEY (id)
) ;

CREATE TABLE  IF NOT EXISTS authorities (
  username varchar(15),
  authority varchar(25),
  id int AUTO_INCREMENT,
  PRIMARY KEY (id)
) ;

insert ignore into users(id, username, password, enabled) values('1', 'admin','$2y$10$1Wt3KwXf5IrMvrGtFN2qKO3Pjv9tC.fDy2cBO2UFUw65kumKw2H6O', '1');
insert ignore into authorities(username, authority, id) values ('admin','ROLE_ADMIN', '1');
insert ignore into users(id, username, password, enabled) values('2','user','$2a$10$b1BExzfpFyUCAamWfiBnpe7KnPwyl2NqHcn6tmtHq/Y8zMSqBtA5K', '1');
insert ignore into authorities(username, authority, id) values('user','ROLE_USER', '2');
