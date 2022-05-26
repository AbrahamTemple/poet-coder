package com.abraham.coder.sql.demo;

import org.apache.commons.io.FileUtils;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupString;

import java.io.File;

/**
 * @Description 多行输出
 * @author Huangyin
 * @since 25/5/2022
 */
public class Type_02 {

    public static void main(String[] args) {
        STGroup stg = new STGroupString("sqlTemplate(columns,condition) ::= <<select <columns> \n" +
                "from table \n" +
                "where 1=1 <if(condition)>and <condition><endif> >>");
        ST sqlST = stg.getInstanceOf("sqlTemplate");
        sqlST.add("columns","order_id");
        sqlST.add("condition", "dt='2017-04-04'");
        System.out.print(sqlST.render());
    }
}
