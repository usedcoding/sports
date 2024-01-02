package com.example.sports.partner.partnerApplicant;

import com.example.sports.member.Member;
import com.example.sports.partner.partnerPost.PartnerPost;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class PartnerApplicant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

    private LocalDateTime modifyDate;

    @ManyToOne
    private Member author;

    @ManyToOne
    private PartnerPost partnerPost;
}
