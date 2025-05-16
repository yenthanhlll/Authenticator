/*     */ package WEB-INF.classes.com.dreammirae.mmth.vo;
/*     */ 
/*     */ import com.dreammirae.mmth.authentication.AuthMethodTypes;
/*     */ import com.dreammirae.mmth.vo.UserDeviceVO;
/*     */ import com.dreammirae.mmth.vo.bean.json.TimestampSerializer;
/*     */ import com.fasterxml.jackson.databind.annotation.JsonSerialize;
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
/*     */ public class AuthManagerVO
/*     */ {
/*  33 */   private int id = -1;
/*  34 */   private int userId = -1;
/*  35 */   private int userDeviceId = -1;
/*     */ 
/*     */   
/*     */   private AuthMethodTypes authType;
/*     */   
/*     */   private int authFailCnt;
/*     */   
/*     */   private int totSuceessCnt;
/*     */   
/*     */   @JsonSerialize(using = TimestampSerializer.class)
/*     */   private long tsLastAuth;
/*     */   
/*     */   @JsonSerialize(using = TimestampSerializer.class)
/*     */   private long tsLastAuthFail;
/*     */ 
/*     */   
/*     */   public int getId() {
/*  52 */     return this.id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setId(int id) {
/*  59 */     this.id = id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getUserId() {
/*  66 */     return this.userId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUserId(int userId) {
/*  73 */     this.userId = userId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getUserDeviceId() {
/*  80 */     return this.userDeviceId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUserDeviceId(int userDeviceId) {
/*  87 */     this.userDeviceId = userDeviceId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AuthMethodTypes getAuthType() {
/*  94 */     return this.authType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAuthType(AuthMethodTypes authType) {
/* 101 */     this.authType = authType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getAuthFailCnt() {
/* 108 */     return this.authFailCnt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAuthFailCnt(int authFailCnt) {
/* 115 */     this.authFailCnt = authFailCnt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTotSuceessCnt() {
/* 122 */     return this.totSuceessCnt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTotSuceessCnt(int totSuceessCnt) {
/* 129 */     this.totSuceessCnt = totSuceessCnt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getTsLastAuth() {
/* 136 */     return this.tsLastAuth;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTsLastAuth(long tsLastAuth) {
/* 143 */     this.tsLastAuth = tsLastAuth;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getTsLastAuthFail() {
/* 150 */     return this.tsLastAuthFail;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTsLastAuthFail(long tsLastAuthFail) {
/* 157 */     this.tsLastAuthFail = tsLastAuthFail;
/*     */   }
/*     */   
/*     */   public static com.dreammirae.mmth.vo.AuthManagerVO createNewAuthManager(UserDeviceVO userDevice, AuthMethodTypes authType) {
/* 161 */     com.dreammirae.mmth.vo.AuthManagerVO vo = new com.dreammirae.mmth.vo.AuthManagerVO();
/*     */     
/* 163 */     if (userDevice != null) {
/* 164 */       vo.setUserId(userDevice.getId());
/* 165 */       vo.setUserDeviceId(userDevice.getId());
/*     */     } 
/* 167 */     vo.setAuthType(authType);
/* 168 */     return vo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 176 */     StringBuilder builder = new StringBuilder();
/* 177 */     builder.append("AuthManagerVO [id=").append(this.id).append(", userId=").append(this.userId).append(", userDeviceId=").append(this.userDeviceId).append(", authType=").append(this.authType)
/* 178 */       .append(", authFailCnt=").append(this.authFailCnt).append(", totSuceessCnt=").append(this.totSuceessCnt).append(", tsLastAuth=").append(this.tsLastAuth).append(", tsLastAuthFail=")
/* 179 */       .append(this.tsLastAuthFail).append("]");
/* 180 */     return builder.toString();
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\vo\AuthManagerVO.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */