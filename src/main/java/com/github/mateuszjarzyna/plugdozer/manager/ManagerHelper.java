package com.github.mateuszjarzyna.plugdozer.manager;

import com.github.mateuszjarzyna.plugdozer.annotation.AnnotationHelper;
import com.github.mateuszjarzyna.plugdozer.source.PluginSource;

import java.util.List;
import java.util.stream.Collectors;

public class ManagerHelper {

    public static List<PluginClass> getPluginClasses(PluginSource pluginSource) {
        return pluginSource.loadClasses()
                .stream()
                .filter(AnnotationHelper::isPlugin)
                .map(ManagerHelper::toPluginClass)
                .collect(Collectors.toList());

    }

    private static PluginClass toPluginClass(Class<?> clazz) {
        String name = AnnotationHelper.getPluginName(clazz);
        return new PluginClass(clazz, name);
    }

}
