package com.github.mateuszjarzyna.examples.plugdozersimple.plugin;

public class EnglishHello implements Hello {
    @Override
    public String sayHello(String name) {
        return "Hello " + name;
    }
}
