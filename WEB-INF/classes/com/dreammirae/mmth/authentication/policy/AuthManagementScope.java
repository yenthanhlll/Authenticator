/*     */ package WEB-INF.classes.com.dreammirae.mmth.authentication.policy;
/*     */ 
/*     */ import com.dreammirae.mmth.authentication.AuthMethodTypes;
/*     */ import com.dreammirae.mmth.authentication.ReturnCodes;
/*     */ import com.dreammirae.mmth.authentication.bean.AuthenticationInfos;
/*     */ import com.dreammirae.mmth.db.dao.SystemSettingsDao;
/*     */ import com.dreammirae.mmth.db.dao.authentication.IAuthenticationManagerDao;
/*     */ import com.dreammirae.mmth.exception.ReturnCodeException;
/*     */ import com.dreammirae.mmth.util.bean.KeyValuePair;
/*     */ import com.dreammirae.mmth.vo.AuthManagerVO;
/*     */ import com.dreammirae.mmth.vo.DeviceAppAgentVO;
/*     */ import com.dreammirae.mmth.vo.UserDeviceVO;
/*     */ import com.dreammirae.mmth.vo.UserVO;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ public enum AuthManagementScope
/*     */ {
/*  20 */   BY_USER("AuthManagerScope.BY_USER"),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  62 */   BY_USER_DEVICE("AuthManagerScope.BY_USER_DEVICE");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final String messageKey;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   AuthManagementScope(String messageKey) {
/* 109 */     this.messageKey = messageKey;
/*     */   }
/*     */   
/*     */   public String getMessageKey() {
/* 113 */     return this.messageKey;
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
/*     */ 
/*     */   
/*     */   private static void validateAuthenticationCount(List<AuthManagerVO> list, UserVO user) {
/* 127 */     int cnt = 0;
/* 128 */     for (AuthManagerVO vo : list) {
/* 129 */       cnt += vo.getAuthFailCnt();
/*     */     }
/*     */     
/* 132 */     int maxErrCnt = SystemSettingsDao.getMaxAuthErrorCnt();
/*     */     
/* 134 */     if (cnt >= maxErrCnt) {
/* 135 */       throw new ReturnCodeException(ReturnCodes.EXCEEDED_AUTH_ERROR, "The user[=" + user
/* 136 */           .getUsername() + "] has exceeded the error count...");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static List<KeyValuePair<String, String>> getMessageKeyPair() {
/* 142 */     List<KeyValuePair<String, String>> list = new ArrayList<>(3);
/*     */     
/* 144 */     for (com.dreammirae.mmth.authentication.policy.AuthManagementScope type : values()) {
/* 145 */       list.add(new KeyValuePair(type.name(), type.getMessageKey()));
/*     */     }
/*     */     
/* 148 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   public static com.dreammirae.mmth.authentication.policy.AuthManagementScope valueByName(String name) {
/*     */     try {
/* 154 */       return valueOf(name);
/* 155 */     } catch (Exception e) {
/* 156 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public abstract void validateAuthentication(IAuthenticationManagerDao paramIAuthenticationManagerDao, UserVO paramUserVO, UserDeviceVO paramUserDeviceVO, AuthMethodTypes paramAuthMethodTypes);
/*     */   
/*     */   public abstract void validateAuthentication(IAuthenticationManagerDao paramIAuthenticationManagerDao, UserVO paramUserVO, DeviceAppAgentVO paramDeviceAppAgentVO);
/*     */   
/*     */   public abstract AuthenticationInfos getLatestAuthManagerByUser(IAuthenticationManagerDao paramIAuthenticationManagerDao, UserVO paramUserVO, AuthMethodTypes paramAuthMethodTypes);
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\authentication\policy\AuthManagementScope.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */