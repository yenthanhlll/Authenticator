/*     */ package WEB-INF.classes.com.dreammirae.mmth.db.dao.authentication;
/*     */ 
/*     */ import com.dreammirae.mmth.authentication.AuthMethodTypes;
/*     */ import com.dreammirae.mmth.authentication.bean.UserServiceLocator;
/*     */ import com.dreammirae.mmth.db.dao.authentication.UserServiceDao;
/*     */ import com.dreammirae.mmth.db.dao.queries.DMLFidoRegistration;
/*     */ import com.dreammirae.mmth.db.dao.queries.DMLServerChallenge;
/*     */ import com.dreammirae.mmth.vo.DeviceAppAgentVO;
/*     */ import com.dreammirae.mmth.vo.FidoRegistrationVO;
/*     */ import com.dreammirae.mmth.vo.ServerChallengeVO;
/*     */ import com.dreammirae.mmth.vo.types.DeviceTypes;
/*     */ import java.util.List;
/*     */ import org.springframework.stereotype.Repository;
/*     */ 
/*     */ 
/*     */ @Repository("fidoUserServiceDao")
/*     */ public class FidoUserServiceDao
/*     */   extends UserServiceDao
/*     */ {
/*     */   public AuthMethodTypes getAuthMethodTypes() {
/*  21 */     return AuthMethodTypes.FIDO;
/*     */   }
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
/*  33 */     if (locator.getDeviceAppAgent() == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*  38 */     List<FidoRegistrationVO> fidoRegis = returnFidoRegistrations(locator.getDeviceAppAgent());
/*  39 */     locator.setFidoRegistrations(fidoRegis);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setUserServiceForDeregImp(UserServiceLocator locator, String username, String deviceId, DeviceTypes deviceType, String pkgUnique) {
/*  47 */     if (locator.getDeviceAppAgent() == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  53 */     List<FidoRegistrationVO> fidoRegis = returnFidoRegistrations(locator.getDeviceAppAgent());
/*  54 */     locator.setFidoRegistrations(fidoRegis);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void saveRegRequestImp(UserServiceLocator locator) {
/*  60 */     ServerChallengeVO svrChallenge = locator.getChallenge();
/*     */ 
/*     */     
/*  63 */     svrChallenge.setDeviceAppId(locator.getDeviceAppAgent().getId());
/*     */ 
/*     */     
/*  66 */     saveServerChallenge(locator.getChallenge(), locator.getUser());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void saveAuthRequestImp(UserServiceLocator locator) {
/*  72 */     List<FidoRegistrationVO> list = locator.getFidoRegistrations();
/*     */     
/*  74 */     if (list == null || list.isEmpty()) {
/*     */       return;
/*     */     }
/*     */     
/*  78 */     for (FidoRegistrationVO vo : list) {
/*     */       
/*  80 */       if (vo.getTransactions() == null || vo.getTransactions().isEmpty()) {
/*     */         continue;
/*     */       }
/*     */       
/*  84 */       doSaveAsBatch("INSERT INTO MMTH_FidoTransaction (challengeId, fidoRegId, tranHash, tsReg) VALUES (?, ?, ?, ?)", DMLFidoRegistration.getFidoTranBatchPreparedStatementSetter(locator.getChallenge(), vo));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void saveDeregRequestImp(UserServiceLocator locator) {
/*  91 */     deleteFidoRegistration(locator.getDeviceAppAgent());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void saveRegResponseImp(UserServiceLocator locator) {
/*  96 */     FidoRegistrationVO newFidoRegi = locator.getFidoRegi();
/*  97 */     DeviceAppAgentVO deviceAppAgent = locator.getDeviceAppAgent();
/*     */ 
/*     */     
/* 100 */     newFidoRegi.setDeviceAgentId(deviceAppAgent.getId());
/* 101 */     newFidoRegi.setId(doInsert("INSERT INTO MMTH_FidoRegistrations (aaid, keyId, tsReg, data) VALUES (?, ?, ?, ?)", DMLFidoRegistration.toInsertParams(newFidoRegi), DMLFidoRegistration.getInsertTypes()));
/*     */     
/* 103 */     doInsert("INSERT INTO MMTH_FidoUsers (deviceAgentId, fidoRegId, signCntUpdated, tsReg) VALUES (?, ?, ?, ?)", DMLFidoRegistration.toInsertFidoUserParams(newFidoRegi), DMLFidoRegistration.getInsertFidoUserTypes());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void saveAuthResponseImp(UserServiceLocator locator) {
/* 108 */     authenticationSuccess(locator.getDeviceAppAgent());
/* 109 */     updateFidoUser(locator.getFidoRegi());
/*     */     
/* 111 */     doUpdate("UPDATE MMTH_ServerChallenge set status=?, tsDone=? WHERE id=?", DMLServerChallenge.toUpdateParams(locator.getChallenge()));
/*     */   }
/*     */   
/*     */   protected void saveOTPResponseImp(UserServiceLocator locator) {}
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\db\dao\authentication\FidoUserServiceDao.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */