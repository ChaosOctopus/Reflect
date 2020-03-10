package com.fengxing.reflect;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    public static String CLASSNAME = "com.fengxing.reflect.Person";
    public static String ANNOTATION_CLASSNAME = "com.fengxing.reflect.UseCustomAnnotation";
    public static String FIELDDEFAULT = "value";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        printConstructor(CLASSNAME);
        printConstructorWithParams(CLASSNAME,String.class,Integer.class);
        getAllField(CLASSNAME);
        getChooseFieldValue(CLASSNAME,FIELDDEFAULT,String.class,String.class,String.class);
        getAllMethod(CLASSNAME);
        getAssignMethod(CLASSNAME,"setCountry",String.class);
        getArrayClass();
        getGenericType(CLASSNAME);
        parseTypeAnnotation(ANNOTATION_CLASSNAME);
        parseMethodAnnotation();
        parseConstructAnnotation();
    }

    /**
     * 打印构造方法
     * @param className
     */
    public static void printConstructor(String className){
        try {
            Class<?> aClass = Class.forName(className);
            Constructor<?>[] constructors = aClass.getConstructors();
            for (Constructor<?> constructor : constructors) {
                System.out.println(constructor+":one");
            }
            Constructor<?>[] declaredConstructors = aClass.getDeclaredConstructors();
            for (Constructor<?> declaredConstructor : declaredConstructors) {
                System.out.println(declaredConstructor+":two");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取指定构造参数的构造方法，并生成实例
     */
    public static void printConstructorWithParams(String className,Class<?>...clzs){
        try {
            Class<?> aClass = Class.forName(className);
            Constructor<?> constructor = aClass.getDeclaredConstructor(clzs);
            constructor.setAccessible(true);
            Object china = constructor.newInstance("CHINA", 20);
            Person person = (Person)china;
            System.out.println("Country:"+person.country+"-----"+"AGE:"+person.age);
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取所有的Field变量
     */
    public static void getAllField(String className){
        try {
            Class<?> aClass = Class.forName(className);
            Field[] declaredFields = aClass.getDeclaredFields();
            for (Field declaredField : declaredFields) {
                System.out.println(declaredField);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取指定的私有变量的值
     */
    public static void getChooseFieldValue(String className,String filedName,Class<?>...claz){
        try {
            Class<?> aClass = Class.forName(className);
            //首先反射出一个对象实例
            Constructor<?> constructor = aClass.getConstructor(claz);
            constructor.setAccessible(true);
            //根据三参构造创建一个实例出来 String country, String city, String name
            Object object = constructor.newInstance("CHINA", "hangzhou", "fengxing");
            //转成person对象
            Person person = (Person)object;
            //获取对应的字段名称，并获取值
            Field declaredField = aClass.getDeclaredField(filedName);
            declaredField.setAccessible(true);
            String value =(String)declaredField.get(person);
            //打印默认值
            System.out.println("获取Value的默认值："+value);
        } catch (ClassNotFoundException | NoSuchFieldException | NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取所有的方法
     */
    public static void getAllMethod(String clazzName){
        try {
            Class<?> aClass = Class.forName(clazzName);
            Field[] declaredFields = aClass.getDeclaredFields();
            for (Field declaredField : declaredFields) {
                System.out.println(declaredField);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取指定的Method setCountry为例子
     */
    public static void getAssignMethod(String clazzName,String methodName,Class<?>...claz){
        try {
            //先通过无参构造 创造一个实例，通过反射创建也可以
            Person person = new Person();
            //获取到对应的Method
            Class<?> aClass = Class.forName(clazzName);
            Method declaredMethod = aClass.getDeclaredMethod(methodName, claz);
            declaredMethod.setAccessible(true);
            declaredMethod.invoke(person, "USA");
            //我们给person对象的setCountry方法已经赋值了
            System.out.println(person.country);
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * 使用反射操作数组
     */
    public static void getArrayClass(){
        String[] strArray = new String[]{"你好啊","我是第二条数据","可怕"};
        Array.set(strArray,0,"我是要修改的数据");
        Class<? extends String[]> aClass = strArray.getClass();
        if (aClass.isArray()){
            int length = Array.getLength(strArray);
            for (int i = 0;i < length; i++){
                Object o = Array.get(strArray, i);
                String name = o.getClass().getName();
                System.out.println(o+"----"+name);
            }
        }
    }

    /**
     * 使用反射获取泛型类型
     * Person：getGenericHelper(HashMap<String,String> hashMap)
     */
    public static void getGenericType(String className){
        try {
            //首先反射获取方法
            Class<?> aClass = Class.forName(className);
            Method getGenericHelper = aClass.getDeclaredMethod("getGenericHelper", HashMap.class);
            //校验 并取得第一个参数
            Type[] genericParameterTypes = getGenericHelper.getGenericParameterTypes();
            if (genericParameterTypes.length < 1) return;
            ParameterizedType parameterizedType = (ParameterizedType)genericParameterTypes[0];
            Type rawType = parameterizedType.getRawType();
            System.out.println("rawType:"+rawType);
            //获取参数类型中 所有的子参数
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            if (actualTypeArguments.length < 1) return;
            for (int i = 0; i < actualTypeArguments.length; i++){
                Type type = actualTypeArguments[i];
                System.out.println("type:"+type);
            }
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    /**
     * 打印UseCustomAnnotation类中使用到的类注解，该方法只打印了Type(类)类型的注解
     */
    public static void parseTypeAnnotation(String className){
        try {
            Class<?> aClass = Class.forName(className);
            Annotation[] annotations = aClass.getAnnotations();
            for (Annotation annotation : annotations) {
                AboutAnnotation.CustomAnnotationInfo  custom = (AboutAnnotation.CustomAnnotationInfo) annotation;
                System.out.println("id= \"" + custom.id() + "\"; name= \""
                        + custom.name() + "\"; gid = " + custom.gid());
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    /**
     * 打印UseCustomAnnotation类中使用到的方法注解，该方法只打印了Method(类)类型的注解
     */
    public static void parseMethodAnnotation(){
        Method[] declaredMethods = UseCustomAnnotation.class.getDeclaredMethods();
        for (Method declaredMethod : declaredMethods) {
            //判断方法中是否有指定类型的注解
            boolean hasAnnotation = declaredMethod.isAnnotationPresent(AboutAnnotation.CustomAnnotationInfo.class);
            if (hasAnnotation){
                AboutAnnotation.CustomAnnotationInfo annotation = declaredMethod.getAnnotation(AboutAnnotation.CustomAnnotationInfo.class);
                System.out.println("method = " + declaredMethod.getName() + " ; id = "
                        + annotation.id() + " ; name = "
                        + annotation.name() + "; gid= " + annotation.gid());
            }
        }
    }

    /**
     * 打印UseCustomAnnotation类中使用到的构造方法注解，CONSTRUCTOR(构造方法)类型的注解
     */
    public static void parseConstructAnnotation(){
        Constructor<?>[] constructors = UseCustomAnnotation.class.getConstructors();
        for (Constructor<?> constructor : constructors) {
            boolean annotationPresent = constructor.isAnnotationPresent(AboutAnnotation.CustomAnnotationInfo.class);
            if (annotationPresent){
                AboutAnnotation.CustomAnnotationInfo custom = constructor.getAnnotation(AboutAnnotation.CustomAnnotationInfo.class);
                System.out.println("constructor = " + constructor.getName()
                        + " ; id = " + custom.id() + " ; name = "
                        + custom.name() + "; gid= " + custom.gid());
            }
        }
    }
}
