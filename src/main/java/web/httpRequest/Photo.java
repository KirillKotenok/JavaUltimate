package web.httpRequest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Photo {
    @JsonProperty("img_src")
    private String url;
    private byte[] photo;
    private Long size;
}
