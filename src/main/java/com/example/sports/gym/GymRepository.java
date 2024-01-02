package com.example.sports.gym;

import com.example.sports.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GymRepository extends JpaRepository<Gym, Long> {

    Page<Gym> findAll(Pageable pageable);

    @Query("select "
            + "distinct G "
            + "from Gym G "
//            + "left outer join Member U on P.author=U "
            + "where "
            + "   G.title like %:keyword% "
            + "   or G.address like %:keyword% ")
//            + "   or U.username like %:keyword% ")
    Page<Gym> findAllByKeyword(@Param("keyword") String keyword, Pageable pageable);
}
