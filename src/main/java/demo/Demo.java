package demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static java.net.URI.create;

@SpringBootApplication
public class Demo {
    public static void main(String[] args) {
/*

        String json = "{\n" +
                "                  \"firstName\": \"Kirill\",\n" +
                "                  \"lastName\": \"Kotenok\",\n" +
                "                  \"team\": \"Petros\",\n" +
                "                  \"trainingDaysPerWeek\": 5,\n" +
                "                  \"minutesPerTrainingDay\": 120\n" +
                "                }";
        String url = "https://salty-escarpment-89358.herokuapp.com/training/stats";

        var restTemplate = new RestTemplate();
        var headers = new HttpHeaders();
        headers.put("Content-Type", List.of("application/json"));
        RequestEntity<String> request = RequestEntity.post(create(url)).headers(headers).body(json);
        var response = restTemplate.exchange(request, String.class);

        System.out.println(response.getBody());
*/

        SpringApplication.run(Demo.class, args);
    }
}
