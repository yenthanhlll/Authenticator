/*    */ package WEB-INF.classes.com.dreammirae.mmth.vo.bean;
/*    */ 
/*    */ import com.dreammirae.mmth.vo.bean.ICustomLogData;
/*    */ 
/*    */ public class MiraeAssetVietnamLogData
/*    */   implements ICustomLogData {
/*    */   private String macAddress;
/*    */   private long ip;
/*    */   private int authLogType;
/*    */   
/*    */   public MiraeAssetVietnamLogData() {}
/*    */   
/*    */   public MiraeAssetVietnamLogData(String macAddress, long ip) {
/* 14 */     this.macAddress = macAddress;
/* 15 */     this.ip = ip;
/*    */   }
/*    */   
/*    */   public String getMacAddress() {
/* 19 */     return this.macAddress;
/*    */   }
/*    */   
/*    */   public void setMacAddress(String macAddress) {
/* 23 */     this.macAddress = macAddress;
/*    */   }
/*    */   
/*    */   public long getIp() {
/* 27 */     return this.ip;
/*    */   }
/*    */   
/*    */   public void setIp(long ip) {
/* 31 */     this.ip = ip;
/*    */   }
/*    */   
/*    */   public int getAuthLogType() {
/* 35 */     return this.authLogType;
/*    */   }
/*    */   
/*    */   public void setAuthLogType(int authLogType) {
/* 39 */     this.authLogType = authLogType;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 49 */     StringBuilder builder = new StringBuilder();
/* 50 */     builder.append("MiraeAssetVietnamLogData [macAddress=").append(this.macAddress).append("]");
/* 51 */     return builder.toString();
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\vo\bean\MiraeAssetVietnamLogData.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */