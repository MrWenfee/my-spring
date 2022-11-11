package com.wenfee.spring.annotation;

import java.lang.annotation.*;

/**
 * @author Wenfee
 * @date 2022/11/11
 * <p>
 * 注入注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.CONSTRUCTOR, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Documented
public @interface Autowired {
    boolean required() default true;
}
