package com.tony.router.compiler;


import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.WildcardTypeName;
import com.tony.router.annotation.RouterMap;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.FilerException;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;

@AutoService(Processor.class)
public class RouterProcessor extends AbstractProcessor {
    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(RouterMap.class.getCanonicalName());
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        //获取所有该注解的元素
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(RouterMap.class);
        if (elements == null || elements.size() == 0) {
            return true;
        }
        //构建一个方法void initRouterTable(Map<String, Class<? extends Activity>> router);
        //获取Activity类型
        TypeElement activityType = processingEnv.getElementUtils().getTypeElement("android.app.Activity");
        ClassName map = ClassName.get(Map.class);
        ClassName string = ClassName.get(String.class);
        ClassName clazz = ClassName.get(Class.class);
        ClassName activity = ClassName.get(activityType);
        WildcardTypeName subActivity = WildcardTypeName.subtypeOf(activity);
        //Class<? extends Activity>
        ParameterizedTypeName mapValue = ParameterizedTypeName.get(clazz, subActivity);
        //Map<String, Class<? extends Activity>>
        ParameterizedTypeName mapTypeName = ParameterizedTypeName.get(map, string, mapValue);
        //Map<String, Class<? extends Activity>> router
        ParameterSpec mapParameterSpec = ParameterSpec.builder(mapTypeName, "router").build();
        //构建整个方法
        MethodSpec.Builder initRouterTableBuilder = MethodSpec.methodBuilder("initRouterTable")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .addParameter(mapParameterSpec);
        //写入方法内容
        for (Element element : elements) {
            //实现该注解的类型判断
            if (element.getKind() != ElementKind.CLASS) {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "only support class");
            }
            RouterMap router = element.getAnnotation(RouterMap.class);
            //获取每一个实现该注解的value[]
            String[] urls = router.value();
            if (urls != null) {
                for (String url : urls) {
                    //加入方法体中
                    initRouterTableBuilder.addStatement("router.put($S, $T.class)", url, ClassName.get((TypeElement) element));
                }
            }
        }
        //完成该方法
        MethodSpec initRouterTableMethod = initRouterTableBuilder.build();

        //ActivityRouteTableInitializer类型
        TypeElement activityRouteTableInitializer = processingEnv.getElementUtils().getTypeElement("com.tony.router.router.ActivityRouteTableInitializer");
        //构建类
        TypeSpec annotatedActivityRouteTableInitializer = TypeSpec.classBuilder("AnnotatedActivityRouteTableInitializer")
                .addSuperinterface(ClassName.get(activityRouteTableInitializer))
                .addModifiers(Modifier.PUBLIC)
                .addMethod(initRouterTableMethod)
                .build();

        //构建该java文件,包名:com.tony.router.router
        JavaFile javaFile = JavaFile.builder("com.tony.router.router", annotatedActivityRouteTableInitializer).build();

        //生成类文件
        try {
            javaFile.writeTo(processingEnv.getFiler());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
}
