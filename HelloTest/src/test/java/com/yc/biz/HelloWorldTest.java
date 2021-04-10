package com.yc.biz;

import com.yc.AppConfig;
import junit.framework.TestCase;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class HelloWorldTest extends TestCase {
    private ApplicationContext ac;//容器

    @Override
    public void setUp() throws Exception {
        //AnnotationConfigApplicationContext基于注解的配置容器
        ac = new AnnotationConfigApplicationContext(AppConfig.class);
        //读取AppConfig.class->basePackages = "com.yc"-->得到扫描的的路径
        //检查这些包上是否有 @Component 注解 有就进行实例化
        //存到一个Map<String,Object> ==ac
    }

    public void testHello() {
        HelloWorld hw = (HelloWorld) ac.getBean("helloWorld");
        hw.hello();
        HelloWorld hw2 = (HelloWorld) ac.getBean("helloWorld");
        hw2.hello();
        //spring的容器是一个单例模式
    }
}