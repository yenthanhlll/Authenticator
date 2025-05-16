/*    */ package WEB-INF.classes.com.dreammirae.mmth.vo.bean;
/*    */ 
/*    */ import java.util.List;
/*    */ 
/*    */ public class SqlResult {
/*    */   private List<String> headers;
/*    */   private List<List<Object>> data;
/*    */   private int resultSize;
/*  9 */   private int updateResult = -1;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public List<String> getHeaders() {
/* 16 */     return this.headers;
/*    */   }
/*    */   
/*    */   public void setHeaders(List<String> headers) {
/* 20 */     this.headers = headers;
/*    */   }
/*    */   
/*    */   public List<List<Object>> getData() {
/* 24 */     return this.data;
/*    */   }
/*    */   
/*    */   public void setData(List<List<Object>> data) {
/* 28 */     this.data = data;
/*    */   }
/*    */   
/*    */   public int getResultSize() {
/* 32 */     return this.resultSize;
/*    */   }
/*    */   
/*    */   public void setResultSize(int resultSize) {
/* 36 */     this.resultSize = resultSize;
/*    */   }
/*    */   
/*    */   public int getUpdateResult() {
/* 40 */     return this.updateResult;
/*    */   }
/*    */   
/*    */   public void setUpdateResult(int updateResult) {
/* 44 */     this.updateResult = updateResult;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 49 */     StringBuilder builder = new StringBuilder();
/* 50 */     builder.append("SqlResult [headers=").append(this.headers).append(", data=").append(this.data).append(", resultSize=").append(this.resultSize).append(", updateResult=").append(this.updateResult).append("]");
/* 51 */     return builder.toString();
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\vo\bean\SqlResult.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */