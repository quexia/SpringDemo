package com.yc.biz;

import com.yc.AppConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
@DependsOn("helloWorld")//只是一个描述依赖HelloWorld类
public class HelloWorldTest2 {
    @Autowired
    private HelloWorld helloWorld;//默认情况下所有的bean 都是 eager（勤加载）
    @Autowired
    private HelloWorld helloWorld2;//默认情况下所有的bean 都是 eager（勤加载）

    @Test
    public void testHello() {
        System.out.println("Aaaaaa");
    }
}