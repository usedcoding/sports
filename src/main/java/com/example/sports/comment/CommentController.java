package com.example.sports.comment;

import com.example.sports.member.Member;
import com.example.sports.member.UserService;
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

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;
    private final PostService postService;
    private final UserService userService;


    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create/{id}")
    public String create(@PathVariable(value = "id") Long id, @Valid CommentForm commentForm, BindingResult bindingResult, Model model, Principal principal) {
        Post post = this.postService.getPost(id);
        Member member = this.userService.getMember(principal.getName());

        if (bindingResult.hasErrors()) {
            return String.format("redirect:/post/detail/%d", id);
        }
        this.commentService.create(post, commentForm.getContent(), member);
        return String.format("redirect:/post/detail/%d", id);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable(value = "id") Long id, Model model, Principal principal) {
        Comment comment = this.commentService.GetComment(id);

        if (!comment.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        this.commentService.delete(comment);
        return String.format("redirect:/post/detail/%d", comment.getPost().getId());
    }

    @GetMapping("/modify/{id}")
    public String modify(@PathVariable(value = "id") Long id, Model model, Principal principal, CommentForm commentForm) {
        Comment comment = this.commentService.GetComment(id);
        model.addAttribute("comment", comment);

        if (!comment.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
        }
        return "comment_modify";

    }

    @PostMapping("/modify/{id}")
    public String modify(@PathVariable(value = "id") Long id, Model model, Principal principal, @Valid CommentForm commentForm, BindingResult bindingResult) {
        Comment comment = this.commentService.GetComment(id);
        model.addAttribute("comment", comment);
        this.commentService.modify(comment, commentForm.getContent());

        if (bindingResult.hasErrors()) {
            return String.format("redirect:/post/detail/%d", comment.getPost().getId());
        }

        if (!comment.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
        }
        return String.format("redirect:/post/detail/%d", comment.getPost().getId());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/like/{id}")
    public String like(@PathVariable(value = "id") Long id, Principal principal) {
        Comment comment = this.commentService.GetComment(id);
        Member member = this.userService.getMember(principal.getName());
        if (!comment.getLike().contains(member)) {
            commentService.like(comment, member);
        } else if (comment.getLike().contains(member)) {
            commentService.disLike(comment, member);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "권한이 없습니다.");
        }
        return String.format("redirect:/post/detail/%d", comment.getPost().getId());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/unLike/{id}")
    public String unLike(@PathVariable(value = "id") Long id, Principal principal) {
        Comment comment = this.commentService.GetComment(id);
        Member member = this.userService.getMember(principal.getName());
        if (!comment.getUnLike().contains(member)) {
            commentService.unLike(comment, member);
        } else if (comment.getUnLike().contains(member)) {
            commentService.disUnLike(comment, member);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "권한이 없습니다.");
        }
        return String.format("redirect:/post/detail/%d", comment.getPost().getId());
    }


}
