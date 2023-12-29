package com.example.sports.partner.partnerApplicant;

import com.example.sports.member.Member;
import com.example.sports.member.UserService;
import com.example.sports.partner.partnerPost.PartnerPost;
import com.example.sports.partner.partnerPost.PartnerPostService;
import com.example.sports.post.entity.Post;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/applicant")
public class PAController {
    private final PAService paService;
    private final PartnerPostService partnerPostService;
    private final UserService userService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create/{id}")
    public String create(@PathVariable(value = "id") Long id, @Valid PAForm paForm, BindingResult bindingResult, Model model, Principal principal) {
        PartnerPost partnerPost = this.partnerPostService.getPartnerPost(id);
        Member member = this.userService.getMember(principal.getName());

        if (bindingResult.hasErrors()) {
            return "partner_detail";
        }

        this.paService.create(partnerPost, paForm.getContent(), member);

        return String.format("redirect:/partner/detail/%d", id);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable(value = "id") Long id, Model model, Principal principal) {
        PartnerApplicant partnerApplicant = this.paService.getPartnerApplicant(id);
        model.addAttribute("partnerApplicant", partnerApplicant);

        if (!partnerApplicant.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }

        this.paService.delete(partnerApplicant);
        return String.format("redirect:/partner/detail/%d", partnerApplicant.getPartnerPost().getId());
    }
}
