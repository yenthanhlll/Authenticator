/*     */ package WEB-INF.classes.com.dreammirae.mmth.rp.controller.fido;
/*     */ 
/*     */ import com.dreammirae.mmth.authentication.AuthMethodTypes;
/*     */ import com.dreammirae.mmth.authentication.ReturnCodes;
/*     */ import com.dreammirae.mmth.authentication.bean.AuthenticationRequestLocator;
/*     */ import com.dreammirae.mmth.authentication.bean.AuthenticationResponseLocator;
/*     */ import com.dreammirae.mmth.authentication.service.ext.FidoExternalService;
/*     */ import com.dreammirae.mmth.exception.ReturnCodeException;
/*     */ import com.dreammirae.mmth.rp.controller.fido.FidoAuthenticationController;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.web.bind.annotation.RequestBody;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ import org.springframework.web.bind.annotation.RestController;
/*     */ 
/*     */ 
/*     */ 
/*     */ @RestController
/*     */ public class FidoExternalController
/*     */ {
/*  23 */   protected static final Logger LOG = LoggerFactory.getLogger(FidoAuthenticationController.class);
/*     */ 
/*     */   
/*     */   protected static final String LOG_FORMAT_REQ_MSG = "## FIDO-RECEIVED MSG [RequestURI=%s] :: %s";
/*     */ 
/*     */   
/*     */   protected static final String LOG_FORMAT_RESP_MSG = "## FIDO-SEND MSG [RequestURI=%s, LapTime=%,d ms] :: %s";
/*     */ 
/*     */   
/*     */   protected static final String LOG_FORMAT_ERROR_MSG = "## FIDO-ERR MSG [RequestURI=%s] :: %s";
/*     */   
/*     */   @Autowired
/*     */   private FidoExternalService externalService;
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/rpserver/httpapi/FIDO/CheckUser"}, consumes = {"application/fido+uaf; charset=utf-8"}, produces = {"application/fido+uaf; charset=utf-8"})
/*     */   public String checkUser(@RequestBody String payload, HttpServletRequest httpRequest) {
/*  40 */     String json = null;
/*  41 */     long startMillis = 0L;
/*     */     
/*  43 */     if (LOG.isDebugEnabled()) {
/*  44 */       LOG.debug(String.format("## FIDO-RECEIVED MSG [RequestURI=%s] :: %s", new Object[] { httpRequest.getRequestURI(), payload }));
/*  45 */       startMillis = System.currentTimeMillis();
/*     */     } 
/*     */     
/*  48 */     AuthenticationResponseLocator respLocator = getResponseLocator();
/*     */ 
/*     */     
/*  51 */     AuthenticationRequestLocator reqLocator = parseRequestPayload(respLocator, payload, httpRequest);
/*     */     
/*  53 */     if (reqLocator != null) {
/*     */       
/*     */       try {
/*  56 */         this.externalService.procCheckUser(respLocator, reqLocator.getUsername());
/*  57 */       } catch (ReturnCodeException e) {
/*  58 */         respLocator.setReturnCode(e.getReturnCode());
/*  59 */       } catch (Exception e) {
/*  60 */         respLocator.setReturnCode(ReturnCodes.INTERNAL_SERVER_ERROR);
/*  61 */         LOG.error(e.getMessage());
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*  66 */     json = generateResponsePayload(respLocator, httpRequest);
/*     */     
/*  68 */     if (LOG.isDebugEnabled()) {
/*  69 */       LOG.debug(String.format("## FIDO-SEND MSG [RequestURI=%s, LapTime=%,d ms] :: %s", new Object[] { httpRequest.getRequestURI(), Long.valueOf(System.currentTimeMillis() - startMillis), json }));
/*     */     }
/*     */     
/*  72 */     return json;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @RequestMapping({"/rpserver/httpapi/FIDO/UserCancel"})
/*     */   private String cancelAuthentication(@RequestBody String payload, HttpServletRequest httpRequest) {
/*  79 */     String json = null;
/*  80 */     long startMillis = 0L;
/*     */     
/*  82 */     if (LOG.isDebugEnabled()) {
/*  83 */       LOG.debug(String.format("## FIDO-RECEIVED MSG [RequestURI=%s] :: %s", new Object[] { httpRequest.getRequestURI(), payload }));
/*  84 */       startMillis = System.currentTimeMillis();
/*     */     } 
/*     */     
/*  87 */     AuthenticationResponseLocator respLocator = getResponseLocator();
/*     */ 
/*     */     
/*  90 */     AuthenticationRequestLocator reqLocator = parseRequestPayload(respLocator, payload, httpRequest);
/*     */ 
/*     */     
/*  93 */     if (reqLocator != null) {
/*     */       
/*     */       try {
/*  96 */         this.externalService.procCancelUser(respLocator, reqLocator.getUsername());
/*  97 */       } catch (ReturnCodeException e) {
/*  98 */         respLocator.setReturnCode(e.getReturnCode());
/*  99 */       } catch (Exception e) {
/* 100 */         respLocator.setReturnCode(ReturnCodes.INTERNAL_SERVER_ERROR);
/* 101 */         LOG.error(e.getMessage());
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 106 */     json = generateResponsePayload(respLocator, httpRequest);
/*     */     
/* 108 */     if (LOG.isDebugEnabled()) {
/* 109 */       LOG.debug(String.format("## FIDO-SEND MSG [RequestURI=%s, LapTime=%,d ms] :: %s", new Object[] { httpRequest.getRequestURI(), Long.valueOf(System.currentTimeMillis() - startMillis), json }));
/*     */     }
/*     */     
/* 112 */     return json;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @RequestMapping({"/rpserver/httpapi/FIDO/NotifyDereg"})
/*     */   private String notifyDereg(@RequestBody String payload, HttpServletRequest httpRequest) {
/* 119 */     String json = null;
/* 120 */     long startMillis = 0L;
/*     */     
/* 122 */     if (LOG.isDebugEnabled()) {
/* 123 */       LOG.debug(String.format("## FIDO-RECEIVED MSG [RequestURI=%s] :: %s", new Object[] { httpRequest.getRequestURI(), payload }));
/* 124 */       startMillis = System.currentTimeMillis();
/*     */     } 
/*     */ 
/*     */     
/* 128 */     AuthenticationResponseLocator respLocator = getResponseLocator();
/*     */ 
/*     */     
/* 131 */     AuthenticationRequestLocator reqLocator = parseRequestPayload(respLocator, payload, httpRequest);
/*     */     
/* 133 */     if (reqLocator != null) {
/*     */       
/*     */       try {
/* 136 */         this.externalService.procNotifyDereg(respLocator, reqLocator.getUsername());
/* 137 */       } catch (ReturnCodeException e) {
/* 138 */         respLocator.setReturnCode(e.getReturnCode());
/* 139 */       } catch (Exception e) {
/* 140 */         respLocator.setReturnCode(ReturnCodes.INTERNAL_SERVER_ERROR);
/* 141 */         LOG.error(e.getMessage());
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 146 */     json = generateResponsePayload(respLocator, httpRequest);
/*     */     
/* 148 */     if (LOG.isDebugEnabled()) {
/* 149 */       LOG.debug(String.format("## FIDO-SEND MSG [RequestURI=%s, LapTime=%,d ms] :: %s", new Object[] { httpRequest.getRequestURI(), Long.valueOf(System.currentTimeMillis() - startMillis), json }));
/*     */     }
/*     */     
/* 152 */     return json;
/*     */   }
/*     */   
/*     */   protected AuthenticationRequestLocator parseRequestPayload(AuthenticationResponseLocator respLocator, String payload, HttpServletRequest request) throws ReturnCodeException {
/*     */     try {
/* 157 */       return AuthMethodTypes.FIDO.getUserServiceMessageParser().parseRequestLocator(payload, request);
/* 158 */     } catch (ReturnCodeException e) {
/* 159 */       respLocator.setReturnCode(e.getReturnCode());
/* 160 */       LOG.error(String.format("## FIDO-ERR MSG [RequestURI=%s] :: %s", new Object[] { e.getMessage(), "" }));
/* 161 */     } catch (Exception e) {
/* 162 */       respLocator.setReturnCode(ReturnCodes.INVALID_PARAMS);
/* 163 */       LOG.error(String.format("## FIDO-ERR MSG [RequestURI=%s] :: %s", new Object[] { e.getMessage(), "" }));
/*     */     } 
/*     */     
/* 166 */     return null;
/*     */   }
/*     */   
/*     */   protected AuthenticationResponseLocator getResponseLocator() {
/* 170 */     return AuthMethodTypes.FIDO.getUserServiceMessageParser().createResponseLocatorInstance();
/*     */   }
/*     */   
/*     */   protected String generateResponsePayload(AuthenticationResponseLocator respLocator, HttpServletRequest request) throws ReturnCodeException {
/* 174 */     return AuthMethodTypes.FIDO.getUserServiceMessageParser().generateResponseLocatorPayload(respLocator, request);
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\rp\controller\fido\FidoExternalController.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */