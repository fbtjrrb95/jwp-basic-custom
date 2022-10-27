package next.controller;

import next.view.View;
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
            View view = controller.execute(request, response);
            if (view != null) {
                view.render(request, response);
            }
        } catch (Exception e) {
            log.error("Exception : {}", e.toString());
            throw new ServletException(e.getMessage());
        }
    }
}
