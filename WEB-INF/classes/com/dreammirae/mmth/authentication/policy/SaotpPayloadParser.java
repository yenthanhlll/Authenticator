/*    */ package WEB-INF.classes.com.dreammirae.mmth.authentication.policy;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ public enum SaotpPayloadParser
/*    */ {
/*  8 */   BASIC;
/*    */   
/*    */   public static List<String> getNames() {
/* 11 */     List<String> list = new ArrayList<>();
/* 12 */     for (com.dreammirae.mmth.authentication.policy.SaotpPayloadParser type : values()) {
/* 13 */       list.add(type.name());
/*    */     }
/* 15 */     return list;
/*    */   }
/*    */ 
/*    */   
/*    */   public static com.dreammirae.mmth.authentication.policy.SaotpPayloadParser valueByName(String name) {
/*    */     try {
/* 21 */       return valueOf(name);
/* 22 */     } catch (Exception e) {
/* 23 */       return null;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\authentication\policy\SaotpPayloadParser.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */