package com.yc.dao;

import com.yc.AppConfig;
import com.yc.biz.StudentBizImpl;
import junit.framework.TestCase;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class StudentJpaImplTest extends TestCase {
    private ApplicationContext ac;
    private StudentDAO studentDAO;
    private StudentBizImpl studentBiz;

    @Override
    public void setUp() throws Exception {
        ac = new AnnotationConfigApplicationContext(AppConfig.class);

        studentDAO = (StudentDAO) ac.getBean("studentJpaImpl");
        //1.能否自动完成实例化对象 -》IOC容器控制反转 由容器实例化对象 由容器来完成


        studentBiz = (StudentBizImpl) ac.getBean("studentBiz");//后面相当于bean里面的键

        //2.能否制动完成装配过程 --》DI依赖注入 -》由容器装配对象
        //     studentBiz.setStudentDAO(studentDAO);

    }

    public void testAdd() {
        studentBiz.add("张三");
    }

    public void testUpdate() {
    }
}