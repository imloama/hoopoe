package hoopoe.core.zfarm.annotation;

public @interface ZAction {

    String name();
    String label();
    /**
     * 过滤条件
     */
    String filter() default "";

}
