package com.trashhcan.letter.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LetterCreateDto {
    private Long letterbox_id;
    private String content;
    private String letterimage_url;
    private String trashimage_url;
    private String letter_theme;
}
