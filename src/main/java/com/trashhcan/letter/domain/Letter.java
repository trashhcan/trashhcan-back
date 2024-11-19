package com.trashhcan.letter.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Letter extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "letter_id")
    private Long id;
    private String content; //null 가능한가요?

    @Column(nullable = true)
    private String trashimage_url; //이미지 url 로 처리. 나중에 private 떼야 할수도

    @Column(nullable = true)
    private String letterimage_url;

    @Column(nullable = true)
    private String letter_theme;

    @JoinColumn(name="sender_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @JoinColumn(name="letterBox_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private LetterBox letterBox;

    @Builder
    public Letter(String content, Member member, LetterBox letterBox, String trashimage_url, String letterimage_url, String letter_theme) {
        this.content = content;
        this.member = member;
        this.letterBox = letterBox;
        this.trashimage_url = trashimage_url;
        this.letterimage_url = letterimage_url;
        this.letter_theme= letter_theme;
    }
}
