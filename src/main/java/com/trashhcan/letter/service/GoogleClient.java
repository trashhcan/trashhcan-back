package com.trashhcan.letter.service;

import com.trashhcan.letter.dto.request.GoogleAccessTokenRequest;
import com.trashhcan.letter.dto.response.GoogleAccessTokenResponse;
import com.trashhcan.letter.dto.response.GoogleAccountProfileResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.security.auth.login.LoginException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class GoogleClient {

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String clientSecret;

    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String redirectUri;

    private final String accessTokenUrl = "https://oauth2.googleapis.com/token";
    private final String profileUrl = "https://www.googleapis.com/userinfo/v2/me";

    private final RestTemplate restTemplate;
    
    public GoogleAccountProfileResponse getGoogleAccountProfile(final String code) {
        // authorization code로 access token 요청
        final String accessToken = requestGoogleAccessToken(code);
        // access token으로 구글 사용자 정보 요청
        return requestGoogleAccountProfile(accessToken);
    }
    
    // authorization code로 access token 요청하는 메소드
    public String requestGoogleAccessToken(final String code) {
        // 인가코드 디코딩
        final String decodedCode = URLDecoder.decode(code, StandardCharsets.UTF_8);

        // 인가코드를 포함한 httpheader 객체 생성
        final HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        final HttpEntity<GoogleAccessTokenRequest> httpEntity = new HttpEntity<>(
                new GoogleAccessTokenRequest(decodedCode, clientId, clientSecret, redirectUri, "authorization_code"),
                headers
        );

        // 객체를 전달하고 access token을 받는다
        final GoogleAccessTokenResponse response = restTemplate.exchange(
                accessTokenUrl, HttpMethod.POST, httpEntity, GoogleAccessTokenResponse.class
        ).getBody();

        try {
            return Optional.ofNullable(response)
                    .orElseThrow(() -> new LoginException("Access Token not found"))
                    .getAccess_token();
        } catch (LoginException e){
            throw new RuntimeException(e);
        }
    }
    
    // access token으로 구글 사용자 정보 요청하는 메소드
    public GoogleAccountProfileResponse requestGoogleAccountProfile(final String accessToken) {
        // access token을 포함한 객체 생성
        final HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
        final HttpEntity<?> httpEntity = new HttpEntity<>(headers);
        return restTemplate.exchange(profileUrl, HttpMethod.GET, httpEntity, GoogleAccountProfileResponse.class)
                .getBody();
    }

}
