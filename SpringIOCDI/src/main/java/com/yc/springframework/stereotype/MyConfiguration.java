package com.yc.springframework.stereotype;

import java.lang.annotation.*;

/**
 * @program: SpringDemo
 * @description:
 * @author: 阙霞
 * @create: 2021-04-05 11:34
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@MyComponent
public @interface MyConfiguration {
    String value() default "";
}
