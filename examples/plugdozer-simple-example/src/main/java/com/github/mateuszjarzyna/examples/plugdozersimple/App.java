package com.github.mateuszjarzyna.examples.plugdozersimple;

import com.github.mateuszjarzyna.examples.plugdozersimple.plugin.*;
import com.github.mateuszjarzyna.plugdozer.annotation.PlugDozerHelper;
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
        pluginManager.addInstance(new AncientLanguage());

        // Get plugins by interface
        List<Hello> instances = pluginManager.getInstances(Hello.class);
        instances.forEach(plugin -> System.out.println(plugin.sayHello("PlugDozer's user!")));

        // Get plugin by custom name from annotation
        Optional<Hello> ancient = pluginManager.getInstance("ancient", Hello.class);
        ancient.ifPresent(hello -> System.out.println(hello.sayHello(" man from the future!")));

        // Get plugin by class
        Optional<ItalianHello> maybeItalianHello = pluginManager.getInstance(ItalianHello.class);
        if (maybeItalianHello.isPresent()) {
            System.out.println("Italian plugin is registered");
        } else {
            System.out.println("Italian plugin does not exist");
        }

        // Add plugin with custom name
        pluginManager.addInstance("italian", new ItalianHello());

        // Get plugin by name
        Optional<ItalianHello> italianPluginByName = pluginManager.getInstance("italian", ItalianHello.class);
        italianPluginByName.ifPresent(p -> System.out.println(p.sayHello("my friend")));
    }

}
