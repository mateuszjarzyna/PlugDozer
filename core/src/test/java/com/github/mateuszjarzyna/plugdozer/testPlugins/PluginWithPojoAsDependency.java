package com.github.mateuszjarzyna.plugdozer.testPlugins;

import com.github.mateuszjarzyna.plugdozer.annotation.Plugin;

import javax.inject.Inject;

@Plugin
public class PluginWithPojoAsDependency {

    private final DummyClass pojo;

    @Inject
    public PluginWithPojoAsDependency(DummyClass dummyClass) {
        pojo = dummyClass;
    }

    public DummyClass getPojo() {
        return pojo;
    }
}
