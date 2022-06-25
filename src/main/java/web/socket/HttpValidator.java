package web.socket;

import java.util.regex.Pattern;

public class HttpValidator {
    private static final Pattern pattern = Pattern.compile("GET \\/hello\\?name=.+HTTP\\/1\\.1");

    public static boolean validate(String data) {
        return data.matches(pattern.pattern());
    }
}
