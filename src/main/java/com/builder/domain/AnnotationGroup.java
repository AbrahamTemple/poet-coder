package com.builder.domain;

import com.squareup.javapoetx.AnnotationVal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.annotation.Annotation;

/**
 * @Description
 * @author AbrahamVong
 * @since 30/5/2022
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnnotationGroup {

    private Class<? extends Annotation> annotation;

    private AnnotationVal[] annotationVal;
}
