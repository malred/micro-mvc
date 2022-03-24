package com.malred.micro_mvc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author malguy-wang sir
 * @create ---
 */
@Target(ElementType.METHOD) //适用范围:加在方法上
@Retention(RetentionPolicy.RUNTIME) //运行时生效
public @interface RequestMapping {
    String value() default "";
}
