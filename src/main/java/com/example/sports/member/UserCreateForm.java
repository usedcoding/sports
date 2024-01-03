package com.example.sports.member;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateForm {

    @NotEmpty(message = "닉네임은 필수 사항 입니다.")
    private String nickname;

    @NotEmpty(message = "비밀번호는 필수 사항 입니다.")
    private String password1;

    @NotEmpty(message = "비밀번호를 확인해 주세요.")
    private String password2;

    @NotEmpty(message = "이메일은 필수 사항 입니다.")
    @Email
    private String username;

    private String phoneNum1;
    private String phoneNum2;
}
