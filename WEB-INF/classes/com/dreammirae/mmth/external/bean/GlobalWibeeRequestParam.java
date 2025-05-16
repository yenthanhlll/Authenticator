/*     */ package WEB-INF.classes.com.dreammirae.mmth.external.bean;
/*     */ 
/*     */ import com.dreammirae.mmth.external.bean.WebApiRequestParam;
/*     */ import com.dreammirae.mmth.util.StringUtils;
/*     */ import com.dreammirae.mmth.vo.bean.GlobalWibeeLogData;
/*     */ import com.dreammirae.mmth.vo.bean.ICustomLogData;
/*     */ import java.util.regex.Pattern;
/*     */ 
/*     */ public class GlobalWibeeRequestParam
/*     */   extends WebApiRequestParam {
/*  11 */   public static final Pattern REGEX_COUNTRY_CODE_PATTERN = Pattern.compile("^[A-Z]{2}$");
/*     */ 
/*     */ 
/*     */   
/*     */   private String countryCode;
/*     */ 
/*     */ 
/*     */   
/*     */   private String regEmpNo;
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCountryCode() {
/*  24 */     return this.countryCode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCountryCode(String countryCode) {
/*  32 */     this.countryCode = countryCode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRegEmpNo() {
/*  41 */     return this.regEmpNo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRegEmpNo(String regEmpNo) {
/*  48 */     this.regEmpNo = regEmpNo;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ICustomLogData createCustomLogData() {
/*  54 */     if (StringUtils.isEmpty(this.regEmpNo)) {
/*  55 */       return null;
/*     */     }
/*     */     
/*  58 */     GlobalWibeeLogData data = new GlobalWibeeLogData();
/*  59 */     data.setRegEmpNo(this.regEmpNo);
/*  60 */     return (ICustomLogData)data;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/*  70 */     StringBuilder sb = new StringBuilder();
/*  71 */     sb.append("{");
/*     */     
/*  73 */     if (!StringUtils.isEmpty(getUserName())) {
/*  74 */       sb.append("userName : ").append(getUserName());
/*     */     }
/*     */     
/*  77 */     if (!StringUtils.isEmpty(getTid())) {
/*  78 */       if (sb.length() > 1) {
/*  79 */         sb.append(", ");
/*     */       }
/*  81 */       sb.append("tid : ").append(getTid());
/*     */     } 
/*     */     
/*  84 */     if (!StringUtils.isEmpty(getOtp())) {
/*  85 */       sb.append(", otp : ").append(getOtp());
/*     */     }
/*     */     
/*  88 */     if (!StringUtils.isEmpty(getTranInfo())) {
/*  89 */       sb.append(", tranInfo : ").append(getTranInfo());
/*     */     }
/*     */     
/*  92 */     if (!StringUtils.isEmpty(getCountryCode())) {
/*  93 */       sb.append(", countryCode : ").append(getCountryCode());
/*     */     }
/*     */     
/*  96 */     if (!StringUtils.isEmpty(getDeviceId())) {
/*  97 */       sb.append(", deviceId : ").append(getDeviceId());
/*     */     }
/*     */     
/* 100 */     if (getDeviceType() != null) {
/* 101 */       sb.append(", deviceType : ").append(getDeviceType().getCode());
/*     */     }
/*     */     
/* 104 */     if (getLimit() != null) {
/* 105 */       sb.append(", limit : ").append(getLimit());
/*     */     }
/*     */     
/* 108 */     if (!StringUtils.isEmpty(getRegEmpNo())) {
/* 109 */       sb.append(", regEmpNo : ").append(getRegEmpNo());
/*     */     }
/*     */     
/* 112 */     sb.append("}");
/*     */     
/* 114 */     return sb.toString();
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\external\bean\GlobalWibeeRequestParam.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */