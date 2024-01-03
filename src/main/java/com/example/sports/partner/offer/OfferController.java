package com.example.sports.partner.offer;

import com.example.sports.member.Member;
import com.example.sports.member.UserService;
import com.example.sports.partner.partnerPost.PartnerPost;
import com.example.sports.partner.partnerPost.PartnerPostService;
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

@Controller
@RequiredArgsConstructor
@RequestMapping("/offer")
public class OfferController {
    private final OfferService offerService;
    private final PartnerPostService partnerPostService;
    private final UserService userService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create/{id}")
    public String create(@PathVariable(value = "id") Long id, @Valid OfferForm offerForm, BindingResult bindingResult, Model model, Principal principal) {

        PartnerPost partnerPost = this.partnerPostService.getPartnerPost(id);
        Member member = this.userService.getMember(principal.getName());

        if (bindingResult.hasErrors()) {
            return "partner_detail";
        }

        this.offerService.create(partnerPost, offerForm.getContent(), member);

        return String.format("redirect:/partner/detail/%d", id);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable(value = "id") Long id, Model model, Principal principal) {
        Offer offer = this.offerService.getOffer(id);
        model.addAttribute("offer", offer);

        if (!offer.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }

        this.offerService.delete(offer);
        return String.format("redirect:/partner/detail/%d", offer.getPartnerPost().getId());
    }
}
