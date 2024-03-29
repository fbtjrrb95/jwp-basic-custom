package next.controller;

import next.dao.UserDao;
import next.model.User;
import next.view.ModelAndView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String userId = request.getParameter("userId");
        String password = request.getParameter("password");

        UserDao userDao = new UserDao();
        User user = userDao.findByUserId(userId);

        if (user == null) {
            log.error("no user by userId, {}", userId);
            return jspView("/user/login.jsp");
        }

        if (!user.matchPassword(password)) {
            log.error("invalid password");
            return jspView("/user/login.jsp");
        }

        HttpSession session = request.getSession();
        session.setAttribute("user", user);
        return jspView("redirect:/");
    }
}
