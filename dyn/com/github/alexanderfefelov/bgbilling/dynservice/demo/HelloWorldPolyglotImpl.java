package com.github.alexanderfefelov.bgbilling.dynservice.demo;

import ru.bitel.bgbilling.kernel.container.service.server.AbstractService;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HelloWorldPolyglotImpl extends AbstractService implements HelloWorldPolyglot {

    @Override
    public List<String> hello() {
        return SOURCES.entrySet().stream()
            .map(entry -> {
                try {
                    return polyglot.eval(entry.getKey(), new File(entry.getValue()), TIMEOUT)
                        .execute(entry.getKey().language())
                        .asString();
                } catch (IOException e) {
                    return e.getMessage();
                }
            })
            .collect(Collectors.toList());
    }

    private final Polyglot polyglot = new Polyglot();

    private static final Map<Polyglot.Language, String> SOURCES = new HashMap<Polyglot.Language, String>() {{
        put(Polyglot.Language.JS, "dyn/com/github/alexanderfefelov/bgbilling/dynservice/demo/Hello.js");
        put(Polyglot.Language.PYTHON, "dyn/com/github/alexanderfefelov/bgbilling/dynservice/demo/Hello.py");
        put(Polyglot.Language.RUBY, "dyn/com/github/alexanderfefelov/bgbilling/dynservice/demo/Hello.rb");
    }};

    private static final int TIMEOUT = 10 * 1000;

}
