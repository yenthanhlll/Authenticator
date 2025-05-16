/*    */ package WEB-INF.classes.com.dreammirae.mmth.web.service;
/*    */ 
/*    */ import com.dreammirae.mmth.db.dao.IViewDao;
/*    */ import com.dreammirae.mmth.db.dao.IssuanceHistoryDao;
/*    */ import com.dreammirae.mmth.vo.AdministratorVO;
/*    */ import com.dreammirae.mmth.vo.IssuanceHistoryVO;
/*    */ import com.dreammirae.mmth.vo.bean.IRestValidator;
/*    */ import com.dreammirae.mmth.vo.bean.IViewStatsLocator;
/*    */ import com.dreammirae.mmth.web.exception.I18nMessageException;
/*    */ import com.dreammirae.mmth.web.service.ViewService;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Service;
/*    */ 
/*    */ @Service("issuanceHistoryService")
/*    */ public class IssuanceHistoryService
/*    */   extends ViewService<IssuanceHistoryVO, Long> {
/*    */   @Autowired
/*    */   private IssuanceHistoryDao dao;
/*    */   
/*    */   public IViewStatsLocator getViewStatsLocator() throws I18nMessageException {
/* 21 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void saveBefore(AdministratorVO sessionAdmin, IssuanceHistoryVO vo, IssuanceHistoryVO prev, boolean isNew) throws I18nMessageException {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void saveAfter(AdministratorVO sessionAdmin, IssuanceHistoryVO vo, IssuanceHistoryVO prev, boolean isNew) throws I18nMessageException {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void deleteBefore(AdministratorVO sessionAdmin, Long id, IssuanceHistoryVO vo) throws I18nMessageException {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void deleteAfter(AdministratorVO sessionAdmin, Long id, IssuanceHistoryVO deleted) throws I18nMessageException {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected boolean isNew(IssuanceHistoryVO vo) {
/* 51 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected IViewDao<IssuanceHistoryVO, Long> getViewDao() {
/* 57 */     if (this.dao == null) {
/* 58 */       this.dao = new IssuanceHistoryDao();
/*    */     }
/*    */     
/* 61 */     return (IViewDao<IssuanceHistoryVO, Long>)this.dao;
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\web\service\IssuanceHistoryService.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */