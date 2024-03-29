package next.view;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class JspView implements View {
    private static final String DEFAULT_REDIRECT_PREFIX = "redirect:";
    private static final Map<String, JspView> jspViewFactory = new HashMap<>();

    private String viewName;
    private JspView(String viewName) {
        this.viewName = viewName;
    }

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (viewName.startsWith(DEFAULT_REDIRECT_PREFIX)) {
            response.sendRedirect(viewName.substring(DEFAULT_REDIRECT_PREFIX.length()));
            return;
        }

        Set<String> keys = model.keySet();
        keys.forEach(key -> request.setAttribute(key, model.get(key)));

        RequestDispatcher requestDispatcher = request.getRequestDispatcher(viewName);
        requestDispatcher.forward(request, response);
    }

    public synchronized static JspView of(String viewName) {
        if (!jspViewFactory.containsKey(viewName)) {
            JspView jspView = new JspView(viewName);
            jspViewFactory.put(viewName, jspView);
        }

        return jspViewFactory.get(viewName);
    }
}
