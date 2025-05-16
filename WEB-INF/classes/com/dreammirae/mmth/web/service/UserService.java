/*     */ package WEB-INF.classes.com.dreammirae.mmth.web.service;
/*     */ 
/*     */ import com.dreammirae.mmth.db.dao.IViewDao;
/*     */ import com.dreammirae.mmth.db.dao.UserDao;
/*     */ import com.dreammirae.mmth.vo.AdministratorVO;
/*     */ import com.dreammirae.mmth.vo.UserVO;
/*     */ import com.dreammirae.mmth.vo.bean.IRestValidator;
/*     */ import com.dreammirae.mmth.vo.bean.IViewStatsLocator;
/*     */ import com.dreammirae.mmth.vo.bean.UserStats;
/*     */ import com.dreammirae.mmth.vo.types.UserStatus;
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
/*     */ @Service("userService")
/*     */ public class UserService
/*     */   extends ViewService<UserVO, Integer>
/*     */ {
/*     */   @Autowired
/*     */   private UserDao dao;
/*     */   
/*     */   public IViewStatsLocator getViewStatsLocator() throws I18nMessageException {
/*  33 */     UserStats stats = new UserStats();
/*  34 */     stats.setTotal(getViewDao().getViewConentCount(null, null, null));
/*  35 */     UserVO vo = new UserVO();
/*  36 */     vo.setStatus(UserStatus.NOT_AVAILABLE);
/*  37 */     stats.setNotAvailable(getViewDao().getViewConentCount(vo, null, null));
/*  38 */     vo.setStatus(UserStatus.AVAILABLE);
/*  39 */     stats.setAvailable(getViewDao().getViewConentCount(vo, null, null));
/*     */ 
/*     */ 
/*     */     
/*  43 */     vo.setStatus(UserStatus.LOST_STOLEN);
/*  44 */     stats.setLostStolen(getViewDao().getViewConentCount(vo, null, null));
/*  45 */     stats.setDiscard(stats.getTotal() - stats.getNotAvailable() - stats.getAvailable() - stats.getLostStolen() - stats.getSuspend());
/*     */     
/*  47 */     return (IViewStatsLocator)stats;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void saveBefore(AdministratorVO sessionAdmin, UserVO vo, UserVO prev, boolean isNew) throws I18nMessageException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void saveAfter(AdministratorVO sessionAdmin, UserVO vo, UserVO prev, boolean isNew) throws I18nMessageException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void deleteBefore(AdministratorVO sessionAdmin, Integer id, UserVO vo) throws I18nMessageException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void deleteAfter(AdministratorVO sessionAdmin, Integer id, UserVO vo) throws I18nMessageException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void save(AdministratorVO sessionAdmin, UserVO vo) throws I18nMessageException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void delete(AdministratorVO sessionAdmin, Integer id) throws I18nMessageException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isNew(UserVO vo) {
/* 108 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected UserDao getViewDao() {
/* 118 */     if (this.dao == null) {
/* 119 */       this.dao = new UserDao();
/*     */     }
/*     */     
/* 122 */     return this.dao;
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\web\service\UserService.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */