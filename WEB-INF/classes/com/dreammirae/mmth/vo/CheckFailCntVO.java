/*    */ package WEB-INF.classes.com.dreammirae.mmth.vo;
/*    */ 
/*    */ import com.dreammirae.mmth.authentication.ReturnCodes;
/*    */ 
/*    */ public class CheckFailCntVO {
/*    */   private ReturnCodes returnCode;
/*    */   private String userName;
/*    */   private int pinFailCnt;
/*    */   private int patternFailCnt;
/*    */   
/*    */   public ReturnCodes getReturnCode() {
/* 12 */     return this.returnCode;
/*    */   }
/*    */   
/*    */   public void setReturnCode(ReturnCodes returnCode) {
/* 16 */     this.returnCode = returnCode;
/*    */   }
/*    */   
/*    */   public String getUsername() {
/* 20 */     return this.userName;
/*    */   }
/*    */   
/*    */   public void setUsername(String userName) {
/* 24 */     this.userName = userName;
/*    */   }
/*    */   
/*    */   public int getPinFailCnt() {
/* 28 */     return this.pinFailCnt;
/*    */   }
/*    */   
/*    */   public void setPinFailCnt(int pinFailCnt) {
/* 32 */     this.pinFailCnt = pinFailCnt;
/*    */   }
/*    */   
/*    */   public int getPatternFailCnt() {
/* 36 */     return this.patternFailCnt;
/*    */   }
/*    */   
/*    */   public void setPatternFailCnt(int patternFailCnt) {
/* 40 */     this.patternFailCnt = patternFailCnt;
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\vo\CheckFailCntVO.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */