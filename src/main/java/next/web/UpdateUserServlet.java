package next.web;

import core.db.DataBase;
import next.model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user/update")
public class UpdateUserServlet extends HttpServlet {

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getParameter("userId");
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        RequestDispatcher requestDispatcher;
        if (userId.isEmpty() || name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            requestDispatcher = request.getRequestDispatcher("/user/update.jsp");
            requestDispatcher.forward(request, response);
            return;
        }

        User user = DataBase.findUserById(userId);
        user.setEmail(email);
        user.setName(name);
        user.setPassword(password);

        DataBase.addUser(user);
    }
}
