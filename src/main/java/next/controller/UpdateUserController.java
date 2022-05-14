package next.controller;

import next.dao.UserDao;
import next.model.User;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Objects;

public class UpdateUserController implements Controller {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String userId = request.getParameter("userId");
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        HttpSession session = request.getSession();
        User userBySession = (User) session.getAttribute("user");

        if (userBySession == null || !Objects.equals(userId, userBySession.getUserId())) {
            return "/user/update.jsp";
        }

        if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(name) || StringUtils.isEmpty(email) || StringUtils.isEmpty(password)) {
            return "/user/update.jsp";
        }

        UserDao userDao = new UserDao();
        User user = userDao.findByUserId(userId);

        user.setEmail(email);
        user.setName(name);
        user.setPassword(password);

        userDao.update(user);

        return "redirect:/users";
    }
}
