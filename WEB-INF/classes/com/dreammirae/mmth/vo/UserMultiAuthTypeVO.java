/*     */ package WEB-INF.classes.com.dreammirae.mmth.vo;
/*     */ 
/*     */ import com.dreammirae.mmth.authentication.ProductAuthType;
/*     */ import com.dreammirae.mmth.authentication.ProductType;
/*     */ import com.dreammirae.mmth.misc.Base64Utils;
/*     */ 
/*     */ 
/*     */ public class UserMultiAuthTypeVO
/*     */ {
/*  10 */   private int id = -1;
/*     */   
/*     */   private int userId;
/*     */   
/*     */   private ProductType productType;
/*     */   
/*     */   private ProductAuthType authType;
/*     */   
/*     */   private String value;
/*     */   
/*     */   private long tsReg;
/*     */   
/*     */   private long tsUpdated;
/*     */   
/*     */   private int failCnt;
/*     */ 
/*     */   
/*     */   public int getId() {
/*  28 */     return this.id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setId(int id) {
/*  36 */     this.id = id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getUserId() {
/*  43 */     return this.userId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUserId(int userId) {
/*  51 */     this.userId = userId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getAuthData() {
/*  58 */     return this.value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAuthData(String authData) {
/*  66 */     this.value = authData;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getTsReg() {
/*  73 */     return this.tsReg;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTsReg(long tsReg) {
/*  81 */     this.tsReg = tsReg;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getTsUpdated() {
/*  88 */     return this.tsUpdated;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTsUpdated(long tsUpdated) {
/*  96 */     this.tsUpdated = tsUpdated;
/*     */   }
/*     */   
/*     */   public ProductType getProductType() {
/* 100 */     return this.productType;
/*     */   }
/*     */   
/*     */   public void setProductType(ProductType type) {
/* 104 */     this.productType = type;
/*     */   }
/*     */   
/*     */   public ProductAuthType getProductAuthType() {
/* 108 */     return this.authType;
/*     */   }
/*     */   
/*     */   public void setProductAuthType(ProductAuthType type) {
/* 112 */     this.authType = type;
/*     */   }
/*     */   
/*     */   public int getFailCnt() {
/* 116 */     return this.failCnt;
/*     */   }
/*     */   
/*     */   public void setFailCnt(int failCnt) {
/* 120 */     this.failCnt = failCnt;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 125 */     StringBuilder builder = new StringBuilder();
/* 126 */     builder.append("UserMuliAuthTypeVO [id=").append(this.id).append(", userId=").append(this.userId).append(", productType=")
/* 127 */       .append(this.productType.getMessageKey()).append(", productAuthType=")
/* 128 */       .append(this.authType.getMessageKey()).append(", authData=").append(this.value)
/* 129 */       .append(", tsReg=").append(this.tsReg).append(", tsUpdated=").append(this.tsUpdated).append(", failCnt=").append(this.failCnt).append("]");
/* 130 */     return builder.toString();
/*     */   }
/*     */   
/*     */   public static com.dreammirae.mmth.vo.UserMultiAuthTypeVO createNewInstance(int userId, int productTypeCode, String authData, int productAuthType) {
/* 134 */     com.dreammirae.mmth.vo.UserMultiAuthTypeVO userAuthTypeVO = new com.dreammirae.mmth.vo.UserMultiAuthTypeVO();
/* 135 */     userAuthTypeVO.setUserId(userId);
/* 136 */     userAuthTypeVO.setProductAuthType(ProductAuthType.getAuthType(productAuthType));
/* 137 */     userAuthTypeVO.setProductType(ProductType.getProductType(productTypeCode));
/* 138 */     userAuthTypeVO.setAuthData(Base64Utils.encode(authData));
/*     */     
/* 140 */     return userAuthTypeVO;
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\vo\UserMultiAuthTypeVO.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */