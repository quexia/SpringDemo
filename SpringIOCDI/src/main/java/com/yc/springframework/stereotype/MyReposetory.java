package com.yc.springframework.stereotype;

import java.lang.annotation.*;

/**
 * @program: SpringDemo
 * @description:
 * @author: 阙霞
 * @create: 2021-04-05 11:29
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyReposetory {
    String value() default "";
}
