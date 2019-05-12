package com.github.mateuszjarzyna.plugdozer.testPlugins.cycle;

import com.github.mateuszjarzyna.plugdozer.annotation.Plugin;

import javax.inject.Inject;

@Plugin
public class CyclePlugin1 {

    @Inject
    public CyclePlugin1(CyclePlugin2 plugin) {

    }

}
