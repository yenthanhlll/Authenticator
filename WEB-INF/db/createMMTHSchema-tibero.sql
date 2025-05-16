--
-- Oracle DDL SCRIPT 
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
-- == DATA TYPE FOR HSQL TO ORACLE
-- INT > NUMBER(10,0)
-- BIGINT > NUMBER(19,0)
-- VARCHAR > VARCHAR2





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
CREATE SEQUENCE MMTH_UserServerPins_id_Seq1 INCREMENT BY 1 START WITH 1;
CREATE SEQUENCE MMTH_ExtServiceItems_id_Seq1 INCREMENT BY 1 START WITH 1;
CREATE SEQUENCE MMTH_UserAnnotations_id_Seq1 INCREMENT BY 1 START WITH 1;
-- For MiraeAsset Vietnam
CREATE SEQUENCE MMTH_UserAuthType_id_Seq1 INCREMENT BY 1 START WITH 1;
CREATE SEQUENCE MMTH_AuthFailCount_id_Seq1 INCREMENT BY 1 START WITH 1;
CREATE SEQUENCE MMTH_HwTokenPolicy_id_Seq1 INCREMENT BY 1 START WITH 1;
CREATE SEQUENCE MMTH_HwTokens_id_Seq1 INCREMENT BY 1 START WITH 1;

--
--
-- # System Settings #
--
CREATE TABLE MMTH_SystemSettings (
	settingName VARCHAR2(64) NOT NULL,
	settingValue CLOB
);
ALTER TABLE MMTH_SystemSettings ADD CONSTRAINT MMTH_SystemSettings_Pk PRIMARY KEY (settingName);


--
--
-- # Administrators #
--
-- 	********************************
--	 * disabled - 사용 일시 정지,  N:enabled, Y:disabled, default 'N'
--   * adminType - '1' : dev, '2' : super, '3' : admin, '4' : permitted admin, '5' : manager(영업점)
-- 	********************************
--
CREATE TABLE MMTH_Administrators (
	id NUMBER(10,0) NOT NULL,
	username VARCHAR2(128) NOT NULL,
	password VARCHAR2(128) NOT NULL,
	adminType CHAR(1) NOT NULL,
	disabled CHAR(1) NOT NULL,
	tsLastLogin NUMBER(19,0) DEFAULT 0,
	lastRemoteAddr VARCHAR2(20),
	homeUrl VARCHAR2(255),
	tsReg NUMBER(19,0) NOT NULL,
    tsUpdated NUMBER(19,0) DEFAULT 0,
    data BLOB NOT NULL
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
    id NUMBER(19,0) NOT NULL,
    auditType NUMBER(10,0) NOT NULL,
    actionType NUMBER(10,0) NOT NULL,
    alarmLevel NUMBER(10,0) NOT NULL,
    description VARCHAR2(1024) NOT NULL,
    tsReg NUMBER(19,0) NOT NULL,
    ack CHAR(1) DEFAULT 'N',
    ackAdmin VARCHAR2(128) DEFAULT '-',
    tsAck NUMBER(19,0) DEFAULT 0 
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
    id NUMBER(10,0) NOT NULL,
    facetId VARCHAR2(512) NOT NULL,
    majorVer NUMBER(10,0) NOT NULL,
    minorVer NUMBER(10,0) NOT NULL,
    alias VARCHAR2(128) NOT NULL,
    disabled CHAR(1) NOT NULL,
    description VARCHAR2(1024) DEFAULT '',
    tsReg NUMBER(19,0) NOT NULL,
    tsUpdated NUMBER(19,0) DEFAULT 0
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
    id NUMBER(10,0) NOT NULL,
    aaid CHAR(9) NOT NULL,
    alias VARCHAR2(128) NOT NULL,
    disabled CHAR(1) NOT NULL,
    userVerification NUMBER(19,0) NOT NULL,
    description VARCHAR2(1024) DEFAULT '',
    tsReg NUMBER(19,0) NOT NULL,
    tsUpdated NUMBER(19,0) DEFAULT 0,
    data BLOB NOT NULL
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
	id NUMBER(19,0) NOT NULL,
    authType NUMBER(10,0) NOT NULL,
    username VARCHAR2(128) NOT NULL,
    opType NUMBER(10,0) NOT NULL,
    reqType NUMBER(10,0) NOT NULL,
    actionType NUMBER(10,0) NOT NULL,
    returnCode CHAR(4),
    deviceId VARCHAR2(64),
    deviceType CHAR(2),
    pkgUnique VARCHAR2(255),
    aaid VARCHAR2(9),
    tokenId VARCHAR2(12),
    description VARCHAR2(1024),
    tsReg NUMBER(19,0) NOT NULL,
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
	id NUMBER(10,0) NOT NULL,
    authType NUMBER(10,0) NOT NULL,
    username VARCHAR2(128) NOT NULL,
    deviceId VARCHAR2(128),
    deviceType CHAR(2),
    pkgUnique VARCHAR2(255),
    issueType CHAR(1) NOT NULL,
    aaid VARCHAR2(9),
    tokenId  VARCHAR2(12),
    tsIssue NUMBER(19,0) NOT NULL,
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
    id NUMBER(10,0) NOT NULL,
    username VARCHAR2(128) NOT NULL,
    accountName VARCHAR2(200) NOT NULL,
    disabled CHAR(1) NOT NULL,
	productType NUMBER(10,0) DEFAULT 64,
    multiLoginYN CHAR(1) DEFAULT 'N',
    status CHAR(1) NOT NULL,
    tsReg NUMBER(19,0) NOT NULL,
    tsUpdated NUMBER(19,0) DEFAULT 0,
    data BLOB NOT NULL 
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
    id NUMBER(10,0) NOT NULL,
    pkgUnique VARCHAR2(255) NOT NULL,
    osType CHAR(1) NOT NULL,
    alias VARCHAR2(128) NOT NULL,
    disabled CHAR(1) NOT NULL,
    description VARCHAR2(1024) DEFAULT '',
    tsReg NUMBER(19,0) NOT NULL,
    tsUpdated NUMBER(19,0) DEFAULT 0,
    data BLOB NOT NULL
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
	tokenId VARCHAR2(12) NOT NULL,
	tokenData VARCHAR2(1024) NOT NULL,
	status CHAR(1) NOT NULL,
	authType NUMBER(10,0) DEFAULT -1,
	username VARCHAR2(128) DEFAULT '-',
	lost CHAR(1) DEFAULT 'N' NOT NULL,
	tsReg NUMBER(19,0) NOT NULL,
	tsOccupied NUMBER(19,0) DEFAULT 0,
	tsDiscard NUMBER(19,0) DEFAULT 0,
	tsLost NUMBER(19,0) DEFAULT 0
);
ALTER TABLE MMTH_Tokens ADD CONSTRAINT MMTH_Tokens_Pk PRIMARY KEY (tokenId);

--
--   For MiraeAsset Vietnam
-- # MMTH_HwTokens (Mirae, OATH, Advanced) #
--
-- 	********************************
--	 * status : '0':AVAILABLE, '1':OCCUPIED, '2':SUSPEND, '9':DISCARD
-- 	********************************
--
CREATE TABLE MMTH_HwTokens (
	id NUMBER(10,0) NOT NULL,
	userId NUMBER(10,0) DEFAULT 0,
	username VARCHAR2(128),
	branchId VARCHAR2(128),
	commend VARCHAR2(64),
	tokenType CHAR(2) NOT NULL,
	tokenId VARCHAR2(12) NOT NULL,
	tokenKey VARCHAR2(256) NOT NULL,
	status CHAR(1) NOT NULL,
	lost CHAR(1) DEFAULT 'N' NOT NULL,
	tsLost NUMBER(19,0) DEFAULT 0,
	unlockCnt NUMBER(10,0) DEFAULT 0,
	tsReg NUMBER(19,0) DEFAULT 0,
	tsOccupied NUMBER(19,0) DEFAULT 0,
	tsSuspend NUMBER(19,0) DEFAULT 0,
	tsDiscard NUMBER(19,0) DEFAULT 0
);
ALTER TABLE MMTH_HwTokens ADD CONSTRAINT MMTH_HwTokens_Pk PRIMARY KEY (id);
ALTER TABLE MMTH_HwTokens ADD CONSTRAINT MMTH_HwTokens_Un1 UNIQUE (tokenId, tokenKey, username);

--
--   For MiraeAsset Vietnam
-- # MMTH_HwTokenPolicy (Mirae) #
--
-- 	********************************
--	 * Hw OTP 정책 설정
-- 	********************************
--
CREATE TABLE  MMTH_HwTokenPolicy (
    id NUMBER(10,0) NOT NULL,
    name VARCHAR2(64) NOT NULL, /* 인증정책 이름 */
    normal_auth_corr_time_skew NUMBER(10,0) DEFAULT 0, /* 일반인증허용범위_시간 */
    user_corr_time_skew NUMBER(10,0) DEFAULT 0, /* 사용자보정허용범위_시간 */
    admin_corr_time_skew NUMBER(10,0) DEFAULT 0, /* 관리자보정허용범위_시간 */
    init_auth_time_skew NUMBER(10,0) DEFAULT 0, /* 초기인증허용범위_시간 */
    long_auth_time_skew NUMBER(10,0) DEFAULT 0, /* 장기미사용허용범위_시간 */
    long_term NUMBER(10,0) DEFAULT 0, /* 장기미사용기간_시간 */
    max_auth_fail_cnt NUMBER(10,0) DEFAULT 10, /* 최대인증실패허용건수 */ 
    ts_change NUMBER(19,0) DEFAULT 0, /* 마지막 변경 시각 */
    ts_create NUMBER(19,0) DEFAULT 0 /* 생성 시각 */
);
ALTER TABLE MMTH_HwTokenPolicy ADD CONSTRAINT MMTH_HwTokenPolicy_Pk PRIMARY KEY (id);
ALTER TABLE MMTH_HwTokenPolicy ADD CONSTRAINT MMTH_HwTokenPolicy_Un1 UNIQUE (name);

--
--
-- MMTH_FidoRegistrations *
--
CREATE TABLE MMTH_FidoRegistrations (
    id NUMBER(10,0) NOT NULL,
    aaid CHAR(9) NOT NULL,
    keyId VARCHAR2(2048) NOT NULL,
    tsReg NUMBER(19,0) NOT NULL,
    data BLOB NOT NULL
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
    id NUMBER(10,0) NOT NULL,
    userId NUMBER(10,0) NOT NULL,
    deviceId VARCHAR2(64) NOT NULL,
    deviceType CHAR(2) NOT NULL,
    model VARCHAR2(128) NOT NULL,
    alias VARCHAR2(128),
    disabled CHAR(1) NOT NULL,
    status CHAR(1) NOT NULL,
    tsReg NUMBER(19,0) NOT NULL,
    tsUpdated NUMBER(19,0) DEFAULT 0,
    data BLOB NOT NULL
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
    id NUMBER(10,0) NOT NULL,
    userDeviceId NUMBER(10,0) NOT NULL,
    agentId NUMBER(10,0) NOT NULL,
    authType NUMBER(10,0) NOT NULL,
    status CHAR(1) NOT NULL,
    registrationId VARCHAR2(2048),
    tsReg NUMBER(19,0) NOT NULL,
    tsUpdated NUMBER(19,0) DEFAULT 0,
    tsDone NUMBER(19,0) DEFAULT 0,
    tsExpired NUMBER(19,0) DEFAULT 0
);
ALTER TABLE MMTH_DeviceAppAgents ADD CONSTRAINT MMTH_DeviceAppAgents_Pk PRIMARY KEY (id);
ALTER TABLE MMTH_DeviceAppAgents ADD CONSTRAINT MMTH_DeviceAppAgents_Un1 UNIQUE (userDeviceId, agentId, authType);

--
--
-- MMTH_FidoUsers	*
--
CREATE TABLE MMTH_FidoUsers (
	id NUMBER(10,0) NOT NULL,
    fidoRegId NUMBER(10,0) NOT NULL,
    deviceAgentId NUMBER(10,0) NOT NULL,
    signCntUpdated NUMBER(19,0) DEFAULT 0 NOT NULL,
    tsReg NUMBER(19,0) DEFAULT 0 NOT NULL
);
ALTER TABLE MMTH_FidoUsers ADD CONSTRAINT MMTH_FidoUsers_Pk PRIMARY KEY (id);
ALTER TABLE MMTH_FidoUsers ADD CONSTRAINT MMTH_FidoUsers_Un1 UNIQUE (fidoRegId);



--
--
-- MMTH_TokenUsers *
--
CREATE TABLE MMTH_TokenUsers (
	id NUMBER(10,0) NOT NULL,
    tokenId VARCHAR2(12) NOT NULL,
    deviceAgentId NUMBER(10,0) NOT NULL,
    tsReg NUMBER(19,0) DEFAULT 0 NOT NULL,
    data BLOB NOT NULL
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
    id NUMBER(19,0) NOT NULL,
    username VARCHAR2(128) NOT NULL,
    challenge CHAR(39) NOT NULL,
    challengeType CHAR(1) NOT NULL, 
    status CHAR(1) NOT NULL,
    tsLifeTime NUMBER(19,0) NOT NULL,
    transactionId VARCHAR2(64) NOT NULL,
    tranContent VARCHAR2(64),
    deviceAgentId NUMBER(10,0) DEFAULT -1,
    tsDone NUMBER(19,0) DEFAULT 0
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
	id NUMBER(19,0) NOT NULL,
    challengeId NUMBER(19,0) NOT NULL,
    fidoRegId NUMBER(10,0) NOT NULL, 
    tranHash CHAR(64) NOT NULL,
    tsReg NUMBER(19,0) NOT NULL
);
ALTER TABLE MMTH_FidoTransaction ADD CONSTRAINT MMTH_FidoTransaction_Pk PRIMARY KEY (id);

--
--
-- MMTH_IssueCodeData *
--
CREATE TABLE MMTH_IssueCodeData (
	id NUMBER(10,0) NOT NULL,
    username VARCHAR2(128) NOT NULL,
    authType NUMBER(10,0) NOT NULL,
    issueCodeData VARCHAR2(128) NOT NULL,
    tsLifeTime NUMBER(19,0) NOT NULL
);
ALTER TABLE MMTH_IssueCodeData ADD CONSTRAINT MMTH_IssueCodeData_Pk PRIMARY KEY (id);
ALTER TABLE MMTH_IssueCodeData ADD CONSTRAINT MMTH_IssueCodeData_Un1 UNIQUE (username, authType);


--
--
-- MMTH_AuthManager
--
CREATE TABLE MMTH_AuthManager (
	id NUMBER(10,0) NOT NULL,
    userId NUMBER(10,0) NOT NULL,
    userDeviceId NUMBER(10,0) NOT NULL,
    authType NUMBER(10,0) NOT NULL,
    authFailCnt NUMBER(10,0) NOT NULL,
    totSuccessCnt NUMBER(10,0) NOT NULL,
    tsLastAuth NUMBER(19,0) NOT NULL,
    tsLastAuthFail NUMBER(19,0) NOT NULL
);
ALTER TABLE MMTH_AuthManager ADD CONSTRAINT MMTH_AuthManager_Pk PRIMARY KEY (id);
ALTER TABLE MMTH_AuthManager ADD CONSTRAINT MMTH_AuthManager_Un1 UNIQUE (userDeviceId, authType);


--
--
-- MMTH_UserServerPins *
--
CREATE TABLE MMTH_UserServerPins (
	id NUMBER(10,0) NOT NULL,
    userId NUMBER(10,0) NOT NULL,
    serverPin VARCHAR2(128) NOT NULL,
    failCnt NUMBER(10,0) DEFAULT 0 NOT NULL,
    tsReg NUMBER(19,0) NOT NULL,
    tsUpdated NUMBER(19,0) DEFAULT 0 NOT NULL,
    data BLOB NOT NULL 
);
ALTER TABLE MMTH_UserServerPins ADD CONSTRAINT MMTH_UserServerPins_Pk PRIMARY KEY (id);
ALTER TABLE MMTH_UserServerPins ADD CONSTRAINT MMTH_UserServerPins_Un1 UNIQUE (userId);


CREATE TABLE MMTH_ExtServiceItems (
	id NUMBER(19,0) NOT NULL,
	userId NUMBER(10,0) not null,
	transactionId VARCHAR2(32) not null,
	externalIdentifier VARCHAR2(256) not null,
	status CHAR(1) DEFAULT '0' not null,
	tsReg NUMBER(19,0) NOT NULL,
	tsExpired NUMBER(19,0) NOT NULL,
	tsDone NUMBER(19,0) DEFAULT 0,
	data BLOB NOT NULL
);
ALTER TABLE MMTH_ExtServiceItems ADD CONSTRAINT MMTH_ExtServiceItems_Pk PRIMARY KEY(id);
ALTER TABLE MMTH_ExtServiceItems ADD CONSTRAINT MMTH_ExtServiceItems_Un1 UNIQUE (externalIdentifier);
ALTER TABLE MMTH_ExtServiceItems ADD CONSTRAINT MMTH_ExtServiceItems_Un2 UNIQUE (transactionId);


CREATE TABLE MMTH_UserAnnotations (
	id NUMBER(10,0) NOT NULL,
	userId NUMBER(10,0) NOT NULL,
	username VARCHAR2(128),
	displayName VARCHAR2(64),
	extUnique VARCHAR2(64),
	customerCode CHAR(4),
	countryCode CHAR(2),
	data BLOB NOT NULL
);
ALTER TABLE MMTH_UserAnnotations ADD CONSTRAINT MMTH_UserAnnotations_Pk PRIMARY KEY (id);
ALTER TABLE MMTH_UserAnnotations ADD CONSTRAINT MMTH_UserAnnotations_Un1 UNIQUE (userId);

CREATE TABLE MMTH_ServiceLogAnnotations (
    serviceLogId NUMBER(19,0) NOT NULL,
    regEmpNo VARCHAR2(32),
	ip NUMBER(19,0),
    macAddress VARCHAR2(128)
);
ALTER TABLE MMTH_ServiceLogAnnotations ADD CONSTRAINT MMTH_ServiceLogAnnotations_Un1 UNIQUE (serviceLogId);

-- 
--
-- For MiraeAsset Vietnam
-- MMTH_UserMultiFactorAuthType
		
CREATE TABLE MMTH_UserMultiFactorAuthType
(
	id NUMBER(10,0) NOT NULL,
	-- 내부 테이블 식별 아이디 (int)
	userId NUMBER(10,0) NOT NULL,
	productType NUMBER(10,0) NOT NULL,
	-- 1: PATTERN
	-- 2: PIN
	authType NUMBER(10,0) NOT NULL,
	-- 인증데이터(sha256+Hex)
	value VARCHAR2(256) NOT NULL,
	tsUpdated NUMBER(19,0),
	tsReg NUMBER(19,0)
);
ALTER TABLE MMTH_UserMultiFactorAuthType ADD CONSTRAINT MMTH_UserAuthType_Pk PRIMARY KEY (id);
ALTER TABLE MMTH_UserMultiFactorAuthType ADD CONSTRAINT MMTH_UserAuthType_Un UNIQUE (userId, authType);

-- 
--
-- For MiraeAsset Vietnam
-- MMTH_AuthFailCount
	
CREATE TABLE MMTH_AuthFailCount
(
	id NUMBER(10,0) NOT NULL,
	-- 멀티 팩터 인증매체 식별자
	authTypeId NUMBER(10,0) NOT NULL UNIQUE,
	-- 오류횟수 카운트
	failCnt NUMBER(10,0),
	tsLastAuth NUMBER(19,0),
	tsLastAuthFail NUMBER(19,0)
);
ALTER TABLE MMTH_AuthFailCount ADD CONSTRAINT MMTH_AuthFailCount_Pk PRIMARY KEY (id);
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
ALTER TABLE MMTH_UserServerPins ADD CONSTRAINT MMTH_UserServerPins_Fk1 FOREIGN KEY (userId) REFERENCES MMTH_Users(id) ON DELETE CASCADE;
ALTER TABLE MMTH_FidoTransaction ADD CONSTRAINT MMTH_FidoTransaction_Fk1 FOREIGN KEY (fidoRegId) REFERENCES MMTH_FidoRegistrations(id) ON DELETE CASCADE;
ALTER TABLE MMTH_FidoTransaction ADD CONSTRAINT MMTH_FidoTransaction_Fk2 FOREIGN KEY (challengeId) REFERENCES MMTH_ServerChallenge(id) ON DELETE CASCADE;
ALTER TABLE MMTH_UserAnnotations ADD CONSTRAINT MMTH_UserAnnotations_Fk1 FOREIGN KEY (userId) REFERENCES MMTH_Users(id) ON DELETE CASCADE;
ALTER TABLE MMTH_ServiceLogAnnotations ADD CONSTRAINT MMTH_ServiceLogAnnotations_Fk1 FOREIGN KEY (serviceLogId) REFERENCES MMTH_ServiceLogs(id) ON DELETE CASCADE;
ALTER TABLE MMTH_AuthFailCount ADD CONSTRAINT MMTH_AuthFailCount_Fk1 FOREIGN KEY (authTypeId) REFERENCES MMTH_UserMultiFactorAuthType(id) ON DELETE CASCADE;
ALTER TABLE MMTH_UserMultiFactorAuthType ADD CONSTRAINT MMTH_UserAuthType_Fk FOREIGN KEY (userId) REFERENCES MMTH_Users(id) ON DELETE CASCADE;
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
	SELECT  u.id AS id, u.username AS username, u.productType AS productType, u.multiLoginYN AS multiLoginYN, u.disabled AS disabled, u.status AS status, u.tsReg AS tsReg, u.tsUpdated AS tsUpdated, u.data AS data, a.displayName AS displayName, a.extUnique AS extUnique, a.customerCode AS customerCode, a.countryCode AS countryCode, a.data AS annotationData
	FROM MMTH_Users u
		LEFT JOIN MMTH_UserAnnotations a ON u.id = a.userId;

--
-- /////////////////////////
-- //	create trigger
-- /////////////////////////
--

-- MMTH_Administrators
CREATE OR REPLACE TRIGGER MMTH_Administrators_id_Tr1
BEFORE INSERT ON MMTH_Administrators 
REFERENCING NEW AS NEW FOR EACH ROW 
BEGIN 
    SELECT MMTH_Administrators_id_Seq1.NEXTVAL
    INTO :NEW.id
    FROM DUAL;
END;

-- MMTH_AuditAlarms
CREATE OR REPLACE TRIGGER MMTH_AuditAlarms_id_Tr1
BEFORE INSERT ON MMTH_AuditAlarms 
REFERENCING NEW AS NEW FOR EACH ROW 
BEGIN 
    SELECT MMTH_AuditAlarms_id_Seq1.NEXTVAL
    INTO :NEW.id
    FROM DUAL;
END;

-- MMTH_FidoFacets
CREATE OR REPLACE TRIGGER MMTH_FidoFacets_id_Tr1
BEFORE INSERT ON MMTH_FidoFacets 
REFERENCING NEW AS NEW FOR EACH ROW 
BEGIN 
    SELECT MMTH_FidoFacets_id_Seq1.NEXTVAL
    INTO :NEW.id
    FROM DUAL;
END;

-- MMTH_FidoMetadata
CREATE OR REPLACE TRIGGER MMTH_FidoMetadata_id_Tr1
BEFORE INSERT ON MMTH_FidoMetadata 
REFERENCING NEW AS NEW FOR EACH ROW 
BEGIN 
    SELECT MMTH_FidoMetadata_id_Seq1.NEXTVAL
    INTO :NEW.id
    FROM DUAL;
END;

-- MMTH_ServiceLogs
CREATE OR REPLACE TRIGGER MMTH_ServiceLogs_id_Tr1
BEFORE INSERT ON MMTH_ServiceLogs 
REFERENCING NEW AS NEW FOR EACH ROW 
BEGIN 
    SELECT MMTH_ServiceLogs_id_Seq1.NEXTVAL
    INTO :NEW.id
    FROM DUAL;
END;
-- MMTH_IssuanceHistory
CREATE OR REPLACE TRIGGER MMTH_IssuanceHistory_id_Tr1
BEFORE INSERT ON MMTH_IssuanceHistory 
REFERENCING NEW AS NEW FOR EACH ROW 
BEGIN 
    SELECT MMTH_IssuanceHistory_id_Seq1.NEXTVAL
    INTO :NEW.id
    FROM DUAL;
END;

-- MMTH_Users
CREATE OR REPLACE TRIGGER MMTH_Users_id_Tr1
BEFORE INSERT ON MMTH_Users 
REFERENCING NEW AS NEW FOR EACH ROW 
BEGIN 
    SELECT MMTH_Users_id_Seq1.NEXTVAL
    INTO :NEW.id
    FROM DUAL;
END;

-- MMTH_AppAgents
CREATE OR REPLACE TRIGGER MMTH_AppAgents_id_Tr1
BEFORE INSERT ON MMTH_AppAgents 
REFERENCING NEW AS NEW FOR EACH ROW 
BEGIN 
    SELECT MMTH_AppAgents_id_Seq1.NEXTVAL
    INTO :NEW.id
    FROM DUAL;
END;

-- MMTH_FidoRegistrations
CREATE OR REPLACE TRIGGER MMTH_FidoRegistrations_id_Tr1
BEFORE INSERT ON MMTH_FidoRegistrations 
REFERENCING NEW AS NEW FOR EACH ROW 
BEGIN 
    SELECT MMTH_FidoRegistrations_id_Seq1.NEXTVAL
    INTO :NEW.id
    FROM DUAL;
END;

-- MMTH_UserDevices
CREATE OR REPLACE TRIGGER MMTH_UserDevices_id_Tr1
BEFORE INSERT ON MMTH_UserDevices 
REFERENCING NEW AS NEW FOR EACH ROW 
BEGIN 
    SELECT MMTH_UserDevices_id_Seq1.NEXTVAL
    INTO :NEW.id
    FROM DUAL;
END;

-- MMTH_DeviceAppAgents
CREATE OR REPLACE TRIGGER MMTH_DeviceAppAgents_id_Tr1
BEFORE INSERT ON MMTH_DeviceAppAgents 
REFERENCING NEW AS NEW FOR EACH ROW 
BEGIN 
    SELECT MMTH_DeviceAppAgents_id_Seq1.NEXTVAL
    INTO :NEW.id
    FROM DUAL;
END;

-- MMTH_FidoUsers
CREATE OR REPLACE TRIGGER MMTH_FidoUsers_id_Tr1
BEFORE INSERT ON MMTH_FidoUsers 
REFERENCING NEW AS NEW FOR EACH ROW 
BEGIN 
    SELECT MMTH_FidoUsers_id_Seq1.NEXTVAL
    INTO :NEW.id
    FROM DUAL;
END;

-- MMTH_TokenUsers
CREATE OR REPLACE TRIGGER MMTH_TokenUsers_id_Tr1
BEFORE INSERT ON MMTH_TokenUsers 
REFERENCING NEW AS NEW FOR EACH ROW 
BEGIN 
    SELECT MMTH_TokenUsers_id_Seq1.NEXTVAL
    INTO :NEW.id
    FROM DUAL;
END;

-- MMTH_ServerChallenge
CREATE OR REPLACE TRIGGER MMTH_ServerChallenge_id_Tr1
BEFORE INSERT ON MMTH_ServerChallenge 
REFERENCING NEW AS NEW FOR EACH ROW 
BEGIN 
    SELECT MMTH_ServerChallenge_id_Seq1.NEXTVAL
    INTO :NEW.id
    FROM DUAL;
END;

-- MMTH_FidoTransaction
CREATE OR REPLACE TRIGGER MMTH_FidoTransaction_id_Tr1
BEFORE INSERT ON MMTH_FidoTransaction 
REFERENCING NEW AS NEW FOR EACH ROW 
BEGIN 
    SELECT MMTH_FidoTransaction_id_Seq1.NEXTVAL
    INTO :NEW.id
    FROM DUAL;
END;

-- MMTH_IssueCodeData
CREATE OR REPLACE TRIGGER MMTH_IssueCodeData_id_Tr1
BEFORE INSERT ON MMTH_IssueCodeData 
REFERENCING NEW AS NEW FOR EACH ROW 
BEGIN 
    SELECT MMTH_IssueCodeData_id_Seq1.NEXTVAL
    INTO :NEW.id
    FROM DUAL;
END;

-- MMTH_AuthManager
CREATE OR REPLACE TRIGGER MMTH_AuthManager_id_Tr1
BEFORE INSERT ON MMTH_AuthManager 
REFERENCING NEW AS NEW FOR EACH ROW 
BEGIN 
    SELECT MMTH_AuthManager_id_Seq1.NEXTVAL
    INTO :NEW.id
    FROM DUAL;
END;

-- MMTH_UserServerPins
CREATE OR REPLACE TRIGGER MMTH_UserServerPins_id_Tr1
BEFORE INSERT ON MMTH_UserServerPins 
REFERENCING NEW AS NEW FOR EACH ROW 
BEGIN 
    SELECT MMTH_UserServerPins_id_Seq1.NEXTVAL
    INTO :NEW.id
    FROM DUAL;
END;

-- MMTH_ExtServiceItems
CREATE OR REPLACE TRIGGER MMTH_ExtServiceItems_id_Tr1
BEFORE INSERT ON MMTH_ExtServiceItems 
REFERENCING NEW AS NEW FOR EACH ROW 
BEGIN 
    SELECT MMTH_ExtServiceItems_id_Seq1.NEXTVAL
    INTO :NEW.id
    FROM DUAL;
END;

-- MMTH_UserAnnotations
CREATE OR REPLACE TRIGGER MMTH_UserAnnotations_id_Tr1
BEFORE INSERT ON MMTH_UserAnnotations 
REFERENCING NEW AS NEW FOR EACH ROW 
BEGIN 
    SELECT MMTH_UserAnnotations_id_Seq1.NEXTVAL
    INTO :NEW.id
    FROM DUAL;
END;

-- MMTH_UserMultiFactorAuthType
CREATE OR REPLACE TRIGGER MMTH_UserAuthType_id_Tr1
BEFORE INSERT ON MMTH_UserMultiFactorAuthType 
REFERENCING NEW AS NEW FOR EACH ROW 
BEGIN 
    SELECT MMTH_UserAuthType_id_Seq1.NEXTVAL
    INTO :NEW.id
    FROM DUAL;
END;

-- MMTH_AuthFailCount
CREATE OR REPLACE TRIGGER MMTH_AuthFailCount_id_Tr1
BEFORE INSERT ON MMTH_AuthFailCount 
REFERENCING NEW AS NEW FOR EACH ROW 
BEGIN 
    SELECT MMTH_AuthFailCount_id_Seq1.NEXTVAL
    INTO :NEW.id
    FROM DUAL;
END;

-- MMTH_HwTokenPolicy
CREATE OR REPLACE TRIGGER MMTH_HwTokenPolicy_id_Tr1
BEFORE INSERT ON MMTH_HwTokenPolicy 
REFERENCING NEW AS NEW FOR EACH ROW 
BEGIN 
    SELECT MMTH_HwTokenPolicy_id_Seq1.NEXTVAL
    INTO :NEW.id
    FROM DUAL;
END;

-- MMTH_HwTokens
CREATE OR REPLACE TRIGGER MMTH_HwTokens_id_Tr1
BEFORE INSERT ON MMTH_HwTokens 
REFERENCING NEW AS NEW FOR EACH ROW 
BEGIN 
    SELECT MMTH_HwTokens_id_Seq1.NEXTVAL
    INTO :NEW.id
    FROM DUAL;
END;

--
--  TABLE COMMENTS SCRIPT
--

--
--
-- # System Settings #
--

COMMENT ON TABLE MMTH_SystemSettings IS '서버 서비스 정책에 대한 설정 값을 관리하는 테이블';

COMMENT ON COLUMN MMTH_SystemSettings.settingName IS '정책설정 이름';
COMMENT ON COLUMN MMTH_SystemSettings.settingValue IS '정책설정 값';

--
--
-- # Administrators #
--
--

COMMENT ON TABLE MMTH_Administrators IS '서버 웹 관리자 정보를 위한 테이블';

COMMENT ON COLUMN MMTH_Administrators.id IS '기본 키(PK)';
COMMENT ON COLUMN MMTH_Administrators.username IS '웹 관리자 아이디 (Unique)';
COMMENT ON COLUMN MMTH_Administrators.password IS '웹 관리자 비밀번호';
COMMENT ON COLUMN MMTH_Administrators.adminType IS '웹 관리자 유형 (SUPER, ADMIN, PERMITTED)';
COMMENT ON COLUMN MMTH_Administrators.disabled IS '계정 활성화 여부 ( N:활성화, Y:비활성화, default N)';
COMMENT ON COLUMN MMTH_Administrators.tsLastLogin IS '마지막 로그인 시간';
COMMENT ON COLUMN MMTH_Administrators.lastRemoteAddr IS '최근 접속 주소';
COMMENT ON COLUMN MMTH_Administrators.homeUrl IS '디폴트 웹 관리자 페이지';
COMMENT ON COLUMN MMTH_Administrators.tsReg IS '생성 시간';
COMMENT ON COLUMN MMTH_Administrators.tsUpdated IS '수정 시간';
COMMENT ON COLUMN MMTH_Administrators.data IS '확장 프로퍼티 데이터';


--
--
-- # MMTH_AuditAlarms #	
--

COMMENT ON TABLE MMTH_AuditAlarms IS '서비스 설정 변경 등 서버 감사알람';

COMMENT ON COLUMN MMTH_AuditAlarms.id IS '기본 키(PK)';
COMMENT ON COLUMN MMTH_AuditAlarms.auditType IS '감사 알람 유형';
COMMENT ON COLUMN MMTH_AuditAlarms.actionType IS '세부 동작 유형';
COMMENT ON COLUMN MMTH_AuditAlarms.alarmLevel IS '알람 레벨 (NONE, INFORMATION, URGENT, CRITICAL, LIFE_SAFETY)';
COMMENT ON COLUMN MMTH_AuditAlarms.description IS '알람 내용';
COMMENT ON COLUMN MMTH_AuditAlarms.tsReg IS '생성 시간';
COMMENT ON COLUMN MMTH_AuditAlarms.ack IS '알람 확인 여부';
COMMENT ON COLUMN MMTH_AuditAlarms.ackAdmin IS '알람 확인 웹 관리자';
COMMENT ON COLUMN MMTH_AuditAlarms.tsAck IS '알람 확인 시간';


--
--
--  # MMTH_FidoFacets #
--

COMMENT ON TABLE MMTH_FidoFacets IS '서비스를 이용하기 위해 Android, IOS 등 다양한 플랫폼에서 구현한 신뢰하는 어플리케이션(Facet ID) 목록을 관리하는 테이블';

COMMENT ON COLUMN MMTH_FidoFacets.id IS '기본 키(PK)';
COMMENT ON COLUMN MMTH_FidoFacets.facetId IS 'Facet ID';
COMMENT ON COLUMN MMTH_FidoFacets.majorVer IS 'FIDO 버전 - Major Version';
COMMENT ON COLUMN MMTH_FidoFacets.minorVer IS 'FIDO 버전 - Minor Version';
COMMENT ON COLUMN MMTH_FidoFacets.alias IS 'Facet ID 별칭';
COMMENT ON COLUMN MMTH_FidoFacets.disabled IS 'FIDO 정책 사용 일시 정지 (N:사용, Y:사용안함, default N)';
COMMENT ON COLUMN MMTH_FidoFacets.description IS '설명';
COMMENT ON COLUMN MMTH_FidoFacets.tsReg IS '생성 시간';
COMMENT ON COLUMN MMTH_FidoFacets.tsUpdated IS '수정 시간';


--
--
-- # MMTH_FidoMetadata #
--

COMMENT ON TABLE MMTH_FidoMetadata IS 'FIDO 인증장치의 Metadata 정보를 관리하기 위한 테이블';

COMMENT ON COLUMN MMTH_FidoMetadata.id IS '기본 키(PK)';
COMMENT ON COLUMN MMTH_FidoMetadata.aaid IS 'Authenticator Attestation ID. FIDO 인증장치 아이디';
COMMENT ON COLUMN MMTH_FidoMetadata.alias IS '메타데이타 별칭';
COMMENT ON COLUMN MMTH_FidoMetadata.disabled IS 'FIDO 정책 사용 일시 정지 (N:사용, Y:사용안함, default N)';
COMMENT ON COLUMN MMTH_FidoMetadata.userVerification IS 'FIDO 인증장치 사용자 확인 수단';
COMMENT ON COLUMN MMTH_FidoMetadata.description IS '설명';
COMMENT ON COLUMN MMTH_FidoMetadata.tsReg IS '생성 시간';
COMMENT ON COLUMN MMTH_FidoMetadata.tsUpdated IS '수정 시간';
COMMENT ON COLUMN MMTH_FidoMetadata.data IS 'FIDO 메타데이터 정보';


--
--
-- # MMTH_AppAgents #
--
COMMENT ON TABLE MMTH_AppAgents IS '이용가능한 클라이언트 앱 정보를 관리하기 위한 테이블';

COMMENT ON COLUMN MMTH_AppAgents.id IS '기본 키(PK)';
COMMENT ON COLUMN MMTH_AppAgents.pkgUnique IS 'App Agent를 구분하는 이름 (패키지 이름)';
COMMENT ON COLUMN MMTH_AppAgents.osType IS '디바이스 OS 유형을 구별하는 코드 (X : conformance/NA, A : 안드로이드, I : IOS)';
COMMENT ON COLUMN MMTH_AppAgents.alias IS '앱 별칭';
COMMENT ON COLUMN MMTH_AppAgents.disabled IS '정책 사용 일시 정지 (N:사용, Y:사용안함, default N)';
COMMENT ON COLUMN MMTH_AppAgents.description IS '설명';
COMMENT ON COLUMN MMTH_AppAgents.tsReg IS '생성 시간';
COMMENT ON COLUMN MMTH_AppAgents.tsUpdated IS '수정 시간';
COMMENT ON COLUMN MMTH_AppAgents.data IS '확장 데이타';



--
--
-- # MMTH_ServiceLogs #
--


COMMENT ON TABLE MMTH_ServiceLogs IS '사용자 서비스 로그, 사용자 등록/인증/해지 처리에 대한 각 단계 별 로그';

COMMENT ON COLUMN MMTH_ServiceLogs.id IS '기본 키(PK)';
COMMENT ON COLUMN MMTH_ServiceLogs.authType IS '인증매체유형 (0x01:FIDO,0x02:SAO,0x04:BIOTP)';
COMMENT ON COLUMN MMTH_ServiceLogs.username IS '사용자 식별자';
COMMENT ON COLUMN MMTH_ServiceLogs.opType IS '서비스 동작 유형 (0x1001 : ISSUE_CODE, 0x1002 : ISSUE_CODE_ONLINE, 0x2001 : REG, 0x3001 : DEREG, 0x3002 : FORCE_DEREG, 0x4001 : FIDO_AUTH, 0x4002 : BIOTP_AUTH, 0x4003 : SAOTP_AUTH, 0x5001 : LOST, 0x5002 : RESET_AUTH_FAIL)';
COMMENT ON COLUMN MMTH_ServiceLogs.actionType IS '세부 동작 유형 (0x01:REQ, 0x02:FAIL, 0x03:DONE)';
COMMENT ON COLUMN MMTH_ServiceLogs.returnCode IS '처리 결과코드';
COMMENT ON COLUMN MMTH_ServiceLogs.deviceId IS '디바이스(기기)를 구별하는 식별자';
COMMENT ON COLUMN MMTH_ServiceLogs.deviceType IS '디바이스(기기) 유형을 구별하는 코드 (XX: conformance/NA, AD : 안드로이드, AW : 안드로이드 WiFi, IU : IOS)';
COMMENT ON COLUMN MMTH_ServiceLogs.pkgUnique IS '기기 모델';
COMMENT ON COLUMN MMTH_ServiceLogs.aaid IS 'AAID';
COMMENT ON COLUMN MMTH_ServiceLogs.tokenId IS 'Token ID';
COMMENT ON COLUMN MMTH_ServiceLogs.description IS '설명';
COMMENT ON COLUMN MMTH_ServiceLogs.tsReg IS '생성 시간';
COMMENT ON COLUMN MMTH_ServiceLogs.regDate IS '생성 일';
COMMENT ON COLUMN MMTH_ServiceLogs.regHour IS '생성 시';


--
--
-- # MMTH_IssuanceHistory #
--

COMMENT ON TABLE MMTH_IssuanceHistory IS '사용자의 신규/추가_교체 발급현황을 기록하기 위한 테이블';

COMMENT ON COLUMN MMTH_IssuanceHistory.id IS '기본 키(PK)';
COMMENT ON COLUMN MMTH_IssuanceHistory.authType IS '인증매체유형 (0x01:FIDO,0x02:SAO,0x04:BIOTP)';
COMMENT ON COLUMN MMTH_IssuanceHistory.username IS '사용자 식별자';
COMMENT ON COLUMN MMTH_IssuanceHistory.deviceId IS '디바이스(기기)를 구별하는 식별자';
COMMENT ON COLUMN MMTH_IssuanceHistory.deviceType IS '디바이스(기기) 유형을 구별하는 코드 (XX: conformance/NA, AD : 안드로이드, AW : 안드로이드 WiFi, IU : IOS)';
COMMENT ON COLUMN MMTH_IssuanceHistory.pkgUnique IS 'App Agent를 구분하는 이름';
COMMENT ON COLUMN MMTH_IssuanceHistory.issueType IS '1:신규사용자 발급, 2:기존사용자의 기기추가, 3:앱추가등록';
COMMENT ON COLUMN MMTH_IssuanceHistory.aaid IS 'Authenticator Attestation ID. FIDO인증장치아이디';
COMMENT ON COLUMN MMTH_IssuanceHistory.tokenId IS 'OTP일련번호(토큰일련번호)';
COMMENT ON COLUMN MMTH_IssuanceHistory.tsIssue IS '발급 시간';
COMMENT ON COLUMN MMTH_IssuanceHistory.issueMonth IS '발급 YYYYMM';
COMMENT ON COLUMN MMTH_IssuanceHistory.issueDate IS '발급 YYYYMMDD';
COMMENT ON COLUMN MMTH_IssuanceHistory.issueTime IS '발급 HHmmss';


--
--
-- # MMTH_Users #
--

COMMENT ON TABLE MMTH_Users IS '서비스를 이용하는 모든 사용자 정보';

COMMENT ON COLUMN MMTH_Users.id IS '기본 키(PK)';
COMMENT ON COLUMN MMTH_Users.username IS '사용자 식별자';
COMMENT ON COLUMN MMTH_Users.disabled IS '사용 일시 정지, N:enabled, Y:disabled, default N';
COMMENT ON COLUMN MMTH_Users.status IS '0:NOT_AVAILABLE(유효한 기기 (인증매체) 등록 건이 없는 경우), 1 :AVAILABLE(유효한 기기 (인증 매체) 등록 건이 있는 경우), 2 : 사고접수(모든 기기 및 인증 매체 이용 불가), 9: DISCARD (탈회, 모든 서비스 해지)';
COMMENT ON COLUMN MMTH_Users.tsReg IS '생성 시간';
COMMENT ON COLUMN MMTH_Users.tsUpdated IS '수정 시간';
COMMENT ON COLUMN MMTH_Users.data IS '사용자 프로퍼티 확장 데이터';



--
--
-- # MMTH_Tokens #
--

COMMENT ON TABLE MMTH_Tokens IS 'OTP 생성을 위한 OTP데이터를 관리하는 토큰 테이블';

COMMENT ON COLUMN MMTH_Tokens.tokenId IS '토큰(OTP키) 일련번호 (PK)';
COMMENT ON COLUMN MMTH_Tokens.tokenData IS 'OTP 생성을 위한 키 데이터 (암호화 됨)';
COMMENT ON COLUMN MMTH_Tokens.status IS '0:발급가능, 1:발급불가능, 9:폐기';
COMMENT ON COLUMN MMTH_Tokens.authType IS '인증매체유형 (0x01:FIDO,0x02:SAO,0x04:BIOTP)';
COMMENT ON COLUMN MMTH_Tokens.lost IS '사고신고 (Y/N)';
COMMENT ON COLUMN MMTH_Tokens.username IS '사용자 식별자';
COMMENT ON COLUMN MMTH_Tokens.tsReg IS '생성 시간';
COMMENT ON COLUMN MMTH_Tokens.tsOccupied IS '토큰 등 할당시간';
COMMENT ON COLUMN MMTH_Tokens.tsDiscard IS '폐기 시간';
COMMENT ON COLUMN MMTH_Tokens.tsLost IS '사고신고 시간';



--
--
-- # MMTH_FidoRegistrations #
--
COMMENT ON TABLE MMTH_FidoRegistrations IS 'FIDO 등록정보를 관리하기 위한 테이블';

COMMENT ON COLUMN MMTH_FidoRegistrations.id IS '기본 키(PK)';
COMMENT ON COLUMN MMTH_FidoRegistrations.aaid IS 'Authenticator Attestation ID. 인증장치';
COMMENT ON COLUMN MMTH_FidoRegistrations.keyId IS 'FIDO 등록 시 인증장치에서 생성한 Key ID';
COMMENT ON COLUMN MMTH_FidoRegistrations.tsReg IS '생성 시간';
COMMENT ON COLUMN MMTH_FidoRegistrations.data IS '확장 데이타';

--
--
-- # MMTH_UserDevices  #
--
COMMENT ON TABLE MMTH_UserDevices IS '서비스를 이용하는 사용자의 단말정보를 관리하기 위한 테이블';

COMMENT ON COLUMN MMTH_UserDevices.id IS '기본 키(PK)';
COMMENT ON COLUMN MMTH_UserDevices.userId IS '사용자 외래키 : Users(id)';
COMMENT ON COLUMN MMTH_UserDevices.deviceId IS '디바이스(기기)를 구별하는 식별자';
COMMENT ON COLUMN MMTH_UserDevices.deviceType IS '디바이스(기기) OS 유형을 구별하는 코드 (XX: conformance/NA, AD : 안드로이드, AW : 안드로이드 WiFi, IU : IOS)';
COMMENT ON COLUMN MMTH_UserDevices.model IS '디바이스(기기) 모델명';
COMMENT ON COLUMN MMTH_UserDevices.alias IS '사용자 정의 기기 별칭';
COMMENT ON COLUMN MMTH_UserDevices.disabled IS '사용자 정의 일시 정지, N:enabled, Y:disabled, default N';
COMMENT ON COLUMN MMTH_UserDevices.status IS '0:NOT_AVAILABLE(유효한 에이전트 등록 건이 없는 경우), 1 :AVAILABLE(유효한 등록 건이 있는 경우), 2 : 사고접수(모든 기기 이용 불가)';
COMMENT ON COLUMN MMTH_UserDevices.tsReg IS '생성 시간';
COMMENT ON COLUMN MMTH_UserDevices.tsUpdated IS '수정 시간';
COMMENT ON COLUMN MMTH_UserDevices.data IS '확장 데이터';



--
--
-- # MMTH_DeviceAppAgents # 
--

COMMENT ON TABLE MMTH_DeviceAppAgents IS '사용자 디바이스 정보와 이용가능한 클라이언트 앱을 매핑하고 등록상태를 관리하는 테이블';

COMMENT ON COLUMN MMTH_DeviceAppAgents.id IS '기본 키(PK)';
COMMENT ON COLUMN MMTH_DeviceAppAgents.userDeviceId IS '사용자 디바이스 외래키: UserDevice(id)';
COMMENT ON COLUMN MMTH_DeviceAppAgents.agentId IS 'AppAgent 외래키 : AppAgents(id)';
COMMENT ON COLUMN MMTH_DeviceAppAgents.authType IS '인증매체유형 (0x01:FIDO,0x02:SAO,0x04:BIOTP)';
COMMENT ON COLUMN MMTH_DeviceAppAgents.status IS '0 : 발급대기, 1 : 발급완료';
COMMENT ON COLUMN MMTH_DeviceAppAgents.registrationId IS 'FCM 앱인스턴스등록토큰';
COMMENT ON COLUMN MMTH_DeviceAppAgents.tsReg IS '생성 시간';
COMMENT ON COLUMN MMTH_DeviceAppAgents.tsUpdated IS '수정 시간';
COMMENT ON COLUMN MMTH_DeviceAppAgents.tsDone IS '발급완료 시간';
COMMENT ON COLUMN MMTH_DeviceAppAgents.tsExpired IS '만료 시간';


--
--
-- # MMTH_FidoUsers	#
--
COMMENT ON TABLE MMTH_FidoUsers IS '사용자(사용자디바이스+에이전트)와 FIDO 등록정보를 매핑하기 위한 테이블';

COMMENT ON COLUMN MMTH_FidoUsers.id IS '기본 키(PK)';
COMMENT ON COLUMN MMTH_FidoUsers.deviceAgentId IS '사용자 등록정보 외래키 : DeviceAppAgents(id)';
COMMENT ON COLUMN MMTH_FidoUsers.fidoRegId IS 'FIDO  등록정보 외래키: FidoRegistrations(id)';
COMMENT ON COLUMN MMTH_FidoUsers.signCntUpdated IS 'FIDO 인증카운터 (for FIDO validation)';
COMMENT ON COLUMN MMTH_FidoUsers.tsReg IS '생성 시간';


--
--
-- # MMTH_TokenUsers #
--

COMMENT ON TABLE MMTH_TokenUsers IS '사용자(사용자디바이스+에이전트)와 토큰을 매핑하기 위한 테이블';

COMMENT ON COLUMN MMTH_TokenUsers.id IS '기본 키(PK)';
COMMENT ON COLUMN MMTH_TokenUsers.deviceAgentId IS '사용자 등록정보 외래키 : DeviceAppAgents(id)';
COMMENT ON COLUMN MMTH_TokenUsers.tokenId IS '토큰 정보 외래키 : Tokens(tokenId)';
COMMENT ON COLUMN MMTH_TokenUsers.tsReg IS '등록 시간';
COMMENT ON COLUMN MMTH_TokenUsers.data IS 'OTP 생성관련 확장데이터';


--
--
-- # MMTH_ServerChallenge #
--
COMMENT ON TABLE MMTH_ServerChallenge IS '사용자 등록/인증 시 FIDO및OTP데이터에 필요한 서버 Nonce를 관리하는 테이블';

COMMENT ON COLUMN MMTH_ServerChallenge.id IS '기본 키(PK)';
COMMENT ON COLUMN MMTH_ServerChallenge.username IS '사용자 식별자';
COMMENT ON COLUMN MMTH_ServerChallenge.challenge IS 'FIDO/OTP 서버 nonce.';
COMMENT ON COLUMN MMTH_ServerChallenge.challengeType IS 'REG ("1"), AUTH ("2"), AUTH_WITH_TRANSACTION("3"), AUTH_WITH_TRAN_INFO("4")';
COMMENT ON COLUMN MMTH_ServerChallenge.status IS 'FIDO_REQ("1"), OTP_REQ("2"),	DONE("9")';
COMMENT ON COLUMN MMTH_ServerChallenge.tsLifetime IS 'FIDO/OTP 인증/검증유효 시간';
COMMENT ON COLUMN MMTH_ServerChallenge.transactionId IS '거래연동OTP 인증 시 사용할 해당 거래에 대한 식별자';
COMMENT ON COLUMN MMTH_ServerChallenge.tranContent IS '거래연동 OTP를 생성하기 위한 정보';
COMMENT ON COLUMN MMTH_ServerChallenge.deviceAgentId IS '사용자 등록정보 외래키 - DeviceAppAgents(id)';
COMMENT ON COLUMN MMTH_ServerChallenge.tsDone IS '발급완료 시간';


--
--
-- # MMTH_FidoTransaction #
--
COMMENT ON TABLE MMTH_FidoTransaction IS 'FIDO Transaction을 검증하기 위한 테이블 (only available when challengeType is TRANSACTION)';

COMMENT ON COLUMN MMTH_FidoTransaction.id IS '기본 키(PK)';
COMMENT ON COLUMN MMTH_FidoTransaction.challengeId IS '챌린지 외래키 :  ServerChallenges(id)';
COMMENT ON COLUMN MMTH_FidoTransaction.fidoRegId IS 'FIDO 등록 정보 외래키 : FidoRegistrations(id)';
COMMENT ON COLUMN MMTH_FidoTransaction.tranHash IS '해시된 트랜잭션';
COMMENT ON COLUMN MMTH_FidoTransaction.tsReg IS '생성 시간';


--
--
-- # MMTH_IssueCodeData #
--

COMMENT ON TABLE MMTH_IssueCodeData IS '서비스 등록 전 발급코드 생성 데이터 테이블.';

COMMENT ON COLUMN MMTH_IssueCodeData.id IS '기본 키(PK)';
COMMENT ON COLUMN MMTH_IssueCodeData.username IS '사용자 식별자';
COMMENT ON COLUMN MMTH_IssueCodeData.authType IS '인증매체유형 ( 0x01:FIDO,0x02:SAO,0x04:BIOTP )';
COMMENT ON COLUMN MMTH_IssueCodeData.issueCodeData IS '발급코드 생성 데이타';
COMMENT ON COLUMN MMTH_IssueCodeData.tsLifetime IS '인증/검증유효 시간';


--
--
-- MMTH_AuthManager
--
COMMENT ON TABLE MMTH_AuthManager IS '사용자디바이스-인증매체 별로 인증(오류)횟수와 시간을 기록하기 위한 관리 테이블';

COMMENT ON COLUMN MMTH_AuthManager.id IS '기본 키(PK)';
COMMENT ON COLUMN MMTH_AuthManager.userId IS '사용자 외래키 : Users(id)';
COMMENT ON COLUMN MMTH_AuthManager.userDeviceId IS '사용자 디바이스 외래키: UserDevice(id)';
COMMENT ON COLUMN MMTH_AuthManager.authType IS '인증매체유형 ( 0x01:FIDO,0x02:SAO,0x04:BIOTP )';
COMMENT ON COLUMN MMTH_AuthManager.authFailCnt IS '인증 오류 횟수';
COMMENT ON COLUMN MMTH_AuthManager.totSuccessCnt IS '누적 인증 성공 횟수';
COMMENT ON COLUMN MMTH_AuthManager.tsLastAuth IS '마지막 인증 성공 시간';
COMMENT ON COLUMN MMTH_AuthManager.tsLastAuthFail IS '마지막 인증 오류 시간';


--
--
-- # MMTH_UserServerPins #
--
COMMENT ON TABLE MMTH_UserServerPins IS 'SecureAnyOTP 사용 시 서버핀을 기록하기 위한 테이블';

COMMENT ON COLUMN MMTH_UserServerPins.id IS '기본 키(PK)';
COMMENT ON COLUMN MMTH_UserServerPins.userId IS '사용자 외래키 : Users(id)';
COMMENT ON COLUMN MMTH_UserServerPins.serverPin IS '서버PIN 정보';
COMMENT ON COLUMN MMTH_UserServerPins.failCnt IS '서버PIN오류횟수. 검증 성공 시 0으로 초기화';
COMMENT ON COLUMN MMTH_UserServerPins.tsReg IS '생성 시간';
COMMENT ON COLUMN MMTH_UserServerPins.tsUpdated IS '수정 시간';
COMMENT ON COLUMN MMTH_UserServerPins.data IS '확장 데이터';


--
--
-- # MMTH_ExtServiceItems #
--
COMMENT ON TABLE MMTH_ExtServiceItems IS 'AnyPASS 서비스에서 사용하는 연동 테이블';

COMMENT ON COLUMN MMTH_ExtServiceItems.id IS '기본 키(PK)';
COMMENT ON COLUMN MMTH_ExtServiceItems.userId IS '사용자 외래키 : Users(id)';
COMMENT ON COLUMN MMTH_ExtServiceItems.transactionId IS '인증 시 사용할 해당 챌린지에 대한 식별자';
COMMENT ON COLUMN MMTH_ExtServiceItems.externalIdentifier IS '외부에서 검증할 식별 정보';
COMMENT ON COLUMN MMTH_ExtServiceItems.status IS '0 : 대기, 1 : 진행, 2: 완료';
COMMENT ON COLUMN MMTH_ExtServiceItems.tsReg IS '생성 시간';
COMMENT ON COLUMN MMTH_ExtServiceItems.tsExpired IS '만료 시간';
COMMENT ON COLUMN MMTH_ExtServiceItems.tsDone IS '완료 시간';
COMMENT ON COLUMN MMTH_ExtServiceItems.data IS '확장 데이터';


--
--
-- # MMTH_UserAnnotations #
--
COMMENT ON TABLE MMTH_UserAnnotations IS '외부연동사용자를 위한 부가정보 테이블';

COMMENT ON COLUMN MMTH_UserAnnotations.id IS '기본 키(PK)';
COMMENT ON COLUMN MMTH_UserAnnotations.userId IS '사용자 외래키 : Users(id)';
COMMENT ON COLUMN MMTH_UserAnnotations.displayName IS '표시 이름';
COMMENT ON COLUMN MMTH_UserAnnotations.extUnique IS '외부 사용자식별자';
COMMENT ON COLUMN MMTH_UserAnnotations.customerCode IS '고객번호';
COMMENT ON COLUMN MMTH_UserAnnotations.data IS '확장 데이터';
COMMENT ON COLUMN MMTH_UserAnnotations.username IS '사용자 식별자';
COMMENT ON COLUMN MMTH_UserAnnotations.countryCode IS '국가코드(글로벌위비뱅크)';


--
--
-- # MMTH_ServiceLogAnnotations #
--

COMMENT ON TABLE MMTH_ServiceLogAnnotations IS '사용자 서비스 로그 커스텀 추가를 위한 테이블';

COMMENT ON COLUMN MMTH_ServiceLogAnnotations.serviceLogId IS '서비스 로그 외래키 : ServiceLogs(id)';
COMMENT ON COLUMN MMTH_ServiceLogAnnotations.regEmpNo IS '서비스 처리자에 대한 사원정보';







