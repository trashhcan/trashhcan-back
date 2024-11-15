package com.trashhcan.letter.dto.response;

import com.trashhcan.letter.domain.LetterBox;
import com.trashhcan.letter.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LetterResponseDto {
        String content;
        Long id;
        Long member_id;
        String user_name;
        Long letterBox_id;
        String trashimage_url;
        String letterimage_url;
        String letter_theme;
}
