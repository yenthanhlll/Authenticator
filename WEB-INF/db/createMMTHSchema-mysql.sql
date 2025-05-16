--
-- MySQL DDL SCRIPT
-- 	BIOTP_SCHEME_v2.0.6
--

-- ******** 중요 ******* KeyId 사이즈 변경 >> 2048 에서 255으로..


--
--
-- # System Settings #
--
CREATE TABLE MMTH_SystemSettings (
    settingName   VARCHAR(64) NOT NULL COMMENT '정책설정 이름', 
    settingValue  TEXT COMMENT '정책설정 값', 
    PRIMARY KEY (settingName)
) engine=InnoDB;

ALTER TABLE MMTH_SystemSettings COMMENT '서버 서비스 정책에 대한 설정 값을 관리하는 테이블';

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
	id INT NOT NULL AUTO_INCREMENT COMMENT 'PK',
	username VARCHAR(128) NOT NULL COMMENT '웹 관리자 아이디 (Unique)',
	password VARCHAR(128) NOT NULL COMMENT '웹 관리자 비밀번호',
	adminType CHAR(1) NOT NULL COMMENT '웹 관리자 유형 (SUPER, ADMIN, PERMITTED)',
	disabled CHAR(1) NOT NULL COMMENT '계정 활성화 여부 (N:활성화, Y:비활성화, default N)',
	tsLastLogin BIGINT DEFAULT 0 COMMENT '마지막 로그인 시간',
	lastRemoteAddr VARCHAR(20) COMMENT '최근 접속 주소',
	homeUrl VARCHAR(255) COMMENT '디폴트 웹 관리자 페이지',
	tsReg BIGINT NOT NULL COMMENT '생성 시간',
    tsUpdated BIGINT DEFAULT 0 COMMENT '수정 시간',
    data LONGBLOB NOT NULL COMMENT '확장 데이터',
    PRIMARY KEY (id)
) engine=InnoDB;

ALTER TABLE MMTH_Administrators ADD CONSTRAINT MMTH_Administrators_Un1 UNIQUE (username);
ALTER TABLE MMTH_Administrators COMMENT '서버 웹 관리자 정보를 위한 테이블';


--
--
-- # MMTH_AuditAlarms #	-
--
-- 	********************************
--   * alarmLevel - NONE : 0, INFORMATION : 1, URGENT : 2, CRITICAL : 3, LIFE_SAFETY : 4
-- 	********************************
--
CREATE TABLE MMTH_AuditAlarms (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT 'PK',
    auditType INT NOT NULL COMMENT '감사 알람 유형',
    actionType INT NOT NULL COMMENT '세부 동작 유형',
    alarmLevel INT NOT NULL COMMENT '알람 레벨 (NONE, INFORMATION, URGENT, CRITICAL, LIFE_SAFETY)',
    description VARCHAR(1024) NOT NULL COMMENT '세부내용',
    tsReg BIGINT NOT NULL COMMENT '생성 시간',
    ack CHAR(1) DEFAULT 'N' COMMENT '알람확인 여부',
    ackAdmin VARCHAR(128) DEFAULT '-' COMMENT '알람확인 관리자',
    tsAck BIGINT DEFAULT 0 COMMENT '알람확인 시간',
	PRIMARY KEY (id)
) engine=InnoDB;

ALTER TABLE MMTH_AuditAlarms COMMENT '서비스 구동 상 문제가 발생하거나 서비스 설정 변경할 경우 로그를 남기기 위한 감사알람';


--
--
--  # MMTH_FidoFacets #
--
-- 	********************************
--	 * disabled : 사용 일시 정지,  N:enabled, Y:disabled, default 'N'
-- 	********************************
--
CREATE TABLE MMTH_FidoFacets (
    id INT NOT NULL AUTO_INCREMENT COMMENT 'PK',
    facetId VARCHAR(512) NOT NULL COMMENT 'FIDO Facet ID',
    majorVer INT NOT NULL COMMENT 'Major 버전',
    minorVer INT NOT NULL COMMENT 'Minor 버전',
    alias VARCHAR(128) NOT NULL COMMENT '별칭 (이름)',
    disabled CHAR(1) NOT NULL COMMENT '비활성화 여부 (N/Y)',
    description VARCHAR(1024) DEFAULT '' COMMENT '세부내용',
    tsReg BIGINT NOT NULL COMMENT '생성 시간',
    tsUpdated BIGINT NOT NULL DEFAULT 0 COMMENT '수정 시간', 
    PRIMARY KEY (id)
) engine=InnoDB;

ALTER TABLE MMTH_FidoFacets ADD CONSTRAINT MMTH_FidoFacets_Un1 UNIQUE (facetId);
ALTER TABLE MMTH_FidoFacets COMMENT '서비스를 이용하기 위해 Android, IOS 등 다양한 플랫폼에서 구현한 신뢰하는 어플리케이션(Trusted Facet ID) 목록을 관리하는 테이블';



--
--
-- # MMTH_FidoMetadata #
--
-- 	********************************
--	 * disabled : 사용 여부,  N:enabled, Y:disabled, default 'N'
-- 	********************************
--
CREATE TABLE MMTH_FidoMetadata (
    id INT NOT NULL AUTO_INCREMENT COMMENT 'PK',
    aaid CHAR(9) NOT NULL COMMENT 'FIDO Authenticator Attestation ID',
    alias VARCHAR(128) NOT NULL COMMENT '별칭 (이름)',
    disabled CHAR(1) NOT NULL COMMENT '비활성화 여부 (N/Y)',
    userVerification BIGINT NOT NULL COMMENT '인증장치의 사용자 검증확인 수단',
    description VARCHAR(1024) DEFAULT '' COMMENT '세부내용',
    tsReg BIGINT NOT NULL COMMENT '생성 시간',
    tsUpdated BIGINT NOT NULL DEFAULT 0 COMMENT '수정 시간',
    data LONGBLOB NOT NULL COMMENT '확장 데이터', 
    PRIMARY KEY (id)
) engine=InnoDB;

ALTER TABLE MMTH_FidoMetadata ADD CONSTRAINT MMTH_FidoMetadata_Un1 UNIQUE (aaid);
ALTER TABLE MMTH_FidoMetadata COMMENT 'FIDO 인증장치의 Metadata 정보를 관리하기 위한 테이블';


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
-- 	 * reqType  : 0x01:FIDO_UAF_REQ, 0x02:FIDO_UAF_RESP, 0x03:HTTP_API, 0x00 : UNKNOWN (OLD)
--	 * actionType : 0x01:REQ, 0x02:FAIL, 0x03:DONE
-- 	********************************
--
CREATE TABLE MMTH_ServiceLogs (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT 'PK',
    authType INT NOT NULL COMMENT '등록인증매체유형(FIDO/BIOTP)',
    username VARCHAR(128) NOT NULL COMMENT '사용자아이디',
    opType INT NOT NULL COMMENT '서비스 동작 유형',
    reqType INT NOT NULL COMMENT '요청 유형',
    actionType INT NOT NULL COMMENT '동작 결과',
    returnCode CHAR(4) COMMENT '결과코드',
    deviceId VARCHAR(64) COMMENT '디바이스 식별자',
    deviceType CHAR(2) COMMENT '디바이스 유형',
    pkgUnique VARCHAR(255) COMMENT '앱 패키지 이름',
    aaid VARCHAR(9) COMMENT 'FIDO Authenticator Attestation ID',
    tokenId VARCHAR(12) COMMENT 'OTP SN',
    description VARCHAR(1024) DEFAULT '' COMMENT '세부내용',
    tsReg BIGINT NOT NULL COMMENT '생성 시간',
    regDate CHAR(8) NOT NULL COMMENT '생성 일 (YYYYMMDD)',
    regHour CHAR(10) NOT NULL COMMENT '생성 시 (YYYYMMDDHH)', 
    PRIMARY KEY (id)
) engine=InnoDB;

ALTER TABLE MMTH_ServiceLogs COMMENT '사용자 서비스 로그, 사용자 등록/인증/해지 처리에 대한 각 단계 별 로그';


--
--
-- # MMTH_IssuanceHistory #
--
-- 	********************************
--	 * issueType : 발급유형 ('1' : 신규사용자 발급, '2' : 기존사용자의 기기추가, '3' :  앱추가등록, '4' : 사고신고등에 의한 재이용발급)
-- 	********************************
--
CREATE TABLE MMTH_IssuanceHistory (
	id INT NOT NULL AUTO_INCREMENT COMMENT 'PK',
    authType INT NOT NULL COMMENT '등록인증매체유형(FIDO/BIOTP)',
    username VARCHAR(128) NOT NULL COMMENT '사용자아이디',
    deviceId VARCHAR(64) NOT NULL COMMENT '디바이스 식별자',
    deviceType CHAR(2) COMMENT '디바이스 유형',
    pkgUnique VARCHAR(255) NOT NULL COMMENT '앱 패키지 이름',
    issueType CHAR(1) NOT NULL COMMENT '발급 유형',
    aaid VARCHAR(9) COMMENT 'FIDO Authenticator Attestation ID',
    tokenId  VARCHAR(12) COMMENT 'OTP SN',
    tsIssue BIGINT NOT NULL COMMENT '발급 Timestamp',
    issueMonth CHAR(6) NOT NULL COMMENT '발급 월',
    issueDate CHAR(8) NOT NULL COMMENT '발급 일',
    issueTime CHAR(6) NOT NULL COMMENT '발급 시간', 
    PRIMARY KEY (id)
) engine=InnoDB;

ALTER TABLE MMTH_IssuanceHistory COMMENT '사용자의 신규/추가_교체 발급현황을 기록하기 위한 테이블';


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
    id INT NOT NULL AUTO_INCREMENT COMMENT 'PK',
    username VARCHAR(128) NOT NULL COMMENT '사용자아이디',
    disabled CHAR(1) NOT NULL COMMENT '비활성화 여부 (N/Y)',
    status CHAR(1) NOT NULL COMMENT '사용자 등록 상태',
    tsReg BIGINT NOT NULL COMMENT '생성 시간',
    tsUpdated BIGINT NOT NULL DEFAULT 0 COMMENT '수정 시간',
    data LONGBLOB NOT NULL COMMENT '확장 데이터', 
    PRIMARY KEY (id)
) engine=InnoDB;

ALTER TABLE MMTH_Users ADD CONSTRAINT MMTH_Users_Un1 UNIQUE (username);
ALTER TABLE MMTH_Users COMMENT '서비스 사용자 테이블';

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
    id INT NOT NULL AUTO_INCREMENT COMMENT 'PK',
    pkgUnique VARCHAR(255) NOT NULL COMMENT '앱 패키지 이름',
    osType CHAR(1) NOT NULL COMMENT 'OS 유형 (안드로이드/IOS)',
    alias VARCHAR(128) NOT NULL COMMENT '별칭 (이름)',
    disabled CHAR(1) NOT NULL COMMENT '비활성화 여부 (N/Y)',
    description VARCHAR(1024) DEFAULT '' COMMENT '세부내용',
    tsReg BIGINT NOT NULL COMMENT '생성 시간',
    tsUpdated BIGINT NOT NULL DEFAULT 0 COMMENT '수정 시간',
    data LONGBLOB NOT NULL COMMENT '확장 데이터', 
    PRIMARY KEY (id)
) engine=InnoDB;

ALTER TABLE MMTH_AppAgents ADD CONSTRAINT MMTH_AppAgents_Un1 UNIQUE (pkgUnique, osType);
ALTER TABLE MMTH_AppAgents COMMENT '이용가능한 클라이언트 앱 정보를 관리하기 위한 테이블';

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
	tokenId VARCHAR(12) NOT NULL COMMENT 'OTP SN',
	tokenData VARCHAR(1024) NOT NULL COMMENT 'OTP 데이터 (암호화)',
	status CHAR(1) NOT NULL COMMENT 'OTP 발급 상태 (유휴/발급/폐기/사고)',
	authType INT DEFAULT -1 COMMENT '등록인증매체유형(FIDO/BIOTP)',
	username VARCHAR(128) DEFAULT '-' COMMENT '발급받은 사용자아이디',
	lost CHAR(1) DEFAULT 'N' NOT NULL COMMENT '분실사고여부',
	tsReg BIGINT NOT NULL COMMENT '생성 시간',
	tsOccupied BIGINT DEFAULT 0 COMMENT '발급 시간',
	tsDiscard BIGINT DEFAULT 0 COMMENT '폐기 시간',
	tsLost  BIGINT DEFAULT 0 COMMENT '사고 시간', 
    PRIMARY KEY (tokenId)
) engine=InnoDB;

ALTER TABLE MMTH_Tokens COMMENT 'OTP 생성을 위한 OTP데이터를 관리하는 토큰 테이블';

--
--
-- MMTH_FidoRegistrations *
--
CREATE TABLE MMTH_FidoRegistrations (
    id INT NOT NULL AUTO_INCREMENT COMMENT 'PK',
    aaid CHAR(9) NOT NULL COMMENT 'FIDO Authenticator Attestation ID',
    keyId VARCHAR(255) NOT NULL COMMENT 'FIDO 등록 시 인증장치에서 생성한 Key ID',
    tsReg BIGINT NOT NULL COMMENT '생성 시간',
    data LONGBLOB NOT NULL COMMENT '확장 데이터', 
    PRIMARY KEY (id)
) engine=InnoDB;

ALTER TABLE MMTH_FidoRegistrations ADD CONSTRAINT MMTH_FidoRegistrations_Un1 UNIQUE (keyId, aaid);
ALTER TABLE MMTH_FidoRegistrations COMMENT 'FIDO 등록정보를 관리하기 위한 테이블';
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
    id INT NOT NULL AUTO_INCREMENT COMMENT 'PK',
    userId INT NOT NULL COMMENT '사용자식별자(FK)',
    deviceId VARCHAR(64) NOT NULL COMMENT '디바이스 식별자',
    deviceType CHAR(2) NOT NULL COMMENT '디바이스 유형',
    model VARCHAR(128) NOT NULL COMMENT '디바이스 모델',
    alias VARCHAR(128) COMMENT '별칭',
    disabled CHAR(1) NOT NULL COMMENT '비활성화 여부 (N/Y)',
    status CHAR(1) NOT NULL COMMENT '상태',
    tsReg BIGINT NOT NULL COMMENT '생성 시간',
    tsUpdated BIGINT NOT NULL DEFAULT 0 COMMENT '수정 시간',
    data LONGBLOB NOT NULL COMMENT '확장 데이터', 
    PRIMARY KEY (id)
) engine=InnoDB;

ALTER TABLE MMTH_UserDevices ADD CONSTRAINT MMTH_UserDevices_Un1 UNIQUE (userId, deviceId, deviceType);
ALTER TABLE MMTH_UserDevices COMMENT '서비스를 이용하는 사용자의 단말정보를 관리하기 위한 테이블';


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
    id INT NOT NULL AUTO_INCREMENT COMMENT 'PK',
    userDeviceId int NOT NULL COMMENT '사용자디바이스식별자(FK)',
    agentId INT NOT NULL COMMENT '앱관리식별자(FK)',
    authType INT NOT NULL COMMENT '등록인증매체유형(FIDO/BIOTP)',
    status CHAR(1) NOT NULL COMMENT '상태',
    registrationId VARCHAR(2048) COMMENT '',
    tsReg BIGINT NOT NULL COMMENT '생성 시간',
    tsUpdated BIGINT NOT NULL DEFAULT 0 COMMENT '수정 시간',
    tsDone BIGINT DEFAULT 0 COMMENT '등록완료 시간',
    tsExpired BIGINT DEFAULT 0 COMMENT '만료 시간', 
    PRIMARY KEY (id)
) engine=InnoDB;

ALTER TABLE MMTH_DeviceAppAgents ADD CONSTRAINT MMTH_DeviceAppAgents_Un1 UNIQUE (userDeviceId, agentId, authType);
ALTER TABLE MMTH_DeviceAppAgents COMMENT '기기등록정보, 사용자 디바이스 정보와 이용가능한 클라이언트 앱을 매핑하고 등록상태를 관리하는 테이블';


--
--
-- MMTH_FidoUsers	*
--
CREATE TABLE MMTH_FidoUsers (
	id INT NOT NULL AUTO_INCREMENT COMMENT 'PK',
    fidoRegId INT NOT NULL COMMENT 'FIDO등록정보식별자(FK)',
    deviceAgentId INT NOT NULL COMMENT '기기등록식별자(FK)',
    signCntUpdated BIGINT DEFAULT 0 NOT NULL COMMENT '인증장치서명누적카운트',
    tsReg BIGINT DEFAULT 0 NOT NULL COMMENT '생성 시간', 
    PRIMARY KEY (id)
) engine=InnoDB;

ALTER TABLE MMTH_FidoUsers ADD CONSTRAINT MMTH_FidoUsers_Un1 UNIQUE (fidoRegId);
ALTER TABLE MMTH_FidoUsers COMMENT '사용자(사용자기기등록정보)와 FIDO 등록정보를 매핑하기 위한 테이블';

--
--
-- MMTH_TokenUsers *
--
CREATE TABLE MMTH_TokenUsers (
	id INT NOT NULL AUTO_INCREMENT COMMENT 'PK',
    tokenId VARCHAR(12) NOT NULL COMMENT 'OTP SN',
    deviceAgentId INT NOT NULL COMMENT '기기등록식별자(FK)',
    tsReg BIGINT DEFAULT 0 NOT NULL COMMENT '생성 시간',
    data LONGBLOB NOT NULL COMMENT '확장 데이터', 
    PRIMARY KEY (id)
) engine=InnoDB;

ALTER TABLE MMTH_TokenUsers ADD CONSTRAINT MMTH_TokenUsers_Un1 UNIQUE (deviceAgentId);
ALTER TABLE MMTH_TokenUsers COMMENT '사용자(사용자기기등록정보)와 토큰을 매핑하기 위한 테이블';

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
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT 'PK',
    username VARCHAR(128) NOT NULL COMMENT '사용자아이디',
    challenge CHAR(39) NOT NULL COMMENT '챌린지',
    challengeType CHAR(1) NOT NULL COMMENT '챌린지유형', 
    status CHAR(1) NOT NULL COMMENT '상태',
    tsLifeTime BIGINT NOT NULL COMMENT '유효시간',
    transactionId VARCHAR(64) NOT NULL COMMENT '트랜잭션아이디',
    tranContent VARCHAR(64) COMMENT 'TranInfo',
    deviceAgentId INT DEFAULT -1 COMMENT '기기등록식별자(FK)',
    tsDone BIGINT DEFAULT 0 COMMENT '인증완료시간', 
    PRIMARY KEY (id)
) engine=InnoDB;

ALTER TABLE MMTH_ServerChallenge ADD CONSTRAINT MMTH_ServerChallenge_Un1 UNIQUE (username);
ALTER TABLE MMTH_ServerChallenge ADD CONSTRAINT MMTH_ServerChallenge_Un2 UNIQUE (transactionId);
ALTER TABLE MMTH_ServerChallenge COMMENT '사용자 등록/인증 시 FIDO및OTP데이터에 필요한 서버 Nonce를 관리하는 테이블';

--
--
-- MMTH_FidoTransaction 
-- only available challengeType is TRANSACTION
--
CREATE TABLE MMTH_FidoTransaction (
	id BIGINT NOT NULL AUTO_INCREMENT COMMENT 'PK',
    challengeId BIGINT NOT NULL COMMENT '챌린지정보식별자(FK)',
    fidoRegId INT NOT NULL COMMENT 'FIDO등록정보식별자(FK)', 
    tranHash CHAR(64) NOT NULL COMMENT 'FIDO TC 해시정보',
    tsReg BIGINT NOT NULL COMMENT '생성 시간', 
    PRIMARY KEY (id)
) engine=InnoDB;

ALTER TABLE MMTH_FidoTransaction COMMENT 'FIDO Transaction을 검증하기 위한 테이블';

--
--
-- MMTH_IssueCodeData *
--
CREATE TABLE MMTH_IssueCodeData (
	id INT NOT NULL AUTO_INCREMENT COMMENT 'PK',
    username VARCHAR(128) NOT NULL COMMENT '사용자아이디',
    authType INT NOT NULL COMMENT '등록인증매체유형(FIDO/BIOTP)',
    issueCodeData VARCHAR(128) NOT NULL COMMENT '',
    tsLifeTime BIGINT NOT NULL COMMENT '', 
    PRIMARY KEY (id)
) engine=InnoDB;

ALTER TABLE MMTH_IssueCodeData ADD CONSTRAINT MMTH_IssueCodeData_Un1 UNIQUE (username, authType);
ALTER TABLE MMTH_IssueCodeData COMMENT '서비스 등록 전 발급코드 생성 데이터 테이블';

--
--
-- MMTH_AuthManager
--
CREATE TABLE MMTH_AuthManager (
	id INT NOT NULL AUTO_INCREMENT COMMENT 'PK',
    userId INT NOT NULL COMMENT '사용자식별자(FK)',
    userDeviceId INT NOT NULL COMMENT '',
    authType INT NOT NULL COMMENT '등록인증매체유형(FIDO/BIOTP)',
    authFailCnt INT NOT NULL COMMENT '',
    totSuccessCnt INT NOT NULL COMMENT '',
    tsLastAuth BIGINT NOT NULL COMMENT '',
    tsLastAuthFail BIGINT NOT NULL COMMENT '', 
    PRIMARY KEY (id)
) engine=InnoDB;

ALTER TABLE MMTH_AuthManager ADD CONSTRAINT MMTH_AuthManager_Un1 UNIQUE (userDeviceId, authType);
ALTER TABLE MMTH_AuthManager COMMENT '사용자디바이스-인증매체 별로 인증(오류)횟수와 시간을 기록하기 위한 관리 테이블';

--
--
-- MMTH_UserServerPwd *
--
CREATE TABLE MMTH_UserServerPwd (
	id INT NOT NULL AUTO_INCREMENT COMMENT 'PK',
    userId INT NOT NULL COMMENT '사용자식별자(FK)',
    pwdType CHAR(1) NOT NULL COMMENT '패스워드타입 (PIN/PASSWORD)',
    serverPwd VARCHAR(128) NOT NULL COMMENT '서버핀',
    failCnt INT DEFAULT 0 NOT NULL COMMENT '서버핀오류횟수',
    tsReg BIGINT NOT NULL COMMENT '생성 시간',
    tsUpdated BIGINT DEFAULT 0 NOT NULL COMMENT '',
    tsLatestFailed BIGINT DEFAULT 0 NOT NULL COMMENT '마지막 오류 시간',
    tsExpired BIGINT DEFAULT 0 NOT NULL COMMENT '만료 시간',
    data LONGBLOB NOT NULL  COMMENT '', 
    PRIMARY KEY (id)
) engine=InnoDB;

ALTER TABLE MMTH_UserServerPwd ADD CONSTRAINT MMTH_UserServerPwd_Un1 UNIQUE (userId, pwdType);


--
--
-- MMTH_ExtServiceItems *
--
CREATE TABLE MMTH_ExtServiceItems (
	id BIGINT NOT NULL AUTO_INCREMENT COMMENT 'PK',
	userId INT NOT NULL COMMENT '사용자식별자(FK)',
	transactionId VARCHAR(32) not null COMMENT '인증 트랜잭션아이디',
	externalIdentifier VARCHAR(256) not null COMMENT '외부 거래 식별 아이디',
	status CHAR(1) DEFAULT '0' not null COMMENT '상태',
	tsReg BIGINT NOT NULL COMMENT '생성 시간',
	tsExpired BIGINT NOT NULL COMMENT '만료 시간',
	tsDone BIGINT DEFAULT 0 COMMENT '인증 완료 시간',
	data LONGBLOB NOT NULL COMMENT '확장 데이터', 
    PRIMARY KEY (id)
) engine=InnoDB;

ALTER TABLE MMTH_ExtServiceItems ADD CONSTRAINT MMTH_ExtServiceItems_Un1 UNIQUE (externalIdentifier);
ALTER TABLE MMTH_ExtServiceItems ADD CONSTRAINT MMTH_ExtServiceItems_Un2 UNIQUE (transactionId);
ALTER TABLE MMTH_ExtServiceItems COMMENT '3rd party 거래 연동 테이블';

--
--
-- MMTH_UserAnnotations *
--
CREATE TABLE MMTH_UserAnnotations (
	id INT NOT NULL AUTO_INCREMENT COMMENT 'PK',
	userId INT NOT NULL COMMENT '사용자식별자(FK)',
	username VARCHAR(128) COMMENT '사용자아이디',
	displayName VARCHAR(64) COMMENT '-',
	extUnique VARCHAR(64) COMMENT '-',
	customerCode CHAR(4) COMMENT '-',
	countryCode CHAR(2) COMMENT '-',
	data LONGBLOB NOT NULL COMMENT '확장 데이터', 
    PRIMARY KEY (id)
) engine=InnoDB;

ALTER TABLE MMTH_UserAnnotations ADD CONSTRAINT MMTH_UserAnnotations_Un1 UNIQUE (userId);
ALTER TABLE MMTH_UserAnnotations COMMENT '사용자 부가정보 테이블';


--
--
-- MMTH_ServiceLogAnnotations *
--
CREATE TABLE MMTH_ServiceLogAnnotations (
    serviceLogId BIGINT NOT NULL COMMENT '-',
    regEmpNo VARCHAR(32) NOT NULL COMMENT '-'
);
ALTER TABLE MMTH_ServiceLogAnnotations ADD CONSTRAINT MMTH_ServiceLogAnnotations_Un1 UNIQUE (serviceLogId);
ALTER TABLE MMTH_ServiceLogAnnotations COMMENT '서비스로그 부가정보 테이블';

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
	SELECT  u.id AS id, u.username AS username, u.disabled AS disabled, u.status AS status, u.tsReg AS tsReg, u.tsUpdated AS tsUpdated, u.data AS data, a.displayName AS displayName, a.extUnique AS extUnique, a.customerCode AS customerCode, a.countryCode AS countryCode, a.data AS annotationData
	FROM MMTH_Users u
		LEFT JOIN MMTH_UserAnnotations a ON u.id = a.userId;
