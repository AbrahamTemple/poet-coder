package com.builder.apply;

import com.abraham.coder.jpa.domain.FieldsInfo;
import com.builder.anno.Coder;
import com.builder.bean.CoderContext;
import com.builder.domain.AnnotationGroup;
import com.builder.domain.MethodInfo;
import com.builder.struct.clazz.DefaultClassCoder;
import com.builder.struct.method.DefaultMethodCoder;
import com.common.utils.ObjectUtils;
import com.squareup.javapoetx.FieldSpec;
import com.squareup.javapoetx.MethodSpec;
import com.squareup.javapoetx.TypeSpec;
import lombok.Data;

import javax.lang.model.element.Modifier;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Description 代码生成
 * @author AbrahamVong
 * @since 30/5/2022
 */
@Data
public abstract class Definition {

    protected String clazzPkg;
    protected Modifier[] modifiers;
    protected TypeSpec.Kind kind;
    protected Integer isImpl;
    protected Object extend;
    protected Object implement;
    protected List<AnnotationGroup> annotations;
    protected List<FieldSpec> fields;
    protected List<MethodSpec> methods;

    public Definition(String clazzPkg, Modifier[] modifiers, TypeSpec.Kind kind, Integer isImpl, Object extend, Object implement, List<AnnotationGroup> annotations, List<FieldSpec> fields) {
        this.clazzPkg = clazzPkg;
        this.modifiers = modifiers;
        this.kind = kind;
        this.isImpl = isImpl;
        this.extend = extend;
        this.implement = implement;
        this.annotations = annotations;
        this.fields = fields;
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

    protected Definition() {
    }

    public void create(){
        new DefaultClassCoder().buildJava(clazzPkg, modifiers, kind, isImpl, extend, implement, annotations, fields, methods);
    }

}
