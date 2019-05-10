package com.github.mateuszjarzyna.plugdozer.manager;

import com.github.mateuszjarzyna.plugdozer.source.PluginSource;

import java.util.List;
import java.util.Optional;

/**
 *
 */
public interface PluginManager {

    void loadPlugins(PluginSource pluginSource);

    <P> void addInstance(P plugin);

    <P> void addInstance(String pluginName, P pluginInstance);

    <P> Optional<P> getInstance(String pluginName, Class<P> type);

    <P> Optional<P> getInstance(Class<P> clazz);

    <P> List<P> getInstances(Class<?> clazz);

    boolean hasPlugin(Class<?> clazz);

    boolean hasPlugin(String name);

}
