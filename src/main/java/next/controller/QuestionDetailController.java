package next.controller;

import javassist.NotFoundException;
import next.dao.QuestionDao;
import next.view.ModelAndView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class QuestionDetailController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(QuestionDetailController.class);
    private final String prefix = "/qna/questions/";

    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (isDelete(request)) {
            String questionIdString = request.getRequestURI().substring(prefix.length());
            long questionId;
            try {
                questionId = Long.parseLong(questionIdString);
            } catch (NumberFormatException e) {
                throw new NotFoundException("NOT FOUND");
            }
            log.debug("delete question id: {}", questionId);
            QuestionDao questionDao = new QuestionDao();
            questionDao.delete(questionId);
            return jsonView();
        }
        throw new NotFoundException("NOT FOUND");
    }
}
