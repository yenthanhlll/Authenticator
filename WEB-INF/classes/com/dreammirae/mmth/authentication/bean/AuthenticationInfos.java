/*    */ package WEB-INF.classes.com.dreammirae.mmth.authentication.bean;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AuthenticationInfos
/*    */ {
/*    */   private int failCnt;
/*    */   private int successCnt;
/*    */   private long latestTsFail;
/*    */   private long latestTsSuccess;
/*    */   
/*    */   public int getFailCnt() {
/* 15 */     return this.failCnt;
/*    */   }
/*    */   
/*    */   public void setFailCnt(int failCnt) {
/* 19 */     this.failCnt = failCnt;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSuccessCnt() {
/* 24 */     return this.successCnt;
/*    */   }
/*    */   
/*    */   public void setSuccessCnt(int successCnt) {
/* 28 */     this.successCnt = successCnt;
/*    */   }
/*    */   
/*    */   public long getLatestTsFail() {
/* 32 */     return this.latestTsFail;
/*    */   }
/*    */   
/*    */   public void setLatestTsFail(long latestTsFail) {
/* 36 */     this.latestTsFail = latestTsFail;
/*    */   }
/*    */   
/*    */   public long getLatestTsSuccess() {
/* 40 */     return this.latestTsSuccess;
/*    */   }
/*    */   
/*    */   public void setLatestTsSuccess(long latestTsSuccess) {
/* 44 */     this.latestTsSuccess = latestTsSuccess;
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\authentication\bean\AuthenticationInfos.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */