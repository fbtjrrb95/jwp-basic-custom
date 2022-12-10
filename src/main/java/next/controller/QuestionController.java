package next.controller;

import javassist.NotFoundException;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import next.model.Question;
import next.model.User;
import next.view.ModelAndView;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;

public class QuestionController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(QuestionController.class);

    private final QuestionDao questionDao = new QuestionDao();
    private final AnswerDao answerDao = new AnswerDao();
    private final String QUESTION_ID_PREFIX = "/qna/questions/";

    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (isDelete(request)) {
            CompletableFuture<Long> future = CompletableFuture.supplyAsync(() -> QuestionIdParser.apply(QUESTION_ID_PREFIX, request.getRequestURI()));
            Long questionId = future.get();
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
            long questionId = QuestionIdParser.apply(QUESTION_ID_PREFIX, request.getRequestURI());
            ModelAndView jspView = jspView("/qna/show.jsp");
            Question question = questionDao.findById(questionId);
            jspView.addObject("question", question);
            if (question != null) {
                List<Answer> answers = answerDao.findByQuestionId(question.getId());
                jspView.addObject("answers", answers);
            }
            return jspView;
        }

        if (isPost(request)) {
            User user = (User) request.getSession().getAttribute("user");
            if (user == null || StringUtils.isEmpty(user.getUserId())) {
                throw new IllegalAccessException("unavailable user");
            }

            String writer = user.getName();
            String title = request.getParameter("title");
            String contents = request.getParameter("contents");
            Timestamp createdAt = Timestamp.from(Instant.now());
            Timestamp updatedAt = Timestamp.from(Instant.now());
            Question question = new Question(writer, title, contents, createdAt, updatedAt);
            log.debug("question: {}", question);

            Question savedQuestion = questionDao.save(question);
            return jsonView().addObject("question", savedQuestion);
        }

        throw new NotFoundException("NOT FOUND");
    }

    private final BiFunction<String, String, Long> QuestionIdParser = (prefix, url) -> {
        String questionIdString = url.substring(prefix.length());
        return Long.parseLong(questionIdString);
    };
}
