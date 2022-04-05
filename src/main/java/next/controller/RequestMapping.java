package next.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class RequestMapping {

    private static final Logger log = LoggerFactory.getLogger(RequestMapping.class);
    private static final Map<String, Controller> map = new HashMap<>();

    void init() {
        map.put("/", new HomeController());
        map.put("/forms/signup", new ForwardController("/user/form.jsp"));
        map.put("/forms/login", new ForwardController("/user/login.jsp"));
        map.put("/forms/users/update", new ForwardController("/user/update.jsp"));
        map.put("/login", new LoginController());
        map.put("/logout", new LogoutController());
        map.put("/users", new ListUserController());
        map.put("/users/create", new CreateUserController());
        map.put("/users/update", new UpdateUserController());
        map.put("/users/profile", new ProfileController());

    }

    public Controller getController(String url) {
        return map.get(url);
    }


}
