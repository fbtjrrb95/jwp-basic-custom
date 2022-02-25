package next.controller;

import core.db.DataBase;
import next.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginController extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/user/login.jsp");
        requestDispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String userId = request.getParameter("userId");
        String password = request.getParameter("password");

        User user = DataBase.findUserById(userId);
        RequestDispatcher requestDispatcher;
        if (user == null) {
            log.error("no user by userId, {}", userId);
            requestDispatcher = request.getRequestDispatcher("/user/login.jsp");
            requestDispatcher.forward(request, response);
            return;
        }

        if (!user.matchPassword(password)) {
            log.error("invalid password");
            requestDispatcher = request.getRequestDispatcher("/user/login.jsp");
            requestDispatcher.forward(request, response);
            return;
        }

        HttpSession session = request.getSession();
        session.setAttribute("user", user);

        response.sendRedirect("/");
    }
}
