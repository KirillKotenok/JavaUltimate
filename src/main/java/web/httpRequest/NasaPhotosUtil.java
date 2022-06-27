package web.httpRequest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.UnknownContentTypeException;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class NasaPhotosUtil {
    public static final String NASA_URL = "https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?sol=15&api_key=DEMO_KEY";

    public static void main(String[] args) {
        getPhotosSizeByUrlUsingRestTemplate().getPhotos().stream()
                .forEach(System.out::println);
       /* var maxSizePhoto = photosSizeByUrlUsingHttpClient().entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .get();
        System.out.println(maxSizePhoto.getKey());
        System.out.println(maxSizePhoto.getValue());*/
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

    public static Photos getPhotoUrlsUsingWithRestTemplate() {
        var restTemplate = new RestTemplate();
        var photos = restTemplate.getForObject(URI.create(NASA_URL), Photos.class);
        return photos;
    }

    public static Map<String, Long> photosSizeByUrlUsingHttpClient() {
        var client = HttpClient.newBuilder().build();
        var request = HttpRequest.newBuilder(URI.create(NASA_URL)).GET().build();

        return getPhotoUrlsUsingHttpClient().stream()
                .collect(Collectors.toMap(Function.identity(), photoUrl -> getPhotoSize(client, photoUrl)));
    }

    public static Photos getPhotosSizeByUrlUsingRestTemplate() {
        var photos = getPhotoUrlsUsingWithRestTemplate();
        var restTemplate = new RestTemplate();
        for (Photo photo : photos.getPhotos()) {
            var photoSize = "";
            try {
                photoSize = restTemplate.getForObject(URI.create(photo.getUrl()), JsonNode.class)
                        .findValue("Content-Length")
                        .asText();
            } catch (UnknownContentTypeException e) {
                var redirectLocation = e.getResponseHeaders().getLocation();
                System.out.printf("\nRedirect to %s...\n", redirectLocation);
                var photoResult = restTemplate.getForObject(redirectLocation, byte[].class);
                photoSize = restTemplate.headForHeaders(redirectLocation).getFirst("Content-Length");

                photo.setPhoto(photoResult);
                photo.setSize(Long.parseLong(photoSize));
            }
            photo.setSize(Long.parseLong(photoSize));
        }
        return photos;
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
