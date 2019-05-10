package com.github.mateuszjarzyna.plugdozer.manager;

import com.github.mateuszjarzyna.plugdozer.annotation.AnnotationHelper;
import com.github.mateuszjarzyna.plugdozer.exception.PluginAlreadyLoaded;
import com.github.mateuszjarzyna.plugdozer.exception.TooManyPluginsWithGivenType;
import com.github.mateuszjarzyna.plugdozer.source.PluginSource;

import java.util.*;

import static java.util.stream.Collectors.toList;

public class DefaultPluginManager implements PluginManager {

    private final Map<String, PluginWrapper<?>> byName;
    private final Map<Class<?>, List<PluginWrapper<?>>> byType;

    public DefaultPluginManager() {
        this.byName = new HashMap<>();
        this.byType = new HashMap<>();
    }

    @Override
    public void loadPlugins(PluginSource pluginSource) {

    }

    @Override
    public void addInstance(Object pluginInstance) {
        String pluginName = getName(pluginInstance);
        addInstance(pluginName, pluginInstance);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <P> void addInstance(String pluginName, P pluginInstance) {
        validateName(pluginName, pluginInstance);
        Class<P> pluginType = (Class<P>) pluginInstance.getClass();
        Set<Class<?>> pluginTypes = getAllTypes(pluginType);
        PluginWrapper<P> pluginWrapper = new PluginWrapper<>(pluginName, pluginInstance, pluginType);
        pluginTypes.forEach(pt -> addPlugin(pluginWrapper, pt));
        byName.put(pluginName, pluginWrapper);
    }

    private void validateName(String pluginName, Object pluginInstance) {
        if (byName.containsKey(pluginName)) {
            PluginWrapper existingPlugin = byName.get(pluginName);
            throw new PluginAlreadyLoaded(pluginName, pluginInstance.getClass(), existingPlugin.getType());
        }
    }

    @Override
    public <P> Optional<P> getInstance(String pluginName, Class<P> type) {
        PluginWrapper pluginWrapper = byName.get(pluginName);
        return maybeGetInstance(type, pluginWrapper);
    }

    @Override
    public <P> Optional<P> getInstance(Class<P> clazz) {
        List<PluginWrapper<?>> plugins = byType.get(clazz);
        if (plugins == null || plugins.isEmpty()) {
            return Optional.empty();
        }
        if (plugins.size() > 1) {
            List<Class<?>> types = plugins.stream()
                    .map(PluginWrapper::getType)
                    .collect(toList());
            throw new TooManyPluginsWithGivenType(clazz, types);
        }
        return maybeGetInstance(clazz, plugins.get(0));
    }

    @Override
    @SuppressWarnings("unchecked")
    public <P> List<P> getInstances(Class<?> clazz) {
        return byType.get(clazz)
                .stream()
                .map(pw -> maybeGetInstance(clazz, pw))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(i -> (P) i)
                .collect(toList());
    }

    @Override
    public boolean hasPlugin(Class<?> clazz) {
        return byType.containsKey(clazz);
    }

    @Override
    public boolean hasPlugin(String name) {
        return byName.containsKey(name);
    }

    private void addPlugin(PluginWrapper pluginWrapper, Class<?> type) {
        if (!byType.containsKey(type)) {
            byType.put(type, new ArrayList<>());
        }
        byType.get(type).add(pluginWrapper);
    }

    private Set<Class<?>> getAllTypes(Class<?> type) {
        Set<Class<?>> result = new HashSet<>();

        result.add(type);
        addSuperclassesTypes(type, result);
        addInterfaces(type, result);

        return result;
    }

    private void addSuperclassesTypes(Class<?> type, Set<Class<?>> result) {
        Class<?> superclass = type.getSuperclass();
        if (superclass != null) {
            result.add(superclass);
            Set<Class<?>> superclassTypes = getAllTypes(superclass);
            result.addAll(superclassTypes);
        }
    }

    private void addInterfaces(Class<?> type, Set<Class<?>> result) {
        Class<?>[] interfaces = type.getInterfaces();
        result.addAll(Arrays.asList(interfaces));
    }

    private String getName(Object instance) {
        if (AnnotationHelper.isPlugin(instance)) {
            return AnnotationHelper.getPluginName(instance);
        } else {
            return instance.getClass().getName();
        }
    }

    @SuppressWarnings("unchecked")
    private <P> Optional<P> maybeGetInstance(Class<P> type, PluginWrapper pluginWrapper) {
        if (pluginWrapper == null) {
            return Optional.empty();
        }

        Set<Class<?>> allTypes = getAllTypes(pluginWrapper.getType());
        if (allTypes.contains(type)) {
            P plugin = (P) pluginWrapper.getInstance();
            return Optional.of(plugin);
        }

        return Optional.empty();
    }

}
