package com.trashhcan.letter.controller;

import com.trashhcan.letter.dto.request.LetterBoxCreateDto;
import com.trashhcan.letter.dto.response.ImageResponseDto;
import com.trashhcan.letter.dto.response.LetterBoxResponseDto;
import com.trashhcan.letter.service.LetterBoxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/letterbox")
public class LetterBoxController {
    @Autowired
    private LetterBoxService letterboxService;

    //레터박스 생성. 나중에 이미 letterbox 가 존재하는 member 면 letterbox 편지 조회 링크로 리디렉션 하는것도 만들어야할듯
    @PostMapping("")
    public ResponseEntity<Long> createLetterBox(@RequestBody LetterBoxCreateDto requestDto){
        Long letterBoxId=letterboxService.createLetterBox(requestDto);
        return new ResponseEntity<>(letterBoxId, HttpStatus.CREATED);
    }

    //레터박스 아이디로 찾는경우
    @GetMapping("/box/{letterbox_id}")
    public ResponseEntity <LetterBoxResponseDto> getLetterBox(@PathVariable("letterbox_id") Long letterBoxId){
        LetterBoxResponseDto letterBox = letterboxService.findLetterBoxByBoxId(letterBoxId);
        if (letterBox == null) { //No Content 204 반환
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(letterBox);
    }

    //멤버 아이디로 찾는경우
    @GetMapping("/{member_id}")
    public ResponseEntity <LetterBoxResponseDto> getMemberLetterBox(@PathVariable("member_id") Long memberId){
        LetterBoxResponseDto letterBox = letterboxService.findLetterBoxByMemberId(memberId);
        if (letterBox == null) { //No Content 204 반환
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(letterBox);
    }

    @GetMapping("/image")
    public ResponseEntity <ImageResponseDto> getLetterIcons(){
        String url = "trashhcan.s3.ap-northeast-2.amazonaws.com/";
        List<String> images = List.of("trash1.jpg", "trash2.jpg", "trash3.jpg");
        images = images.stream()
                .map(item -> url + item)
                .collect(Collectors.toList());
        return ResponseEntity.ok(new ImageResponseDto("letter_icon", images));
    }
}
