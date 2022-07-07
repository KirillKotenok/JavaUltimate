package web.spring.largestPictureUsingSpring.service;

import web.spring.largestPictureUsingSpring.entity.Photo;

public interface PictureService {
    Photo getLargestPicture(String sol);
}
