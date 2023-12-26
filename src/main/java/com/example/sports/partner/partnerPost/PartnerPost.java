package com.example.sports.partner.partnerPost;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

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
}
