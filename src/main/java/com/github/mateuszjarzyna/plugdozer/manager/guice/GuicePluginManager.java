package com.github.mateuszjarzyna.plugdozer.manager.guice;

import com.github.mateuszjarzyna.plugdozer.exception.PluginAlreadyLoaded;
import com.github.mateuszjarzyna.plugdozer.manager.ManagerHelper;
import com.github.mateuszjarzyna.plugdozer.manager.PluginClass;
import com.github.mateuszjarzyna.plugdozer.manager.PluginManager;
import com.github.mateuszjarzyna.plugdozer.source.PluginSource;
import com.google.inject.Guice;
import com.google.inject.Injector;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GuicePluginManager implements PluginManager {

    private final Map<String, PluginClass> allPlugins;

    private Injector injector;

    public GuicePluginManager() {
        this.allPlugins = new HashMap<>();
        this.injector = Guice.createInjector();
    }

    @Override
    public void loadPlugins(PluginSource pluginSource) {
        List<PluginClass> pluginClasses = ManagerHelper.getPluginClasses(pluginSource);
        rememberPlugins(pluginClasses);
        PluginModule module = new PluginModule(pluginClasses);
        this.injector = this.injector.createChildInjector(module);
    }

    private void rememberPlugins(List<PluginClass> classes) {
        for (PluginClass pc : classes) {
            if (allPlugins.containsKey(pc.getPluginName())) {
                Class alreadyLoaded = allPlugins.get(pc.getPluginName()).getPluginClass();
                throw new PluginAlreadyLoaded(pc.getPluginName(), pc.getPluginClass(), alreadyLoaded);
            }
        }
    }

    @Override
    public <P> P getInstance(Class<P> clazz) {
        return injector.getInstance(clazz);
    }

}
