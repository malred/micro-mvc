package com.malred.micro_mvc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author malguy-wang sir
 * @create ---
 */
@Target(ElementType.PARAMETER)//加在参数上
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestParam {
    String value() default "";
}
