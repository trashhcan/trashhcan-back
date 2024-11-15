package com.trashhcan.letter.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TrashImage {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long id;

    private String trashimage_url;
}
