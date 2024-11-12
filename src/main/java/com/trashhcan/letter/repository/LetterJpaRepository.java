package com.trashhcan.letter.repository;

import com.trashhcan.letter.domain.Letter;
import com.trashhcan.letter.domain.LetterBox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LetterJpaRepository extends JpaRepository<Letter, Long> {
    //LetterBox 로 Letter 찾아 최신순 정렬 반환
    List<Letter> findByLetterBoxOrderByCreatedAtDesc(LetterBox letterBox);
}
