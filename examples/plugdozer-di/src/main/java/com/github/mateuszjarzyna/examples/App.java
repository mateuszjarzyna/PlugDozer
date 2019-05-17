package com.github.mateuszjarzyna.examples;

import com.github.mateuszjarzyna.examples.plugin.EmailNotifier;
import com.github.mateuszjarzyna.examples.plugin.EmailSender;
import com.github.mateuszjarzyna.examples.plugin.UserNotifier;
import com.github.mateuszjarzyna.examples.repository.UserRepository;
import com.github.mateuszjarzyna.plugdozer.manager.PluginManager;
import com.github.mateuszjarzyna.plugdozer.manager.PluginManagers;
import com.github.mateuszjarzyna.plugdozer.source.PluginSources;

import java.util.List;

public class App {
    public static void main( String[] args ) {
        // Create some fake repository
        UserRepository userRepository = new UserRepository();

        // Create plugin manager
        PluginManager pluginManager = PluginManagers.defaultManager();
        // Register repository in plugin's manager
        pluginManager.addInstance(userRepository);

        // Load plugins. In you application use PluginSources.jar(...) or create your own PluginSource.
        // See other examples and read documentation
        pluginManager.loadPlugins(PluginSources.simple(EmailNotifier.class, EmailSender.class));

        List<UserNotifier> notifiers = pluginManager.getInstances(UserNotifier.class);
        notifiers.forEach(n -> n.notifyUser("patrick12"));
    }
}
