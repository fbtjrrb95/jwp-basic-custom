package next.controller;

import core.exception.NotFoundException;

import java.util.*;

public class RequestMapping {

    private static final Map<String, Controller> map = new HashMap<>();
    private static final Map<String, Map<Method, Controller>> controllerMap = new HashMap<>();

    void init() {
        controllerMap.put("/", buildHomeControllerMap());
        controllerMap.put("/forms/signup", buildForwardControllerMap("/user/form.jsp"));
        controllerMap.put("/forms/login", buildForwardControllerMap("/user/login.jsp"));
        controllerMap.put("/forms/users/update", buildForwardControllerMap("/user/update.jsp"));
        controllerMap.put("/forms/qna", buildForwardControllerMap("/qna/form.jsp"));
        controllerMap.put("/qna", buildForwardControllerMap("/qna/show.jsp"));
        controllerMap.put("/login", buildLoginControllerMap());
        controllerMap.put("/logout", buildLogoutControllerMap());
        controllerMap.put("/users", buildUserControllerMap());
        controllerMap.put("/users/profile", buildProfileControllerMap());
        controllerMap.put("/qna/answers", buildAnswerControllerMap());
        map.put("/qna/answers/", new AnswerDetailController());
        map.put("/qna/questions", new QuestionController());
        map.put("/qna/questions/", new QuestionDetailController());
    }

    private Map<Method, Controller> buildAnswerControllerMap() {
        Map<Method, Controller> answerControllerMap = new HashMap<>();
        answerControllerMap.put(Method.POST, new AnswerController());
        return answerControllerMap;
    }

    private Map<Method, Controller> buildProfileControllerMap() {
        Map<Method, Controller> profileControllerMap = new HashMap<>();
        profileControllerMap.put(Method.GET, new ProfileController());
        return profileControllerMap;
    }

    private Map<Method, Controller> buildUserControllerMap() {
        Map<Method, Controller> userControllerMap = new HashMap<>();
        userControllerMap.put(Method.GET, new ListUserController());
        userControllerMap.put(Method.PUT, new UpdateUserController());
        userControllerMap.put(Method.POST, new CreateUserController());
        return userControllerMap;
    }

    private Map<Method, Controller> buildLogoutControllerMap() {
        Map<Method, Controller> loginControllerMap = new HashMap<>();
        loginControllerMap.put(Method.GET, new LogoutController());
        return loginControllerMap;
    }

    private Map<Method, Controller> buildLoginControllerMap() {
        Map<Method, Controller> loginControllerMap = new HashMap<>();
        loginControllerMap.put(Method.POST, new LoginController());
        return loginControllerMap;
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
                                .orElseThrow(NotFoundException::new)
                );
    }

    private Optional<String> getMappedUrl(Collection<String> keySet, String url) {
        return keySet.stream()
                .filter(url::startsWith)
                .max(Comparator.naturalOrder());
    }
}
