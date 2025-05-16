/*     */ package WEB-INF.classes.com.dreammirae.mmth.web.service;
/*     */ 
/*     */ import com.dreammirae.mmth.db.dao.AdministratorDao;
/*     */ import com.dreammirae.mmth.db.dao.IViewDao;
/*     */ import com.dreammirae.mmth.db.dao.SystemSettingsDao;
/*     */ import com.dreammirae.mmth.exception.InternalDBException;
/*     */ import com.dreammirae.mmth.misc.I18nMessage;
/*     */ import com.dreammirae.mmth.misc.SysEnvCommon;
/*     */ import com.dreammirae.mmth.runtime.audit.type.AlarmLevels;
/*     */ import com.dreammirae.mmth.runtime.audit.type.AuditAlarmTypes;
/*     */ import com.dreammirae.mmth.vo.AdministratorVO;
/*     */ import com.dreammirae.mmth.vo.bean.AdministratorStats;
/*     */ import com.dreammirae.mmth.vo.bean.IRestValidator;
/*     */ import com.dreammirae.mmth.vo.bean.IViewStatsLocator;
/*     */ import com.dreammirae.mmth.vo.types.AdministratorTypes;
/*     */ import com.dreammirae.mmth.web.exception.I18nMessageException;
/*     */ import com.dreammirae.mmth.web.service.ViewService;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.security.core.userdetails.UserDetails;
/*     */ import org.springframework.security.core.userdetails.UserDetailsService;
/*     */ import org.springframework.security.core.userdetails.UsernameNotFoundException;
/*     */ import org.springframework.stereotype.Service;
/*     */ 
/*     */ 
/*     */ @Service("administratorService")
/*     */ public class AdministratorService
/*     */   extends ViewService<AdministratorVO, Integer>
/*     */   implements UserDetailsService
/*     */ {
/*     */   @Autowired
/*     */   private AdministratorDao dao;
/*     */   
/*     */   public void forceChangePassword(int id, String password, AdministratorVO sessionAdmin) throws I18nMessageException {
/*  34 */     if (!password.matches("^[a-zA-Z0-9\\!\\@\\#\\?\\-\\_\\.]{5,120}$")) {
/*  35 */       throw new I18nMessageException("change-password", new I18nMessage("validate.regex.password"));
/*     */     }
/*     */     
/*     */     try {
/*  39 */       AdministratorVO target = getViewDao().getOneByPK(Integer.valueOf(id));
/*     */       
/*  41 */       if (target == null) {
/*  42 */         throw new I18nMessageException("change-password", new I18nMessage("validate.noExist"));
/*     */       }
/*  44 */       target.setPassword(SysEnvCommon.encPassword(password));
/*  45 */       getViewDao().save(target);
/*     */ 
/*     */       
/*  48 */       AuditAlarmTypes.ADMIN.raiseAlarm(sessionAdmin, 517, AlarmLevels.INFORMATION, new Object[] { sessionAdmin.getUsername(), target.getUsername() });
/*     */     }
/*  50 */     catch (I18nMessageException e) {
/*  51 */       throw e;
/*  52 */     } catch (InternalDBException e) {
/*  53 */       throw new I18nMessageException(new I18nMessage("validate.internalError", new Object[] { e.getMessage() }), e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
/*  64 */     AdministratorVO vo = getViewDao().getOneByObj(new AdministratorVO(username));
/*     */     
/*  66 */     if (vo == null) {
/*  67 */       throw new UsernameNotFoundException("Could not find user. username=" + username);
/*     */     }
/*  69 */     return (UserDetails)vo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IViewStatsLocator getViewStatsLocator() throws I18nMessageException {
/*     */     try {
/*  80 */       AdministratorStats loc = new AdministratorStats();
/*     */       
/*  82 */       int total = getViewDao().getViewConentCount(null, null, null);
/*  83 */       loc.setTotal(total);
/*     */       
/*  85 */       return (IViewStatsLocator)loc;
/*     */     }
/*  87 */     catch (InternalDBException e) {
/*  88 */       throw new I18nMessageException(new I18nMessage("validate.internalError", new Object[] { e.getMessage() }), e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void saveBefore(AdministratorVO sessionAdmin, AdministratorVO vo, AdministratorVO prev, boolean isNew) throws I18nMessageException {
/*  96 */     if (isNew) {
/*     */       
/*  98 */       if (prev != null) {
/*  99 */         throw new I18nMessageException("username", new I18nMessage("validate.existAlready", new Object[] { new I18nMessage("administrator.label.username"), vo.getUsername() }));
/*     */       }
/*     */ 
/*     */       
/* 103 */       String rawPwd = vo.getPassword();
/* 104 */       vo.setPassword(SysEnvCommon.getPwdEncoder().encode(rawPwd));
/*     */       
/* 106 */       if (vo.getAdminType().equals(AdministratorTypes.CSTEAM) || vo.getAdminType().equals(AdministratorTypes.OPTEAM)) {
/* 107 */         vo.setHomeUrl("/web/manager/branch");
/*     */       } else {
/*     */         
/* 110 */         vo.setHomeUrl("/web/manager/user");
/*     */       } 
/* 112 */       vo.setLocale(SystemSettingsDao.getSystemLocale());
/*     */     } else {
/*     */       
/* 115 */       if (prev == null) {
/* 116 */         throw new I18nMessageException("username", new I18nMessage("validate.noExist.args", new Object[] { new I18nMessage("administrator.label.username"), vo.getUsername() }));
/*     */       }
/*     */ 
/*     */       
/* 120 */       vo.setPassword(prev.getPassword());
/* 121 */       if (vo.getAdminType().equals(AdministratorTypes.CSTEAM) || vo.getAdminType().equals(AdministratorTypes.OPTEAM)) {
/* 122 */         vo.setHomeUrl("/web/manager/branch");
/*     */       } else {
/*     */         
/* 125 */         vo.setHomeUrl("/web/manager/user");
/*     */       } 
/* 127 */       vo.setLastRemoteAddr(prev.getLastRemoteAddr());
/* 128 */       vo.setTsLastLogin(prev.getTsLastLogin());
/* 129 */       vo.setTsReg(prev.getTsReg());
/* 130 */       vo.setLocale((prev.getLocale() == null) ? SystemSettingsDao.getSystemLocale() : prev.getLocale());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void saveAfter(AdministratorVO sessionAdmin, AdministratorVO vo, AdministratorVO prev, boolean isNew) throws I18nMessageException {
/* 137 */     vo.setPassword(null);
/*     */ 
/*     */     
/* 140 */     if (isNew) {
/* 141 */       AuditAlarmTypes.ADMIN.raiseAlarm(sessionAdmin, 514, AlarmLevels.INFORMATION, new Object[] { sessionAdmin.getUsername(), vo.getUsername() });
/*     */     } else {
/* 143 */       AuditAlarmTypes.ADMIN.raiseAlarm(sessionAdmin, 515, AlarmLevels.INFORMATION, new Object[] { sessionAdmin.getUsername(), vo.getUsername() });
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void deleteBefore(AdministratorVO sessionAdmin, Integer id, AdministratorVO vo) throws I18nMessageException {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void deleteAfter(AdministratorVO sessionAdmin, Integer id, AdministratorVO deleted) throws I18nMessageException {
/* 156 */     AuditAlarmTypes.ADMIN.raiseAlarm(sessionAdmin, 516, AlarmLevels.INFORMATION, new Object[] { sessionAdmin.getUsername(), deleted.getUsername() });
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isNew(AdministratorVO vo) {
/* 161 */     return (-1 == vo.getId());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected AdministratorDao getViewDao() {
/* 167 */     if (this.dao == null) {
/* 168 */       this.dao = new AdministratorDao();
/*     */     }
/*     */     
/* 171 */     return this.dao;
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\web\service\AdministratorService.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */