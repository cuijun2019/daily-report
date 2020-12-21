/*==============================================================*/
/* Database name:  pmcx                                         */
/* DBMS name:      ORACLE Version 11g                           */
/* Created on:     2013/6/21 星期五 10:42:40                       */
/*==============================================================*/



-- Type package declaration
create or replace package PDTypes  
as
    TYPE ref_cursor IS REF CURSOR;
end;

-- Integrity package declaration
create or replace package IntegrityPackage AS
 procedure InitNestLevel;
 function GetNestLevel return number;
 procedure NextNestLevel;
 procedure PreviousNestLevel;
 end IntegrityPackage;
/

-- Integrity package definition
create or replace package body IntegrityPackage AS
 NestLevel number;

-- Procedure to initialize the trigger nest level
 procedure InitNestLevel is
 begin
 NestLevel := 0;
 end;


-- Function to return the trigger nest level
 function GetNestLevel return number is
 begin
 if NestLevel is null then
     NestLevel := 0;
 end if;
 return(NestLevel);
 end;

-- Procedure to increase the trigger nest level
 procedure NextNestLevel is
 begin
 if NestLevel is null then
     NestLevel := 0;
 end if;
 NestLevel := NestLevel + 1;
 end;

-- Procedure to decrease the trigger nest level
 procedure PreviousNestLevel is
 begin
 NestLevel := NestLevel - 1;
 end;

 end IntegrityPackage;
/


drop trigger "CompoundDeleteTrigger_tbdictio"
/

drop trigger "CompoundInsertTrigger_tbdictio"
/

drop trigger "CompoundUpdateTrigger_tbdictio"
/

drop trigger "tib_tbdictionary"
/

drop trigger "CompoundDeleteTrigger_tbemploy"
/

drop trigger "CompoundInsertTrigger_tbemploy"
/

drop trigger "CompoundUpdateTrigger_tbemploy"
/

drop trigger "tib_tbemployee"
/

drop trigger "CompoundDeleteTrigger_tboperat"
/

drop trigger "CompoundInsertTrigger_tboperat"
/

drop trigger "CompoundUpdateTrigger_tboperat"
/

drop trigger "tib_tboperatelog"
/

drop trigger "CompoundDeleteTrigger_tborgani"
/

drop trigger "CompoundInsertTrigger_tborgani"
/

drop trigger "CompoundUpdateTrigger_tborgani"
/

drop trigger "tib_tborganization"
/

drop trigger "CompoundDeleteTrigger_tborgani"
/

drop trigger "CompoundInsertTrigger_tborgani"
/

drop trigger "CompoundUpdateTrigger_tborgani"
/

drop trigger "tib_tborganizationrole"
/

drop trigger "CompoundDeleteTrigger_tbplanet"
/

drop trigger "CompoundInsertTrigger_tbplanet"
/

drop trigger "CompoundUpdateTrigger_tbplanet"
/

drop trigger "tib_tbplanet"
/

drop trigger "CompoundDeleteTrigger_tbprivil"
/

drop trigger "CompoundInsertTrigger_tbprivil"
/

drop trigger "CompoundUpdateTrigger_tbprivil"
/

drop trigger "tib_tbprivilege"
/

drop trigger "CompoundDeleteTrigger_tbrole"
/

drop trigger "CompoundInsertTrigger_tbrole"
/

drop trigger "CompoundUpdateTrigger_tbrole"
/

drop trigger "tib_tbrole"
/

drop trigger "CompoundDeleteTrigger_tbrolepr"
/

drop trigger "CompoundInsertTrigger_tbrolepr"
/

drop trigger "CompoundUpdateTrigger_tbrolepr"
/

drop trigger "tib_tbroleprivilege"
/

drop trigger "CompoundDeleteTrigger_tbsystem"
/

drop trigger "CompoundInsertTrigger_tbsystem"
/

drop trigger "CompoundUpdateTrigger_tbsystem"
/

drop trigger "tib_tbsystemlog"
/

drop trigger "CompoundDeleteTrigger_tbuser"
/

drop trigger "CompoundInsertTrigger_tbuser"
/

drop trigger "CompoundUpdateTrigger_tbuser"
/

drop trigger "tib_tbuser"
/

drop trigger "CompoundDeleteTrigger_tbuserro"
/

drop trigger "CompoundInsertTrigger_tbuserro"
/

drop trigger "CompoundUpdateTrigger_tbuserro"
/

drop trigger "tib_tbuserrole"
/

alter table "tbEmployee"
   drop constraint FK_REF_ORGANIZATION_EMPLOYEE1
/

alter table "tbOrganizationRole"
   drop constraint FK_REF_ORGANIZATION_ROLE1
/

alter table "tbOrganizationRole"
   drop constraint FK_REF_ROLE_ORGANIZATION1
/

alter table "tbRolePrivilege"
   drop constraint FK_REF_PROVILEGE_ROLE1
/

alter table "tbRolePrivilege"
   drop constraint FK_REF_ROLE_PROVILEGE1
/

alter table "tbUserRole"
   drop constraint FK_REF_ROLE_USER1
/

alter table "tbUserRole"
   drop constraint FK_REF_USER_ROLE1
/

drop table "tbBaseta" cascade constraints
/

drop table "tbDictionary" cascade constraints
/

drop table "tbEmployee" cascade constraints
/

drop table "tbOperateLog" cascade constraints
/

drop table "tbOrganization" cascade constraints
/

drop table "tbOrganizationRole" cascade constraints
/

drop table "tbPlanet" cascade constraints
/

drop table "tbPrivilege" cascade constraints
/

drop table "tbRole" cascade constraints
/

drop table "tbRolePrivilege" cascade constraints
/

drop table "tbSystemLog" cascade constraints
/

drop table "tbUita" cascade constraints
/

drop table "tbUser" cascade constraints
/

drop table "tbUserRole" cascade constraints
/

drop sequence "S_tbDictionary"
/

drop sequence "S_tbEmployee"
/

drop sequence "S_tbOperateLog"
/

drop sequence "S_tbOrganization"
/

drop sequence "S_tbOrganizationRole"
/

drop sequence "S_tbPlanet"
/

drop sequence "S_tbPrivilege"
/

drop sequence "S_tbRole"
/

drop sequence "S_tbRolePrivilege"
/

drop sequence "S_tbSystemLog"
/

drop sequence "S_tbUser"
/

drop sequence "S_tbUserRole"
/

/*==============================================================*/
/* Database: "pmcx"                                             */
/*==============================================================*/
create database "pmcx"
/

create sequence "S_tbDictionary"
/

create sequence "S_tbEmployee"
/

create sequence "S_tbOperateLog"
/

create sequence "S_tbOrganization"
/

create sequence "S_tbOrganizationRole"
/

create sequence "S_tbPlanet"
/

create sequence "S_tbPrivilege"
/

create sequence "S_tbRole"
/

create sequence "S_tbRolePrivilege"
/

create sequence "S_tbSystemLog"
/

create sequence "S_tbUser"
/

create sequence "S_tbUserRole"
/

/*==============================================================*/
/* Table: "tbBaseta"                                            */
/*==============================================================*/
create table "tbBaseta" 
(
   "bigId"              INTEGER              default NULL not null,
   "bigParentId"        INTEGER              default NULL,
   "vcKind"             VARCHAR2(20)         default NULL,
   "vcCatalog"          VARCHAR2(200)        default NULL,
   "vcName"             VARCHAR2(200)        default NULL,
   "vcSchema"           VARCHAR2(200)        default NULL,
   "vcFieldName"        VARCHAR2(200)        default NULL,
   "vcPropertyName"     VARCHAR2(200)        default NULL,
   "intWidth"           INTEGER              default NULL,
   "isHidden"           SMALLINT             default NULL,
   "isExportable"       SMALLINT             default NULL,
   "isLeaf"             SMALLINT             default NULL,
   "intOrder"           INTEGER              default NULL,
   "intLevel"           INTEGER              default NULL,
   "vcOuPath"           VARCHAR2(1000)       default NULL,
   "vcCreateUser"       VARCHAR2(20)         default NULL,
   "dtCreateTime"       TIMESTAMP            default 'CURRENT_TIMESTAMP' not null,
   "vcModifyUser"       VARCHAR2(20)         default NULL,
   "dtModifyTime"       TIMESTAMP            default NULL,
   "vcRemark"           VARCHAR2(2000)       default NULL,
   constraint PK_TBBASETA primary key ("bigId")
)
/

comment on column "tbBaseta"."vcOuPath" is
'如：省公司/市公司/地市公司/部门/工作组/营业厅'
/

/*==============================================================*/
/* Table: "tbDictionary"                                        */
/*==============================================================*/
create table "tbDictionary" 
(
   "bigId"              NUMBER(6)            not null,
   "bigParentId"        INTEGER              default NULL,
   "vcKind"             VARCHAR2(10)         default NULL,
   "vcName"             VARCHAR2(200)        default NULL,
   "vcValue"            VARCHAR2(200)        default NULL,
   "isLeaf"             SMALLINT             default 0,
   "intLevel"           INTEGER              default 0,
   "vcOuPath"           VARCHAR2(1000)       default NULL,
   "vcCreateUser"       VARCHAR2(20)         default NULL,
   "dtCreateTime"       TIMESTAMP            default 'CURRENT_TIMESTAMP' not null,
   "vcModifyUser"       VARCHAR2(20)         default NULL,
   "dtModifyTime"       TIMESTAMP            default NULL,
   "vcRemark"           VARCHAR2(2000)       default NULL,
   constraint PK_TBDICTIONARY primary key ("bigId")
)
/

comment on column "tbDictionary"."vcKind" is
'1:数值\n            2:字符\n            3:日期'
/

comment on column "tbDictionary"."vcOuPath" is
'如：省公司/市公司/地市公司/部门/工作组/营业厅'
/

/*==============================================================*/
/* Table: "tbEmployee"                                          */
/*==============================================================*/
create table "tbEmployee" 
(
   "bigId"              NUMBER(6)            not null,
   "bigOrganizationId"  INTEGER              not null,
   "vcCompany"          VARCHAR2(200)        default NULL,
   "vcCompanyGuid"      VARCHAR2(50)         default NULL,
   "vcDepartment"       VARCHAR2(50)         default NULL,
   "vcDepartmentGuid"   VARCHAR2(50)         default NULL,
   "vcStaffGuid"        VARCHAR2(50)         default NULL,
   "vcFullName"         VARCHAR2(100)        default NULL,
   "vcStaffNo"          VARCHAR2(20)         default NULL,
   "vcWorkphone"        VARCHAR2(50)         default NULL,
   "vcTelephone"        VARCHAR2(50)         default NULL,
   "vcShortMobile"      VARCHAR2(10)         default NULL,
   "vcSex"              VARCHAR2(4)          default NULL,
   "vcTitle"            VARCHAR2(50)         default NULL,
   "vcEmail"            VARCHAR2(128)        default NULL,
   "vcAddress"          VARCHAR2(200)        default NULL,
   "vcPosition"         VARCHAR2(50)         default NULL,
   "dtBrithday"         TIMESTAMP            default NULL,
   "dtHireDate"         TIMESTAMP            default NULL,
   "dtFireDate"         TIMESTAMP            default NULL,
   "vcOuPath"           VARCHAR2(1000)       default NULL,
   "vcCreateUser"       VARCHAR2(20)         default NULL,
   "dtCreateTime"       TIMESTAMP            default 'CURRENT_TIMESTAMP' not null,
   "vcModifyUser"       VARCHAR2(20)         default NULL,
   "dtModifyTime"       TIMESTAMP            default NULL,
   "vcRemark"           VARCHAR2(2000)       default NULL,
   constraint PK_TBEMPLOYEE primary key ("bigId"),
   constraint FK_REF_ORGANIZATION_EMPLOYEE unique ("bigOrganizationId")
)
/

comment on column "tbEmployee"."vcOuPath" is
'如：省公司/市公司/地市公司/部门/工作组/营业厅'
/

/*==============================================================*/
/* Table: "tbOperateLog"                                        */
/*==============================================================*/
create table "tbOperateLog" 
(
   "bigId"              NUMBER(6)            not null,
   "intYear"            INTEGER              default NULL,
   "intMonth"           INTEGER              default NULL,
   "intDay"             INTEGER              default NULL,
   "intHour"            INTEGER              default NULL,
   "vcIpv4"             VARCHAR2(20)         default NULL,
   "vcIpv6"             VARCHAR2(20)         default NULL,
   "vcAccount"          VARCHAR2(50)         default NULL,
   "vcSystemCode"       VARCHAR2(100)        default NULL,
   "vcSystem"           VARCHAR2(100)        default NULL,
   "vcModuleCode"       VARCHAR2(100)        default NULL,
   "vcModule"           VARCHAR2(100)        default NULL,
   "dtStartLogTime"     TIMESTAMP            default NULL,
   "dtEndLogTime"       TIMESTAMP            default NULL,
   "intRespCost"        INTEGER              default NULL,
   "intQueryCost"       INTEGER              default NULL,
   "intRespRecord"      INTEGER              default NULL,
   "vcResultCode"       VARCHAR2(20)         default NULL,
   "intLogLevel"        INTEGER              default NULL,
   "vcLogLevelName"     VARCHAR2(20)         default NULL,
   "intLogType"         INTEGER              default NULL,
   "vcLogTypeName"      VARCHAR2(20)         default NULL,
   "intStatus"          INTEGER              default NULL,
   "vcContent"          VARCHAR2(5000)       default NULL,
   "vcOuPath"           VARCHAR2(1000)       default NULL,
   "vcCreateUser"       VARCHAR2(20)         default NULL,
   "dtCreateTime"       TIMESTAMP            default 'CURRENT_TIMESTAMP' not null,
   "vcModifyUser"       VARCHAR2(20)         default NULL,
   "dtModifyTime"       TIMESTAMP            default NULL,
   "vcRemark"           VARCHAR2(2000)       default NULL,
   constraint PK_TBOPERATELOG primary key ("bigId")
)
/

comment on column "tbOperateLog"."intStatus" is
'实施中,结项'
/

comment on column "tbOperateLog"."vcOuPath" is
'如：省公司/市公司/地市公司/部门/工作组/营业厅'
/

/*==============================================================*/
/* Table: "tbOrganization"                                      */
/*==============================================================*/
create table "tbOrganization" 
(
   "bigId"              NUMBER(6)            not null,
   "bigParentId"        INTEGER              default NULL,
   "vcOuguid"           VARCHAR2(50)         default NULL,
   "vcParentOuguid"     VARCHAR2(50)         default NULL,
   "intOuKind"          SMALLINT             default NULL,
   "vcOuName"           VARCHAR2(100)        default NULL,
   "vcParentName"       VARCHAR2(200)        default NULL,
   "intLevel"           INTEGER              default 0,
   "isLeaf"             SMALLINT             default 0,
   "intOrder"           INTEGER              default NULL,
   "vcOuPath"           VARCHAR2(1000)       default NULL,
   "vcCreateUser"       VARCHAR2(20)         default NULL,
   "dtCreateTime"       TIMESTAMP            default 'CURRENT_TIMESTAMP' not null,
   "vcModifyUser"       VARCHAR2(20)         default NULL,
   "dtModifyTime"       TIMESTAMP            default NULL,
   "vcRemark"           VARCHAR2(2000)       default NULL,
   constraint PK_TBORGANIZATION primary key ("bigId")
)
/

comment on column "tbOrganization"."vcOuPath" is
'如：省公司/市公司/地市公司/部门/工作组/营业厅'
/

/*==============================================================*/
/* Table: "tbOrganizationRole"                                  */
/*==============================================================*/
create table "tbOrganizationRole" 
(
   "bigId"              NUMBER(6)            not null,
   "bigOrganizationId"  INTEGER              not null,
   "bigRoleId"          INTEGER              not null,
   "vcOuPath"           VARCHAR2(1000)       default NULL,
   "vcCreateUser"       VARCHAR2(20)         default NULL,
   "dtCreateTime"       TIMESTAMP            default 'CURRENT_TIMESTAMP' not null,
   "vcModifyUser"       VARCHAR2(20)         default NULL,
   "dtModifyTime"       TIMESTAMP            default NULL,
   "vcRemark"           VARCHAR2(2000)       default NULL,
   constraint PK_TBORGANIZATIONROLE primary key ("bigId"),
   constraint FK_REF_ORGANIZATION_ROLE unique ("bigOrganizationId"),
   constraint FK_REF_ROLE_ORGANIZATION unique ("bigRoleId")
)
/

comment on column "tbOrganizationRole"."vcOuPath" is
'如：省公司/市公司/地市公司/部门/工作组/营业厅'
/

/*==============================================================*/
/* Table: "tbPlanet"                                            */
/*==============================================================*/
create table "tbPlanet" 
(
   "bigId"              NUMBER(6)            not null,
   "bigParentId"        INTEGER              not null,
   "vcKind"             VARCHAR2(10)         default NULL,
   "vcCode"             VARCHAR2(50)         default NULL,
   "vcName"             VARCHAR2(200)        default NULL,
   "isLeaf"             SMALLINT             default 0,
   "intLevel"           INTEGER              default 0,
   "vcOuPath"           VARCHAR2(1000)       default NULL,
   "vcCreateUser"       VARCHAR2(20)         default NULL,
   "dtCreateTime"       TIMESTAMP            default 'CURRENT_TIMESTAMP' not null,
   "vcModifyUser"       VARCHAR2(20)         default NULL,
   "dtModifyTime"       TIMESTAMP            default NULL,
   "vcRemark"           VARCHAR2(2000)       default NULL,
   constraint PK_TBPLANET primary key ("bigId"),
   constraint FK_REF_PROVINCE_CITY unique ("bigParentId")
)
/

comment on column "tbPlanet"."vcOuPath" is
'如：省公司/市公司/地市公司/部门/工作组/营业厅'
/

/*==============================================================*/
/* Table: "tbPrivilege"                                         */
/*==============================================================*/
create table "tbPrivilege" 
(
   "bigId"              NUMBER(6)            not null,
   "bigParentId"        INTEGER              default NULL,
   "vcKind"             VARCHAR2(10)         default NULL,
   "vcName"             VARCHAR2(200)        default NULL,
   "vcCode"             VARCHAR2(12)         default NULL,
   "vcSymbol"           VARCHAR2(200)        default NULL,
   "vcPath"             VARCHAR2(1000)       default NULL,
   "vcUrl"              VARCHAR2(1000)       default NULL,
   "isLeaf"             SMALLINT             default 0,
   "intLevel"           INTEGER              default 0,
   "intOrder"           INTEGER              default NULL,
   "vcOuPath"           VARCHAR2(1000)       default NULL,
   "vcCreateUser"       VARCHAR2(20)         default NULL,
   "dtCreateTime"       TIMESTAMP            default 'CURRENT_TIMESTAMP' not null,
   "vcModifyUser"       VARCHAR2(20)         default NULL,
   "dtModifyTime"       TIMESTAMP            default NULL,
   "vcRemark"           VARCHAR2(2000)       default NULL,
   constraint PK_TBPRIVILEGE primary key ("bigId")
)
/

comment on column "tbPrivilege"."vcKind" is
'对应数据字典：USER_TYPE\r\n            组织：USER_TYPE_ORGANIZATION\r\n            本地：USER_TYPE_LOCAL'
/

comment on column "tbPrivilege"."vcCode" is
'权限编码'
/

comment on column "tbPrivilege"."vcOuPath" is
'如：省公司/市公司/地市公司/部门/工作组/营业厅'
/

/*==============================================================*/
/* Table: "tbRole"                                              */
/*==============================================================*/
create table "tbRole" 
(
   "bigId"              NUMBER(6)            not null,
   "vcName"             VARCHAR2(200)        not null,
   "vcOuPath"           VARCHAR2(1000)       default NULL,
   "vcCreateUser"       VARCHAR2(20)         default NULL,
   "dtCreateTime"       TIMESTAMP            default 'CURRENT_TIMESTAMP' not null,
   "vcModifyUser"       VARCHAR2(20)         default NULL,
   "dtModifyTime"       TIMESTAMP            default NULL,
   "vcRemark"           VARCHAR2(2000)       default NULL,
   constraint PK_TBROLE primary key ("bigId")
)
/

comment on column "tbRole"."vcOuPath" is
'如：省公司/市公司/地市公司/部门/工作组/营业厅'
/

/*==============================================================*/
/* Table: "tbRolePrivilege"                                     */
/*==============================================================*/
create table "tbRolePrivilege" 
(
   "bigId"              NUMBER(6)            not null,
   "bigPrivilegeId"     INTEGER              not null,
   "bigRoleId"          INTEGER              not null,
   "vcOuPath"           VARCHAR2(1000)       default NULL,
   "vcCreateUser"       VARCHAR2(20)         default NULL,
   "dtCreateTime"       TIMESTAMP            default 'CURRENT_TIMESTAMP' not null,
   "vcModifyUser"       VARCHAR2(20)         default NULL,
   "dtModifyTime"       TIMESTAMP            default NULL,
   "vcRemark"           VARCHAR2(2000)       default NULL,
   constraint PK_TBROLEPRIVILEGE primary key ("bigId"),
   constraint FK_REF_PROVILEGE_ROLE unique ("bigPrivilegeId"),
   constraint FK_REF_ROLE_PROVILEGE unique ("bigRoleId")
)
/

comment on column "tbRolePrivilege"."vcOuPath" is
'如：省公司/市公司/地市公司/部门/工作组/营业厅'
/

/*==============================================================*/
/* Table: "tbSystemLog"                                         */
/*==============================================================*/
create table "tbSystemLog" 
(
   "bigId"              NUMBER(6)            not null,
   "vcIpv4"             VARCHAR2(20)         default NULL,
   "vcIpv6"             VARCHAR2(20)         default NULL,
   "vcAccount"          VARCHAR2(50)         default NULL,
   "vcSystemCode"       VARCHAR2(100)        default NULL,
   "vcSystem"           VARCHAR2(100)        default NULL,
   "vcModuleCode"       VARCHAR2(100)        default NULL,
   "vcModule"           VARCHAR2(100)        default NULL,
   "vcContent"          VARCHAR2(5000)       default NULL,
   "vcResultCode"       VARCHAR2(20)         default NULL,
   "intLogLevel"        INTEGER              default NULL,
   "vcLogLevelName"     VARCHAR2(20)         default NULL,
   "intLogType"         INTEGER              default NULL,
   "vcLogTypeName"      VARCHAR2(20)         default NULL,
   "vcOuPath"           VARCHAR2(1000)       default NULL,
   "vcCreateUser"       VARCHAR2(20)         default NULL,
   "dtCreateTime"       TIMESTAMP            default 'CURRENT_TIMESTAMP' not null,
   "vcModifyUser"       VARCHAR2(20)         default NULL,
   "dtModifyTime"       TIMESTAMP            default NULL,
   "vcRemark"           VARCHAR2(2000)       default NULL,
   constraint PK_TBSYSTEMLOG primary key ("bigId")
)
/

comment on column "tbSystemLog"."vcOuPath" is
'如：省公司/市公司/地市公司/部门/工作组/营业厅'
/

/*==============================================================*/
/* Table: "tbUita"                                              */
/*==============================================================*/
create table "tbUita" 
(
   "bigId"              INTEGER              default NULL not null,
   "bigParentId"        INTEGER              default NULL,
   "bigPrivilegeId"     INTEGER              default NULL,
   "vcKind"             VARCHAR2(20)         default NULL,
   "vcCatalog"          VARCHAR2(200)        default NULL,
   "vcName"             VARCHAR2(200)        default NULL,
   "vcSchema"           VARCHAR2(200)        default NULL,
   "vcFieldName"        VARCHAR2(200)        default NULL,
   "vcPropertyName"     VARCHAR2(200)        default NULL,
   "intWidth"           INTEGER              default NULL,
   "isHidden"           SMALLINT             default NULL,
   "isExportable"       SMALLINT             default NULL,
   "isLeaf"             SMALLINT             default NULL,
   "intOrder"           INTEGER              default NULL,
   "intLevel"           INTEGER              default NULL,
   "vcOuPath"           VARCHAR2(1000)       default NULL,
   "vcCreateUser"       VARCHAR2(20)         default NULL,
   "dtCreateTime"       TIMESTAMP            default 'CURRENT_TIMESTAMP' not null,
   "vcModifyUser"       VARCHAR2(20)         default NULL,
   "dtModifyTime"       TIMESTAMP            default NULL,
   "vcRemark"           VARCHAR2(2000)       default NULL,
   constraint PK_TBUITA primary key ("bigId")
)
/

comment on column "tbUita"."vcOuPath" is
'如：省公司/市公司/地市公司/部门/工作组/营业厅'
/

/*==============================================================*/
/* Table: "tbUser"                                              */
/*==============================================================*/
create table "tbUser" 
(
   "bigId"              NUMBER(6)            not null,
   "vcStaffguid"        VARCHAR2(50)         default NULL,
   "vcOuguid"           VARCHAR2(50)         default NULL,
   "vcOuName"           VARCHAR2(100)        default NULL,
   "vcAccount"          VARCHAR2(50)         not null,
   "vcPassword"         VARCHAR2(64)         default NULL,
   "vcSalt"             VARCHAR2(64)         default NULL,
   "vcTips"             VARCHAR2(100)        default NULL,
   "vcTipAnswer"        VARCHAR2(200)        default NULL,
   "vcKind"             VARCHAR2(10)         default NULL,
   "vcFullName"         VARCHAR2(100)        default NULL,
   "vcSex"              VARCHAR2(4)          default NULL,
   "vcWorkphone"        VARCHAR2(50)         default NULL,
   "vcTelephone"        VARCHAR2(50)         default NULL,
   "vcShortMobile"      VARCHAR2(10)         default NULL,
   "vcEmail"            VARCHAR2(128)        default NULL,
   "isSupervisor"       SMALLINT             default NULL,
   "isLock"             SMALLINT             default NULL,
   "dtLockedDate"       TIMESTAMP            default NULL,
   "vcStatus"           VARCHAR2(10)         default NULL,
   "dtValidTime"        TIMESTAMP            default NULL,
   "vcOuPath"           VARCHAR2(1000)       default NULL,
   "vcCreateUser"       VARCHAR2(20)         default NULL,
   "dtCreateTime"       TIMESTAMP            default 'CURRENT_TIMESTAMP' not null,
   "vcModifyUser"       VARCHAR2(20)         default NULL,
   "dtModifyTime"       TIMESTAMP            default NULL,
   "vcRemark"           VARCHAR2(2000)       default NULL,
   constraint PK_TBUSER primary key ("bigId")
)
/

comment on column "tbUser"."vcPassword" is
'支持存储MD5,SHA-1,SHA-256'
/

comment on column "tbUser"."vcSalt" is
'随机强密码序列，基于base-64编码，用于密码加密验证'
/

comment on column "tbUser"."vcKind" is
'对应数据字典：USER_TYPE\r\n            组织：USER_TYPE_ORGANIZATION\r\n            本地：USER_TYPE_LOCAL'
/

comment on column "tbUser"."dtLockedDate" is
'锁定时间'
/

comment on column "tbUser"."dtValidTime" is
'密码有效期'
/

comment on column "tbUser"."vcOuPath" is
'如：省公司/市公司/地市公司/部门/工作组/营业厅'
/

/*==============================================================*/
/* Table: "tbUserRole"                                          */
/*==============================================================*/
create table "tbUserRole" 
(
   "bigId"              NUMBER(6)            not null,
   "bigRoleId"          INTEGER              not null,
   "bigUserId"          INTEGER              not null,
   "vcOuPath"           VARCHAR2(1000)       default NULL,
   "vcCreateUser"       VARCHAR2(20)         default NULL,
   "dtCreateTime"       TIMESTAMP            default 'CURRENT_TIMESTAMP' not null,
   "vcModifyUser"       VARCHAR2(20)         default NULL,
   "dtModifyTime"       TIMESTAMP            default NULL,
   "vcRemark"           VARCHAR2(2000)       default NULL,
   constraint PK_TBUSERROLE primary key ("bigId"),
   constraint FK_REF_ROLE_USER unique ("bigRoleId"),
   constraint FK_REF_USER_ROLE unique ("bigUserId")
)
/

comment on column "tbUserRole"."vcOuPath" is
'如：省公司/市公司/地市公司/部门/工作组/营业厅'
/

alter table "tbEmployee"
   add constraint FK_REF_ORGANIZATION_EMPLOYEE1 foreign key ("bigOrganizationId")
      references "tbOrganization" ("bigId")
/

alter table "tbOrganizationRole"
   add constraint FK_REF_ORGANIZATION_ROLE1 foreign key ("bigOrganizationId")
      references "tbOrganization" ("bigId")
/

alter table "tbOrganizationRole"
   add constraint FK_REF_ROLE_ORGANIZATION1 foreign key ("bigRoleId")
      references "tbRole" ("bigId")
/

alter table "tbRolePrivilege"
   add constraint FK_REF_PROVILEGE_ROLE1 foreign key ("bigPrivilegeId")
      references "tbPrivilege" ("bigId")
/

alter table "tbRolePrivilege"
   add constraint FK_REF_ROLE_PROVILEGE1 foreign key ("bigRoleId")
      references "tbRole" ("bigId")
/

alter table "tbUserRole"
   add constraint FK_REF_ROLE_USER1 foreign key ("bigRoleId")
      references "tbRole" ("bigId")
/

alter table "tbUserRole"
   add constraint FK_REF_USER_ROLE1 foreign key ("bigUserId")
      references "tbUser" ("bigId")
/


create or replace trigger "CompoundDeleteTrigger_tbdictio"
for delete on "tbDictionary" compound trigger
// Declaration
// Body
  before statement is
  begin
     NULL;
  end before statement;

  before each row is
  begin
     NULL;
  end before each row;

  after each row is
  begin
     NULL;
  end after each row;

  after statement is
  begin
     NULL;
  end after statement;

END
/


create or replace trigger "CompoundInsertTrigger_tbdictio"
for insert on "tbDictionary" compound trigger
// Declaration
// Body
  before statement is
  begin
     NULL;
  end before statement;

  before each row is
  begin
     NULL;
  end before each row;

  after each row is
  begin
     NULL;
  end after each row;

  after statement is
  begin
     NULL;
  end after statement;

END
/


create or replace trigger "CompoundUpdateTrigger_tbdictio"
for update on "tbDictionary" compound trigger
// Declaration
// Body
  before statement is
  begin
     NULL;
  end before statement;

  before each row is
  begin
     NULL;
  end before each row;

  after each row is
  begin
     NULL;
  end after each row;

  after statement is
  begin
     NULL;
  end after statement;

END
/


create trigger "tib_tbdictionary" before insert
on "tbDictionary" for each row
declare
    integrity_error  exception;
    errno            integer;
    errmsg           char(200);
    dummy            integer;
    found            boolean;

begin
    --  Column ""bigId"" uses sequence S_tbDictionary
    select S_tbDictionary.NEXTVAL INTO :new."bigId" from dual;

--  Errors handling
exception
    when integrity_error then
       raise_application_error(errno, errmsg);
end;
/


create or replace trigger "CompoundDeleteTrigger_tbemploy"
for delete on "tbEmployee" compound trigger
// Declaration
// Body
  before statement is
  begin
     NULL;
  end before statement;

  before each row is
  begin
     NULL;
  end before each row;

  after each row is
  begin
     NULL;
  end after each row;

  after statement is
  begin
     NULL;
  end after statement;

END
/


create or replace trigger "CompoundInsertTrigger_tbemploy"
for insert on "tbEmployee" compound trigger
// Declaration
// Body
  before statement is
  begin
     NULL;
  end before statement;

  before each row is
  begin
     NULL;
  end before each row;

  after each row is
  begin
     NULL;
  end after each row;

  after statement is
  begin
     NULL;
  end after statement;

END
/


create or replace trigger "CompoundUpdateTrigger_tbemploy"
for update on "tbEmployee" compound trigger
// Declaration
// Body
  before statement is
  begin
     NULL;
  end before statement;

  before each row is
  begin
     NULL;
  end before each row;

  after each row is
  begin
     NULL;
  end after each row;

  after statement is
  begin
     NULL;
  end after statement;

END
/


create trigger "tib_tbemployee" before insert
on "tbEmployee" for each row
declare
    integrity_error  exception;
    errno            integer;
    errmsg           char(200);
    dummy            integer;
    found            boolean;

begin
    --  Column ""bigId"" uses sequence S_tbEmployee
    select S_tbEmployee.NEXTVAL INTO :new."bigId" from dual;

--  Errors handling
exception
    when integrity_error then
       raise_application_error(errno, errmsg);
end;
/


create or replace trigger "CompoundDeleteTrigger_tboperat"
for delete on "tbOperateLog" compound trigger
// Declaration
// Body
  before statement is
  begin
     NULL;
  end before statement;

  before each row is
  begin
     NULL;
  end before each row;

  after each row is
  begin
     NULL;
  end after each row;

  after statement is
  begin
     NULL;
  end after statement;

END
/


create or replace trigger "CompoundInsertTrigger_tboperat"
for insert on "tbOperateLog" compound trigger
// Declaration
// Body
  before statement is
  begin
     NULL;
  end before statement;

  before each row is
  begin
     NULL;
  end before each row;

  after each row is
  begin
     NULL;
  end after each row;

  after statement is
  begin
     NULL;
  end after statement;

END
/


create or replace trigger "CompoundUpdateTrigger_tboperat"
for update on "tbOperateLog" compound trigger
// Declaration
// Body
  before statement is
  begin
     NULL;
  end before statement;

  before each row is
  begin
     NULL;
  end before each row;

  after each row is
  begin
     NULL;
  end after each row;

  after statement is
  begin
     NULL;
  end after statement;

END
/


create trigger "tib_tboperatelog" before insert
on "tbOperateLog" for each row
declare
    integrity_error  exception;
    errno            integer;
    errmsg           char(200);
    dummy            integer;
    found            boolean;

begin
    --  Column ""bigId"" uses sequence S_tbOperateLog
    select S_tbOperateLog.NEXTVAL INTO :new."bigId" from dual;

--  Errors handling
exception
    when integrity_error then
       raise_application_error(errno, errmsg);
end;
/


create or replace trigger "CompoundDeleteTrigger_tborgani"
for delete on "tbOrganization" compound trigger
// Declaration
// Body
  before statement is
  begin
     NULL;
  end before statement;

  before each row is
  begin
     NULL;
  end before each row;

  after each row is
  begin
     NULL;
  end after each row;

  after statement is
  begin
     NULL;
  end after statement;

END
/


create or replace trigger "CompoundInsertTrigger_tborgani"
for insert on "tbOrganization" compound trigger
// Declaration
// Body
  before statement is
  begin
     NULL;
  end before statement;

  before each row is
  begin
     NULL;
  end before each row;

  after each row is
  begin
     NULL;
  end after each row;

  after statement is
  begin
     NULL;
  end after statement;

END
/


create or replace trigger "CompoundUpdateTrigger_tborgani"
for update on "tbOrganization" compound trigger
// Declaration
// Body
  before statement is
  begin
     NULL;
  end before statement;

  before each row is
  begin
     NULL;
  end before each row;

  after each row is
  begin
     NULL;
  end after each row;

  after statement is
  begin
     NULL;
  end after statement;

END
/


create trigger "tib_tborganization" before insert
on "tbOrganization" for each row
declare
    integrity_error  exception;
    errno            integer;
    errmsg           char(200);
    dummy            integer;
    found            boolean;

begin
    --  Column ""bigId"" uses sequence S_tbOrganization
    select S_tbOrganization.NEXTVAL INTO :new."bigId" from dual;

--  Errors handling
exception
    when integrity_error then
       raise_application_error(errno, errmsg);
end;
/


create or replace trigger "CompoundDeleteTrigger_tborgani"
for delete on "tbOrganizationRole" compound trigger
// Declaration
// Body
  before statement is
  begin
     NULL;
  end before statement;

  before each row is
  begin
     NULL;
  end before each row;

  after each row is
  begin
     NULL;
  end after each row;

  after statement is
  begin
     NULL;
  end after statement;

END
/


create or replace trigger "CompoundInsertTrigger_tborgani"
for insert on "tbOrganizationRole" compound trigger
// Declaration
// Body
  before statement is
  begin
     NULL;
  end before statement;

  before each row is
  begin
     NULL;
  end before each row;

  after each row is
  begin
     NULL;
  end after each row;

  after statement is
  begin
     NULL;
  end after statement;

END
/


create or replace trigger "CompoundUpdateTrigger_tborgani"
for update on "tbOrganizationRole" compound trigger
// Declaration
// Body
  before statement is
  begin
     NULL;
  end before statement;

  before each row is
  begin
     NULL;
  end before each row;

  after each row is
  begin
     NULL;
  end after each row;

  after statement is
  begin
     NULL;
  end after statement;

END
/


create trigger "tib_tborganizationrole" before insert
on "tbOrganizationRole" for each row
declare
    integrity_error  exception;
    errno            integer;
    errmsg           char(200);
    dummy            integer;
    found            boolean;

begin
    --  Column ""bigId"" uses sequence S_tbOrganizationRole
    select S_tbOrganizationRole.NEXTVAL INTO :new."bigId" from dual;

--  Errors handling
exception
    when integrity_error then
       raise_application_error(errno, errmsg);
end;
/


create or replace trigger "CompoundDeleteTrigger_tbplanet"
for delete on "tbPlanet" compound trigger
// Declaration
// Body
  before statement is
  begin
     NULL;
  end before statement;

  before each row is
  begin
     NULL;
  end before each row;

  after each row is
  begin
     NULL;
  end after each row;

  after statement is
  begin
     NULL;
  end after statement;

END
/


create or replace trigger "CompoundInsertTrigger_tbplanet"
for insert on "tbPlanet" compound trigger
// Declaration
// Body
  before statement is
  begin
     NULL;
  end before statement;

  before each row is
  begin
     NULL;
  end before each row;

  after each row is
  begin
     NULL;
  end after each row;

  after statement is
  begin
     NULL;
  end after statement;

END
/


create or replace trigger "CompoundUpdateTrigger_tbplanet"
for update on "tbPlanet" compound trigger
// Declaration
// Body
  before statement is
  begin
     NULL;
  end before statement;

  before each row is
  begin
     NULL;
  end before each row;

  after each row is
  begin
     NULL;
  end after each row;

  after statement is
  begin
     NULL;
  end after statement;

END
/


create trigger "tib_tbplanet" before insert
on "tbPlanet" for each row
declare
    integrity_error  exception;
    errno            integer;
    errmsg           char(200);
    dummy            integer;
    found            boolean;

begin
    --  Column ""bigId"" uses sequence S_tbPlanet
    select S_tbPlanet.NEXTVAL INTO :new."bigId" from dual;

--  Errors handling
exception
    when integrity_error then
       raise_application_error(errno, errmsg);
end;
/


create or replace trigger "CompoundDeleteTrigger_tbprivil"
for delete on "tbPrivilege" compound trigger
// Declaration
// Body
  before statement is
  begin
     NULL;
  end before statement;

  before each row is
  begin
     NULL;
  end before each row;

  after each row is
  begin
     NULL;
  end after each row;

  after statement is
  begin
     NULL;
  end after statement;

END
/


create or replace trigger "CompoundInsertTrigger_tbprivil"
for insert on "tbPrivilege" compound trigger
// Declaration
// Body
  before statement is
  begin
     NULL;
  end before statement;

  before each row is
  begin
     NULL;
  end before each row;

  after each row is
  begin
     NULL;
  end after each row;

  after statement is
  begin
     NULL;
  end after statement;

END
/


create or replace trigger "CompoundUpdateTrigger_tbprivil"
for update on "tbPrivilege" compound trigger
// Declaration
// Body
  before statement is
  begin
     NULL;
  end before statement;

  before each row is
  begin
     NULL;
  end before each row;

  after each row is
  begin
     NULL;
  end after each row;

  after statement is
  begin
     NULL;
  end after statement;

END
/


create trigger "tib_tbprivilege" before insert
on "tbPrivilege" for each row
declare
    integrity_error  exception;
    errno            integer;
    errmsg           char(200);
    dummy            integer;
    found            boolean;

begin
    --  Column ""bigId"" uses sequence S_tbPrivilege
    select S_tbPrivilege.NEXTVAL INTO :new."bigId" from dual;

--  Errors handling
exception
    when integrity_error then
       raise_application_error(errno, errmsg);
end;
/


create or replace trigger "CompoundDeleteTrigger_tbrole"
for delete on "tbRole" compound trigger
// Declaration
// Body
  before statement is
  begin
     NULL;
  end before statement;

  before each row is
  begin
     NULL;
  end before each row;

  after each row is
  begin
     NULL;
  end after each row;

  after statement is
  begin
     NULL;
  end after statement;

END
/


create or replace trigger "CompoundInsertTrigger_tbrole"
for insert on "tbRole" compound trigger
// Declaration
// Body
  before statement is
  begin
     NULL;
  end before statement;

  before each row is
  begin
     NULL;
  end before each row;

  after each row is
  begin
     NULL;
  end after each row;

  after statement is
  begin
     NULL;
  end after statement;

END
/


create or replace trigger "CompoundUpdateTrigger_tbrole"
for update on "tbRole" compound trigger
// Declaration
// Body
  before statement is
  begin
     NULL;
  end before statement;

  before each row is
  begin
     NULL;
  end before each row;

  after each row is
  begin
     NULL;
  end after each row;

  after statement is
  begin
     NULL;
  end after statement;

END
/


create trigger "tib_tbrole" before insert
on "tbRole" for each row
declare
    integrity_error  exception;
    errno            integer;
    errmsg           char(200);
    dummy            integer;
    found            boolean;

begin
    --  Column ""bigId"" uses sequence S_tbRole
    select S_tbRole.NEXTVAL INTO :new."bigId" from dual;

--  Errors handling
exception
    when integrity_error then
       raise_application_error(errno, errmsg);
end;
/


create or replace trigger "CompoundDeleteTrigger_tbrolepr"
for delete on "tbRolePrivilege" compound trigger
// Declaration
// Body
  before statement is
  begin
     NULL;
  end before statement;

  before each row is
  begin
     NULL;
  end before each row;

  after each row is
  begin
     NULL;
  end after each row;

  after statement is
  begin
     NULL;
  end after statement;

END
/


create or replace trigger "CompoundInsertTrigger_tbrolepr"
for insert on "tbRolePrivilege" compound trigger
// Declaration
// Body
  before statement is
  begin
     NULL;
  end before statement;

  before each row is
  begin
     NULL;
  end before each row;

  after each row is
  begin
     NULL;
  end after each row;

  after statement is
  begin
     NULL;
  end after statement;

END
/


create or replace trigger "CompoundUpdateTrigger_tbrolepr"
for update on "tbRolePrivilege" compound trigger
// Declaration
// Body
  before statement is
  begin
     NULL;
  end before statement;

  before each row is
  begin
     NULL;
  end before each row;

  after each row is
  begin
     NULL;
  end after each row;

  after statement is
  begin
     NULL;
  end after statement;

END
/


create trigger "tib_tbroleprivilege" before insert
on "tbRolePrivilege" for each row
declare
    integrity_error  exception;
    errno            integer;
    errmsg           char(200);
    dummy            integer;
    found            boolean;

begin
    --  Column ""bigId"" uses sequence S_tbRolePrivilege
    select S_tbRolePrivilege.NEXTVAL INTO :new."bigId" from dual;

--  Errors handling
exception
    when integrity_error then
       raise_application_error(errno, errmsg);
end;
/


create or replace trigger "CompoundDeleteTrigger_tbsystem"
for delete on "tbSystemLog" compound trigger
// Declaration
// Body
  before statement is
  begin
     NULL;
  end before statement;

  before each row is
  begin
     NULL;
  end before each row;

  after each row is
  begin
     NULL;
  end after each row;

  after statement is
  begin
     NULL;
  end after statement;

END
/


create or replace trigger "CompoundInsertTrigger_tbsystem"
for insert on "tbSystemLog" compound trigger
// Declaration
// Body
  before statement is
  begin
     NULL;
  end before statement;

  before each row is
  begin
     NULL;
  end before each row;

  after each row is
  begin
     NULL;
  end after each row;

  after statement is
  begin
     NULL;
  end after statement;

END
/


create or replace trigger "CompoundUpdateTrigger_tbsystem"
for update on "tbSystemLog" compound trigger
// Declaration
// Body
  before statement is
  begin
     NULL;
  end before statement;

  before each row is
  begin
     NULL;
  end before each row;

  after each row is
  begin
     NULL;
  end after each row;

  after statement is
  begin
     NULL;
  end after statement;

END
/


create trigger "tib_tbsystemlog" before insert
on "tbSystemLog" for each row
declare
    integrity_error  exception;
    errno            integer;
    errmsg           char(200);
    dummy            integer;
    found            boolean;

begin
    --  Column ""bigId"" uses sequence S_tbSystemLog
    select S_tbSystemLog.NEXTVAL INTO :new."bigId" from dual;

--  Errors handling
exception
    when integrity_error then
       raise_application_error(errno, errmsg);
end;
/


create or replace trigger "CompoundDeleteTrigger_tbuser"
for delete on "tbUser" compound trigger
// Declaration
// Body
  before statement is
  begin
     NULL;
  end before statement;

  before each row is
  begin
     NULL;
  end before each row;

  after each row is
  begin
     NULL;
  end after each row;

  after statement is
  begin
     NULL;
  end after statement;

END
/


create or replace trigger "CompoundInsertTrigger_tbuser"
for insert on "tbUser" compound trigger
// Declaration
// Body
  before statement is
  begin
     NULL;
  end before statement;

  before each row is
  begin
     NULL;
  end before each row;

  after each row is
  begin
     NULL;
  end after each row;

  after statement is
  begin
     NULL;
  end after statement;

END
/


create or replace trigger "CompoundUpdateTrigger_tbuser"
for update on "tbUser" compound trigger
// Declaration
// Body
  before statement is
  begin
     NULL;
  end before statement;

  before each row is
  begin
     NULL;
  end before each row;

  after each row is
  begin
     NULL;
  end after each row;

  after statement is
  begin
     NULL;
  end after statement;

END
/


create trigger "tib_tbuser" before insert
on "tbUser" for each row
declare
    integrity_error  exception;
    errno            integer;
    errmsg           char(200);
    dummy            integer;
    found            boolean;

begin
    --  Column ""bigId"" uses sequence S_tbUser
    select S_tbUser.NEXTVAL INTO :new."bigId" from dual;

--  Errors handling
exception
    when integrity_error then
       raise_application_error(errno, errmsg);
end;
/


create or replace trigger "CompoundDeleteTrigger_tbuserro"
for delete on "tbUserRole" compound trigger
// Declaration
// Body
  before statement is
  begin
     NULL;
  end before statement;

  before each row is
  begin
     NULL;
  end before each row;

  after each row is
  begin
     NULL;
  end after each row;

  after statement is
  begin
     NULL;
  end after statement;

END
/


create or replace trigger "CompoundInsertTrigger_tbuserro"
for insert on "tbUserRole" compound trigger
// Declaration
// Body
  before statement is
  begin
     NULL;
  end before statement;

  before each row is
  begin
     NULL;
  end before each row;

  after each row is
  begin
     NULL;
  end after each row;

  after statement is
  begin
     NULL;
  end after statement;

END
/


create or replace trigger "CompoundUpdateTrigger_tbuserro"
for update on "tbUserRole" compound trigger
// Declaration
// Body
  before statement is
  begin
     NULL;
  end before statement;

  before each row is
  begin
     NULL;
  end before each row;

  after each row is
  begin
     NULL;
  end after each row;

  after statement is
  begin
     NULL;
  end after statement;

END
/


create trigger "tib_tbuserrole" before insert
on "tbUserRole" for each row
declare
    integrity_error  exception;
    errno            integer;
    errmsg           char(200);
    dummy            integer;
    found            boolean;

begin
    --  Column ""bigId"" uses sequence S_tbUserRole
    select S_tbUserRole.NEXTVAL INTO :new."bigId" from dual;

--  Errors handling
exception
    when integrity_error then
       raise_application_error(errno, errmsg);
end;
/

/
insert  into `tbOperateLog`(`bigId`,`intYear`,`intMonth`,`intDay`,`intHour`,`vcIpv4`,`vcIpv6`,`vcAccount`,`vcSystemCode`,`vcSystem`,`vcModuleCode`,`vcModule`,`dtStartLogTime`,`dtEndLogTime`,`intRespCost`,`intQueryCost`,`intRespRecord`,`vcResultCode`,`intLogLevel`,`vcLogLevelName`,`intLogType`,`vcLogTypeName`,`intStatus`,`vcContent`,`dtCreateTime`) values (1,2012,1,28,1,'','','','100','基础服务','10000001','[系统登录服务].[登录系统]','2012-02-28 13:58:17','2012-02-28 13:58:17',0,9,0,'0001',3,'error',1,'security',1,'','2012-02-28 13:58:17'),(2,2012,1,28,1,'','','','201','宜通世纪项目管理平台','20100302','[外包合同管理服务].[查询]','2012-02-28 13:58:18','2012-02-28 13:58:18',0,338,0,'0005',1,'info',2,'operate',0,'','2012-02-28 13:58:18'),(3,2012,1,28,1,'','','','201','宜通世纪项目管理平台','20100303','[外包合同管理服务].[新增记录]','2012-02-28 13:58:18','2012-02-28 13:58:18',0,161,0,'0023',3,'error',0,'system',1,'','2012-02-28 13:58:18'),(4,2012,1,28,1,'','','','201','宜通世纪项目管理平台','20100302','[外包合同管理服务].[查询]','2012-02-28 13:58:18','2012-02-28 13:58:18',0,26,0,'0005',1,'info',2,'operate',0,'','2012-02-28 13:58:18'),(5,2012,1,28,1,'','','','201','宜通世纪项目管理平台','20100302','[外包合同管理服务].[查询]','2012-02-28 13:58:18','2012-02-28 13:58:18',0,15,0,'0005',1,'info',2,'operate',0,'','2012-02-28 13:58:18'),(6,2012,1,28,1,'','','','201','宜通世纪项目管理平台','20100306','[外包合同管理服务].[导出EXCEL]','2012-02-28 13:58:18','2012-02-28 13:58:18',0,544,0,'0005',1,'info',2,'operate',0,'','2012-02-28 13:58:18'),(7,2012,1,28,1,'','','','201','宜通世纪项目管理平台','20100307','[外包合同管理服务].[导入EXCEL]','2012-02-28 13:58:18','2012-02-28 13:58:18',0,14,0,'0023',3,'error',0,'system',1,'','2012-02-28 13:58:18'),(8,2012,1,28,1,'','','','100','基础服务','10000001','[系统登录服务].[登录系统]','2012-02-28 13:58:19','2012-02-28 13:58:19',0,8,0,'0001',3,'error',1,'security',1,'','2012-02-28 13:58:19'),(9,2012,1,28,1,'','','','201','宜通世纪项目管理平台','20100402','[合同管理服务].[查询]','2012-02-28 13:58:19','2012-02-28 13:58:19',0,57,0,'0005',1,'info',2,'operate',0,'','2012-02-28 13:58:19'),(10,2012,1,28,1,'','','','201','宜通世纪项目管理平台','20100403','[合同管理服务].[新增记录]','2012-02-28 13:58:19','2012-02-28 13:58:19',0,26,0,'0023',3,'error',0,'system',1,'','2012-02-28 13:58:19'),(11,2012,1,28,1,'','','','201','宜通世纪项目管理平台','20100402','[合同管理服务].[查询]','2012-02-28 13:58:19','2012-02-28 13:58:19',0,24,0,'0005',1,'info',2,'operate',0,'','2012-02-28 13:58:19'),(12,2012,1,28,1,'','','','201','宜通世纪项目管理平台','20100402','[合同管理服务].[查询]','2012-02-28 13:58:19','2012-02-28 13:58:19',0,18,0,'0005',1,'info',2,'operate',0,'','2012-02-28 13:58:19'),(13,2012,1,28,1,'','','','201','宜通世纪项目管理平台','20100406','[合同管理服务].[导出EXCEL]','2012-02-28 13:58:19','2012-02-28 13:58:19',0,43,0,'0005',1,'info',2,'operate',0,'','2012-02-28 13:58:19'),(14,2012,1,28,1,'','','','201','宜通世纪项目管理平台','20100407','[合同管理服务].[导入EXCEL]','2012-02-28 13:58:19','2012-02-28 13:58:19',0,14,0,'0023',3,'error',0,'system',1,'','2012-02-28 13:58:19'),(15,2012,1,28,1,'','','','100','基础服务','10000001','[系统登录服务].[登录系统]','2012-02-28 13:58:19','2012-02-28 13:58:19',0,10,0,'0001',3,'error',1,'security',1,'','2012-02-28 13:58:19'),(16,2012,1,28,1,'','','','201','宜通世纪项目管理平台','20100502','[合同实际开票管理服务].[查询]','2012-02-28 13:58:19','2012-02-28 13:58:19',0,32,0,'0005',1,'info',2,'operate',0,'','2012-02-28 13:58:19'),(17,2012,1,28,1,'','','','201','宜通世纪项目管理平台','20100503','[合同实际开票管理服务].[新增记录]','2012-02-28 13:58:19','2012-02-28 13:58:19',0,38,0,'0023',3,'error',0,'system',1,'','2012-02-28 13:58:19'),(18,2012,1,28,1,'','','','201','宜通世纪项目管理平台','20100502','[合同实际开票管理服务].[查询]','2012-02-28 13:58:19','2012-02-28 13:58:19',0,35,0,'0005',1,'info',2,'operate',0,'','2012-02-28 13:58:19'),(19,2012,1,28,1,'','','','201','宜通世纪项目管理平台','20100502','[合同实际开票管理服务].[查询]','2012-02-28 13:58:19','2012-02-28 13:58:19',0,14,0,'0005',1,'info',2,'operate',0,'','2012-02-28 13:58:19'),(20,2012,1,28,1,'','','','201','宜通世纪项目管理平台','20100506','[合同实际开票管理服务].[导出EXCEL]','2012-02-28 13:58:19','2012-02-28 13:58:19',0,35,0,'0005',1,'info',2,'operate',0,'','2012-02-28 13:58:19'),(21,2012,1,28,1,'','','','201','宜通世纪项目管理平台','20100507','[合同实际开票管理服务].[导入EXCEL]','2012-02-28 13:58:19','2012-02-28 13:58:19',0,15,0,'0023',3,'error',0,'system',1,'','2012-02-28 13:58:19'),(22,2012,1,28,1,'','','','100','基础服务','10000001','[系统登录服务].[登录系统]','2012-02-28 13:58:20','2012-02-28 13:58:20',0,32,0,'0001',3,'error',1,'security',1,'','2012-02-28 13:58:20'),(23,2012,1,28,1,'','','','201','宜通世纪项目管理平台','20100602','[合同计划开票管理服务].[查询]','2012-02-28 13:58:20','2012-02-28 13:58:20',0,52,0,'0005',1,'info',2,'operate',0,'','2012-02-28 13:58:20'),(24,2012,1,28,1,'','','','201','宜通世纪项目管理平台','20100603','[合同计划开票管理服务].[新增记录]','2012-02-28 13:58:20','2012-02-28 13:58:20',0,27,0,'0023',3,'error',0,'system',1,'','2012-02-28 13:58:20'),(25,2012,1,28,1,'','','','201','宜通世纪项目管理平台','20100602','[合同计划开票管理服务].[查询]','2012-02-28 13:58:20','2012-02-28 13:58:20',0,21,0,'0005',1,'info',2,'operate',0,'','2012-02-28 13:58:20'),(26,2012,1,28,1,'','','','201','宜通世纪项目管理平台','20100602','[合同计划开票管理服务].[查询]','2012-02-28 13:58:20','2012-02-28 13:58:20',0,18,0,'0005',1,'info',2,'operate',0,'','2012-02-28 13:58:20'),(27,2012,1,28,1,'','','','201','宜通世纪项目管理平台','20100606','[合同计划开票管理服务].[导出EXCEL]','2012-02-28 13:58:20','2012-02-28 13:58:20',0,43,0,'0005',1,'info',2,'operate',0,'','2012-02-28 13:58:20'),(28,2012,1,28,1,'','','','201','宜通世纪项目管理平台','20100607','[合同计划开票管理服务].[导入EXCEL]','2012-02-28 13:58:20','2012-02-28 13:58:20',0,12,0,'0023',3,'error',0,'system',1,'','2012-02-28 13:58:20'),(29,2012,1,28,1,'','','','201','宜通世纪项目管理平台','20100603','[合同计划开票管理服务].[新增记录]','2012-02-28 13:58:20','2012-02-28 13:58:20',0,19,0,'0023',3,'error',0,'system',1,'','2012-02-28 13:58:20'),(30,2012,1,28,1,'','','','201','宜通世纪项目管理平台','20100604','[合同计划开票管理服务].[更新记录]','2012-02-28 13:58:20','2012-02-28 13:58:20',0,42,0,'0023',3,'error',0,'system',1,'','2012-02-28 13:58:20'),(31,2012,1,28,1,'','','','100','基础服务','10000001','[系统登录服务].[登录系统]','2012-02-28 13:58:54','2012-02-28 13:58:54',0,9,0,'0001',3,'error',1,'security',1,'','2012-02-28 13:58:54'),(32,2012,1,28,1,'','','','201','宜通世纪项目管理平台','20100102','[项目管理服务].[查询]','2012-02-28 13:58:54','2012-02-28 13:58:54',0,39,0,'0005',1,'info',2,'operate',0,'','2012-02-28 13:58:54'),(33,2012,1,28,1,'','','','100','基础服务','10000001','[系统登录服务].[登录系统]','2012-02-28 13:59:12','2012-02-28 13:59:12',0,8,0,'0001',3,'error',1,'security',1,'','2012-02-28 13:59:12'),(34,2012,1,28,1,'','','','201','宜通世纪项目管理平台','20100102','[项目管理服务].[查询]','2012-02-28 13:59:12','2012-02-28 13:59:12',0,24,0,'0005',1,'info',2,'operate',0,'','2012-02-28 13:59:12'),(35,2012,1,28,1,'','','','201','宜通世纪项目管理平台','20100103','[项目管理服务].[新增记录]','2012-02-28 13:59:12','2012-02-28 13:59:12',0,24,0,'0005',1,'info',2,'operate',0,'','2012-02-28 13:59:12'),(36,2012,1,28,1,'','','','201','宜通世纪项目管理平台','20100102','[项目管理服务].[查询]','2012-02-28 13:59:12','2012-02-28 13:59:12',0,285,0,'0005',1,'info',2,'operate',0,'','2012-02-28 13:59:12'),(37,2012,1,28,1,'','','','201','宜通世纪项目管理平台','20100102','[项目管理服务].[查询]','2012-02-28 13:59:12','2012-02-28 13:59:12',0,26,0,'0005',1,'info',2,'operate',0,'','2012-02-28 13:59:12'),(38,2012,1,28,1,'','','','201','宜通世纪项目管理平台','20100104','[项目管理服务].[更新记录]','2012-02-28 13:59:12','2012-02-28 13:59:12',0,20,0,'0005',1,'info',2,'operate',0,'','2012-02-28 13:59:12'),(39,2012,1,28,1,'','','','201','宜通世纪项目管理平台','20100102','[项目管理服务].[查询]','2012-02-28 13:59:13','2012-02-28 13:59:13',0,37,0,'0005',1,'info',2,'operate',0,'','2012-02-28 13:59:13'),(40,2012,1,28,1,'','','','201','宜通世纪项目管理平台','20100105','[项目管理服务].[删除记录]','2012-02-28 13:59:13','2012-02-28 13:59:13',0,91,0,'0005',1,'info',2,'operate',0,'','2012-02-28 13:59:13'),(41,2012,1,28,1,'','','','201','宜通世纪项目管理平台','20100106','[项目管理服务].[导出EXCEL]','2012-02-28 13:59:13','2012-02-28 13:59:13',0,50,0,'0005',1,'info',2,'operate',0,'','2012-02-28 13:59:13'),(42,2012,1,28,1,'','','','201','宜通世纪项目管理平台','20100107','[项目管理服务].[导入EXCEL]','2012-02-28 13:59:13','2012-02-28 13:59:13',0,13,0,'0023',3,'error',0,'system',1,'','2012-02-28 13:59:13'),(43,2012,1,28,1,'','','','100','基础服务','10000001','[系统登录服务].[登录系统]','2012-02-28 13:59:13','2012-02-28 13:59:13',0,7,0,'0001',3,'error',1,'security',1,'','2012-02-28 13:59:13'),(44,2012,1,28,1,'','','','201','宜通世纪项目管理平台','20100302','[外包合同管理服务].[查询]','2012-02-28 13:59:13','2012-02-28 13:59:13',0,17,0,'0005',1,'info',2,'operate',0,'','2012-02-28 13:59:13'),(45,2012,1,28,1,'','','','201','宜通世纪项目管理平台','20100303','[外包合同管理服务].[新增记录]','2012-02-28 13:59:13','2012-02-28 13:59:13',0,20,0,'0023',3,'error',0,'system',1,'','2012-02-28 13:59:13'),(46,2012,1,28,1,'','','','201','宜通世纪项目管理平台','20100302','[外包合同管理服务].[查询]','2012-02-28 13:59:13','2012-02-28 13:59:13',0,12,0,'0005',1,'info',2,'operate',0,'','2012-02-28 13:59:13'),(47,2012,1,28,1,'','','','201','宜通世纪项目管理平台','20100302','[外包合同管理服务].[查询]','2012-02-28 13:59:13','2012-02-28 13:59:13',0,13,0,'0005',1,'info',2,'operate',0,'','2012-02-28 13:59:13'),(48,2012,1,28,1,'','','','201','宜通世纪项目管理平台','20100306','[外包合同管理服务].[导出EXCEL]','2012-02-28 13:59:13','2012-02-28 13:59:13',0,25,0,'0005',1,'info',2,'operate',0,'','2012-02-28 13:59:13'),(49,2012,1,28,1,'','','','201','宜通世纪项目管理平台','20100307','[外包合同管理服务].[导入EXCEL]','2012-02-28 13:59:13','2012-02-28 13:59:13',0,13,0,'0023',3,'error',0,'system',1,'','2012-02-28 13:59:13'),(50,2012,1,28,1,'','','','100','基础服务','10000001','[系统登录服务].[登录系统]','2012-02-28 13:59:13','2012-02-28 13:59:13',0,11,0,'0001',3,'error',1,'security',1,'','2012-02-28 13:59:13'),(51,2012,1,28,1,'','','','201','宜通世纪项目管理平台','20100402','[合同管理服务].[查询]','2012-02-28 13:59:13','2012-02-28 13:59:13',0,27,0,'0005',1,'info',2,'operate',0,'','2012-02-28 13:59:13'),(52,2012,1,28,1,'','','','201','宜通世纪项目管理平台','20100403','[合同管理服务].[新增记录]','2012-02-28 13:59:13','2012-02-28 13:59:13',0,20,0,'0023',3,'error',0,'system',1,'','2012-02-28 13:59:13'),(53,2012,1,28,1,'','','','201','宜通世纪项目管理平台','20100402','[合同管理服务].[查询]','2012-02-28 13:59:13','2012-02-28 13:59:13',0,20,0,'0005',1,'info',2,'operate',0,'','2012-02-28 13:59:13'),(54,2012,1,28,1,'','','','201','宜通世纪项目管理平台','20100402','[合同管理服务].[查询]','2012-02-28 13:59:13','2012-02-28 13:59:13',0,18,0,'0005',1,'info',2,'operate',0,'','2012-02-28 13:59:13'),(55,2012,1,28,1,'','','','201','宜通世纪项目管理平台','20100406','[合同管理服务].[导出EXCEL]','2012-02-28 13:59:13','2012-02-28 13:59:13',0,38,0,'0005',1,'info',2,'operate',0,'','2012-02-28 13:59:13'),(56,2012,1,28,1,'','','','201','宜通世纪项目管理平台','20100407','[合同管理服务].[导入EXCEL]','2012-02-28 13:59:13','2012-02-28 13:59:13',0,13,0,'0023',3,'error',0,'system',1,'','2012-02-28 13:59:13'),(57,2012,1,28,1,'','','','100','基础服务','10000001','[系统登录服务].[登录系统]','2012-02-28 13:59:14','2012-02-28 13:59:14',0,10,0,'0001',3,'error',1,'security',1,'','2012-02-28 13:59:14'),(58,2012,1,28,1,'','','','201','宜通世纪项目管理平台','20100502','[合同实际开票管理服务].[查询]','2012-02-28 13:59:14','2012-02-28 13:59:14',0,18,0,'0005',1,'info',2,'operate',0,'','2012-02-28 13:59:14'),(59,2012,1,28,1,'','','','201','宜通世纪项目管理平台','20100503','[合同实际开票管理服务].[新增记录]','2012-02-28 13:59:14','2012-02-28 13:59:14',0,20,0,'0023',3,'error',0,'system',1,'','2012-02-28 13:59:14'),(60,2012,1,28,1,'','','','201','宜通世纪项目管理平台','20100502','[合同实际开票管理服务].[查询]','2012-02-28 13:59:14','2012-02-28 13:59:14',0,16,0,'0005',1,'info',2,'operate',0,'','2012-02-28 13:59:14'),(61,2012,1,28,1,'','','','201','宜通世纪项目管理平台','20100502','[合同实际开票管理服务].[查询]','2012-02-28 13:59:14','2012-02-28 13:59:14',0,19,0,'0005',1,'info',2,'operate',0,'','2012-02-28 13:59:14'),(62,2012,1,28,1,'','','','201','宜通世纪项目管理平台','20100506','[合同实际开票管理服务].[导出EXCEL]','2012-02-28 13:59:14','2012-02-28 13:59:14',0,45,0,'0005',1,'info',2,'operate',0,'','2012-02-28 13:59:14'),(63,2012,1,28,1,'','','','201','宜通世纪项目管理平台','20100507','[合同实际开票管理服务].[导入EXCEL]','2012-02-28 13:59:14','2012-02-28 13:59:14',0,14,0,'0023',3,'error',0,'system',1,'','2012-02-28 13:59:14'),(64,2012,1,28,1,'','','','100','基础服务','10000001','[系统登录服务].[登录系统]','2012-02-28 13:59:14','2012-02-28 13:59:14',0,9,0,'0001',3,'error',1,'security',1,'','2012-02-28 13:59:14'),(65,2012,1,28,1,'','','','201','宜通世纪项目管理平台','20100602','[合同计划开票管理服务].[查询]','2012-02-28 13:59:14','2012-02-28 13:59:14',0,40,0,'0005',1,'info',2,'operate',0,'','2012-02-28 13:59:14'),(66,2012,1,28,1,'','','','201','宜通世纪项目管理平台','20100603','[合同计划开票管理服务].[新增记录]','2012-02-28 13:59:14','2012-02-28 13:59:14',0,17,0,'0023',3,'error',0,'system',1,'','2012-02-28 13:59:14'),(67,2012,1,28,1,'','','','201','宜通世纪项目管理平台','20100602','[合同计划开票管理服务].[查询]','2012-02-28 13:59:14','2012-02-28 13:59:14',0,12,0,'0005',1,'info',2,'operate',0,'','2012-02-28 13:59:14'),(68,2012,1,28,1,'','','','201','宜通世纪项目管理平台','20100602','[合同计划开票管理服务].[查询]','2012-02-28 13:59:14','2012-02-28 13:59:14',0,15,0,'0005',1,'info',2,'operate',0,'','2012-02-28 13:59:14'),(69,2012,1,28,1,'','','','201','宜通世纪项目管理平台','20100606','[合同计划开票管理服务].[导出EXCEL]','2012-02-28 13:59:14','2012-02-28 13:59:14',0,30,0,'0005',1,'info',2,'operate',0,'','2012-02-28 13:59:14'),(70,2012,1,28,1,'','','','201','宜通世纪项目管理平台','20100607','[合同计划开票管理服务].[导入EXCEL]','2012-02-28 13:59:14','2012-02-28 13:59:14',0,21,0,'0023',3,'error',0,'system',1,'','2012-02-28 13:59:14'),(71,2012,1,28,1,'','','','201','宜通世纪项目管理平台','20100603','[合同计划开票管理服务].[新增记录]','2012-02-28 13:59:14','2012-02-28 13:59:14',0,22,0,'0023',3,'error',0,'system',1,'','2012-02-28 13:59:14'),(72,2012,1,28,1,'','','','201','宜通世纪项目管理平台','20100604','[合同计划开票管理服务].[更新记录]','2012-02-28 13:59:14','2012-02-28 13:59:14',0,19,0,'0023',3,'error',0,'system',1,'','2012-02-28 13:59:14'),(73,2012,1,28,2,'127.0.0.1','127.0.0.1','admin','100','基础服务','10000001','[系统登录服务].[登录系统]','2012-02-28 14:26:03','2012-02-28 14:26:03',0,20,0,'0005',1,'info',2,'operate',0,'','2012-02-28 14:26:03'),(74,2012,1,28,2,'127.0.0.1','127.0.0.1','admin','201','宜通世纪项目管理平台','20100103','[项目管理服务].[新增记录]','2012-02-28 14:26:03','2012-02-28 14:26:03',0,15,0,'0005',1,'info',2,'operate',0,'','2012-02-28 14:26:03'),(75,2012,1,28,2,'127.0.0.1','127.0.0.1','admin','201','宜通世纪项目管理平台','20100102','[项目管理服务].[查询]','2012-02-28 14:26:03','2012-02-28 14:26:03',0,13,0,'0005',1,'info',2,'operate',0,'','2012-02-28 14:26:03'),(76,2012,1,28,2,'127.0.0.1','127.0.0.1','admin','100','基础服务','10000001','[系统登录服务].[登录系统]','2012-02-28 14:46:57','2012-02-28 14:46:57',0,14,0,'0005',1,'info',2,'operate',0,'','2012-02-28 14:46:57'),(77,2012,1,28,2,'127.0.0.1','127.0.0.1','admin','201','宜通世纪项目管理平台','20100103','[项目管理服务].[新增记录]','2012-02-28 14:46:57','2012-02-28 14:46:57',0,18,0,'0005',1,'info',2,'operate',0,'','2012-02-28 14:46:57'),(78,2012,1,28,2,'127.0.0.1','127.0.0.1','admin','201','宜通世纪项目管理平台','20100102','[项目管理服务].[查询]','2012-02-28 14:46:57','2012-02-28 14:46:57',0,34,0,'0005',1,'info',2,'operate',0,'','2012-02-28 14:46:57'),(79,2012,1,28,2,'127.0.0.1','127.0.0.1','admin','100','基础服务','10000001','[系统登录服务].[登录系统]','2012-02-28 14:48:23','2012-02-28 14:48:23',0,13,0,'0005',1,'info',2,'operate',0,'','2012-02-28 14:48:23'),(80,2012,1,28,2,'127.0.0.1','127.0.0.1','admin','201','宜通世纪项目管理平台','20100103','[项目管理服务].[新增记录]','2012-02-28 14:48:23','2012-02-28 14:48:23',0,14,0,'0005',1,'info',2,'operate',0,'','2012-02-28 14:48:23'),(81,2012,1,28,2,'127.0.0.1','127.0.0.1','admin','201','宜通世纪项目管理平台','20100102','[项目管理服务].[查询]','2012-02-28 14:48:23','2012-02-28 14:48:23',0,26,0,'0005',1,'info',2,'operate',0,'','2012-02-28 14:48:23'),(82,2012,1,28,2,'127.0.0.1','127.0.0.1','admin','100','基础服务','10000001','[系统登录服务].[登录系统]','2012-02-28 14:50:49','2012-02-28 14:50:49',0,12,0,'0005',1,'info',2,'operate',0,'','2012-02-28 14:50:49'),(83,2012,1,28,2,'127.0.0.1','127.0.0.1','admin','201','宜通世纪项目管理平台','20100102','[项目管理服务].[查询]','2012-02-28 14:50:49','2012-02-28 14:50:49',0,41,0,'0005',1,'info',2,'operate',0,'','2012-02-28 14:50:49'),(84,2012,1,28,2,'127.0.0.1','127.0.0.1','admin','100','基础服务','10000001','[系统登录服务].[登录系统]','2012-02-28 14:51:26','2012-02-28 14:51:26',0,14,0,'0005',1,'info',2,'operate',0,'','2012-02-28 14:51:26'),(85,2012,1,28,2,'127.0.0.1','127.0.0.1','admin','201','宜通世纪项目管理平台','20100102','[项目管理服务].[查询]','2012-02-28 14:51:26','2012-02-28 14:51:26',0,49,0,'0005',1,'info',2,'operate',0,'','2012-02-28 14:51:26'),(86,2012,1,28,3,'','','','100','基础服务','10000001','[系统登录服务].[登录系统]','2012-02-28 15:18:37','2012-02-28 15:18:37',0,16,0,'0001',3,'error',1,'security',1,'','2012-02-28 15:18:37'),(87,2012,1,28,3,'','','','201','宜通世纪项目管理平台','20100403','[合同管理服务].[新增记录]','2012-02-28 15:18:37','2012-02-28 15:18:37',0,24,0,'0023',3,'error',0,'system',1,'','2012-02-28 15:18:37'),(88,2012,1,28,3,'','','','100','基础服务','10000001','[系统登录服务].[登录系统]','2012-02-28 15:21:18','2012-02-28 15:21:18',0,8,0,'0001',3,'error',1,'security',1,'','2012-02-28 15:21:18'),(89,2012,1,28,3,'','','','201','宜通世纪项目管理平台','20100403','[合同管理服务].[新增记录]','2012-02-28 15:21:18','2012-02-28 15:21:18',0,15,0,'0005',1,'info',2,'operate',0,'','2012-02-28 15:21:18'),(90,2012,1,28,3,'','','','100','基础服务','10000001','[系统登录服务].[登录系统]','2012-02-28 15:23:11','2012-02-28 15:23:11',0,7,0,'0001',3,'error',1,'security',1,'','2012-02-28 15:23:11'),(91,2012,1,28,3,'','','','201','宜通世纪项目管理平台','20100603','[合同计划开票管理服务].[新增记录]','2012-02-28 15:23:11','2012-02-28 15:23:11',0,18,0,'0023',3,'error',0,'system',1,'','2012-02-28 15:23:11'),(92,2012,1,28,3,'','','','100','基础服务','10000001','[系统登录服务].[登录系统]','2012-02-28 15:24:07','2012-02-28 15:24:07',0,9,0,'0001',3,'error',1,'security',1,'','2012-02-28 15:24:07'),(93,2012,1,28,3,'','','','201','宜通世纪项目管理平台','20100603','[合同计划开票管理服务].[新增记录]','2012-02-28 15:24:07','2012-02-28 15:24:07',0,17,0,'0005',1,'info',2,'operate',0,'','2012-02-28 15:24:07'),(94,2012,1,28,3,'','','','100','基础服务','10000001','[系统登录服务].[登录系统]','2012-02-28 15:26:25','2012-02-28 15:26:25',0,8,0,'0001',3,'error',1,'security',1,'','2012-02-28 15:26:25'),(95,2012,1,28,3,'','','','201','宜通世纪项目管理平台','20100503','[合同实际开票管理服务].[新增记录]','2012-02-28 15:26:25','2012-02-28 15:26:25',0,16,0,'0005',1,'info',2,'operate',0,'','2012-02-28 15:26:25'),(96,2012,1,28,3,'127.0.0.1','127.0.0.1','admin','100','基础服务','10000001','[系统登录服务].[登录系统]','2012-02-28 15:26:53','2012-02-28 15:26:53',0,13,0,'0005',1,'info',2,'operate',0,'','2012-02-28 15:26:53'),(97,2012,1,28,3,'127.0.0.1','127.0.0.1','admin','201','宜通世纪项目管理平台','20100102','[项目管理服务].[查询]','2012-02-28 15:26:53','2012-02-28 15:26:53',0,60,0,'0005',1,'info',2,'operate',0,'','2012-02-28 15:26:53'),(98,2012,1,28,3,'127.0.0.1','127.0.0.1','admin','100','基础服务','10000001','[系统登录服务].[登录系统]','2012-02-28 15:28:30','2012-02-28 15:28:30',0,12,0,'0005',1,'info',2,'operate',0,'','2012-02-28 15:28:30'),(99,2012,1,28,3,'127.0.0.1','127.0.0.1','admin','201','宜通世纪项目管理平台','20100102','[项目管理服务].[查询]','2012-02-28 15:28:30','2012-02-28 15:28:30',0,39,0,'0023',3,'error',0,'system',1,'','2012-02-28 15:28:30'),(100,2012,1,28,3,'127.0.0.1','127.0.0.1','admin','100','基础服务','10000001','[系统登录服务].[登录系统]','2012-02-28 15:28:46','2012-02-28 15:28:46',0,12,0,'0005',1,'info',2,'operate',0,'','2012-02-28 15:28:46'),(101,2012,1,28,3,'127.0.0.1','127.0.0.1','admin','201','宜通世纪项目管理平台','20100102','[项目管理服务].[查询]','2012-02-28 15:28:47','2012-02-28 15:28:47',0,34,0,'0005',1,'info',2,'operate',0,'','2012-02-28 15:28:47'),(102,2012,1,28,3,'127.0.0.1','127.0.0.1','admin','100','基础服务','10000001','[系统登录服务].[登录系统]','2012-02-28 15:29:07','2012-02-28 15:29:07',0,14,0,'0005',1,'info',2,'operate',0,'','2012-02-28 15:29:07'),(103,2012,1,28,3,'127.0.0.1','127.0.0.1','admin','201','宜通世纪项目管理平台','20100102','[项目管理服务].[查询]','2012-02-28 15:29:08','2012-02-28 15:29:08',0,65,0,'0023',3,'error',0,'system',1,'','2012-02-28 15:29:08'),(104,2012,1,28,3,'127.0.0.1','127.0.0.1','admin','100','基础服务','10000001','[系统登录服务].[登录系统]','2012-02-28 15:34:17','2012-02-28 15:34:17',0,11,0,'0005',1,'info',2,'operate',0,'','2012-02-28 15:34:17'),(105,2012,1,28,3,'127.0.0.1','127.0.0.1','admin','201','宜通世纪项目管理平台','20100102','[项目管理服务].[查询]','2012-02-28 15:35:30','2012-02-28 15:35:30',0,73807,0,'0023',3,'error',0,'system',1,'','2012-02-28 15:35:30'),(106,2012,1,28,3,'127.0.0.1','127.0.0.1','admin','100','基础服务','10000001','[系统登录服务].[登录系统]','2012-02-28 15:35:39','2012-02-28 15:35:39',0,12,0,'0005',1,'info',2,'operate',0,'','2012-02-28 15:35:39'),(107,2012,1,28,3,'127.0.0.1','127.0.0.1','admin','201','宜通世纪项目管理平台','20100102','[项目管理服务].[查询]','2012-02-28 15:38:29','2012-02-28 15:38:29',0,170022,0,'0023',3,'error',0,'system',1,'','2012-02-28 15:38:29'),(108,2012,1,28,3,'127.0.0.1','127.0.0.1','admin','100','基础服务','10000001','[系统登录服务].[登录系统]','2012-02-28 15:43:07','2012-02-28 15:43:07',0,104,0,'0999',3,'error',2,'operate',1,'','2012-02-28 15:43:07'),(109,2012,1,28,3,'127.0.0.1','127.0.0.1','admin','201','宜通世纪项目管理平台','20100102','[项目管理服务].[查询]','2012-02-28 15:43:12','2012-02-28 15:43:12',0,4367,0,'0005',1,'info',2,'operate',0,'','2012-02-28 15:43:12'),(110,2012,2,7,4,'','','','201','宜通世纪项目管理平台','20100102','[项目管理服务].[查询]','2012-03-07 16:25:12','2012-03-07 16:25:12',0,1011,0,'0999',3,'error',2,'operate',1,'','2012-03-07 16:25:12'),(111,2012,2,7,4,'','','','201','宜通世纪项目管理平台','20100402','[合同管理服务].[查询]','2012-03-07 16:25:18','2012-03-07 16:25:18',0,215,0,'0005',1,'info',2,'operate',0,'','2012-03-07 16:25:18'),(112,2012,2,7,4,'','','','201','宜通世纪项目管理平台','20100402','[合同管理服务].[查询]','2012-03-07 16:25:19','2012-03-07 16:25:19',0,343,0,'0005',1,'info',2,'operate',0,'','2012-03-07 16:25:19'),(113,2012,2,7,4,'','','','201','宜通世纪项目管理平台','20100602','[合同计划开票管理服务].[查询]','2012-03-07 16:25:21','2012-03-07 16:25:21',0,167,0,'0005',1,'info',2,'operate',0,'','2012-03-07 16:25:21'),(114,2012,2,7,4,'','','','201','宜通世纪项目管理平台','20100502','[合同实际开票管理服务].[查询]','2012-03-07 16:25:21','2012-03-07 16:25:21',0,316,0,'0023',3,'error',0,'system',1,'','2012-03-07 16:25:21'),(115,2012,2,7,4,'','','','201','宜通世纪项目管理平台','20100404','[合同管理服务].[更新记录]','2012-03-07 16:25:29','2012-03-07 16:25:29',0,112,0,'0005',1,'info',2,'operate',0,'','2012-03-07 16:25:29'),(116,2012,2,7,4,'','','','201','宜通世纪项目管理平台','20100102','[项目管理服务].[查询]','2012-03-07 16:25:56','2012-03-07 16:25:56',0,442,0,'0005',1,'info',2,'operate',0,'','2012-03-07 16:25:56'),(117,2012,2,7,4,'','','','201','宜通世纪项目管理平台','20100402','[合同管理服务].[查询]','2012-03-07 16:25:59','2012-03-07 16:25:59',0,145,0,'0005',1,'info',2,'operate',0,'','2012-03-07 16:25:59'),(118,2012,2,7,4,'','','','201','宜通世纪项目管理平台','20100402','[合同管理服务].[查询]','2012-03-07 16:25:59','2012-03-07 16:25:59',0,215,0,'0005',1,'info',2,'operate',0,'','2012-03-07 16:25:59'),(119,2012,2,7,4,'','','','201','宜通世纪项目管理平台','20100602','[合同计划开票管理服务].[查询]','2012-03-07 16:26:01','2012-03-07 16:26:01',0,103,0,'0005',1,'info',2,'operate',0,'','2012-03-07 16:26:01'),(120,2012,2,7,4,'','','','201','宜通世纪项目管理平台','20100502','[合同实际开票管理服务].[查询]','2012-03-07 16:26:01','2012-03-07 16:26:01',0,186,0,'0023',3,'error',0,'system',1,'','2012-03-07 16:26:01'),(121,2012,2,7,4,'','','','201','宜通世纪项目管理平台','20100102','[项目管理服务].[查询]','2012-03-07 16:30:28','2012-03-07 16:30:28',0,423,0,'0005',1,'info',2,'operate',0,'','2012-03-07 16:30:28'),(122,2012,2,7,4,'','','','201','宜通世纪项目管理平台','20100402','[合同管理服务].[查询]','2012-03-07 16:30:32','2012-03-07 16:30:32',0,267,0,'0005',1,'info',2,'operate',0,'','2012-03-07 16:30:32'),(123,2012,2,7,4,'','','','201','宜通世纪项目管理平台','20100302','[外包合同管理服务].[查询]','2012-03-07 16:30:32','2012-03-07 16:30:32',0,289,0,'0005',1,'info',2,'operate',0,'','2012-03-07 16:30:32'),(124,2012,2,7,4,'','','','201','宜通世纪项目管理平台','20101302','[项目工作量信息服务].[查询]','2012-03-07 16:30:32','2012-03-07 16:30:32',0,376,0,'0005',1,'info',2,'operate',0,'','2012-03-07 16:30:32'),(125,2012,2,7,4,'','','','201','宜通世纪项目管理平台','20100102','[项目管理服务].[查询]','2012-03-07 16:33:11','2012-03-07 16:33:11',0,456,0,'0005',1,'info',2,'operate',0,'','2012-03-07 16:33:11'),(126,2012,2,7,4,'','','','201','宜通世纪项目管理平台','20101302','[项目工作量信息服务].[查询]','2012-03-07 16:33:14','2012-03-07 16:33:14',0,20,0,'0023',3,'error',0,'system',1,'','2012-03-07 16:33:14'),(127,2012,2,7,4,'','','','201','宜通世纪项目管理平台','20100402','[合同管理服务].[查询]','2012-03-07 16:33:14','2012-03-07 16:33:14',0,122,0,'0005',1,'info',2,'operate',0,'','2012-03-07 16:33:14'),(128,2012,2,7,4,'','','','201','宜通世纪项目管理平台','20100302','[外包合同管理服务].[查询]','2012-03-07 16:33:15','2012-03-07 16:33:15',0,190,0,'0005',1,'info',2,'operate',0,'','2012-03-07 16:33:15'),(129,2012,2,7,4,'','','','201','宜通世纪项目管理平台','20100102','[项目管理服务].[查询]','2012-03-07 16:39:10','2012-03-07 16:39:10',0,392,0,'0005',1,'info',2,'operate',0,'','2012-03-07 16:39:10'),(130,2012,2,7,4,'','','','201','宜通世纪项目管理平台','20100302','[外包合同管理服务].[查询]','2012-03-07 16:39:31','2012-03-07 16:39:31',0,15,0,'0023',3,'error',0,'system',1,'','2012-03-07 16:39:31'),(131,2012,2,7,4,'','','','201','宜通世纪项目管理平台','20100402','[合同管理服务].[查询]','2012-03-07 16:39:31','2012-03-07 16:39:31',0,108,0,'0005',1,'info',2,'operate',0,'','2012-03-07 16:39:31'),(132,2012,2,7,4,'','','','201','宜通世纪项目管理平台','20101302','[项目工作量信息服务].[查询]','2012-03-07 16:39:31','2012-03-07 16:39:31',0,153,0,'0005',1,'info',2,'operate',0,'','2012-03-07 16:39:31'),(133,2012,2,7,4,'','','','201','宜通世纪项目管理平台','20101302','[项目工作量信息服务].[查询]','2012-03-07 16:43:33','2012-03-07 16:43:33',0,134,0,'0005',1,'info',2,'operate',0,'','2012-03-07 16:43:33'),(134,2012,2,7,4,'','','','201','宜通世纪项目管理平台','20100302','[外包合同管理服务].[查询]','2012-03-07 16:43:33','2012-03-07 16:43:33',0,187,0,'0005',1,'info',2,'operate',0,'','2012-03-07 16:43:33'),(135,2012,2,7,4,'','','','201','宜通世纪项目管理平台','20100402','[合同管理服务].[查询]','2012-03-07 16:43:36','2012-03-07 16:43:36',0,103,0,'0005',1,'info',2,'operate',0,'','2012-03-07 16:43:36'),(136,2012,2,7,4,'','','','201','宜通世纪项目管理平台','20100102','[项目管理服务].[查询]','2012-03-07 16:45:51','2012-03-07 16:45:51',0,550,0,'0005',1,'info',2,'operate',0,'','2012-03-07 16:45:51'),(137,2012,2,7,4,'','','','201','宜通世纪项目管理平台','20100302','[外包合同管理服务].[查询]','2012-03-07 16:46:10','2012-03-07 16:46:10',0,100,0,'0005',1,'info',2,'operate',0,'','2012-03-07 16:46:10'),(138,2012,2,7,4,'','','','201','宜通世纪项目管理平台','20101302','[项目工作量信息服务].[查询]','2012-03-07 16:46:10','2012-03-07 16:46:10',0,168,0,'0005',1,'info',2,'operate',0,'','2012-03-07 16:46:10'),(139,2012,2,7,4,'','','','201','宜通世纪项目管理平台','20100402','[合同管理服务].[查询]','2012-03-07 16:46:10','2012-03-07 16:46:10',0,397,0,'0005',1,'info',2,'operate',0,'','2012-03-07 16:46:10'),(140,2012,2,7,4,'','','','201','宜通世纪项目管理平台','20100103','[项目管理服务].[新增记录]','2012-03-07 16:48:14','2012-03-07 16:48:14',0,100,0,'0005',1,'info',2,'operate',0,'','2012-03-07 16:48:14'),(141,2012,2,7,4,'','','','201','宜通世纪项目管理平台','20100102','[项目管理服务].[查询]','2012-03-07 16:48:15','2012-03-07 16:48:15',0,584,0,'0005',1,'info',2,'operate',0,'','2012-03-07 16:48:15'),(142,2012,2,7,4,'','','','201','宜通世纪项目管理平台','20100102','[项目管理服务].[查询]','2012-03-07 16:48:21','2012-03-07 16:48:21',0,568,0,'0005',1,'info',2,'operate',0,'','2012-03-07 16:48:21'),(143,2012,2,7,4,'','','','201','宜通世纪项目管理平台','20100102','[项目管理服务].[查询]','2012-03-07 16:48:22','2012-03-07 16:48:22',0,649,0,'0005',1,'info',2,'operate',0,'','2012-03-07 16:48:22'),(144,2012,2,7,4,'','','','201','宜通世纪项目管理平台','20100102','[项目管理服务].[查询]','2012-03-07 16:48:23','2012-03-07 16:48:23',0,1265,0,'0005',1,'info',2,'operate',0,'','2012-03-07 16:48:23'),(145,2012,2,7,4,'','','','201','宜通世纪项目管理平台','20100102','[项目管理服务].[查询]','2012-03-07 16:48:23','2012-03-07 16:48:23',0,1105,0,'0005',1,'info',2,'operate',0,'','2012-03-07 16:48:23'),(146,2012,2,7,4,'','','','201','宜通世纪项目管理平台','20100302','[外包合同管理服务].[查询]','2012-03-07 16:48:54','2012-03-07 16:48:54',0,159,0,'0005',1,'info',2,'operate',0,'','2012-03-07 16:48:54'),(147,2012,2,7,4,'','','','201','宜通世纪项目管理平台','20100402','[合同管理服务].[查询]','2012-03-07 16:48:54','2012-03-07 16:48:54',0,236,0,'0005',1,'info',2,'operate',0,'','2012-03-07 16:48:54'),(148,2012,2,7,4,'','','','201','宜通世纪项目管理平台','20101302','[项目工作量信息服务].[查询]','2012-03-07 16:48:54','2012-03-07 16:48:54',0,122,0,'0005',1,'info',2,'operate',0,'','2012-03-07 16:48:54'),(149,2012,2,7,4,'','','','201','宜通世纪项目管理平台','20100402','[合同管理服务].[查询]','2012-03-07 16:49:03','2012-03-07 16:49:03',0,94,0,'0005',1,'info',2,'operate',0,'','2012-03-07 16:49:03'),(150,2012,2,7,4,'','','','201','宜通世纪项目管理平台','20101302','[项目工作量信息服务].[查询]','2012-03-07 16:49:03','2012-03-07 16:49:03',0,68,0,'0005',1,'info',2,'operate',0,'','2012-03-07 16:49:03'),(151,2012,2,7,4,'','','','201','宜通世纪项目管理平台','20100302','[外包合同管理服务].[查询]','2012-03-07 16:49:03','2012-03-07 16:49:03',0,204,0,'0005',1,'info',2,'operate',0,'','2012-03-07 16:49:03'),(152,2012,2,7,4,'','','','201','宜通世纪项目管理平台','20100103','[项目管理服务].[新增记录]','2012-03-07 16:50:28','2012-03-07 16:50:28',0,50,0,'0999',3,'error',2,'operate',1,'','2012-03-07 16:50:28'),(153,2012,2,7,4,'','','','201','宜通世纪项目管理平台','20100102','[项目管理服务].[查询]','2012-03-07 16:50:38','2012-03-07 16:50:38',0,2237,0,'0005',1,'info',2,'operate',0,'','2012-03-07 16:50:38'),(154,2012,2,7,4,'','','','201','宜通世纪项目管理平台','20100102','[项目管理服务].[查询]','2012-03-07 16:50:38','2012-03-07 16:50:38',0,2262,0,'0005',1,'info',2,'operate',0,'','2012-03-07 16:50:38'),(155,2012,2,7,4,'','','','201','宜通世纪项目管理平台','20100102','[项目管理服务].[查询]','2012-03-07 16:50:39','2012-03-07 16:50:39',0,2298,0,'0005',1,'info',2,'operate',0,'','2012-03-07 16:50:39'),(156,2012,2,7,4,'','','','201','宜通世纪项目管理平台','20100102','[项目管理服务].[查询]','2012-03-07 16:50:39','2012-03-07 16:50:39',0,2524,0,'0005',1,'info',2,'operate',0,'','2012-03-07 16:50:39'),(157,2012,2,7,4,'','','','201','宜通世纪项目管理平台','20100102','[项目管理服务].[查询]','2012-03-07 16:50:39','2012-03-07 16:50:39',0,2924,0,'0005',1,'info',2,'operate',0,'','2012-03-07 16:50:39'),(158,2012,2,7,4,'','','','201','宜通世纪项目管理平台','20100102','[项目管理服务].[查询]','2012-03-07 16:50:47','2012-03-07 16:50:47',0,512,0,'0005',1,'info',2,'operate',0,'','2012-03-07 16:50:47'),(159,2012,2,7,4,'','','','201','宜通世纪项目管理平台','20100402','[合同管理服务].[查询]','2012-03-07 16:50:56','2012-03-07 16:50:56',0,102,0,'0005',1,'info',2,'operate',0,'','2012-03-07 16:50:56'),(160,2012,2,7,4,'','','','201','宜通世纪项目管理平台','20100302','[外包合同管理服务].[查询]','2012-03-07 16:50:56','2012-03-07 16:50:56',0,151,0,'0005',1,'info',2,'operate',0,'','2012-03-07 16:50:56'),(161,2012,2,7,4,'','','','201','宜通世纪项目管理平台','20101302','[项目工作量信息服务].[查询]','2012-03-07 16:50:56','2012-03-07 16:50:56',0,198,0,'0005',1,'info',2,'operate',0,'','2012-03-07 16:50:56'),(162,2012,2,7,4,'','','','201','宜通世纪项目管理平台','20100104','[项目管理服务].[更新记录]','2012-03-07 16:51:18','2012-03-07 16:51:18',0,114,0,'0005',1,'info',2,'operate',0,'','2012-03-07 16:51:18'),(163,2012,2,7,4,'','','','201','宜通世纪项目管理平台','20100402','[合同管理服务].[查询]','2012-03-07 16:51:25','2012-03-07 16:51:25',0,107,0,'0005',1,'info',2,'operate',0,'','2012-03-07 16:51:25'),(164,2012,2,7,4,'','','','201','宜通世纪项目管理平台','20101302','[项目工作量信息服务].[查询]','2012-03-07 16:51:25','2012-03-07 16:51:25',0,129,0,'0005',1,'info',2,'operate',0,'','2012-03-07 16:51:25'),(165,2012,2,7,4,'','','','201','宜通世纪项目管理平台','20100302','[外包合同管理服务].[查询]','2012-03-07 16:51:25','2012-03-07 16:51:25',0,199,0,'0005',1,'info',2,'operate',0,'','2012-03-07 16:51:25'),(166,2012,2,7,4,'','','','201','宜通世纪项目管理平台','20100102','[项目管理服务].[查询]','2012-03-07 16:51:49','2012-03-07 16:51:49',0,553,0,'0005',1,'info',2,'operate',0,'','2012-03-07 16:51:49'),(167,2012,2,7,4,'','','','201','宜通世纪项目管理平台','20100402','[合同管理服务].[查询]','2012-03-07 16:51:52','2012-03-07 16:51:52',0,105,0,'0005',1,'info',2,'operate',0,'','2012-03-07 16:51:52'),(168,2012,2,7,4,'','','','201','宜通世纪项目管理平台','20101302','[项目工作量信息服务].[查询]','2012-03-07 16:51:52','2012-03-07 16:51:52',0,147,0,'0005',1,'info',2,'operate',0,'','2012-03-07 16:51:52'),(169,2012,2,7,4,'','','','201','宜通世纪项目管理平台','20100302','[外包合同管理服务].[查询]','2012-03-07 16:51:52','2012-03-07 16:51:52',0,201,0,'0005',1,'info',2,'operate',0,'','2012-03-07 16:51:52'),(170,2012,2,7,4,'','','','201','宜通世纪项目管理平台','20100102','[项目管理服务].[查询]','2012-03-07 16:52:05','2012-03-07 16:52:05',0,587,0,'0005',1,'info',2,'operate',0,'','2012-03-07 16:52:05'),(171,2012,2,7,4,'','','','201','宜通世纪项目管理平台','20100102','[项目管理服务].[查询]','2012-03-07 16:52:06','2012-03-07 16:52:06',0,1233,0,'0005',1,'info',2,'operate',0,'','2012-03-07 16:52:06'),(172,2012,2,7,4,'','','','201','宜通世纪项目管理平台','20100102','[项目管理服务].[查询]','2012-03-07 16:52:06','2012-03-07 16:52:06',0,1831,0,'0005',1,'info',2,'operate',0,'','2012-03-07 16:52:06'),(173,2012,2,7,4,'','','','201','宜通世纪项目管理平台','20100102','[项目管理服务].[查询]','2012-03-07 16:52:07','2012-03-07 16:52:07',0,1825,0,'0005',1,'info',2,'operate',0,'','2012-03-07 16:52:07'),(174,2012,2,7,4,'','','','201','宜通世纪项目管理平台','20100102','[项目管理服务].[查询]','2012-03-07 16:52:07','2012-03-07 16:52:07',0,1455,0,'0005',1,'info',2,'operate',0,'','2012-03-07 16:52:07'),(175,2012,2,7,4,'','','','201','宜通世纪项目管理平台','20100102','[项目管理服务].[查询]','2012-03-07 16:53:45','2012-03-07 16:53:45',0,565,0,'0005',1,'info',2,'operate',0,'','2012-03-07 16:53:45'),(176,2012,2,20,2,'','','','200','基础数据管理子系统','20000202','[系统用户服务].[查询]','2012-03-20 14:10:38','2012-03-20 14:10:38',0,689,0,'0999',3,'error',2,'operate',1,'','2012-03-20 14:10:38'),(177,2012,2,20,2,'','','','200','基础数据管理子系统','20000202','[系统用户服务].[查询]','2012-03-20 14:11:08','2012-03-20 14:11:08',0,29,0,'0005',1,'info',2,'operate',0,'','2012-03-20 14:11:08'),(178,2012,2,20,2,'','','','200','基础数据管理子系统','20000202','[系统用户服务].[查询]','2012-03-20 14:11:32','2012-03-20 14:11:32',0,24,0,'0005',1,'info',2,'operate',0,'','2012-03-20 14:11:32');

insert  into `tbPrivilege`(`bigId`,`bigParentId`,`vcKind`,`vcName`,`vcSymbol`,`vcPath`,`isLeaf`,`intLevel`,`intOrder`,`vcCreateUser`,`dtCreateTime`,`vcModifyUser`,`dtModifyTime`,`vcRemark`) values (1,0,'SYSTEM','基础数据管理子系统','200',NULL,0,1,NULL,NULL,'2012-02-27 01:02:54',NULL,NULL,'0'),(2,0,'SYSTEM','宜通世纪运维平台','202',NULL,0,1,NULL,NULL,'2012-02-27 01:02:54',NULL,NULL,'0'),(3,0,'SYSTEM','宜通世纪项目管理平台','201',NULL,0,1,NULL,NULL,'2012-02-27 01:02:54',NULL,NULL,'0'),(4,1,'MENU','数据字典服务','200005',NULL,0,2,NULL,NULL,'2012-02-27 01:02:54',NULL,NULL,'200'),(5,4,'MENU','查询','20000502',NULL,1,3,NULL,NULL,'2012-02-27 01:02:54',NULL,NULL,'200005'),(6,4,'MENU','新增','20000503',NULL,1,3,NULL,NULL,'2012-02-27 01:02:54',NULL,NULL,'200005'),(7,4,'MENU','更新','20000504',NULL,1,3,NULL,NULL,'2012-02-27 01:02:54',NULL,NULL,'200005'),(8,4,'MENU','删除','20000505',NULL,1,3,NULL,NULL,'2012-02-27 01:02:54',NULL,NULL,'200005'),(9,4,'MENU','导出EXCEL','20000506',NULL,1,3,NULL,NULL,'2012-02-27 01:02:54',NULL,NULL,'200005'),(10,4,'MENU','导入EXCEL','20000507',NULL,1,3,NULL,NULL,'2012-02-27 01:02:54',NULL,NULL,'200005'),(11,1,'MENU','操作日志服务','200007',NULL,0,2,NULL,NULL,'2012-02-27 01:02:54',NULL,NULL,'200'),(12,11,'MENU','查询','20000702',NULL,1,3,NULL,NULL,'2012-02-27 01:02:54',NULL,NULL,'200007'),(13,11,'MENU','新增','20000703',NULL,1,3,NULL,NULL,'2012-02-27 01:02:54',NULL,NULL,'200007'),(14,11,'MENU','更新','20000704',NULL,1,3,NULL,NULL,'2012-02-27 01:02:54',NULL,NULL,'200007'),(15,11,'MENU','删除','20000705',NULL,1,3,NULL,NULL,'2012-02-27 01:02:54',NULL,NULL,'200007'),(16,11,'MENU','导出EXCEL','20000706',NULL,1,3,NULL,NULL,'2012-02-27 01:02:54',NULL,NULL,'200007'),(17,11,'MENU','导入EXCEL','20000707',NULL,1,3,NULL,NULL,'2012-02-27 01:02:54',NULL,NULL,'200007'),(18,1,'MENU','组织架构管理服务','200001',NULL,0,2,NULL,NULL,'2012-02-27 01:02:54',NULL,NULL,'200'),(19,18,'MENU','查询','20000102',NULL,1,3,NULL,NULL,'2012-02-27 01:02:54',NULL,NULL,'200001'),(20,18,'MENU','删除','20000105',NULL,1,3,NULL,NULL,'2012-02-27 01:02:54',NULL,NULL,'200001'),(21,18,'MENU','新增','20000103',NULL,1,3,NULL,NULL,'2012-02-27 01:02:54',NULL,NULL,'200001'),(22,18,'MENU','更新','20000104',NULL,1,3,NULL,NULL,'2012-02-27 01:02:54',NULL,NULL,'200001'),(23,18,'MENU','导出EXCEL','20000106',NULL,1,3,NULL,NULL,'2012-02-27 01:02:54',NULL,NULL,'200001'),(24,18,'MENU','导入EXCEL','20000107',NULL,1,3,NULL,NULL,'2012-02-27 01:02:54',NULL,NULL,'200001'),(25,1,'MENU','系统权限服务','200004',NULL,0,2,NULL,NULL,'2012-02-27 01:02:54',NULL,NULL,'200'),(26,25,'MENU','查询','20000402',NULL,1,3,NULL,NULL,'2012-02-27 01:02:54',NULL,NULL,'200004'),(27,25,'MENU','新增','20000403',NULL,1,3,NULL,NULL,'2012-02-27 01:02:55',NULL,NULL,'200004'),(28,25,'MENU','更新','20000404',NULL,1,3,NULL,NULL,'2012-02-27 01:02:55',NULL,NULL,'200004'),(29,25,'MENU','删除','20000405',NULL,1,3,NULL,NULL,'2012-02-27 01:02:55',NULL,NULL,'200004'),(30,25,'MENU','新增','20000403',NULL,1,3,NULL,NULL,'2012-02-27 01:02:55',NULL,NULL,'200004'),(31,25,'MENU','导出EXCEL','20000406',NULL,1,3,NULL,NULL,'2012-02-27 01:02:55',NULL,NULL,'200004'),(32,25,'MENU','导入EXCEL','20000407',NULL,1,3,NULL,NULL,'2012-02-27 01:02:55',NULL,NULL,'200004'),(33,1,'MENU','系统角色服务','200003',NULL,0,2,NULL,NULL,'2012-02-27 01:02:55',NULL,NULL,'200'),(34,33,'MENU','查询','20000302',NULL,1,3,NULL,NULL,'2012-02-27 01:02:55',NULL,NULL,'200003'),(35,33,'MENU','新增','20000303',NULL,1,3,NULL,NULL,'2012-02-27 01:02:55',NULL,NULL,'200003'),(36,33,'MENU','更新','20000304',NULL,1,3,NULL,NULL,'2012-02-27 01:02:55',NULL,NULL,'200003'),(37,33,'MENU','删除','20000305',NULL,1,3,NULL,NULL,'2012-02-27 01:02:55',NULL,NULL,'200003'),(38,33,'MENU','为一个角色分配多个用户','20000312',NULL,1,3,NULL,NULL,'2012-02-27 01:02:55',NULL,NULL,'200003'),(39,33,'MENU','分配角色权限','20000313',NULL,1,3,NULL,NULL,'2012-02-27 01:02:55',NULL,NULL,'200003'),(40,33,'MENU','导出EXCEL','20000306',NULL,1,3,NULL,NULL,'2012-02-27 01:02:55',NULL,NULL,'200003'),(41,33,'MENU','导入EXCEL','20000307',NULL,1,3,NULL,NULL,'2012-02-27 01:02:55',NULL,NULL,'200003'),(42,1,'MENU','系统日志服务','200006',NULL,0,2,NULL,NULL,'2012-02-27 01:02:55',NULL,NULL,'200'),(43,42,'MENU','查询','20000602',NULL,1,3,NULL,NULL,'2012-02-27 01:02:55',NULL,NULL,'200006'),(44,42,'MENU','新增','20000603',NULL,1,3,NULL,NULL,'2012-02-27 01:02:55',NULL,NULL,'200006'),(45,42,'MENU','更新','20000604',NULL,1,3,NULL,NULL,'2012-02-27 01:02:55',NULL,NULL,'200006'),(46,42,'MENU','删除','20000605',NULL,1,3,NULL,NULL,'2012-02-27 01:02:55',NULL,NULL,'200006'),(47,42,'MENU','导出EXCEL','20000606',NULL,1,3,NULL,NULL,'2012-02-27 01:02:55',NULL,NULL,'200006'),(48,42,'MENU','导入EXCEL','20000607',NULL,1,3,NULL,NULL,'2012-02-27 01:02:55',NULL,NULL,'200006'),(49,1,'MENU','系统用户服务','200002',NULL,0,2,NULL,NULL,'2012-02-27 01:02:55',NULL,NULL,'200'),(50,49,'MENU','查询','20000202',NULL,1,3,NULL,NULL,'2012-02-27 01:02:55',NULL,NULL,'200002'),(51,49,'MENU','新增','20000203',NULL,1,3,NULL,NULL,'2012-02-27 01:02:55',NULL,NULL,'200002'),(52,49,'MENU','更新','20000204',NULL,1,3,NULL,NULL,'2012-02-27 01:02:55',NULL,NULL,'200002'),(53,49,'MENU','删除','20000205',NULL,1,3,NULL,NULL,'2012-02-27 01:02:55',NULL,NULL,'200002'),(54,49,'MENU','分配用户角色','20000211',NULL,1,3,NULL,NULL,'2012-02-27 01:02:55',NULL,NULL,'200002'),(55,49,'MENU','导出EXCEL','20000206',NULL,1,3,NULL,NULL,'2012-02-27 01:02:55',NULL,NULL,'200002'),(56,49,'MENU','导入EXCEL','20000207',NULL,1,3,NULL,NULL,'2012-02-27 01:02:55',NULL,NULL,'200002'),(57,3,'MENU','合同管理服务','201004',NULL,0,2,NULL,NULL,'2012-02-27 01:02:55',NULL,NULL,'201'),(58,57,'MENU','查询','20100402',NULL,1,3,NULL,NULL,'2012-02-27 01:02:55',NULL,NULL,'201004'),(59,57,'MENU','新增记录','20100403',NULL,1,3,NULL,NULL,'2012-02-27 01:02:55',NULL,NULL,'201004'),(60,57,'MENU','更新记录','20100404',NULL,1,3,NULL,NULL,'2012-02-27 01:02:55',NULL,NULL,'201004'),(61,57,'MENU','删除记录','20100405',NULL,1,3,NULL,NULL,'2012-02-27 01:02:55',NULL,NULL,'201004'),(62,57,'MENU','导出EXCEL','20100406',NULL,1,3,NULL,NULL,'2012-02-27 01:02:55',NULL,NULL,'201004'),(63,57,'MENU','导入EXCEL','20100407',NULL,1,3,NULL,NULL,'2012-02-27 01:02:55',NULL,NULL,'201004'),(64,3,'MENU','成本信息管理服务','201012',NULL,0,2,NULL,NULL,'2012-02-27 01:02:55',NULL,NULL,'201'),(65,64,'MENU','查询','20101202',NULL,1,3,NULL,NULL,'2012-02-27 01:02:55',NULL,NULL,'201012'),(66,64,'MENU','新增记录','20101203',NULL,1,3,NULL,NULL,'2012-02-27 01:02:55',NULL,NULL,'201012'),(67,64,'MENU','更新记录','20101204',NULL,1,3,NULL,NULL,'2012-02-27 01:02:55',NULL,NULL,'201012'),(68,64,'MENU','删除记录','20101205',NULL,1,3,NULL,NULL,'2012-02-27 01:02:55',NULL,NULL,'201012'),(69,64,'MENU','导出EXCEL','20101206',NULL,1,3,NULL,NULL,'2012-02-27 01:02:55',NULL,NULL,'201012'),(70,64,'MENU','导入EXCEL','20101207',NULL,1,3,NULL,NULL,'2012-02-27 01:02:55',NULL,NULL,'201012'),(71,3,'MENU','成本类型管理服务','201011',NULL,0,2,NULL,NULL,'2012-02-27 01:02:55',NULL,NULL,'201'),(72,71,'MENU','查询','20101102',NULL,1,3,NULL,NULL,'2012-02-27 01:02:55',NULL,NULL,'201011'),(73,71,'MENU','新增记录','20101103',NULL,1,3,NULL,NULL,'2012-02-27 01:02:55',NULL,NULL,'201011'),(74,71,'MENU','更新记录','20101104',NULL,1,3,NULL,NULL,'2012-02-27 01:02:55',NULL,NULL,'201011'),(75,71,'MENU','删除记录','20101105',NULL,1,3,NULL,NULL,'2012-02-27 01:02:55',NULL,NULL,'201011'),(76,71,'MENU','导出EXCEL','20101106',NULL,1,3,NULL,NULL,'2012-02-27 01:02:55',NULL,NULL,'201011'),(77,71,'MENU','导入EXCEL','20101107',NULL,1,3,NULL,NULL,'2012-02-27 01:02:55',NULL,NULL,'201011'),(78,3,'MENU','客户管理服务','201007',NULL,0,2,NULL,NULL,'2012-02-27 01:02:55',NULL,NULL,'201'),(79,78,'MENU','查询','20100702',NULL,1,3,NULL,NULL,'2012-02-27 01:02:55',NULL,NULL,'201007'),(80,78,'MENU','新增记录','20100703',NULL,1,3,NULL,NULL,'2012-02-27 01:02:55',NULL,NULL,'201007'),(81,78,'MENU','更新记录','20100704',NULL,1,3,NULL,NULL,'2012-02-27 01:02:55',NULL,NULL,'201007'),(82,78,'MENU','删除记录','20100705',NULL,1,3,NULL,NULL,'2012-02-27 01:02:55',NULL,NULL,'201007'),(83,78,'MENU','导出EXCEL','20100706',NULL,1,3,NULL,NULL,'2012-02-27 01:02:55',NULL,NULL,'201007'),(84,78,'MENU','导入EXCEL','20100707',NULL,1,3,NULL,NULL,'2012-02-27 01:02:55',NULL,NULL,'201007'),(85,3,'MENU','客户联系人管理服务','201010',NULL,0,2,NULL,NULL,'2012-02-27 01:02:55',NULL,NULL,'201'),(86,85,'MENU','查询','20101002',NULL,1,3,NULL,NULL,'2012-02-27 01:02:55',NULL,NULL,'201010'),(87,85,'MENU','新增记录','20101003',NULL,1,3,NULL,NULL,'2012-02-27 01:02:55',NULL,NULL,'201010'),(88,85,'MENU','更新记录','20101004',NULL,1,3,NULL,NULL,'2012-02-27 01:02:55',NULL,NULL,'201010'),(89,85,'MENU','删除记录','20101005',NULL,1,3,NULL,NULL,'2012-02-27 01:02:55',NULL,NULL,'201010'),(90,85,'MENU','导出EXCEL','20101006',NULL,1,3,NULL,NULL,'2012-02-27 01:02:55',NULL,NULL,'201010'),(91,85,'MENU','导入EXCEL','20101007',NULL,1,3,NULL,NULL,'2012-02-27 01:02:55',NULL,NULL,'201010'),(92,3,'MENU','合同实际开票管理服务','201005',NULL,0,2,NULL,NULL,'2012-02-27 01:02:55',NULL,NULL,'201'),(93,92,'MENU','查询','20100502',NULL,1,3,NULL,NULL,'2012-02-27 01:02:55',NULL,NULL,'201005'),(94,92,'MENU','新增记录','20100503',NULL,1,3,NULL,NULL,'2012-02-27 01:02:55',NULL,NULL,'201005'),(95,92,'MENU','更新记录','20100504',NULL,1,3,NULL,NULL,'2012-02-27 01:02:55',NULL,NULL,'201005'),(96,92,'MENU','删除记录','20100505',NULL,1,3,NULL,NULL,'2012-02-27 01:02:56',NULL,NULL,'201005'),(97,92,'MENU','导出EXCEL','20100506',NULL,1,3,NULL,NULL,'2012-02-27 01:02:56',NULL,NULL,'201005'),(98,92,'MENU','导入EXCEL','20100507',NULL,1,3,NULL,NULL,'2012-02-27 01:02:56',NULL,NULL,'201005'),(99,3,'MENU','合同计划开票管理服务','201006',NULL,0,2,NULL,NULL,'2012-02-27 01:02:56',NULL,NULL,'201'),(100,99,'MENU','查询','20100602',NULL,1,3,NULL,NULL,'2012-02-27 01:02:56',NULL,NULL,'201006'),(101,99,'MENU','新增记录','20100603',NULL,1,3,NULL,NULL,'2012-02-27 01:02:56',NULL,NULL,'201006'),(102,99,'MENU','更新记录','20100604',NULL,1,3,NULL,NULL,'2012-02-27 01:02:56',NULL,NULL,'201006'),(103,99,'MENU','删除记录','20100605',NULL,1,3,NULL,NULL,'2012-02-27 01:02:56',NULL,NULL,'201006'),(104,99,'MENU','导出EXCEL','20100606',NULL,1,3,NULL,NULL,'2012-02-27 01:02:56',NULL,NULL,'201006'),(105,99,'MENU','导入EXCEL','20100607',NULL,1,3,NULL,NULL,'2012-02-27 01:02:56',NULL,NULL,'201006'),(106,99,'MENU','新增记录','20100603',NULL,1,3,NULL,NULL,'2012-02-27 01:02:56',NULL,NULL,'201006'),(107,99,'MENU','更新记录','20100604',NULL,1,3,NULL,NULL,'2012-02-27 01:02:56',NULL,NULL,'201006'),(108,3,'MENU','外包管理服务','201008',NULL,0,2,NULL,NULL,'2012-02-27 01:02:56',NULL,NULL,'201'),(109,108,'MENU','查询','20100802',NULL,1,3,NULL,NULL,'2012-02-27 01:02:56',NULL,NULL,'201008'),(110,108,'MENU','新增记录','20100803',NULL,1,3,NULL,NULL,'2012-02-27 01:02:56',NULL,NULL,'201008'),(111,108,'MENU','更新记录','20100804',NULL,1,3,NULL,NULL,'2012-02-27 01:02:56',NULL,NULL,'201008'),(112,108,'MENU','删除记录','20100805',NULL,1,3,NULL,NULL,'2012-02-27 01:02:56',NULL,NULL,'201008'),(113,108,'MENU','导出EXCEL','20100806',NULL,1,3,NULL,NULL,'2012-02-27 01:02:56',NULL,NULL,'201008'),(114,108,'MENU','导入EXCEL','20100807',NULL,1,3,NULL,NULL,'2012-02-27 01:02:56',NULL,NULL,'201008'),(115,3,'MENU','外包合同管理服务','201003',NULL,0,2,NULL,NULL,'2012-02-27 01:02:56',NULL,NULL,'201'),(116,115,'MENU','查询','20100302',NULL,1,3,NULL,NULL,'2012-02-27 01:02:56',NULL,NULL,'201003'),(117,115,'MENU','新增记录','20100303',NULL,1,3,NULL,NULL,'2012-02-27 01:02:56',NULL,NULL,'201003'),(118,115,'MENU','更新记录','20100304',NULL,1,3,NULL,NULL,'2012-02-27 01:02:56',NULL,NULL,'201003'),(119,115,'MENU','删除记录','20100305',NULL,1,3,NULL,NULL,'2012-02-27 01:02:56',NULL,NULL,'201003'),(120,115,'MENU','导出EXCEL','20100306',NULL,1,3,NULL,NULL,'2012-02-27 01:02:56',NULL,NULL,'201003'),(121,115,'MENU','导入EXCEL','20100307',NULL,1,3,NULL,NULL,'2012-02-27 01:02:56',NULL,NULL,'201003'),(122,3,'MENU','外包联系人管理服务','201009',NULL,0,2,NULL,NULL,'2012-02-27 01:02:56',NULL,NULL,'201'),(123,122,'MENU','查询','20100902',NULL,1,3,NULL,NULL,'2012-02-27 01:02:56',NULL,NULL,'201009'),(124,122,'MENU','新增记录','20100903',NULL,1,3,NULL,NULL,'2012-02-27 01:02:56',NULL,NULL,'201009'),(125,122,'MENU','更新记录','20100904',NULL,1,3,NULL,NULL,'2012-02-27 01:02:56',NULL,NULL,'201009'),(126,122,'MENU','删除记录','20100905',NULL,1,3,NULL,NULL,'2012-02-27 01:02:56',NULL,NULL,'201009'),(127,122,'MENU','导出EXCEL','20100906',NULL,1,3,NULL,NULL,'2012-02-27 01:02:56',NULL,NULL,'201009'),(128,122,'MENU','导入EXCEL','20100907',NULL,1,3,NULL,NULL,'2012-02-27 01:02:56',NULL,NULL,'201009'),(129,3,'MENU','项目管理服务','201001',NULL,0,2,NULL,NULL,'2012-02-27 01:02:56',NULL,NULL,'201'),(130,129,'MENU','查询','20100102',NULL,1,3,NULL,NULL,'2012-02-27 01:02:56',NULL,NULL,'201001'),(131,129,'MENU','新增记录','20100103',NULL,1,3,NULL,NULL,'2012-02-27 01:02:56',NULL,NULL,'201001'),(132,129,'MENU','更新记录','20100104',NULL,1,3,NULL,NULL,'2012-02-27 01:02:56',NULL,NULL,'201001'),(133,129,'MENU','删除记录','20100105',NULL,1,3,NULL,NULL,'2012-02-27 01:02:56',NULL,NULL,'201001'),(134,129,'MENU','导出EXCEL','20100106',NULL,1,3,NULL,NULL,'2012-02-27 01:02:56',NULL,NULL,'201001'),(135,129,'MENU','导入EXCEL','20100107',NULL,1,3,NULL,NULL,'2012-02-27 01:02:56',NULL,NULL,'201001'),(136,3,'MENU','项目工作量信息服务','201013',NULL,0,2,NULL,NULL,'2012-02-27 01:02:56',NULL,NULL,'201'),(137,136,'MENU','查询','20101302',NULL,1,3,NULL,NULL,'2012-02-27 01:02:56',NULL,NULL,'201013'),(138,136,'MENU','新增记录','20101303',NULL,1,3,NULL,NULL,'2012-02-27 01:02:56',NULL,NULL,'201013'),(139,136,'MENU','更新记录','20101304',NULL,1,3,NULL,NULL,'2012-02-27 01:02:56',NULL,NULL,'201013'),(140,136,'MENU','删除记录','20101305',NULL,1,3,NULL,NULL,'2012-02-27 01:02:56',NULL,NULL,'201013'),(141,136,'MENU','导出EXCEL','20101306',NULL,1,3,NULL,NULL,'2012-02-27 01:02:56',NULL,NULL,'201013'),(142,136,'MENU','导入EXCEL','20101307',NULL,1,3,NULL,NULL,'2012-02-27 01:02:56',NULL,NULL,'201013'),(143,136,'MENU','批量保存','20101311',NULL,1,3,NULL,NULL,'2012-02-27 01:02:56',NULL,NULL,'201013'),(144,2,'MENU','基站信息管理服务','202005',NULL,0,2,NULL,NULL,'2012-02-27 01:02:56',NULL,NULL,'202'),(145,144,'MENU','查询','20200502',NULL,1,3,NULL,NULL,'2012-02-27 01:02:56',NULL,NULL,'202005'),(146,144,'MENU','新增','20200503',NULL,1,3,NULL,NULL,'2012-02-27 01:02:56',NULL,NULL,'202005'),(147,144,'MENU','更新','20200504',NULL,1,3,NULL,NULL,'2012-02-27 01:02:56',NULL,NULL,'202005'),(148,144,'MENU','删除','20200505',NULL,1,3,NULL,NULL,'2012-02-27 01:02:56',NULL,NULL,'202005'),(149,144,'MENU','导出EXCEL','20200506',NULL,1,3,NULL,NULL,'2012-02-27 01:02:56',NULL,NULL,'202005'),(150,144,'MENU','导入EXCEL','20200507',NULL,1,3,NULL,NULL,'2012-02-27 01:02:56',NULL,NULL,'202005'),(151,2,'MENU','油机信息管理服务','202009',NULL,0,2,NULL,NULL,'2012-02-27 01:02:56',NULL,NULL,'202'),(152,151,'MENU','查询','20200902',NULL,1,3,NULL,NULL,'2012-02-27 01:02:56',NULL,NULL,'202009'),(153,151,'MENU','新增','20200903',NULL,1,3,NULL,NULL,'2012-02-27 01:02:56',NULL,NULL,'202009'),(154,151,'MENU','更新','20200904',NULL,1,3,NULL,NULL,'2012-02-27 01:02:56',NULL,NULL,'202009'),(155,151,'MENU','删除','20200905',NULL,1,3,NULL,NULL,'2012-02-27 01:02:56',NULL,NULL,'202009'),(156,151,'MENU','导出EXCEL','20200906',NULL,1,3,NULL,NULL,'2012-02-27 01:02:56',NULL,NULL,'202009'),(157,151,'MENU','导入EXCEL','20200907',NULL,1,3,NULL,NULL,'2012-02-27 01:02:56',NULL,NULL,'202009'),(158,2,'MENU','油机日志信息管理服务','202008',NULL,0,2,NULL,NULL,'2012-02-27 01:02:56',NULL,NULL,'202'),(159,158,'MENU','查询','20200802',NULL,1,3,NULL,NULL,'2012-02-27 01:02:56',NULL,NULL,'202008'),(160,158,'MENU','新增','20200803',NULL,1,3,NULL,NULL,'2012-02-27 01:02:56',NULL,NULL,'202008'),(161,158,'MENU','更新','20200804',NULL,1,3,NULL,NULL,'2012-02-27 01:02:56',NULL,NULL,'202008'),(162,158,'MENU','删除','20200805',NULL,1,3,NULL,NULL,'2012-02-27 01:02:56',NULL,NULL,'202008'),(163,158,'MENU','导出EXCEL','20200806',NULL,1,3,NULL,NULL,'2012-02-27 01:02:56',NULL,NULL,'202008'),(164,2,'MENU','油机出库信息管理服务','202007',NULL,0,2,NULL,NULL,'2012-02-27 01:02:56',NULL,NULL,'202'),(165,164,'MENU','查询','20200702',NULL,1,3,NULL,NULL,'2012-02-27 01:02:56',NULL,NULL,'202007'),(166,164,'MENU','新增','20200703',NULL,1,3,NULL,NULL,'2012-02-27 01:02:56',NULL,NULL,'202007'),(167,164,'MENU','更新','20200704',NULL,1,3,NULL,NULL,'2012-02-27 01:02:57',NULL,NULL,'202007'),(168,164,'MENU','删除','20200705',NULL,1,3,NULL,NULL,'2012-02-27 01:02:57',NULL,NULL,'202007'),(169,164,'MENU','导出EXCEL','20200706',NULL,1,3,NULL,NULL,'2012-02-27 01:02:57',NULL,NULL,'202007'),(170,2,'MENU','发电记录服务','202012',NULL,0,2,NULL,NULL,'2012-02-27 01:02:57',NULL,NULL,'202'),(171,170,'MENU','查询','20201202',NULL,1,3,NULL,NULL,'2012-02-27 01:02:57',NULL,NULL,'202012'),(172,170,'MENU','新增','20201203',NULL,1,3,NULL,NULL,'2012-02-27 01:02:57',NULL,NULL,'202012'),(173,170,'MENU','更新','20201204',NULL,1,3,NULL,NULL,'2012-02-27 01:02:57',NULL,NULL,'202012'),(174,170,'MENU','删除','20201205',NULL,1,3,NULL,NULL,'2012-02-27 01:02:57',NULL,NULL,'202012'),(175,170,'MENU','导出EXCEL','20201206',NULL,1,3,NULL,NULL,'2012-02-27 01:02:57',NULL,NULL,'202012'),(176,170,'MENU','导入EXCEL','20201207',NULL,1,3,NULL,NULL,'2012-02-27 01:02:57',NULL,NULL,'202012'),(177,2,'MENU','故障处理成本明细服务','202013',NULL,0,2,NULL,NULL,'2012-02-27 01:02:57',NULL,NULL,'202'),(178,177,'MENU','查询','20201302',NULL,1,3,NULL,NULL,'2012-02-27 01:02:57',NULL,NULL,'202013'),(179,177,'MENU','新增','20201303',NULL,1,3,NULL,NULL,'2012-02-27 01:02:57',NULL,NULL,'202013'),(180,177,'MENU','更新','20201304',NULL,1,3,NULL,NULL,'2012-02-27 01:02:57',NULL,NULL,'202013'),(181,177,'MENU','删除','20201305',NULL,1,3,NULL,NULL,'2012-02-27 01:02:57',NULL,NULL,'202013'),(182,177,'MENU','导出EXCEL','20201306',NULL,1,3,NULL,NULL,'2012-02-27 01:02:57',NULL,NULL,'202013'),(183,2,'MENU','故障工单延期日志服务','202004',NULL,0,2,NULL,NULL,'2012-02-27 01:02:57',NULL,NULL,'202'),(184,183,'MENU','查询','20200402',NULL,1,3,NULL,NULL,'2012-02-27 01:02:57',NULL,NULL,'202004'),(185,183,'MENU','新增','20200403',NULL,1,3,NULL,NULL,'2012-02-27 01:02:57',NULL,NULL,'202004'),(186,183,'MENU','更新','20200404',NULL,1,3,NULL,NULL,'2012-02-27 01:02:57',NULL,NULL,'202004'),(187,183,'MENU','删除','20200405',NULL,1,3,NULL,NULL,'2012-02-27 01:02:57',NULL,NULL,'202004'),(188,183,'MENU','导出EXCEL','20200406',NULL,1,3,NULL,NULL,'2012-02-27 01:02:57',NULL,NULL,'202004'),(189,183,'MENU','导入EXCEL','20200407',NULL,1,3,NULL,NULL,'2012-02-27 01:02:57',NULL,NULL,'202004'),(190,2,'MENU','故障编码服务','202002',NULL,0,2,NULL,NULL,'2012-02-27 01:02:57',NULL,NULL,'202'),(191,190,'MENU','查询故障原因','20200211',NULL,1,3,NULL,NULL,'2012-02-27 01:02:57',NULL,NULL,'202002'),(192,190,'MENU','新增故障原因','20200212',NULL,1,3,NULL,NULL,'2012-02-27 01:02:57',NULL,NULL,'202002'),(193,190,'MENU','更新故障原因','20200213',NULL,1,3,NULL,NULL,'2012-02-27 01:02:57',NULL,NULL,'202002'),(194,190,'MENU','删除故障原因','20200214',NULL,1,3,NULL,NULL,'2012-02-27 01:02:57',NULL,NULL,'202002'),(195,190,'MENU','查询故障处理方法','20200215',NULL,1,3,NULL,NULL,'2012-02-27 01:02:57',NULL,NULL,'202002'),(196,190,'MENU','新增故障处理方法','20200216',NULL,1,3,NULL,NULL,'2012-02-27 01:02:57',NULL,NULL,'202002'),(197,190,'MENU','更新故障处理方法','20200217',NULL,1,3,NULL,NULL,'2012-02-27 01:02:57',NULL,NULL,'202002'),(198,190,'MENU','删除故障处理方法','20200218',NULL,1,3,NULL,NULL,'2012-02-27 01:02:57',NULL,NULL,'202002'),(199,190,'MENU','查询故障超时原因','20200219',NULL,1,3,NULL,NULL,'2012-02-27 01:02:57',NULL,NULL,'202002'),(200,190,'MENU','新增故障超时原因','20200220',NULL,1,3,NULL,NULL,'2012-02-27 01:02:57',NULL,NULL,'202002'),(201,190,'MENU','更新故障超时原因','20200221',NULL,1,3,NULL,NULL,'2012-02-27 01:02:57',NULL,NULL,'202002'),(202,190,'MENU','删除故障超时原因','20200222',NULL,1,3,NULL,NULL,'2012-02-27 01:02:57',NULL,NULL,'202002'),(203,2,'MENU','故障工单服务','202003',NULL,0,2,NULL,NULL,'2012-02-27 01:02:57',NULL,NULL,'202'),(204,203,'MENU','查询','20200302',NULL,1,3,NULL,NULL,'2012-02-27 01:02:57',NULL,NULL,'202003'),(205,203,'MENU','批量增加故障工单','20200323',NULL,1,3,NULL,NULL,'2012-02-27 01:02:57',NULL,NULL,'202003'),(206,203,'MENU','新增','20200303',NULL,1,3,NULL,NULL,'2012-02-27 01:02:57',NULL,NULL,'202003'),(207,203,'MENU','更新','20200304',NULL,1,3,NULL,NULL,'2012-02-27 01:02:57',NULL,NULL,'202003'),(208,203,'MENU','删除','20200305',NULL,1,3,NULL,NULL,'2012-02-27 01:02:57',NULL,NULL,'202003'),(209,203,'MENU','导出EXCEL','20200306',NULL,1,3,NULL,NULL,'2012-02-27 01:02:57',NULL,NULL,'202003'),(210,203,'MENU','导入EXCEL','20200307',NULL,1,3,NULL,NULL,'2012-02-27 01:02:57',NULL,NULL,'202003'),(211,2,'MENU','故障工单维护日志服务','202011',NULL,0,2,NULL,NULL,'2012-02-27 01:02:57',NULL,NULL,'202'),(212,211,'MENU','查询','20201102',NULL,1,3,NULL,NULL,'2012-02-27 01:02:57',NULL,NULL,'202011'),(213,211,'MENU','新增','20201103',NULL,1,3,NULL,NULL,'2012-02-27 01:02:57',NULL,NULL,'202011'),(214,211,'MENU','更新','20201104',NULL,1,3,NULL,NULL,'2012-02-27 01:02:57',NULL,NULL,'202011'),(215,211,'MENU','删除','20201105',NULL,1,3,NULL,NULL,'2012-02-27 01:02:57',NULL,NULL,'202011'),(216,211,'MENU','导出EXCEL','20201106',NULL,1,3,NULL,NULL,'2012-02-27 01:02:57',NULL,NULL,'202011'),(217,2,'MENU','物资编码信息管理服务','202006',NULL,0,2,NULL,NULL,'2012-02-27 01:02:57',NULL,NULL,'202'),(218,217,'MENU','查询','20200602',NULL,1,3,NULL,NULL,'2012-02-27 01:02:57',NULL,NULL,'202006'),(219,217,'MENU','新增','20200603',NULL,1,3,NULL,NULL,'2012-02-27 01:02:57',NULL,NULL,'202006'),(220,217,'MENU','更新','20200604',NULL,1,3,NULL,NULL,'2012-02-27 01:02:57',NULL,NULL,'202006'),(221,217,'MENU','删除','20200605',NULL,1,3,NULL,NULL,'2012-02-27 01:02:57',NULL,NULL,'202006'),(222,217,'MENU','导出EXCEL','20200606',NULL,1,3,NULL,NULL,'2012-02-27 01:02:57',NULL,NULL,'202006'),(223,217,'MENU','导入EXCEL','20200607',NULL,1,3,NULL,NULL,'2012-02-27 01:02:57',NULL,NULL,'202006'),(224,2,'MENU','项目成员管理服务','202014',NULL,0,2,NULL,NULL,'2012-02-27 01:02:57',NULL,NULL,'202'),(225,224,'MENU','查询','20201402',NULL,1,3,NULL,NULL,'2012-02-27 01:02:57',NULL,NULL,'202014'),(226,224,'MENU','新增','20201403',NULL,1,3,NULL,NULL,'2012-02-27 01:02:57',NULL,NULL,'202014'),(227,224,'MENU','更新','20201404',NULL,1,3,NULL,NULL,'2012-02-27 01:02:57',NULL,NULL,'202014'),(228,224,'MENU','删除','20201405',NULL,1,3,NULL,NULL,'2012-02-27 01:02:57',NULL,NULL,'202014'),(229,224,'MENU','导出EXCEL','20201406',NULL,1,3,NULL,NULL,'2012-02-27 01:02:57',NULL,NULL,'202014'),(230,224,'MENU','导入EXCEL','20201407',NULL,1,3,NULL,NULL,'2012-02-27 01:02:57',NULL,NULL,'202014'),(231,2,'MENU','团队项目管理服务','202015',NULL,0,2,NULL,NULL,'2012-02-27 01:02:57',NULL,NULL,'202'),(232,231,'MENU','查询','20201502',NULL,1,3,NULL,NULL,'2012-02-27 01:02:57',NULL,NULL,'202015'),(233,231,'MENU','新增','20201503',NULL,1,3,NULL,NULL,'2012-02-27 01:02:57',NULL,NULL,'202015'),(234,231,'MENU','更新','20201504',NULL,1,3,NULL,NULL,'2012-02-27 01:02:58',NULL,NULL,'202015'),(235,231,'MENU','删除','20201505',NULL,1,3,NULL,NULL,'2012-02-27 01:02:58',NULL,NULL,'202015'),(236,231,'MENU','导出EXCEL','20201506',NULL,1,3,NULL,NULL,'2012-02-27 01:02:58',NULL,NULL,'202015'),(237,231,'MENU','导入EXCEL','20201507',NULL,1,3,NULL,NULL,'2012-02-27 01:02:58',NULL,NULL,'202015'),(238,2,'MENU','车辆信息管理服务','202010',NULL,0,2,NULL,NULL,'2012-02-27 01:02:58',NULL,NULL,'202'),(239,238,'MENU','查询','20201002',NULL,1,3,NULL,NULL,'2012-02-27 01:02:58',NULL,NULL,'202010'),(240,238,'MENU','新增','20201003',NULL,1,3,NULL,NULL,'2012-02-27 01:02:58',NULL,NULL,'202010'),(241,238,'MENU','更新','20201004',NULL,1,3,NULL,NULL,'2012-02-27 01:02:58',NULL,NULL,'202010'),(242,238,'MENU','删除','20201005',NULL,1,3,NULL,NULL,'2012-02-27 01:02:58',NULL,NULL,'202010'),(243,238,'MENU','导出EXCEL','20201006',NULL,1,3,NULL,NULL,'2012-02-27 01:02:58',NULL,NULL,'202010'),(244,238,'MENU','导入EXCEL','20201007',NULL,1,3,NULL,NULL,'2012-02-27 01:02:58',NULL,NULL,'202010');

insert  into `tbRole`(`bigId`,`vcName`,`vcRemark`) values (1,'超级管理员','超级管理员');

insert  into `tbRolePrivilege`(`bigId`,`bigPrivilegeId`,`bigRoleId`) values (1,1,1),(2,2,1),(3,3,1),(4,4,1),(5,5,1),(6,6,1),(7,7,1),(8,8,1),(9,9,1),(10,10,1),(11,11,1),(12,12,1),(13,13,1),(14,14,1),(15,15,1),(16,16,1),(17,17,1),(18,18,1),(19,19,1),(20,20,1),(21,21,1),(22,22,1),(23,23,1),(24,24,1),(25,25,1),(26,26,1),(27,27,1),(28,28,1),(29,29,1),(30,30,1),(31,31,1),(32,32,1),(33,33,1),(34,34,1),(35,35,1),(36,36,1),(37,37,1),(38,38,1),(39,39,1),(40,40,1),(41,41,1),(42,42,1),(43,43,1),(44,44,1),(45,45,1),(46,46,1),(47,47,1),(48,48,1),(49,49,1),(50,50,1),(51,51,1),(52,52,1),(53,53,1),(54,54,1),(55,55,1),(56,56,1),(57,57,1),(58,58,1),(59,59,1),(60,60,1),(61,61,1),(62,62,1),(63,63,1),(64,64,1),(65,65,1),(66,66,1),(67,67,1),(68,68,1),(69,69,1),(70,70,1),(71,71,1),(72,72,1),(73,73,1),(74,74,1),(75,75,1),(76,76,1),(77,77,1),(78,78,1),(79,79,1),(80,80,1),(81,81,1),(82,82,1),(83,83,1),(84,84,1),(85,85,1),(86,86,1),(87,87,1),(88,88,1),(89,89,1),(90,90,1),(91,91,1),(92,92,1),(93,93,1),(94,94,1),(95,95,1),(96,96,1),(97,97,1),(98,98,1),(99,99,1),(100,100,1),(101,101,1),(102,102,1),(103,103,1),(104,104,1),(105,105,1),(106,106,1),(107,107,1),(108,108,1),(109,109,1),(110,110,1),(111,111,1),(112,112,1),(113,113,1),(114,114,1),(115,115,1),(116,116,1),(117,117,1),(118,118,1),(119,119,1),(120,120,1),(121,121,1),(122,122,1),(123,123,1),(124,124,1),(125,125,1),(126,126,1),(127,127,1),(128,128,1),(129,129,1),(130,130,1),(131,131,1),(132,132,1),(133,133,1),(134,134,1),(135,135,1),(136,136,1),(137,137,1),(138,138,1),(139,139,1),(140,140,1),(141,141,1),(142,142,1),(143,143,1),(144,144,1),(145,145,1),(146,146,1),(147,147,1),(148,148,1),(149,149,1),(150,150,1),(151,151,1),(152,152,1),(153,153,1),(154,154,1),(155,155,1),(156,156,1),(157,157,1),(158,158,1),(159,159,1),(160,160,1),(161,161,1),(162,162,1),(163,163,1),(164,164,1),(165,165,1),(166,166,1),(167,167,1),(168,168,1),(169,169,1),(170,170,1),(171,171,1),(172,172,1),(173,173,1),(174,174,1),(175,175,1),(176,176,1),(177,177,1),(178,178,1),(179,179,1),(180,180,1),(181,181,1),(182,182,1),(183,183,1),(184,184,1),(185,185,1),(186,186,1),(187,187,1),(188,188,1),(189,189,1),(190,190,1),(191,191,1),(192,192,1),(193,193,1),(194,194,1),(195,195,1),(196,196,1),(197,197,1),(198,198,1),(199,199,1),(200,200,1),(201,201,1),(202,202,1),(203,203,1),(204,204,1),(205,205,1),(206,206,1),(207,207,1),(208,208,1),(209,209,1),(210,210,1),(211,211,1),(212,212,1),(213,213,1),(214,214,1),(215,215,1),(216,216,1),(217,217,1),(218,218,1),(219,219,1),(220,220,1),(221,221,1),(222,222,1),(223,223,1),(224,224,1),(225,225,1),(226,226,1),(227,227,1),(228,228,1),(229,229,1),(230,230,1),(231,231,1),(232,232,1),(233,233,1),(234,234,1),(235,235,1),(236,236,1),(237,237,1),(238,238,1),(239,239,1),(240,240,1),(241,241,1),(242,242,1),(243,243,1),(244,244,1);

insert  into `tbSystemLog`(`bigId`,`vcIpv4`,`vcIpv6`,`vcAccount`,`vcSystemCode`,`vcSystem`,`vcModuleCode`,`vcModule`,`vcContent`,`vcResultCode`,`intLogLevel`,`vcLogLevelName`,`intLogType`,`vcLogTypeName`,`dtCreateTime`) values (1,'','','','100','基础服务','10000001','[系统登录服务].[登录系统]','登录失败，用户名密码错误或者没有访问权限！','0001',3,'error',1,'security','2012-02-28 13:58:17'),(2,'','','','201','宜通世纪项目管理平台','20100303','[外包合同管理服务].[新增记录]','未处理异常: org.hibernate.exception.ConstraintViolationException: could not insert: [com.etone.itc.project.entity.TbOutsourceContract]','0023',3,'error',0,'system','2012-02-28 13:58:18'),(3,'','','','201','宜通世纪项目管理平台','20100307','[外包合同管理服务].[导入EXCEL]','未处理异常: 0999','0023',3,'error',0,'system','2012-02-28 13:58:18'),(4,'','','','100','基础服务','10000001','[系统登录服务].[登录系统]','登录失败，用户名密码错误或者没有访问权限！','0001',3,'error',1,'security','2012-02-28 13:58:19'),(5,'','','','201','宜通世纪项目管理平台','20100403','[合同管理服务].[新增记录]','未处理异常: org.hibernate.exception.ConstraintViolationException: could not insert: [com.etone.itc.project.entity.TbContract]','0023',3,'error',0,'system','2012-02-28 13:58:19'),(6,'','','','201','宜通世纪项目管理平台','20100407','[合同管理服务].[导入EXCEL]','未处理异常: 0999','0023',3,'error',0,'system','2012-02-28 13:58:19'),(7,'','','','100','基础服务','10000001','[系统登录服务].[登录系统]','登录失败，用户名密码错误或者没有访问权限！','0001',3,'error',1,'security','2012-02-28 13:58:19'),(8,'','','','201','宜通世纪项目管理平台','20100503','[合同实际开票管理服务].[新增记录]','未处理异常: org.hibernate.exception.ConstraintViolationException: could not insert: [com.etone.itc.project.entity.TbInvoice]','0023',3,'error',0,'system','2012-02-28 13:58:19'),(9,'','','','201','宜通世纪项目管理平台','20100507','[合同实际开票管理服务].[导入EXCEL]','未处理异常: 0999','0023',3,'error',0,'system','2012-02-28 13:58:19'),(10,'','','','100','基础服务','10000001','[系统登录服务].[登录系统]','登录失败，用户名密码错误或者没有访问权限！','0001',3,'error',1,'security','2012-02-28 13:58:20'),(11,'','','','201','宜通世纪项目管理平台','20100603','[合同计划开票管理服务].[新增记录]','未处理异常: org.hibernate.exception.ConstraintViolationException: could not insert: [com.etone.itc.project.entity.TbInvoicePlan]','0023',3,'error',0,'system','2012-02-28 13:58:20'),(12,'','','','201','宜通世纪项目管理平台','20100607','[合同计划开票管理服务].[导入EXCEL]','未处理异常: 0999','0023',3,'error',0,'system','2012-02-28 13:58:20'),(13,'','','','201','宜通世纪项目管理平台','20100603','[合同计划开票管理服务].[新增记录]','未处理异常: org.hibernate.exception.ConstraintViolationException: could not insert: [com.etone.itc.project.entity.TbInvoicePlan]','0023',3,'error',0,'system','2012-02-28 13:58:20'),(14,'','','','201','宜通世纪项目管理平台','20100604','[合同计划开票管理服务].[更新记录]','未处理异常: org.hibernate.exception.ConstraintViolationException: could not insert: [com.etone.itc.project.entity.TbInvoicePlan]','0023',3,'error',0,'system','2012-02-28 13:58:20'),(15,'','','','100','基础服务','10000001','[系统登录服务].[登录系统]','登录失败，用户名密码错误或者没有访问权限！','0001',3,'error',1,'security','2012-02-28 13:58:54'),(16,'','','','100','基础服务','10000001','[系统登录服务].[登录系统]','登录失败，用户名密码错误或者没有访问权限！','0001',3,'error',1,'security','2012-02-28 13:59:12'),(17,'','','','201','宜通世纪项目管理平台','20100107','[项目管理服务].[导入EXCEL]','未处理异常: 0999','0023',3,'error',0,'system','2012-02-28 13:59:13'),(18,'','','','100','基础服务','10000001','[系统登录服务].[登录系统]','登录失败，用户名密码错误或者没有访问权限！','0001',3,'error',1,'security','2012-02-28 13:59:13'),(19,'','','','201','宜通世纪项目管理平台','20100303','[外包合同管理服务].[新增记录]','未处理异常: org.hibernate.exception.ConstraintViolationException: could not insert: [com.etone.itc.project.entity.TbOutsourceContract]','0023',3,'error',0,'system','2012-02-28 13:59:13'),(20,'','','','201','宜通世纪项目管理平台','20100307','[外包合同管理服务].[导入EXCEL]','未处理异常: 0999','0023',3,'error',0,'system','2012-02-28 13:59:13'),(21,'','','','100','基础服务','10000001','[系统登录服务].[登录系统]','登录失败，用户名密码错误或者没有访问权限！','0001',3,'error',1,'security','2012-02-28 13:59:13'),(22,'','','','201','宜通世纪项目管理平台','20100403','[合同管理服务].[新增记录]','未处理异常: org.hibernate.exception.ConstraintViolationException: could not insert: [com.etone.itc.project.entity.TbContract]','0023',3,'error',0,'system','2012-02-28 13:59:13'),(23,'','','','201','宜通世纪项目管理平台','20100407','[合同管理服务].[导入EXCEL]','未处理异常: 0999','0023',3,'error',0,'system','2012-02-28 13:59:13'),(24,'','','','100','基础服务','10000001','[系统登录服务].[登录系统]','登录失败，用户名密码错误或者没有访问权限！','0001',3,'error',1,'security','2012-02-28 13:59:14'),(25,'','','','201','宜通世纪项目管理平台','20100503','[合同实际开票管理服务].[新增记录]','未处理异常: org.hibernate.exception.ConstraintViolationException: could not insert: [com.etone.itc.project.entity.TbInvoice]','0023',3,'error',0,'system','2012-02-28 13:59:14'),(26,'','','','201','宜通世纪项目管理平台','20100507','[合同实际开票管理服务].[导入EXCEL]','未处理异常: 0999','0023',3,'error',0,'system','2012-02-28 13:59:14'),(27,'','','','100','基础服务','10000001','[系统登录服务].[登录系统]','登录失败，用户名密码错误或者没有访问权限！','0001',3,'error',1,'security','2012-02-28 13:59:14'),(28,'','','','201','宜通世纪项目管理平台','20100603','[合同计划开票管理服务].[新增记录]','未处理异常: org.hibernate.exception.ConstraintViolationException: could not insert: [com.etone.itc.project.entity.TbInvoicePlan]','0023',3,'error',0,'system','2012-02-28 13:59:14'),(29,'','','','201','宜通世纪项目管理平台','20100607','[合同计划开票管理服务].[导入EXCEL]','未处理异常: 0999','0023',3,'error',0,'system','2012-02-28 13:59:14'),(30,'','','','201','宜通世纪项目管理平台','20100603','[合同计划开票管理服务].[新增记录]','未处理异常: org.hibernate.exception.ConstraintViolationException: could not insert: [com.etone.itc.project.entity.TbInvoicePlan]','0023',3,'error',0,'system','2012-02-28 13:59:14'),(31,'','','','201','宜通世纪项目管理平台','20100604','[合同计划开票管理服务].[更新记录]','未处理异常: org.hibernate.exception.ConstraintViolationException: could not insert: [com.etone.itc.project.entity.TbInvoicePlan]','0023',3,'error',0,'system','2012-02-28 13:59:14'),(32,'','','','100','基础服务','10000001','[系统登录服务].[登录系统]','登录失败，用户名密码错误或者没有访问权限！','0001',3,'error',1,'security','2012-02-28 15:18:37'),(33,'','','','201','宜通世纪项目管理平台','20100403','[合同管理服务].[新增记录]','未处理异常: org.hibernate.exception.ConstraintViolationException: could not insert: [com.etone.itc.project.entity.TbContract]','0023',3,'error',0,'system','2012-02-28 15:18:37'),(34,'','','','100','基础服务','10000001','[系统登录服务].[登录系统]','登录失败，用户名密码错误或者没有访问权限！','0001',3,'error',1,'security','2012-02-28 15:21:18'),(35,'','','','100','基础服务','10000001','[系统登录服务].[登录系统]','登录失败，用户名密码错误或者没有访问权限！','0001',3,'error',1,'security','2012-02-28 15:23:11'),(36,'','','','201','宜通世纪项目管理平台','20100603','[合同计划开票管理服务].[新增记录]','未处理异常: org.hibernate.exception.ConstraintViolationException: could not insert: [com.etone.itc.project.entity.TbInvoicePlan]','0023',3,'error',0,'system','2012-02-28 15:23:11'),(37,'','','','100','基础服务','10000001','[系统登录服务].[登录系统]','登录失败，用户名密码错误或者没有访问权限！','0001',3,'error',1,'security','2012-02-28 15:24:07'),(38,'','','','100','基础服务','10000001','[系统登录服务].[登录系统]','登录失败，用户名密码错误或者没有访问权限！','0001',3,'error',1,'security','2012-02-28 15:26:25'),(39,'127.0.0.1','127.0.0.1','admin','201','宜通世纪项目管理平台','20100102','[项目管理服务].[查询]','未处理异常: java.lang.Integer cannot be cast to java.lang.Short','0023',3,'error',0,'system','2012-02-28 15:28:30'),(40,'127.0.0.1','127.0.0.1','admin','201','宜通世纪项目管理平台','20100102','[项目管理服务].[查询]','未处理异常: java.lang.Integer cannot be cast to java.lang.Short','0023',3,'error',0,'system','2012-02-28 15:29:08'),(41,'127.0.0.1','127.0.0.1','admin','201','宜通世纪项目管理平台','20100102','[项目管理服务].[查询]','未处理异常: java.lang.Integer cannot be cast to java.lang.Short','0023',3,'error',0,'system','2012-02-28 15:35:30'),(42,'127.0.0.1','127.0.0.1','admin','201','宜通世纪项目管理平台','20100102','[项目管理服务].[查询]','未处理异常: java.lang.Integer cannot be cast to java.lang.Short','0023',3,'error',0,'system','2012-02-28 15:38:29'),(43,'','','','201','宜通世纪项目管理平台','20100502','[合同实际开票管理服务].[查询]','未处理异常: org.hibernate.exception.SQLGrammarException: could not execute query','0023',3,'error',0,'system','2012-03-07 16:25:21'),(44,'','','','201','宜通世纪项目管理平台','20100502','[合同实际开票管理服务].[查询]','未处理异常: org.hibernate.exception.SQLGrammarException: could not execute query','0023',3,'error',0,'system','2012-03-07 16:26:01'),(45,'','','','201','宜通世纪项目管理平台','20101302','[项目工作量信息服务].[查询]','未处理异常: null','0023',3,'error',0,'system','2012-03-07 16:33:14'),(46,'','','','201','宜通世纪项目管理平台','20100302','[外包合同管理服务].[查询]','未处理异常: null','0023',3,'error',0,'system','2012-03-07 16:39:31');

insert into `tbUser` (`bigId`, `vcStaffguid`, `vcOuguid`, `vcOuName`, `vcAccount`, `vcPassword`, `vcSalt`, `vcTips`, `vcTipAnswer`, `vcKind`, `vcFullName`, `vcSex`, `vcWorkphone`, `vcTelephone`, `vcShortMobile`, `vcEmail`, `isSupervisor`, `isLock`, `dtLockedDate`, `vcStatus`, `dtValidTime`, `vcOuPath`, `vcCreateUser`, `dtCreateTime`, `vcModifyUser`, `dtModifyTime`, `vcRemark`) values('1',NULL,NULL,NULL,'admin','63d348232ada9b10ae9068267c664f8fef8d1019','12b6f220b64fdf26',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','0',NULL,'NORMAL',NULL,NULL,NULL,'2012-02-26 20:40:19',NULL,NULL,NULL);

insert  into `tbUserRole`(`bigId`,`bigRoleId`,`bigUserId`) values (1,1,1);



/