package com.github.mateuszjarzyna.plugdozer.testPlugins.cycle;

import com.github.mateuszjarzyna.plugdozer.annotation.Plugin;

import javax.inject.Inject;

@Plugin
public class CyclePluginEntry {

    @Inject
    public CyclePluginEntry(CyclePlugin1 plugin) {

    }

}
