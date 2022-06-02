package com.builder.struct.clazz;

import com.builder.domain.AnnotationGroup;
import com.squareup.javapoetx.*;

import javax.lang.model.element.Modifier;
import java.lang.annotation.Annotation;
import java.util.List;

/**
 * @Description
 * @author AbrahamVong
 * @since 30/5/2022
 */
public interface ClassCoder {

    /**
     * 多注解类生成
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
    void buildJava(String clazzPkg, Modifier[] modifiers, TypeSpec.Kind kind, Integer isImpl, Object extend, Object implement, List<AnnotationGroup> annotations, List<FieldSpec> fields, List<MethodSpec> method);


    /**
     * 构建继承
     * @param clazz 类包
     * @param generics 泛型
     * @return
     */
    TypeName getExtends(String clazz, String... generics);
}
