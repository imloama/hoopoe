package hoopoe.core.base;

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
    private String value;

}
