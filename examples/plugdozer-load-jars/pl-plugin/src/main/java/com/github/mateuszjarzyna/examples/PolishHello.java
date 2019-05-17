package com.github.mateuszjarzyna.examples;

import com.github.mateuszjarzyna.examples.contract.SayHello;
import com.github.mateuszjarzyna.plugdozer.annotation.Plugin;

@Plugin
public class PolishHello implements SayHello {
    @Override
    public String sayHello(String s) {
        return "Czesc " + s;
    }
}
