package web.spring.largestPictureService.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import web.spring.largestPictureService.entity.Photo;
import web.spring.largestPictureService.entity.Photos;

import java.util.Comparator;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PictureServiceImpl implements PictureService {
    private transient final RestTemplate restTemplate;
    @Value("${nasa.base.url}")
    private String baseUrl;
    @Value("${nasa.api.key}")
    private String apiKey;

    @Override
    @Cacheable("largestPicture")
    public Photo getLargestPicture(String sol) {
        var requestUrl = UriComponentsBuilder.fromUriString(baseUrl)
                .queryParam("sol", sol)
                .queryParam("api_key", apiKey)
                .toUriString();
        return generatePhotosFromJson(requestUrl).getPhotos().stream()
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
