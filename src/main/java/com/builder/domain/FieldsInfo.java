package com.builder.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.lang.model.element.Modifier;

/**
 * @Description
 * @author AbrahamVong
 * @since 25/5/2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FieldsInfo {

    /**
     * 字段类型
     */
    private Object type;

    /**
     * 字段名称
     */
    private String name;

    /**
     * 修饰符
     */
    private Modifier modifier;

    private String dfn;

    /**
     * 字段注释
     */
    private String doc;

}
