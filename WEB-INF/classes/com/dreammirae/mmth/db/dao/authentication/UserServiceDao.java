/*      */ package WEB-INF.classes.com.dreammirae.mmth.db.dao.authentication;
/*      */ 
/*      */ import com.dreammirae.mmth.authentication.AuthMethodTypes;
/*      */ import com.dreammirae.mmth.authentication.ProductType;
/*      */ import com.dreammirae.mmth.authentication.ReturnCodes;
/*      */ import com.dreammirae.mmth.authentication.bean.UserServiceLocator;
/*      */ import com.dreammirae.mmth.db.dao.AppAgentDao;
/*      */ import com.dreammirae.mmth.db.dao.BaseDao;
/*      */ import com.dreammirae.mmth.db.dao.SystemSettingsDao;
/*      */ import com.dreammirae.mmth.db.dao.authentication.BiotpUserServiceDao;
/*      */ import com.dreammirae.mmth.db.dao.authentication.FidoUserServiceDao;
/*      */ import com.dreammirae.mmth.db.dao.authentication.IUserServiceDao;
/*      */ import com.dreammirae.mmth.db.dao.queries.DMLAuthManager;
/*      */ import com.dreammirae.mmth.db.dao.queries.DMLDeviceAppAgent;
/*      */ import com.dreammirae.mmth.db.dao.queries.DMLFidoRegistration;
/*      */ import com.dreammirae.mmth.db.dao.queries.DMLServerChallenge;
/*      */ import com.dreammirae.mmth.db.dao.queries.DMLServiceLog;
/*      */ import com.dreammirae.mmth.db.dao.queries.DMLServiceLogAnnotation;
/*      */ import com.dreammirae.mmth.db.dao.queries.DMLToken;
/*      */ import com.dreammirae.mmth.db.dao.queries.DMLTokenRegistration;
/*      */ import com.dreammirae.mmth.db.dao.queries.DMLUser;
/*      */ import com.dreammirae.mmth.db.dao.queries.DMLUserAnnotation;
/*      */ import com.dreammirae.mmth.db.dao.queries.DMLUserDevice;
/*      */ import com.dreammirae.mmth.db.dao.queries.DMLUserView;
/*      */ import com.dreammirae.mmth.exception.InternalDBException;
/*      */ import com.dreammirae.mmth.exception.ReturnCodeException;
/*      */ import com.dreammirae.mmth.external.ExternalWebApiTypes;
/*      */ import com.dreammirae.mmth.runtime.service.TokenThresholdTimerTask;
/*      */ import com.dreammirae.mmth.runtime.service.type.LogActionTypes;
/*      */ import com.dreammirae.mmth.runtime.service.type.LogOperationTypes;
/*      */ import com.dreammirae.mmth.runtime.service.type.OpRequstTypes;
/*      */ import com.dreammirae.mmth.util.StringUtils;
/*      */ import com.dreammirae.mmth.util.db.ExtendedJdbcTemplate;
/*      */ import com.dreammirae.mmth.vo.AppAgentVO;
/*      */ import com.dreammirae.mmth.vo.AuthManagerVO;
/*      */ import com.dreammirae.mmth.vo.DeviceAppAgentVO;
/*      */ import com.dreammirae.mmth.vo.FidoRegistrationVO;
/*      */ import com.dreammirae.mmth.vo.ServerChallengeVO;
/*      */ import com.dreammirae.mmth.vo.ServiceLogVO;
/*      */ import com.dreammirae.mmth.vo.TokenRegistrationVO;
/*      */ import com.dreammirae.mmth.vo.TokenVO;
/*      */ import com.dreammirae.mmth.vo.UserDeviceVO;
/*      */ import com.dreammirae.mmth.vo.UserVO;
/*      */ import com.dreammirae.mmth.vo.bean.GlobalWibeeLogData;
/*      */ import com.dreammirae.mmth.vo.bean.ICustomLogData;
/*      */ import com.dreammirae.mmth.vo.bean.MiraeAssetVietnamLogData;
/*      */ import com.dreammirae.mmth.vo.types.DeviceAppAgentStatus;
/*      */ import com.dreammirae.mmth.vo.types.DeviceTypes;
/*      */ import com.dreammirae.mmth.vo.types.TokenStatus;
/*      */ import com.dreammirae.mmth.vo.types.UserDeviceStatus;
/*      */ import com.dreammirae.mmth.vo.types.UserStatus;
/*      */ import java.util.List;
/*      */ import java.util.concurrent.locks.Lock;
/*      */ import java.util.concurrent.locks.ReentrantLock;
/*      */ import org.springframework.transaction.support.TransactionCallbackWithoutResult;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class UserServiceDao
/*      */   extends BaseDao
/*      */   implements IUserServiceDao
/*      */ {
/*      */   private static final long TIME_SLICE = 100L;
/*      */   private static final int MAX_RETRY_CNT = 30;
/*   69 */   private static Lock mLock = new ReentrantLock();
/*      */   
/*      */   public int getAvailableTokenCount() {
/*   72 */     return queryForInt("SELECT count(*) FROM MMTH_Tokens WHERE status=?", DMLToken.getSelectAvailableCount(), 0);
/*      */   }
/*      */ 
/*      */   
/*      */   protected static TokenVO releaseToken(ExtendedJdbcTemplate ejt, String username, AuthMethodTypes authType) {
/*   77 */     if (!mLock.tryLock()) {
/*   78 */       int retries = 0;
/*      */       
/*      */       do {
/*   81 */         if (++retries >= 30) {
/*   82 */           throw new ReturnCodeException(ReturnCodes.INTERNAL_SERVER_ERROR, "Failed to get a lock to get OTP key.");
/*      */         }
/*      */         
/*      */         try {
/*   86 */           Thread.sleep(100L);
/*   87 */         } catch (InterruptedException ignore) {}
/*      */ 
/*      */       
/*      */       }
/*   91 */       while (!mLock.tryLock());
/*      */     } 
/*      */     
/*      */     try {
/*   95 */       return releaseTokenImp(ejt, username, authType);
/*      */     } finally {
/*   97 */       mLock.unlock();
/*      */       
/*   99 */       TokenThresholdTimerTask.doSchedule();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static TokenVO releaseTokenImp(ExtendedJdbcTemplate ejt, String username, AuthMethodTypes authType) {
/*  106 */     List<TokenVO> list = ejt.queryForList(DMLToken.getSelectAvailableWithLimit(), DMLToken.getSelectAvailableParam(), DMLToken.getRowMapper());
/*      */ 
/*      */     
/*  109 */     if (list == null || list.isEmpty()) {
/*  110 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  114 */     TokenVO releaseOne = list.get(0);
/*      */     
/*  116 */     releaseOne.setStatus(TokenStatus.OCCUPIED);
/*  117 */     releaseOne.setUsername(username);
/*  118 */     releaseOne.setAuthType(authType);
/*  119 */     releaseOne.setTsOccupied(System.currentTimeMillis());
/*      */     
/*  121 */     ejt.update("UPDATE MMTH_Tokens SET status=?, authType=?, username=?, tsOccupied=? WHERE tokenId=?", DMLToken.toUpdateParams(releaseOne), DMLToken.getUpdateTypes());
/*      */     
/*  123 */     return releaseOne;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUserServiceForReg(UserServiceLocator locator, String username, String deviceId, DeviceTypes deviceType, String pkgUnique) {
/*  144 */     setUserServiceLocator(locator, username, deviceId, deviceType, pkgUnique);
/*      */ 
/*      */     
/*  147 */     setUserServiceForRegImp(locator, username, deviceId, deviceType, pkgUnique);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUserServiceForAuth(UserServiceLocator locator, String username, String deviceId, DeviceTypes deviceType, String pkgUnique) {
/*  162 */     setUserServiceLocator(locator, username, deviceId, deviceType, pkgUnique);
/*      */ 
/*      */     
/*  165 */     setUserServiceForAuthImp(locator, username, deviceId, deviceType, pkgUnique);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUserServiceForDereg(UserServiceLocator locator, String username, String deviceId, DeviceTypes deviceType, String pkgUnique) {
/*  180 */     setUserServiceLocator(locator, username, deviceId, deviceType, pkgUnique);
/*      */ 
/*      */     
/*  183 */     setUserServiceForDeregImp(locator, username, deviceId, deviceType, pkgUnique);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void saveRegRequest(UserServiceLocator locator) {
/*  197 */     if (locator.getUser() == null)
/*      */       return; 
/*  199 */     if (locator.getUserDevice() == null)
/*      */       return; 
/*  201 */     if (locator.getDeviceAppAgent() == null)
/*      */       return; 
/*  203 */     if (locator.getAuthManager() == null) {
/*      */       return;
/*      */     }
/*  206 */     doInTransaction((TransactionCallbackWithoutResult)new Object(this, locator));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void saveAuthRequest(UserServiceLocator locator) {
/*  255 */     doInTransaction((TransactionCallbackWithoutResult)new Object(this, locator));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void saveDeregRequest(UserServiceLocator locator) {
/*  276 */     doInTransaction((TransactionCallbackWithoutResult)new Object(this, locator));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void saveRegResponse(UserServiceLocator locator) {
/*  363 */     doInTransaction((TransactionCallbackWithoutResult)new Object(this, locator));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void saveAuthResponse(UserServiceLocator locator) {
/*  436 */     doInTransaction((TransactionCallbackWithoutResult)new Object(this, locator));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void saveOTPResponse(UserServiceLocator locator) {
/*  453 */     doInTransaction((TransactionCallbackWithoutResult)new Object(this, locator));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void forceDeregByUser(UserVO user, ICustomLogData customData, UserServiceLocator addLocator) {
/*  469 */     doInTransaction((TransactionCallbackWithoutResult)new Object(this, user, customData, addLocator));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void resetAuthFailByUser(UserVO user) {
/*  483 */     doInTransaction((TransactionCallbackWithoutResult)new Object(this, user));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void raiseLostToken(TokenRegistrationVO tokenRegi) {
/*  495 */     doInTransaction((TransactionCallbackWithoutResult)new Object(this, tokenRegi));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public UserVO returnUser(String username) {
/*  516 */     return (UserVO)queryForObject("SELECT data, a.id, a.username, accountName, productType, multiLoginYN, disabled, a.status, a.tsReg, a.tsUpdated, b.tokenId, b.tokentype FROM MMTH_Users a LEFT OUTER JOIN MMTH_HwTokens b ON a.id=b.userid WHERE a.username=?", DMLUser.toSelectParams(username), DMLUser.getRowMapper());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public UserDeviceVO returnUserDevice(UserVO user, String deviceId, DeviceTypes deviceType) {
/*  528 */     return (UserDeviceVO)queryForObject("SELECT data, id, userId, deviceId, deviceType, model, alias, disabled, status, tsReg, tsUpdated FROM MMTH_UserDevices WHERE userId=? and deviceId=? and deviceType=?", DMLUserDevice.toSelectParams(user.getId(), deviceId, deviceType), DMLUserDevice.getRowMapper());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public UserDeviceVO returnUserDevice(DeviceAppAgentVO deviceAppAgent) {
/*  539 */     return (UserDeviceVO)queryForObject("SELECT data, id, userId, deviceId, deviceType, model, alias, disabled, status, tsReg, tsUpdated FROM MMTH_UserDevices WHERE id=?", DMLUserDevice.toSelectPKParam(deviceAppAgent.getUserDeviceId()), DMLUserDevice.getRowMapper());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<UserDeviceVO> returnUserDevices(UserVO user) {
/*  549 */     return queryForList("SELECT data, id, userId, deviceId, deviceType, model, alias, disabled, status, tsReg, tsUpdated FROM MMTH_UserDevices WHERE userId=? ORDER BY tsReg desc", DMLUserDevice.toSelectParams(user), DMLUserDevice.getRowMapper());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DeviceAppAgentVO returnDeviceAppAgent(UserDeviceVO userDevice, AppAgentVO appAgent) {
/*  561 */     return (DeviceAppAgentVO)queryForObject("SELECT id, userDeviceId, agentId, authType, status, registrationId, tsReg, tsUpdated, tsDone, tsExpired FROM MMTH_DeviceAppAgents WHERE userDeviceId=? and agentId=? and authType=?", DMLDeviceAppAgent.toSelectParams(userDevice.getId(), appAgent.getId(), getAuthMethodTypes()), DMLDeviceAppAgent.getRowMapper());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FidoRegistrationVO returnFidoRegistration(String aaid, String keyId) {
/*  572 */     return (FidoRegistrationVO)queryForObject("SELECT f.data, f.id, f.aaid, f.keyId, f.tsReg, fu.deviceAgentId, fu.signCntUpdated FROM MMTH_FidoRegistrations f LEFT JOIN MMTH_FidoUsers fu ON f.id = fu.fidoRegId WHERE f.keyId = ? and f.aaid = ?", DMLFidoRegistration.toSelectParams(keyId, aaid), DMLFidoRegistration.getRowMapper());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ServerChallengeVO returnServerChallengeByUsername(String username) {
/*  583 */     return (ServerChallengeVO)queryForObject("SELECT id, username, challenge, challengeType, status, tsLifetime, transactionId, tranContent, deviceAgentId, tsDone FROM MMTH_ServerChallenge WHERE username=?", DMLServerChallenge.toSelectParams(username), DMLServerChallenge.getRowMapper());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ServerChallengeVO returnServerChallengeByTid(String tid) {
/*  594 */     return (ServerChallengeVO)queryForObject("SELECT id, username, challenge, challengeType, status, tsLifetime, transactionId, tranContent, deviceAgentId, tsDone FROM MMTH_ServerChallenge WHERE transactionId=?", DMLServerChallenge.toSelectParamsByTid(tid), DMLServerChallenge.getRowMapper());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AuthManagerVO returnAuthManager(UserDeviceVO userDevice) {
/*  605 */     return (AuthManagerVO)queryForObject("SELECT id, userId, userDeviceId, authType, authFailCnt, totSuccessCnt, tsLastAuth, tsLastAuthFail FROM MMTH_AuthManager WHERE userDeviceId=? AND authType=?", DMLAuthManager.toSelectParams(userDevice, getAuthMethodTypes()), DMLAuthManager.getRowMapper());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean checkIfHasFidoTC(FidoRegistrationVO fidoRegi, String tranHash) {
/*  617 */     return (queryForInt("SELECT count(*) FROM MMTH_FidoTransaction WHERE fidoRegId = ? and tranHash = ?", DMLFidoRegistration.toSelectFidoTranParam(fidoRegi, tranHash), 0) > 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<DeviceAppAgentVO> returnDeviceApps(UserDeviceVO userDevice, AuthMethodTypes authType) {
/*  629 */     return queryForList("SELECT id, userDeviceId, agentId, authType, status, registrationId, tsReg, tsUpdated, tsDone, tsExpired FROM MMTH_DeviceAppAgents WHERE userDeviceId=? AND authType=?", DMLDeviceAppAgent.toSelectUserDeviceAuthTypeParams(userDevice, authType), DMLDeviceAppAgent.getRowMapper());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<DeviceAppAgentVO> returnDeviceApps(UserDeviceVO userDevice) {
/*  640 */     return queryForList("SELECT id, userDeviceId, agentId, authType, status, registrationId, tsReg, tsUpdated, tsDone, tsExpired FROM MMTH_DeviceAppAgents WHERE userDeviceId=?", DMLDeviceAppAgent.toSelectParams(userDevice), DMLDeviceAppAgent.getRowMapper());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<DeviceAppAgentVO> returnDeviceApps(UserVO user, AuthMethodTypes authType) {
/*  652 */     return queryForList("SELECT id, userDeviceId, agentId, authType, status, registrationId, tsReg, tsUpdated, tsDone, tsExpired FROM MMTH_DeviceAppAgents WHERE userDeviceId in (SELECT id FROM MMTH_UserDevices WHERE userId=?) AND authType=?", DMLDeviceAppAgent.toSelectUserAuthTypeParams(user, authType), DMLDeviceAppAgent.getRowMapper());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<DeviceAppAgentVO> returnDeviceApps(UserVO user) {
/*  663 */     return queryForList("SELECT id, userDeviceId, agentId, authType, status, registrationId, tsReg, tsUpdated, tsDone, tsExpired FROM MMTH_DeviceAppAgents WHERE userDeviceId in (SELECT id FROM MMTH_UserDevices WHERE userId=?)", DMLDeviceAppAgent.toSelectUserParams(user), DMLDeviceAppAgent.getRowMapper());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DeviceAppAgentVO returnDeviceAppAgent(ServerChallengeVO serverChallenge) {
/*  674 */     return (DeviceAppAgentVO)queryForObject("SELECT id, userDeviceId, agentId, authType, status, registrationId, tsReg, tsUpdated, tsDone, tsExpired FROM MMTH_DeviceAppAgents WHERE id=?", DMLDeviceAppAgent.toSelectPKParam(serverChallenge.getDeviceAppId()), DMLDeviceAppAgent.getRowMapper());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DeviceAppAgentVO returnDeviceAppAgent(FidoRegistrationVO fidoRegi) {
/*  685 */     return (DeviceAppAgentVO)queryForObject("SELECT id, userDeviceId, agentId, authType, status, registrationId, tsReg, tsUpdated, tsDone, tsExpired FROM MMTH_DeviceAppAgents WHERE id=?", DMLDeviceAppAgent.toSelectPKParam(fidoRegi.getDeviceAgentId()), DMLDeviceAppAgent.getRowMapper());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TokenRegistrationVO returnTokenRegistration(DeviceAppAgentVO deviceAppAgent) {
/*  696 */     return (TokenRegistrationVO)queryForObject("SELECT tu.data, tu.tokenId, tu.deviceAgentId, t.tokenData, t.status, t.authType, t.username, t.lost, t.tsReg, t.tsOccupied, t.tsDiscard, t.tsLost FROM MMTH_TokenUsers tu LEFT JOIN MMTH_Tokens t ON tu.tokenId = t.tokenId WHERE tu.deviceAgentId=? ", DMLTokenRegistration.toSelectParams(deviceAppAgent), DMLTokenRegistration.getRowMapper());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void authenticationSuccess(DeviceAppAgentVO deviceAppAgent) {
/*  707 */     doUpdate("UPDATE MMTH_AuthManager set authFailCnt=0, totSuccessCnt=totSuccessCnt+1, tsLastAuth=? WHERE userDeviceId=? and authType=?", DMLAuthManager.toUpdateLatestParams(deviceAppAgent));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void authenticationFailed(DeviceAppAgentVO deviceAppAgent) {
/*  718 */     doUpdate("UPDATE MMTH_AuthManager set authFailCnt=authFailCnt+1, tsLastAuthFail=? WHERE userDeviceId=? and authType=?", DMLAuthManager.toUpdateLatestParams(deviceAppAgent));
/*      */   }
/*      */   
/*      */   public DeviceAppAgentVO returnDeviceAppAgent(int deviceAppId) {
/*  722 */     return (DeviceAppAgentVO)queryForObject("SELECT id, userDeviceId, agentId, authType, status, registrationId, tsReg, tsUpdated, tsDone, tsExpired FROM MMTH_DeviceAppAgents WHERE id=?", DMLDeviceAppAgent.toSelectPKParam(deviceAppId), DMLDeviceAppAgent.getRowMapper());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<AuthManagerVO> getAuthManagerByUser(UserVO user, AuthMethodTypes authType) {
/*  734 */     return queryForList("SELECT id, userId, userDeviceId, authType, authFailCnt, totSuccessCnt, tsLastAuth, tsLastAuthFail FROM MMTH_AuthManager WHERE userId=? AND authType=?", DMLAuthManager.toSelectListByUser(user, authType), DMLAuthManager.getRowMapper());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<AuthManagerVO> getAuthManagerByUserDevice(UserDeviceVO userDevice, AuthMethodTypes authType) {
/*  746 */     return queryForList("SELECT id, userId, userDeviceId, authType, authFailCnt, totSuccessCnt, tsLastAuth, tsLastAuthFail FROM MMTH_AuthManager WHERE userDeviceId=? AND authType=?", DMLAuthManager.toSelectListByUserDevice(userDevice, authType), DMLAuthManager.getRowMapper());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<AuthManagerVO> getAuthManagerForUserDevice(DeviceAppAgentVO deviceAppAgent) {
/*  757 */     return queryForList("SELECT id, userId, userDeviceId, authType, authFailCnt, totSuccessCnt, tsLastAuth, tsLastAuthFail FROM MMTH_AuthManager WHERE userDeviceId=? AND authType=?", DMLAuthManager.toSelectListForUserDevice(deviceAppAgent), DMLAuthManager.getRowMapper());
/*      */   }
/*      */   
/*      */   public UserVO returnUserWithRelation(String username) {
/*  761 */     return (UserVO)queryForObject("SELECT data, id, username, productType, multiLoginYN, disabled, status, tsReg, tsUpdated, annotationData, displayName, extUnique, customerCode, countryCode FROM MMTH_UserView WHERE username=?", DMLUserView.toSelectParams(username), DMLUserView.getRowMapper());
/*      */   }
/*      */   
/*      */   public List<ServiceLogVO> returnLatestServiceLogs(UserVO user, int limit) {
/*  765 */     List<ServiceLogVO> logs = queryForListWithLimit("SELECT id, authType, username, opType, reqType, actionType, returnCode, deviceId, null AS model, deviceType, pkgUnique, aaid, tokenId, description, tsReg, regDate, regHour FROM MMTH_ServiceLogs WHERE username=? AND authType=? AND opType not in (?, ?, ?, ?) ORDER BY id desc", DMLServiceLog.toSelectLogForOperation(user, getAuthMethodTypes()), DMLServiceLog.getRowMapper(), limit, 0);
/*      */ 
/*      */     
/*  768 */     if (!ExternalWebApiTypes.GLOBAL_WIBEE.equals(SystemSettingsDao.getWebApiPolicy()) && !ExternalWebApiTypes.MIRAE_ASSET_VT.equals(SystemSettingsDao.getWebApiPolicy())) {
/*  769 */       return logs;
/*      */     }
/*      */     
/*  772 */     for (ServiceLogVO vo : logs) {
/*  773 */       if (!ExternalWebApiTypes.GLOBAL_WIBEE.equals(SystemSettingsDao.getWebApiPolicy())) {
/*  774 */         MiraeAssetVietnamLogData logData = (MiraeAssetVietnamLogData)queryForObject("SELECT ip, macAddress FROM MMTH_ServiceLogAnnotations WHERE serviceLogId = ?", DMLServiceLogAnnotation.toSelect_miraeAssetVietnam(vo), MiraeAssetVietnamLogData.class, null);
/*      */         
/*  776 */         if (logData != null) {
/*  777 */           vo.setCustomData((ICustomLogData)logData);
/*      */         }
/*      */         continue;
/*      */       } 
/*  781 */       if (LogOperationTypes.ISSUE_CODE.equals(vo.getOpType()) || LogOperationTypes.FORCE_DEREG.equals(vo.getOpType()) || LogOperationTypes.LOST.equals(vo.getOpType()) || LogOperationTypes.RESET_AUTH_FAIL
/*  782 */         .equals(vo.getOpType())) {
/*      */         
/*  784 */         String regEmpNo = (String)queryForObject("SELECT regEmpNo FROM MMTH_ServiceLogAnnotations WHERE serviceLogId = ?", DMLServiceLogAnnotation.toSelect_globalWibee(vo), String.class, null);
/*      */         
/*  786 */         if (!StringUtils.isEmpty(regEmpNo)) {
/*  787 */           vo.setCustomData((ICustomLogData)new GlobalWibeeLogData(regEmpNo));
/*      */         }
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  793 */     return logs;
/*      */   }
/*      */ 
/*      */   
/*      */   public int returnRegisteredDeviceAppCnt(UserVO user, AuthMethodTypes authType) {
/*  798 */     return queryForInt("SELECT COUNT(*) FROM MMTH_DeviceAppAgents WHERE userDeviceId in (SELECT id FROM MMTH_UserDevices WHERE userId = ?) AND status = ? AND authType = ?", DMLDeviceAppAgent.toSelectActiveCntByUserAuthType(user, DeviceAppAgentStatus.AVAIABLE, authType), 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void procForceDereg(UserVO user, ICustomLogData customData, UserServiceLocator addLocator) {
/*  855 */     List<UserDeviceVO> userDevice = returnUserDevices(user);
/*      */     
/*  857 */     for (UserDeviceVO ud : userDevice) {
/*  858 */       procForceDereg(user, ud, customData, addLocator);
/*      */     }
/*      */     
/*  861 */     List<DeviceAppAgentVO> deviceAppAgents = returnDeviceApps(user);
/*      */     
/*  863 */     int activeCnt = 0;
/*      */     
/*  865 */     for (DeviceAppAgentVO deviceAppAgent : deviceAppAgents) {
/*  866 */       if (DeviceAppAgentStatus.AVAIABLE.equals(deviceAppAgent.getStatus())) {
/*  867 */         activeCnt++;
/*      */       }
/*      */     } 
/*      */     
/*  871 */     if (activeCnt > 0) {
/*  872 */       user.setStatus(UserStatus.AVAILABLE);
/*  873 */       user.setProductType(ProductType.BIOTP);
/*      */     } else {
/*  875 */       user.setTokenId(null);
/*  876 */       user.setStatus(UserStatus.DISCARD);
/*      */     } 
/*      */     
/*  879 */     user.setReprAppAgentId(-1);
/*      */     
/*  881 */     saveUser(user);
/*      */   }
/*      */   
/*      */   private void procForceDereg(UserVO user, UserDeviceVO userDevice, ICustomLogData customData, UserServiceLocator addLocator) {
/*  885 */     List<DeviceAppAgentVO> deviceAppAgents = returnDeviceApps(userDevice);
/*      */     
/*  887 */     int activeCnt = 0;
/*  888 */     for (DeviceAppAgentVO deviceAppAgent : deviceAppAgents) {
/*      */       
/*  890 */       if (getAuthMethodTypes().equals(deviceAppAgent.getAuthType())) {
/*  891 */         procForceDereg(user, userDevice, deviceAppAgent, customData, addLocator); continue;
/*  892 */       }  if (DeviceAppAgentStatus.AVAIABLE.equals(deviceAppAgent.getStatus())) {
/*  893 */         activeCnt++;
/*      */       }
/*      */     } 
/*      */     
/*  897 */     if (activeCnt > 0) {
/*  898 */       userDevice.setStatus(UserDeviceStatus.AVAILABLE);
/*      */     } else {
/*  900 */       userDevice.setStatus(UserDeviceStatus.DISCARD);
/*      */     } 
/*      */     
/*  903 */     userDevice.setAppRegCnt(activeCnt);
/*      */ 
/*      */     
/*  906 */     userDevice.resetUserVerificationFailCnt();
/*      */     
/*  908 */     saveUserDevice(userDevice);
/*      */   }
/*      */ 
/*      */   
/*      */   private void procForceDereg(UserVO user, UserDeviceVO userDevice, DeviceAppAgentVO deviceAppAgent, ICustomLogData customData, UserServiceLocator addLocator) {
/*  913 */     FidoRegistrationVO fidoRegi = null;
/*  914 */     TokenRegistrationVO tokenRegi = null;
/*  915 */     if (!AuthMethodTypes.SAOTP.equals(getAuthMethodTypes())) {
/*  916 */       fidoRegi = returnFidoRegistration(deviceAppAgent);
/*      */     }
/*      */     
/*  919 */     deleteFidoRegistration(deviceAppAgent);
/*      */     
/*  921 */     String tokenId = null;
/*  922 */     if (!AuthMethodTypes.FIDO.equals(deviceAppAgent.getAuthType())) {
/*      */       
/*  924 */       tokenRegi = (TokenRegistrationVO)queryForObject("SELECT tu.data, tu.tokenId, tu.deviceAgentId, t.tokenData, t.status, t.authType, t.username, t.lost, t.tsReg, t.tsOccupied, t.tsDiscard, t.tsLost FROM MMTH_TokenUsers tu LEFT JOIN MMTH_Tokens t ON tu.tokenId = t.tokenId WHERE tu.deviceAgentId=? ", DMLTokenRegistration.toSelectParams(deviceAppAgent), DMLTokenRegistration.getRowMapper());
/*      */       
/*  926 */       if (tokenRegi != null) {
/*  927 */         tokenId = tokenRegi.getTokenId();
/*      */         
/*  929 */         int assignCnt = queryForInt("SELECT COUNT(*) FROM MMTH_TokenUsers WHERE tokenId = ?", DMLTokenRegistration.toSelectTokenAssignedCnt(tokenRegi), 0);
/*      */         
/*  931 */         doUpdate("DELETE FROM MMTH_TokenUsers WHERE deviceAgentId=? and tokenId=?", DMLTokenRegistration.toDeleteParams(tokenRegi));
/*      */         
/*  933 */         if (assignCnt <= 1) {
/*      */ 
/*      */ 
/*      */           
/*  937 */           user.setTokenId(null);
/*  938 */           doUpdate("UPDATE MMTH_Tokens SET status=?, tsDiscard=? WHERE tokenId=?", DMLToken.toUpdateDiscardParams(tokenRegi.getTokenId()));
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  945 */     if (DeviceAppAgentStatus.AVAIABLE.equals(deviceAppAgent.getStatus())) {
/*  946 */       addLocator.setUser(user);
/*  947 */       addLocator.setDeviceAppAgent(deviceAppAgent);
/*  948 */       addLocator.setUserDevice(userDevice);
/*  949 */       addLocator.setFidoRegi(fidoRegi);
/*  950 */       addLocator.setTokenRegi(tokenRegi);
/*      */     } 
/*      */     
/*  953 */     doUpdate("DELETE FROM MMTH_DeviceAppAgents WHERE id=?", DMLDeviceAppAgent.toDeletePKParam(deviceAppAgent.getId()));
/*      */     
/*  955 */     doUpdate("DELETE FROM MMTH_ExtServiceItems WHERE userId=?", new Object[] { Integer.valueOf(user.getId()) });
/*      */     
/*  957 */     LogOperationTypes.FORCE_DEREG.addServiceLog(deviceAppAgent.getAuthType(), user.getUsername(), OpRequstTypes.HTTP_API, LogActionTypes.SUCCESS, ReturnCodes.OK, userDevice
/*  958 */         .getDeviceId(), userDevice.getDeviceType(), null, (fidoRegi != null) ? fidoRegi.getAaid() : null, tokenId, null, customData);
/*      */   }
/*      */ 
/*      */   
/*      */   protected List<FidoRegistrationVO> returnFidoRegistrations(DeviceAppAgentVO deviceAppAgent) {
/*  963 */     return queryForList("SELECT f.data, f.id, f.aaid, f.keyId, f.tsReg, fu.deviceAgentId, fu.signCntUpdated FROM MMTH_FidoRegistrations f LEFT JOIN MMTH_FidoUsers fu ON f.id = fu.fidoRegId WHERE fu.deviceAgentId = ?", DMLFidoRegistration.toSelectRefParam(deviceAppAgent), DMLFidoRegistration.getRowMapper());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setUserServiceLocator(UserServiceLocator locator, String username, String deviceId, DeviceTypes deviceType, String pkgUnique) {
/*  971 */     AppAgentVO appAgent = AppAgentDao.getAcceptableAppAgent(deviceType.getOsType(), pkgUnique);
/*      */     
/*  973 */     if (appAgent == null) {
/*      */       return;
/*      */     }
/*      */     
/*  977 */     locator.setAppAgent(appAgent);
/*      */ 
/*      */     
/*  980 */     UserVO user = returnUser(username);
/*      */     
/*  982 */     if (user == null) {
/*      */       return;
/*      */     }
/*      */     
/*  986 */     locator.setUser(user);
/*      */ 
/*      */     
/*  989 */     UserDeviceVO userDevice = returnUserDevice(user, deviceId, deviceType);
/*      */     
/*  991 */     if (userDevice == null) {
/*      */       return;
/*      */     }
/*      */     
/*  995 */     locator.setUserDevice(userDevice);
/*      */ 
/*      */     
/*  998 */     DeviceAppAgentVO deviceAppAgent = returnDeviceAppAgent(userDevice, appAgent);
/*      */     
/* 1000 */     if (deviceAppAgent != null) {
/* 1001 */       locator.setDeviceAppAgent(deviceAppAgent);
/*      */     }
/*      */ 
/*      */     
/* 1005 */     AuthManagerVO authManager = returnAuthManager(userDevice);
/*      */     
/* 1007 */     if (authManager != null) {
/* 1008 */       locator.setAuthManager(authManager);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void saveUser(UserVO user) throws InternalDBException {
/* 1016 */     if (-1 == user.getId()) {
/* 1017 */       insert(user);
/*      */     } else {
/* 1019 */       update(user);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void saveUserDevice(UserDeviceVO userDevice) {
/* 1026 */     if (-1 == userDevice.getId()) {
/* 1027 */       insert(userDevice);
/*      */     } else {
/* 1029 */       update(userDevice);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void saveFcmPushRegister(UserVO user, DeviceAppAgentVO deviceAppAgent) {
/* 1034 */     doInTransaction((TransactionCallbackWithoutResult)new Object(this, user, deviceAppAgent));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void insert(UserVO vo) throws InternalDBException {
/* 1046 */     vo.setId(doInsert("INSERT INTO MMTH_Users (username, accountName, disabled, productType, multiLoginYN, status, tsReg, data) VALUES (?, ?, ?, ?, ?, ?, ?, ?)", DMLUser.toInsertParams(vo), DMLUser.getInsertTypes()));
/*      */     
/* 1048 */     if (vo.getAnnotation() != null) {
/* 1049 */       vo.getAnnotation().setUserId(vo.getId());
/* 1050 */       doInsert("INSERT INTO MMTH_UserAnnotations (userId, username, displayName, extUnique, customerCode, countryCode, data) VALUES (?, ?, ?, ?, ?, ?, ?)", DMLUserAnnotation.toInsertParams(vo.getAnnotation()), DMLUserAnnotation.getInsertTypes());
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void update(UserVO vo) throws InternalDBException {
/* 1056 */     vo.setTsUpdated(System.currentTimeMillis());
/* 1057 */     doUpdate("UPDATE MMTH_Users set disabled=?, productType=?, multiLoginYN=?, status=?, tsUpdated=?, data=? WHERE id=?", DMLUser.toUpdateParams(vo), DMLUser.getUpdateTypes());
/*      */     
/* 1059 */     if (vo.getAnnotation() != null) {
/* 1060 */       doUpdate("DELETE FROM MMTH_UserAnnotations WHERE userId=?", DMLUserAnnotation.toDeleteParams(vo));
/* 1061 */       vo.getAnnotation().setUserId(vo.getId());
/* 1062 */       doInsert("INSERT INTO MMTH_UserAnnotations (userId, username, displayName, extUnique, customerCode, countryCode, data) VALUES (?, ?, ?, ?, ?, ?, ?)", DMLUserAnnotation.toInsertParams(vo.getAnnotation()), DMLUserAnnotation.getInsertTypes());
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void updateUserPolicy(UserVO vo) throws InternalDBException {
/* 1068 */     doInTransaction((TransactionCallbackWithoutResult)new Object(this, vo));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateUserAuthType(UserVO vo) throws InternalDBException {
/* 1080 */     doInTransaction((TransactionCallbackWithoutResult)new Object(this, vo));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void insert(UserDeviceVO userDevice) throws InternalDBException {
/* 1091 */     userDevice.setId(doInsert("INSERT INTO MMTH_UserDevices (userId, deviceId, deviceType, model, alias, disabled, status, tsReg, data) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)", DMLUserDevice.toInsertParams(userDevice), DMLUserDevice.getInsertTypes()));
/*      */   }
/*      */   
/*      */   private void update(UserDeviceVO userDevice) throws InternalDBException {
/* 1095 */     userDevice.setTsUpdated(System.currentTimeMillis());
/* 1096 */     doUpdate("UPDATE MMTH_UserDevices set alias=?, disabled=?, status=?, tsUpdated=?, data=? WHERE id=?", DMLUserDevice.toUpdateParams(userDevice), DMLUserDevice.getUpdateTypes());
/*      */   }
/*      */ 
/*      */   
/*      */   protected void saveDeviceAppAgent(DeviceAppAgentVO deviceAppAgent) {
/* 1101 */     if (-1 == deviceAppAgent.getId()) {
/* 1102 */       insert(deviceAppAgent);
/*      */     } else {
/* 1104 */       update(deviceAppAgent);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void insert(DeviceAppAgentVO deviceAppAgent) throws InternalDBException {
/* 1109 */     deviceAppAgent.setId(doInsert("INSERT INTO MMTH_DeviceAppAgents (userDeviceId, agentId, authType, status, tsReg) VALUES (?, ?, ?, ?, ?)", DMLDeviceAppAgent.toInsertParams(deviceAppAgent), DMLDeviceAppAgent.getInsertTypes()));
/*      */   }
/*      */   
/*      */   private void update(DeviceAppAgentVO deviceAppAgent) throws InternalDBException {
/* 1113 */     deviceAppAgent.setTsUpdated(System.currentTimeMillis());
/* 1114 */     doUpdate("UPDATE MMTH_DeviceAppAgents set status=?, registrationId=?, tsUpdated=?, tsDone=?, tsExpired=? WHERE id=?", DMLDeviceAppAgent.toUpdateParams(deviceAppAgent), DMLDeviceAppAgent.getUpdateTypes());
/*      */   }
/*      */ 
/*      */   
/*      */   protected void saveAuthManager(AuthManagerVO authManager) throws InternalDBException {
/* 1119 */     if (-1 == authManager.getId()) {
/* 1120 */       insert(authManager);
/*      */     } else {
/* 1122 */       update(authManager);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void insert(AuthManagerVO authManager) throws InternalDBException {
/* 1127 */     authManager.setId(doInsert("INSERT INTO MMTH_AuthManager (userId, userDeviceId, authType, authFailCnt, totSuccessCnt, tsLastAuth, tsLastAuthFail) VALUES (?, ?, ?, ?, ?, ?, ?)", DMLAuthManager.toInsertParams(authManager), DMLAuthManager.getInsertTypes()));
/*      */   }
/*      */   
/*      */   private void update(AuthManagerVO authManager) throws InternalDBException {
/* 1131 */     doUpdate("UPDATE MMTH_AuthManager set authFailCnt=?, totSuccessCnt=?, tsLastAuth=?, tsLastAuthFail=? WHERE userDeviceId=? and authType=?", DMLAuthManager.toUpdateParams(authManager), DMLAuthManager.getUpdateTypes());
/*      */   }
/*      */   
/*      */   protected void saveServerChallenge(ServerChallengeVO serverChallenge, UserVO user) {
/* 1135 */     if (serverChallenge == null) {
/* 1136 */       throw new InternalDBException("param is null");
/*      */     }
/* 1138 */     if (-1L == serverChallenge.getId()) {
/* 1139 */       deleteSeverChallenge(user);
/* 1140 */       insert(serverChallenge);
/*      */     } else {
/* 1142 */       update(serverChallenge);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void insert(ServerChallengeVO serverChallenge) throws InternalDBException {
/* 1148 */     serverChallenge.setId(doInsert("INSERT INTO MMTH_ServerChallenge (username, challenge, challengeType, status, tsLifetime, transactionId, tranContent, deviceAgentId) VALUES (?, ?, ?, ?, ?, ?, ?, ?)", DMLServerChallenge.toInsertParams(serverChallenge), DMLServerChallenge.getInsertTypes()));
/*      */   }
/*      */   
/*      */   private void update(ServerChallengeVO serverChallenge) throws InternalDBException {
/* 1152 */     doUpdate("UPDATE MMTH_ServerChallenge set status=?, tsDone=? WHERE id=?", DMLServerChallenge.toUpdateParams(serverChallenge), DMLServerChallenge.getUpdateTypes());
/*      */   }
/*      */   
/*      */   protected void deleteSeverChallenge(UserVO user) throws InternalDBException {
/* 1156 */     doUpdate("DELETE FROM MMTH_ServerChallenge WHERE username=?", DMLServerChallenge.toDeleteParams(user));
/*      */   }
/*      */   
/*      */   protected void deleteSeverChallenge(ServerChallengeVO serverChallenge) throws InternalDBException {
/* 1160 */     doUpdate("DELETE FROM MMTH_ServerChallenge WHERE id=?", DMLServerChallenge.toDeletePKParam(serverChallenge.getId()));
/*      */   }
/*      */   
/*      */   protected void deleteFidoRegistration(DeviceAppAgentVO deviceAppAgent) throws InternalDBException {
/* 1164 */     doUpdate("DELETE FROM MMTH_FidoRegistrations WHERE id=(SELECT fidoRegId FROM MMTH_FidoUsers WHERE deviceAgentId=?)", DMLFidoRegistration.toDeleteRefParams(deviceAppAgent));
/*      */   }
/*      */   
/*      */   protected void updateFidoUser(FidoRegistrationVO fidoRegi) {
/* 1168 */     doUpdate("UPDATE MMTH_FidoUsers set signCntUpdated=? WHERE fidoRegId=?", DMLFidoRegistration.toUpdateFidoUserParams(fidoRegi), DMLFidoRegistration.getUpdateFidoUserTypes());
/*      */   }
/*      */   
/*      */   protected FidoRegistrationVO returnFidoRegistration(DeviceAppAgentVO deviceAppAgent) {
/* 1172 */     return (FidoRegistrationVO)queryForObject("SELECT f.data, f.id, f.aaid, f.keyId, f.tsReg, fu.deviceAgentId, fu.signCntUpdated FROM MMTH_FidoRegistrations f LEFT JOIN MMTH_FidoUsers fu ON f.id = fu.fidoRegId WHERE fu.deviceAgentId = ?", DMLFidoRegistration.toSelectRefParam(deviceAppAgent), DMLFidoRegistration.getRowMapper());
/*      */   }
/*      */   
/*      */   public TokenRegistrationVO returnTokenRegistration(String tokenId) {
/* 1176 */     return (TokenRegistrationVO)queryForObject("SELECT tu.data, tu.tokenId, tu.deviceAgentId, t.tokenData, t.status, t.authType, t.username, t.lost, t.tsReg, t.tsOccupied, t.tsDiscard, t.tsLost FROM MMTH_TokenUsers tu LEFT JOIN MMTH_Tokens t ON tu.tokenId = t.tokenId WHERE t.tokenId=? ", DMLTokenRegistration.toSelectByToken(tokenId), DMLTokenRegistration.getRowMapper());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static com.dreammirae.mmth.db.dao.authentication.UserServiceDao getInstance(AuthMethodTypes authType) {
/* 1182 */     if (AuthMethodTypes.FIDO.equals(authType))
/* 1183 */       return (com.dreammirae.mmth.db.dao.authentication.UserServiceDao)new FidoUserServiceDao(); 
/* 1184 */     if (AuthMethodTypes.BIOTP.equals(authType)) {
/* 1185 */       return (com.dreammirae.mmth.db.dao.authentication.UserServiceDao)new BiotpUserServiceDao();
/*      */     }
/*      */     
/* 1188 */     return null;
/*      */   }
/*      */   
/*      */   protected abstract void setUserServiceForRegImp(UserServiceLocator paramUserServiceLocator, String paramString1, String paramString2, DeviceTypes paramDeviceTypes, String paramString3);
/*      */   
/*      */   protected abstract void setUserServiceForAuthImp(UserServiceLocator paramUserServiceLocator, String paramString1, String paramString2, DeviceTypes paramDeviceTypes, String paramString3);
/*      */   
/*      */   protected abstract void setUserServiceForDeregImp(UserServiceLocator paramUserServiceLocator, String paramString1, String paramString2, DeviceTypes paramDeviceTypes, String paramString3);
/*      */   
/*      */   protected abstract void saveRegRequestImp(UserServiceLocator paramUserServiceLocator);
/*      */   
/*      */   protected abstract void saveAuthRequestImp(UserServiceLocator paramUserServiceLocator);
/*      */   
/*      */   protected abstract void saveDeregRequestImp(UserServiceLocator paramUserServiceLocator);
/*      */   
/*      */   protected abstract void saveRegResponseImp(UserServiceLocator paramUserServiceLocator);
/*      */   
/*      */   protected abstract void saveAuthResponseImp(UserServiceLocator paramUserServiceLocator);
/*      */   
/*      */   protected abstract void saveOTPResponseImp(UserServiceLocator paramUserServiceLocator);
/*      */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\db\dao\authentication\UserServiceDao.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */