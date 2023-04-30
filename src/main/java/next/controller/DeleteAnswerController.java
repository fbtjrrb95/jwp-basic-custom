package next.controller;

import core.exception.NotFoundException;
import next.dao.AnswerDao;
import next.view.ModelAndView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteAnswerController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(DeleteAnswerController.class);

    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String prefix = "/qna/answers/";
        String answerIdString = request.getRequestURI().substring(prefix.length());
            long answerId;
            try {
                answerId = Long.parseLong(answerIdString);
            } catch (NumberFormatException e) {
                throw new NotFoundException("NOT FOUND");
            }
            log.debug("delete answer id: {}", answerId);
            AnswerDao answerDao = new AnswerDao();
            answerDao.delete(answerId);
            // TODO: decrease answerCount of target question
            return jsonView();
    }
}
