package com.trashhcan.letter.repository;

import com.trashhcan.letter.domain.Letter;
import com.trashhcan.letter.domain.LetterBox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LetterBoxJpaRepository extends JpaRepository<LetterBox, Long> {
    List<LetterBox> findByLettersContaining(Letter letter); //내가 쓴 편지가 포함되어 있는 LetterBox 목록
}
