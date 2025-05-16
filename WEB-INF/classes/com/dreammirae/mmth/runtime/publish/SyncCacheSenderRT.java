package WEB-INF.classes.com.dreammirae.mmth.runtime.publish;

import com.dreammirae.mmth.runtime.code.SyncCacheTypes;

public interface SyncCacheSenderRT {
  void initialize();
  
  void sendEvent(SyncCacheTypes paramSyncCacheTypes);
  
  void terminate();
  
  void refresh();
}


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\runtime\publish\SyncCacheSenderRT.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */