package next.controller;

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

public class CreateQuestionController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(UpdateQuestionController.class);

    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

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

        QuestionDao questionDao = new QuestionDao();
        Question savedQuestion = questionDao.save(question);

        return jsonView().addObject("question", savedQuestion);
    }
}
