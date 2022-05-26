package com.abraham.coder.jpa.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.lang.model.element.Modifier;
import java.lang.reflect.Type;

/**
 * @Description
 * @author AbrahamVong
 * @since 25/5/2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FieldsInfo {

    private Object type;

    private String name;

    private Modifier modifier;
}
