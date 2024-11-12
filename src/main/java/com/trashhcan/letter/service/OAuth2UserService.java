//package com.trashhcan.letter.service;
//
//import com.trashhcan.letter.domain.Member;
//import com.trashhcan.letter.repository.MemberRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.boot.autoconfigure.task.TaskExecutionProperties;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
//import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
//import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
//import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//import org.springframework.stereotype.Service;
//
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Optional;
//
//@Service
//@RequiredArgsConstructor
//public class OAuth2UserService extends DefaultOAuth2UserService {
//
//    private final MemberRepository memberRepository;
//
//    // provider 분기 처리 메소드
//    private Map<String, Object> getProviderAccountAttributes(OAuth2User oAuth2User, String registrationId) {
//        Map<String, Object> providerAccount = new HashMap<>();
//
//        switch(registrationId){
//            case "google":
//                providerAccount.put("email", oAuth2User.getAttribute("email"));
//                providerAccount.put("username", oAuth2User.getAttribute("name"));
//                providerAccount.put("identifier", "sub");
//                providerAccount.put("provider", "google");
//                break;
//            case "kakao":
//                Map<String, Object> kakaoAccount = oAuth2User.getAttribute("kakao_account");
//                if(kakaoAccount != null){
//                    providerAccount.put("email", kakaoAccount.get("email"));
//
//                    Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
//                    providerAccount.put("username", profile != null ? profile.get("nickname") : "unknown");
//                    providerAccount.put("identifier", "id");
//                    providerAccount.put("provider", "kakao");
//                }
//                break;
//            default:
//                throw new OAuth2AuthenticationException("Unknown registration id " + registrationId);
//        }
//
//        return providerAccount;
//    }
//
//
//    @Override
//    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
//        // access token 확인용
//        String accessToken = userRequest.getAccessToken().getTokenValue();
//        System.out.println("Access Token :" + accessToken);
//
//
//        String registrationId = userRequest.getClientRegistration().getRegistrationId();
//        OAuth2User oAuth2User = super.loadUser(userRequest);
//
//        // provider에 따라 분기해서 데이터 처리
//        Map<String, Object> providerAccount = getProviderAccountAttributes(oAuth2User, registrationId);
//
//        String email = providerAccount.get("email").toString();
//        String username = providerAccount.get("username").toString();
//        String identifier = providerAccount.get("identifier").toString();
//        String provider = providerAccount.get("provider").toString();
//
//        // 이미 회원이면 회원 객체 반환, 새로운 회원이면 회원 생성 & DB 저장 후 반환
//        Member member = memberRepository.findByEmail(email)
//                .orElseGet(() -> Member.builder()
//                        .username(username)
//                        .email(email)
//                        .provider(provider)
//                        .build());
//        // 새로운 회원은 DB에 저장
//        if(member.getId() == null){
//            memberRepository.save(member);
//        }
//
//        return new DefaultOAuth2User(
//                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
//                oAuth2User.getAttributes(),
//                identifier);
//
//    }
//}
