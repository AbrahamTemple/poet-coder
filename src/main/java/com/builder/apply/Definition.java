package com.builder.apply;

import com.builder.bean.CoderContext;
import com.builder.domain.AnnotationGroup;
import com.builder.struct.clazz.DefaultClassCoder;
import com.squareup.javapoetx.FieldSpec;
import com.squareup.javapoetx.MethodSpec;
import com.squareup.javapoetx.TypeSpec;

import javax.lang.model.element.Modifier;
import javax.swing.table.DefaultTableModel;
import java.util.List;

/**
 * @Description 代码生成
 * @author AbrahamVong
 * @since 30/5/2022
 */
public class Definition {


    private CoderContext context;

    private String clazzPkg;
    private Modifier[] modifiers;
    private TypeSpec.Kind kind;
    private Integer isImpl;
    private Object extend;
    private Object implement;
    private List<AnnotationGroup> annotations;
    private List<FieldSpec> fields;
    private List<MethodSpec> methods;

    public Definition(CoderContext context) {
        this.context = context;
    }

    public Definition(String clazzPkg, Modifier[] modifiers, TypeSpec.Kind kind, Integer isImpl, Object extend, Object implement, List<AnnotationGroup> annotations, List<FieldSpec> fields, List<MethodSpec> methods) {
        this.clazzPkg = clazzPkg;
        this.modifiers = modifiers;
        this.kind = kind;
        this.isImpl = isImpl;
        this.extend = extend;
        this.implement = implement;
        this.annotations = annotations;
        this.fields = fields;
        this.methods = methods;
    }

    private void init(){

    }

    public void create(){
        new DefaultClassCoder().buildJava(clazzPkg,modifiers,kind,isImpl,extend,implement,annotations,fields,methods);
    }
}
