package com.example.sports.gym;

import com.example.sports.member.Member;
import com.example.sports.member.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/gym")
public class GymController {

    private final GymService gymService;
    private final UserService userService;

    @GetMapping("/list")
    public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "keyword", defaultValue = "") String keyword) {
        Page<Gym> paging = this.gymService.getList(page, keyword);
        model.addAttribute("paging", paging);
        model.addAttribute("keyword", keyword);
        return "gym_list";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable(value = "id") Long id, Model model) {
        Gym gym = this.gymService.getGym(id);
        model.addAttribute("gym", gym);
        return "gym_detail";
    }

    @GetMapping("/create")
    public String create(GymForm gymForm) {
        return"gym_form";
    }

    @PostMapping("/create")
    public String create(@Valid GymForm gymForm, BindingResult bindingResult, Principal principal, @RequestParam("thumbnail") MultipartFile thumbnail) {

        if(bindingResult.hasErrors()) {
            return "gym_form";
        }

        Member member = this.userService.getMember(principal.getName());

        this.gymService.create(gymForm.getTitle(), gymForm.getPhoneNum(), gymForm.getTime(), gymForm.getPrice(), gymForm.getAddress(),member,thumbnail);
        return"redirect:/gym/list";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable(value = "id")Long id, Model model, Principal principal) {
        Gym gym = this.gymService.getGym(id);
        model.addAttribute("gym", gym);

        if (!gym.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제 권한이 없습니다.");
        }

        this.gymService.delete(gym);

        return "redirect:/gym/list";
    }

    @GetMapping("/modify/{id}")
    public String modify(@PathVariable(value = "id") Long id,GymForm gymForm, Model model, Principal principal) {
        Gym gym = this.gymService.getGym(id);
        model.addAttribute("gym", gym);

        if (!gym.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
        }

        return "gym_modify";
    }

    @PostMapping("/modify/{id}")
    public String modify(@PathVariable(value = "id") Long id, @Valid GymForm gymForm, BindingResult bindingResult, Model model, Principal principal) {
        Gym gym = this.gymService.getGym(id);
        model.addAttribute("gym", gym);

        if(bindingResult.hasErrors()) {
            return"gym_modify";
        }

        if(!gym.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
        }

        this.gymService.modify(gym, gymForm.getTitle(), gymForm.getPhoneNum(), gymForm.getTime(), gymForm.getPrice(), gymForm.getAddress());

        return String.format("redirect:/gym/detail/%d", gym.getId());
    }

}
