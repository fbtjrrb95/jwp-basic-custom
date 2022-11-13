package next.controller;

import javassist.NotFoundException;
import next.dao.QuestionDao;
import next.view.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

public class HomeController extends AbstractController {
    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (Method.valueOf(request.getMethod().toUpperCase(Locale.ROOT)).equals(Method.GET)) {
            QuestionDao questionDao = new QuestionDao();
            return jspView("/home.jsp").addObject("questions", questionDao.findAll());
        }
        throw new NotFoundException("NOT FOUND");
    }
}
