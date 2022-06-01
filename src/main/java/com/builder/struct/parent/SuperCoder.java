package com.builder.struct.parent;

import com.squareup.javapoetx.TypeName;

/**
 * @Description
 * @author AbrahamVong
 * @since 30/5/2022
 */
public abstract class SuperCoder {

    /**
     * 构建复杂继或实现
     * @param clazz
     * @param generics
     * @return
     */
    public abstract TypeName superStruct(String clazz, String... generics);

}
