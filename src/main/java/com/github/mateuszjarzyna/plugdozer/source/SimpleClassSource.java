package com.github.mateuszjarzyna.plugdozer.source;

import java.util.Collection;

public class SimpleClassSource implements PluginSource {

    private final Collection<Class<?>> classes;

    public SimpleClassSource(Collection<Class<?>> classes) {
        this.classes = classes;
    }

    @Override
    public Collection<Class<?>> loadClasses() {
        return this.classes;
    }
}
