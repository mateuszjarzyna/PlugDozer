package com.github.mateuszjarzyna.plugdozer.source;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

class SimpleClassSource implements PluginSource {

    private final Set<Class<?>> classes;

    SimpleClassSource(Collection<Class<?>> classes) {
        this.classes = new HashSet<>(classes);
    }

    @Override
    public Set<Class<?>> loadClasses() {
        return this.classes;
    }
}
