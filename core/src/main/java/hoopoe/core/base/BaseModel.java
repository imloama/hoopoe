package hoopoe.core.base;

import com.google.common.collect.Lists;

import java.io.Serializable;
import java.util.List;

/**
 * 模型层
 * @param <T>
 * @param <ID>
 */
public abstract class BaseModel<T extends BaseModel<T,ID>, ID extends Serializable>
        extends com.github.imloama.mybatisplus.bootext.base.BaseModel<T,ID> {


    /**
     * 返回支持模糊查询的字段
     * @return
     */
    public List<String> searchColumns(){
        return Lists.newArrayList();
    }

}
