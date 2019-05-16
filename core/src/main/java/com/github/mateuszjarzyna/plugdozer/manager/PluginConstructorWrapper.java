package com.github.mateuszjarzyna.plugdozer.manager;

import java.lang.reflect.Constructor;

class PluginConstructorWrapper {

    private final Class<?> clazz;
    private final Constructor<?> constructor;

    PluginConstructorWrapper(Class<?> clazz, Constructor<?> constructor) {
        this.clazz = clazz;
        this.constructor = constructor;
    }

    Class<?> getClazz() {
        return clazz;
    }

    Constructor<?> getConstructor() {
        return constructor;
    }

}
