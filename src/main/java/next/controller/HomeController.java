package next.controller;

import javassist.NotFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

public class HomeController implements Controller {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (Method.valueOf(request.getMethod().toUpperCase(Locale.ROOT)).equals(Method.GET)) {
            return "/home.jsp";
        }
        throw new NotFoundException("NOT FOUND");
    }
}
