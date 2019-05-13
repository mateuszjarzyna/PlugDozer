package com.github.mateuszjarzyna.plugdozer.manager;

import com.github.mateuszjarzyna.plugdozer.exception.PluginAlreadyLoaded;
import com.github.mateuszjarzyna.plugdozer.exception.TooManyPluginsWithGivenType;
import com.github.mateuszjarzyna.plugdozer.source.PluginSource;
import com.github.mateuszjarzyna.plugdozer.source.PluginsSource;
import com.github.mateuszjarzyna.plugdozer.testPlugins.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DefaultPluginManagerTest {

    private final Logger log = LoggerFactory.getLogger(DefaultPluginManagerTest.class);

    private PluginManager pluginManager;

    @BeforeEach
    void initManager() {
        pluginManager = new DefaultPluginManager();
    }

    @Test
    void shouldAddAndReturnPluginByName() {
        NamedPlugin expectedNamedPlugin = new NamedPlugin();
        pluginManager.addInstance(expectedNamedPlugin);
        pluginManager.addInstance(new SimpleDependencyPlugin());

        Optional<NamedPlugin> maybeInstance = pluginManager.getInstance("testNamedPlugin", NamedPlugin.class);

        assertThat(maybeInstance).isPresent();
        NamedPlugin pluginInstance = maybeInstance.get();
        assertThat(pluginInstance).isEqualTo(expectedNamedPlugin);
    }

    @Test
    void shouldReturnPluginByDefaultName() {
        SimpleDependencyPlugin expectedPlugin = new SimpleDependencyPlugin();
        String defaultPluginName = "com.github.mateuszjarzyna.plugdozer.testPlugins.SimpleDependencyPlugin";
        pluginManager.addInstance(expectedPlugin);
        pluginManager.addInstance(new NamedPlugin());

        Optional<SimpleDependencyPlugin> maybeInstance = pluginManager.getInstance(defaultPluginName,
                SimpleDependencyPlugin.class);

        assertThat(maybeInstance).isPresent();
        SimpleDependencyPlugin pluginInstance = maybeInstance.get();
        assertThat(pluginInstance).isEqualTo(expectedPlugin);
    }

    @Test
    void shouldRegisterPluginWithCustomName() {
        SimpleDependencyPlugin expectedPlugin = new SimpleDependencyPlugin();
        String customName = "my-plugin";
        pluginManager.addInstance(customName, expectedPlugin);
        pluginManager.addInstance(new NamedPlugin());

        Optional<SimpleDependencyPlugin> maybeInstance = pluginManager.getInstance(customName,
                SimpleDependencyPlugin.class);

        assertThat(maybeInstance).isPresent();
        SimpleDependencyPlugin pluginInstance = maybeInstance.get();
        assertThat(pluginInstance).isEqualTo(expectedPlugin);
    }

    @Test
    void shouldThrowExceptionWhileRegisteringPluginWithDuplicatedName() {
        NamedPlugin expectedNamedPlugin = new NamedPlugin();
        pluginManager.addInstance(expectedNamedPlugin);
        pluginManager.addInstance(new SimpleDependencyPlugin());

        PluginAlreadyLoaded exception = assertThrows(PluginAlreadyLoaded.class,
                () -> pluginManager.addInstance("testNamedPlugin", new DummyClass()));

        assertThat(exception.getMessage())
                .contains("com.github.mateuszjarzyna.plugdozer.testPlugins.NamedPlugin")
                .contains("testNamedPlugin");
    }

    @Test
    void shouldRegisterNonPluginClass() {
        DummyClass expectedClass = new DummyClass();
        pluginManager.addInstance(expectedClass);

        Optional<DummyClass> maybeInstance = pluginManager.getInstance(DummyClass.class);

        assertThat(maybeInstance).isPresent();
        DummyClass pluginInstance = maybeInstance.get();
        assertThat(pluginInstance).isEqualTo(expectedClass);
    }

    @Test
    void shouldReturnPluginByType() {
        EnglishHello expectedPlugin = new EnglishHello();
        pluginManager.addInstance(expectedPlugin);
        pluginManager.addInstance(new SimpleDependencyPlugin());

        Optional<EnglishHello> maybePlugin = pluginManager.getInstance(EnglishHello.class);

        assertThat(maybePlugin).isPresent();
        EnglishHello pluginInstance = maybePlugin.get();
        assertThat(pluginInstance).isEqualTo(expectedPlugin);
    }

    @Test
    void shouldReturnEmptyOptionalWhenPluginNotFound() {
        EnglishHello expectedPlugin = new EnglishHello();
        pluginManager.addInstance(expectedPlugin);
        pluginManager.addInstance(new SimpleDependencyPlugin());

        Optional<JapaneseHello> maybePlugin = pluginManager.getInstance(JapaneseHello.class);

        assertThat(maybePlugin).isNotPresent();
    }

    @Test
    void shouldReturnPluginByInterface() {
        EnglishHello expectedPlugin = new EnglishHello();
        pluginManager.addInstance(expectedPlugin);
        pluginManager.addInstance(new SimpleDependencyPlugin());

        Optional<Hello> maybePlugin = pluginManager.getInstance(Hello.class);

        assertThat(maybePlugin).isPresent();
        Hello pluginInstance = maybePlugin.get();
        assertThat(pluginInstance).isEqualTo(expectedPlugin);
    }

    @Test
    void shouldReturnPluginByAbstractClass() {
        EnglishHello expectedPlugin = new EnglishHello();
        pluginManager.addInstance(expectedPlugin);
        pluginManager.addInstance(new SimpleDependencyPlugin());

        Optional<AbstractHello> maybePlugin = pluginManager.getInstance(AbstractHello.class);

        assertThat(maybePlugin).isPresent();
        AbstractHello pluginInstance = maybePlugin.get();
        assertThat(pluginInstance).isEqualTo(expectedPlugin);
    }

    @Test
    void shouldReturnPluginByAbstractClassWithRegisteredManyInterfaces() {
        EnglishHello expectedPlugin = new EnglishHello();
        pluginManager.addInstance(expectedPlugin);
        pluginManager.addInstance(new JapaneseHello());

        Optional<AbstractHello> maybePlugin = pluginManager.getInstance(AbstractHello.class);

        assertThat(maybePlugin).isPresent();
        AbstractHello pluginInstance = maybePlugin.get();
        assertThat(pluginInstance).isEqualTo(expectedPlugin);
    }

    @Test
    void shouldThrowExceptionWhenFoundManyInstanceOfGivenType() {
        pluginManager.addInstance(new EnglishHello());
        pluginManager.addInstance(new JapaneseHello());
        pluginManager.addInstance(new SimpleDependencyPlugin());

        TooManyPluginsWithGivenType exception = assertThrows(TooManyPluginsWithGivenType.class,
                () -> pluginManager.getInstance(Hello.class));

        log.info(exception.toString());
        assertThat(exception.getMessage())
                .contains("com.github.mateuszjarzyna.plugdozer.testPlugins.Hello")
                .contains("com.github.mateuszjarzyna.plugdozer.testPlugins.EnglishHello")
                .contains("com.github.mateuszjarzyna.plugdozer.testPlugins.JapaneseHello")
                .doesNotContain("com.github.mateuszjarzyna.plugdozer.testPlugins.SimpleDependencyPlugin");
    }

    @Test
    void shouldReturnPluginsWithGivenType() {
        EnglishHello expectedEnglish = new EnglishHello();
        JapaneseHello expectedJapanese = new JapaneseHello();
        pluginManager.addInstance(expectedEnglish);
        pluginManager.addInstance(expectedJapanese);
        pluginManager.addInstance(new SimpleDependencyPlugin());

        List<Object> plugins = pluginManager.getInstances(Hello.class);

        assertThat(plugins).containsExactlyInAnyOrder(expectedEnglish, expectedJapanese);
    }

    @Test
    void shouldReturnTrueWhenPluginIsRegistered_byClass() {
        pluginManager.addInstance(new EnglishHello());
        pluginManager.addInstance(new NamedPlugin());

        boolean hasPlugin = pluginManager.hasPlugin(NamedPlugin.class);

        assertThat(hasPlugin).isTrue();
    }

    @Test
    void shouldReturnTrueWhenPluginIsNotRegistered_byClass() {
        pluginManager.addInstance(new EnglishHello());

        boolean hasPlugin = pluginManager.hasPlugin(NamedPlugin.class);

        assertThat(hasPlugin).isFalse();
    }

    @Test
    void shouldReturnTrueWhenPluginIsRegistered_byName() {
        pluginManager.addInstance(new EnglishHello());
        pluginManager.addInstance(new NamedPlugin());

        boolean hasPlugin = pluginManager.hasPlugin("testNamedPlugin");

        assertThat(hasPlugin).isTrue();
    }

    @Test
    void shouldReturnTrueWhenPluginIsNotRegistered_byName() {
        pluginManager.addInstance(new EnglishHello());

        boolean hasPlugin = pluginManager.hasPlugin("testNamedPlugin");

        assertThat(hasPlugin).isFalse();
    }

    @Test
    void shouldCreatePlugin() {
        PluginSource source = PluginsSource.simple(EchoPlugin.class, SimpleDependencyPlugin.class);
        pluginManager.loadPlugins(source);

        Optional<EchoPlugin> instance = pluginManager.getInstance(EchoPlugin.class);

        assertThat(instance).isPresent();
    }

    @Test
    void shouldUseExistingInstanceWhenCreatingPlugin() {
        SimpleDependencyPlugin dependencyPlugin = new SimpleDependencyPlugin();
        pluginManager.addInstance(dependencyPlugin);
        PluginSource source = PluginsSource.simple(EchoPlugin.class, SimpleDependencyPlugin.class);
        pluginManager.loadPlugins(source);

        Optional<EchoPlugin> maybeInstance = pluginManager.getInstance(EchoPlugin.class);

        assertThat(maybeInstance).isPresent();
        EchoPlugin instance = maybeInstance.get();
        assertThat(instance.getDependencyPlugin()).isEqualTo(dependencyPlugin);
    }

    @Test
    void shouldIgnoreNotProvidedClassesWhenExistInstance() {
        SimpleDependencyPlugin dependencyPlugin = new SimpleDependencyPlugin();
        pluginManager.addInstance(dependencyPlugin);
        PluginSource source = PluginsSource.simple(EchoPlugin.class);
        pluginManager.loadPlugins(source);

        Optional<EchoPlugin> maybeInstance = pluginManager.getInstance(EchoPlugin.class);

        assertThat(maybeInstance).isPresent();
        EchoPlugin instance = maybeInstance.get();
        assertThat(instance.getDependencyPlugin()).isEqualTo(dependencyPlugin);
    }

    @Test
    void shouldIgnoreNonPluginClassesInSource() {
        PluginSource source = PluginsSource.simple(EchoPlugin.class, SimpleDependencyPlugin.class, DummyClass.class);
        pluginManager.loadPlugins(source);

        Optional<EchoPlugin> echo = pluginManager.getInstance(EchoPlugin.class);
        Optional<DummyClass> dummyClass = pluginManager.getInstance(DummyClass.class);

        assertThat(echo).isPresent();
        assertThat(dummyClass).isNotPresent();
    }

    @Test
    void shouldInjectPojoClass() {
        DummyClass pojo = new DummyClass();
        pluginManager.addInstance(pojo);
        PluginSource source = PluginsSource.simple(PluginWithPojoAsDependency.class);
        pluginManager.loadPlugins(source);

        Optional<PluginWithPojoAsDependency> maybeInstance = pluginManager.getInstance(PluginWithPojoAsDependency.class);

        assertThat(maybeInstance).isPresent();
        PluginWithPojoAsDependency instance = maybeInstance.get();
        assertThat(instance.getPojo()).isEqualTo(pojo);
    }

    @Test
    void shouldCreateAllInstancesOfInterface() {
        PluginSource source = PluginsSource.simple(EnglishHello.class, JapaneseHello.class);
        pluginManager.loadPlugins(source);

        List<Hello> instance = pluginManager.getInstances(Hello.class);

        assertThat(instance).hasSize(2);
    }

}