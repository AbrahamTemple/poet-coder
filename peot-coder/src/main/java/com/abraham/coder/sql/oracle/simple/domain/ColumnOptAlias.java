package com.abraham.coder.sql.oracle.simple.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Description
 * @author Huangyin
 * @since 25/5/2022
 */
@Data
@AllArgsConstructor
public class ColumnOptAlias {

    private String name;

    private String alias;
}
