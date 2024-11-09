package com.trashhcan.letter.repository;

import com.trashhcan.letter.domain.Letter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LetterJpaRepository extends JpaRepository<Letter, Long> {
    List<Letter> findAllByOrderByIdAsc();
}
