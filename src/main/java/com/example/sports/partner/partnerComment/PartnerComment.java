package com.example.sports.partner.partnerComment;

import com.example.sports.member.Member;
import com.example.sports.partner.partnerPost.PartnerPost;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class PartnerComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDate createDate;

    private LocalDate modifyDate;

    private Member author;

    @ManyToOne
    private PartnerPost partnerPost;
}
