package com.yc;

import com.yc.biz.StudentBizImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @program: SpringDemo
 * @description:
 * @author: 阙霞
 * @create: 2021-04-04 15:22
 */
@Configuration
@ComponentScan(basePackages = "com.yc")
public class AppConfig {
    //bean 容器实际是一个Map<Sttring,Objrct>
    //studentBiz 是键名
    //IOC操作
    @Bean
    @Resource
    public StudentBizImpl studentBiz() {
        return new StudentBizImpl();
    }
}
