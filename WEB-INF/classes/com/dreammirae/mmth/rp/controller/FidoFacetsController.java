/*    */ package WEB-INF.classes.com.dreammirae.mmth.rp.controller;
/*    */ 
/*    */ import com.dreammirae.mmth.db.dao.FidoFacetDao;
/*    */ import com.dreammirae.mmth.fido.transport.facets.Facets;
/*    */ import org.springframework.web.bind.annotation.RequestMapping;
/*    */ import org.springframework.web.bind.annotation.RestController;
/*    */ 
/*    */ 
/*    */ @RestController
/*    */ @RequestMapping(value = {"/rpserver/httpapi/public"}, produces = {"application/fido.trusted-apps+json"})
/*    */ public class FidoFacetsController
/*    */ {
/*    */   @RequestMapping({"/facets"})
/*    */   public Facets facets() {
/* 15 */     return FidoFacetDao.getTrustedFacets();
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\rp\controller\FidoFacetsController.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */