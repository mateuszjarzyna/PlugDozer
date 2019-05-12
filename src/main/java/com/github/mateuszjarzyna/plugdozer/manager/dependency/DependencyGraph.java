package com.github.mateuszjarzyna.plugdozer.manager.dependency;

import com.github.mateuszjarzyna.plugdozer.annotation.AnnotationHelper;
import com.github.mateuszjarzyna.plugdozer.exception.CannotResolveDependency;
import com.github.mateuszjarzyna.plugdozer.exception.CycleInDependencyGraph;
import com.github.mateuszjarzyna.plugdozer.manager.PluginManager;

import javax.inject.Inject;
import java.lang.reflect.Constructor;
import java.util.*;

public class DependencyGraph {

    private final PluginManager pluginManager;
    private final Set<Class<?>> allClasses;
    private final Map<Class<?>, List<Class<?>>> unresolvedDependencies;

    public DependencyGraph(PluginManager pluginManager, Set<Class<?>> allClasses) {
        this.pluginManager = pluginManager;
        this.allClasses = allClasses;
        this.unresolvedDependencies = new HashMap<>();
    }

    public void resolveDependencies() {
        for (Class<?> clazz : allClasses) {
            if (isPluginWithoutInstance(clazz)) {
                List<Class<?>> unresolvedDependency = getUnresolvedDependency(clazz);
                unresolvedDependencies.put(clazz, unresolvedDependency);
            }
        }
    }

    public List<Class<?>> getSortedDependencies() {
        List<Class<?>> sortedDependencies = new ArrayList<>();
        Set<Class<?>> visited = new HashSet<>();
        Stack<Class<?>> stack = new Stack<>();
        for (Class<?> plugin : unresolvedDependencies.keySet()) {
            if (!visited.contains(plugin)) {
                topologicalSort(plugin, sortedDependencies, visited, stack);
            }
        }

        return sortedDependencies;
    }

    private void topologicalSort(Class<?> plugin, List<Class<?>> sortedDependencies, Set<Class<?>> visited,
                                 Stack<Class<?>> dependenciesStack) {
        visited.add(plugin);
        dependenciesStack.push(plugin);
        List<Class<?>> dependencies = unresolvedDependencies.get(plugin);
        for (Class<?> dep : dependencies) {
            if (!visited.contains(dep)) {
                topologicalSort(dep, sortedDependencies, visited, dependenciesStack);
            } else if (dependenciesStack.contains(dep)) {
                List<Class<?>> cycle = getCycle(dependenciesStack, dep);
                throw new CycleInDependencyGraph(plugin, cycle);
            }
        }

        dependenciesStack.pop();
        sortedDependencies.add(plugin);
    }

    private List<Class<?>> getCycle(Stack<Class<?>> dependenciesStack, Class<?> dep) {
        List<Class<?>> cycle = new ArrayList<>();
        while (dependenciesStack.peek() != dep) {
            cycle.add(dependenciesStack.pop());
        }
        cycle.add(dependenciesStack.pop());
        Collections.reverse(cycle);
        cycle.add(dep);
        return cycle;
    }

    private List<Class<?>> getUnresolvedDependency(Class<?> clazz) {
        Constructor<?> constructor = getConstructor(clazz);
        List<Class<?>> unresolvedParameters = new ArrayList<>();
        for (Class<?> parameter : constructor.getParameterTypes()) {
            if (shouldCreateInstance(parameter, clazz)) {
                unresolvedParameters.add(parameter);
            }
        }

        return unresolvedParameters;
    }

    private boolean isPluginWithoutInstance(Class<?> clazz) {
        return AnnotationHelper.isPlugin(clazz) && !pluginManager.hasPlugin(clazz);
    }

    private boolean shouldCreateInstance(Class<?> constructorParameter, Class<?> pluginClass) {
        if (pluginManager.hasPlugin(constructorParameter)) {
            return false;
        }

        if (!AnnotationHelper.isPlugin(constructorParameter)) {
            String msg = String.format("Cannot create plugin %s because unknown dependency was found." +
                            "Please add @Plugin annotation to class %s or add an instance to PluginManager",
                    pluginClass, constructorParameter);
            throw new CannotResolveDependency(msg);
        }

        if (!allClasses.contains(constructorParameter)) {
            String msg = String.format("Found unknown plugin of type %s in %s's constructor",
                    constructorParameter, pluginClass);
            throw new CannotResolveDependency(msg);
        }

        return true;
    }

    private Constructor<?> getConstructor(Class<?> clazz) {
        Constructor<?>[] allConstructors = getAllConstructors(clazz);
        List<Constructor<?>> injectableConstructors = getInjectableConstructors(allConstructors);
        return getPluginConstructor(clazz, injectableConstructors);
    }

    private Constructor<?> getPluginConstructor(Class<?> clazz, List<Constructor<?>> injectableConstructors) {
        if (injectableConstructors.isEmpty()) {
            String msg = String.format("No default constructor and no constructor marked with javax.inject.Inject " +
                    "found in class %s", clazz);
            throw new CannotResolveDependency(msg);
        }
        if (injectableConstructors.size() > 1) {
            String msg = String.format("Found %d constructors in class %s", injectableConstructors.size(),
                    clazz);
            throw new CannotResolveDependency(msg);
        }
        return injectableConstructors.get(0);
    }

    private List<Constructor<?>> getInjectableConstructors(Constructor<?>[] constructors) {
        List<Constructor<?>> possibleConstructors = new ArrayList<>();
        for (Constructor<?> constructor : constructors) {
            if (constructor.isAnnotationPresent(Inject.class)) {
                possibleConstructors.add(constructor);
            } else if (constructor.getParameterCount() == 0) {
                possibleConstructors.add(constructor);
            }
        }
        return possibleConstructors;
    }

    private Constructor<?>[] getAllConstructors(Class<?> clazz) {
        Constructor<?>[] constructors = clazz.getConstructors();
        if (constructors.length == 0) {
            String msg = String.format("Cannot create instance of %s. No public constructor found", clazz);
            throw new CannotResolveDependency(msg);
        }
        return constructors;
    }

}
