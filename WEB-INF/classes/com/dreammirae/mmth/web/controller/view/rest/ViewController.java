/*     */ package WEB-INF.classes.com.dreammirae.mmth.web.controller.view.rest;
/*     */ 
/*     */ import com.dreammirae.mmth.config.Commons;
/*     */ import com.dreammirae.mmth.misc.I18nMessage;
/*     */ import com.dreammirae.mmth.util.StringUtils;
/*     */ import com.dreammirae.mmth.util.io.SerializationUtils;
/*     */ import com.dreammirae.mmth.vo.AdministratorVO;
/*     */ import com.dreammirae.mmth.vo.bean.IRestValidator;
/*     */ import com.dreammirae.mmth.vo.bean.IViewStatsLocator;
/*     */ import com.dreammirae.mmth.vo.bean.Pagination;
/*     */ import com.dreammirae.mmth.vo.bean.RestResponse;
/*     */ import com.dreammirae.mmth.web.exception.I18nMessageException;
/*     */ import com.dreammirae.mmth.web.service.ViewService;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.web.bind.annotation.ExceptionHandler;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ import org.springframework.web.bind.annotation.RequestParam;
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
/*     */ public abstract class ViewController<T extends IRestValidator, K>
/*     */ {
/*  38 */   protected static final Logger LOG = LoggerFactory.getLogger(com.dreammirae.mmth.web.controller.view.rest.ViewController.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RequestMapping({"/init"})
/*     */   public RestResponse init(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
/*  47 */     RestResponse resp = new RestResponse();
/*  48 */     initImp(resp, session, request, response);
/*  49 */     return resp;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RequestMapping({"/pageContents"})
/*     */   public RestResponse pageContents(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum, @RequestParam(value = "startDate", required = false) String startDate, @RequestParam(value = "endDate", required = false) String endDate, T searchParams) {
/*  58 */     RestResponse resp = new RestResponse();
/*     */     
/*  60 */     Long tsFrom = StringUtils.isEmpty(startDate) ? null : Long.valueOf(Commons.DISPLAY_DATE_FORMATTER.parseDateTime(startDate).getMillis());
/*  61 */     Long tsTo = StringUtils.isEmpty(endDate) ? null : Long.valueOf(Commons.DISPLAY_DATE_FORMATTER.parseDateTime(endDate).plusDays(1).getMillis() - 1L);
/*     */     
/*  63 */     getPagination(resp, pageNum, searchParams, tsFrom, tsTo);
/*     */     
/*  65 */     return resp;
/*     */   }
/*     */   
/*     */   @RequestMapping({"/details"})
/*     */   public RestResponse details(@RequestParam(value = "id", defaultValue = "-1") K id, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
/*  70 */     RestResponse resp = new RestResponse();
/*  71 */     detailsImp(resp, id, session, request, response);
/*  72 */     return resp;
/*     */   }
/*     */   
/*     */   @RequestMapping({"/save"})
/*     */   public RestResponse save(T vo, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
/*  77 */     RestResponse resp = new RestResponse();
/*  78 */     saveImp(resp, vo, session, request, response);
/*  79 */     return resp;
/*     */   }
/*     */ 
/*     */   
/*     */   @RequestMapping({"/delete"})
/*     */   public RestResponse delete(@RequestParam(value = "id", defaultValue = "-1") K id, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
/*  85 */     RestResponse resp = new RestResponse();
/*  86 */     deleteImp(resp, id, session, request, response);
/*  87 */     return resp;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void getPagination(RestResponse resp, int pageNum, T params, Long tsFrom, Long tsTo) {
/*     */     try {
/*  96 */       Pagination<T> pagination = getViewService().getPagination(pageNum, (IRestValidator)params, tsFrom, tsTo);
/*  97 */       resp.addData("pageContents", pagination);
/*  98 */     } catch (I18nMessageException e) {
/*  99 */       setI18nErrorMessage(resp, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void getViewStats(RestResponse resp) {
/*     */     try {
/* 106 */       IViewStatsLocator loc = getViewService().getViewStatsLocator();
/* 107 */       resp.addData("stats", loc);
/* 108 */     } catch (I18nMessageException e) {
/* 109 */       setI18nErrorMessage(resp, e);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void getDetails(RestResponse resp, K id) {
/*     */     try {
/* 115 */       IRestValidator iRestValidator = getViewService().getDetails(id);
/* 116 */       resp.addData("details", iRestValidator);
/* 117 */     } catch (I18nMessageException e) {
/* 118 */       setI18nErrorMessage(resp, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doSave(RestResponse resp, T vo, HttpSession session) {
/* 124 */     vo.validate(resp);
/*     */     
/* 126 */     if (resp.getHasMessages()) {
/*     */       return;
/*     */     }
/*     */     
/*     */     try {
/* 131 */       getViewService().save(getSessionAdmin(session), (IRestValidator)vo);
/* 132 */       resp.addData("details", vo);
/* 133 */     } catch (I18nMessageException e) {
/* 134 */       setI18nErrorMessage(resp, e);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void doDelete(RestResponse resp, K id, HttpSession session) {
/*     */     try {
/* 140 */       getViewService().delete(getSessionAdmin(session), id);
/* 141 */     } catch (I18nMessageException e) {
/* 142 */       setI18nErrorMessage(resp, e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void setI18nErrorMessage(RestResponse response, I18nMessageException e) {
/* 147 */     if (StringUtils.isEmpty(e.getContextKey())) {
/* 148 */       response.addGeneralMessage(e.getI18nMessage());
/*     */     } else {
/* 150 */       response.addContextMessage(e.getContextKey(), e.getI18nMessage());
/*     */     } 
/*     */     
/* 153 */     LOG.error("Error occured...", (Throwable)e);
/*     */   }
/*     */ 
/*     */   
/*     */   public static AdministratorVO getSessionAdmin(HttpSession session) throws I18nMessageException {
/* 158 */     if (session == null) {
/* 159 */       throw new I18nMessageException(new I18nMessage("validate.lostSession"));
/*     */     }
/*     */     
/* 162 */     byte[] adminRaw = (byte[])session.getAttribute("MMTH.ADMIN");
/*     */ 
/*     */     
/*     */     try {
/* 166 */       AdministratorVO admin = (AdministratorVO)SerializationUtils.deserializeObject(adminRaw);
/* 167 */       if (admin == null || StringUtils.isEmpty(admin.getUsername())) {
/* 168 */         throw new I18nMessageException(new I18nMessage("validate.lostSession"));
/*     */       }
/*     */       
/* 171 */       return admin;
/* 172 */     } catch (ClassNotFoundException|java.io.IOException e) {
/* 173 */       e.printStackTrace();
/*     */ 
/*     */       
/* 176 */       throw new I18nMessageException(new I18nMessage("validate.lostSession"));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void initImp(RestResponse paramRestResponse, HttpSession paramHttpSession, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void detailsImp(RestResponse paramRestResponse, K paramK, HttpSession paramHttpSession, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void saveImp(RestResponse paramRestResponse, T paramT, HttpSession paramHttpSession, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void deleteImp(RestResponse paramRestResponse, K paramK, HttpSession paramHttpSession, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract ViewService<T, K> getViewService();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @ExceptionHandler({Throwable.class})
/*     */   public RestResponse handleException(HttpServletRequest req, HttpServletResponse resp, Throwable ex) {
/* 214 */     RestResponse response = new RestResponse();
/* 215 */     response.addGeneralMessage(new I18nMessage("validate.unknownError", new Object[] { ex.getMessage() }));
/* 216 */     return response;
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\web\controller\view\rest\ViewController.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */