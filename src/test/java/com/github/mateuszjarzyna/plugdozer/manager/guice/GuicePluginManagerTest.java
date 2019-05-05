package com.github.mateuszjarzyna.plugdozer.manager.guice;

import com.github.mateuszjarzyna.plugdozer.manager.PluginManager;
import com.github.mateuszjarzyna.plugdozer.source.SimpleClassSource;
import com.github.mateuszjarzyna.plugdozer.testPlugins.DummyClass;
import com.github.mateuszjarzyna.plugdozer.testPlugins.EchoPlugin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class GuicePluginManagerTest {

    private PluginManager pluginManager;

    @BeforeEach
    void initManager() {
        pluginManager = new GuicePluginManager();
        SimpleClassSource source = new SimpleClassSource(Arrays.asList(DummyClass.class, EchoPlugin.class));
        pluginManager.loadPlugins(source);
    }

    @Test
    void shouldCreateInstance() {
        EchoPlugin instance = pluginManager.getInstance(EchoPlugin.class);

        assertThat(instance).isNotNull();
        String sentence = "Hello!";
        assertThat(instance.echo(sentence)).isEqualTo(sentence);
    }

}
