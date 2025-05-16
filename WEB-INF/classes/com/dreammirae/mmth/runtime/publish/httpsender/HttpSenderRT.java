/*     */ package WEB-INF.classes.com.dreammirae.mmth.runtime.publish.httpsender;
/*     */ 
/*     */ import com.dreammirae.mmth.db.dao.SystemSettingsDao;
/*     */ import com.dreammirae.mmth.misc.SysEnvCommon;
/*     */ import com.dreammirae.mmth.runtime.audit.type.AlarmLevels;
/*     */ import com.dreammirae.mmth.runtime.audit.type.AuditAlarmTypes;
/*     */ import com.dreammirae.mmth.runtime.code.SyncCacheTypes;
/*     */ import com.dreammirae.mmth.runtime.publish.SyncCacheSenderRT;
/*     */ import com.dreammirae.mmth.util.EndPointUtils;
/*     */ import com.dreammirae.mmth.util.IPv4AddressUtils;
/*     */ import com.dreammirae.mmth.util.io.Closer;
/*     */ import com.dreammirae.mmth.util.notary.TransportNotary;
/*     */ import java.io.Closeable;
/*     */ import java.net.URI;
/*     */ import java.security.SecureRandom;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import javax.net.ssl.HostnameVerifier;
/*     */ import javax.net.ssl.SSLContext;
/*     */ import javax.net.ssl.TrustManager;
/*     */ import org.apache.http.StatusLine;
/*     */ import org.apache.http.client.methods.CloseableHttpResponse;
/*     */ import org.apache.http.client.methods.HttpGet;
/*     */ import org.apache.http.client.methods.HttpUriRequest;
/*     */ import org.apache.http.client.utils.URIBuilder;
/*     */ import org.apache.http.impl.client.CloseableHttpClient;
/*     */ import org.apache.http.impl.client.HttpClients;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HttpSenderRT
/*     */   implements SyncCacheSenderRT
/*     */ {
/*  40 */   private static final Logger LOG = LoggerFactory.getLogger(com.dreammirae.mmth.runtime.publish.httpsender.HttpSenderRT.class);
/*     */ 
/*     */   
/*     */   private static final String HTTPS_SCHEME = "https";
/*     */   
/*     */   public static final String PARAM_KEY_EVENT_TYPE = "eventDataType";
/*     */   
/*     */   private static final String SEND_URI_FORMAT = "%s/mmth/service/httper";
/*     */   
/*     */   private static String[] SEND_OTHER_SERVER_URI;
/*     */ 
/*     */   
/*     */   public void initialize() {
/*  53 */     refresh();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void terminate() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void refresh() {
/*  64 */     List<String> sendToList = new ArrayList<>();
/*     */     
/*  66 */     String[] serverUrls = SystemSettingsDao.getArrayValue("advanced.publisherTarget");
/*  67 */     List<String> locals = EndPointUtils.getMyEndPoints();
/*     */     
/*  69 */     for (String url : serverUrls) {
/*     */       
/*  71 */       Iterator<String> iterator = locals.iterator(); while (true) { if (iterator.hasNext()) { String l = iterator.next();
/*     */           
/*  73 */           if (url.contains(l)) {
/*     */             break;
/*     */           }
/*     */           continue; }
/*     */         
/*  78 */         sendToList.add(String.format("%s/mmth/service/httper", new Object[] { url }));
/*     */         break; }
/*     */     
/*     */     } 
/*  82 */     SEND_OTHER_SERVER_URI = sendToList.<String>toArray(new String[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendEvent(SyncCacheTypes type) {
/*  89 */     if (type == null) {
/*     */       return;
/*     */     }
/*     */     
/*  93 */     if (SEND_OTHER_SERVER_URI.length < 1) {
/*     */       return;
/*     */     }
/*     */     
/*  97 */     for (String url : SEND_OTHER_SERVER_URI) {
/*     */       try {
/*  99 */         URI uri = (new URIBuilder(url)).addParameter("eventDataType", type.getCode()).build();
/*     */         
/* 101 */         if ("https".equalsIgnoreCase(uri.getScheme())) {
/* 102 */           sendHttpsReqeust(uri);
/*     */         } else {
/* 104 */           sendHttpReqeust(uri);
/*     */         } 
/*     */         
/* 107 */         AuditAlarmTypes.PUBLISHER.raiseAlarm(null, 2305, AlarmLevels.INFORMATION, new Object[] { type.name(), Arrays.toString((Object[])IPv4AddressUtils.myIps()) });
/*     */       }
/* 109 */       catch (Exception e) {
/* 110 */         AuditAlarmTypes.PUBLISHER.raiseAlarm(null, 2307, AlarmLevels.URGENT, new Object[] { e.getMessage(), Arrays.toString((Object[])IPv4AddressUtils.myIps()) });
/* 111 */         LOG.error("Failed to send event", e);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void sendHttpReqeust(URI uri) throws Exception {
/* 120 */     CloseableHttpClient client = null;
/* 121 */     CloseableHttpResponse resp = null;
/*     */     
/*     */     try {
/* 124 */       client = HttpClients.custom().build();
/* 125 */       HttpGet method = new HttpGet();
/*     */ 
/*     */       
/* 128 */       method.addHeader(SysEnvCommon.getPublishHttpApiHeaderKey(), 
/* 129 */           TransportNotary.generateNotaryData(SysEnvCommon.getPublishServiceApiKey()));
/* 130 */       method.addHeader("Content-type", "application/json; charset=utf-8");
/* 131 */       method.setURI(uri);
/*     */       
/* 133 */       resp = client.execute((HttpUriRequest)method);
/*     */       
/* 135 */       StatusLine sl = resp.getStatusLine();
/*     */       
/* 137 */       int statusCode = sl.getStatusCode();
/*     */       
/* 139 */       if (200 == statusCode) {
/* 140 */         LOG.info("Send event data sucessfully... to=" + uri.getHost() + ", queries=" + uri.getQuery());
/*     */       } else {
/* 142 */         LOG.error("Failed to send event data... to=" + uri.getHost() + ", queries=" + uri.getQuery() + ", cause received status = " + statusCode);
/* 143 */         throw new RuntimeException("Failed to send event data... to=" + uri.getHost() + ", queries=" + uri.getQuery() + ", cause received status = " + statusCode);
/*     */       }
/*     */     
/* 146 */     } catch (Exception e) {
/* 147 */       LOG.error("Failed to send event data... to=" + uri.getHost() + ", queries=" + uri.getQuery(), e);
/* 148 */       throw e;
/*     */     } finally {
/* 150 */       Closer.close((Closeable)resp);
/* 151 */       Closer.close((Closeable)client);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void sendHttpsReqeust(URI uri) throws Exception {
/* 158 */     CloseableHttpClient client = null;
/* 159 */     CloseableHttpResponse resp = null;
/*     */     
/*     */     try {
/* 162 */       SSLContext sslContext = SSLContext.getInstance("SSL");
/* 163 */       sslContext.init(null, new TrustManager[] { (TrustManager)new X509TrustManagerImp(null) }, new SecureRandom());
/*     */       
/* 165 */       client = HttpClients.custom().setSSLContext(sslContext).setSSLHostnameVerifier((HostnameVerifier)new HostnameVerifierImp(null)).build();
/* 166 */       HttpGet method = new HttpGet();
/*     */ 
/*     */ 
/*     */       
/* 170 */       method.addHeader(SysEnvCommon.getPublishHttpApiHeaderKey(), 
/* 171 */           TransportNotary.generateNotaryData(SysEnvCommon.getPublishServiceApiKey()));
/* 172 */       method.addHeader("Content-type", "application/json; charset=utf-8");
/* 173 */       method.setURI(uri);
/*     */       
/* 175 */       resp = client.execute((HttpUriRequest)method);
/*     */       
/* 177 */       StatusLine sl = resp.getStatusLine();
/*     */       
/* 179 */       int statusCode = sl.getStatusCode();
/*     */       
/* 181 */       if (200 == statusCode) {
/* 182 */         LOG.info("Send event data sucessfully... to=" + uri.getHost() + ", queries=" + uri.getQuery());
/*     */       } else {
/* 184 */         LOG.error("Failed to send event data... to=" + uri.getHost() + ", queries=" + uri.getQuery() + ", cause received status = " + statusCode);
/* 185 */         throw new RuntimeException("Failed to send event data... to=" + uri.getHost() + ", queries=" + uri.getQuery() + ", cause received status = " + statusCode);
/*     */       }
/*     */     
/* 188 */     } catch (Exception e) {
/* 189 */       LOG.error("Failed to send event data... to=" + uri.getHost() + ", queries=" + uri.getQuery(), e);
/* 190 */       throw e;
/*     */     } finally {
/* 192 */       Closer.close((Closeable)resp);
/* 193 */       Closer.close((Closeable)client);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\runtime\publish\httpsender\HttpSenderRT.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */