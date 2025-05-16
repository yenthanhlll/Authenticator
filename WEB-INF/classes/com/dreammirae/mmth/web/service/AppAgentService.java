/*     */ package WEB-INF.classes.com.dreammirae.mmth.web.service;
/*     */ 
/*     */ import com.dreammirae.mmth.config.Commons;
/*     */ import com.dreammirae.mmth.db.dao.AppAgentDao;
/*     */ import com.dreammirae.mmth.db.dao.IViewDao;
/*     */ import com.dreammirae.mmth.exception.InternalDBException;
/*     */ import com.dreammirae.mmth.misc.I18nMessage;
/*     */ import com.dreammirae.mmth.runtime.audit.type.AlarmLevels;
/*     */ import com.dreammirae.mmth.runtime.audit.type.AuditAlarmTypes;
/*     */ import com.dreammirae.mmth.runtime.code.SyncCacheTypes;
/*     */ import com.dreammirae.mmth.vo.AdministratorVO;
/*     */ import com.dreammirae.mmth.vo.AppAgentVO;
/*     */ import com.dreammirae.mmth.vo.bean.AppAgentStats;
/*     */ import com.dreammirae.mmth.vo.bean.IRestValidator;
/*     */ import com.dreammirae.mmth.vo.bean.IViewStatsLocator;
/*     */ import com.dreammirae.mmth.vo.types.AgentOsTypes;
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
/*     */ @Service("appAgentService")
/*     */ public class AppAgentService
/*     */   extends ViewService<AppAgentVO, Integer>
/*     */ {
/*     */   @Autowired
/*     */   private AppAgentDao dao;
/*     */   
/*     */   public IViewStatsLocator getViewStatsLocator() throws I18nMessageException {
/*     */     try {
/*  41 */       AppAgentStats stat = new AppAgentStats();
/*  42 */       stat.setTotal(getViewDao().getViewConentCount(null, null, null));
/*  43 */       AppAgentVO param = new AppAgentVO();
/*  44 */       param.setOsType(AgentOsTypes.ANDROID);
/*  45 */       stat.setAndroid(getViewDao().getViewConentCount(param, null, null));
/*  46 */       param.setOsType(AgentOsTypes.IOS);
/*  47 */       stat.setIos(getViewDao().getViewConentCount(param, null, null));
/*  48 */       return (IViewStatsLocator)stat;
/*  49 */     } catch (InternalDBException e) {
/*  50 */       throw new I18nMessageException(new I18nMessage("validate.internalError", new Object[] { e.getMessage() }), e);
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
/*     */   protected void saveBefore(AdministratorVO sessionAdmin, AppAgentVO vo, AppAgentVO prev, boolean isNew) throws I18nMessageException {
/*  63 */     if (isNew) {
/*  64 */       if (prev != null) {
/*  65 */         throw new I18nMessageException("pkgUnique", new I18nMessage("validate.existAlready", new Object[] { new I18nMessage("appagent.label.pkgUnique"), vo.getPkgUnique() }));
/*     */       }
/*     */     } else {
/*  68 */       if (prev == null) {
/*  69 */         throw new I18nMessageException("pkgUnique", new I18nMessage("validate.noExist.args", new Object[] { new I18nMessage("appagent.label.pkgUnique"), vo.getPkgUnique() }));
/*     */       }
/*  71 */       vo.setTsReg(prev.getTsReg());
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
/*     */   protected void saveAfter(AdministratorVO sessionAdmin, AppAgentVO vo, AppAgentVO prev, boolean isNew) throws I18nMessageException {
/*  85 */     AppAgentDao.resetAcceptAppAgnet();
/*     */ 
/*     */     
/*  88 */     if (isNew) {
/*  89 */       AuditAlarmTypes.APP_AGENT.raiseAlarm(sessionAdmin, 1793, AlarmLevels.INFORMATION, new Object[] { sessionAdmin.getUsername(), vo.getOsType().name(), vo.getPkgUnique() });
/*     */     } else {
/*  91 */       AuditAlarmTypes.APP_AGENT.raiseAlarm(sessionAdmin, 1794, AlarmLevels.INFORMATION, new Object[] { sessionAdmin.getUsername(), vo.getOsType().name(), vo.getPkgUnique() });
/*     */     } 
/*     */     
/*  94 */     if (Commons.ctx.getSyncCachePublisher() != null) {
/*  95 */       Commons.ctx.getSyncCachePublisher().syncCacheChanged(SyncCacheTypes.APP_AGENTS);
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
/*     */   protected void deleteBefore(AdministratorVO sessionAdmin, Integer id, AppAgentVO vo) throws I18nMessageException {}
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
/*     */   protected void deleteAfter(AdministratorVO sessionAdmin, Integer id, AppAgentVO deleted) throws I18nMessageException {
/* 121 */     AppAgentDao.resetAcceptAppAgnet();
/*     */     
/* 123 */     AuditAlarmTypes.APP_AGENT.raiseAlarm(sessionAdmin, 1795, AlarmLevels.INFORMATION, new Object[] { sessionAdmin.getUsername(), deleted.getOsType().name(), deleted.getPkgUnique() });
/*     */     
/* 125 */     if (Commons.ctx.getSyncCachePublisher() != null) {
/* 126 */       Commons.ctx.getSyncCachePublisher().syncCacheChanged(SyncCacheTypes.APP_AGENTS);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isNew(AppAgentVO vo) {
/* 132 */     return (-1 == vo.getId());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected AppAgentDao getViewDao() {
/* 142 */     if (this.dao == null) {
/* 143 */       this.dao = new AppAgentDao();
/*     */     }
/*     */     
/* 146 */     return this.dao;
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\web\service\AppAgentService.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */