package com.yc.dao;

import org.springframework.stereotype.Repository;

import java.util.Random;

/**
 * @program: SpringDemo
 * @description:
 * @author: 阙霞
 * @create: 2021-04-04 14:45
 */
//@Component
@Repository//异常转换从 Exception 自动转为runtimeExcetion
public class StudentMybatismpl implements StudentDAO {

    @Override
    public int add(String name) {
        System.out.println("batis 添加学生" + name);
        Random random = new Random();
        return random.nextInt();
    }

    @Override
    public void update(String name) {
        System.out.println("batis 修改学生" + name);
    }

    @Override
    public void find(String name) {
        System.out.println("batis 查找学生" + name);
    }


}
