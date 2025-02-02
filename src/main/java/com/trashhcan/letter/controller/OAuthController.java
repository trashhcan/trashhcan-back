package com.trashhcan.letter.controller;


import com.trashhcan.letter.dto.request.CodeRequest;
import com.trashhcan.letter.dto.response.GoogleAccountProfileResponse;
import com.trashhcan.letter.dto.response.KakaoAccountProfileResponse;
import com.trashhcan.letter.dto.response.MemberResponseDto;
import com.trashhcan.letter.service.GoogleClient;
import com.trashhcan.letter.service.KakaoClient;
import com.trashhcan.letter.service.OAuth2MemberService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/login")
@Slf4j
public class OAuthController {

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String googleClientId;

    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String googleRedirectUri;

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String kakaoClientId;

    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String kakaoRedirectUri;

    private final GoogleClient googleClient;
    private final OAuth2MemberService oAuth2MemberService;
    private final KakaoClient kakaoClient;

    // 로컬 테스트용
    @GetMapping("/google")
    public void redirectToGoogleAuth(HttpServletResponse response) throws IOException {
        String googleLoginUrl = "https://accounts.google.com/o/oauth2/v2/auth" +
                "?client_id=" + googleClientId +
                "&redirect_uri=" + googleRedirectUri +
                "&response_type=code" +
                "&scope=email%20profile";
        log.info(googleLoginUrl);
        response.sendRedirect(googleLoginUrl);
    }
    // 로컽 테스트용


    @PostMapping("/google/callback")
    public ResponseEntity<MemberResponseDto> googleCallback(@RequestBody CodeRequest codeRequest) {
        String code = codeRequest.getCode();
        log.info("Authorization code: {}", code);

        // 구글 사용자 계정 정보 가져오기
        GoogleAccountProfileResponse googleAccountInfo = googleClient.getGoogleAccountProfile(code);

        // 구글 사용자 계정 정보로 회원가입 및 로그인 처리
        MemberResponseDto memberResponseDto = oAuth2MemberService.signUpOrIn(googleAccountInfo, "google");

        return ResponseEntity.ok(memberResponseDto);
    }

    // 로컬 테스트용
    @GetMapping("/kakao")
    public void redirectToKakaoAuth(HttpServletResponse response) throws IOException {
        String kakaoLoginUrl = "https://kauth.kakao.com/oauth/authorize"+
                "?response_type=code" +
                "&client_id=" + kakaoClientId +
                "&redirect_uri=" + kakaoRedirectUri;

        log.info(kakaoLoginUrl);
        response.sendRedirect(kakaoLoginUrl);
    }

    @PostMapping("/kakao/callback")
    public ResponseEntity<MemberResponseDto> kakaoCallback(@RequestBody CodeRequest codeRequest) {
        String code = codeRequest.getCode();
        log.info("Authorization code: {}", code);

        // 카카오 사용자 계정 정보 가져오기
        KakaoAccountProfileResponse kakaoAccountInfo = kakaoClient.getKakaoAccountProfile(code);

        // 카카오 사용자 계정 정보로 회원가입 및 로그인 처리
        MemberResponseDto memberResponseDto = oAuth2MemberService.signUpOrIn(kakaoAccountInfo, "kakao");

        return ResponseEntity.ok(memberResponseDto);
    }
}
