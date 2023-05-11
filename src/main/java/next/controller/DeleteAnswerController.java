package next.controller;

import next.service.AnswerService;
import next.view.ModelAndView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteAnswerController extends AbstractAnswerController {
    private static final Logger log = LoggerFactory.getLogger(DeleteAnswerController.class);

    private final AnswerService answerService = new AnswerService();

    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String prefix = "/qna/answers/";
        String answerIdString = request.getRequestURI().substring(prefix.length());
        long answerId = obtainAnswerId(answerIdString);
        log.debug("delete answer id: {}", answerId);
        answerService.delete(answerId);

        return jsonView();
    }

}
