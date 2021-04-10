package com.yc;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @program: SpringDemo
 * @description:
 * @author: 阙霞
 * @create: 2021-04-04 15:11
 */
@Configuration//表示当前这个类是一个配置类
@ComponentScan(basePackages = "com.yc")//将来要托管的bean要扫描的包 以及子包
public class AppConfig {
}
