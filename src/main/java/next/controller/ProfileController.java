package next.controller;

import next.dao.UserDao;
import next.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ProfileController implements Controller {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        UserDao userDao = new UserDao();
        String userId = request.getParameter("userId");
        User user = userDao.findByUserId(userId);

        if (user == null) throw new IllegalArgumentException("No Valid User Id");

        request.setAttribute("user", user);
        return "/user/profile.jsp";
    }
}
