package com.example.sports.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUsername(String username);

//    Optional<Member> findByNameAndPhone(String username, String phoneNum);
}
