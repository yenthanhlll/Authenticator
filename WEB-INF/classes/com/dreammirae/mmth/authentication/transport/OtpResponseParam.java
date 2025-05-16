/*    */ package WEB-INF.classes.com.dreammirae.mmth.authentication.transport;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class OtpResponseParam
/*    */ {
/*    */   private String otp;
/*    */   private String userName;
/*    */   private String tid;
/*    */   private String deviceId;
/*    */   
/*    */   public String getOtp() {
/* 31 */     return this.otp;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setOtp(String otp) {
/* 38 */     this.otp = otp;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getUserName() {
/* 45 */     return this.userName;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setUserName(String userName) {
/* 52 */     this.userName = userName;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getTid() {
/* 59 */     return this.tid;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setTid(String tid) {
/* 66 */     this.tid = tid;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getDeviceId() {
/* 73 */     return this.deviceId;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setDeviceId(String deviceId) {
/* 80 */     this.deviceId = deviceId;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 88 */     StringBuilder builder = new StringBuilder();
/* 89 */     builder.append("OtpResponseParam [otp=").append(this.otp).append(", userName=").append(this.userName).append(", tid=")
/* 90 */       .append(this.tid).append(", deviceId=").append(this.deviceId).append("]");
/* 91 */     return builder.toString();
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\authentication\transport\OtpResponseParam.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */