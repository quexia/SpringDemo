package com.yc.bean.sonbean;

import com.yc.springframework.stereotype.MyPostConstruct;
import com.yc.springframework.stereotype.MyPreDestroy;

/**
 * @program: SpringDemo
 * @description:
 * @author: 阙霞
 * @create: 2021-04-05 11:51
 */

public class HelloWorld {
    @MyPostConstruct
    public void setUp() {
        System.out.println("MyPostConstruct");
    }

    @MyPreDestroy
    public void destroy() {
        System.out.println("MyPreDestroy");
    }

    public HelloWorld() {
        System.out.println("hello world无参构造方法");
    }

    public void show() {
        System.out.println("hellow");
    }
}
