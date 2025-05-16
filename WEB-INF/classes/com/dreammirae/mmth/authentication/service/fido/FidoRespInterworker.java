/*     */ package WEB-INF.classes.com.dreammirae.mmth.authentication.service.fido;
/*     */ 
/*     */ import com.dreammirae.mmth.authentication.ReturnCodes;
/*     */ import com.dreammirae.mmth.authentication.service.IExteranlRespInterworker;
/*     */ import com.dreammirae.mmth.config.Commons;
/*     */ import com.dreammirae.mmth.db.dao.ExternalServiceItemDao;
/*     */ import com.dreammirae.mmth.db.dao.SystemSettingsDao;
/*     */ import com.dreammirae.mmth.external.bean.ExtRequestStatus;
/*     */ import com.dreammirae.mmth.misc.Base64Utils;
/*     */ import com.dreammirae.mmth.runtime.audit.type.AlarmLevels;
/*     */ import com.dreammirae.mmth.runtime.audit.type.AuditAlarmTypes;
/*     */ import com.dreammirae.mmth.vo.ExternalServiceItemVO;
/*     */ import com.dreammirae.mmth.vo.FidoRegistrationVO;
/*     */ import com.dreammirae.mmth.vo.ServerChallengeVO;
/*     */ import com.dreammirae.mmth.vo.UserVO;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import org.apache.http.client.utils.URIBuilder;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FidoRespInterworker
/*     */   implements IExteranlRespInterworker
/*     */ {
/*  27 */   protected static final Logger LOG = LoggerFactory.getLogger(com.dreammirae.mmth.authentication.service.fido.FidoRespInterworker.class);
/*     */ 
/*     */ 
/*     */   
/*     */   private static final String URI_FIDO_REG = "/webapi/fido/notify-regi";
/*     */ 
/*     */   
/*     */   private static final String URI_FIDO_AUTH = "/webapi/fido/notify-auth";
/*     */ 
/*     */   
/*     */   private static final String URI_FIDO_DEREG = "/webapi/fido/notify-deregi";
/*     */ 
/*     */ 
/*     */   
/*     */   public void respRegInterworker(UserVO user, ServerChallengeVO challenge, FidoRegistrationVO fidoRegi) {
/*     */     try {
/*  43 */       URI uri = (new URIBuilder(getServiceAddress() + "/webapi/fido/notify-regi")).addParameter("userno", Base64Utils.encodeUrl(user.getUsername())).addParameter("aaid", Base64Utils.encodeUrl(fidoRegi.getAaid())).build();
/*  44 */       sendResultToGPTWR(uri);
/*     */       
/*  46 */       AuditAlarmTypes.EXTERNAL_API.raiseAlarm(null, 2565, AlarmLevels.INFORMATION, new Object[] { user.getUsername() });
/*  47 */     } catch (URISyntaxException e) {
/*     */       
/*  49 */       LOG.warn("Failed to send GPTWR...", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void respAuthInterworker(UserVO user, ServerChallengeVO challenge, FidoRegistrationVO fidoRegi) {
/*  57 */     ExternalServiceItemDao dao = new ExternalServiceItemDao();
/*  58 */     ExternalServiceItemVO vo = dao.getOneByUser(user);
/*  59 */     vo.setStatus(ExtRequestStatus.DONE);
/*     */ 
/*     */     
/*  62 */     dao.save(vo);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  69 */       URI uri = (new URIBuilder(getServiceAddress() + "/webapi/fido/notify-auth")).addParameter("userno", Base64Utils.encodeUrl(user.getUsername())).addParameter("tid", Base64Utils.encodeUrl(vo.getExternalIdentifier())).addParameter("returnCode", Base64Utils.encodeUrl(ReturnCodes.OK.getCode())).build();
/*     */       
/*  71 */       sendResultToGPTWR(uri);
/*  72 */     } catch (URISyntaxException e) {
/*     */       
/*  74 */       LOG.warn("Failed to send GPTWR...", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void respAuthFailInterworker(UserVO user, ServerChallengeVO challenge, FidoRegistrationVO fidoRegi) {
/*  83 */     ExternalServiceItemDao dao = new ExternalServiceItemDao();
/*  84 */     ExternalServiceItemVO vo = dao.getOneByUser(user);
/*  85 */     vo.setStatus(ExtRequestStatus.FAIL);
/*  86 */     dao.save(vo);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  94 */       URI uri = (new URIBuilder(getServiceAddress() + "/webapi/fido/notify-auth")).addParameter("userno", Base64Utils.encodeUrl(user.getUsername())).addParameter("tid", Base64Utils.encodeUrl(vo.getExternalIdentifier())).addParameter("returnCode", Base64Utils.encodeUrl(ReturnCodes.AUTH_FAILED.getCode())).build();
/*     */       
/*  96 */       sendResultToGPTWR(uri);
/*  97 */     } catch (URISyntaxException e) {
/*     */       
/*  99 */       LOG.warn("Failed to send GPTWR...", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void respUserCancleInterworker(UserVO user, ServerChallengeVO challenge, FidoRegistrationVO fidoRegi) {
/* 108 */     ExternalServiceItemDao dao = new ExternalServiceItemDao();
/* 109 */     ExternalServiceItemVO vo = dao.getOneByUser(user);
/* 110 */     vo.setStatus(ExtRequestStatus.FAIL);
/* 111 */     dao.save(vo);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 119 */       URI uri = (new URIBuilder(getServiceAddress() + "/webapi/fido/notify-auth")).addParameter("userno", Base64Utils.encodeUrl(user.getUsername())).addParameter("tid", Base64Utils.encodeUrl(vo.getExternalIdentifier())).addParameter("returnCode", Base64Utils.encodeUrl(ReturnCodes.AUTH_FAILED.getCode())).build();
/*     */       
/* 121 */       sendResultToGPTWR(uri);
/* 122 */     } catch (URISyntaxException e) {
/*     */       
/* 124 */       LOG.warn("Failed to send GPTWR...", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void respDeregNotifiedInterworker(String userName) {
/*     */     try {
/* 134 */       URI uri = (new URIBuilder(getServiceAddress() + "/webapi/fido/notify-deregi")).addParameter("userno", Base64Utils.encodeUrl(userName)).build();
/*     */       
/* 136 */       sendResultToGPTWR(uri);
/*     */       
/* 138 */       AuditAlarmTypes.EXTERNAL_API.raiseAlarm(null, 2566, AlarmLevels.INFORMATION, new Object[] { userName });
/*     */     }
/* 140 */     catch (URISyntaxException e) {
/*     */       
/* 142 */       LOG.warn("Failed to send GPTWR...", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void sendResultToGPTWR(URI requestUri) {
/* 147 */     Commons.ctx.getExternalServiceManager().addReturnExternalMsg(requestUri);
/*     */   }
/*     */   
/*     */   private String getServiceAddress() {
/* 151 */     return SystemSettingsDao.getValue("externalApi.serivceAddress");
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\authentication\service\fido\FidoRespInterworker.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */