package hoopoe.core.zfarm.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标注到model对象上
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ZFarm {

    /**
     * 组成 /api/v1/${value} 完整的URL前缀
     * @return
     */
//    String value();

    /**
     * api前缀
     */
    String apiPrefix();
    /**
     * 主键字段
     */
    String primaryKey() default  "id";

    boolean tree() default false;

    ZAction[] actions() default {};

}
