package com.builder.struct.method;

import com.builder.domain.ComplexMethod;
import com.builder.domain.MethodInfo;
import com.builder.domain.AnnotationGroup;
import com.squareup.javapoetx.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @author AbrahamVong
 * @since 30/5/2022
 */
public class DefaultMethodCoder extends MethodCoder{


    @Override
    public List<MethodSpec> methodStruct(MethodInfo... infos) {
        List<MethodSpec> methods = new ArrayList<>();

        for (MethodInfo info : infos) {

            ClassName type = ClassName.get(info.getType().substring(0,info.getType().lastIndexOf(".")),info.getType().substring(info.getType().lastIndexOf(".")+1));

            ClassName generics = ClassName.get(info.getGenerics().substring(0,info.getGenerics().lastIndexOf(".")),info.getGenerics().substring(info.getGenerics().lastIndexOf(".")+1));

            TypeName clazz = generics !=null ? ParameterizedTypeName.get(type,generics) : ParameterizedTypeName.get(type);

            MethodSpec.Builder builder = MethodSpec.methodBuilder(info.getName());

            if(null != info.getModifiers() && info.getModifiers().length > 0){
                builder.addModifiers(info.getModifiers());
            }

            for (AnnotationGroup anno : info.getAnnotations()) {

                if(null != anno.getAnnotationVal() && anno.getAnnotationVal().length > 0) {
                    builder
                            .addAnnotation(AnnotationSpec.builder(anno.getAnnotation())
                                    .addMembers(anno.getAnnotationVal())
                                    .build());
                }

            }

            if(null != info.getParametersVal() && info.getParametersVal().length > 0){
                builder.addParameters(info.getParametersVal());
            }

            if(null != info.getCodesVal() && info.getCodesVal().length > 0){
                builder.addStatements(info.getCodesVal());
            }

            builder.returns(clazz);

            methods.add(builder.build());
        }

        return methods;
    }

}
