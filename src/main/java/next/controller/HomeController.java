package next.controller;

import javassist.NotFoundException;
import next.dao.QuestionDao;
import next.model.Question;
import next.view.JspView;
import next.view.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Locale;

public class HomeController implements Controller {
    @Override
    public View execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (Method.valueOf(request.getMethod().toUpperCase(Locale.ROOT)).equals(Method.GET)) {
            QuestionDao questionDao = new QuestionDao();
            List<Question> questions = questionDao.findAll();
            request.setAttribute("questions", questions);
            return JspView.of("/home.jsp");
        }
        throw new NotFoundException("NOT FOUND");
    }
}
