/*    */ package WEB-INF.classes.com.dreammirae.mmth.external.bean;
/*    */ 
/*    */ public enum ExtRequestStatus
/*    */ {
/*  5 */   REQ("1"), FAIL("2"), DONE("3");
/*    */   
/*    */   private final String code;
/*    */   
/*    */   ExtRequestStatus(String code) {
/* 10 */     this.code = code;
/*    */   }
/*    */   
/*    */   public String getCode() {
/* 14 */     return this.code;
/*    */   }
/*    */   
/*    */   public static com.dreammirae.mmth.external.bean.ExtRequestStatus getExtRequestStatus(String code) {
/* 18 */     for (com.dreammirae.mmth.external.bean.ExtRequestStatus st : values()) {
/* 19 */       if (st.code.equals(code)) {
/* 20 */         return st;
/*    */       }
/*    */     } 
/*    */     
/* 24 */     return null;
/*    */   }
/*    */   
/*    */   public static com.dreammirae.mmth.external.bean.ExtRequestStatus getExtRequestStatusByName(String name) {
/* 28 */     for (com.dreammirae.mmth.external.bean.ExtRequestStatus st : values()) {
/* 29 */       if (st.name().equalsIgnoreCase(name)) {
/* 30 */         return st;
/*    */       }
/*    */     } 
/*    */     
/* 34 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\external\bean\ExtRequestStatus.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */