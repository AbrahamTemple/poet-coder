package com.builder.anno;

import java.lang.annotation.*;

/**
 * @Description
 * @author AbrahamVong
 * @since 1/6/2022
 */
@Target({ElementType.METHOD,ElementType.FIELD,ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Generate {
}
