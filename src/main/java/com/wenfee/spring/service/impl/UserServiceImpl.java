package com.wenfee.spring.service.impl;

import com.wenfee.spring.annotation.Autowired;
import com.wenfee.spring.annotation.Component;
import com.wenfee.spring.annotation.Scope;
import com.wenfee.spring.framework.BeanNameAware;
import com.wenfee.spring.service.IOrderService;
import com.wenfee.spring.service.IUserService;

/**
 * @author Wenfee
 * @date 2022/11/10
 */
@Component
@Scope("prototype")
// @Scope
public class UserServiceImpl implements IUserService, BeanNameAware {

    @Autowired
    private IOrderService orderService;

    private String beanName;

    @Override
    public void test() {
        System.out.println("@" + this.hashCode() + " ---> " + "UserServiceImpl 执行了 test 方法 ～");
    }

    @Override
    public void testAutowired() {
        System.out.println("Autowired: orderService ---> @" + orderService.hashCode());
    }

    /**
     * 模拟通过类型解决依赖
     *
     * @param beanName
     */
    @Override
    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    @Override
    public String getBeanName() {
        return beanName;
    }
}
