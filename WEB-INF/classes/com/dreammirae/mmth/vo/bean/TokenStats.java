/*    */ package WEB-INF.classes.com.dreammirae.mmth.vo.bean;
/*    */ 
/*    */ import com.dreammirae.mmth.vo.bean.IViewStatsLocator;
/*    */ 
/*    */ public class TokenStats implements IViewStatsLocator {
/*    */   private int total;
/*    */   private int available;
/*    */   private int occupied;
/*    */   private int discard;
/*    */   private int suspend;
/*    */   
/*    */   public int getTotal() {
/* 13 */     return this.total;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setTotal(int total) {
/* 18 */     this.total = total;
/*    */   }
/*    */   
/*    */   public int getAvailable() {
/* 22 */     return this.available;
/*    */   }
/*    */   
/*    */   public void setAvailable(int available) {
/* 26 */     this.available = available;
/*    */   }
/*    */   
/*    */   public int getOccupied() {
/* 30 */     return this.occupied;
/*    */   }
/*    */   
/*    */   public void setOccupied(int occupied) {
/* 34 */     this.occupied = occupied;
/*    */   }
/*    */   
/*    */   public int getSuspend() {
/* 38 */     return this.suspend;
/*    */   }
/*    */   
/*    */   public void setSuspend(int suspend) {
/* 42 */     this.suspend = suspend;
/*    */   }
/*    */   
/*    */   public int getDiscard() {
/* 46 */     return this.discard;
/*    */   }
/*    */   
/*    */   public void setDiscard(int discard) {
/* 50 */     this.discard = discard;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 55 */     StringBuilder builder = new StringBuilder();
/* 56 */     builder.append("TokenStats [total=").append(this.total).append(", available=").append(this.available).append(", occupied=").append(this.occupied).append(", discard=").append(this.discard).append("]");
/* 57 */     return builder.toString();
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\vo\bean\TokenStats.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */