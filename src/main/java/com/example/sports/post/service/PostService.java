package com.example.sports.post.service;

import com.example.sports.DataNotFoundException;
import com.example.sports.comment.Comment;
import com.example.sports.member.Member;
import com.example.sports.post.entity.Post;
import com.example.sports.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public Page<Post> getList(int page,  String keyword) {
        Pageable pageable = PageRequest.of(page, 10);
        return this.postRepository.findAllByKeyword(keyword, pageable);
    }

    public Post getPost(Long id) {
        Optional<Post> post = this.postRepository.findById(id);
        if(post.isPresent()){
            return post.get();
        } else {
            throw new DataNotFoundException("post not found");
        }

    }


    public void create(String title, String content, Member author) {
       Post post = new Post();
       post.setTitle(title);
       post.setContent(content);
       post.setCreateDate(LocalDateTime.now());
       post.setAuthor(author);

       this.postRepository.save(post);
    }

    public void delete(Post post) {
        this.postRepository.delete(post);
    }

    public void modify(Post post, String title, String content) {
        post.setTitle(title);
        post.setContent(content);
        post.setModifyDate(LocalDateTime.now());

        this.postRepository.save(post);
    }

    public void like(Post post, Member member) {
        post.getLike().add(member);
        this.postRepository.save(post);
    }

    public void disLike(Post post, Member member) {
        post.getLike().remove(member);
        this.postRepository.save(post);
    }

    public void unLike(Post post, Member member) {
        post.getUnLike().add(member);
        this.postRepository.save(post);
    }

    public void disUnLike(Post post, Member member) {
        post.getUnLike().remove(member);
        this.postRepository.save(post);
    }



}
