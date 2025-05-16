/*     */ package WEB-INF.classes.com.dreammirae.mmth.authentication.service;
/*     */ 
/*     */ import com.dreammirae.mmth.authentication.AuthMethodTypes;
/*     */ import com.dreammirae.mmth.authentication.ProductType;
/*     */ import com.dreammirae.mmth.authentication.ReturnCodes;
/*     */ import com.dreammirae.mmth.authentication.bean.AuthenticationRequestLocator;
/*     */ import com.dreammirae.mmth.authentication.bean.AuthenticationResponseLocator;
/*     */ import com.dreammirae.mmth.authentication.issuecode.IssueCodeResult;
/*     */ import com.dreammirae.mmth.authentication.issuecode.IssueCodeStatusCode;
/*     */ import com.dreammirae.mmth.authentication.issuecode.IssueCodeUtil;
/*     */ import com.dreammirae.mmth.db.dao.IssueCodeDao;
/*     */ import com.dreammirae.mmth.db.dao.SystemSettingsDao;
/*     */ import com.dreammirae.mmth.db.dao.authentication.BiotpUserServiceDao;
/*     */ import com.dreammirae.mmth.db.dao.authentication.IUserServiceDao;
/*     */ import com.dreammirae.mmth.db.dao.authentication.UserServiceDao;
/*     */ import com.dreammirae.mmth.exception.ReturnCodeException;
/*     */ import com.dreammirae.mmth.external.bean.GlobalWibeeRequestParam;
/*     */ import com.dreammirae.mmth.external.bean.IssueCodeApiData;
/*     */ import com.dreammirae.mmth.external.bean.WebApiRequestParam;
/*     */ import com.dreammirae.mmth.external.bean.WebApiResponse;
/*     */ import com.dreammirae.mmth.hwotp.service.HwOtpAuthenticationService;
/*     */ import com.dreammirae.mmth.misc.Base64Utils;
/*     */ import com.dreammirae.mmth.runtime.service.type.LogActionTypes;
/*     */ import com.dreammirae.mmth.runtime.service.type.LogOperationTypes;
/*     */ import com.dreammirae.mmth.runtime.service.type.OpRequstTypes;
/*     */ import com.dreammirae.mmth.util.bean.HashMapWrapper;
/*     */ import com.dreammirae.mmth.vo.DeviceAppAgentVO;
/*     */ import com.dreammirae.mmth.vo.IssueCodeDataVO;
/*     */ import com.dreammirae.mmth.vo.UserAnnotationVO;
/*     */ import com.dreammirae.mmth.vo.UserVO;
/*     */ import com.dreammirae.mmth.vo.bean.ICustomLogData;
/*     */ import com.dreammirae.mmth.vo.types.DeviceAppAgentStatus;
/*     */ import com.dreammirae.mmth.vo.types.DeviceTypes;
/*     */ import com.dreammirae.mmth.vo.types.UserStatus;
/*     */ import com.fasterxml.jackson.core.JsonParseException;
/*     */ import com.fasterxml.jackson.databind.JsonMappingException;
/*     */ import com.fasterxml.jackson.databind.ObjectMapper;
/*     */ import java.io.IOException;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.commons.lang3.StringUtils;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Service;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Service("issueCodeService")
/*     */ public class IssueCodeService
/*     */ {
/*     */   @Autowired
/*     */   private IssueCodeDao issueCodeDao;
/*     */   @Autowired
/*     */   private HwOtpAuthenticationService hwOtpService;
/*     */   @Autowired
/*     */   private BiotpUserServiceDao biotpUserService;
/*  60 */   private final String DEFAULT_ACCOUNT_NM = "-";
/*     */   
/*     */   public void generateIssueCode(AuthMethodTypes authType, AuthenticationResponseLocator result, AuthenticationRequestLocator params) throws ReturnCodeException {
/*  63 */     if (!authType.enabledIssueCode()) {
/*  64 */       throw new ReturnCodeException(ReturnCodes.FORBIDDEN_REQUEST_ISSUE_CODE, "IssueCode is not required in this auth type [" + authType.name() + "].");
/*     */     }
/*     */     
/*     */     try {
/*  68 */       validateBeforeGenerate(authType, params.getUsername(), null);
/*  69 */       IssueCodeDataVO issueCodeDataVo = generateIssueCode(authType, params.getUsername());
/*  70 */       result.setIssueCode(IssueCodeUtil.getIssueCode(issueCodeDataVo.getIssueCodeData()));
/*  71 */       result.setReturnCode(ReturnCodes.OK);
/*  72 */       result.setIssueCodeExpired(Long.valueOf(issueCodeDataVo.getTsLifetime()));
/*     */       
/*  74 */       LogOperationTypes.ISSUE_CODE.addServiceLog(authType, params.getUsername(), OpRequstTypes.HTTP_API, LogActionTypes.SUCCESS, ReturnCodes.OK, null);
/*  75 */     } catch (ReturnCodeException e) {
/*  76 */       result.setReturnCode(e.getReturnCode());
/*  77 */       if (!StringUtils.isEmpty(params.getUsername())) {
/*  78 */         LogOperationTypes.ISSUE_CODE.addServiceLog(authType, params.getUsername(), OpRequstTypes.HTTP_API, LogActionTypes.FAIL, e.getReturnCode(), e.getMessage());
/*     */       }
/*  80 */       throw e;
/*  81 */     } catch (Exception e) {
/*  82 */       result.setReturnCode(ReturnCodes.INTERNAL_SERVER_ERROR);
/*  83 */       if (!StringUtils.isEmpty(params.getUsername()))
/*  84 */         LogOperationTypes.ISSUE_CODE.addServiceLog(authType, params.getUsername(), OpRequstTypes.HTTP_API, LogActionTypes.FAIL, ReturnCodes.INTERNAL_SERVER_ERROR, e.getMessage()); 
/*  85 */       throw e;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void generateIssueCode(AuthMethodTypes authType, WebApiResponse result, WebApiRequestParam params) throws ReturnCodeException {
/*  91 */     if (!authType.enabledIssueCode()) {
/*  92 */       throw new ReturnCodeException(ReturnCodes.FORBIDDEN_REQUEST_ISSUE_CODE, "IssueCode is not required in this auth type [" + authType.name() + "].");
/*     */     }
/*     */     
/*  95 */     UserAnnotationVO userAnnotation = null;
/*     */     
/*  97 */     if (params instanceof GlobalWibeeRequestParam) {
/*     */       
/*  99 */       GlobalWibeeRequestParam wibeeParam = (GlobalWibeeRequestParam)params;
/*     */       
/* 101 */       if (!StringUtils.isEmpty(wibeeParam.getCountryCode())) {
/* 102 */         userAnnotation = new UserAnnotationVO();
/* 103 */         userAnnotation.setCountryCode(wibeeParam.getCountryCode());
/* 104 */         userAnnotation.setUsername(params.getUserName());
/* 105 */         userAnnotation.setCustomerCode("");
/* 106 */         userAnnotation.setDisplayName("");
/* 107 */         userAnnotation.setExtUnique("");
/*     */       } 
/*     */     } 
/*     */     
/* 111 */     generateIssueCode(authType, result, params, userAnnotation, LogOperationTypes.ISSUE_CODE, params.createCustomLogData());
/*     */   }
/*     */ 
/*     */   
/*     */   public void generateReIssueCode(AuthMethodTypes authType, WebApiResponse result, WebApiRequestParam params) throws ReturnCodeException {
/* 116 */     if (!authType.enabledIssueCode()) {
/* 117 */       throw new ReturnCodeException(ReturnCodes.FORBIDDEN_REQUEST_ISSUE_CODE, "IssueCode is not required in this auth type [" + authType.name() + "].");
/*     */     }
/*     */ 
/*     */     
/* 121 */     UserAnnotationVO userAnnotation = null;
/*     */     
/* 123 */     if (params instanceof GlobalWibeeRequestParam) {
/*     */       
/* 125 */       GlobalWibeeRequestParam wibeeParam = (GlobalWibeeRequestParam)params;
/*     */       
/* 127 */       if (!StringUtils.isEmpty(wibeeParam.getCountryCode())) {
/* 128 */         userAnnotation = new UserAnnotationVO();
/* 129 */         userAnnotation.setCountryCode(wibeeParam.getCountryCode());
/* 130 */         userAnnotation.setUsername(params.getUserName());
/* 131 */         userAnnotation.setCustomerCode("");
/* 132 */         userAnnotation.setDisplayName("");
/* 133 */         userAnnotation.setExtUnique("");
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 138 */     generateIssueCode(authType, result, params, userAnnotation, LogOperationTypes.ISSUE_CODE_ONLINE, null);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void generateIssueCode(AuthMethodTypes authType, WebApiResponse result, WebApiRequestParam params, UserAnnotationVO userAnnotation, LogOperationTypes opType, ICustomLogData customData) throws ReturnCodeException {
/* 143 */     if (!authType.enabledIssueCode()) {
/* 144 */       throw new ReturnCodeException(ReturnCodes.FORBIDDEN_REQUEST_ISSUE_CODE, "IssueCode is not required in this auth type [" + authType.name() + "].");
/*     */     }
/*     */     
/*     */     try {
/* 148 */       validateBeforeGenerate(authType, params.getUserName(), userAnnotation);
/* 149 */       IssueCodeDataVO issueCodeDataVo = generateIssueCode(authType, params.getUserName());
/* 150 */       result.setIssueCode(IssueCodeUtil.getIssueCode(issueCodeDataVo.getIssueCodeData()));
/* 151 */       result.setReturnCode(ReturnCodes.OK);
/* 152 */       result.setIssueCodeExpired(Long.valueOf(issueCodeDataVo.getTsLifetime()));
/* 153 */       opType.addServiceLog(authType, params.getUserName(), OpRequstTypes.HTTP_API, LogActionTypes.SUCCESS, ReturnCodes.OK, null);
/* 154 */     } catch (ReturnCodeException e) {
/* 155 */       result.setReturnCode(e.getReturnCode());
/* 156 */       if (!StringUtils.isEmpty(params.getUserName()))
/* 157 */         opType.addServiceLog(authType, params.getUserName(), OpRequstTypes.HTTP_API, LogActionTypes.FAIL, e.getReturnCode(), e.getMessage()); 
/* 158 */       throw e;
/* 159 */     } catch (Exception e) {
/* 160 */       result.setReturnCode(ReturnCodes.INTERNAL_SERVER_ERROR);
/* 161 */       if (!StringUtils.isEmpty(params.getUserName()))
/* 162 */         opType.addServiceLog(authType, params.getUserName(), OpRequstTypes.HTTP_API, LogActionTypes.FAIL, ReturnCodes.INTERNAL_SERVER_ERROR, e.getMessage()); 
/* 163 */       throw e;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public IssueCodeApiData getIssueCodeData(UserVO user, AuthMethodTypes authType) {
/* 169 */     IssueCodeApiData data = new IssueCodeApiData();
/*     */     
/* 171 */     IssueCodeDataVO vo = getIssueCodeDao().getOneByUnique(user.getUsername(), authType);
/*     */     
/* 173 */     if (vo == null) {
/* 174 */       return null;
/*     */     }
/*     */     
/* 177 */     int failCnt = IssueCodeUtil.getFailCnt(vo.getIssueCodeData());
/* 178 */     int maxFailCnt = SystemSettingsDao.getInt("integrate.issueCodeMaxFailCount");
/*     */     
/* 180 */     data.setIssueCode(IssueCodeUtil.getIssueCode(vo.getIssueCodeData()));
/* 181 */     data.setIssueCodeFailCnt(Integer.valueOf(failCnt));
/* 182 */     data.setIssueCodeExpired(Long.valueOf(vo.getTsLifetime()));
/* 183 */     data.setIssueCodeExpiredYn(Boolean.valueOf((vo.getTsLifetime() < System.currentTimeMillis())));
/* 184 */     data.setIssueCodeFailExceedYn(Boolean.valueOf((maxFailCnt <= failCnt)));
/* 185 */     data.setIssueCodeRegTs(Long.valueOf(IssueCodeUtil.getGenTime(vo.getIssueCodeData())));
/*     */     
/* 187 */     return data;
/*     */   }
/*     */   
/*     */   private IssueCodeDataVO generateIssueCode(AuthMethodTypes authType, String username) throws ReturnCodeException {
/* 191 */     String issueCodeData = IssueCodeUtil.genIssueCodeData();
/* 192 */     IssueCodeDataVO vo = new IssueCodeDataVO();
/* 193 */     vo.setUsername(username);
/* 194 */     vo.setIssueCodeData(issueCodeData);
/* 195 */     vo.setAuthType(authType);
/* 196 */     vo.setTsLifetime(System.currentTimeMillis() + getPeriods());
/*     */     
/* 198 */     getIssueCodeDao().saveNewIssueCode(vo);
/*     */     
/* 200 */     return vo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void verifyIssueCode(HashMapWrapper verifyResult, AuthMethodTypes authType, String username, String issueCode, String deviceId, DeviceTypes deviceType, String pkgName) throws ReturnCodeException {
/* 207 */     if (!authType.enabledIssueCode()) {
/*     */       return;
/*     */     }
/*     */     
/* 211 */     if (!issueCode.matches("^[0-9]{5}$")) {
/* 212 */       throw new ReturnCodeException(ReturnCodes.INVALID_PARAMS, "Issue code must be 5-digit number.");
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 217 */       IssueCodeDataVO vo = getIssueCodeDao().getOneByUnique(username, authType);
/*     */       
/* 219 */       if (vo == null) {
/* 220 */         throw new ReturnCodeException(ReturnCodes.DISCARD_ISSUE_CODE, "Request a valid generate issueCode prior to verifying code.");
/*     */       }
/*     */       
/* 223 */       int maxFailCnt = SystemSettingsDao.getInt("integrate.issueCodeMaxFailCount");
/* 224 */       IssueCodeResult result = IssueCodeUtil.isValid(vo.getIssueCodeData(), issueCode, getPeriods(), maxFailCnt);
/*     */       
/* 226 */       if (IssueCodeStatusCode.OK.equals(result.getStatus())) {
/* 227 */         LogOperationTypes.ISSUE_CODE.addServiceLog(authType, username, OpRequstTypes.FIDO_UAF_REQ, LogActionTypes.SUCCESS, ReturnCodes.OK, deviceId, deviceType, pkgName, null, null, "");
/*     */         
/*     */         return;
/*     */       } 
/* 231 */       if (!StringUtils.isEmpty(result.getNewData())) {
/* 232 */         vo.setIssueCodeData(result.getNewData());
/* 233 */         getIssueCodeDao().save(vo);
/*     */       } 
/*     */       
/* 236 */       if (result.getFailCnt() > 0) {
/* 237 */         verifyResult.set("issueCodeTotalAttempts", Integer.valueOf(maxFailCnt));
/* 238 */         verifyResult.set("issueCodeNumOfFailures", Integer.valueOf(result.getFailCnt()));
/*     */       } 
/*     */       
/* 241 */       if (IssueCodeStatusCode.FAIL.equals(result.getStatus())) {
/* 242 */         throw new ReturnCodeException(ReturnCodes.VERIFY_ISSUE_CODE_FAILED, "IssueCode [" + issueCode + "] is not valid.");
/*     */       }
/* 244 */       throw new ReturnCodeException(ReturnCodes.DISCARD_ISSUE_CODE, "IssueCode [" + issueCode + "] is not valid and the issueCode has been discard.");
/*     */     
/*     */     }
/* 247 */     catch (ReturnCodeException e) {
/* 248 */       throw e;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void validateBeforeGenerate(AuthMethodTypes authType, String username, UserAnnotationVO userAnnotation) throws ReturnCodeException {
/* 254 */     UserVO.validateUsername(username);
/*     */     
/* 256 */     IUserServiceDao dao = getUserServiceDao(authType);
/*     */     
/* 258 */     UserVO user = dao.returnUser(username);
/*     */ 
/*     */     
/* 261 */     if (user == null) {
/*     */       
/* 263 */       user = UserVO.createNewInstance(username, ProductType.NONE.getCode(), "-");
/*     */       
/* 265 */       if (userAnnotation != null) {
/* 266 */         user.setAnnotation(userAnnotation);
/*     */       }
/*     */       
/* 269 */       dao.saveUser(user);
/*     */       return;
/*     */     } 
/* 272 */     String accountName = user.getAccountName();
/*     */     
/* 274 */     if (StringUtils.isBlank(accountName)) {
/* 275 */       user.setAccountName("-");
/*     */     }
/* 277 */     if (userAnnotation != null) {
/* 278 */       user.setAnnotation(userAnnotation);
/* 279 */       dao.saveUser(user);
/*     */     } 
/*     */ 
/*     */     
/* 283 */     if (!user.getProductType().equals(ProductType.NONE)) {
/*     */       
/* 285 */       if (user.getProductType() == ProductType.HWOTP) {
/* 286 */         String returnCode = this.hwOtpService.disuseOtp(Base64Utils.encode(user.getUsername()), null, 0L, null, null);
/* 287 */         ObjectMapper map = new ObjectMapper();
/*     */         
/*     */         try {
/* 290 */           Map<String, String> returnCodeMap = (Map<String, String>)map.readValue(returnCode, Map.class);
/* 291 */           String code = returnCodeMap.get("returnCode");
/*     */           
/* 293 */           if (!StringUtils.equals(code, ReturnCodes.OK.getCode())) {
/* 294 */             throw new ReturnCodeException(ReturnCodes.getReturnCode(returnCode));
/*     */           }
/* 296 */           user.setStatus(UserStatus.DISCARD);
/* 297 */         } catch (JsonParseException e) {
/* 298 */           e.printStackTrace();
/* 299 */         } catch (JsonMappingException e) {
/* 300 */           e.printStackTrace();
/* 301 */         } catch (IOException e) {
/* 302 */           e.printStackTrace();
/*     */         } 
/* 304 */       } else if (user.getProductType() == ProductType.MATRIX) {
/* 305 */         user.setStatus(UserStatus.DISCARD);
/* 306 */         this.biotpUserService.updateUserAuthType(user);
/* 307 */         LogOperationTypes.DEREG.addServiceLog(AuthMethodTypes.MATRIX, user.getUsername(), OpRequstTypes.HTTP_API, LogActionTypes.SUCCESS, ReturnCodes.OK, null, null, null, null, null);
/*     */       } 
/*     */       
/* 310 */       if (user.getStatus().equals(UserStatus.AVAILABLE)) {
/* 311 */         throw new ReturnCodeException(ReturnCodes.EXIST_ALREADY, "The user [" + user.getUsername() + "] is allowed only one device. User must deregister previous registrations.  before registering any device.");
/*     */       }
/*     */     } 
/* 314 */     if (user.getDisabled().toBoolean()) {
/* 315 */       throw new ReturnCodeException(ReturnCodes.USER_FORBIDDEN, "The user [" + username + "] is locked.");
/*     */     }
/*     */     
/* 318 */     if (!UserStatus.AVAILABLE.equals(user.getStatus())) {
/* 319 */       user.setStatus(UserStatus.NOT_AVAILABLE);
/* 320 */       if (userAnnotation != null) {
/* 321 */         user.setAnnotation(userAnnotation);
/*     */       }
/* 323 */       dao.saveUser(user);
/*     */       
/*     */       return;
/*     */     } 
/* 327 */     List<DeviceAppAgentVO> deviceApps = dao.returnDeviceApps(user);
/*     */ 
/*     */     
/* 330 */     if (deviceApps.isEmpty()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 335 */     boolean hasActive = false;
/* 336 */     for (DeviceAppAgentVO agentVO : deviceApps) {
/*     */       
/* 338 */       if (authType.equals(agentVO.getAuthType()) && 
/* 339 */         DeviceAppAgentStatus.AVAIABLE.equals(agentVO.getStatus())) {
/* 340 */         hasActive = true;
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 346 */     if (hasActive) {
/* 347 */       authType.validateMultiDevice(user);
/*     */     }
/*     */   }
/*     */   
/*     */   private long getPeriods() {
/* 352 */     return SystemSettingsDao.getPeriods("integrate.issueCodeLifetimePeriodType", "integrate.issueCodeLifetimePeriods");
/*     */   }
/*     */ 
/*     */   
/*     */   private IUserServiceDao getUserServiceDao(AuthMethodTypes authType) {
/* 357 */     return (IUserServiceDao)UserServiceDao.getInstance(authType);
/*     */   }
/*     */   
/*     */   private IssueCodeDao getIssueCodeDao() {
/* 361 */     if (this.issueCodeDao == null) {
/* 362 */       this.issueCodeDao = new IssueCodeDao();
/*     */     }
/* 364 */     return this.issueCodeDao;
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\authentication\service\IssueCodeService.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */