package com.github.mateuszjarzyna.examples.plugdozersimple.plugin;

public class ItalianHello implements Hello {
    @Override
    public String sayHello(String name) {
        return "Ciao " + name;
    }
}
