package com.trashhcan.letter.controller;


import com.trashhcan.letter.dto.response.GoogleAccountProfileResponse;
import com.trashhcan.letter.dto.response.MemberResponseDto;
import com.trashhcan.letter.service.GoogleClient;
import com.trashhcan.letter.service.OAuth2MemberService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/login")
@Slf4j
public class OAuthController {

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String redirectUri;

    private final String googleAuthUrl = "https://accounts.google.com/o/oauth2/v2/auth";

    private final GoogleClient googleClient;
    private final OAuth2MemberService oAuth2MemberService;

    // 로컬 테스트용
    @GetMapping("/google")
    public void redirectToGoogleAuth(HttpServletResponse response) throws IOException {
        String googleLoginUrl = "https://accounts.google.com/o/oauth2/v2/auth" +
                "?client_id=" + clientId +
                "&redirect_uri=" + redirectUri +
                "&response_type=code" +
                "&scope=email%20profile";
        log.info(googleLoginUrl);
        response.sendRedirect(googleLoginUrl);
    }
    // 로컽 테스트용


    @GetMapping("/google/callback")
    public ResponseEntity<MemberResponseDto> googleCallback(@RequestParam("code") String code) {

        log.info("Authorization code: {}", code);

        // 구글 사용자 계정 정보 가져오기
        GoogleAccountProfileResponse googleAccountInfo = googleClient.getGoogleAccountProfile(code);

        // 구글 사용자 계정 정보로 회원가입 및 로그인 처리
        MemberResponseDto memberResponseDto = oAuth2MemberService.signUpOrIn(googleAccountInfo, "google");

        return ResponseEntity.ok(memberResponseDto);
    }
}
