package com.trashhcan.letter.service;

import com.trashhcan.letter.domain.Letter;
import com.trashhcan.letter.domain.LetterBox;
import com.trashhcan.letter.domain.Member;
import com.trashhcan.letter.dto.request.LetterCreateDto;
import com.trashhcan.letter.dto.response.LetterResponseDto;
import com.trashhcan.letter.repository.LetterBoxJpaRepository;
import com.trashhcan.letter.repository.LetterJpaRepository;
import com.trashhcan.letter.repository.MemberRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LetterService {

    @Autowired
    private LetterJpaRepository letterJpaRepository;
    @Autowired
    private LetterBoxJpaRepository letterBoxJpaRepository;
    @Autowired
    private MemberRepository memberRepository;

    @Transactional
    public Long createLetter(LetterCreateDto requestDto, Long MemberId) {
        Member member=memberRepository.findById(MemberId)
                .orElseThrow(() -> new RuntimeException("해당 아이디를 가진 회원이 존재하지 않습니다."));
        LetterBox letterBox=letterBoxJpaRepository.findLetterBoxById(requestDto.getLetterbox_id())
                .orElseThrow(()->new RuntimeException("해당 회원은 쓰레기통이 없습니다."));

        Letter letter = Letter.builder()
                .content(requestDto.getContent())
                .member(member)
                .letterimage_url(requestDto.getLetterimage_url())
                .trashimage_url(requestDto.getTrashimage_url())
                .letterBox(letterBox)
                .letter_theme(requestDto.getLetter_theme())
                .build();

        letterJpaRepository.save(letter);

        return letter.getId();
    }

    @Transactional
    public LetterResponseDto findLetterByLetterId (Long letterId){
        Letter letter =letterJpaRepository.findById(letterId)
                .orElseThrow(()->new RuntimeException("해당 편지가 없습니다"));
        return new LetterResponseDto(
                letter.getContent(),
                letter.getId(),
                letter.getMember().getId(),
                letter.getMember().getUsername(),
                letter.getLetterBox().getId(),
                letter.getLetterBox().getBox_name(),
                letter.getTrashimage_url(),
                letter.getLetterimage_url(),
                letter.getLetter_theme()
        );
    }
}
