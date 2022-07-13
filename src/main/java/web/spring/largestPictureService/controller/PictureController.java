package web.spring.largestPictureService.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import web.spring.largestPictureService.service.PictureService;

import java.net.URI;

@Log4j2
@RestController
@RequestMapping("/pictures")
@RequiredArgsConstructor
public class PictureController {
    private final PictureService pictureService;

    @GetMapping("/{sol}/largest")
    public ResponseEntity<?> getLargestPicture(@PathVariable String sol) {
        log.info("Request with sol: " +  sol);
        var largestPictureUrl = pictureService.getLargestPicture(sol).getUrl();
        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY)
                .location(URI.create(largestPictureUrl))
                .build();
    }
}
