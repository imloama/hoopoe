package hoopoe.core.zfarm.annotation;

import hoopoe.core.zfarm.FieldType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 属性
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ZField {

    /**
     * 展示类型
     */
    FieldType type() default FieldType.String;
    /**
     * 是否展示，在表格时
     */
    boolean show() default true;

    /**
     * 是否可查询
     * @return
     */
    boolean search() default true;
    /**
     * 是否可编辑，在新增修改时
     * @return
     */
    boolean edit() default true;

    String name() default "";
    String label() default "";
    boolean showLabel() default true;
    String format() default "";//格式化输出
    ZRule[] rules() default {};
    String[] children() default "";

    ZOption[] options() default  {};

    ZRef[] ref() default {};
}
