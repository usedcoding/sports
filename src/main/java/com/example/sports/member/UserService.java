package com.example.sports.member;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void create(String email, String nickname, String password) {
        Member member = new Member();
        member.setEmail(email);
        member.setNickname(nickname);
        member.setPassword(passwordEncoder.encode(password));

        this.userRepository.save(member);
    }
}
