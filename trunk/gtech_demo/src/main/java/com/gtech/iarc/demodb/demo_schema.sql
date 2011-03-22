drop table USER if exists;
drop table PERMISSION if exists;
drop table USER_PERMISSIONS if exists;

create table USER (ID integer identity primary key, USERNAME varchar(50) not null,FULLNAME varchar(50) not null, PASSWORD varchar(100) not null, unique(USERNAME));
	
create table PERMISSION (ID integer identity primary key, PERMISSION varchar(300) not null, PERMISSION_DESC varchar(50) not null, unique(PERMISSION));
create table USER_PERMISSIONS (ID integer identity primary key, PERMISSION_ID integer not null, USER_ID integer not null);

alter table USER_PERMISSIONS add constraint FK_USER_PERMISSION_USERID foreign key (USER_ID) references USER(ID) on delete cascade;
alter table USER_PERMISSIONS add constraint FK_USER_PERMISSION_PERMID foreign key (PERMISSION_ID) references PERMISSION(ID) on delete cascade;