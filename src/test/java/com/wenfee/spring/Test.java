package com.wenfee.spring;

import com.wenfee.spring.config.AppConfig;
import com.wenfee.spring.context.ApplicationContext;
import com.wenfee.spring.service.IUserService;
import com.wenfee.spring.service.impl.UserServiceImpl;

/**
 * @author Wenfee
 * @date 2022/11/10
 */
public class Test {
    public static void main(String[] args) {
        testIOC();
    }

    /**
     * 测试- spring IOC功能
     */
    public static void testIOC() {
        ApplicationContext applicationContext = new ApplicationContext(AppConfig.class);
        // IUserService 作用域是：原型
        IUserService userService = (UserServiceImpl) applicationContext.getBean("userService");
        IUserService userService2 = (UserServiceImpl) applicationContext.getBean("userService");
        // 依赖
        userService.test();
        userService2.test();

        // 属性注入
        userService.testAutowired();
        userService2.testAutowired();
    }
}
