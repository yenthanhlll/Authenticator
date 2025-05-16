/*     */ package WEB-INF.classes.com.dreammirae.mmth.authentication.service;
/*     */ 
/*     */ import com.dreammirae.mmth.misc.Base64Utils;
/*     */ import com.dreammirae.mmth.util.io.Closer;
/*     */ import com.google.zxing.BarcodeFormat;
/*     */ import com.google.zxing.EncodeHintType;
/*     */ import com.google.zxing.WriterException;
/*     */ import com.google.zxing.client.j2se.MatrixToImageWriter;
/*     */ import com.google.zxing.common.BitMatrix;
/*     */ import com.google.zxing.qrcode.QRCodeWriter;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.util.Hashtable;
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class QRCodeGenerator
/*     */ {
/*  18 */   private static Hashtable<EncodeHintType, String> hints = new Hashtable<>(1);
/*     */   private static final int DEFAULT_DIMESION = 300;
/*     */   
/*     */   static {
/*  22 */     hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
/*     */   }
/*     */   private static final String DEAFAULT_IMG_FORMAT = "png";
/*     */   
/*     */   private static BitMatrix generateQRCode(String src, int dimension) throws Exception {
/*  27 */     if (dimension < 50 || dimension > 500) {
/*  28 */       dimension = 300;
/*     */     }
/*     */     
/*  31 */     QRCodeWriter writer = new QRCodeWriter();
/*     */     
/*     */     try {
/*  34 */       return writer.encode(src, BarcodeFormat.QR_CODE, dimension, dimension, hints);
/*  35 */     } catch (WriterException e) {
/*  36 */       throw e;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static String generateQRCodeBinary(String tid, long expired, int dimension) throws Exception {
/*  42 */     String qrData = (new QrData(tid, expired)).toJson();
/*     */ 
/*     */     
/*     */     try {
/*  46 */       BitMatrix bm = generateQRCode(qrData, dimension);
/*     */       
/*  48 */       ByteArrayOutputStream os = new ByteArrayOutputStream();
/*     */       try {
/*  50 */         MatrixToImageWriter.writeToStream(bm, "png", os);
/*  51 */         return Base64Utils.encode(os.toByteArray());
/*     */       } finally {
/*  53 */         Closer.close(os);
/*     */       }
/*     */     
/*  56 */     } catch (Exception e) {
/*  57 */       throw e;
/*     */     } 
/*     */   }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void main(String[] args) throws Exception {
/* 110 */     System.out.println(generateQRCodeBinary("sdferewekjsdke", System.currentTimeMillis(), 250));
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\authentication\service\QRCodeGenerator.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */