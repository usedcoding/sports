package com.example.sports.partner.offer;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OfferForm {

    @NotEmpty(message = "내용은 필수 사항 입니다.")
    private String content;
}
