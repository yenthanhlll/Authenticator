/*     */ package WEB-INF.classes.com.dreammirae.mmth.runtime.audit;
/*     */ 
/*     */ import com.dreammirae.mmth.config.Commons;
/*     */ import com.dreammirae.mmth.util.thread.ILifecycle;
/*     */ import com.dreammirae.mmth.vo.AuditAlarmVO;
/*     */ import java.util.concurrent.LinkedBlockingQueue;
/*     */ import java.util.concurrent.locks.ReentrantLock;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class AuditAlarmManager
/*     */   implements ILifecycle
/*     */ {
/*  19 */   private static final Logger LOG = LoggerFactory.getLogger(com.dreammirae.mmth.runtime.audit.AuditAlarmManager.class);
/*  20 */   private static final LinkedBlockingQueue<AuditAlarmVO> ALARM_BROKER = new LinkedBlockingQueue<>(64);
/*     */   
/*     */   private static final int MAX_EXECUTING_SIZE = 32;
/*     */   
/*     */   private static final long THREAD_SLEEP = 100L;
/*     */   
/*     */   private static final long WAIT_TO_TERMITATING = 500L;
/*     */   private static final long TIME_SLICE = 100L;
/*     */   private static final int MAX_RETRY_CNT = 10;
/*  29 */   private static final ReentrantLock mLock = new ReentrantLock();
/*     */   
/*     */   private AuditAlarmHandler auditAlarmHandler;
/*     */ 
/*     */   
/*     */   public void initialize() {
/*  35 */     LOG.info("initialize()");
/*     */     
/*  37 */     this.auditAlarmHandler = new AuditAlarmHandler(this);
/*  38 */     this.auditAlarmHandler.start();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void terminate() {
/*  44 */     LOG.info("terminate()");
/*  45 */     terminateAlarmHandler();
/*     */   }
/*     */ 
/*     */   
/*     */   public void joinTermination() {
/*  50 */     LOG.info("joinTermination()");
/*  51 */     terminateAlarmHandler();
/*     */   }
/*     */ 
/*     */   
/*     */   private void terminateAlarmHandler() {
/*  56 */     wakeup();
/*     */     
/*     */     try {
/*  59 */       Thread.sleep(500L);
/*  60 */     } catch (InterruptedException e) {}
/*     */ 
/*     */ 
/*     */     
/*  64 */     if (this.auditAlarmHandler != null) {
/*  65 */       this.auditAlarmHandler.stopRunning();
/*     */     }
/*  67 */     AuditAlarmHandler auditAlarmHandler = this.auditAlarmHandler;
/*  68 */     if (auditAlarmHandler != null) {
/*     */       try {
/*  70 */         auditAlarmHandler.join(500L);
/*  71 */       } catch (InterruptedException ignore) {}
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void addAlarm(AuditAlarmVO vo) {
/*     */     try {
/*  79 */       tryLock();
/*  80 */       ALARM_BROKER.offer(vo);
/*  81 */     } catch (RuntimeException ignore) {
/*     */       return;
/*     */     } finally {
/*  84 */       unLock();
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void tryLock() {
/*  89 */     if (!mLock.tryLock()) {
/*  90 */       int retries = 0;
/*     */       
/*     */       do {
/*  93 */         if (++retries >= 10) {
/*  94 */           throw new RuntimeException();
/*     */         }
/*     */         
/*     */         try {
/*  98 */           Thread.sleep(100L);
/*  99 */         } catch (InterruptedException ignore) {}
/*     */ 
/*     */       
/*     */       }
/* 103 */       while (!mLock.tryLock());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void unLock() {
/* 109 */     if (mLock.isLocked()) {
/* 110 */       mLock.unlock();
/*     */     }
/* 112 */     if (Commons.ctx.getAuditAlarmManager() != null) {
/* 113 */       Commons.ctx.getAuditAlarmManager().wakeup();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void wakeup() {
/* 119 */     if (this.auditAlarmHandler != null)
/* 120 */       this.auditAlarmHandler.wakeUp(); 
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\runtime\audit\AuditAlarmManager.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */