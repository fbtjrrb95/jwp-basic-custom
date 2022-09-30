package next.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import javassist.NotFoundException;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import next.model.Question;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

// TODO: change QnAController
public class QuestionController implements Controller {
    private static final Logger log = LoggerFactory.getLogger(QuestionController.class);
    private static final String CONTENT_TYPE = "application/json;charset=UTF-8";
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        QuestionDao questionDao = new QuestionDao();
        AnswerDao answerDao = new AnswerDao();
        if (isPost(request)) {
            String writer = request.getParameter("writer");
            String title = request.getParameter("title");
            String contents = request.getParameter("contents");
            Timestamp createdAt = Timestamp.from(Instant.now());
            Timestamp updatedAt = Timestamp.from(Instant.now());
            Question question = new Question(writer, title, contents, createdAt, updatedAt);
            log.debug("question: {}", question);

            Question savedQuestion = questionDao.save(question);
            ObjectMapper objectMapper = new ObjectMapper();
            response.setContentType(CONTENT_TYPE);
            PrintWriter printWriter = response.getWriter();
            printWriter.print(objectMapper.writeValueAsString(savedQuestion));
            return null;
        }
        if (isGet(request)) {
            String questionId = request.getParameter("questionId");
            if (!StringUtils.isEmpty(questionId)) {
                Question question = questionDao.findById(Long.parseLong(questionId));
                request.setAttribute("question", question);
                if (question != null) {
                    List<Answer> answers = answerDao.findByQuestionId(question.getId());
                    request.setAttribute("answers", answers);
                }
                return "/qna/show.jsp";
            }
            throw new NotFoundException("NOT FOUND");
        }
        throw new NotFoundException("NOT FOUND");
    }
}
