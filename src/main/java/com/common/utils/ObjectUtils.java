package com.common.utils;

import lombok.SneakyThrows;
import org.springframework.beans.BeanUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Description
 * @author AbrahamVong
 * @since 27/4/2022
 */
public class ObjectUtils {

    /**
     * 得到对象所有的数字类型字段值
     * @param obj 对象
     * @return
     */
    @SneakyThrows
    public static List<Integer> getObjectNumberFields(Object obj){

        Field[] fields = obj.getClass().getDeclaredFields();

        List<Integer> values = new ArrayList<>();

        for (Field field : fields) {

            field.setAccessible(true);

            String value = field.get(obj).toString();

            if (isNumeric(value)) {

                values.add(Integer.parseInt(value));

            }
        }

        return values;
    }

    /**
     * 得到对象内指定除外的的字段
     * @param clazz 对象
     * @param excludes 定义除外的字段
     * @return
     */
    public static List<String> getObjectFields(Class<?> clazz, String...excludes) {
        List<Field> fieldList = new ArrayList<>();
        while (clazz != null) {
            fieldList.addAll(new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())));
            clazz = clazz.getSuperclass();
        }

        Field[] fields = fieldList.stream().toArray(Field[]::new);
        List<String> result = new ArrayList<>();

        for (Field field : fields) {
            result.add(field.getName());
        }

        for (String fieldName : excludes) {
            result.remove(fieldName);
        }

        return result;
    }

    /**
     * 得到类带指定注解的字段
     * @param clazz 对象
     * @param annotatedClass 注解
     * @return
     */
    public static List<String> getAnnotationFields(Class<?> clazz, Class<? extends Annotation> annotatedClass){
        List<String> fields = new ArrayList<>();
        Field[] declaredFields = clazz.getDeclaredFields();
        if (declaredFields.length != 0)
        {
            for (Field field : declaredFields)
            {
                if (field.getAnnotation(annotatedClass) != null)
                {
                    fields.add(field.getName());
                }
            }
            return fields;
        }
        return null;
    }

    /**
     * Object转化List
     * @param obj 对象
     * @param clazz 转化的列表类型
     * @param <T> 列表类型
     * @return
     */
    public static <T> List<T> castList(Object obj, Class<T> clazz)
    {
        List<T> result = new ArrayList<T>();
        if(obj instanceof List<?>)
        {
            for (Object o : (List<?>) obj)
            {
                result.add(clazz.cast(o));
            }
            return result;
        }
        return null;
    }

    /**
     * 将多个相同的对象添加到列表当中
     * @param merged 多个对象
     * @param <T> 对象类型
     * @return
     */
    @SafeVarargs
    public static <T> List<T> getMergedList(T ...merged){

        return new ArrayList<T>(Arrays.asList(merged));
    }


    /**
     * 返回字段交换的对象
     * @param target 目标交换对象
     * @param result 交换结果对象
     * @param <T> 对象类型
     * @return
     */
    public static <T> T tempObject(Object target, T result){
        BeanUtils.copyProperties(target, result);
        return result;
    }

    /**
     * 是否为数字字符串
     * @param str 字符串
     * @return
     */
    private static boolean isNumeric(String str) {
        return str != null && str.matches("-?\\d+(\\.\\d+)?");
    }
}
