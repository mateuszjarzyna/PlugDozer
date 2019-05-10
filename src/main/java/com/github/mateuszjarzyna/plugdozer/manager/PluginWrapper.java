package com.github.mateuszjarzyna.plugdozer.manager;

public class PluginWrapper<P> {

    private final P instance;
    private final String name;
    private final Class<P> type;

    public PluginWrapper(String name, P instance, Class<P> type) {
        this.instance = instance;
        this.name = name;
        this.type = type;
    }

    public Object getInstance() {
        return instance;
    }

    public String getName() {
        return name;
    }

    public Class<P> getType() {
        return type;
    }
}
