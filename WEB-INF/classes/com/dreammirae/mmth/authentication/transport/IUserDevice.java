package WEB-INF.classes.com.dreammirae.mmth.authentication.transport;

import com.dreammirae.mmth.vo.types.DeviceTypes;

public interface IUserDevice {
  String getDeviceId();
  
  void setDeviceId(String paramString);
  
  DeviceTypes getDeviceType();
  
  void setDeviceType(DeviceTypes paramDeviceTypes);
  
  String getModel();
  
  void setModel(String paramString);
  
  String getAlias();
  
  void setAlias(String paramString);
  
  Boolean isDisabled();
  
  void setDisabled(Boolean paramBoolean);
  
  long getTsReg();
  
  void setTsReg(long paramLong);
  
  long getTsUpdated();
  
  void setTsUpdated(long paramLong);
  
  long getTsExpired();
  
  void setTsExpired(long paramLong);
}


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\authentication\transport\IUserDevice.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */