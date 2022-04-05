package next.controller;

import core.db.DataBase;
import next.util.UserSessionUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ListUserController implements Controller {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (!UserSessionUtils.isLogined(request.getSession())) {
            return "redirect:/login";
        }

        request.setAttribute("users", DataBase.findAll());
        return "/user/list.jsp";
    }
}
