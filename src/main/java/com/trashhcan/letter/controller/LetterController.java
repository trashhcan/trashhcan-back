package com.trashhcan.letter.controller;

import com.trashhcan.letter.dto.request.LetterBoxCreateDto;
import com.trashhcan.letter.dto.request.LetterCreateDto;
import com.trashhcan.letter.dto.response.ImageResponseDto;
import com.trashhcan.letter.dto.response.LetterBoxResponseDto;
import com.trashhcan.letter.dto.response.LetterListResponseDto;
import com.trashhcan.letter.dto.response.LetterResponseDto;
import com.trashhcan.letter.service.LetterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/letter")
public class LetterController {

    private final LetterService letterService;

    public LetterController(LetterService letterService) {
        this.letterService = letterService;
    }

    @GetMapping("/image")
    public ResponseEntity<ImageResponseDto> getLetterBackgroundImages() {
        String url = "trashhcan.s3.ap-northeast-2.amazonaws.com/";
        List<String> images = List.of("bg1.png", "bg2.png", "bg3.png", "bg4.png", "bg5.png", "bg6.png", "bg7.png");
        images = images.stream()
                .map(item -> url + item)
                .collect(Collectors.toList());
        return ResponseEntity.ok(new ImageResponseDto("letter_background", images));
    }

    @PostMapping("/{sender_id}")
    public ResponseEntity<Long> createLetter(
            @PathVariable("sender_id") Long memberId,
            @RequestBody LetterCreateDto requestDto)
    {
        Long letterId=letterService.createLetter(requestDto,memberId);
        return new ResponseEntity<>(letterId, HttpStatus.CREATED);
    }

    @GetMapping("/{letter_id}")
    public ResponseEntity<LetterResponseDto> getLetter(
            @PathVariable("letter_id")
            Long letterId
    ) {
        LetterResponseDto letter = letterService.findLetterByLetterId(letterId);
        if (letter == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(letter);
    }

}
