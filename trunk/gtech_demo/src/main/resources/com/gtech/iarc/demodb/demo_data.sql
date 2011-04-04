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

insert into PERSONNEL (FirstName, LastName, MiddleName , PersonnelNo ,Phone, Fax, Mobile, Email, BirthDate) values ('John', 'Wyne','' , 'YUO19890201987', '', '', '', '', '1965-11-28');
insert into PERSONNEL (FirstName, LastName, MiddleName , PersonnelNo ,Phone, Fax, Mobile, Email, BirthDate) values ('Kitty', 'Law', 'Aikeo','FND19342019216','123123', '5674577','565473345', 'so@ho.com', '1934-01-08');

insert into CORE_UOM (NAME,CATEGORY,CODE,ACTIVE,BASE,FACTOR) values ('Meter','LENGTH','M', 'y', 'n', '1000.00');
insert into CORE_UOM (NAME,CATEGORY,CODE,ACTIVE,BASE,FACTOR) values ('Milimeter','LENGTH','MM', 'y', 'y', '1.00');
insert into CORE_UOM (NAME,CATEGORY,CODE,ACTIVE,BASE,FACTOR) values ('Centimeter','LENGTH','CM', 'y', 'n', '10.00');
insert into CORE_UOM (NAME,CATEGORY,CODE,ACTIVE,BASE,FACTOR) values ('KiloMeter','LENGTH','KM', 'y', 'n', '1000000.00');
