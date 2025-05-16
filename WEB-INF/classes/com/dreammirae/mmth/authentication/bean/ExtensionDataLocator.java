/*    */ package WEB-INF.classes.com.dreammirae.mmth.authentication.bean;
/*    */ 
/*    */ import com.dreammirae.mmth.fido.StatusCodes;
/*    */ import com.dreammirae.mmth.fido.exception.FidoUafStatusCodeException;
/*    */ import com.dreammirae.mmth.fido.uaf.Extension;
/*    */ import com.dreammirae.mmth.misc.Base64Utils;
/*    */ import com.dreammirae.mmth.util.StringUtils;
/*    */ import com.dreammirae.mmth.util.io.HexUtils;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ExtensionDataLocator
/*    */ {
/*    */   private byte[] otpPublicKey;
/*    */   private byte[] randomSeedKey;
/*    */   private byte[] randomChallenge;
/*    */   
/*    */   public byte[] getOtpPublicKey() {
/* 22 */     return this.otpPublicKey;
/*    */   }
/*    */   
/*    */   public void setOtpPublicKey(byte[] otpPublicKey) {
/* 26 */     this.otpPublicKey = otpPublicKey;
/*    */   }
/*    */   
/*    */   public byte[] getRandomSeedKey() {
/* 30 */     return this.randomSeedKey;
/*    */   }
/*    */   
/*    */   public void setRandomSeedKey(byte[] randomSeedKey) {
/* 34 */     this.randomSeedKey = randomSeedKey;
/*    */   }
/*    */   
/*    */   public byte[] getRandomChallenge() {
/* 38 */     return this.randomChallenge;
/*    */   }
/*    */   
/*    */   public void setRandomChallenge(byte[] randomChallenge) {
/* 42 */     this.randomChallenge = randomChallenge;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static com.dreammirae.mmth.authentication.bean.ExtensionDataLocator extsToLocator(Extension[] exts) throws FidoUafStatusCodeException {
/* 48 */     if (exts == null) {
/* 49 */       return new com.dreammirae.mmth.authentication.bean.ExtensionDataLocator();
/*    */     }
/*    */     
/* 52 */     com.dreammirae.mmth.authentication.bean.ExtensionDataLocator loc = new com.dreammirae.mmth.authentication.bean.ExtensionDataLocator();
/*    */     
/* 54 */     for (Extension ext : exts) {
/*    */       
/* 56 */       if (StringUtils.isEmpty(ext.getId()) || StringUtils.isEmpty(ext.getData())) {
/* 57 */         throw new FidoUafStatusCodeException(StatusCodes.CODE_1497, "EXTENSION_DATA is required.");
/*    */       }
/*    */       
/* 60 */       if ("OTP_PUB_KEY".equals(ext.getId())) {
/* 61 */         loc.setOtpPublicKey(Base64Utils.decodeRaw(ext.getData()));
/* 62 */       } else if ("RND_SEED_KEY".equals(ext.getId())) {
/* 63 */         loc.setRandomSeedKey(Base64Utils.decodeRaw(ext.getData()));
/* 64 */       } else if ("RND_CHALLENGE".equals(ext.getId())) {
/* 65 */         loc.setRandomChallenge(Base64Utils.decodeRaw(ext.getData()));
/*    */       } else {
/* 67 */         throw new FidoUafStatusCodeException(StatusCodes.CODE_1497, "Unknown EXTENSION_ID, ID=" + ext.getId());
/*    */       } 
/*    */     } 
/*    */     
/* 71 */     return loc;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 79 */     StringBuilder builder = new StringBuilder();
/* 80 */     builder.append("ExtensionDataLocator [otpPublicKey=").append(HexUtils.toHexString(this.otpPublicKey)).append(", randomSeedKey=").append(HexUtils.toHexString(this.randomSeedKey)).append(", randomChallenge=")
/* 81 */       .append(HexUtils.toHexString(this.randomChallenge)).append("]");
/* 82 */     return builder.toString();
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\authentication\bean\ExtensionDataLocator.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */