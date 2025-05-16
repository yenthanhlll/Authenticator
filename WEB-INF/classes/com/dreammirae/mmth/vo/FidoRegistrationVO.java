/*     */ package WEB-INF.classes.com.dreammirae.mmth.vo;
/*     */ 
/*     */ import com.dreammirae.mmth.fido.handler.bean.IFidoRegistrionLocator;
/*     */ import com.dreammirae.mmth.fido.metadata.DisplayPNGCharacteristicsDescriptor;
/*     */ import com.dreammirae.mmth.fido.registry.AuthenticationAlgorithms;
/*     */ import com.dreammirae.mmth.fido.registry.PublicKeyRepresentationFormats;
/*     */ import com.dreammirae.mmth.fido.tlv.loc.RegAssertionLocator;
/*     */ import com.dreammirae.mmth.util.io.HexUtils;
/*     */ import com.dreammirae.mmth.util.io.SerializationUtils;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.Arrays;
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
/*     */ public class FidoRegistrationVO
/*     */   implements IFidoRegistrionLocator, Serializable
/*     */ {
/*  75 */   private int id = -1;
/*     */   
/*     */   private String aaid;
/*     */   
/*     */   private String keyId;
/*     */   
/*     */   private long tsReg;
/*     */   
/*     */   private DisplayPNGCharacteristicsDescriptor[] tcDisplayPNGCharacteristics;
/*     */   
/*     */   private int authenticatorVersion;
/*     */   
/*     */   private AuthenticationAlgorithms signatureAlgAndEncoding;
/*     */   
/*     */   private PublicKeyRepresentationFormats publicKeyAlgAndEncoding;
/*     */   
/*     */   private long regCounter;
/*     */   
/*     */   private byte[] publicKey;
/*  94 */   private transient int deviceAgentId = -1;
/*     */ 
/*     */   
/*     */   private transient long signCntUpdated;
/*     */ 
/*     */   
/*     */   private List<String> transactions;
/*     */ 
/*     */   
/*     */   private static final long serialVersionUID = 1L;
/*     */ 
/*     */   
/*     */   private static final int version = 1;
/*     */ 
/*     */   
/*     */   public int getId() {
/* 110 */     return this.id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setId(int id) {
/* 118 */     this.id = id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getAaid() {
/* 126 */     return this.aaid;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAaid(String aaid) {
/* 134 */     this.aaid = aaid;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getKeyId() {
/* 142 */     return this.keyId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setKeyId(String keyId) {
/* 150 */     this.keyId = keyId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getTsReg() {
/* 157 */     return this.tsReg;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTsReg(long tsReg) {
/* 165 */     this.tsReg = tsReg;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DisplayPNGCharacteristicsDescriptor[] getTcDisplayPNGCharacteristics() {
/* 173 */     return this.tcDisplayPNGCharacteristics;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTcDisplayPNGCharacteristics(DisplayPNGCharacteristicsDescriptor[] tcDisplayPNGCharacteristics) {
/* 181 */     this.tcDisplayPNGCharacteristics = tcDisplayPNGCharacteristics;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getAuthenticatorVersion() {
/* 188 */     return this.authenticatorVersion;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAuthenticatorVersion(int authenticatorVersion) {
/* 196 */     this.authenticatorVersion = authenticatorVersion;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AuthenticationAlgorithms getSignatureAlgAndEncoding() {
/* 203 */     return this.signatureAlgAndEncoding;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSignatureAlgAndEncoding(AuthenticationAlgorithms signatureAlgAndEncoding) {
/* 211 */     this.signatureAlgAndEncoding = signatureAlgAndEncoding;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PublicKeyRepresentationFormats getPublicKeyAlgAndEncoding() {
/* 219 */     return this.publicKeyAlgAndEncoding;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPublicKeyAlgAndEncoding(PublicKeyRepresentationFormats publicKeyAlgAndEncoding) {
/* 227 */     this.publicKeyAlgAndEncoding = publicKeyAlgAndEncoding;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getRegCounter() {
/* 234 */     return this.regCounter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRegCounter(long regCounter) {
/* 242 */     this.regCounter = regCounter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getPublicKey() {
/* 250 */     return this.publicKey;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPublicKey(byte[] publicKey) {
/* 258 */     this.publicKey = publicKey;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDeviceAgentId() {
/* 265 */     return this.deviceAgentId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDeviceAgentId(int deviceAgentId) {
/* 273 */     this.deviceAgentId = deviceAgentId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getSignCntUpdated() {
/* 280 */     return this.signCntUpdated;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSignCntUpdated(long signCntUpdated) {
/* 288 */     this.signCntUpdated = signCntUpdated;
/*     */   }
/*     */ 
/*     */   
/*     */   public long getLatestSignCounter() {
/* 293 */     return this.signCntUpdated;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasTcDisplayInfomation() {
/* 298 */     return (this.tcDisplayPNGCharacteristics != null);
/*     */   }
/*     */   
/*     */   public List<String> getTransactions() {
/* 302 */     return this.transactions;
/*     */   }
/*     */   
/*     */   public void setTransactions(List<String> transactions) {
/* 306 */     this.transactions = transactions;
/*     */   }
/*     */   
/*     */   public static com.dreammirae.mmth.vo.FidoRegistrationVO createNewInstance(RegAssertionLocator loc) {
/* 310 */     com.dreammirae.mmth.vo.FidoRegistrationVO vo = new com.dreammirae.mmth.vo.FidoRegistrationVO();
/* 311 */     vo.id = -1;
/* 312 */     vo.aaid = loc.getAAID();
/* 313 */     vo.keyId = loc.getKeyId();
/* 314 */     vo.tsReg = System.currentTimeMillis();
/*     */ 
/*     */     
/* 317 */     vo.authenticatorVersion = loc.getAuthenticatorVersion();
/* 318 */     vo.signatureAlgAndEncoding = loc.getAuthenticationAlgorithms();
/* 319 */     vo.publicKeyAlgAndEncoding = loc.getPublicKeyAlgAndEncoding();
/* 320 */     vo.signCntUpdated = loc.getSignCounter();
/* 321 */     vo.regCounter = loc.getRegCounter();
/* 322 */     vo.publicKey = loc.getPublicKey();
/*     */     
/* 324 */     return vo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 334 */     StringBuilder builder = new StringBuilder();
/* 335 */     builder.append("FidoRegistrationVO [id=").append(this.id).append(", aaid=").append(this.aaid).append(", keyId=").append(this.keyId).append(", tsReg=").append(this.tsReg).append(", tcDisplayPNGCharacteristics=")
/* 336 */       .append(Arrays.toString((Object[])this.tcDisplayPNGCharacteristics)).append(", authenticatorVersion=").append(this.authenticatorVersion).append(", signatureAlgAndEncoding=")
/* 337 */       .append(this.signatureAlgAndEncoding).append(", publicKeyAlgAndEncoding=").append(this.publicKeyAlgAndEncoding).append(", regCounter=").append(this.regCounter).append(", publicKey=")
/* 338 */       .append(HexUtils.toHexString(this.publicKey)).append(", signCntUpdated=").append(this.signCntUpdated).append("]");
/* 339 */     return builder.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream out) throws IOException, ClassNotFoundException {
/* 349 */     out.writeInt(1);
/*     */     
/* 351 */     SerializationUtils.writeSafeObject(out, this.tcDisplayPNGCharacteristics);
/* 352 */     out.writeInt(this.authenticatorVersion);
/* 353 */     out.writeInt(this.signatureAlgAndEncoding.getId());
/* 354 */     out.writeInt(this.publicKeyAlgAndEncoding.getId());
/* 355 */     out.writeLong(this.regCounter);
/* 356 */     SerializationUtils.writeSafeObject(out, this.publicKey);
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
/* 360 */     int ver = in.readInt();
/*     */     
/* 362 */     if (ver == 1) {
/* 363 */       this.tcDisplayPNGCharacteristics = (DisplayPNGCharacteristicsDescriptor[])SerializationUtils.readSafeObject(in);
/* 364 */       this.authenticatorVersion = in.readInt();
/* 365 */       this.signatureAlgAndEncoding = AuthenticationAlgorithms.get(in.readInt());
/* 366 */       this.publicKeyAlgAndEncoding = PublicKeyRepresentationFormats.get(in.readInt());
/* 367 */       this.regCounter = in.readLong();
/* 368 */       this.publicKey = (byte[])SerializationUtils.readSafeObject(in);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\vo\FidoRegistrationVO.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */