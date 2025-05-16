/*     */ package WEB-INF.classes.com.dreammirae.mmth.db.dao;
/*     */ 
/*     */ import com.dreammirae.mmth.authentication.AuthMethodTypes;
/*     */ import com.dreammirae.mmth.db.dao.BaseDao;
/*     */ import com.dreammirae.mmth.db.dao.IViewDao;
/*     */ import com.dreammirae.mmth.db.dao.queries.DMLServiceLog;
/*     */ import com.dreammirae.mmth.db.dao.queries.DMLServiceLogAnnotation;
/*     */ import com.dreammirae.mmth.exception.InternalDBException;
/*     */ import com.dreammirae.mmth.vo.ServiceLogVO;
/*     */ import com.dreammirae.mmth.vo.UserVO;
/*     */ import com.dreammirae.mmth.vo.bean.ICustomLogData;
/*     */ import com.dreammirae.mmth.vo.bean.MiraeAssetVietnamLogData;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.springframework.stereotype.Repository;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Repository("serviceLogDao")
/*     */ public class ServiceLogDao
/*     */   extends BaseDao
/*     */   implements IViewDao<ServiceLogVO, Long>
/*     */ {
/*     */   public void saveAsBatch(List<ServiceLogVO> serviceLogs) throws InternalDBException {
/*  27 */     doSaveAsBatch("INSERT INTO MMTH_ServiceLogs (authType, username, opType, reqType, actionType, returnCode, deviceId, deviceType, pkgUnique, aaid, tokenId, description, tsReg, regDate, regHour) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)", DMLServiceLog.getBatchPreparedStatementSetter(serviceLogs));
/*     */   }
/*     */   
/*     */   public void saveAsBatchWithAppAgent(List<ServiceLogVO> serviceLogs) throws InternalDBException {
/*  31 */     doSaveAsBatch("INSERT INTO MMTH_ServiceLogs (authType, username, opType, reqType, actionType, returnCode, deviceId, deviceType, pkgUnique, aaid, tokenId, description, tsReg, regDate, regHour) SELECT ?, ?, ?, ?, ?, ?, ud.deviceId, ud.deviceType, aa.pkgUnique, ?, ?, ?, ?, ?, ? FROM MMTH_DeviceAppAgents da LEFT JOIN MMTH_UserDevices ud ON da.userDeviceId = ud.id LEFT JOIN MMTH_AppAgents aa on da.agentId = aa.id WHERE da.id = ?", DMLServiceLog.getBatchPreparedStatementSetterWithDeviceApp(serviceLogs));
/*     */   }
/*     */ 
/*     */   
/*     */   public void saveAsBatchWithCustomData(List<ServiceLogVO> serviceLogs) throws InternalDBException {
/*  36 */     for (ServiceLogVO vo : serviceLogs) {
/*  37 */       insert(vo);
/*  38 */       if (vo.getCustomData() != null && vo.getCustomData() instanceof com.dreammirae.mmth.vo.bean.GlobalWibeeLogData) {
/*     */         
/*     */         try {
/*  41 */           doUpdate("INSERT INTO MMTH_ServiceLogAnnotations (serviceLogId, regEmpNo) VALUES (?, ?)", DMLServiceLogAnnotation.toInsertParams_globalWibee(vo));
/*  42 */         } catch (Exception ignore) {}
/*     */         
/*     */         continue;
/*     */       } 
/*  46 */       if (vo.getCustomData() != null && vo.getCustomData() instanceof MiraeAssetVietnamLogData) {
/*     */         
/*     */         try {
/*  49 */           doUpdate("INSERT INTO MMTH_ServiceLogAnnotations (serviceLogId, ip, macAddress) VALUES (?, ?, ?)", DMLServiceLogAnnotation.toInsertParams_miraeAssetVietnam(vo));
/*  50 */         } catch (Exception ignore) {}
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void save(ServiceLogVO vo) throws InternalDBException {
/*  59 */     if (-1L == vo.getId()) {
/*  60 */       insert(vo);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void insert(ServiceLogVO vo) throws InternalDBException {
/*  66 */     vo.setId(doInsertLong("INSERT INTO MMTH_ServiceLogs (authType, username, opType, reqType, actionType, returnCode, deviceId, deviceType, pkgUnique, aaid, tokenId, description, tsReg, regDate, regHour) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)", DMLServiceLog.toInsertParams(vo), DMLServiceLog.getInsertTypes()));
/*     */   }
/*     */ 
/*     */   
/*     */   public ServiceLogVO getOneByPK(Long id) throws InternalDBException {
/*  71 */     return (ServiceLogVO)queryForObject("SELECT a.id, authType, username, opType, reqType, actionType, returnCode, a.deviceId, b.model, a.deviceType, pkgUnique, aaid, tokenId, description, a.tsReg, regDate, regHour FROM (SELECT id, authType, username, opType, reqType, actionType, returnCode, deviceId, null AS model, deviceType, pkgUnique, aaid, tokenId, description, tsReg, regDate, regHour FROM MMTH_ServiceLogs  WHERE id = ?) a LEFT OUTER JOIN MMTH_UserDevices b ON b.deviceId = a.deviceId AND a.username = (SELECT username FROM MMTH_users WHERE id = b.userId)", DMLServiceLog.toSelectPKParam(id.longValue()), DMLServiceLog.getRowMapper());
/*     */   }
/*     */ 
/*     */   
/*     */   public ServiceLogVO getOneByObj(ServiceLogVO vo) throws InternalDBException {
/*  76 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<ServiceLogVO> getViewContent(int limit, int offset, ServiceLogVO vo, Long tsFrom, Long tsTo) throws InternalDBException {
/*  81 */     List<ServiceLogVO> list = new ArrayList<>();
/*  82 */     if (limit == 0) {
/*  83 */       list = queryForList(DMLServiceLog.selectContents(vo, tsFrom, tsTo), DMLServiceLog.toSelectParams(vo, tsFrom, tsTo), DMLServiceLog.getRowMapper());
/*     */     } else {
/*  85 */       list = queryForListWithLimit(DMLServiceLog.selectContents(vo, tsFrom, tsTo), DMLServiceLog.toSelectParams(vo, tsFrom, tsTo), DMLServiceLog.getRowMapper(), limit, offset);
/*     */     } 
/*  87 */     for (ServiceLogVO log : list) {
/*     */       
/*  89 */       MiraeAssetVietnamLogData logData = (MiraeAssetVietnamLogData)queryForObject("SELECT ip, macAddress FROM MMTH_ServiceLogAnnotations WHERE serviceLogId = ?", DMLServiceLogAnnotation.toSelect_miraeAssetVietnam(log), DMLServiceLogAnnotation.getRowMapper(), null);
/*  90 */       log.setCustomData((ICustomLogData)logData);
/*     */     } 
/*     */     
/*  93 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getViewConentCount(ServiceLogVO vo, Long tsFrom, Long tsTo) throws InternalDBException {
/*  98 */     return queryForInt(DMLServiceLog.selectContentCount(vo, tsFrom, tsTo), DMLServiceLog.toSelectParams(vo, tsFrom, tsTo), 0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int deleteByPk(Long id) throws InternalDBException {
/* 104 */     if (id == null || id.longValue() < 1L)
/*     */     {
/* 106 */       return -1;
/*     */     }
/*     */     
/* 109 */     return doUpdate("DELETE FROM MMTH_ServiceLogs WHERE id = ?", DMLServiceLog.toDeletePKParam(id.longValue()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int delete(ServiceLogVO vo) throws InternalDBException {
/* 119 */     if (vo == null || vo.getTsReg() == null) {
/* 120 */       return -1;
/*     */     }
/*     */     
/* 123 */     return doUpdate("DELETE FROM MMTH_ServiceLogs WHERE tsReg < ?", DMLServiceLog.toDeleteParams(vo));
/*     */   }
/*     */   
/*     */   public List<ServiceLogVO> getLatestLog(UserVO user, int limit) {
/* 127 */     return queryForListWithLimit("SELECT id, authType, username, opType, reqType, actionType, returnCode, deviceId, null AS model, deviceType, pkgUnique, aaid, tokenId, description, tsReg, regDate, regHour FROM MMTH_ServiceLogs WHERE username=? AND (opType=? OR opType=?) ORDER BY id desc", DMLServiceLog.toSelectLatestAuthLog(user), DMLServiceLog.getRowMapper(), limit, 0);
/*     */   }
/*     */   
/*     */   public List<ServiceLogVO> getServiceLogForOperation(UserVO user, AuthMethodTypes authType, int limit) {
/* 131 */     return queryForListWithLimit("SELECT id, authType, username, opType, reqType, actionType, returnCode, deviceId, null AS model, deviceType, pkgUnique, aaid, tokenId, description, tsReg, regDate, regHour FROM MMTH_ServiceLogs WHERE username=? AND authType=? AND opType not in (?, ?, ?, ?) ORDER BY id desc", DMLServiceLog.toSelectLogForOperation(user, authType), DMLServiceLog.getRowMapper(), limit, 0);
/*     */   }
/*     */   
/*     */   public MiraeAssetVietnamLogData getCustomData(ServiceLogVO vo) {
/* 135 */     return (MiraeAssetVietnamLogData)queryForObject("SELECT ip, macAddress FROM MMTH_ServiceLogAnnotations WHERE serviceLogId = ?", DMLServiceLogAnnotation.toSelect_miraeAssetVietnam(vo), DMLServiceLogAnnotation.getRowMapper());
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\db\dao\ServiceLogDao.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */