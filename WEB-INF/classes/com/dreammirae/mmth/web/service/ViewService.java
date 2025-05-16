/*     */ package WEB-INF.classes.com.dreammirae.mmth.web.service;
/*     */ 
/*     */ import com.dreammirae.mmth.db.dao.IViewDao;
/*     */ import com.dreammirae.mmth.exception.InternalDBException;
/*     */ import com.dreammirae.mmth.misc.I18nMessage;
/*     */ import com.dreammirae.mmth.vo.AdministratorVO;
/*     */ import com.dreammirae.mmth.vo.bean.IRestValidator;
/*     */ import com.dreammirae.mmth.vo.bean.IViewStatsLocator;
/*     */ import com.dreammirae.mmth.vo.bean.Pagination;
/*     */ import com.dreammirae.mmth.web.exception.I18nMessageException;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class ViewService<T extends IRestValidator, K>
/*     */ {
/*     */   private static final int DEFAULT_PAGILIMIT = 15;
/*     */   
/*     */   public Pagination<T> getPagination(int pageNum, T params, Long tsFrom, Long tsTo) throws I18nMessageException {
/*     */     try {
/*  24 */       int offset = (pageNum - 1) * getLimit();
/*     */       
/*  26 */       Pagination<T> pagination = new Pagination(getLimit(), offset);
/*     */       
/*  28 */       List<T> contents = getViewDao().getViewContent(getLimit(), offset, params, tsFrom, tsTo);
/*  29 */       int total = getViewDao().getViewConentCount(params, tsFrom, tsTo);
/*     */       
/*  31 */       pagination.setContents(contents);
/*  32 */       pagination.setTotal(total);
/*     */       
/*  34 */       pagination.setTsFrom(tsFrom);
/*  35 */       pagination.setTsTo(tsTo);
/*     */       
/*  37 */       return pagination;
/*  38 */     } catch (InternalDBException e) {
/*  39 */       throw new I18nMessageException(new I18nMessage("validate.internalError", new Object[] { e.getMessage() }), e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T getDetails(K id) throws I18nMessageException {
/*     */     try {
/*  50 */       IRestValidator iRestValidator = (IRestValidator)getViewDao().getOneByPK(id);
/*     */       
/*  52 */       if (iRestValidator == null) {
/*  53 */         throw new I18nMessageException(new I18nMessage("validate.noExist"));
/*     */       }
/*     */       
/*  56 */       return (T)iRestValidator;
/*     */     }
/*  58 */     catch (InternalDBException e) {
/*  59 */       throw new I18nMessageException(new I18nMessage("validate.internalError", new Object[] { e.getMessage() }), e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void save(AdministratorVO sessionAdmin, T vo) throws I18nMessageException {
/*     */     try {
/*  66 */       boolean isNew = isNew(vo);
/*     */       
/*  68 */       IRestValidator iRestValidator = (IRestValidator)getViewDao().getOneByObj(vo);
/*     */       
/*  70 */       saveBefore(sessionAdmin, vo, (T)iRestValidator, isNew);
/*     */       
/*  72 */       getViewDao().save(vo);
/*     */       
/*  74 */       saveAfter(sessionAdmin, vo, (T)iRestValidator, isNew);
/*     */     }
/*  76 */     catch (InternalDBException e) {
/*  77 */       throw new I18nMessageException(new I18nMessage("validate.internalError", new Object[] { e.getMessage() }), e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void delete(AdministratorVO sessionAdmin, K id) throws I18nMessageException {
/*     */     try {
/*  85 */       T vo = getDetails(id);
/*     */       
/*  87 */       if (vo == null)
/*     */       {
/*  89 */         throw new I18nMessageException(new I18nMessage("validate.noExist"));
/*     */       }
/*     */       
/*  92 */       deleteBefore(sessionAdmin, id, vo);
/*     */       
/*  94 */       getViewDao().deleteByPk(id);
/*     */       
/*  96 */       deleteAfter(sessionAdmin, id, vo);
/*     */     }
/*  98 */     catch (InternalDBException e) {
/*  99 */       throw new I18nMessageException(new I18nMessage("validate.internalError", new Object[] { e.getMessage() }), e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public List<T> getLatestList(int limit) {
/*     */     try {
/* 106 */       List<T> contents = getViewDao().getViewContent(limit, 0, null, null, null);
/* 107 */       return contents;
/* 108 */     } catch (InternalDBException e) {
/* 109 */       throw new I18nMessageException(new I18nMessage("validate.internalError", new Object[] { e.getMessage() }), e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public List<T> getCsvData(T params, Long tsFrom, Long tsTo) {
/* 114 */     return getViewDao().getViewContent(0, 0, params, tsFrom, tsTo);
/*     */   }
/*     */   
/*     */   protected int getLimit() {
/* 118 */     return 15;
/*     */   }
/*     */   
/*     */   public abstract IViewStatsLocator getViewStatsLocator() throws I18nMessageException;
/*     */   
/*     */   protected abstract void saveBefore(AdministratorVO paramAdministratorVO, T paramT1, T paramT2, boolean paramBoolean) throws I18nMessageException;
/*     */   
/*     */   protected abstract void saveAfter(AdministratorVO paramAdministratorVO, T paramT1, T paramT2, boolean paramBoolean) throws I18nMessageException;
/*     */   
/*     */   protected abstract void deleteBefore(AdministratorVO paramAdministratorVO, K paramK, T paramT) throws I18nMessageException;
/*     */   
/*     */   protected abstract void deleteAfter(AdministratorVO paramAdministratorVO, K paramK, T paramT) throws I18nMessageException;
/*     */   
/*     */   protected abstract boolean isNew(T paramT);
/*     */   
/*     */   protected abstract IViewDao<T, K> getViewDao();
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\web\service\ViewService.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */