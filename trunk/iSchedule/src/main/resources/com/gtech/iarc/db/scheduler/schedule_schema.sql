drop table TASK_EXE_DETAIL if exists;
drop table TASK_SCHEDULE if exists;

create table TASK_EXE_DETAIL (
	ID integer identity primary key, 
	CODE varchar(50) not null, 
	DESC varchar(300) not null, 
	CLASSNAME varchar(300) not null,
	CATEGORY varchar(100) not null,
	GROUP_CODE varchar(100) not null,  
	ACTIVE Boolean,unique(CODE)
);

	
create table TASK_SCHEDULE (
	ID integer identity primary key, 
	PRIORITY integer, 
	STATUS varchar(100) not null, 
	START_DATE date,
	END_DATE date, 
	MODE varchar(100), 
	JOB_CODE varchar(100) not null, 
	DESC varchar(300), 
	TASK_DETAIL_ID integer not null, 
	EMAIL varchar(100), 
	ACTIVE Boolean, 
	CRON_EXP varchar(300), 
	CREATED_BY varchar(100), 
	CREATED_DATE date, 
	unique(JOB_CODE)
);

alter table TASK_SCHEDULE add constraint FK_TASK_DETAIL_ID foreign key (TASK_DETAIL_ID) references TASK_EXE_DETAIL(ID) on delete cascade;