package com.wenfee.spring.service.impl;

import com.wenfee.spring.annotation.Component;
import com.wenfee.spring.annotation.Scope;
import com.wenfee.spring.service.IUserService;

/**
 * @author Wenfee
 * @date 2022/11/10
 */
@Component("userService")
@Scope("prototype")
public class UserServiceImpl implements IUserService {
    @Override
    public void test() {
        System.out.println("UserServiceImpl 执行了 test 方法 ～");
    }
}
