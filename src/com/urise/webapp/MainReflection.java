package com.urise.webapp;

import com.urise.webapp.model.Resume;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainReflection {

    public static void main(String[] args) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Resume resume = new Resume();
        Field field = resume.getClass().getDeclaredFields()[0];
        System.out.println(resume.getUuid());
        field.setAccessible(true);
        field.get(resume);
        field.set(resume, "newuuid");
        field.setAccessible(false);
        System.out.println(resume.getUuid());
        Method method = resume.getClass().getMethod("toString");
        System.out.println(method.invoke(resume));
    }
}
