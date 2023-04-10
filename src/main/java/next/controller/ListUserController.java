package next.controller;

import next.dao.UserDao;
import next.util.UserSessionUtils;
import next.view.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class ListUserController extends AbstractController {

    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        if (!UserSessionUtils.isLogined(request.getSession())) {
            return jspView("/user/login.jsp");
        }

        UserDao userDao = new UserDao();
        return jspView("/user/list.jsp").addObject("users", userDao.findAll());
    }
}
