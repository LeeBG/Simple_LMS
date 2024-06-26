package com.itbank.simpleboard.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class StudentLectureDto {
    private Long idx;
    private String name;
    private String intro;
    private Integer credit;
    private String day;
    private String start;
    private String end;
    private String type;
    private Integer maxCount;
    private Integer currentCount;
    private String semester;
    private Integer grade;
    private String abolition;

    private Long professor_idx;
    private String student_name;
    private String plan;
    private String major;
    private String location;
    private String lectureRoom;

    @QueryProjection
    public StudentLectureDto(Long idx, String name, String intro, Integer credit, String day, String start, String end, String type, Integer maxCount, Integer currentCount, String semester, Integer grade, String abolition, Long professor_idx, String student_name, String plan, String major, String location, String lectureRoom) {
        this.idx = idx;
        this.name = name;
        this.intro = intro;
        this.credit = credit;
        this.day = day;
        this.start = start;
        this.end = end;
        this.type = type;
        this.maxCount = maxCount;
        this.currentCount = currentCount;
        this.semester = semester;
        this.grade = grade;
        this.abolition = abolition;
        this.professor_idx = professor_idx;
        this.student_name = student_name;
        this.plan = plan;
        this.major = major;
        this.location = location;
        this.lectureRoom = lectureRoom;
    }
}
