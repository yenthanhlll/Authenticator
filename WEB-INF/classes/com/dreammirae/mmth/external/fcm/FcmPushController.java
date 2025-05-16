/*     */ package WEB-INF.classes.com.dreammirae.mmth.external.fcm;
/*     */ 
/*     */ import com.dreammirae.mmth.authentication.AuthMethodTypes;
/*     */ import com.dreammirae.mmth.authentication.ReturnCodes;
/*     */ import com.dreammirae.mmth.authentication.bean.AuthenticationRequestLocator;
/*     */ import com.dreammirae.mmth.exception.ReturnCodeException;
/*     */ import com.dreammirae.mmth.external.bean.GptwrWebApiResponse;
/*     */ import com.dreammirae.mmth.external.service.fcm.FCMTokenRegisterService;
/*     */ import com.dreammirae.mmth.misc.Base64Utils;
/*     */ import com.dreammirae.mmth.util.StringUtils;
/*     */ import com.dreammirae.mmth.util.io.HexUtils;
/*     */ import com.dreammirae.mmth.vo.types.DeviceTypes;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.web.bind.annotation.PathVariable;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ import org.springframework.web.bind.annotation.ResponseBody;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Controller
/*     */ @RequestMapping({"/fcm"})
/*     */ public class FcmPushController
/*     */ {
/*  30 */   private static final Logger LOG = LoggerFactory.getLogger(com.dreammirae.mmth.external.fcm.FcmPushController.class);
/*     */ 
/*     */   
/*     */   private static final String LOG_FORMAT_REQ_SERVICE = "## FCM-RECEIVED MSG [RequestURI=%s] : Param :: %s";
/*     */ 
/*     */   
/*     */   private static final String LOG_FORMAT_RESP_SERVICE = "## FCM-SEND MSG [RequestURI=%s, LapTime=%,d ms] : result :: %s";
/*     */   
/*     */   @Autowired
/*     */   private FCMTokenRegisterService service;
/*     */ 
/*     */   
/*     */   @RequestMapping({"/register/{authMethod}"})
/*     */   @ResponseBody
/*     */   public GptwrWebApiResponse registerFcmToken(@PathVariable("authMethod") AuthMethodTypes authMethod, HttpServletRequest request, HttpServletResponse response) {
/*  45 */     AuthenticationRequestLocator data = getRequestLocator(request);
/*  46 */     long startMillis = 0L;
/*  47 */     if (LOG.isDebugEnabled()) {
/*  48 */       LOG.debug(String.format("## FCM-RECEIVED MSG [RequestURI=%s] : Param :: %s", new Object[] { request.getRequestURI(), data.toString() }));
/*  49 */       startMillis = System.currentTimeMillis();
/*     */     } 
/*     */     
/*  52 */     GptwrWebApiResponse resp = new GptwrWebApiResponse();
/*     */     
/*     */     try {
/*  55 */       this.service.registerTokenId(authMethod, data.getFcmTokenId(), data.getUsername(), data.getDeviceId(), data.getDeviceType(), data.getPakageName());
/*  56 */       resp.setReturnCode(ReturnCodes.OK);
/*  57 */     } catch (ReturnCodeException e) {
/*  58 */       resp.setReturnCode(e.getReturnCode());
/*  59 */       resp.setErrorMessage(e.getMessage());
/*  60 */       LOG.error(e.getMessage());
/*  61 */     } catch (Exception e) {
/*  62 */       resp.setReturnCode(ReturnCodes.INTERNAL_SERVER_ERROR);
/*  63 */       LOG.error(e.getMessage());
/*     */     } 
/*     */     
/*  66 */     if (LOG.isDebugEnabled()) {
/*  67 */       LOG.debug(String.format("## FCM-SEND MSG [RequestURI=%s, LapTime=%,d ms] : result :: %s", new Object[] { request.getRequestURI(), Long.valueOf(System.currentTimeMillis() - startMillis), resp.toString() }));
/*     */     }
/*     */     
/*  70 */     return resp;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private AuthenticationRequestLocator getRequestLocator(HttpServletRequest request) {
/*  76 */     AuthenticationRequestLocator data = new AuthenticationRequestLocator();
/*  77 */     data.setUsername(decodeValue("userName", request));
/*     */     
/*  79 */     String deviceId = request.getParameter("deviceId");
/*     */     
/*  81 */     if (!StringUtils.isEmpty(deviceId)) {
/*  82 */       byte[] raw = Base64Utils.decodeRaw(deviceId);
/*  83 */       data.setDeviceId(HexUtils.toHexString(raw));
/*     */     } 
/*     */     
/*  86 */     String val = decodeValue("deviceType", request);
/*  87 */     if (!StringUtils.isEmpty(val)) {
/*  88 */       data.setDeviceType(DeviceTypes.getDeviceType(val));
/*     */     }
/*  90 */     data.setPakageName(decodeValue("pkgName", request));
/*  91 */     data.setFcmTokenId(decodeValue("fcmTokenId", request));
/*     */     
/*  93 */     return data;
/*     */   }
/*     */   
/*     */   private String decodeValue(String key, HttpServletRequest request) {
/*  97 */     String value = request.getParameter(key);
/*     */     
/*  99 */     if (StringUtils.isEmpty(value)) {
/* 100 */       return null;
/*     */     }
/*     */     
/* 103 */     return Base64Utils.decode(value);
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\external\fcm\FcmPushController.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */