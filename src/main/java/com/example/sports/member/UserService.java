package com.example.sports.member;

import com.example.sports.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void create(String username, String nickname, String password, String phoneNum) {
        Member member = new Member();
        member.setUsername(username);
        member.setNickname(nickname);
        member.setPassword(passwordEncoder.encode(password));
        member.setPhoneNum(phoneNum);

        this.userRepository.save(member);
    }

    public Member getMember(String username) {
        Optional<Member> member = this.userRepository.findByUsername(username);

        if(member.isPresent()) {
            return member.get();
        } else {
            throw new DataNotFoundException("member not found");
        }
    }


}
