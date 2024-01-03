package com.example.sports.gym;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Gym {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(unique = true)
    private String address;

    private String time;

    private String price;

    @Column(unique = true)
    private String phoneNum;

    private LocalDateTime createDate;

    private LocalDateTime modifyDate;

    private String thumbnailImg;
}
