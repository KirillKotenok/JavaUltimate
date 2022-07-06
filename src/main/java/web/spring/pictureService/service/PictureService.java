package web.spring.pictureService.service;

import web.pictureService.Picture;

import java.util.Collection;

public interface PictureService {
    public Collection<Picture> getPictureList(String picturesUrl);
}
