package web.spring.largestPictureUsingSpring.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Photos {
    private List<Photo> photos = new ArrayList<>();
}
