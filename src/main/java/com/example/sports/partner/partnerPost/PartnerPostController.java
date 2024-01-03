package com.example.sports.partner.partnerPost;

import com.example.sports.SmsUtil;
import com.example.sports.member.Member;
import com.example.sports.member.UserService;
import com.example.sports.partner.offer.Offer;
import com.example.sports.partner.offer.OfferForm;
import com.example.sports.partner.offer.OfferService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@Controller
@RequestMapping("/partner")
@RequiredArgsConstructor
public class PartnerPostController {
    private final PartnerPostService partnerPostService;
    private final UserService userService;
    private final OfferService offerService;
    private final SmsUtil smsUtil;


    @GetMapping("/list")
    public String getList(Model model, @RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "keyword", defaultValue = "") String keyword) {
        Page<PartnerPost> paging = this.partnerPostService.getList(page, keyword);
        model.addAttribute("paging", paging);
        model.addAttribute("keyword", keyword);
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
    public String detail(Model model, @PathVariable(value = "id") long id, OfferForm offerForm) {
        PartnerPost partnerPost = this.partnerPostService.getPartnerPost(id);
        model.addAttribute("partnerPost", partnerPost);
        System.out.println(partnerPost);

        model.addAttribute("offerForm", offerForm);

        return "partner_detail";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/detail/{id}")
    public String detail(@PathVariable(value = "id") Long id, @Valid OfferForm offerForm, BindingResult bindingResult, Model model, Principal principal) {
        PartnerPost partnerPost = this.partnerPostService.getPartnerPost(id);
        Member member = this.userService.getMember(principal.getName());


        if (bindingResult.hasErrors()) {
            model.addAttribute("partnerPost", partnerPost);

            return "partner_detail";
        }

        this.offerService.create(partnerPost, offerForm.getContent(), member);
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

        if (bindingResult.hasErrors()) {
            return "partner_modify";
        }

        if (!partnerPost.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
        }

        this.partnerPostService.modify(partnerPost, partnerPostForm.getTitle(), partnerPostForm.getContent());

        return String.format("redirect:/partner/detail/%d", id);
    }

    @GetMapping("/allow/{id}")
    public String allow(@PathVariable("id") Long id, Model model) {
        Offer offer = this.offerService.getOffer(id);
        model.addAttribute("offer", offer);

        String from = offer.getPartnerPost().getAuthor().getPhoneNum();
        String to = offer.getAuthor().getPhoneNum();
                smsUtil.sendOne(from, to);
        return String.format("redirect:/partner/detail/%d", offer.getPartnerPost().getId());
    }
    //view  post 해결 및 코드 수정 필요

}
