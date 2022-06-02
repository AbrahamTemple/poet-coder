package com.builder.struct.clazz;

import com.builder.domain.AnnotationGroup;
import com.squareup.javapoetx.*;
import lombok.SneakyThrows;

import javax.lang.model.element.Modifier;
import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @author AbrahamVong
 * @since 30/5/2022
 */
public class DefaultClassCoder implements ClassCoder{

    /**
     * 项目java的位置
     */
    private String root;

    public DefaultClassCoder() {
        super();
        this.root = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "java";
    }

    public DefaultClassCoder(String root) {
        this.root = root;
    }


    /**
     * 构建多注解类
     * @param clazzPkg 类的包路径
     * @param modifiers 修饰符
     * @param kind 类/接口/枚举/注解
     * @param isImpl 0:不实现、1:单实现、2:继承又实现
     * @param extend 继承的类
     * @param implement 实现的类
     * @param annotations 多个注解构建信息
     * @param fields 类里面的成员变量
     * @param method 类里面的方法
     */
    @SneakyThrows
    @Override
    public void buildJava(String clazzPkg, Modifier[] modifiers, TypeSpec.Kind kind, Integer isImpl, Object extend, Object implement, List<AnnotationGroup> annotations, List<FieldSpec> fields, List<MethodSpec> method) {

        String pkg = clazzPkg.substring(0,clazzPkg.lastIndexOf("."));

        String clazz = clazzPkg.substring(clazzPkg.lastIndexOf(".")+1);

        TypeSpec typeSpec = null;

        TypeSpec.Builder builder = null;

        if (kind == TypeSpec.Kind.INTERFACE)
        {

            builder = TypeSpec.interfaceBuilder(clazz);

        }else{

            builder = com.squareup.javapoetx.TypeSpec.classBuilder(clazz,isImpl);

        }

        if(null != extend){
            if(extend instanceof TypeName) {
                builder.addSuperinterface((TypeName) extend);
            } else if (extend instanceof Type){
                builder.addSuperinterface((Type) extend);
            }
        }

        if(null != implement){
            if(implement instanceof TypeName) {
                builder.addSuperAbstract((TypeName) implement);
            } else if (implement instanceof Type){
                builder.addSuperAbstract((Type) implement);
            } else if (implement instanceof TypeName[]){
                for (TypeName impl : (TypeName[]) implement) {
                    builder.addSuperAbstract(impl);
                }
            } else if (implement instanceof Type[]){
                for (Type impl : (Type[]) implement) {
                    builder.addSuperAbstract(impl);
                }
            }
        }

        if(null != fields && !fields.isEmpty()){
            builder.addFields(fields);
        }

        if(null != method && !method.isEmpty()){
            builder.addMethods(method);
        }

        if(null != modifiers && modifiers.length > 0){
            builder.addModifiers(modifiers);
        }

        if(null != annotations && annotations.size() > 0){

            for (AnnotationGroup anno : annotations) {

                if(null != anno.getAnnotationVal() && anno.getAnnotationVal().length > 0) {
                    builder
                            .addAnnotation(com.squareup.javapoetx.AnnotationSpec.builder(anno.getAnnotation())
                                    .addMembers(anno.getAnnotationVal())
                                    .build());
                } else {
                    builder.addAnnotation(anno.getAnnotation());
                }

            }

        }

        typeSpec = builder.build();

        com.squareup.javapoetx.JavaFile javaFile = com.squareup.javapoetx.JavaFile.builder(pkg,typeSpec).build();

        File file = new File(root);

        javaFile.writeTo(file);

    }


    /**
     * 构建继承
     * @param clazz 类包
     * @param generics 泛型
     * @return
     */
    @Override
    public TypeName getExtends(String clazz, String... generics){

        List<ClassName> genericsList = new ArrayList<>();

        for (String clz : generics) {

            genericsList.add(com.squareup.javapoetx.ClassName.get(clz.substring(0, clz.lastIndexOf(".")), clz.substring(clz.lastIndexOf(".") + 1)));

        }

        com.squareup.javapoetx.ClassName repository = com.squareup.javapoetx.ClassName.get(clazz.substring(0,clazz.lastIndexOf(".")), clazz.substring(clazz.lastIndexOf(".")+ 1) );

        return com.squareup.javapoetx.ParameterizedTypeName.get(repository, genericsList.stream().toArray(com.squareup.javapoetx.ClassName[]::new));
    }
}
