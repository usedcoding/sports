package com.example.sports.partner.partnerPost;

import com.example.sports.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PartnerPostRepository extends JpaRepository<PartnerPost, Long> {
    Page<PartnerPost> findAll(Pageable pageable);

    @Query("select "
            + "distinct P "
            + "from PartnerPost P "
            + "left outer join Member U on P.author=U "
            + "where "
            + "   P.title like %:keyword% "
            + "   or P.content like %:keyword% "
            + "   or U.username like %:keyword% ")
    Page <PartnerPost> findAllByKeyword(@Param("keyword") String keyword, Pageable pageable);
}
