package next.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import core.util.ObjectMapperFactory;
import javassist.NotFoundException;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import next.model.Question;
import next.view.ModelAndView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;

public class QuestionDetailController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(QuestionDetailController.class);

    private final QuestionDao questionDao = new QuestionDao();
    private final AnswerDao answerDao = new AnswerDao();

    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String QUESTION_ID_PREFIX = "/qna/questions/";
        long questionId = QuestionIdParser.apply(QUESTION_ID_PREFIX, request.getRequestURI());

        if (isDelete(request)) {
            log.debug(String.format("delete question id: %d", questionId));
            CompletableFuture.runAsync(() -> {
                try {
                    questionDao.delete(questionId);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }).thenRun(() -> {
                try {
                    answerDao.deleteByQuestionId(questionId);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }).join();
            return jsonView();
        }

        if (isGet(request)) {
            ModelAndView jspView = jspView("/qna/show.jsp");
            Question question = questionDao.findById(questionId);
            jspView.addObject("question", question);
            if (question != null) {
                List<Answer> answers = answerDao.findByQuestionId(question.getId());
                jspView.addObject("answers", answers);
            }
            return jspView;
        }

        if (isPut(request)) {
            ObjectMapper objectMapper = ObjectMapperFactory.getInstance();
            Map<String, String> map = objectMapper.readValue(
                    request.getInputStream(),
                    new TypeReference<>() {});
            Question question = questionDao.findById(questionId);
            question.setContents(map.get("contents"));
            Question updatedQuestion = questionDao.update(question);
            return jsonView().addObject("contents", updatedQuestion.getContents());
        }

        throw new NotFoundException("NOT FOUND");
    }

    private final BiFunction<String, String, Long> QuestionIdParser = (prefix, url) -> {
        String questionIdString = url.substring(prefix.length());
        return Long.parseLong(questionIdString);
    };
}
