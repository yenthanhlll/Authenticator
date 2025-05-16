/*    */ package WEB-INF.classes.com.dreammirae.mmth.authentication.bean;
/*    */ 
/*    */ import com.dreammirae.mmth.fido.transport.jwt.JWTBody;
/*    */ import com.dreammirae.mmth.misc.Base64Utils;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FidoJWTBody
/*    */   implements JWTBody
/*    */ {
/*    */   private static final String JSON_FORMAT = "{\"tid\":\"%s\"}";
/*    */   private String tid;
/*    */   
/*    */   public FidoJWTBody() {}
/*    */   
/*    */   public FidoJWTBody(String tid) {
/* 18 */     this.tid = tid;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getTid() {
/* 25 */     return this.tid;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setTid(String tid) {
/* 32 */     this.tid = tid;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getBody() {
/* 37 */     return Base64Utils.encodeUrl(String.format("{\"tid\":\"%s\"}", new Object[] { this.tid }).toString());
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\authentication\bean\FidoJWTBody.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */