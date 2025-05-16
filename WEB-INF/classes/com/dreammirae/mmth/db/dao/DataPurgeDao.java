/*    */ package WEB-INF.classes.com.dreammirae.mmth.db.dao;
/*    */ 
/*    */ import com.dreammirae.mmth.db.DatabaseAccess;
/*    */ import com.dreammirae.mmth.db.dao.BaseDao;
/*    */ import com.dreammirae.mmth.runtime.audit.type.AlarmLevels;
/*    */ import com.dreammirae.mmth.runtime.audit.type.AuditAlarmTypes;
/*    */ import com.dreammirae.mmth.vo.bean.DataPurgeTableStats;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ 
/*    */ public class DataPurgeDao
/*    */   extends BaseDao
/*    */ {
/* 14 */   private static final Logger LOG = LoggerFactory.getLogger(com.dreammirae.mmth.db.dao.DataPurgeDao.class);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static final String SERVICE_LOGS_SIZE = "SELECT sum(vsize(nvl(ID, 0)) + vsize(nvl(AUTHTYPE, 0)) + vsize(nvl(USERNAME, 0)) + vsize(nvl(OPTYPE, 0)) + vsize(nvl(ACTIONTYPE, 0)) + vsize(nvl(RETURNCODE, 0)) + vsize(nvl(DEVICEID, 0)) + vsize(nvl(DEVICETYPE, 0)) + vsize(nvl(PKGUNIQUE, 0)) + vsize(nvl(AAID, 0)) + vsize(nvl(TOKENID, 0)) + vsize(nvl(DESCRIPTION, 0)) + vsize(nvl(TSREG, 0)) + vsize(nvl(REGDATE, 0)) + vsize(nvl(REGHOUR, 0)) +  3) AS bytes FROM MMTH_ServiceLogs";
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static final String SERVICE_LOGS_COUNT = "SELECT COUNT(*) FROM MMTH_ServiceLogs";
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static final String SERVICE_LOGS_COUNT_COND = "SELECT COUNT(*) FROM MMTH_ServiceLogs WHERE TSREG < ?";
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static final String SERVICE_LOGS_SIZE_COND = "SELECT sum(vsize(nvl(ID, 0)) + vsize(nvl(AUTHTYPE, 0)) + vsize(nvl(USERNAME, 0)) + vsize(nvl(OPTYPE, 0)) + vsize(nvl(ACTIONTYPE, 0)) + vsize(nvl(RETURNCODE, 0)) + vsize(nvl(DEVICEID, 0)) + vsize(nvl(DEVICETYPE, 0)) + vsize(nvl(PKGUNIQUE, 0)) + vsize(nvl(AAID, 0)) + vsize(nvl(TOKENID, 0)) + vsize(nvl(DESCRIPTION, 0)) + vsize(nvl(TSREG, 0)) + vsize(nvl(REGDATE, 0)) + vsize(nvl(REGHOUR, 0)) +  3) AS bytes FROM MMTH_ServiceLogs WHERE TSREG < ?";
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static final String SERIVICE_LOGS_PURGE = "DELETE FROM MMTH_ServiceLogs WHERE TSREG < ?";
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private Long getLogSize() {
/* 45 */     return Long.valueOf(queryForLong("SELECT sum(vsize(nvl(ID, 0)) + vsize(nvl(AUTHTYPE, 0)) + vsize(nvl(USERNAME, 0)) + vsize(nvl(OPTYPE, 0)) + vsize(nvl(ACTIONTYPE, 0)) + vsize(nvl(RETURNCODE, 0)) + vsize(nvl(DEVICEID, 0)) + vsize(nvl(DEVICETYPE, 0)) + vsize(nvl(PKGUNIQUE, 0)) + vsize(nvl(AAID, 0)) + vsize(nvl(TOKENID, 0)) + vsize(nvl(DESCRIPTION, 0)) + vsize(nvl(TSREG, 0)) + vsize(nvl(REGDATE, 0)) + vsize(nvl(REGHOUR, 0)) +  3) AS bytes FROM MMTH_ServiceLogs", null, 0L));
/*    */   }
/*    */   
/*    */   private Long getLogSize(long tsBase) {
/* 49 */     return Long.valueOf(queryForLong("SELECT sum(vsize(nvl(ID, 0)) + vsize(nvl(AUTHTYPE, 0)) + vsize(nvl(USERNAME, 0)) + vsize(nvl(OPTYPE, 0)) + vsize(nvl(ACTIONTYPE, 0)) + vsize(nvl(RETURNCODE, 0)) + vsize(nvl(DEVICEID, 0)) + vsize(nvl(DEVICETYPE, 0)) + vsize(nvl(PKGUNIQUE, 0)) + vsize(nvl(AAID, 0)) + vsize(nvl(TOKENID, 0)) + vsize(nvl(DESCRIPTION, 0)) + vsize(nvl(TSREG, 0)) + vsize(nvl(REGDATE, 0)) + vsize(nvl(REGHOUR, 0)) +  3) AS bytes FROM MMTH_ServiceLogs WHERE TSREG < ?", new Object[] {
/* 50 */             Long.valueOf(tsBase) }, 0L));
/*    */   }
/*    */ 
/*    */   
/*    */   private Long getRowCount() {
/* 55 */     return Long.valueOf(queryForLong("SELECT COUNT(*) FROM MMTH_ServiceLogs", null, 0L));
/*    */   }
/*    */   
/*    */   private Long getRowCount(long tsBase) {
/* 59 */     return Long.valueOf(queryForLong("SELECT COUNT(*) FROM MMTH_ServiceLogs WHERE TSREG < ?", new Object[] {
/* 60 */             Long.valueOf(tsBase) }, 0L));
/*    */   }
/*    */   
/*    */   private Long deleteLogs(long tsBase) {
/* 64 */     int deleted = doUpdate("DELETE FROM MMTH_ServiceLogs WHERE TSREG < ?", new Object[] { Long.valueOf(tsBase) });
/* 65 */     return Long.valueOf(deleted);
/*    */   }
/*    */ 
/*    */   
/*    */   public static void doDataPurge(DataPurgeTableStats stats, long tsBase) {
/*    */     try {
/* 71 */       com.dreammirae.mmth.db.dao.DataPurgeDao dao = new com.dreammirae.mmth.db.dao.DataPurgeDao();
/* 72 */       stats.setDeletedRows(dao.deleteLogs(tsBase));
/* 73 */       stats.setTotalRows(dao.getRowCount());
/*    */       
/* 75 */       if (DatabaseAccess.DatabaseType.JEUS_ORACLE.equals(dao.type) || DatabaseAccess.DatabaseType.ORACLE.equals(dao.type)) {
/* 76 */         stats.setTotalSize(dao.getLogSize());
/*    */       }
/*    */     }
/* 79 */     catch (Exception e) {
/* 80 */       LOG.error("Failed to purging data...", e);
/* 81 */       AuditAlarmTypes.SYSTEM.raiseAlarm(null, 261, AlarmLevels.CRITICAL, new Object[] { "Failed to purging data..., messages=" + e.getMessage() });
/*    */     } 
/*    */   }
/*    */   
/*    */   public static void readPurgingData(DataPurgeTableStats stats, long tsBase) {
/*    */     try {
/* 87 */       com.dreammirae.mmth.db.dao.DataPurgeDao dao = new com.dreammirae.mmth.db.dao.DataPurgeDao();
/* 88 */       stats.setTotalRows(dao.getRowCount());
/* 89 */       stats.setTargetRows(dao.getRowCount(tsBase));
/*    */       
/* 91 */       if (DatabaseAccess.DatabaseType.JEUS_ORACLE.equals(dao.type) || DatabaseAccess.DatabaseType.ORACLE.equals(dao.type)) {
/* 92 */         stats.setTotalSize(dao.getLogSize());
/* 93 */         stats.setTargetSize(dao.getLogSize(tsBase));
/*    */       } 
/* 95 */     } catch (Exception e) {
/* 96 */       LOG.error("Failed to read data...", e);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\db\dao\DataPurgeDao.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */