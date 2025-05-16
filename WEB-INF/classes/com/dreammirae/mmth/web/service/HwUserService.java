/*    */ package WEB-INF.classes.com.dreammirae.mmth.web.service;
/*    */ 
/*    */ import com.dreammirae.mmth.db.dao.HwTokenDao;
/*    */ import com.dreammirae.mmth.db.dao.IViewDao;
/*    */ import com.dreammirae.mmth.vo.AdministratorVO;
/*    */ import com.dreammirae.mmth.vo.HwTokenVO;
/*    */ import com.dreammirae.mmth.vo.bean.IRestValidator;
/*    */ import com.dreammirae.mmth.vo.bean.IViewStatsLocator;
/*    */ import com.dreammirae.mmth.vo.bean.TokenStats;
/*    */ import com.dreammirae.mmth.vo.types.TokenStatus;
/*    */ import com.dreammirae.mmth.web.exception.I18nMessageException;
/*    */ import com.dreammirae.mmth.web.service.ViewService;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Service;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Service("hwUserService")
/*    */ public class HwUserService
/*    */   extends ViewService<HwTokenVO, String>
/*    */ {
/*    */   @Autowired
/*    */   private HwTokenDao dao;
/*    */   
/*    */   public IViewStatsLocator getViewStatsLocator() throws I18nMessageException {
/* 33 */     TokenStats stats = new TokenStats();
/*    */     
/* 35 */     HwTokenVO vo = new HwTokenVO();
/* 36 */     vo.setStatus(TokenStatus.OCCUPIED);
/* 37 */     stats.setOccupied(getViewDao().getViewConentCount(vo, null, null));
/*    */     
/* 39 */     stats.setTotal(stats.getOccupied());
/*    */     
/* 41 */     return (IViewStatsLocator)stats;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected HwTokenDao getViewDao() {
/* 52 */     if (this.dao == null) {
/* 53 */       this.dao = new HwTokenDao();
/*    */     }
/*    */     
/* 56 */     return this.dao;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void saveBefore(AdministratorVO sessionAdmin, HwTokenVO vo, HwTokenVO prev, boolean isNew) throws I18nMessageException {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void saveAfter(AdministratorVO sessionAdmin, HwTokenVO vo, HwTokenVO prev, boolean isNew) throws I18nMessageException {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void deleteBefore(AdministratorVO sessionAdmin, String id, HwTokenVO vo) throws I18nMessageException {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void deleteAfter(AdministratorVO sessionAdmin, String id, HwTokenVO deleted) throws I18nMessageException {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected boolean isNew(HwTokenVO vo) {
/* 88 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\web\service\HwUserService.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */