package web.pictureService;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

@UtilityClass
public class PictureUtils {
    private static final String NASA_URL = "https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?sol=16&api_key=Cpr2Rp2XK8uqHU4MHIs5OPOJKia7irsIuoCk2pXu";
    private static final Map<String, Long> pictureMap = new HashMap<>();

    static {
        insertPicturesMap();
    }

    public static Picture getLargestPicture() {
        var largestPictureEntry = pictureMap.entrySet()
                .stream()
                .parallel()
                .max(Map.Entry.comparingByValue()).get();
        return new Picture(largestPictureEntry.getKey(), largestPictureEntry.getValue());
    }

    public static Collection<Picture> getPicturesList() {
        return pictureMap.entrySet().stream()
                .map(entry -> new Picture(entry.getKey(), entry.getValue()))
                .collect(Collectors.toSet());
    }

    private static void insertPicturesMap() {
        pictureMap.putAll(getUrls().stream()
                .collect((toMap(identity(), PictureUtils::getImageSize))));
    }

    private static Collection<String> getUrls() {
        var restTemplate = new RestTemplate();
        var jsonNode = restTemplate.getForObject(URI.create(NASA_URL), JsonNode.class);
        return Optional.ofNullable(jsonNode.findValuesAsText("img_src"))
                .orElseGet(ArrayList::new);
    }

    @SneakyThrows
    private static Long getImageSize(String imageUrl) {
        var client = HttpClient.newBuilder().followRedirects(HttpClient.Redirect.NORMAL).build();
        var response = client.send(HttpRequest.newBuilder()
                        .method("HEAD", HttpRequest.BodyPublishers.noBody())
                        .uri(URI.create(imageUrl)).build(),
                HttpResponse.BodyHandlers.ofString());
        return Long.parseLong(response.headers().firstValue("Content-Length").orElseGet(() -> "0"));
    }
}
