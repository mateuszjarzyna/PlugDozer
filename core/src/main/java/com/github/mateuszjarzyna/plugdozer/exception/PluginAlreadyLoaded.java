package com.github.mateuszjarzyna.plugdozer.exception;

public class PluginAlreadyLoaded extends PlugDozerException {

    public PluginAlreadyLoaded(String pluginName, Class failed, Class alreadyLoaded) {
        super(String.format("Cannot register plugin %s with name %s because there is plugin %s with same name",
                pluginName, failed, alreadyLoaded));
    }

}
