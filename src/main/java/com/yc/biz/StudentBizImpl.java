package com.yc.biz;

import com.yc.dao.StudentDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * @program: SpringDemo
 * @description:
 * @author: 阙霞
 * @create: 2021-04-04 14:47
 */

//@Service 第一种方法 可以看appconfig第二种
public class StudentBizImpl {
    /**
     * 默认ByName方式 不指定name属性 IOC容器中必须要有一个符合的bean
     * 并且 id和这里的属性名相同
     */
//    @Resource(name = "studentJpaImpl")//自动装配
    @Autowired //可以添加的地方详情 看源码
    @Qualifier("studentJpaImpl")//如果一个接口被多个对象实现 因为可能有多个对象可以注入 所以这里必须用@Qualifier 来声明注入的对象
    //@Inject 他是javax包下的 需要另外导入Javax.Inject的依赖
    //@Named("studentJpaImpl")//如果一个接口被多个对象实现 因为可能有多个对象可以注入 所以这里必须用@Qualifier 来声明注入的对象
    //@ReSource
    private StudentDAO studentDAO;

    public StudentBizImpl() {
    }

    public StudentBizImpl(StudentDAO studentDAO) {
        this.studentDAO = studentDAO;
    }

    public StudentDAO getStudentDAO() {
        return studentDAO;
    }

    public void setStudentDAO(StudentDAO studentDAO) {
        this.studentDAO = studentDAO;
    }


    public int add(String name) {
        System.out.println("业务层--------------");
        int result = studentDAO.add(name);
        System.out.println("业务层结束--------------");
        return result;
    }


    public void update(String name) {
        System.out.println("业务层--------------");
        studentDAO.update(name);
        System.out.println("业务层结束--------------");
    }

}
