package com.tony.router.compiler;


import com.google.auto.service.AutoService;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import com.tony.router.annotation.RouterMap;

import java.io.IOException;
import java.util.Collections;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
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
    private Messager mMessager;
    private Filer mFiler;
    private Elements mElementUtils;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        mMessager = processingEnv.getMessager();
        mFiler = processingEnv.getFiler();
        mElementUtils = processingEnv.getElementUtils();
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
//        //获取所有该注解的元素
//        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(RouterMap.class);
//        try {
//            TypeSpec type = getRouterTableInitializer(elements);
//            if(type != null) {
//                JavaFile.builder("com.tony.router.router", type).build().writeTo(mFiler);
//            }
//        } catch (FilerException e){
//            e.printStackTrace();
//        } catch (Exception e) {
//            mMessager.printMessage(Diagnostic.Kind.ERROR, e.getMessage());
//        }
//        return true;
        //获取所有该注解的元素
        Set<? extends Element> set = roundEnv.getElementsAnnotatedWith(RouterMap.class);
        for (Element element : set) {
            if (element.getKind() != ElementKind.CLASS) {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "only support class");
            }
            MethodSpec main = MethodSpec.methodBuilder("main")
                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                    .returns(void.class)
                    .addParameter(String[].class, "args")
                    .addStatement("$T.out.println($S)", System.class, "Hello, JavaPoet!")
                    .build();

            TypeSpec helloWorld = TypeSpec.classBuilder("HelloWorld").addModifiers(Modifier.PUBLIC, Modifier.FINAL).addMethod(main).build();
            JavaFile javaFile = JavaFile.builder("com.lighters.apt", helloWorld).build();

            try {
                javaFile.writeTo(processingEnv.getFiler());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

//    /**
//     * 获取初始化时候的元素
//     * @param elements
//     * @return
//     * @throws ClassNotFoundException
//     * @throws TargetErrorException
//     */
//    private TypeSpec getRouterTableInitializer(Set<? extends Element> elements) throws ClassNotFoundException, TargetErrorException {
//        if(elements == null || elements.size() == 0){
//            return null;
//        }
//        //获取Activity元素
//        TypeElement activityType = mElementUtils.getTypeElement("android.app.Activity");
//
//        ParameterizedTypeName mapTypeName = ParameterizedTypeName
//                .get(ClassName.get(Map.class), ClassName.get(String.class), ParameterizedTypeName.get(ClassName.get(Class.class), WildcardTypeName.subtypeOf(ClassName.get(activityType))));
//        ParameterSpec mapParameterSpec = ParameterSpec.builder(mapTypeName, "router")
//                .build();
//        MethodSpec.Builder routerInitBuilder = MethodSpec.methodBuilder("initRouterTable")
//                .addAnnotation(Override.class)
//                .addModifiers(Modifier.PUBLIC)
//                .addParameter(mapParameterSpec);
//        for(Element element : elements){
//            if(element.getKind() != ElementKind.CLASS){
//                throw new TargetErrorException();
//            }
//            RouterMap router = element.getAnnotation(RouterMap.class);
//            String [] routerUrls = router.value();
//            if(routerUrls != null){
//                for(String routerUrl : routerUrls){
//                    routerInitBuilder.addStatement("router.put($S, $T.class)", routerUrl, ClassName.get((TypeElement) element));
//                }
//            }
//        }
//        MethodSpec routerInitMethod = routerInitBuilder.build();
//        TypeElement routerInitializerType = mElementUtils.getTypeElement("com.tony.router.router.IActivityRouteTableInitializer");
//        return TypeSpec.classBuilder("AnnotatedRouterTableInitializer")
//                .addSuperinterface(ClassName.get(routerInitializerType))
//                .addModifiers(Modifier.PUBLIC)
//                .addMethod(routerInitMethod)
//                .build();
//    }
}
