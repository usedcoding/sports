package com.example.sports.post.controller;

import com.example.sports.post.entity.Post;
import com.example.sports.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {
    private final PostService postService;

    @GetMapping("/list")
    public String list(Model model) {
        List<Post> post = this.postService.getList();
        model.addAttribute("postList", post);
        return"post_list";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable(value = "id")Long id, Model model) {
       Post post = this.postService.getPost(id);
       model.addAttribute("post", post);
        return"post_detail";
    }

}
