package com.github.mateuszjarzyna.examples.plugdozersimple.plugin;

public class PolishHello implements Hello {
    @Override
    public String sayHello(String name) {
        return "Witaj " + name;
    }
}
