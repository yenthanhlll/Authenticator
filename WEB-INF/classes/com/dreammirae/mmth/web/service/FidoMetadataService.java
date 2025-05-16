/*     */ package WEB-INF.classes.com.dreammirae.mmth.web.service;
/*     */ 
/*     */ import com.dreammirae.mmth.config.Commons;
/*     */ import com.dreammirae.mmth.db.dao.FidoMetadataDao;
/*     */ import com.dreammirae.mmth.db.dao.IViewDao;
/*     */ import com.dreammirae.mmth.exception.InternalDBException;
/*     */ import com.dreammirae.mmth.fido.metadata.MetadataStatement;
/*     */ import com.dreammirae.mmth.misc.I18nMessage;
/*     */ import com.dreammirae.mmth.parser.json.GsonUtils;
/*     */ import com.dreammirae.mmth.runtime.audit.type.AlarmLevels;
/*     */ import com.dreammirae.mmth.runtime.audit.type.AuditAlarmTypes;
/*     */ import com.dreammirae.mmth.runtime.code.SyncCacheTypes;
/*     */ import com.dreammirae.mmth.util.StringUtils;
/*     */ import com.dreammirae.mmth.vo.AdministratorVO;
/*     */ import com.dreammirae.mmth.vo.FidoMetadataVO;
/*     */ import com.dreammirae.mmth.vo.bean.FidoMetadataStats;
/*     */ import com.dreammirae.mmth.vo.bean.IRestValidator;
/*     */ import com.dreammirae.mmth.vo.bean.IViewStatsLocator;
/*     */ import com.dreammirae.mmth.vo.types.DisabledStatus;
/*     */ import com.dreammirae.mmth.web.exception.I18nMessageException;
/*     */ import com.dreammirae.mmth.web.service.ViewService;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Service;
/*     */ import org.springframework.web.multipart.MultipartFile;
/*     */ 
/*     */ @Service("fidoMetadataService")
/*     */ public class FidoMetadataService
/*     */   extends ViewService<FidoMetadataVO, Integer>
/*     */ {
/*     */   @Autowired
/*     */   private FidoMetadataDao dao;
/*     */   
/*     */   public FidoMetadataVO upload(MultipartFile metadataJson, String alias, String description, AdministratorVO sessionAdmin) throws I18nMessageException {
/*  36 */     if (metadataJson == null || metadataJson.isEmpty() || metadataJson.getSize() < 1L) {
/*  37 */       throw new I18nMessageException("metadataFile", new I18nMessage("validate.required"));
/*     */     }
/*     */     
/*  40 */     if (alias == null || StringUtils.isEmpty(alias = alias.trim())) {
/*  41 */       throw new I18nMessageException("add-alias", new I18nMessage("validate.required"));
/*     */     }
/*     */     
/*  44 */     if (description != null) {
/*  45 */       description = description.trim();
/*     */     }
/*     */     
/*  48 */     MetadataStatement ms = null;
/*  49 */     String json = null;
/*     */     
/*     */     try {
/*  52 */       ByteArrayInputStream bis = new ByteArrayInputStream(metadataJson.getBytes());
/*  53 */       json = IOUtils.toString(bis, Commons.UTF8_CS);
/*     */       
/*  55 */       ms = (MetadataStatement)GsonUtils.gson().fromJson(json, MetadataStatement.class);
/*     */       
/*  57 */       if (!MetadataStatement.validateMetadataStatement(ms)) {
/*  58 */         throw new I18nMessageException("metadataFile", new I18nMessage("metadata.validate.invalidJson"));
/*     */       }
/*     */     }
/*  61 */     catch (Exception e) {
/*  62 */       throw new I18nMessageException("metadataFile", new I18nMessage("metadata.validate.invalidJson"));
/*     */     } 
/*     */     
/*  65 */     FidoMetadataVO vo = FidoMetadataVO.createAdministrationVO(ms, alias, json);
/*  66 */     vo.setDescription(description);
/*     */     
/*  68 */     save(sessionAdmin, (IRestValidator)vo);
/*     */     
/*  70 */     return vo;
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
/*     */   public IViewStatsLocator getViewStatsLocator() throws I18nMessageException {
/*     */     try {
/*  89 */       FidoMetadataStats stats = new FidoMetadataStats();
/*  90 */       stats.setTotal(getViewDao().getViewConentCount(null, null, null));
/*     */       
/*  92 */       FidoMetadataVO vo = new FidoMetadataVO();
/*  93 */       vo.setDisabled(DisabledStatus.ENABLED);
/*     */       
/*  95 */       stats.setAllowed(getViewDao().getViewConentCount(null, null, null));
/*     */       
/*  97 */       return (IViewStatsLocator)stats;
/*  98 */     } catch (InternalDBException e) {
/*  99 */       throw new I18nMessageException(new I18nMessage("validate.internalError", new Object[] { e.getMessage() }), e);
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
/*     */   protected void saveBefore(AdministratorVO sessionAdmin, FidoMetadataVO vo, FidoMetadataVO prev, boolean isNew) throws I18nMessageException {
/* 111 */     if (isNew) {
/* 112 */       if (prev != null) {
/* 113 */         throw new I18nMessageException("metadataFile", new I18nMessage("validate.existAlready", new Object[] { new I18nMessage("common.aaid"), vo.getAaid() }));
/*     */       }
/*     */     } else {
/* 116 */       if (prev == null) {
/* 117 */         throw new I18nMessageException("aaid", new I18nMessage("validate.noExist.args", new Object[] { new I18nMessage("common.aaid"), vo.getAaid() }));
/*     */       }
/* 119 */       vo.setTsReg(prev.getTsReg());
/* 120 */       vo.setMetadataAsJson(prev.getMetadataAsJson());
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
/*     */   protected void saveAfter(AdministratorVO sessionAdmin, FidoMetadataVO vo, FidoMetadataVO prev, boolean isNew) throws I18nMessageException {
/* 133 */     FidoMetadataDao.resetAuthenticatorPolicy();
/*     */ 
/*     */     
/* 136 */     if (isNew) {
/* 137 */       AuditAlarmTypes.METADATA.raiseAlarm(sessionAdmin, 1281, AlarmLevels.INFORMATION, new Object[] { sessionAdmin.getUsername(), vo.getAaid() });
/*     */     } else {
/* 139 */       AuditAlarmTypes.METADATA.raiseAlarm(sessionAdmin, 1282, AlarmLevels.INFORMATION, new Object[] { sessionAdmin.getUsername(), vo.getAaid() });
/*     */     } 
/*     */ 
/*     */     
/* 143 */     if (Commons.ctx.getSyncCachePublisher() != null) {
/* 144 */       Commons.ctx.getSyncCachePublisher().syncCacheChanged(SyncCacheTypes.FIDO_METADATA);
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
/*     */   protected void deleteBefore(AdministratorVO sessionAdmin, Integer id, FidoMetadataVO vo) throws I18nMessageException {}
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
/*     */   protected void deleteAfter(AdministratorVO sessionAdmin, Integer id, FidoMetadataVO deleted) throws I18nMessageException {
/* 170 */     FidoMetadataDao.resetAuthenticatorPolicy();
/*     */ 
/*     */     
/* 173 */     AuditAlarmTypes.METADATA.raiseAlarm(sessionAdmin, 1283, AlarmLevels.URGENT, new Object[] { sessionAdmin.getUsername(), deleted.getAaid() });
/*     */ 
/*     */     
/* 176 */     if (Commons.ctx.getSyncCachePublisher() != null) {
/* 177 */       Commons.ctx.getSyncCachePublisher().syncCacheChanged(SyncCacheTypes.FIDO_METADATA);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isNew(FidoMetadataVO vo) {
/* 183 */     return (-1 == vo.getId());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected FidoMetadataDao getViewDao() {
/* 193 */     if (this.dao == null) {
/* 194 */       this.dao = new FidoMetadataDao();
/*     */     }
/*     */     
/* 197 */     return this.dao;
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\web\service\FidoMetadataService.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */