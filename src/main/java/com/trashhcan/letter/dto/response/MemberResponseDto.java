package com.trashhcan.letter.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberResponseDto {

    private Long id;
    private String username;
    private String email;
    private String box_name;

    // jwt 토큰
    private TokenResponse token;
}

