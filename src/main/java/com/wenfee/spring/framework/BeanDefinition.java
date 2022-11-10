package com.wenfee.spring.framework;

/**
 * @author Wenfee
 * @date 2022/11/10
 * <p>
 * Bean的定义，声明作用域;
 */
public class BeanDefinition {

    /**
     * 作用域
     */
    private String scope;

    /**
     * 是否懒加载
     */
    private boolean isLazy;

    /**
     * Bean 类型;
     */
    private Class beanClass;

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public boolean isLazy() {
        return isLazy;
    }

    public void setLazy(boolean lazy) {
        isLazy = lazy;
    }

    public Class getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(Class beanClass) {
        this.beanClass = beanClass;
    }
}
