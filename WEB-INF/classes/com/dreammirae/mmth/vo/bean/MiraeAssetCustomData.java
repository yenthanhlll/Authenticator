/*     */ package WEB-INF.classes.com.dreammirae.mmth.vo.bean;
/*     */ 
/*     */ import com.dreammirae.mmth.vo.bean.ICustomLogData;
/*     */ 
/*     */ public class MiraeAssetCustomData implements ICustomLogData {
/*     */   private String REG_DATE;
/*     */   private String REG_TIME;
/*     */   private Integer HIST_NO;
/*     */   private String SRVR_IP_ADDR;
/*     */   private String TERM_ID;
/*     */   private String CNCT_MEDIA_SECT;
/*     */   private String SVC_MEDIA_SECT;
/*     */   private String END_YN;
/*     */   private String END_DATE;
/*     */   private String END_TIME;
/*     */   private String IP_ADDR;
/*     */   private String NETWORK_IP_ADDR;
/*     */   private String UNIV_UNIQ_IDEN;
/*     */   private String DUP_YN;
/*     */   private int DBLE_CNCT_TMS;
/*     */   private String SIGN_OPTN;
/*     */   private String CNCT_DPTBRN_CD;
/*     */   private String PGM_ID;
/*     */   private String OCS_W_DATE;
/*     */   private String OCS_W_TIME;
/*     */   private String SIGN_ENCODE;
/*     */   private String MAC_ADDR;
/*     */   
/*     */   public MiraeAssetCustomData(String macAddress, String ip) {
/*  30 */     this.MAC_ADDR = macAddress;
/*  31 */     this.IP_ADDR = ip;
/*     */   }
/*     */   
/*     */   public String getREG_DATE() {
/*  35 */     return this.REG_DATE;
/*     */   }
/*     */   public void setREG_DATE(String rEG_DATE) {
/*  38 */     this.REG_DATE = rEG_DATE;
/*     */   }
/*     */   public String getREG_TIME() {
/*  41 */     return this.REG_TIME;
/*     */   }
/*     */   public void setREG_TIME(String rEG_TIME) {
/*  44 */     this.REG_TIME = rEG_TIME;
/*     */   }
/*     */   public Integer getHIST_NO() {
/*  47 */     return this.HIST_NO;
/*     */   }
/*     */   public void setHIST_NO(Integer hIST_NO) {
/*  50 */     this.HIST_NO = hIST_NO;
/*     */   }
/*     */   public String getSRVR_IP_ADDR() {
/*  53 */     return this.SRVR_IP_ADDR;
/*     */   }
/*     */   public void setSRVR_IP_ADDR(String sRVR_IP_ADDR) {
/*  56 */     this.SRVR_IP_ADDR = sRVR_IP_ADDR;
/*     */   }
/*     */   public String getTERM_ID() {
/*  59 */     return this.TERM_ID;
/*     */   }
/*     */   public void setTERM_ID(String tERM_ID) {
/*  62 */     this.TERM_ID = tERM_ID;
/*     */   }
/*     */   public String getCNCT_MEDIA_SECT() {
/*  65 */     return this.CNCT_MEDIA_SECT;
/*     */   }
/*     */   public void setCNCT_MEDIA_SECT(String cNCT_MEDIA_SECT) {
/*  68 */     this.CNCT_MEDIA_SECT = cNCT_MEDIA_SECT;
/*     */   }
/*     */   public String getSVC_MEDIA_SECT() {
/*  71 */     return this.SVC_MEDIA_SECT;
/*     */   }
/*     */   public void setSVC_MEDIA_SECT(String sVC_MEDIA_SECT) {
/*  74 */     this.SVC_MEDIA_SECT = sVC_MEDIA_SECT;
/*     */   }
/*     */   public String getEND_YN() {
/*  77 */     return this.END_YN;
/*     */   }
/*     */   public void setEND_YN(String eND_YN) {
/*  80 */     this.END_YN = eND_YN;
/*     */   }
/*     */   public String getEND_DATE() {
/*  83 */     return this.END_DATE;
/*     */   }
/*     */   public void setEND_DATE(String eND_DATE) {
/*  86 */     this.END_DATE = eND_DATE;
/*     */   }
/*     */   public String getEND_TIME() {
/*  89 */     return this.END_TIME;
/*     */   }
/*     */   public void setEND_TIME(String eND_TIME) {
/*  92 */     this.END_TIME = eND_TIME;
/*     */   }
/*     */   public String getIP_ADDR() {
/*  95 */     return this.IP_ADDR;
/*     */   }
/*     */   public void setIP_ADDR(String iP_ADDR) {
/*  98 */     this.IP_ADDR = iP_ADDR;
/*     */   }
/*     */   public String getNETWORK_IP_ADDR() {
/* 101 */     return this.NETWORK_IP_ADDR;
/*     */   }
/*     */   public void setNETWORK_IP_ADDR(String nETWORK_IP_ADDR) {
/* 104 */     this.NETWORK_IP_ADDR = nETWORK_IP_ADDR;
/*     */   }
/*     */   public String getUNIV_UNIQ_IDEN() {
/* 107 */     return this.UNIV_UNIQ_IDEN;
/*     */   }
/*     */   public void setUNIV_UNIQ_IDEN(String uNIV_UNIQ_IDEN) {
/* 110 */     this.UNIV_UNIQ_IDEN = uNIV_UNIQ_IDEN;
/*     */   }
/*     */   public String getDUP_YN() {
/* 113 */     return this.DUP_YN;
/*     */   }
/*     */   public void setDUP_YN(String dUP_YN) {
/* 116 */     this.DUP_YN = dUP_YN;
/*     */   }
/*     */   public int getDBLE_CNCT_TMS() {
/* 119 */     return this.DBLE_CNCT_TMS;
/*     */   }
/*     */   public void setDBLE_CNCT_TMS(int dBLE_CNCT_TMS) {
/* 122 */     this.DBLE_CNCT_TMS = dBLE_CNCT_TMS;
/*     */   }
/*     */   public String getSIGN_OPTN() {
/* 125 */     return this.SIGN_OPTN;
/*     */   }
/*     */   public void setSIGN_OPTN(String sIGN_OPTN) {
/* 128 */     this.SIGN_OPTN = sIGN_OPTN;
/*     */   }
/*     */   public String getCNCT_DPTBRN_CD() {
/* 131 */     return this.CNCT_DPTBRN_CD;
/*     */   }
/*     */   public void setCNCT_DPTBRN_CD(String cNCT_DPTBRN_CD) {
/* 134 */     this.CNCT_DPTBRN_CD = cNCT_DPTBRN_CD;
/*     */   }
/*     */   public String getPGM_ID() {
/* 137 */     return this.PGM_ID;
/*     */   }
/*     */   public void setPGM_ID(String pGM_ID) {
/* 140 */     this.PGM_ID = pGM_ID;
/*     */   }
/*     */   public String getOCS_W_DATE() {
/* 143 */     return this.OCS_W_DATE;
/*     */   }
/*     */   public void setOCS_W_DATE(String oCS_W_DATE) {
/* 146 */     this.OCS_W_DATE = oCS_W_DATE;
/*     */   }
/*     */   public String getOCS_W_TIME() {
/* 149 */     return this.OCS_W_TIME;
/*     */   }
/*     */   public void setOCS_W_TIME(String oCS_W_TIME) {
/* 152 */     this.OCS_W_TIME = oCS_W_TIME;
/*     */   }
/*     */   public String getSIGN_ENCODE() {
/* 155 */     return this.SIGN_ENCODE;
/*     */   }
/*     */   public void setSIGN_ENCODE(String sIGN_ENCODE) {
/* 158 */     this.SIGN_ENCODE = sIGN_ENCODE;
/*     */   }
/*     */   public String getMAC_ADDR() {
/* 161 */     return this.MAC_ADDR;
/*     */   }
/*     */   public void setMAC_ADDR(String mAC_ADDR) {
/* 164 */     this.MAC_ADDR = mAC_ADDR;
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\vo\bean\MiraeAssetCustomData.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */