/*     */ package WEB-INF.classes.com.dreammirae.mmth.authentication.service;
/*     */ 
/*     */ import com.dreammirae.mmth.authentication.ReturnCodes;
/*     */ import com.dreammirae.mmth.authentication.bean.OtpAdditionalData;
/*     */ import com.dreammirae.mmth.authentication.otp.OTPAuthnException;
/*     */ import com.dreammirae.mmth.authentication.otp.OTPDataException;
/*     */ import com.dreammirae.mmth.authentication.otp.OTPDataLocator;
/*     */ import com.dreammirae.mmth.authentication.otp.OTPDataUtils;
/*     */ import com.dreammirae.mmth.authentication.otp.TokenDataUtils;
/*     */ import com.dreammirae.mmth.exception.ReturnCodeException;
/*     */ import com.dreammirae.mmth.fido.Operation;
/*     */ import com.dreammirae.mmth.misc.Base64Utils;
/*     */ import com.dreammirae.mmth.misc.SysEnvCommon;
/*     */ import com.dreammirae.mmth.util.io.HexUtils;
/*     */ import com.dreammirae.mmth.vo.ServerChallengeVO;
/*     */ import com.dreammirae.mmth.vo.TokenRegistrationVO;
/*     */ import java.util.concurrent.locks.Lock;
/*     */ import java.util.concurrent.locks.ReentrantLock;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.stereotype.Service;
/*     */ 
/*     */ 
/*     */ 
/*     */ @Service("otpDataService")
/*     */ public class OTPDataService
/*     */ {
/*  28 */   private static final Logger LOG = LoggerFactory.getLogger(com.dreammirae.mmth.authentication.service.OTPDataService.class);
/*     */   
/*     */   private static final long TIME_SLICE = 250L;
/*     */   
/*     */   private static final int MAX_RETRY_CNT = 20;
/*  33 */   private static Lock otpLock = new ReentrantLock();
/*  34 */   private static Lock otpDataLock = new ReentrantLock();
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
/*     */   public void verifyOTP(TokenRegistrationVO token, ServerChallengeVO challenge, String fromClientOtp) throws ReturnCodeException {
/*  46 */     if (!otpLock.tryLock()) {
/*  47 */       int retries = 0;
/*     */       
/*     */       do {
/*  50 */         if (++retries >= 20) {
/*  51 */           throw new ReturnCodeException(ReturnCodes.INTERNAL_SERVER_ERROR, "Failed to get a lock to verify OTP.");
/*     */         }
/*     */         
/*     */         try {
/*  55 */           Thread.sleep(250L);
/*  56 */         } catch (InterruptedException ignore) {}
/*     */ 
/*     */       
/*     */       }
/*  60 */       while (!otpLock.tryLock());
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/*  65 */       byte[] svrChallenge = getServerChallenge(challenge.getTranContent(), challenge.getChallenge());
/*     */ 
/*     */       
/*  68 */       byte[] tokenData = TokenDataUtils.decryptTokenData(token.getTokenData());
/*     */       
/*  70 */       String genSvrOtp = OTPDataUtils.genOTPCode(tokenData, svrChallenge);
/*     */       
/*  72 */       if (LOG.isDebugEnabled()) {
/*  73 */         LOG.debug("***********  Challenge " + challenge.toString());
/*  74 */         LOG.debug("***********  Token " + token.toString());
/*  75 */         LOG.debug("***********  OTP validation ::: Received OTP = " + fromClientOtp + ", Generated OTP = " + genSvrOtp);
/*     */       } 
/*     */       
/*  78 */       if (!fromClientOtp.equals(genSvrOtp)) {
/*  79 */         throw new ReturnCodeException(ReturnCodes.AUTH_FAILED, "Failed to validate otp. ::: Received OTP = " + fromClientOtp + ", Generated OTP = " + genSvrOtp);
/*     */       
/*     */       }
/*     */     }
/*  83 */     catch (OTPAuthnException e) {
/*  84 */       throw new ReturnCodeException(ReturnCodes.AUTH_FAILED, e);
/*  85 */     } catch (ReturnCodeException e) {
/*  86 */       throw e;
/*  87 */     } catch (Exception e) {
/*  88 */       throw new ReturnCodeException(ReturnCodes.AUTH_FAILED, e);
/*     */     } finally {
/*  90 */       otpLock.unlock();
/*     */     } 
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
/*     */   
/*     */   public void verifyEncOTP(TokenRegistrationVO token, ServerChallengeVO challenge, String fromClientOtp) throws ReturnCodeException {
/* 104 */     if (!otpLock.tryLock()) {
/* 105 */       int retries = 0;
/*     */       
/*     */       do {
/* 108 */         if (++retries >= 20) {
/* 109 */           throw new ReturnCodeException(ReturnCodes.INTERNAL_SERVER_ERROR, "Failed to get a lock to verify OTP.");
/*     */         }
/*     */         
/*     */         try {
/* 113 */           Thread.sleep(250L);
/* 114 */         } catch (InterruptedException ignore) {}
/*     */ 
/*     */       
/*     */       }
/* 118 */       while (!otpLock.tryLock());
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 123 */       byte[] svrChallenge = getServerChallenge(challenge.getTranContent(), challenge.getChallenge());
/*     */ 
/*     */       
/* 126 */       byte[] tokenData = TokenDataUtils.decryptTokenData(token.getTokenData());
/*     */       
/* 128 */       String decOtp = OTPDataUtils.decryptOTPCode(fromClientOtp, tokenData);
/* 129 */       String genSvrOtp = OTPDataUtils.genOTPCode(tokenData, svrChallenge);
/*     */       
/* 131 */       if (LOG.isDebugEnabled()) {
/* 132 */         LOG.debug("***********  Challenge " + challenge.toString());
/* 133 */         LOG.debug("***********  Token " + token.toString());
/* 134 */         LOG.debug("***********  OTP validation ::: Received OTP = " + decOtp + ", Generated OTP = " + genSvrOtp);
/*     */       } 
/*     */       
/* 137 */       if (!decOtp.equals(genSvrOtp)) {
/* 138 */         throw new ReturnCodeException(ReturnCodes.AUTH_FAILED, "Failed to validate otp. ::: Received OTP = " + fromClientOtp + ", Generated OTP = " + genSvrOtp);
/*     */       }
/*     */     }
/* 141 */     catch (OTPAuthnException e) {
/* 142 */       throw new ReturnCodeException(ReturnCodes.AUTH_FAILED, e);
/* 143 */     } catch (ReturnCodeException e) {
/* 144 */       throw e;
/* 145 */     } catch (Exception e) {
/* 146 */       throw new ReturnCodeException(ReturnCodes.AUTH_FAILED, e);
/*     */     } finally {
/* 148 */       otpLock.unlock();
/*     */     } 
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
/*     */   
/*     */   public void makeNewOtpDataAdd(OtpAdditionalData otpData, Operation op, TokenRegistrationVO tokenRegi, ServerChallengeVO challenge) throws ReturnCodeException {
/* 162 */     if (!otpDataLock.tryLock()) {
/* 163 */       int retries = 0;
/*     */       
/*     */       do {
/* 166 */         if (++retries >= 20) {
/* 167 */           throw new ReturnCodeException(ReturnCodes.INTERNAL_SERVER_ERROR, "Failed to get a lock to generate otp data... see you next time!.");
/*     */         }
/*     */         
/*     */         try {
/* 171 */           Thread.sleep(250L);
/* 172 */         } catch (InterruptedException ignore) {}
/*     */ 
/*     */       
/*     */       }
/* 176 */       while (!otpDataLock.tryLock());
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 181 */       byte[] svrChalleng = getServerChallenge(challenge.getTranContent(), challenge.getChallenge());
/*     */       
/* 183 */       OTPDataLocator loc = null;
/* 184 */       if (Operation.Reg.equals(op)) {
/* 185 */         byte[] tokenId = SysEnvCommon.getUtf8Bytes(tokenRegi.getTokenId());
/* 186 */         loc = OTPDataUtils.generateOtpData(tokenRegi.getTokenData(), svrChalleng, tokenRegi.getOtpPublicKey(), null, true, tokenId);
/* 187 */         otpData.setEncToken(loc.getEncToken());
/* 188 */         otpData.setEncOtp(loc.getEncOtp());
/*     */       } else {
/* 190 */         byte[] tid = SysEnvCommon.getUtf8Bytes(challenge.getTransactionId());
/* 191 */         loc = OTPDataUtils.generateOtpData(tokenRegi.getTokenData(), svrChalleng, tokenRegi.getOtpPublicKey(), tid, false, null);
/* 192 */         otpData.setEncTid(loc.getEncTid());
/* 193 */         otpData.setTsExpiredOTP(Long.valueOf(challenge.getTsLifeTime()));
/*     */       } 
/*     */ 
/*     */       
/* 197 */       otpData.setEncKey(loc.getEncKey());
/* 198 */       otpData.setEncData(loc.getEncData());
/*     */ 
/*     */ 
/*     */       
/* 202 */       if (tokenRegi.getRndChallenge() != null) {
/* 203 */         byte[] baseChallenge = SysEnvCommon.getUtf8Bytes(challenge.getUsername());
/* 204 */         byte[] encRndSeedKey = OTPDataUtils.toEncRNDSeedKey(tokenRegi.getRndSeedKey(), baseChallenge, tokenRegi.getRndChallenge());
/* 205 */         otpData.setEncRndSeedKey(encRndSeedKey);
/*     */       }
/*     */     
/* 208 */     } catch (ReturnCodeException e) {
/* 209 */       throw e;
/* 210 */     } catch (OTPDataException e) {
/* 211 */       throw new ReturnCodeException(ReturnCodes.INTERNAL_SERVER_ERROR, e);
/* 212 */     } catch (Exception e) {
/* 213 */       throw new ReturnCodeException(ReturnCodes.INTERNAL_SERVER_ERROR, e);
/*     */     } finally {
/* 215 */       otpDataLock.unlock();
/*     */     } 
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
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] toDecRndSeedKeyWithPubKey(byte[] rndSeedKey, byte[] otpPub) throws ReturnCodeException {
/* 231 */     if (rndSeedKey == null) {
/* 232 */       return null;
/*     */     }
/*     */     
/*     */     try {
/* 236 */       return OTPDataUtils.toDecRNDSeedKey(rndSeedKey, otpPub);
/* 237 */     } catch (Exception e) {
/* 238 */       throw new ReturnCodeException(ReturnCodes.INTERNAL_SERVER_ERROR, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static byte[] getServerChallenge(String tranInfo, String challenge) throws ReturnCodeException {
/*     */     try {
/* 246 */       byte[] tranInfoBytes = HexUtils.fromHexString(tranInfo);
/* 247 */       byte[] challengeBytes = Base64Utils.decodeRaw(challenge);
/*     */       
/* 249 */       return OTPDataUtils.genOtpChallenge(tranInfoBytes, challengeBytes);
/* 250 */     } catch (OTPDataException e) {
/* 251 */       throw new ReturnCodeException(ReturnCodes.INTERNAL_SERVER_ERROR, e);
/* 252 */     } catch (Exception e) {
/* 253 */       throw new ReturnCodeException(ReturnCodes.INTERNAL_SERVER_ERROR, e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\authentication\service\OTPDataService.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */