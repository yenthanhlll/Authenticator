/*     */ package WEB-INF.classes.com.dreammirae.mmth.db.dao.queries;
/*     */ 
/*     */ import com.dreammirae.mmth.exception.InternalDBException;
/*     */ import com.dreammirae.mmth.vo.ServiceLogVO;
/*     */ import com.dreammirae.mmth.vo.bean.GlobalWibeeLogData;
/*     */ import com.dreammirae.mmth.vo.bean.MiraeAssetVietnamLogData;
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
/*     */ public final class DMLServiceLogAnnotation
/*     */ {
/*     */   public static final String INSERT_GLOBAL_WIBEE = "INSERT INTO MMTH_ServiceLogAnnotations (serviceLogId, regEmpNo) VALUES (?, ?)";
/*     */   public static final String INSERT_MIRAEASSET_VIETNAM = "INSERT INTO MMTH_ServiceLogAnnotations (serviceLogId, ip, macAddress) VALUES (?, ?, ?)";
/*     */   public static final String SELECT_GLOBAL_WIBEE = "SELECT regEmpNo FROM MMTH_ServiceLogAnnotations WHERE serviceLogId = ?";
/*     */   public static final String SELECT_MIRAEASSET_VIETNAM = "SELECT ip, macAddress FROM MMTH_ServiceLogAnnotations WHERE serviceLogId = ?";
/*     */   
/*     */   public static int[] getInsertTypes_globalWibee() {
/*  61 */     return new int[] { -5, 12 };
/*     */   }
/*     */   
/*     */   public static int[] getInsertTypes_miraeAssetVietnam() {
/*  65 */     return new int[] { -5, 4, -5, -9 };
/*     */   }
/*     */ 
/*     */   
/*     */   public static Object[] toInsertParams_globalWibee(ServiceLogVO vo) {
/*  70 */     if (vo.getCustomData() instanceof GlobalWibeeLogData) {
/*  71 */       return new Object[] { Long.valueOf(vo.getId()), ((GlobalWibeeLogData)vo.getCustomData()).getRegEmpNo() };
/*     */     }
/*     */     
/*  74 */     throw new InternalDBException("The custom log data is not GlobalWibeeLogData instance.");
/*     */   }
/*     */ 
/*     */   
/*     */   public static Object[] toInsertParams_miraeAssetVietnam(ServiceLogVO vo) {
/*  79 */     if (vo.getCustomData() instanceof MiraeAssetVietnamLogData) {
/*  80 */       return new Object[] { Long.valueOf(vo.getId()), Long.valueOf(((MiraeAssetVietnamLogData)vo.getCustomData()).getIp()), ((MiraeAssetVietnamLogData)vo.getCustomData()).getMacAddress() };
/*     */     }
/*     */     
/*  83 */     throw new InternalDBException("The custom log data is not MiraeAssetVietnamLogData instance.");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object[] toSelect_globalWibee(ServiceLogVO vo) {
/*  92 */     return new Object[] { Long.valueOf(vo.getId()) };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object[] toSelect_miraeAssetVietnam(ServiceLogVO vo) {
/*  98 */     return new Object[] { Long.valueOf(vo.getId()) };
/*     */   }
/*     */   
/*     */   public static RowMapper<MiraeAssetVietnamLogData> getRowMapper() {
/* 102 */     return (RowMapper<MiraeAssetVietnamLogData>)new MireaAssetServiceLogAnnotationRowMapper(null);
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\db\dao\queries\DMLServiceLogAnnotation.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */