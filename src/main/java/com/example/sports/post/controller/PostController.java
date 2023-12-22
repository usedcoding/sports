package com.example.sports.post.controller;

import com.example.sports.comment.Comment;
import com.example.sports.comment.CommentForm;
import com.example.sports.comment.CommentService;
import com.example.sports.member.Member;
import com.example.sports.member.UserService;
import com.example.sports.post.PostForm;
import com.example.sports.post.entity.Post;
import com.example.sports.post.service.PostService;
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
@RequestMapping("/post")
public class PostController {
    private final PostService postService;

    private final UserService userService;

    private final CommentService commentService;

    @GetMapping("/list")
    public String list(Model model, @RequestParam(value = "keyword", defaultValue = "")String keyword) {
        List<Post> post = this.postService.getList(keyword);
        model.addAttribute("postList", post);
        return "post_list";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable(value = "id") Long id, Model model, CommentForm commentForm) {
        Post post = this.postService.getPost(id);
        model.addAttribute("post", post);
        return "post_detail";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/detail/{id}")
    public String detail( @PathVariable(value = "id") Long id, @Valid CommentForm commentForm, BindingResult bindingResult, Model model, Principal principal) {
        Post post = this.postService.getPost(id);
        Member member = this.userService.getMember(principal.getName());

        if(bindingResult.hasErrors()) {
            return String.format("redirect:/post/detail/%d", id);
        }
        this.commentService.create(post, commentForm.getContent(), member);
        return String.format("redirect:/post/detail/%d", id);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String create(PostForm postForm) {
        return "post_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String create(@Valid PostForm postForm, BindingResult bindingResult, Principal principal) {

        if (bindingResult.hasErrors()) {
            return "post_form";
        }

        Member member = this.userService.getMember(principal.getName());

        this.postService.create(postForm.getTitle(), postForm.getContent(),member);
        return "redirect:/post/list";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable(value = "id")Long id, Model model, Principal principal) {
        Post post = this.postService.getPost(id);
        model.addAttribute("post", post);

        if(!post.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        this.postService.delete(post);
        return "redirect:/post/list";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String modify(@PathVariable(value = "id")Long id, PostForm postForm, Model model, Principal principal) {
        Post post = this.postService.getPost(id);
        model.addAttribute("post", post);

        if(!post.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }

        return"post_modify";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String modify(@PathVariable(value = "id")Long id, @Valid PostForm postForm, BindingResult bindingResult, Model model, Principal principal) {
        Post post = this.postService.getPost(id);
        model.addAttribute("post", post);

        if (bindingResult.hasErrors()) {
            return"post_modify";
        }

        if(!post.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }

        this.postService.modify(post, postForm.getTitle(), postForm.getContent());

        return String.format("redirect:/post/detail/%d",id);
    }


}
