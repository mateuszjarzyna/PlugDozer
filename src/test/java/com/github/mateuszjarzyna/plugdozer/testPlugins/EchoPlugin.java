package com.github.mateuszjarzyna.plugdozer.testPlugins;

import com.github.mateuszjarzyna.plugdozer.annotation.Plugin;

import javax.inject.Inject;

@Plugin
public class EchoPlugin {

    private final SimpleDependencyPlugin dependencyPlugin;

    @Inject
    public EchoPlugin(SimpleDependencyPlugin dependencyPlugin) {
        this.dependencyPlugin = dependencyPlugin;
    }

    public String echo(String sentence) {
        return sentence;
    }

}
