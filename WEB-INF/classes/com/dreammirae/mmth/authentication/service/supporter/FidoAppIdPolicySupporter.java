/*    */ package WEB-INF.classes.com.dreammirae.mmth.authentication.service.supporter;
/*    */ 
/*    */ import com.dreammirae.mmth.db.dao.FidoFacetDao;
/*    */ import com.dreammirae.mmth.db.dao.SystemSettingsDao;
/*    */ import com.dreammirae.mmth.fido.handler.supporter.AppIDPolicy;
/*    */ import com.dreammirae.mmth.fido.handler.supporter.AppIDSupporter;
/*    */ import com.dreammirae.mmth.fido.transport.facets.Facets;
/*    */ 
/*    */ public class FidoAppIdPolicySupporter
/*    */   extends AppIDSupporter
/*    */ {
/*    */   protected String returnTrustedFacetsURI() {
/* 13 */     return SystemSettingsDao.getValue("integrate.trustedFacetUrl");
/*    */   }
/*    */ 
/*    */   
/*    */   protected String returnFacetId() {
/* 18 */     return SystemSettingsDao.getValue("integrate.trustedFacetUrl");
/*    */   }
/*    */ 
/*    */   
/*    */   protected AppIDPolicy returnAppIDPolicy() {
/* 23 */     return SystemSettingsDao.getAppIDPolicy();
/*    */   }
/*    */ 
/*    */   
/*    */   public String[] trustedFacets() {
/* 28 */     Facets facets = FidoFacetDao.getTrustedFacets();
/*    */     
/* 30 */     if (facets == null || facets.getTrustedFacets() == null) {
/* 31 */       return null;
/*    */     }
/*    */     
/* 34 */     return facets.getTrustedFacets()[0].getIds();
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\authentication\service\supporter\FidoAppIdPolicySupporter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */