/*     */ package WEB-INF.classes.com.dreammirae.mmth.db.dao.authentication;
/*     */ 
/*     */ import com.dreammirae.mmth.authentication.AuthMethodTypes;
/*     */ import com.dreammirae.mmth.authentication.ReturnCodes;
/*     */ import com.dreammirae.mmth.authentication.bean.ExtensionDataLocator;
/*     */ import com.dreammirae.mmth.authentication.bean.UserServiceLocator;
/*     */ import com.dreammirae.mmth.db.dao.authentication.UserServiceDao;
/*     */ import com.dreammirae.mmth.db.dao.queries.DMLFidoRegistration;
/*     */ import com.dreammirae.mmth.db.dao.queries.DMLServerChallenge;
/*     */ import com.dreammirae.mmth.db.dao.queries.DMLToken;
/*     */ import com.dreammirae.mmth.db.dao.queries.DMLTokenRegistration;
/*     */ import com.dreammirae.mmth.exception.InternalDBException;
/*     */ import com.dreammirae.mmth.exception.ReturnCodeException;
/*     */ import com.dreammirae.mmth.vo.DeviceAppAgentVO;
/*     */ import com.dreammirae.mmth.vo.FidoRegistrationVO;
/*     */ import com.dreammirae.mmth.vo.ServerChallengeVO;
/*     */ import com.dreammirae.mmth.vo.TokenRegistrationVO;
/*     */ import com.dreammirae.mmth.vo.TokenVO;
/*     */ import com.dreammirae.mmth.vo.UserDeviceVO;
/*     */ import com.dreammirae.mmth.vo.UserVO;
/*     */ import com.dreammirae.mmth.vo.types.ChallengeStatus;
/*     */ import com.dreammirae.mmth.vo.types.DeviceTypes;
/*     */ import com.dreammirae.mmth.vo.types.MethodStatus;
/*     */ import java.util.List;
/*     */ import org.springframework.stereotype.Repository;
/*     */ import org.springframework.transaction.support.TransactionCallbackWithoutResult;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Repository("biotpUserServiceDao")
/*     */ public class BiotpUserServiceDao
/*     */   extends UserServiceDao
/*     */ {
/*     */   public AuthMethodTypes getAuthMethodTypes() {
/*  37 */     return AuthMethodTypes.BIOTP;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setUserServiceForRegImp(UserServiceLocator locator, String username, String deviceId, DeviceTypes deviceType, String pkgUnique) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setUserServiceForAuthImp(UserServiceLocator locator, String username, String deviceId, DeviceTypes deviceType, String pkgUnique) {
/*  50 */     if (locator.getDeviceAppAgent() == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*  55 */     List<FidoRegistrationVO> fidoRegis = returnFidoRegistrations(locator.getDeviceAppAgent());
/*  56 */     locator.setFidoRegistrations(fidoRegis);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setUserServiceForDeregImp(UserServiceLocator locator, String username, String deviceId, DeviceTypes deviceType, String pkgUnique) {
/*  63 */     if (locator.getDeviceAppAgent() == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*  68 */     List<FidoRegistrationVO> fidoRegis = returnFidoRegistrations(locator.getDeviceAppAgent());
/*  69 */     locator.setFidoRegistrations(fidoRegis);
/*     */ 
/*     */     
/*  72 */     TokenRegistrationVO tokenRegi = returnTokenRegistration(locator.getDeviceAppAgent());
/*  73 */     locator.setTokenRegi(tokenRegi);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void saveRegRequestImp(UserServiceLocator locator) {
/*  78 */     ServerChallengeVO svrChallenge = locator.getChallenge();
/*     */ 
/*     */     
/*  81 */     svrChallenge.setDeviceAppId(locator.getDeviceAppAgent().getId());
/*     */ 
/*     */     
/*  84 */     saveServerChallenge(locator.getChallenge(), locator.getUser());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void saveAuthRequestImp(UserServiceLocator locator) {
/*  89 */     List<FidoRegistrationVO> list = locator.getFidoRegistrations();
/*     */     
/*  91 */     if (list == null || list.isEmpty()) {
/*     */       return;
/*     */     }
/*     */     
/*  95 */     for (FidoRegistrationVO vo : list) {
/*     */       
/*  97 */       if (vo.getTransactions() == null || vo.getTransactions().isEmpty()) {
/*     */         continue;
/*     */       }
/*     */       
/* 101 */       doSaveAsBatch("INSERT INTO MMTH_FidoTransaction (challengeId, fidoRegId, tranHash, tsReg) VALUES (?, ?, ?, ?)", DMLFidoRegistration.getFidoTranBatchPreparedStatementSetter(locator.getChallenge(), vo));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void saveDeregRequestImp(UserServiceLocator locator) {
/* 108 */     deleteFidoRegistration(locator.getDeviceAppAgent());
/*     */ 
/*     */ 
/*     */     
/* 112 */     TokenRegistrationVO tokenRegi = returnTokenRegistration(locator.getDeviceAppAgent());
/*     */ 
/*     */     
/* 115 */     int assignCnt = queryForInt("SELECT COUNT(*) FROM MMTH_TokenUsers WHERE tokenId = ?", DMLTokenRegistration.toSelectTokenAssignedCnt(tokenRegi), 0);
/*     */     
/* 117 */     doUpdate("DELETE FROM MMTH_TokenUsers WHERE deviceAgentId=? and tokenId=?", DMLTokenRegistration.toDeleteParams(tokenRegi));
/*     */     
/* 119 */     if (assignCnt <= 1) {
/*     */ 
/*     */ 
/*     */       
/* 123 */       locator.getUser().setTokenId(null);
/* 124 */       doUpdate("UPDATE MMTH_Tokens SET status=?, tsDiscard=? WHERE tokenId=?", DMLToken.toUpdateDiscardParams(tokenRegi.getTokenId()));
/*     */     } 
/*     */     
/* 127 */     locator.getUser().setmOtpIssueStatus(MethodStatus.DISCARD);
/* 128 */     locator.getUser().setTsMOtpStatusUpdated(System.currentTimeMillis());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void saveRegResponseImp(UserServiceLocator locator) {
/* 135 */     UserVO user = locator.getUser();
/* 136 */     FidoRegistrationVO newFidoRegi = locator.getFidoRegi();
/* 137 */     DeviceAppAgentVO deviceAppAgent = locator.getDeviceAppAgent();
/*     */ 
/*     */     
/* 140 */     newFidoRegi.setDeviceAgentId(deviceAppAgent.getId());
/* 141 */     newFidoRegi.setId(doInsert("INSERT INTO MMTH_FidoRegistrations (aaid, keyId, tsReg, data) VALUES (?, ?, ?, ?)", DMLFidoRegistration.toInsertParams(newFidoRegi), DMLFidoRegistration.getInsertTypes()));
/*     */     
/* 143 */     doInsert("INSERT INTO MMTH_FidoUsers (deviceAgentId, fidoRegId, signCntUpdated, tsReg) VALUES (?, ?, ?, ?)", DMLFidoRegistration.toInsertFidoUserParams(newFidoRegi), DMLFidoRegistration.getInsertFidoUserTypes());
/*     */ 
/*     */     
/* 146 */     TokenVO token = releaseToken(this.ejt, user.getUsername(), getAuthMethodTypes());
/*     */     
/* 148 */     if (token == null) {
/* 149 */       throw new ReturnCodeException(ReturnCodes.NO_OTP_KEY_TO_ASSIGN, "There has no OTP key to assign to user. username=" + user.getUsername());
/*     */     }
/*     */     
/* 152 */     ExtensionDataLocator extLoc = locator.getExtLoc();
/*     */     
/* 154 */     TokenRegistrationVO tokenRegi = TokenRegistrationVO.createTokenRegistration(deviceAppAgent, token, extLoc
/* 155 */         .getOtpPublicKey(), extLoc.getRandomSeedKey(), extLoc.getRandomChallenge());
/*     */ 
/*     */ 
/*     */     
/* 159 */     doInsert("INSERT INTO MMTH_TokenUsers (tokenId, deviceAgentId, tsReg, data) VALUES (?, ?, ?, ?)", DMLTokenRegistration.toInsertParams(tokenRegi), DMLTokenRegistration.getInsertTypes());
/*     */ 
/*     */     
/* 162 */     locator.setTokenRegi(tokenRegi);
/*     */ 
/*     */     
/* 165 */     user.setTokenId(token.getTokenId());
/*     */     
/* 167 */     ServerChallengeVO challenge = locator.getChallenge();
/*     */ 
/*     */     
/* 170 */     challenge.setStatus(ChallengeStatus.OTP_REQ);
/* 171 */     doUpdate("UPDATE MMTH_ServerChallenge set status=?, tsDone=? WHERE id=?", DMLServerChallenge.toUpdateParams(challenge));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void saveAuthResponseImp(UserServiceLocator locator) {
/* 179 */     updateFidoUser(locator.getFidoRegi());
/*     */ 
/*     */     
/* 182 */     ServerChallengeVO challenge = locator.getChallenge();
/* 183 */     challenge.setStatus(ChallengeStatus.OTP_REQ);
/* 184 */     doUpdate("UPDATE MMTH_ServerChallenge set status=?, tsDone=? WHERE id=?", DMLServerChallenge.toUpdateParams(challenge));
/*     */ 
/*     */     
/* 187 */     UserDeviceVO userDevice = returnUserDevice(locator.getDeviceAppAgent());
/*     */     
/* 189 */     if (userDevice.getUserVerificationFailCnt() > 0) {
/* 190 */       userDevice.resetUserVerificationFailCnt();
/* 191 */       saveUserDevice(userDevice);
/*     */     } 
/*     */ 
/*     */     
/* 195 */     TokenRegistrationVO tokenRegi = returnTokenRegistration(locator.getDeviceAppAgent());
/* 196 */     locator.setTokenRegi(tokenRegi);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void saveOTPResponseImp(UserServiceLocator locator) {
/* 202 */     authenticationSuccess(locator.getDeviceAppAgent());
/*     */ 
/*     */     
/* 205 */     ServerChallengeVO challenge = locator.getChallenge();
/* 206 */     challenge.setStatus(ChallengeStatus.DONE);
/* 207 */     challenge.setTsDone(System.currentTimeMillis());
/* 208 */     doUpdate("UPDATE MMTH_ServerChallenge set status=?, tsDone=? WHERE id=?", DMLServerChallenge.toUpdateParams(challenge));
/*     */     
/* 210 */     doUpdate("DELETE FROM MMTH_ExtServiceItems WHERE userId=?", new Object[] { Integer.valueOf(locator.getUser().getId()) });
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateUserPolicy(UserVO vo) throws InternalDBException {
/* 215 */     doInTransaction((TransactionCallbackWithoutResult)new Object(this, vo));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateUserAuthType(UserVO vo) throws InternalDBException {
/* 227 */     doInTransaction((TransactionCallbackWithoutResult)new Object(this, vo));
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\db\dao\authentication\BiotpUserServiceDao.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */