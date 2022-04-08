package next.controller;

import core.db.DataBase;
import next.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ProfileController implements Controller {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String userId = request.getParameter("userId");
        User user = DataBase.findUserById(userId);

        if (user == null) {
            throw new IllegalArgumentException("No Valid User Id");
        }

        request.setAttribute("user", user);

        return "/user/profile.jsp";
    }
}
