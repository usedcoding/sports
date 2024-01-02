package com.example.sports.gym;

import com.example.sports.post.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/gym")
public class GymController {

    private final GymService gymService;

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


}
