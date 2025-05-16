/*     */ package WEB-INF.classes.com.dreammirae.mmth.web.service;
/*     */ 
/*     */ import com.dreammirae.gt.otp.keyfile.Body;
/*     */ import com.dreammirae.gt.otp.keyfile.Header;
/*     */ import com.dreammirae.gt.otp.keyfile.InvalidPinException;
/*     */ import com.dreammirae.gt.otp.keyfile.InvalidTokenFormatException;
/*     */ import com.dreammirae.gt.otp.keyfile.TokenFile;
/*     */ import com.dreammirae.gt.otp.keyfile.TokenFileFactoryBiotp;
/*     */ import com.dreammirae.mmth.MMTHConstants;
/*     */ import com.dreammirae.mmth.authentication.otp.TokenDataException;
/*     */ import com.dreammirae.mmth.authentication.otp.TokenDataUtils;
/*     */ import com.dreammirae.mmth.config.Commons;
/*     */ import com.dreammirae.mmth.db.dao.IViewDao;
/*     */ import com.dreammirae.mmth.db.dao.TokenDao;
/*     */ import com.dreammirae.mmth.exception.InternalDBException;
/*     */ import com.dreammirae.mmth.misc.I18nMessage;
/*     */ import com.dreammirae.mmth.runtime.audit.type.AlarmLevels;
/*     */ import com.dreammirae.mmth.runtime.audit.type.AuditAlarmTypes;
/*     */ import com.dreammirae.mmth.util.StringUtils;
/*     */ import com.dreammirae.mmth.vo.AdministratorVO;
/*     */ import com.dreammirae.mmth.vo.TokenVO;
/*     */ import com.dreammirae.mmth.vo.bean.IRestValidator;
/*     */ import com.dreammirae.mmth.vo.bean.IViewStatsLocator;
/*     */ import com.dreammirae.mmth.vo.bean.TokenStats;
/*     */ import com.dreammirae.mmth.vo.types.TokenStatus;
/*     */ import com.dreammirae.mmth.web.exception.I18nMessageException;
/*     */ import com.dreammirae.mmth.web.service.ViewService;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import org.apache.commons.io.FileUtils;
/*     */ import org.apache.commons.io.FilenameUtils;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Service;
/*     */ import org.springframework.web.multipart.MultipartFile;
/*     */ 
/*     */ 
/*     */ @Service("tokenService")
/*     */ public class TokenService
/*     */   extends ViewService<TokenVO, String>
/*     */ {
/*     */   @Autowired
/*     */   private TokenDao dao;
/*     */   
/*     */   public void upload(MultipartFile ttkFile, String pin, AdministratorVO sessionAdmin) {
/*  48 */     if (ttkFile == null || ttkFile.isEmpty() || ttkFile.getSize() < 1L) {
/*  49 */       throw new I18nMessageException("ttkFile", new I18nMessage("validate.required"));
/*     */     }
/*     */     
/*  52 */     if (pin == null || StringUtils.isEmpty(pin = pin.trim())) {
/*  53 */       throw new I18nMessageException("pin", new I18nMessage("validate.required"));
/*     */     }
/*     */     
/*  56 */     String fileName = ttkFile.getOriginalFilename();
/*     */     
/*  58 */     if (!FilenameUtils.isExtension(fileName, MMTHConstants.ACCEPTED_TTK_EXTS)) {
/*  59 */       throw new I18nMessageException("ttkFile", new I18nMessage("validate.extension", new Object[] { Arrays.toString(MMTHConstants.ACCEPTED_TTK_EXTS) }));
/*     */     }
/*     */     
/*  62 */     String path = Commons.ctx.getServletContext().getRealPath("/ttk");
/*     */     
/*  64 */     File dest = null;
/*  65 */     File ttkFileUploadFolder = null;
/*     */     
/*     */     try {
/*  68 */       ttkFileUploadFolder = new File(path);
/*     */       
/*  70 */       if (!ttkFileUploadFolder.isDirectory()) {
/*  71 */         ttkFileUploadFolder.mkdir();
/*     */       }
/*     */       
/*  74 */       dest = new File(ttkFileUploadFolder, fileName);
/*     */       
/*  76 */       FileUtils.copyInputStreamToFile(ttkFile.getInputStream(), dest);
/*     */       
/*  78 */       if (!dest.exists()) {
/*  79 */         throw new I18nMessageException("ttkFile", new I18nMessage("validate.invalideTTKIO"));
/*     */       }
/*     */       
/*  82 */       keyload(dest.getAbsolutePath(), pin, sessionAdmin);
/*     */     }
/*  84 */     catch (I18nMessageException e) {
/*  85 */       throw e;
/*  86 */     } catch (Exception e) {
/*  87 */       throw new I18nMessageException("ttkFile", new I18nMessage("validate.invalideTTKIO"));
/*     */     } finally {
/*  89 */       if (dest == null) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/*     */       try {
/*  95 */         if (!FileUtils.deleteQuietly(dest)) {
/*  96 */           FileUtils.forceDeleteOnExit(dest);
/*     */         }
/*  98 */       } catch (IOException e) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void keyload(String ttkFileFilePath, String ttkFilePin, AdministratorVO sessionAdmin) throws I18nMessageException {
/* 107 */     String snFrom = null, snTo = null;
/*     */ 
/*     */     
/*     */     try {
/* 111 */       TokenFile token = TokenFileFactoryBiotp.getTokenFile(ttkFileFilePath, ttkFilePin);
/* 112 */       Header header = token.getHeader();
/* 113 */       snFrom = header.getStartSn();
/* 114 */       snTo = header.getEndSn();
/*     */       
/* 116 */       Body[] body = token.getBody();
/*     */       
/* 118 */       List<TokenVO> tokens = new ArrayList<>(512);
/*     */       
/* 120 */       for (Body b : body) {
/* 121 */         TokenVO vo = TokenVO.createTokenVO(b.getSn(), TokenDataUtils.encryptTokenData(b.getSeed()));
/* 122 */         tokens.add(vo);
/*     */         
/* 124 */         if (tokens.size() >= 128) {
/* 125 */           saveAsBatch(tokens);
/* 126 */           tokens.clear();
/*     */         } 
/*     */       } 
/*     */       
/* 130 */       if (tokens.size() > 0) {
/* 131 */         saveAsBatch(tokens);
/*     */       }
/*     */       
/* 134 */       tokens.clear();
/*     */       
/* 136 */       AuditAlarmTypes.TOKEN.raiseAlarm(sessionAdmin, 1025, AlarmLevels.INFORMATION, new Object[] { sessionAdmin.getUsername(), snFrom, snTo });
/*     */     }
/* 138 */     catch (InvalidTokenFormatException e) {
/* 139 */       throw new I18nMessageException("ttkFile", new I18nMessage("validate.invalideTTK", new Object[] { e }));
/* 140 */     } catch (InvalidPinException e) {
/* 141 */       throw new I18nMessageException("pin", new I18nMessage("token.upload.error.incorrected"), e);
/* 142 */     } catch (TokenDataException e) {
/* 143 */       throw new I18nMessageException("ttkFile", new I18nMessage("token.upload.error.cryptoFail"), e);
/* 144 */     } catch (InternalDBException e) {
/*     */       
/* 146 */       if (e.getCause() instanceof org.springframework.dao.DuplicateKeyException) {
/* 147 */         throw new I18nMessageException("ttkFile", new I18nMessage("token.upload.error.duplicated", new Object[] { snFrom, snTo }), e);
/*     */       }
/* 149 */       throw new I18nMessageException("ttkFile", new I18nMessage("token.upload.error.unknown", new Object[] { snFrom, snTo, e.getMessage() }), e);
/*     */     
/*     */     }
/* 152 */     catch (Throwable e) {
/* 153 */       throw new I18nMessageException("ttkFile", new I18nMessage("token.upload.error.unknown", new Object[] { snFrom, snTo, e.getMessage() }), e);
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
/*     */   
/*     */   public IViewStatsLocator getViewStatsLocator() throws I18nMessageException {
/* 170 */     TokenStats stats = new TokenStats();
/*     */     
/* 172 */     TokenVO vo = new TokenVO();
/* 173 */     vo.setStatus(TokenStatus.AVAILABLE);
/* 174 */     stats.setAvailable(getViewDao().getViewConentCount(vo, null, null));
/* 175 */     vo.setStatus(TokenStatus.OCCUPIED);
/* 176 */     stats.setOccupied(getViewDao().getViewConentCount(vo, null, null));
/* 177 */     vo.setStatus(TokenStatus.DISCARD);
/* 178 */     stats.setDiscard(getViewDao().getViewConentCount(vo, null, null));
/*     */     
/* 180 */     stats.setTotal(stats.getAvailable() + stats.getOccupied() + stats.getDiscard());
/*     */     
/* 182 */     return (IViewStatsLocator)stats;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void saveBefore(AdministratorVO sessionAdmin, TokenVO vo, TokenVO prev, boolean isNew) throws I18nMessageException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void saveAfter(AdministratorVO sessionAdmin, TokenVO vo, TokenVO prev, boolean isNew) throws I18nMessageException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void deleteBefore(AdministratorVO sessionAdmin, String id, TokenVO vo) throws I18nMessageException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void deleteAfter(AdministratorVO sessionAdmin, String id, TokenVO vo) throws I18nMessageException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void save(AdministratorVO sessionAdmin, TokenVO vo) throws I18nMessageException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void delete(AdministratorVO sessionAdmin, String id) throws I18nMessageException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void saveAsBatch(List<TokenVO> tokens) throws I18nMessageException {
/* 242 */     this.dao.saveAsBatch(tokens);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isNew(TokenVO vo) {
/* 247 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected TokenDao getViewDao() {
/* 257 */     if (this.dao == null) {
/* 258 */       this.dao = new TokenDao();
/*     */     }
/*     */     
/* 261 */     return this.dao;
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\web\service\TokenService.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */