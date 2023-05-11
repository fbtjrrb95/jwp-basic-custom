package next.controller;

import core.exception.NotFoundException;

public abstract class AbstractAnswerController extends AbstractController {

    protected long obtainAnswerId(String answerIdString) {
        long answerId;
        try {
            answerId = Long.parseLong(answerIdString);
        } catch (NumberFormatException e) {
            throw new NotFoundException("NOT FOUND");
        }
        return answerId;
    }

}
