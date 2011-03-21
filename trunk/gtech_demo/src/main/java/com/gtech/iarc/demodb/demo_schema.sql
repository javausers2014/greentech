drop table USER if exists;

create table USER (ID integer identity primary key, USERNAME varchar(50) not null, FULLNAME varchar(50) not null, PASSWORD varchar(100) not null, unique(USERNAME));
