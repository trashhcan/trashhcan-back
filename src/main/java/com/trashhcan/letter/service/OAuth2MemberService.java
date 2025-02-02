package com.trashhcan.letter.service;

import com.trashhcan.letter.config.jwt.JwtTokenProvider;
import com.trashhcan.letter.domain.LetterBox;
import com.trashhcan.letter.domain.Member;
import com.trashhcan.letter.dto.response.MemberResponseDto;
import com.trashhcan.letter.dto.response.OAuthAccountProfile;
import com.trashhcan.letter.dto.response.TokenResponse;
import com.trashhcan.letter.repository.LetterBoxJpaRepository;
import com.trashhcan.letter.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OAuth2MemberService {
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final LetterBoxJpaRepository letterBoxJpaRepository;

    public MemberResponseDto signUpOrIn(OAuthAccountProfile oAuthAccountProfile, String provider){
        // 이미 회원이면 회원 객체 반환, 새로운 회원이면 회원 생성 & DB 저장 후 반환
        Member member = memberRepository.findByEmail(oAuthAccountProfile.getEmail())
                .orElseGet(() -> Member.builder()
                        .username(oAuthAccountProfile.getName())
                        .email(oAuthAccountProfile.getEmail())
                        .provider(provider)
                        .build());

        // 새로운 회원은 DB에 저장
        if(member.getId() == null){
            memberRepository.save(member);
        }

        TokenResponse token = TokenResponse.of(
                jwtTokenProvider.generateAccessToken(member.getId()),
                jwtTokenProvider.generateRefreshToken(member.getId())
        );

        Optional<LetterBox> letterBox=letterBoxJpaRepository.findByMemberId(member.getId());


        return new MemberResponseDto(
                member.getId(),
                member.getUsername(),
                member.getEmail(),
                letterBox.map(LetterBox::getBox_name).orElse(null),
                token);
    }
}
