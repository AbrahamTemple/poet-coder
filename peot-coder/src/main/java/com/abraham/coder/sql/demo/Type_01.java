package com.abraham.coder.sql.demo;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupString;

/**
 * @Description 单行输出
 * @author Huangyin
 * @since 25/5/2022
 */
public class Type_01 {

    public static void main(String[] args) {
        STGroup stg = new STGroupString("sqlTemplate(columns,condition) ::= \"select <columns> from table where 1=1 <if(condition)>and <condition><endif> \"");
        ST sqlST = stg.getInstanceOf("sqlTemplate");
        sqlST.add("columns","order_id");
        sqlST.add("condition", "dt='2017-04-04'");
        System.out.print(sqlST.render());
    }
}
