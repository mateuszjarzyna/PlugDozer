package com.github.mateuszjarzyna.plugdozer.source;

import java.util.Collection;

@FunctionalInterface
public interface PluginSource {

    Collection<Class<?>> loadClasses();

}
