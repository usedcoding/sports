package com.example.sports.gym;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GymForm {

    @NotEmpty(message = "이름을 입력해 주세요.")
    private String title;

    @NotEmpty(message = "주소를 입력해 주세요.")
    private String address;

    @NotEmpty(message = "운영 시간을 입력해 주세요.")
    private String time;

    @NotEmpty(message = "일일권 가격을 입력해 주세요.")
    private String price;

    @NotEmpty(message = "전화번호를 입력해 주세요.")
    private String phoneNum;

    @NotEmpty(message = "사진은 필수 사항입니다.")
    private String thumbnailImg;
}
