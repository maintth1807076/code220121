package com.heleyquin.dto;

import com.google.common.base.CaseFormat;
import lombok.Getter;

import java.lang.reflect.Field;
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
        List<Student> students = new ArrayList<>();
        students.add(new Student("ab"));
        students.add(new Student("a"));
        students.add(new Student("b"));
        Collections.sort(students);
        Collections.sort(students, (s1,s2)-> s2.getName().compareTo(s1.getName()));
        students.stream().map(Student::getName).forEach(System.out::println);
    }

}
@Getter
class Student implements Comparable{
    private String name;

    Student(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}



