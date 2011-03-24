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