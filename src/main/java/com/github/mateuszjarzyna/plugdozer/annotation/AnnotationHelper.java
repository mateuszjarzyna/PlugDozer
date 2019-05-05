package com.github.mateuszjarzyna.plugdozer.annotation;

import com.google.common.base.Strings;

public class AnnotationHelper {

    public static boolean isPlugin(Class<?> clazz) {
        return clazz.getAnnotation(Plugin.class) != null;
    }

    public static String getName(Class<?> clazz) {
        Plugin plugin = clazz.getAnnotation(Plugin.class);
        String pluginName = plugin.value();
        return maybeCreatePluginName(clazz, pluginName);
    }

    private static String maybeCreatePluginName(Class<?> clazz, String pluginName) {
        if (Strings.isNullOrEmpty(pluginName)) {
            return clazz.getName();
        }
        return pluginName;
    }

}
