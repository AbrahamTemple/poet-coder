package com.builder.struct.method;

import com.builder.domain.ComplexMethod;
import com.builder.domain.MethodInfo;
import com.squareup.javapoetx.MethodSpec;

import java.util.List;

/**
 * @Description
 * @author AbrahamVong
 * @since 30/5/2022
 */
public abstract class MethodCoder {

    /**
     * 复杂方法
     * @param infos
     * @return
     */
    public abstract List<MethodSpec> methodStruct(MethodInfo... infos);


}
