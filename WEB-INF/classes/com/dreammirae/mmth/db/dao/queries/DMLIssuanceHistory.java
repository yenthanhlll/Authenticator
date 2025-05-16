/*     */ package WEB-INF.classes.com.dreammirae.mmth.db.dao.queries;
/*     */ 
/*     */ import com.dreammirae.mmth.config.Commons;
/*     */ import com.dreammirae.mmth.util.StringUtils;
/*     */ import com.dreammirae.mmth.vo.IssuanceHistoryVO;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
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
/*     */ public final class DMLIssuanceHistory
/*     */ {
/*     */   public static final String INSERT = "INSERT INTO MMTH_IssuanceHistory (authType, username, deviceId, deviceType, pkgUnique, issueType, aaid, tokenId, tsIssue, issueMonth, issueDate, issueTime) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
/*     */   private static final String SELECT_PREFIX = "SELECT DISTINCT(a.id), authType, username, b.deviceId, b.deviceType, pkgUnique, issueType, aaid, tokenId, tsIssue, issueMonth, issueDate, issueTime, b.model FROM MMTH_IssuanceHistory a LEFT OUTER JOIN MMTH_UserDevices b ON a.deviceId = b.deviceId ";
/*     */   private static final String SELECT_CONTENTS_SUFFIX = " ORDER BY id desc";
/*     */   private static final String SELECT_CONTENT_COUNT_PREFIX = "SELECT COUNT(*) FROM MMTH_IssuanceHistory ";
/*     */   private static final String WHERE = " WHERE ";
/*     */   private static final String AND = " AND ";
/*     */   public static final String SELECT_ONE_PK = "SELECT DISTINCT(a.id), authType, username, b.deviceId, b.deviceType, pkgUnique, issueType, aaid, tokenId, tsIssue, issueMonth, issueDate, issueTime, b.model FROM MMTH_IssuanceHistory a LEFT OUTER JOIN MMTH_UserDevices b ON a.deviceId = b.deviceId WHERE id=? ";
/*     */   
/*     */   public static int[] getInsertTypes() {
/*  71 */     return new int[] { 4, 12, 12, 1, 12, 1, 12, 12, -5, 1, 1, 1 };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object[] toInsertParams(IssuanceHistoryVO vo) {
/*  79 */     vo.setTsIssue(System.currentTimeMillis());
/*  80 */     vo.setIssueMonth(Commons.MONTH_FORMATTER.print(vo.getTsIssue()));
/*  81 */     vo.setIssueDate(Commons.DATE_FORMATTER.print(vo.getTsIssue()));
/*  82 */     vo.setIssueTime(Commons.TIME_FORMATTER.print(vo.getTsIssue()));
/*  83 */     return new Object[] { 
/*  84 */         Integer.valueOf(vo.getAuthType().getCode()), vo.getUsername(), vo.getDeviceId(), vo.getDeviceType().getCode(), vo
/*  85 */         .getPkgUnique(), vo.getIssueType().getCode(), vo.getAaid(), vo.getTokenId(), 
/*  86 */         Long.valueOf(vo.getTsIssue()), vo.getIssueMonth(), vo.getIssueDate(), vo.getIssueTime() };
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
/*     */   public static Object[] toSelectPKParam(long id) {
/* 105 */     return new Object[] { Long.valueOf(id) };
/*     */   }
/*     */ 
/*     */   
/*     */   public static String selectContents(IssuanceHistoryVO vo, Long tsFrom, Long tsTo) {
/* 110 */     StringBuilder sb = new StringBuilder(255);
/*     */ 
/*     */     
/* 113 */     sb.append("SELECT DISTINCT(a.id), authType, username, b.deviceId, b.deviceType, pkgUnique, issueType, aaid, tokenId, tsIssue, issueMonth, issueDate, issueTime, b.model FROM MMTH_IssuanceHistory a LEFT OUTER JOIN MMTH_UserDevices b ON a.deviceId = b.deviceId ");
/*     */     
/* 115 */     appendCondition(sb, vo, tsFrom, tsTo);
/*     */     
/* 117 */     sb.append(" ORDER BY id desc");
/*     */     
/* 119 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public static String selectContentConunt(IssuanceHistoryVO vo, Long tsFrom, Long tsTo) {
/* 124 */     StringBuilder sb = new StringBuilder(255);
/*     */ 
/*     */     
/* 127 */     sb.append("SELECT COUNT(*) FROM MMTH_IssuanceHistory ");
/*     */     
/* 129 */     appendCondition(sb, vo, tsFrom, tsTo);
/*     */     
/* 131 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   private static void appendCondition(StringBuilder sb, IssuanceHistoryVO vo, Long tsFrom, Long tsTo) {
/* 136 */     boolean hasParam = false;
/*     */     
/* 138 */     if (tsFrom != null) {
/* 139 */       sb.append(" WHERE ").append("tsIssue >= ? ");
/* 140 */       hasParam = true;
/*     */     } 
/*     */     
/* 143 */     if (tsTo != null) {
/* 144 */       sb.append(hasParam ? " AND " : " WHERE ").append("tsIssue <= ?");
/* 145 */       hasParam = true;
/*     */     } 
/*     */     
/* 148 */     if (vo == null) {
/*     */       return;
/*     */     }
/*     */     
/* 152 */     if (vo.getIssueType() != null) {
/* 153 */       sb.append(hasParam ? " AND " : " WHERE ").append("issueType = ?");
/* 154 */       hasParam = true;
/*     */     } 
/*     */     
/* 157 */     if (vo.getAuthType() != null) {
/* 158 */       sb.append(hasParam ? " AND " : " WHERE ").append("authType = ?");
/* 159 */       hasParam = true;
/*     */     } 
/*     */     
/* 162 */     if (!StringUtils.isEmpty(vo.getUsername())) {
/* 163 */       sb.append(hasParam ? " AND " : " WHERE ").append("username like ?");
/* 164 */       hasParam = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object[] toSelectParams(IssuanceHistoryVO vo, Long tsFrom, Long tsTo) {
/* 171 */     List<Object> params = new ArrayList(6);
/*     */     
/* 173 */     if (tsFrom != null) {
/* 174 */       params.add(Long.valueOf(tsFrom.longValue()));
/*     */     }
/*     */     
/* 177 */     if (tsTo != null) {
/* 178 */       params.add(Long.valueOf(tsTo.longValue()));
/*     */     }
/*     */     
/* 181 */     if (vo == null) {
/* 182 */       return params.toArray();
/*     */     }
/*     */ 
/*     */     
/* 186 */     if (vo.getIssueType() != null) {
/* 187 */       params.add(vo.getIssueType().getCode());
/*     */     }
/*     */     
/* 190 */     if (vo.getAuthType() != null) {
/* 191 */       params.add(Integer.valueOf(vo.getAuthType().getCode()));
/*     */     }
/*     */     
/* 194 */     if (!StringUtils.isEmpty(vo.getUsername())) {
/* 195 */       params.add("%" + vo.getUsername() + "%");
/*     */     }
/*     */     
/* 198 */     return params.toArray();
/*     */   }
/*     */   
/*     */   public static RowMapper<IssuanceHistoryVO> getRowMapper() {
/* 202 */     return (RowMapper<IssuanceHistoryVO>)new IssuanceHistoryRowMapper(null);
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\db\dao\queries\DMLIssuanceHistory.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */