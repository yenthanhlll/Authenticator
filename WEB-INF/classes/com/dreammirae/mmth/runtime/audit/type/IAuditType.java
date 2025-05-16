package WEB-INF.classes.com.dreammirae.mmth.runtime.audit.type;

import com.dreammirae.mmth.misc.I18nMessage;
import com.dreammirae.mmth.runtime.audit.type.AuditAlarmTypes;

public interface IAuditType {
  I18nMessage getDescriptionMessage(int paramInt, Object... paramVarArgs);
  
  String getActionMsgKey(int paramInt);
  
  AuditAlarmTypes getAuditAlarmTypes();
}


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\runtime\audit\type\IAuditType.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */