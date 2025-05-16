/*    */ package WEB-INF.classes.com.dreammirae.mmth.vo.bean;
/*    */ 
/*    */ import com.dreammirae.mmth.vo.bean.IViewStatsLocator;
/*    */ 
/*    */ public class FidoMetadataStats implements IViewStatsLocator {
/*    */   private int total;
/*    */   private int allowed;
/*    */   
/*    */   public int getTotal() {
/* 10 */     return this.total;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setTotal(int total) {
/* 15 */     this.total = total;
/*    */   }
/*    */   
/*    */   public int getAllowed() {
/* 19 */     return this.allowed;
/*    */   }
/*    */   
/*    */   public void setAllowed(int allowed) {
/* 23 */     this.allowed = allowed;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 28 */     StringBuilder builder = new StringBuilder();
/* 29 */     builder.append("FidoMetadataStats [total=").append(this.total).append(", allowed=").append(this.allowed).append("]");
/* 30 */     return builder.toString();
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\vo\bean\FidoMetadataStats.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */