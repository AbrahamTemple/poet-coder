package com.squareup.javapoetx;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description
 * @author AbrahamVong
 * @since 26/5/2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CodeVal {

    private String format;

    private Object[] args;

    private Integer sort;

}
