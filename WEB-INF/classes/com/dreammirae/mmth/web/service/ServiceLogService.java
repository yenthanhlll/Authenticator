/*     */ package WEB-INF.classes.com.dreammirae.mmth.web.service;
/*     */ 
/*     */ import com.dreammirae.mmth.db.dao.IViewDao;
/*     */ import com.dreammirae.mmth.db.dao.ServiceLogDao;
/*     */ import com.dreammirae.mmth.db.dao.UserDeviceDao;
/*     */ import com.dreammirae.mmth.misc.MessageUtils;
/*     */ import com.dreammirae.mmth.vo.AdministratorVO;
/*     */ import com.dreammirae.mmth.vo.ServiceLogVO;
/*     */ import com.dreammirae.mmth.vo.UserDeviceVO;
/*     */ import com.dreammirae.mmth.vo.bean.IRestValidator;
/*     */ import com.dreammirae.mmth.vo.bean.IViewStatsLocator;
/*     */ import com.dreammirae.mmth.vo.bean.MiraeAssetVietnamLogData;
/*     */ import com.dreammirae.mmth.web.exception.I18nMessageException;
/*     */ import com.dreammirae.mmth.web.service.ViewService;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.util.List;
/*     */ import org.apache.poi.ss.usermodel.CreationHelper;
/*     */ import org.apache.poi.ss.usermodel.Row;
/*     */ import org.apache.poi.ss.usermodel.Sheet;
/*     */ import org.apache.poi.xssf.usermodel.XSSFWorkbook;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.context.i18n.LocaleContextHolder;
/*     */ import org.springframework.stereotype.Service;
/*     */ 
/*     */ 
/*     */ 
/*     */ @Service("serviceLogService")
/*     */ public class ServiceLogService
/*     */   extends ViewService<ServiceLogVO, Long>
/*     */ {
/*     */   @Autowired
/*     */   private ServiceLogDao dao;
/*     */   @Autowired
/*     */   private UserDeviceDao UserDeviceDao;
/*     */   
/*     */   public IViewStatsLocator getViewStatsLocator() throws I18nMessageException {
/*  39 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void saveBefore(AdministratorVO sessionAdmin, ServiceLogVO vo, ServiceLogVO prev, boolean isNew) throws I18nMessageException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void saveAfter(AdministratorVO sessionAdmin, ServiceLogVO vo, ServiceLogVO prev, boolean isNew) throws I18nMessageException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void deleteBefore(AdministratorVO sessionAdmin, Long id, ServiceLogVO vo) throws I18nMessageException {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void deleteAfter(AdministratorVO sessionAdmin, Long id, ServiceLogVO deleted) throws I18nMessageException {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isNew(ServiceLogVO vo) {
/*  69 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected IViewDao<ServiceLogVO, Long> getViewDao() {
/*  75 */     if (this.dao == null) {
/*  76 */       this.dao = new ServiceLogDao();
/*     */     }
/*     */     
/*  79 */     return (IViewDao<ServiceLogVO, Long>)this.dao;
/*     */   }
/*     */   
/*     */   public MiraeAssetVietnamLogData getCustomData(ServiceLogVO vo) {
/*  83 */     return this.dao.getCustomData(vo);
/*     */   }
/*     */   
/*     */   public File exportCsv(List<ServiceLogVO> contents, String[] header, String[] columnKeys, String filename) {
/*  87 */     XSSFWorkbook xSSFWorkbook = new XSSFWorkbook();
/*  88 */     CreationHelper helper = xSSFWorkbook.getCreationHelper();
/*  89 */     Sheet sheet = xSSFWorkbook.createSheet("new sheet");
/*  90 */     File file = null;
/*     */     try {
/*  92 */       int rowNum = 0;
/*  93 */       Row rowHeader = sheet.createRow((short)rowNum++);
/*  94 */       for (int j = 0; j < header.length; j++)
/*     */       {
/*  96 */         rowHeader.createCell(j).setCellValue(MessageUtils.getMessageSource().getMessage(header[j], null, LocaleContextHolder.getLocale()));
/*     */       }
/*     */       
/*  99 */       for (ServiceLogVO content : contents) {
/* 100 */         Row row = sheet.createRow((short)rowNum++);
/* 101 */         for (int i = 0; i < columnKeys.length; i++) {
/* 102 */           row.createCell(i)
/* 103 */             .setCellValue(helper.createRichTextString(content.getValue(columnKeys[i], content)));
/*     */         }
/*     */       } 
/*     */       
/* 107 */       file = new File(filename);
/* 108 */       FileOutputStream fileOut = new FileOutputStream(file);
/*     */       
/* 110 */       xSSFWorkbook.write(fileOut);
/*     */ 
/*     */       
/* 113 */       fileOut.close();
/*     */     }
/* 115 */     catch (IOException e) {
/* 116 */       e.printStackTrace();
/*     */     } finally {
/* 118 */       if (xSSFWorkbook != null) try { xSSFWorkbook.close(); } catch (IOException e) {}
/*     */     
/*     */     } 
/* 121 */     return file;
/*     */   }
/*     */ 
/*     */   
/*     */   public UserDeviceVO getModel(int id) {
/* 126 */     if (null == this.UserDeviceDao)
/*     */     {
/* 128 */       this.UserDeviceDao = new UserDeviceDao();
/*     */     }
/*     */     
/* 131 */     return this.UserDeviceDao.getOneByPK(id);
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\web\service\ServiceLogService.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */