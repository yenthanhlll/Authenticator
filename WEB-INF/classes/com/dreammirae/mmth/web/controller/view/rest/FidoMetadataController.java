/*     */ package WEB-INF.classes.com.dreammirae.mmth.web.controller.view.rest;
/*     */ 
/*     */ import com.dreammirae.mmth.db.dao.FidoMetadataDao;
/*     */ import com.dreammirae.mmth.fido.uaf.MatchCriteria;
/*     */ import com.dreammirae.mmth.fido.uaf.Policy;
/*     */ import com.dreammirae.mmth.misc.I18nMessage;
/*     */ import com.dreammirae.mmth.parser.json.GsonUtils;
/*     */ import com.dreammirae.mmth.vo.FidoMetadataVO;
/*     */ import com.dreammirae.mmth.vo.bean.IRestValidator;
/*     */ import com.dreammirae.mmth.vo.bean.RestResponse;
/*     */ import com.dreammirae.mmth.web.controller.view.rest.ViewController;
/*     */ import com.dreammirae.mmth.web.exception.I18nMessageException;
/*     */ import com.dreammirae.mmth.web.service.FidoMetadataService;
/*     */ import com.dreammirae.mmth.web.service.ViewService;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ import org.springframework.web.bind.annotation.RequestMethod;
/*     */ import org.springframework.web.bind.annotation.RequestParam;
/*     */ import org.springframework.web.bind.annotation.RestController;
/*     */ import org.springframework.web.multipart.MultipartFile;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @RestController
/*     */ @RequestMapping(value = {"/web/manager/metadata/rest"}, method = {RequestMethod.POST})
/*     */ public class FidoMetadataController
/*     */   extends ViewController<FidoMetadataVO, Integer>
/*     */ {
/*     */   @Autowired
/*     */   private FidoMetadataService service;
/*     */   
/*     */   @RequestMapping({"/policy"})
/*     */   public RestResponse policy() {
/*  39 */     RestResponse resp = new RestResponse();
/*     */     
/*     */     try {
/*  42 */       MatchCriteria[][] mc = FidoMetadataDao.getAcceptPolicy();
/*     */       
/*  44 */       if (mc == null) {
/*  45 */         return resp;
/*     */       }
/*  47 */       Policy policy = new Policy();
/*  48 */       policy.setAccepted(mc);
/*     */       
/*  50 */       resp.addData("policy", GsonUtils.gson().toJson(policy));
/*  51 */     } catch (Exception e) {
/*     */       
/*  53 */       e.printStackTrace();
/*     */     } 
/*     */     
/*  56 */     return resp;
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
/*     */   @RequestMapping({"/upload"})
/*     */   public RestResponse upload(@RequestParam("metadataFile") MultipartFile metadataJson, @RequestParam("add-alias") String alias, @RequestParam(value = "add-description", defaultValue = "") String description, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
/*  69 */     RestResponse resp = new RestResponse();
/*     */     
/*     */     try {
/*  72 */       FidoMetadataVO vo = getViewService().upload(metadataJson, alias, description, getSessionAdmin(session));
/*  73 */       resp.addData("details", vo);
/*  74 */       getPagination(resp, 1, null, null, null);
/*  75 */       getViewStats(resp);
/*     */     }
/*  77 */     catch (I18nMessageException e) {
/*  78 */       setI18nErrorMessage(resp, e);
/*     */     } 
/*     */     
/*  81 */     return resp;
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
/*     */   protected void initImp(RestResponse resp, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
/*  98 */     getPagination(resp, 1, null, null, null);
/*  99 */     getViewStats(resp);
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
/* 112 */     getDetails(resp, id);
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
/*     */   protected void saveImp(RestResponse resp, FidoMetadataVO vo, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
/* 127 */     if (-1 == vo.getId()) {
/* 128 */       resp.addGeneralMessage(new I18nMessage("validate.notSupprted"));
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 133 */     doSave(resp, (IRestValidator)vo, session);
/*     */     
/* 135 */     if (resp.getHasMessages()) {
/*     */       return;
/*     */     }
/*     */     
/* 139 */     getPagination(resp, 1, null, null, null);
/* 140 */     getViewStats(resp);
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
/*     */   protected void deleteImp(RestResponse resp, Integer id, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
/* 154 */     doDelete(resp, id, session);
/*     */     
/* 156 */     if (resp.getHasMessages()) {
/*     */       return;
/*     */     }
/*     */     
/* 160 */     getPagination(resp, 1, null, null, null);
/* 161 */     getViewStats(resp);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected FidoMetadataService getViewService() {
/* 172 */     if (this.service == null) {
/* 173 */       this.service = new FidoMetadataService();
/*     */     }
/*     */     
/* 176 */     return this.service;
/*     */   }
/*     */   
/*     */   public static void main(String[] args) {
/* 180 */     MatchCriteria[][] accepted = new MatchCriteria[5][1];
/*     */     
/* 182 */     for (int i = 0, len = 5; i < len; i++) {
/*     */ 
/*     */ 
/*     */       
/* 186 */       MatchCriteria mc = new MatchCriteria();
/* 187 */       mc.setAaid(new String[] { "1000#000" + (i + 1) });
/*     */ 
/*     */       
/* 190 */       MatchCriteria[] row = new MatchCriteria[1];
/* 191 */       row[0] = mc;
/* 192 */       accepted[i] = row;
/*     */     } 
/*     */     
/* 195 */     System.out.println(GsonUtils.gson().toJson(accepted));
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\web\controller\view\rest\FidoMetadataController.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */