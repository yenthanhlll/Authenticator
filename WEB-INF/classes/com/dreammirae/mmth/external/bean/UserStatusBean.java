/*     */ package WEB-INF.classes.com.dreammirae.mmth.external.bean;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class UserStatusBean
/*     */ {
/*     */   private String otpSerialNumber;
/*     */   private String deviceId;
/*  14 */   private String status = "0";
/*     */ 
/*     */   
/*     */   private Boolean lostYn;
/*     */   
/*     */   private Long issueDateTime;
/*     */   
/*     */   private String countryCode;
/*     */   
/*     */   private String deviceOS;
/*     */   
/*     */   private String deviceModel;
/*     */ 
/*     */   
/*     */   public String getOtpSerialNumber() {
/*  29 */     return this.otpSerialNumber;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOtpSerialNumber(String otpSerialNumber) {
/*  37 */     this.otpSerialNumber = otpSerialNumber;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDeviceId() {
/*  44 */     return this.deviceId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDeviceId(String deviceId) {
/*  52 */     this.deviceId = deviceId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getStatus() {
/*  59 */     return this.status;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setStatus(String status) {
/*  67 */     this.status = status;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Boolean getLostYn() {
/*  74 */     return this.lostYn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLostYn(Boolean lostYn) {
/*  82 */     this.lostYn = lostYn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Long getIssueDateTime() {
/*  89 */     return this.issueDateTime;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setIssueDateTime(Long issueDateTime) {
/*  97 */     this.issueDateTime = issueDateTime;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCountryCode() {
/* 104 */     return this.countryCode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCountryCode(String countryCode) {
/* 112 */     this.countryCode = countryCode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDeviceOS() {
/* 119 */     return this.deviceOS;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDeviceOS(String deviceOS) {
/* 127 */     this.deviceOS = deviceOS;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDeviceModel() {
/* 134 */     return this.deviceModel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDeviceModel(String deviceModel) {
/* 142 */     this.deviceModel = deviceModel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 152 */     StringBuilder builder = new StringBuilder();
/* 153 */     builder.append("UserStatusBean [otpSerialNumber=").append(this.otpSerialNumber).append(", deviceId=").append(this.deviceId).append(", status=").append(this.status).append(", lostYn=").append(this.lostYn)
/* 154 */       .append(", issueDateTime=").append(this.issueDateTime).append(", countryCode=").append(this.countryCode).append(", deviceOS=").append(this.deviceOS).append(", deviceModel=").append(this.deviceModel)
/* 155 */       .append("]");
/* 156 */     return builder.toString();
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\external\bean\UserStatusBean.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */