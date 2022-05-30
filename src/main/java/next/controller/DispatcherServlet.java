package next.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(DispatcherServlet.class);
    private static final String DEFAULT_REDIRECT_PREFIX = "redirect:";

    private RequestMapping map;

    public void init() {
        map = new RequestMapping();
        map.init();
    }

    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        String uri = request.getRequestURI();
        log.debug("Method : {}, Request URI : {}", request.getMethod(), uri);

        Controller controller = map.getController(uri);
        try {
            String viewName = controller.execute(request, response);
            move(viewName, request, response);
        } catch (Exception e) {
            log.error("Exception : {}", e.toString());
            throw new ServletException(e.getMessage());
        }

    }

    private void move(String viewName, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        if (viewName.startsWith(DEFAULT_REDIRECT_PREFIX)) {
            response.sendRedirect(viewName.substring(DEFAULT_REDIRECT_PREFIX.length()));
            return;
        }

        RequestDispatcher requestDispatcher = request.getRequestDispatcher(viewName);
        requestDispatcher.forward(request, response);
    }


}
