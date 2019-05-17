package com.github.mateuszjarzyna.plugdozer.source;

import com.github.mateuszjarzyna.plugdozer.exception.CannotLoadPlugins;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import static java.util.stream.Collectors.toList;

class JarSource implements PluginSource {

    private static final String JAR_FILE_EXTENSION = ".jar";
    private static final String CLASS_FILE_EXTENSION = ".class";

    private final Path directoryWithJars;
    private final JarClassLoaderFactory classLoaderFactory;

    JarSource(Path directoryWithJars) {
        this(directoryWithJars, new DefaultJarClassLoaderFactory());
    }

    JarSource(Path directoryWithJars, JarClassLoaderFactory classLoaderFactory) {
        validateDirectory(directoryWithJars);
        this.directoryWithJars = directoryWithJars;
        this.classLoaderFactory = classLoaderFactory;
    }

    @Override
    public Set<Class<?>> loadClasses() {
        Set<Class<?>> loadedClasses = new HashSet<>();
        List<Path> jars = getJars();
        prepareClassLoadersFactory(jars);

        for (Path jarPath : jars) {
            Set<Class<?>> classesFromJar = loadJar(jarPath);
            loadedClasses.addAll(classesFromJar);
        }

        return loadedClasses;
    }

    private Set<Class<?>> loadJar(Path jarPath) {
        Set<Class<?>> loadedClasses = new HashSet<>();
        ClassLoader classLoader = classLoaderFactory.createClassLoader(toUrl(jarPath));
        JarFile jarFile = getJarFile(jarPath);
        Enumeration<JarEntry> entries = jarFile.entries();
        while (entries.hasMoreElements()) {
            JarEntry jarEntry = entries.nextElement();
            Optional<Class<?>> clazz = loadClass(jarPath, classLoader, jarEntry);
            clazz.ifPresent(loadedClasses::add);
        }

        return loadedClasses;
    }

    private Optional<Class<?>> loadClass(Path jarPath, ClassLoader classLoader, JarEntry jarEntry) {
        if (isClass(jarEntry)) {
            String className = createClassName(jarEntry);
            try {
                Class<?> clazz = Class.forName(className, true, classLoader);
                return Optional.of(clazz);
            } catch (ClassNotFoundException e) {
                String msg = String.format("Cannot load class %s from file %s", className, jarPath);
                throw new CannotLoadPlugins(msg, e);
            }
        }

        return Optional.empty();
    }

    private String createClassName(JarEntry jarEntry) {
        String className = jarEntry.getName().substring(0, jarEntry.getName().length() - 6);
        className = className.replace('/', '.');
        return className;
    }

    private boolean isClass(JarEntry jarEntry) {
        return !jarEntry.isDirectory() && jarEntry.getName().endsWith(CLASS_FILE_EXTENSION);
    }

    private JarFile getJarFile(Path jarPath) {
        try {
            return new JarFile(jarPath.toFile());
        } catch (IOException e) {
            String msg = String.format("Cannot open plugin jar %s. %s", jarPath, e.getMessage());
            throw new CannotLoadPlugins(msg, e);
        }
    }

    private void prepareClassLoadersFactory(List<Path> jars) {
        URL[] asUrls = jars.stream()
                .map(this::toUrl)
                .toArray(URL[]::new);
        classLoaderFactory.prepare(asUrls);
    }

    private URL toUrl(Path p) {
        try {
            return p.toUri().toURL();
        } catch (MalformedURLException e) {
            String msg = String.format("Problem with file %s. %s", p, e.getMessage());
            throw new CannotLoadPlugins(msg, e);
        }
    }

    private List<Path> getJars() {
        try {
            return Files.list(directoryWithJars)
                    .filter(f -> f.toString().endsWith(JAR_FILE_EXTENSION))
                    .collect(toList());
        } catch (IOException e) {
            String msg = String.format("Cannot read directory %s. %s", directoryWithJars, e.getMessage());
            throw new CannotLoadPlugins(msg, e);
        }
    }

    private void validateDirectory(Path directoryWithJars) {
        if (!Files.exists(directoryWithJars) || !Files.isDirectory(directoryWithJars)) {
            String msg = String.format("Directory %s does not exit or it is not a directory", directoryWithJars);
            throw new CannotLoadPlugins(msg);
        }
    }

}
