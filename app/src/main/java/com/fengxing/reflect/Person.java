package com.fengxing.reflect;

import androidx.annotation.NonNull;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;

/**
 * com.fengxing.fs.Person
 * Created by zhaoyuanchao on 2020/3/9.
 */
public class Person {
    public String country;
    public String city;
    public String name;
    public String province;
    public Integer height;
    public Integer age;
    protected String hobby;
    private String sex;
    private String value = "default_value";

    public Person() {
        System.out.println("调用Person的无参构造方法");
    }

    public Person(String country, String city, String name) {
        this.country = country;
        this.city = city;
        this.name = name;
    }

    public Person(String country, Integer age) {
        this.country = country;
        this.age = age;
    }

    private Person(String country, String city, String name, String province, Integer height, Integer age) {
        this.country = country;
        this.city = city;
        this.name = name;
        this.province = province;
        this.height = height;
        this.age = age;
    }

    protected Person(String country, String city, String name, String province, Integer height) {
        this.country = country;
        this.city = city;
        this.name = name;
        this.province = province;
        this.height = height;
    }

    public String getMobile(String number){
        String mobile = "010-110"+"-"+number;
        return mobile;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void getGenericHelper(HashMap<String,String> hashMap){

    }

    public Class getGenericType(){
        try {
            HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
            Method method = getClass().getDeclaredMethod("getGenericHelper",HashMap.class);
            Type[] genericParameterTypes = method.getGenericParameterTypes();
            if (genericParameterTypes.length < 1) {
                return null;
            }

            ParameterizedType parameterizedType=(ParameterizedType)genericParameterTypes[0];
            Type rawType = parameterizedType.getRawType();
            System.out.println("----> rawType=" + rawType);
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            if (actualTypeArguments==genericParameterTypes || actualTypeArguments.length<1) {
                return null;
            }

            for (Type type : actualTypeArguments) {
                System.out.println("----> type=" + type);
            }
        } catch (Exception ignored) {

        }
        return null;
    }

    @NonNull
    @Override
    public String toString() {
        return "Person{" +
                "country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", name='" + name + '\'' +
                ", province='" + province + '\'' +
                ", height=" + height +
                '}';
    }
}
