package web.spring.largestPictureService.service;

import web.spring.largestPictureService.entity.Photo;

public interface PictureService {
    Photo getLargestPicture(String sol);
}
