package web.socket.ssl;

import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import javax.net.ssl.SSLSocketFactory;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;
import static org.apache.commons.lang3.StringUtils.*;

@UtilityClass
public class PictureUtils {
    private static final String NASA_API_HOST = "api.nasa.gov";
    private static final String NASA_BREAKPOINT = "/mars-photos/api/v1/rovers/curiosity/photos?sol=16&" +
            "api_key=Cpr2Rp2XK8uqHU4MHIs5OPOJKia7irsIuoCk2pXu";
    private static final int DEFAULT_PORT = 443;
    private static final Map<String, Long> pictureMap = new HashMap<>();

    static {
        insertPicturesMap();
    }

    private static void insertPicturesMap() {
        pictureMap.putAll(getPictureUrls().stream()
                .collect((toMap(identity(), PictureUtils::getImageSize))));
    }

    public static Picture getLargestPicture() {
        var largestPictureEntry = pictureMap.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue()).get();
        return new Picture(largestPictureEntry.getKey(), largestPictureEntry.getValue());
    }

    public static Collection<Picture> getPicturesList() {
        return pictureMap.entrySet().stream()
                .map(entry -> new Picture(entry.getKey(), entry.getValue()))
                .collect(Collectors.toSet());
    }

    @SneakyThrows
    private static Collection<String> getPictureUrls() {
        @Cleanup var socket = SSLSocketFactory.getDefault().createSocket(NASA_API_HOST, DEFAULT_PORT);
        var responseBody = SocketUtils.doGet(socket, NASA_BREAKPOINT);
        return getPhotosUrlFromResponseBody(responseBody);
    }

    @SneakyThrows
    private static Long getImageSize(String imageUrl) {
        @Cleanup var socket = SocketUtils.getSSLSocket(SocketUtils.getHostFromUrl(imageUrl), DEFAULT_PORT);
        var response = SocketUtils.doHead(socket, imageUrl);
        if (containsIgnoreCase(response, "Moved Permanently")) {
            var redirectLocation = getRedirectLocation(response);
            @Cleanup var redirectSocket = SocketUtils.getSSLSocket(
                    SocketUtils.getHostFromUrl(redirectLocation), DEFAULT_PORT);
            response = SocketUtils.doHead(redirectSocket, redirectLocation);
        }
        return getPictureSizeFromResponse(response);
    }

    private static Long getPictureSizeFromResponse(String response) {
        return Long.parseLong(Optional.ofNullable(substringBetween(
                response, "Content-Length: ", "\n")).orElseGet(() -> "0"));
    }

    private static String getRedirectLocation(String responseBody) {
        return substringBetween(responseBody, "Location: ", "\n");
    }

    private static Collection<String> getPhotosUrlFromResponseBody(String responseBody) {
        return Arrays.stream(substringsBetween(responseBody, "img_src\":\"", "\""))
                .collect(Collectors.toList());
    }
}
