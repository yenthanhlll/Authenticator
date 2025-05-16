/*     */ package WEB-INF.classes.com.dreammirae.mmth.hwotp.controller;
/*     */ 
/*     */ import com.dreammirae.mmth.authentication.ReturnCodes;
/*     */ import com.dreammirae.mmth.hwotp.service.HwOtpAuthenticationService;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
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
/*     */ 
/*     */ 
/*     */ @RequestMapping(value = {"/rpserver/webapi"}, consumes = {"application/rp+json; charset=utf-8"}, produces = {"application/rp+json; charset=utf-8"})
/*     */ @RestController
/*     */ public class HwOtpController
/*     */ {
/*  23 */   protected static final Logger LOG = LoggerFactory.getLogger(com.dreammirae.mmth.hwotp.controller.HwOtpController.class);
/*     */ 
/*     */   
/*     */   protected static final String LOG_FORMAT_REQ_MSG = "## H/W OTP-RECEIVED MSG [RequestURI=%s] :: %s";
/*     */   
/*     */   protected static final String LOG_FORMAT_RESP_MSG = "## H/W OTP-SEND MSG [RequestURI=%s, LapTime=%,d ms] :: %s";
/*     */   
/*     */   protected static final String LOG_FORMAT_ERROR_MSG = "## H/W OTP-ERR MSG [RequestURI=%s] :: %s";
/*     */   
/*     */   protected static final int TYPE_MIRAE_OTP = 1;
/*     */   
/*     */   @Autowired
/*     */   private HwOtpAuthenticationService hwotpService;
/*     */ 
/*     */   
/*     */   @RequestMapping({"/HWOTP/ReturnCodes"})
/*     */   public HashMap<String, String> getReturnCodes() {
/*  40 */     HashMap<String, String> map = ReturnCodes.getMap();
/*     */     
/*  42 */     return map;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RequestMapping({"/HWOTP/IssueOtp"})
/*     */   public String issueOtp(@RequestBody Map<String, Object> param, HttpServletRequest httpRequest) {
/*  54 */     long startMillis = System.currentTimeMillis();
/*     */     
/*  56 */     LOG.info(String.format("## H/W OTP-RECEIVED MSG [RequestURI=%s] :: %s", new Object[] { httpRequest.getRequestURI(), param }));
/*     */     
/*  58 */     String user = (String)param.get("userName");
/*  59 */     String tokenId = (String)param.get("tokenId");
/*  60 */     long ip = 0L;
/*  61 */     String macAddress = null;
/*  62 */     String deviceId = null;
/*  63 */     String comment = null;
/*  64 */     String branchId = null;
/*  65 */     String accountName = null;
/*     */     
/*  67 */     if (param.get("ip") != null)
/*  68 */       ip = ((Long)param.get("ip")).longValue(); 
/*  69 */     if (param.get("macAddress") != null)
/*  70 */       macAddress = (String)param.get("macAddress"); 
/*  71 */     if (param.get("deviceId") != null)
/*  72 */       deviceId = (String)param.get("deviceId"); 
/*  73 */     if (param.get("comment") != null)
/*  74 */       comment = (String)param.get("comment"); 
/*  75 */     if (param.get("branchId") != null)
/*  76 */       branchId = (String)param.get("branchId"); 
/*  77 */     if (param.get("accountName") != null)
/*  78 */       accountName = (String)param.get("accountName"); 
/*  79 */     String json = this.hwotpService.issueOtp(user, tokenId, ip, macAddress, deviceId, comment, branchId, accountName);
/*  80 */     LOG.info(String.format("## H/W OTP-SEND MSG [RequestURI=%s, LapTime=%,d ms] :: %s", new Object[] { httpRequest.getRequestURI(), Long.valueOf(System.currentTimeMillis() - startMillis), json }));
/*     */     
/*  82 */     return json;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RequestMapping({"/HWOTP/GenChallenge"})
/*     */   public String genChallenge(@RequestBody Map<String, Object> param, HttpServletRequest httpRequest) {
/* 154 */     long startMillis = System.currentTimeMillis();
/*     */     
/* 156 */     LOG.info(String.format("## H/W OTP-RECEIVED MSG [RequestURI=%s] :: %s", new Object[] { httpRequest.getRequestURI(), param }));
/* 157 */     String user = (String)param.get("userName");
/* 158 */     String json = this.hwotpService.getChallenge(user);
/*     */     
/* 160 */     LOG.info(String.format("## H/W OTP-SEND MSG [RequestURI=%s, LapTime=%,d ms] :: %s", new Object[] { httpRequest.getRequestURI(), Long.valueOf(System.currentTimeMillis() - startMillis), json }));
/* 161 */     return json;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RequestMapping({"/HWOTP/VerifyOtp"})
/*     */   public String verifyOtp(@RequestBody Map<String, Object> param, HttpServletRequest httpRequest) {
/* 173 */     long startMillis = System.currentTimeMillis();
/*     */     
/* 175 */     LOG.info(String.format("## H/W OTP-RECEIVED MSG [RequestURI=%s] :: %s", new Object[] { httpRequest.getRequestURI(), param }));
/* 176 */     String user = (String)param.get("userName");
/* 177 */     String otp = (String)param.get("otp");
/* 178 */     long ip = 0L;
/* 179 */     String macAddress = null;
/* 180 */     String deviceId = null;
/* 181 */     String sessionCode = null;
/* 182 */     if (param.get("ip") != null)
/* 183 */       ip = ((Long)param.get("ip")).longValue(); 
/* 184 */     if (param.get("macAddress") != null)
/* 185 */       macAddress = (String)param.get("macAddress"); 
/* 186 */     if (param.get("deviceId") != null)
/* 187 */       deviceId = (String)param.get("deviceId"); 
/* 188 */     if (param.get("sessionCode") != null)
/* 189 */       sessionCode = (String)param.get("sessionCode"); 
/* 190 */     String json = this.hwotpService.verify(user, otp, ip, macAddress, deviceId, sessionCode);
/* 191 */     LOG.info(String.format("## H/W OTP-SEND MSG [RequestURI=%s, LapTime=%,d ms] :: %s", new Object[] { httpRequest.getRequestURI(), Long.valueOf(System.currentTimeMillis() - startMillis), json }));
/*     */     
/* 193 */     return json;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RequestMapping({"/HWOTP/DisuseOtp"})
/*     */   public String disuseOtp(@RequestBody Map<String, Object> param, HttpServletRequest httpRequest) {
/* 205 */     long startMillis = System.currentTimeMillis();
/*     */     
/* 207 */     LOG.info(String.format("## H/W OTP-RECEIVED MSG [RequestURI=%s] :: %s", new Object[] { httpRequest.getRequestURI(), param }));
/* 208 */     String user = (String)param.get("userName");
/* 209 */     String tokenId = (String)param.get("tokenId");
/* 210 */     long ip = 0L;
/* 211 */     String macAddress = null;
/* 212 */     String deviceId = null;
/* 213 */     if (param.get("ip") != null)
/* 214 */       ip = ((Long)param.get("ip")).longValue(); 
/* 215 */     if (param.get("macAddress") != null)
/* 216 */       macAddress = (String)param.get("macAddress"); 
/* 217 */     if (param.get("deviceId") != null)
/* 218 */       deviceId = (String)param.get("deviceId"); 
/* 219 */     String json = this.hwotpService.disuseOtp(user, tokenId, ip, macAddress, deviceId);
/* 220 */     LOG.info(String.format("## H/W OTP-SEND MSG [RequestURI=%s, LapTime=%,d ms] :: %s", new Object[] { httpRequest.getRequestURI(), Long.valueOf(System.currentTimeMillis() - startMillis), json }));
/*     */     
/* 222 */     return json;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RequestMapping({"/HWOTP/ResetUnlockCnt"})
/*     */   public String resetUnlockCnt(@RequestBody Map<String, Object> param, HttpServletRequest httpRequest) {
/* 234 */     long startMillis = System.currentTimeMillis();
/*     */     
/* 236 */     LOG.info(String.format("## H/W OTP-RECEIVED MSG [RequestURI=%s] :: %s", new Object[] { httpRequest.getRequestURI(), param }));
/* 237 */     String user = (String)param.get("userName");
/* 238 */     String json = this.hwotpService.resetUnlockCnt(user);
/* 239 */     LOG.info(String.format("## H/W OTP-SEND MSG [RequestURI=%s, LapTime=%,d ms] :: %s", new Object[] { httpRequest.getRequestURI(), Long.valueOf(System.currentTimeMillis() - startMillis), json }));
/*     */     
/* 241 */     return json;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RequestMapping({"/HWOTP/GenUnlockcode"})
/*     */   public String genUnlockcode(@RequestBody Map<String, Object> param, HttpServletRequest httpRequest) {
/* 253 */     long startMillis = System.currentTimeMillis();
/*     */     
/* 255 */     LOG.info(String.format("## H/W OTP-RECEIVED MSG [RequestURI=%s] :: %s", new Object[] { httpRequest.getRequestURI(), param }));
/* 256 */     String user = (String)param.get("userName");
/* 257 */     String json = this.hwotpService.genUnlockcode(user);
/* 258 */     LOG.info(String.format("## H/W OTP-SEND MSG [RequestURI=%s, LapTime=%,d ms] :: %s", new Object[] { httpRequest.getRequestURI(), Long.valueOf(System.currentTimeMillis() - startMillis), json }));
/*     */     
/* 260 */     return json;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RequestMapping({"/HWOTP/SyncUser"})
/*     */   public String syncUser(@RequestBody Map<String, Object> param, HttpServletRequest httpRequest) {
/* 272 */     long startMillis = System.currentTimeMillis();
/*     */     
/* 274 */     LOG.info(String.format("## H/W OTP-RECEIVED MSG [RequestURI=%s] :: %s", new Object[] { httpRequest.getRequestURI(), param }));
/* 275 */     String user = (String)param.get("userName");
/* 276 */     String otp = (String)param.get("otp");
/* 277 */     String json = this.hwotpService.syncUser(user, otp);
/* 278 */     LOG.info(String.format("## H/W OTP-SEND MSG [RequestURI=%s, LapTime=%,d ms] :: %s", new Object[] { httpRequest.getRequestURI(), Long.valueOf(System.currentTimeMillis() - startMillis), json }));
/*     */     
/* 280 */     return json;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RequestMapping({"/HWOTP/SyncAdmin"})
/*     */   public String syncAdmin(@RequestBody Map<String, Object> param, HttpServletRequest httpRequest) {
/* 292 */     long startMillis = System.currentTimeMillis();
/*     */     
/* 294 */     LOG.info(String.format("## H/W OTP-RECEIVED MSG [RequestURI=%s] :: %s", new Object[] { httpRequest.getRequestURI(), param }));
/* 295 */     String user = (String)param.get("userName");
/* 296 */     String otp = (String)param.get("otp");
/* 297 */     String nextotp = (String)param.get("nextotp");
/* 298 */     String json = this.hwotpService.syncAdmin(user, otp, nextotp);
/* 299 */     LOG.info(String.format("## H/W OTP-SEND MSG [RequestURI=%s, LapTime=%,d ms] :: %s", new Object[] { httpRequest.getRequestURI(), Long.valueOf(System.currentTimeMillis() - startMillis), json }));
/*     */     
/* 301 */     return json;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RequestMapping({"/HWOTP/SetLost"})
/*     */   public String setLost(@RequestBody Map<String, Object> param, HttpServletRequest httpRequest) {
/* 313 */     long startMillis = System.currentTimeMillis();
/*     */     
/* 315 */     LOG.info(String.format("## H/W OTP-RECEIVED MSG [RequestURI=%s] :: %s", new Object[] { httpRequest.getRequestURI(), param }));
/* 316 */     String user = (String)param.get("userName");
/* 317 */     long ip = 0L;
/* 318 */     String macAddress = null;
/* 319 */     String deviceId = null;
/* 320 */     if (param.get("ip") != null)
/* 321 */       ip = ((Long)param.get("ip")).longValue(); 
/* 322 */     if (param.get("macAddress") != null)
/* 323 */       macAddress = (String)param.get("macAddress"); 
/* 324 */     if (param.get("deviceId") != null)
/* 325 */       deviceId = (String)param.get("deviceId"); 
/* 326 */     String json = this.hwotpService.setLost(user, ip, macAddress, deviceId);
/* 327 */     LOG.info(String.format("## H/W OTP-SEND MSG [RequestURI=%s, LapTime=%,d ms] :: %s", new Object[] { httpRequest.getRequestURI(), Long.valueOf(System.currentTimeMillis() - startMillis), json }));
/*     */     
/* 329 */     return json;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RequestMapping({"/HWOTP/ClearLost"})
/*     */   public String clearLost(@RequestBody Map<String, Object> param, HttpServletRequest httpRequest) {
/* 341 */     long startMillis = System.currentTimeMillis();
/*     */     
/* 343 */     LOG.info(String.format("## H/W OTP-RECEIVED MSG [RequestURI=%s] :: %s", new Object[] { httpRequest.getRequestURI(), param }));
/* 344 */     String user = (String)param.get("userName");
/* 345 */     long ip = 0L;
/* 346 */     String macAddress = null;
/* 347 */     String deviceId = null;
/* 348 */     if (param.get("ip") != null)
/* 349 */       ip = ((Long)param.get("ip")).longValue(); 
/* 350 */     if (param.get("macAddress") != null)
/* 351 */       macAddress = (String)param.get("macAddress"); 
/* 352 */     if (param.get("deviceId") != null)
/* 353 */       deviceId = (String)param.get("deviceId"); 
/* 354 */     String json = this.hwotpService.clearLost(user, ip, macAddress, deviceId);
/* 355 */     LOG.info(String.format("## H/W OTP-SEND MSG [RequestURI=%s, LapTime=%,d ms] :: %s", new Object[] { httpRequest.getRequestURI(), Long.valueOf(System.currentTimeMillis() - startMillis), json }));
/*     */     
/* 357 */     return json;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RequestMapping({"/HWOTP/CheckUser"})
/*     */   public String checkUser(@RequestBody Map<String, Object> param, HttpServletRequest httpRequest) {
/* 368 */     String json = null;
/*     */     
/* 370 */     long startMillis = System.currentTimeMillis();
/*     */     
/* 372 */     LOG.info(String.format("## H/W OTP-RECEIVED MSG [RequestURI=%s] :: %s", new Object[] { httpRequest.getRequestURI(), param }));
/* 373 */     String user = (String)param.get("userName");
/* 374 */     String tokenId = null;
/* 375 */     if (param.get("tokenId") != null) {
/* 376 */       tokenId = (String)param.get("tokenId");
/* 377 */       json = this.hwotpService.checkUser(user, tokenId);
/*     */     } else {
/*     */       
/* 380 */       json = this.hwotpService.checkUser(user);
/* 381 */     }  LOG.info(String.format("## H/W OTP-SEND MSG [RequestURI=%s, LapTime=%,d ms] :: %s", new Object[] { httpRequest.getRequestURI(), Long.valueOf(System.currentTimeMillis() - startMillis), json }));
/*     */     
/* 383 */     return json;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RequestMapping({"/HWOTP/CheckToken"})
/*     */   public String checkToken(@RequestBody Map<String, Object> param, HttpServletRequest httpRequest) {
/* 394 */     String json = null;
/*     */     
/* 396 */     long startMillis = System.currentTimeMillis();
/*     */     
/* 398 */     LOG.info(String.format("## H/W OTP-RECEIVED MSG [RequestURI=%s] :: %s", new Object[] { httpRequest.getRequestURI(), param }));
/* 399 */     String tokenId = (String)param.get("tokenId");
/* 400 */     json = this.hwotpService.checkToken(tokenId);
/* 401 */     LOG.info(String.format("## H/W OTP-SEND MSG [RequestURI=%s, LapTime=%,d ms] :: %s", new Object[] { httpRequest.getRequestURI(), Long.valueOf(System.currentTimeMillis() - startMillis), json }));
/*     */     
/* 403 */     return json;
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\hwotp\controller\HwOtpController.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */