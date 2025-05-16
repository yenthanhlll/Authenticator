/*     */ package WEB-INF.classes.com.dreammirae.mmth.authentication.transport;
/*     */ 
/*     */ import com.dreammirae.mmth.authentication.ReturnCodes;
/*     */ import com.dreammirae.mmth.exception.ReturnCodeException;
/*     */ import com.dreammirae.mmth.fido.Operation;
/*     */ import com.dreammirae.mmth.util.StringUtils;
/*     */ import com.dreammirae.mmth.util.io.HexUtils;
/*     */ import com.dreammirae.mmth.vo.types.DeviceTypes;
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
/*     */ public class SaotpRequestParam
/*     */ {
/*     */   private String userName;
/*     */   private DeviceTypes deviceType;
/*     */   private String deviceId;
/*     */   private String serverPin;
/*     */   private byte[] otpPub;
/*     */   private byte[] rndSeedKey;
/*     */   private byte[] rndChallenge;
/*     */   private String tranInfo;
/*     */   private String issueCode;
/*     */   private String model;
/*     */   private String alias;
/*     */   
/*     */   public String getUserName() {
/*  49 */     return this.userName;
/*     */   }
/*     */   
/*     */   public void setUserName(String userName) {
/*  53 */     this.userName = userName;
/*     */   }
/*     */   
/*     */   public DeviceTypes getDeviceType() {
/*  57 */     return this.deviceType;
/*     */   }
/*     */   
/*     */   public void setDeviceType(DeviceTypes deviceType) {
/*  61 */     this.deviceType = deviceType;
/*     */   }
/*     */   
/*     */   public String getDeviceId() {
/*  65 */     return this.deviceId;
/*     */   }
/*     */   
/*     */   public void setDeviceId(String deviceId) {
/*  69 */     this.deviceId = deviceId;
/*     */   }
/*     */   
/*     */   public byte[] getOtpPub() {
/*  73 */     return this.otpPub;
/*     */   }
/*     */   
/*     */   public void setOtpPub(byte[] otpPub) {
/*  77 */     this.otpPub = otpPub;
/*     */   }
/*     */   
/*     */   public byte[] getRndSeedKey() {
/*  81 */     return this.rndSeedKey;
/*     */   }
/*     */   
/*     */   public void setRndSeedKey(byte[] rndSeedKey) {
/*  85 */     this.rndSeedKey = rndSeedKey;
/*     */   }
/*     */   
/*     */   public byte[] getRndChallenge() {
/*  89 */     return this.rndChallenge;
/*     */   }
/*     */   
/*     */   public void setRndChallenge(byte[] rndChallenge) {
/*  93 */     this.rndChallenge = rndChallenge;
/*     */   }
/*     */   
/*     */   public String getServerPin() {
/*  97 */     return this.serverPin;
/*     */   }
/*     */   
/*     */   public void setServerPin(String serverPin) {
/* 101 */     this.serverPin = serverPin;
/*     */   }
/*     */   
/*     */   public String getIssueCode() {
/* 105 */     return this.issueCode;
/*     */   }
/*     */   
/*     */   public void setIssueCode(String issueCode) {
/* 109 */     this.issueCode = issueCode;
/*     */   }
/*     */   
/*     */   public String getTranInfo() {
/* 113 */     return this.tranInfo;
/*     */   }
/*     */   
/*     */   public void setTranInfo(String tranInfo) {
/* 117 */     this.tranInfo = tranInfo;
/*     */   }
/*     */   
/*     */   public String getModel() {
/* 121 */     return this.model;
/*     */   }
/*     */   
/*     */   public void setModel(String model) {
/* 125 */     this.model = model;
/*     */   }
/*     */   
/*     */   public String getAlias() {
/* 129 */     return this.alias;
/*     */   }
/*     */   
/*     */   public void setAlias(String alias) {
/* 133 */     this.alias = alias;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void validate(Operation op) throws ReturnCodeException {
/* 139 */     if (StringUtils.isEmpty(this.userName)) {
/* 140 */       throw new ReturnCodeException(ReturnCodes.INVALID_PARAMS, "[userName] is required.");
/*     */     }
/*     */     
/* 143 */     if (StringUtils.isEmpty(this.deviceId)) {
/* 144 */       throw new ReturnCodeException(ReturnCodes.INVALID_PARAMS, "[deviceId] is required.");
/*     */     }
/*     */     
/* 147 */     if (this.deviceType == null) {
/* 148 */       throw new ReturnCodeException(ReturnCodes.INVALID_PARAMS, "[deviceType] is required and only accepted 'A' or 'I'.");
/*     */     }
/*     */     
/* 151 */     if (Operation.Reg.equals(op)) {
/* 152 */       if (StringUtils.isEmpty(this.serverPin)) {
/* 153 */         throw new ReturnCodeException(ReturnCodes.INVALID_PARAMS, "[serverPin] is required when op=Reg.");
/*     */       }
/*     */       
/* 156 */       if (this.otpPub == null) {
/* 157 */         throw new ReturnCodeException(ReturnCodes.INVALID_PARAMS, "[otpPub] is required when op=Reg.");
/*     */       }
/*     */       
/* 160 */       if (this.rndSeedKey != null && 
/* 161 */         this.rndChallenge == null) {
/* 162 */         throw new ReturnCodeException(ReturnCodes.INVALID_PARAMS, "[rndChallenge] is required when rndSeedKey is not null.");
/*     */       
/*     */       }
/*     */     }
/* 166 */     else if (Operation.Auth.equals(op) && 
/* 167 */       StringUtils.isEmpty(this.serverPin)) {
/* 168 */       throw new ReturnCodeException(ReturnCodes.INVALID_PARAMS, "[serverPin] is required.");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 177 */     StringBuilder builder = new StringBuilder();
/* 178 */     builder.append("SaoParam [userName=").append(this.userName).append(", deviceType=").append(this.deviceType)
/* 179 */       .append(", deviceId=").append(this.deviceId).append(", otpPub=").append(HexUtils.toHexString(this.otpPub)).append(", rndSeedKey=")
/* 180 */       .append(HexUtils.toHexString(this.rndSeedKey)).append(", rndChallenge=").append(HexUtils.toHexString(this.rndChallenge)).append(", serverPin=")
/* 181 */       .append(this.serverPin).append(", issueCode=").append(this.issueCode).append("]");
/* 182 */     return builder.toString();
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\authentication\transport\SaotpRequestParam.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */