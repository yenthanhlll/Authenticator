/*     */ package WEB-INF.classes.com.dreammirae.mmth.web.controller;
/*     */ 
/*     */ import com.dreammirae.mmth.MMTHConstants;
/*     */ import com.dreammirae.mmth.config.Commons;
/*     */ import com.dreammirae.mmth.util.io.Closer;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.net.Socket;
/*     */ import org.bouncycastle.util.Arrays;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.web.bind.annotation.RequestBody;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ import org.springframework.web.bind.annotation.RequestMethod;
/*     */ import org.springframework.web.bind.annotation.ResponseBody;
/*     */ 
/*     */ 
/*     */ 
/*     */ @Controller
/*     */ public class IndexController
/*     */ {
/*     */   @RequestMapping(value = {"/login"}, method = {RequestMethod.GET, RequestMethod.POST})
/*     */   public String login() {
/*  23 */     return "login";
/*     */   }
/*     */   
/*     */   @RequestMapping(value = {"/unsupported"}, method = {RequestMethod.GET, RequestMethod.POST})
/*     */   public String unsuppored() {
/*  28 */     return "common/unsupported";
/*     */   }
/*     */   
/*     */   @RequestMapping(value = {"/dev/tran"}, method = {RequestMethod.GET})
/*     */   public String test5001() {
/*  33 */     return "tran";
/*     */   }
/*     */   
/*     */   @RequestMapping(value = {"/qrcode"}, method = {RequestMethod.GET})
/*     */   public String qrcode() {
/*  38 */     return "qrcode";
/*     */   }
/*     */   
/*     */   @RequestMapping(value = {"/test"}, method = {RequestMethod.GET})
/*     */   public String test() {
/*  43 */     return "test";
/*     */   }
/*     */   
/*     */   @RequestMapping(value = {"/test2"}, method = {RequestMethod.GET})
/*     */   public String test2() {
/*  48 */     return "test2";
/*     */   }
/*     */   
/*     */   @RequestMapping(value = {"/dev/tran"}, method = {RequestMethod.POST}, consumes = {"text/plain; charset=MS949"}, produces = {"text/plain; charset=MS949"})
/*     */   @ResponseBody
/*     */   public String test5001Data(@RequestBody String data) {
/*  54 */     byte[] received = data.getBytes(MMTHConstants.MS949_CS);
/*  55 */     byte[] send = new byte[2157];
/*     */     
/*  57 */     Arrays.fill(send, (byte)32);
/*  58 */     System.arraycopy(received, 0, send, 0, received.length);
/*     */     
/*     */     try {
/*  61 */       return send5001Payload(send);
/*  62 */     } catch (Exception e) {
/*  63 */       return "failed";
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/dev/votp"}, method = {RequestMethod.POST}, consumes = {"text/plain; charset=MS949"}, produces = {"text/plain; charset=MS949"})
/*     */   @ResponseBody
/*     */   public String test9003Data(@RequestBody String data) {
/*  71 */     byte[] received = data.getBytes(MMTHConstants.MS949_CS);
/*  72 */     byte[] send = new byte[114];
/*     */     
/*  74 */     Arrays.fill(send, (byte)32);
/*  75 */     System.arraycopy(received, 0, send, 0, received.length);
/*     */     
/*     */     try {
/*  78 */       return send9003Payload(send);
/*  79 */     } catch (Exception e) {
/*  80 */       return "failed";
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private String send5001Payload(byte[] sendBody) throws Exception {
/*  87 */     String d = Commons.getFormatDate(System.currentTimeMillis());
/*  88 */     String t = Commons.getFormatTime(System.currentTimeMillis());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  96 */     byte[] sendHeader = { 50, 49, 56, 48, 66, 79, 84, 80, 53, 48, 48, 49, 83, (byte)d.charAt(0), (byte)d.charAt(1), (byte)d.charAt(2), (byte)d.charAt(3), (byte)d.charAt(4), (byte)d.charAt(5), (byte)d.charAt(6), (byte)d.charAt(7), (byte)t.charAt(0), (byte)t.charAt(1), (byte)t.charAt(2), (byte)t.charAt(3), (byte)t.charAt(4), (byte)t.charAt(5) };
/*     */ 
/*     */ 
/*     */     
/* 100 */     byte[] payload = new byte[2184];
/* 101 */     System.arraycopy(sendHeader, 0, payload, 0, sendHeader.length);
/* 102 */     System.arraycopy(sendBody, 0, payload, sendHeader.length, sendBody.length);
/*     */     
/* 104 */     Socket socket = null;
/* 105 */     OutputStream os = null;
/* 106 */     InputStream is = null;
/*     */     try {
/* 108 */       socket = new Socket("192.168.1.101", 10000);
/* 109 */       os = socket.getOutputStream();
/* 110 */       os.write(payload);
/* 111 */       os.flush();
/*     */ 
/*     */       
/* 114 */       Thread.sleep(3000L);
/*     */ 
/*     */       
/* 117 */       is = socket.getInputStream();
/* 118 */       int len = is.available();
/*     */       
/* 120 */       byte[] received = new byte[len];
/*     */       
/* 122 */       is.read(received);
/*     */       
/* 124 */       return new String(received, MMTHConstants.MS949_CS);
/*     */     }
/* 126 */     catch (Exception e) {
/* 127 */       throw e;
/*     */     } finally {
/* 129 */       Closer.close(os);
/* 130 */       Closer.close(is);
/* 131 */       Closer.close(socket);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String send9003Payload(byte[] sendBody) throws Exception {
/* 140 */     String d = Commons.getFormatDate(System.currentTimeMillis());
/* 141 */     String t = Commons.getFormatTime(System.currentTimeMillis());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 149 */     byte[] sendHeader = { 48, 49, 51, 55, 66, 79, 84, 80, 57, 48, 48, 51, 83, (byte)d.charAt(0), (byte)d.charAt(1), (byte)d.charAt(2), (byte)d.charAt(3), (byte)d.charAt(4), (byte)d.charAt(5), (byte)d.charAt(6), (byte)d.charAt(7), (byte)t.charAt(0), (byte)t.charAt(1), (byte)t.charAt(2), (byte)t.charAt(3), (byte)t.charAt(4), (byte)t.charAt(5) };
/*     */ 
/*     */ 
/*     */     
/* 153 */     byte[] payload = new byte[141];
/* 154 */     System.arraycopy(sendHeader, 0, payload, 0, sendHeader.length);
/* 155 */     System.arraycopy(sendBody, 0, payload, sendHeader.length, sendBody.length);
/*     */     
/* 157 */     Socket socket = null;
/* 158 */     OutputStream os = null;
/* 159 */     InputStream is = null;
/*     */     try {
/* 161 */       socket = new Socket("192.168.1.101", 10000);
/* 162 */       os = socket.getOutputStream();
/* 163 */       os.write(payload);
/* 164 */       os.flush();
/*     */ 
/*     */       
/* 167 */       Thread.sleep(3000L);
/*     */ 
/*     */       
/* 170 */       is = socket.getInputStream();
/* 171 */       int len = is.available();
/*     */       
/* 173 */       byte[] received = new byte[len];
/*     */       
/* 175 */       is.read(received);
/*     */       
/* 177 */       return new String(received, MMTHConstants.MS949_CS);
/*     */     }
/* 179 */     catch (Exception e) {
/* 180 */       throw e;
/*     */     } finally {
/* 182 */       Closer.close(os);
/* 183 */       Closer.close(is);
/* 184 */       Closer.close(socket);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\web\controller\IndexController.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */