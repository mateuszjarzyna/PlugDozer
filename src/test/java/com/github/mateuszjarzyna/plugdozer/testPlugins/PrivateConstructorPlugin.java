package com.github.mateuszjarzyna.plugdozer.testPlugins;

import com.github.mateuszjarzyna.plugdozer.annotation.Plugin;

import javax.inject.Inject;

@Plugin
public class PrivateConstructorPlugin {

    private PrivateConstructorPlugin() {

    }

    @Inject
    private PrivateConstructorPlugin(SimpleDependencyPlugin simpleDependencyPlugin) {

    }

}
