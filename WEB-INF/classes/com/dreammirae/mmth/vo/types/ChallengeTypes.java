/*    */ package WEB-INF.classes.com.dreammirae.mmth.vo.types;
/*    */ 
/*    */ public enum ChallengeTypes
/*    */ {
/*  5 */   REG("1"),
/*  6 */   AUTH("2"),
/*  7 */   AUTH_WITH_TRANSACTION("3"),
/*  8 */   AUTH_WITH_TRAN_INFO("4");
/*    */   
/*    */   private final String code;
/*    */   
/*    */   ChallengeTypes(String code) {
/* 13 */     this.code = code;
/*    */   }
/*    */   
/*    */   public String getCode() {
/* 17 */     return this.code;
/*    */   }
/*    */ 
/*    */   
/*    */   public static com.dreammirae.mmth.vo.types.ChallengeTypes getChallengeTypes(String code) {
/* 22 */     for (com.dreammirae.mmth.vo.types.ChallengeTypes type : values()) {
/* 23 */       if (type.code.equals(code)) {
/* 24 */         return type;
/*    */       }
/*    */     } 
/* 27 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\vo\types\ChallengeTypes.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */