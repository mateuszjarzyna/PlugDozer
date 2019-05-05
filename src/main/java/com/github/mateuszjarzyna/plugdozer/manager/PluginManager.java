package com.github.mateuszjarzyna.plugdozer.manager;

import com.github.mateuszjarzyna.plugdozer.source.PluginSource;

/**
 *
 */
public interface PluginManager {

    void loadPlugins(PluginSource pluginSource);

    <P> P getInstance(Class<P> clazz);

}
