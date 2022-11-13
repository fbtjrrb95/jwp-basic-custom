package next.controller;

import javassist.NotFoundException;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import next.model.Question;
import next.view.ModelAndView;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

// TODO: change QnAController
public class QuestionController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(QuestionController.class);
    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
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
            return jsonView().addObject("question", savedQuestion);
        }
        if (isGet(request)) {
            String questionId = request.getParameter("questionId");
            if (StringUtils.isEmpty(questionId)) {
                throw new NotFoundException("NOT FOUND");
            }
            ModelAndView jspView = jspView("/qna/show.jsp");
            Question question = questionDao.findById(Long.parseLong(questionId));
            jspView.addObject("question", question);
            if (question != null) {
                List<Answer> answers = answerDao.findByQuestionId(question.getId());
                jspView.addObject("answers", answers);
            }
            return jspView;
        }
        throw new NotFoundException("NOT FOUND");
    }
}
