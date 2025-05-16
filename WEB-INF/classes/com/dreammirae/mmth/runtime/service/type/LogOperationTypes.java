/*     */ package WEB-INF.classes.com.dreammirae.mmth.runtime.service.type;
/*     */ 
/*     */ import com.dreammirae.mmth.authentication.AuthMethodTypes;
/*     */ import com.dreammirae.mmth.authentication.ReturnCodes;
/*     */ import com.dreammirae.mmth.config.Commons;
/*     */ import com.dreammirae.mmth.runtime.service.type.LogActionTypes;
/*     */ import com.dreammirae.mmth.runtime.service.type.OpRequstTypes;
/*     */ import com.dreammirae.mmth.util.bean.KeyValuePair;
/*     */ import com.dreammirae.mmth.vo.DeviceAppAgentVO;
/*     */ import com.dreammirae.mmth.vo.ServiceLogVO;
/*     */ import com.dreammirae.mmth.vo.bean.ICustomLogData;
/*     */ import com.dreammirae.mmth.vo.types.DeviceTypes;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
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
/*     */ public enum LogOperationTypes
/*     */ {
/*  37 */   ISSUE_CODE(4097, "LogOperationTypes.ISSUE_CODE"),
/*  38 */   ISSUE_CODE_ONLINE(4098, "LogOperationTypes.ISSUE_CODE_ONLINE"),
/*  39 */   REG(8193, "LogOperationTypes.REG"),
/*  40 */   LOGIN_BIOMETRIC(8194, "LogOperationTypes.LOGIN_BIOMETRIC"),
/*  41 */   LOGIN_IDPW(8195, "LogOperationTypes.LOGIN_IDPW"),
/*     */   
/*  43 */   AUTH_FIDO(16385, "LogOperationTypes.AUTH_FIDO"),
/*     */ 
/*     */   
/*  46 */   AUTH_BIOTP(16386, "LogOperationTypes.AUTH_BIOTP"),
/*     */ 
/*     */   
/*  49 */   AUTH_SAOTP(16387, "LogOperationTypes.AUTH_SAOTP"),
/*     */   
/*  51 */   FIDO_AUTH(16388, "LogOperationTypes.FIDO_AUTH"),
/*     */   
/*  53 */   AUTH(16393, "LogOperationTypes.AUTH"),
/*  54 */   OTP_AUTH(16389, "LogOperationTypes.OTP_AUTH"),
/*  55 */   AUTH_BIOMETRIC(16392, "LogOperationTypes.AUTH_BIOMETRIC"),
/*  56 */   AUTH_PIN(16400, "LogOperationTypes.AUTH_PIN"),
/*  57 */   AUTH_PATTERN(16401, "LogOperationTypes.AUTH_PATTERN"),
/*     */   
/*  59 */   LOST(20481, "LogOperationTypes.LOST"),
/*     */   
/*  61 */   RECOVERY(20485, "LogOperationTypes.RECOVERY"),
/*     */   
/*  63 */   DEREG(12289, "LogOperationTypes.DEREG"),
/*  64 */   FORCE_DEREG(12290, "LogOperationTypes.FORCE_DEREG"),
/*  65 */   FORCE_DEREG_FOR_LOCAL_FAILED(12291, "LogOperationTypes.FORCE_DEREG_FOR_LOCAL_FAILED"),
/*     */ 
/*     */   
/*  68 */   RESET_AUTH_FAIL(20482, "LogOperationTypes.RESET_AUTH_FAIL"),
/*     */   
/*  70 */   UPDATE_USER_VERIFICATION_FLAG(20483, "LogOperationTypes.UPDATE_USER_VERIFICATION_FLAG");
/*     */   
/*     */   private final int code;
/*     */   
/*     */   private final String messageKey;
/*     */   
/*     */   LogOperationTypes(int code, String messageKey) {
/*  77 */     this.code = code;
/*  78 */     this.messageKey = messageKey;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCode() {
/*  85 */     return this.code;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMessageKey() {
/*  92 */     return this.messageKey;
/*     */   }
/*     */   
/*     */   public String getHexCode() {
/*  96 */     return Integer.toHexString(this.code);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addServiceLog(AuthMethodTypes type, String username, OpRequstTypes req, LogActionTypes action, ReturnCodes code, String deviceId, DeviceTypes deviceType, String pkgName, String aaid, String tokenId, String description) {
/* 103 */     ServiceLogVO vo = createServiceLogVO(type, username, req, action, code, deviceId, deviceType, pkgName, aaid, tokenId, description, null);
/*     */ 
/*     */     
/* 106 */     addServiceLog(vo);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addServiceLog(AuthMethodTypes type, String username, OpRequstTypes req, LogActionTypes action, ReturnCodes code, String deviceId, DeviceTypes deviceType, String pkgName, String aaid, String tokenId, String description, ICustomLogData logData) {
/* 113 */     ServiceLogVO vo = createServiceLogVO(type, username, req, action, code, deviceId, deviceType, pkgName, aaid, tokenId, description, logData);
/*     */ 
/*     */     
/* 116 */     addServiceLog(vo);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void addServiceLog(AuthMethodTypes type, String username, OpRequstTypes req, LogActionTypes action, ReturnCodes code, String aaid, String tokenId, DeviceAppAgentVO deviceAppAgent, String description) {
/* 122 */     ServiceLogVO vo = createServiceLogVO(type, username, req, action, code, null, null, null, aaid, tokenId, description, null);
/*     */ 
/*     */     
/* 125 */     addServiceLog(vo);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void addServiceLog(AuthMethodTypes type, String username, OpRequstTypes req, LogActionTypes action, ReturnCodes code, String aaid, String tokenId, DeviceAppAgentVO deviceAppAgent, String description, ICustomLogData logData) {
/* 131 */     ServiceLogVO vo = createServiceLogVO(type, username, req, action, code, null, null, null, aaid, tokenId, description, logData);
/*     */ 
/*     */     
/* 134 */     addServiceLog(vo);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void addServiceLog(AuthMethodTypes type, String username, OpRequstTypes req, LogActionTypes action, ReturnCodes code, String description) {
/* 140 */     ServiceLogVO vo = createServiceLogVO(type, username, req, action, code, null, null, null, null, null, description, null);
/*     */     
/* 142 */     addServiceLog(vo);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void addServiceLog(AuthMethodTypes type, String username, OpRequstTypes req, LogActionTypes action, ReturnCodes code, String description, ICustomLogData logData) {
/* 148 */     ServiceLogVO vo = createServiceLogVO(type, username, req, action, code, null, null, null, null, null, description, logData);
/*     */     
/* 150 */     addServiceLog(vo);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void addServiceLog(AuthMethodTypes type, String username, OpRequstTypes req, LogActionTypes action, ReturnCodes code, DeviceAppAgentVO deviceAppAgent, String description) {
/* 156 */     ServiceLogVO vo = createServiceLogVO(type, username, req, action, code, null, null, null, null, null, description, null);
/*     */ 
/*     */     
/* 159 */     vo.setDeviceAppAgent(deviceAppAgent);
/* 160 */     addServiceLog(vo);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void addServiceLog(AuthMethodTypes type, String username, OpRequstTypes req, LogActionTypes action, ReturnCodes code, DeviceAppAgentVO deviceAppAgent, String description, ICustomLogData logData) {
/* 166 */     ServiceLogVO vo = createServiceLogVO(type, username, req, action, code, null, null, null, null, null, description, logData);
/*     */ 
/*     */     
/* 169 */     vo.setDeviceAppAgent(deviceAppAgent);
/* 170 */     addServiceLog(vo);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ServiceLogVO createServiceLogVO(AuthMethodTypes type, String username, OpRequstTypes req, LogActionTypes action, ReturnCodes code, String deviceId, DeviceTypes deviceType, String pkgName, String aaid, String tokenId, String description, ICustomLogData logData) {
/* 177 */     ServiceLogVO vo = new ServiceLogVO();
/* 178 */     vo.setUsername(username);
/* 179 */     vo.setAuthType(type);
/* 180 */     vo.setOpType(this);
/* 181 */     vo.setReqType(req);
/* 182 */     vo.setActionType(action);
/* 183 */     vo.setReturnCode(code);
/* 184 */     vo.setDeviceId(deviceId);
/* 185 */     vo.setDeviceType(deviceType);
/* 186 */     vo.setPkgUnique(pkgName);
/* 187 */     vo.setAaid(aaid);
/* 188 */     vo.setTokenId(tokenId);
/* 189 */     vo.setDescription(description);
/* 190 */     vo.setCustomData(logData);
/* 191 */     vo.setTsReg(Long.valueOf(System.currentTimeMillis()));
/* 192 */     vo.setRegDate(Commons.getFormatDate(vo.getTsReg().longValue()));
/* 193 */     vo.setRegHour(Commons.getFormatDateHour(vo.getTsReg().longValue()));
/*     */     
/* 195 */     return vo;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void addServiceLog(ServiceLogVO log) {
/* 201 */     if (log == null) {
/*     */       return;
/*     */     }
/*     */     
/* 205 */     if (log.getUsername() == null) {
/*     */       return;
/*     */     }
/*     */     
/*     */     try {
/* 210 */       Commons.ctx.getServiceLogsManager().addServiceLog(log);
/* 211 */     } catch (Exception ignore) {
/* 212 */       ignore.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static com.dreammirae.mmth.runtime.service.type.LogOperationTypes getRegOperationType(int code) {
/* 218 */     for (com.dreammirae.mmth.runtime.service.type.LogOperationTypes type : values()) {
/* 219 */       if (type.code == code) {
/* 220 */         return type;
/*     */       }
/*     */     } 
/* 223 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public static List<KeyValuePair<String, String>> getMessageKeyPair(com.dreammirae.mmth.runtime.service.type.LogOperationTypes... excludes) {
/* 228 */     List<KeyValuePair<String, String>> list = new ArrayList<>();
/*     */     
/* 230 */     for (com.dreammirae.mmth.runtime.service.type.LogOperationTypes type : values()) {
/*     */       
/* 232 */       com.dreammirae.mmth.runtime.service.type.LogOperationTypes[] arrayOfLogOperationTypes = excludes; int i = arrayOfLogOperationTypes.length; byte b = 0; while (true) { if (b < i) { com.dreammirae.mmth.runtime.service.type.LogOperationTypes exclude = arrayOfLogOperationTypes[b];
/* 233 */           if (type.equals(exclude))
/*     */             break; 
/*     */           b++;
/*     */           continue; }
/*     */         
/* 238 */         list.add(new KeyValuePair(type.name(), type.getMessageKey())); break; }
/*     */     
/*     */     } 
/* 241 */     return list;
/*     */   }
/*     */   
/*     */   public static com.dreammirae.mmth.runtime.service.type.LogOperationTypes getRegOperationTypeByName(String name) {
/* 245 */     for (com.dreammirae.mmth.runtime.service.type.LogOperationTypes type : values()) {
/* 246 */       if (type.name().equalsIgnoreCase(name)) {
/* 247 */         return type;
/*     */       }
/*     */     } 
/* 250 */     return null;
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\runtime\service\type\LogOperationTypes.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */