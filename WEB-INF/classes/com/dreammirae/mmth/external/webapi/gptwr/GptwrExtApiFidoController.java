/*     */ package WEB-INF.classes.com.dreammirae.mmth.external.webapi.gptwr;
/*     */ 
/*     */ import com.dreammirae.mmth.authentication.ReturnCodes;
/*     */ import com.dreammirae.mmth.exception.ReturnCodeException;
/*     */ import com.dreammirae.mmth.external.bean.GptwrRequestParam;
/*     */ import com.dreammirae.mmth.external.bean.GptwrWebApiResponse;
/*     */ import com.dreammirae.mmth.external.service.gptwr.GptwrExtApiFidoService;
/*     */ import com.dreammirae.mmth.runtime.audit.type.AlarmLevels;
/*     */ import com.dreammirae.mmth.runtime.audit.type.AuditAlarmTypes;
/*     */ import com.dreammirae.mmth.util.StringUtils;
/*     */ import com.dreammirae.mmth.vo.ExternalServiceItemVO;
/*     */ import java.util.regex.Pattern;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.web.bind.annotation.ExceptionHandler;
/*     */ import org.springframework.web.bind.annotation.RequestBody;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ import org.springframework.web.bind.annotation.RequestMethod;
/*     */ import org.springframework.web.bind.annotation.ResponseBody;
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
/*     */ @Controller
/*     */ @RequestMapping(value = {"/extapi/fido/v1"}, consumes = {"application/json; charset=utf-8"})
/*     */ public class GptwrExtApiFidoController
/*     */ {
/*  55 */   private static final Logger LOG = LoggerFactory.getLogger(com.dreammirae.mmth.external.webapi.gptwr.GptwrExtApiFidoController.class);
/*     */   
/*     */   private static final String LOG_FORMAT_REQ_SERVICE = "## ExtAPI-RECEIVED MSG [RequestURI=%s] : Param :: %s";
/*     */   
/*     */   private static final String LOG_FORMAT_RESP_SERVICE = "## ExtAPI-SEND MSG [RequestURI=%s, LapTime=%,d ms] : result :: %s";
/*  60 */   private static final Pattern PATTERN_USERNAME = Pattern.compile("^[a-zA-Z0-9\\@\\.\\_\\-]+@[0-9]{4}$");
/*     */   
/*     */   @Autowired
/*     */   private GptwrExtApiFidoService service;
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/user_register"}, method = {RequestMethod.GET})
/*     */   @ResponseBody
/*     */   public GptwrWebApiResponse userRegister(GptwrRequestParam param, HttpServletRequest request) {
/*  69 */     long startMillis = 0L;
/*  70 */     if (LOG.isDebugEnabled()) {
/*  71 */       LOG.debug(String.format("## ExtAPI-RECEIVED MSG [RequestURI=%s] : Param :: %s", new Object[] { request.getRequestURI(), param.toString() }));
/*  72 */       startMillis = System.currentTimeMillis();
/*     */     } 
/*     */     
/*  75 */     GptwrWebApiResponse resp = new GptwrWebApiResponse();
/*     */ 
/*     */     
/*     */     try {
/*  79 */       validateUserName(param.getUserName());
/*     */       
/*  81 */       if (StringUtils.isEmpty(param.getDisplayName())) {
/*  82 */         throw new ReturnCodeException(ReturnCodes.INVALID_PARAMS, "The 'name' is required.");
/*     */       }
/*     */       
/*  85 */       if (StringUtils.isEmpty(param.getCustomerCode()) || !param.getCustomerCode().matches("^[0-9]{4}$")) {
/*  86 */         throw new ReturnCodeException(ReturnCodes.INVALID_PARAMS, "The 'costomerCode' field length must be 4.");
/*     */       }
/*     */       
/*  89 */       this.service.procRegisterUser(resp, param.getUserName(), param.getDisplayName(), param.getExtUserId(), param.getCustomerCode());
/*     */     }
/*  91 */     catch (ReturnCodeException e) {
/*  92 */       resp.setReturnCode(e.getReturnCode());
/*  93 */       resp.setErrorMessage(e.getMessage());
/*     */     } 
/*     */     
/*  96 */     if (!StringUtils.isEmpty(param.getUserName())) {
/*  97 */       AuditAlarmTypes.EXTERNAL_API.raiseAlarm(null, 2561, AlarmLevels.INFORMATION, new Object[] { param.getUserName(), resp.getReturnCode().getCode() });
/*     */     }
/*     */     
/* 100 */     if (LOG.isDebugEnabled()) {
/* 101 */       LOG.debug(String.format("## ExtAPI-SEND MSG [RequestURI=%s, LapTime=%,d ms] : result :: %s", new Object[] { request.getRequestURI(), Long.valueOf(System.currentTimeMillis() - startMillis), resp.toString() }));
/*     */     }
/*     */     
/* 104 */     return resp;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/user_remove"}, method = {RequestMethod.GET})
/*     */   @ResponseBody
/*     */   public GptwrWebApiResponse removeUser(GptwrRequestParam param, HttpServletRequest request) {
/* 112 */     long startMillis = 0L;
/* 113 */     if (LOG.isDebugEnabled()) {
/* 114 */       LOG.debug(String.format("## ExtAPI-RECEIVED MSG [RequestURI=%s] : Param :: %s", new Object[] { request.getRequestURI(), param.toString() }));
/* 115 */       startMillis = System.currentTimeMillis();
/*     */     } 
/*     */     
/* 118 */     GptwrWebApiResponse resp = new GptwrWebApiResponse();
/*     */     
/*     */     try {
/* 121 */       validateUserName(param.getUserName());
/* 122 */       this.service.procRemoveUser(resp, param.getUserName());
/*     */     }
/* 124 */     catch (ReturnCodeException e) {
/* 125 */       resp.setReturnCode(e.getReturnCode());
/* 126 */       resp.setErrorMessage(e.getMessage());
/*     */     } 
/*     */ 
/*     */     
/* 130 */     if (!StringUtils.isEmpty(param.getUserName())) {
/* 131 */       AuditAlarmTypes.EXTERNAL_API.raiseAlarm(null, 2562, AlarmLevels.INFORMATION, new Object[] { param.getUserName(), resp.getReturnCode().getCode() });
/*     */     }
/*     */     
/* 134 */     if (LOG.isDebugEnabled()) {
/* 135 */       LOG.debug(String.format("## ExtAPI-SEND MSG [RequestURI=%s, LapTime=%,d ms] : result :: %s", new Object[] { request.getRequestURI(), Long.valueOf(System.currentTimeMillis() - startMillis), resp.toString() }));
/*     */     }
/*     */     
/* 138 */     return resp;
/*     */   }
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/forceDereg"}, method = {RequestMethod.GET})
/*     */   @ResponseBody
/*     */   public GptwrWebApiResponse forceDereg(GptwrRequestParam param, HttpServletRequest request) {
/* 145 */     long startMillis = 0L;
/* 146 */     if (LOG.isDebugEnabled()) {
/* 147 */       LOG.debug(String.format("## ExtAPI-RECEIVED MSG [RequestURI=%s] : Param :: %s", new Object[] { request.getRequestURI(), param.toString() }));
/* 148 */       startMillis = System.currentTimeMillis();
/*     */     } 
/*     */     
/* 151 */     GptwrWebApiResponse resp = new GptwrWebApiResponse();
/*     */     
/*     */     try {
/* 154 */       validateUserName(param.getUserName());
/* 155 */       this.service.procForceDereg(resp, param.getUserName());
/*     */     }
/* 157 */     catch (ReturnCodeException e) {
/* 158 */       resp.setReturnCode(e.getReturnCode());
/* 159 */       resp.setErrorMessage(e.getMessage());
/*     */     } 
/*     */     
/* 162 */     if (!StringUtils.isEmpty(param.getUserName())) {
/* 163 */       AuditAlarmTypes.EXTERNAL_API.raiseAlarm(null, 2563, AlarmLevels.INFORMATION, new Object[] { param.getUserName(), resp.getReturnCode().getCode() });
/*     */     }
/*     */     
/* 166 */     if (LOG.isDebugEnabled()) {
/* 167 */       LOG.debug(String.format("## ExtAPI-SEND MSG [RequestURI=%s, LapTime=%,d ms] : result :: %s", new Object[] { request.getRequestURI(), Long.valueOf(System.currentTimeMillis() - startMillis), resp.toString() }));
/*     */     }
/*     */     
/* 170 */     return resp;
/*     */   }
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/issuecode"}, method = {RequestMethod.GET})
/*     */   @ResponseBody
/*     */   public GptwrWebApiResponse genIssuecode(GptwrRequestParam param, HttpServletRequest request) {
/* 177 */     long startMillis = 0L;
/* 178 */     if (LOG.isDebugEnabled()) {
/* 179 */       LOG.debug(String.format("## ExtAPI-RECEIVED MSG [RequestURI=%s] : Param :: %s", new Object[] { request.getRequestURI(), param.toString() }));
/* 180 */       startMillis = System.currentTimeMillis();
/*     */     } 
/*     */     
/* 183 */     GptwrWebApiResponse resp = new GptwrWebApiResponse();
/*     */     
/*     */     try {
/* 186 */       validateUserName(param.getUserName());
/* 187 */       this.service.procGenIssueCode(resp, param);
/*     */     }
/* 189 */     catch (ReturnCodeException e) {
/* 190 */       resp.setReturnCode(e.getReturnCode());
/* 191 */       resp.setErrorMessage(e.getMessage());
/*     */     } 
/*     */     
/* 194 */     if (!StringUtils.isEmpty(param.getUserName())) {
/* 195 */       AuditAlarmTypes.EXTERNAL_API.raiseAlarm(null, 2564, AlarmLevels.INFORMATION, new Object[] { param.getUserName(), resp.getIssueCode(), resp.getReturnCode().getCode() });
/*     */     }
/*     */     
/* 198 */     if (LOG.isDebugEnabled()) {
/* 199 */       LOG.debug(String.format("## ExtAPI-SEND MSG [RequestURI=%s, LapTime=%,d ms] : result :: %s", new Object[] { request.getRequestURI(), Long.valueOf(System.currentTimeMillis() - startMillis), resp.toString() }));
/*     */     }
/*     */     
/* 202 */     return resp;
/*     */   }
/*     */   
/*     */   @RequestMapping(value = {"/push_notification"}, method = {RequestMethod.POST})
/*     */   @ResponseBody
/*     */   public GptwrWebApiResponse notification(@RequestBody ExternalServiceItemVO param, HttpServletRequest request) {
/* 208 */     long startMillis = 0L;
/* 209 */     if (LOG.isDebugEnabled()) {
/* 210 */       LOG.debug(String.format("## ExtAPI-RECEIVED MSG [RequestURI=%s] : Param :: %s", new Object[] { request.getRequestURI(), param }));
/* 211 */       startMillis = System.currentTimeMillis();
/*     */     } 
/*     */     
/* 214 */     GptwrWebApiResponse resp = new GptwrWebApiResponse();
/*     */ 
/*     */     
/*     */     try {
/* 218 */       param.validate();
/* 219 */       this.service.procNotification(resp, param);
/*     */     }
/* 221 */     catch (ReturnCodeException e) {
/* 222 */       resp.setReturnCode(e.getReturnCode());
/* 223 */       resp.setErrorMessage(e.getMessage());
/*     */     } 
/*     */     
/* 226 */     if (LOG.isDebugEnabled()) {
/* 227 */       LOG.debug(String.format("## ExtAPI-SEND MSG [RequestURI=%s, LapTime=%,d ms] : result :: %s", new Object[] { request.getRequestURI(), Long.valueOf(System.currentTimeMillis() - startMillis), resp.toString() }));
/*     */     }
/*     */     
/* 230 */     return resp;
/*     */   }
/*     */ 
/*     */   
/*     */   private void validateUserName(String userName) {
/* 235 */     if (StringUtils.isEmpty(userName)) {
/* 236 */       throw new ReturnCodeException(ReturnCodes.INVALID_PARAMS, "The 'userno' is required.");
/*     */     }
/*     */     
/* 239 */     if (!PATTERN_USERNAME.matcher(userName).matches()) {
/* 240 */       throw new ReturnCodeException(ReturnCodes.INVALID_PARAMS, "The 'userno' format is invalid.");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @ExceptionHandler({Throwable.class})
/*     */   @ResponseBody
/*     */   public GptwrWebApiResponse handleError(HttpServletRequest request, Throwable e) {
/* 248 */     GptwrWebApiResponse resp = new GptwrWebApiResponse();
/* 249 */     resp.setReturnCode(ReturnCodes.INTERNAL_SERVER_ERROR);
/* 250 */     resp.setErrorMessage(e.getMessage());
/* 251 */     return resp;
/*     */   }
/*     */   
/*     */   public static void main(String[] args) {
/* 255 */     System.out.println(System.currentTimeMillis() + 3600000L);
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\external\webapi\gptwr\GptwrExtApiFidoController.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */