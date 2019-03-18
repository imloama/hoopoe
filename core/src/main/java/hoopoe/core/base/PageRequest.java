package hoopoe.core.base;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 分页查询条件
 */
@Data
public class PageRequest implements Serializable {

    //查询条件，模糊查询
    private String search;
    //精确查询
    private List<Query> query;
    private String orderby = "id";
    private boolean asc = true;
    //区间查询
    private int pageNum;//请求的页码
    private int pageSize = 50;//每页多少条记录



}
