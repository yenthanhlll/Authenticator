/*      */ package WEB-INF.classes.com.dreammirae.mmth.external.webapi;
/*      */ 
/*      */ import com.dreammirae.mmth.authentication.AuthMethodTypes;
/*      */ import com.dreammirae.mmth.authentication.ReturnCodes;
/*      */ import com.dreammirae.mmth.authentication.service.IVerifyOTPService;
/*      */ import com.dreammirae.mmth.authentication.service.IssueCodeService;
/*      */ import com.dreammirae.mmth.authentication.service.biotp.BiotpVerifyOTPService;
/*      */ import com.dreammirae.mmth.db.dao.SystemSettingsDao;
/*      */ import com.dreammirae.mmth.exception.ReturnCodeException;
/*      */ import com.dreammirae.mmth.external.ExternalWebApiTypes;
/*      */ import com.dreammirae.mmth.external.bean.WebApiRequestParam;
/*      */ import com.dreammirae.mmth.external.bean.WebApiResponse;
/*      */ import com.dreammirae.mmth.external.service.ExternalAuthenticationService;
/*      */ import com.dreammirae.mmth.parser.json.GsonUtils;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import org.slf4j.Logger;
/*      */ import org.slf4j.LoggerFactory;
/*      */ import org.springframework.beans.factory.annotation.Autowired;
/*      */ import org.springframework.web.bind.annotation.CrossOrigin;
/*      */ import org.springframework.web.bind.annotation.PathVariable;
/*      */ import org.springframework.web.bind.annotation.RequestBody;
/*      */ import org.springframework.web.bind.annotation.RequestMapping;
/*      */ import org.springframework.web.bind.annotation.RestController;
/*      */ 
/*      */ 
/*      */ 
/*      */ @CrossOrigin(origins = {"*"})
/*      */ @RestController
/*      */ @RequestMapping(value = {"/rpserver/webapi"}, consumes = {"application/rp+json; charset=utf-8"}, produces = {"application/rp+json; charset=utf-8"})
/*      */ public class WepApiAuthenticationController
/*      */ {
/*   32 */   protected static final Logger LOG = LoggerFactory.getLogger(com.dreammirae.mmth.external.webapi.WepApiAuthenticationController.class);
/*      */ 
/*      */   
/*      */   protected static final String LOG_FORMAT_REQ_MSG = "## WEBAPI-RECEIVED MSG [RequestURI=%s] :: %s";
/*      */ 
/*      */   
/*      */   protected static final String LOG_FORMAT_RESP_MSG = "## WEBAPI-SEND MSG [RequestURI=%s, LapTime=%,d ms] :: %s";
/*      */ 
/*      */   
/*      */   protected static final String LOG_FORMAT_ERROR_MSG = "## WEBAPI-ERR MSG [RequestURI=%s, RetrunCode=%s] :: %s";
/*      */ 
/*      */   
/*      */   protected static final String LOG_FORMAT_REQ_RAW_MSG = "$$ WEBAPI-RECEIVED REQUEST MSG PARSED :: ";
/*      */ 
/*      */   
/*      */   protected static final String LOG_FORMAT_RESP_RAW_MSG = "$$ WEBAPI-RECEIVED RESPONSE RAW MSG :: ";
/*      */ 
/*      */   
/*      */   @Autowired
/*      */   private IssueCodeService issueCodeService;
/*      */ 
/*      */ 
/*      */   
/*      */   @RequestMapping({"/{authType}/IssueCode"})
/*      */   public String reqGenIssueCode(@PathVariable("authType") AuthMethodTypes authType, @RequestBody String payload, HttpServletRequest httpRequest) {
/*   57 */     String json = null;
/*   58 */     long startMillis = 0L;
/*      */     
/*   60 */     if (LOG.isInfoEnabled()) {
/*   61 */       LOG.info(String.format("## WEBAPI-RECEIVED MSG [RequestURI=%s] :: %s", new Object[] { httpRequest.getRequestURI(), payload }));
/*   62 */       startMillis = System.currentTimeMillis();
/*      */     } 
/*      */     
/*   65 */     ExternalWebApiTypes externalType = getExternalType();
/*      */     
/*   67 */     WebApiResponse response = externalType.createResponseInstance();
/*      */ 
/*      */     
/*      */     try {
/*   71 */       WebApiRequestParam param = externalType.parseWebApiRequestParam(payload);
/*      */       
/*   73 */       if (param != null) {
/*      */         
/*   75 */         if (LOG.isInfoEnabled()) {
/*   76 */           LOG.info("$$ WEBAPI-RECEIVED REQUEST MSG PARSED :: " + param.toString());
/*      */         }
/*      */         
/*   79 */         externalType.validateIssueCodeParam(param);
/*   80 */         getIssueCodeService().generateIssueCode(authType, response, param);
/*      */       } else {
/*   82 */         response.setReturnCode(ReturnCodes.INVALID_PARAMS);
/*      */       } 
/*      */       
/*   85 */       json = GsonUtils.gson().toJson(response);
/*      */     }
/*   87 */     catch (Exception e) {
/*      */       
/*   89 */       if (e instanceof ReturnCodeException) {
/*   90 */         response.setReturnCode(((ReturnCodeException)e).getReturnCode());
/*   91 */       } else if (e.getCause() instanceof ReturnCodeException) {
/*   92 */         response.setReturnCode(((ReturnCodeException)e.getCause()).getReturnCode());
/*      */       } else {
/*   94 */         response.setReturnCode(ReturnCodes.INTERNAL_SERVER_ERROR);
/*      */       } 
/*      */       
/*   97 */       json = GsonUtils.gson().toJson(response);
/*      */       
/*   99 */       LOG.error(String.format("## WEBAPI-ERR MSG [RequestURI=%s, RetrunCode=%s] :: %s", new Object[] { httpRequest.getRequestURI(), response.getReturnCode().getCode(), e.getMessage() }));
/*      */     } 
/*      */     
/*  102 */     if (LOG.isInfoEnabled()) {
/*  103 */       LOG.info("$$ WEBAPI-RECEIVED RESPONSE RAW MSG :: " + response.toString());
/*  104 */       LOG.info(String.format("## WEBAPI-SEND MSG [RequestURI=%s, LapTime=%,d ms] :: %s", new Object[] { httpRequest.getRequestURI(), Long.valueOf(System.currentTimeMillis() - startMillis), json }));
/*      */     } 
/*      */     
/*  107 */     return json;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @RequestMapping({"/{authType}/CheckIssueCode"})
/*      */   public String reqCheckIssueCode(@PathVariable("authType") AuthMethodTypes authType, @RequestBody String payload, HttpServletRequest httpRequest) {
/*  115 */     String json = null;
/*  116 */     long startMillis = 0L;
/*      */     
/*  118 */     if (LOG.isInfoEnabled()) {
/*  119 */       LOG.info(String.format("## WEBAPI-RECEIVED MSG [RequestURI=%s] :: %s", new Object[] { httpRequest.getRequestURI(), payload }));
/*  120 */       startMillis = System.currentTimeMillis();
/*      */     } 
/*      */     
/*  123 */     ExternalWebApiTypes externalType = getExternalType();
/*      */     
/*  125 */     WebApiResponse response = externalType.createResponseInstance();
/*      */ 
/*      */     
/*      */     try {
/*  129 */       WebApiRequestParam param = externalType.parseWebApiRequestParam(payload);
/*      */       
/*  131 */       if (param != null) {
/*  132 */         if (LOG.isInfoEnabled()) {
/*  133 */           LOG.info("$$ WEBAPI-RECEIVED REQUEST MSG PARSED :: " + param.toString());
/*      */         }
/*  135 */         externalType.validateUsernameParam(param);
/*  136 */         ExternalAuthenticationService service = getAuthenticationMethodService(authType);
/*  137 */         service.getIssueCode(response, param);
/*      */       } else {
/*  139 */         response.setReturnCode(ReturnCodes.INVALID_PARAMS);
/*      */       } 
/*      */       
/*  142 */       json = GsonUtils.gson().toJson(response);
/*      */     }
/*  144 */     catch (Exception e) {
/*      */       
/*  146 */       if (e instanceof ReturnCodeException) {
/*  147 */         response.setReturnCode(((ReturnCodeException)e).getReturnCode());
/*  148 */       } else if (e.getCause() instanceof ReturnCodeException) {
/*  149 */         response.setReturnCode(((ReturnCodeException)e.getCause()).getReturnCode());
/*      */       } else {
/*  151 */         response.setReturnCode(ReturnCodes.INTERNAL_SERVER_ERROR);
/*      */       } 
/*      */       
/*  154 */       json = GsonUtils.gson().toJson(response);
/*      */       
/*  156 */       LOG.error(String.format("## WEBAPI-ERR MSG [RequestURI=%s, RetrunCode=%s] :: %s", new Object[] { httpRequest.getRequestURI(), response.getReturnCode().getCode(), e.getMessage() }));
/*      */     } 
/*      */     
/*  159 */     if (LOG.isInfoEnabled()) {
/*  160 */       LOG.info("$$ WEBAPI-RECEIVED RESPONSE RAW MSG :: " + response.toString());
/*  161 */       LOG.info(String.format("## WEBAPI-SEND MSG [RequestURI=%s, LapTime=%,d ms] :: %s", new Object[] { httpRequest.getRequestURI(), Long.valueOf(System.currentTimeMillis() - startMillis), json }));
/*      */     } 
/*      */     
/*  164 */     return json;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @RequestMapping({"/{authType}/VerifyOTP"})
/*      */   public String reqDoVerifyingOTP(@PathVariable("authType") AuthMethodTypes authType, @RequestBody String payload, HttpServletRequest httpRequest) {
/*  172 */     String json = null;
/*  173 */     long startMillis = 0L;
/*      */     
/*  175 */     if (LOG.isInfoEnabled()) {
/*  176 */       LOG.info(String.format("## WEBAPI-RECEIVED MSG [RequestURI=%s] :: %s", new Object[] { httpRequest.getRequestURI(), payload }));
/*  177 */       startMillis = System.currentTimeMillis();
/*      */     } 
/*      */ 
/*      */     
/*  181 */     ExternalWebApiTypes externalType = getExternalType();
/*      */     
/*  183 */     WebApiResponse response = externalType.createResponseInstance();
/*      */ 
/*      */     
/*      */     try {
/*  187 */       WebApiRequestParam param = externalType.parseWebApiRequestParam(payload);
/*      */       
/*  189 */       if (param != null) {
/*  190 */         if (LOG.isInfoEnabled()) {
/*  191 */           LOG.info("$$ WEBAPI-RECEIVED REQUEST MSG PARSED :: " + param.toString());
/*      */         }
/*  193 */         externalType.validateVerifyOTPParam(param);
/*  194 */         IVerifyOTPService service = getVerifyOTPService(authType);
/*  195 */         service.verifyingOTP(response, param.getOtp(), param.getTid(), param.getUserName(), param.getMacAddress(), param.getIp());
/*      */       } else {
/*  197 */         response.setReturnCode(ReturnCodes.INVALID_PARAMS);
/*      */       } 
/*      */       
/*  200 */       json = GsonUtils.gson().toJson(response);
/*      */     }
/*  202 */     catch (Exception e) {
/*      */       
/*  204 */       if (e instanceof ReturnCodeException) {
/*  205 */         response.setReturnCode(((ReturnCodeException)e).getReturnCode());
/*  206 */       } else if (e.getCause() instanceof ReturnCodeException) {
/*  207 */         response.setReturnCode(((ReturnCodeException)e.getCause()).getReturnCode());
/*      */       } else {
/*  209 */         response.setReturnCode(ReturnCodes.INTERNAL_SERVER_ERROR);
/*      */       } 
/*      */       
/*  212 */       json = GsonUtils.gson().toJson(response);
/*      */       
/*  214 */       LOG.error(String.format("## WEBAPI-ERR MSG [RequestURI=%s, RetrunCode=%s] :: %s", new Object[] { httpRequest.getRequestURI(), response.getReturnCode().getCode(), e.getMessage() }));
/*      */     } 
/*      */     
/*  217 */     if (LOG.isInfoEnabled()) {
/*  218 */       LOG.info("$$ WEBAPI-RECEIVED RESPONSE RAW MSG :: " + response.toString());
/*  219 */       LOG.info(String.format("## WEBAPI-SEND MSG [RequestURI=%s, LapTime=%,d ms] :: %s", new Object[] { httpRequest.getRequestURI(), Long.valueOf(System.currentTimeMillis() - startMillis), json }));
/*      */     } 
/*      */     
/*  222 */     return json;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   @RequestMapping({"/{authType}/VerifyEncOTP"})
/*      */   public String reqDoVerifyingEncOTP(@PathVariable("authType") AuthMethodTypes authType, @RequestBody String payload, HttpServletRequest httpRequest) {
/*  229 */     String json = null;
/*  230 */     long startMillis = 0L;
/*      */     
/*  232 */     if (LOG.isInfoEnabled()) {
/*  233 */       LOG.info(String.format("## WEBAPI-RECEIVED MSG [RequestURI=%s] :: %s", new Object[] { httpRequest.getRequestURI(), payload }));
/*  234 */       startMillis = System.currentTimeMillis();
/*      */     } 
/*      */ 
/*      */     
/*  238 */     ExternalWebApiTypes externalType = getExternalType();
/*      */     
/*  240 */     WebApiResponse response = externalType.createResponseInstance();
/*      */ 
/*      */     
/*      */     try {
/*  244 */       WebApiRequestParam param = externalType.parseWebApiRequestParam(payload);
/*      */       
/*  246 */       if (param != null) {
/*  247 */         if (LOG.isInfoEnabled()) {
/*  248 */           LOG.info("$$ WEBAPI-RECEIVED REQUEST MSG PARSED :: " + param.toString());
/*      */         }
/*  250 */         externalType.validateVerifyEncOTPParam(param);
/*  251 */         IVerifyOTPService service = getVerifyOTPService(authType);
/*  252 */         service.verifyingEncOTP(response, param.getOtp(), param.getTid(), param.getUserName());
/*      */       } else {
/*  254 */         response.setReturnCode(ReturnCodes.INVALID_PARAMS);
/*      */       } 
/*      */       
/*  257 */       json = GsonUtils.gson().toJson(response);
/*      */     }
/*  259 */     catch (Exception e) {
/*      */       
/*  261 */       if (e instanceof ReturnCodeException) {
/*  262 */         response.setReturnCode(((ReturnCodeException)e).getReturnCode());
/*  263 */       } else if (e.getCause() instanceof ReturnCodeException) {
/*  264 */         response.setReturnCode(((ReturnCodeException)e.getCause()).getReturnCode());
/*      */       } else {
/*  266 */         response.setReturnCode(ReturnCodes.INTERNAL_SERVER_ERROR);
/*      */       } 
/*      */       
/*  269 */       json = GsonUtils.gson().toJson(response);
/*      */       
/*  271 */       LOG.error(String.format("## WEBAPI-ERR MSG [RequestURI=%s, RetrunCode=%s] :: %s", new Object[] { httpRequest.getRequestURI(), response.getReturnCode().getCode(), e.getMessage() }));
/*      */     } 
/*      */     
/*  274 */     if (LOG.isInfoEnabled()) {
/*  275 */       LOG.info("$$ WEBAPI-RECEIVED RESPONSE RAW MSG :: " + response.toString());
/*  276 */       LOG.info(String.format("## WEBAPI-SEND MSG [RequestURI=%s, LapTime=%,d ms] :: %s", new Object[] { httpRequest.getRequestURI(), Long.valueOf(System.currentTimeMillis() - startMillis), json }));
/*      */     } 
/*      */     
/*  279 */     return json;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @RequestMapping({"/{authType}/VerifyTranOTP"})
/*      */   public String reqDoVerifyingTranOTP(@PathVariable("authType") AuthMethodTypes authType, @RequestBody String payload, HttpServletRequest httpRequest) {
/*  287 */     String json = null;
/*  288 */     long startMillis = 0L;
/*      */     
/*  290 */     if (LOG.isInfoEnabled()) {
/*  291 */       LOG.info(String.format("## WEBAPI-RECEIVED MSG [RequestURI=%s] :: %s", new Object[] { httpRequest.getRequestURI(), payload }));
/*  292 */       startMillis = System.currentTimeMillis();
/*      */     } 
/*      */     
/*  295 */     ExternalWebApiTypes externalType = getExternalType();
/*      */     
/*  297 */     WebApiResponse response = externalType.createResponseInstance();
/*      */ 
/*      */     
/*      */     try {
/*  301 */       WebApiRequestParam param = externalType.parseWebApiRequestParam(payload);
/*      */       
/*  303 */       if (param != null) {
/*  304 */         if (LOG.isInfoEnabled()) {
/*  305 */           LOG.info("$$ WEBAPI-RECEIVED REQUEST MSG PARSED :: " + param.toString());
/*      */         }
/*  307 */         externalType.validateVerifyTranOTPParam(param);
/*  308 */         IVerifyOTPService service = getVerifyOTPService(authType);
/*  309 */         service.verifyingTranOTP(response, param.getOtp(), param.getTranInfo(), param.getTid(), param.getUserName());
/*      */       } else {
/*  311 */         response.setReturnCode(ReturnCodes.INVALID_PARAMS);
/*      */       } 
/*      */       
/*  314 */       json = GsonUtils.gson().toJson(response);
/*      */     }
/*  316 */     catch (Exception e) {
/*      */       
/*  318 */       if (e instanceof ReturnCodeException) {
/*  319 */         response.setReturnCode(((ReturnCodeException)e).getReturnCode());
/*  320 */       } else if (e.getCause() instanceof ReturnCodeException) {
/*  321 */         response.setReturnCode(((ReturnCodeException)e.getCause()).getReturnCode());
/*      */       } else {
/*  323 */         response.setReturnCode(ReturnCodes.INTERNAL_SERVER_ERROR);
/*      */       } 
/*      */       
/*  326 */       json = GsonUtils.gson().toJson(response);
/*      */       
/*  328 */       LOG.error(String.format("## WEBAPI-ERR MSG [RequestURI=%s, RetrunCode=%s] :: %s", new Object[] { httpRequest.getRequestURI(), response.getReturnCode().getCode(), e.getMessage() }));
/*      */     } 
/*      */     
/*  331 */     if (LOG.isInfoEnabled()) {
/*  332 */       LOG.info("$$ WEBAPI-RECEIVED RESPONSE RAW MSG :: " + response.toString());
/*  333 */       LOG.info(String.format("## WEBAPI-SEND MSG [RequestURI=%s, LapTime=%,d ms] :: %s", new Object[] { httpRequest.getRequestURI(), Long.valueOf(System.currentTimeMillis() - startMillis), json }));
/*      */     } 
/*      */     
/*  336 */     return json;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   @RequestMapping({"/{authType}/VerifyTranEncOTP"})
/*      */   public String reqDoVerifyingTranEncOTP(@PathVariable("authType") AuthMethodTypes authType, @RequestBody String payload, HttpServletRequest httpRequest) {
/*  343 */     String json = null;
/*  344 */     long startMillis = 0L;
/*      */     
/*  346 */     if (LOG.isInfoEnabled()) {
/*  347 */       LOG.info(String.format("## WEBAPI-RECEIVED MSG [RequestURI=%s] :: %s", new Object[] { httpRequest.getRequestURI(), payload }));
/*  348 */       startMillis = System.currentTimeMillis();
/*      */     } 
/*      */     
/*  351 */     ExternalWebApiTypes externalType = getExternalType();
/*      */     
/*  353 */     WebApiResponse response = externalType.createResponseInstance();
/*      */ 
/*      */     
/*      */     try {
/*  357 */       WebApiRequestParam param = externalType.parseWebApiRequestParam(payload);
/*      */       
/*  359 */       if (param != null) {
/*  360 */         if (LOG.isInfoEnabled()) {
/*  361 */           LOG.info("$$ WEBAPI-RECEIVED REQUEST MSG PARSED :: " + param.toString());
/*      */         }
/*  363 */         externalType.validateVerifyTranEncOTPParam(param);
/*  364 */         IVerifyOTPService service = getVerifyOTPService(authType);
/*  365 */         service.verifyingTranEncOTP(response, param.getOtp(), param.getTranInfo(), param.getTid(), param.getUserName());
/*      */       } else {
/*  367 */         response.setReturnCode(ReturnCodes.INVALID_PARAMS);
/*      */       } 
/*      */       
/*  370 */       json = GsonUtils.gson().toJson(response);
/*      */     }
/*  372 */     catch (Exception e) {
/*      */       
/*  374 */       if (e instanceof ReturnCodeException) {
/*  375 */         response.setReturnCode(((ReturnCodeException)e).getReturnCode());
/*  376 */       } else if (e.getCause() instanceof ReturnCodeException) {
/*  377 */         response.setReturnCode(((ReturnCodeException)e.getCause()).getReturnCode());
/*      */       } else {
/*  379 */         response.setReturnCode(ReturnCodes.INTERNAL_SERVER_ERROR);
/*      */       } 
/*      */       
/*  382 */       json = GsonUtils.gson().toJson(response);
/*      */       
/*  384 */       LOG.error(String.format("## WEBAPI-ERR MSG [RequestURI=%s, RetrunCode=%s] :: %s", new Object[] { httpRequest.getRequestURI(), response.getReturnCode().getCode(), e.getMessage() }));
/*      */     } 
/*      */     
/*  387 */     if (LOG.isInfoEnabled()) {
/*  388 */       LOG.info("$$ WEBAPI-RECEIVED RESPONSE RAW MSG :: " + response.toString());
/*  389 */       LOG.info(String.format("## WEBAPI-SEND MSG [RequestURI=%s, LapTime=%,d ms] :: %s", new Object[] { httpRequest.getRequestURI(), Long.valueOf(System.currentTimeMillis() - startMillis), json }));
/*      */     } 
/*      */     
/*  392 */     return json;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @RequestMapping({"/{authType}/ResetAuthFail"})
/*      */   public String reqResetAuthFail(@PathVariable("authType") AuthMethodTypes authType, @RequestBody String payload, HttpServletRequest httpRequest) {
/*  404 */     String json = null;
/*  405 */     long startMillis = 0L;
/*      */     
/*  407 */     if (LOG.isInfoEnabled()) {
/*  408 */       LOG.info(String.format("## WEBAPI-RECEIVED MSG [RequestURI=%s] :: %s", new Object[] { httpRequest.getRequestURI(), payload }));
/*  409 */       startMillis = System.currentTimeMillis();
/*      */     } 
/*      */     
/*  412 */     ExternalWebApiTypes externalType = getExternalType();
/*      */     
/*  414 */     WebApiResponse response = externalType.createResponseInstance();
/*      */ 
/*      */     
/*      */     try {
/*  418 */       WebApiRequestParam param = externalType.parseWebApiRequestParam(payload);
/*      */       
/*  420 */       if (param != null) {
/*      */         
/*  422 */         if (LOG.isInfoEnabled()) {
/*  423 */           LOG.info("$$ WEBAPI-RECEIVED REQUEST MSG PARSED :: " + param.toString());
/*      */         }
/*      */         
/*  426 */         externalType.validateServiceApiParam(param);
/*  427 */         ExternalAuthenticationService service = getAuthenticationMethodService(authType);
/*  428 */         service.doResetAuthFail(response, param);
/*      */       } else {
/*  430 */         response.setReturnCode(ReturnCodes.INVALID_PARAMS);
/*      */       } 
/*      */       
/*  433 */       json = GsonUtils.gson().toJson(response);
/*      */     }
/*  435 */     catch (Exception e) {
/*      */       
/*  437 */       if (e instanceof ReturnCodeException) {
/*  438 */         response.setReturnCode(((ReturnCodeException)e).getReturnCode());
/*  439 */       } else if (e.getCause() instanceof ReturnCodeException) {
/*  440 */         response.setReturnCode(((ReturnCodeException)e.getCause()).getReturnCode());
/*      */       } else {
/*  442 */         response.setReturnCode(ReturnCodes.INTERNAL_SERVER_ERROR);
/*      */       } 
/*      */       
/*  445 */       json = GsonUtils.gson().toJson(response);
/*      */       
/*  447 */       LOG.error(String.format("## WEBAPI-ERR MSG [RequestURI=%s, RetrunCode=%s] :: %s", new Object[] { httpRequest.getRequestURI(), response.getReturnCode().getCode(), e.getMessage() }));
/*      */     } 
/*      */     
/*  450 */     if (LOG.isInfoEnabled()) {
/*  451 */       LOG.info("$$ WEBAPI-RECEIVED RESPONSE RAW MSG :: " + response.toString());
/*  452 */       LOG.info(String.format("## WEBAPI-SEND MSG [RequestURI=%s, LapTime=%,d ms] :: %s", new Object[] { httpRequest.getRequestURI(), Long.valueOf(System.currentTimeMillis() - startMillis), json }));
/*      */     } 
/*      */     
/*  455 */     return json;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @RequestMapping({"/{authType}/ForceDereg"})
/*      */   public String reqDoForceDereg(@PathVariable("authType") AuthMethodTypes authType, @RequestBody String payload, HttpServletRequest httpRequest) {
/*  469 */     String json = null;
/*  470 */     long startMillis = 0L;
/*      */     
/*  472 */     if (LOG.isInfoEnabled()) {
/*  473 */       LOG.info(String.format("## WEBAPI-RECEIVED MSG [RequestURI=%s] :: %s", new Object[] { httpRequest.getRequestURI(), payload }));
/*  474 */       startMillis = System.currentTimeMillis();
/*      */     } 
/*      */ 
/*      */     
/*  478 */     ExternalWebApiTypes externalType = getExternalType();
/*      */     
/*  480 */     WebApiResponse response = externalType.createResponseInstance();
/*      */ 
/*      */     
/*      */     try {
/*  484 */       WebApiRequestParam param = externalType.parseWebApiRequestParam(payload);
/*      */       
/*  486 */       if (param != null) {
/*      */         
/*  488 */         if (LOG.isInfoEnabled()) {
/*  489 */           LOG.info("$$ WEBAPI-RECEIVED REQUEST MSG PARSED :: " + param.toString());
/*      */         }
/*      */         
/*  492 */         externalType.validateServiceApiParam(param);
/*  493 */         ExternalAuthenticationService service = getAuthenticationMethodService(authType);
/*  494 */         service.doForceDereg(response, param);
/*      */       } else {
/*  496 */         response.setReturnCode(ReturnCodes.INVALID_PARAMS);
/*      */       } 
/*      */       
/*  499 */       json = GsonUtils.gson().toJson(response);
/*      */     }
/*  501 */     catch (Exception e) {
/*      */       
/*  503 */       if (e instanceof ReturnCodeException) {
/*  504 */         response.setReturnCode(((ReturnCodeException)e).getReturnCode());
/*  505 */       } else if (e.getCause() instanceof ReturnCodeException) {
/*  506 */         response.setReturnCode(((ReturnCodeException)e.getCause()).getReturnCode());
/*      */       } else {
/*  508 */         response.setReturnCode(ReturnCodes.INTERNAL_SERVER_ERROR);
/*      */       } 
/*      */       
/*  511 */       json = GsonUtils.gson().toJson(response);
/*      */       
/*  513 */       LOG.error(String.format("## WEBAPI-ERR MSG [RequestURI=%s, RetrunCode=%s] :: %s", new Object[] { httpRequest.getRequestURI(), response.getReturnCode().getCode(), e.getMessage() }));
/*      */     } 
/*      */     
/*  516 */     if (LOG.isInfoEnabled()) {
/*  517 */       LOG.info("$$ WEBAPI-RECEIVED RESPONSE RAW MSG :: " + response.toString());
/*  518 */       LOG.info(String.format("## WEBAPI-SEND MSG [RequestURI=%s, LapTime=%,d ms] :: %s", new Object[] { httpRequest.getRequestURI(), Long.valueOf(System.currentTimeMillis() - startMillis), json }));
/*      */     } 
/*      */     
/*  521 */     return json;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @RequestMapping({"/{authType}/UserStatus"})
/*      */   public String reqGetUserStatus(@PathVariable("authType") AuthMethodTypes authType, @RequestBody String payload, HttpServletRequest httpRequest) {
/*  534 */     String json = null;
/*  535 */     long startMillis = 0L;
/*      */     
/*  537 */     if (LOG.isInfoEnabled()) {
/*  538 */       LOG.info(String.format("## WEBAPI-RECEIVED MSG [RequestURI=%s] :: %s", new Object[] { httpRequest.getRequestURI(), payload }));
/*  539 */       startMillis = System.currentTimeMillis();
/*      */     } 
/*      */ 
/*      */     
/*  543 */     ExternalWebApiTypes externalType = getExternalType();
/*      */     
/*  545 */     WebApiResponse response = externalType.createResponseInstance();
/*      */ 
/*      */     
/*      */     try {
/*  549 */       WebApiRequestParam param = externalType.parseWebApiRequestParam(payload);
/*  550 */       if (param != null) {
/*      */         
/*  552 */         if (LOG.isInfoEnabled()) {
/*  553 */           LOG.info("$$ WEBAPI-RECEIVED REQUEST MSG PARSED :: " + param.toString());
/*      */         }
/*      */         
/*  556 */         externalType.validateUsernameParam(param);
/*  557 */         ExternalAuthenticationService service = getAuthenticationMethodService(authType);
/*  558 */         service.getUserStatus(response, param.getUserName());
/*      */       } else {
/*  560 */         response.setReturnCode(ReturnCodes.INVALID_PARAMS);
/*      */       } 
/*      */       
/*  563 */       json = GsonUtils.gson().toJson(response);
/*      */     }
/*  565 */     catch (Exception e) {
/*      */       
/*  567 */       if (e instanceof ReturnCodeException) {
/*  568 */         response.setReturnCode(((ReturnCodeException)e).getReturnCode());
/*  569 */       } else if (e.getCause() instanceof ReturnCodeException) {
/*  570 */         response.setReturnCode(((ReturnCodeException)e.getCause()).getReturnCode());
/*      */       } else {
/*  572 */         response.setReturnCode(ReturnCodes.INTERNAL_SERVER_ERROR);
/*      */       } 
/*      */       
/*  575 */       json = GsonUtils.gson().toJson(response);
/*      */       
/*  577 */       LOG.error(String.format("## WEBAPI-ERR MSG [RequestURI=%s, RetrunCode=%s] :: %s", new Object[] { httpRequest.getRequestURI(), response.getReturnCode().getCode(), e.getMessage() }));
/*      */     } 
/*      */     
/*  580 */     if (LOG.isInfoEnabled()) {
/*  581 */       LOG.info("$$ WEBAPI-RECEIVED RESPONSE RAW MSG :: " + response.toString());
/*  582 */       LOG.info(String.format("## WEBAPI-SEND MSG [RequestURI=%s, LapTime=%,d ms] :: %s", new Object[] { httpRequest.getRequestURI(), Long.valueOf(System.currentTimeMillis() - startMillis), json }));
/*      */     } 
/*      */     
/*  585 */     return json;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @RequestMapping({"/{authType}/OTPStatus"})
/*      */   public String reqOTPStatus(@PathVariable("authType") AuthMethodTypes authType, HttpServletRequest httpRequest) {
/*  597 */     String json = null;
/*  598 */     long startMillis = 0L;
/*      */     
/*  600 */     if (LOG.isInfoEnabled()) {
/*  601 */       LOG.info(String.format("## WEBAPI-RECEIVED MSG [RequestURI=%s] :: %s", new Object[] { httpRequest.getRequestURI(), "" }));
/*  602 */       startMillis = System.currentTimeMillis();
/*      */     } 
/*      */ 
/*      */     
/*  606 */     WebApiResponse response = getExternalType().createResponseInstance();
/*      */     
/*      */     try {
/*  609 */       ExternalAuthenticationService service = getAuthenticationMethodService(authType);
/*  610 */       service.getTokenStatus(response);
/*      */       
/*  612 */       json = GsonUtils.gson().toJson(response);
/*      */     }
/*  614 */     catch (Exception e) {
/*      */       
/*  616 */       if (e instanceof ReturnCodeException) {
/*  617 */         response.setReturnCode(((ReturnCodeException)e).getReturnCode());
/*  618 */       } else if (e.getCause() instanceof ReturnCodeException) {
/*  619 */         response.setReturnCode(((ReturnCodeException)e.getCause()).getReturnCode());
/*      */       } else {
/*  621 */         response.setReturnCode(ReturnCodes.INTERNAL_SERVER_ERROR);
/*      */       } 
/*      */       
/*  624 */       json = GsonUtils.gson().toJson(response);
/*      */       
/*  626 */       LOG.error(String.format("## WEBAPI-ERR MSG [RequestURI=%s, RetrunCode=%s] :: %s", new Object[] { httpRequest.getRequestURI(), response.getReturnCode().getCode(), e.getMessage() }));
/*      */     } 
/*      */     
/*  629 */     if (LOG.isInfoEnabled()) {
/*  630 */       LOG.info(String.format("## WEBAPI-SEND MSG [RequestURI=%s, LapTime=%,d ms] :: %s", new Object[] { httpRequest.getRequestURI(), Long.valueOf(System.currentTimeMillis() - startMillis), json }));
/*      */     }
/*      */     
/*  633 */     return json;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   @RequestMapping({"/{authType}/CheckAuthResult"})
/*      */   public String reqCheckAuthResult(@PathVariable("authType") AuthMethodTypes authType, @RequestBody String payload, HttpServletRequest httpRequest) {
/*  640 */     String json = null;
/*  641 */     long startMillis = 0L;
/*      */     
/*  643 */     if (LOG.isInfoEnabled()) {
/*  644 */       LOG.info(String.format("## WEBAPI-RECEIVED MSG [RequestURI=%s] :: %s", new Object[] { httpRequest.getRequestURI(), payload }));
/*  645 */       startMillis = System.currentTimeMillis();
/*      */     } 
/*      */ 
/*      */     
/*  649 */     ExternalWebApiTypes externalType = getExternalType();
/*      */     
/*  651 */     WebApiResponse response = externalType.createResponseInstance();
/*      */ 
/*      */     
/*      */     try {
/*  655 */       WebApiRequestParam param = externalType.parseWebApiRequestParam(payload);
/*  656 */       if (param != null) {
/*  657 */         if (LOG.isInfoEnabled()) {
/*  658 */           LOG.info("$$ WEBAPI-RECEIVED REQUEST MSG PARSED :: " + param.toString());
/*      */         }
/*  660 */         externalType.validateTidParam(param);
/*  661 */         ExternalAuthenticationService service = getAuthenticationMethodService(authType);
/*  662 */         service.checkAuthenticationResult(response, param);
/*      */       } else {
/*  664 */         response.setReturnCode(ReturnCodes.INVALID_PARAMS);
/*      */       } 
/*      */       
/*  667 */       json = GsonUtils.gson().toJson(response);
/*      */     }
/*  669 */     catch (Exception e) {
/*      */       
/*  671 */       if (e instanceof ReturnCodeException) {
/*  672 */         response.setReturnCode(((ReturnCodeException)e).getReturnCode());
/*  673 */       } else if (e.getCause() instanceof ReturnCodeException) {
/*  674 */         response.setReturnCode(((ReturnCodeException)e.getCause()).getReturnCode());
/*      */       } else {
/*  676 */         response.setReturnCode(ReturnCodes.INTERNAL_SERVER_ERROR);
/*      */       } 
/*      */       
/*  679 */       json = GsonUtils.gson().toJson(response);
/*      */       
/*  681 */       LOG.error(String.format("## WEBAPI-ERR MSG [RequestURI=%s, RetrunCode=%s] :: %s", new Object[] { httpRequest.getRequestURI(), response.getReturnCode().getCode(), e.getMessage() }));
/*      */     } 
/*      */     
/*  684 */     if (LOG.isInfoEnabled()) {
/*  685 */       LOG.info("$$ WEBAPI-RECEIVED RESPONSE RAW MSG :: " + response.toString());
/*  686 */       LOG.info(String.format("## WEBAPI-SEND MSG [RequestURI=%s, LapTime=%,d ms] :: %s", new Object[] { httpRequest.getRequestURI(), Long.valueOf(System.currentTimeMillis() - startMillis), json }));
/*      */     } 
/*      */     
/*  689 */     return json;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   @RequestMapping({"/{authType}/UserLogs"})
/*      */   public String reqUserLogs(@PathVariable("authType") AuthMethodTypes authType, @RequestBody String payload, HttpServletRequest httpRequest) {
/*  696 */     String json = null;
/*  697 */     long startMillis = 0L;
/*      */     
/*  699 */     if (LOG.isInfoEnabled()) {
/*  700 */       LOG.info(String.format("## WEBAPI-RECEIVED MSG [RequestURI=%s] :: %s", new Object[] { httpRequest.getRequestURI(), payload }));
/*  701 */       startMillis = System.currentTimeMillis();
/*      */     } 
/*      */     
/*  704 */     ExternalWebApiTypes externalType = getExternalType();
/*      */     
/*  706 */     WebApiResponse response = externalType.createResponseInstance();
/*      */ 
/*      */     
/*      */     try {
/*  710 */       WebApiRequestParam param = externalType.parseWebApiRequestParam(payload);
/*      */       
/*  712 */       if (param != null) {
/*      */         
/*  714 */         if (LOG.isInfoEnabled()) {
/*  715 */           LOG.info("$$ WEBAPI-RECEIVED REQUEST MSG PARSED :: " + param.toString());
/*      */         }
/*  717 */         if (param.getLimit() == null) {
/*  718 */           param.setLimit(Integer.valueOf(100));
/*  719 */         } else if (param.getLimit().intValue() < 1 || param.getLimit().intValue() > 100) {
/*  720 */           param.setLimit(Integer.valueOf(100));
/*      */         } 
/*      */         
/*  723 */         externalType.validateUsernameParam(param);
/*      */         
/*  725 */         ExternalAuthenticationService service = getAuthenticationMethodService(authType);
/*  726 */         service.getUserLogs(response, param);
/*      */       } else {
/*  728 */         response.setReturnCode(ReturnCodes.INVALID_PARAMS);
/*      */       } 
/*      */       
/*  731 */       json = GsonUtils.gson().toJson(response);
/*      */     }
/*  733 */     catch (Exception e) {
/*      */       
/*  735 */       if (e instanceof ReturnCodeException) {
/*  736 */         response.setReturnCode(((ReturnCodeException)e).getReturnCode());
/*  737 */       } else if (e.getCause() instanceof ReturnCodeException) {
/*  738 */         response.setReturnCode(((ReturnCodeException)e.getCause()).getReturnCode());
/*      */       } else {
/*  740 */         response.setReturnCode(ReturnCodes.INTERNAL_SERVER_ERROR);
/*      */       } 
/*      */       
/*  743 */       json = GsonUtils.gson().toJson(response);
/*      */       
/*  745 */       LOG.error(String.format("## WEBAPI-ERR MSG [RequestURI=%s, RetrunCode=%s] :: %s", new Object[] { httpRequest.getRequestURI(), response.getReturnCode().getCode(), e.getMessage() }));
/*      */     } 
/*      */     
/*  748 */     if (LOG.isInfoEnabled()) {
/*  749 */       LOG.info("$$ WEBAPI-RECEIVED RESPONSE RAW MSG :: " + response.toString());
/*  750 */       LOG.info(String.format("## WEBAPI-SEND MSG [RequestURI=%s, LapTime=%,d ms] :: %s", new Object[] { httpRequest.getRequestURI(), Long.valueOf(System.currentTimeMillis() - startMillis), json }));
/*      */     } 
/*      */     
/*  753 */     return json;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @RequestMapping({"/{authType}/Lost"})
/*      */   public String reqLostOtp(@PathVariable("authType") AuthMethodTypes authType, @RequestBody String payload, HttpServletRequest httpRequest) {
/*  761 */     String json = null;
/*  762 */     long startMillis = 0L;
/*      */     
/*  764 */     if (LOG.isInfoEnabled()) {
/*  765 */       LOG.info(String.format("## WEBAPI-RECEIVED MSG [RequestURI=%s] :: %s", new Object[] { httpRequest.getRequestURI(), payload }));
/*  766 */       startMillis = System.currentTimeMillis();
/*      */     } 
/*      */     
/*  769 */     ExternalWebApiTypes externalType = getExternalType();
/*      */     
/*  771 */     WebApiResponse response = externalType.createResponseInstance();
/*      */ 
/*      */     
/*      */     try {
/*  775 */       if (AuthMethodTypes.FIDO.equals(authType)) {
/*  776 */         response.setReturnCode(ReturnCodes.BAD_REQUEST);
/*      */       } else {
/*  778 */         WebApiRequestParam param = externalType.parseWebApiRequestParam(payload);
/*      */         
/*  780 */         if (param != null) {
/*  781 */           if (LOG.isInfoEnabled()) {
/*  782 */             LOG.info("$$ WEBAPI-RECEIVED REQUEST MSG PARSED :: " + param.toString());
/*      */           }
/*  784 */           externalType.validateServiceApiParam(param);
/*  785 */           ExternalAuthenticationService service = getAuthenticationMethodService(authType);
/*  786 */           service.raiseLostOtp(response, param);
/*      */         } 
/*      */       } 
/*      */       
/*  790 */       json = GsonUtils.gson().toJson(response);
/*      */     }
/*  792 */     catch (Exception e) {
/*      */       
/*  794 */       if (e instanceof ReturnCodeException) {
/*  795 */         response.setReturnCode(((ReturnCodeException)e).getReturnCode());
/*  796 */       } else if (e.getCause() instanceof ReturnCodeException) {
/*  797 */         response.setReturnCode(((ReturnCodeException)e.getCause()).getReturnCode());
/*      */       } else {
/*  799 */         response.setReturnCode(ReturnCodes.INTERNAL_SERVER_ERROR);
/*      */       } 
/*      */       
/*  802 */       json = GsonUtils.gson().toJson(response);
/*      */       
/*  804 */       LOG.error(String.format("## WEBAPI-ERR MSG [RequestURI=%s, RetrunCode=%s] :: %s", new Object[] { httpRequest.getRequestURI(), response.getReturnCode().getCode(), e.getMessage() }));
/*      */     } 
/*      */     
/*  807 */     if (LOG.isInfoEnabled()) {
/*  808 */       LOG.info("$$ WEBAPI-RECEIVED RESPONSE RAW MSG :: " + response.toString());
/*  809 */       LOG.info(String.format("## WEBAPI-SEND MSG [RequestURI=%s, LapTime=%,d ms] :: %s", new Object[] { httpRequest.getRequestURI(), Long.valueOf(System.currentTimeMillis() - startMillis), json }));
/*      */     } 
/*      */     
/*  812 */     return json;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   @RequestMapping({"/{authType}/CheckDevice"})
/*      */   public String reqCheckDevice(@PathVariable("authType") AuthMethodTypes authType, @RequestBody String payload, HttpServletRequest httpRequest) {
/*  819 */     String json = null;
/*  820 */     long startMillis = 0L;
/*      */     
/*  822 */     if (LOG.isInfoEnabled()) {
/*  823 */       LOG.info(String.format("## WEBAPI-RECEIVED MSG [RequestURI=%s] :: %s", new Object[] { httpRequest.getRequestURI(), payload }));
/*  824 */       startMillis = System.currentTimeMillis();
/*      */     } 
/*      */     
/*  827 */     ExternalWebApiTypes externalType = getExternalType();
/*      */     
/*  829 */     WebApiResponse response = externalType.createResponseInstance();
/*      */ 
/*      */     
/*      */     try {
/*  833 */       if (AuthMethodTypes.FIDO.equals(authType)) {
/*  834 */         response.setReturnCode(ReturnCodes.BAD_REQUEST);
/*      */       } else {
/*  836 */         WebApiRequestParam param = externalType.parseWebApiRequestParam(payload);
/*      */         
/*  838 */         if (param != null) {
/*  839 */           if (LOG.isInfoEnabled()) {
/*  840 */             LOG.info("$$ WEBAPI-RECEIVED REQUEST MSG PARSED :: " + param.toString());
/*      */           }
/*  842 */           externalType.validateCheckDevice(param);
/*  843 */           ExternalAuthenticationService service = getAuthenticationMethodService(authType);
/*  844 */           service.reqForCheckDevice(response, param);
/*      */         } 
/*      */         
/*  847 */         json = GsonUtils.gson().toJson(response);
/*      */       }
/*      */     
/*  850 */     } catch (Exception e) {
/*      */       
/*  852 */       if (e instanceof ReturnCodeException) {
/*  853 */         response.setReturnCode(((ReturnCodeException)e).getReturnCode());
/*  854 */       } else if (e.getCause() instanceof ReturnCodeException) {
/*  855 */         response.setReturnCode(((ReturnCodeException)e.getCause()).getReturnCode());
/*      */       } else {
/*  857 */         response.setReturnCode(ReturnCodes.INTERNAL_SERVER_ERROR);
/*      */       } 
/*      */       
/*  860 */       json = GsonUtils.gson().toJson(response);
/*      */       
/*  862 */       LOG.error(String.format("## WEBAPI-ERR MSG [RequestURI=%s, RetrunCode=%s] :: %s", new Object[] { httpRequest.getRequestURI(), response.getReturnCode().getCode(), e.getMessage() }));
/*      */     } 
/*      */     
/*  865 */     if (LOG.isInfoEnabled()) {
/*  866 */       LOG.info("$$ WEBAPI-RECEIVED RESPONSE RAW MSG :: " + response.toString());
/*  867 */       LOG.info(String.format("## WEBAPI-SEND MSG [RequestURI=%s, LapTime=%,d ms] :: %s", new Object[] { httpRequest.getRequestURI(), Long.valueOf(System.currentTimeMillis() - startMillis), json }));
/*      */     } 
/*      */     
/*  870 */     return json;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @RequestMapping({"/{authType}/ReIssueCode"})
/*      */   public String reqDoReIssueCode(@PathVariable("authType") AuthMethodTypes authType, @RequestBody String payload, HttpServletRequest httpRequest) {
/*  878 */     String json = null;
/*  879 */     long startMillis = 0L;
/*      */     
/*  881 */     if (LOG.isInfoEnabled()) {
/*  882 */       LOG.info(String.format("## WEBAPI-RECEIVED MSG [RequestURI=%s] :: %s", new Object[] { httpRequest.getRequestURI(), payload }));
/*  883 */       startMillis = System.currentTimeMillis();
/*      */     } 
/*      */ 
/*      */     
/*  887 */     ExternalWebApiTypes externalType = getExternalType();
/*      */     
/*  889 */     WebApiResponse response = externalType.createResponseInstance();
/*      */ 
/*      */     
/*      */     try {
/*  893 */       WebApiRequestParam param = externalType.parseWebApiRequestParam(payload);
/*      */       
/*  895 */       if (param != null) {
/*  896 */         if (LOG.isInfoEnabled()) {
/*  897 */           LOG.info("$$ WEBAPI-RECEIVED REQUEST MSG PARSED :: " + param.toString());
/*      */         }
/*  899 */         externalType.validateReIssueCodeParam(param);
/*  900 */         ExternalAuthenticationService service = getAuthenticationMethodService(authType);
/*  901 */         service.reqForReIssueCode(response, param);
/*      */       } else {
/*  903 */         response.setReturnCode(ReturnCodes.INVALID_PARAMS);
/*      */       } 
/*      */       
/*  906 */       json = GsonUtils.gson().toJson(response);
/*      */     }
/*  908 */     catch (Exception e) {
/*      */       
/*  910 */       if (e instanceof ReturnCodeException) {
/*  911 */         response.setReturnCode(((ReturnCodeException)e).getReturnCode());
/*  912 */       } else if (e.getCause() instanceof ReturnCodeException) {
/*  913 */         response.setReturnCode(((ReturnCodeException)e.getCause()).getReturnCode());
/*      */       } else {
/*  915 */         response.setReturnCode(ReturnCodes.INTERNAL_SERVER_ERROR);
/*      */       } 
/*      */       
/*  918 */       json = GsonUtils.gson().toJson(response);
/*      */       
/*  920 */       LOG.error(String.format("## WEBAPI-ERR MSG [RequestURI=%s, RetrunCode=%s] :: %s", new Object[] { httpRequest.getRequestURI(), response.getReturnCode().getCode(), e.getMessage() }));
/*      */     } 
/*      */     
/*  923 */     if (LOG.isInfoEnabled()) {
/*  924 */       LOG.info("$$ WEBAPI-RECEIVED RESPONSE RAW MSG :: " + response.toString());
/*  925 */       LOG.info(String.format("## WEBAPI-SEND MSG [RequestURI=%s, LapTime=%,d ms] :: %s", new Object[] { httpRequest.getRequestURI(), Long.valueOf(System.currentTimeMillis() - startMillis), json }));
/*      */     } 
/*      */     
/*  928 */     return json;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @RequestMapping({"/{authType}/GenQRCode"})
/*      */   public String reqGenerateQRCode(@PathVariable("authType") AuthMethodTypes authType, @RequestBody String payload, HttpServletRequest httpRequest) {
/*  936 */     String json = null;
/*  937 */     long startMillis = 0L;
/*      */     
/*  939 */     if (LOG.isInfoEnabled()) {
/*  940 */       LOG.info(String.format("## WEBAPI-RECEIVED MSG [RequestURI=%s] :: %s", new Object[] { httpRequest.getRequestURI(), payload }));
/*      */       
/*  942 */       startMillis = System.currentTimeMillis();
/*      */     } 
/*      */     
/*  945 */     ExternalWebApiTypes externalType = getExternalType();
/*      */     
/*  947 */     WebApiResponse response = externalType.createResponseInstance();
/*      */ 
/*      */     
/*      */     try {
/*  951 */       WebApiRequestParam param = externalType.parseWebApiRequestParam(payload);
/*      */       
/*  953 */       if (param != null) {
/*      */         
/*  955 */         if (LOG.isInfoEnabled()) {
/*  956 */           LOG.info("$$ WEBAPI-RECEIVED REQUEST MSG PARSED :: " + param.toString());
/*      */         }
/*      */         
/*  959 */         ExternalAuthenticationService service = getAuthenticationMethodService(authType);
/*  960 */         service.reqForGenQRCode(response, param);
/*      */       } else {
/*  962 */         response.setReturnCode(ReturnCodes.INVALID_PARAMS);
/*      */       } 
/*      */       
/*  965 */       json = GsonUtils.gson().toJson(response);
/*      */     }
/*  967 */     catch (Exception e) {
/*      */       
/*  969 */       if (e instanceof ReturnCodeException) {
/*  970 */         response.setReturnCode(((ReturnCodeException)e).getReturnCode());
/*  971 */       } else if (e.getCause() instanceof ReturnCodeException) {
/*  972 */         response.setReturnCode(((ReturnCodeException)e.getCause()).getReturnCode());
/*      */       } else {
/*  974 */         response.setReturnCode(ReturnCodes.INTERNAL_SERVER_ERROR);
/*      */       } 
/*      */       
/*  977 */       json = GsonUtils.gson().toJson(response);
/*      */       
/*  979 */       LOG.error(String.format("## WEBAPI-ERR MSG [RequestURI=%s, RetrunCode=%s] :: %s", new Object[] { httpRequest.getRequestURI(), response.getReturnCode().getCode(), e.getMessage() }));
/*      */     } 
/*      */     
/*  982 */     if (LOG.isInfoEnabled()) {
/*  983 */       LOG.info("$$ WEBAPI-RECEIVED RESPONSE RAW MSG :: " + response.toString());
/*  984 */       LOG.info(String.format("## WEBAPI-SEND MSG [RequestURI=%s, LapTime=%,d ms] :: %s", new Object[] { httpRequest.getRequestURI(), Long.valueOf(System.currentTimeMillis() - startMillis), json }));
/*      */     } 
/*      */     
/*  987 */     return json;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @RequestMapping({"/{authType}/RegUser"})
/*      */   public String regUser(@PathVariable("authType") AuthMethodTypes authType, @RequestBody String payload, HttpServletRequest httpRequest) {
/*  996 */     String json = null;
/*  997 */     long startMillis = 0L;
/*      */     
/*  999 */     if (LOG.isInfoEnabled()) {
/* 1000 */       LOG.info(String.format("## WEBAPI-RECEIVED MSG [RequestURI=%s] :: %s", new Object[] { httpRequest.getRequestURI(), payload }));
/* 1001 */       startMillis = System.currentTimeMillis();
/*      */     } 
/*      */ 
/*      */     
/* 1005 */     ExternalWebApiTypes externalType = getExternalType();
/*      */     
/* 1007 */     WebApiResponse response = externalType.createResponseInstance();
/*      */ 
/*      */     
/*      */     try {
/* 1011 */       WebApiRequestParam[] param = externalType.parseWebApiRequestParams(payload);
/*      */       
/* 1013 */       if (param != null) {
/* 1014 */         if (LOG.isInfoEnabled()) {
/* 1015 */           LOG.info("$$ WEBAPI-RECEIVED REQUEST MSG PARSED :: " + param.toString());
/*      */         }
/* 1017 */         ExternalAuthenticationService service = getAuthenticationMethodService(authType);
/* 1018 */         service.regUser(response, param);
/*      */       } else {
/* 1020 */         response.setReturnCode(ReturnCodes.INVALID_PARAMS);
/*      */       } 
/*      */       
/* 1023 */       json = GsonUtils.gson().toJson(response);
/*      */     }
/* 1025 */     catch (Exception e) {
/*      */       
/* 1027 */       if (e instanceof ReturnCodeException) {
/* 1028 */         response.setReturnCode(((ReturnCodeException)e).getReturnCode());
/* 1029 */       } else if (e.getCause() instanceof ReturnCodeException) {
/* 1030 */         response.setReturnCode(((ReturnCodeException)e.getCause()).getReturnCode());
/*      */       } else {
/* 1032 */         response.setReturnCode(ReturnCodes.INTERNAL_SERVER_ERROR);
/*      */       } 
/*      */       
/* 1035 */       json = GsonUtils.gson().toJson(response);
/*      */       
/* 1037 */       LOG.error(String.format("## WEBAPI-ERR MSG [RequestURI=%s, RetrunCode=%s] :: %s", new Object[] { httpRequest.getRequestURI(), response.getReturnCode().getCode(), e.getMessage() }));
/*      */     } 
/*      */     
/* 1040 */     if (LOG.isInfoEnabled()) {
/* 1041 */       LOG.info("$$ WEBAPI-RECEIVED RESPONSE RAW MSG :: " + response.toString());
/* 1042 */       LOG.info(String.format("## WEBAPI-SEND MSG [RequestURI=%s, LapTime=%,d ms] :: %s", new Object[] { httpRequest.getRequestURI(), Long.valueOf(System.currentTimeMillis() - startMillis), json }));
/*      */     } 
/*      */     
/* 1045 */     return json;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @RequestMapping({"/{authType}/GetUserInfo"})
/*      */   public String getUserInfo(@PathVariable("authType") AuthMethodTypes authType, @RequestBody String payload, HttpServletRequest httpRequest) {
/* 1054 */     String json = null;
/* 1055 */     long startMillis = 0L;
/*      */     
/* 1057 */     if (LOG.isInfoEnabled()) {
/* 1058 */       LOG.info(String.format("## WEBAPI-RECEIVED MSG [RequestURI=%s] :: %s", new Object[] { httpRequest.getRequestURI(), payload }));
/* 1059 */       startMillis = System.currentTimeMillis();
/*      */     } 
/*      */     
/* 1062 */     ExternalWebApiTypes externalType = getExternalType();
/*      */     
/* 1064 */     WebApiResponse response = externalType.createResponseInstance();
/*      */ 
/*      */     
/*      */     try {
/* 1068 */       WebApiRequestParam param = externalType.parseWebApiRequestParam(payload);
/*      */       
/* 1070 */       if (param != null) {
/* 1071 */         if (LOG.isInfoEnabled()) {
/* 1072 */           LOG.info("$$ WEBAPI-RECEIVED REQUEST MSG PARSED :: " + param.toString());
/*      */         }
/* 1074 */         ExternalAuthenticationService service = getAuthenticationMethodService(authType);
/* 1075 */         service.getUserInfo(response, param);
/*      */       } else {
/* 1077 */         response.setReturnCode(ReturnCodes.INVALID_PARAMS);
/*      */       } 
/*      */       
/* 1080 */       json = GsonUtils.gson().toJson(response);
/*      */     }
/* 1082 */     catch (Exception e) {
/*      */       
/* 1084 */       if (e instanceof ReturnCodeException) {
/* 1085 */         response.setReturnCode(((ReturnCodeException)e).getReturnCode());
/* 1086 */       } else if (e.getCause() instanceof ReturnCodeException) {
/* 1087 */         response.setReturnCode(((ReturnCodeException)e.getCause()).getReturnCode());
/*      */       } else {
/* 1089 */         response.setReturnCode(ReturnCodes.INTERNAL_SERVER_ERROR);
/*      */       } 
/*      */       
/* 1092 */       json = GsonUtils.gson().toJson(response);
/*      */       
/* 1094 */       LOG.error(String.format("## WEBAPI-ERR MSG [RequestURI=%s, RetrunCode=%s] :: %s", new Object[] { httpRequest.getRequestURI(), response.getReturnCode().getCode(), e.getMessage() }));
/*      */     } 
/*      */     
/* 1097 */     if (LOG.isInfoEnabled()) {
/* 1098 */       LOG.info("$$ WEBAPI-RECEIVED RESPONSE RAW MSG :: " + response.toString());
/* 1099 */       LOG.info(String.format("## WEBAPI-SEND MSG [RequestURI=%s, LapTime=%,d ms] :: %s", new Object[] { httpRequest.getRequestURI(), Long.valueOf(System.currentTimeMillis() - startMillis), json }));
/*      */     } 
/*      */     
/* 1102 */     return json;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @RequestMapping({"/{authType}/UpdateMultiLoginPolicy"})
/*      */   public String updateMultiLoginPolicy(@PathVariable("authType") AuthMethodTypes authType, @RequestBody String payload, HttpServletRequest httpRequest) {
/* 1110 */     String json = null;
/* 1111 */     long startMillis = 0L;
/*      */     
/* 1113 */     if (LOG.isInfoEnabled()) {
/* 1114 */       LOG.info(String.format("## WEBAPI-RECEIVED MSG [RequestURI=%s] :: %s", new Object[] { httpRequest.getRequestURI(), payload }));
/* 1115 */       startMillis = System.currentTimeMillis();
/*      */     } 
/*      */ 
/*      */     
/* 1119 */     ExternalWebApiTypes externalType = getExternalType();
/*      */     
/* 1121 */     WebApiResponse response = externalType.createResponseInstance();
/*      */ 
/*      */     
/*      */     try {
/* 1125 */       WebApiRequestParam param = externalType.parseWebApiRequestParam(payload);
/*      */       
/* 1127 */       if (param != null) {
/* 1128 */         if (LOG.isInfoEnabled()) {
/* 1129 */           LOG.info("$$ WEBAPI-RECEIVED REQUEST MSG PARSED :: " + param.toString());
/*      */         }
/* 1131 */         ExternalAuthenticationService service = getAuthenticationMethodService(authType);
/* 1132 */         service.updateMultiLoginPolicy(response, param);
/*      */       } else {
/* 1134 */         response.setReturnCode(ReturnCodes.INVALID_PARAMS);
/*      */       } 
/*      */       
/* 1137 */       json = GsonUtils.gson().toJson(response);
/*      */     }
/* 1139 */     catch (Exception e) {
/*      */       
/* 1141 */       if (e instanceof ReturnCodeException) {
/* 1142 */         response.setReturnCode(((ReturnCodeException)e).getReturnCode());
/* 1143 */       } else if (e.getCause() instanceof ReturnCodeException) {
/* 1144 */         response.setReturnCode(((ReturnCodeException)e.getCause()).getReturnCode());
/*      */       } else {
/* 1146 */         response.setReturnCode(ReturnCodes.INTERNAL_SERVER_ERROR);
/*      */       } 
/*      */       
/* 1149 */       json = GsonUtils.gson().toJson(response);
/*      */       
/* 1151 */       LOG.error(String.format("## WEBAPI-ERR MSG [RequestURI=%s, RetrunCode=%s] :: %s", new Object[] { httpRequest.getRequestURI(), response.getReturnCode().getCode(), e.getMessage() }));
/*      */     } 
/*      */     
/* 1154 */     if (LOG.isInfoEnabled()) {
/* 1155 */       LOG.info("$$ WEBAPI-RECEIVED RESPONSE RAW MSG :: " + response.toString());
/* 1156 */       LOG.info(String.format("## WEBAPI-SEND MSG [RequestURI=%s, LapTime=%,d ms] :: %s", new Object[] { httpRequest.getRequestURI(), Long.valueOf(System.currentTimeMillis() - startMillis), json }));
/*      */     } 
/*      */     
/* 1159 */     return json;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @RequestMapping({"/{authType}/DeleteUser"})
/*      */   public String deleteUser(@PathVariable("authType") AuthMethodTypes authType, @RequestBody String payload, HttpServletRequest httpRequest) {
/* 1168 */     String json = null;
/* 1169 */     long startMillis = 0L;
/*      */     
/* 1171 */     if (LOG.isInfoEnabled()) {
/* 1172 */       LOG.info(String.format("## WEBAPI-RECEIVED MSG [RequestURI=%s] :: %s", new Object[] { httpRequest.getRequestURI(), payload }));
/* 1173 */       startMillis = System.currentTimeMillis();
/*      */     } 
/*      */ 
/*      */     
/* 1177 */     ExternalWebApiTypes externalType = getExternalType();
/*      */     
/* 1179 */     WebApiResponse response = externalType.createResponseInstance();
/*      */ 
/*      */     
/*      */     try {
/* 1183 */       WebApiRequestParam[] param = externalType.parseWebApiRequestParams(payload);
/*      */       
/* 1185 */       if (param != null) {
/* 1186 */         if (LOG.isInfoEnabled()) {
/* 1187 */           LOG.info("$$ WEBAPI-RECEIVED REQUEST MSG PARSED :: " + param.toString());
/*      */         }
/* 1189 */         ExternalAuthenticationService service = getAuthenticationMethodService(authType);
/* 1190 */         service.deleteUser(response, param);
/*      */       } else {
/* 1192 */         response.setReturnCode(ReturnCodes.INVALID_PARAMS);
/*      */       } 
/*      */       
/* 1195 */       json = GsonUtils.gson().toJson(response);
/*      */     }
/* 1197 */     catch (Exception e) {
/*      */       
/* 1199 */       if (e instanceof ReturnCodeException) {
/* 1200 */         response.setReturnCode(((ReturnCodeException)e).getReturnCode());
/* 1201 */       } else if (e.getCause() instanceof ReturnCodeException) {
/* 1202 */         response.setReturnCode(((ReturnCodeException)e.getCause()).getReturnCode());
/*      */       } else {
/* 1204 */         response.setReturnCode(ReturnCodes.INTERNAL_SERVER_ERROR);
/*      */       } 
/*      */       
/* 1207 */       json = GsonUtils.gson().toJson(response);
/*      */       
/* 1209 */       LOG.error(String.format("## WEBAPI-ERR MSG [RequestURI=%s, RetrunCode=%s] :: %s", new Object[] { httpRequest.getRequestURI(), response.getReturnCode().getCode(), e.getMessage() }));
/*      */     } 
/*      */     
/* 1212 */     if (LOG.isInfoEnabled()) {
/* 1213 */       LOG.info("$$ WEBAPI-RECEIVED RESPONSE RAW MSG :: " + response.toString());
/* 1214 */       LOG.info(String.format("## WEBAPI-SEND MSG [RequestURI=%s, LapTime=%,d ms] :: %s", new Object[] { httpRequest.getRequestURI(), Long.valueOf(System.currentTimeMillis() - startMillis), json }));
/*      */     } 
/*      */     
/* 1217 */     return json;
/*      */   }
/*      */ 
/*      */   
/*      */   private ExternalAuthenticationService getAuthenticationMethodService(AuthMethodTypes authType) {
/* 1222 */     return ExternalAuthenticationService.getServiceInstance(authType);
/*      */   }
/*      */ 
/*      */   
/*      */   private IVerifyOTPService getVerifyOTPService(AuthMethodTypes authType) {
/* 1227 */     if (AuthMethodTypes.BIOTP.equals(authType)) {
/* 1228 */       return (IVerifyOTPService)new BiotpVerifyOTPService();
/*      */     }
/* 1230 */     throw new ReturnCodeException(ReturnCodes.BAD_REQUEST);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private IssueCodeService getIssueCodeService() {
/* 1236 */     if (this.issueCodeService == null) {
/* 1237 */       this.issueCodeService = new IssueCodeService();
/*      */     }
/*      */     
/* 1240 */     return this.issueCodeService;
/*      */   }
/*      */   
/*      */   private ExternalWebApiTypes getExternalType() {
/* 1244 */     return SystemSettingsDao.getWebApiPolicy();
/*      */   }
/*      */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\external\webapi\WepApiAuthenticationController.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */