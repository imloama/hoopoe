package hoopoe.core.base;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.Data;

import java.io.Serializable;

/**
 * 查询条件封装
 */
@Data
public class Query implements Serializable {

    // 字段
    private String col;
    // 类型，eq noteq like in not in gt lgt 等
    private String type;
    private Object value;


    public static final String TYPE_EQ = "eq";
    public static final String TYPE_NOTEQ = "ne";
    public static final String TYPE_LIKE = "like";
    public static final String TYPE_IN = "in";
    public static final String TYPE_NOTIN = "notin";
    public static final String TYPE_GT = "gt";//大于
    public static final String TYPE_GE = "ge";//大于等于
    public static final String TYPE_LT = "lt";//小于
    public static final String TYPE_LE = "le";//小于等于


    public <M> QueryWrapper<M> fill(QueryWrapper<M> queryWrapper){
        if(TYPE_EQ.equals(this.type)){
            return queryWrapper.eq(this.col, this.value);
        }
        if(TYPE_NOTEQ.equals(this.type)){
            return queryWrapper.ne(this.col, this.value);
        }
        if(TYPE_LIKE.equals(this.type)){
            return queryWrapper.like(this.col, this.value);
        }
        if(TYPE_IN.equals(this.type)){
            return queryWrapper.in(this.col, this.value);
        }
        if(TYPE_NOTIN.equals(this.type)){
            return queryWrapper.notIn(this.col, this.value);
        }
        if(TYPE_GT.equals(this.type)){
            return queryWrapper.gt(this.col, this.value);
        }
        if(TYPE_GE.equals(this.type)){
            return queryWrapper.ge(this.col, this.value);
        }
        if(TYPE_LT.equals(this.type)){
            return queryWrapper.lt(this.col, this.value);
        }
        if(TYPE_LE.equals(this.type)){
            return queryWrapper.le(this.col, this.value);
        }
        return queryWrapper;
    }


}
