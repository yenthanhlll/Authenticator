/*     */ package WEB-INF.classes.com.dreammirae.mmth.db.dao;
/*     */ 
/*     */ import com.dreammirae.mmth.authentication.ReturnCodes;
/*     */ import com.dreammirae.mmth.db.dao.BaseDao;
/*     */ import com.dreammirae.mmth.db.dao.IViewDao;
/*     */ import com.dreammirae.mmth.db.dao.UserDao;
/*     */ import com.dreammirae.mmth.db.dao.queries.DMLHwtoken;
/*     */ import com.dreammirae.mmth.exception.InternalDBException;
/*     */ import com.dreammirae.mmth.exception.ReturnCodeException;
/*     */ import com.dreammirae.mmth.util.StringUtils;
/*     */ import com.dreammirae.mmth.vo.HwTokenVO;
/*     */ import com.dreammirae.mmth.vo.UserVO;
/*     */ import java.util.List;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.jdbc.core.RowMapper;
/*     */ import org.springframework.stereotype.Repository;
/*     */ import org.springframework.transaction.support.TransactionCallbackWithoutResult;
/*     */ 
/*     */ @Repository("hwTokenDao")
/*     */ public class HwTokenDao
/*     */   extends BaseDao implements IViewDao<HwTokenVO, String> {
/*  23 */   protected static final Logger LOG = LoggerFactory.getLogger(com.dreammirae.mmth.db.dao.HwTokenDao.class);
/*     */   
/*     */   public void save(HwTokenVO vo) throws InternalDBException {
/*  26 */     if (-1 == vo.getId()) {
/*  27 */       insert(vo);
/*     */     } else {
/*     */       
/*  30 */       if (vo.getUsername() != null) {
/*  31 */         UserDao dao = new UserDao();
/*  32 */         UserVO user = dao.getOneByUnique(vo.getUsername());
/*  33 */         if (user == null)
/*     */         {
/*  35 */           throw new ReturnCodeException(ReturnCodes.USER_UNAUTHORIED);
/*     */         }
/*  37 */         vo.setUserId(user.getId());
/*  38 */         vo.setUsername(user.getUsername());
/*     */       } 
/*     */       
/*  41 */       update(vo);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void insert(HwTokenVO vo) throws InternalDBException {
/*  47 */     doInTransaction((TransactionCallbackWithoutResult)new Object(this, vo));
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
/*     */   private void update(HwTokenVO vo) throws InternalDBException {
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
/*     */   public void updateKey(HwTokenVO vo) throws InternalDBException {
/*  70 */     doInTransaction((TransactionCallbackWithoutResult)new Object(this, vo));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateUnLockCnt(HwTokenVO vo) throws InternalDBException {
/*  81 */     doInTransaction((TransactionCallbackWithoutResult)new Object(this, vo));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateLost(HwTokenVO vo) throws InternalDBException {
/*  92 */     doInTransaction((TransactionCallbackWithoutResult)new Object(this, vo));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateDiscard(HwTokenVO vo) throws InternalDBException {
/* 103 */     doInTransaction((TransactionCallbackWithoutResult)new Object(this, vo));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateOccupied(HwTokenVO vo) throws InternalDBException {
/* 114 */     doInTransaction((TransactionCallbackWithoutResult)new Object(this, vo));
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
/*     */   public HwTokenVO getOneByToken(String tokenId) throws InternalDBException {
/* 138 */     if (StringUtils.isEmpty(tokenId)) {
/* 139 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 146 */     String sql = "SELECT userId, username, branchId, commend, tokenType, tokenId, tokenKey, status, lost, tsLost, unlockCnt, tsReg, tsOccupied, tsSuspend, tsDiscard  FROM MMTH_HwTokens WHERE tokenId=?";
/* 147 */     Object[] args = DMLHwtoken.toSelectToken(tokenId);
/* 148 */     RowMapper<HwTokenVO> rowMapper = DMLHwtoken.getRowMapper();
/*     */     
/* 150 */     HwTokenVO hwtoken = (HwTokenVO)queryForObject(sql, args, rowMapper);
/*     */     
/* 152 */     return hwtoken;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HwTokenVO getOneByPK(String id) throws InternalDBException {
/* 159 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public HwTokenVO getOneByObj(HwTokenVO vo) throws InternalDBException {
/* 165 */     if (vo.getUserId() == 0) {
/* 166 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 173 */     String sql = "SELECT userId, username, branchId, commend, tokenType, tokenId, tokenKey, status, lost, tsLost, unlockCnt, tsReg, tsOccupied, tsSuspend, tsDiscard  FROM MMTH_HwTokens WHERE userId=?";
/* 174 */     Object[] args = DMLHwtoken.toSelectUser(vo);
/* 175 */     RowMapper<HwTokenVO> rowMapper = DMLHwtoken.getRowMapper();
/*     */     
/* 177 */     HwTokenVO hwtoken = (HwTokenVO)queryForObject(sql, args, rowMapper);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 182 */     return hwtoken;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void saveAsBatch(List<HwTokenVO> tokens) throws InternalDBException {
/* 191 */     doSaveAsBatch("INSERT INTO MMTH_HwTokens (tokenType, tokenId, tokenKey, status, tsReg) VALUES (?, ?, ?, ?, ?)", DMLHwtoken.getBatchPreparedStatementSetter(tokens));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<HwTokenVO> getViewContent(int limit, int offset, HwTokenVO vo, Long tsFrom, Long tsTo) throws InternalDBException {
/* 197 */     String sql = DMLHwtoken.selectContents(vo, tsFrom, tsTo);
/* 198 */     Object[] args = DMLHwtoken.toSelectParams(vo, tsFrom, tsTo);
/*     */     
/* 200 */     if (args == null || args.length < 1) {
/* 201 */       return queryForListWithLimit(sql, DMLHwtoken.getRowMapper(), limit, offset);
/*     */     }
/* 203 */     return queryForListWithLimit(sql, args, DMLHwtoken.getRowMapper(), limit, offset);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getViewConentCount(HwTokenVO vo, Long tsFrom, Long tsTo) throws InternalDBException {
/* 209 */     String sql = DMLHwtoken.selectContentConunt(vo, tsFrom, tsTo);
/* 210 */     Object[] args = DMLHwtoken.toSelectParams(vo, tsFrom, tsTo);
/* 211 */     return queryForInt(sql, args, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int deleteByPk(String id) throws InternalDBException {
/* 218 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int delete(HwTokenVO vo) throws InternalDBException {
/* 224 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int deleteByUser(String username) throws InternalDBException {
/* 229 */     if (StringUtils.isEmpty(username))
/*     */     {
/* 231 */       return -1;
/*     */     }
/*     */     
/* 234 */     return doUpdate("DELETE FROM MMTH_HwTokens WHERE username=?", DMLHwtoken.toDeleteUserParam(username));
/*     */   }
/*     */ 
/*     */   
/*     */   public int allDelete() throws InternalDBException {
/* 239 */     return doTruncate("TRUNCATE TABLE MMTH_HwTokens");
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\db\dao\HwTokenDao.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */