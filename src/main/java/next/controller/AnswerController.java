package next.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import javassist.NotFoundException;
import next.dao.AnswerDao;
import next.model.Answer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Locale;

public class AnswerController implements Controller {
    private static final Logger log =
            LoggerFactory.getLogger(AnswerController.class);
    private static final String CONTENT_TYPE = "application/json;charset=UTF-8";
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (Method.valueOf(request.getMethod().toUpperCase(Locale.ROOT)).equals(Method.POST)) {
            String writer = request.getParameter("writer");
            String contents = request.getParameter("contents");
            Long questionId = Long.parseLong(request.getParameter("questionId"));
            Timestamp createdAt = Timestamp.from(Instant.now());
            Timestamp updatedAt = Timestamp.from(Instant.now());
            Answer answer = new Answer(writer, contents, questionId, createdAt, updatedAt);
            log.debug("answer: {}", answer);

            AnswerDao answerDao = new AnswerDao();
            Answer savedAnswer = answerDao.save(answer);
            ObjectMapper objectMapper = new ObjectMapper();
            response.setContentType(CONTENT_TYPE);
            PrintWriter printWriter = response.getWriter();
            printWriter.print(objectMapper.writeValueAsString(savedAnswer));
            return null;
        }

        if (Method.valueOf(request.getMethod().toUpperCase(Locale.ROOT)).equals(Method.GET)) {
            return null;
        }
        throw new NotFoundException("NOT FOUND");
    }
}
