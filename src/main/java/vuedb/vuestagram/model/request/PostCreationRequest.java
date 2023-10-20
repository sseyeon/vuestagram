package vuedb.vuestagram.model.request;

import lombok.Data;

@Data
public class PostCreationRequest {
    private String content;
    private String date;
    private String filter;
    private String post_image;
}
