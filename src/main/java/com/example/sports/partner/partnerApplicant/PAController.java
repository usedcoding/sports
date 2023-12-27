package com.example.sports.partner.partnerApplicant;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/applicant")
public class PAController {

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable(value = "id")Long id, Model model) {

    }
}
