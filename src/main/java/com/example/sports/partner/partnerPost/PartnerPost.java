package com.example.sports.partner.partnerPost;

import com.example.sports.comment.Comment;
import com.example.sports.member.Member;
import com.example.sports.partner.partnerApplicant.PartnerApplicant;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
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

    private LocalDate createDate;

    private LocalDate modifyDate;

    @ManyToOne
    private Member author;

    //cascade = CascadeType.REMOVE -> 부모 엔티티 삭제시 자식 엔티티도 삭제
    @OneToMany(mappedBy = "partnerPost", cascade = CascadeType.REMOVE)
    private List<PartnerApplicant> partnerApplicantList;
}
