/*     */ package WEB-INF.classes.com.dreammirae.mmth.vo.bean;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HwOtpResp
/*     */ {
/*     */   private String returnCode;
/*     */   private String challenge;
/*     */   private String unlockCode;
/*     */   private Integer unlockCnt;
/*     */   private String userName;
/*     */   private String multiLoginYN;
/*     */   private String status;
/*     */   private Integer productTypeCode;
/*     */   private String sessionCode;
/*     */   
/*     */   public String getReturnCode() {
/*  23 */     return this.returnCode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setReturnCode(String returnCode) {
/*  30 */     this.returnCode = returnCode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getChallenge() {
/*  37 */     return this.challenge;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setChallenge(String challenge) {
/*  44 */     this.challenge = challenge;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getUnlockCode() {
/*  51 */     return this.unlockCode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUnlockCode(String unlockCode) {
/*  58 */     this.unlockCode = unlockCode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer getUnlockCnt() {
/*  66 */     return this.unlockCnt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUnlockCnt(Integer unlockCnt) {
/*  73 */     this.unlockCnt = unlockCnt;
/*     */   }
/*     */   
/*     */   public String getUserName() {
/*  77 */     return this.userName;
/*     */   }
/*     */   
/*     */   public void setUserName(String userName) {
/*  81 */     this.userName = userName;
/*     */   }
/*     */   
/*     */   public String getMultiLoginYN() {
/*  85 */     return this.multiLoginYN;
/*     */   }
/*     */   
/*     */   public void setMultiLoginYN(String multiLoginYN) {
/*  89 */     this.multiLoginYN = multiLoginYN;
/*     */   }
/*     */   
/*     */   public String getStatus() {
/*  93 */     return this.status;
/*     */   }
/*     */   
/*     */   public void setStatus(String status) {
/*  97 */     this.status = status;
/*     */   }
/*     */   
/*     */   public Integer getProductTypeCode() {
/* 101 */     return this.productTypeCode;
/*     */   }
/*     */   
/*     */   public void setProductTypeCode(Integer productTypeCode) {
/* 105 */     this.productTypeCode = productTypeCode;
/*     */   }
/*     */   
/*     */   public String getSessionCode() {
/* 109 */     return this.sessionCode;
/*     */   }
/*     */   
/*     */   public void setSessionCode(String sessionCode) {
/* 113 */     this.sessionCode = sessionCode;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 118 */     return "HwOtpResp [returnCode=" + this.returnCode + ", challenge=" + this.challenge + ", unlockCode=" + this.unlockCode + ", unlockCnt=" + this.unlockCnt + "]";
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\vo\bean\HwOtpResp.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */