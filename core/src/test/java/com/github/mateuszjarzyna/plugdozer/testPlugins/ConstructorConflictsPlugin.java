package com.github.mateuszjarzyna.plugdozer.testPlugins;

import com.github.mateuszjarzyna.plugdozer.annotation.Plugin;

import javax.inject.Inject;

@Plugin
public class ConstructorConflictsPlugin {

    public ConstructorConflictsPlugin() {

    }

    @Inject
    public ConstructorConflictsPlugin(EchoPlugin echoPlugin) {

    }

    @Inject
    public ConstructorConflictsPlugin(SimpleDependencyPlugin simpleDependencyPlugin) {

    }

}
