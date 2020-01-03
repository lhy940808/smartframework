package com.lhy.javaweb.smart.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileFilter;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 类操作工具类
 * @author liuhaiyan
 * @date 2019-12-30 21:36
 */
@Slf4j
public final class ClassUtil {

    /**
     * @Description :获取类加载器
     * @params []
     * @return java.lang.ClassLoader
     * @author liuhaiyan
     * @date 2019-12-30 21:38
     */
    public static ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();

    }

    /**
     * @Description :加载类
     * @params [className, isInitialized]
     * 这里的初始化是指是否执行类的静态代码块
     * @return java.lang.Class<?>
     * @author liuhaiyan
     * @date 2019-12-30 21:39
     */
    public static Class<?> loadClasss(String className, boolean isInitialized) {

        Class<?> cls;
        try {
            cls = Class.forName(className, isInitialized, getClassLoader());
        } catch (ClassNotFoundException e) {
            log.error("load class error,", e);
            throw new RuntimeException(e);
        }
        return cls;
    }

    /**
     * @Description : 获取指定包名下的所有类
     * @params [packageName]
     * @return java.util.Set<java.lang.Class<?>>
     * @author liuhaiyan
     * @date 2019-12-30 21:40
     */
    public static Set<Class<?>> getClassSet(String packageName) {
        Set<Class<?>> classSet = new HashSet<>();
        try {
            Enumeration<URL> urls = getClassLoader().getResources(packageName.replace(",", "/"));
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                if (url != null) {
                    String protocol = url.getProtocol();
                    if (protocol.equals("file")) {
                        String packagePath = url.getPath().replaceAll("%20", " ");
                        addClass(classSet, packagePath, packageName);
                    } else if (protocol.equals("jar")) {
                        JarURLConnection jarURLConnection = (JarURLConnection)url.openConnection();
                        if (jarURLConnection != null) {
                            JarFile jarFile = jarURLConnection.getJarFile();
                            if (jarFile != null) {
                                Enumeration<JarEntry> jarEntries = jarFile.entries();
                                while (jarEntries.hasMoreElements()) {
                                    JarEntry jarEntry = jarEntries.nextElement();
                                    String jarEntryName = jarEntry.getName();
                                    if (jarEntryName.endsWith(".class")) {
                                        String className = jarEntryName.substring(0, jarEntryName.lastIndexOf(".")).replaceAll("/", ".");
                                        doAddClass(classSet, className);
                                    }
                                }

                            }
                        }
                    }
                }
            }

        }catch (Exception e) {
            log.error("get class set error, ", e);
            throw new RuntimeException(e);
        }
        return classSet;

    }

    /**
     * @Description :
     * @params [classSet, packagePath, packageName]
     * @return void
     * @author liuhaiyan
     * @date 2019-12-31 13:45
     */
    private static void addClass(Set<Class<?>> classSet, String packagePath, String packageName) {
        File[] files = new File(packagePath).listFiles(new FileFilter() {

            @Override
            public boolean accept(File file) {
                return (file.isFile() && file.getName().endsWith(".class")) || file.isDirectory();
            }
        });
        for (File file : files) {
            String fileName = file.getName();
            if (file.isFile()) {
                String className = fileName.substring(0, fileName.lastIndexOf("."));
                if (StringUtil.isNotEmpty(packageName)) {
                    className = packageName + "." + className;
                }
                doAddClass(classSet, className);
            }else {
                String subPackagePath = fileName;
                if (StringUtil.isNotEmpty(packagePath)) {
                    subPackagePath = packagePath + "/" + subPackagePath;
                }
                String subPackageName = fileName;
                if (StringUtil.isNotEmpty(packageName)) {
                    subPackageName = packageName + "." + subPackageName;
                }
                addClass(classSet, subPackagePath, subPackageName);
            }
        }
    }

    private static void doAddClass(Set<Class<?>> classSet, String className) {
        Class<?> cls = loadClasss(className, false);
        classSet.add(cls);
    }
}
