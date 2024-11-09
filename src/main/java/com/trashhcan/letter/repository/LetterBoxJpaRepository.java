package com.trashhcan.letter.repository;

import com.trashhcan.letter.domain.Letter;
import com.trashhcan.letter.domain.LetterBox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LetterBoxJpaRepository extends JpaRepository<LetterBox, Long> {
    List<LetterBox> findByLetter(Letter letter); //나중에 내가 작성한 LetterBox 리스트 호출 기능에서 사용가능
}
