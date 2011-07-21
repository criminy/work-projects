INSERT INTO Person (uuid,firstName,lastName,password,userID,dob,changePwdFlag,deleteUserFlag) values ('personUuid1','personFirstName1','personLastName1','asdf','personUsername',null,0,0);
INSERT INTO Person (uuid,firstName,lastName,password,userID,dob,changePwdFlag,deleteUserFlag) values ('personUuid2','personFirstName2','personLastName2','asdf','personUsername2',null,0,0);
insert into Court (uuid,id,name) values ('TESTCOURTID','COURTID','Sample Court');
insert into CaseRecordStatus (code, description) values (2,'Imported');
insert into CaseRecordStatus (code, description) values (21,'QueuedForImport');
insert into CaseRecord (uuid,statusCode,courtUuid,caseTypeCode) values ('caseRecordTest01',21,'TESTCORTID',1);