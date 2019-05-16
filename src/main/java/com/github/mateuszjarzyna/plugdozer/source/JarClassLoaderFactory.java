package com.github.mateuszjarzyna.plugdozer.source;

import java.net.URL;

public interface JarClassLoaderFactory {

    void prepare(URL[] jars);

    ClassLoader createClassLoader(URL jar);

}
