drop table USER if exists;
drop table PERMISSION if exists;
drop table USER_PERMISSIONS if exists;
drop table PERSONNEL if exists;
drop table CORE_UOM if exists;

drop table PRODUCT if exists;
CREATE TABLE PRODUCT (
    ID          				integer identity primary key, 
    CODE                   		VARCHAR(60),
    OWNER_ID                    integer,
    PROD_TYPE                   VARCHAR(60),
    PROD_DESC                 	VARCHAR(300),
    BRAND_NAME                  VARCHAR(60),    
    PRODUCT_GRP                 VARCHAR(60),
    RETAIL_PROD                 VARCHAR(60),
    ALT_PROD_CODE               VARCHAR(60),
    EXP_DATE_CNTL               CHAR(1),
    SHELF_LIFE                  INTEGER,
    CUST_SHELF_LIFE             INTEGER,
    SHELF_LIFE_UOM              VARCHAR(60),
    COA_REQUIRED                VARCHAR(60),
    PICKING_MTD                 VARCHAR(60),
    PUTAWAY_MTD                 VARCHAR(60),
    DEFAULT_PUTAWAY_LOC         INTEGER,
    BU_CODE                     VARCHAR(60),
    PICKUP_UOM                  VARCHAR(60),    
    BASE_UOM                    VARCHAR(60),    
    ISSUE_UOM                   VARCHAR(60),
    PRICE_UOM                   VARCHAR(60),    
    RCV_UOM                     VARCHAR(60),
    PURCHASE_UOM                VARCHAR(60),
    OUTER_UOM                   VARCHAR(60),
    PALLETISATION_UOM           VARCHAR(60),
    WT_UOM                      VARCHAR(60),
    VOL_UOM                     VARCHAR(60),
    DIM_UOM                     VARCHAR(60),
    DIM_MEASURED_UOM            VARCHAR(60),
    UOM_CONV_ID                 INTEGER,
    LENGTH                      DOUBLE,
    WIDTH                       DOUBLE,
    HEIGHT                      DOUBLE,
    COMPLETE_INFO               CHAR(1),
    STYLE                       VARCHAR(60),
    REPLENISH_MTD               VARCHAR(60),
    PARENT_PROD_CODE            VARCHAR(60),
    HS_CODE                     VARCHAR(60),
    MSDS_FILENAME               VARCHAR(300),
    LOT_CNTL_MTD                VARCHAR(60),
    LOT_PREFIX                  VARCHAR(60),
    LOT_NO_SIZE                 INTEGER,
    LOT_LEADING_ZERO_IND        CHAR(1),
    LOT_RANGE                   BIGINT,
    AUTO_GEN_LOT_NO             CHAR(1),
    SERIAL_CNTL_MTD             VARCHAR(60),
    SERIAL_PREFIX               VARCHAR(60),
    SERIAL_NO_SIZE              INTEGER,
    SERIAL_LEADING_ZERO_IND     CHAR(1),
    SERIAL_RANGE                BIGINT,
    QC_SAMPLING_SIZE            INTEGER,
    INV_LOC_ID                  BIGINT,
    AREA_CODE_CNTL              VARCHAR(60),
    AREA_CODE                   VARCHAR(60),
    STORAGE_CLASS_CNTL          VARCHAR(60),
    STORAGE_CLASS               VARCHAR(60),
    STORAGE_GRP_CTRL            VARCHAR(60),
    STORAGE_GRP                 VARCHAR(60),
    FIXED_LOT_SIZE              INTEGER,
    MIN_LOT_SIZE                INTEGER,
    MAX_LOT_SIZE                INTEGER,
    REPLENISH_LOT_SIZE          INTEGER,
    ORDERING_COST               DOUBLE,
    SAFETY_STOCK_MTD            VARCHAR(300),
    MIN_STOCK_LEVEL             DOUBLE,
    MAX_STOCK_LEVEL             DOUBLE,
    SERVICE_LEVEL               INTEGER,
    SAFETY_STOCK                BIGINT,

    RECORDER_POINT              INTEGER,
    CC_ENABLED                  VARCHAR(300),
    LEAD_TIME                   INTEGER,
    MOV_ABC_TYPE                VARCHAR(300),
    AUTO_GEN_SERIAL_NO          VARCHAR(300),
    PROD_ORIGIN                 VARCHAR(300),
    FORECAST_MTD                VARCHAR(300),
    LAST_CCBY_PROJ_DATE         DATE,
    LAST_CCBY_VAL_DATE          DATE,
    LAST_CCBY_MOV_DATE          DATE,

    CONT_REQ                    VARCHAR(60),
    REMARK                      VARCHAR(300),
    BILLING_CLASS               VARCHAR(60),
    PRODUCT_CLASS               VARCHAR(60),
    INB_SERIAL_SCAN_IND         CHAR(1),
    INB_SERIAL_CNTL_MTD         VARCHAR(60),
    OUTB_SERIAL_SCAN_IND        CHAR(1),
    OUTB_SERIAL_CNTL_MTD        VARCHAR(60),
    PACK_TYPE					VARCHAR(60),
    ACTIVE_IND                  CHAR(1)				NULL NOT NULL
); 

drop table OWNER if exists;
CREATE TABLE OWNER (
    Id                    		integer identity primary key, 
    OWNER_CODE            		VARCHAR(60)    		NOT NULL,
    OWNER_FULLNAME             	VARCHAR(300),
    TP_ID                   	integer,
    CUST_DO_PREFIX              VARCHAR(60),
    PICKING_PRIORITY            INTEGER,
    ACTIVE_IND                  CHAR(1)	NOT NULL,
    unique(OWNER_CODE)
) 

drop table organization if exists;

CREATE TABLE organization (
    ORG_ID                      integer identity primary key, 
    ORG_CODE              		varchar(60)    	NOT NULL,
    ORG_FULLNAME               	varchar(300)   	NOT NULL,
    COMPANY_REG_NO              varchar(60),
    TAX_REG_NO                  varchar(60),
    OPN_COUNTRY              	varchar(300)   	NOT NULL,
    BASE_CURRENCY_CODE          varchar(10),
    ADDR_ID                     integer,
    PHONE_NO                    varchar(60),
    FAX_NO                      varchar(60),
    LOGO_PATH_NAME              varchar(60),
    OTH_INFO_CHAR_1             varchar(300),
    OTH_INFO_CHAR_2             varchar(300),
    OTH_INFO_CHAR_3             varchar(300),
    INVOICE_PREFIX              varchar(10)
);

drop table ADDRESS if exists;
CREATE TABLE ADDRESS (
    ID                     integer identity primary key, 
    NAME 						varchar(300),    
    UNIT                        varchar(300),
    BUILDING                    varchar(300),
    STREET                      varchar(300),
    STREET2						varchar(300),
    STREET3						varchar(300),
    STREET4						varchar(300),
    STREET5						varchar(300),
    CITY                        varchar(300),
    STATE                       varchar(60),
    ZIP_CODE                    varchar(60),
    PRINT_ADDR					varchar(4000)
);

drop table TP if exists;
create table TP(
	ID 					integer identity primary key, 
	TYPE 				varchar(50) not null, 
	FULLNAME 			varchar(50) not null, 
	CODE 				varchar(50) not null, 
	USEACCOUNT 			varchar(50), 
	CATEGORY 			varchar(50) not null, 
    ORG_ID              integer not null,
    ACC_CODE            VARCHAR(60),
    SHIP_ZONE           VARCHAR(60),
    SHIP_ADDR_ID        integer,
    BILL_ADDR_ID        integer,
    CONTACT_PERSON      VARCHAR(300),
    EMAIL               VARCHAR(300),
    PHONE_NO            VARCHAR(60),
    FAX_NO              VARCHAR(60),
	unique(code)
);


create table USER (
	ID integer identity primary key, 
	USERNAME varchar(50) not null,
	FULLNAME varchar(50) not null, 
	PERSONNEL_ID integer, 
	PASSWORD varchar(100) not null,  
	unique(USERNAME)
);
	
create table PERMISSION (
	ID integer identity primary key, 
	PERMISSION varchar(300) not null, 
	PERMISSION_DESC varchar(50) not null, 
	unique(PERMISSION)
);

create table USER_PERMISSIONS (
	ID integer identity primary key, 
	PERMISSION_ID integer not null, 
	USER_ID integer not null
);

create table PERSONNEL (
	ID integer identity primary key, 
	FirstName varchar(30) not null, 
	LastName varchar(30) not null, 
	MiddleName varchar(30), 
	FullName varchar(300), 
	PersonnelNo varchar(20) ,
	StaffNo varchar(50), 
	Phone varchar(15), 
	Fax varchar(15), 
	Mobile varchar(15), 
	Email varchar(30), 
	BirthDate date, 
	unique(STAFFNO)
);

create table CORE_UOM (
	ID integer identity primary key, 
	NAME varchar(50) not null,
	CATEGORY varchar(50) not null,
	CODE varchar(50) not null, 
	ACTIVE char(1), 
	BASE char(1), 
	FACTOR double not null, 
	unique(CATEGORY,CODE)
);


alter table USER_PERMISSIONS add constraint FK_USER_PERMISSION_USERID foreign key (USER_ID) references USER(ID) on delete cascade;
alter table USER_PERMISSIONS add constraint FK_USER_PERMISSION_PERMID foreign key (PERMISSION_ID) references PERMISSION(ID) on delete cascade;