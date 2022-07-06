package web.spring.pictureService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import web.pictureService.Picture;
import web.spring.pictureService.service.PictureService;

import java.util.Collection;

@RestController
@RequestMapping("/picture")
@RequiredArgsConstructor
public class Controller {
    private transient final PictureService pictureService;

    @GetMapping
    public Collection<Picture> getPictureList(@RequestBody String picturesUrl) {
        return pictureService.getPictureList(picturesUrl);
    }
}
