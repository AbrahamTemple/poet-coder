package com.builder.anno;

import java.lang.annotation.*;

/**
 * @Description
 * @author AbrahamVong
 * @since 1/6/2022
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Coder {
}
