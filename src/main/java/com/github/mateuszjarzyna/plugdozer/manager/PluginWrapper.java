package com.github.mateuszjarzyna.plugdozer.manager;

class PluginWrapper<P> {

    private final P instance;
    private final String name;
    private final Class<P> type;

    PluginWrapper(String name, P instance, Class<P> type) {
        this.instance = instance;
        this.name = name;
        this.type = type;
    }

    Object getInstance() {
        return instance;
    }

    String getName() {
        return name;
    }

    Class<P> getType() {
        return type;
    }
}
