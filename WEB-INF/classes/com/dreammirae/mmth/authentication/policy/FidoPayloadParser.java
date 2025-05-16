/*    */ package WEB-INF.classes.com.dreammirae.mmth.authentication.policy;
/*    */ 
/*    */ import com.dreammirae.mmth.parser.IUserServiceMessageParser;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum FidoPayloadParser
/*    */ {
/* 12 */   BASIC,
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
/* 25 */   ANYPASS;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static final String FIDO_UAF_CONTENT_TYPE = "application/fido+uaf; charset=utf-8";
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
/*    */   public static List<String> getNames() {
/* 47 */     List<String> list = new ArrayList<>();
/* 48 */     for (com.dreammirae.mmth.authentication.policy.FidoPayloadParser type : values()) {
/* 49 */       list.add(type.name());
/*    */     }
/* 51 */     return list;
/*    */   }
/*    */ 
/*    */   
/*    */   public static com.dreammirae.mmth.authentication.policy.FidoPayloadParser valueByName(String name) {
/*    */     try {
/* 57 */       return valueOf(name);
/* 58 */     } catch (Exception e) {
/* 59 */       return null;
/*    */     } 
/*    */   }
/*    */   
/*    */   public abstract IUserServiceMessageParser getParser();
/*    */   
/*    */   public abstract String getContentType();
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\authentication\policy\FidoPayloadParser.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */