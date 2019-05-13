package com.github.mateuszjarzyna.plugdozer.source;

import java.util.Arrays;
import java.util.List;

public class PluginsSource {

    public static PluginSource simple(Class<?>... pluginClasses) {
        return simple(Arrays.asList(pluginClasses));
    }

    public static PluginSource simple(List<Class<?>> pluginClasses) {
        return new SimpleClassSource(pluginClasses);
    }

}
