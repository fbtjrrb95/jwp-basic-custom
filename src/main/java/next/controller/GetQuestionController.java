package next.controller;

import next.dao.QuestionDao;
import next.model.Question;
import next.view.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class GetQuestionController extends AbstractController {
    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        QuestionDao questionDao = new QuestionDao();
        List<Question> questions = questionDao.findAll();

        return jsonView().addObject("questions", questions);

    }
}
