/*     */ package WEB-INF.classes.com.dreammirae.mmth.vo;
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
/*     */ public class UserServerPwdVO
/*     */   implements Serializable
/*     */ {
/*  18 */   private int id = -1;
/*  19 */   private int userId = -1;
/*     */   
/*     */   private char pwdType;
/*     */   
/*     */   private String serverPin;
/*     */   
/*     */   private int pinFailCnt;
/*     */   
/*     */   private long tsReg;
/*     */   
/*     */   private long tsUpdated;
/*     */   
/*     */   private long tsLatestFailed;
/*     */   
/*     */   private long tsExpired;
/*     */   
/*     */   private String serverPinLong;
/*     */   
/*     */   private String previousServerPin;
/*     */   
/*     */   private static final long serialVersionUID = 1L;
/*     */   private static final int version = 1;
/*     */   
/*     */   public int getId() {
/*  43 */     return this.id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setId(int id) {
/*  51 */     this.id = id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getUserId() {
/*  58 */     return this.userId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUserId(int userId) {
/*  66 */     this.userId = userId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getServerPin() {
/*  73 */     return this.serverPin;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setServerPin(String serverPin) {
/*  81 */     this.serverPin = serverPin;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPinFailCnt() {
/*  88 */     return this.pinFailCnt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPinFailCnt(int pinFailCnt) {
/*  96 */     this.pinFailCnt = pinFailCnt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getTsReg() {
/* 103 */     return this.tsReg;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTsReg(long tsReg) {
/* 111 */     this.tsReg = tsReg;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getTsUpdated() {
/* 118 */     return this.tsUpdated;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTsUpdated(long tsUpdated) {
/* 126 */     this.tsUpdated = tsUpdated;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getServerPinLong() {
/* 133 */     return this.serverPinLong;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setServerPinLong(String serverPinLong) {
/* 141 */     this.serverPinLong = serverPinLong;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPreviousServerPin() {
/* 148 */     return this.previousServerPin;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPreviousServerPin(String previousServerPin) {
/* 156 */     this.previousServerPin = previousServerPin;
/*     */   }
/*     */   
/*     */   public char getPwdType() {
/* 160 */     return this.pwdType;
/*     */   }
/*     */   
/*     */   public void setPwdType(char pwdType) {
/* 164 */     this.pwdType = pwdType;
/*     */   }
/*     */   
/*     */   public long getTsLatestFailed() {
/* 168 */     return this.tsLatestFailed;
/*     */   }
/*     */   
/*     */   public void setTsLatestFailed(long tsLatestFailed) {
/* 172 */     this.tsLatestFailed = tsLatestFailed;
/*     */   }
/*     */   
/*     */   public long getTsExpired() {
/* 176 */     return this.tsExpired;
/*     */   }
/*     */   
/*     */   public void setTsExpired(long tsExpired) {
/* 180 */     this.tsExpired = tsExpired;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 186 */     StringBuilder builder = new StringBuilder();
/* 187 */     builder.append("UserServerPwdVO [id=").append(this.id).append(", userId=").append(this.userId).append(", pwdType=")
/* 188 */       .append(this.pwdType).append(", serverPin=").append(this.serverPin).append(", pinFailCnt=").append(this.pinFailCnt)
/* 189 */       .append(", tsReg=").append(this.tsReg).append(", tsUpdated=").append(this.tsUpdated).append(", tsLatestFailed=")
/* 190 */       .append(this.tsLatestFailed).append(", tsExpired=").append(this.tsExpired).append(", serverPinLong=")
/* 191 */       .append(this.serverPinLong).append(", previousServerPin=").append(this.previousServerPin).append("]");
/* 192 */     return builder.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream out) throws IOException, ClassNotFoundException {
/* 203 */     out.writeInt(1);
/* 204 */     SerializationUtils.writeSafeUTF(out, this.serverPinLong);
/* 205 */     SerializationUtils.writeSafeUTF(out, this.previousServerPin);
/*     */   }
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
/* 210 */     int ver = in.readInt();
/* 211 */     if (ver == 1) {
/* 212 */       this.serverPinLong = SerializationUtils.readSafeUTF(in);
/* 213 */       this.previousServerPin = SerializationUtils.readSafeUTF(in);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\vo\UserServerPwdVO.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */