package com.trashhcan.letter.service;

import com.trashhcan.letter.domain.Letter;
import com.trashhcan.letter.domain.LetterBox;
import com.trashhcan.letter.domain.Member;
import com.trashhcan.letter.dto.request.LetterBoxCreateDto;
import com.trashhcan.letter.dto.request.LetterCreateDto;
import com.trashhcan.letter.dto.response.LetterBoxResponseDto;
import com.trashhcan.letter.repository.LetterBoxJpaRepository;
import com.trashhcan.letter.repository.LetterJpaRepository;
import com.trashhcan.letter.repository.MemberRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class LetterBoxService {

    @Autowired
    private LetterBoxJpaRepository letterBoxJpaRepository;
    @Autowired
    private MemberRepository memberRepository;

    //레터박스 생성~
    @Transactional
    public Long createLetterBox(LetterBoxCreateDto requestDto) {
        Member member = memberRepository.findById(requestDto.getMember_id())
                .orElseThrow(() -> new RuntimeException("해당 아이디를 가진 회원이 존재하지 않습니다."));
        LetterBox letterBox = LetterBox.builder()
                .box_name(requestDto.getBox_name())
                .member(member)
                .letters(new ArrayList<>())
                .build();
        letterBoxJpaRepository.save(letterBox);

        return letterBox.getId();
    }

    //회원의 아이디로 레터박스 찾기
    @Transactional
    public LetterBoxResponseDto findLetterBoxByMemberId(Long memberId) {
        LetterBox letterBox = letterBoxJpaRepository.findByMemberId(memberId)
                .orElseThrow(() -> new RuntimeException("해당 회원의 레터박스를 찾을 수 없습니다."));
        return new LetterBoxResponseDto(letterBox.getId(),letterBox.getBox_name(), letterBox.getMember().getId(), letterBox.getLetters());
    }

    //레터박스 아이디로 찾기  (걍 혹시몰라서 둘다 해둠...)
    @Transactional
    public LetterBoxResponseDto findLetterBoxByBoxId(Long letterBoxId) {
        LetterBox letterBox = letterBoxJpaRepository.findLetterBoxById(letterBoxId)
                .orElseThrow(() -> new RuntimeException("해당 레터박스를 찾을 수 없습니다."));
        return new LetterBoxResponseDto(letterBox.getId(),letterBox.getBox_name(), letterBox.getMember().getId(),letterBox.getLetters());
    }
}
