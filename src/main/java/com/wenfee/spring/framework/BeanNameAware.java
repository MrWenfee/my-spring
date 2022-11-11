package com.wenfee.spring.framework;

/**
 * @author Wenfee
 * @date 2022/11/11
 * <p>
 * 模拟类型注入
 */
public interface BeanNameAware {

    /**
     * 模拟类型注入
     *
     * @param beanName
     */
    void setBeanName(String beanName);

}
