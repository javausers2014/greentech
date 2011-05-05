drop table TASK_EXE_DETAIL if exists;
drop table TASK_EXE_DETAIL if exists;
drop table TASK_EXE_DETAIL if exists;

create table TASK_EXE_DETAIL (ID integer identity primary key, USERNAME varchar(50) not null,FULLNAME varchar(50) not null, PERSONNEL_ID integer, PASSWORD varchar(100) not null,  unique(USERNAME));
	
create table PERMISSION (ID integer identity primary key, PERMISSION varchar(300) not null, PERMISSION_DESC varchar(50) not null, unique(PERMISSION));
create table USER_PERMISSIONS (ID integer identity primary key, PERMISSION_ID integer not null, USER_ID integer not null);

create table PERSONNEL (ID integer identity primary key, FirstName varchar(30) not null, LastName varchar(30) not null, MiddleName varchar(30), FullName varchar(300), PersonnelNo varchar(20) ,
	StaffNo varchar(50), Phone varchar(15), Fax varchar(15), Mobile varchar(15), Email varchar(30), BirthDate date, unique(STAFFNO));

create table CORE_UOM (ID integer identity primary key, NAME varchar(50) not null,CATEGORY varchar(50) not null,CODE varchar(50) not null, ACTIVE char(1), BASE char(1), FACTOR double not null, unique(CATEGORY,CODE));


alter table USER_PERMISSIONS add constraint FK_USER_PERMISSION_USERID foreign key (USER_ID) references USER(ID) on delete cascade;
alter table USER_PERMISSIONS add constraint FK_USER_PERMISSION_PERMID foreign key (PERMISSION_ID) references PERMISSION(ID) on delete cascade;