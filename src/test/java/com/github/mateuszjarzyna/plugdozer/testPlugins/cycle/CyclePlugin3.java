package com.github.mateuszjarzyna.plugdozer.testPlugins.cycle;

import com.github.mateuszjarzyna.plugdozer.annotation.Plugin;

import javax.inject.Inject;

@Plugin
public class CyclePlugin3 {

    @Inject
    public CyclePlugin3(CyclePlugin4 plugin) {

    }

}
