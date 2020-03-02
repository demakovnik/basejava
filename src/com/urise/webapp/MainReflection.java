package com.urise.webapp;

import com.urise.webapp.model.Resume;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MainReflection {

    public static void main(String[] args) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Resume resume = new Resume("abc");
        Field field = resume.getClass().getDeclaredFields()[0];
        System.out.println(resume.getUuid());
        field.setAccessible(true);
        field.get(resume);
        field.set(resume, "newuuid");
        field.setAccessible(false);
        System.out.println(resume.getUuid());
        Method method = resume.getClass().getMethod("toString");
        System.out.println(method.invoke(resume));
        Map<String,String> map = new HashMap<>();
        map.put("hello1".intern(),"hello1value".intern());
        map.put("hello2".intern(),"hello2vv".intern());
        map.put("hello3".intern(),"hello3ff".intern());
        Iterator<Map.Entry<String,String>> iterator = map.entrySet().iterator();
        Map.Entry<String,String> entry0 = null;
        Map.Entry<String,String> entry;
        while(iterator.hasNext()) {
            entry = iterator.next();
            System.out.println(entry.equals(entry0));
            entry0 = entry;
        }

    }
}
