package hoopoe.core.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

public abstract class BaseServiceImpl<M extends BaseMapper<T>, T extends BaseModel>
        extends com.github.imloama.mybatisplus.bootext.base.BaseServiceImpl<M,T>
        implements BaseService<T>{

    @Override
    public boolean create(T entity) {
        return this.save(entity);
    }

    @Override
    public boolean update(T entity) {
        return this.updateById(entity);
    }



}
