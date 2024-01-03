package com.example.sports.partner.partnerPost;

import com.example.sports.member.Member;
import com.example.sports.partner.offer.Offer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class PartnerPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 200)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @CreatedDate
    private LocalDateTime createDate;

    @LastModifiedDate
    private LocalDateTime modifyDate;

    @ManyToOne
    private Member author;

    //cascade = CascadeType.REMOVE -> 부모 엔티티 삭제시 자식 엔티티도 삭제
    @OneToMany(mappedBy = "partnerPost", cascade = CascadeType.REMOVE)
    private List<Offer> offerList;
}
