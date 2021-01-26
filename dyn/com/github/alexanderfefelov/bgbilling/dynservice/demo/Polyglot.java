package com.github.alexanderfefelov.bgbilling.dynservice.demo;

import org.graalvm.polyglot.*;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class Polyglot {

    public Value eval(Language language, File sourceFile, long timeout) throws IOException {
        Source source = Source.newBuilder(language.language(), sourceFile).build();
        return eval(source, timeout);
    }

    public Value eval(Language language, String text, long timeout) throws IOException {
        Source source = Source.newBuilder(language.language(), text, "<literal>").build();
        return eval(source, timeout);
    }

    private Value eval(Source source, long timeout) throws PolyglotException {
        Context context = Context
            .newBuilder()
            .allowHostAccess(HostAccess.NONE)
            .allowCreateThread(false)
            .allowIO(false)
            .allowNativeAccess(false)
            .build();

        Timer timer = new Timer(true);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                context.close(true);
            }
        }, timeout);

        return context.eval(source);
    }

    public enum Language {

        JS("js"), PYTHON("python"), RUBY("ruby"), R("R");

        Language(String language) {
            this.language = language;
        }

        public String language() {
            return language;
        }

        private final String language;

    }

}
