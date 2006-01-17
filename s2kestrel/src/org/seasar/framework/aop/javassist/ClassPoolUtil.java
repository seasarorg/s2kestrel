package org.seasar.framework.aop.javassist;

import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;

import javassist.ClassPool;
import javassist.LoaderClassPath;

/**
 * @author koichik
 */
public class ClassPoolUtil {
    //static fields
    protected static final Map classPoolMap = Collections.synchronizedMap(new WeakHashMap());

    public static ClassPool getClassPool() {
        final ClassLoader classLoader = getClassLoader();

        ClassPool classPool = (ClassPool) classPoolMap.get(classLoader);
        if (classPool == null) {
            if (classLoader == null) {
                return ClassPool.getDefault();
            }
            classPool = new ClassPool();
            classPool.appendClassPath(new LoaderClassPath(classLoader));
            classPoolMap.put(classLoader, classPool);
        }
        return classPool;
    }

    protected static ClassLoader getClassLoader() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if (classLoader != null) {
            return classLoader;
        }

        classLoader = ClassPoolUtil.class.getClassLoader();
        if (classLoader != null) {
            return classLoader;
        }

        return null;
    }
}