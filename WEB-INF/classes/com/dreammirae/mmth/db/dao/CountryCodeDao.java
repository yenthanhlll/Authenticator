/*     */ package WEB-INF.classes.com.dreammirae.mmth.db.dao;
/*     */ 
/*     */ import com.dreammirae.mmth.authentication.AuthMethodTypes;
/*     */ import com.dreammirae.mmth.db.dao.BaseDao;
/*     */ import com.dreammirae.mmth.db.dao.SystemSettingsDao;
/*     */ import com.dreammirae.mmth.runtime.service.type.LogActionTypes;
/*     */ import com.dreammirae.mmth.runtime.service.type.LogOperationTypes;
/*     */ import com.dreammirae.mmth.vo.bean.CountryStatsLocator;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.springframework.jdbc.core.RowCallbackHandler;
/*     */ import org.springframework.stereotype.Repository;
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
/*     */ @Repository("countryCodeDao")
/*     */ public class CountryCodeDao
/*     */   extends BaseDao
/*     */ {
/*     */   private static final String STAT_QUERY = "SELECT u.countryCode, count(u.id) FROM MMTH_UserAnnotations u INNER JOIN MMTH_ServiceLogs sl ON u.username = sl.username WHERE sl.tsReg >=? AND sl.tsReg < ? AND sl.opType = ? AND sl.actionType = ? AND sl.authType = ? GROUP BY u.countryCode";
/*     */   private static final String DEREG_STAT_QUERY = "SELECT u.countryCode, count(u.id) FROM MMTH_UserAnnotations u INNER JOIN MMTH_ServiceLogs sl ON u.username = sl.username WHERE sl.tsReg >=? AND sl.tsReg < ? AND sl.opType in (? ,?) AND sl.actionType = ? AND sl.authType = ? GROUP BY u.countryCode";
/*     */   
/*     */   public List<CountryStatsLocator> getStatsByCountry(Long tsStart, Long tsEnd) {
/*  34 */     List<CountryStatsLocator> result = createLocatorInstance();
/*  35 */     CountryStatsLocator etc = new CountryStatsLocator();
/*     */ 
/*     */ 
/*     */     
/*  39 */     this.ejt.query("SELECT u.countryCode, count(u.id) FROM MMTH_UserAnnotations u INNER JOIN MMTH_ServiceLogs sl ON u.username = sl.username WHERE sl.tsReg >=? AND sl.tsReg < ? AND sl.opType = ? AND sl.actionType = ? AND sl.authType = ? GROUP BY u.countryCode", new Object[] {
/*  40 */           Long.valueOf(tsStart.longValue()), Long.valueOf(tsEnd.longValue()), Integer.valueOf(LogOperationTypes.REG.getCode()), Integer.valueOf(LogActionTypes.SUCCESS.getCode()), Integer.valueOf(AuthMethodTypes.BIOTP.getCode()) }, (RowCallbackHandler)new Object(this, result, etc));
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
/*  64 */     this.ejt.query("SELECT u.countryCode, count(u.id) FROM MMTH_UserAnnotations u INNER JOIN MMTH_ServiceLogs sl ON u.username = sl.username WHERE sl.tsReg >=? AND sl.tsReg < ? AND sl.opType = ? AND sl.actionType = ? AND sl.authType = ? GROUP BY u.countryCode", new Object[] {
/*  65 */           Long.valueOf(tsStart.longValue()), Long.valueOf(tsEnd.longValue()), Integer.valueOf(LogOperationTypes.OTP_AUTH.getCode()), Integer.valueOf(LogActionTypes.SUCCESS.getCode()), Integer.valueOf(AuthMethodTypes.BIOTP.getCode()) }, (RowCallbackHandler)new Object(this, result, etc));
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
/*  91 */     this.ejt.query("SELECT u.countryCode, count(u.id) FROM MMTH_UserAnnotations u INNER JOIN MMTH_ServiceLogs sl ON u.username = sl.username WHERE sl.tsReg >=? AND sl.tsReg < ? AND sl.opType in (? ,?) AND sl.actionType = ? AND sl.authType = ? GROUP BY u.countryCode", new Object[] {
/*  92 */           Long.valueOf(tsStart.longValue()), Long.valueOf(tsEnd.longValue()), Integer.valueOf(LogOperationTypes.DEREG.getCode()), Integer.valueOf(LogOperationTypes.FORCE_DEREG.getCode()), Integer.valueOf(LogActionTypes.SUCCESS.getCode()), Integer.valueOf(AuthMethodTypes.BIOTP.getCode()) }, (RowCallbackHandler)new Object(this, result, etc));
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
/* 117 */     result.add(etc);
/* 118 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   private List<CountryStatsLocator> createLocatorInstance() {
/* 123 */     List<String> cc = SystemSettingsDao.getCountryCodeRegistered();
/*     */     
/* 125 */     List<CountryStatsLocator> list = new ArrayList<>();
/*     */     
/* 127 */     for (String code : cc) {
/* 128 */       CountryStatsLocator loc = new CountryStatsLocator();
/* 129 */       loc.setCountryCode(code);
/* 130 */       list.add(loc);
/*     */     } 
/*     */ 
/*     */     
/* 134 */     return list;
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\db\dao\CountryCodeDao.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */