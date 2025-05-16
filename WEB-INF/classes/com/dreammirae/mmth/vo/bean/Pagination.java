/*     */ package WEB-INF.classes.com.dreammirae.mmth.vo.bean;
/*     */ 
/*     */ import com.dreammirae.mmth.vo.bean.json.TimestampToDateSerializer;
/*     */ import com.fasterxml.jackson.databind.annotation.JsonSerialize;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Pagination<T>
/*     */ {
/*     */   private static final int START_PAGE = 1;
/*     */   private final int limit;
/*     */   private final int offset;
/*     */   private final int currentPage;
/*     */   private List<T> contents;
/*     */   private int total;
/*     */   private int totalPageSize;
/*     */   @JsonSerialize(using = TimestampToDateSerializer.class)
/*     */   private Long tsFrom;
/*     */   @JsonSerialize(using = TimestampToDateSerializer.class)
/*     */   private Long tsTo;
/*     */   
/*     */   public Pagination(int limit, int offset) {
/*  25 */     if (limit < 0) {
/*  26 */       limit = 1;
/*     */     }
/*     */ 
/*     */     
/*  30 */     if (offset < 0) {
/*  31 */       offset = 1;
/*     */     }
/*     */     
/*  34 */     this.limit = limit;
/*  35 */     this.offset = offset;
/*     */     
/*  37 */     if (limit > offset) {
/*  38 */       this.currentPage = 1;
/*     */     } else {
/*  40 */       this.currentPage = offset / limit + 1;
/*     */     } 
/*     */   }
/*     */   
/*     */   public List<T> getContents() {
/*  45 */     return this.contents;
/*     */   }
/*     */   
/*     */   public void setContents(List<T> contents) {
/*  49 */     this.contents = contents;
/*     */   }
/*     */   
/*     */   public int getCurrentPage() {
/*  53 */     return this.currentPage;
/*     */   }
/*     */   
/*     */   public int getTotal() {
/*  57 */     return this.total;
/*     */   }
/*     */   
/*     */   public void setTotal(int total) {
/*  61 */     if (total < 0) {
/*  62 */       total = 0;
/*     */     }
/*  64 */     this.total = total;
/*     */     
/*  66 */     if (total == 0) {
/*  67 */       this.totalPageSize = 0;
/*     */     }
/*     */     
/*  70 */     int page = total / this.limit;
/*     */     
/*  72 */     if (total % this.limit != 0) {
/*  73 */       page++;
/*     */     }
/*     */     
/*  76 */     this.totalPageSize = page;
/*     */   }
/*     */   
/*     */   public int getStartIdx() {
/*  80 */     return this.offset + 1;
/*     */   }
/*     */   
/*     */   public int getTotalPageSize() {
/*  84 */     return this.totalPageSize;
/*     */   }
/*     */   
/*     */   public int getNumOfContents() {
/*  88 */     if (this.contents == null || this.contents.isEmpty()) {
/*  89 */       return 0;
/*     */     }
/*     */     
/*  92 */     return this.contents.size();
/*     */   }
/*     */   
/*     */   public boolean isFirstPage() {
/*  96 */     return (this.currentPage == 1);
/*     */   }
/*     */   
/*     */   public boolean hasPrevPage() {
/* 100 */     return !isFirstPage();
/*     */   }
/*     */   
/*     */   public boolean isLastPage() {
/* 104 */     return (this.totalPageSize == 0 || this.currentPage == this.totalPageSize);
/*     */   }
/*     */   
/*     */   public boolean hasNextPage() {
/* 108 */     return !isLastPage();
/*     */   }
/*     */   
/*     */   public Long getTsFrom() {
/* 112 */     return this.tsFrom;
/*     */   }
/*     */   
/*     */   public void setTsFrom(Long tsFrom) {
/* 116 */     this.tsFrom = tsFrom;
/*     */   }
/*     */   
/*     */   public Long getTsTo() {
/* 120 */     return this.tsTo;
/*     */   }
/*     */   
/*     */   public void setTsTo(Long tsTo) {
/* 124 */     this.tsTo = tsTo;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 129 */     StringBuilder builder = new StringBuilder();
/* 130 */     builder.append("Pagenation [limit=").append(this.limit).append(", offset=").append(this.offset).append(", currentPage=")
/* 131 */       .append(this.currentPage).append(", contents=").append(this.contents).append(", total=").append(this.total)
/* 132 */       .append(", totalPageSize=").append(this.totalPageSize).append("]");
/* 133 */     return builder.toString();
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\vo\bean\Pagination.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */