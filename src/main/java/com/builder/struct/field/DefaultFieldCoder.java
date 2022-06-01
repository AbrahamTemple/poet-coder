package com.builder.struct.field;

import com.builder.domain.FieldsInfo;
import com.builder.domain.AnnotationGroup;
import com.squareup.javapoetx.AnnotationSpec;
import com.squareup.javapoetx.AnnotationVal;
import com.squareup.javapoetx.FieldSpec;
import com.squareup.javapoetx.TypeName;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description
 * @author AbrahamVong
 * @since 30/5/2022
 */
public class DefaultFieldCoder extends FieldCoder{


    @Override
    public List<FieldSpec> fieldStruct(FieldsInfo[] infos, List<AnnotationGroup> annotationGroup) {
        List<FieldSpec> fields = new ArrayList<>();

        FieldSpec.Builder builder = null;

        for (FieldsInfo info : infos) {
            if(info.getType() instanceof Type) {

                builder = FieldSpec.builder((Type)info.getType(), info.getName(), info.getModifier());

            }
            else if(info.getType() instanceof TypeName){

                builder = FieldSpec.builder((TypeName) info.getType(), info.getName(), info.getModifier());

            }

            for (AnnotationGroup anno : annotationGroup) {

                assert builder != null;
                builder.addAnnotation(AnnotationSpec.builder(anno.getAnnotation()).addMembers(anno.getAnnotationVal()).build());

            }

            assert builder != null;
            fields.add(builder.build());
        }

        return fields;
    }

    /**
     * 驼峰命名法转化
     * @param param
     * @return
     */
    private static String camel4underline(String param){
        Pattern p=Pattern.compile("[A-Z]");
        if(param==null ||param.equals("")){
            return "";
        }
        StringBuilder builder=new StringBuilder(param);
        Matcher mc=p.matcher(param);
        int i=0;
        while(mc.find()){
            builder.replace(mc.start()+i, mc.end()+i, "_"+mc.group().toLowerCase());
            i++;
        }

        if('_' == builder.charAt(0)){
            builder.deleteCharAt(0);
        }
        return builder.toString();
    }


    /**
     * 驼峰命名法转回
     * @param name
     * @return
     */
    private static String camel4convert(String name) {
        StringBuilder result = new StringBuilder();
        if (name == null || name.isEmpty()) {
            return "";
        } else if (!name.contains("_")) {
            return name.substring(0, 1).toLowerCase() + name.substring(1);
        }
        String camels[] = name.split("_");
        for (String camel :  camels) {
            if (camel.isEmpty()) {
                continue;
            }
            if (result.length() == 0) {
                result.append(camel.toLowerCase());
            } else {
                result.append(camel.substring(0, 1).toUpperCase());
                result.append(camel.substring(1).toLowerCase());
            }
        }
        return result.toString();
    }
}
