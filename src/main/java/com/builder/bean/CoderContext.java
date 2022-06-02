package com.builder.bean;

import com.abraham.coder.jpa.dto.NodeMatchDeduceDto;
import com.abraham.coder.sql.anno.Generate;
import com.abraham.coder.sql.exception.PkgException;
import com.common.utils.PackageUtils;
import com.common.utils.StringUtils;
import lombok.SneakyThrows;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description
 * @author AbrahamVong
 * @since 1/6/2022
 */
public class CoderContext {

    /**
     * 包
     */
    private String packageName;

    /**
     * 容器
     */
    private ConcurrentHashMap<String, Object> beanMap = null;


    @SneakyThrows
    public CoderContext(String packageName) {
        if(StringUtils.isEmpty(packageName)){
            throw new PkgException("cannot found the package...");
        }
        this.packageName = packageName;
        initBean();
    }

    public Object getBean(String beanid) throws Exception {
        if (beanid==null||beanid.equals("")){
            throw new Exception("beanid not null");
        }
        Object o = beanMap.get(beanid);
        if (o==null){
            throw new Exception("class not find");
        }
        return o;
    }

    private void initBean() throws Exception {
        beanMap = getIncludeAnnoClass();
        AttriAssign();
    }

    private void AttriAssign() throws IllegalAccessException {
        if (beanMap!=null&&beanMap.size()>0){
            for (Object o:beanMap.values()){
                Class<? extends Object> classInfo = o.getClass();
                Field[] declaredFields = classInfo.getDeclaredFields();
                for (Field field : declaredFields) {
                    String name = field.getName();
                    Object bean = beanMap.get(name);
                    if (bean != null) {
                        field.setAccessible(true);
                        field.set(o, bean);
                    }
                }
            }
        }
    }


    private ConcurrentHashMap<String,Object> getIncludeAnnoClass() throws Exception {
        ConcurrentHashMap<String,Object> concurrentHashMap = null;
        List<Class<?>> allClasses = PackageUtils.getClasses(packageName);
        if (allClasses.size() == 0){
            throw new Exception("package not class");
        }
        concurrentHashMap = new ConcurrentHashMap<String, Object>();
        for (Class<?> c : allClasses) {
            if (c.getDeclaredAnnotation((Class<? extends Annotation>) Generate.class) != null){
                Object newInstance = c.newInstance();
                String beanId = toLowerCaseFirstOne(c.getSimpleName());
                concurrentHashMap.put(beanId, newInstance);
            }
        }
        return concurrentHashMap;
    }

    public static String toLowerCaseFirstOne(String s) {
        if (Character.isLowerCase(s.charAt(0))) {
            return s;
        } else {
            return Character.toLowerCase(s.charAt(0)) + s.substring(1);
        }
    }

}
