package web.socket.simple;

import static org.apache.commons.lang3.StringUtils.contains;

public class HostValidator {
    public static boolean validate(String host, String data) {
        return contains(data, String.format("Host: %s.+", host));
    }
}
