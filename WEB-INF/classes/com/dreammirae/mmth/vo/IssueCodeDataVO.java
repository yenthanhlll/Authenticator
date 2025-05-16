/*     */ package WEB-INF.classes.com.dreammirae.mmth.vo;
/*     */ 
/*     */ import com.dreammirae.mmth.authentication.AuthMethodTypes;
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
/*     */ public class IssueCodeDataVO
/*     */ {
/*  29 */   private int id = -1;
/*     */   
/*     */   private String username;
/*     */   
/*     */   private AuthMethodTypes authType;
/*     */   private String issueCodeData;
/*     */   private long tsLifetime;
/*     */   
/*     */   public IssueCodeDataVO() {}
/*     */   
/*     */   public IssueCodeDataVO(String username, AuthMethodTypes authType) {
/*  40 */     this.username = username;
/*  41 */     this.authType = authType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getId() {
/*  48 */     return this.id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setId(int id) {
/*  55 */     this.id = id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getUsername() {
/*  62 */     return this.username;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUsername(String username) {
/*  69 */     this.username = username;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AuthMethodTypes getAuthType() {
/*  76 */     return this.authType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAuthType(AuthMethodTypes authType) {
/*  83 */     this.authType = authType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getIssueCodeData() {
/*  90 */     return this.issueCodeData;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setIssueCodeData(String issueCodeData) {
/*  97 */     this.issueCodeData = issueCodeData;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getTsLifetime() {
/* 104 */     return this.tsLifetime;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTsLifetime(long tsLifetime) {
/* 111 */     this.tsLifetime = tsLifetime;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 119 */     StringBuilder builder = new StringBuilder();
/* 120 */     builder.append("IssueCodeDataVO [id=").append(this.id).append(", username=").append(this.username).append(", authType=").append(this.authType).append(", issueCodeData=").append(this.issueCodeData)
/* 121 */       .append(", tsLifetime=").append(this.tsLifetime).append("]");
/* 122 */     return builder.toString();
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\vo\IssueCodeDataVO.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */