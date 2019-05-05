package com.github.mateuszjarzyna.plugdozer.testPlugins;

import com.github.mateuszjarzyna.plugdozer.annotation.Plugin;

@Plugin
public class EchoPlugin {

    public String echo(String sentence) {
        return sentence;
    }

}
