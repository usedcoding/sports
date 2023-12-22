package com.example.sports.comment;

import com.example.sports.member.Member;
import com.example.sports.post.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    public Comment GetComment(Long id) {
        Optional<Comment> comment = this.commentRepository.findById(id);
        return comment.get();
    }

    public void create(Post post, String content, Member author) {
        Comment comment = new Comment();
        comment.setPost(post);
        comment.setContent(content);
        comment.setAuthor(author);
        comment.setCreateDate(LocalDateTime.now());

        this.commentRepository.save(comment);
    }

    public void delete(Comment comment) {
        this.commentRepository.delete(comment);
    }

    public void modify(Comment comment, String content) {
        comment.setContent(content);
        comment.setModifyDate(LocalDateTime.now());

        this.commentRepository.save(comment);
    }
}
