/*     */ package WEB-INF.classes.com.dreammirae.mmth.runtime.code;
/*     */ 
/*     */ import com.dreammirae.mmth.util.bean.KeyValuePair;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.joda.time.DateTime;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public enum TimePeriodTypes
/*     */ {
/*  13 */   MILLIS(1, "TimePeriodTypes.MILLIS"),
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
/*  25 */   SECOND(2, "TimePeriodTypes.SECOND"),
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
/*  36 */   MINUTE(3, "TimePeriodTypes.MINUTE"),
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
/*  47 */   HOUR(4, "TimePeriodTypes.HOUR"),
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
/*  58 */   DAY(5, "TimePeriodTypes.DAY"),
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
/*  69 */   WEEK(6, "TimePeriodTypes.WEEK"),
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
/*  80 */   MONTH(7, "TimePeriodTypes.MONTH"),
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
/*  93 */   YEAR(8, "TimePeriodTypes.YEAR"),
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
/* 106 */   CRON(9, "TimePeriodTypes.CRON");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final int key;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final String messageKey;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   TimePeriodTypes(int key, String messageKey) {
/* 123 */     this.key = key;
/* 124 */     this.messageKey = messageKey;
/*     */   }
/*     */   
/*     */   public int getKey() {
/* 128 */     return this.key;
/*     */   }
/*     */   
/*     */   public String getMessageKey() {
/* 132 */     return this.messageKey;
/*     */   }
/*     */   
/*     */   public String getName() {
/* 136 */     return name();
/*     */   }
/*     */   
/*     */   public static com.dreammirae.mmth.runtime.code.TimePeriodTypes getPeriodTypes(int key) {
/* 140 */     for (com.dreammirae.mmth.runtime.code.TimePeriodTypes type : values()) {
/* 141 */       if (type.key == key) {
/* 142 */         return type;
/*     */       }
/*     */     } 
/*     */     
/* 146 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public static com.dreammirae.mmth.runtime.code.TimePeriodTypes valueOfIgnoreCase(String code) {
/* 151 */     for (com.dreammirae.mmth.runtime.code.TimePeriodTypes type : values()) {
/* 152 */       if (type.name().equalsIgnoreCase(code)) {
/* 153 */         return type;
/*     */       }
/*     */     } 
/* 156 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static List<KeyValuePair<String, String>> getMessageKeyPair() {
/* 165 */     List<KeyValuePair<String, String>> list = new ArrayList<>(2);
/*     */     
/* 167 */     for (com.dreammirae.mmth.runtime.code.TimePeriodTypes type : values()) {
/* 168 */       list.add(new KeyValuePair(type.name(), type.getMessageKey()));
/*     */     }
/*     */     
/* 171 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   public static List<KeyValuePair<String, String>> getMessageKeyPair(com.dreammirae.mmth.runtime.code.TimePeriodTypes... excludes) {
/* 176 */     List<KeyValuePair<String, String>> list = new ArrayList<>(2);
/*     */     
/* 178 */     for (com.dreammirae.mmth.runtime.code.TimePeriodTypes type : values()) {
/*     */       
/* 180 */       com.dreammirae.mmth.runtime.code.TimePeriodTypes[] arrayOfTimePeriodTypes = excludes; int i = arrayOfTimePeriodTypes.length; byte b = 0; while (true) { if (b < i) { com.dreammirae.mmth.runtime.code.TimePeriodTypes ex = arrayOfTimePeriodTypes[b];
/* 181 */           if (type.equals(ex))
/*     */             break; 
/*     */           b++;
/*     */           continue; }
/*     */         
/* 186 */         list.add(new KeyValuePair(type.name(), type.getMessageKey())); break; }
/*     */     
/*     */     } 
/* 189 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   public static com.dreammirae.mmth.runtime.code.TimePeriodTypes valueByName(String name) {
/*     */     try {
/* 195 */       return valueOf(name);
/* 196 */     } catch (Exception e) {
/* 197 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public abstract long defaultMillisOfPeriods(int paramInt);
/*     */   
/*     */   public abstract DateTime nextDateTime(int paramInt, long paramLong);
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\runtime\code\TimePeriodTypes.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */