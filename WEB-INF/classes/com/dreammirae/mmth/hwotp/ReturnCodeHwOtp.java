package WEB-INF.classes.com.dreammirae.mmth.hwotp;

public interface ReturnCodeHwOtp {
  public static final String OK = "0000";
  
  public static final String FAILED = "1000";
  
  public static final String TYPE_ERROR = "1001";
  
  public static final String ISSUE_USER_ID_ERROR = "5001";
  
  public static final String ISSUE_TOKEN_ID_NONE = "5002";
  
  public static final String ISSUE_KEY_DATA_NONE = "5003";
  
  public static final String ISSUE_NOT_AVAILABLE = "5004";
  
  public static final String AUTH_FAILED = "6000";
  
  public static final String AUTH_FAILED_REUSE = "6001";
  
  public static final String CORRECTION_REQUIRE = "6002";
  
  public static final String CORRECTION_FAILED = "6003";
  
  public static final String CORRECTION_FAILED_REUSE = "6004";
  
  public static final String INPUT_PARAMETER_ERROR = "6007";
  
  public static final String OTP_RESPONSE_TYPE_ERROR = "6010";
  
  public static final String AUTH_CHALLENGE_EXPIRED = "6011";
  
  public static final String CHECKSUM_NOT_MATCHED = "6015";
  
  public static final String EXCEEDED_AUTH_ERROR = "6020";
  
  public static final String LOST = "6021";
  
  public static final String LOST_NOT_AVAILABLE = "6022";
  
  public static final String NOT_ISSUED = "6023";
  
  public static final String SUSPEND = "6024";
  
  public static final String DISUSED = "6025";
  
  public static final String SUSPEND_USER_ID_NONE = "7001";
  
  public static final String SUSPEND_TOKEN_ID_NONE = "7002";
  
  public static final String SUSPEND_KEY_DATA_NONE = "7003";
  
  public static final String SUSPEND_NOT_AVAILABLE = "7004";
  
  public static final String DISUSE_USER_ID_NONE = "8001";
  
  public static final String DISUSE_TOKEN_ID_NONE = "8002";
  
  public static final String DISUSE_KEY_DATA_NONE = "8003";
  
  public static final String DISUSE_NOT_AVAILABLE = "8004";
  
  public static final String EXIST_ALREADY = "1003";
}


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\hwotp\ReturnCodeHwOtp.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */