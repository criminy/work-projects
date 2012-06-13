/* Test county superior court */
insert into court (id,name,uuid) values ('testid','Test','courtUuidTest');
insert into organizations (uuid,name) values ('testorg1','Child Support');
/* Test2 county superior court */
insert into court (id,name,uuid) values ('testid2','Test2','courtUuidTest2');
insert into organizations (uuid,name) values ('testorg2','Child Support');
/* Attorney */
insert into person (uuid,firstName,lastName) values ('testattorney','Test','Attorney');
insert into attorney (uuid,personUuid,barId) values ('testattorney','testattorney','000001');
insert into personOrganizations (uuid,personUuid,organizationUuid,courtUuid) values ('poatt1','testattorney','testorg1','courtUuidTest');
insert into personOrganizations (uuid,personUuid,organizationUuid,courtUuid) values ('poatt2','testattorney','testorg2','courtUuidTest2');
/* Legal preparer */
insert into person (uuid,firstName,lastName) values ('testagent','Test','Agent');
insert into legalPreparer (uuid,personUuid,attorneyUuid,orgUuid) values ('testagent','testagent','testattorney','testorg1');
insert into legalPreparer (uuid,personUuid,attorneyUuid,orgUuid) values ('testagent2','testagent','testattorney','testorg2');
insert into personOrganizations (uuid,personUuid,organizationUuid,courtUuid) values ('poagent1','testagent','testorg1','courtUuidTest');
insert into personOrganizations (uuid,personUuid,organizationUuid,courtUuid) values ('poagent2','testagent','testorg2','courtUuidTest2');
/* Users */
insert into ef_user (uuid,username,primaryRole) values ('testagent1','testagent','agent');
insert into ef_user (uuid,username,primaryRole) values ('testattorney1','testattorney','attorney');
insert into ef_user (uuid,username,primaryRole) values ('testclerk1','testclerk','clerk');
insert into ef_user (uuid,username,primaryRole) values ('testjudge1','testjudge','judge');
/* case data */
insert into caseRecord (uuid,courtUuid,statusCode,instantiationDateTime,caseTrackingId,clearFilingFlag,deletedFilingFlag) values ('cruuid1','courtUuidTest',1,CURRENT_TIMESTAMP,'AOCXX00001',0,0);
insert into caseRecord (uuid,courtUuid,statusCode,instantiationDateTime,caseTrackingId,clearFilingFlag,deletedFilingFlag) values ('cruuid2','courtUuidTest',1,CURRENT_TIMESTAMP,'AOCXX00002',0,0);
insert into caseRecord (uuid,courtUuid,statusCode,instantiationDateTime,caseTrackingId,clearFilingFlag,deletedFilingFlag) values ('cruuid3','courtUuidTest',1,CURRENT_TIMESTAMP,'',0,0);


