/*     */ package WEB-INF.classes.com.dreammirae.mmth.db.dao.queries;
/*     */ 
/*     */ import com.dreammirae.mmth.util.StringUtils;
/*     */ import com.dreammirae.mmth.vo.UserVO;
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
/*     */ public class DMLUserView
/*     */ {
/*     */   private static final String SELECT_PREFIX = "SELECT data, id, username, productType, multiLoginYN, disabled, status, tsReg, tsUpdated, annotationData, displayName, extUnique, customerCode, countryCode FROM MMTH_UserView ";
/*     */   private static final String SELECT_CONTENTS_SUFFIX = " ORDER BY username";
/*     */   private static final String SELECT_CONTENT_COUNT_PREFIX = "SELECT COUNT(*) FROM MMTH_Users ";
/*     */   public static final String SELECT_ONE_PK = "SELECT data, id, username, productType, multiLoginYN, disabled, status, tsReg, tsUpdated, annotationData, displayName, extUnique, customerCode, countryCode FROM MMTH_UserView WHERE id=?";
/*     */   public static final String SELECT_ONE_UNIQUE = "SELECT data, id, username, productType, multiLoginYN, disabled, status, tsReg, tsUpdated, annotationData, displayName, extUnique, customerCode, countryCode FROM MMTH_UserView WHERE username=?";
/*     */   private static final String WHERE = " WHERE ";
/*     */   private static final String AND = " AND ";
/*     */   
/*     */   public static Object[] toSelectPKParam(int id) {
/*  63 */     return new Object[] { Integer.valueOf(id) };
/*     */   }
/*     */   
/*     */   public static Object[] toSelectParams(UserVO vo) {
/*  67 */     return new Object[] { vo.getUsername() };
/*     */   }
/*     */   
/*     */   public static Object[] toSelectParams(String username) {
/*  71 */     return new Object[] { username };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String selectContents(UserVO vo, Long tsFrom, Long tsTo) {
/*  77 */     StringBuilder sb = new StringBuilder(255);
/*     */ 
/*     */     
/*  80 */     sb.append("SELECT data, id, username, productType, multiLoginYN, disabled, status, tsReg, tsUpdated, annotationData, displayName, extUnique, customerCode, countryCode FROM MMTH_UserView ");
/*     */     
/*  82 */     appendCondition(sb, vo, tsFrom, tsTo);
/*     */     
/*  84 */     sb.append(" ORDER BY username");
/*     */     
/*  86 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public static String selectContentConunt(UserVO vo, Long tsFrom, Long tsTo) {
/*  91 */     StringBuilder sb = new StringBuilder(255);
/*     */ 
/*     */     
/*  94 */     sb.append("SELECT COUNT(*) FROM MMTH_Users ");
/*     */     
/*  96 */     appendCondition(sb, vo, tsFrom, tsTo);
/*     */     
/*  98 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   private static void appendCondition(StringBuilder sb, UserVO vo, Long tsFrom, Long tsTo) {
/* 103 */     if (vo == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 108 */     boolean hasParam = false;
/* 109 */     if (vo.getDisabled() != null) {
/* 110 */       sb.append(hasParam ? " AND " : " WHERE ").append("disabled = ?");
/* 111 */       hasParam = true;
/*     */     } 
/*     */     
/* 114 */     if (vo.getStatus() != null) {
/* 115 */       sb.append(hasParam ? " AND " : " WHERE ").append("status = ?");
/* 116 */       hasParam = true;
/*     */     } 
/*     */     
/* 119 */     if (!StringUtils.isEmpty(vo.getUsername())) {
/* 120 */       sb.append(hasParam ? " AND " : " WHERE ").append("username like ?");
/* 121 */       hasParam = true;
/*     */     } 
/*     */     
/* 124 */     if (vo.getAnnotation() == null) {
/*     */       return;
/*     */     }
/*     */     
/* 128 */     if (!StringUtils.isEmpty(vo.getAnnotation().getDisplayName())) {
/* 129 */       sb.append(hasParam ? " AND " : " WHERE ").append("displayName like ?");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static Object[] toSelectParams(UserVO vo, Long tsFrom, Long tsTo) {
/* 135 */     if (vo == null) {
/* 136 */       return null;
/*     */     }
/*     */     
/* 139 */     List<Object> params = new ArrayList(3);
/* 140 */     if (vo.getDisabled() != null) {
/* 141 */       params.add(vo.getDisabled().getCode());
/*     */     }
/*     */     
/* 144 */     if (vo.getStatus() != null) {
/* 145 */       params.add(vo.getStatus().getCode());
/*     */     }
/*     */     
/* 148 */     if (!StringUtils.isEmpty(vo.getUsername())) {
/* 149 */       params.add("%" + vo.getUsername() + "%");
/*     */     }
/*     */     
/* 152 */     if (vo.getAnnotation() != null && 
/* 153 */       !StringUtils.isEmpty(vo.getAnnotation().getDisplayName())) {
/* 154 */       params.add("%" + vo.getAnnotation().getDisplayName() + "%");
/*     */     }
/*     */ 
/*     */     
/* 158 */     return params.toArray();
/*     */   }
/*     */   
/*     */   public static RowMapper<UserVO> getRowMapper() {
/* 162 */     return (RowMapper<UserVO>)new UserRowMapper(null);
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\db\dao\queries\DMLUserView.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */