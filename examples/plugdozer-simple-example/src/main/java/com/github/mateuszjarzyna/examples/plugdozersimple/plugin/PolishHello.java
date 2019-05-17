package com.github.mateuszjarzyna.examples.plugdozersimple.plugin;

import com.github.mateuszjarzyna.plugdozer.annotation.Plugin;

@Plugin
public class PolishHello implements Hello {
    @Override
    public String sayHello(String name) {
        return "Witaj " + name;
    }
}
