package com.fengxing.reflect;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhaoyuanchao on 2020/3/10.
 */
// TYPE
@AboutAnnotation.CustomAnnotationInfo(name = "class",id = 0,gid = Long.class)
public class UseCustomAnnotation {
    // 成员变量 field
    @AboutAnnotation.CustomAnnotationInfo(name = "filed_age",id = 1,gid = Long.class)
    private Integer age;

    // 构造方法 CONSTRUCTOR
    @AboutAnnotation.CustomAnnotationInfo(name = "constructor",id = 2,gid = Long.class)
    public UseCustomAnnotation(){

    }
    // 方法 METHOD
    @AboutAnnotation.CustomAnnotationInfo(name = "public_method_A",id = 3,gid = Long.class)
    public void A(){
        Map<String,String>  m = new HashMap<String, String>(0);
    }

    @AboutAnnotation.CustomAnnotationInfo(name = "protected_method_B",id = 4,gid = Long.class)
    protected void B(){
        Map<String,String>  m = new HashMap<String, String>(0);
    }

    @AboutAnnotation.CustomAnnotationInfo(name = "private_method_C",id = 5,gid = Long.class)
    private void C(){
        Map<String,String>  m = new HashMap<String, String>(0);
    }

}
