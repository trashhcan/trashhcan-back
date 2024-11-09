package com.trashhcan.letter.service;

import com.trashhcan.letter.domain.Member;
import com.trashhcan.letter.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.task.TaskExecutionProperties;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class OAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // access token 확인용
        //String accessToken = userRequest.getAccessToken().getTokenValue();
        //System.out.println("Access Token :" + accessToken);

        // google에서 oauth2user 객체 받아오기
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String username = oAuth2User.getAttribute("name");
        String email = oAuth2User.getAttribute("email");

        // 이미 회원이면 회원 객체 반환, 새로운 회원이면 회원 생성 & DB 저장 후 반환
        Member member = memberRepository.findByEmail(email)
                .orElseGet(() -> Member.builder()
                        .username(username)
                        .email(email)
                        .build());
        // 새로운 회원은 DB에 저장
        if(member.getId() == null){
            memberRepository.save(member);
        }

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
                oAuth2User.getAttributes(),
                "sub");

    }
}
