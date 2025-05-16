/*     */ package WEB-INF.classes.com.dreammirae.mmth.vo.bean;
/*     */ 
/*     */ import com.dreammirae.mmth.util.io.HexUtils;
/*     */ import com.dreammirae.mmth.util.io.SerializationUtils;
/*     */ import com.dreammirae.mmth.vo.types.YNStatus;
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
/*     */ public class TransactionInfoData
/*     */   implements Serializable
/*     */ {
/*     */   private YNStatus tranInfoYn;
/*     */   private String tranInfo;
/*     */   private byte[] tranInfoOrgEnc;
/*     */   private String tokenId;
/*     */   private String userName;
/*     */   private String deviceId;
/*     */   private static final long serialVersionUID = 1L;
/*     */   private static final int version = 1;
/*     */   
/*     */   public YNStatus getTranInfoYn() {
/*  29 */     return this.tranInfoYn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTranInfoYn(YNStatus tranInfoYn) {
/*  37 */     this.tranInfoYn = tranInfoYn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTranInfo() {
/*  44 */     return this.tranInfo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTranInfo(String tranInfo) {
/*  52 */     this.tranInfo = tranInfo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getTranInfoOrgEnc() {
/*  59 */     return this.tranInfoOrgEnc;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTranInfoOrgEnc(byte[] tranInfoOrg) {
/*  67 */     this.tranInfoOrgEnc = tranInfoOrg;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTokenId() {
/*  74 */     return this.tokenId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTokenId(String tokenId) {
/*  82 */     this.tokenId = tokenId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getUserName() {
/*  89 */     return this.userName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUserName(String userName) {
/*  97 */     this.userName = userName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDeviceId() {
/* 104 */     return this.deviceId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDeviceId(String deviceId) {
/* 112 */     this.deviceId = deviceId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 122 */     StringBuilder builder = new StringBuilder();
/* 123 */     builder.append("TransactionInfoData [tranInfoYn=").append(this.tranInfoYn).append(", tranInfo=").append(this.tranInfo).append(", tranInfoOrgEnc=").append(HexUtils.toHexString(this.tranInfoOrgEnc)).append(", tokenId=")
/* 124 */       .append(this.tokenId).append(", userName=").append(this.userName).append(", deviceId=").append(this.deviceId).append("]");
/* 125 */     return builder.toString();
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
/* 137 */     out.writeInt(1);
/* 138 */     SerializationUtils.writeSafeUTF(out, (this.tranInfoYn != null) ? this.tranInfoYn.getCode() : YNStatus.N.getCode());
/* 139 */     SerializationUtils.writeSafeUTF(out, this.tranInfo);
/* 140 */     SerializationUtils.writeSafeObject(out, this.tranInfoOrgEnc);
/* 141 */     SerializationUtils.writeSafeUTF(out, this.tokenId);
/* 142 */     SerializationUtils.writeSafeUTF(out, this.userName);
/* 143 */     SerializationUtils.writeSafeUTF(out, this.deviceId);
/*     */   }
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
/* 148 */     int ver = in.readInt();
/* 149 */     if (ver == 1) {
/* 150 */       this.tranInfoYn = YNStatus.getYNStatus(SerializationUtils.readSafeUTF(in));
/* 151 */       this.tranInfo = SerializationUtils.readSafeUTF(in);
/* 152 */       this.tranInfoOrgEnc = (byte[])SerializationUtils.readSafeObject(in);
/* 153 */       this.tokenId = SerializationUtils.readSafeUTF(in);
/* 154 */       this.userName = SerializationUtils.readSafeUTF(in);
/* 155 */       this.deviceId = SerializationUtils.readSafeUTF(in);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\vo\bean\TransactionInfoData.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */