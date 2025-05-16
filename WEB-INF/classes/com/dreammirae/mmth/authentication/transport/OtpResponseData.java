/*    */ package WEB-INF.classes.com.dreammirae.mmth.authentication.transport;
/*    */ 
/*    */ import com.dreammirae.mmth.authentication.ReturnCodes;
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
/*    */ 
/*    */ 
/*    */ public class OtpResponseData
/*    */ {
/*    */   private ReturnCodes returnCode;
/*    */   
/*    */   public ReturnCodes getReturnCode() {
/* 32 */     return this.returnCode;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setReturnCode(ReturnCodes returnCode) {
/* 40 */     this.returnCode = returnCode;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 50 */     StringBuilder builder = new StringBuilder();
/* 51 */     builder.append("OtpResponseData [returnCode=").append(this.returnCode).append("]");
/* 52 */     return builder.toString();
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\authentication\transport\OtpResponseData.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */