package com.fengxing.seriableannotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by zhaoyuanchao on 2020/3/10.
 */
@Documented()
//基于编译时注解的
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.FIELD,ElementType.TYPE})
public @interface Seriable {

}
