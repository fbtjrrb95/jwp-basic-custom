package next.controller;

import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import next.model.Question;
import next.view.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class GetQuestionController extends QuestionDetailController {
    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        long questionId = QuestionIdParser.apply("/qna/questions/", request.getRequestURI());
        QuestionDao questionDao = new QuestionDao();
        Question question = questionDao.findById(questionId);

        ModelAndView jspView = jspView("/qna/show.jsp");
        jspView.addObject("question", question);

        if (question != null) {
            AnswerDao answerDao = new AnswerDao();
            List<Answer> answers = answerDao.findByQuestionId(question.getId());
            jspView.addObject("answers", answers);
        }

        return jspView;
    }
}
