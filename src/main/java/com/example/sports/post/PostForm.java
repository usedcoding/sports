package com.example.sports.post;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostForm {

    @NotEmpty(message = "제목은 필수 사항 입니다.")
    private String title;

    @NotEmpty(message = "내용은 필수 사항 입니다.")
    private String content;
}
