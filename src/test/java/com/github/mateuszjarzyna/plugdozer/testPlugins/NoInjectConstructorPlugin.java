package com.github.mateuszjarzyna.plugdozer.testPlugins;

import com.github.mateuszjarzyna.plugdozer.annotation.Plugin;

@Plugin
public class NoInjectConstructorPlugin {

    public NoInjectConstructorPlugin(SimpleDependencyPlugin simpleDependencyPlugin) {

    }

}
