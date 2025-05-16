--
-- Postgresql DDL SCRIPT 
-- 	BIOTP_SCHEME_v2.0.6
--
-- =========== SCHEME 버전(빌드버전) 설명 =============
--
--  * 버전 정보의 구성은 v{메이저버전}.{마이너버전}.{서비스버전}
--  * 같은 메이저 버전인 경우, 업그레이드 가능
--  * 메이저 버전은 기존 사용하고 있는 pojo VO 구조가 바뀌지 않는다는 가정하에 출발한다. (VO 멤버변수 변경은 불가, 추가는 가능)
--  * 테이블의 추가 및 변경은 마이너 버전에 표시한다.
--  * API 및 서비스 변경 등 소스 변경에 대한 배포가 있는 경우 서비스버전을 변경한다. 마이너버전 변경 시 서비스버전 0으로 초기화 
--
-- == DATA TYPE FOR HSQL TO Postgresql 
-- 	* CLOB to TEXT
-- 	* BLOB to BYTEA 




--
-- /////////////////////////
-- //	create sequence
-- /////////////////////////
--
CREATE SEQUENCE MMTH_Administrators_id_Seq1 INCREMENT BY 1 START WITH 1;
CREATE SEQUENCE MMTH_AuditAlarms_id_Seq1 INCREMENT BY 1 START WITH 1;
CREATE SEQUENCE MMTH_FidoFacets_id_Seq1 INCREMENT BY 1 START WITH 1;
CREATE SEQUENCE MMTH_FidoMetadata_id_Seq1 INCREMENT BY 1 START WITH 1;
CREATE SEQUENCE MMTH_ServiceLogs_id_Seq1 INCREMENT BY 1 START WITH 1;
CREATE SEQUENCE MMTH_IssuanceHistory_id_Seq1 INCREMENT BY 1 START WITH 1;
CREATE SEQUENCE MMTH_Users_id_Seq1 INCREMENT BY 1 START WITH 1;
CREATE SEQUENCE MMTH_AppAgents_id_Seq1 INCREMENT BY 1 START WITH 1;
CREATE SEQUENCE MMTH_FidoRegistrations_id_Seq1 INCREMENT BY 1 START WITH 1;
CREATE SEQUENCE MMTH_UserDevices_id_Seq1 INCREMENT BY 1 START WITH 1;
CREATE SEQUENCE MMTH_DeviceAppAgents_id_Seq1 INCREMENT BY 1 START WITH 1;
CREATE SEQUENCE MMTH_FidoUsers_id_Seq1 INCREMENT BY 1 START WITH 1;
CREATE SEQUENCE MMTH_TokenUsers_id_Seq1 INCREMENT BY 1 START WITH 1;
CREATE SEQUENCE MMTH_ServerChallenge_id_Seq1 INCREMENT BY 1 START WITH 1;
CREATE SEQUENCE MMTH_FidoTransaction_id_Seq1 INCREMENT BY 1 START WITH 1;
CREATE SEQUENCE MMTH_IssueCodeData_id_Seq1 INCREMENT BY 1 START WITH 1;
CREATE SEQUENCE MMTH_AuthManager_id_Seq1 INCREMENT BY 1 START WITH 1;
CREATE SEQUENCE MMTH_UserServerPwd_id_Seq1 INCREMENT BY 1 START WITH 1;
CREATE SEQUENCE MMTH_ExtServiceItems_id_Seq1 INCREMENT BY 1 START WITH 1;
CREATE SEQUENCE MMTH_UserAnnotations_id_Seq1 INCREMENT BY 1 START WITH 1;


--
--
-- # System Settings #
--
CREATE TABLE MMTH_SystemSettings (
	settingName VARCHAR(64) NOT NULL,
	settingValue TEXT
);
ALTER TABLE MMTH_SystemSettings ADD CONSTRAINT MMTH_SystemSettings_Pk PRIMARY KEY (settingName);


--
--
-- # Administrators #
--
-- 	********************************
--	 * disabled - 사용 일시 정지,  N:enabled, Y:disabled, default 'N'
--   * adminType - '1' : dev, '2' : super, '3' : admin, '4' : permitted admin
-- 	********************************
--
CREATE TABLE MMTH_Administrators (
	id INT NOT NULL DEFAULT NEXTVAL('MMTH_Administrators_id_Seq1'),
	username VARCHAR(128) NOT NULL,
	password VARCHAR(128) NOT NULL,
	adminType CHAR(1) NOT NULL,
	disabled CHAR(1) NOT NULL,
	tsLastLogin BIGINT DEFAULT 0,
	lastRemoteAddr VARCHAR(20),
	homeUrl VARCHAR(255),
	tsReg BIGINT NOT NULL,
    tsUpdated BIGINT DEFAULT 0,
    data BYTEA NOT NULL
);
ALTER TABLE MMTH_Administrators ADD CONSTRAINT MMTH_Administrators_Pk PRIMARY KEY (id);
ALTER TABLE MMTH_Administrators ADD CONSTRAINT MMTH_Administrators_Un1 UNIQUE (username);


--
--
-- # MMTH_AuditAlarms #	-
--
-- 	********************************
--   * alarmLevel - NONE : 0, INFORMATION : 1, URGENT : 2, CRITICAL : 3, LIFE_SAFETY : 4
-- 	********************************
--
CREATE TABLE MMTH_AuditAlarms (
    id BIGINT NOT NULL DEFAULT NEXTVAL('MMTH_AuditAlarms_id_Seq1'),
    auditType INT NOT NULL,
    actionType INT NOT NULL,
    alarmLevel INT NOT NULL,
    description VARCHAR(1024) NOT NULL,
    tsReg BIGINT NOT NULL,
    ack CHAR(1) DEFAULT 'N',
    ackAdmin VARCHAR(128) DEFAULT '-',
    tsAck BIGINT DEFAULT 0 
);
ALTER TABLE MMTH_AuditAlarms ADD CONSTRAINT MMTH_AuditAlarms_Pk PRIMARY KEY (id);


--
--
--  # MMTH_FidoFacets #
--
-- 	********************************
--	 * disabled : 사용 일시 정지,  N:enabled, Y:disabled, default 'N'
-- 	********************************
--
CREATE TABLE MMTH_FidoFacets (
    id INT NOT NULL DEFAULT NEXTVAL('MMTH_FidoFacets_id_Seq1'),
    facetId VARCHAR(512) NOT NULL,
    majorVer INT NOT NULL,
    minorVer INT NOT NULL,
    alias VARCHAR(128) NOT NULL,
    disabled CHAR(1) NOT NULL,
    description VARCHAR(1024) DEFAULT '',
    tsReg BIGINT NOT NULL,
    tsUpdated BIGINT DEFAULT 0
);
ALTER TABLE MMTH_FidoFacets ADD CONSTRAINT MMTH_FidoFacets_Pk PRIMARY KEY (id);
ALTER TABLE MMTH_FidoFacets ADD CONSTRAINT MMTH_FidoFacets_Un1 UNIQUE (facetId);


--
--
-- # MMTH_FidoMetadata #
--
-- 	********************************
--	 * disabled : 사용 여부,  N:enabled, Y:disabled, default 'N'
-- 	********************************
--
CREATE TABLE MMTH_FidoMetadata (
    id INT NOT NULL DEFAULT NEXTVAL('MMTH_FidoMetadata_id_Seq1'),
    aaid CHAR(9) NOT NULL,
    alias VARCHAR(128) NOT NULL,
    disabled CHAR(1) NOT NULL,
    userVerification BIGINT NOT NULL,
    description VARCHAR(1024) DEFAULT '',
    tsReg BIGINT NOT NULL,
    tsUpdated BIGINT DEFAULT 0,
    data BYTEA NOT NULL
);
ALTER TABLE MMTH_FidoMetadata ADD CONSTRAINT MMTH_FidoMetadata_Pk PRIMARY KEY (id);
ALTER TABLE MMTH_FidoMetadata ADD CONSTRAINT MMTH_FidoMetadata_Un1 UNIQUE (aaid);

--
--
-- # MMTH_ServiceLogs #
--
-- 	********************************
--	 * OperationType
-- 	 * 0x1001 : ISSUE_CODE
-- 	 * 0x2001 : REG
-- 	 * 0x3001 : DEREG
-- 	 * 0x3002 : FORCE_DEREG
-- 	 * 0x4001 : FIDO_AUTH
-- 	 * 0x4002 : BIOTP_AUTH
-- 	 * 0x4003 : SAOTP_AUTH	
-- 	 
-- 	 
--	 * actionType : 0x01:REQ, 0x02:FAIL, 0x03:DONE
-- 	********************************
--
CREATE TABLE MMTH_ServiceLogs (
	id BIGINT NOT NULL DEFAULT NEXTVAL('MMTH_ServiceLogs_id_Seq1'),
    authType INT NOT NULL,
    username VARCHAR(128) NOT NULL,
    opType INT NOT NULL,
    reqType INT NOT NULL,
    actionType INT NOT NULL,
    returnCode CHAR(4),
    deviceId VARCHAR(64),
    deviceType CHAR(2),
    pkgUnique VARCHAR(255),
    aaid VARCHAR(9),
    tokenId VARCHAR(12),
    description VARCHAR(1024),
    tsReg BIGINT NOT NULL,
    regDate CHAR(8) NOT NULL,
    regHour CHAR(10) NOT NULL
);
ALTER TABLE MMTH_ServiceLogs ADD CONSTRAINT MMTH_ServiceLogs_Pk PRIMARY KEY (id);



--
--
-- # MMTH_IssuanceHistory #
--
-- 	********************************
--	 * issueType : 발급유형 ('1' : 신규사용자 발급, '2' : 기존사용자의 기기추가, '3' :  앱추가등록, '4' : 사고신고등에 의한 재이용발급)
-- 	********************************
--
CREATE TABLE MMTH_IssuanceHistory (
	id INT NOT NULL DEFAULT NEXTVAL('MMTH_IssuanceHistory_id_Seq1'),
    authType INT NOT NULL,
    username VARCHAR(128) NOT NULL,
    deviceId VARCHAR(128) NOT NULL,
    deviceType CHAR(2),
    pkgUnique VARCHAR(255) NOT NULL,
    issueType CHAR(1) NOT NULL,
    aaid VARCHAR(9),
    tokenId  VARCHAR(12),
    tsIssue BIGINT NOT NULL,
    issueMonth CHAR(6) NOT NULL,
    issueDate CHAR(8) NOT NULL,
    issueTime CHAR(6) NOT NULL
);
ALTER TABLE MMTH_IssuanceHistory ADD CONSTRAINT MMTH_IssuanceHistory_Pk PRIMARY KEY (id);



--
--
-- MMTH_Users *
--
-- 	********************************
--	 * disabled : 사용 일시 정지,  N:enabled, Y:disabled, default 'N'
--	 * status : '0':NOT_AVAILABLE(유효한 기기 등록 건이 없는 경우), '1' :AVAILABLE(유효한 기기 등록 건이 있는 경우), '2' : 사고접수(모든 기기 이용 불가), '9': DISCARD (모든 서비스 해지)
-- 	********************************
--
CREATE TABLE MMTH_Users (
    id INT NOT NULL DEFAULT NEXTVAL('MMTH_Users_id_Seq1'),
    username VARCHAR(128) NOT NULL,
    disabled CHAR(1) NOT NULL,
    status CHAR(1) NOT NULL,
    tsReg BIGINT NOT NULL,
    tsUpdated BIGINT DEFAULT 0,
    data BYTEA NOT NULL 
);
ALTER TABLE MMTH_Users ADD CONSTRAINT MMTH_Users_Pk PRIMARY KEY (id);
ALTER TABLE MMTH_Users ADD CONSTRAINT MMTH_Users_Un1 UNIQUE (username);

--
--
-- # MMTH_AppAgents #
--
-- 	********************************
--	 * disabled : 사용 일시 정지,  N:enabled, Y:disabled, default 'N'
--	 * osType : 사용하고 있는 OS. A : Android OS, I : IOS
-- 	********************************
--
CREATE TABLE MMTH_AppAgents (
    id INT NOT NULL DEFAULT NEXTVAL('MMTH_AppAgents_id_Seq1'),
    pkgUnique VARCHAR(255) NOT NULL,
    osType CHAR(1) NOT NULL,
    alias VARCHAR(128) NOT NULL,
    disabled CHAR(1) NOT NULL,
    description VARCHAR(1024) DEFAULT '',
    tsReg BIGINT NOT NULL,
    tsUpdated BIGINT DEFAULT 0,
    data BYTEA NOT NULL
);
ALTER TABLE MMTH_AppAgents ADD CONSTRAINT MMTH_AppAgents_Pk PRIMARY KEY (id);
ALTER TABLE MMTH_AppAgents ADD CONSTRAINT MMTH_AppAgents_Un1 UNIQUE (pkgUnique, osType);


--
--
-- # MMTH_Tokens #
--
-- 	********************************
--	 * tokenData : Seed(비밀키(64, hex)) : OTP 생성을 위한 키데이터
--	 * status : '0':AVAILABLE, '1':OCCUPIED, '9':DISCARD
-- 	********************************
--
CREATE TABLE MMTH_Tokens (
	tokenId VARCHAR(12) NOT NULL,
	tokenData VARCHAR(1024) NOT NULL,
	status CHAR(1) NOT NULL,
	authType INT DEFAULT -1,
	username VARCHAR(128) DEFAULT '-',
	lost CHAR(1) DEFAULT 'N' NOT NULL,
	tsReg BIGINT NOT NULL,
	tsOccupied BIGINT DEFAULT 0,
	tsDiscard BIGINT DEFAULT 0,
	tsLost BIGINT DEFAULT 0
);
ALTER TABLE MMTH_Tokens ADD CONSTRAINT MMTH_Tokens_Pk PRIMARY KEY (tokenId);


--
--
-- MMTH_FidoRegistrations *
--
CREATE TABLE MMTH_FidoRegistrations (
    id INT NOT NULL DEFAULT NEXTVAL('MMTH_FidoRegistrations_id_Seq1'),
    aaid CHAR(9) NOT NULL,
    keyId VARCHAR(2048) NOT NULL,
    tsReg BIGINT NOT NULL,
    data BYTEA NOT NULL
);
ALTER TABLE MMTH_FidoRegistrations ADD CONSTRAINT MMTH_FidoRegistrations_Pk PRIMARY KEY (id);
ALTER TABLE MMTH_FidoRegistrations ADD CONSTRAINT MMTH_FidoRegistrations_Un1 UNIQUE (keyId, aaid);

--
--
-- MMTH_UserDevices  *
--
-- 	********************************
--	 * disabled : 사용 일시 정지,  N:enabled, Y:disabled, default 'N'
--	 * status : '0':NOT_AVAILABLE(유효한 기기 등록 건이 없는 경우), '1' :AVAILABLE(유효한 기기 등록 건이 있는 경우), '2' : 사고접수(모든 기기 이용 불가), '9': DISCARD (모든 서비스 해지)
-- 	********************************
--
CREATE TABLE MMTH_UserDevices (
    id INT NOT NULL DEFAULT NEXTVAL('MMTH_UserDevices_id_Seq1'),
    userId INT NOT NULL,
    deviceId VARCHAR(64) NOT NULL,
    deviceType CHAR(2) NOT NULL,
    model VARCHAR(128) NOT NULL,
    alias VARCHAR(128),
    disabled CHAR(1) NOT NULL,
    status CHAR(1) NOT NULL,
    tsReg BIGINT NOT NULL,
    tsUpdated BIGINT DEFAULT 0,
    data BYTEA NOT NULL
);
ALTER TABLE MMTH_UserDevices ADD CONSTRAINT MMTH_UserDevices_Pk PRIMARY KEY (id);
ALTER TABLE MMTH_UserDevices ADD CONSTRAINT MMTH_UserDevices_Un1 UNIQUE (userId, deviceId, deviceType);



--
--
-- MMTH_DeviceAppAgents *
--
-- 	********************************
--	 * disabled : 사용 일시 정지,  N:enabled, Y:disabled, default 'N'
--	 * status : '0':NOT_AVAILABLE(유효한  등록 건이 없는 경우), '1' :AVAILABLE(유효한 등록 건이 있는 경우), '2' : 사고접수(모든 기기 이용 불가), '9': DISCARD (모든 서비스 해지)
-- 	********************************
--
CREATE TABLE MMTH_DeviceAppAgents (
    id INT NOT NULL DEFAULT NEXTVAL('MMTH_DeviceAppAgents_id_Seq1'),
    userDeviceId int NOT NULL,
    agentId INT NOT NULL,
    authType INT NOT NULL,
    status CHAR(1) NOT NULL,
    registrationId VARCHAR(2048),
    tsReg BIGINT NOT NULL,
    tsUpdated BIGINT DEFAULT 0,
    tsDone BIGINT DEFAULT 0,
    tsExpired BIGINT DEFAULT 0
);
ALTER TABLE MMTH_DeviceAppAgents ADD CONSTRAINT MMTH_DeviceAppAgents_Pk PRIMARY KEY (id);
ALTER TABLE MMTH_DeviceAppAgents ADD CONSTRAINT MMTH_DeviceAppAgents_Un1 UNIQUE (userDeviceId, agentId, authType);

--
--
-- MMTH_FidoUsers	*
--
CREATE TABLE MMTH_FidoUsers (
	id INT NOT NULL DEFAULT NEXTVAL('MMTH_FidoUsers_id_Seq1'),
    fidoRegId INT NOT NULL,
    deviceAgentId INT NOT NULL,
    signCntUpdated BIGINT DEFAULT 0 NOT NULL,
    tsReg BIGINT DEFAULT 0 NOT NULL
);
ALTER TABLE MMTH_FidoUsers ADD CONSTRAINT MMTH_FidoUsers_Pk PRIMARY KEY (id);
ALTER TABLE MMTH_FidoUsers ADD CONSTRAINT MMTH_FidoUsers_Un1 UNIQUE (fidoRegId);



--
--
-- MMTH_TokenUsers *
--
CREATE TABLE MMTH_TokenUsers (
	id INT NOT NULL DEFAULT NEXTVAL('MMTH_TokenUsers_id_Seq1'),
    tokenId VARCHAR(12) NOT NULL,
    deviceAgentId INT NOT NULL,
    tsReg BIGINT DEFAULT 0 NOT NULL,
    data BYTEA NOT NULL
);
ALTER TABLE MMTH_TokenUsers ADD CONSTRAINT MMTH_TokenUsers_Pk PRIMARY KEY (id);
ALTER TABLE MMTH_TokenUsers ADD CONSTRAINT MMTH_TokenUsers_Un1 UNIQUE (deviceAgentId);

--
--
-- MMTH_ServerChallenge *
--
-- 	********************************
--	 * challengeType : REG, AUTH, TRANSACTION
--	 * status : '0': FIDO_REQ, '1' : OTP_REQ
-- 	********************************
--
CREATE TABLE MMTH_ServerChallenge (
    id BIGINT NOT NULL DEFAULT NEXTVAL('MMTH_ServerChallenge_id_Seq1'),
    username VARCHAR(128) NOT NULL,
    challenge CHAR(39) NOT NULL,
    challengeType CHAR(1) NOT NULL, 
    status CHAR(1) NOT NULL,
    tsLifeTime BIGINT NOT NULL,
    transactionId VARCHAR(64) NOT NULL,
    tranContent VARCHAR(64),
    deviceAgentId INT DEFAULT -1,
    tsDone BIGINT DEFAULT 0
);
ALTER TABLE MMTH_ServerChallenge ADD CONSTRAINT MMTH_ServerChallenge_Pk PRIMARY KEY (id);
ALTER TABLE MMTH_ServerChallenge ADD CONSTRAINT MMTH_ServerChallenge_Un1 UNIQUE (username);
ALTER TABLE MMTH_ServerChallenge ADD CONSTRAINT MMTH_ServerChallenge_Un2 UNIQUE (transactionId);



--
--
-- MMTH_FidoTransaction 
-- only available challengeType is TRANSACTION
--
CREATE TABLE MMTH_FidoTransaction (
	id BIGINT NOT NULL DEFAULT NEXTVAL('MMTH_FidoTransaction_id_Seq1'),
    challengeId BIGINT NOT NULL,
    fidoRegId INT NOT NULL, 
    tranHash CHAR(64) NOT NULL,
    tsReg BIGINT NOT NULL
);
ALTER TABLE MMTH_FidoTransaction ADD CONSTRAINT MMTH_FidoTransaction_Pk PRIMARY KEY (id);

--
--
-- MMTH_IssueCodeData *
--
CREATE TABLE MMTH_IssueCodeData (
	id INT NOT NULL DEFAULT NEXTVAL('MMTH_IssueCodeData_id_Seq1'),
    username VARCHAR(128) NOT NULL,
    authType INT NOT NULL,
    issueCodeData VARCHAR(128) NOT NULL,
    tsLifeTime BIGINT NOT NULL
);
ALTER TABLE MMTH_IssueCodeData ADD CONSTRAINT MMTH_IssueCodeData_Pk PRIMARY KEY (id);
ALTER TABLE MMTH_IssueCodeData ADD CONSTRAINT MMTH_IssueCodeData_Un1 UNIQUE (username, authType);


--
--
-- MMTH_AuthManager
--
CREATE TABLE MMTH_AuthManager (
	id INT NOT NULL DEFAULT NEXTVAL('MMTH_AuthManager_id_Seq1'),
    userId INT NOT NULL,
    userDeviceId INT NOT NULL,
    authType INT NOT NULL,
    authFailCnt INT NOT NULL,
    totSuccessCnt INT NOT NULL,
    tsLastAuth BIGINT NOT NULL,
    tsLastAuthFail BIGINT NOT NULL,
);
ALTER TABLE MMTH_AuthManager ADD CONSTRAINT MMTH_AuthManager_Pk PRIMARY KEY (id);
ALTER TABLE MMTH_AuthManager ADD CONSTRAINT MMTH_AuthManager_Un1 UNIQUE (userDeviceId, authType);


--
--
-- MMTH_UserServerPwd *
--
CREATE TABLE MMTH_UserServerPwd (
	id INT NOT NULL DEFAULT NEXTVAL('MMTH_UserServerPwd_id_Seq1'),
    userId INT NOT NULL,
    pwdType CHAR(1) NOT NULL,
    serverPin VARCHAR(128) NOT NULL,
    failCnt INT DEFAULT 0 NOT NULL,
    tsReg BIGINT NOT NULL,
    tsUpdated BIGINT DEFAULT 0 NOT NULL,
    tsLatestFailed BIGINT DEFAULT 0 NOT NULL,
    tsExpired BIGINT DEFAULT 0 NOT NULL,
    data BYTEA NOT NULL 
);
ALTER TABLE MMTH_UserServerPwd ADD CONSTRAINT MMTH_UserServerPwd_Pk PRIMARY KEY (id);
ALTER TABLE MMTH_UserServerPwd ADD CONSTRAINT MMTH_UserServerPwd_Un1 UNIQUE (userId, pwdType);


CREATE TABLE MMTH_ExtServiceItems (
	id BIGINT NOT NULL DEFAULT NEXTVAL('MMTH_ExtServiceItems_id_Seq1'),
	userId INT not null,
	transactionId VARCHAR(32) not null,
	externalIdentifier VARCHAR(256) not null,
	status CHAR(1) DEFAULT '0' not null,
	tsReg BIGINT NOT NULL,
	tsExpired BIGINT NOT NULL,
	tsDone BIGINT DEFAULT 0,
	data BYTEA NOT NULL
);
ALTER TABLE MMTH_ExtServiceItems ADD CONSTRAINT MMTH_ExtServiceItems_Pk PRIMARY KEY(id);
ALTER TABLE MMTH_ExtServiceItems ADD CONSTRAINT MMTH_ExtServiceItems_Un1 UNIQUE (externalIdentifier);
ALTER TABLE MMTH_ExtServiceItems ADD CONSTRAINT MMTH_ExtServiceItems_Un2 UNIQUE (transactionId);


CREATE TABLE MMTH_UserAnnotations (
	id INT NOT NULL DEFAULT NEXTVAL('MMTH_UserAnnotations_id_Seq1'),
	userId INT not null,
	username VARCHAR(128),
	displayName VARCHAR(64),
	extUnique VARCHAR(64),
	customerCode CHAR(4),
	countryCode CHAR(2),
	data BYTEA NOT NULL
);
ALTER TABLE MMTH_UserAnnotations ADD CONSTRAINT MMTH_UserAnnotations_Pk PRIMARY KEY (id);
ALTER TABLE MMTH_UserAnnotations ADD CONSTRAINT MMTH_UserAnnotations_Un1 UNIQUE (userId);

CREATE TABLE MMTH_ServiceLogAnnotations (
    serviceLogId BIGINT NOT NULL,
    regEmpNo VARCHAR(32) NOT NULL
);
ALTER TABLE MMTH_ServiceLogAnnotations ADD CONSTRAINT MMTH_ServiceLogAnnotations_Un1 UNIQUE (serviceLogId);

--
--
-- alter foreign key
--
--
ALTER TABLE MMTH_UserDevices ADD CONSTRAINT MMTH_UserDevices_Fk1 FOREIGN KEY (userId) REFERENCES MMTH_Users(id) ON DELETE CASCADE;
ALTER TABLE MMTH_DeviceAppAgents ADD CONSTRAINT MMTH_DeviceAppAgents_Fk1 FOREIGN KEY (userDeviceId) REFERENCES MMTH_UserDevices(id) ON DELETE CASCADE;
ALTER TABLE MMTH_DeviceAppAgents ADD CONSTRAINT MMTH_DeviceAppAgents_Fk2 FOREIGN KEY (agentId) REFERENCES MMTH_AppAgents(id) ON DELETE CASCADE;
ALTER TABLE MMTH_FidoUsers ADD CONSTRAINT MMTH_FidoUsers_Fk1 FOREIGN KEY (deviceAgentId) REFERENCES MMTH_DeviceAppAgents(id) ON DELETE CASCADE;
ALTER TABLE MMTH_FidoUsers ADD CONSTRAINT MMTH_FidoUsers_Fk2 FOREIGN KEY (fidoRegId) REFERENCES MMTH_FidoRegistrations(id) ON DELETE CASCADE;
ALTER TABLE MMTH_TokenUsers ADD CONSTRAINT MMTH_TokenUsers_Fk1 FOREIGN KEY (deviceAgentId) REFERENCES MMTH_DeviceAppAgents(id) ON DELETE CASCADE;
ALTER TABLE MMTH_TokenUsers ADD CONSTRAINT MMTH_TokenUsers_Fk2 FOREIGN KEY (tokenId) REFERENCES MMTH_Tokens(tokenId) ON DELETE CASCADE;
ALTER TABLE MMTH_ServerChallenge ADD CONSTRAINT MMTH_ServerChallenge_Fk1 FOREIGN KEY (username) REFERENCES MMTH_Users(username) ON DELETE CASCADE;
ALTER TABLE MMTH_IssueCodeData ADD CONSTRAINT MMTH_IssueCodeData_Fk1 FOREIGN KEY (username) REFERENCES MMTH_Users(username) ON DELETE CASCADE;
ALTER TABLE MMTH_AuthManager ADD CONSTRAINT MMTH_AuthManager_Fk1 FOREIGN KEY (userDeviceId) REFERENCES MMTH_UserDevices(id) ON DELETE CASCADE;
ALTER TABLE MMTH_UserServerPwd ADD CONSTRAINT MMTH_UserServerPwd_Fk1 FOREIGN KEY (userId) REFERENCES MMTH_Users(id) ON DELETE CASCADE;
ALTER TABLE MMTH_FidoTransaction ADD CONSTRAINT MMTH_FidoTransaction_Fk1 FOREIGN KEY (fidoRegId) REFERENCES MMTH_FidoRegistrations(id) ON DELETE CASCADE;
ALTER TABLE MMTH_FidoTransaction ADD CONSTRAINT MMTH_FidoTransaction_Fk2 FOREIGN KEY (challengeId) REFERENCES MMTH_ServerChallenge(id) ON DELETE CASCADE;
ALTER TABLE MMTH_UserAnnotations ADD CONSTRAINT MMTH_UserAnnotations_Fk1 FOREIGN KEY (userId) REFERENCES MMTH_Users(id) ON DELETE CASCADE;
ALTER TABLE MMTH_ServiceLogAnnotations ADD CONSTRAINT MMTH_ServiceLogAnnotations_Fk1 FOREIGN KEY (serviceLogId) REFERENCES MMTH_ServiceLogs(id) ON DELETE CASCADE;


--
-- AuditAlarms은 등록시간으로 select가 빈번히 수행가능성이 높음
--
CREATE INDEX MMTH_AuditAlarms_Idx1 ON MMTH_AuditAlarms (tsReg, auditType);
--
-- MMTH_ServiceLogs_Idx1 
--
CREATE INDEX MMTH_ServiceLogs_Idx1 ON MMTH_ServiceLogs (tsReg, opType, actionType);
--
-- MMTH_IssuanceHistory_Idx1 
--
CREATE INDEX MMTH_IssuanceHistory_Idx1 ON MMTH_IssuanceHistory (tsIssue, issueType, issueMonth);

--
--
-- VIEW Table MMTH_UserView
--
CREATE VIEW MMTH_UserView AS 
	SELECT  u.id AS id, u.username AS username, u.disabled AS disabled, u.status AS status, u.tsReg AS tsReg, u.tsUpdated AS tsUpdated, u.data AS data, a.displayName AS displayName, a.extUnique AS extUnique, a.customerCode AS customerCode, a.countryCode AS countryCode,a.data AS annotationData
	FROM MMTH_Users u
		LEFT JOIN MMTH_UserAnnotations a ON u.id = a.userId;
