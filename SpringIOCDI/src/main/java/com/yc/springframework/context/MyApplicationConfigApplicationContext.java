package com.yc.springframework.context;


import com.yc.springframework.stereotype.*;

import java.io.File;
import java.io.FileFilter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;

/**
 * @program: SpringDemo
 * @description:
 * @author: 阙霞
 * @create: 2021-04-05 11:41
 */

public class MyApplicationConfigApplicationContext implements MyApplicationContext {
    private Map<String, Object> beanMap = new HashMap<>();
    private Set<Class> manageBeanClasses = new HashSet<>();

    public MyApplicationConfigApplicationContext(Class<?>... componentClasses) throws MalformedURLException {
        try {
            register(componentClasses);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void register(Class<?>[] componentClasses) throws Exception {

        if (componentClasses == null || componentClasses.length <= 0) {
            throw new RuntimeException("没有指定配置类");
        }

        for (Class cl : componentClasses) {
            if (!cl.isAnnotationPresent(MyConfiguration.class)) {
                continue;
            }
            String[] basePackages = getAppConfigBasePackages(cl);//默认扫描传入类的扫描路径
            //路径扫描
            if (cl.isAnnotationPresent(MyComponentScan.class)) {
                MyComponentScan mcs = (MyComponentScan) cl.getAnnotation(MyComponentScan.class);//获取到当前注解
                if (mcs.basePackageClasses() != null && mcs.basePackageClasses().length > 0) {//获取当前注解 basePackageClasses
                    basePackages = mcs.basePackageClasses();

                }

            }

            //处理@Bean的情况
            Object obj = cl.newInstance();
            handleAtMyBean(cl, obj);
            //处理component情况
            // HanderAtComponent(basePackages);
            for (String s : basePackages) {
                scanPackageAndPackageClass(s);
            }
            //继续处理其他的托管bean
            HandelManageBean();

            //请实现
            //源码1 IOC  MyPostConstruct MyPreDestroy


            //版本2 实现di 循环beanMap中的每个bean 找到每个类中的每个由@Autowired注解的方法来实现di
            HandelDI(beanMap);


        }


        /*
        //大概思路  扫描那个路径下的所有的类 然后将其实例化放入到Map中

        Class[] classes = componentClasses;
        for (int i = 0; i < classes.length; i++) {
            Class<?> componentClass = classes[i];
            if (componentClass.getAnnotation(MyConfiguration.class) == null) {
                break;
            }
            String basePackage = "file:///" + System.getProperty("user.dir") + File.separator + "Test" + File.separator + componentClass.getAnnotation(MyComponentScan.class).basePackageClasses()[0].replace(".", "\\");
            ClassLoader cl = new URLClassLoader(new URL[]{new URL(basePackage)});


        }
*/

    }

    private void HandelDI(Map<String, Object> beanMap) throws InvocationTargetException, IllegalAccessException, InstantiationException, NoSuchMethodException {
        Collection<Object> objectCollection = beanMap.values();
        for (Object obj : objectCollection) {
            Class cls = obj.getClass();
            Method[] declaredMethods = cls.getDeclaredMethods();
            for (Method m : declaredMethods) {
                if (m.isAnnotationPresent(MyAutowired.class) && m.getName().startsWith("set")) {
                    invokeAutowirede(m, obj);
                } else if (m.isAnnotationPresent(MyResource.class) && m.getName().startsWith("set")) {
                    invokeResource(m, obj);
                }
            }
            Field[] declaredFields = cls.getDeclaredFields();
            for (Field f : declaredFields) {
                if (f.isAnnotationPresent(MyAutowired.class)) {
                    invokeFeildAutowired(f, obj);
                } else if (f.isAnnotationPresent(MyResource.class)) {
                    invokeFeildResource(f, obj);

                }
            }

        }
    }

    //属性名注入Resource
    private void invokeFeildResource(Field f, Object obj) throws IllegalAccessException {
        //获取属性名
        Class objType = f.getType();
        MyResource annotation = f.getAnnotation(MyResource.class);
        String name = annotation.name();
        Object o = beanMap.get(name);
        Class[] interfaces = o.getClass().getInterfaces();
        for (Class c : interfaces) {
            if (c == objType) {
                f.setAccessible(true);//私有属性
                f.set(obj, o);
            }
        }


    }

    //属性名注入Autowired
    private void invokeFeildAutowired(Field f, Object obj) throws IllegalAccessException {
        //获取属性名
        Class objType = f.getType();
        //从beanMap中取出所有object
        Set<String> keys = beanMap.keySet();
        for (String k : keys) {
            Object o = beanMap.get(k);
            //必须要面向接口编程才可以！
            Class[] interfaces = o.getClass().getInterfaces();
            for (Class c : interfaces) {
                if (c == objType) {
                    f.setAccessible(true);//私有属性
                    f.set(obj, o);
                }
            }
        }
    }

    private void invokeResource(Method m, Object obj) throws InvocationTargetException, IllegalAccessException {
        //Resource
        //取出MyResource中的name属性值 当初beanID
        MyResource mr = m.getAnnotation(MyResource.class);
        String beanID = mr.name();

        //如果没与则取出 m方法中参数的类型名 首字母小写
        if (beanID == null || beanID.equalsIgnoreCase("")) {
            String pname = m.getParameterTypes()[0].getSimpleName();
            beanID = pname.substring(0, 1).toLowerCase() + pname.substring(1);
        }
        //从beanMap中取出
        Object o = beanMap.get(beanID);
        //invoke
        m.invoke(o);
    }

    private void invokeAutowirede(Method m, Object obj) throws InvocationTargetException, IllegalAccessException {
        //取出m的参数的类型
        //不可以使用简单名 必须全路径
        Class typeClass = m.getParameterTypes()[0];
        //从beanMap中训话所有的object
        Set<String> keys = beanMap.keySet();
        for (String k : keys) {
            Object o = beanMap.get(k);
            Class[] interfaces = o.getClass().getInterfaces();
            Class flag = null;
            int i = 0;
            for (Class c : interfaces) {
                if (c == typeClass) {
                    flag = c;
                    i++;
                }
            }
            if (i > 1) {
                throw new RuntimeException("存在两个类");
            }
            if (flag != null) {
                m.invoke(obj, o);
            }

//            if (o.getClass().getName().equalsIgnoreCase(typeClass.getName())) {
//                m.invoke(obj, o);
//            }
        }
        //判断这些Object 是否为参数类型的实例 instanceof
        //如果是从beanMap中取出
        //invoke

    }

    /*
    处理manageBeanClasses 所有Class类  @Component @Service @Repository @Controller
     */
    private void HandelManageBean() throws Exception {
        for (Class s : manageBeanClasses) {
            if (s.isAnnotationPresent(MyComponent.class)) {

                SaveManageBean(s);

            } else if (s.isAnnotationPresent(MyService.class)) {
                SaveManageBean(s);
            } else if (s.isAnnotationPresent(MyReposetory.class)) {
                SaveManageBean(s);
            } else if (s.isAnnotationPresent(MyController.class)) {
                SaveManageBean(s);
            } else {
                continue;
            }
        }

    }

    private void SaveManageBean(Class s) throws IllegalAccessException, InstantiationException {
        Object o = s.newInstance();
        String beanID = s.getSimpleName().substring(0, 1).toLowerCase() + s.getSimpleName().substring(1);
        beanMap.put(beanID, o);
    }

    /*
  扫描包和子包
   */
    private void scanPackageAndPackageClass(String s) throws Exception {
        String packagePath = s.replaceAll("\\.", "/");
        //获取到在target文件下的biz全路径
        Enumeration<URL> files = Thread.currentThread().getContextClassLoader().getResources(packagePath);//获取当前线程的字节码加载器 获取类路径 绝对路径
        while (files.hasMoreElements()) {
            URL url = files.nextElement();
            findClassesInPackage(url.getFile(), s);
        }
    }


    private void findClassesInPackage(String file, String s) throws Exception {
        File f = new File(file);
        //获取到当前路径下的子包和.class文件
        File[] classFiles = f.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return f.getName().endsWith(".class") || f.isDirectory();
            }
        });
        for (File cf : classFiles) {
            if (cf.isDirectory()) {
                findClassesInPackage(cf.getAbsolutePath(), s + "." + cf.getName().substring(cf.getName().lastIndexOf("/") + 1));
            } else {
                //加载cf 作为class文件
                URL[] urls = new URL[]{};
                URLClassLoader url = new URLClassLoader(urls);
                Class c = url.loadClass(s + "." + cf.getName().replace(".class", ""));//com.yc.bean.hello
                manageBeanClasses.add(c);
            }
        }
    }

    /*
    private void HanderAtComponent(String[] basePackages) throws Exception {
        //1.拿出包下所有的类名
        //2.反射
        //3.将类名小写存入键
        for (String s : basePackages) {
            List<Class> classList = new ArrayList<>();
            String path = s.replace(".", "/");
            URL url = Thread.currentThread().getContextClassLoader().getResource(path);
            File file = new File(url.getPath());
            if (!file.exists()) {
                throw new Exception("初始化包名不存在");
            }
            File[] files = file.listFiles(new FileFilter() {
                @Override
                public boolean accept(File pathname) {
                    if (pathname.getName().endsWith(".class")) {
                        return true;
                    }

                    return false;
                }
            });
            for (File f : files) {
                String cname = s + "." + f.getName();
                System.out.println(cname);
            }

        }

    }

     */

    private void handleAtMyBean(Class cls, Object obj) throws InvocationTargetException, IllegalAccessException {
        //获取cls中所有的methed
        Method[] declaredMethods = cls.getDeclaredMethods();
        //循环判断每个method上的是否有@MyBean注解
        for (Method m : declaredMethods) {
            if (m.isAnnotationPresent(MyBean.class)) {
                Object o = m.invoke(obj);
                //TODO 加入处理@MyBean中注解对应的方法所以实例化对应的类中的@MyPostContrcut
                HandleMyPostConstruct(o, o.getClass());//o当前Hello world 对象 o.getclass 他的反射对象
                beanMap.put(m.getName(), o);
            }
        }

        //有 则invoke它 并且将方法名作为键存入beanMap 返回值作为值

    }

    /**
     * 处理bean中@MyPostConstruct注解
     *
     * @param o
     * @param cls
     */
    private void HandleMyPostConstruct(Object o, Class<?> cls) throws InvocationTargetException, IllegalAccessException {
        Method[] declaredMethods = cls.getDeclaredMethods();
        for (Method m : declaredMethods) {
            if (m.isAnnotationPresent(MyPostConstruct.class)) {
                m.invoke(o);
            }
        }

    }

    /**
     * 获取appconfig类所在的包路径
     *
     * @param cl
     * @return
     */
    private String[] getAppConfigBasePackages(Class cl) {
        String[] string = new String[1];
        string[0] = cl.getPackage().getName();//获取所在包的包名
        return string;
    }

    @Override
    public Object getBean(String id) {
        return beanMap.get(id);
    }
}
