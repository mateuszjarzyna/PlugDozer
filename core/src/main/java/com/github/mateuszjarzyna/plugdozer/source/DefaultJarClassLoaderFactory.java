package com.github.mateuszjarzyna.plugdozer.source;

import java.net.URL;
import java.net.URLClassLoader;

class DefaultJarClassLoaderFactory implements JarClassLoaderFactory {

    @Override
    public void prepare(URL[] jars) {
    }

    @Override
    public ClassLoader createClassLoader(URL jar) {
        return new URLClassLoader(new URL[] {jar}, getClass().getClassLoader());
    }

}
