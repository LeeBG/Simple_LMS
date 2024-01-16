package com.itbank.simpleboard.controller;

import com.itbank.simpleboard.dto.LectureDto;
import com.itbank.simpleboard.dto.StudentDto;
import com.itbank.simpleboard.dto.UserDTO;
import com.itbank.simpleboard.entity.*;
import com.itbank.simpleboard.service.EnrollmentService;
import com.itbank.simpleboard.service.LectureService;
import com.itbank.simpleboard.service.SituationServive;
import com.itbank.simpleboard.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/student")
@RequiredArgsConstructor
@Slf4j
public class StudentController {


    private final SituationServive situationServive;
    //  수강신청
    private final EnrollmentService enrollmentService;

    private final LectureService lectureService;

    private final StudentService studentService;
    @GetMapping("/enroll")
    public ModelAndView enrollList(HttpSession session, String searchType, String keyword) {
        long startTime = System.currentTimeMillis();
        ModelAndView mav = new ModelAndView("home");
        UserDTO dto = (UserDTO)session.getAttribute("user");
        List<LectureDto> dtos = null;
        if(searchType == null && keyword == null){
            dtos = lectureService.selectAll();
        }else{
            dtos = lectureService.selectAll(searchType, keyword);
        }

        StudentDto student = studentService.findByUserIdx(dto.getIdx());
        List<Enrollment> enrollmentList = enrollmentService.findByStudent(student.getIdx());
        List<LectureDto> lectureList = new ArrayList<>();
        for(Enrollment e : enrollmentList){
            LectureDto lecturedto = new LectureDto();
            lecturedto.setCredit(e.getLecture().getCredit());
            lecturedto.setDay(e.getLecture().getDay());
            lecturedto.setStart(e.getLecture().getStart());
            lecturedto.setEnd(e.getLecture().getEnd());
            lecturedto.setIdx(e.getLecture().getIdx());
            lecturedto.setGrade(e.getLecture().getGrade());
            lecturedto.setIntro(e.getLecture().getIntro());
            lecturedto.setMajor(e.getLecture().getMajor().getIdx());
            lecturedto.setPlan(e.getLecture().getPlan());
            lecturedto.setName(e.getLecture().getName());
            lecturedto.setType(e.getLecture().getType().toString());
            lecturedto.setVisible(e.getLecture().getVisible().toString());
            lecturedto.setProfessor(e.getLecture().getProfessor());
            lecturedto.setSemester(e.getLecture().getSemester());
            lecturedto.setMaxCount(e.getLecture().getMaxCount());
            lecturedto.setCurrentCount(e.getLecture().getCurrentCount());
            lecturedto.setLectureRoom(e.getLecture().getLectureRoom().getIdx());
            lectureList.add(lecturedto);
        }

        if(dto != null && dto.getRole().toString().equals("학생")){
            mav.setViewName("student/enrollment");
            mav.addObject("lectureList",lectureList);
            mav.addObject("stuIdx", student.getIdx());
            mav.addObject("list",dtos);
        }
        long endTime = System.currentTimeMillis();
        log.info("총 실행시간 : "+(endTime-startTime));
        return mav;
    }

    // 수강 신청프로세스
    @PostMapping("/enroll")
    public ModelAndView enrollPro(Long stuIdx, Long idx, HttpSession session, RedirectAttributes ra) {
        long startTime = System.currentTimeMillis();
        ModelAndView mav = new ModelAndView("home");
        UserDTO userDto = (UserDTO)session.getAttribute("user");

        Long userIdx = userDto.getIdx();
        if(userDto == null){
            // 로그인 안되어 있으면 로그인 화면으로 이거는 인터셉터에서 처리도 가능하다.
            mav.setViewName("redirect:/");
        }else if(userDto.getRole().toString().equals("학생")){

            Enrollment enrollment = enrollmentService.save(stuIdx, idx);

            if(enrollment != null) {
                ra.addFlashAttribute("msg","수강신청되었습니다.");
            }
            mav.setViewName("redirect:/student/enroll");
        }else{
            // 로그인이 안되어 있거나 학생이 아니야???
            // 학생이 아닌데 어케 수강신청함???? - 너 돌아가
            mav.setViewName("redirect:/side");
        }
        long endTime = System.currentTimeMillis();
        log.info("총 실행시간 : "+(endTime-startTime));
        return mav;
    }

    // 수강 취소
    @GetMapping("/cancel")
    public String cancel(Long stuIdx, Long idx, HttpSession session, RedirectAttributes ra) {
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
//        취소
        enrollmentService.cancel(stuIdx, idx);
        return "redirect:/enroll";
    }

}
