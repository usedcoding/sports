package com.example.sports.post.repository;

import com.example.sports.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findAll(Pageable pageable);

    @Query("select "
            + "distinct P "
            + "from Post P "
            + "left outer join Member U on P.author=U "
            + "where "
            + "   P.title like %:keyword% "
            + "   or P.content like %:keyword% "
            + "   or U.username like %:keyword% ")
    Page<Post> findAllByKeyword(@Param("keyword") String keyword, Pageable pageable);
}
