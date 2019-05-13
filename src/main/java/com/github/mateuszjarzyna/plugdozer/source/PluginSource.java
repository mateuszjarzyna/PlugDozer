package com.github.mateuszjarzyna.plugdozer.source;

import java.util.Set;

@FunctionalInterface
public interface PluginSource {

    Set<Class<?>> loadClasses();

}
