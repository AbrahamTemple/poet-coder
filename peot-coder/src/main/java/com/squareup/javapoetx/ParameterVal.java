package com.squareup.javapoetx;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.lang.model.element.Modifier;
import java.lang.reflect.Type;

/**
 * @Description
 * @author AbrahamVong
 * @since 26/5/2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParameterVal {

    private Type type;

    private String name;

    private Modifier[] modifier;
}
