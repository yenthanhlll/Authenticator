/*     */ package WEB-INF.classes.com.dreammirae.mmth.db;
/*     */ 
/*     */ import com.dreammirae.gt.otp.keyfile.Body;
/*     */ import com.dreammirae.gt.otp.keyfile.Header;
/*     */ import com.dreammirae.gt.otp.keyfile.TokenFile;
/*     */ import com.dreammirae.gt.otp.keyfile.TokenFileFactoryBiotp;
/*     */ import com.dreammirae.mmth.authentication.otp.TokenDataUtils;
/*     */ import com.dreammirae.mmth.config.Commons;
/*     */ import com.dreammirae.mmth.db.dao.AdministratorDao;
/*     */ import com.dreammirae.mmth.db.dao.AppAgentDao;
/*     */ import com.dreammirae.mmth.db.dao.FidoFacetDao;
/*     */ import com.dreammirae.mmth.db.dao.FidoMetadataDao;
/*     */ import com.dreammirae.mmth.db.dao.HwTokenPolicyDao;
/*     */ import com.dreammirae.mmth.db.dao.TokenDao;
/*     */ import com.dreammirae.mmth.exception.InternalDBException;
/*     */ import com.dreammirae.mmth.fido.FidoUAFUtils;
/*     */ import com.dreammirae.mmth.fido.metadata.MetadataStatement;
/*     */ import com.dreammirae.mmth.misc.SysEnvCommon;
/*     */ import com.dreammirae.mmth.parser.json.GsonUtils;
/*     */ import com.dreammirae.mmth.runtime.audit.type.AlarmLevels;
/*     */ import com.dreammirae.mmth.runtime.audit.type.AuditAlarmTypes;
/*     */ import com.dreammirae.mmth.util.StringUtils;
/*     */ import com.dreammirae.mmth.util.io.ExternalResoureBundleWrapper;
/*     */ import com.dreammirae.mmth.vo.AdministratorVO;
/*     */ import com.dreammirae.mmth.vo.AppAgentVO;
/*     */ import com.dreammirae.mmth.vo.FidoFacetVO;
/*     */ import com.dreammirae.mmth.vo.FidoMetadataVO;
/*     */ import com.dreammirae.mmth.vo.HwTokenPolicyVO;
/*     */ import com.dreammirae.mmth.vo.TokenVO;
/*     */ import com.dreammirae.mmth.vo.types.AdministratorTypes;
/*     */ import com.dreammirae.mmth.vo.types.AgentOsTypes;
/*     */ import java.io.File;
/*     */ import java.io.FileFilter;
/*     */ import java.io.FilenameFilter;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.MissingResourceException;
/*     */ import javax.servlet.ServletContext;
/*     */ import org.apache.commons.io.FileUtils;
/*     */ import org.apache.commons.io.FilenameUtils;
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
/*     */ public final class NewDatabaseInserts
/*     */ {
/*     */   private static final String DIR_BUILT_IN = "built-in";
/*     */   private static final String DIR_ADMIN = "admin";
/*     */   private static final String DIR_APP_AGENT = "appAgents";
/*     */   private static final String DIR_FACETS = "facets";
/*     */   private static final String DIR_METADATA = "metadata";
/*     */   private static final String DIR_TOKEN = "token";
/*     */   private static final String REGEX_NAME_METADATA = "^[0-9A-Fa-f]{4}+\\#+[0-9A-Fa-f]{4}+\\.+(json|JSON)$";
/*  69 */   private static final String[] SUFFIX_NAME_TOKEN = new String[] { "ttk", "TTK" };
/*     */   
/*     */   private static final String SUPPIX_NAME_TTK_PIN = ".pin";
/*     */   
/*     */   private static final String PROP_NAME_ADMIN = "admin";
/*     */   
/*     */   private static final String PROP_NAME_APP_AGENTS = "app_agent";
/*     */   
/*     */   private static final String PROP_NAME_FACETS = "facet";
/*     */   
/*     */   private static final int MAX_LEN_ADMIN = 35;
/*     */   
/*     */   private static final String ADMIN_KEY_FORMAT_USERNAME = "admin%d.username";
/*     */   
/*     */   private static final String ADMIN_KEY_FORMAT_PASSWORD = "admin%d.password";
/*     */   
/*     */   private static final String ADMIN_KEY_FORMAT_AUTHTYPE = "admin%d.adminType";
/*     */   private static final int MAX_LEN_APP_AGENTS = 20;
/*     */   private static final String APP_AGENTS_KEY_FORMAT_ALIAS = "app%d.alias";
/*     */   private static final String APP_AGENTS_KEY_FORMAT_PKGUNIQUE = "app%d.pkgUnique";
/*     */   private static final String APP_AGENTS_KEY_FORMAT_OSTYPE = "app%d.osType";
/*     */   private static final String APP_AGENTS_KEY_FORMAT_FCM_TOKEN = "app%d.fcmAuthorizationKey";
/*     */   private static final int MAX_LEN_FACETS = 20;
/*     */   private static final String FACETS_KEY_FORMAT_FACET_ID = "facet%d.facetId";
/*     */   private static final String FACETS_KEY_FORMAT_ALIAS = "facet%d.alias";
/*     */   
/*     */   public static void doInsertData(ServletContext ctx) {
/*  96 */     String path = ctx.getRealPath(File.separator);
/*     */     
/*  98 */     File baseDir = new File(path, "built-in");
/*     */     
/* 100 */     File[] dirs = baseDir.listFiles(getBaseFileFilter());
/*     */     
/* 102 */     if (dirs == null)
/*     */       return; 
/* 104 */     for (File dir : dirs) {
/*     */       
/* 106 */       if (dir != null) {
/*     */         
/* 108 */         String dirName = dir.getName();
/* 109 */         if ("admin".equals(dirName)) {
/* 110 */           insertAdministrator(dir);
/* 111 */         } else if ("appAgents".equals(dirName)) {
/* 112 */           insertAppAgents(dir);
/* 113 */         } else if ("facets".equals(dirName)) {
/* 114 */           insertFacets(dir);
/* 115 */         } else if ("metadata".equals(dirName)) {
/* 116 */           insertMetadata(dir);
/* 117 */         } else if ("token".equals(dirName)) {
/*     */         
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 124 */     checkAdmin();
/* 125 */     insertHwTokenDefaultPolicy();
/*     */   }
/*     */ 
/*     */   
/*     */   private static void insertHwTokenDefaultPolicy() {
/* 130 */     HwTokenPolicyDao dao = new HwTokenPolicyDao();
/* 131 */     HwTokenPolicyVO vo = dao.getOneByPK("1");
/*     */     
/* 133 */     if (vo == null) {
/* 134 */       vo = new HwTokenPolicyVO();
/* 135 */       vo.setName("Policy");
/* 136 */       vo.setNormAuthCntSkew(Integer.valueOf(1));
/* 137 */       vo.setUserSyncTmSkew(Integer.valueOf(5));
/* 138 */       vo.setAdminSyncTmSkew(Integer.valueOf(120));
/* 139 */       vo.setMaxAuthFailCnt(Integer.valueOf(10));
/* 140 */       vo.setInitAuthTmSkew(Integer.valueOf(30));
/* 141 */       vo.setLongAuthTmSkew(Integer.valueOf(10));
/* 142 */       vo.setLongTerm(Integer.valueOf(60));
/*     */       
/* 144 */       dao.save(vo);
/*     */     } 
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
/*     */   private static void checkAdmin() {
/* 170 */     AdministratorDao dao = new AdministratorDao();
/* 171 */     int count = dao.getCount();
/* 172 */     dao.save(AdministratorVO.createAdministrationVO("admin", SysEnvCommon.encPassword("abcd1234"), AdministratorTypes.SUPER));
/*     */     
/* 174 */     if (count > 0) {
/*     */       return;
/*     */     }
/*     */     
/* 178 */     dao.save(AdministratorVO.createAdministrationVO("manager", SysEnvCommon.encPassword("manager@@"), AdministratorTypes.ADMIN));
/* 179 */     dao.save(AdministratorVO.createAdministrationVO("cs00", SysEnvCommon.encPassword("cs00@@"), AdministratorTypes.CSTEAM));
/* 180 */     dao.save(AdministratorVO.createAdministrationVO("op00", SysEnvCommon.encPassword("op00@@"), AdministratorTypes.OPTEAM));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void insertAdministrator(File dir) {
/*     */     try {
/* 188 */       ExternalResoureBundleWrapper bundle = new ExternalResoureBundleWrapper(dir, "admin");
/*     */       
/* 190 */       AdministratorDao dao = new AdministratorDao();
/*     */       
/* 192 */       for (int i = 0; i < 35; i++) {
/*     */         
/*     */         try {
/* 195 */           String username = bundle.getString(String.format("admin%d.username", new Object[] { Integer.valueOf(i) }));
/* 196 */           String password = bundle.getString(String.format("admin%d.password", new Object[] { Integer.valueOf(i) }));
/* 197 */           String adminType = bundle.getString(String.format("admin%d.adminType", new Object[] { Integer.valueOf(i) }));
/*     */           
/* 199 */           AdministratorTypes type = AdministratorTypes.getAdministratorTypeByName(adminType);
/*     */           
/* 201 */           if (!StringUtils.isEmpty(username) && username.matches("^[a-zA-Z0-9]{1}[a-zA-Z0-9\\!\\@\\#\\?\\-\\_\\.]{3,120}$"))
/*     */           {
/*     */ 
/*     */             
/* 205 */             if (!StringUtils.isEmpty(password) && password.matches("^[a-zA-Z0-9\\!\\@\\#\\?\\-\\_\\.]{5,120}$")) {
/*     */ 
/*     */ 
/*     */               
/* 209 */               AdministratorVO vo = AdministratorVO.createAdministrationVO(username, SysEnvCommon.getPwdEncoder().encode(password), type);
/* 210 */               dao.save(vo);
/*     */               
/* 212 */               if (!AdministratorTypes.DEV.equals(vo.getAdminType()))
/* 213 */                 AuditAlarmTypes.ADMIN.raiseAlarm(null, 514, AlarmLevels.INFORMATION, new Object[] { "BUILT-IN", vo.getUsername() }); 
/*     */             }  } 
/* 215 */         } catch (MissingResourceException ignore) {}
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 220 */       FileUtils.cleanDirectory(dir);
/*     */     }
/* 222 */     catch (Throwable ignore) {
/* 223 */       ignore.printStackTrace();
/*     */     } finally {
/* 225 */       FileUtils.deleteQuietly(dir);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void insertAppAgents(File dir) {
/*     */     try {
/* 235 */       ExternalResoureBundleWrapper bundle = new ExternalResoureBundleWrapper(dir, "app_agent");
/* 236 */       AppAgentDao dao = new AppAgentDao();
/*     */       
/* 238 */       for (int i = 0; i < 20; i++)
/*     */       {
/*     */         try {
/* 241 */           String alias = bundle.getString(String.format("app%d.alias", new Object[] { Integer.valueOf(i) }));
/* 242 */           String pkgUnique = bundle.getString(String.format("app%d.pkgUnique", new Object[] { Integer.valueOf(i) }));
/* 243 */           String osType = bundle.getString(String.format("app%d.osType", new Object[] { Integer.valueOf(i) }));
/* 244 */           String fcmAuthorizationKey = null;
/*     */           try {
/* 246 */             fcmAuthorizationKey = bundle.getString(String.format("app%d.fcmAuthorizationKey", new Object[] { Integer.valueOf(i) }));
/* 247 */           } catch (Exception ignore) {}
/*     */ 
/*     */ 
/*     */           
/* 251 */           AgentOsTypes type = AgentOsTypes.getAgentOsType(osType);
/*     */           
/* 253 */           if (type != null)
/*     */           {
/*     */ 
/*     */             
/* 257 */             if (!StringUtils.isEmpty(alias) && alias.length() <= 40)
/*     */             {
/*     */ 
/*     */               
/* 261 */               if (!StringUtils.isEmpty(pkgUnique) && pkgUnique.matches("^[a-zA-Z][a-zA-Z0-9_]*(\\.[a-zA-Z0-9_]+)+[a-zA-Z0-9_]$")) {
/*     */ 
/*     */ 
/*     */                 
/* 265 */                 AppAgentVO vo = AppAgentVO.createAppAgentVO(pkgUnique, type, alias, fcmAuthorizationKey);
/*     */                 
/* 267 */                 dao.save(vo);
/*     */                 
/* 269 */                 AuditAlarmTypes.APP_AGENT.raiseAlarm(null, 1793, AlarmLevels.INFORMATION, new Object[] { "BUILT-IN", vo.getOsType().name(), vo.getPkgUnique() });
/*     */               }  }  } 
/* 271 */         } catch (MissingResourceException ignore) {}
/*     */       }
/*     */     
/*     */     }
/* 275 */     catch (Throwable ignore) {
/* 276 */       ignore.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void insertFacets(File dir) {
/*     */     try {
/* 286 */       ExternalResoureBundleWrapper bundle = new ExternalResoureBundleWrapper(dir, "facet");
/*     */       
/* 288 */       FidoFacetDao dao = new FidoFacetDao();
/*     */       
/* 290 */       for (int i = 0; i < 20; i++)
/*     */       {
/*     */         try {
/* 293 */           String alias = bundle.getString(String.format("facet%d.alias", new Object[] { Integer.valueOf(i) }));
/* 294 */           String facetId = bundle.getString(String.format("facet%d.facetId", new Object[] { Integer.valueOf(i) }));
/*     */           
/* 296 */           if (!StringUtils.isEmpty(alias) && alias.length() <= 40)
/*     */           {
/*     */ 
/*     */             
/* 300 */             if (!StringUtils.isEmpty(facetId) && FidoUAFUtils.validateFacetId(facetId)) {
/*     */ 
/*     */ 
/*     */               
/* 304 */               FidoFacetVO vo = FidoFacetVO.createAdministrationVO(facetId, alias);
/*     */               
/* 306 */               dao.save(vo);
/*     */               
/* 308 */               AuditAlarmTypes.FACET.raiseAlarm(null, 1537, AlarmLevels.INFORMATION, new Object[] { "BUILT-IN", vo.getFacetId() });
/*     */             }  } 
/* 310 */         } catch (MissingResourceException ignore) {}
/*     */       }
/*     */     
/*     */     }
/* 314 */     catch (Throwable ignore) {
/* 315 */       ignore.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void insertMetadata(File dir) {
/* 324 */     File[] metadataFiles = dir.listFiles(getMetadataFileFilter());
/*     */     
/* 326 */     if (metadataFiles == null || metadataFiles.length < 1) {
/*     */       return;
/*     */     }
/*     */     
/* 330 */     FidoMetadataDao dao = new FidoMetadataDao();
/* 331 */     for (File f : metadataFiles) {
/*     */       try {
/* 333 */         String json = FileUtils.readFileToString(f, Commons.UTF8_CS);
/* 334 */         FidoMetadataVO vo = parseMetadata(json);
/*     */         
/* 336 */         if (vo != null) {
/* 337 */           dao.save(vo);
/* 338 */           AuditAlarmTypes.METADATA.raiseAlarm(null, 1281, AlarmLevels.INFORMATION, new Object[] { "BUILT-IN", vo.getAaid() });
/*     */         }
/*     */       
/* 341 */       } catch (IOException ignore) {
/* 342 */         ignore.printStackTrace();
/* 343 */       } catch (InternalDBException ignore) {
/* 344 */         ignore.printStackTrace();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static FidoMetadataVO parseMetadata(String json) {
/*     */     try {
/* 352 */       MetadataStatement metadata = (MetadataStatement)GsonUtils.gson().fromJson(json, MetadataStatement.class);
/* 353 */       FidoMetadataVO vo = FidoMetadataVO.createAdministrationVO(metadata, metadata.getAaid(), json);
/* 354 */       return vo;
/* 355 */     } catch (Exception ignore) {
/* 356 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void insertToken(File dir) {
/* 365 */     File[] ttkFiles = dir.listFiles(getTokenFileFilter());
/*     */     
/* 367 */     if (ttkFiles == null || ttkFiles.length < 1) {
/*     */       return;
/*     */     }
/*     */     
/* 371 */     for (File f : ttkFiles) {
/*     */       
/*     */       try {
/* 374 */         String pinFileName = dir.getAbsolutePath() + File.separator + FilenameUtils.getBaseName(f.getName()) + ".pin";
/* 375 */         File pinFile = FileUtils.getFile(new String[] { pinFileName });
/*     */         
/* 377 */         if (!pinFile.isFile()) {
/* 378 */           System.out.println("There has no pin file for " + f.getAbsolutePath());
/*     */         }
/*     */         else {
/*     */           
/* 382 */           String pin = FileUtils.readFileToString(pinFile, Commons.UTF8_CS);
/*     */           
/* 384 */           loadAndSaveKeyFile(f, pin);
/*     */         } 
/* 386 */       } catch (IOException ignore) {
/* 387 */         ignore.printStackTrace();
/* 388 */       } catch (Exception ignore) {
/* 389 */         ignore.printStackTrace();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void loadAndSaveKeyFile(File ttk, String pin) {
/*     */     try {
/* 397 */       TokenFile token = TokenFileFactoryBiotp.getTokenFile(ttk.getAbsolutePath(), pin);
/* 398 */       Header header = token.getHeader();
/* 399 */       Body[] body = token.getBody();
/*     */       
/* 401 */       List<TokenVO> tokens = new ArrayList<>(128);
/*     */       
/* 403 */       TokenDao dao = new TokenDao();
/* 404 */       for (Body b : body) {
/* 405 */         String encData = TokenDataUtils.encryptTokenData(b.getSeed());
/* 406 */         tokens.add(TokenVO.createTokenVO(b.getSn(), encData));
/*     */         
/* 408 */         if (tokens.size() >= 128) {
/* 409 */           dao.saveAsBatch(tokens);
/* 410 */           tokens.clear();
/*     */         } 
/*     */       } 
/*     */       
/* 414 */       if (tokens.size() > 0) {
/* 415 */         dao.saveAsBatch(tokens);
/*     */       }
/*     */       
/* 418 */       tokens.clear();
/* 419 */       AuditAlarmTypes.TOKEN.raiseAlarm(null, 1025, AlarmLevels.INFORMATION, new Object[] { "BUILT-IN", header.getStartSn(), header.getEndSn() });
/* 420 */     } catch (Throwable e) {
/* 421 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   private static FilenameFilter getTokenFileFilter() {
/* 426 */     return (FilenameFilter)new Object();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static FilenameFilter getMetadataFileFilter() {
/* 436 */     return (FilenameFilter)new Object();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static FileFilter getBaseFileFilter() {
/* 446 */     return (FileFilter)new Object();
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\db\NewDatabaseInserts.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */