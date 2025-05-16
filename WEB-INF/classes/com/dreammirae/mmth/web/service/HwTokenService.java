/*     */ package WEB-INF.classes.com.dreammirae.mmth.web.service;
/*     */ 
/*     */ import com.dreammirae.mmth.config.Commons;
/*     */ import com.dreammirae.mmth.db.dao.HwTokenDao;
/*     */ import com.dreammirae.mmth.db.dao.IViewDao;
/*     */ import com.dreammirae.mmth.exception.InternalDBException;
/*     */ import com.dreammirae.mmth.misc.I18nMessage;
/*     */ import com.dreammirae.mmth.runtime.audit.type.AlarmLevels;
/*     */ import com.dreammirae.mmth.runtime.audit.type.AuditAlarmTypes;
/*     */ import com.dreammirae.mmth.vo.AdministratorVO;
/*     */ import com.dreammirae.mmth.vo.HwTokenVO;
/*     */ import com.dreammirae.mmth.vo.bean.IRestValidator;
/*     */ import com.dreammirae.mmth.vo.bean.IViewStatsLocator;
/*     */ import com.dreammirae.mmth.vo.bean.TokenStats;
/*     */ import com.dreammirae.mmth.vo.types.HwTokenTypes;
/*     */ import com.dreammirae.mmth.vo.types.TokenStatus;
/*     */ import com.dreammirae.mmth.web.exception.I18nMessageException;
/*     */ import com.dreammirae.mmth.web.service.ViewService;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileReader;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.apache.commons.io.FileUtils;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Service;
/*     */ import org.springframework.web.multipart.MultipartFile;
/*     */ 
/*     */ 
/*     */ @Service("hwTokenService")
/*     */ public class HwTokenService
/*     */   extends ViewService<HwTokenVO, String>
/*     */ {
/*  37 */   private static final Logger LOG = LoggerFactory.getLogger(com.dreammirae.mmth.web.service.HwTokenService.class);
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
/*     */   @Autowired
/*     */   private HwTokenDao dao;
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
/*     */   public void upload(MultipartFile csvFile, String tokenType, String pin, AdministratorVO sessionAdmin) {
/* 102 */     if (csvFile == null || csvFile.isEmpty() || csvFile.getSize() < 1L) {
/* 103 */       throw new I18nMessageException("csvFile", new I18nMessage("validate.required"));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 110 */     String fileName = csvFile.getOriginalFilename();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 116 */     String path = Commons.ctx.getServletContext().getRealPath("/ttk");
/*     */     
/* 118 */     File dest = null;
/* 119 */     File csvFileUploadFolder = null;
/*     */     
/*     */     try {
/* 122 */       csvFileUploadFolder = new File(path);
/*     */       
/* 124 */       if (!csvFileUploadFolder.isDirectory()) {
/* 125 */         csvFileUploadFolder.mkdir();
/*     */       }
/*     */       
/* 128 */       dest = new File(csvFileUploadFolder, fileName);
/*     */       
/* 130 */       FileUtils.copyInputStreamToFile(csvFile.getInputStream(), dest);
/*     */       
/* 132 */       if (!dest.exists()) {
/* 133 */         throw new I18nMessageException("csvFile", new I18nMessage("validate.invalideTTKIO"));
/*     */       }
/*     */       
/* 136 */       keyload(dest.getAbsolutePath(), tokenType, pin, sessionAdmin);
/*     */     }
/* 138 */     catch (I18nMessageException e) {
/* 139 */       throw e;
/* 140 */     } catch (Exception e) {
/* 141 */       throw new I18nMessageException("csvFile", new I18nMessage("validate.invalideTTKIO"));
/*     */     } finally {
/* 143 */       if (dest == null) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/*     */       try {
/* 149 */         if (!FileUtils.deleteQuietly(dest)) {
/* 150 */           FileUtils.forceDeleteOnExit(dest);
/*     */         }
/* 152 */       } catch (IOException e) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void keyload(String csvFileFilePath, String tokenType, String csvFilePin, AdministratorVO sessionAdmin) throws I18nMessageException {
/* 160 */     String snFrom = null, snTo = null;
/*     */     
/* 162 */     BufferedReader br = null;
/*     */     try {
/* 164 */       String line = "";
/*     */       
/* 166 */       br = new BufferedReader(new FileReader(csvFileFilePath));
/*     */       
/* 168 */       List<HwTokenVO> tokens = new ArrayList<>(512);
/* 169 */       while ((line = br.readLine()) != null) {
/* 170 */         String[] data = line.split(",");
/*     */ 
/*     */ 
/*     */         
/* 174 */         HwTokenVO vo = HwTokenVO.createTokenVO(data[0], data[2], data[1]);
/* 175 */         vo.setType(HwTokenTypes.getHwTokenTypesByName(tokenType).getCode());
/*     */         
/* 177 */         if (this.dao.getOneByToken(data[0]) == null) {
/* 178 */           tokens.add(vo);
/*     */         }
/*     */         
/* 181 */         if (tokens.size() >= 128) {
/* 182 */           saveAsBatch(tokens);
/* 183 */           tokens.clear();
/*     */         } 
/*     */       } 
/*     */       
/* 187 */       br.close();
/*     */ 
/*     */       
/* 190 */       if (tokens.size() > 0) {
/* 191 */         snFrom = ((HwTokenVO)tokens.get(0)).getTokenId();
/* 192 */         snTo = ((HwTokenVO)tokens.get(tokens.size() - 1)).getTokenId();
/* 193 */         saveAsBatch(tokens);
/* 194 */         AuditAlarmTypes.TOKEN.raiseAlarm(sessionAdmin, 1025, AlarmLevels.INFORMATION, new Object[] { sessionAdmin.getUsername(), snFrom, snTo });
/*     */       } 
/*     */       
/* 197 */       tokens.clear();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 206 */     catch (InternalDBException e) {
/*     */       
/* 208 */       if (e.getCause() instanceof org.springframework.dao.DuplicateKeyException) {
/* 209 */         throw new I18nMessageException("csvFile", new I18nMessage("token.upload.error.duplicated", new Object[] { snFrom, snTo }), e);
/*     */       }
/* 211 */       throw new I18nMessageException("csvFile", new I18nMessage("token.upload.error.unknown", new Object[] { snFrom, snTo, e.getMessage() }), e);
/*     */     
/*     */     }
/* 214 */     catch (Throwable e) {
/* 215 */       throw new I18nMessageException("csvFile", new I18nMessage("token.upload.error.unknown", new Object[] { snFrom, snTo, e.getMessage() }), e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void alldelete(AdministratorVO sessionAdmin) {
/* 221 */     LOG.info("alldelete : " + this.dao.allDelete() + "\n");
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
/*     */   public IViewStatsLocator getViewStatsLocator() throws I18nMessageException {
/* 309 */     TokenStats stats = new TokenStats();
/*     */     
/* 311 */     HwTokenVO vo = new HwTokenVO();
/* 312 */     vo.setStatus(TokenStatus.AVAILABLE);
/* 313 */     stats.setAvailable(getViewDao().getViewConentCount(vo, null, null));
/* 314 */     vo.setStatus(TokenStatus.OCCUPIED);
/* 315 */     stats.setOccupied(getViewDao().getViewConentCount(vo, null, null));
/*     */ 
/*     */ 
/*     */     
/* 319 */     vo.setStatus(TokenStatus.DISCARD);
/* 320 */     stats.setDiscard(getViewDao().getViewConentCount(vo, null, null));
/*     */     
/* 322 */     stats.setTotal(stats.getAvailable() + stats.getOccupied() + stats.getSuspend() + stats.getDiscard());
/* 323 */     return (IViewStatsLocator)stats;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void saveBefore(AdministratorVO sessionAdmin, HwTokenVO vo, HwTokenVO prev, boolean isNew) throws I18nMessageException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void saveAfter(AdministratorVO sessionAdmin, HwTokenVO vo, HwTokenVO prev, boolean isNew) throws I18nMessageException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void deleteBefore(AdministratorVO sessionAdmin, String id, HwTokenVO vo) throws I18nMessageException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void deleteAfter(AdministratorVO sessionAdmin, String id, HwTokenVO vo) throws I18nMessageException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void save(AdministratorVO sessionAdmin, HwTokenVO vo) throws I18nMessageException {}
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
/*     */   public void saveAsBatch(List<HwTokenVO> tokens) throws I18nMessageException {
/* 383 */     for (HwTokenVO vo : tokens) {
/* 384 */       LOG.info("saveAsBatch : " + vo.getTokenId() + " / " + vo.getStatus().getCode());
/*     */     }
/* 386 */     this.dao.saveAsBatch(tokens);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isNew(HwTokenVO vo) {
/* 392 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected HwTokenDao getViewDao() {
/* 402 */     if (this.dao == null) {
/* 403 */       this.dao = new HwTokenDao();
/*     */     }
/*     */     
/* 406 */     return this.dao;
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\web\service\HwTokenService.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */