/*     */ package WEB-INF.classes.com.dreammirae.mmth.authentication.transport;
/*     */ 
/*     */ import com.dreammirae.mmth.authentication.transport.BizReqData;
/*     */ import com.dreammirae.mmth.misc.Base64Utils;
/*     */ import com.dreammirae.mmth.misc.SysEnvCommon;
/*     */ import com.dreammirae.mmth.parser.json.GsonUtils;
/*     */ import com.dreammirae.mmth.util.StringUtils;
/*     */ import java.util.Collections;
/*     */ import java.util.Enumeration;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import javax.servlet.ServletInputStream;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletRequestWrapper;
/*     */ 
/*     */ public class BizDataConverterRequestWrapper
/*     */   extends HttpServletRequestWrapper
/*     */ {
/*     */   private Map<String, String[]> parameterMap;
/*     */   
/*     */   public BizDataConverterRequestWrapper(HttpServletRequest request) {
/*  22 */     super(request);
/*  23 */     parseRequest(request);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void parseRequest(HttpServletRequest request) {
/*  29 */     int contentLen = getContentLength();
/*     */     
/*  31 */     if (contentLen > 0) {
/*     */       
/*  33 */       this.parameterMap = (Map)new HashMap<>(request.getParameterMap());
/*     */ 
/*     */       
/*     */       try {
/*  37 */         byte[] payload = new byte[contentLen];
/*  38 */         ServletInputStream servletInputStream = getInputStream();
/*     */         
/*  40 */         servletInputStream.read(payload, 0, contentLen);
/*     */         
/*  42 */         String jsonData = SysEnvCommon.toUtf8(payload);
/*     */         
/*  44 */         BizReqData reqData = (BizReqData)GsonUtils.gson().fromJson(jsonData, BizReqData.class);
/*     */         
/*  46 */         if (!StringUtils.isEmpty(reqData.getUserName())) {
/*  47 */           this.parameterMap.put("userName", new String[] { reqData.getUserName() });
/*     */         }
/*     */         
/*  50 */         if (!StringUtils.isEmpty(reqData.getTid())) {
/*  51 */           this.parameterMap.put("tid", new String[] { reqData.getTid() });
/*     */         }
/*     */         
/*  54 */         if (!StringUtils.isEmpty(reqData.getOtp())) {
/*  55 */           this.parameterMap.put("otp", new String[] { reqData.getOtp() });
/*     */         }
/*     */         
/*  58 */         if (!StringUtils.isEmpty(reqData.getDeviceId())) {
/*  59 */           this.parameterMap.put("deviceId", new String[] { reqData.getDeviceId() });
/*     */         }
/*     */         
/*  62 */         if (!StringUtils.isEmpty(reqData.getServerPin())) {
/*  63 */           this.parameterMap.put("serverPin", new String[] { reqData.getServerPin() });
/*     */         }
/*     */         
/*  66 */         if (!StringUtils.isEmpty(reqData.getNewServerPin())) {
/*  67 */           this.parameterMap.put("newServerPin", new String[] { reqData.getNewServerPin() });
/*     */         }
/*     */       }
/*  70 */       catch (Exception e) {}
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  75 */     if (SysEnvCommon.applyEnvCaseInsensitive()) {
/*     */       
/*  77 */       if (this.parameterMap == null) {
/*  78 */         this.parameterMap = (Map)new HashMap<>(request.getParameterMap());
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*  83 */       if (SysEnvCommon.getEnvCaseInsensitiveKeys() != null) {
/*  84 */         for (String key : SysEnvCommon.getEnvCaseInsensitiveKeys()) {
/*  85 */           String[] values = this.parameterMap.get(key);
/*     */           
/*  87 */           if (values != null) {
/*  88 */             values[0] = values[0].toLowerCase();
/*  89 */             this.parameterMap.put(key, values);
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/*  96 */     String tid = request.getParameter("tid");
/*     */     
/*  98 */     if (StringUtils.isEmpty(tid)) {
/*     */       return;
/*     */     }
/*     */     
/* 102 */     if (this.parameterMap == null) {
/* 103 */       this.parameterMap = (Map)new HashMap<>(request.getParameterMap());
/*     */     }
/*     */ 
/*     */     
/* 107 */     this.parameterMap.put("tid", new String[] { Base64Utils.decode(tid) });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getParameter(String name) {
/* 114 */     if (this.parameterMap == null) {
/* 115 */       return super.getParameter(name);
/*     */     }
/*     */     
/* 118 */     String[] values = this.parameterMap.get(name);
/* 119 */     return values[0];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<String, String[]> getParameterMap() {
/* 125 */     if (this.parameterMap == null) {
/* 126 */       return super.getParameterMap();
/*     */     }
/*     */     
/* 129 */     return (Map)Collections.unmodifiableMap((Map)this.parameterMap);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Enumeration<String> getParameterNames() {
/* 135 */     if (this.parameterMap == null) {
/* 136 */       return super.getParameterNames();
/*     */     }
/*     */     
/* 139 */     return Collections.enumeration(this.parameterMap.keySet());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] getParameterValues(String name) {
/* 145 */     if (this.parameterMap == null) {
/* 146 */       return super.getParameterValues(name);
/*     */     }
/*     */     
/* 149 */     return this.parameterMap.get(name);
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\authentication\transport\BizDataConverterRequestWrapper.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */