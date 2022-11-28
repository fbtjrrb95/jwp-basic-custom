package next.controller;

import javassist.NotFoundException;
import next.dao.AnswerDao;
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

public class AnswerController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(AnswerController.class);
    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (isPost(request)) {

            User user = (User) request.getSession().getAttribute("user");
            if (user == null || StringUtils.isEmpty(user.getUserId())) {
                throw new IllegalAccessException("unavailable user");
            }

            String writer = user.getName();
            String contents = request.getParameter("contents");
            Long questionId = Long.parseLong(request.getParameter("questionId"));
            Timestamp createdAt = Timestamp.from(Instant.now());
            Timestamp updatedAt = Timestamp.from(Instant.now());
            Answer answer = new Answer(writer, contents, questionId, createdAt, updatedAt);
            log.debug("answer: {}", answer);

            AnswerDao answerDao = new AnswerDao();
            Answer savedAnswer = answerDao.save(answer);
            return jsonView().addObject("answer", savedAnswer);
        }
        throw new NotFoundException("NOT FOUND");
    }
}
