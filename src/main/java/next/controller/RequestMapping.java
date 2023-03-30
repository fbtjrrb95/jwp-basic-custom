package next.controller;

import java.util.*;

public class RequestMapping {

    private static final Map<String, Controller> map = new HashMap<>();
    private static final Map<String, Map<Method, Controller>> controllerMap = new HashMap<>();

    void init() {
        controllerMap.put("/", buildHomeControllerMap());
        controllerMap.put("/forms/signup", buildForwardControllerMap("/user/form.jsp"));
        controllerMap.put("/forms/login", buildForwardControllerMap("/user/login.jsp"));
        controllerMap.put("/forms/users/update", buildForwardControllerMap("/user/update.jsp"));
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
        map.put("/qna/questions/", new QuestionDetailController());
    }

    private Map<Method, Controller> buildHomeControllerMap() {
        Map<Method, Controller> homeControllerMap = new HashMap<>();
        homeControllerMap.put(Method.GET, new HomeController());
        return homeControllerMap;
    }

    private Map<Method, Controller> buildForwardControllerMap(String jspPath) {
        Map<Method, Controller> forwardControllerMap = new HashMap<>();
        forwardControllerMap.put(Method.GET, new ForwardController(jspPath));
        return forwardControllerMap;
    }

    public Controller getController(String url, Method method) {
        return getMappedUrl(map.keySet(), url)
                .map(map::get)
                .orElseGet(
                        () -> getMappedUrl(controllerMap.keySet(), url)
                                .map(s -> controllerMap.get(s).get(method))
                                .orElse(null)
                );
    }

    private Optional<String> getMappedUrl(Collection<String> keySet, String url) {
        return keySet.stream()
                .filter(url::startsWith)
                .max(Comparator.naturalOrder());
    }
}
