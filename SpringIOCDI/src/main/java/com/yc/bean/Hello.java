package com.yc.bean;

import com.yc.springframework.stereotype.MyComponent;

/**
 * @program: SpringDemo
 * @description:
 * @author: 阙霞
 * @create: 2021-04-06 20:51
 */
@MyComponent
public class Hello {
    public Hello() {
        System.out.println("hello的无参构造方法");
    }

    public void hello() {
        System.out.println("hello里的hello方法");
    }


}
