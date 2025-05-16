/*    */ package WEB-INF.classes.com.dreammirae.mmth.vo.bean;
/*    */ 
/*    */ import com.dreammirae.mmth.vo.bean.IViewStatsLocator;
/*    */ 
/*    */ public class FidoFacetStats implements IViewStatsLocator {
/*    */   private int total;
/*    */   private int trustedFacet;
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
/*    */   public int getTrustedFacet() {
/* 19 */     return this.trustedFacet;
/*    */   }
/*    */   
/*    */   public void setTrustedFacet(int trustedFacet) {
/* 23 */     this.trustedFacet = trustedFacet;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 28 */     StringBuilder builder = new StringBuilder();
/* 29 */     builder.append("FidoFacetStats [total=").append(this.total).append(", trustedFacet=").append(this.trustedFacet).append("]");
/* 30 */     return builder.toString();
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\vo\bean\FidoFacetStats.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */