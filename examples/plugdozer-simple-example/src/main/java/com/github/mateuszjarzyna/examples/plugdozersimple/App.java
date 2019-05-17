package com.github.mateuszjarzyna.examples.plugdozersimple;

import com.github.mateuszjarzyna.examples.plugdozersimple.plugin.EnglishHello;
import com.github.mateuszjarzyna.examples.plugdozersimple.plugin.Hello;
import com.github.mateuszjarzyna.examples.plugdozersimple.plugin.ItalianHello;
import com.github.mateuszjarzyna.examples.plugdozersimple.plugin.PolishHello;
import com.github.mateuszjarzyna.plugdozer.manager.PluginManager;
import com.github.mateuszjarzyna.plugdozer.manager.PluginManagers;

import java.util.List;
import java.util.Optional;

/**
 * Very simple example of how to use the PluginManager
 */
public class App {

    public static void main(String[] args) {
        // Create plugin manager
        PluginManager pluginManager = PluginManagers.defaultManager();

        // Add plugins' instances
        pluginManager.addInstance(new EnglishHello());
        pluginManager.addInstance(new PolishHello());

        // Get plugins by interface
        List<Hello> instances = pluginManager.getInstances(Hello.class);
        instances.forEach(plugin -> System.out.println(plugin.sayHello("PlugDozer's user!")));

        // Get plugin by class
        Optional<ItalianHello> maybeItalianHello = pluginManager.getInstance(ItalianHello.class);
        if (maybeItalianHello.isPresent()) {
            System.out.println("Italian plugin is registered");
        } else {
            System.out.println("Italian plugin does not exist");
        }

        // Add named plugin
        pluginManager.addInstance("italian", new ItalianHello());

        // Get plugin by name
        Optional<ItalianHello> italianPluginByName = pluginManager.getInstance("italian", ItalianHello.class);
        italianPluginByName.ifPresent(p -> System.out.println(p.sayHello("my friend")));
    }

}
