package com.abraham.coder.jpa;

import com.abraham.coder.jpa.domain.FieldsInfo;
import com.abraham.coder.jpa.domain.MethodInfo;
import com.abraham.coder.jpa.service.BreakerInfoService;
import com.abraham.coder.pkg.utils.PackageUtils;
import com.benliu.jpa.annotation.JpaColumn;
import com.benliu.jpa.annotation.JpaDto;
import com.squareup.javapoetx.*;
import lombok.Data;
import lombok.SneakyThrows;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.lang.model.element.Modifier;
import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description
 * @author AbrahamVong
 * @since 23/5/2022
 */
public class App {

    private static final String root = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "java";

    public static void main(String[] args) {

        FieldsInfo[] dtoFields = {
                new FieldsInfo(Integer.class, "nodeType", Modifier.PRIVATE),
                new FieldsInfo(String.class,"nodeCode",Modifier.PRIVATE),
                new FieldsInfo(String.class,"nodeName",Modifier.PRIVATE),
                new FieldsInfo(Integer.class,"transformerType",Modifier.PRIVATE),
                new FieldsInfo(String.class,"location",Modifier.PRIVATE),
                new FieldsInfo(getComplexField_01(),"locationList",Modifier.PRIVATE)
        };

        MethodInfo[] repositoryMethods = {
                new MethodInfo("java.util.List",PackageUtils.buildPkg("jpa","dto","NodeMatchDeduceDto"),"findBySubstationGisId",new Modifier[]{Modifier.PUBLIC, Modifier.ABSTRACT},Query.class,
                        new AnnotationVal[]{new AnnotationVal("value","$S",new Object[]{""}),new AnnotationVal("nativeQuery","$L",new Object[]{true})},
                        new ParameterVal[]{new ParameterVal(String.class, "subGisId",null)},
                        null),
                new MethodInfo("java.util.List",PackageUtils.buildPkg("jpa","entity","BreakerInfo"),"findMatchNode",new Modifier[]{Modifier.PUBLIC, Modifier.ABSTRACT},Query.class,
                        new AnnotationVal[]{new AnnotationVal("value","$S",new Object[]{""}),new AnnotationVal("nativeQuery","$L",new Object[]{true})},
                        new ParameterVal[]{new ParameterVal(Double.class,"latitude",null),new ParameterVal(Double.class,"longitude",null),new ParameterVal(Double.class,"radius",null)},
                        null)
        };

        MethodInfo[] serviceMethods = {
                new MethodInfo("java.util.List",PackageUtils.buildPkg("jpa","dto","NodeMatchDeduceDto"),"findBySubstationGisId",new Modifier[]{Modifier.PUBLIC, Modifier.ABSTRACT},Query.class,null,
                        new ParameterVal[]{new ParameterVal(String.class, "subGisId",null)},
                        null),
                new MethodInfo("java.util.List",PackageUtils.buildPkg("jpa","entity","BreakerInfo"), "findMatchNode",new Modifier[]{Modifier.PUBLIC, Modifier.ABSTRACT},Query.class,null,
                        new ParameterVal[]{new ParameterVal(Double.class,"latitude",null),new ParameterVal(Double.class,"longitude",null),new ParameterVal(Double.class,"radius",null)},
                        null)
        };

        MethodInfo[] serviceImplMethods = {
                new MethodInfo("java.util.List",PackageUtils.buildPkg("jpa","dto","NodeMatchDeduceDto"),"findBySubstationGisId",new Modifier[]{Modifier.PUBLIC},Query.class,null,
                        new ParameterVal[]{new ParameterVal(String.class, "subGisId",null)},
                        null),
                new MethodInfo("java.util.List",PackageUtils.buildPkg("jpa","entity","BreakerInfo"), "findMatchNode",new Modifier[]{Modifier.PUBLIC},Query.class,null,
                        new ParameterVal[]{new ParameterVal(Double.class,"latitude",null),new ParameterVal(Double.class,"longitude",null),new ParameterVal(Double.class,"radius",null)},
                        null)
        };


        /*
        * 缺陷：
        * 1、构建类的时候不能构建多个类上注解
        * 2、复杂类字段难以封装
        */

        // Dto
        buildJava(PackageUtils.buildPkg("jpa","dto","NodeMatchDeduceDto"),new Modifier[]{Modifier.PUBLIC}, TypeSpec.Kind.CLASS, false,
                null,null,JpaDto.class,null,getClassFields(dtoFields),null);

        //Repository
        buildJava(PackageUtils.buildPkg("jpa","repository","BreakerInfoRepository"),new Modifier[]{Modifier.PUBLIC}, TypeSpec.Kind.INTERFACE,false,
                getExtends("com.benliu.jpa.repository.BaseRepository",PackageUtils.buildPkg("jpa","entity","BreakerInfo"),"java.lang.String"),null,Repository.class,null,null,
                getMethods(repositoryMethods));

        //Service
        buildJava(PackageUtils.buildPkg("jpa","service","BreakerInfoService"),new Modifier[]{Modifier.PUBLIC}, TypeSpec.Kind.INTERFACE,false,
                getExtends("com.benliu.jpa.service.BaseService",PackageUtils.buildPkg("jpa","entity","BreakerInfo"),"java.lang.String"),null,Repository.class,null,null,
                getMethods(serviceMethods));

        //ServiceImpl
        buildJava(PackageUtils.buildPkg("jpa","service.impl","BreakerInfoServiceImpl"),new Modifier[]{Modifier.PUBLIC}, TypeSpec.Kind.CLASS,true,
                getExtends("com.benliu.jpa.service.impl.BaseServiceImpl",PackageUtils.buildPkg("jpa","entity","BreakerInfo"),"java.lang.String"),
                BreakerInfoService.class,Repository.class,null,null,
                getServiceImplMethods());

        /*
         *  新问题：难以封装不定条可变代码块
         *  addStatement("$T result = new $T<>()", listOfNodeMatchDeduceDto, arrayList)
         *  addStatement("return result.isEmpty() ? $T.emptyList() : result", Collections.class)
         */
    }

    /**
     * 构建Dto
     * @param pkg
     * @param clazz
     * @param fields
     */
    @SneakyThrows
    private static void buildDto(String pkg, String clazz,List<FieldSpec> fields){

        com.squareup.javapoetx.JavaFile javaFile = JavaFile.builder(pkg,
                com.squareup.javapoetx.TypeSpec.classBuilder(clazz,false)
                        .addAnnotation(JpaDto.class)
                        .addAnnotation(Data.class)
                        .addModifiers(Modifier.PUBLIC)
                        .addFields(fields)
                        .build())
                .build();

        File file = new File(root);

        javaFile.writeTo(file);

    }

    /**
     * 构建Java类文件
     * @param clazzPkg 类的包路径
     * @param modifiers 修饰符
     * @param kind 类/接口/枚举/注解
     * @param isFully 是否既继承又实现
     * @param extend 继承的类
     * @param implement 实现的类
     * @param annotated 类上的注解
     * @param annotationsVal 注解上的值
     * @param fields 类里面的成员变量
     * @param method 类里面的方法
     */
    @SneakyThrows
    private static void buildJava(String clazzPkg, Modifier[] modifiers, TypeSpec.Kind kind, Boolean isFully, com.squareup.javapoetx.TypeName extend, Class<?> implement, Class<? extends Annotation> annotated,AnnotationVal[] annotationsVal, List<FieldSpec> fields,List<com.squareup.javapoetx.MethodSpec> method){

        String pkg = clazzPkg.substring(0,clazzPkg.lastIndexOf("."));

        String clazz = clazzPkg.substring(clazzPkg.lastIndexOf(".")+1);

        TypeSpec typeSpec = null;

        TypeSpec.Builder builder = null;

        if (kind == TypeSpec.Kind.INTERFACE)
        {
            builder = TypeSpec.interfaceBuilder(clazz);
        }
        else if(kind == TypeSpec.Kind.CLASS && isFully)
        {
            builder = com.squareup.javapoetx.TypeSpec.classBuilder(clazz,true);
        }
        else
        {
            builder = com.squareup.javapoetx.TypeSpec.classBuilder(clazz,false);
        }

        if(null != extend){
            builder.addSuperinterface(extend);
        }

        if(null != implement){
            builder.addSuperAbstract(implement);
        }

        if(null != fields && !fields.isEmpty()){
            builder.addFields(fields);
        }

        if(null != method && !method.isEmpty()){
            builder.addMethods(method);
        }

        if(null != modifiers && modifiers.length > 0){
            builder.addModifiers(modifiers);
        }

        if(null != annotated){

            if(null != annotationsVal && annotationsVal.length > 0) {
                builder
                        .addAnnotation(com.squareup.javapoetx.AnnotationSpec.builder(annotated)
                                .addMembers(annotationsVal)
                                .build());
            } else {
                builder.addAnnotation(annotated);
            }

        }

        typeSpec = builder.build();

        com.squareup.javapoetx.JavaFile javaFile = com.squareup.javapoetx.JavaFile.builder(pkg,typeSpec).build();

        File file = new File(root);

        javaFile.writeTo(file);
    }

    /**
     * 构建字段
     * @param infos
     * @return
     */
    private static List<FieldSpec> getClassFields(FieldsInfo[] infos){
        List<FieldSpec> fields = new ArrayList<>();

        for (FieldsInfo info : infos) {
            if(info.getType() instanceof Type) {
                fields.add(com.squareup.javapoetx.FieldSpec.builder((Type)info.getType(), info.getName(), info.getModifier())
                        .addAnnotation(com.squareup.javapoetx.AnnotationSpec.builder(JpaColumn.class)
                                .addMember("value", "$S", camel4underline(info.getName())).build())
                        .build());
            }
            else if(info.getType() instanceof TypeName){
                fields.add(com.squareup.javapoetx.FieldSpec.builder((TypeName) info.getType(), info.getName(), info.getModifier())
                        .addAnnotation(com.squareup.javapoetx.AnnotationSpec.builder(JpaColumn.class)
                                .addMember("value", "$S", camel4underline(info.getName())).build())
                        .build());
            }
        }
        return fields;
    }

    /**
     * 构建复杂字段01
     * @return
     */
    private static com.squareup.javapoetx.TypeName getComplexField_01(){

        com.squareup.javapoetx.ClassName locationVo = com.squareup.javapoetx.ClassName.get("com.abraham.coder.jpa.vo", "LocationVo");

        com.squareup.javapoetx.ClassName list = com.squareup.javapoetx.ClassName.get("java.util", "List");

        return com.squareup.javapoetx.ParameterizedTypeName.get(list,
                com.squareup.javapoetx.ParameterizedTypeName.get(list,locationVo));
    }

    /**
     * 构建继承
     * @return
     */
    private static com.squareup.javapoetx.TypeName getExtends(String clazz, String... generics){

        List<com.squareup.javapoetx.ClassName> genericsList = new ArrayList<>();

        for (String clz : generics) {

            genericsList.add(com.squareup.javapoetx.ClassName.get(clz.substring(0, clz.lastIndexOf(".")), clz.substring(clz.lastIndexOf(".") + 1)));

        }

        com.squareup.javapoetx.ClassName repository = com.squareup.javapoetx.ClassName.get(clazz.substring(0,clazz.lastIndexOf(".")), clazz.substring(clazz.lastIndexOf(".")+ 1) );

        return com.squareup.javapoetx.ParameterizedTypeName.get(repository, genericsList.stream().toArray(com.squareup.javapoetx.ClassName[]::new));
    }

    /**
     * 得到ServiceImpl所有的方法
     * @return
     */
    private static List<com.squareup.javapoetx.MethodSpec> getServiceImplMethods(){
        List<com.squareup.javapoetx.MethodSpec> methods = new ArrayList<>();

        com.squareup.javapoetx.ClassName list = com.squareup.javapoetx.ClassName.get("java.util", "List");
        com.squareup.javapoetx.ClassName arrayList = com.squareup.javapoetx.ClassName.get("java.util", "ArrayList");

        com.squareup.javapoetx.ClassName nodeMatchDeduceDto = com.squareup.javapoetx.ClassName.get("com.abraham.coder.jpa.dto", "NodeMatchDeduceDto");
        com.squareup.javapoetx.ClassName breakerInfo = com.squareup.javapoetx.ClassName.get("com.abraham.coder.jpa.entity", "BreakerInfo");


        com.squareup.javapoetx.TypeName listOfNodeMatchDeduceDto = com.squareup.javapoetx.ParameterizedTypeName.get(list, nodeMatchDeduceDto);
        com.squareup.javapoetx.TypeName listOfBreakerInfo = com.squareup.javapoetx.ParameterizedTypeName.get(list,breakerInfo);

        com.squareup.javapoetx.MethodSpec findBySubstationGisId = com.squareup.javapoetx.MethodSpec.methodBuilder("findBySubstationGisId")
                .addModifiers(Modifier.PUBLIC)
                .addParameter(String.class, "subGisId")
                .addStatement("$T result = new $T<>()", listOfNodeMatchDeduceDto, arrayList)
                .addStatement("return result.isEmpty() ? $T.emptyList() : result", Collections.class)
                .returns(listOfNodeMatchDeduceDto)
                .build();


        com.squareup.javapoetx.MethodSpec findMatchNode = com.squareup.javapoetx.MethodSpec.methodBuilder("findMatchNode")
                .addModifiers(Modifier.PUBLIC)
                .addParameter(Double.class,"latitude")
                .addParameter(Double.class,"longitude")
                .addParameter(Double.class,"radius")
                .addStatement("$T result = new $T<>()", listOfBreakerInfo, arrayList)
                .addStatement("return result.isEmpty() ? $T.emptyList() : result", Collections.class)
                .returns(listOfBreakerInfo)
                .build();

        methods.add(findBySubstationGisId);

        methods.add(findMatchNode);

        return methods;
    }


    /**
     * 构建所有的方法
     * @return
     */
    private static List<com.squareup.javapoetx.MethodSpec> getMethods(MethodInfo... infos){
        List<com.squareup.javapoetx.MethodSpec> methods = new ArrayList<>();

        for (MethodInfo info : infos) {

            com.squareup.javapoetx.ClassName type = com.squareup.javapoetx.ClassName.get(info.getType().substring(0,info.getType().lastIndexOf(".")),info.getType().substring(info.getType().lastIndexOf(".")+1));

            com.squareup.javapoetx.ClassName generics = com.squareup.javapoetx.ClassName.get(info.getGenerics().substring(0,info.getGenerics().lastIndexOf(".")),info.getGenerics().substring(info.getGenerics().lastIndexOf(".")+1));

            com.squareup.javapoetx.TypeName clazz = generics !=null ? com.squareup.javapoetx.ParameterizedTypeName.get(type,generics) : com.squareup.javapoetx.ParameterizedTypeName.get(type);

            MethodSpec.Builder builder = MethodSpec.methodBuilder(info.getName());

            if(null != info.getModifiers() && info.getModifiers().length > 0){
                builder.addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT);
            }

            if(null != info.getAnnotationsVal() && info.getAnnotationsVal().length > 0) {
                builder
                        .addAnnotation(com.squareup.javapoetx.AnnotationSpec.builder(info.getAnnotated())
                                .addMembers(info.getAnnotationsVal())
                                .build());
            }

            if(null != info.getParametersVal() && info.getParametersVal().length > 0){
                builder.addParameters(info.getParametersVal());
            }

            if(null != info.getCodesVal() && info.getCodesVal().length > 0){
                builder.addStatements(info.getCodesVal());
            }

            builder.returns(clazz);

            methods.add(builder.build());
        }

        return methods;
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
}
