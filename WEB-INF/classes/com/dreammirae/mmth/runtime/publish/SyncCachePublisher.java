/*     */ package WEB-INF.classes.com.dreammirae.mmth.runtime.publish;
/*     */ 
/*     */ import com.dreammirae.mmth.config.Commons;
/*     */ import com.dreammirae.mmth.db.dao.AppAgentDao;
/*     */ import com.dreammirae.mmth.db.dao.FidoFacetDao;
/*     */ import com.dreammirae.mmth.db.dao.FidoMetadataDao;
/*     */ import com.dreammirae.mmth.db.dao.SystemSettingsDao;
/*     */ import com.dreammirae.mmth.runtime.code.SyncCacheTypes;
/*     */ import com.dreammirae.mmth.runtime.publish.ReceiveSyncCacheListener;
/*     */ import com.dreammirae.mmth.runtime.publish.SyncCacheSenderRT;
/*     */ import java.util.List;
/*     */ import java.util.TimerTask;
/*     */ import java.util.concurrent.CopyOnWriteArrayList;
/*     */ import java.util.concurrent.ExecutorService;
/*     */ import java.util.concurrent.Executors;
/*     */ import java.util.concurrent.RejectedExecutionException;
/*     */ import java.util.concurrent.locks.ReentrantLock;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SyncCachePublisher
/*     */ {
/*  28 */   private static final Logger LOG = LoggerFactory.getLogger(com.dreammirae.mmth.runtime.publish.SyncCachePublisher.class);
/*     */   
/*     */   private static final long DELAY_TO_FIRE_WORKER = 100L;
/*     */   private static final long TIME_SLICE = 100L;
/*  32 */   private static final ReentrantLock mLock = new ReentrantLock();
/*     */   
/*     */   private final SyncCacheSenderRT eventRT;
/*  35 */   private final List<ReceiveSyncCacheListener> listeners = new CopyOnWriteArrayList<>();
/*     */   
/*     */   private ExecutorService executorService;
/*     */   
/*     */   public SyncCachePublisher(SyncCacheSenderRT eventRT) {
/*  40 */     this.eventRT = eventRT;
/*     */   }
/*     */   
/*     */   public void initialize() {
/*  44 */     this.executorService = Executors.newSingleThreadExecutor();
/*  45 */     this.eventRT.initialize();
/*     */     
/*  47 */     addReceiveSyncCacheListener((ReceiveSyncCacheListener)new FidoMetadataDao());
/*  48 */     addReceiveSyncCacheListener((ReceiveSyncCacheListener)new FidoFacetDao());
/*  49 */     addReceiveSyncCacheListener((ReceiveSyncCacheListener)new SystemSettingsDao());
/*  50 */     addReceiveSyncCacheListener((ReceiveSyncCacheListener)new AppAgentDao());
/*     */   }
/*     */   
/*     */   public void terminate() {
/*  54 */     this.eventRT.terminate();
/*     */     
/*  56 */     if (this.executorService != null) {
/*  57 */       this.executorService.shutdownNow();
/*     */     }
/*     */     
/*  60 */     this.listeners.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public void refreshPublisher() {
/*  65 */     if (this.eventRT != null) {
/*  66 */       this.eventRT.refresh();
/*     */     }
/*     */   }
/*     */   
/*     */   public void syncCacheChanged(SyncCacheTypes type) {
/*  71 */     if (SystemSettingsDao.getSettingEnabled("advanced.publisherEnabled").toBoolean() && 
/*  72 */       type != null) {
/*  73 */       Commons.timer.schedule((TimerTask)new EventPublishWorker(this, type, null), 100L);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addReceiveSyncCacheListener(ReceiveSyncCacheListener listener) {
/*  81 */     mLock.lock();
/*     */     try {
/*  83 */       if (!this.listeners.contains(listener)) {
/*  84 */         this.listeners.add(listener);
/*     */       }
/*     */     } finally {
/*  87 */       mLock.unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeReceiveSyncCacheListener(ReceiveSyncCacheListener listener) {
/*  93 */     if (this.listeners.isEmpty()) {
/*     */       return;
/*     */     }
/*     */     
/*  97 */     mLock.lock();
/*     */     try {
/*  99 */       if (this.listeners.contains(listener)) {
/* 100 */         this.listeners.remove(listener);
/*     */       }
/*     */     } finally {
/* 103 */       mLock.unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeReceiveSyncCacheListener(Class<? extends ReceiveSyncCacheListener> clazz) {
/* 110 */     if (this.listeners.isEmpty()) {
/*     */       return;
/*     */     }
/*     */     
/* 114 */     mLock.lock();
/*     */     
/*     */     try {
/* 117 */       Object[] arr = this.listeners.toArray();
/* 118 */       for (int i = 0, len = arr.length; i < len; i++) {
/*     */         
/* 120 */         if (arr[i].getClass().equals(clazz)) {
/* 121 */           this.listeners.remove(arr[i]);
/*     */         }
/*     */       } 
/*     */     } finally {
/* 125 */       mLock.unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void notifyReceived(SyncCacheTypes type) {
/* 132 */     if (this.listeners.isEmpty()) {
/*     */       return;
/*     */     }
/*     */     
/* 136 */     ReceiveSyncCacheListener[] listenerArr = null;
/*     */     
/* 138 */     mLock.lock();
/*     */     
/*     */     try {
/* 141 */       listenerArr = this.listeners.<ReceiveSyncCacheListener>toArray(new ReceiveSyncCacheListener[0]);
/*     */     } finally {
/* 143 */       mLock.unlock();
/*     */     } 
/*     */     
/* 146 */     boolean isRet = false;
/* 147 */     int retries = 0;
/*     */     do {
/*     */       try {
/* 150 */         this.executorService
/* 151 */           .execute((Runnable)new ReceiveSyncCacheExecutorImp(this, type, listenerArr, null));
/* 152 */         isRet = true;
/* 153 */       } catch (RejectedExecutionException e) {
/*     */         
/* 155 */         if (retries++ > 3)
/*     */         {
/* 157 */           isRet = true;
/*     */         }
/*     */         
/*     */         try {
/* 161 */           Thread.sleep(100L);
/* 162 */         } catch (InterruptedException ignore) {}
/*     */       
/*     */       }
/*     */     
/*     */     }
/* 167 */     while (!isRet);
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\runtime\publish\SyncCachePublisher.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */