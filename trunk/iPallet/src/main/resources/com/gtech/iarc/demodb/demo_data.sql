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
insert into PERSONNEL (FirstName, LastName, MiddleName, FullName, PersonnelNo, StaffNo, Phone, Fax, Mobile, Email, BirthDate) values ('润春', '许', '','许润春','ID-1974032119216','PUC_211','123123', '5674577','565473345', 'so@ho.com', '1949-11-18');
insert into PERSONNEL (FirstName, LastName, MiddleName, FullName, PersonnelNo, StaffNo, Phone, Fax, Mobile, Email, BirthDate) values ('炯', '张', '','张炯','FND19342019216','THS_402','123123', '5674577','565473345', 'so@ho.com', '1974-04-28');
insert into PERSONNEL (FirstName, LastName, MiddleName, FullName, PersonnelNo, StaffNo, Phone, Fax, Mobile, Email, BirthDate) values ('テリー', '伊藤', '','テリー伊藤','JP-D19342019216','SAL_002','123123', '5674577','565473345', 'so@ho.com', '1964-01-12');
insert into PERSONNEL (FirstName, LastName, MiddleName, FullName, PersonnelNo, StaffNo, Phone, Fax, Mobile, Email, BirthDate) values ('สมชาย', 'ปรีชา', '','สมชาย ปรีชา','THA-19242019216','VIP_2','123123', '5674577','565473345', 'so@ho.com', '1959-09-17');
insert into PERSONNEL (FirstName, LastName, MiddleName, FullName, PersonnelNo, StaffNo, Phone, Fax, Mobile, Email, BirthDate) values ('Jack', 'Teo','' ,'Jack Teo','ID-19890207687','OPT_032', '', '', '', 'jw@gmail.com', '1965-11-28');
insert into PERSONNEL (FirstName, LastName, MiddleName, FullName, PersonnelNo, StaffNo, Phone, Fax, Mobile, Email, BirthDate) values ('Kanny', 'Chau', 'Alen','Kanny Chau, Alen','ID-45642019216','SCM_413','123123', '5674577','565473345', 'sdo@hfo.com', '1934-01-08');
insert into PERSONNEL (FirstName, LastName, MiddleName, FullName, PersonnelNo, StaffNo, Phone, Fax, Mobile, Email, BirthDate) values ('睿', '钟', '','钟睿','ID-19631119216','PUC_102','123123', '5674577','565473345', 'lrzo@njho.com', '1963-02-13');
insert into PERSONNEL (FirstName, LastName, MiddleName, FullName, PersonnelNo, StaffNo, Phone, Fax, Mobile, Email, BirthDate) values ('润', '许', '','许润','ID-1912332119216','PUC_461','123123', '5674577','565473345', 'so@ho.com', '1949-11-18');
insert into PERSONNEL (FirstName, LastName, MiddleName, FullName, PersonnelNo, StaffNo, Phone, Fax, Mobile, Email, BirthDate) values ('钱', '张', '','张强','FND19322019216','THS_452','123123', '5674577','565473345', 'so@ho.com', '1974-04-28');
insert into PERSONNEL (FirstName, LastName, MiddleName, FullName, PersonnelNo, StaffNo, Phone, Fax, Mobile, Email, BirthDate) values ('梁', '伊藤', '','梁伊藤','JP-D12342019216','SAL_0T2','123123', '5674577','565473345', 'so@ho.com', '1964-01-12');
insert into PERSONNEL (FirstName, LastName, MiddleName, FullName, PersonnelNo, StaffNo, Phone, Fax, Mobile, Email, BirthDate) values ('Tonohi', 'James','' ,'James Tonohi','ID-1234902434','OPT_402', '', '', '', 'jw@gmail.com', '1965-11-28');
insert into PERSONNEL (FirstName, LastName, MiddleName, FullName, PersonnelNo, StaffNo, Phone, Fax, Mobile, Email, BirthDate) values ('Holliy', 'Tomitah', 'G','Holliy Tomitah G','ID-4534342019216','SCM_453','123123', '5674577','565473345', 'sdo@hfo.com', '1934-01-08');
insert into PERSONNEL (FirstName, LastName, MiddleName, FullName, PersonnelNo, StaffNo, Phone, Fax, Mobile, Email, BirthDate) values ('哲', '徐', '','徐哲','ID-19633456216','CIO_232','123123', '5674577','565473345', 'lrzo@njho.com', '1963-02-13');
insert into PERSONNEL (FirstName, LastName, MiddleName, FullName, PersonnelNo, StaffNo, Phone, Fax, Mobile, Email, BirthDate) values ('传升', '许', '','许传升','ID-19734119216','PUC_341','123123', '5674577','565473345', 'so@ho.com', '1949-11-18');
insert into PERSONNEL (FirstName, LastName, MiddleName, FullName, PersonnelNo, StaffNo, Phone, Fax, Mobile, Email, BirthDate) values ('亮', '张', '','张亮','FND1934439216','THS_4H2','123123', '5674577','565473345', 'so@ho.com', '1974-04-28');
insert into PERSONNEL (FirstName, LastName, MiddleName, FullName, PersonnelNo, StaffNo, Phone, Fax, Mobile, Email, BirthDate) values ('テリー', '伊藤', '','テリー伊藤','JP-D178342019216','SAL_D02','123123', '5674577','565473345', 'so@ho.com', '1964-01-12');
insert into PERSONNEL (FirstName, LastName, MiddleName, FullName, PersonnelNo, StaffNo, Phone, Fax, Mobile, Email, BirthDate) values ('สมชาย', 'ปรีชา', '','สมชาย ปรีชา','THA-19242034516','VIP_23','123123', '5674577','565473345', 'so@ho.com', '1959-09-17');
insert into PERSONNEL (FirstName, LastName, MiddleName, FullName, PersonnelNo, StaffNo, Phone, Fax, Mobile, Email, BirthDate) values ('James', 'Bond','' ,'James Bond','ID-123490201987','OPT_862', '', '', '', 'jw@gmail.com', '1965-11-28');
insert into PERSONNEL (FirstName, LastName, MiddleName, FullName, PersonnelNo, StaffNo, Phone, Fax, Mobile, Email, BirthDate) values ('Nicholas', 'Cage', '','Nicholas Cage','ID-53342019216','SCM_033','123123', '5674577','565473345', 'sdo@hfo.com', '1934-01-08');
insert into PERSONNEL (FirstName, LastName, MiddleName, FullName, PersonnelNo, StaffNo, Phone, Fax, Mobile, Email, BirthDate) values ('John', 'Trifolta', '','John Trifolta','ID-19632349216','CIO_0T4','123123', '5674577','565473345', 'lrzo@njho.com', '1963-02-13');
insert into PERSONNEL (FirstName, LastName, MiddleName, FullName, PersonnelNo, StaffNo, Phone, Fax, Mobile, Email, BirthDate) values ('春强', '赵', '','赵春强','ID-1524032119216','PUC_321','123123', '5674577','565473345', 'so@ho.com', '1949-11-18');
insert into PERSONNEL (FirstName, LastName, MiddleName, FullName, PersonnelNo, StaffNo, Phone, Fax, Mobile, Email, BirthDate) values ('炯', '董', '','董炯','FND1435519216','THS_453','123123', '5674577','565473345', 'so@ho.com', '1974-04-28');
insert into PERSONNEL (FirstName, LastName, MiddleName, FullName, PersonnelNo, StaffNo, Phone, Fax, Mobile, Email, BirthDate) values ('テリ', '伊藤', '','テリ伊藤','JP-D16652019216','SAL_082','123123', '5674577','565473345', 'so@ho.com', '1964-01-12');
insert into PERSONNEL (FirstName, LastName, MiddleName, FullName, PersonnelNo, StaffNo, Phone, Fax, Mobile, Email, BirthDate) values ('สมชาย', 'ปรีชา', '','สมชาย ปรีชา','THA-634242019216','VIP_9','123123', '5674577','565473345', 'so@ho.com', '1959-09-17');


insert into CORE_UOM (NAME,CATEGORY,CODE,ACTIVE,BASE,FACTOR) values ('Meter','LENGTH','M', 'true', 'false', '1000.00');
insert into CORE_UOM (NAME,CATEGORY,CODE,ACTIVE,BASE,FACTOR) values ('Milimeter','LENGTH','MM', 'true', 'true', '1.00');
insert into CORE_UOM (NAME,CATEGORY,CODE,ACTIVE,BASE,FACTOR) values ('Centimeter','LENGTH','CM', 'true', 'false', '10.00');
insert into CORE_UOM (NAME,CATEGORY,CODE,ACTIVE,BASE,FACTOR) values ('KiloMeter','LENGTH','KM', 'true', 'false', '1000000.00');

insert into TP (TYPE, FULLNAME, CODE, USEACCOUNT, CATEGORY, BIZ_START_DATE) values ('OWNER','Philips Electrics Pte Ltd','PHILIPS','','COMPANY','1999-09-17');
insert into TP (TYPE, FULLNAME, CODE, USEACCOUNT, CATEGORY, BIZ_START_DATE) values ('OWNER','Dell Computers Ltd','DELL','','COMPANY','2001-01-14');
insert into TP (TYPE, FULLNAME, CODE, USEACCOUNT, CATEGORY, BIZ_START_DATE) values ('OWNER','Motorola Electronics Ltd','Moto','','COMPANY','1990-11-24');
