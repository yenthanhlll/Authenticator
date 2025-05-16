/*     */ package WEB-INF.classes.com.dreammirae.mmth.rp.controller.biotp.kiwoom;
/*     */ 
/*     */ import com.dreammirae.mmth.authentication.AuthMethodTypes;
/*     */ import com.dreammirae.mmth.authentication.ReturnCodes;
/*     */ import com.dreammirae.mmth.authentication.bean.AuthenticationRequestLocator;
/*     */ import com.dreammirae.mmth.authentication.bean.AuthenticationResponseLocator;
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
/*     */ import com.dreammirae.mmth.parser.IUserServiceMessageParser;
/*     */ import com.dreammirae.mmth.parser.biotp.kiwoom.TransportAPI;
/*     */ import com.dreammirae.mmth.rp.controller.AuthenticationController;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.web.bind.annotation.PathVariable;
/*     */ import org.springframework.web.bind.annotation.RequestBody;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ import org.springframework.web.bind.annotation.RestController;
/*     */ 
/*     */ 
/*     */ @RestController
/*     */ @RequestMapping(value = {"/rpserver/kiwoom/BIOTP"}, consumes = {"text/plain"}, produces = {"text/plain"})
/*     */ public class KiwoomBiotpAuthenticationController
/*     */   extends AuthenticationController
/*     */ {
/*  36 */   private static final Logger LOG = LoggerFactory.getLogger(com.dreammirae.mmth.rp.controller.biotp.kiwoom.KiwoomBiotpAuthenticationController.class);
/*     */   @Autowired
/*     */   private BiotpUAFRequestService biotpUAFRequestService;
/*     */   @Autowired
/*     */   private BiotpUAFResponseService biotpUAFResponseService;
/*     */   @Autowired
/*     */   private BiotpVerifyOTPService biotpVerifyOTPService;
/*     */   @Autowired
/*     */   private BiotpGeneralService biotpGeneralService;
/*     */   private static final String LOG_FORMAT_REQ_MSG_PREFIX = "### REQ MSG [API=";
/*     */   private static final String LOG_FORMAT_REQ_MSG_LEN = ", REQ_LEN=";
/*     */   private static final String LOG_FORMAT_REQ_MSG_SUFFIX = "]";
/*     */   private static final String LOG_FORMAT_RESP_MSG_PREFIX = "### RESP MSG [API=";
/*     */   private static final String LOG_FORMAT_RESP_MSG_LEN = ", RESP_LEN=";
/*     */   private static final String LOG_FORMAT_RESP_MSG_LAPTIME = "] :: LapTime=";
/*     */   private static final String LOG_FORMAT_RESP_MSG_SUFFIX = " ms";
/*     */   
/*     */   @RequestMapping({"/{apiCode}"})
/*     */   public byte[] reqTransportApi(@PathVariable("apiCode") String apiCode, @RequestBody byte[] payload, HttpServletRequest httpRequest) {
/*  55 */     long startMillis = System.currentTimeMillis();
/*  56 */     LOG.info(genReceivedReqLogMsg(apiCode, payload, httpRequest));
/*     */ 
/*     */     
/*  59 */     TransportAPI transportApi = TransportAPI.getTransportAPI(apiCode);
/*     */ 
/*     */     
/*  62 */     if (transportApi == null) {
/*  63 */       LOG.error("There has no available URL... API CODE=" + apiCode);
/*  64 */       return ReturnCodes.NOT_FOUND_URL.getCodeBytes();
/*     */     } 
/*     */ 
/*     */     
/*  68 */     transportApi.setTransportAPI(httpRequest);
/*     */ 
/*     */     
/*  71 */     byte[] respPayload = null;
/*     */ 
/*     */     
/*  74 */     if (TransportAPI.API_1001.equals(transportApi) || TransportAPI.API_2001.equals(transportApi) || TransportAPI.API_3001
/*  75 */       .equals(transportApi) || TransportAPI.API_4001.equals(transportApi) || TransportAPI.API_4002
/*  76 */       .equals(transportApi)) {
/*  77 */       respPayload = generateUAFRequestImp(payload, httpRequest);
/*     */     }
/*  79 */     else if (TransportAPI.API_1002.equals(transportApi)) {
/*  80 */       respPayload = verifyingUAFResponseImp(Operation.Reg, payload, httpRequest);
/*     */     }
/*  82 */     else if (TransportAPI.API_2002.equals(transportApi) || TransportAPI.API_3002.equals(transportApi)) {
/*  83 */       respPayload = verifyingUAFResponseImp(Operation.Auth, payload, httpRequest);
/*     */ 
/*     */     
/*     */     }
/*  87 */     else if (TransportAPI.API_5001.equals(transportApi)) {
/*  88 */       respPayload = registerTransactionInfoImp(payload, httpRequest);
/*  89 */     } else if (TransportAPI.API_5002.equals(transportApi)) {
/*  90 */       respPayload = getTransactionInfoImp(payload, httpRequest);
/*  91 */     } else if (TransportAPI.API_5003.equals(transportApi)) {
/*  92 */       respPayload = setUserVerificationFlagImp(payload, httpRequest);
/*  93 */     } else if (TransportAPI.API_9001.equals(transportApi)) {
/*  94 */       respPayload = getUserRegistrationInfoImp(payload, httpRequest);
/*  95 */     } else if (TransportAPI.API_9002.equals(transportApi)) {
/*  96 */       respPayload = verifyingOTPImp(payload, httpRequest, false);
/*  97 */     } else if (TransportAPI.API_9003.equals(transportApi)) {
/*  98 */       respPayload = verifyingTranOTPImp(payload, httpRequest, false);
/*  99 */     } else if (TransportAPI.API_9005.equals(transportApi)) {
/* 100 */       respPayload = verifyingTranOTPImp(payload, httpRequest, true);
/*     */     } else {
/* 102 */       respPayload = doForceDeregImp(payload, httpRequest);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 107 */     LOG.info(genSendRespLogMsg(apiCode, respPayload, startMillis, httpRequest));
/*     */     
/* 109 */     return respPayload;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected byte[] registerTransactionInfoImp(byte[] payload, HttpServletRequest httpRequest) {
/* 115 */     IUserServiceMessageParser parser = getAuthMethodType().getUserServiceMessageParser();
/*     */ 
/*     */     
/* 118 */     AuthenticationResponseLocator respLoc = parser.createResponseLocatorInstance();
/*     */     
/* 120 */     byte[] respPayload = null;
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 125 */       AuthenticationRequestLocator reqLoc = parser.parseRequestLocatorRaw(payload, httpRequest);
/*     */ 
/*     */       
/* 128 */       LOG.info(genReadableReqLogMsg(httpRequest, reqLoc.toString()));
/*     */ 
/*     */       
/* 131 */       getGeneralService().registerTransactionInfo(respLoc, reqLoc);
/*     */ 
/*     */       
/* 134 */       LOG.info(genReadableRespLogMsg(httpRequest, respLoc.toString()));
/*     */ 
/*     */       
/* 137 */       respPayload = parser.generateResponseLocatorPayloadRaw(respLoc, httpRequest);
/*     */     }
/* 139 */     catch (Throwable e) {
/*     */ 
/*     */       
/* 142 */       if (e instanceof ReturnCodeException) {
/* 143 */         respLoc.setReturnCode(((ReturnCodeException)e).getReturnCode());
/* 144 */       } else if (e.getCause() instanceof ReturnCodeException) {
/* 145 */         respLoc.setReturnCode(((ReturnCodeException)e.getCause()).getReturnCode());
/*     */       } else {
/* 147 */         respLoc.setReturnCode(ReturnCodes.INTERNAL_SERVER_ERROR);
/*     */       } 
/*     */ 
/*     */       
/* 151 */       LOG.error(genReadableErrorLogMsg(httpRequest, respLoc.toString(), e));
/*     */       
/* 153 */       respPayload = parser.generateResponseLocatorPayloadRaw(respLoc, httpRequest);
/*     */     } 
/*     */ 
/*     */     
/* 157 */     return respPayload;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected byte[] getTransactionInfoImp(byte[] payload, HttpServletRequest httpRequest) {
/* 163 */     IUserServiceMessageParser parser = getAuthMethodType().getUserServiceMessageParser();
/*     */ 
/*     */     
/* 166 */     AuthenticationResponseLocator respLoc = parser.createResponseLocatorInstance();
/*     */     
/* 168 */     byte[] respPayload = null;
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 173 */       AuthenticationRequestLocator reqLoc = parser.parseRequestLocatorRaw(payload, httpRequest);
/*     */ 
/*     */       
/* 176 */       LOG.info(genReadableReqLogMsg(httpRequest, reqLoc.toString()));
/*     */ 
/*     */       
/* 179 */       getGeneralService().getTransactionInfo(respLoc, reqLoc);
/*     */ 
/*     */       
/* 182 */       LOG.info(genReadableRespLogMsg(httpRequest, respLoc.toString()));
/*     */ 
/*     */       
/* 185 */       respPayload = parser.generateResponseLocatorPayloadRaw(respLoc, httpRequest);
/*     */     }
/* 187 */     catch (Throwable e) {
/*     */ 
/*     */       
/* 190 */       if (e instanceof ReturnCodeException) {
/* 191 */         respLoc.setReturnCode(((ReturnCodeException)e).getReturnCode());
/* 192 */       } else if (e.getCause() instanceof ReturnCodeException) {
/* 193 */         respLoc.setReturnCode(((ReturnCodeException)e.getCause()).getReturnCode());
/*     */       } else {
/* 195 */         respLoc.setReturnCode(ReturnCodes.INTERNAL_SERVER_ERROR);
/*     */       } 
/*     */ 
/*     */       
/* 199 */       LOG.error(genReadableErrorLogMsg(httpRequest, respLoc.toString(), e));
/*     */       
/* 201 */       respPayload = parser.generateResponseLocatorPayloadRaw(respLoc, httpRequest);
/*     */     } 
/*     */     
/* 204 */     return respPayload;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected byte[] setUserVerificationFlagImp(byte[] payload, HttpServletRequest httpRequest) {
/* 210 */     IUserServiceMessageParser parser = getAuthMethodType().getUserServiceMessageParser();
/*     */ 
/*     */     
/* 213 */     AuthenticationResponseLocator respLoc = parser.createResponseLocatorInstance();
/*     */     
/* 215 */     byte[] respPayload = null;
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 220 */       AuthenticationRequestLocator reqLoc = parser.parseRequestLocatorRaw(payload, httpRequest);
/*     */ 
/*     */       
/* 223 */       LOG.info(genReadableReqLogMsg(httpRequest, reqLoc.toString()));
/*     */ 
/*     */       
/* 226 */       getGeneralService().updateUserVerificationFlag(respLoc, reqLoc);
/*     */ 
/*     */       
/* 229 */       LOG.info(genReadableRespLogMsg(httpRequest, respLoc.toString()));
/*     */ 
/*     */       
/* 232 */       respPayload = parser.generateResponseLocatorPayloadRaw(respLoc, httpRequest);
/*     */     }
/* 234 */     catch (Throwable e) {
/*     */ 
/*     */       
/* 237 */       if (e instanceof ReturnCodeException) {
/* 238 */         respLoc.setReturnCode(((ReturnCodeException)e).getReturnCode());
/* 239 */       } else if (e.getCause() instanceof ReturnCodeException) {
/* 240 */         respLoc.setReturnCode(((ReturnCodeException)e.getCause()).getReturnCode());
/*     */       } else {
/* 242 */         respLoc.setReturnCode(ReturnCodes.INTERNAL_SERVER_ERROR);
/*     */       } 
/*     */ 
/*     */       
/* 246 */       LOG.error(genReadableErrorLogMsg(httpRequest, respLoc.toString(), e));
/*     */       
/* 248 */       respPayload = parser.generateResponseLocatorPayloadRaw(respLoc, httpRequest);
/*     */     } 
/*     */     
/* 251 */     return respPayload;
/*     */   }
/*     */ 
/*     */   
/*     */   protected AuthMethodTypes getAuthMethodType() {
/* 256 */     return AuthMethodTypes.BIOTP;
/*     */   }
/*     */ 
/*     */   
/*     */   protected IUAFRequestService getUAFRequestService() {
/* 261 */     if (this.biotpUAFRequestService == null) {
/* 262 */       this.biotpUAFRequestService = new BiotpUAFRequestService();
/*     */     }
/* 264 */     return (IUAFRequestService)this.biotpUAFRequestService;
/*     */   }
/*     */ 
/*     */   
/*     */   protected IUAFResponseService getUAFResponseService() {
/* 269 */     if (this.biotpUAFResponseService == null) {
/* 270 */       this.biotpUAFResponseService = new BiotpUAFResponseService();
/*     */     }
/* 272 */     return (IUAFResponseService)this.biotpUAFResponseService;
/*     */   }
/*     */ 
/*     */   
/*     */   protected IVerifyOTPService getVerifyOTPService() {
/* 277 */     if (this.biotpVerifyOTPService == null) {
/* 278 */       this.biotpVerifyOTPService = new BiotpVerifyOTPService();
/*     */     }
/*     */     
/* 281 */     return (IVerifyOTPService)this.biotpVerifyOTPService;
/*     */   }
/*     */ 
/*     */   
/*     */   protected IssueCodeService getIssueCodeService() {
/* 286 */     throw new ReturnCodeException(ReturnCodes.NOT_FOUND_URL);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected IGeneralService getGeneralService() {
/* 292 */     if (this.biotpGeneralService == null) {
/* 293 */       this.biotpGeneralService = new BiotpGeneralService();
/*     */     }
/* 295 */     return (IGeneralService)this.biotpGeneralService;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String genReceivedReqLogMsg(String apiCode, byte[] payload, HttpServletRequest httpRequest) {
/* 304 */     StringBuilder sb = new StringBuilder(64);
/*     */     
/*     */     try {
/* 307 */       sb.append("### REQ MSG [API=").append(apiCode).append(", REQ_LEN=");
/* 308 */       if (payload == null) {
/* 309 */         sb.append(0);
/*     */       } else {
/* 311 */         sb.append(THOUSAND_NUMBER_FORMAT.format(payload.length));
/*     */       } 
/*     */       
/* 314 */       sb.append("]");
/* 315 */       return sb.toString();
/*     */     } finally {
/* 317 */       sb.setLength(0);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String genSendRespLogMsg(String apiCode, byte[] respPayload, long startMillis, HttpServletRequest httpRequest) {
/* 329 */     StringBuilder sb = new StringBuilder(64);
/*     */     
/*     */     try {
/* 332 */       sb.append("### RESP MSG [API=").append(apiCode).append(", RESP_LEN=");
/* 333 */       if (respPayload == null) {
/* 334 */         sb.append(0);
/*     */       } else {
/* 336 */         sb.append(THOUSAND_NUMBER_FORMAT.format(respPayload.length));
/*     */       } 
/*     */       
/* 339 */       sb.append("] :: LapTime=");
/* 340 */       sb.append(THOUSAND_NUMBER_FORMAT.format(System.currentTimeMillis() - startMillis))
/* 341 */         .append(" ms");
/*     */       
/* 343 */       return sb.toString();
/*     */     } finally {
/* 345 */       sb.setLength(0);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\rp\controller\biotp\kiwoom\KiwoomBiotpAuthenticationController.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */