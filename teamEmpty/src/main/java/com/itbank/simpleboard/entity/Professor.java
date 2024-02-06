package com.itbank.simpleboard.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.util.Optional;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Data
public class Professor {
    // 교수 번호
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long professor_idx;

    // 교수 사진
    private String professor_img;

    @OneToOne
    @JoinColumn(name="user_idx")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "major_idx")
    private Major major;    // 학과 번호

    @Column(name = "hire_date")
    private Date hireDate;

    public Professor(String professor_img, User user, Major major, Date hireDate) {
        this.professor_img = professor_img;
        this.user = user;
        this.major = major;
        this.hireDate = hireDate;
    }


}
