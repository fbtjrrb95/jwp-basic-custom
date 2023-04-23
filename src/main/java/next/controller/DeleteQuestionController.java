package next.controller;

import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.view.ModelAndView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;

public class DeleteQuestionController extends QuestionDetailController {

    private static final Logger log = LoggerFactory.getLogger(DeleteQuestionController.class);
    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        long questionId = QuestionIdParser.apply("/qna/questions/", request.getRequestURI());
        log.debug(String.format("delete question id: %d", questionId));
        CompletableFuture.runAsync(() -> {
            try {
                QuestionDao questionDao = new QuestionDao();
                questionDao.delete(questionId);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }).thenRun(() -> {
            try {
                AnswerDao answerDao = new AnswerDao();
                answerDao.deleteByQuestionId(questionId);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }).join();
        return jsonView();
    }
}
