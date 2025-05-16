/*    */ package WEB-INF.classes.com.dreammirae.mmth.runtime.code;
/*    */ 
/*    */ public enum SyncCacheTypes
/*    */ {
/*  5 */   TRUSTED_FACETS("1"), FIDO_METADATA("2"), SYSTEM_ENV("3"), APP_AGENTS("4");
/*    */   
/*    */   private String code;
/*    */   
/*    */   SyncCacheTypes(String code) {
/* 10 */     this.code = code;
/*    */   }
/*    */   
/*    */   public String getCode() {
/* 14 */     return this.code;
/*    */   }
/*    */ 
/*    */   
/*    */   public static com.dreammirae.mmth.runtime.code.SyncCacheTypes getEventDataTypes(String code) {
/* 19 */     for (com.dreammirae.mmth.runtime.code.SyncCacheTypes type : values()) {
/* 20 */       if (type.code.equals(code)) {
/* 21 */         return type;
/*    */       }
/*    */     } 
/*    */     
/* 25 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\runtime\code\SyncCacheTypes.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */