package next.controller;

import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import next.model.User;
import next.view.ModelAndView;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.time.Instant;

public class CreateAnswerController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(CreateAnswerController.class);

    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        User user = (User) request.getSession().getAttribute("user");
        if (user == null || StringUtils.isEmpty(user.getUserId())) {
            throw new IllegalAccessException("unavailable user");
        }

        String writer = user.getName();
        String contents = request.getParameter("contents");
        long questionId = Long.parseLong(request.getParameter("questionId"));
        Timestamp createdAt = Timestamp.from(Instant.now());
        Timestamp updatedAt = Timestamp.from(Instant.now());
        Answer answer = new Answer(writer, contents, questionId, createdAt, updatedAt);
        log.debug("answer: {}", answer);

        AnswerDao answerDao = new AnswerDao();
        QuestionDao questionDao = new QuestionDao();
        Answer savedAnswer = answerDao.save(answer);
        questionDao.increaseAnswerCount(questionId);

        return jsonView().addObject("answer", savedAnswer);
    }
}
