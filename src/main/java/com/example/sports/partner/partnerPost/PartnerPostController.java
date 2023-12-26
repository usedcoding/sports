package com.example.sports.partner.partnerPost;

import com.example.sports.member.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/partner")
@RequiredArgsConstructor
public class PartnerPostController {
    private final PartnerPostService partnerPostService;

    @GetMapping("/list")
    public String getList(Model model) {
       List<PartnerPost> partnerPost = this.partnerPostService.getList();
        model.addAttribute("partnerPostList", partnerPost);

        return"partner_list";
    }

    @GetMapping("/create")
    public String create(PartnerPostForm partnerPostForm) {
        return "partner_form";
    }

    @PostMapping("/create")
    public String create(@Valid PartnerPostForm partnerPostForm, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "redirect:/partner/list";
        }

        this.partnerPostService.create(partnerPostForm.getTitle(), partnerPostForm.getContent());

        return "redirect:/partner/list";
    }

}
