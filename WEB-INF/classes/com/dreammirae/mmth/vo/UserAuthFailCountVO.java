/*    */ package WEB-INF.classes.com.dreammirae.mmth.vo;
/*    */ 
/*    */ 
/*    */ public class UserAuthFailCountVO
/*    */ {
/*  6 */   private int id = -1;
/*    */ 
/*    */   
/*    */   private int authTypeId;
/*    */ 
/*    */   
/*    */   private int failCnt;
/*    */ 
/*    */   
/*    */   private long tsLastAuthFail;
/*    */ 
/*    */   
/*    */   public int getAuthTypeId() {
/* 19 */     return this.authTypeId;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setAuthTypeId(int authTypeId) {
/* 27 */     this.authTypeId = authTypeId;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getFailCnt() {
/* 34 */     return this.failCnt;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setFailCnt(int failCnt) {
/* 42 */     this.failCnt = failCnt;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public long getTsLastAuthFail() {
/* 49 */     return this.tsLastAuthFail;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setTsLastAuthFail(long tsLastAuthFail) {
/* 57 */     this.tsLastAuthFail = tsLastAuthFail;
/*    */   }
/*    */   
/*    */   public int getId() {
/* 61 */     return this.id;
/*    */   }
/*    */   
/*    */   public void setId(int id) {
/* 65 */     this.id = id;
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\vo\UserAuthFailCountVO.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */