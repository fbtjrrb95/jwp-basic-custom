package next.view;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;


public class JsonView implements View {
    @Override
    public void render(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter printWriter = response.getWriter();
        printWriter.print(objectMapper.writeValueAsString(createModel(request)));
    }

    private Map<String, Object> createModel(HttpServletRequest request) {
        Enumeration<String> attributeNames = request.getAttributeNames();
        Map<String, Object> model = new HashMap<>();
        while (attributeNames.hasMoreElements()) {
            String name = attributeNames.nextElement();
            model.put(name, request.getAttribute(name));
        }
        return model;
    }
}
