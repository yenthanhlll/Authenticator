/*    */ package WEB-INF.classes.com.dreammirae.mmth.authentication.policy;
/*    */ 
/*    */ import com.dreammirae.mmth.authentication.bean.AuthenticationResponseLocator;
/*    */ import com.dreammirae.mmth.parser.IUserServiceMessageParser;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum BiotpPayloadParser
/*    */ {
/* 13 */   BASIC,
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
/* 24 */   KIWOOM;
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
/*    */   public static List<String> getNames() {
/* 42 */     List<String> list = new ArrayList<>();
/* 43 */     for (com.dreammirae.mmth.authentication.policy.BiotpPayloadParser type : values()) {
/* 44 */       list.add(type.name());
/*    */     }
/* 46 */     return list;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static com.dreammirae.mmth.authentication.policy.BiotpPayloadParser valueByName(String name) {
/*    */     try {
/* 53 */       return valueOf(name);
/* 54 */     } catch (Exception e) {
/* 55 */       return null;
/*    */     } 
/*    */   }
/*    */   
/*    */   public abstract IUserServiceMessageParser getParser();
/*    */   
/*    */   public abstract AuthenticationResponseLocator createResponseLocator();
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\authentication\policy\BiotpPayloadParser.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */