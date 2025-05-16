package WEB-INF.classes.com.dreammirae.mmth.db.dao;

import com.dreammirae.mmth.exception.InternalDBException;
import java.util.List;

public interface IViewDao<T, K> {
  void save(T paramT) throws InternalDBException;
  
  T getOneByPK(K paramK) throws InternalDBException;
  
  T getOneByObj(T paramT) throws InternalDBException;
  
  List<T> getViewContent(int paramInt1, int paramInt2, T paramT, Long paramLong1, Long paramLong2) throws InternalDBException;
  
  int getViewConentCount(T paramT, Long paramLong1, Long paramLong2) throws InternalDBException;
  
  int deleteByPk(K paramK) throws InternalDBException;
  
  int delete(T paramT) throws InternalDBException;
}


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\db\dao\IViewDao.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */