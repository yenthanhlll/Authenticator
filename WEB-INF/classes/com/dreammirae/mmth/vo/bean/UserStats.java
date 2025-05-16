/*    */ package WEB-INF.classes.com.dreammirae.mmth.vo.bean;
/*    */ 
/*    */ import com.dreammirae.mmth.vo.bean.IViewStatsLocator;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class UserStats
/*    */   implements IViewStatsLocator
/*    */ {
/*    */   private int total;
/*    */   private int notAvailable;
/*    */   private int available;
/*    */   private int lostStolen;
/*    */   private int discard;
/*    */   private int suspend;
/*    */   
/*    */   public int getTotal() {
/* 18 */     return this.total;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setTotal(int total) {
/* 23 */     this.total = total;
/*    */   }
/*    */   
/*    */   public int getNotAvailable() {
/* 27 */     return this.notAvailable;
/*    */   }
/*    */   
/*    */   public void setNotAvailable(int notAvailable) {
/* 31 */     this.notAvailable = notAvailable;
/*    */   }
/*    */   
/*    */   public int getAvailable() {
/* 35 */     return this.available;
/*    */   }
/*    */   
/*    */   public void setAvailable(int available) {
/* 39 */     this.available = available;
/*    */   }
/*    */   
/*    */   public int getLostStolen() {
/* 43 */     return this.lostStolen;
/*    */   }
/*    */   
/*    */   public void setLostStolen(int lostStolen) {
/* 47 */     this.lostStolen = lostStolen;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getSuspend() {
/* 55 */     return this.suspend;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setSuspend(int suspend) {
/* 62 */     this.suspend = suspend;
/*    */   }
/*    */   
/*    */   public int getDiscard() {
/* 66 */     return this.discard;
/*    */   }
/*    */   
/*    */   public void setDiscard(int discard) {
/* 70 */     this.discard = discard;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 75 */     StringBuilder builder = new StringBuilder();
/* 76 */     builder.append("UserStats [total=").append(this.total).append(", notAvailable=").append(this.notAvailable).append(", available=").append(this.available).append(", lostStolen=").append(this.lostStolen)
/* 77 */       .append(", suspend=").append(this.suspend).append(", discard=").append(this.discard).append("]");
/* 78 */     return builder.toString();
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\vo\bean\UserStats.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */