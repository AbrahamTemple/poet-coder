package com.builder.domain;

import com.squareup.javapoetx.AnnotationVal;
import com.squareup.javapoetx.CodeVal;
import com.squareup.javapoetx.ParameterVal;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.lang.model.element.Modifier;
import java.lang.annotation.Annotation;
import java.util.List;

/**
 * @Description
 * @author AbrahamVong
 * @since 25/5/2022
 */
@Data
@AllArgsConstructor
public class MethodInfo {

    /**
     * 返回类型
     */
    private String type;

    /**
     * 泛型
     */
    private String generics;

    /**
     * 方法名
     */
    private String name;


    /**
     * 修饰符
     */
    private Modifier[] modifiers;


    /**
     * 多个注解
     */
    private List<AnnotationGroup> annotations;


    /**
     * 注解参数
     */
    private AnnotationVal[] annotationsVal;


    /**
     * 形参
     */
    private ParameterVal[] parametersVal;


    /**
     * 代码块
     */
    private CodeVal[] codesVal;


}
