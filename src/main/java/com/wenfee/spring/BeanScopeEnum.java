package com.wenfee.spring;

/**
 * @author Wenfee
 * @date 2022/11/10
 * Bean 作用范围
 */
public enum BeanScopeEnum {

    /**
     * 单例
     */
    SINGLETON("singleton"),

    /**
     * 原型
     */
    PROTOTYPE("prototype"),
    ;

    private String scope;

    BeanScopeEnum(String scope) {
        this.scope = scope;
    }

    public String getScope() {
        return scope;
    }
}
