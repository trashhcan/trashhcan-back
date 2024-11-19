package com.trashhcan.letter.repository;

import com.trashhcan.letter.domain.Letter;
import com.trashhcan.letter.domain.LetterBox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LetterJpaRepository extends JpaRepository<Letter, Long> {
    //LetterBox 로 Letter 찾아 최신순 정렬 반환
    List<Letter> findByLetterBoxId(Long letterbox_Id); //letterbox의 편지
    List<Letter> findByMemberId(Long memberId);//특정 Member id 가 쓴 편지

    Optional<Letter> findById(Long letterId);
}
