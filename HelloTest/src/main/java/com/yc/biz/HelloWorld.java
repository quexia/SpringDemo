package com.yc.biz;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @program: SpringDemo
 * @description:
 * @author: 阙霞
 * @create: 2021-04-04 14:56
 */
@Component //只要加了这个注解 则这个类就可以被spring 容器托管
@Lazy//懒加载 构造方法不会初始化
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)//生命周器 当前表示多例模式 默认为单例模式
//@Named("hw")可以约定一个别名
public class HelloWorld {
    public HelloWorld() {
        System.out.println("无参构造方法");
    }

    public void hello() {
        System.out.println("hello-----");
    }
}
