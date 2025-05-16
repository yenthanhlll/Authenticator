/*    */ package WEB-INF.classes.com.dreammirae.mmth.vo.bean;
/*    */ 
/*    */ import com.dreammirae.mmth.vo.bean.IViewStatsLocator;
/*    */ 
/*    */ public class AdministratorStats implements IViewStatsLocator {
/*    */   private int total;
/*    */   
/*    */   public int getTotal() {
/*  9 */     return this.total;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setTotal(int total) {
/* 14 */     this.total = total;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 19 */     StringBuilder builder = new StringBuilder();
/* 20 */     builder.append("AdministratorStats [total=").append(this.total).append("]");
/* 21 */     return builder.toString();
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\vo\bean\AdministratorStats.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */