package next.controller;

import javassist.NotFoundException;
import next.dao.QuestionDao;
import next.model.Question;
import next.model.User;
import next.view.ModelAndView;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

public class QuestionController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(QuestionController.class);

    private final QuestionDao questionDao = new QuestionDao();

    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
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
            Question question = Question.builder()
                    .contents(contents)
                    .writer(writer)
                    .title(title)
                    .createdAt(createdAt)
                    .updatedAt(updatedAt)
                    .build();
            log.debug("question: {}", question);

            Question savedQuestion = questionDao.save(question);
            return jsonView().addObject("question", savedQuestion);
        }

        if (isGet(request)) {
            List<Question> questions = questionDao.findAll();
            return jsonView().addObject("questions", questions);
        }

        throw new NotFoundException("NOT FOUND");
    }
}
