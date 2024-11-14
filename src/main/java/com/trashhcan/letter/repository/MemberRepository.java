package com.trashhcan.letter.repository;

import com.trashhcan.letter.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);

    Optional<Member> findById(Long id);
    Member findByUsername(String username);
}
