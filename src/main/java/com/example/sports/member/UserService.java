package com.example.sports.member;

import com.example.sports.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void create(String username, String nickname, String password) {
        Member member = new Member();
        member.setUsername(username);
        member.setNickname(nickname);
        member.setPassword(passwordEncoder.encode(password));

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

//    public ResponseEntity<?> sendSmsToFindEmail(FindEmailRequestDto requestDto) {
//        String name = requestDto.getName();
//        //수신번호 형태에 맞춰 "-"을 ""로 변환
//        String phoneNum = requestDto.getPhoneNum().replaceAll("-","");
//
//        Member foundUser = userRepository.findByNameAndPhone(name, phoneNum).orElseThrow(()->
//                new NoSuchElementException("회원이 존재하지 않습니다."));
//
//        String receiverEmail = foundUser.getUsername();
//        String verificationCode = validationUtil.createCode();
//        smsUtil.sendOne(phoneNum, verificationCode);
//
//        //인증코드 유효기간 5분 설정
//        redisUtil.setDataExpire(verificationCode, receiverEmail, 60 * 5L);
//
//        return ResponseEntity.ok(new Message("SMS 전송 성공"));
//    }


}
