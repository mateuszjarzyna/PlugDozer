package com.github.mateuszjarzyna.examples;

import com.github.mateuszjarzyna.examples.contract.SayHello;
import com.github.mateuszjarzyna.plugdozer.manager.PluginManager;
import com.github.mateuszjarzyna.plugdozer.manager.PluginManagers;
import com.github.mateuszjarzyna.plugdozer.source.PluginSources;

import java.nio.file.Paths;
import java.util.List;

public class App {
    public static void main( String[] args ) {
        PluginManager pluginManager = PluginManagers.defaultManager();

        String dir = System.getProperty("jars", "/tmp/jars");
        pluginManager.loadPlugins(PluginSources.jar(Paths.get(dir)));

        List<SayHello> plugins = pluginManager.getInstances(SayHello.class);
        plugins.forEach(p -> System.out.println(p.sayHello("Robert")));
    }
}
