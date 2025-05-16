/*    */ package WEB-INF.classes.com.dreammirae.mmth.authentication.transport;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BizReqData
/*    */ {
/*    */   private String userName;
/*    */   private String tid;
/*    */   private String otp;
/*    */   private String serverPin;
/*    */   private String newServerPin;
/*    */   private String deviceId;
/*    */   
/*    */   public String getUserName() {
/* 17 */     return this.userName;
/*    */   }
/*    */   
/*    */   public void setUserName(String userName) {
/* 21 */     this.userName = userName;
/*    */   }
/*    */   
/*    */   public String getTid() {
/* 25 */     return this.tid;
/*    */   }
/*    */   
/*    */   public void setTid(String tid) {
/* 29 */     this.tid = tid;
/*    */   }
/*    */   
/*    */   public String getOtp() {
/* 33 */     return this.otp;
/*    */   }
/*    */   
/*    */   public void setOtp(String otp) {
/* 37 */     this.otp = otp;
/*    */   }
/*    */   
/*    */   public String getServerPin() {
/* 41 */     return this.serverPin;
/*    */   }
/*    */   
/*    */   public void setServerPin(String serverPin) {
/* 45 */     this.serverPin = serverPin;
/*    */   }
/*    */   
/*    */   public String getNewServerPin() {
/* 49 */     return this.newServerPin;
/*    */   }
/*    */   
/*    */   public void setNewServerPin(String newServerPin) {
/* 53 */     this.newServerPin = newServerPin;
/*    */   }
/*    */   
/*    */   public String getDeviceId() {
/* 57 */     return this.deviceId;
/*    */   }
/*    */   
/*    */   public void setDeviceId(String deviceId) {
/* 61 */     this.deviceId = deviceId;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 66 */     StringBuilder builder = new StringBuilder();
/* 67 */     builder.append("BizReqData [userName=").append(this.userName).append(", tid=").append(this.tid).append(", otp=")
/* 68 */       .append(this.otp).append(", serverPin=").append(this.serverPin).append(", newServerPin=").append(this.newServerPin)
/* 69 */       .append(", deviceId=").append(this.deviceId).append("]");
/* 70 */     return builder.toString();
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\authentication\transport\BizReqData.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */