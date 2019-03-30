package hoopoe.core.zfarm.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ZRule {

    /**
     * 是否必填
     * @return
     */
    boolean required() default false;
    /**
     * 正则校验不通过时的提示语
     */
    String errMsg() default "";
    /**
     * 正则表达式校验
     */
    String pattern() default "";
    /**
     * 最短
     */
    int min() default 0;
    /**
     * 最长
     */
    int max() default Integer.MAX_VALUE;


}
