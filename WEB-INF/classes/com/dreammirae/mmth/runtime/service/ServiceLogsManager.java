/*     */ package WEB-INF.classes.com.dreammirae.mmth.runtime.service;
/*     */ 
/*     */ import com.dreammirae.mmth.config.Commons;
/*     */ import com.dreammirae.mmth.db.dao.ServiceLogDao;
/*     */ import com.dreammirae.mmth.util.thread.AppThreadFactory;
/*     */ import com.dreammirae.mmth.vo.ServiceLogVO;
/*     */ import java.util.concurrent.LinkedBlockingQueue;
/*     */ import java.util.concurrent.ThreadFactory;
/*     */ import java.util.concurrent.ThreadPoolExecutor;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.locks.ReentrantLock;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ServiceLogsManager
/*     */ {
/*  22 */   private static final Logger LOG = LoggerFactory.getLogger(com.dreammirae.mmth.runtime.service.ServiceLogsManager.class);
/*     */   
/*  24 */   private static final LinkedBlockingQueue<ServiceLogVO> SERVICE_LOG_BROKER = new LinkedBlockingQueue<>(1024);
/*     */   
/*     */   private static final int MAX_EXECUTING_SIZE = 100;
/*     */   
/*     */   private static final long WAIT_TO_TERMITATING = 500L;
/*     */   
/*     */   private static final int THREAD_POOL_MIN_SIZE = 2;
/*     */   
/*     */   private static final int THREAD_POOL_MAX_SIZE = 5;
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
/*  45 */   private static final ReentrantLock mLock = new ReentrantLock();
/*     */ 
/*     */ 
/*     */   
/*     */   private ServiceLogHandler svcLogHandler;
/*     */ 
/*     */ 
/*     */   
/*     */   private ServiceLogDao serviceLogDao;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initialize() {
/*  59 */     this.serviceLogDao = new ServiceLogDao();
/*     */ 
/*     */     
/*  62 */     initializeThreadPool();
/*     */ 
/*     */     
/*  65 */     initializesvcLogHandler();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void terminate() {
/*  72 */     terminatesvcLogHandler();
/*     */ 
/*     */     
/*  75 */     terminateThreadPool();
/*     */   }
/*     */ 
/*     */   
/*     */   private void initializeThreadPool() {
/*  80 */     AppThreadFactory myFactory = new AppThreadFactory("MMTH_messageProcessor_worker");
/*     */ 
/*     */     
/*  83 */     this.executorPool = new ThreadPoolExecutor(2, 5, 6L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(2), (ThreadFactory)myFactory);
/*     */   }
/*     */ 
/*     */   
/*     */   private void terminateThreadPool() {
/*     */     try {
/*  89 */       Thread.sleep(500L);
/*     */       
/*  91 */       this.executorPool.shutdown();
/*  92 */     } catch (InterruptedException ignore) {}
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void initializesvcLogHandler() {
/*  98 */     this.svcLogHandler = new ServiceLogHandler(this);
/*  99 */     this.svcLogHandler.start();
/*     */   }
/*     */ 
/*     */   
/*     */   private void terminatesvcLogHandler() {
/* 104 */     wakeUpWorker();
/*     */     
/*     */     try {
/* 107 */       Thread.sleep(500L);
/* 108 */     } catch (InterruptedException ignore) {}
/*     */ 
/*     */ 
/*     */     
/* 112 */     if (this.svcLogHandler != null) {
/* 113 */       this.svcLogHandler.stopRunning();
/*     */     }
/* 115 */     ServiceLogHandler serviceLogHandler = this.svcLogHandler;
/* 116 */     if (serviceLogHandler != null) {
/*     */       try {
/* 118 */         serviceLogHandler.join(500L);
/* 119 */       } catch (InterruptedException ignore) {}
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void addServiceLog(ServiceLogVO vo) {
/*     */     try {
/* 127 */       tryLock();
/* 128 */       SERVICE_LOG_BROKER.offer(vo);
/* 129 */     } catch (RuntimeException ignore) {
/*     */       return;
/*     */     } finally {
/* 132 */       unLock();
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void tryLock() {
/* 137 */     if (!mLock.tryLock()) {
/* 138 */       int retries = 0;
/*     */       
/*     */       do {
/* 141 */         if (++retries >= 10) {
/* 142 */           throw new RuntimeException();
/*     */         }
/*     */         
/*     */         try {
/* 146 */           Thread.sleep(100L);
/* 147 */         } catch (InterruptedException ignore) {}
/*     */ 
/*     */       
/*     */       }
/* 151 */       while (!mLock.tryLock());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void unLock() {
/* 157 */     if (mLock.isLocked()) {
/* 158 */       mLock.unlock();
/*     */     }
/* 160 */     Commons.ctx.getServiceLogsManager().wakeUpWorker();
/*     */   }
/*     */   
/*     */   private void wakeUpWorker() {
/* 164 */     this.svcLogHandler.wakeUp();
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\runtime\service\ServiceLogsManager.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */