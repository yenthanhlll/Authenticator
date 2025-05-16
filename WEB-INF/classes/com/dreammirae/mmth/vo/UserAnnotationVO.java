/*     */ package WEB-INF.classes.com.dreammirae.mmth.vo;
/*     */ 
/*     */ import com.dreammirae.mmth.vo.bean.json.MessageKeySerializer;
/*     */ import com.fasterxml.jackson.databind.annotation.JsonSerialize;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ 
/*     */ public class UserAnnotationVO
/*     */   implements Serializable
/*     */ {
/*  14 */   private int id = -1;
/*  15 */   private int userId = -1;
/*     */   
/*     */   private String username;
/*     */   
/*     */   private String displayName;
/*     */   
/*     */   private String extUnique;
/*     */   
/*     */   private String customerCode;
/*     */   
/*     */   private String countryCode;
/*     */   
/*     */   private static final long serialVersionUID = 1L;
/*     */   private static final int version = 1;
/*     */   
/*     */   public int getId() {
/*  31 */     return this.id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setId(int id) {
/*  38 */     this.id = id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getUserId() {
/*  45 */     return this.userId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUserId(int userId) {
/*  52 */     this.userId = userId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getUsername() {
/*  60 */     return this.username;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUsername(String username) {
/*  67 */     this.username = username;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDisplayName() {
/*  74 */     return this.displayName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDisplayName(String displayName) {
/*  81 */     this.displayName = displayName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getExtUnique() {
/*  88 */     return this.extUnique;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setExtUnique(String extUnique) {
/*  95 */     this.extUnique = extUnique;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCustomerCode() {
/* 102 */     return this.customerCode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCustomerCode(String customerCode) {
/* 109 */     this.customerCode = customerCode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCountryCode() {
/* 117 */     return this.countryCode;
/*     */   }
/*     */   
/*     */   @JsonSerialize(using = MessageKeySerializer.class)
/*     */   public String getCountryCodeDesc() {
/* 122 */     return this.countryCode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCountryCode(String countryCode) {
/* 129 */     this.countryCode = countryCode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 137 */     StringBuilder builder = new StringBuilder();
/* 138 */     builder.append("UserAnnotationVO [id=").append(this.id).append(", userId=").append(this.userId).append(", username=").append(this.username).append(", displayName=").append(this.displayName).append(", extUnique=")
/* 139 */       .append(this.extUnique).append(", customerCode=").append(this.customerCode).append(", countryCode=").append(this.countryCode).append("]");
/* 140 */     return builder.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream out) throws IOException, ClassNotFoundException {
/* 150 */     out.writeInt(1);
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
/* 154 */     int ver = in.readInt();
/* 155 */     if (ver == 1);
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\vo\UserAnnotationVO.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */