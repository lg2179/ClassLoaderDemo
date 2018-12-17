package com.hikvison.lg.classLoader.demo;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * <p>类说明<p>
 *
 * @author ligang14  2018/12/3
 * @version V1.0
 * @name TestDemo
 */
public class TestDemo {

    public static void main(String[] args)
    {
        //printClassPath();
       // printClassLoader();
        PrintDiskClassLoader();
    }

    /*打印类加载路径*/
    public static void printClassPath()
    {
        System.out.println(System.getProperty("sun.boot.class.path"));
        System.out.println(System.getProperty("java.ext.dirs"));
        System.out.println(System.getProperty("java.class.path"));
    }

    public static void printClassLoader()
    {
        ClassLoader loader = TestDemo.class.getClassLoader();
        //ExtClassLoader的父加载器Bootstrap ClassLoader,但并没有打印出来，这是因为Bootstrap ClassLoader是由C/C++编写的，并不是一个Java类，因此我们无法在Java代码中获取它的引用。
        while (loader != null) {
            System.out.println(loader);
            loader = loader.getParent();
        }
    }

    /*
    * 自定义ClassLoader
    * */
    public static void PrintDiskClassLoader()
    {
        ClassLoader loader = TestDemo.class.getClassLoader();
        DiskClassLoader classLoader=new DiskClassLoader("D:\\outlook");
        try {
            Class c = classLoader.loadClass("com.example.Jobs");//2
            if (c != null) {
                try {
                    Object obj = c.newInstance();
                    Method method = c.getDeclaredMethod("say", null);
                    method.invoke(obj, null);//3
                } catch (InstantiationException | IllegalAccessException
                        | NoSuchMethodException
                        | SecurityException |
                        IllegalArgumentException |
                        InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
