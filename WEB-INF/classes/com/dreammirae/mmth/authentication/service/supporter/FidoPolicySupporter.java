/*    */ package WEB-INF.classes.com.dreammirae.mmth.authentication.service.supporter;
/*    */ 
/*    */ import com.dreammirae.mmth.db.dao.FidoMetadataDao;
/*    */ import com.dreammirae.mmth.fido.handler.supporter.PolicySupporter;
/*    */ import com.dreammirae.mmth.fido.metadata.MetadataStatement;
/*    */ import com.dreammirae.mmth.fido.registry.UserVerificationMethods;
/*    */ import com.dreammirae.mmth.fido.uaf.MatchCriteria;
/*    */ import com.dreammirae.mmth.util.StringUtils;
/*    */ 
/*    */ public class FidoPolicySupporter
/*    */   implements PolicySupporter {
/*    */   private final String acceptedAAID;
/*    */   private final UserVerificationMethods userVerification;
/*    */   
/*    */   public FidoPolicySupporter(String acceptedAAID, UserVerificationMethods userVerification) {
/* 16 */     this.acceptedAAID = acceptedAAID;
/* 17 */     this.userVerification = userVerification;
/*    */   }
/*    */ 
/*    */   
/*    */   public MatchCriteria[][] returnAllowed() {
/* 22 */     if (!StringUtils.isEmpty(this.acceptedAAID)) {
/* 23 */       MatchCriteria[][] accepted = FidoMetadataDao.getAcceptPolicy(this.acceptedAAID);
/* 24 */       if (accepted != null) {
/* 25 */         return accepted;
/*    */       }
/*    */     } 
/*    */ 
/*    */     
/* 30 */     if (this.userVerification != null) {
/* 31 */       MatchCriteria[][] accepted = FidoMetadataDao.getAcceptPolicy(this.userVerification);
/*    */       
/* 33 */       if (accepted != null) {
/* 34 */         return accepted;
/*    */       }
/*    */     } 
/*    */     
/* 38 */     return FidoMetadataDao.getAcceptPolicy();
/*    */   }
/*    */ 
/*    */   
/*    */   public MatchCriteria[] returnDisallowed() {
/* 43 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public MetadataStatement returnMetadataStatement(String aaid) {
/* 48 */     return FidoMetadataDao.getMetadataStatmement(aaid);
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\authentication\service\supporter\FidoPolicySupporter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */