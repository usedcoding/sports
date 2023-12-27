package com.example.sports.partner.partnerPost;

import com.example.sports.member.Member;
import com.example.sports.member.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/partner")
@RequiredArgsConstructor
public class PartnerPostController {
    private final PartnerPostService partnerPostService;
    private final UserService userService;

    @GetMapping("/list")
    public String getList(Model model) {
        List<PartnerPost> partnerPost = this.partnerPostService.getList();
        model.addAttribute("partnerPostList", partnerPost);

        return "partner_list";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String create(PartnerPostForm partnerPostForm) {
        return "partner_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String create(@Valid PartnerPostForm partnerPostForm, BindingResult bindingResult, Principal principal) {

        if (bindingResult.hasErrors()) {
            return "redirect:/partner/list";
        }
        Member member = this.userService.getMember(principal.getName());
        this.partnerPostService.create(partnerPostForm.getTitle(), partnerPostForm.getContent(), member);

        return "redirect:/partner/list";
    }

    //비회원도 볼 수 있게 해야 한다.
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/detail/{id}")
    public String detail(@PathVariable(value = "id") long id, Model model, Principal principal) {
        PartnerPost partnerPost = this.partnerPostService.getPartnerPost(id);
        model.addAttribute("partnerPost", partnerPost);
        if (partnerPost.getAuthor().getUsername().equals(principal.getName())) {
            return "partner_detail";
        } else {
            return "partner_view";
        }
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable(value = "id") Long id, Model model, Principal principal) {
        PartnerPost partnerPost = this.partnerPostService.getPartnerPost(id);
        model.addAttribute("partnerPost", partnerPost);

        if (!partnerPost.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제 권한이 없습니다.");
        }

        this.partnerPostService.delete(partnerPost);
        return "redirect:/partner/list";
    }

    @GetMapping("/modify/{id}")
    public String modify(@PathVariable(value = "id") Long id, PartnerPostForm partnerPostForm, Model model, Principal principal) {
        PartnerPost partnerPost = this.partnerPostService.getPartnerPost(id);
        model.addAttribute("partnerPost", partnerPost);

        if (!partnerPost.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
        }

        return "partner_modify";
    }

    @PostMapping("/modify/{id}")
    public String modify(@PathVariable(value = "id") Long id, @Valid PartnerPostForm partnerPostForm, BindingResult bindingResult, Model model, Principal principal) {
        PartnerPost partnerPost = this.partnerPostService.getPartnerPost(id);
        model.addAttribute("partnerPost", partnerPost);

        if (!partnerPost.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
        }

        this.partnerPostService.modify(partnerPost, partnerPostForm.getTitle(), partnerPostForm.getContent());

        return String.format("redirect:/partner/detail/%d", id);
    }
}
