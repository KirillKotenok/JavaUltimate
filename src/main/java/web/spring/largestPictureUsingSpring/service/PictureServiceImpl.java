package web.spring.largestPictureUsingSpring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import web.spring.largestPictureUsingSpring.entity.Photo;
import web.spring.largestPictureUsingSpring.entity.Photos;

import java.util.Comparator;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PictureServiceImpl implements PictureService {
    private transient final RestTemplate restTemplate;
    private final String baseUrlTemplate = "https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?sol=%s&api_key=Cpr2Rp2XK8uqHU4MHIs5OPOJKia7irsIuoCk2pXu";

    @Override
    @Cacheable("largestPicture")
    public Photo getLargestPicture(String sol) {
        var baseUrl = String.format(baseUrlTemplate, sol);
        return generatePhotosFromJson(baseUrl).getPhotos().stream()
                .peek(this::setPictureSize)
                .max(Comparator.comparing(Photo::getSize))
                .get();
    }

    private Photos generatePhotosFromJson(String baseUrl) {
        return restTemplate.getForObject(baseUrl, Photos.class);
    }

    private void setPictureSize(Photo photo) {
        var headers = restTemplate.headForHeaders(photo.getUrl());
        var redirectLocation = headers.getLocation();
        var size = Long.parseLong(Optional.ofNullable(restTemplate.headForHeaders(redirectLocation)
                .getFirst("Content-Length")).orElseGet(() -> "0"));
        photo.setSize(size);
    }

}
