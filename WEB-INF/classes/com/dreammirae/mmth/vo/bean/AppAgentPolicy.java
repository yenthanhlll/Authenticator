/*    */ package WEB-INF.classes.com.dreammirae.mmth.vo.bean;
/*    */ 
/*    */ import com.dreammirae.mmth.vo.AppAgentVO;
/*    */ import com.dreammirae.mmth.vo.bean.json.AppAgentPolicySerializer;
/*    */ import com.fasterxml.jackson.databind.annotation.JsonSerialize;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @JsonSerialize(using = AppAgentPolicySerializer.class)
/*    */ public class AppAgentPolicy
/*    */ {
/*    */   private List<AppAgentVO> policy;
/*    */   
/*    */   public List<AppAgentVO> getPolicy() {
/* 20 */     return this.policy;
/*    */   }
/*    */   
/*    */   public void setPolicy(List<AppAgentVO> policy) {
/* 24 */     this.policy = policy;
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\vo\bean\AppAgentPolicy.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */