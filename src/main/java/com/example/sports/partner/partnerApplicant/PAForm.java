package com.example.sports.partner.partnerApplicant;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PAForm {

    @NotEmpty(message = "내용은 필수 사항 입니다.")
    private String content;
}
