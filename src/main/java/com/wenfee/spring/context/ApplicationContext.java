package com.wenfee.spring.context;

import com.wenfee.spring.BeanScopeEnum;
import com.wenfee.spring.annotation.Component;
import com.wenfee.spring.annotation.ComponentScan;
import com.wenfee.spring.annotation.Lazy;
import com.wenfee.spring.annotation.Scope;
import com.wenfee.spring.framework.BeanDefinition;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Wenfee
 * @date 2022/11/10
 */
public class ApplicationContext {

    private Class configClass;

    /**
     * 解析后的Bean缓存池
     */
    private Map<String, BeanDefinition> beanDefinitionMap = new HashMap<String, BeanDefinition>();

    /**
     * 单例池
     */
    private Map<String, Object> singletonObj = new HashMap<String, Object>();

    /**
     * 1. 通过 Class 获取 ComponentScan 扫描的路径;
     * 2. 获取到扫描路径下的所有 字节码class 文件;
     * 3. 通过类加载器，加载字节码文件;
     * 4. 判断 是否 存在 Component 注解;
     *
     * @param configClass
     */
    public ApplicationContext(Class configClass) {
        this.configClass = configClass;

        scan(configClass, beanDefinitionMap);

        //创建非懒加载的单例bean
        createNonLazySingleton(beanDefinitionMap);
    }

    private void createNonLazySingleton(Map<String, BeanDefinition> beanDefinitionMap) {
        for (String beanName : beanDefinitionMap.keySet()) {
            BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
            if(Objects.equals(beanDefinition.getScope(), BeanScopeEnum.SINGLETON.getScope()) && !beanDefinition.isLazy()) {
                //创建bean
                Object valueBean = createBean(beanDefinition);
                singletonObj.put(beanName,valueBean);
            }
        }
    }

    private static void scan(Class configClass, Map<String, BeanDefinition> beanDefinitionMap) {
        // 判断是否存在  ComponentScan 注解
        if (configClass.isAnnotationPresent(ComponentScan.class)) {
            // 获取 ComponentScan
            ComponentScan componentScanAnnotation = (ComponentScan) configClass.getAnnotation(ComponentScan.class);
            // 扫描路径
            String scanPath = componentScanAnnotation.value();
            System.out.println("扫描路径：" + scanPath);
            // 格式化为：类加载路径
            String path = scanPath.replace('.', '/');

            ClassLoader classLoader = ApplicationContext.class.getClassLoader();
            // 查找具有给定名称的资源。
            // 资源是一些数据（图像、音频、文本等），类代码可以以独立于代码位置的方式访问这些数据。
            URL url = classLoader.getResource(path);
            System.out.println("根据类加载获取完整路径: " + url);
            File file = new File(url.getFile());

            // 遍历路径下的所有文件
            for (File f : file.listFiles()) {
                String absolutePath = f.getAbsolutePath();
                // 获取到字节码文件; class结尾的文件;
                if (absolutePath.endsWith(".class")) {
                    // 截取com...class
                    String subPath = absolutePath.substring(absolutePath.indexOf("com"), absolutePath.indexOf(".class"));
                    // com/wenfee/spring/service/impl/UserServiceImpl
                    System.out.println(subPath);
                    subPath = subPath.replace("/", ".");

                    // 拿到要添加到容器中的 Bean
                    try {
                        Class aClass = classLoader.loadClass(subPath);

                        // 存在 Component 注解需要添加到容器中
                        if (aClass.isAnnotationPresent(Component.class)) {

                            BeanDefinition beanDefinition = new BeanDefinition();
                            beanDefinition.setBeanClass(aClass);

                            Component componentAnnotation = (Component) aClass.getAnnotation(Component.class);
                            System.out.println("使用 Component 注解的类: " + componentAnnotation);
                            // 拿到注解上到值; BeanName;
                            String beanName = componentAnnotation.value();
                            System.out.println("Component 注解的值: " + beanName);

                            // 懒加载
                            if (aClass.isAnnotationPresent(Lazy.class)) {
                                beanDefinition.setLazy(true);
                            }

                            // Bean 作用域;
                            if (aClass.isAnnotationPresent(Scope.class)) {
                                Scope scope = (Scope) aClass.getAnnotation(Scope.class);
                                String scopeValue = scope.value();
                                beanDefinition.setScope(scopeValue);
                            }

                            beanDefinitionMap.put(beanName, beanDefinition);
                        }
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
    }

    public Object getBean(String beanName) {
        Object o = null;
        if (!beanDefinitionMap.containsKey(beanName)) {
            throw new NullPointerException();
        }

        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);

        if (Objects.equals(beanDefinition.getScope(), BeanScopeEnum.PROTOTYPE.getScope())) {
            // 原型
            System.out.println("原型：new一个对象");
            o = createBean(beanDefinition);

        } else if (Objects.equals(beanDefinition.getScope(), BeanScopeEnum.SINGLETON.getScope())) {
            // 单例
            o = singletonObj.get(beanName);
        }
        return o;
    }

    private Object createBean(BeanDefinition beanDefinition) {
        Class beanClass = beanDefinition.getBeanClass();
        Object o = null;
        try {
            // 通过无参构造，生成一个实例;
            o = beanClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return o;
    }
}
