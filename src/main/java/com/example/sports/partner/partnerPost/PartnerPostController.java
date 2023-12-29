package com.example.sports.partner.partnerPost;

import com.example.sports.member.Member;
import com.example.sports.member.UserService;
import com.example.sports.partner.partnerApplicant.PAForm;
import com.example.sports.partner.partnerApplicant.PAService;
import com.example.sports.partner.partnerApplicant.PartnerApplicant;
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
@RequestMapping("/partner")
@RequiredArgsConstructor
public class PartnerPostController {
    private final PartnerPostService partnerPostService;
    private final UserService userService;
    private final PAService paService;

    @GetMapping("/list")
    public String getList(Model model, @RequestParam(value = "keyword", defaultValue = "") String keyword) {
        List<PartnerPost> partnerPost = this.partnerPostService.getList(keyword);
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
            return "partner_form";
        }
        Member member = this.userService.getMember(principal.getName());
        this.partnerPostService.create(partnerPostForm.getTitle(), partnerPostForm.getContent(), member);

        return "redirect:/partner/list";
    }

    //비회원도 볼 수 있게 해야 한다.
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable(value = "id") long id, PAForm PAForm) {
        PartnerPost partnerPost = this.partnerPostService.getPartnerPost(id);
        model.addAttribute("partnerPost", partnerPost);

        List<PartnerApplicant> partnerApplicant = this.paService.getList();
        model.addAttribute("PAList", partnerApplicant);

        model.addAttribute("PAForm", PAForm);

        return "partner_detail";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/detail/{id}")
    public String detail(@PathVariable(value = "id") Long id, @Valid PAForm PAForm, BindingResult bindingResult, Model model, Principal principal) {
        PartnerPost partnerPost = this.partnerPostService.getPartnerPost(id);
        Member member = this.userService.getMember(principal.getName());


        if (bindingResult.hasErrors()) {
            model.addAttribute("partnerPost", partnerPost);

            return "partner_detail";
        }

        this.paService.create(partnerPost, PAForm.getContent(), member);
        return String.format("redirect:/partner/detail/%d", id);
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

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String modify(@PathVariable(value = "id") Long id, PartnerPostForm partnerPostForm, Model model, Principal principal) {
        PartnerPost partnerPost = this.partnerPostService.getPartnerPost(id);
        model.addAttribute("partnerPost", partnerPost);

        if (!partnerPost.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
        }

        return "partner_modify";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String modify(@PathVariable(value = "id") Long id, @Valid PartnerPostForm partnerPostForm, BindingResult bindingResult, Model model, Principal principal) {
        PartnerPost partnerPost = this.partnerPostService.getPartnerPost(id);
        model.addAttribute("partnerPost", partnerPost);

        if(bindingResult.hasErrors()) {
            return "partner_modify";
        }

        if (!partnerPost.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
        }

        this.partnerPostService.modify(partnerPost, partnerPostForm.getTitle(), partnerPostForm.getContent());

        return String.format("redirect:/partner/detail/%d", id);
    }
}
