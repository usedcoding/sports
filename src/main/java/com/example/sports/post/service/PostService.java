package com.example.sports.post.service;

import com.example.sports.post.entity.Post;
import com.example.sports.post.repository.PostRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Builder
public class PostService {
    private final PostRepository postRepository;

    public List<Post> getList() {
        return this.postRepository.findAll();
    }

    public Post getPost(Long id) {
        Optional<Post> post = this.postRepository.findById(id);
        return post.get();
    }


    public void create(String title, String content) {
       Post post = Post.builder()
                .title(title)
                .content(content)
                .createDate(LocalDateTime.now())
                .build();

       this.postRepository.save(post);
    }

}
