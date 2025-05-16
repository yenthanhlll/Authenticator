/*     */ package WEB-INF.classes.com.dreammirae.mmth.web.controller.view.rest;
/*     */ 
/*     */ import com.dreammirae.mmth.db.dao.FidoFacetDao;
/*     */ import com.dreammirae.mmth.fido.transport.facets.Facets;
/*     */ import com.dreammirae.mmth.vo.FidoFacetVO;
/*     */ import com.dreammirae.mmth.vo.bean.IRestValidator;
/*     */ import com.dreammirae.mmth.vo.bean.RestResponse;
/*     */ import com.dreammirae.mmth.web.controller.view.rest.ViewController;
/*     */ import com.dreammirae.mmth.web.service.FidoFacetService;
/*     */ import com.dreammirae.mmth.web.service.ViewService;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ import org.springframework.web.bind.annotation.RequestMethod;
/*     */ import org.springframework.web.bind.annotation.RestController;
/*     */ 
/*     */ 
/*     */ @RestController
/*     */ @RequestMapping(value = {"/web/manager/facets/rest"}, method = {RequestMethod.POST})
/*     */ public class FidoFacetController
/*     */   extends ViewController<FidoFacetVO, Integer>
/*     */ {
/*     */   @Autowired
/*     */   private FidoFacetService service;
/*     */   
/*     */   @RequestMapping({"/policy"})
/*     */   public RestResponse policy() {
/*  30 */     RestResponse resp = new RestResponse();
/*     */     
/*  32 */     Facets facet = FidoFacetDao.getTrustedFacets();
/*     */     
/*  34 */     if (facet == null || facet.getTrustedFacets() == null) {
/*  35 */       return resp;
/*     */     }
/*     */     
/*  38 */     if ((facet.getTrustedFacets()).length > 0 && (facet.getTrustedFacets()[0].getIds()).length > 0) {
/*  39 */       resp.addData("policy", facet);
/*     */     }
/*     */     
/*  42 */     return resp;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void initImp(RestResponse resp, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
/*  61 */     getPagination(resp, 1, null, null, null);
/*  62 */     getViewStats(resp);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void detailsImp(RestResponse resp, Integer id, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
/*  75 */     if (-1 == id.intValue()) {
/*  76 */       FidoFacetVO vo = FidoFacetVO.createAdministrationVO(null, null);
/*  77 */       resp.addData("details", vo);
/*     */       
/*     */       return;
/*     */     } 
/*  81 */     getDetails(resp, id);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void saveImp(RestResponse resp, FidoFacetVO vo, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
/*  97 */     doSave(resp, (IRestValidator)vo, session);
/*     */     
/*  99 */     if (resp.getHasMessages()) {
/*     */       return;
/*     */     }
/*     */     
/* 103 */     getPagination(resp, 1, null, null, null);
/* 104 */     getViewStats(resp);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void deleteImp(RestResponse resp, Integer id, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
/* 119 */     doDelete(resp, id, session);
/*     */     
/* 121 */     if (resp.getHasMessages()) {
/*     */       return;
/*     */     }
/*     */     
/* 125 */     getPagination(resp, 1, null, null, null);
/* 126 */     getViewStats(resp);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected FidoFacetService getViewService() {
/* 137 */     if (this.service == null) {
/* 138 */       this.service = new FidoFacetService();
/*     */     }
/*     */     
/* 141 */     return this.service;
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\web\controller\view\rest\FidoFacetController.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */