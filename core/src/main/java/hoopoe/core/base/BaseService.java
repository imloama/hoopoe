package hoopoe.core.base;


import java.io.Serializable;

public interface BaseService<T extends BaseModel> extends com.github.imloama.mybatisplus.bootext.base.BaseService<T> {

    public boolean create(T entity);

    public boolean update(T entity);

    public default void doAction(String action, Serializable id){

    }


}
