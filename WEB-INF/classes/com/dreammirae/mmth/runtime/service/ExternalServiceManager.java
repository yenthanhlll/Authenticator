/*     */ package WEB-INF.classes.com.dreammirae.mmth.runtime.service;
/*     */ 
/*     */ import com.dreammirae.mmth.util.thread.AppThreadFactory;
/*     */ import com.dreammirae.mmth.vo.bean.FcmPushContents;
/*     */ import java.net.URI;
/*     */ import java.security.KeyManagementException;
/*     */ import java.util.concurrent.LinkedBlockingQueue;
/*     */ import java.util.concurrent.ThreadFactory;
/*     */ import java.util.concurrent.ThreadPoolExecutor;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.locks.ReentrantLock;
/*     */ import javax.net.ssl.HostnameVerifier;
/*     */ import javax.net.ssl.SSLContext;
/*     */ import org.apache.http.conn.ssl.NoopHostnameVerifier;
/*     */ import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
/*     */ import org.apache.http.ssl.SSLContexts;
/*     */ import org.apache.http.ssl.TrustStrategy;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ExternalServiceManager
/*     */ {
/*  51 */   private static final Logger LOG = LoggerFactory.getLogger(com.dreammirae.mmth.runtime.service.ExternalServiceManager.class);
/*     */   
/*  53 */   private static final LinkedBlockingQueue<URI> INTERWORK_RESULT_QUEUE = new LinkedBlockingQueue<>(512);
/*  54 */   private static final LinkedBlockingQueue<FcmPushContents> PUSH_MESSGAE_QUEUE = new LinkedBlockingQueue<>(512);
/*     */ 
/*     */   
/*     */   private static final long WAIT_TO_TERMITATING = 500L;
/*     */ 
/*     */   
/*     */   private static final int THREAD_POOL_MIN_SIZE = 2;
/*     */ 
/*     */   
/*     */   private static final int THREAD_POOL_MAX_SIZE = 10;
/*     */ 
/*     */   
/*     */   private static final int THREAD_KEEP_ALIVE_TIME = 6;
/*     */   
/*     */   private static final int THREAD_POOL_WORKER_QUEUE = 2;
/*     */   
/*     */   private static final long THREAD_SLEEP = 100L;
/*     */   
/*     */   private ThreadPoolExecutor executorPool;
/*     */   
/*     */   private static final long TIME_SLICE = 100L;
/*     */   
/*     */   private static final int MAX_RETRY_CNT = 10;
/*     */   
/*     */   private static final String CONTENT_TYPE = "application/json; charset=utf-8";
/*     */   
/*     */   private ExternalSenderMsgHandler serviceMsgHandler;
/*     */   
/*  82 */   private static final ReentrantLock mLock = new ReentrantLock();
/*     */ 
/*     */ 
/*     */   
/*     */   public void initialize() {
/*  87 */     initializeThreadPool();
/*     */ 
/*     */     
/*  90 */     initializeMsgSenderHandler();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void terminate() {
/*  97 */     terminateMsgSenderHandler();
/*     */ 
/*     */     
/* 100 */     terminateThreadPool();
/*     */   }
/*     */ 
/*     */   
/*     */   private void initializeThreadPool() {
/* 105 */     AppThreadFactory myFactory = new AppThreadFactory("MMTH_externalMsgSender_worker");
/*     */ 
/*     */     
/* 108 */     this.executorPool = new ThreadPoolExecutor(2, 10, 6L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(2), (ThreadFactory)myFactory);
/*     */   }
/*     */ 
/*     */   
/*     */   private void terminateThreadPool() {
/*     */     try {
/* 114 */       Thread.sleep(500L);
/*     */       
/* 116 */       this.executorPool.shutdown();
/* 117 */     } catch (InterruptedException ignore) {}
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void initializeMsgSenderHandler() {
/* 123 */     this.serviceMsgHandler = new ExternalSenderMsgHandler(this);
/* 124 */     this.serviceMsgHandler.start();
/*     */   }
/*     */ 
/*     */   
/*     */   private void terminateMsgSenderHandler() {
/* 129 */     if (this.serviceMsgHandler != null) {
/* 130 */       this.serviceMsgHandler.stopRunning();
/*     */     }
/* 132 */     ExternalSenderMsgHandler externalSenderMsgHandler = this.serviceMsgHandler;
/* 133 */     if (externalSenderMsgHandler != null) {
/*     */       try {
/* 135 */         externalSenderMsgHandler.join(500L);
/* 136 */       } catch (InterruptedException ignore) {}
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void wakeUpWorker() {
/* 143 */     this.serviceMsgHandler.wakeUp();
/*     */   }
/*     */ 
/*     */   
/*     */   public void addReturnExternalMsg(URI uri) {
/*     */     try {
/* 149 */       tryLock();
/*     */       
/* 151 */       INTERWORK_RESULT_QUEUE.offer(uri);
/*     */     }
/* 153 */     catch (RuntimeException ignore) {
/*     */       return;
/*     */     } finally {
/* 156 */       unLock();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void addFCMPushMsg(FcmPushContents vo) {
/*     */     try {
/* 163 */       tryLock();
/*     */       
/* 165 */       PUSH_MESSGAE_QUEUE.offer(vo);
/*     */     }
/* 167 */     catch (RuntimeException ignore) {
/*     */       return;
/*     */     } finally {
/* 170 */       unLock();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void tryLock() {
/* 175 */     if (!mLock.tryLock()) {
/* 176 */       int retries = 0;
/*     */       
/*     */       do {
/* 179 */         if (++retries >= 10) {
/* 180 */           throw new RuntimeException();
/*     */         }
/*     */         
/*     */         try {
/* 184 */           Thread.sleep(100L);
/* 185 */         } catch (InterruptedException ignore) {}
/*     */ 
/*     */       
/*     */       }
/* 189 */       while (!mLock.tryLock());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void unLock() {
/* 195 */     wakeUpWorker();
/*     */     
/* 197 */     if (mLock.isLocked()) {
/* 198 */       mLock.unlock();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private SSLConnectionSocketFactory createSSLConnectionSocketFactory() {
/*     */     try {
/* 210 */       SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, (TrustStrategy)new Object(this)).build();
/* 211 */       SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, (HostnameVerifier)NoopHostnameVerifier.INSTANCE);
/*     */       
/* 213 */       return sslsf;
/*     */     }
/* 215 */     catch (KeyManagementException|java.security.NoSuchAlgorithmException|java.security.KeyStoreException e) {
/*     */ 
/*     */ 
/*     */       
/* 219 */       return null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\runtime\service\ExternalServiceManager.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */