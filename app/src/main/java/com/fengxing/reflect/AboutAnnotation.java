package com.fengxing.reflect;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by zhaoyuanchao on 2020/3/10.
 *
 *      元注解：负责注解其他注解
 *           （1)@Target : 用来修饰哪些程序元素
 *                 1.ANNOTATION_TYPE(注解类型生命) 2.PACKAGE(包)  3.TYPE(类)   4.METHOD(方法)
 *                 5.CONSTRUCTOR(构造方法) 6.FIELD(成员变量) 7.PARAMETER(参数) 8.LOCAL_VARIABLE(局部变量)
 *            (2)@Retention : 保留时间可选值
 *                  1.SOURCE(源码时)  2.CLASS(编译时)   3.RUNTIME(运行时)
 *            (3)@Documented : 是否会保存到Javadoc文档中
 *            (4)@Inherited :  是否可以被继承 默认false
 *
 *      元数据(metadata):元数据可以用来创建文档，跟踪代码的依赖性，执行编译时格式检查，代替已有的配置文件
 */
public class AboutAnnotation {

    /**
     * 自定义一个注解
     *         1:@interface 注解名为 CustomAnnotationInfo
     *         2:注解配置参数为注解类的方法名
     *         3:可用于注解到 类 方法 成员变量 构造方法
     */
    @Documented
    @Target({
            ElementType.TYPE,
            ElementType.METHOD,
            ElementType.FIELD,
            ElementType.CONSTRUCTOR
    })
    @Retention(RetentionPolicy.RUNTIME)
    @Inherited
    public @interface CustomAnnotationInfo{
        String name() default "fengxing";
        int id() default 0;
        Class<Long> gid();
    }
}
