package com.heleyquin.dto;

import com.google.common.base.CaseFormat;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

public class Test {

    public  static Map<String, Object> getParams(Object o) {
        Map<String, Object> params = new HashMap<>();
        Field[] fields = o.getClass().getDeclaredFields();
        for(Field f : fields) {
            try {
                f.setAccessible(true);
                Object value = f.get(o);
                if (Objects.nonNull(value)) {
                    params.put(CaseFormat.LOWER_CAMEL.converterTo(CaseFormat.LOWER_UNDERSCORE).convert(f.getName()), value);
                }
            } catch (Exception e) {

            }
        }
        return params;
    }

    public static void main(String[] args) {
        System.out.println("ha");
    }

}



