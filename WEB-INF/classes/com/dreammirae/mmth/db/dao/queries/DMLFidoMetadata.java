/*     */ package WEB-INF.classes.com.dreammirae.mmth.db.dao.queries;
/*     */ 
/*     */ import com.dreammirae.mmth.config.Commons;
/*     */ import com.dreammirae.mmth.misc.Base64Utils;
/*     */ import com.dreammirae.mmth.util.StringUtils;
/*     */ import com.dreammirae.mmth.vo.FidoMetadataVO;
/*     */ import com.dreammirae.mmth.vo.types.DisabledStatus;
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
/*     */ public final class DMLFidoMetadata
/*     */ {
/*     */   private static final String SELECT_PREFIX = "SELECT data, id, aaid, alias, disabled, userVerification, description, tsReg, tsUpdated FROM MMTH_FidoMetadata ";
/*     */   public static final String SELECT_ALLOWED = "SELECT data, id, aaid, alias, disabled, userVerification, description, tsReg, tsUpdated FROM MMTH_FidoMetadata WHERE disabled=?";
/*     */   public static final String INSERT = "INSERT INTO MMTH_FidoMetadata (aaid, alias, disabled, userVerification, description, tsReg, data) VALUES (?, ?, ?, ?, ?, ?, ?)";
/*     */   public static final String UPDATE = "UPDATE MMTH_FidoMetadata set alias=?, disabled=?, userVerification=?, description=?, tsUpdated=? WHERE id=?";
/*     */   public static final String DELETE_BY_PK = "DELETE FROM MMTH_FidoMetadata WHERE id=?";
/*     */   public static final String DELETE_BY_COND = "DELETE FROM MMTH_FidoMetadata WHERE aaid=?";
/*     */   private static final String SELECT_CONTENTS_SUFFIX = " ORDER BY aaid";
/*     */   private static final String SELECT_CONTENT_COUNT_PREFIX = "SELECT COUNT(*) FROM MMTH_FidoMetadata ";
/*     */   private static final String WHERE = " WHERE ";
/*     */   private static final String AND = " AND ";
/*     */   public static final String SELECT_ONE_PK = "SELECT data, id, aaid, alias, disabled, userVerification, description, tsReg, tsUpdated FROM MMTH_FidoMetadata WHERE id=? ";
/*     */   public static final String SELECT_ONE_COND = "SELECT data, id, aaid, alias, disabled, userVerification, description, tsReg, tsUpdated FROM MMTH_FidoMetadata WHERE aaid=? ";
/*     */   public static final String SELECT_ALL_LIST = "SELECT data, id, aaid, alias, disabled, userVerification, description, tsReg, tsUpdated FROM MMTH_FidoMetadata ";
/*     */   
/*     */   public static Object[] getAllowedParams() {
/*  58 */     return new Object[] { DisabledStatus.ENABLED.getCode() };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int[] getInsertTypes() {
/*  69 */     return new int[] { 1, 12, 1, -5, 12, -5, Commons.ctx.getDatabaseAccess().getType().getBLOBType() };
/*     */   }
/*     */ 
/*     */   
/*     */   public static Object[] toInsertParams(FidoMetadataVO vo) {
/*  74 */     String alias = StringUtils.trimUTF8(vo.getAlias(), 128, Commons.ctx.getDatabaseAccess().useSafeUtf8());
/*     */     
/*  76 */     String desc = StringUtils.trimUTF8(vo.getDescription(), 1024, Commons.ctx.getDatabaseAccess().useSafeUtf8());
/*     */     
/*  78 */     vo.setTsReg(Long.valueOf(System.currentTimeMillis()));
/*  79 */     return new Object[] { vo.getAaid(), alias, vo.getDisabled().getCode(), Long.valueOf(vo.getUserVerification().getCode()), desc, vo.getTsReg(), Commons.ctx
/*  80 */         .getDatabaseAccess().getType().seializeBlob(vo) };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int[] getUpdateTypes() {
/*  90 */     return new int[] { 12, 1, -5, 12, -5, 4 };
/*     */   }
/*     */ 
/*     */   
/*     */   public static Object[] toUpdateParams(FidoMetadataVO vo) {
/*  95 */     String desc = StringUtils.trimUTF8(vo.getDescription(), 1024, Commons.ctx.getDatabaseAccess().useSafeUtf8());
/*  96 */     String alias = StringUtils.trimUTF8(vo.getAlias(), 128, Commons.ctx.getDatabaseAccess().useSafeUtf8());
/*     */     
/*  98 */     vo.setTsUpdated(Long.valueOf(System.currentTimeMillis()));
/*  99 */     return new Object[] { alias, vo.getDisabled().getCode(), Long.valueOf(vo.getUserVerification().getCode()), desc, vo.getTsUpdated(), Integer.valueOf(vo.getId()) };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object[] toDeletePKParam(int id) {
/* 108 */     return new Object[] { Integer.valueOf(id) };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object[] toDeleteParams(FidoMetadataVO vo) {
/* 114 */     return new Object[] { vo.getAaid() };
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
/*     */   public static Object[] toSelectPKParam(int id) {
/* 132 */     return new Object[] { Integer.valueOf(id) };
/*     */   }
/*     */   
/*     */   public static Object[] toSelectONEParams(FidoMetadataVO vo) {
/* 136 */     return new Object[] { vo.getAaid() };
/*     */   }
/*     */ 
/*     */   
/*     */   public static String selectContents(FidoMetadataVO vo, Long tsFrom, Long tsTo) {
/* 141 */     StringBuilder sb = new StringBuilder(255);
/*     */ 
/*     */     
/* 144 */     sb.append("SELECT data, id, aaid, alias, disabled, userVerification, description, tsReg, tsUpdated FROM MMTH_FidoMetadata ");
/*     */     
/* 146 */     appendCondition(sb, vo, tsFrom, tsTo);
/*     */     
/* 148 */     sb.append(" ORDER BY aaid");
/*     */     
/* 150 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public static String selectContentConunt(FidoMetadataVO vo, Long tsFrom, Long tsTo) {
/* 155 */     StringBuilder sb = new StringBuilder(255);
/*     */ 
/*     */     
/* 158 */     sb.append("SELECT COUNT(*) FROM MMTH_FidoMetadata ");
/*     */     
/* 160 */     appendCondition(sb, vo, tsFrom, tsTo);
/*     */     
/* 162 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   private static void appendCondition(StringBuilder sb, FidoMetadataVO vo, Long tsFrom, Long tsTo) {
/* 167 */     if (vo == null) {
/*     */       return;
/*     */     }
/*     */     
/* 171 */     boolean hasParam = false;
/*     */     
/* 173 */     if (!StringUtils.isEmpty(vo.getAaid())) {
/* 174 */       sb.append(" WHERE ").append("aaid like ?");
/* 175 */       hasParam = true;
/* 176 */     } else if (!StringUtils.isEmpty(vo.getAlias())) {
/* 177 */       sb.append(" WHERE ").append("alias like ?");
/* 178 */       hasParam = true;
/*     */     } 
/*     */     
/* 181 */     if (vo.getDisabled() != null) {
/* 182 */       sb.append(hasParam ? " AND " : " WHERE ").append("disabled = ?");
/* 183 */       hasParam = true;
/*     */     } 
/*     */     
/* 186 */     if (vo.getUserVerification() != null) {
/* 187 */       sb.append(hasParam ? " AND " : " WHERE ").append("userVerification = ?");
/* 188 */       hasParam = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object[] toSelectParams(FidoMetadataVO vo, Long tsFrom, Long tsTo) {
/* 196 */     if (vo == null) {
/* 197 */       return null;
/*     */     }
/*     */     
/* 200 */     List<Object> params = new ArrayList(4);
/*     */     
/* 202 */     if (!StringUtils.isEmpty(vo.getAaid())) {
/* 203 */       params.add("%" + vo.getAaid() + "%");
/* 204 */     } else if (!StringUtils.isEmpty(vo.getAlias())) {
/* 205 */       String alias = vo.getAlias();
/*     */       
/* 207 */       if (Commons.ctx.getDatabaseAccess().useSafeUtf8()) {
/* 208 */         alias = Base64Utils.encodeUrl(alias);
/*     */       }
/* 210 */       params.add("%" + alias + "%");
/*     */     } 
/*     */ 
/*     */     
/* 214 */     if (vo.getDisabled() != null) {
/* 215 */       params.add(vo.getDisabled().getCode());
/*     */     }
/*     */     
/* 218 */     if (vo.getUserVerification() != null) {
/* 219 */       params.add(Long.valueOf(vo.getUserVerification().getCode()));
/*     */     }
/*     */     
/* 222 */     return params.toArray();
/*     */   }
/*     */   
/*     */   public static RowMapper<FidoMetadataVO> getRowMapper() {
/* 226 */     return (RowMapper<FidoMetadataVO>)new FidoMetadataRowMapper(null);
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\db\dao\queries\DMLFidoMetadata.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */