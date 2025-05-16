/*     */ package WEB-INF.classes.com.dreammirae.mmth.web.service;
/*     */ 
/*     */ import com.dreammirae.mmth.config.Commons;
/*     */ import com.dreammirae.mmth.db.dao.FidoFacetDao;
/*     */ import com.dreammirae.mmth.db.dao.IViewDao;
/*     */ import com.dreammirae.mmth.exception.InternalDBException;
/*     */ import com.dreammirae.mmth.misc.I18nMessage;
/*     */ import com.dreammirae.mmth.runtime.audit.type.AlarmLevels;
/*     */ import com.dreammirae.mmth.runtime.audit.type.AuditAlarmTypes;
/*     */ import com.dreammirae.mmth.runtime.code.SyncCacheTypes;
/*     */ import com.dreammirae.mmth.vo.AdministratorVO;
/*     */ import com.dreammirae.mmth.vo.FidoFacetVO;
/*     */ import com.dreammirae.mmth.vo.bean.FidoFacetStats;
/*     */ import com.dreammirae.mmth.vo.bean.IRestValidator;
/*     */ import com.dreammirae.mmth.vo.bean.IViewStatsLocator;
/*     */ import com.dreammirae.mmth.vo.types.DisabledStatus;
/*     */ import com.dreammirae.mmth.web.exception.I18nMessageException;
/*     */ import com.dreammirae.mmth.web.service.ViewService;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Service;
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
/*     */ @Service("fidoFacetService")
/*     */ public class FidoFacetService
/*     */   extends ViewService<FidoFacetVO, Integer>
/*     */ {
/*     */   @Autowired
/*     */   private FidoFacetDao dao;
/*     */   
/*     */   public IViewStatsLocator getViewStatsLocator() throws I18nMessageException {
/*     */     try {
/*  42 */       FidoFacetStats stats = new FidoFacetStats();
/*  43 */       stats.setTotal(getViewDao().getViewConentCount(null, null, null));
/*  44 */       FidoFacetVO vo = new FidoFacetVO();
/*  45 */       vo.setDisabled(DisabledStatus.ENABLED);
/*  46 */       stats.setTrustedFacet(getViewDao().getViewConentCount(vo, null, null));
/*  47 */       return (IViewStatsLocator)stats;
/*  48 */     } catch (InternalDBException e) {
/*  49 */       throw new I18nMessageException(new I18nMessage("validate.internalError", new Object[] { e.getMessage() }), e);
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
/*     */   protected void saveBefore(AdministratorVO sessionAdmin, FidoFacetVO vo, FidoFacetVO prev, boolean isNew) throws I18nMessageException {
/*  62 */     if (isNew) {
/*  63 */       if (prev != null) {
/*  64 */         throw new I18nMessageException("facetId", new I18nMessage("validate.existAlready", new Object[] { new I18nMessage("facet.label.facetId"), vo.getFacetId() }));
/*     */       }
/*     */     } else {
/*  67 */       if (prev == null) {
/*  68 */         throw new I18nMessageException("facetId", new I18nMessage("validate.noExist.args", new Object[] { new I18nMessage("facet.label.facetId"), vo.getFacetId() }));
/*     */       }
/*  70 */       vo.setTsReg(prev.getTsReg());
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
/*     */   protected void saveAfter(AdministratorVO sessionAdmin, FidoFacetVO vo, FidoFacetVO prev, boolean isNew) throws I18nMessageException {
/*  83 */     FidoFacetDao.resetTrustedFacets();
/*     */ 
/*     */     
/*  86 */     if (isNew) {
/*  87 */       AuditAlarmTypes.FACET.raiseAlarm(sessionAdmin, 1537, AlarmLevels.INFORMATION, new Object[] { sessionAdmin.getUsername(), vo.getFacetId() });
/*     */     } else {
/*  89 */       AuditAlarmTypes.FACET.raiseAlarm(sessionAdmin, 1538, AlarmLevels.INFORMATION, new Object[] { sessionAdmin.getUsername(), vo.getFacetId() });
/*     */     } 
/*     */     
/*  92 */     if (Commons.ctx.getSyncCachePublisher() != null) {
/*  93 */       Commons.ctx.getSyncCachePublisher().syncCacheChanged(SyncCacheTypes.TRUSTED_FACETS);
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
/*     */   protected void deleteBefore(AdministratorVO sessionAdmin, Integer id, FidoFacetVO vo) throws I18nMessageException {}
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
/*     */   protected void deleteAfter(AdministratorVO sessionAdmin, Integer id, FidoFacetVO deleted) throws I18nMessageException {
/* 118 */     FidoFacetDao.resetTrustedFacets();
/*     */ 
/*     */     
/* 121 */     AuditAlarmTypes.FACET.raiseAlarm(sessionAdmin, 1539, AlarmLevels.URGENT, new Object[] { sessionAdmin.getUsername(), deleted.getFacetId() });
/*     */     
/* 123 */     if (Commons.ctx.getSyncCachePublisher() != null) {
/* 124 */       Commons.ctx.getSyncCachePublisher().syncCacheChanged(SyncCacheTypes.TRUSTED_FACETS);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isNew(FidoFacetVO vo) {
/* 130 */     return (-1 == vo.getId());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected IViewDao<FidoFacetVO, Integer> getViewDao() {
/* 140 */     if (this.dao == null) {
/* 141 */       this.dao = new FidoFacetDao();
/*     */     }
/*     */     
/* 144 */     return (IViewDao<FidoFacetVO, Integer>)this.dao;
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\web\service\FidoFacetService.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */