package web.httpRequest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class NasaPhotosUtil {
    public static final String NASA_URL = "https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?sol=15&api_key=DEMO_KEY";

    public static void main(String[] args) {
        var maxSizePhoto = photosSizeByUrlUsingHttpClient().entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .get();
        System.out.println(maxSizePhoto.getKey());
        System.out.println(maxSizePhoto.getValue());
    }

    @SneakyThrows
    public static List<String> getPhotoUrlsUsingHttpClient() {
        var request = HttpRequest.newBuilder(URI.create(NASA_URL))
                .GET()
                .build();
        var client = HttpClient.newBuilder().followRedirects(HttpClient.Redirect.NORMAL).build();
        var response = client.send(request, HttpResponse.BodyHandlers.ofString());
        var objectMapper = new ObjectMapper();
        return objectMapper.readTree(response.body())
                .findValuesAsText("img_src");
    }

    public static List<String> getPhotoUrlsUsingWithRestTemplate() {
        var restTemplate = new RestTemplate();
        var node = restTemplate.getForObject(URI.create(NASA_URL), JsonNode.class);
        return node.findValuesAsText("img_src");
    }

    public static Map<String, Long> photosSizeByUrlUsingHttpClient() {
        var client = HttpClient.newBuilder().build();
        var request = HttpRequest.newBuilder(URI.create(NASA_URL)).GET().build();

        return getPhotoUrlsUsingHttpClient().stream()
                .collect(Collectors.toMap(Function.identity(), photoUrl -> getPhotoSize(client, photoUrl)));
    }

    public static Photos photosSizeByUrlUsingRestTemplate() {
        var photosUrl = getPhotoUrlsUsingWithRestTemplate();


        return new Photos();
    }

    @SneakyThrows
    private static Long getPhotoSize(HttpClient client, String url) {
        var response = client.send(HttpRequest.newBuilder().uri(URI.create(url))
                .method("HEAD", HttpRequest.BodyPublishers.noBody()).build(), HttpResponse.BodyHandlers.discarding());

        if (String.valueOf(response.statusCode()).startsWith("3")) {
            return getPhotoSize(client, response.headers().firstValue("Location").get());
        } else {
            return Long.parseLong(response.headers().firstValue("Content-Length").orElseGet(() -> "0"));
        }
    }

}
