package hoopoe.core.zfarm;

import lombok.Data;

import java.io.Serializable;

/**
 * 操作按钮
 */
@Data
public class Action implements Serializable {

    private String name;
    private String label;
    /**
     * 过滤条件
     */
    private String filter;

}
