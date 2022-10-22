package next.controller;

import next.dao.UserDao;
import next.util.UserSessionUtils;
import next.view.JspView;
import next.view.View;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class ListUserController implements Controller {
    private static final Logger log = LoggerFactory.getLogger(ListUserController.class);

    @Override
    public View execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!UserSessionUtils.isLogined(request.getSession()))
            return JspView.of("redirect:/login");

        UserDao userDao = new UserDao();
        try {
            request.setAttribute("users", userDao.findAll());
        } catch (SQLException e) {
            log.error(e.getMessage());
            return JspView.of("redirect:/");
        }

        return JspView.of("/user/list.jsp");
    }
}
