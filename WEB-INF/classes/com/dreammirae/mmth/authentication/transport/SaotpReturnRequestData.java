/*    */ package WEB-INF.classes.com.dreammirae.mmth.authentication.transport;
/*    */ 
/*    */ import com.dreammirae.mmth.authentication.ReturnCodes;
/*    */ import com.dreammirae.mmth.authentication.bean.OtpAdditionalData;
/*    */ import com.dreammirae.mmth.util.io.HexUtils;
/*    */ import com.google.gson.JsonDeserializationContext;
/*    */ import com.google.gson.JsonObject;
/*    */ import com.google.gson.JsonSerializationContext;
/*    */ import java.util.Arrays;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SaotpReturnRequestData
/*    */   extends OtpAdditionalData
/*    */ {
/*    */   private ReturnCodes returnCode;
/*    */   
/*    */   public ReturnCodes getReturnCode() {
/* 24 */     return this.returnCode;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setReturnCode(ReturnCodes returnCode) {
/* 31 */     this.returnCode = returnCode;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void jsonSerializationImp(JsonObject parent, JsonSerializationContext ctx) {
/* 37 */     if (this.returnCode != null) {
/* 38 */       parent.add("returnCode", ctx.serialize(this.returnCode));
/*    */     }
/*    */     
/* 41 */     super.jsonSerializationImp(parent, ctx);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void jsonDeserializationImp(JsonObject additionalData, JsonDeserializationContext ctx) {
/* 47 */     if (additionalData.has("returnCode")) {
/* 48 */       this.returnCode = (ReturnCodes)ctx.deserialize(additionalData.get("returnCode"), ReturnCodes.class);
/*    */     }
/*    */     
/* 51 */     super.jsonDeserializationImp(additionalData, ctx);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 59 */     StringBuilder builder = new StringBuilder();
/* 60 */     builder.append("SaotpOTPResponseData [returnCode=").append(this.returnCode).append(", getEncKey()=")
/* 61 */       .append(HexUtils.toHexString(getEncKey())).append(", getEncData()=").append(HexUtils.toHexString(getEncData()))
/* 62 */       .append(", getEncTid()=").append(HexUtils.toHexString(getEncTid())).append(", getEncRndSeedKey()=")
/* 63 */       .append(HexUtils.toHexString(getEncRndSeedKey())).append(", getEncToken()=")
/* 64 */       .append(HexUtils.toHexString(getEncToken())).append(", getReferences()=")
/* 65 */       .append(Arrays.toString((Object[])getReferences())).append(", getExpiredDateTime()=").append(getExpiredDateTime())
/* 66 */       .append("]");
/* 67 */     return builder.toString();
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\authentication\transport\SaotpReturnRequestData.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */