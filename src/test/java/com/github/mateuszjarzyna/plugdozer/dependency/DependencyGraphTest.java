package com.github.mateuszjarzyna.plugdozer.dependency;

import com.github.mateuszjarzyna.plugdozer.exception.CannotResolveDependency;
import com.github.mateuszjarzyna.plugdozer.manager.DefaultPluginManager;
import com.github.mateuszjarzyna.plugdozer.manager.PluginManager;
import com.github.mateuszjarzyna.plugdozer.testPlugins.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.Collections.singleton;
import static java.util.stream.Collectors.toSet;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


class DependencyGraphTest {

    private final Logger log = LoggerFactory.getLogger(DependencyGraphTest.class);

    private PluginManager pluginManager;

    @BeforeEach
    void initPluginManager() {
        pluginManager = new DefaultPluginManager();
    }

    @Test
    void shouldThrowExceptionWhenNoPublicConstructor() {
        DependencyGraph graph = new DependencyGraph(pluginManager, singleton(PrivateConstructorPlugin.class));

        CannotResolveDependency exception = assertThrows(CannotResolveDependency.class, graph::resolveDependencies);
        log.info(exception.toString());
        assertThat(exception.getMessage())
                .contains("com.github.mateuszjarzyna.plugdozer.testPlugins.PrivateConstructorPlugin");
    }

    @Test
    void shouldThrowExceptionWhenNoConstructorWithInjectAnnotation() {
        DependencyGraph graph = new DependencyGraph(pluginManager, singleton(NoInjectConstructorPlugin.class));

        CannotResolveDependency exception = assertThrows(CannotResolveDependency.class, graph::resolveDependencies);
        log.info(exception.toString());
        assertThat(exception.getMessage())
                .contains("com.github.mateuszjarzyna.plugdozer.testPlugins.NoInjectConstructorPlugin");
    }

    @Test
    void shouldThrowExceptionWhenNoConstructorWhenTooManyConstructors() {
        DependencyGraph graph = new DependencyGraph(pluginManager, singleton(ConstructorConflictsPlugin.class));

        CannotResolveDependency exception = assertThrows(CannotResolveDependency.class, graph::resolveDependencies);
        log.info(exception.toString());
        assertThat(exception.getMessage())
                .contains("com.github.mateuszjarzyna.plugdozer.testPlugins.ConstructorConflictsPlugin");
    }

    @Test
    void shouldThrowExceptionWhenUnknownParameterWasFoundInConstructor() {
        DependencyGraph graph = new DependencyGraph(pluginManager, singleton(PluginWithPojoAsDependency.class));

        CannotResolveDependency exception = assertThrows(CannotResolveDependency.class, graph::resolveDependencies);
        log.info(exception.toString());
        assertThat(exception.getMessage())
                .contains("com.github.mateuszjarzyna.plugdozer.testPlugins.PluginWithPojoAsDependency")
                .contains("com.github.mateuszjarzyna.plugdozer.testPlugins.DummyClass");
    }

    @Test
    void shouldIgnoreNonPluginClasses() {
        Set<Class<?>> plugins = Stream.of(SimpleDependencyPlugin.class, DummyClass.class).collect(toSet());
        DependencyGraph graph = new DependencyGraph(pluginManager, plugins);

        graph.resolveDependencies();
        List<Class<?>> sortedDependencies = graph.getSortedDependencies();

        assertThat(sortedDependencies).containsExactly(SimpleDependencyPlugin.class);
    }

    @Test
    void shouldReturnPluginsInCorrectOrder() {
        Set<Class<?>> plugins = Stream.of(SimpleDependencyPlugin.class, EchoPlugin.class).collect(toSet());
        DependencyGraph graph = new DependencyGraph(pluginManager, plugins);

        graph.resolveDependencies();
        List<Class<?>> sortedDependencies = graph.getSortedDependencies();

        assertThat(sortedDependencies).containsExactly(SimpleDependencyPlugin.class, EchoPlugin.class);
    }

    @Test
    void shouldReturnPluginsInCorrectOrder2() {
        Set<Class<?>> plugins = Stream.of(SimpleDependencyPlugin.class, NestedDependencyPlugin.class, EchoPlugin.class)
                .collect(toSet());
        DependencyGraph graph = new DependencyGraph(pluginManager, plugins);

        graph.resolveDependencies();
        List<Class<?>> sortedDependencies = graph.getSortedDependencies();

        assertThat(sortedDependencies).containsExactly(SimpleDependencyPlugin.class, EchoPlugin.class,
                NestedDependencyPlugin.class);
    }

    @Test
    void shouldIgnoreCreatedPluginsInDependencyGraph() {
        pluginManager.addInstance(new SimpleDependencyPlugin());
        Set<Class<?>> plugins = Stream.of(SimpleDependencyPlugin.class, NestedDependencyPlugin.class, EchoPlugin.class)
                .collect(toSet());
        DependencyGraph graph = new DependencyGraph(pluginManager, plugins);

        graph.resolveDependencies();
        List<Class<?>> sortedDependencies = graph.getSortedDependencies();

        assertThat(sortedDependencies).containsExactly(EchoPlugin.class,
                NestedDependencyPlugin.class);
    }

    @Test
    void shouldCreateGraphWhenNonPluginDependencyIsInjected() {
        pluginManager.addInstance(new DummyClass());
        DependencyGraph graph = new DependencyGraph(pluginManager, singleton(PluginWithPojoAsDependency.class));

        graph.resolveDependencies();
        List<Class<?>> sortedDependencies = graph.getSortedDependencies();

        assertThat(sortedDependencies).containsExactly(PluginWithPojoAsDependency.class);
    }

    @Test
    void shouldThrowExceptionWhenUnknownPluginIsADependency() {
        DependencyGraph graph = new DependencyGraph(pluginManager, singleton(EchoPlugin.class));

        CannotResolveDependency exception = assertThrows(CannotResolveDependency.class, graph::resolveDependencies);
        log.info(exception.toString());
        assertThat(exception.getMessage())
                .contains("com.github.mateuszjarzyna.plugdozer.testPlugins.SimpleDependencyPlugin")
                .contains("com.github.mateuszjarzyna.plugdozer.testPlugins.EchoPlugin");
    }

}