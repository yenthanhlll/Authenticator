/*     */ package WEB-INF.classes.com.dreammirae.mmth.db.dao.queries;
/*     */ 
/*     */ import com.dreammirae.mmth.config.Commons;
/*     */ import com.dreammirae.mmth.db.dao.SystemSettingsDao;
/*     */ import com.dreammirae.mmth.external.ExternalWebApiTypes;
/*     */ import com.dreammirae.mmth.util.StringUtils;
/*     */ import com.dreammirae.mmth.vo.AdministratorVO;
/*     */ import com.dreammirae.mmth.vo.AuditAlarmVO;
/*     */ import com.dreammirae.mmth.vo.types.AckCodes;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.springframework.jdbc.core.BatchPreparedStatementSetter;
/*     */ import org.springframework.jdbc.core.RowMapper;
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
/*     */ public final class DMLAuditAlarm
/*     */ {
/*     */   public static final String INSERT = "INSERT INTO MMTH_AuditAlarms (auditType, actionType, alarmLevel, description, tsReg) VALUES (?, ?, ?, ?, ?)";
/*     */   public static final String UPDATE = "UPDATE MMTH_AuditAlarms set ack=?, ackAdmin=?, tsAck=? WHERE id=?";
/*     */   private static final String UPDATE_BATCH = "UPDATE MMTH_AuditAlarms set ack=?, ackAdmin=?, tsAck=? WHERE id in ";
/*     */   public static final String DELETE_BY_PK = "DELETE FROM MMTH_AuditAlarms WHERE id = ?";
/*     */   public static final String DELETE_BY_COND = "DELETE FROM MMTH_AuditAlarms WHERE tsReg < ?";
/*     */   private static final String SELECT_PREFIX = "SELECT id, auditType, actionType, alarmLevel, description, tsReg, ack, ackAdmin, tsAck FROM MMTH_AuditAlarms ";
/*     */   private static final String SELECT_CONTENTS_SUFFIX = " ORDER BY id desc";
/*     */   private static final String SELECT_CONTENT_COUNT_PREFIX = "SELECT COUNT(*) FROM MMTH_AuditAlarms ";
/*     */   private static final String WHERE = " WHERE ";
/*     */   private static final String AND = " AND ";
/*     */   public static final String SELECT_ONE_PK = "SELECT id, auditType, actionType, alarmLevel, description, tsReg, ack, ackAdmin, tsAck FROM MMTH_AuditAlarms WHERE id=? ";
/*     */   
/*     */   public static int[] getInsertTypes() {
/*  68 */     return new int[] { 4, 4, 4, 12, -5 };
/*     */   }
/*     */   
/*     */   public static Object[] toInsertParams(AuditAlarmVO vo) {
/*  72 */     String desc = StringUtils.trimUTF8(vo.getDescription(), 1024, Commons.ctx.getDatabaseAccess().useSafeUtf8());
/*  73 */     vo.setTsReg(Long.valueOf(System.currentTimeMillis()));
/*  74 */     return new Object[] { Integer.valueOf(vo.getAuditType().getCode()), Integer.valueOf(vo.getActionType()), Integer.valueOf(vo.getAlarmLevel().getCode()), desc, vo.getTsReg() };
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
/*     */   public static int[] getUpdateTypes() {
/*  86 */     return new int[] { 1, 12, -5, 4 };
/*     */   }
/*     */   
/*     */   public static Object[] toUpdateParams(AuditAlarmVO vo) {
/*  90 */     vo.setTsAck(Long.valueOf(System.currentTimeMillis()));
/*  91 */     return new Object[] { vo.getAck().getCode(), vo.getAckAdmin(), vo.getTsAck(), Long.valueOf(vo.getId()) };
/*     */   }
/*     */   
/*     */   public static String getUpdateAcknowledge(long[] ids) {
/*  95 */     StringBuilder sb = new StringBuilder(128);
/*  96 */     sb.append("UPDATE MMTH_AuditAlarms set ack=?, ackAdmin=?, tsAck=? WHERE id in ");
/*  97 */     sb.append("(");
/*  98 */     for (int i = 0, len = ids.length; i < len; i++) {
/*  99 */       sb.append("?");
/* 100 */       if (i < len - 1) {
/* 101 */         sb.append(",");
/*     */       }
/*     */     } 
/*     */     
/* 105 */     sb.append(")");
/*     */     
/* 107 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public static Object[] toUpdateAcknowledgeParams(long[] ids, AdministratorVO admin) {
/* 112 */     List<Object> list = new ArrayList();
/* 113 */     list.add(AckCodes.ACK.getCode());
/* 114 */     list.add(admin.getUsername());
/* 115 */     list.add(Long.valueOf(System.currentTimeMillis()));
/*     */     
/* 117 */     for (long id : ids) {
/* 118 */       list.add(Long.valueOf(id));
/*     */     }
/*     */     
/* 121 */     return list.toArray();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object[] toDeletePKParam(long id) {
/* 131 */     return new Object[] { Long.valueOf(id) };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object[] toDeleteParams(AuditAlarmVO vo) {
/* 137 */     return new Object[] { Long.valueOf(vo.getTsReg().longValue()) };
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
/*     */ 
/*     */   
/*     */   public static Object[] toSelectPKParam(long id) {
/* 153 */     return new Object[] { Long.valueOf(id) };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String selectContents(AuditAlarmVO vo, Long tsFrom, Long tsTo) {
/* 159 */     StringBuilder sb = new StringBuilder(255);
/*     */ 
/*     */     
/* 162 */     sb.append("SELECT id, auditType, actionType, alarmLevel, description, tsReg, ack, ackAdmin, tsAck FROM MMTH_AuditAlarms ");
/*     */     
/* 164 */     appendCondition(sb, vo, tsFrom, tsTo);
/*     */     
/* 166 */     sb.append(" ORDER BY id desc");
/*     */     
/* 168 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public static String selectContentConunt(AuditAlarmVO vo, Long tsFrom, Long tsTo) {
/* 173 */     StringBuilder sb = new StringBuilder(255);
/*     */ 
/*     */     
/* 176 */     sb.append("SELECT COUNT(*) FROM MMTH_AuditAlarms ");
/*     */     
/* 178 */     appendCondition(sb, vo, tsFrom, tsTo);
/*     */     
/* 180 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   private static void appendCondition(StringBuilder sb, AuditAlarmVO vo, Long tsFrom, Long tsTo) {
/* 185 */     boolean hasParam = false;
/*     */     
/* 187 */     if (tsFrom != null) {
/* 188 */       sb.append(" WHERE ").append("tsReg >= ? ");
/* 189 */       hasParam = true;
/*     */     } 
/*     */     
/* 192 */     if (tsTo != null) {
/* 193 */       sb.append(hasParam ? " AND " : " WHERE ").append("tsReg <= ?");
/* 194 */       hasParam = true;
/*     */     } 
/*     */     
/* 197 */     if (vo == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 202 */     if (vo.getAuditType() != null) {
/* 203 */       sb.append(hasParam ? " AND " : " WHERE ").append("auditType = ?");
/* 204 */       hasParam = true;
/*     */     } 
/*     */     
/* 207 */     if (vo.getAlarmLevel() != null) {
/* 208 */       sb.append(hasParam ? " AND " : " WHERE ").append("alarmLevel = ?");
/* 209 */       hasParam = true;
/*     */     } 
/*     */     
/* 212 */     if (vo.getAck() != null) {
/* 213 */       sb.append(hasParam ? " AND " : " WHERE ").append("ack = ?");
/* 214 */       hasParam = true;
/*     */     } 
/*     */     
/* 217 */     if (ExternalWebApiTypes.MIRAE_ASSET_VT.equals(SystemSettingsDao.getWebApiPolicy())) {
/*     */ 
/*     */       
/* 220 */       sb.append(hasParam ? " AND " : " WHERE ").append("actionType != 261 ");
/*     */       
/* 222 */       sb.append("AND auditType NOT IN (768,1280,1536,1792,2304,2560)");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static Object[] toSelectParams(AuditAlarmVO vo, Long tsFrom, Long tsTo) {
/* 228 */     List<Object> params = new ArrayList(6);
/*     */     
/* 230 */     if (tsFrom != null) {
/* 231 */       params.add(Long.valueOf(tsFrom.longValue()));
/*     */     }
/*     */     
/* 234 */     if (tsTo != null) {
/* 235 */       params.add(Long.valueOf(tsTo.longValue()));
/*     */     }
/*     */     
/* 238 */     if (vo == null) {
/* 239 */       return params.toArray();
/*     */     }
/*     */     
/* 242 */     if (vo.getAuditType() != null) {
/* 243 */       params.add(Integer.valueOf(vo.getAuditType().getCode()));
/*     */     }
/*     */     
/* 246 */     if (vo.getAlarmLevel() != null) {
/* 247 */       params.add(Integer.valueOf(vo.getAlarmLevel().getCode()));
/*     */     }
/*     */     
/* 250 */     if (vo.getAck() != null) {
/* 251 */       params.add(vo.getAck().getCode());
/*     */     }
/*     */     
/* 254 */     return params.toArray();
/*     */   }
/*     */   
/*     */   public static RowMapper<AuditAlarmVO> getRowMapper() {
/* 258 */     return (RowMapper<AuditAlarmVO>)new AuditAlarmRowMapper(null);
/*     */   }
/*     */   
/*     */   public static BatchPreparedStatementSetter getBatchPreparedStatementSetter(List<AuditAlarmVO> alarms) {
/* 262 */     return (BatchPreparedStatementSetter)new AuditAlarmBatchPreparedStatementSetter(alarms, null);
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\db\dao\queries\DMLAuditAlarm.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */