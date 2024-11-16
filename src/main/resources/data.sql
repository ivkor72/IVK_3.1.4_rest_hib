CREATE TABLE  IF NOT EXISTS users (
  username varchar(15),
  password varchar(100),
  enabled tinyint(1),
  PRIMARY KEY (username)
) ;

CREATE TABLE  IF NOT EXISTS authorities (
  username varchar(15),
  authority varchar(25),
  FOREIGN KEY (username) references users(username),
  UNIQUE (username)
) ;

insert ignore into users(username, password, enabled) values('admin','$2y$10$1Wt3KwXf5IrMvrGtFN2qKO3Pjv9tC.fDy2cBO2UFUw65kumKw2H6O', '1');
insert ignore into authorities(username, authority) values ('admin','ROLE_ADMIN');
insert ignore into users(username, password, enabled) values('user','$2y$10$1Wt3KwXf5IrMvrGtFN2qKO3Pjv9tC.fDy2cBO2UFUw65kumKw2H6O', '1');
insert ignore into authorities(username, authority) values('user','ROLE_USER');
