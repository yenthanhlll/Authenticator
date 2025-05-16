/*     */ package WEB-INF.classes.com.dreammirae.mmth.vo;
/*     */ 
/*     */ import com.dreammirae.mmth.authentication.ReturnCodes;
/*     */ import com.dreammirae.mmth.exception.ReturnCodeException;
/*     */ import com.dreammirae.mmth.external.bean.ExtRequestStatus;
/*     */ import com.dreammirae.mmth.util.StringUtils;
/*     */ import com.dreammirae.mmth.util.io.SerializationUtils;
/*     */ import com.dreammirae.mmth.vo.bean.AuthRequestContents;
/*     */ import com.dreammirae.mmth.vo.bean.TransactionInfoData;
/*     */ import com.dreammirae.mmth.vo.bean.json.ExternalServiceItemDeserializer;
/*     */ import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.regex.Pattern;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @JsonDeserialize(using = ExternalServiceItemDeserializer.class)
/*     */ public class ExternalServiceItemVO
/*     */   implements Serializable
/*     */ {
/*     */   public static final int EXT_SERVICE_ITEM_TYPE_ANY_PASS = 1;
/*     */   public static final int EXT_SERVICE_ITEM_TYPE_KIWOOM_TRAN_INFO = 2;
/*     */   public static final int EXT_SERVICE_ITEM_TYPE_VT_QR = 3;
/*  68 */   private static final Pattern PATTERN_USERNAME = Pattern.compile("^[a-zA-Z0-9\\@\\.\\_\\-]+@[0-9]{4}$");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  74 */   private long id = -1L;
/*  75 */   private int userId = -1;
/*     */   
/*     */   private String transactionId;
/*     */   
/*     */   private String externalIdentifier;
/*     */   
/*     */   private ExtRequestStatus status;
/*     */   
/*     */   private long tsReg;
/*     */   
/*     */   private long tsExpired;
/*     */   private long tsDone;
/*     */   private boolean usePush;
/*     */   private String userName;
/*     */   private String title;
/*     */   private String msg;
/*     */   private String type;
/*     */   private AuthRequestContents requestContents;
/*  93 */   private int itemType = 1;
/*     */ 
/*     */   
/*     */   private TransactionInfoData transactionInfoData;
/*     */   
/*     */   private static final long serialVersionUID = 1L;
/*     */   
/*     */   private static final int version = 2;
/*     */ 
/*     */   
/*     */   public long getId() {
/* 104 */     return this.id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setId(long id) {
/* 112 */     this.id = id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getUserId() {
/* 119 */     return this.userId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUserId(int userId) {
/* 127 */     this.userId = userId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTransactionId() {
/* 134 */     return this.transactionId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTransactionId(String transactionId) {
/* 142 */     this.transactionId = transactionId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getExternalIdentifier() {
/* 149 */     return this.externalIdentifier;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setExternalIdentifier(String externalIdentifier) {
/* 157 */     this.externalIdentifier = externalIdentifier;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ExtRequestStatus getStatus() {
/* 164 */     return this.status;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setStatus(ExtRequestStatus status) {
/* 172 */     this.status = status;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getTsReg() {
/* 179 */     return this.tsReg;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTsReg(long tsReg) {
/* 187 */     this.tsReg = tsReg;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getTsExpired() {
/* 194 */     return this.tsExpired;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTsExpired(long tsExpired) {
/* 202 */     this.tsExpired = tsExpired;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getTsDone() {
/* 210 */     return this.tsDone;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTsDone(long tsDone) {
/* 218 */     this.tsDone = tsDone;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUsePush() {
/* 225 */     return this.usePush;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUsePush(boolean usePush) {
/* 233 */     this.usePush = usePush;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getUserName() {
/* 240 */     return this.userName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUserName(String userName) {
/* 248 */     this.userName = userName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTitle() {
/* 255 */     return this.title;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTitle(String title) {
/* 263 */     this.title = title;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMsg() {
/* 270 */     return this.msg;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMsg(String msg) {
/* 278 */     this.msg = msg;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getType() {
/* 285 */     return this.type;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setType(String type) {
/* 293 */     this.type = type;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AuthRequestContents getRequestContents() {
/* 300 */     return this.requestContents;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getItemType() {
/* 307 */     return this.itemType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setItemType(int itemType) {
/* 314 */     this.itemType = itemType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TransactionInfoData getTransactionInfoData() {
/* 321 */     return this.transactionInfoData;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTransactionInfoData(TransactionInfoData transactionInfoData) {
/* 328 */     this.transactionInfoData = transactionInfoData;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRequestContents(AuthRequestContents requestContents) {
/* 336 */     this.requestContents = requestContents;
/*     */   }
/*     */ 
/*     */   
/*     */   public void validate() throws ReturnCodeException {
/* 341 */     if (this.tsExpired < System.currentTimeMillis()) {
/* 342 */       throw new ReturnCodeException(ReturnCodes.INVALID_PARAMS, "The 'timeout' is greater than now. timeout=" + this.tsExpired + ", current=" + System.currentTimeMillis());
/*     */     }
/*     */     
/* 345 */     if (!PATTERN_USERNAME.matcher(this.userName).matches()) {
/* 346 */       throw new ReturnCodeException(ReturnCodes.INVALID_PARAMS, "The 'userno' is invaild format. userno=" + this.userName);
/*     */     }
/*     */     
/* 349 */     if (this.requestContents.getCustomerCode().length() != 4) {
/* 350 */       throw new ReturnCodeException(ReturnCodes.INVALID_PARAMS, "The 'customer_code' is invaild. customer_code=" + this.requestContents.getCustomerCode());
/*     */     }
/*     */     
/* 353 */     if (!this.requestContents.getCustomerCode().matches("^[0-9]+$")) {
/* 354 */       throw new ReturnCodeException(ReturnCodes.INVALID_PARAMS, "The 'customer_code' is invaild. customer_code=" + this.requestContents.getCustomerCode());
/*     */     }
/*     */     
/* 357 */     if (StringUtils.isEmpty(this.externalIdentifier)) {
/* 358 */       throw new ReturnCodeException(ReturnCodes.INVALID_PARAMS, "The 'tid' is required. ");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 368 */     StringBuilder builder = new StringBuilder();
/* 369 */     builder.append("ExternalServiceItemVO [id=").append(this.id).append(", userId=").append(this.userId).append(", transactionId=").append(this.transactionId).append(", externalIdentifier=")
/* 370 */       .append(this.externalIdentifier).append(", status=").append(this.status).append(", tsReg=").append(this.tsReg).append(", tsExpired=").append(this.tsExpired).append(", tsDone=").append(this.tsDone);
/*     */     
/* 372 */     builder.append(", itemType=").append(this.itemType);
/* 373 */     if (this.itemType == 1) {
/* 374 */       builder.append(", usePush=").append(this.usePush).append(", userName=").append(this.userName).append(", title=").append(this.title).append(", msg=").append(this.msg).append(", type=").append(this.type)
/* 375 */         .append(", requestContents=").append(this.requestContents);
/*     */     } else {
/* 377 */       builder.append(", transactionInfoData=").append(this.transactionInfoData);
/*     */     } 
/*     */     
/* 380 */     builder.append("]");
/* 381 */     return builder.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream out) throws IOException, ClassNotFoundException {
/* 392 */     out.writeInt(2);
/* 393 */     out.writeInt(this.itemType);
/*     */     
/* 395 */     if (this.itemType == 1) {
/* 396 */       out.writeBoolean(this.usePush);
/* 397 */       SerializationUtils.writeSafeUTF(out, this.userName);
/* 398 */       SerializationUtils.writeSafeUTF(out, this.title);
/* 399 */       SerializationUtils.writeSafeUTF(out, this.msg);
/* 400 */       SerializationUtils.writeSafeUTF(out, this.type);
/* 401 */       SerializationUtils.writeSafeObject(out, this.requestContents);
/* 402 */     } else if (this.itemType == 2) {
/* 403 */       SerializationUtils.writeSafeObject(out, this.transactionInfoData);
/* 404 */     } else if (this.itemType == 3) {
/*     */     
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
/* 412 */     int ver = in.readInt();
/* 413 */     if (ver == 1) {
/* 414 */       this.usePush = in.readBoolean();
/* 415 */       this.userName = SerializationUtils.readSafeUTF(in);
/* 416 */       this.title = SerializationUtils.readSafeUTF(in);
/* 417 */       this.msg = SerializationUtils.readSafeUTF(in);
/* 418 */       this.type = SerializationUtils.readSafeUTF(in);
/* 419 */       this.requestContents = (AuthRequestContents)SerializationUtils.readSafeObject(in);
/* 420 */       this.itemType = 1;
/* 421 */     } else if (ver == 2) {
/* 422 */       this.itemType = in.readInt();
/* 423 */       if (this.itemType == 1) {
/* 424 */         this.usePush = in.readBoolean();
/* 425 */         this.userName = SerializationUtils.readSafeUTF(in);
/* 426 */         this.title = SerializationUtils.readSafeUTF(in);
/* 427 */         this.msg = SerializationUtils.readSafeUTF(in);
/* 428 */         this.type = SerializationUtils.readSafeUTF(in);
/* 429 */         this.requestContents = (AuthRequestContents)SerializationUtils.readSafeObject(in);
/* 430 */       } else if (this.itemType == 2) {
/* 431 */         this.transactionInfoData = (TransactionInfoData)SerializationUtils.readSafeObject(in);
/* 432 */       } else if (this.itemType == 3) {
/*     */       
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\vo\ExternalServiceItemVO.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */