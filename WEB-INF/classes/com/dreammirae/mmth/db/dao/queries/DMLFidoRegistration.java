/*     */ package WEB-INF.classes.com.dreammirae.mmth.db.dao.queries;
/*     */ 
/*     */ import com.dreammirae.mmth.config.Commons;
/*     */ import com.dreammirae.mmth.vo.DeviceAppAgentVO;
/*     */ import com.dreammirae.mmth.vo.FidoRegistrationVO;
/*     */ import com.dreammirae.mmth.vo.ServerChallengeVO;
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
/*     */ public class DMLFidoRegistration
/*     */ {
/*     */   public static final String INSERT = "INSERT INTO MMTH_FidoRegistrations (aaid, keyId, tsReg, data) VALUES (?, ?, ?, ?)";
/*     */   public static final String INSERT_FIDO_USER = "INSERT INTO MMTH_FidoUsers (deviceAgentId, fidoRegId, signCntUpdated, tsReg) VALUES (?, ?, ?, ?)";
/*     */   public static final String INSERT_FIDO_TRAN = "INSERT INTO MMTH_FidoTransaction (challengeId, fidoRegId, tranHash, tsReg) VALUES (?, ?, ?, ?)";
/*     */   public static final String UPDATE_FIDO_USER = "UPDATE MMTH_FidoUsers set signCntUpdated=? WHERE fidoRegId=?";
/*     */   public static final String DELETE_BY_PK = "DELETE FROM MMTH_FidoRegistrations WHERE id=?";
/*     */   public static final String DELETE_BY_UNIQUE = "DELETE FROM MMTH_FidoRegistrations WHERE keyId=? and aaid=?";
/*     */   public static final String DELETE_BY_REF = "DELETE FROM MMTH_FidoRegistrations WHERE id=(SELECT fidoRegId FROM MMTH_FidoUsers WHERE deviceAgentId=?)";
/*     */   private static final String SELECT_PREFIX = "SELECT f.data, f.id, f.aaid, f.keyId, f.tsReg, fu.deviceAgentId, fu.signCntUpdated FROM MMTH_FidoRegistrations f LEFT JOIN MMTH_FidoUsers fu ON f.id = fu.fidoRegId ";
/*     */   public static final String SELECT_ONE_PK = "SELECT f.data, f.id, f.aaid, f.keyId, f.tsReg, fu.deviceAgentId, fu.signCntUpdated FROM MMTH_FidoRegistrations f LEFT JOIN MMTH_FidoUsers fu ON f.id = fu.fidoRegId WHERE f.id = ?";
/*     */   public static final String SELECT_ONE_UNIQUE = "SELECT f.data, f.id, f.aaid, f.keyId, f.tsReg, fu.deviceAgentId, fu.signCntUpdated FROM MMTH_FidoRegistrations f LEFT JOIN MMTH_FidoUsers fu ON f.id = fu.fidoRegId WHERE f.keyId = ? and f.aaid = ?";
/*     */   public static final String SELECT_ONE_REF = "SELECT f.data, f.id, f.aaid, f.keyId, f.tsReg, fu.deviceAgentId, fu.signCntUpdated FROM MMTH_FidoRegistrations f LEFT JOIN MMTH_FidoUsers fu ON f.id = fu.fidoRegId WHERE fu.deviceAgentId = ?";
/*     */   public static final String SELECT_FIDO_TRAN = "SELECT count(*) FROM MMTH_FidoTransaction WHERE fidoRegId = ? and tranHash = ?";
/*     */   
/*     */   public static int[] getInsertTypes() {
/*  78 */     return new int[] { 12, 12, -5, Commons.ctx
/*  79 */         .getDatabaseAccess().getType().getBLOBType() };
/*     */   }
/*     */ 
/*     */   
/*     */   public static Object[] toInsertParams(FidoRegistrationVO vo) {
/*  84 */     vo.setTsReg(System.currentTimeMillis());
/*     */     
/*  86 */     return new Object[] { vo
/*  87 */         .getAaid(), vo.getKeyId(), Long.valueOf(vo.getTsReg()), Commons.ctx
/*  88 */         .getDatabaseAccess().getType().seializeBlob(vo) };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static int[] getInsertFidoUserTypes() {
/*  94 */     return new int[] { 4, 4, -5, -5 };
/*     */   }
/*     */   
/*     */   public static Object[] toInsertFidoUserParams(FidoRegistrationVO vo) {
/*  98 */     return new Object[] {
/*  99 */         Integer.valueOf(vo.getDeviceAgentId()), Integer.valueOf(vo.getId()), Long.valueOf(vo.getSignCntUpdated()), Long.valueOf(System.currentTimeMillis())
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public static int[] getInsertFidoTranTypes() {
/* 105 */     return new int[] { -5, 4, 12, -5 };
/*     */   }
/*     */ 
/*     */   
/*     */   public static BatchPreparedStatementSetter getFidoTranBatchPreparedStatementSetter(ServerChallengeVO challenge, FidoRegistrationVO vo) {
/* 110 */     List<String> trans = vo.getTransactions();
/*     */     
/* 112 */     return (BatchPreparedStatementSetter)new Object(trans, challenge, vo);
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
/*     */ 
/*     */   
/*     */   public static int[] getUpdateFidoUserTypes() {
/* 137 */     return new int[] { -5, 4 };
/*     */   }
/*     */   
/*     */   public static Object[] toUpdateFidoUserParams(FidoRegistrationVO vo) {
/* 141 */     return new Object[] { Long.valueOf(vo.getSignCntUpdated()), Integer.valueOf(vo.getId()) };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object[] toDeletePKParam(int id) {
/* 150 */     return new Object[] { Integer.valueOf(id) };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object[] toDeleteParams(FidoRegistrationVO vo) {
/* 156 */     return toDeleteParams(vo.getKeyId(), vo.getAaid());
/*     */   }
/*     */   
/*     */   public static Object[] toDeleteParams(String keyId, String aaid) {
/* 160 */     return new Object[] { keyId, aaid };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object[] toDeleteRefParams(DeviceAppAgentVO deviceAppAgent) {
/* 166 */     return new Object[] { Integer.valueOf(deviceAppAgent.getId()) };
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
/*     */   public static Object[] toSelectPKParam(int id) {
/* 180 */     return new Object[] { Integer.valueOf(id) };
/*     */   }
/*     */   
/*     */   public static Object[] toSelectParams(String keyId, String aaid) {
/* 184 */     return new Object[] { keyId, aaid };
/*     */   }
/*     */   
/*     */   public static Object[] toSelectRefParam(DeviceAppAgentVO deviceAgent) {
/* 188 */     return new Object[] { Integer.valueOf(deviceAgent.getId()) };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object[] toSelectFidoTranParam(FidoRegistrationVO vo, String tranHash) {
/* 195 */     return new Object[] { Integer.valueOf(vo.getId()), tranHash };
/*     */   }
/*     */ 
/*     */   
/*     */   public static RowMapper<FidoRegistrationVO> getRowMapper() {
/* 200 */     return (RowMapper<FidoRegistrationVO>)new FidoRegistrationRowMapper(null);
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\db\dao\queries\DMLFidoRegistration.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */