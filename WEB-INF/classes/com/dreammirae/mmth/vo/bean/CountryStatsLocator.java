/*    */ package WEB-INF.classes.com.dreammirae.mmth.vo.bean;
/*    */ 
/*    */ import com.dreammirae.mmth.vo.bean.json.CountryStatsLocatorSerializer;
/*    */ import com.fasterxml.jackson.databind.annotation.JsonSerialize;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @JsonSerialize(using = CountryStatsLocatorSerializer.class)
/*    */ public class CountryStatsLocator
/*    */ {
/*    */   private String countryCode;
/*    */   private int reg;
/*    */   private int dereg;
/*    */   private int auth;
/*    */   
/*    */   public String getCountryCode() {
/* 22 */     return this.countryCode;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setCountryCode(String countryCode) {
/* 29 */     this.countryCode = countryCode;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getReg() {
/* 36 */     return this.reg;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setReg(int reg) {
/* 43 */     this.reg = reg;
/*    */   }
/*    */   
/*    */   public void addReg(int reg) {
/* 47 */     this.reg += reg;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getDereg() {
/* 54 */     return this.dereg;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setDereg(int dereg) {
/* 61 */     this.dereg = dereg;
/*    */   }
/*    */   
/*    */   public void addDereg(int dereg) {
/* 65 */     this.dereg += dereg;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getAuth() {
/* 72 */     return this.auth;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setAuth(int auth) {
/* 79 */     this.auth = auth;
/*    */   }
/*    */   
/*    */   public void addAuth(int auth) {
/* 83 */     this.auth += auth;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 91 */     StringBuilder builder = new StringBuilder();
/* 92 */     builder.append("CountryStatsLocator [countryCode=").append(this.countryCode).append(", reg=").append(this.reg).append(", dereg=").append(this.dereg).append(", auth=").append(this.auth).append("]");
/* 93 */     return builder.toString();
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\vo\bean\CountryStatsLocator.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */