package com.fengxing.seriableannotation;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.Elements;

/**
 * 将Model 输出成Json数据
 */

@SupportedAnnotationTypes("com.fengxing.seriableannotation.Seriable")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class JsonProcessor extends AbstractProcessor {
    private Elements mElements;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        //工具辅助类
        mElements = processingEnv.getElementUtils();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        // 根据自定义注解拿到 elements set集合
        Set<? extends Element> elementsAnnotatedWith = roundEnv.getElementsAnnotatedWith(Seriable.class);
        TypeElement typeElement;
        VariableElement variableElement;
        Map<String, List<VariableElement>> map = new HashMap<>();
        List<VariableElement> fields = null;
        // 根基具体注解类型 分类存入map集合
        for (Element element : elementsAnnotatedWith) {
            ElementKind kind = element.getKind();
            //判断 elementKind的具体类型 我们只定义了两种类型
            if (kind == ElementKind.CLASS){
                typeElement = (TypeElement) element;
                // 类的全限定类名 作为key
                String qualifiedName = typeElement.getQualifiedName().toString();
                map.put(qualifiedName,fields = new ArrayList<VariableElement>());
            }
            //判断是否为成员变量
            else if (kind == ElementKind.FIELD){
                variableElement = (VariableElement) element;
                // 获取元素的封装类型
                typeElement = (TypeElement) variableElement.getEnclosingElement();
                String qualifiedName = typeElement.getQualifiedName().toString();
                fields = map.get(qualifiedName);
                if (fields == null){
                    map.put(qualifiedName,fields = new ArrayList<VariableElement>());
                }
                fields.add(variableElement);
            }
        }

        Set<String> set = map.keySet();
        for (String key : set) {
            if (map.get(key).size() == 0){
                typeElement = mElements.getTypeElement(key);
                List<? extends Element> allMembers = mElements.getAllMembers(typeElement);
                if (allMembers.size() > 0){
                    map.get(key).addAll(ElementFilter.fieldsIn(allMembers));
                }
            }
        }

        //根据生成的 map 集合数据生成代码
        generateCodes(map);

        return true;
    }

    private void generateCodes(Map<String, List<VariableElement>> map) {
        File dir = new File("/Users/zhaoyuanchao/Desktop/Web/Json");
        if (!dir.exists()) dir.mkdirs();
        for (String key : map.keySet()) {
            File file = new File(dir,key.replaceAll("\\.","_")+".txt");
            try {
                FileWriter fileWriter = new FileWriter(file);
                fileWriter.append("{").append("class:").append("\""+key+"\"").append(",\n");
                List<VariableElement> fields = map.get(key);
                for (int i= 0;i<fields.size();i++){
                    VariableElement variableElement = fields.get(i);
                    fileWriter.append("   ").append(variableElement.getSimpleName()).append(":")
                            .append("\""+variableElement.asType().toString()+"\"");
                    if (i < fields.size() - 1){
                        fileWriter.append(",");
                        fileWriter.append("\n");
                    }
                }
                fileWriter.append("\n }\n");
                fileWriter.append("}");
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
