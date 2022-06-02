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
import com.common.utils.PackageUtils;
import com.squareup.javapoetx.AnnotationVal;
import com.squareup.javapoetx.FieldSpec;
import com.squareup.javapoetx.MethodSpec;
import com.squareup.javapoetx.TypeSpec;

import javax.lang.model.element.Modifier;
import javax.persistence.Table;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @Description 对象定义
 * @author AbrahamVong
 * @since 30/5/2022
 */
public abstract class EntityDefinition extends Definition{

    public EntityDefinition(CoderContext context,String clazzName, NamingConst buildType) {
        try {
            Class<?> clazz = context.getBean(clazzName).getClass();
            init(clazz,buildType);
        } catch (Exception e) {
            throw new PkgException();
        }
    }

    private void init(Class<?> clazz,NamingConst buildType){
        this.clazzPkg = clazz.getPackage().getName() + NamingConst.DOT + clazz.getPackage().getName().substring(clazz.getPackage().getName().lastIndexOf(NamingConst.DOT)+1)+buildType;
        this.kind = TypeSpec.Kind.CLASS;
        this.modifiers = new Modifier[]{Modifier.PUBLIC};
        this.isImpl = clazz.getInterfaces().length != 0 ? 1 : 0;
        Map<String, Class<?>> fields = ObjectUtils.getAnnoDetailFields(clazz, Coder.class);
        FieldsInfo[] fieldInfos = Objects.requireNonNull(fields).keySet().stream().map(name -> new FieldsInfo(fields.get(name), name, Modifier.PRIVATE)).toArray(FieldsInfo[]::new);
        this.fields = getClassFields(fieldInfos);
        this.extend = clazz.getSuperclass() != Object.class ? new DefaultClassCoder().getExtends(clazz.getSuperclass().getName()) : null;
        this.implement = clazz.getInterfaces();
        this.methods = null;
        this.annotations = getClassAnnotations();
    }

    public abstract List<FieldSpec> getClassFields(FieldsInfo[] infos);

    public abstract List<AnnotationGroup> getClassAnnotations();
}
