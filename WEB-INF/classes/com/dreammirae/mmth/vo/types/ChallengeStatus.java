/*    */ package WEB-INF.classes.com.dreammirae.mmth.vo.types;
/*    */ 
/*    */ public enum ChallengeStatus
/*    */ {
/*  5 */   FIDO_REQ("1"),
/*  6 */   OTP_REQ("2"),
/*  7 */   DONE("9");
/*    */   
/*    */   private final String code;
/*    */   
/*    */   ChallengeStatus(String code) {
/* 12 */     this.code = code;
/*    */   }
/*    */   
/*    */   public String getCode() {
/* 16 */     return this.code;
/*    */   }
/*    */ 
/*    */   
/*    */   public static com.dreammirae.mmth.vo.types.ChallengeStatus getChallengeStatus(String code) {
/* 21 */     for (com.dreammirae.mmth.vo.types.ChallengeStatus type : values()) {
/* 22 */       if (type.code.equals(code)) {
/* 23 */         return type;
/*    */       }
/*    */     } 
/*    */     
/* 27 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\vo\types\ChallengeStatus.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */