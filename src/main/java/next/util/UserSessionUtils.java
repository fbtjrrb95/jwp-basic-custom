package next.util;

import javax.servlet.http.HttpSession;

public class UserSessionUtils {
    private UserSessionUtils() {}

    public static boolean isLogined(HttpSession session) {
        Object value = session.getAttribute("user");
        return value != null;
    }
}
