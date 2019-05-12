package com.github.mateuszjarzyna.plugdozer.exception;

import java.util.List;

public class CycleInDependencyGraph extends PlugDozerException {

    public CycleInDependencyGraph(Class<?> plugin, List<Class<?>> cycle) {
        super(String.format("Found cycle in plugin %s - %s", plugin, cycle));
    }

}
