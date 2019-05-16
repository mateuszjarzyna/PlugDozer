package com.github.mateuszjarzyna.plugdozer.exception;

import java.util.List;

public class TooManyPluginsWithGivenType extends PlugDozerException {

    public TooManyPluginsWithGivenType(Class<?> type, List<Class<?>> found) {
        super(String.format("Found %d plugins with type %s: %s", found.size(), type, found));
    }

}
