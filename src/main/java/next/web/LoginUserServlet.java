package next.web;

import core.db.DataBase;
import next.model.User;
import org.apache.commons.lang3.StringUtils;
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

@WebServlet("/user/login")
public class LoginUserServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(LoginUserServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String userId = request.getParameter("userId");
        String password = request.getParameter("password");

        RequestDispatcher requestDispatcher;
        if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(password)) {
            if (StringUtils.isEmpty(userId)) {
                log.error("invalid userId");
            }
            if (StringUtils.isEmpty(password)) {
                log.error("invalid password");
            }
            requestDispatcher = request.getRequestDispatcher("/user/login.html");
            requestDispatcher.forward(request, response);
            return;
        }

        User userById = DataBase.findUserById(userId);
        if (userById == null) {
            log.error("no user by userId, {}", userId);
            requestDispatcher = request.getRequestDispatcher("/user/login.html");
            requestDispatcher.forward(request, response);
            return;
        }

        if (!userById.getPassword().equals(password)) {
            log.error("invalid password");
            requestDispatcher = request.getRequestDispatcher("/user/login.html");
            requestDispatcher.forward(request, response);
            return;
        }

        HttpSession session = request.getSession();
        session.setAttribute("user", userById);

        response.sendRedirect("/index.html");

    }
}
