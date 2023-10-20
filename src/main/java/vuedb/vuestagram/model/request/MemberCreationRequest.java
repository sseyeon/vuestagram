package vuedb.vuestagram.model.request;

import lombok.Data;

@Data
public class MemberCreationRequest {
    private String name;
    private String password;
    private String reg_date;
    private String status;
    private String user_image;
    private String userid;
}
