package web;

import lombok.SneakyThrows;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class FindLargestImage {

    public static void printLargestImageParallel(List<? extends String> imageUrls) {
        var startTime1 = System.currentTimeMillis();

        imageUrls.stream()
                .parallel()
                .max(FindLargestImage::compareImgSize)
                .ifPresent(url -> System.out.printf("\n---PARALLEL---\nPhoto with url: %s is the largest size (%d) photo!!!\n",
                        url, getImageSize(url)));

        var finishTime1 = System.currentTimeMillis();
        System.out.println("Time: " + (finishTime1 - startTime1));
    }


    public static void printLargestImage(List<? extends String> imageUrls) {
        var startTime1 = System.currentTimeMillis();

        imageUrls.stream()
                .max(FindLargestImage::compareImgSize)
                .ifPresent(url -> System.out.printf("\n---Simple---\nPhoto with url: %s is the largest size (%d) photo!!!\n",
                        url, getImageSize(url)));

        var finishTime1 = System.currentTimeMillis();
        System.out.println("Time: " + (finishTime1 - startTime1));
    }

    private static int compareImgSize(String firstPhoto, String secondPhoto) {
        var firstSize = getImageSize(firstPhoto);
        var secondSize = getImageSize(secondPhoto);

        return Long.compare(firstSize, secondSize);
    }

@SneakyThrows
    private static long getImageSize(String imageUrl){
        var client = HttpClient.newBuilder().followRedirects(HttpClient.Redirect.NORMAL).build();
        var response = client.send(HttpRequest.newBuilder()
                        .method("HEAD", HttpRequest.BodyPublishers.noBody())
                        .uri(URI.create(imageUrl)).build(),
                HttpResponse.BodyHandlers.ofString());
        return Integer.parseInt(response.headers().firstValue("Content-Length").orElseGet(() -> "0"));
    }
}