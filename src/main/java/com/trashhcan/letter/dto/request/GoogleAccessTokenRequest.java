package com.trashhcan.letter.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GoogleAccessTokenRequest {

    private String code;
    private String client_id;
    private String client_secret;
    private String redirect_uri;
    private String grant_type = "authorization_code";
}
