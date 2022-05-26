package com.abraham.coder.sql.demo;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

/**
 * @Description 模板文件
 * @author Huangyin
 * @since 25/5/2022
 */
public class Type_03 {

    private static final String root = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "java";

    public static void main(String[] args) {

        String path = root + File.separator + "com\\abraham\\coder\\sql\\demo\\";

        STGroup stg = new STGroupFile(path + "type_03.stg");
        ST sqlST = stg.getInstanceOf("sqlTemplate");

        List<String> columnList = new LinkedList<String>();
        columnList.add("order_id");
        columnList.add("price");
        columnList.add("phone");
        sqlST.add("columns", columnList);
        sqlST.add("condition", "dt='2017-04-04'");
        System.out.print(sqlST.render());
    }
}
