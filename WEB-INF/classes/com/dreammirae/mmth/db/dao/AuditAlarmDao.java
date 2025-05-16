/*     */ package WEB-INF.classes.com.dreammirae.mmth.db.dao;
/*     */ 
/*     */ import com.dreammirae.mmth.db.dao.BaseDao;
/*     */ import com.dreammirae.mmth.db.dao.IViewDao;
/*     */ import com.dreammirae.mmth.db.dao.queries.DMLAuditAlarm;
/*     */ import com.dreammirae.mmth.exception.InternalDBException;
/*     */ import com.dreammirae.mmth.vo.AdministratorVO;
/*     */ import com.dreammirae.mmth.vo.AuditAlarmVO;
/*     */ import com.dreammirae.mmth.vo.types.AckCodes;
/*     */ import java.util.List;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.stereotype.Repository;
/*     */ 
/*     */ @Repository("auditAlarmDao")
/*     */ public class AuditAlarmDao
/*     */   extends BaseDao
/*     */   implements IViewDao<AuditAlarmVO, Long> {
/*  19 */   private static final Logger LOG = LoggerFactory.getLogger(com.dreammirae.mmth.db.dao.AuditAlarmDao.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void save(AuditAlarmVO vo) throws InternalDBException {
/*  26 */     if (-1L == vo.getId()) {
/*  27 */       insert(vo);
/*     */     }
/*     */   }
/*     */   
/*     */   private void insert(AuditAlarmVO alarm) throws InternalDBException {
/*  32 */     alarm.setId(doInsertLong("INSERT INTO MMTH_AuditAlarms (auditType, actionType, alarmLevel, description, tsReg) VALUES (?, ?, ?, ?, ?)", DMLAuditAlarm.toInsertParams(alarm), DMLAuditAlarm.getInsertTypes()));
/*     */   }
/*     */   
/*     */   public void saveAsBatch(List<AuditAlarmVO> alarms) {
/*     */     try {
/*  37 */       this.ejt.batchUpdate("INSERT INTO MMTH_AuditAlarms (auditType, actionType, alarmLevel, description, tsReg) VALUES (?, ?, ?, ?, ?)", DMLAuditAlarm.getBatchPreparedStatementSetter(alarms));
/*  38 */     } catch (Exception e) {
/*  39 */       LOG.error("Failed to batch update...", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateAcknowledge(long[] ids, AdministratorVO admin) {
/*  45 */     doUpdate(DMLAuditAlarm.getUpdateAcknowledge(ids), DMLAuditAlarm.toUpdateAcknowledgeParams(ids, admin));
/*     */   }
/*     */   
/*     */   public long noAcknowledgeCount() {
/*  49 */     AuditAlarmVO vo = new AuditAlarmVO();
/*  50 */     vo.setAck(AckCodes.NONE);
/*  51 */     return queryForLong(DMLAuditAlarm.selectContentConunt(vo, null, null), DMLAuditAlarm.toSelectParams(vo, null, null), 0L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AuditAlarmVO getOneByPK(Long id) throws InternalDBException {
/*  61 */     if (id.longValue() > 0L) {
/*  62 */       return null;
/*     */     }
/*     */     
/*  65 */     return (AuditAlarmVO)queryForObject("SELECT id, auditType, actionType, alarmLevel, description, tsReg, ack, ackAdmin, tsAck FROM MMTH_AuditAlarms WHERE id=? ", DMLAuditAlarm.toSelectPKParam(id.longValue()), DMLAuditAlarm.getRowMapper());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AuditAlarmVO getOneByObj(AuditAlarmVO vo) throws InternalDBException {
/*  74 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<AuditAlarmVO> getViewContent(int limit, int offset, AuditAlarmVO vo, Long tsFrom, Long tsTo) throws InternalDBException {
/*  84 */     String sql = DMLAuditAlarm.selectContents(vo, tsFrom, tsTo);
/*  85 */     Object[] args = DMLAuditAlarm.toSelectParams(vo, tsFrom, tsTo);
/*     */     
/*  87 */     if (args == null || args.length < 1) {
/*  88 */       return queryForListWithLimit(sql, DMLAuditAlarm.getRowMapper(), limit, offset);
/*     */     }
/*  90 */     return queryForListWithLimit(sql, args, DMLAuditAlarm.getRowMapper(), limit, offset);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getViewConentCount(AuditAlarmVO vo, Long tsFrom, Long tsTo) throws InternalDBException {
/*  99 */     String sql = DMLAuditAlarm.selectContentConunt(vo, tsFrom, tsTo);
/* 100 */     Object[] args = DMLAuditAlarm.toSelectParams(vo, tsFrom, tsTo);
/* 101 */     return queryForInt(sql, args, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int deleteByPk(Long id) throws InternalDBException {
/* 110 */     if (id == null || id.longValue() < 1L)
/*     */     {
/* 112 */       return -1;
/*     */     }
/*     */     
/* 115 */     return doUpdate("DELETE FROM MMTH_AuditAlarms WHERE id = ?", DMLAuditAlarm.toDeletePKParam(id.longValue()));
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
/*     */   public int delete(AuditAlarmVO vo) throws InternalDBException {
/* 129 */     if (vo == null || vo.getTsReg() == null) {
/* 130 */       return -1;
/*     */     }
/*     */     
/* 133 */     return doUpdate("DELETE FROM MMTH_AuditAlarms WHERE tsReg < ?", DMLAuditAlarm.toDeleteParams(vo));
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\db\dao\AuditAlarmDao.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */