/*     */ package WEB-INF.classes.com.dreammirae.mmth.external.bean;
/*     */ 
/*     */ import com.dreammirae.mmth.authentication.ReturnCodes;
/*     */ import com.dreammirae.mmth.authentication.bean.AuthenticationInfos;
/*     */ import com.dreammirae.mmth.external.bean.IssueCodeApiData;
/*     */ import com.dreammirae.mmth.external.bean.UserStatusBean;
/*     */ import com.dreammirae.mmth.util.StringUtils;
/*     */ import com.dreammirae.mmth.vo.ServiceLogVO;
/*     */ import com.dreammirae.mmth.vo.bean.TokenStats;
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
/*     */ public class WebApiResponse
/*     */ {
/*     */   private ReturnCodes returnCode;
/*     */   private String issueCode;
/*     */   private Long issueCodeRegDateTime;
/*     */   private Long issueCodeExpired;
/*     */   private Integer issueCodeFailCnt;
/*     */   private Boolean issueCodeExpiredYn;
/*     */   private Boolean issueCodeFailExceedYn;
/*     */   private TokenStats tokenStats;
/*     */   private AuthenticationInfos authInfo;
/*     */   private UserStatusBean userStatus;
/*     */   private List<ServiceLogVO> serviceLogs;
/*     */   private IssueCodeApiData issueCodeData;
/*     */   private String qrData;
/*     */   private Integer expiredDuration;
/*     */   private String tid;
/*     */   private String userName;
/*     */   private Integer productType;
/*     */   private String multiLoginYN;
/*     */   private String status;
/*     */   private String accountName;
/*     */   
/*     */   public ReturnCodes getReturnCode() {
/*  45 */     return this.returnCode;
/*     */   }
/*     */   
/*     */   public void setReturnCode(ReturnCodes returnCode) {
/*  49 */     this.returnCode = returnCode;
/*     */   }
/*     */   
/*     */   public String getIssueCode() {
/*  53 */     return this.issueCode;
/*     */   }
/*     */   
/*     */   public void setIssueCode(String issueCode) {
/*  57 */     this.issueCode = issueCode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Long getIssueCodeRegDateTime() {
/*  64 */     return this.issueCodeRegDateTime;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setIssueCodeRegDateTime(Long issueCodeRegDateTime) {
/*  71 */     this.issueCodeRegDateTime = issueCodeRegDateTime;
/*     */   }
/*     */   
/*     */   public Long getIssueCodeExpired() {
/*  75 */     return this.issueCodeExpired;
/*     */   }
/*     */   
/*     */   public void setIssueCodeExpired(Long issueCodeExpired) {
/*  79 */     this.issueCodeExpired = issueCodeExpired;
/*     */   }
/*     */   
/*     */   public Integer getIssueCodeFailCnt() {
/*  83 */     return this.issueCodeFailCnt;
/*     */   }
/*     */   
/*     */   public void setIssueCodeFailCnt(Integer issueCodeFailCnt) {
/*  87 */     this.issueCodeFailCnt = issueCodeFailCnt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Boolean getIssueCodeFailExceedYn() {
/*  94 */     return this.issueCodeFailExceedYn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setIssueCodeFailExceedYn(Boolean issueCodeFailExceedYn) {
/* 101 */     this.issueCodeFailExceedYn = issueCodeFailExceedYn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Boolean getIssueCodeExpiredYn() {
/* 108 */     return this.issueCodeExpiredYn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setIssueCodeExpiredYn(Boolean issueCodeExpiredYn) {
/* 115 */     this.issueCodeExpiredYn = issueCodeExpiredYn;
/*     */   }
/*     */   
/*     */   public TokenStats getTokenStats() {
/* 119 */     return this.tokenStats;
/*     */   }
/*     */   
/*     */   public void setTokenStats(TokenStats tokenStats) {
/* 123 */     this.tokenStats = tokenStats;
/*     */   }
/*     */   
/*     */   public AuthenticationInfos getAuthInfo() {
/* 127 */     return this.authInfo;
/*     */   }
/*     */   
/*     */   public void setAuthInfo(AuthenticationInfos authInfo) {
/* 131 */     this.authInfo = authInfo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UserStatusBean getUserStatus() {
/* 138 */     return this.userStatus;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUserStatus(UserStatusBean userStatus) {
/* 145 */     this.userStatus = userStatus;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<ServiceLogVO> getServiceLogs() {
/* 152 */     return this.serviceLogs;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setServiceLogs(List<ServiceLogVO> serviceLogs) {
/* 159 */     this.serviceLogs = serviceLogs;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IssueCodeApiData getIssueCodeData() {
/* 166 */     return this.issueCodeData;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setIssueCodeData(IssueCodeApiData issueCodeData) {
/* 173 */     this.issueCodeData = issueCodeData;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getQrData() {
/* 180 */     return this.qrData;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setQrData(String qrData) {
/* 187 */     this.qrData = qrData;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer getExpiredDuration() {
/* 194 */     return this.expiredDuration;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setExpiredDuration(Integer expiredDuration) {
/* 201 */     this.expiredDuration = expiredDuration;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTid() {
/* 208 */     return this.tid;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTid(String tid) {
/* 215 */     this.tid = tid;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getUserName() {
/* 223 */     return this.userName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUserName(String userName) {
/* 230 */     this.userName = userName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer getProductType() {
/* 237 */     return this.productType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setProductType(Integer productType) {
/* 244 */     this.productType = productType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMultiLoginYN() {
/* 251 */     return this.multiLoginYN;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMultiLoginYN(String multiLoginYN) {
/* 258 */     this.multiLoginYN = multiLoginYN;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getStatus() {
/* 265 */     return this.status;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setStatus(String status) {
/* 272 */     this.status = status;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getAccountName() {
/* 279 */     return this.accountName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAccountName(String accountName) {
/* 286 */     this.accountName = accountName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 294 */     StringBuilder sb = new StringBuilder();
/* 295 */     sb.append("{")
/* 296 */       .append("returnCode : ").append((this.returnCode != null) ? this.returnCode.getCode() : null);
/*     */     
/* 298 */     if (this.authInfo != null) {
/* 299 */       sb.append(", authFailCnt : ").append(this.authInfo.getFailCnt());
/*     */     }
/*     */     
/* 302 */     if (!StringUtils.isEmpty(this.issueCode)) {
/* 303 */       sb.append(", issueCode : ").append(this.issueCode);
/*     */     }
/*     */     
/* 306 */     if (this.issueCodeRegDateTime != null) {
/* 307 */       sb.append(", issueCodeRegDateTime : ").append(this.issueCodeRegDateTime);
/*     */     }
/*     */     
/* 310 */     if (this.issueCodeExpired != null) {
/* 311 */       sb.append(", issueCodeExpired : ").append(this.issueCodeExpired);
/*     */     }
/*     */     
/* 314 */     if (this.issueCodeFailCnt != null) {
/* 315 */       sb.append(", issueCodeFailCnt : ").append(this.issueCodeFailCnt);
/*     */     }
/*     */     
/* 318 */     if (this.issueCodeExpiredYn != null) {
/* 319 */       sb.append(", issueCodeExpiredYn : ").append(this.issueCodeExpiredYn.booleanValue() ? "Y" : "N");
/*     */     }
/*     */     
/* 322 */     if (this.issueCodeFailExceedYn != null) {
/* 323 */       sb.append(", issueCodeFailExceedYn : ").append(this.issueCodeFailExceedYn.booleanValue() ? "Y" : "N");
/*     */     }
/*     */ 
/*     */     
/* 327 */     if (this.expiredDuration != null) {
/* 328 */       sb.append(", expiredDuration : ").append(this.expiredDuration);
/*     */     }
/*     */     
/* 331 */     if (!StringUtils.isEmpty(this.qrData)) {
/* 332 */       sb.append(", qrData : ").append(this.qrData);
/*     */     }
/*     */     
/* 335 */     if (!StringUtils.isEmpty(this.tid)) {
/* 336 */       sb.append(", tid : ").append(this.tid);
/*     */     }
/*     */ 
/*     */     
/* 340 */     if (this.userStatus != null) {
/*     */       
/* 342 */       sb.append(", data : {");
/*     */       
/* 344 */       if (!StringUtils.isEmpty(this.userStatus.getStatus())) {
/* 345 */         sb.append("status : ").append(this.userStatus.getStatus());
/*     */       }
/*     */       
/* 348 */       if (this.userStatus.getLostYn() != null) {
/* 349 */         sb.append(", lostYn : ").append(this.userStatus.getLostYn());
/*     */       }
/*     */       
/* 352 */       if (!StringUtils.isEmpty(this.userStatus.getOtpSerialNumber())) {
/* 353 */         sb.append(", otpSerialNumber : ").append(this.userStatus.getOtpSerialNumber());
/*     */       }
/*     */       
/* 356 */       if (this.userStatus.getIssueDateTime() != null) {
/* 357 */         sb.append(", issueDateTime : ").append(this.userStatus.getIssueDateTime());
/*     */       }
/*     */       
/* 360 */       if (!StringUtils.isEmpty(this.userStatus.getCountryCode())) {
/* 361 */         sb.append(", countryCode : ").append(this.userStatus.getCountryCode());
/*     */       }
/*     */       
/* 364 */       if (!StringUtils.isEmpty(this.userStatus.getDeviceOS())) {
/* 365 */         sb.append(", deviceOS : ").append(this.userStatus.getDeviceOS());
/*     */       }
/*     */       
/* 368 */       if (!StringUtils.isEmpty(this.userStatus.getDeviceModel())) {
/* 369 */         sb.append(", deviceModel : ").append(this.userStatus.getDeviceModel());
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 374 */     if (this.issueCodeData != null) {
/* 375 */       if (!StringUtils.isEmpty(this.issueCodeData.getIssueCode())) {
/* 376 */         sb.append(", issueCode : ").append(this.issueCodeData.getIssueCode());
/*     */       }
/*     */       
/* 379 */       if (this.issueCodeData.getIssueCodeRegTs() != null) {
/* 380 */         sb.append(", issueCodeRegDateTime : ").append(this.issueCodeData.getIssueCodeRegTs());
/*     */       }
/*     */       
/* 383 */       if (this.issueCodeData.getIssueCodeExpired() != null) {
/* 384 */         sb.append(", issueCodeExpired : ").append(this.issueCodeData.getIssueCodeExpired());
/*     */       }
/*     */       
/* 387 */       if (this.issueCodeData.getIssueCodeFailCnt() != null) {
/* 388 */         sb.append(", issueCodeFailCnt : ").append(this.issueCodeData.getIssueCodeFailCnt());
/*     */       }
/*     */       
/* 391 */       if (this.issueCodeData.getIssueCodeExpiredYn() != null) {
/* 392 */         sb.append(", issueCodeExpiredYn : ").append(this.issueCodeData.getIssueCodeExpiredYn().booleanValue() ? "Y" : "N");
/*     */       }
/*     */       
/* 395 */       if (this.issueCodeData.getIssueCodeFailExceedYn() != null) {
/* 396 */         sb.append(", issueCodeFailExceedYn : ").append(this.issueCodeData.getIssueCodeFailExceedYn().booleanValue() ? "Y" : "N");
/*     */       }
/*     */     } 
/*     */     
/* 400 */     if (this.userStatus != null) {
/* 401 */       sb.append("}");
/*     */     }
/*     */     
/* 404 */     if (this.tokenStats != null) {
/* 405 */       sb.append(", data : {");
/* 406 */       sb.append("total : ").append(this.tokenStats.getTotal());
/* 407 */       sb.append(", available : ").append(this.tokenStats.getAvailable());
/* 408 */       sb.append(", occupied : ").append(this.tokenStats.getAvailable());
/* 409 */       sb.append(", discard : ").append(this.tokenStats.getDiscard());
/* 410 */       sb.append("}");
/*     */     } 
/*     */     
/* 413 */     if (!StringUtils.isEmpty(this.userName)) {
/* 414 */       sb.append(", userName : ").append(this.userName);
/*     */     }
/*     */     
/* 417 */     if (this.productType != null) {
/* 418 */       sb.append(", productType  : ").append(this.productType);
/*     */     }
/*     */     
/* 421 */     if (!StringUtils.isEmpty(this.multiLoginYN)) {
/* 422 */       sb.append(", multiLoginYN : ").append(this.multiLoginYN);
/*     */     }
/*     */     
/* 425 */     if (!StringUtils.isEmpty(this.status)) {
/* 426 */       sb.append(", status : ").append(this.status);
/*     */     }
/*     */     
/* 429 */     if (this.serviceLogs != null && !this.serviceLogs.isEmpty()) {
/* 430 */       sb.append(", logs : [");
/*     */       
/* 432 */       for (ServiceLogVO vo : this.serviceLogs) {
/* 433 */         sb.append("{");
/* 434 */         sb.append("opType : ").append(vo.getOpType().getCode());
/* 435 */         sb.append(", regDateTime : ").append(vo.getTsReg());
/* 436 */         sb.append("}");
/*     */       } 
/*     */       
/* 439 */       sb.append("]");
/*     */     } 
/*     */     
/* 442 */     sb.append("}");
/*     */     
/* 444 */     return sb.toString();
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\external\bean\WebApiResponse.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */