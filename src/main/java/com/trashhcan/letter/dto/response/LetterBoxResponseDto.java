package com.trashhcan.letter.dto.response;

import com.trashhcan.letter.domain.Letter;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class LetterBoxResponseDto {
    private Long id;
    private String box_name;
    private Long member_id;
    private List<TrashletterResponseDto> letters;

}
