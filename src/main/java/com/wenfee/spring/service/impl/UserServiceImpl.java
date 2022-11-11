package com.wenfee.spring.service.impl;

import com.wenfee.spring.annotation.Autowired;
import com.wenfee.spring.annotation.Component;
import com.wenfee.spring.annotation.Scope;
import com.wenfee.spring.service.IOrderService;
import com.wenfee.spring.service.IUserService;

/**
 * @author Wenfee
 * @date 2022/11/10
 */
@Component("userService")
@Scope("prototype")
public class UserServiceImpl implements IUserService {

    @Autowired
    private IOrderService orderService;

    @Override
    public void test() {
        System.out.println("@" + this.hashCode() + " ---> " + "UserServiceImpl 执行了 test 方法 ～");
    }

    @Override
    public void testAutowired() {
        System.out.println("Autowired: orderService ---> @" + orderService.hashCode());
    }
}
