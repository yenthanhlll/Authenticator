/*     */ package WEB-INF.classes.com.dreammirae.mmth.external.bean;
/*     */ 
/*     */ import com.dreammirae.mmth.vo.bean.ICustomLogData;
/*     */ import com.dreammirae.mmth.vo.bean.MiraeAssetVietnamLogData;
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
/*     */ public class WebApiRequestParam
/*     */ {
/*     */   public static final int DEFAULT_LIMIT = 100;
/*     */   private String userName;
/*     */   private String otp;
/*     */   private String tid;
/*     */   private String tranInfo;
/*     */   private Integer limit;
/*     */   private String deviceId;
/*     */   private DeviceTypes deviceType;
/*     */   private String qrSessionId;
/*     */   private String productTypeCode;
/*     */   private String multiLoginYN;
/*     */   private String macAddress;
/*     */   private long ip;
/*     */   private String accountName;
/*     */   
/*     */   public String getUserName() {
/*  34 */     return this.userName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUserName(String userName) {
/*  41 */     this.userName = userName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getOtp() {
/*  48 */     return this.otp;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOtp(String otp) {
/*  55 */     this.otp = otp;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTid() {
/*  62 */     return this.tid;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTid(String tid) {
/*  69 */     this.tid = tid;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTranInfo() {
/*  76 */     return this.tranInfo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTranInfo(String tranInfo) {
/*  83 */     this.tranInfo = tranInfo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer getLimit() {
/*  90 */     return this.limit;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLimit(Integer limit) {
/*  97 */     this.limit = limit;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDeviceId() {
/* 106 */     return this.deviceId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDeviceId(String deviceId) {
/* 113 */     this.deviceId = deviceId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DeviceTypes getDeviceType() {
/* 120 */     return this.deviceType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDeviceType(DeviceTypes deviceType) {
/* 127 */     this.deviceType = deviceType;
/*     */   }
/*     */   
/*     */   public String getQrSessionId() {
/* 131 */     return this.qrSessionId;
/*     */   }
/*     */   
/*     */   public void setQrSessionId(String qrSessionId) {
/* 135 */     this.qrSessionId = qrSessionId;
/*     */   }
/*     */ 
/*     */   
/*     */   public ICustomLogData createCustomLogData() {
/* 140 */     MiraeAssetVietnamLogData logData = new MiraeAssetVietnamLogData();
/*     */ 
/*     */ 
/*     */     
/* 144 */     logData.setMacAddress(this.macAddress);
/* 145 */     logData.setIp(this.ip);
/*     */     
/* 147 */     return (ICustomLogData)logData;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getProductTypeCode() {
/* 155 */     return this.productTypeCode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setProductTypeCode(String productTypeCode) {
/* 162 */     this.productTypeCode = productTypeCode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMultiLoginYN() {
/* 171 */     return this.multiLoginYN;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMultiLoginYN(String multiLoginYN) {
/* 178 */     this.multiLoginYN = multiLoginYN;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMacAddress() {
/* 185 */     return this.macAddress;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMacAddress(String macAddress) {
/* 192 */     this.macAddress = macAddress;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getIp() {
/* 199 */     return this.ip;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setIp(long ip) {
/* 206 */     this.ip = ip;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getAccountName() {
/* 213 */     return this.accountName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAccountName(String accountName) {
/* 220 */     this.accountName = accountName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 228 */     StringBuilder builder = new StringBuilder();
/* 229 */     builder.append("WebApiRequestParam [userName=").append(this.userName).append(", otp=").append(this.otp).append(", tid=").append(this.tid).append(", tranInfo=").append(this.tranInfo).append(", productTypeCode=").append(this.productTypeCode).append(", multiLoginYN=").append(this.multiLoginYN)
/* 230 */       .append(", macAddress=").append(this.macAddress).append(", ip=").append(this.ip).append(", accountName=").append(this.accountName).append("]");
/*     */     
/* 232 */     return builder.toString();
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\external\bean\WebApiRequestParam.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */