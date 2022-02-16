package next.controller;

import core.db.DataBase;
import next.model.User;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Objects;

@WebServlet("/users/update")
public class UpdateUserController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getParameter("userId");
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        HttpSession session = request.getSession();
        User userBySession = (User) session.getAttribute("user");

        RequestDispatcher requestDispatcher;
        if (!Objects.equals(userId, userBySession.getUserId())) {
            requestDispatcher = request.getRequestDispatcher("/user/update.jsp");
            requestDispatcher.forward(request, response);
            return;
        }

        if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(name) || StringUtils.isEmpty(email) || StringUtils.isEmpty(password)) {
            requestDispatcher = request.getRequestDispatcher("/user/update.jsp");
            requestDispatcher.forward(request, response);
            return;
        }

        User user = DataBase.findUserById(userId);
        user.setEmail(email);
        user.setName(name);
        user.setPassword(password);

        DataBase.addUser(user);

        response.sendRedirect("/users");
    }
}
