package hoopoe.core.zfarm.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ZRef {

    String value();
    String primaryKey();
    String labelKey();

    /**
     * 如果有值表示为树表结构
     * @return
     */
    String parentKey() default "";

}
