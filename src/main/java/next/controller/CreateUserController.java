package next.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import core.util.ObjectMapperFactory;
import next.dao.UserDao;
import next.model.User;
import next.view.ModelAndView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.Map;

public class CreateUserController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(CreateUserController.class);

    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ObjectMapper objectMapper = ObjectMapperFactory.getInstance();
        Map<String, String> map = objectMapper.readValue(
                request.getInputStream(),
                new TypeReference<>() {});

        String userId = map.get("userId");
        String name = map.get("name");
        String email = map.get("email");
        String password = map.get("password");

        UserDao userDao = new UserDao();
        User user = new User(userId, password, name, email);
        log.debug("user : {}", user);

        try {
            userDao.insert(user);
        } catch (SQLException e) {
            log.error(e.getMessage());
            return jspView("redirect:/forms/signup");
        }

        HttpSession session = request.getSession();
        session.setAttribute("user", user);
        return jsonView().addObject("user", user);
    }
}
