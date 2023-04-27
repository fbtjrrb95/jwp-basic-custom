package next.controller;

import java.util.function.BiFunction;

public abstract class QuestionDetailController extends AbstractController {
    protected final BiFunction<String, String, Long> QuestionIdParser = (prefix, url) -> {
        String questionIdString = url.substring(prefix.length());
        return Long.parseLong(questionIdString);
    };
}
