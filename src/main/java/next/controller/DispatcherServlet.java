package next.controller;

import next.view.ModelAndView;
import next.view.View;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
        String method = request.getMethod();
        log.debug("Method : {}, Request URI : {}", method, uri);

        Controller controller = map.getController(uri, Method.valueOf(method));
        ModelAndView modelAndView;
        try {
            modelAndView = controller.execute(request, response);
            View view = modelAndView.getView();
            view.render(modelAndView.getModel(), request, response);
        } catch (IllegalAccessException e) {
            response.setStatus(403);
        } catch (Exception e) {
            log.error("Exception : {}", e.toString());
            throw new ServletException(e.getMessage());
        }
    }
}
