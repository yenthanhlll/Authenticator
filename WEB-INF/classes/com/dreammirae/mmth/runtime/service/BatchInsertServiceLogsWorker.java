/*    */ package WEB-INF.classes.com.dreammirae.mmth.runtime.service;
/*    */ 
/*    */ import com.dreammirae.mmth.db.dao.ServiceLogDao;
/*    */ import com.dreammirae.mmth.vo.ServiceLogVO;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ public class BatchInsertServiceLogsWorker
/*    */   implements Runnable
/*    */ {
/*    */   private final List<ServiceLogVO> serviceLogs;
/*    */   private final ServiceLogDao serviceLogDao;
/*    */   
/*    */   public BatchInsertServiceLogsWorker(ServiceLogDao serviceLogDao, List<ServiceLogVO> serviceLogs) {
/* 15 */     this.serviceLogs = serviceLogs;
/* 16 */     this.serviceLogDao = serviceLogDao;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void run() {
/*    */     try {
/* 24 */       List<ServiceLogVO> insertLog = new ArrayList<>();
/* 25 */       List<ServiceLogVO> insertAppLog = new ArrayList<>();
/* 26 */       List<ServiceLogVO> insertCustomLog = new ArrayList<>();
/*    */       
/* 28 */       for (ServiceLogVO vo : this.serviceLogs) {
/* 29 */         if (vo.getCustomData() != null) {
/* 30 */           insertCustomLog.add(vo); continue;
/* 31 */         }  if (vo.getDeviceAppAgent() != null) {
/* 32 */           insertAppLog.add(vo); continue;
/*    */         } 
/* 34 */         insertLog.add(vo);
/*    */       } 
/*    */ 
/*    */       
/* 38 */       if (!insertCustomLog.isEmpty()) {
/* 39 */         this.serviceLogDao.saveAsBatchWithCustomData(insertCustomLog);
/*    */       }
/* 41 */       if (!insertAppLog.isEmpty()) {
/* 42 */         this.serviceLogDao.saveAsBatchWithAppAgent(insertAppLog);
/*    */       }
/* 44 */       if (!insertLog.isEmpty()) {
/* 45 */         this.serviceLogDao.saveAsBatch(insertLog);
/*    */       }
/* 47 */     } catch (Exception e) {
/* 48 */       e.printStackTrace();
/*    */     } 
/*    */     
/* 51 */     this.serviceLogs.clear();
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\runtime\service\BatchInsertServiceLogsWorker.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */