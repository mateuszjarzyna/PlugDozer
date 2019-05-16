package com.github.mateuszjarzyna.plugdozer.testPlugins.cycle;

import com.github.mateuszjarzyna.plugdozer.annotation.Plugin;

import javax.inject.Inject;

@Plugin
public class CyclePlugin4 {

    @Inject
    public CyclePlugin4(CyclePlugin2 plugin) {

    }

}
