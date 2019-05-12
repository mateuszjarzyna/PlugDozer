package com.github.mateuszjarzyna.plugdozer.testPlugins.cycle;

import com.github.mateuszjarzyna.plugdozer.annotation.Plugin;

import javax.inject.Inject;

@Plugin
public class CyclePlugin2 {

    @Inject
    public CyclePlugin2(CyclePlugin3 plugin) {

    }

}
