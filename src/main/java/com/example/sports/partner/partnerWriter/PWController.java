package com.example.sports.partner.partnerWriter;

import com.example.sports.partner.partnerPost.PartnerPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/writer")
public class PWController {
    private final PartnerPostService partnerPostService;
    private final PWService pcService;

//    @PostMapping("/create/{id}")
//    public String create(@PathVariable(value = "id") Long id, @Valid PCForm pcForm, BindingResult bindingResult, Model model, Principal principal) {
//        PartnerPost partnerPost = this.partnerPostService.getPartnerPost(id);
//
//        if(bindingResult.hasErrors()) {
//            return String.format("redirect:/partner/")
//        }
//    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable(value = "id") Long id, Model model, Principal principal) {
        PartnerWriter partnerWriter = this.pcService.getPartnerWriter(id);

        if(!partnerWriter.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }

        this.pcService.delete(partnerWriter);

        return String.format("redirect:/partner/detail/%d", partnerWriter.getPartnerPost().getId());
    }

    @GetMapping("/modify/{id}")
    public String modify(@PathVariable(value = "id") Long id, Model model, Principal principal, PWForm pcForm) {
        PartnerWriter partnerWriter = this.pcService.getPartnerWriter(id);
        model.addAttribute("partnerWriter", partnerWriter);

        if(!partnerWriter.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"수정 권한이 없습니다.");
        }

        return "partner_modify";

    }
//
//    @PostMapping("/modify/{id}")



}
