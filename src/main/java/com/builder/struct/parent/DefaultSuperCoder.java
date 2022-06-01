package com.builder.struct.parent;

import com.squareup.javapoetx.ClassName;
import com.squareup.javapoetx.ParameterizedTypeName;
import com.squareup.javapoetx.TypeName;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @author AbrahamVong
 * @since 30/5/2022
 */
public class DefaultSuperCoder extends SuperCoder{

    /**
     * 构建复杂继承或实现
     * @param clazz
     * @param generics
     * @return
     */
    @Override
    public TypeName superStruct(String clazz, String... generics){

        List<ClassName> genericsList = new ArrayList<>();

        for (String clz : generics) {

            genericsList.add(ClassName.get(clz.substring(0, clz.lastIndexOf(".")), clz.substring(clz.lastIndexOf(".") + 1)));

        }

        ClassName master = ClassName.get(clazz.substring(0,clazz.lastIndexOf(".")), clazz.substring(clazz.lastIndexOf(".")+ 1) );

        return ParameterizedTypeName.get(master, genericsList.stream().toArray(ClassName[]::new));
    }


}
