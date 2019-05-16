package com.github.mateuszjarzyna.plugdozer.manager;

import com.github.mateuszjarzyna.plugdozer.exception.CannotResolveDependency;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

class PluginFactory {

    private final PluginManager pluginManager;

    PluginFactory(PluginManager pluginManager) {
        this.pluginManager = pluginManager;
    }

    Object createPlugin(PluginConstructorWrapper wrapper) {
        List<Object> parameters = new ArrayList<>();
        Constructor<?> constructor = wrapper.getConstructor();
        for (Class<?> param : constructor.getParameterTypes()) {
            Object instance = pluginManager.getInstance(param)
                    .orElseThrow(() -> new CannotResolveDependency(String.format("Not found instance of %s", param)));
            parameters.add(instance);
        }

        try {
            return constructor.newInstance(parameters.toArray());
        } catch (Exception e) {
            String msg = String.format("Cannot create instance of %s", wrapper.getClazz());
            throw new CannotResolveDependency(msg);
        }
    }

}
