/*     */ package WEB-INF.classes.com.dreammirae.mmth.db.dao.queries;
/*     */ 
/*     */ import com.dreammirae.mmth.authentication.AuthMethodTypes;
/*     */ import com.dreammirae.mmth.config.Commons;
/*     */ import com.dreammirae.mmth.runtime.service.type.LogOperationTypes;
/*     */ import com.dreammirae.mmth.util.StringUtils;
/*     */ import com.dreammirae.mmth.vo.ServiceLogVO;
/*     */ import com.dreammirae.mmth.vo.UserVO;
/*     */ import com.dreammirae.mmth.vo.types.DeviceTypes;
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
/*     */ public final class DMLServiceLog
/*     */ {
/*     */   public static final String INSERT = "INSERT INTO MMTH_ServiceLogs (authType, username, opType, reqType, actionType, returnCode, deviceId, deviceType, pkgUnique, aaid, tokenId, description, tsReg, regDate, regHour) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
/*     */   public static final String INSERT_WITH_DEVICE_APP = "INSERT INTO MMTH_ServiceLogs (authType, username, opType, reqType, actionType, returnCode, deviceId, deviceType, pkgUnique, aaid, tokenId, description, tsReg, regDate, regHour) SELECT ?, ?, ?, ?, ?, ?, ud.deviceId, ud.deviceType, aa.pkgUnique, ?, ?, ?, ?, ?, ? FROM MMTH_DeviceAppAgents da LEFT JOIN MMTH_UserDevices ud ON da.userDeviceId = ud.id LEFT JOIN MMTH_AppAgents aa on da.agentId = aa.id WHERE da.id = ?";
/*     */   public static final String DELETE_BY_PK = "DELETE FROM MMTH_ServiceLogs WHERE id = ?";
/*     */   public static final String DELETE_BY_COND = "DELETE FROM MMTH_ServiceLogs WHERE tsReg < ?";
/*     */   private static final String SELECT_PREFIX = "SELECT id, authType, username, opType, reqType, actionType, returnCode, deviceId, null AS model, deviceType, pkgUnique, aaid, tokenId, description, tsReg, regDate, regHour FROM MMTH_ServiceLogs ";
/*     */   private static final String SELECT_CONTENTS_SUFFIX = " ORDER BY id desc";
/*     */   private static final String SELECT_CONTENT_COUNT_PREFIX = "SELECT COUNT(id) FROM MMTH_ServiceLogs ";
/*     */   private static final String WHERE = " WHERE ";
/*     */   private static final String AND = " AND ";
/*     */   public static final String SELECT_ONE_PK = "SELECT a.id, authType, username, opType, reqType, actionType, returnCode, a.deviceId, b.model, a.deviceType, pkgUnique, aaid, tokenId, description, a.tsReg, regDate, regHour FROM (SELECT id, authType, username, opType, reqType, actionType, returnCode, deviceId, null AS model, deviceType, pkgUnique, aaid, tokenId, description, tsReg, regDate, regHour FROM MMTH_ServiceLogs  WHERE id = ?) a LEFT OUTER JOIN MMTH_UserDevices b ON b.deviceId = a.deviceId AND a.username = (SELECT username FROM MMTH_users WHERE id = b.userId)";
/*     */   public static final String SELECT_LATEST_AUTH_LOG = "SELECT id, authType, username, opType, reqType, actionType, returnCode, deviceId, null AS model, deviceType, pkgUnique, aaid, tokenId, description, tsReg, regDate, regHour FROM MMTH_ServiceLogs WHERE username=? AND (opType=? OR opType=?) ORDER BY id desc";
/*     */   public static final String SELECT_LOG_FOR_OPERATION = "SELECT id, authType, username, opType, reqType, actionType, returnCode, deviceId, null AS model, deviceType, pkgUnique, aaid, tokenId, description, tsReg, regDate, regHour FROM MMTH_ServiceLogs WHERE username=? AND authType=? AND opType not in (?, ?, ?, ?) ORDER BY id desc";
/*     */   public static final String NOT_REQ_TYPE_LOG = "OR (reqType = 1 AND opType = 12289) ";
/*     */   
/*     */   public static int[] getInsertTypes() {
/*  90 */     return new int[] { 4, 12, 4, 4, 4, 1, 12, 1, 12, 12, 12, 12, -5, 1, 1 };
/*     */   }
/*     */ 
/*     */   
/*     */   public static Object[] toInsertParams(ServiceLogVO vo) {
/*  95 */     String desc = StringUtils.trimUTF8(vo.getDescription(), 1024, Commons.ctx.getDatabaseAccess().useSafeUtf8());
/*  96 */     if (vo.getDeviceType() == null)
/*     */     {
/*  98 */       vo.setDeviceType(DeviceTypes.ANANYMOUS);
/*     */     }
/*     */     
/* 101 */     return new Object[] { Integer.valueOf(vo.getAuthType().getCode()), vo.getUsername(), Integer.valueOf(vo.getOpType().getCode()), Integer.valueOf(vo.getReqType().getCode()), Integer.valueOf(vo.getActionType().getCode()), vo.getReturnCode().getCode(), vo.getDeviceId(), vo
/* 102 */         .getDeviceType().getCode(), vo.getPkgUnique(), vo.getAaid(), vo.getTokenId(), desc, vo.getTsReg(), vo.getRegDate(), vo.getRegHour() };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object[] toDeletePKParam(long id) {
/* 113 */     return new Object[] { Long.valueOf(id) };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object[] toDeleteParams(ServiceLogVO vo) {
/* 119 */     return new Object[] { Long.valueOf(vo.getTsReg().longValue()) };
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
/*     */ 
/*     */   
/*     */   public static Object[] toSelectLatestAuthLog(UserVO user) {
/* 142 */     return new Object[] { user.getUsername(), Integer.valueOf(LogOperationTypes.AUTH.getCode()), Integer.valueOf(LogOperationTypes.AUTH_PIN.getCode()) };
/*     */   }
/*     */ 
/*     */   
/*     */   public static Object[] toSelectLogForOperation(UserVO user, AuthMethodTypes authType) {
/* 147 */     return new Object[] { user
/* 148 */         .getUsername(), Integer.valueOf(authType.getCode()), Integer.valueOf(LogOperationTypes.AUTH_PIN.getCode()), Integer.valueOf(LogOperationTypes.AUTH_BIOTP.getCode()), Integer.valueOf(LogOperationTypes.AUTH_FIDO.getCode()), Integer.valueOf(LogOperationTypes.AUTH_SAOTP.getCode()) };
/*     */   }
/*     */ 
/*     */   
/*     */   public static Object[] toSelectPKParam(long id) {
/* 153 */     return new Object[] { Long.valueOf(id) };
/*     */   }
/*     */ 
/*     */   
/*     */   public static String selectContents(ServiceLogVO vo, Long tsFrom, Long tsTo) {
/* 158 */     StringBuilder sb = new StringBuilder(255);
/*     */ 
/*     */     
/* 161 */     sb.append("SELECT id, authType, username, opType, reqType, actionType, returnCode, deviceId, null AS model, deviceType, pkgUnique, aaid, tokenId, description, tsReg, regDate, regHour FROM MMTH_ServiceLogs ");
/*     */     
/* 163 */     appendCondition(sb, vo, tsFrom, tsTo);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 170 */     sb.append(" ORDER BY id desc");
/*     */     
/* 172 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public static String selectContentCount(ServiceLogVO vo, Long tsFrom, Long tsTo) {
/* 177 */     StringBuilder sb = new StringBuilder(255);
/*     */ 
/*     */     
/* 180 */     sb.append("SELECT COUNT(id) FROM MMTH_ServiceLogs ");
/*     */     
/* 182 */     appendCondition(sb, vo, tsFrom, tsTo);
/*     */     
/* 184 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   private static void appendCondition(StringBuilder sb, ServiceLogVO vo, Long tsFrom, Long tsTo) {
/* 189 */     boolean hasParam = false;
/*     */     
/* 191 */     if (tsFrom != null) {
/* 192 */       sb.append(" WHERE ").append("tsReg >= ? ");
/* 193 */       hasParam = true;
/*     */     } 
/*     */     
/* 196 */     if (tsTo != null) {
/* 197 */       sb.append(hasParam ? " AND " : " WHERE ").append("tsReg <= ?");
/* 198 */       hasParam = true;
/*     */     } 
/*     */     
/* 201 */     if (vo == null) {
/*     */       return;
/*     */     }
/*     */     
/* 205 */     if (vo.getOpType() != null) {
/* 206 */       sb.append(hasParam ? " AND " : " WHERE ").append("opType = ?");
/* 207 */       hasParam = true;
/*     */     } 
/*     */     
/* 210 */     if (vo.getReqType() != null) {
/* 211 */       sb.append(hasParam ? " AND " : " WHERE ").append("reqType = ?");
/* 212 */       hasParam = true;
/*     */     } 
/*     */     
/* 215 */     if (vo.getActionType() != null) {
/* 216 */       sb.append(hasParam ? " AND " : " WHERE ").append("actionType = ?");
/* 217 */       hasParam = true;
/*     */     } 
/*     */     
/* 220 */     if (!StringUtils.isEmpty(vo.getUsername())) {
/* 221 */       sb.append(hasParam ? " AND " : " WHERE ").append("username like ?");
/* 222 */       hasParam = true;
/*     */     } 
/*     */     
/* 225 */     if (vo.getAuthType() != null) {
/* 226 */       sb.append(hasParam ? " AND " : " WHERE ").append("authType = ?");
/* 227 */       hasParam = true;
/*     */     } 
/*     */     
/* 230 */     if (vo.getOptions() != null && vo.getOptions().size() != 0) {
/* 231 */       String temp = "opType in (?";
/* 232 */       for (int i = 1; i < vo.getOptions().size(); i++) {
/* 233 */         temp = temp + ",?";
/*     */       }
/* 235 */       temp = temp + ")";
/* 236 */       sb.append(hasParam ? " AND " : " WHERE ").append("((" + temp + " AND reqType != 1) OR (reqType != 2 AND opType = 12289))");
/*     */       
/* 238 */       hasParam = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static Object[] toSelectParams(ServiceLogVO vo, Long tsFrom, Long tsTo) {
/* 244 */     List<Object> params = new ArrayList(6);
/*     */     
/* 246 */     if (tsFrom != null) {
/* 247 */       params.add(Long.valueOf(tsFrom.longValue()));
/*     */     }
/*     */     
/* 250 */     if (tsTo != null) {
/* 251 */       params.add(Long.valueOf(tsTo.longValue()));
/*     */     }
/*     */     
/* 254 */     if (vo == null) {
/* 255 */       return params.toArray();
/*     */     }
/*     */     
/* 258 */     if (vo.getOpType() != null) {
/* 259 */       params.add(Integer.valueOf(vo.getOpType().getCode()));
/*     */     }
/*     */     
/* 262 */     if (vo.getReqType() != null) {
/* 263 */       params.add(Integer.valueOf(vo.getReqType().getCode()));
/*     */     }
/*     */     
/* 266 */     if (vo.getActionType() != null) {
/* 267 */       params.add(Integer.valueOf(vo.getActionType().getCode()));
/*     */     }
/*     */     
/* 270 */     if (!StringUtils.isEmpty(vo.getUsername())) {
/* 271 */       params.add("%" + vo.getUsername() + "%");
/*     */     }
/*     */     
/* 274 */     if (vo.getAuthType() != null) {
/* 275 */       params.add(Integer.valueOf(vo.getAuthType().getCode()));
/*     */     }
/*     */     
/* 278 */     if (vo.getOptions() != null && vo.getOptions().size() != 0) {
/* 279 */       for (LogOperationTypes type : vo.getOptions()) {
/* 280 */         params.add(Integer.valueOf(type.getCode()));
/*     */       }
/*     */     }
/*     */     
/* 284 */     return params.toArray();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static RowMapper<ServiceLogVO> getRowMapper() {
/* 290 */     return (RowMapper<ServiceLogVO>)new ServiceLogRowMapper(null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BatchPreparedStatementSetter getBatchPreparedStatementSetter(List<ServiceLogVO> serviceLogs) {
/* 298 */     return (BatchPreparedStatementSetter)new ServiceLogBatchPreparedStatementSetter(serviceLogs, null);
/*     */   }
/*     */   
/*     */   public static BatchPreparedStatementSetter getBatchPreparedStatementSetterWithDeviceApp(List<ServiceLogVO> serviceLogs) {
/* 302 */     return (BatchPreparedStatementSetter)new ServiceLogBatchWithDeviceAppBatchPreparedStatementSetter(serviceLogs, null);
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\db\dao\queries\DMLServiceLog.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */