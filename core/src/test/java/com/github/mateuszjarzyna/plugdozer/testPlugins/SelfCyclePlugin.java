package com.github.mateuszjarzyna.plugdozer.testPlugins;

import com.github.mateuszjarzyna.plugdozer.annotation.Plugin;

import javax.inject.Inject;

@Plugin
public class SelfCyclePlugin {

    @Inject
    public SelfCyclePlugin(SelfCyclePlugin plugin) {

    }

}
