package com.itbank.simpleboard.service;

import com.itbank.simpleboard.dto.AcademicCalendarDto;
import com.itbank.simpleboard.entity.AcademicCalendar;
import com.itbank.simpleboard.repository.AcademicCalendarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AcademicCalendarService {
    private final AcademicCalendarRepository academicCalendarRepository;

    // 학사 일정 조회
    public List<AcademicCalendar> findCalendarAll() {
        return academicCalendarRepository.findAll();
    }

    // 학사 일정 추가
    @Transactional
    public AcademicCalendar addCalendar(AcademicCalendarDto calendarDto) {
        // AcademicCalendarDto에서 AcademicCalendar로 변환
        AcademicCalendar academicCalendar = new AcademicCalendar();
        academicCalendar.setTitle(calendarDto.getTitle());
        academicCalendar.setStart_date(Date.valueOf(String.valueOf(calendarDto.getStart_date())).toLocalDate());
        academicCalendar.setEnd_date(Date.valueOf(String.valueOf(calendarDto.getEnd_date())).toLocalDate());
        academicCalendar.setCreated_date(calendarDto.getCreated_date());

        // 저장
        return academicCalendarRepository.save(academicCalendar);
    }

    public AcademicCalendarDto getCalendarById(Long id) {
        Optional<AcademicCalendar> calendarOptional = academicCalendarRepository.findById(id);

        if (calendarOptional.isPresent()) {
            // DB 에 있는 학사일정 을 AcademicCalendarDto 로 변환하여 반환
            AcademicCalendar academicCalendar = calendarOptional.get();
            AcademicCalendarDto academicCalendarDto = new AcademicCalendarDto();

            academicCalendarDto.setIdx(academicCalendar.getIdx());
            academicCalendarDto.setStart_date(academicCalendar.getStart_date());
            academicCalendarDto.setEnd_date(academicCalendar.getEnd_date());
            academicCalendarDto.setTitle(academicCalendar.getTitle());

            return academicCalendarDto;
        } else {
            return null;
        }
    }

    @Transactional
    public AcademicCalendar editCalendar(Long id, AcademicCalendarDto calendarDto) {
        // 학사일정 엔터티를 데이터베이스에서 가져온다.
        AcademicCalendar existingCalendar = academicCalendarRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("학사일정이 존재하지 않습니다. id: " + id));

        // AcademicCalendarDto에서 AcademicCalendar로 변환
        existingCalendar.setTitle(calendarDto.getTitle());
        existingCalendar.setStart_date(Date.valueOf(String.valueOf(calendarDto.getStart_date())).toLocalDate());
        existingCalendar.setEnd_date(Date.valueOf(String.valueOf(calendarDto.getEnd_date())).toLocalDate());
        existingCalendar.setCreated_date(calendarDto.getCreated_date());

        // 수정된 학사일정을 저장
        return academicCalendarRepository.save(existingCalendar);
    }
}
