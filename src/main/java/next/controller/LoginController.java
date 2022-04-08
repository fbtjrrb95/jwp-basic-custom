package next.controller;

import core.db.DataBase;
import next.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginController implements Controller {
    private static final Logger log = LoggerFactory.getLogger(LoginController.class);


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String userId = request.getParameter("userId");
        String password = request.getParameter("password");

        User user = DataBase.findUserById(userId);

        if (user == null) {
            log.error("no user by userId, {}", userId);
            return "/user/login.jsp";
        }

        if (!user.matchPassword(password)) {
            log.error("invalid password");
            return "/user/login.jsp";
        }

        HttpSession session = request.getSession();
        session.setAttribute("user", user);

        return "redirect:/";
    }
}
