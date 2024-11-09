package com.trashhcan.letter.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LetterBox extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "letterBox_id")
    private Long id;

    @JoinColumn(name="owner_id")
    @OneToOne(fetch= FetchType.LAZY)
    private Member member;

}
