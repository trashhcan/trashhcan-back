package com.trashhcan.letter.service;


import com.trashhcan.letter.dto.response.GoogleAccountProfileResponse;
import com.trashhcan.letter.dto.response.KakaoAccessTokenResponse;
import com.trashhcan.letter.dto.response.KakaoAccountProfileResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.security.auth.login.LoginException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class KakaoClient {

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String redirectUri;

    @Value("${spring.security.oauth2.client.registration.kakao.client-secret}")
    private String clientSecret;

    private final RestTemplate restTemplate;
    private final String accessTokenUrl = "https://kauth.kakao.com/oauth/token";
    private final String profileUrl = "https://kapi.kakao.com/v2/user/me";

    public KakaoAccountProfileResponse getKakaoAccountProfile(final String code) {
        // authorization code로 access token 요청
        final String accessToken = requestKakaoAccessToken(code);
        log.info("Access token: " + accessToken);

        // access token으로 카카오 사용자 정보 요청
        return requestKakaoAccountProfile(accessToken);
    }

    // authorization code로 access token 요청하는 메소드
    public String requestKakaoAccessToken(final String code) {
        // 인가코드 디코딩
        //final String decodedCode = URLDecoder.decode(code, StandardCharsets.UTF_8);

        // 인가코드를 포함한 httpheader 객체 생성
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", clientId);
        params.add("redirect_uri", redirectUri);
        params.add("code", code);
        params.add("client_secret", clientSecret);


        final HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params, headers);

        // 객체를 전달하고 access token을 받는다
        try {
            final KakaoAccessTokenResponse response = restTemplate.exchange(
                    accessTokenUrl, HttpMethod.POST, httpEntity, KakaoAccessTokenResponse.class
            ).getBody();

            return Optional.ofNullable(response)
                    .orElseThrow(() -> new LoginException("Access token not found"))
                    .getAccess_token();
        } catch (LoginException | RestClientException e){
            throw new RuntimeException("Failed to get access token", e);
        }
    }

    // access token으로 구글 사용자 정보 요청하는 메소드
    public KakaoAccountProfileResponse requestKakaoAccountProfile(final String accessToken) {
        // access token을 포함한 객체 생성
        final HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
        final HttpEntity<?> httpEntity = new HttpEntity<>(headers);

        try {
            return restTemplate.exchange(profileUrl, HttpMethod.GET, httpEntity, KakaoAccountProfileResponse.class)
                    .getBody();
        } catch (RestClientException e) {
            throw new RuntimeException("Failed to get kakao profile", e);
        }
    }
}
