package com.trashhcan.letter.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LetterResponseDto {
        String content;
        Long id;
        Long sender_id;
        String user_name;
        Long this_letterBox_id;
        String sender_box_name;
        String trashimage_url;
        String letterimage_url;
        String letter_theme;
}
