package com.abraham.coder.sql.oracle.simple.domain;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @Description
 * @author Huangyin
 * @since 25/5/2022
 */
@Data
@Builder
public class SelectStmt {

    private List<ColumnOptAlias> columns;

    private String tableName;
}
