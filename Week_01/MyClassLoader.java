package com.zzy.stduy;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MyClassLoader extends ClassLoader{
    public static void main(String[] args) throws IllegalAccessException {
        Class<?> clazz = null;
        try {
            clazz = new MyClassLoader().findClass("Hello");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            Method hello = clazz.getDeclaredMethod("hello");
            hello.invoke(clazz.newInstance());
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] data = parseData();
        return defineClass(name, data, 0, data.length);
    }

    private byte[] parseData() throws ClassNotFoundException {
        //use absolute path
        String fileName = "C:\\workspace\\Test\\src\\Hello.xlass";
        byte[] bytes;
        try {
            File file = new File(fileName);
            bytes = new byte[(int) file.length()];
            FileInputStream input = null;
            try {
                input = new FileInputStream(file);
                input.read(bytes);
            } finally {
                if (input != null) {
                    input.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw  new ClassNotFoundException("file not found");
        }
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte)(255 - bytes[i]);
        }
        return bytes;
    }
}
