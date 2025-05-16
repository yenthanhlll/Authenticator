/*     */ package WEB-INF.classes.com.dreammirae.mmth.db.dao;
/*     */ 
/*     */ import com.dreammirae.mmth.db.dao.BaseDao;
/*     */ import com.dreammirae.mmth.db.dao.IViewDao;
/*     */ import com.dreammirae.mmth.db.dao.queries.DMLToken;
/*     */ import com.dreammirae.mmth.exception.InternalDBException;
/*     */ import com.dreammirae.mmth.util.StringUtils;
/*     */ import com.dreammirae.mmth.vo.TokenVO;
/*     */ import java.util.List;
/*     */ import org.springframework.stereotype.Repository;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Repository("tokenDao")
/*     */ public class TokenDao
/*     */   extends BaseDao
/*     */   implements IViewDao<TokenVO, String>
/*     */ {
/*     */   public void saveAsBatch(List<TokenVO> tokens) throws InternalDBException {
/*  22 */     doSaveAsBatch("INSERT INTO MMTH_Tokens (tokenId, tokenData, status, tsReg) VALUES (?, ?, ?, ?)", DMLToken.getBatchPreparedStatementSetter(tokens));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void save(TokenVO vo) throws InternalDBException {
/*  30 */     if (!StringUtils.isEmpty(vo.getTokenId())) {
/*  31 */       doUpdate("UPDATE MMTH_Tokens SET status=?, authType=?, username=?, tsOccupied=? WHERE tokenId=?", DMLToken.toUpdateParams(vo), DMLToken.getUpdateTypes());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TokenVO getOneByPK(String tokenId) throws InternalDBException {
/*  40 */     if (StringUtils.isEmpty(tokenId))
/*     */     {
/*  42 */       return null;
/*     */     }
/*     */     
/*  45 */     return (TokenVO)queryForObject("SELECT tokenId, tokenData, status, authType, username, lost, tsReg, tsOccupied, tsDiscard, tsLost FROM MMTH_Tokens WHERE tokenId=? ", DMLToken.toSelectPKParam(tokenId), 
/*  46 */         DMLToken.getRowMapper());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TokenVO getOneByObj(TokenVO vo) throws InternalDBException {
/*  56 */     if (vo == null || StringUtils.isEmpty(vo.getTokenId())) {
/*  57 */       String sql = DMLToken.selectTokens(vo);
/*  58 */       return (TokenVO)queryForObject(sql, DMLToken.toSelectTokenParams(vo), 
/*  59 */           DMLToken.getTokenRowMapper());
/*     */     } 
/*     */     
/*  62 */     return (TokenVO)queryForObject("SELECT tokenId, tokenData, status, authType, username, lost, tsReg, tsOccupied, tsDiscard, tsLost FROM MMTH_Tokens WHERE tokenId=? ", DMLToken.toSelectONEParams(vo), 
/*  63 */         DMLToken.getRowMapper());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<TokenVO> getViewContent(int limit, int offset, TokenVO vo, Long tsFrom, Long tsTo) throws InternalDBException {
/*  71 */     String sql = DMLToken.selectContents(vo, tsFrom, tsTo);
/*  72 */     Object[] args = DMLToken.toSelectParams(vo, tsFrom, tsTo);
/*     */     
/*  74 */     if (args == null || args.length < 1) {
/*  75 */       return queryForListWithLimit(sql, DMLToken.getRowMapper(), limit, offset);
/*     */     }
/*  77 */     return queryForListWithLimit(sql, args, DMLToken.getRowMapper(), limit, offset);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getViewConentCount(TokenVO vo, Long tsFrom, Long tsTo) throws InternalDBException {
/*  86 */     String sql = DMLToken.selectContentCount(vo, tsFrom, tsTo);
/*  87 */     Object[] args = DMLToken.toSelectParams(vo, tsFrom, tsTo);
/*  88 */     return queryForInt(sql, args, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int deleteByPk(String tokenId) throws InternalDBException {
/*  97 */     if (StringUtils.isEmpty(tokenId))
/*     */     {
/*  99 */       return -1;
/*     */     }
/*     */     
/* 102 */     return doUpdate("DELETE FROM MMTH_Tokens WHERE tokenId=?", DMLToken.toDeletePKParam(tokenId));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int delete(TokenVO vo) throws InternalDBException {
/* 111 */     if (vo == null || StringUtils.isEmpty(vo.getTokenId())) {
/* 112 */       return -1;
/*     */     }
/*     */     
/* 115 */     return doUpdate("DELETE FROM MMTH_Tokens WHERE tokenId=?", DMLToken.toDeleteParams(vo));
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\db\dao\TokenDao.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */