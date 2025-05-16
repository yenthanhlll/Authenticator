/*    */ package WEB-INF.classes.com.dreammirae.mmth.authentication.transport;
/*    */ 
/*    */ import com.dreammirae.mmth.authentication.ReturnCodes;
/*    */ import com.dreammirae.mmth.authentication.transport.IUserDevice;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BizRespData
/*    */ {
/*    */   private ReturnCodes returnCode;
/*    */   private String issueCode;
/*    */   private int deviceCnt;
/*    */   private List<? extends IUserDevice> devices;
/*    */   
/*    */   public ReturnCodes getReturnCode() {
/* 20 */     return this.returnCode;
/*    */   }
/*    */   
/*    */   public void setReturnCode(ReturnCodes returnCode) {
/* 24 */     this.returnCode = returnCode;
/*    */   }
/*    */   
/*    */   public String getIssueCode() {
/* 28 */     return this.issueCode;
/*    */   }
/*    */   
/*    */   public void setIssueCode(String issueCode) {
/* 32 */     this.issueCode = issueCode;
/*    */   }
/*    */   
/*    */   public int getDeviceCnt() {
/* 36 */     return this.deviceCnt;
/*    */   }
/*    */   
/*    */   public void setDeviceCnt(int deviceCnt) {
/* 40 */     this.deviceCnt = deviceCnt;
/*    */   }
/*    */   
/*    */   public List<? extends IUserDevice> getDevices() {
/* 44 */     return this.devices;
/*    */   }
/*    */   
/*    */   public void setDevices(List<? extends IUserDevice> devices) {
/* 48 */     this.devices = devices;
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\authentication\transport\BizRespData.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */