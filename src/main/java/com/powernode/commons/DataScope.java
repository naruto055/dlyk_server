package com.powernode.commons;

import java.lang.annotation.*;

/**
 * 数据范围注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataScope {
    // 要在SQL语句的末尾添加一个过滤条件
    // select * from t_user （管理员）
    // select * from t_user tu where tu.id = 2   （普通用户）

    /**
     * 表的别名
     * @return
     */
    public String tableAlias() default "";

    /**
     * 表的字段名
     * @return
     */
    public String tableField() default "";
}
