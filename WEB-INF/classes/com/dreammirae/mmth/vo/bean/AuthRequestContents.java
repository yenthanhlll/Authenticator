/*     */ package WEB-INF.classes.com.dreammirae.mmth.vo.bean;
/*     */ 
/*     */ import com.dreammirae.mmth.util.io.SerializationUtils;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
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
/*     */ public class AuthRequestContents
/*     */   implements Serializable
/*     */ {
/*     */   private String userId;
/*     */   private String customerCode;
/*     */   private String customerName;
/*     */   private String serviceName;
/*     */   private long tsExpired;
/*     */   private long tsRemaining;
/*     */   private static final long serialVersionUID = 1L;
/*     */   private static final int version = 1;
/*     */   
/*     */   public String getUserId() {
/*  34 */     return this.userId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUserId(String userId) {
/*  41 */     this.userId = userId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCustomerCode() {
/*  48 */     return this.customerCode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCustomerCode(String customerCode) {
/*  55 */     this.customerCode = customerCode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCustomerName() {
/*  62 */     return this.customerName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCustomerName(String customerName) {
/*  69 */     this.customerName = customerName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getServiceName() {
/*  76 */     return this.serviceName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setServiceName(String serviceName) {
/*  83 */     this.serviceName = serviceName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getTsExpired() {
/*  90 */     return this.tsExpired;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTsExpired(long tsExpired) {
/*  97 */     this.tsExpired = tsExpired;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getTsRemaining() {
/* 104 */     return this.tsRemaining;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTsRemaining(long tsRemaining) {
/* 111 */     this.tsRemaining = tsRemaining;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 119 */     StringBuilder builder = new StringBuilder();
/* 120 */     builder.append("AuthRequestContents [userId=").append(this.userId).append(", customerCode=").append(this.customerCode).append(", customerName=").append(this.customerName).append(", serviceName=")
/* 121 */       .append(this.serviceName).append(", tsExpired=").append(this.tsExpired).append(", tsRemaining=").append(this.tsRemaining).append("]");
/* 122 */     return builder.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream out) throws IOException, ClassNotFoundException {
/* 134 */     out.writeInt(1);
/* 135 */     SerializationUtils.writeSafeUTF(out, this.userId);
/* 136 */     SerializationUtils.writeSafeUTF(out, this.customerCode);
/* 137 */     SerializationUtils.writeSafeUTF(out, this.customerName);
/* 138 */     SerializationUtils.writeSafeUTF(out, this.serviceName);
/* 139 */     out.writeLong(this.tsExpired);
/* 140 */     out.writeLong(this.tsRemaining);
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
/* 144 */     int ver = in.readInt();
/* 145 */     if (ver == 1) {
/* 146 */       this.userId = SerializationUtils.readSafeUTF(in);
/* 147 */       this.customerCode = SerializationUtils.readSafeUTF(in);
/* 148 */       this.customerName = SerializationUtils.readSafeUTF(in);
/* 149 */       this.serviceName = SerializationUtils.readSafeUTF(in);
/* 150 */       this.tsExpired = in.readLong();
/* 151 */       this.tsRemaining = this.tsExpired - System.currentTimeMillis();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\vo\bean\AuthRequestContents.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */