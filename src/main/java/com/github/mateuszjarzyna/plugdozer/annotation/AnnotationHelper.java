package com.github.mateuszjarzyna.plugdozer.annotation;

public class AnnotationHelper {

    public static boolean isPlugin(Object plugin) {
        return isPlugin(plugin.getClass());
    }

    public static boolean isPlugin(Class<?> clazz) {
        return clazz.isAnnotationPresent(Plugin.class);
    }

    public static String getPluginName(Object plugin) {
        return getPluginName(plugin.getClass());
    }

    public static String getPluginName(Class<?> clazz) {
        Plugin plugin = clazz.getAnnotation(Plugin.class);
        String pluginName = plugin.name();
        return maybeCreatePluginName(clazz, pluginName);
    }

    private static String maybeCreatePluginName(Class<?> clazz, String pluginName) {
        if (pluginName == null || pluginName.trim().isEmpty()) {
            return clazz.getName();
        }
        return pluginName;
    }

}
