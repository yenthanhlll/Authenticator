/*    */ package WEB-INF.classes.com.dreammirae.mmth.web.service;
/*    */ 
/*    */ import com.dreammirae.mmth.db.dao.AuditAlarmDao;
/*    */ import com.dreammirae.mmth.db.dao.IViewDao;
/*    */ import com.dreammirae.mmth.exception.InternalDBException;
/*    */ import com.dreammirae.mmth.misc.I18nMessage;
/*    */ import com.dreammirae.mmth.vo.AdministratorVO;
/*    */ import com.dreammirae.mmth.vo.AuditAlarmVO;
/*    */ import com.dreammirae.mmth.vo.bean.AuditAlarmStats;
/*    */ import com.dreammirae.mmth.vo.bean.IRestValidator;
/*    */ import com.dreammirae.mmth.vo.bean.IViewStatsLocator;
/*    */ import com.dreammirae.mmth.web.exception.I18nMessageException;
/*    */ import com.dreammirae.mmth.web.service.ViewService;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Service;
/*    */ 
/*    */ @Service("auditAlarmService")
/*    */ public class AuditAlarmService
/*    */   extends ViewService<AuditAlarmVO, Long>
/*    */ {
/*    */   public IViewStatsLocator acknowledgeAlarms(long[] ids, AdministratorVO sessionAdmin) throws I18nMessageException {
/*    */     try {
/* 23 */       getViewDao().updateAcknowledge(ids, sessionAdmin);
/* 24 */       return getViewStatsLocator();
/* 25 */     } catch (InternalDBException e) {
/* 26 */       throw new I18nMessageException(new I18nMessage("validate.internalError", new Object[] { e.getMessage() }), e);
/*    */     } 
/*    */   }
/*    */   @Autowired
/*    */   private AuditAlarmDao dao;
/*    */   public IViewStatsLocator getViewStatsLocator() throws I18nMessageException {
/*    */     try {
/* 33 */       AuditAlarmStats stat = new AuditAlarmStats();
/* 34 */       stat.setNoAck(getViewDao().noAcknowledgeCount());
/* 35 */       return (IViewStatsLocator)stat;
/* 36 */     } catch (InternalDBException e) {
/* 37 */       throw new I18nMessageException(new I18nMessage("validate.internalError", new Object[] { e.getMessage() }), e);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void saveBefore(AdministratorVO sessionAdmin, AuditAlarmVO vo, AuditAlarmVO prev, boolean isNew) throws I18nMessageException {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void save(AdministratorVO sessionAdmin, AuditAlarmVO vo) throws I18nMessageException {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void saveAfter(AdministratorVO sessionAdmin, AuditAlarmVO vo, AuditAlarmVO prev, boolean isNew) throws I18nMessageException {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void deleteBefore(AdministratorVO sessionAdmin, Long id, AuditAlarmVO vo) throws I18nMessageException {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void delete(AdministratorVO sessionAdmin, Long id) throws I18nMessageException {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void deleteAfter(AdministratorVO sessionAdmin, Long id, AuditAlarmVO deleted) throws I18nMessageException {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected boolean isNew(AuditAlarmVO vo) {
/* 76 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected AuditAlarmDao getViewDao() {
/* 82 */     if (this.dao == null) {
/* 83 */       this.dao = new AuditAlarmDao();
/*    */     }
/*    */     
/* 86 */     return this.dao;
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\web\service\AuditAlarmService.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */