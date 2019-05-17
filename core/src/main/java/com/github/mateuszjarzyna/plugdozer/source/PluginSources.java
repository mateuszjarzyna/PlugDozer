package com.github.mateuszjarzyna.plugdozer.source;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class PluginSources {

    public static PluginSource simple(Class<?>... pluginClasses) {
        return simple(Arrays.asList(pluginClasses));
    }

    public static PluginSource simple(List<Class<?>> pluginClasses) {
        return new SimpleClassSource(pluginClasses);
    }

    public static PluginSource jar(Path directoryWithJars) {
        return new JarSource(directoryWithJars);
    }

    public static PluginSource jar(Path directoryWithJars, JarClassLoaderFactory jarClassLoaderFactory) {
        return new JarSource(directoryWithJars, jarClassLoaderFactory);
    }

}
