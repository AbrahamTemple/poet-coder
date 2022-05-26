package com.abraham.coder.jpa;

import com.abraham.coder.jpa.domain.FieldsInfo;
import com.abraham.coder.jpa.domain.MethodInfo;
import com.abraham.coder.jpa.service.BreakerInfoService;
import com.benliu.jpa.annotation.JpaColumn;
import com.benliu.jpa.annotation.JpaDto;
import com.squareup.javapoetx.FieldSpec;
import com.squareup.javapoetx.JavaFile;
import com.squareup.javapoetx.TypeName;
import lombok.Data;
import lombok.SneakyThrows;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.lang.model.element.Modifier;
import java.io.File;
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

        FieldsInfo[] infos = {
                new FieldsInfo(Integer.class, "nodeType", Modifier.PRIVATE),
                new FieldsInfo(String.class,"nodeCode",Modifier.PRIVATE),
                new FieldsInfo(String.class,"nodeName",Modifier.PRIVATE),
                new FieldsInfo(Integer.class,"transformerType",Modifier.PRIVATE),
                new FieldsInfo(String.class,"location",Modifier.PRIVATE),
                new FieldsInfo(getComplexField_01(),"locationList",Modifier.PRIVATE)
        };

        buildDto("com.abraham.coder.jpa.dto","NodeMatchDeduceDto",getClassFields(infos));
        buildRepository("com.abraham.coder.jpa.repository","BreakerInfoRepository",
                getRepositoryExtends("com.benliu.jpa.repository.BaseRepository","com.abraham.coder.jpa.entity.BreakerInfo","java.lang.String"),
                getRepositoryMethods());
        buildService("com.abraham.coder.jpa.service","BreakerInfoService",
                getServiceExtends("com.benliu.jpa.service.BaseService","com.abraham.coder.jpa.entity.BreakerInfo","java.lang.String"),
                getServiceMethods());
        //无法既又继承，又去实现：接口只能继承、类只能去实现
        buildServiceImpl("com.abraham.coder.jpa.service.impl","BreakerInfoServiceImpl"
                ,getServiceImplExtends("com.benliu.jpa.implement.impl.BaseServiceImpl","com.abraham.coder.jpa.entity.BreakerInfo","java.lang.String")
                ,BreakerInfoService.class
                ,getServiceImplMethods());
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
     * 构建Repository
     * @param pkg
     * @param clazz
     * @param extend
     * @param method
     */
    @SneakyThrows
    private static void buildRepository(String pkg, String clazz, com.squareup.javapoetx.TypeName extend, List<com.squareup.javapoetx.MethodSpec> method){

        com.squareup.javapoetx.JavaFile javaFile = com.squareup.javapoetx.JavaFile.builder(pkg,
                com.squareup.javapoetx.TypeSpec.interfaceBuilder(clazz)
                        .addSuperinterface(extend)
                        .addAnnotation(Repository.class)
                        .addModifiers(Modifier.PUBLIC)
                        .addMethods(method)
                        .build())
                .build();

        File file = new File(root);

        javaFile.writeTo(file);
    }

    /**
     * 构建Service
     * @param pkg
     * @param clazz
     * @param extend
     * @param method
     */
    @SneakyThrows
    private static void buildService(String pkg, String clazz, com.squareup.javapoetx.TypeName extend, List<com.squareup.javapoetx.MethodSpec> method){

        com.squareup.javapoetx.JavaFile javaFile = com.squareup.javapoetx.JavaFile.builder(pkg,
                com.squareup.javapoetx.TypeSpec.interfaceBuilder(clazz)
                        .addSuperinterface(extend)
                        .addModifiers(Modifier.PUBLIC)
                        .addMethods(method)
                        .build())
                .build();

        File file = new File(root);

        javaFile.writeTo(file);
    }

    /**
     * 构建实现类
     * @param pkg
     * @param clazz
     * @param extend
     * @param method
     */
    @SneakyThrows
    private static void buildServiceImpl(String pkg, String clazz, com.squareup.javapoetx.TypeName extend,Class<?> implement, List<com.squareup.javapoetx.MethodSpec> method){

        com.squareup.javapoetx.JavaFile javaFile = JavaFile.builder(pkg,
                com.squareup.javapoetx.TypeSpec.classBuilder(clazz,true)
                        .addSuperinterface(extend)
                        .addSuperAbstract(implement)
                        .addAnnotation(Service.class)
                        .addModifiers(Modifier.PUBLIC)
                        .addMethods(method)
                        .build())
                .build();

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
     * 构建Repository的继承
     * @return
     */
    private static com.squareup.javapoetx.TypeName getRepositoryExtends(String clazz, String... generics){

        List<com.squareup.javapoetx.ClassName> genericsList = new ArrayList<>();

        for(String clz :generics){

            genericsList.add(com.squareup.javapoetx.ClassName.get(clz.substring(0,clz.lastIndexOf(".")), clz.substring(clz.lastIndexOf(".")+ 1)));

        }

        com.squareup.javapoetx.ClassName repository = com.squareup.javapoetx.ClassName.get(clazz.substring(0,clazz.lastIndexOf(".")), clazz.substring(clazz.lastIndexOf(".")+ 1) );

        return com.squareup.javapoetx.ParameterizedTypeName.get(repository, genericsList.stream().toArray(com.squareup.javapoetx.ClassName[]::new));

//        com.squareup.javapoetx.ClassName repository = com.squareup.javapoetx.ClassName.get("com.benliu.jpa.repository", "BaseRepository");
//
//        com.squareup.javapoetx.ClassName breakerInfo = com.squareup.javapoetx.ClassName.get("com.abraham.coder.jpa.entity", "BreakerInfo");
//
//        com.squareup.javapoetx.ClassName string = com.squareup.javapoetx.ClassName.get("java.lang", "String");
//
//        return com.squareup.javapoetx.ParameterizedTypeName.get(repository,breakerInfo,string);
    }

    /**
     * 构建Service的继承
     * @return
     */
    private static com.squareup.javapoetx.TypeName getServiceExtends(String clazz, String... generics){

        List<com.squareup.javapoetx.ClassName> genericsList = new ArrayList<>();

        for(String clz :generics){

            genericsList.add(com.squareup.javapoetx.ClassName.get(clz.substring(0,clz.lastIndexOf(".")), clz.substring(clz.lastIndexOf(".")+ 1)));

        }

        com.squareup.javapoetx.ClassName service = com.squareup.javapoetx.ClassName.get(clazz.substring(0,clazz.lastIndexOf(".")), clazz.substring(clazz.lastIndexOf(".")+ 1) );

        return com.squareup.javapoetx.ParameterizedTypeName.get(service, genericsList.stream().toArray(com.squareup.javapoetx.ClassName[]::new));

//        com.squareup.javapoetx.ClassName breakerInfo = com.squareup.javapoetx.ClassName.get("com.abraham.coder.jpa.entity", "BreakerInfo");
//
//        com.squareup.javapoetx.ClassName string = com.squareup.javapoetx.ClassName.get("java.lang", "String");
//
//        com.squareup.javapoetx.ClassName service = com.squareup.javapoetx.ClassName.get("com.benliu.jpa.service", "BaseService");
//
//        return com.squareup.javapoetx.ParameterizedTypeName.get(service,breakerInfo,string);
    }

    /**
     * 构建ServiceImpl的继承
     * @return
     */
    private static com.squareup.javapoetx.TypeName getServiceImplExtends(String clazz, String... generics){

        List<com.squareup.javapoetx.ClassName> genericsList = new ArrayList<>();

        for(String clz :generics){

            genericsList.add(com.squareup.javapoetx.ClassName.get(clz.substring(0,clz.lastIndexOf(".")), clz.substring(clz.lastIndexOf(".")+ 1)));

        }

        com.squareup.javapoetx.ClassName implement = com.squareup.javapoetx.ClassName.get(clazz.substring(0,clazz.lastIndexOf(".")), clazz.substring(clazz.lastIndexOf(".")+ 1) );

        return com.squareup.javapoetx.ParameterizedTypeName.get(implement, genericsList.stream().toArray(com.squareup.javapoetx.ClassName[]::new));

//        com.squareup.javapoetx.ClassName breakerInfo = com.squareup.javapoetx.ClassName.get("com.abraham.coder.jpa.entity", "BreakerInfo");
//
//        com.squareup.javapoetx.ClassName string = com.squareup.javapoetx.ClassName.get("java.lang", "String");
//
//        com.squareup.javapoetx.ClassName serviceImpl = com.squareup.javapoetx.ClassName.get("com.benliu.jpa.implement.impl", "BaseServiceImpl");
//
//        return com.squareup.javapoetx.ParameterizedTypeName.get(serviceImpl,breakerInfo,string);
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
     * 得到Service所有的方法
     * @return
     */
    private static List<com.squareup.javapoetx.MethodSpec> getServiceMethods(){
        List<com.squareup.javapoetx.MethodSpec> methods = new ArrayList<>();

        com.squareup.javapoetx.ClassName list = com.squareup.javapoetx.ClassName.get("java.util", "List");

        com.squareup.javapoetx.ClassName nodeMatchDeduceDto = com.squareup.javapoetx.ClassName.get("com.abraham.coder.jpa.dto", "NodeMatchDeduceDto");
        com.squareup.javapoetx.ClassName breakerInfo = com.squareup.javapoetx.ClassName.get("com.abraham.coder.jpa.entity", "BreakerInfo");


        com.squareup.javapoetx.TypeName listOfNodeMatchDeduceDto = com.squareup.javapoetx.ParameterizedTypeName.get(list, nodeMatchDeduceDto);
        com.squareup.javapoetx.TypeName listOfBreakerInfo = com.squareup.javapoetx.ParameterizedTypeName.get(list,breakerInfo);

        com.squareup.javapoetx.MethodSpec findBySubstationGisId = com.squareup.javapoetx.MethodSpec.methodBuilder("findBySubstationGisId")
                .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                .addParameter(String.class, "subGisId")
                .returns(listOfNodeMatchDeduceDto)
                .build();


        com.squareup.javapoetx.MethodSpec findMatchNode = com.squareup.javapoetx.MethodSpec.methodBuilder("findMatchNode")
                .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                .addParameter(Double.class,"latitude")
                .addParameter(Double.class,"longitude")
                .addParameter(Double.class,"radius")
                .returns(listOfBreakerInfo)
                .build();

        methods.add(findBySubstationGisId);

        methods.add(findMatchNode);

        return methods;
    }

    /**
     * 得到Repository所有的方法
     * @return
     */
    private static List<com.squareup.javapoetx.MethodSpec> getRepositoryMethods(MethodInfo... method){
        List<com.squareup.javapoetx.MethodSpec> methods = new ArrayList<>();

        for (MethodInfo info : method) {

            com.squareup.javapoetx.ClassName type = com.squareup.javapoetx.ClassName.get(info.getType().substring(0,info.getType().lastIndexOf(".")),info.getType().substring(info.getType().lastIndexOf(".")+1));

            com.squareup.javapoetx.ClassName name = com.squareup.javapoetx.ClassName.get(info.getName().substring(0,info.getName().lastIndexOf(".")),info.getName().substring(info.getName().lastIndexOf(".")+1));

        }



        com.squareup.javapoetx.ClassName list = com.squareup.javapoetx.ClassName.get("java.util", "List");

        com.squareup.javapoetx.ClassName nodeMatchDeduceDto = com.squareup.javapoetx.ClassName.get("com.abraham.coder.jpa.dto", "NodeMatchDeduceDto");
        com.squareup.javapoetx.ClassName breakerInfo = com.squareup.javapoetx.ClassName.get("com.abraham.coder.jpa.entity", "BreakerInfo");


        com.squareup.javapoetx.TypeName listOfNodeMatchDeduceDto = com.squareup.javapoetx.ParameterizedTypeName.get(list, nodeMatchDeduceDto);
        com.squareup.javapoetx.TypeName listOfBreakerInfo = com.squareup.javapoetx.ParameterizedTypeName.get(list,breakerInfo);

        com.squareup.javapoetx.MethodSpec findBySubstationGisId = com.squareup.javapoetx.MethodSpec.methodBuilder("findBySubstationGisId")
                .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                .addAnnotation(com.squareup.javapoetx.AnnotationSpec.builder(Query.class)
                        .addMember("value","$S","")
                        .addMember("nativeQuery","$L",true)
                        .build())
                .addParameter(String.class, "subGisId")
                .returns(listOfNodeMatchDeduceDto)
                .build();


        com.squareup.javapoetx.MethodSpec findMatchNode = com.squareup.javapoetx.MethodSpec.methodBuilder("findMatchNode")
                .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                .addAnnotation(com.squareup.javapoetx.AnnotationSpec.builder(Query.class)
                        .addMember("value","$S","")
                        .addMember("nativeQuery","$L",true)
                        .build())
                .addParameter(Double.class,"latitude")
                .addParameter(Double.class,"longitude")
                .addParameter(Double.class,"radius")
                .returns(listOfBreakerInfo)
                .build();

        methods.add(findBySubstationGisId);

        methods.add(findMatchNode);

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
