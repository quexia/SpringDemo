package com.yc;

import com.yc.springframework.stereotype.MyComponentScan;
import com.yc.springframework.stereotype.MyConfiguration;

/**
 * @program: SpringDemo
 * @description:
 * @author: 阙霞
 * @create: 2021-04-05 14:32
 */
@MyConfiguration
@MyComponentScan(basePackageClasses = {"com.yc.biz", "com.yc.dao",})
public class MyAppConfig {
//    @MyBean
//    public HelloWorld helloWorld() {
//        return new HelloWorld();
//    }
}
