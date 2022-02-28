package next.controller;

import core.db.DataBase;
import next.model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/users/profile")
public class ProfileController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = req.getParameter("userId");
        User user = DataBase.findUserById(userId);

        if (user == null) {
            throw new IllegalArgumentException("No Valid User Id");
        }

        req.setAttribute("user", user);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/user/profile.jsp");
        requestDispatcher.forward(req, resp);
    }
}
