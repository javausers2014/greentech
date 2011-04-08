insert into USER (USERNAME, FULLNAME, PASSWORD) values ('admin', 'X Man','b644c3042fbed226b2c1a8250c4bc7b1178f80b1');
insert into USER (USERNAME, FULLNAME, PASSWORD) values ('demo', 'Y Man','b644c3042fbed226b2c1a8250c4bc7b1178f80b1');

insert into PERMISSION (PERMISSION, PERMISSION_DESC) values ('LOOK','For normal user');
insert into PERMISSION (PERMISSION, PERMISSION_DESC) values ('ADMIN_CTRL','For ADMIN user');
insert into PERMISSION (PERMISSION, PERMISSION_DESC) values ('OPER_CTRL','For Operation user');
insert into PERMISSION (PERMISSION, PERMISSION_DESC) values ('INPUT_USER','For Operation user');

insert into USER_PERMISSIONS (PERMISSION_ID, USER_ID) values (0,0);
insert into USER_PERMISSIONS (PERMISSION_ID, USER_ID) values (1,0);
insert into USER_PERMISSIONS (PERMISSION_ID, USER_ID) values (2,0);
insert into USER_PERMISSIONS (PERMISSION_ID, USER_ID) values (3,0);
insert into USER_PERMISSIONS (PERMISSION_ID, USER_ID) values (1,1);

insert into PERSONNEL (FirstName, LastName, MiddleName, FullName, PersonnelNo, StaffNo, Phone, Fax, Mobile, Email, BirthDate) values ('John', 'Wyne','' ,'John Wyne','ID-19890201987','OPT_002', '', '', '', 'jw@gmail.com', '1965-11-28');
insert into PERSONNEL (FirstName, LastName, MiddleName, FullName, PersonnelNo, StaffNo, Phone, Fax, Mobile, Email, BirthDate) values ('Kitty', 'Law', 'Aikeo','Kitty Law, Aikeo','ID-19342019216','SCM_013','123123', '5674577','565473345', 'sdo@hfo.com', '1934-01-08');
insert into PERSONNEL (FirstName, LastName, MiddleName, FullName, PersonnelNo, StaffNo, Phone, Fax, Mobile, Email, BirthDate) values ('睿哲', '李', '','李睿哲','ID-19632019216','CIO_002','123123', '5674577','565473345', 'lrzo@njho.com', '1963-02-13');
insert into PERSONNEL (FirstName, LastName, MiddleName, FullName, PersonnelNo, StaffNo, Phone, Fax, Mobile, Email, BirthDate) values ('润春', '许', '','','ID-1974032119216','PUC_211','123123', '5674577','565473345', 'so@ho.com', '1949-11-18');
insert into PERSONNEL (FirstName, LastName, MiddleName, FullName, PersonnelNo, StaffNo, Phone, Fax, Mobile, Email, BirthDate) values ('炯', '张', '','张炯','FND19342019216','THS_402','123123', '5674577','565473345', 'so@ho.com', '1974-04-28');
insert into PERSONNEL (FirstName, LastName, MiddleName, FullName, PersonnelNo, StaffNo, Phone, Fax, Mobile, Email, BirthDate) values ('テリー', '伊藤', '','テリー伊藤','JP-D19342019216','SAL_002','123123', '5674577','565473345', 'so@ho.com', '1964-01-12');
insert into PERSONNEL (FirstName, LastName, MiddleName, FullName, PersonnelNo, StaffNo, Phone, Fax, Mobile, Email, BirthDate) values ('สมชาย', 'ปรีชา', '','สมชาย ปรีชา','THA-19242019216','VIP_2','123123', '5674577','565473345', 'so@ho.com', '1959-09-17');


insert into CORE_UOM (NAME,CATEGORY,CODE,ACTIVE,BASE,FACTOR) values ('Meter','LENGTH','M', 'true', 'false', '1000.00');
insert into CORE_UOM (NAME,CATEGORY,CODE,ACTIVE,BASE,FACTOR) values ('Milimeter','LENGTH','MM', 'true', 'true', '1.00');
insert into CORE_UOM (NAME,CATEGORY,CODE,ACTIVE,BASE,FACTOR) values ('Centimeter','LENGTH','CM', 'true', 'false', '10.00');
insert into CORE_UOM (NAME,CATEGORY,CODE,ACTIVE,BASE,FACTOR) values ('KiloMeter','LENGTH','KM', 'true', 'false', '1000000.00');
