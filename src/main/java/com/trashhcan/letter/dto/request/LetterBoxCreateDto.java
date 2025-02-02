package com.trashhcan.letter.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class LetterBoxCreateDto {
        private Long member_id;
        private String box_name;
}
