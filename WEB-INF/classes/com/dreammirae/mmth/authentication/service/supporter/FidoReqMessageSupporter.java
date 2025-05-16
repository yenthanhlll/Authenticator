/*     */ package WEB-INF.classes.com.dreammirae.mmth.authentication.service.supporter;
/*     */ 
/*     */ import com.dreammirae.mmth.authentication.service.supporter.FidoAppIdPolicySupporter;
/*     */ import com.dreammirae.mmth.authentication.service.supporter.FidoPolicySupporter;
/*     */ import com.dreammirae.mmth.config.Commons;
/*     */ import com.dreammirae.mmth.db.dao.FidoMetadataDao;
/*     */ import com.dreammirae.mmth.db.dao.SystemSettingsDao;
/*     */ import com.dreammirae.mmth.fido.Operation;
/*     */ import com.dreammirae.mmth.fido.StatusCodes;
/*     */ import com.dreammirae.mmth.fido.exception.FidoUafStatusCodeException;
/*     */ import com.dreammirae.mmth.fido.handler.bean.IServerDataLocator;
/*     */ import com.dreammirae.mmth.fido.handler.bean.SimpleServerDataLocator;
/*     */ import com.dreammirae.mmth.fido.handler.supporter.AppIDSupporter;
/*     */ import com.dreammirae.mmth.fido.handler.supporter.PolicySupporter;
/*     */ import com.dreammirae.mmth.fido.handler.supporter.ReqMessageSupporter;
/*     */ import com.dreammirae.mmth.fido.metadata.DisplayPNGCharacteristicsDescriptor;
/*     */ import com.dreammirae.mmth.fido.metadata.MetadataStatement;
/*     */ import com.dreammirae.mmth.fido.registry.UserVerificationMethods;
/*     */ import com.dreammirae.mmth.fido.transport.context.RpContext;
/*     */ import com.dreammirae.mmth.fido.uaf.Extension;
/*     */ import com.dreammirae.mmth.fido.uaf.Version;
/*     */ import com.dreammirae.mmth.misc.Base64Utils;
/*     */ import com.dreammirae.mmth.misc.SysEnvCommon;
/*     */ import com.dreammirae.mmth.util.StringUtils;
/*     */ import com.dreammirae.mmth.util.img.PngUtils;
/*     */ import com.dreammirae.mmth.util.io.Closer;
/*     */ import com.dreammirae.mmth.vo.FidoRegistrationVO;
/*     */ import freemarker.template.Template;
/*     */ import java.io.StringWriter;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ 
/*     */ public abstract class FidoReqMessageSupporter
/*     */   implements ReqMessageSupporter<FidoRegistrationVO>
/*     */ {
/*     */   public IServerDataLocator returnServerData(Operation op, String username, boolean isTransaction, RpContext rpCtx) throws FidoUafStatusCodeException {
/*  37 */     SimpleServerDataLocator serverData = new SimpleServerDataLocator();
/*  38 */     serverData.setOp(op);
/*  39 */     serverData.setUsername(username);
/*     */     
/*  41 */     serverData.setChallenge(Base64Utils.encodeUrl(SysEnvCommon.generateChallenge()));
/*  42 */     serverData.setTransaction(isTransaction);
/*     */     
/*  44 */     if (rpCtx.containsKey("externalRequestExpired")) {
/*  45 */       serverData.setLifeTimeTs(((Long)rpCtx.get("externalRequestExpired", Long.class)).longValue());
/*     */     } else {
/*  47 */       serverData.setLifeTimeTs(System.currentTimeMillis() + SystemSettingsDao.getPeriods("integrate.authLifetimePeriodType", "integrate.authLifetimePeriods"));
/*     */     } 
/*     */ 
/*     */     
/*  51 */     return (IServerDataLocator)serverData;
/*     */   }
/*     */ 
/*     */   
/*     */   public Version returnCurrentVersion() {
/*  56 */     return Version.getCurrentVersion();
/*     */   }
/*     */ 
/*     */   
/*     */   public AppIDSupporter returnAppIDSupporter(RpContext rpCtx) {
/*  61 */     return (AppIDSupporter)new FidoAppIdPolicySupporter();
/*     */   }
/*     */ 
/*     */   
/*     */   public PolicySupporter returnPolicySupporter(RpContext rpCtx) {
/*  66 */     String acceptedAAID = (String)rpCtx.get("AAID", String.class);
/*     */     
/*  68 */     UserVerificationMethods mehtod = null;
/*     */     
/*  70 */     if (rpCtx.containsKey("userVerification")) {
/*     */       
/*     */       try {
/*  73 */         long userVerification = ((Long)rpCtx.get("userVerification", Long.class)).longValue();
/*  74 */         mehtod = UserVerificationMethods.getUserVerificationMethods(userVerification);
/*  75 */       } catch (Exception ignore) {
/*     */         
/*  77 */         ignore.printStackTrace();
/*     */       } 
/*     */     }
/*  80 */     return (PolicySupporter)new FidoPolicySupporter(acceptedAAID, mehtod);
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] returnImageData(String username, DisplayPNGCharacteristicsDescriptor descriptor, String transaction, RpContext rpCtx) throws FidoUafStatusCodeException {
/*  85 */     Map<String, Object> map = new HashMap<>(2);
/*  86 */     map.put("contents", transaction);
/*  87 */     map.put("width", descriptor.getWidth());
/*     */ 
/*     */     
/*  90 */     StringWriter writer = new StringWriter();
/*     */     try {
/*  92 */       Template template = Commons.ctx.getFreemarkerConfig().getTemplate("fido/tc_adaptor.ftl");
/*  93 */       template.process(map, writer);
/*  94 */       String html = writer.toString();
/*  95 */       return PngUtils.createImgToBytes(html, descriptor.getWidth().intValue(), descriptor.getHeight().intValue());
/*  96 */     } catch (Exception e) {
/*  97 */       Closer.close(writer);
/*  98 */       return new byte[0];
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] returnTextData(String username, String transaction, RpContext rpCtx) throws FidoUafStatusCodeException {
/* 104 */     if (StringUtils.isEmpty(transaction)) {
/* 105 */       return new byte[0];
/*     */     }
/*     */     
/* 108 */     return SysEnvCommon.getUtf8Bytes(transaction);
/*     */   }
/*     */ 
/*     */   
/*     */   public MetadataStatement returnMetadataStatement(String aaid, RpContext rpCtx) throws FidoUafStatusCodeException {
/* 113 */     MetadataStatement ms = FidoMetadataDao.getMetadataStatmement(aaid);
/*     */     
/* 115 */     if (ms == null) {
/* 116 */       throw new FidoUafStatusCodeException(StatusCodes.CODE_1480);
/*     */     }
/*     */     
/* 119 */     return ms;
/*     */   }
/*     */ 
/*     */   
/*     */   public Extension[] returnOperationHeaderExtentions(Operation op, String username, RpContext rpCtx) throws FidoUafStatusCodeException {
/* 124 */     return null;
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\authentication\service\supporter\FidoReqMessageSupporter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */