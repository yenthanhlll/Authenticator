/*     */ package WEB-INF.classes.com.dreammirae.mmth.rp.controller.biotp;
/*     */ 
/*     */ import com.dreammirae.mmth.authentication.AuthMethodTypes;
/*     */ import com.dreammirae.mmth.authentication.ReturnCodes;
/*     */ import com.dreammirae.mmth.authentication.service.IGeneralService;
/*     */ import com.dreammirae.mmth.authentication.service.IUAFRequestService;
/*     */ import com.dreammirae.mmth.authentication.service.IUAFResponseService;
/*     */ import com.dreammirae.mmth.authentication.service.IVerifyOTPService;
/*     */ import com.dreammirae.mmth.authentication.service.IssueCodeService;
/*     */ import com.dreammirae.mmth.authentication.service.biotp.BiotpGeneralService;
/*     */ import com.dreammirae.mmth.authentication.service.biotp.BiotpUAFRequestService;
/*     */ import com.dreammirae.mmth.authentication.service.biotp.BiotpUAFResponseService;
/*     */ import com.dreammirae.mmth.authentication.service.biotp.BiotpVerifyOTPService;
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
/*     */ @RequestMapping(value = {"/rpserver/httpapi/BIOTP"}, consumes = {"application/fido+uaf; charset=utf-8"}, produces = {"application/fido+uaf; charset=utf-8"})
/*     */ public class BiotpAuthenticationController
/*     */   extends AuthenticationController
/*     */ {
/*  37 */   private static final Logger LOG = LoggerFactory.getLogger(com.dreammirae.mmth.rp.controller.biotp.BiotpAuthenticationController.class);
/*     */   
/*     */   @Autowired
/*     */   private BiotpUAFRequestService biotpUAFRequestService;
/*     */   
/*     */   @Autowired
/*     */   private BiotpUAFResponseService biotpUAFResponseService;
/*     */   
/*     */   @Autowired
/*     */   private BiotpVerifyOTPService biotpVerifyOTPService;
/*     */   
/*     */   @Autowired
/*     */   private BiotpGeneralService biotpGeneralService;
/*     */   
/*     */   @Autowired
/*     */   private IssueCodeService issueCodeService;
/*     */   
/*     */   protected static final String REGEX_URI = "^.*.(Get|Send/(Reg|Auth))$";
/*     */   
/*     */   protected static final String REGEX_ISSUE_CODE_URI = "^.*.(IssueCode)$";
/*     */   
/*     */   protected static final String UAF_ERR_RETURN_FORMAT = "{\"statusCode\" : %d }";
/*     */   
/*     */   protected static final String ERR_RETURN_FORMAT = "{\"returnCode\" : \"%s\" }";
/*     */   
/*     */   @RequestMapping({"/Get"})
/*     */   public String generateUAFRequest(@RequestBody String payload, HttpServletRequest httpRequest) {
/*  64 */     String json = null;
/*  65 */     long startMillis = System.currentTimeMillis();
/*     */ 
/*     */     
/*  68 */     LOG.info(genReceivedReqLogMsg(payload, httpRequest));
/*     */     
/*  70 */     json = generateUAFRequestImp(payload, httpRequest);
/*     */ 
/*     */     
/*  73 */     LOG.info(genSendRespLogMsg(json, startMillis, httpRequest));
/*     */     
/*  75 */     return json;
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
/*  88 */     String json = null;
/*  89 */     long startMillis = System.currentTimeMillis();
/*     */ 
/*     */     
/*  92 */     LOG.info(genReceivedReqLogMsg(payload, httpRequest));
/*     */     
/*  94 */     json = verifyingUAFResponseImp(op, payload, httpRequest);
/*     */ 
/*     */     
/*  97 */     LOG.info(genSendRespLogMsg(json, startMillis, httpRequest));
/*     */     
/*  99 */     return json;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RequestMapping({"/VerifyOTP"})
/*     */   public String verifyingCommonOTP(@RequestBody String payload, HttpServletRequest httpRequest) {
/* 111 */     String json = null;
/* 112 */     long startMillis = System.currentTimeMillis();
/*     */ 
/*     */     
/* 115 */     LOG.info(genReceivedReqLogMsg(payload, httpRequest));
/*     */     
/* 117 */     json = verifyingOTPImp(payload, httpRequest, false);
/*     */ 
/*     */     
/* 120 */     LOG.info(genSendRespLogMsg(json, startMillis, httpRequest));
/*     */     
/* 122 */     return json;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RequestMapping({"/VerifyEncOTP"})
/*     */   public String verifyingCommonEncOTP(@RequestBody String payload, HttpServletRequest httpRequest) {
/* 134 */     String json = null;
/* 135 */     long startMillis = System.currentTimeMillis();
/*     */ 
/*     */     
/* 138 */     LOG.info(genReceivedReqLogMsg(payload, httpRequest));
/*     */     
/* 140 */     json = verifyingOTPImp(payload, httpRequest, true);
/*     */ 
/*     */     
/* 143 */     LOG.info(genSendRespLogMsg(json, startMillis, httpRequest));
/*     */     
/* 145 */     return json;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RequestMapping({"/VerifyTranOTP"})
/*     */   public String verifyingTranOTP(@RequestBody String payload, HttpServletRequest httpRequest) {
/* 157 */     String json = null;
/* 158 */     long startMillis = System.currentTimeMillis();
/*     */ 
/*     */     
/* 161 */     LOG.info(genReceivedReqLogMsg(payload, httpRequest));
/*     */     
/* 163 */     json = verifyingTranOTPImp(payload, httpRequest, false);
/*     */ 
/*     */     
/* 166 */     LOG.info(genSendRespLogMsg(json, startMillis, httpRequest));
/*     */     
/* 168 */     return json;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RequestMapping({"/VerifyEncTranOTP"})
/*     */   public String verifyingTranEncOTP(@RequestBody String payload, HttpServletRequest httpRequest) {
/* 180 */     String json = null;
/* 181 */     long startMillis = System.currentTimeMillis();
/*     */ 
/*     */     
/* 184 */     LOG.info(genReceivedReqLogMsg(payload, httpRequest));
/*     */     
/* 186 */     json = verifyingTranOTPImp(payload, httpRequest, true);
/*     */ 
/*     */     
/* 189 */     LOG.info(genSendRespLogMsg(json, startMillis, httpRequest));
/*     */     
/* 191 */     return json;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RequestMapping({"/IssueCode"})
/*     */   public String generateIssueCode(@RequestBody String payload, HttpServletRequest httpRequest) {
/* 200 */     if (!AuthMethodTypes.FIDO.enabledIssueCode()) {
/* 201 */       return String.format("{\"returnCode\" : \"%s\" }", new Object[] { ReturnCodes.NOT_FOUND_URL.getCode() });
/*     */     }
/*     */ 
/*     */     
/* 205 */     String json = null;
/* 206 */     long startMillis = System.currentTimeMillis();
/*     */ 
/*     */     
/* 209 */     LOG.info(genReceivedReqLogMsg(payload, httpRequest));
/*     */     
/* 211 */     json = generateIssueCodeImp(payload, httpRequest);
/*     */ 
/*     */     
/* 214 */     LOG.info(genSendRespLogMsg(json, startMillis, httpRequest));
/*     */     
/* 216 */     return json;
/*     */   }
/*     */ 
/*     */   
/*     */   protected AuthMethodTypes getAuthMethodType() {
/* 221 */     return AuthMethodTypes.BIOTP;
/*     */   }
/*     */ 
/*     */   
/*     */   protected IUAFRequestService getUAFRequestService() {
/* 226 */     if (this.biotpUAFRequestService == null) {
/* 227 */       this.biotpUAFRequestService = new BiotpUAFRequestService();
/*     */     }
/* 229 */     return (IUAFRequestService)this.biotpUAFRequestService;
/*     */   }
/*     */ 
/*     */   
/*     */   protected IUAFResponseService getUAFResponseService() {
/* 234 */     if (this.biotpUAFResponseService == null) {
/* 235 */       this.biotpUAFResponseService = new BiotpUAFResponseService();
/*     */     }
/* 237 */     return (IUAFResponseService)this.biotpUAFResponseService;
/*     */   }
/*     */ 
/*     */   
/*     */   protected IVerifyOTPService getVerifyOTPService() {
/* 242 */     if (this.biotpVerifyOTPService == null) {
/* 243 */       this.biotpVerifyOTPService = new BiotpVerifyOTPService();
/*     */     }
/*     */     
/* 246 */     return (IVerifyOTPService)this.biotpVerifyOTPService;
/*     */   }
/*     */ 
/*     */   
/*     */   protected IssueCodeService getIssueCodeService() {
/* 251 */     if (this.issueCodeService == null) {
/* 252 */       this.issueCodeService = new IssueCodeService();
/*     */     }
/*     */     
/* 255 */     return this.issueCodeService;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected IGeneralService getGeneralService() {
/* 262 */     if (this.biotpGeneralService == null) {
/* 263 */       this.biotpGeneralService = new BiotpGeneralService();
/*     */     }
/* 265 */     return (IGeneralService)this.biotpGeneralService;
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
/*     */   @ExceptionHandler({FidoUafStatusCodeException.class})
/*     */   public String handleFidoUafStatusCodeException(HttpServletRequest req, HttpServletResponse resp, FidoUafStatusCodeException ex) {
/* 285 */     LOG.error(String.format("## ERROR MSG [AuthMethodType=%s, RequestURI=%s] :: %s", new Object[] { getAuthMethodType().name(), req.getRequestURI(), ex }));
/* 286 */     return String.format("{\"statusCode\" : %d }", new Object[] { ex.getStatusCode().getId() });
/*     */   }
/*     */   
/*     */   @ExceptionHandler({FidoUafParsingException.class})
/*     */   public String handleFidoUafParsingException(HttpServletRequest req, HttpServletResponse resp, FidoUafParsingException ex) {
/* 291 */     LOG.error(String.format("## ERROR MSG [AuthMethodType=%s, RequestURI=%s] :: %s", new Object[] { getAuthMethodType().name(), req.getRequestURI(), ex }));
/* 292 */     return String.format("{\"statusCode\" : %d }", new Object[] { StatusCodes.CODE_1498.getId() });
/*     */   }
/*     */   
/*     */   @ExceptionHandler({ReturnCodeException.class})
/*     */   public String handleReturnCodeException(HttpServletRequest req, HttpServletResponse resp, ReturnCodeException ex) {
/* 297 */     ReturnCodes returnCode = ex.getReturnCode();
/*     */     
/* 299 */     if (req.getRequestURI().matches("^.*.(Get|Send/(Reg|Auth))$")) {
/* 300 */       LOG.error(String.format("## ERROR MSG [AuthMethodType=%s, RequestURI=%s] :: %s", new Object[] { getAuthMethodType().name(), req.getRequestURI(), ex }));
/* 301 */       return String.format("{\"statusCode\" : %d }", new Object[] { returnCode.getStatusCodes().getId() });
/* 302 */     }  if (req.getRequestURI().matches("^.*.(IssueCode)$")) {
/* 303 */       LOG.error(String.format("## ERROR MSG [AuthMethodType=%s, RequestURI=%s] :: %s", new Object[] { getAuthMethodType().name(), req.getRequestURI(), ex }));
/*     */     } else {
/* 305 */       LOG.error(String.format("## ERROR MSG [AuthMethodType=%s, RequestURI=%s] :: %s", new Object[] { getAuthMethodType().name(), req.getRequestURI(), ex }));
/*     */     } 
/*     */     
/* 308 */     return String.format("{\"returnCode\" : \"%s\" }", new Object[] { returnCode.getCode() });
/*     */   }
/*     */ 
/*     */   
/*     */   @ExceptionHandler({Throwable.class})
/*     */   public String handleException(HttpServletRequest req, HttpServletResponse resp, Throwable ex) {
/* 314 */     if (req.getRequestURI().matches("^.*.(Get|Send/(Reg|Auth))$")) {
/* 315 */       LOG.error(String.format("## ERROR MSG [AuthMethodType=%s, RequestURI=%s] :: %s", new Object[] { getAuthMethodType().name(), req.getRequestURI(), ex }));
/* 316 */       return String.format("{\"statusCode\" : %d }", new Object[] { ReturnCodes.INTERNAL_SERVER_ERROR.getStatusCodes().getId() });
/* 317 */     }  if (req.getRequestURI().matches("^.*.(IssueCode)$")) {
/* 318 */       LOG.error(String.format("## ERROR MSG [AuthMethodType=%s, RequestURI=%s] :: %s", new Object[] { getAuthMethodType().name(), req.getRequestURI(), ex }));
/*     */     } else {
/* 320 */       LOG.error(String.format("## ERROR MSG [AuthMethodType=%s, RequestURI=%s] :: %s", new Object[] { getAuthMethodType().name(), req.getRequestURI(), ex }));
/*     */     } 
/*     */     
/* 323 */     return String.format("{\"returnCode\" : \"%s\" }", new Object[] { ReturnCodes.INTERNAL_SERVER_ERROR.getCode() });
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\rp\controller\biotp\BiotpAuthenticationController.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */