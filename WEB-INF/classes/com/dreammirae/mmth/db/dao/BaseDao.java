/*     */ package WEB-INF.classes.com.dreammirae.mmth.db.dao;
/*     */ 
/*     */ import com.dreammirae.mmth.config.Commons;
/*     */ import com.dreammirae.mmth.db.DatabaseAccess;
/*     */ import com.dreammirae.mmth.exception.InternalDBException;
/*     */ import com.dreammirae.mmth.runtime.audit.type.AlarmLevels;
/*     */ import com.dreammirae.mmth.runtime.audit.type.AuditAlarmTypes;
/*     */ import com.dreammirae.mmth.util.db.ExtendedJdbcTemplate;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import javax.sql.DataSource;
/*     */ import org.springframework.dao.EmptyResultDataAccessException;
/*     */ import org.springframework.dao.IncorrectResultSizeDataAccessException;
/*     */ import org.springframework.dao.TypeMismatchDataAccessException;
/*     */ import org.springframework.jdbc.core.BatchPreparedStatementSetter;
/*     */ import org.springframework.jdbc.core.RowMapper;
/*     */ import org.springframework.jdbc.datasource.DataSourceTransactionManager;
/*     */ import org.springframework.transaction.PlatformTransactionManager;
/*     */ import org.springframework.transaction.support.TransactionCallback;
/*     */ import org.springframework.transaction.support.TransactionCallbackWithoutResult;
/*     */ import org.springframework.transaction.support.TransactionTemplate;
/*     */ import org.springframework.util.Assert;
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
/*     */ public class BaseDao
/*     */ {
/*     */   protected final DataSource dataSource;
/*     */   protected final ExtendedJdbcTemplate ejt;
/*     */   protected final DatabaseAccess.DatabaseType type;
/*     */   protected DataSourceTransactionManager tm;
/*     */   
/*     */   public BaseDao() {
/*  42 */     this(Commons.ctx.getDatabaseAccess().getDataSource(), Commons.ctx.getDatabaseAccess().getType());
/*     */   }
/*     */ 
/*     */   
/*     */   public BaseDao(DataSource dataSource, DatabaseAccess.DatabaseType type) {
/*  47 */     if (dataSource == null) {
/*  48 */       throw new IllegalStateException("The application context is not yet available.");
/*     */     }
/*     */     
/*  51 */     this.dataSource = dataSource;
/*  52 */     this.type = type;
/*  53 */     this.ejt = new ExtendedJdbcTemplate(this.type.getCode());
/*  54 */     this.ejt.setDataSource(dataSource);
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
/*     */ 
/*     */ 
/*     */   
/*     */   protected int doInsert(String sql, Object[] args, int[] argTypes) {
/*     */     try {
/*  74 */       Assert.isTrue((args.length == argTypes.length), "The args{Object[]} and argTypes{int[]} arrays must have same length. query = " + sql);
/*  75 */       return this.ejt.doInsertAndReturnIntKey(sql, args, argTypes);
/*  76 */     } catch (InternalDBException e) {
/*  77 */       throw e;
/*  78 */     } catch (Exception e) {
/*  79 */       AuditAlarmTypes.SYSTEM.raiseAlarm(null, 261, AlarmLevels.CRITICAL, new Object[] { "Query=" + sql + ", Args=" + Arrays.toString(args) + ", messages=" + e.getMessage() });
/*  80 */       throw new InternalDBException(e);
/*     */     } 
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
/*     */   protected long doInsertLong(String sql, Object[] args, int[] argTypes) {
/*     */     try {
/*  98 */       Assert.isTrue((args.length == argTypes.length), "The args{Object[]} and argTypes{int[]} arrays must have same length. query = " + sql);
/*  99 */       return this.ejt.doInsertAndReturnLongKey(sql, args, argTypes);
/* 100 */     } catch (InternalDBException e) {
/* 101 */       throw e;
/* 102 */     } catch (Exception e) {
/* 103 */       AuditAlarmTypes.SYSTEM.raiseAlarm(null, 261, AlarmLevels.CRITICAL, new Object[] { "Query=" + sql + ", Args=" + Arrays.toString(args) + ", messages=" + e.getMessage() });
/* 104 */       throw new InternalDBException(e);
/*     */     } 
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
/*     */   protected int doUpdate(String sql, Object[] args) {
/*     */     try {
/* 119 */       return this.ejt.update(sql, args);
/* 120 */     } catch (InternalDBException e) {
/* 121 */       throw e;
/* 122 */     } catch (Exception e) {
/* 123 */       AuditAlarmTypes.SYSTEM.raiseAlarm(null, 261, AlarmLevels.CRITICAL, new Object[] { "Query=" + sql + ", Args=" + Arrays.toString(args) + ", messages=" + e.getMessage() });
/* 124 */       throw new InternalDBException(e);
/*     */     } 
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
/*     */   protected int doUpdate(String sql, Object[] args, int[] argTypes) {
/*     */     try {
/* 141 */       Assert.isTrue((args.length == argTypes.length), "The args{Object[]} and argTypes{int[]} arrays must have same length. query = " + sql);
/* 142 */       return this.ejt.update(sql, args, argTypes);
/* 143 */     } catch (InternalDBException e) {
/* 144 */       throw e;
/* 145 */     } catch (Exception e) {
/* 146 */       AuditAlarmTypes.SYSTEM.raiseAlarm(null, 261, AlarmLevels.CRITICAL, new Object[] { "Query=" + sql + ", Args=" + Arrays.toString(args) + ", messages" + e.getMessage() });
/* 147 */       throw new InternalDBException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doSaveAsBatch(String sql, BatchPreparedStatementSetter pss) {
/*     */     try {
/* 159 */       this.ejt.batchUpdate(sql, pss);
/* 160 */     } catch (InternalDBException e) {
/* 161 */       throw e;
/* 162 */     } catch (Exception e) {
/* 163 */       AuditAlarmTypes.SYSTEM.raiseAlarm(null, 261, AlarmLevels.CRITICAL, new Object[] { "Failed to batch update. Query=" + sql + ", messages" + e.getMessage() });
/* 164 */       throw new InternalDBException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int doTruncate(String sql) {
/*     */     try {
/* 175 */       return this.ejt.update(sql);
/* 176 */     } catch (InternalDBException e) {
/* 177 */       throw e;
/* 178 */     } catch (Exception e) {
/* 179 */       AuditAlarmTypes.SYSTEM.raiseAlarm(null, 261, AlarmLevels.CRITICAL, new Object[] { "Failed to batch update. Query=" + sql + ", messages" + e.getMessage() });
/* 180 */       throw new InternalDBException(e);
/*     */     } 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected <T> T queryForObject(String sql, Object[] args, RowMapper<T> rowMapper, T zeroResult) {
/*     */     try {
/* 203 */       T result = (T)this.ejt.queryForObject(sql, args, rowMapper);
/*     */       
/* 205 */       if (result == null) {
/* 206 */         return zeroResult;
/*     */       }
/*     */       
/* 209 */       return result;
/* 210 */     } catch (InternalDBException e) {
/* 211 */       throw e;
/* 212 */     } catch (EmptyResultDataAccessException e) {
/* 213 */       return zeroResult;
/* 214 */     } catch (IncorrectResultSizeDataAccessException e) {
/* 215 */       AuditAlarmTypes.SYSTEM.raiseAlarm(null, 261, AlarmLevels.CRITICAL, new Object[] { "The query should have single result. (result size > 1) SQL = " + sql + ", Args = " + 
/* 216 */             Arrays.toString(args) });
/* 217 */       throw new InternalDBException("The query should have single result. (result size > 1) SQL = " + sql + ", Args = " + Arrays.toString(args), e);
/* 218 */     } catch (TypeMismatchDataAccessException e) {
/* 219 */       AuditAlarmTypes.SYSTEM.raiseAlarm(null, 261, AlarmLevels.CRITICAL, new Object[] { "The query result is invalid type. SQL = " + sql + ", Exception=" + e
/* 220 */             .getMessage() });
/* 221 */       throw new InternalDBException("The query result is invalid type. SQL = " + sql, e);
/* 222 */     } catch (Exception e) {
/* 223 */       AuditAlarmTypes.SYSTEM.raiseAlarm(null, 261, AlarmLevels.CRITICAL, new Object[] { "The query result is invalid. SQL = " + sql + ", Exception=" + e
/* 224 */             .getMessage() });
/* 225 */       throw new InternalDBException("The query result is invalid. SQL = " + sql, e);
/*     */     } 
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
/*     */   protected <T> T queryForObject(String sql, Object[] args, RowMapper<T> rowMapper) {
/* 242 */     return queryForObject(sql, args, rowMapper, null);
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
/*     */ 
/*     */ 
/*     */   
/*     */   protected <T> T queryForObject(String sql, Object[] args, Class<T> requiredType, T zeroResult) {
/*     */     try {
/* 262 */       T result = null;
/* 263 */       if (args == null || args.length < 1) {
/* 264 */         result = (T)this.ejt.queryForObject(sql, requiredType);
/*     */       } else {
/* 266 */         result = (T)this.ejt.queryForObject(sql, args, requiredType);
/*     */       } 
/*     */       
/* 269 */       if (result == null) {
/* 270 */         return zeroResult;
/*     */       }
/*     */       
/* 273 */       return result;
/* 274 */     } catch (EmptyResultDataAccessException e) {
/* 275 */       return zeroResult;
/* 276 */     } catch (InternalDBException e) {
/* 277 */       throw e;
/* 278 */     } catch (IncorrectResultSizeDataAccessException e) {
/* 279 */       AuditAlarmTypes.SYSTEM.raiseAlarm(null, 261, AlarmLevels.CRITICAL, new Object[] { "The query should have single result. (result size > 1) SQL = " + sql + ", Args = " + 
/* 280 */             Arrays.toString(args) });
/* 281 */       throw new InternalDBException("The query should have single result. (result size > 1) SQL = " + sql + ", Args = " + Arrays.toString(args), e);
/* 282 */     } catch (TypeMismatchDataAccessException e) {
/* 283 */       AuditAlarmTypes.SYSTEM.raiseAlarm(null, 261, AlarmLevels.CRITICAL, new Object[] { "The query result is invalid type. SQL = " + sql + ", Exception=" + e
/* 284 */             .getMessage() });
/* 285 */       throw new InternalDBException("The query result is invalid type. SQL = " + sql, e);
/* 286 */     } catch (Exception e) {
/* 287 */       AuditAlarmTypes.SYSTEM.raiseAlarm(null, 261, AlarmLevels.CRITICAL, new Object[] { "The query result is invalid. SQL = " + sql + ", Exception=" + e
/* 288 */             .getMessage() });
/* 289 */       throw new InternalDBException("The query result is invalid. SQL = " + sql, e);
/*     */     } 
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
/*     */   protected <T> T queryForObject(String sql, Object[] args, Class<T> requiredType) {
/* 305 */     return queryForObject(sql, args, requiredType, null);
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
/*     */   protected String queryForString(String sql, Object[] args, String zeroResult) {
/* 320 */     return queryForObject(sql, args, String.class, zeroResult);
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
/*     */   protected int queryForInt(String sql, Object[] args, int zeroResult) {
/* 335 */     return ((Integer)queryForObject(sql, args, Integer.class, Integer.valueOf(zeroResult))).intValue();
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
/*     */   protected long queryForLong(String sql, Object[] args, long zeroResult) {
/* 350 */     return ((Long)queryForObject(sql, args, Long.class, Long.valueOf(zeroResult))).longValue();
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
/*     */   
/*     */   protected <T> List<T> queryForList(String sql, Object[] args, RowMapper<T> rowMapper) {
/*     */     try {
/* 368 */       return this.ejt.queryForList(sql, args, rowMapper);
/* 369 */     } catch (InternalDBException e) {
/* 370 */       throw e;
/* 371 */     } catch (TypeMismatchDataAccessException e) {
/* 372 */       AuditAlarmTypes.SYSTEM.raiseAlarm(null, 261, AlarmLevels.CRITICAL, new Object[] { "The query result is invalid type. SQL = " + sql + ", Exception=" + e
/* 373 */             .getMessage() });
/* 374 */       throw new InternalDBException("The query result is invalid type. SQL = " + sql, e);
/* 375 */     } catch (Exception e) {
/* 376 */       AuditAlarmTypes.SYSTEM.raiseAlarm(null, 261, AlarmLevels.CRITICAL, new Object[] { "The query result is invalid. SQL = " + sql + ", Exception=" + e
/* 377 */             .getMessage() });
/* 378 */       throw new InternalDBException("The query result is invalid. SQL = " + sql, e);
/*     */     } 
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
/*     */ 
/*     */   
/*     */   protected <T> List<T> queryForList(String sql, Object[] args, Class<T> clz) {
/*     */     try {
/* 398 */       return this.ejt.queryForList(sql, args, clz);
/* 399 */     } catch (InternalDBException e) {
/* 400 */       throw e;
/* 401 */     } catch (TypeMismatchDataAccessException e) {
/* 402 */       AuditAlarmTypes.SYSTEM.raiseAlarm(null, 261, AlarmLevels.CRITICAL, new Object[] { "The query result is invalid type. SQL = " + sql + ", Exception=" + e
/* 403 */             .getMessage() });
/* 404 */       throw new InternalDBException("The query result is invalid type. SQL = " + sql, e);
/* 405 */     } catch (Exception e) {
/* 406 */       AuditAlarmTypes.SYSTEM.raiseAlarm(null, 261, AlarmLevels.CRITICAL, new Object[] { "The query result is invalid. SQL = " + sql + ", Exception=" + e
/* 407 */             .getMessage() });
/* 408 */       throw new InternalDBException("The query result is invalid. SQL = " + sql, e);
/*     */     } 
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
/*     */   protected <T> List<T> queryForList(String sql, RowMapper<T> rowMapper) {
/*     */     try {
/* 425 */       return this.ejt.queryForList(sql, rowMapper);
/* 426 */     } catch (InternalDBException e) {
/* 427 */       throw e;
/* 428 */     } catch (TypeMismatchDataAccessException e) {
/* 429 */       AuditAlarmTypes.SYSTEM.raiseAlarm(null, 261, AlarmLevels.CRITICAL, new Object[] { "The query result is invalid type. SQL = " + sql + ", Exception=" + e
/* 430 */             .getMessage() });
/* 431 */       throw new InternalDBException("The query result is invalid type. SQL = " + sql, e);
/* 432 */     } catch (Exception e) {
/* 433 */       AuditAlarmTypes.SYSTEM.raiseAlarm(null, 261, AlarmLevels.CRITICAL, new Object[] { "The query result is invalid. SQL = " + sql + ", Exception=" + e
/* 434 */             .getMessage() });
/* 435 */       throw new InternalDBException("The query result is invalid. SQL = " + sql, e);
/*     */     } 
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected <T> List<T> queryForListWithLimit(String sql, Object[] args, RowMapper<T> rowMapper, int limit, int offset) {
/* 456 */     String cvtSql = this.type.convertLimitQuery(sql, limit, offset);
/* 457 */     return queryForList(cvtSql, args, rowMapper);
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
/*     */ 
/*     */   
/*     */   protected <T> List<T> queryForListWithLimit(String sql, RowMapper<T> rowMapper, int limit, int offset) {
/* 475 */     String cvtSql = this.type.convertLimitQuery(sql, limit, offset);
/* 476 */     return queryForList(cvtSql, rowMapper);
/*     */   }
/*     */ 
/*     */   
/*     */   protected int returnBlobType() {
/* 481 */     return this.type.getBLOBType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String boolToChar(boolean b) {
/* 488 */     return b ? "Y" : "N";
/*     */   }
/*     */   
/*     */   public static boolean charToBool(String s) {
/* 492 */     return "Y".equals(s);
/*     */   }
/*     */   
/*     */   protected static String getFormatDate(long ts) {
/* 496 */     return Commons.getFormatDate(ts);
/*     */   }
/*     */   
/*     */   protected static String getFormatTime(long ts) {
/* 500 */     return Commons.getFormatTime(ts);
/*     */   }
/*     */   
/*     */   protected Object serialize(Object obj) {
/* 504 */     return this.type.seializeBlob(obj);
/*     */   }
/*     */   
/*     */   protected <T> T deserialize(ResultSet rs, int colNum) {
/* 508 */     return (T)this.type.deserializeBlob(rs, colNum);
/*     */   }
/*     */   
/*     */   protected static DatabaseAccess.DatabaseType getDBType() {
/* 512 */     if (Commons.ctx.getDatabaseAccess() != null) {
/* 513 */       return Commons.ctx.getDatabaseAccess().getType();
/*     */     }
/* 515 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Object[] addQueryParams(Object[] original, Object newParam) {
/* 522 */     if (newParam == null) {
/* 523 */       return original;
/*     */     }
/*     */     int orgLen;
/* 526 */     if (original == null || (orgLen = original.length) < 1) {
/* 527 */       return new Object[] { newParam };
/*     */     }
/*     */     
/* 530 */     Object[] newParamArr = new Object[orgLen + 1];
/* 531 */     System.arraycopy(original, 0, newParamArr, 0, orgLen);
/* 532 */     newParamArr[orgLen] = newParam;
/*     */     
/* 534 */     return newParamArr;
/*     */   }
/*     */ 
/*     */   
/*     */   protected static Object[] createParams(int[] params) {
/* 539 */     Object[] dest = new Object[params.length];
/*     */     
/* 541 */     for (int i = 0, len = params.length; i < len; i++) {
/* 542 */       dest[i] = Integer.valueOf(params[i]);
/*     */     }
/*     */     
/* 545 */     return dest;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected DataSourceTransactionManager getTransactionManager() {
/* 553 */     if (this.tm == null) {
/* 554 */       this.tm = new DataSourceTransactionManager(this.dataSource);
/*     */     }
/*     */     
/* 557 */     return this.tm;
/*     */   }
/*     */   
/*     */   protected TransactionTemplate getTransactionTemplate() {
/* 561 */     return new TransactionTemplate((PlatformTransactionManager)getTransactionManager());
/*     */   }
/*     */ 
/*     */   
/*     */   public void doInTransaction(TransactionCallbackWithoutResult tranCallback) {
/*     */     try {
/* 567 */       getTransactionTemplate().execute((TransactionCallback)tranCallback);
/* 568 */     } catch (InternalDBException e) {
/* 569 */       throw e;
/* 570 */     } catch (TypeMismatchDataAccessException e) {
/* 571 */       AuditAlarmTypes.SYSTEM.raiseAlarm(null, 261, AlarmLevels.CRITICAL, new Object[] { "The query is invalid type. Exception=" + e
/* 572 */             .getMessage() });
/* 573 */       throw new InternalDBException("The query result is invalid type. cause=" + e.getMessage(), e);
/* 574 */     } catch (Exception e) {
/* 575 */       AuditAlarmTypes.SYSTEM.raiseAlarm(null, 261, AlarmLevels.CRITICAL, new Object[] { "The query is invalid. Exception=" + e
/* 576 */             .getMessage() });
/* 577 */       throw new InternalDBException("The query result is invalid. cause=" + e.getMessage(), e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\db\dao\BaseDao.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */