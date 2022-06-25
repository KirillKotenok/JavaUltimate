package web.httpRequest;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.SneakyThrows;
import org.springframework.web.client.RestTemplate;
import web.json.Backson;
import web.json.Person;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URI;
import java.util.List;

public class Demo {
    private static final String NASA_URLS = "https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?sol=15&api_key=DEMO_KEY";
    private static final String HOST = "93.175.204.87:8080";

    @SneakyThrows
    public static void main(String[] args) {
        var json = "{\n" +
                "  \"last_name\": \"Shtramak\",\n" +
                "  \"first_name\": \"Andrii\",\n" +
                "  \"city\": \"Zarichchya\"\n" +
                "}";
        new Backson<Person>().readObject(Person.class, json)
                .ifPresent(System.out::println);
    }

    private static void sendSocketRequest() {
        try (var socketClient = new Socket("93.175.204.87", 8899)) {
            var os = socketClient.getOutputStream();
            var is = socketClient.getInputStream();

            var writer = new PrintWriter(os);
            writer.println("GET /hello?name=KirillKotenok HTTP/1.1");
            writer.println("HOST: 93.175.204.87");
            writer.flush();

            var reader = new BufferedReader(new InputStreamReader(is));
            reader.lines().forEach(System.out::println);
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    private static List<String> getPhotoUrls(String baseUrl) {
        var template = new RestTemplate();
        var node = template.getForObject(URI.create(baseUrl), JsonNode.class);

        return node.findValuesAsText("img_src");
    }
}
