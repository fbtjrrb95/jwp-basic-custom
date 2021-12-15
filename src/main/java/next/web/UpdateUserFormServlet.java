package next.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user/form")
public class UpdateUserFormServlet extends HttpServlet {

    Logger log = LoggerFactory.getLogger(UpdateUserFormServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        log.info("request : {}", request);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/user/update.jsp");
        requestDispatcher.forward(request, response);

        log.info("response : {}", response);
    }

}
