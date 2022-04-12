package next.controller;

import next.dao.UserDao;
import next.util.UserSessionUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class ListUserController implements Controller {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (!UserSessionUtils.isLogined(request.getSession())) {
            return "redirect:/login";
        }

        UserDao userDao = new UserDao();
        try {
            request.setAttribute("users", userDao.findAll());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "/user/list.jsp";
    }
}
