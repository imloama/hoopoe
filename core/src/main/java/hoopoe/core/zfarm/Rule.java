package hoopoe.core.zfarm;

import lombok.Data;

import java.io.Serializable;

/**
 * 校验规则
 */
@Data
public class Rule implements Serializable {



    private boolean required;
    /**
     * 正则校验不通过时的提示语
     */
    private String errMsg;
    /**
     * 正则表达式校验
     */
    private String pattern;
    /**
     * 最短
     */
    private Integer min;
    /**
     * 最长
     */
    private Integer max;



}
