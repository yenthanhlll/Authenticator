/*     */ package WEB-INF.classes.com.dreammirae.mmth.rp.controller.fido;
/*     */ 
/*     */ import com.dreammirae.mmth.authentication.AuthMethodTypes;
/*     */ import com.dreammirae.mmth.authentication.ReturnCodes;
/*     */ import com.dreammirae.mmth.authentication.service.IGeneralService;
/*     */ import com.dreammirae.mmth.authentication.service.IUAFRequestService;
/*     */ import com.dreammirae.mmth.authentication.service.IUAFResponseService;
/*     */ import com.dreammirae.mmth.authentication.service.IVerifyOTPService;
/*     */ import com.dreammirae.mmth.authentication.service.IssueCodeService;
/*     */ import com.dreammirae.mmth.authentication.service.fido.FidoUAFRequestService;
/*     */ import com.dreammirae.mmth.authentication.service.fido.FidoUAFResponseService;
/*     */ import com.dreammirae.mmth.exception.ReturnCodeException;
/*     */ import com.dreammirae.mmth.fido.Operation;
/*     */ import com.dreammirae.mmth.fido.StatusCodes;
/*     */ import com.dreammirae.mmth.fido.exception.FidoUafParsingException;
/*     */ import com.dreammirae.mmth.fido.exception.FidoUafStatusCodeException;
/*     */ import com.dreammirae.mmth.rp.controller.AuthenticationController;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.web.bind.annotation.ExceptionHandler;
/*     */ import org.springframework.web.bind.annotation.PathVariable;
/*     */ import org.springframework.web.bind.annotation.RequestBody;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ import org.springframework.web.bind.annotation.RestController;
/*     */ 
/*     */ 
/*     */ @RestController
/*     */ @RequestMapping(value = {"/rpserver/httpapi/FIDO"}, consumes = {"application/fido+uaf; charset=utf-8"}, produces = {"application/fido+uaf; charset=utf-8"})
/*     */ public class FidoAuthenticationController
/*     */   extends AuthenticationController
/*     */ {
/*  35 */   private static final Logger LOG = LoggerFactory.getLogger(com.dreammirae.mmth.rp.controller.fido.FidoAuthenticationController.class);
/*     */   
/*     */   @Autowired
/*     */   private FidoUAFRequestService fidoUAFRequestService;
/*     */   
/*     */   @Autowired
/*     */   private FidoUAFResponseService fidoUAFResponseService;
/*     */   
/*     */   @Autowired
/*     */   private IssueCodeService issueCodeService;
/*     */   
/*     */   protected static final String REGEX_URI = "^.*.(Get|Send/(Reg|Auth))$";
/*     */   
/*     */   protected static final String UAF_ERR_RETURN_FORMAT = "{\"statusCode\" : %d }";
/*     */   
/*     */   protected static final String ERR_RETURN_FORMAT = "{\"returnCode\" : \"%s\" }";
/*     */   
/*     */   @RequestMapping({"/Get"})
/*     */   public String generateUAFRequest(@RequestBody String payload, HttpServletRequest httpRequest) {
/*  54 */     String json = null;
/*  55 */     long startMillis = System.currentTimeMillis();
/*     */ 
/*     */     
/*  58 */     LOG.info(genReceivedReqLogMsg(payload, httpRequest));
/*     */     
/*  60 */     json = generateUAFRequestImp(payload, httpRequest);
/*     */ 
/*     */     
/*  63 */     LOG.info(genSendRespLogMsg(json, startMillis, httpRequest));
/*     */     
/*  65 */     return json;
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
/*     */   @RequestMapping({"/Send/{op}"})
/*     */   public String verifyingUAFResponse(@PathVariable("op") Operation op, @RequestBody String payload, HttpServletRequest httpRequest) {
/*  78 */     String json = null;
/*  79 */     long startMillis = System.currentTimeMillis();
/*     */ 
/*     */     
/*  82 */     LOG.info(genReceivedReqLogMsg(payload, httpRequest));
/*     */     
/*  84 */     json = verifyingUAFResponseImp(op, payload, httpRequest);
/*     */ 
/*     */     
/*  87 */     LOG.info(genSendRespLogMsg(json, startMillis, httpRequest));
/*     */     
/*  89 */     return json;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RequestMapping({"/IssueCode"})
/*     */   public String generateIssueCode(@RequestBody String payload, HttpServletRequest httpRequest) {
/*  98 */     if (!AuthMethodTypes.FIDO.enabledIssueCode()) {
/*  99 */       return String.format("{\"returnCode\" : \"%s\" }", new Object[] { ReturnCodes.NOT_FOUND_URL.getCode() });
/*     */     }
/*     */ 
/*     */     
/* 103 */     String json = null;
/* 104 */     long startMillis = System.currentTimeMillis();
/*     */ 
/*     */     
/* 107 */     LOG.info(genReceivedReqLogMsg(payload, httpRequest));
/*     */     
/* 109 */     json = generateIssueCodeImp(payload, httpRequest);
/*     */ 
/*     */     
/* 112 */     LOG.info(genSendRespLogMsg(json, startMillis, httpRequest));
/*     */     
/* 114 */     return json;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected AuthMethodTypes getAuthMethodType() {
/* 120 */     return AuthMethodTypes.FIDO;
/*     */   }
/*     */ 
/*     */   
/*     */   protected IUAFRequestService getUAFRequestService() {
/* 125 */     if (this.fidoUAFRequestService == null) {
/* 126 */       this.fidoUAFRequestService = new FidoUAFRequestService();
/*     */     }
/*     */     
/* 129 */     return (IUAFRequestService)this.fidoUAFRequestService;
/*     */   }
/*     */ 
/*     */   
/*     */   protected IUAFResponseService getUAFResponseService() {
/* 134 */     if (this.fidoUAFResponseService == null) {
/* 135 */       this.fidoUAFResponseService = new FidoUAFResponseService();
/*     */     }
/*     */     
/* 138 */     return (IUAFResponseService)this.fidoUAFResponseService;
/*     */   }
/*     */ 
/*     */   
/*     */   protected IVerifyOTPService getVerifyOTPService() {
/* 143 */     throw new ReturnCodeException(ReturnCodes.REQUEST_INVALID);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected IssueCodeService getIssueCodeService() {
/* 149 */     if (this.issueCodeService == null) {
/* 150 */       this.issueCodeService = new IssueCodeService();
/*     */     }
/*     */     
/* 153 */     return this.issueCodeService;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected IGeneralService getGeneralService() {
/* 159 */     return null;
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
/*     */   @ExceptionHandler({FidoUafStatusCodeException.class})
/*     */   public String handleFidoUafStatusCodeException(HttpServletRequest req, HttpServletResponse resp, FidoUafStatusCodeException ex) {
/* 177 */     LOG.error(String.format("## ERROR MSG [AuthMethodType=%s, RequestURI=%s] :: %s", new Object[] { getAuthMethodType().name(), req.getRequestURI(), ex }));
/* 178 */     return String.format("{\"statusCode\" : %d }", new Object[] { ex.getStatusCode().getId() });
/*     */   }
/*     */   
/*     */   @ExceptionHandler({FidoUafParsingException.class})
/*     */   public String handleFidoUafParsingException(HttpServletRequest req, HttpServletResponse resp, FidoUafParsingException ex) {
/* 183 */     LOG.error(String.format("## ERROR MSG [AuthMethodType=%s, RequestURI=%s] :: %s", new Object[] { getAuthMethodType().name(), req.getRequestURI(), ex }));
/* 184 */     return String.format("{\"statusCode\" : %d }", new Object[] { StatusCodes.CODE_1498.getId() });
/*     */   }
/*     */   
/*     */   @ExceptionHandler({ReturnCodeException.class})
/*     */   public String handleReturnCodeException(HttpServletRequest req, HttpServletResponse resp, ReturnCodeException ex) {
/* 189 */     ReturnCodes returnCode = ex.getReturnCode();
/*     */     
/* 191 */     if (req.getRequestURI().matches("^.*.(Get|Send/(Reg|Auth))$")) {
/* 192 */       LOG.error(String.format("## ERROR MSG [AuthMethodType=%s, RequestURI=%s] :: %s", new Object[] { getAuthMethodType().name(), req.getRequestURI(), ex }));
/* 193 */       return String.format("{\"statusCode\" : %d }", new Object[] { returnCode.getStatusCodes().getId() });
/*     */     } 
/*     */     
/* 196 */     LOG.error(String.format("## ERROR MSG [AuthMethodType=%s, RequestURI=%s] :: %s", new Object[] { getAuthMethodType().name(), req.getRequestURI(), ex }));
/* 197 */     return String.format("{\"returnCode\" : \"%s\" }", new Object[] { returnCode.getCode() });
/*     */   }
/*     */ 
/*     */   
/*     */   @ExceptionHandler({Throwable.class})
/*     */   public String handleException(HttpServletRequest req, HttpServletResponse resp, Throwable ex) {
/* 203 */     if (req.getRequestURI().matches("^.*.(Get|Send/(Reg|Auth))$")) {
/* 204 */       LOG.error(String.format("## ERROR MSG [AuthMethodType=%s, RequestURI=%s] :: %s", new Object[] { getAuthMethodType().name(), req.getRequestURI(), ex }));
/* 205 */       return String.format("{\"statusCode\" : %d }", new Object[] { StatusCodes.CODE_1500.getId() });
/*     */     } 
/*     */     
/* 208 */     LOG.error(String.format("## ERROR MSG [AuthMethodType=%s, RequestURI=%s] :: %s", new Object[] { getAuthMethodType().name(), req.getRequestURI(), ex }));
/* 209 */     return String.format("{\"returnCode\" : \"%s\" }", new Object[] { ReturnCodes.INTERNAL_SERVER_ERROR.getCode() });
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\rp\controller\fido\FidoAuthenticationController.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */