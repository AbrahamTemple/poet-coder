package com.builder.apply;

import com.abraham.coder.jpa.domain.FieldsInfo;
import com.abraham.coder.sql.exception.PkgException;
import com.builder.anno.Coder;
import com.builder.bean.CoderContext;
import com.builder.constant.NamingConst;
import com.builder.domain.AnnotationGroup;
import com.builder.domain.MethodInfo;
import com.builder.struct.clazz.DefaultClassCoder;
import com.builder.struct.method.DefaultMethodCoder;
import com.common.utils.ObjectUtils;
import com.squareup.javapoetx.FieldSpec;
import com.squareup.javapoetx.MethodSpec;
import com.squareup.javapoetx.TypeSpec;

import javax.lang.model.element.Modifier;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @Description 接口定义
 * @author AbrahamVong
 * @since 30/5/2022
 */
public abstract class InterfaceDefinition extends Definition{

    public InterfaceDefinition(CoderContext context, String clazzName, Class<?> extend, Class<?>[] impls, MethodInfo[] infos) {
        try {
            Class<?> clazz = context.getBean(clazzName).getClass();
            init(clazz,extend, impls, infos);
        } catch (Exception e) {
            throw new PkgException();
        }
    }

    private void init(Class<?> clazz, Class<?> extend, Class<?>[] impls, MethodInfo[] infos){
        String parentPkg = clazz.getPackage().getName().substring(0, clazz.getPackage().getName().lastIndexOf(NamingConst.DOT));
        String buildName = clazz.getName().substring(clazz.getName().lastIndexOf(NamingConst.DOT)+1) + NamingConst.SERVICE;
        this.clazzPkg = parentPkg + NamingConst.SERVICE_PKG + buildName;
        this.kind = TypeSpec.Kind.INTERFACE;
        this.modifiers = new Modifier[]{Modifier.PUBLIC};
        this.isImpl = 0;
        this.fields = null;
        this.extend = extend.getName();
        this.implement = impls.length == 1 ? impls[0] : impls;
        this.methods = getMethods(infos);
        this.annotations = getClassAnnotations();
    }

    private List<MethodSpec> getMethods(MethodInfo[] infos){
        return new DefaultMethodCoder().methodStruct(infos);
    }

    public abstract List<AnnotationGroup> getClassAnnotations();
}
