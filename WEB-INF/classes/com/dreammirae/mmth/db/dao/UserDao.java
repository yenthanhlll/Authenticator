/*     */ package WEB-INF.classes.com.dreammirae.mmth.db.dao;
/*     */ 
/*     */ import com.dreammirae.mmth.authentication.AuthMethodTypes;
/*     */ import com.dreammirae.mmth.db.dao.BaseDao;
/*     */ import com.dreammirae.mmth.db.dao.IViewDao;
/*     */ import com.dreammirae.mmth.db.dao.SystemSettingsDao;
/*     */ import com.dreammirae.mmth.db.dao.queries.DMLAuthManager;
/*     */ import com.dreammirae.mmth.db.dao.queries.DMLDeviceAppAgent;
/*     */ import com.dreammirae.mmth.db.dao.queries.DMLToken;
/*     */ import com.dreammirae.mmth.db.dao.queries.DMLUser;
/*     */ import com.dreammirae.mmth.db.dao.queries.DMLUserView;
/*     */ import com.dreammirae.mmth.exception.InternalDBException;
/*     */ import com.dreammirae.mmth.external.ExternalWebApiTypes;
/*     */ import com.dreammirae.mmth.util.StringUtils;
/*     */ import com.dreammirae.mmth.vo.AuthManagerVO;
/*     */ import com.dreammirae.mmth.vo.UserVO;
/*     */ import com.dreammirae.mmth.vo.types.DeviceAppAgentStatus;
/*     */ import com.dreammirae.mmth.vo.types.TokenStatus;
/*     */ import com.dreammirae.mmth.vo.types.UserStatus;
/*     */ import com.dreammirae.mmth.vo.types.YNStatus;
/*     */ import java.util.List;
/*     */ import org.springframework.jdbc.core.RowMapper;
/*     */ import org.springframework.stereotype.Repository;
/*     */ import org.springframework.transaction.support.TransactionCallbackWithoutResult;
/*     */ 
/*     */ @Repository("userDao")
/*     */ public class UserDao
/*     */   extends BaseDao
/*     */   implements IViewDao<UserVO, Integer>
/*     */ {
/*     */   public void save(UserVO vo) throws InternalDBException {
/*  32 */     if (-1 == vo.getId()) {
/*  33 */       insert(vo);
/*     */     } else {
/*  35 */       update(vo);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void insert(UserVO vo) throws InternalDBException {
/*  41 */     doInTransaction((TransactionCallbackWithoutResult)new Object(this, vo));
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
/*     */   private void update(UserVO vo) throws InternalDBException {
/*  59 */     doInTransaction((TransactionCallbackWithoutResult)new Object(this, vo));
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
/*     */   public void saveAsBatch(List<UserVO> users) throws InternalDBException {
/*  82 */     doSaveAsBatch("INSERT INTO MMTH_Users (username, accountName, disabled, productType, multiLoginYN, status, tsReg, data) VALUES (?, ?, ?, ?, ?, ?, ?, ?)", DMLUser.getBatchPreparedStatementSetter(users));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void deleteAsBatch(List<UserVO> users) throws InternalDBException {
/*  90 */     doSaveAsBatch("DELETE FROM MMTH_Users WHERE username=?", DMLUser.getBatchPreparedStatementSetterDel(users));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public UserVO getOneByPK(Integer id) throws InternalDBException {
/*     */     String sql;
/*     */     Object[] args;
/*     */     RowMapper<UserVO> rowMapper;
/*  99 */     if (!ExternalWebApiTypes.NONE.equals(SystemSettingsDao.getWebApiPolicy()) && !ExternalWebApiTypes.BASIC.equals(SystemSettingsDao.getWebApiPolicy())) {
/* 100 */       sql = "SELECT data, id, username, productType, multiLoginYN, disabled, status, tsReg, tsUpdated, annotationData, displayName, extUnique, customerCode, countryCode FROM MMTH_UserView WHERE id=?";
/* 101 */       args = DMLUserView.toSelectPKParam(id.intValue());
/* 102 */       rowMapper = DMLUserView.getRowMapper();
/*     */     } else {
/* 104 */       sql = "SELECT data, a.id, a.username, accountName, productType, multiLoginYN, disabled, a.status, a.tsReg, a.tsUpdated, b.tokenId, b.tokentype FROM MMTH_Users a LEFT OUTER JOIN MMTH_HwTokens b ON a.id=b.userid WHERE a.id=?";
/* 105 */       args = DMLUser.toSelectPKParam(id.intValue());
/* 106 */       rowMapper = DMLUser.getRowMapper();
/*     */     } 
/* 108 */     UserVO vo = (UserVO)queryForObject(sql, args, rowMapper);
/* 109 */     if (vo != null)
/*     */     {
/* 111 */       setRelationData(vo);
/*     */     }
/*     */     
/* 114 */     return vo;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public UserVO getOneByUnique(String username) throws InternalDBException {
/* 120 */     if (StringUtils.isEmpty(username)) {
/* 121 */       return null;
/*     */     }
/*     */     
/* 124 */     UserVO user = (UserVO)queryForObject("SELECT data, a.id, a.username, accountName, productType, multiLoginYN, disabled, a.status, a.tsReg, a.tsUpdated, b.tokenId, b.tokentype FROM MMTH_Users a LEFT OUTER JOIN MMTH_HwTokens b ON a.id=b.userid WHERE a.username=?", DMLUser.toSelectParams(username), DMLUser.getRowMapper());
/*     */ 
/*     */     
/* 127 */     return user;
/*     */   }
/*     */   
/*     */   public UserVO getOneByObj(UserVO vo) throws InternalDBException {
/*     */     String sql;
/*     */     Object[] args;
/*     */     RowMapper<UserVO> rowMapper;
/* 134 */     if (StringUtils.isEmpty(vo.getUsername())) {
/* 135 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 141 */     if (!ExternalWebApiTypes.NONE.equals(SystemSettingsDao.getWebApiPolicy()) && !ExternalWebApiTypes.BASIC.equals(SystemSettingsDao.getWebApiPolicy())) {
/* 142 */       sql = "SELECT data, id, username, productType, multiLoginYN, disabled, status, tsReg, tsUpdated, annotationData, displayName, extUnique, customerCode, countryCode FROM MMTH_UserView WHERE username=?";
/* 143 */       args = DMLUserView.toSelectParams(vo);
/* 144 */       rowMapper = DMLUserView.getRowMapper();
/*     */     } else {
/* 146 */       sql = "SELECT data, a.id, a.username, accountName, productType, multiLoginYN, disabled, a.status, a.tsReg, a.tsUpdated, b.tokenId, b.tokentype FROM MMTH_Users a LEFT OUTER JOIN MMTH_HwTokens b ON a.id=b.userid WHERE a.username=?";
/* 147 */       args = DMLUser.toSelectParams(vo);
/* 148 */       rowMapper = DMLUser.getRowMapper();
/*     */     } 
/*     */     
/* 151 */     UserVO user = (UserVO)queryForObject(sql, args, rowMapper);
/*     */     
/* 153 */     if (user != null) {
/* 154 */       setRelationData(user);
/*     */     }
/*     */     
/* 157 */     return user;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<UserVO> getViewContent(int limit, int offset, UserVO vo, Long tsFrom, Long tsTo) throws InternalDBException {
/*     */     String sql;
/*     */     Object[] args;
/*     */     RowMapper<UserVO> rowMapper;
/* 167 */     if (!ExternalWebApiTypes.NONE.equals(SystemSettingsDao.getWebApiPolicy()) && !ExternalWebApiTypes.BASIC.equals(SystemSettingsDao.getWebApiPolicy()) && !ExternalWebApiTypes.MIRAE_ASSET_VT.equals(SystemSettingsDao.getWebApiPolicy())) {
/* 168 */       sql = DMLUserView.selectContents(vo, tsFrom, tsTo);
/* 169 */       args = DMLUserView.toSelectParams(vo, tsFrom, tsTo);
/* 170 */       rowMapper = DMLUserView.getRowMapper();
/*     */     } else {
/* 172 */       sql = DMLUser.selectContents(vo, tsFrom, tsTo);
/* 173 */       args = DMLUser.toSelectParams(vo, tsFrom, tsTo);
/* 174 */       rowMapper = DMLUser.getRowMapper();
/*     */     } 
/*     */     
/* 177 */     List<UserVO> list = null;
/* 178 */     if (args == null || args.length < 1) {
/* 179 */       list = queryForListWithLimit(sql, rowMapper, limit, offset);
/*     */     } else {
/* 181 */       list = queryForListWithLimit(sql, args, rowMapper, limit, offset);
/*     */     } 
/*     */     
/* 184 */     for (UserVO user : list) {
/* 185 */       setRelationData(user);
/*     */     }
/*     */     
/* 188 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getViewConentCount(UserVO vo, Long tsFrom, Long tsTo) throws InternalDBException {
/* 193 */     String sql = DMLUser.selectContentConunt(vo, tsFrom, tsTo);
/* 194 */     Object[] args = DMLUser.toSelectParams(vo, tsFrom, tsTo);
/* 195 */     return queryForInt(sql, args, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int deleteByPk(Integer id) throws InternalDBException {
/* 201 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int delete(UserVO vo) throws InternalDBException {
/* 207 */     if (vo == null || StringUtils.isEmpty(vo.getUsername())) {
/* 208 */       return -1;
/*     */     }
/*     */     
/* 211 */     return doUpdate("DELETE FROM MMTH_Users WHERE username=?", DMLUser.toDeleteParams(vo));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void setRelationData(UserVO user) {
/* 217 */     if (SystemSettingsDao.getSettingEnabled("advanced.biotpEnabled").toBoolean()) {
/* 218 */       int biotpActive = queryForInt("SELECT COUNT(*) FROM MMTH_DeviceAppAgents WHERE userDeviceId in (SELECT id FROM MMTH_UserDevices WHERE userId = ?) AND status = ? AND authType = ?", DMLDeviceAppAgent.toSelectActiveCntByUserAuthType(user, DeviceAppAgentStatus.AVAIABLE, AuthMethodTypes.BIOTP), 0);
/* 219 */       if (biotpActive > 0) {
/* 220 */         int lostCnt = queryForInt("SELECT count(*) FROM MMTH_Tokens WHERE status=? AND username=? AND authType=? AND lost=?", DMLToken.getSelectUserCondition(TokenStatus.OCCUPIED, user.getUsername(), AuthMethodTypes.BIOTP, YNStatus.Y), 0);
/*     */         
/* 222 */         if (lostCnt > 0) {
/* 223 */           user.setBiotpStatus(UserStatus.LOST_STOLEN);
/*     */         } else {
/* 225 */           user.setBiotpStatus(UserStatus.AVAILABLE);
/*     */         }
/*     */       
/* 228 */       } else if (user.getBiotpRegCumulation() > 0) {
/* 229 */         user.setBiotpStatus(UserStatus.DISCARD);
/*     */       } else {
/* 231 */         user.setBiotpStatus(UserStatus.NOT_AVAILABLE);
/*     */       } 
/*     */       
/* 234 */       AuthManagerVO vo = (AuthManagerVO)queryForObject("SELECT 0, userId, 0, authType, sum(authFailCnt), sum(totSuccessCnt), max(tsLastAuth), max(tsLastAuthFail) FROM MMTH_AuthManager WHERE userId = ? AND authType = ? GROUP BY userId, authType", DMLAuthManager.toSelectLatestByUser(user, AuthMethodTypes.BIOTP), DMLAuthManager.getRowMapper());
/* 235 */       if (vo != null) {
/* 236 */         user.setBiotpAuthManager(vo);
/*     */       }
/*     */     } 
/*     */     
/* 240 */     if (SystemSettingsDao.getSettingEnabled("advanced.fidoEnabled").toBoolean()) {
/* 241 */       int fidoActive = queryForInt("SELECT COUNT(*) FROM MMTH_DeviceAppAgents WHERE userDeviceId in (SELECT id FROM MMTH_UserDevices WHERE userId = ?) AND status = ? AND authType = ?", DMLDeviceAppAgent.toSelectActiveCntByUserAuthType(user, DeviceAppAgentStatus.AVAIABLE, AuthMethodTypes.FIDO), 0);
/*     */       
/* 243 */       if (fidoActive > 0) {
/* 244 */         user.setFidoStatus(UserStatus.AVAILABLE);
/*     */       } else {
/* 246 */         user.setFidoStatus(UserStatus.NOT_AVAILABLE);
/*     */       } 
/*     */       
/* 249 */       AuthManagerVO vo = (AuthManagerVO)queryForObject("SELECT 0, userId, 0, authType, sum(authFailCnt), sum(totSuccessCnt), max(tsLastAuth), max(tsLastAuthFail) FROM MMTH_AuthManager WHERE userId = ? AND authType = ? GROUP BY userId, authType", DMLAuthManager.toSelectLatestByUser(user, AuthMethodTypes.BIOTP), DMLAuthManager.getRowMapper());
/* 250 */       if (vo != null)
/* 251 */         user.setFidoAuthManager(vo); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\db\dao\UserDao.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */