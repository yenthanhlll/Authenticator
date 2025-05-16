/*     */ package WEB-INF.classes.com.dreammirae.mmth.rp.controller.biotp;
/*     */ 
/*     */ import com.dreammirae.mmth.authentication.AuthMethodTypes;
/*     */ import com.dreammirae.mmth.authentication.ProductType;
/*     */ import com.dreammirae.mmth.authentication.ReturnCodes;
/*     */ import com.dreammirae.mmth.authentication.bean.AuthenticationRequestLocator;
/*     */ import com.dreammirae.mmth.authentication.bean.AuthenticationResponseLocator;
/*     */ import com.dreammirae.mmth.authentication.service.IGeneralService;
/*     */ import com.dreammirae.mmth.authentication.service.IUAFRequestService;
/*     */ import com.dreammirae.mmth.authentication.service.IUAFResponseService;
/*     */ import com.dreammirae.mmth.authentication.service.IVerifyOTPService;
/*     */ import com.dreammirae.mmth.authentication.service.IssueCodeService;
/*     */ import com.dreammirae.mmth.authentication.service.biotp.AuthTypeValifyService;
/*     */ import com.dreammirae.mmth.parser.IUserServiceMessageParser;
/*     */ import com.dreammirae.mmth.parser.json.GsonUtils;
/*     */ import com.dreammirae.mmth.rp.controller.AuthenticationController;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.apache.commons.lang3.StringUtils;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.web.bind.annotation.RequestBody;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ import org.springframework.web.bind.annotation.RestController;
/*     */ 
/*     */ 
/*     */ @RestController
/*     */ @RequestMapping(value = {"/rpserver/httpapi/authtype"}, consumes = {"application/fido+uaf; charset=utf-8"}, produces = {"application/fido+uaf; charset=utf-8"})
/*     */ public class UserMultiFatorAuthenticationController
/*     */   extends AuthenticationController
/*     */ {
/*     */   @Autowired
/*     */   AuthTypeValifyService authTypeValifyService;
/*  34 */   private static final Logger LOG = LoggerFactory.getLogger(com.dreammirae.mmth.rp.controller.biotp.UserMultiFatorAuthenticationController.class);
/*     */ 
/*     */   
/*     */   @RequestMapping({"/Reg"})
/*     */   public String registrate(@RequestBody String payload, HttpServletRequest httpRequest) {
/*  39 */     IUserServiceMessageParser parser = getAuthMethodType().getUserServiceMessageParser();
/*  40 */     LOG.info(genReceivedReqLogMsg(payload, httpRequest));
/*  41 */     String respPayload = null;
/*  42 */     long startMillis = System.currentTimeMillis();
/*     */ 
/*     */     
/*  45 */     AuthenticationResponseLocator respLoc = parser.createResponseLocatorInstance();
/*     */     
/*  47 */     AuthenticationRequestLocator reqLoc = parser.parseRequestLocator(payload, httpRequest);
/*     */     
/*  49 */     if (reqLoc.getProductType() != ProductType.BIOTP.getCode()) {
/*  50 */       respLoc.setReturnCode(ReturnCodes.BAD_REQUEST);
/*  51 */       respPayload = GsonUtils.gson().toJson(respLoc);
/*  52 */       return respPayload;
/*     */     } 
/*     */     
/*  55 */     if (StringUtils.isBlank(reqLoc.getAuthData())) {
/*  56 */       respLoc.setReturnCode(ReturnCodes.BAD_REQUEST);
/*  57 */       respPayload = GsonUtils.gson().toJson(respLoc);
/*  58 */       return respPayload;
/*     */     } 
/*  60 */     respLoc = this.authTypeValifyService.save(respLoc, reqLoc);
/*     */     
/*  62 */     respPayload = GsonUtils.gson().toJson(respLoc);
/*     */     
/*  64 */     LOG.info(genSendRespLogMsg(respPayload, startMillis, httpRequest));
/*     */     
/*  66 */     return respPayload;
/*     */   }
/*     */ 
/*     */   
/*     */   @RequestMapping({"/Update"})
/*     */   public String update(@RequestBody String payload, HttpServletRequest httpRequest) {
/*  72 */     IUserServiceMessageParser parser = getAuthMethodType().getUserServiceMessageParser();
/*  73 */     LOG.info(genReceivedReqLogMsg(payload, httpRequest));
/*  74 */     String respPayload = null;
/*  75 */     long startMillis = System.currentTimeMillis();
/*     */ 
/*     */     
/*  78 */     AuthenticationResponseLocator respLoc = parser.createResponseLocatorInstance();
/*     */     
/*  80 */     AuthenticationRequestLocator reqLoc = parser.parseRequestLocator(payload, httpRequest);
/*     */     
/*  82 */     if (StringUtils.isBlank(reqLoc.getAuthData())) {
/*  83 */       respLoc.setReturnCode(ReturnCodes.BAD_REQUEST);
/*  84 */       respPayload = GsonUtils.gson().toJson(respLoc);
/*  85 */       return respPayload;
/*     */     } 
/*     */     
/*  88 */     if (reqLoc.getProductType() != ProductType.BIOTP.getCode()) {
/*  89 */       respLoc.setReturnCode(ReturnCodes.BAD_REQUEST);
/*  90 */       respPayload = GsonUtils.gson().toJson(respLoc);
/*  91 */       return respPayload;
/*     */     } 
/*     */     
/*  94 */     this.authTypeValifyService.save(respLoc, reqLoc);
/*     */     
/*  96 */     respPayload = GsonUtils.gson().toJson(respLoc);
/*     */     
/*  98 */     LOG.info(genSendRespLogMsg(respPayload, startMillis, httpRequest));
/*     */     
/* 100 */     return respPayload;
/*     */   }
/*     */ 
/*     */   
/*     */   @RequestMapping({"/Auth"})
/*     */   public String authenticate(@RequestBody String payload, HttpServletRequest httpRequest) {
/* 106 */     IUserServiceMessageParser parser = getAuthMethodType().getUserServiceMessageParser();
/*     */     
/* 108 */     AuthenticationResponseLocator respLoc = parser.createResponseLocatorInstance();
/*     */     
/* 110 */     AuthenticationRequestLocator reqLoc = parser.parseRequestLocator(payload, httpRequest);
/*     */     
/* 112 */     String respPayload = null;
/* 113 */     if (StringUtils.isBlank(reqLoc.getAuthData())) {
/* 114 */       respLoc.setReturnCode(ReturnCodes.BAD_REQUEST);
/* 115 */       respPayload = GsonUtils.gson().toJson(respLoc);
/* 116 */       return respPayload;
/*     */     } 
/*     */     
/* 119 */     if (reqLoc.getProductType() != ProductType.BIOTP.getCode()) {
/* 120 */       respLoc.setReturnCode(ReturnCodes.BAD_REQUEST);
/* 121 */       respPayload = GsonUtils.gson().toJson(respLoc);
/* 122 */       return respPayload;
/*     */     } 
/*     */     
/* 125 */     LOG.info(genReceivedReqLogMsg(payload, httpRequest));
/* 126 */     long startMillis = System.currentTimeMillis();
/*     */     
/* 128 */     respLoc = this.authTypeValifyService.authenticate(respLoc, reqLoc);
/*     */     
/* 130 */     respPayload = GsonUtils.gson().toJson(respLoc);
/*     */     
/* 132 */     LOG.info(genSendRespLogMsg(respPayload, startMillis, httpRequest));
/*     */     
/* 134 */     return respPayload;
/*     */   }
/*     */ 
/*     */   
/*     */   @RequestMapping({"/Reset"})
/*     */   public String resetAuthFailCnt(@RequestBody String payload, HttpServletRequest httpRequest) {
/* 140 */     IUserServiceMessageParser parser = getAuthMethodType().getUserServiceMessageParser();
/*     */     
/* 142 */     AuthenticationResponseLocator respLoc = parser.createResponseLocatorInstance();
/*     */     
/* 144 */     AuthenticationRequestLocator reqLoc = parser.parseRequestLocator(payload, httpRequest);
/*     */     
/* 146 */     String respPayload = null;
/* 147 */     if (reqLoc.getProductType() != ProductType.BIOTP.getCode()) {
/* 148 */       respLoc.setReturnCode(ReturnCodes.BAD_REQUEST);
/* 149 */       respPayload = GsonUtils.gson().toJson(respLoc);
/* 150 */       return respPayload;
/*     */     } 
/*     */     
/* 153 */     LOG.info(genReceivedReqLogMsg(payload, httpRequest));
/* 154 */     long startMillis = System.currentTimeMillis();
/* 155 */     respLoc = this.authTypeValifyService.resetAuthFailCnt(respLoc, reqLoc);
/*     */     
/* 157 */     respPayload = GsonUtils.gson().toJson(respLoc);
/*     */     
/* 159 */     LOG.info(genSendRespLogMsg(respPayload, startMillis, httpRequest));
/*     */     
/* 161 */     return respPayload;
/*     */   }
/*     */ 
/*     */   
/*     */   public String resetAuthData(@RequestBody String payload, HttpServletRequest httpRequest) {
/* 166 */     IUserServiceMessageParser parser = getAuthMethodType().getUserServiceMessageParser();
/*     */     
/* 168 */     AuthenticationResponseLocator respLoc = parser.createResponseLocatorInstance();
/*     */     
/* 170 */     AuthenticationRequestLocator reqLoc = parser.parseRequestLocator(payload, httpRequest);
/*     */     
/* 172 */     LOG.info(genReceivedReqLogMsg(payload, httpRequest));
/* 173 */     String respPayload = null;
/* 174 */     long startMillis = System.currentTimeMillis();
/*     */     
/* 176 */     if (reqLoc.getProductType() != ProductType.BIOTP.getCode()) {
/* 177 */       respLoc.setReturnCode(ReturnCodes.BAD_REQUEST);
/* 178 */       respPayload = GsonUtils.gson().toJson(respLoc);
/* 179 */       return respPayload;
/*     */     } 
/*     */     
/* 182 */     respLoc = this.authTypeValifyService.resetAuthData(respLoc, reqLoc);
/*     */     
/* 184 */     respPayload = GsonUtils.gson().toJson(respLoc);
/*     */     
/* 186 */     LOG.info(genSendRespLogMsg(respPayload, startMillis, httpRequest));
/*     */     
/* 188 */     return respPayload;
/*     */   }
/*     */ 
/*     */   
/*     */   @RequestMapping({"/CheckFailCnt"})
/*     */   public String checkFailCntByProduct(@RequestBody String payload, HttpServletRequest httpRequest) {
/* 194 */     IUserServiceMessageParser parser = getAuthMethodType().getUserServiceMessageParser();
/*     */ 
/*     */ 
/*     */     
/* 198 */     AuthenticationRequestLocator reqLoc = parser.parseRequestLocator(payload, httpRequest);
/*     */     
/* 200 */     LOG.info(genReceivedReqLogMsg(payload, httpRequest));
/* 201 */     String respPayload = null;
/* 202 */     long startMillis = System.currentTimeMillis();
/*     */     
/* 204 */     respPayload = GsonUtils.gson().toJson(this.authTypeValifyService.checkFailCount(reqLoc));
/*     */     
/* 206 */     LOG.info(genSendRespLogMsg(respPayload, startMillis, httpRequest));
/*     */     
/* 208 */     return respPayload;
/*     */   }
/*     */ 
/*     */   
/*     */   protected AuthMethodTypes getAuthMethodType() {
/* 213 */     return AuthMethodTypes.BIOTP;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected IUAFRequestService getUAFRequestService() {
/* 219 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected IUAFResponseService getUAFResponseService() {
/* 225 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected IVerifyOTPService getVerifyOTPService() {
/* 231 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected IGeneralService getGeneralService() {
/* 237 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected IssueCodeService getIssueCodeService() {
/* 243 */     return null;
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\rp\controller\biotp\UserMultiFatorAuthenticationController.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */