package com.github.mateuszjarzyna.plugdozer.testPlugins;

import com.github.mateuszjarzyna.plugdozer.annotation.Plugin;

import javax.inject.Inject;

@Plugin
public class PluginWithPojoAsDependency {

    @Inject
    public PluginWithPojoAsDependency(DummyClass dummyClass) {

    }

}
