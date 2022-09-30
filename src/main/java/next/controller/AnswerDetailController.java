package next.controller;

import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AnswerDetailController implements Controller {
    private static final Logger log = LoggerFactory.getLogger(AnswerDetailController.class);
    private final String prefix = "/qna/answers/";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (isGet(request)) {
            return null;
        }
        if (isPost(request)) {
            return null;
        }
        if (isDelete(request)) {
            String answerIdString = request.getRequestURI().substring(prefix.length());
            Integer integer;
            try {
                integer = Integer.valueOf(answerIdString);
            } catch (NumberFormatException e) {
                // TODO: return 404
                throw new NotFoundException("NOT FOUND");
            }
            log.info("delete answer id: {}", integer);
            return null;
        }
        throw new NotFoundException("NOT FOUND");
    }
}
