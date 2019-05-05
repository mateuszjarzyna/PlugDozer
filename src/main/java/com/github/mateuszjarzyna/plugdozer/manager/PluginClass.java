package com.github.mateuszjarzyna.plugdozer.manager;

public class PluginClass {

    private final Class pluginClass;
    private final String pluginName;

    PluginClass(Class pluginClass, String pluginName) {
        this.pluginClass = pluginClass;
        this.pluginName = pluginName;
    }

    public Class getPluginClass() {
        return pluginClass;
    }

    public String getPluginName() {
        return pluginName;
    }

}
