package next.controller;

import javassist.NotFoundException;
import next.dao.QuestionDao;
import next.view.JsonView;
import next.view.View;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class QuestionDetailController implements Controller {
    private static final Logger log = LoggerFactory.getLogger(QuestionDetailController.class);
    private final String prefix = "/qna/questions/";

    @Override
    public View execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (isDelete(request)) {
            String questionIdString = request.getRequestURI().substring(prefix.length());
            Long questionId;
            try {
                questionId = Long.valueOf(questionIdString);
            } catch (NumberFormatException e) {
                throw new NotFoundException("NOT FOUND");
            }
            log.info("delete question id: {}", questionId);
            QuestionDao questionDao = new QuestionDao();
            questionDao.delete(questionId);
            return new JsonView();
        }
        throw new NotFoundException("NOT FOUND");
    }
}
