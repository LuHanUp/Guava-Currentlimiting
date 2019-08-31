package com.luhan.annotation;

import java.lang.annotation.*;

/**
 * 〈RateLimit的注解类,可以指定方法限流〉<br>
 *
 * @author luHan
 * @create 2019-08-29 19:32
 * @since 1.0.0
 */
@Target(value = ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RateLimit {
    /**
     * 默认每秒10个
     * @return
     */
    int value() default 10;
}
