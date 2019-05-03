package hoopoe.core.base;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.io.Serializable;
import java.util.List;

public interface BaseService<T extends BaseModel> extends com.github.imloama.mybatisplus.bootext.base.BaseService<T> {

    boolean create(T entity)throws Exception;

    boolean update(T entity)throws Exception;

    default void doAction(String action, Serializable id)throws Exception{

    }

    default boolean delete(Serializable id){
        return this.removeById(id);
    }

    default boolean deleteAll(List<? extends Serializable> ids){
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id", ids);
        return this.remove(queryWrapper);
    }

}
