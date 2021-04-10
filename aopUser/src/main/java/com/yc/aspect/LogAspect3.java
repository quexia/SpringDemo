package com.yc.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @program: SpringDemo
 * @description:
 * @author: 阙霞
 * @create: 2021-04-09 19:35
 */
//切面类 你要增强的功能
@Aspect//表示这个类是一个切面
@Component//被IOC托管
@Order(1)//数值越小 优先级越高
public class LogAspect3 {
    @Pointcut("execution(* com.yc.biz.StudentBizImpl.add*(..))")//切入点表达式  *任意修饰符号
    //方法一般只私有 只标明切入点
    private void add() {

    }

    @Pointcut("execution(* com.yc.biz.StudentBizImpl.update*(..))")//切入点表达式  *任意修饰符号
    //方法一般只私有 只标明切入点
    private void update() {

    }

    @Pointcut("add() || update()")//切入点表达式  *任意修饰符号
    //方法一般只私有 只标明切入点
    private void AddAndUpdate() {

    }

    /**
     * 切腹点表达式的语法 ？代表出现0次或者1
     * 修饰符匹配（modifier-pattern?）
     * 返回值匹配（ret-type-pattern）可以为*表示任何返回值,全路径的类名等
     * 类路径匹配（declaring-type-pattern?）
     * 方法名匹配（name-pattern）可以指定方法名 或者 *代表所有, set* 代表以set开头的所有方法
     * 参数匹配（(param-pattern)）可以指定具体的参数类型，多个参数间用“,”隔开，各个参数也可以用“*”来表示匹配任意类型的参数，如(String)表示匹配一个String参数的方法；(*,String) 表示匹配有两个参数的方法，第一个参数可以是任意类型，而第二个参数是String类型；可以用(..)表示零个或多个任意参数
     * 异常类型匹配（throws-pattern?）
     * 其中后面跟着“?”的是可选项
     */


    //增强的声明
    @Before("com.yc.aspect.LogAspect3.AddAndUpdate()")//AddAndUpdate()也可以
    public void log(JoinPoint jp) {
        System.out.println("前置3增强");
        Object target = jp.getTarget();
        Signature signature = jp.getSignature();
        System.out.println(target + "-----" + signature);
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        System.out.println(sdf.format(date));
        System.out.println("前置3增强结束");
    }

    @After("com.yc.aspect.LogAspect3.AddAndUpdate()")//AddAndUpdate()也可以
    public void bye() {
        System.out.println("后置3增强");
        System.out.println("bye3");
        System.out.println("后置3增强结束");
    }

    @Around("execution(* com.yc.biz.StudentBizImpl.find*(..))")
    public Object compute(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("环绕前置增强3");
        long start = System.currentTimeMillis();
        Object proceed = pjp.proceed();//相当于运行一个方法
        System.out.println("环绕后置增强3");
        long end = System.currentTimeMillis();
        System.out.println("运行时间3：" + (end - start));
        return proceed;
    }
}
