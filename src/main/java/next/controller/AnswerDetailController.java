package next.controller;

import javassist.NotFoundException;
import next.dao.AnswerDao;
import next.view.ModelAndView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AnswerDetailController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(AnswerDetailController.class);
    private final String prefix = "/qna/answers/";

    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (isDelete(request)) {
            String answerIdString = request.getRequestURI().substring(prefix.length());
            Long answerId;
            try {
                answerId = Long.valueOf(answerIdString);
            } catch (NumberFormatException e) {
                throw new NotFoundException("NOT FOUND");
            }
            log.debug("delete answer id: {}", answerId);
            AnswerDao answerDao = new AnswerDao();
            answerDao.delete(answerId);
            // TODO: decrease answerCount of target question
            return jsonView();
        }
        throw new NotFoundException("NOT FOUND");
    }
}
