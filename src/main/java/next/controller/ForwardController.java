package next.controller;

import next.view.JspView;
import next.view.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ForwardController implements Controller {
    private String forwardUrl;

    public ForwardController(String forwardUrl) {
        if (forwardUrl == null)
            throw new NullPointerException("forwardUrl is null");

        this.forwardUrl = forwardUrl;
    }

    @Override
    public View execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return JspView.of(forwardUrl);
    }
}
