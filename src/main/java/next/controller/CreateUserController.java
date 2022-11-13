package next.controller;

import next.dao.UserDao;
import next.model.User;
import next.view.JspView;
import next.view.ModelAndView;
import next.view.View;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;

public class CreateUserController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(CreateUserController.class);

    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        UserDao userDao = new UserDao();
        User user = new User(request.getParameter("userId"),
                request.getParameter("password"),
                request.getParameter("name"),
                request.getParameter("email"));
        log.debug("user : {}", user);

        try {
            userDao.insert(user);
        } catch (SQLException e) {
            log.error(e.getMessage());
            return jspView("redirect:/forms/signup");
        }

        HttpSession session = request.getSession();
        session.setAttribute("user", user);
        return jspView("redirect:/users");
    }
}
