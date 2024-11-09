package com.trashhcan.letter.domain;

import jakarta.persistence.*;
import lombok.*;

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
    private String image_url; //우선 이미지 url String으로 처리.

    @JoinColumn(name="sender_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @JoinColumn(name="letterBox_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private LetterBox letterBox;
}
