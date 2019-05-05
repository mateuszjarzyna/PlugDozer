package com.github.mateuszjarzyna.plugdozer.manager.guice;

import com.github.mateuszjarzyna.plugdozer.manager.PluginClass;
import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

import java.util.List;

class PluginModule extends AbstractModule {

    private final List<PluginClass> plugins;

    PluginModule(List<PluginClass> plugins) {
        this.plugins = plugins;
    }

    @Override
    protected void configure() {
        for (PluginClass pc : plugins) {
            bind(pc.getPluginClass())
                    .annotatedWith(Names.named(pc.getPluginName()))
                    .to(pc.getPluginClass());
        }
    }

}
