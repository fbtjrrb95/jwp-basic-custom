package next.controller;

import next.dao.UserDao;
import next.model.User;
import next.view.JspView;
import next.view.ModelAndView;
import next.view.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ProfileController extends AbstractController {

    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        UserDao userDao = new UserDao();
        String userId = request.getParameter("userId");
        User user = userDao.findByUserId(userId);

        if (user == null) throw new IllegalArgumentException("No Valid User Id");

        return jspView("/user/profile.jsp").addObject("user", user);
    }
}
