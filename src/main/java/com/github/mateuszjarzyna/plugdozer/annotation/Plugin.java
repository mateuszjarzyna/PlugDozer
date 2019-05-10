package com.github.mateuszjarzyna.plugdozer.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Use this annotation to mark class as a plugin
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Plugin {

    /**
     * Name of the plugin. Default is full class name (eg. com.github.mateuszjarzyna.plugdozer.manager.PluginManager)
     * @return name of the plugin
     */
    String name() default "";

}
