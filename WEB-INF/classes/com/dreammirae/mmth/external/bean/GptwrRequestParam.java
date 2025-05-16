/*    */ package WEB-INF.classes.com.dreammirae.mmth.external.bean;
/*    */ 
/*    */ import com.dreammirae.mmth.external.bean.WebApiRequestParam;
/*    */ import com.dreammirae.mmth.misc.Base64Utils;
/*    */ import com.dreammirae.mmth.util.StringUtils;
/*    */ import org.springframework.web.bind.annotation.RequestParam;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GptwrRequestParam
/*    */   extends WebApiRequestParam
/*    */ {
/*    */   private String displayName;
/*    */   private String extUserId;
/*    */   private String customerCode;
/*    */   
/*    */   public String getDisplayName() {
/* 22 */     return this.displayName;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setDisplayName(@RequestParam("name") String displayName) {
/* 30 */     this.displayName = displayName;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getExtUserId() {
/* 37 */     return this.extUserId;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setExtUserId(String extUserId) {
/* 45 */     this.extUserId = extUserId;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getCustomerCode() {
/* 52 */     return this.customerCode;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setCustomerCode(String customerCode) {
/* 62 */     if (!StringUtils.isEmpty(customerCode)) {
/* 63 */       this.customerCode = Base64Utils.decode(customerCode);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void setUserno(String userno) {
/* 69 */     if (!StringUtils.isEmpty(userno)) {
/* 70 */       setUserName(Base64Utils.decode(userno));
/*    */     }
/*    */   }
/*    */   
/*    */   public void setName(String name) {
/* 75 */     if (!StringUtils.isEmpty(name)) {
/* 76 */       setDisplayName(Base64Utils.decode(name));
/*    */     }
/*    */   }
/*    */   
/*    */   public void setUserId(String userId) {
/* 81 */     if (!StringUtils.isEmpty(userId)) {
/* 82 */       setExtUserId(Base64Utils.decode(userId));
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 93 */     StringBuilder builder = new StringBuilder();
/* 94 */     builder.append("GptwrRequestParam [name=").append(this.displayName).append(", userId=").append(this.extUserId).append(", customerCode=").append(this.customerCode).append(", getUserName()=")
/* 95 */       .append(getUserName()).append(", getOtp()=").append(getOtp()).append(", getTid()=").append(getTid()).append(", getTranInfo()=").append(getTranInfo()).append("]");
/* 96 */     return builder.toString();
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\external\bean\GptwrRequestParam.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */