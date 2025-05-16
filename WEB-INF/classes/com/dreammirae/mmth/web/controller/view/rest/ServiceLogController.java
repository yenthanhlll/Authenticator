/*     */ package WEB-INF.classes.com.dreammirae.mmth.web.controller.view.rest;
/*     */ 
/*     */ import com.dreammirae.mmth.config.Commons;
/*     */ import com.dreammirae.mmth.db.dao.SystemSettingsDao;
/*     */ import com.dreammirae.mmth.db.dao.authentication.BiotpUserServiceDao;
/*     */ import com.dreammirae.mmth.parser.json.GsonUtils;
/*     */ import com.dreammirae.mmth.runtime.service.type.LogActionTypes;
/*     */ import com.dreammirae.mmth.runtime.service.type.LogOperationTypes;
/*     */ import com.dreammirae.mmth.util.StringUtils;
/*     */ import com.dreammirae.mmth.vo.ServiceLogVO;
/*     */ import com.dreammirae.mmth.vo.bean.ICustomLogData;
/*     */ import com.dreammirae.mmth.vo.bean.IRestValidator;
/*     */ import com.dreammirae.mmth.vo.bean.Pagination;
/*     */ import com.dreammirae.mmth.vo.bean.RestResponse;
/*     */ import com.dreammirae.mmth.web.controller.view.rest.ViewController;
/*     */ import com.dreammirae.mmth.web.exception.I18nMessageException;
/*     */ import com.dreammirae.mmth.web.service.ServiceLogService;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.net.URLEncoder;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import javax.servlet.ServletContext;
/*     */ import javax.servlet.ServletOutputStream;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import org.joda.time.DateTime;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ import org.springframework.web.bind.annotation.RequestMethod;
/*     */ import org.springframework.web.bind.annotation.RequestParam;
/*     */ import org.springframework.web.bind.annotation.RestController;
/*     */ 
/*     */ @RestController
/*     */ @RequestMapping(value = {"/web/manager/servicelog/rest"}, method = {RequestMethod.POST})
/*     */ public class ServiceLogController
/*     */ {
/*     */   @Autowired
/*     */   private ServiceLogService service;
/*     */   @Autowired
/*     */   private BiotpUserServiceDao biotpUserService;
/*  46 */   List<LogOperationTypes> options = null;
/*     */ 
/*     */   
/*     */   @RequestMapping({"/init"})
/*     */   public RestResponse init(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
/*  51 */     RestResponse resp = new RestResponse();
/*  52 */     initImp(resp, null, session, request, response);
/*  53 */     return resp;
/*     */   }
/*     */   
/*     */   @RequestMapping({"/branchlog/init"})
/*     */   public RestResponse branchloginit(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
/*  58 */     ServiceLogVO searchParams = new ServiceLogVO();
/*  59 */     this.options = new ArrayList<>();
/*  60 */     this.options.add(LogOperationTypes.REG);
/*  61 */     this.options.add(LogOperationTypes.RECOVERY);
/*  62 */     this.options.add(LogOperationTypes.FORCE_DEREG);
/*     */     
/*  64 */     this.options.add(LogOperationTypes.LOST);
/*  65 */     searchParams.setOptions(this.options);
/*  66 */     RestResponse resp = new RestResponse();
/*  67 */     searchParams.setActionType(LogActionTypes.SUCCESS);
/*  68 */     initImp(resp, searchParams, session, request, response);
/*  69 */     return resp;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RequestMapping({"/pageContents"})
/*     */   public RestResponse pageContents(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum, @RequestParam(value = "startDate", required = false) String startDate, @RequestParam(value = "endDate", required = false) String endDate, ServiceLogVO searchParams) {
/*  78 */     RestResponse resp = new RestResponse();
/*     */     
/*  80 */     Long tsFrom = StringUtils.isEmpty(startDate) ? null : Long.valueOf(Commons.DISPLAY_DATE_FORMATTER.parseDateTime(startDate).getMillis());
/*  81 */     Long tsTo = StringUtils.isEmpty(endDate) ? null : Long.valueOf(Commons.DISPLAY_DATE_FORMATTER.parseDateTime(endDate).plusDays(1).getMillis() - 1L);
/*     */     
/*  83 */     getPagination(resp, pageNum, searchParams, tsFrom, tsTo);
/*     */     
/*  85 */     return resp;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RequestMapping({"/branchlog/pageContents"})
/*     */   public RestResponse branchlogPageContents(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum, @RequestParam(value = "startDate", required = false) String startDate, @RequestParam(value = "endDate", required = false) String endDate, ServiceLogVO searchParams) {
/*  94 */     RestResponse resp = new RestResponse();
/*     */     
/*  96 */     Long tsFrom = StringUtils.isEmpty(startDate) ? null : Long.valueOf(Commons.DISPLAY_DATE_FORMATTER.parseDateTime(startDate).getMillis());
/*  97 */     Long tsTo = StringUtils.isEmpty(endDate) ? null : Long.valueOf(Commons.DISPLAY_DATE_FORMATTER.parseDateTime(endDate).plusDays(1).getMillis() - 1L);
/*     */     
/*  99 */     if (searchParams.getOptions() == null)
/*     */     {
/* 101 */       searchParams.setOptions(this.options);
/*     */     }
/* 103 */     searchParams.setActionType(LogActionTypes.SUCCESS);
/* 104 */     getPagination(resp, pageNum, searchParams, tsFrom, tsTo);
/*     */     
/* 106 */     return resp;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RequestMapping({"/export"})
/*     */   public void csvExport(@RequestParam(value = "startDate", required = false) String startDate, @RequestParam(value = "endDate", required = false) String endDate, @RequestParam(value = "searchParams", required = false) String searchParams, HttpServletRequest request, HttpServletResponse response) {
/* 115 */     RestResponse resp = new RestResponse();
/* 116 */     ServiceLogVO vo = (ServiceLogVO)GsonUtils.gson().fromJson(searchParams, ServiceLogVO.class);
/* 117 */     Long tsFrom = StringUtils.isEmpty(startDate) ? null : Long.valueOf(Commons.DISPLAY_DATE_FORMATTER.parseDateTime(startDate).getMillis());
/* 118 */     Long tsTo = StringUtils.isEmpty(endDate) ? null : Long.valueOf(Commons.DISPLAY_DATE_FORMATTER.parseDateTime(endDate).plusDays(1).getMillis() - 1L);
/*     */     
/* 120 */     List<ServiceLogVO> list = getViewService().getCsvData((IRestValidator)vo, tsFrom, tsTo);
/* 121 */     String[] columnHeader = { "common.username", "common.authMethod", "servicelog.opType", "servicelog.reqType", "servicelog.actionType", "common.regDateTime" };
/* 122 */     String[] columnKey = { "username", "authType", "opType", "reqType", "activeType", "tsReg" };
/* 123 */     File file = getViewService().exportCsv(list, columnHeader, columnKey, "userServiceLogs_" + Commons.displayDate((new Date()).getTime()) + ".xlsx");
/*     */     
/* 125 */     doDownload(request, response, file);
/* 126 */     resp.addData("file", file);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RequestMapping({"/branchlog/export"})
/*     */   public void csvBranchLogExport(@RequestParam(value = "startDate", required = false) String startDate, @RequestParam(value = "endDate", required = false) String endDate, @RequestParam(value = "searchParams", required = false) String searchParams, HttpServletRequest request, HttpServletResponse response) {
/* 136 */     RestResponse resp = new RestResponse();
/* 137 */     ServiceLogVO vo = (ServiceLogVO)GsonUtils.gson().fromJson(searchParams, ServiceLogVO.class);
/* 138 */     Long tsFrom = StringUtils.isEmpty(startDate) ? null : Long.valueOf(Commons.DISPLAY_DATE_FORMATTER.parseDateTime(startDate).getMillis());
/* 139 */     Long tsTo = StringUtils.isEmpty(endDate) ? null : Long.valueOf(Commons.DISPLAY_DATE_FORMATTER.parseDateTime(endDate).plusDays(1).getMillis() - 1L);
/*     */     
/* 141 */     if (vo.getOptions() == null)
/*     */     {
/* 143 */       vo.setOptions(this.options);
/*     */     }
/* 145 */     vo.setActionType(LogActionTypes.SUCCESS);
/* 146 */     List<ServiceLogVO> list = getViewService().getCsvData((IRestValidator)vo, tsFrom, tsTo);
/* 147 */     String[] columnHeader = { "common.username", "common.authMethod", "common.tokenId", "servicelog.opType", "branch.time" };
/* 148 */     String[] columnKey = { "username", "authType", "tokenId", "opType", "tsReg" };
/* 149 */     File file = getViewService().exportCsv(list, columnHeader, columnKey, "branchLogs_" + Commons.displayDate((new Date()).getTime()) + ".xlsx");
/*     */     
/* 151 */     doDownload(request, response, file);
/* 152 */     resp.addData("file", file);
/*     */   }
/*     */ 
/*     */   
/*     */   private void doDownload(HttpServletRequest request, HttpServletResponse response, File file) {
/*     */     ServletOutputStream servletOutputStream;
/* 158 */     int BUFFER_SIZE = 4096;
/*     */     
/* 160 */     ServletContext context = request.getServletContext();
/* 161 */     FileInputStream inputStream = null;
/* 162 */     OutputStream outStream = null;
/*     */     try {
/* 164 */       inputStream = new FileInputStream(file);
/*     */       
/* 166 */       String mimeType = context.getMimeType(file.getAbsolutePath());
/* 167 */       if (mimeType == null)
/*     */       {
/* 169 */         mimeType = "application/octet-stream";
/*     */       }
/*     */ 
/*     */       
/* 173 */       response.setContentType(mimeType);
/* 174 */       response.setContentLength((int)file.length());
/*     */ 
/*     */       
/* 177 */       String fileName = URLEncoder.encode(file.getName(), "utf-8").replaceAll("\\+", "%20");
/* 178 */       String headerKey = "Content-Disposition";
/* 179 */       String headerValue = String.format("attachment; filename=\"%s\"", new Object[] { fileName });
/* 180 */       response.setHeader(headerKey, headerValue);
/*     */ 
/*     */       
/* 183 */       servletOutputStream = response.getOutputStream();
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 188 */       if ("text/csv".equalsIgnoreCase(mimeType)) {
/* 189 */         servletOutputStream.write(239);
/* 190 */         servletOutputStream.write(187);
/* 191 */         servletOutputStream.write(191);
/*     */       } 
/*     */       
/* 194 */       byte[] buffer = new byte[BUFFER_SIZE];
/*     */ 
/*     */ 
/*     */       
/* 198 */       int length = 0;
/* 199 */       while ((length = inputStream.read(buffer)) > 0) {
/* 200 */         servletOutputStream.write(buffer, 0, length);
/*     */       }
/* 202 */       inputStream.close();
/* 203 */       servletOutputStream.close();
/* 204 */     } catch (IOException e) {
/* 205 */       e.printStackTrace();
/*     */     } finally {
/*     */       try {
/* 208 */         if (inputStream != null) {
/* 209 */           inputStream.close();
/*     */         }
/* 211 */         servletOutputStream.flush();
/* 212 */         servletOutputStream.close();
/* 213 */       } catch (Exception e2) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @RequestMapping({"/details"})
/*     */   public RestResponse details(@RequestParam(value = "id", defaultValue = "-1") Long id, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
/* 220 */     RestResponse resp = new RestResponse();
/*     */     
/*     */     try {
/* 223 */       ServiceLogVO vo = (ServiceLogVO)getViewService().getDetails(id);
/* 224 */       vo.setCustomData((ICustomLogData)getViewService().getCustomData(vo));
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 229 */       resp.addData("details", vo);
/* 230 */     } catch (I18nMessageException e) {
/* 231 */       ViewController.setI18nErrorMessage(resp, e);
/*     */     } 
/* 233 */     return resp;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void initImp(RestResponse resp, ServiceLogVO searchParams, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
/* 239 */     Long tsTo = Long.valueOf(System.currentTimeMillis());
/* 240 */     Long tsFrom = Long.valueOf(SystemSettingsDao.getLong("application.since"));
/* 241 */     DateTime dtFrom = (new DateTime(tsFrom)).withTime(0, 0, 0, 0);
/*     */ 
/*     */     
/* 244 */     getPagination(resp, 1, searchParams, Long.valueOf(dtFrom.getMillis()), tsTo);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void getPagination(RestResponse resp, int pageNum, ServiceLogVO params, Long tsFrom, Long tsTo) {
/*     */     try {
/* 250 */       Pagination<ServiceLogVO> pagination = getViewService().getPagination(pageNum, (IRestValidator)params, tsFrom, tsTo);
/* 251 */       resp.addData("pageContents", pagination);
/* 252 */     } catch (I18nMessageException e) {
/* 253 */       ViewController.setI18nErrorMessage(resp, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected ServiceLogService getViewService() {
/* 259 */     if (this.service == null) {
/* 260 */       this.service = new ServiceLogService();
/*     */     }
/* 262 */     return this.service;
/*     */   }
/*     */ 
/*     */   
/*     */   protected BiotpUserServiceDao getBiotpUserServiceDao() {
/* 267 */     if (this.biotpUserService == null) {
/* 268 */       this.biotpUserService = new BiotpUserServiceDao();
/*     */     }
/* 270 */     return this.biotpUserService;
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\web\controller\view\rest\ServiceLogController.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */