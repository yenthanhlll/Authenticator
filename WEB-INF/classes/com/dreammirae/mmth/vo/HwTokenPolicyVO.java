/*     */ package WEB-INF.classes.com.dreammirae.mmth.vo;
/*     */ 
/*     */ import com.dreammirae.mmth.vo.bean.IRestValidator;
/*     */ import com.dreammirae.mmth.vo.bean.RestResponse;
/*     */ import com.dreammirae.mmth.vo.bean.json.TimestampSerializer;
/*     */ import com.fasterxml.jackson.databind.annotation.JsonSerialize;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ 
/*     */ public class HwTokenPolicyVO
/*     */   implements Serializable, IRestValidator
/*     */ {
/*  13 */   private int id = -1;
/*     */   
/*     */   private String name;
/*     */   
/*     */   private Integer adminSyncCntSkew;
/*     */   
/*     */   private Integer adminSyncTmSkew;
/*     */   private Integer normAuthCntSkew;
/*     */   private Integer normAuthTmSkew;
/*     */   private Integer userSyncCntSkew;
/*     */   private Integer userSyncTmSkew;
/*     */   private Integer initAuthTmSkew;
/*     */   private Integer longAuthTmSkew;
/*     */   private Integer longTerm;
/*     */   private Integer maxAuthFailCnt;
/*  28 */   private String autoUnlockYn = "Y";
/*     */   
/*     */   @JsonSerialize(using = TimestampSerializer.class)
/*     */   private long chgDate;
/*     */   
/*     */   @JsonSerialize(using = TimestampSerializer.class)
/*     */   private long crtDate;
/*     */   
/*     */   private static final long serialVersionUID = 1L;
/*     */   
/*     */   public int getId() {
/*  39 */     return this.id;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setId(int id) {
/*  44 */     this.id = id;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/*  49 */     return this.name;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setName(String name) {
/*  54 */     this.name = name;
/*     */   }
/*     */ 
/*     */   
/*     */   public Integer getAdminSyncCntSkew() {
/*  59 */     return this.adminSyncCntSkew;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setAdminSyncCntSkew(Integer adminSyncCntSkew) {
/*  64 */     this.adminSyncCntSkew = adminSyncCntSkew;
/*     */   }
/*     */ 
/*     */   
/*     */   public Integer getAdminSyncTmSkew() {
/*  69 */     return this.adminSyncTmSkew;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setAdminSyncTmSkew(Integer adminSyncTmSkew) {
/*  74 */     this.adminSyncTmSkew = adminSyncTmSkew;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getAutoUnlockYn() {
/*  79 */     return this.autoUnlockYn;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setAutoUnlockYn(String autoUnlockYn) {
/*  84 */     this.autoUnlockYn = autoUnlockYn;
/*     */   }
/*     */ 
/*     */   
/*     */   public long getChgDate() {
/*  89 */     return this.chgDate;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setChgDate(long chgDate) {
/*  94 */     this.chgDate = chgDate;
/*     */   }
/*     */ 
/*     */   
/*     */   public long getCrtDate() {
/*  99 */     return this.crtDate;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCrtDate(long crtDate) {
/* 104 */     this.crtDate = crtDate;
/*     */   }
/*     */ 
/*     */   
/*     */   public Integer getInitAuthTmSkew() {
/* 109 */     return this.initAuthTmSkew;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setInitAuthTmSkew(Integer initAuthTmSkew) {
/* 114 */     this.initAuthTmSkew = initAuthTmSkew;
/*     */   }
/*     */ 
/*     */   
/*     */   public Integer getLongAuthTmSkew() {
/* 119 */     return this.longAuthTmSkew;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setLongAuthTmSkew(Integer longAuthTmSkew) {
/* 124 */     this.longAuthTmSkew = longAuthTmSkew;
/*     */   }
/*     */ 
/*     */   
/*     */   public Integer getLongTerm() {
/* 129 */     return this.longTerm;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setLongTerm(Integer longTerm) {
/* 134 */     this.longTerm = longTerm;
/*     */   }
/*     */ 
/*     */   
/*     */   public Integer getMaxAuthFailCnt() {
/* 139 */     return this.maxAuthFailCnt;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setMaxAuthFailCnt(Integer maxAuthFailCnt) {
/* 144 */     this.maxAuthFailCnt = maxAuthFailCnt;
/*     */   }
/*     */ 
/*     */   
/*     */   public Integer getNormAuthCntSkew() {
/* 149 */     return this.normAuthCntSkew;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setNormAuthCntSkew(Integer normAuthCntSkew) {
/* 154 */     this.normAuthCntSkew = normAuthCntSkew;
/*     */   }
/*     */ 
/*     */   
/*     */   public Integer getNormAuthTmSkew() {
/* 159 */     return this.normAuthTmSkew;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setNormAuthTmSkew(Integer normAuthTmSkew) {
/* 164 */     this.normAuthTmSkew = normAuthTmSkew;
/*     */   }
/*     */ 
/*     */   
/*     */   public Integer getUserSyncCntSkew() {
/* 169 */     return this.userSyncCntSkew;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setUserSyncCntSkew(Integer userSyncCntSkew) {
/* 174 */     this.userSyncCntSkew = userSyncCntSkew;
/*     */   }
/*     */ 
/*     */   
/*     */   public Integer getUserSyncTmSkew() {
/* 179 */     return this.userSyncTmSkew;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setUserSyncTmSkew(Integer userSyncTmSkew) {
/* 184 */     this.userSyncTmSkew = userSyncTmSkew;
/*     */   }
/*     */ 
/*     */   
/*     */   public static long getSerialversionuid() {
/* 189 */     return 1L;
/*     */   }
/*     */ 
/*     */   
/*     */   public static com.dreammirae.mmth.vo.HwTokenPolicyVO createTokenVO(String name, Integer normAuthTmSkew, Integer adminSyncTmSkew, Integer userSyncTmSkew, Integer initAuthTmSkew, Integer longAuthTmSkew, Integer longTerm, Integer maxAuthFailCnt) {
/* 194 */     com.dreammirae.mmth.vo.HwTokenPolicyVO vo = new com.dreammirae.mmth.vo.HwTokenPolicyVO();
/*     */ 
/*     */     
/* 197 */     vo.setName(name);
/* 198 */     vo.setNormAuthTmSkew(normAuthTmSkew);
/* 199 */     vo.setAdminSyncTmSkew(adminSyncTmSkew);
/* 200 */     vo.setUserSyncTmSkew(userSyncTmSkew);
/* 201 */     vo.setInitAuthTmSkew(initAuthTmSkew);
/* 202 */     vo.setLongAuthTmSkew(longAuthTmSkew);
/* 203 */     vo.setLongTerm(longTerm);
/* 204 */     vo.setMaxAuthFailCnt(maxAuthFailCnt);
/*     */     
/* 206 */     return vo;
/*     */   }
/*     */   
/*     */   public void validate(RestResponse resp) {}
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\vo\HwTokenPolicyVO.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */