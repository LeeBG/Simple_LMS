package com.itbank.simpleboard.service;

import com.itbank.simpleboard.entity.Notice;
import com.itbank.simpleboard.repository.manager.NoticeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class NoticeService {
    private final NoticeRepository noticeRepository;
    public List<Notice> selectAll() {
        return noticeRepository.findAll();
    }

    public Notice selectOne(Long idx) {
        return noticeRepository.findById(idx).get();
    }

    @Transactional
    public void noticeAdd(Map<String, String> map) {
        Notice notice = new Notice(
                map.get("title"), map.get("content")
        );
        noticeRepository.save(notice);
    }

    @Transactional
    public Notice noticeUpdate(HashMap<String, Object> map) {
        Long idx = (Long)map.get("idx");
        String title = (String)map.get("title");
        String content = (String)map.get("content");
        Notice notice = noticeRepository.findById(idx).get();
        notice.setTitle(title);
        notice.setContent(content);
//        Date currentDate = new Date(System.currentTimeMillis());
//        notice.setWdate(currentDate);
        return notice;
    }

    @Transactional
    public void increaseViewCount(Long idx) {
        Notice notice = noticeRepository.findById(idx).get();

        // 조회수를 1 증가시킴
        notice.setViewCount(notice.getViewCount() + 1);
        // 변경된 공지사항을 데이터베이스에 저장
        noticeRepository.save(notice);
    }

    @Transactional
    public void noticeDel(Long idx) {
        noticeRepository.deleteById(idx);
    }


}