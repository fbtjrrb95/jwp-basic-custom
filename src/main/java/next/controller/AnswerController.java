package next.controller;

import javassist.NotFoundException;
import next.dao.AnswerDao;
import next.model.Answer;
import next.view.JsonView;
import next.view.View;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.time.Instant;

public class AnswerController implements Controller {
    private static final Logger log = LoggerFactory.getLogger(AnswerController.class);
    @Override
    public View execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (isPost(request)) {
            String writer = request.getParameter("writer");
            String contents = request.getParameter("contents");
            Long questionId = Long.parseLong(request.getParameter("questionId"));
            Timestamp createdAt = Timestamp.from(Instant.now());
            Timestamp updatedAt = Timestamp.from(Instant.now());
            Answer answer = new Answer(writer, contents, questionId, createdAt, updatedAt);
            log.debug("answer: {}", answer);

            AnswerDao answerDao = new AnswerDao();
            Answer savedAnswer = answerDao.save(answer);
            request.setAttribute("answer", savedAnswer);
            return new JsonView();
        }
        throw new NotFoundException("NOT FOUND");
    }
}
