package com.trashhcan.letter.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@AllArgsConstructor
public class LetterListResponseDto {
    private List<LetterResponseDto> letters;
}
