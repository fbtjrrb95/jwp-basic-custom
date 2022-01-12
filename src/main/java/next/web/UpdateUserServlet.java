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

@WebServlet("/user/update")
public class UpdateUserServlet extends HttpServlet {

    Logger log = LoggerFactory.getLogger(UpdateUserServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        log.info("request : {}", request);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/user/update.jsp");
        requestDispatcher.forward(request, response);

        log.info("response : {}", response);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getParameter("userId");
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        RequestDispatcher requestDispatcher;
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
    }

}
