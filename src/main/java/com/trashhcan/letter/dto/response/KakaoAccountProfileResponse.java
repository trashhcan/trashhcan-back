package com.trashhcan.letter.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class KakaoAccountProfileResponse implements OAuthAccountProfile {
    private KakaoAccount kakao_account;

    @Getter
    @NoArgsConstructor
    public static class KakaoAccount {
        private String email;
        private Profile profile;
    }

    @Getter
    @NoArgsConstructor
    public static class Profile {
        private String nickname;
    }

    @Override
    public String getEmail(){
        return kakao_account.getEmail();
    }

    @Override
    public String getName(){
        return kakao_account.getProfile().getNickname();
    }
}
