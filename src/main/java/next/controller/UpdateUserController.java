package next.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import core.util.ObjectMapperFactory;
import next.dao.UserDao;
import next.model.User;
import next.view.ModelAndView;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.Objects;

public class UpdateUserController extends AbstractController {

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

        HttpSession session = request.getSession();
        User userBySession = (User) session.getAttribute("user");

        if (userBySession == null || !Objects.equals(userId, userBySession.getUserId())) {
            return jsonView();
        }

        if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(name) || StringUtils.isEmpty(email) || StringUtils.isEmpty(password)) {
            return jsonView();
        }

        UserDao userDao = new UserDao();
        User user = userDao.findByUserId(userId);

        user.setEmail(email);
        user.setName(name);
        user.setPassword(password);
        userDao.update(user);

        return jsonView().addObject("user", user);
    }
}
