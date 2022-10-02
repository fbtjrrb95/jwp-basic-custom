package next.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RequestMapping {

    private static final Logger log = LoggerFactory.getLogger(RequestMapping.class);
    private static final Map<String, Controller> map = new HashMap<>();

    void init() {
        map.put("/", new HomeController());
        map.put("/forms/signup", new ForwardController("/user/form.jsp"));
        map.put("/forms/login", new ForwardController("/user/login.jsp"));
        map.put("/forms/users/update", new ForwardController("/user/update.jsp"));
        map.put("/forms/qna", new ForwardController("/qna/form.jsp"));
        map.put("/qna", new ForwardController("/qna/show.jsp"));
        map.put("/login", new LoginController());
        map.put("/logout", new LogoutController());
        map.put("/users", new ListUserController());
        map.put("/users/create", new CreateUserController());
        map.put("/users/update", new UpdateUserController());
        map.put("/users/profile", new ProfileController());
        map.put("/qna/answers", new AnswerController());
        map.put("/qna/answers/", new AnswerDetailController());
        map.put("/qna/questions", new QuestionController());

    }

    public Controller getController(String url) {
        List<String> list = map.keySet().stream()
                .filter(url::startsWith)
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());
        if (list == null || list.isEmpty()) return null;
        return map.get(list.get(0));
    }
}
