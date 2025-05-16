/*     */ package WEB-INF.classes.com.dreammirae.mmth.vo.bean;
/*     */ 
/*     */ import com.dreammirae.mmth.vo.bean.json.TimestampToDateSerializer;
/*     */ import com.fasterxml.jackson.databind.annotation.JsonSerialize;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DataPurgeTableStats
/*     */ {
/*     */   private Long totalSize;
/*     */   private Long targetSize;
/*     */   private Long totalRows;
/*     */   private Long targetRows;
/*     */   private Long deletedRows;
/*     */   @JsonSerialize(using = TimestampToDateSerializer.class)
/*     */   private Long tsBase;
/*     */   
/*     */   public Long getTotalSize() {
/*  24 */     return this.totalSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTotalSize(Long totalSize) {
/*  31 */     this.totalSize = totalSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Long getTargetSize() {
/*  38 */     return this.targetSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTargetSize(Long targetSize) {
/*  45 */     this.targetSize = targetSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Long getTotalRows() {
/*  52 */     return this.totalRows;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTotalRows(Long totalRows) {
/*  59 */     this.totalRows = totalRows;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Long getTargetRows() {
/*  66 */     return this.targetRows;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTargetRows(Long targetRows) {
/*  73 */     this.targetRows = targetRows;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Long getDeletedRows() {
/*  80 */     return this.deletedRows;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDeletedRows(Long deletedRows) {
/*  87 */     this.deletedRows = deletedRows;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Long getTsBase() {
/*  94 */     return this.tsBase;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTsBase(Long tsBase) {
/* 101 */     this.tsBase = tsBase;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 109 */     StringBuilder builder = new StringBuilder();
/* 110 */     builder.append("DataPurgeTableStats [totalSize=").append(this.totalSize).append(", targetSize=").append(this.targetSize).append(", totalRows=").append(this.totalRows).append(", targetRows=")
/* 111 */       .append(this.targetRows).append(", deletedRows=").append(this.deletedRows).append("]");
/* 112 */     return builder.toString();
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\vo\bean\DataPurgeTableStats.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */