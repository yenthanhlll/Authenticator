/*    */ package WEB-INF.classes.com.dreammirae.mmth.vo.bean;
/*    */ 
/*    */ import com.dreammirae.mmth.vo.bean.IViewStatsLocator;
/*    */ 
/*    */ public class AppAgentStats implements IViewStatsLocator {
/*    */   private int total;
/*    */   private int android;
/*    */   private int ios;
/*    */   
/*    */   public int getTotal() {
/* 11 */     return this.total;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setTotal(int total) {
/* 16 */     this.total = total;
/*    */   }
/*    */   
/*    */   public int getAndroid() {
/* 20 */     return this.android;
/*    */   }
/*    */   
/*    */   public void setAndroid(int android) {
/* 24 */     this.android = android;
/*    */   }
/*    */   
/*    */   public int getIos() {
/* 28 */     return this.ios;
/*    */   }
/*    */   
/*    */   public void setIos(int ios) {
/* 32 */     this.ios = ios;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 37 */     StringBuilder builder = new StringBuilder();
/* 38 */     builder.append("AppAgentStats [total=").append(this.total).append(", android=").append(this.android).append(", ios=").append(this.ios).append("]");
/* 39 */     return builder.toString();
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\vo\bean\AppAgentStats.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */