package next.controller;

import next.model.Answer;
import next.model.User;
import next.service.AnswerService;
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

    private final AnswerService answerService = new AnswerService();

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
        Answer answer = Answer.builder()
                .writer(writer)
                .contents(contents)
                .questionId(questionId)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
        log.debug("answer: {}", answer);

        Answer savedAnswer = answerService.create(answer);
        return jsonView().addObject("answer", savedAnswer);
    }
}
