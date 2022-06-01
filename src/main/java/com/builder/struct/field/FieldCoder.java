package com.builder.struct.field;

import com.builder.domain.FieldsInfo;
import com.builder.domain.AnnotationGroup;
import com.squareup.javapoetx.*;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @author AbrahamVong
 * @since 30/5/2022
 */
public abstract class FieldCoder {

    /**
     * 多注解字段
     * @param infos
     * @param annotationGroup
     * @return
     */
    public abstract List<FieldSpec> fieldStruct(FieldsInfo[] infos, List<AnnotationGroup> annotationGroup);


    /**
     * 构建复杂字段
     * 如果复杂字段包含复杂字段也可以进行嵌套，如：List<Map<String,Integer>>
     * getComplexField("java.util.List", getComplexField("java.util.Map", "java.lang.String", "java.lang.Integer"))
     * @return
     */
    protected static TypeName getComplexField(String type, String ...generics){

        ClassName master = ClassName.get(type.substring(0,type.lastIndexOf(".")), type.substring(type.lastIndexOf(".")+1));

        List<TypeName> salvers = new ArrayList<>();

        for (String generic : generics) {

            ClassName salver = ClassName.get(generic.substring(0,generic.lastIndexOf(".")), generic.substring(generic.lastIndexOf(".")+1));

            salvers.add(salver);

        }

        return ParameterizedTypeName.get(master,salvers.stream().toArray(TypeName[]::new));
    }

}
