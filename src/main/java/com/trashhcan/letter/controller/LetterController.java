package com.trashhcan.letter.controller;

import com.trashhcan.letter.dto.response.ImageResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/letter")
public class LetterController {
    @GetMapping("/image")
    public ResponseEntity<ImageResponseDto> getLetterBackgroundImages(){
        String url = "trashhcan.s3.ap-northeast-2.amazonaws.com/";
        List<String> images = List.of("bg1.jpg", "bg2.jpg", "bg3.jpg", "bg4.jpg");
        images = images.stream()
                .map(item -> url + item)
                .collect(Collectors.toList());
        return ResponseEntity.ok(new ImageResponseDto("letter_background", images));
    }
}