package com.github.mateuszjarzyna.examples.plugdozersimple.plugin;

import com.github.mateuszjarzyna.plugdozer.annotation.Plugin;

@Plugin(name = "ancient")
public class AncientLanguage implements Hello {
    @Override
    public String sayHello(String name) {
        return "Ughu ughu " + name;
    }
}
