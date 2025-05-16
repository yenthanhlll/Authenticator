/*    */ package WEB-INF.classes.com.dreammirae.mmth.vo.bean;
/*    */ 
/*    */ import com.dreammirae.mmth.vo.bean.ICustomLogData;
/*    */ 
/*    */ public class GlobalWibeeLogData
/*    */   implements ICustomLogData {
/*    */   private String regEmpNo;
/*    */   
/*    */   public GlobalWibeeLogData() {}
/*    */   
/*    */   public GlobalWibeeLogData(String regEmpNo) {
/* 12 */     this.regEmpNo = regEmpNo;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getRegEmpNo() {
/* 20 */     return this.regEmpNo;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setRegEmpNo(String regEmpNo) {
/* 28 */     this.regEmpNo = regEmpNo;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 38 */     StringBuilder builder = new StringBuilder();
/* 39 */     builder.append("GlobalWibeeLogData [regEmpNo=").append(this.regEmpNo).append("]");
/* 40 */     return builder.toString();
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\vo\bean\GlobalWibeeLogData.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */