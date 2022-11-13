package next.controller;

import next.view.ModelAndView;
import next.view.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;


public interface Controller {
    ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception;

    default boolean isGet(HttpServletRequest request) {
        return Method.valueOf(request.getMethod().toUpperCase(Locale.ROOT)).equals(Method.GET);
    }

    default boolean isPost(HttpServletRequest request) {
        return Method.valueOf(request.getMethod().toUpperCase(Locale.ROOT)).equals(Method.POST);
    }

    default boolean isDelete(HttpServletRequest request) {
        return Method.valueOf(request.getMethod().toUpperCase(Locale.ROOT)).equals(Method.DELETE);
    }
}
